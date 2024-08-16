package de.outstare.fortbattleplayer.parser;

/*
 Copyright (c) 2010 Daniel Raap

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.outstare.fortbattleplayer.logging.LogHelper;
import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Battlefield;
import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.CombatantState;
import de.outstare.fortbattleplayer.model.Fortbattle;
import de.outstare.fortbattleplayer.model.Sector;
import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.impl.SimpleArea;
import de.outstare.fortbattleplayer.model.impl.SimpleBattleField;
import de.outstare.fortbattleplayer.model.impl.SimpleCombatant;
import de.outstare.fortbattleplayer.model.impl.SimpleSector;
import de.outstare.fortbattleplayer.model.impl.SimpleWeapon;
import de.outstare.fortbattleplayer.player.Action;
import de.outstare.fortbattleplayer.player.Battleplan;
import de.outstare.fortbattleplayer.player.CombatantTurn;
import de.outstare.fortbattleplayer.player.PlayerConfiguration;
import de.outstare.fortbattleplayer.player.Round;
import de.outstare.fortbattleplayer.player.actions.MoveAction;
import de.outstare.fortbattleplayer.player.actions.TargetAction;
import de.outstare.fortbattleplayer.statistics.AllStatistics;
import de.outstare.fortbattleplayer.statistics.BattleStatistics;
import de.outstare.fortbattleplayer.statistics.CombatantStatType;
import de.outstare.fortbattleplayer.statistics.CombatantStatistic;
import de.outstare.fortbattleplayer.statistics.RoundStatGenerator;

/**
 * creates model objects out of the plain data according to
 * http://devblog.the-west.net/?p=225&lang=en
 * 
 * @author daniel
 */
public class JSONDataParser {
	/**
	 * visible for inner class
	 */
	static final transient Logger LOG = Logger.getLogger(JSONDataParser.class.getName());

	private final JSONObject data;
	/**
	 * visible for inner class
	 */
	Image mapImage = null;
	private Thread mapPaintThread = null;
	/**
	 * lookup for {@link Area}s (maps the-west map indexes to model objects)
	 */
	private final Map<Integer, Area> areas = new HashMap<Integer, Area>();
	/**
	 * lookup for {@link Combatant}s (maps the-west player IDs to model objects)
	 */
	private final Map<Integer, Combatant> westPlayers = new HashMap<Integer, Combatant>();
	/**
	 * where the players placed the combatants (maybe multiple on same field)
	 */
	private final Map<Combatant, CombatantState> preBattleCombatantStates = new HashMap<Combatant, CombatantState>();
	/**
	 * where the player actual stands at the beginning
	 */
	private final Map<Combatant, CombatantState> initialCombatantStates = new HashMap<Combatant, CombatantState>();
	private final CombatantStatistic combatantStats;
	private final RoundStatGenerator roundStatistics = new RoundStatGenerator();

	/**
	 * @param textdata
	 *            a {@link JSONObject} in textformat
	 * @throws JSONException
	 *             if the textdata is not JSON
	 */
	public JSONDataParser(final String textdata) throws JSONException {
		data = new JSONObject(textdata);
		combatantStats = new CombatantStatistic(numberOfPlayedRounds());
	}

	/**
	 * @return
	 * @throws JSONException
	 */
	int numberOfPlayedRounds() throws JSONException {
		return data.getInt("roundsplayed");
	}

	/**
	 * @return the fortbattle for the given data
	 * @throws JSONException
	 */
	public Fortbattle getBattle() throws JSONException {
		// to improve performance we disable player logging while parsing
		disableAllOtherLogging();
		final long start = System.currentTimeMillis();
		// order is important!!
		// 1. the map where the combatants will be placed
		final Battlefield battlefield = getBattlefield();
		// 2. the combatants which execute actions
		final Map<CombatantSide, Set<Combatant>> combatants = new HashMap<CombatantSide, Set<Combatant>>();
		final Set<Combatant> attackers = getAttackers();
		final Set<Combatant> defenders = getDefenders();
		combatants.put(CombatantSide.ATTACKER, attackers);
		combatants.put(CombatantSide.DEFENDER, defenders);
		// 3. the actions are the actual battle
		final BattleStatistics battleStats = new BattleStatistics(attackers.size(), defenders.size());
		final Battleplan actions = getPlan(battleStats);
		final long end = System.currentTimeMillis();
		LogHelper.restoreLogLevels();
		LOG.info("parsed battle info in " + (end - start) + " ms");

		return new Fortbattle(getFortname(), combatants, battlefield, actions, new AllStatistics(combatantStats,
		        battleStats, roundStatistics), getMapImage());
	}

	/**
	 * disable all logging and only keep our own logging
	 * 
	 * @throws SecurityException
	 */
	private void disableAllOtherLogging() throws SecurityException {
		boolean logParsing;
		try {
			logParsing = Boolean.getBoolean("fbplayer.logParsing");
		} catch (final Exception e) {
			// in the applet accessing properties may not be allowed
			logParsing = false;
		}
		if (!logParsing) {
			LogHelper.backupLogLevels();
			final Level ownLevelBackp = LOG.getLevel();
			// TODO just disable logging of gui, player and model?
			LogHelper.setGlobalLogLevel(Level.OFF);
			try {
				LOG.setLevel(ownLevelBackp);
			} catch (final AccessControlException e) {
				LOG.info("not allowed to set log level for parser.");
			}
		}
	}

	/**
	 * @return all attackers
	 * @throws JSONException
	 */
	protected Set<Combatant> getAttackers() throws JSONException {
		final Set<Combatant> attackers = new HashSet<Combatant>();

		final JSONArray list = data.getJSONArray("attackerlist");
		parsePlayers(attackers, CombatantSide.ATTACKER, list);

		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("parsed " + attackers.size() + " attackers");
		}
		return attackers;
	}

	/**
	 * @return all defenders
	 * @throws JSONException
	 */
	protected Set<Combatant> getDefenders() throws JSONException {
		final Set<Combatant> defenders = new HashSet<Combatant>();

		final JSONArray list = data.getJSONArray("defenderlist");
		parsePlayers(defenders, CombatantSide.DEFENDER, list);

		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("parsed " + defenders.size() + " defenders");
		}
		return defenders;
	}

	/**
	 * @param players
	 *            store for parsed {@link Combatant}s
	 * @param side
	 *            of each parsed {@link Combatant}
	 * @param list
	 *            data to be parsed
	 * @throws JSONException
	 */
	private void parsePlayers(final Set<Combatant> players, final CombatantSide side, final JSONArray list)
	        throws JSONException {
		final String posKey;
		final boolean hasPreBattleData;
		// initial position depends on version
		if (list.length() > 0 && list.getJSONObject(0).has("firstroundpos")) {
			// since fixed 1.29
			posKey = "firstroundpos";
			hasPreBattleData = true;
		} else if (list.length() > 0 && list.getJSONObject(0).has("startposidx")) {
			// since 1.29
			posKey = "startposidx";
			hasPreBattleData = true;
		} else {
			// death pos, not start pos :(
			posKey = "posidx";
			hasPreBattleData = false;
		}

		for (int i = 0; i < list.length(); i++) {
			final JSONObject playerData = list.getJSONObject(i);

			final CombatantState firstRoundState = getCombatantState(posKey, playerData);
			final Combatant player = createCombatant(side, playerData, firstRoundState);
			initialCombatantStates.put(player, firstRoundState);

			final Integer playerID = Integer.valueOf(playerData.getInt("westid"));
			westPlayers.put(playerID, player);
			players.add(player);

			updateStats(playerData, side);

			if (hasPreBattleData) {
				createPreBattleState(playerData, firstRoundState, player);
			}
		}
	}

	/**
	 * @param playerData
	 * @param firstRoundState
	 * @param player
	 * @throws JSONException
	 */
	private void createPreBattleState(final JSONObject playerData, final CombatantState firstRoundState,
	        final Combatant player) throws JSONException {
		// location before start of battle
		final Integer placingIndex = Integer.valueOf(playerData.getInt("startposidx"));
		final Area placing = areas.get(placingIndex);
		final CombatantState preBattleState = firstRoundState.changePosition(placing);
		preBattleCombatantStates.put(player, preBattleState);
	}

	/**
	 * @param side
	 * @param playerData
	 * @param firstRoundState
	 * @return
	 * @throws JSONException
	 */
	private Combatant createCombatant(final CombatantSide side, final JSONObject playerData,
	        final CombatantState firstRoundState) throws JSONException {
		final int maxLP = playerData.getInt("maxhp");
		final String playerName = playerData.getString("name");
		final String city = playerData.optString("townname");
		CharacterClass charClass;
		try {
			final int charClassNumber = playerData.getInt("charclass");
			charClass = CharacterClass.values()[charClassNumber + 1];
		} catch (final JSONException e) {
			// not present in old logs
			charClass = null;
		}
		final Weapon weapon = getWeapon(playerData);
		final Combatant player = new SimpleCombatant(side, firstRoundState, maxLP, playerName, charClass, weapon, city);

		return player;
	}

	/**
	 * @param playerData
	 * @return the weapon in the playerData
	 * @throws JSONException
	 *             if the playerData does not contain weapon data
	 */
	private Weapon getWeapon(final JSONObject playerData) throws JSONException {
		final int weaponId = playerData.getInt("weaponid");
		final String weaponName = playerData.getString("weaponname");
		final int minDmg = playerData.getInt("weaponmindmg");
		final int maxDmg = playerData.getInt("weaponmaxdmg");
		final SimpleWeapon weapon = new SimpleWeapon(weaponId, weaponName, minDmg, maxDmg);
		return weapon;
	}

	/**
	 * @param posKey
	 * @param playerData
	 * @return
	 * @throws JSONException
	 */
	private CombatantState getCombatantState(final String posKey, final JSONObject playerData) throws JSONException {
		final Integer posIndex = Integer.valueOf(playerData.optInt(posKey));
		final Integer targetIndex = Integer.valueOf(playerData.getInt("targetidx"));
		final int curLP = playerData.getInt("starthp");
		final Area pos = areas.get(posIndex);
		final Area target = areas.get(targetIndex);
		final CombatantState firstRoundState = new CombatantState(pos, curLP, target, false);
		return firstRoundState;
	}

	/**
	 * @param playerData
	 * @param side
	 * @throws JSONException
	 */
	private void updateStats(final JSONObject playerData, final CombatantSide side) throws JSONException {
		final Iterator<String> keys = playerData.keys();
		while (keys.hasNext()) {
			final String key = keys.next();
			try {
				final CombatantStatType statType = CombatantStatType.valueOf(key);
				combatantStats.addData(statType, side, playerData.getInt(key));
			} catch (final IllegalArgumentException e) {
				// key is not specified for statistics
			}
		}
	}

	/**
	 * @return the map on which the battle takes place
	 * @throws JSONException
	 */
	protected Battlefield getBattlefield() throws JSONException {
		final JSONObject map = data.getJSONObject("map");
		final int mapWidth = map.getInt("width");
		final int mapHeight = map.getInt("height");
		createFortImage(mapWidth, mapHeight, map.getJSONArray("tiles"));
		final JSONArray sectorData = map.getJSONArray("sectors");
		final List<Sector> sectors = parseSectors(sectorData);
		final JSONArray cellData = map.getJSONArray("cells");
		final Set<Area> cells = parseCells(cellData, sectors, new MapCoordinates(mapWidth));

		return new SimpleBattleField(mapWidth, mapHeight, cells, sectors);
	}

	/**
	 * @return the image of the fort if it is already created, else
	 *         <code>null</code>
	 */
	protected Image getMapImage() {
		if (mapPaintThread != null) {
			// wait that image is created
			try {
				mapPaintThread.join();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		return mapImage;
	}

	/**
	 * @param jsonArray
	 * @throws JSONException
	 * @throws IOException
	 */
	private void createFortImage(final int mapWidth, final int mapHeight, final JSONArray tiles) throws JSONException {
		final int tileSizeX = 24;
		final int tileSizeY = 24;
		mapImage = new BufferedImage(mapWidth * tileSizeX, mapHeight * tileSizeY, BufferedImage.TYPE_INT_ARGB);
		// don't wait that the image is loaded
		mapPaintThread = new Thread() {
			@Override
			public void run() {
				try {
					final String imageFilePath = "/images/tiletexture.png";
					final InputStream imageInput = JSONDataParser.class.getResourceAsStream(imageFilePath);
					final BufferedImage texture;
					if (imageInput == null) {
						// paint all tiles in one color
						texture = new BufferedImage(3120, 360, BufferedImage.TYPE_INT_ARGB);
						final Graphics textureGraphics = texture.getGraphics();
						textureGraphics.setColor(Color.ORANGE);
						textureGraphics.fillRect(0, 0, 3120, 360);
					} else {
						texture = ImageIO.read(imageInput);
					}

					final Graphics g = mapImage.getGraphics();
					try {
						for (int i = 0; i < tiles.length(); i++) {
							final JSONArray tile = tiles.getJSONArray(i);
							final int x = tile.getInt(0);
							final int y = tile.getInt(1);
							final int width = tile.getInt(2);
							final int height = tile.getInt(3);
							final int mapX = tile.getInt(4);
							final int mapY = tile.getInt(5);

							if (LOG.isLoggable(Level.FINE)) {
								LOG.fine("taking tile at " + x * tileSizeX + "/" + y * tileSizeY + " with size "
								        + width * tileSizeX + "x" + height * tileSizeY);
							}
							g.drawImage(texture, mapX * tileSizeX, mapY * tileSizeY, (mapX + width) * tileSizeX,
							        (mapY + height) * tileSizeY, x * tileSizeX, y * tileSizeY, (x + width) * tileSizeX,
							        (y + height) * tileSizeY, null);
						}
					} catch (final JSONException e) {
						e.printStackTrace();
					} finally {
						g.dispose();
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		};
		mapPaintThread.start();
	}

	/**
	 * @param cellData
	 * @param sectors
	 * @param mapCoordinates
	 * @return
	 * @throws JSONException
	 */
	private Set<Area> parseCells(final JSONArray cellData, final List<Sector> sectors,
	        final MapCoordinates mapCoordinates) throws JSONException {
		final Set<Area> cells = new HashSet<Area>();
		areas.clear();

		for (int index = 0; index < cellData.length(); index++) {
			final Point coord = mapCoordinates.point(index);
			final int sectorID = cellData.getInt(index);
			final Area cell = new SimpleArea(coord, sectors.get(sectorID));
			cells.add(cell);
			areas.put(Integer.valueOf(index), cell);
		}

		return cells;
	}

	/**
	 * @param sectorData
	 * @return
	 * @throws JSONException
	 */
	protected List<Sector> parseSectors(final JSONArray sectorData) throws JSONException {
		final List<Sector> sectors = new ArrayList<Sector>();
		for (int i = 0; i < sectorData.length(); i++) {
			final JSONObject sector = sectorData.getJSONObject(i);
			final int height = sector.getInt("height");
			final boolean attackerSpawn = sector.optBoolean("attackerSpawn");
			final int attackerBonus = sector.optInt("attackerBonus");
			final boolean defenderSpawn = sector.optBoolean("defenderSpawn");
			final int defenderBonus = sector.optInt("defenderBonus");
			final int classBonus = sector.optInt("classBonus");
			CharacterClass bonusClass = null;
			try {
				final int classTypeInt = sector.getInt("classType");
				bonusClass = CharacterClass.values()[classTypeInt + 1];
			} catch (final JSONException e) {
				// field may not exist
			}
			final boolean isFlag = sector.optBoolean("flag");
			final Sector newSector = new SimpleSector(height, defenderSpawn, attackerSpawn, attackerBonus,
			        defenderBonus, isFlag, classBonus, bonusClass);
			sectors.add(newSector);
		}
		return sectors;
	}

	/**
	 * @param stats
	 * @return the complete battle
	 * @throws JSONException
	 * @requires getAttackers() and getDefenders() has be called to parse the
	 *           players
	 */
	protected Battleplan getPlan(final BattleStatistics stats) throws JSONException {
		final List<Round> rounds = new LinkedList<Round>();

		final JSONArray typeNames = data.getJSONArray("logtypes");
		final JSONArray log = data.getJSONArray("log");
		/*
		 * the log combines Rounds (all Players), Turns (one Player) and Actions
		 * therefore the parsing code is also crap :(
		 */
		List<CombatantTurn> turns = null;
		Map<Combatant, CombatantState> states = createFirstRound(rounds);
		List<Action> actions = null;
		Combatant currentPlayer = null;
		addCombatantObserver(stats);
		int currentRound = 1;
		for (int i = 0; i < log.length(); i += 2) {
			final int typeValue = (int) log.getLong(i);
			final int value = (int) log.getLong(i + 1);
			try {
				final LogType type = LogType.valueOf(typeNames.getString(typeValue));
				// System.out.println(type.toString() + " " +
				// Integer.toString(value));

				switch (type) {
				case ROUNDSTART:
					// store current round
					if (turns != null) {
						if (actions != null) {
							turns.add(new CombatantTurn(actions));
							actions = null;
						}
						states = saveRound(currentRound, rounds, turns, states);
					}
					// create new round
					currentRound = value;
					stats.setRound(currentRound);
					turns = new ArrayList<CombatantTurn>();
					if (LOG.isLoggable(Level.FINE)) {
						LOG.fine("Round " + value);
					}
					break;
				case CHARTURN:
					// save actions of current player
					if (currentPlayer != null && actions != null && turns != null) {
						turns.add(new CombatantTurn(actions));
					}
					// select next player
					currentPlayer = westPlayers.get(Integer.valueOf(value));
					actions = new ArrayList<Action>();
					if (LOG.isLoggable(Level.FINE)) {
						LOG.fine("  Player " + currentPlayer.toString());
					}
					break;
				default:
					// set actions for player
					final Action action = type.parse(value, currentPlayer, areas, westPlayers);
					if (action != null) {
						if (actions == null) {
							LOG.warning("illegal state for " + type);
							continue;
						}
						actions.add(action);
						if (LOG.isLoggable(Level.FINE)) {
							LOG.fine("    Action " + action);
						}
					}
				}
			} catch (final IllegalArgumentException e) {
				LOG.warning("unknown log type: " + typeNames.getString(typeValue) + " [" + typeValue + "]");
				continue;
			}
		}
		// add last actions
		if (currentPlayer != null && actions != null && turns != null) {
			turns.add(new CombatantTurn(actions));
		}
		// add last round
		if (turns != null) {
			/* states = */saveRound(currentRound, rounds, turns, states);
		}
		removeCombatantObserver(stats);
		resetAllStates();

		return new Battleplan(rounds);
	}

	/**
	 * @param currentRound
	 * @param rounds
	 * @param turns
	 * @param states
	 * @return
	 */
	private Map<Combatant, CombatantState> saveRound(final int roundNo, final List<Round> rounds,
	        final List<CombatantTurn> turns, final Map<Combatant, CombatantState> states) {
		final Round round = new Round(roundNo, states, turns);
		roundStatistics.addRoundState(states);
		rounds.add(round);
		final Map<Combatant, CombatantState> newStates = getStateAfterRound(round);
		return newStates;
	}

	/**
     * 
     */
	private void resetAllStates() {
		resetBattlefieldState();
		resetCombatantStates();
	}

	/**
     * 
     */
	private void removeCombatantObserver(final CombatantObserver stats) {
		for (final Combatant c : westPlayers.values()) {
			c.removeObserver(stats);
		}
	}

	/**
     * 
     */
	private void addCombatantObserver(final CombatantObserver stats) {
		for (final Combatant c : westPlayers.values()) {
			c.addObserver(stats);
		}
	}

	/**
	 * This will add the first round. Normally nothing happens here, but we show
	 * the movement from the wanted positions to the actual positions. This also
	 * initializes the states of the combatants.
	 * 
	 * @param rounds
	 *            where this
	 * @return
	 */
	private Map<Combatant, CombatantState> createFirstRound(final List<Round> rounds) {
		final Map<Combatant, CombatantState> combatantStates;
		if (preBattleCombatantStates.isEmpty()) {
			combatantStates = initialCombatantStates;
		} else {
			combatantStates = preBattleCombatantStates;
		}
		final List<CombatantTurn> actions = new ArrayList<CombatantTurn>();

		// disabled because the data in the log is wrong!
		// in 1.29 for online players the last target is available
		createTargetActions(combatantStates, actions);

		// no movement Action of the players to the pos at the beginning,
		// because the first round forces the correct positions
		final Round preBattleRound = new Round(1, combatantStates, actions);
		rounds.add(preBattleRound);
		return getStateAfterRound(preBattleRound);
	}

	/**
	 * create for the targets in the given states actions and add them to the
	 * given list
	 * 
	 * @param combatantStates
	 * @param actions
	 */
	private void createTargetActions(final Map<Combatant, CombatantState> combatantStates,
	        final List<CombatantTurn> actions) {
		// only if pre battle positions exist
		if (combatantStates == preBattleCombatantStates) {
			for (final Entry<Combatant, CombatantState> combatantState : combatantStates.entrySet()) {
				final Combatant combatant = combatantState.getKey();
				final Area currentPos = combatantState.getValue().getPosition();
				final Area newPos = initialCombatantStates.get(combatant).getPosition();
				if (!currentPos.equals(newPos)) {
					final List<Action> list = createTargetTurn(combatantState);
					addMoveAction(combatant, newPos, list);
					actions.add(new CombatantTurn(list));
				}
			}
		}
	}

	/**
	 * @param combatant
	 * @param newPos
	 * @param actions
	 */
	private void addMoveAction(final Combatant combatant, final Area newPos, final List<Action> actions) {
		final Action move = new MoveAction(combatant, newPos);
		actions.add(move);
	}

	/**
	 * @param combatantState
	 * @return
	 */
	private List<Action> createTargetTurn(final Entry<Combatant, CombatantState> combatantState) {
		final Action action = new TargetAction(combatantState.getKey(), combatantState.getValue().getTarget());
		final List<Action> list = new ArrayList<Action>(2);
		list.add(action);
		return list;
	}

	private void resetBattlefieldState() {
		for (final Area area : areas.values()) {
			area.free();
		}
	}

	/**
	 * sets the initial state
	 */
	private void resetCombatantStates() {
		final Map<Combatant, CombatantState> startStates;
		// if available (added after 1.29) use pre battle positions for initial
		// drawing
		if (preBattleCombatantStates.isEmpty()) {
			startStates = initialCombatantStates;
		} else {
			startStates = preBattleCombatantStates;
		}

		for (final Entry<Combatant, CombatantState> entry : startStates.entrySet()) {
			final Combatant combatant = entry.getKey();
			combatant._setState(entry.getValue());
		}

	}

	/**
	 * @return
	 */
	private Map<Combatant, CombatantState> getStateAfterRound(final Round round) {
		round.execute(PlayerConfiguration.NO_DELAY);
		final Map<Combatant, CombatantState> newStates = new HashMap<Combatant, CombatantState>();
		for (final Combatant combatant : initialCombatantStates.keySet()) {
			newStates.put(combatant, combatant._state());
		}
		return newStates;
	}

	private static class MapCoordinates {
		private final int mapwidth;

		/**
		 * @param mapwidth
		 */
		MapCoordinates(final int mapwidth) {
			this.mapwidth = mapwidth;
		}

		// int index(final int x, final int y) {
		// return x + y * mapwidth;
		// }

		Point point(final int index) {
			return new Point(index % mapwidth, index / mapwidth);
		}
	}

	/**
	 * @return the name of the fort at which the battle took place
	 * @throws JSONException
	 */
	protected String getFortname() throws JSONException {
		return data.optString("fortname");
	}
}
