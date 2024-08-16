package de.outstare.fortbattleplayer.statistics;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.SectorBonus;
import de.outstare.fortbattleplayer.model.Weapon;

/**
 * 
 * @author daniel
 */
public class BattleStatistics implements DynamicStatistics, CombatantObserver {
	private static final transient Logger LOG = Logger.getLogger(BattleStatistics.class.getName());
	private final Map<Combatant, OnlineCounter> onliners = new HashMap<Combatant, OnlineCounter>();
	private final Map<CombatantSide, List<CriticalHit>> critHits = new HashMap<CombatantSide, List<CriticalHit>>();
	{
		for (final CombatantSide side : CombatantSide.values()) {
			critHits.put(side, new ArrayList<CriticalHit>());
		}
	}
	private final Map<CombatantSide, Set<PositionSwitch>> swapList = new HashMap<CombatantSide, Set<PositionSwitch>>();
	{
		for (final CombatantSide side : CombatantSide.values()) {
			swapList.put(side, new HashSet<PositionSwitch>());
		}
	}
	private final AllCombatantSidesCounter crits = new AllCombatantSidesCounter();
	private final AllCombatantSidesCounter moves = new AllCombatantSidesCounter();
	private final AllCombatantSidesCounter swaps = new AllCombatantSidesCounter();
	private final AllCombatantSidesCounter attackBonuses = new AllCombatantSidesCounter();
	private final AllCombatantSidesCounter defenseBonuses = new AllCombatantSidesCounter();
	private final Map<Combatant, Counter> shots = new HashMap<Combatant, Counter>();
	private final Map<CombatantSide, RoundStatistics> shotsPerRound = new HashMap<CombatantSide, RoundStatistics>();
	{
		for (final CombatantSide side : CombatantSide.values()) {
			shotsPerRound.put(side, new RoundStatistics("shots", "shots"));
		}
	}
	private final Map<CombatantSide, RoundStatistics> hitsPerRound = new HashMap<CombatantSide, RoundStatistics>();
	{
		for (final CombatantSide side : CombatantSide.values()) {
			hitsPerRound.put(side, new RoundStatistics("hits", "hits"));
		}
	}
	private final Map<CombatantSide, AtomicInteger> shotRoundCounter = new HashMap<CombatantSide, AtomicInteger>();
	{
		for (final CombatantSide side : CombatantSide.values()) {
			shotRoundCounter.put(side, new AtomicInteger());
		}
	}
	private final Map<CombatantSide, AtomicInteger> hitRoundCounter = new HashMap<CombatantSide, AtomicInteger>();
	{
		for (final CombatantSide side : CombatantSide.values()) {
			hitRoundCounter.put(side, new AtomicInteger());
		}
	}
	private final Map<Combatant, Counter> victims = new HashMap<Combatant, Counter>();
	private final List<Combatant> turnOrder = new LinkedList<Combatant>();
	private final List<AllCombatantSidesCounter> actorsPerRound = new ArrayList<AllCombatantSidesCounter>(50);
	private final Map<Area, AreaStatistic> fieldStats = new HashMap<Area, AreaStatistic>();
	private final int _numberOfDefenders;
	private final int _numberOfAttacker;

	private int currentRoundNo = 0;
	private Boolean firstRound = null;
	private AllCombatantSidesCounter actorsThisRound = new AllCombatantSidesCounter();

	/**
	 * set the current round. all following method calls will belong to this
	 * round till this method is called again with another parameter.
	 * 
	 * @param no
	 */
	public void setRound(final int no) {
		if (firstRound == null) {
			firstRound = Boolean.TRUE;
		} else if (firstRound.booleanValue()) {
			firstRound = Boolean.FALSE;
			// the turn order is determined only in the first round
			for (final Combatant player : onliners.keySet()) {
				if (!turnOrder.contains(player)) {
					LOG.info("could not determine turn order for " + player);
				}
			}
		}
		addAndResetRoundCounter(shotRoundCounter, shotsPerRound);
		addAndResetRoundCounter(hitRoundCounter, hitsPerRound);
		actorsPerRound.add(actorsThisRound);
		actorsThisRound = new AllCombatantSidesCounter();
		currentRoundNo = no;
	}

	/**
	 * this adds the counters for the last round to the total counts per round
	 * and resets the given counter
	 * 
	 * @param roundCounter
	 * @param allRounds
	 */
	void addAndResetRoundCounter(final Map<CombatantSide, AtomicInteger> roundCounter,
	        final Map<CombatantSide, RoundStatistics> allRounds) {
		for (final Entry<CombatantSide, AtomicInteger> mapping : roundCounter.entrySet()) {
			final CombatantSide side = mapping.getKey();
			final AtomicInteger roundShots = mapping.getValue();
			allRounds.get(side).addValue(roundShots.get());
			// reset for next round
			roundShots.set(0);
		}
	}

	/**
	 * @param combatant
	 * @return
	 */
	int combatantLifetime(final Combatant combatant) {
		final OnlineCounter onAndOffline = onliners.get(combatant);
		if (onAndOffline == null) {
			return 0;
		}
		return onAndOffline.sumOfEvents();
	}

	/**
	 * @param numberOfAttacker
	 * @param numberOfDefenders
	 */
	public BattleStatistics(final int numberOfAttacker, final int numberOfDefenders) {
		_numberOfAttacker = numberOfAttacker;
		_numberOfDefenders = numberOfDefenders;
	}

	/**
	 * @param number
	 * @return
	 */
	int round(final double number) {
		return (int) Math.round(number);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#percentAbleToShoot(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int percentAbleToShoot(final CombatantSide side) {
		double sum = 0;
		int players = 0;
		for (final Entry<Combatant, Counter> playerShots : shots.entrySet()) {
			final Combatant combatant = playerShots.getKey();
			if (isOnSide(side, combatant)) {
				final int lifetime = combatantLifetime(combatant);
				final int roundsShooting = playerShots.getValue().getValue();
				sum += roundsShooting * 100.0 / lifetime;
				players++;
			}
		}
		if (players == 0) {
			return 0;
		}
		return round(sum / players);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#amountOfAttackBonus(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int amountOfAttackBonus(final CombatantSide side) {
		return attackBonuses.getSideValue(side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#amountOfDefenseBonus(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int amountOfDefenseBonus(final CombatantSide side) {
		return defenseBonuses.getSideValue(side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfMovesPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double numberOfMovesPerPlayer(final CombatantSide side) {
		double result = 0;
		switch (side) {
		case ATTACKER:
			result = moves.getSideValue(side) / (double) _numberOfAttacker;
			break;
		case DEFENDER:
			result = moves.getSideValue(side) / (double) _numberOfDefenders;
			break;
		default:
			result = moves.getSideValue(null) / (double) (_numberOfAttacker + _numberOfDefenders);
		}
		return result;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfOffliners(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfOffliners(final CombatantSide side) {
		int offliner = 0;
		for (final Entry<Combatant, OnlineCounter> playerStats : onliners.entrySet()) {
			if (isOnSide(side, playerStats.getKey())) {
				final OnlineCounter counter = playerStats.getValue();
				if (counter.wasOffline()) {
					offliner++;
				}
			}
		}
		return offliner;
	}

	/**
	 * @param side
	 * @param combatant
	 * @return
	 */
	private boolean isOnSide(final CombatantSide side, final Combatant combatant) {
		return side == null || side.equals(combatant.getSide());
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#roundsTillOnline(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double roundsTillOnline(final CombatantSide side) {
		int combatants = 0;
		int rounds = 0;
		for (final Entry<Combatant, OnlineCounter> playerStats : onliners.entrySet()) {
			if (isOnSide(side, playerStats.getKey())) {
				final OnlineCounter counter = playerStats.getValue();
				if (!counter.wasOffline()) {
					combatants++;
					rounds += counter.firstOnline();
				}
			}
		}
		// avoid division by zero
		if (combatants == 0) {
			return 0;
		}
		return ((double) rounds / (double) combatants);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#averageRoundsOnline(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double averageRoundsOnline(final CombatantSide side) {
		double combatants = 0;
		double rounds = 0;
		for (final Entry<Combatant, OnlineCounter> playerStats : onliners.entrySet()) {
			if (isOnSide(side, playerStats.getKey())) {
				final OnlineCounter counter = playerStats.getValue();
				if (!counter.wasOffline()) {
					combatants++;
					rounds += counter.onlineEvents();
				}
			}
		}
		// avoid division by zero
		if (combatants == 0.0) {
			return 0.0;
		}
		return rounds / combatants;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasMoved(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void hasMoved(final Combatant combatant, final Area newPos) {
		moves.incrementSide(combatant);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#newDestination(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void newDestination(final Combatant combatant, final Area destination) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#aimsAt(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void aimsAt(final Combatant combatant, final Combatant target) {
		incrementCombatantCounter(shots, combatant);
		incrementCombatantCounter(victims, target);
		shotRoundCounter.get(combatant.getSide()).incrementAndGet();
		final Area field = target._getLocation();
		getFieldStat(field).addShot();
	}

	private void incrementCombatantCounter(final Map<Combatant, Counter> combatantCounters, final Combatant combatant) {
		if (!combatantCounters.containsKey(combatant)) {
			combatantCounters.put(combatant, new Counter());
		}
		combatantCounters.get(combatant).increment();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isHit(de.outstare.fortbattleplayer.model.Combatant,
	 *      int, int)
	 */
	public void isHit(final Combatant combatant, final int damage, final int oldHealthAmount) {
		hitRoundCounter.get(combatant.getSide()).incrementAndGet();
		final Area field = combatant._getLocation();
		getFieldStat(field).addHit();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isDead(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isDead(final Combatant combatant) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isAlive(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isAlive(final Combatant combatant) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isOnline(de.outstare.fortbattleplayer.model.Combatant,
	 *      boolean)
	 */
	public void isOnline(final Combatant combatant, final boolean changed) {
		// count all events
		if (!onliners.containsKey(combatant)) {
			onliners.put(combatant, new OnlineCounter());
		}
		if (combatant.isOnline()) {
			onliners.get(combatant).addOnline();
		} else {
			onliners.get(combatant).addOffline();
		}

		// the online event is triggered every round for a player (hopefully)
		combatantTurn(combatant);
	}

	/**
	 * no special action, just to track the state every round
	 * 
	 * @param combatant
	 */
	private void combatantTurn(final Combatant combatant) {
		final SectorBonus bonus = combatant.getSectorBonus();
		attackBonuses.incrementBy(bonus.attackBonus, combatant);
		defenseBonuses.incrementBy(bonus.defendBonus, combatant);
		if (firstRound != null && firstRound.booleanValue()) {
			turnOrder.add(combatant);
		}
		if (LOG.isLoggable(Level.FINE)) {
			if ((firstRound == null || !firstRound.booleanValue()) && !turnOrder.contains(combatant)
			        && currentRoundNo > 1) {
				LOG.fine("---- not in order: " + combatant);
			}
		}
		actorsThisRound.incrementSide(combatant);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#targetNoOne(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public String targetNoOne(final CombatantSide side) {
		String result = "";
		int max = 0;
		for (final Entry<Combatant, Counter> victim : victims.entrySet()) {
			final Combatant combatant = victim.getKey();
			if (isOnSide(side, combatant)) {
				final int receivedShots = victim.getValue().getValue();
				if (receivedShots > max) {
					max = receivedShots;
					result = combatant.getName() + "(" + receivedShots + ")";
				}
			}
		}
		return result;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfBayonets(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfBayonets(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.BayonetCounter());
	}

	/**
	 * @param side
	 * @param counter
	 * @return
	 */
	int countWeaponMods(final CombatantSide side, final WeaponModCounter counter) {
		// because every player should have an online/offline event, we look at
		// the online data hoping to get all players
		final Set<Combatant> players = onliners.keySet();

		int bayonets = 0;
		for (final Combatant combatant : players) {
			if (isOnSide(side, combatant)) {
				final Weapon playerWeapon = combatant.getWeapon();
				// TODO let the counter count
				if (counter.counts(playerWeapon)) {
					bayonets++;
				}
			}
		}
		return bayonets;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfGraphitLubricants(CombatantSide)
	 */
	public int numberOfGraphitLubricants(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.GraphitLubricantCounter());
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfFettesOil(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfFettesOil(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.FettesOilCounter());
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfSchmierOil(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfSchmierOil(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.SchmierOilCounter());
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfShinyOil(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfShinyOil(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.ShinyOilCounter());
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfLoadingChamerOrEnhancedPatrons(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfLoadingChamerOrEnhancedPatrons(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.LoadingchamberOrEnhancedPatronsCounter());
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#numberOfHipFlasks(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfHipFlasks(final CombatantSide side) {
		return countWeaponMods(side, new WeaponModCounter.HipFlaskCounter());
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasSwappedPosition()
	 */
	public void hasSwappedPosition(final Combatant combatant, final Combatant swappedWith) {
		swaps.incrementSide(combatant);
		final PositionSwitch swap = new PositionSwitch(currentRoundNo, combatant, swappedWith);
		assert swapList.get(combatant.getSide()) != null;
		swapList.get(combatant.getSide()).add(swap);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#totalSwaps(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int totalSwaps(final CombatantSide side) {
		return swaps.getSideValue(side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#switchedPosList(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public Set<PositionSwitch> switchedPosList(final CombatantSide side) {
		final Set<PositionSwitch> allSwaps;
		if (side == null) {
			allSwaps = new HashSet<PositionSwitch>();
			for (final Set<PositionSwitch> sideSwitches : swapList.values()) {
				allSwaps.addAll(sideSwitches);
			}
		} else {
			allSwaps = swapList.get(side);
		}
		return allSwaps;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#criticalShot(de.outstare.fortbattleplayer.model.Combatant,
	 *      Combatant, int)
	 */
	public void criticalShot(final Combatant combatant, final Combatant victim, final int damage) {
		final CriticalHit crit = new CriticalHit(combatant, victim, damage, currentRoundNo);
		critHits.get(combatant.getSide()).add(crit);
		crits.incrementSide(combatant);
		final Area field = victim._getLocation();
		getFieldStat(field).addCrit();
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#critList(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public List<CriticalHit> critList(final CombatantSide side) {
		final List<CriticalHit> allCrits;
		if (side == null) {
			allCrits = new ArrayList<CriticalHit>();
			for (final List<CriticalHit> sideCrits : critHits.values()) {
				allCrits.addAll(sideCrits);
			}
		} else {
			allCrits = critHits.get(side);
		}
		return allCrits;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#criticalHits(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int criticalHits(final CombatantSide side) {
		return crits.getSideValue(side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#criticalHitDamage(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public long criticalHitDamage(final CombatantSide side) {
		long sum = 0;
		for (final CriticalHit crit : critList(side)) {
			sum += crit.critOnlyDamage;
		}
		return sum;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#turnOrder(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public List<Combatant> turnOrder(final CombatantSide side) {
		final List<Combatant> sidePlayers = new ArrayList<Combatant>(turnOrder.size() / 2);
		for (final Combatant player : turnOrder) {
			if (side == null || player.getSide() == side) {
				sidePlayers.add(player);
			}
		}
		return sidePlayers;
	}

	/**
	 * @return the number of shots per round
	 */
	public Map<CombatantSide, ? extends LabeledData> getShotsPerRound() {
		return shotsPerRound;
	}

	/**
	 * @return the number of hits per round
	 */
	public Map<CombatantSide, ? extends LabeledData> getHitsPerRound() {
		return hitsPerRound;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.DynamicStatistics#getNotShootersPerRound()
	 */
	public Map<CombatantSide, ? extends LabeledData> getNotShootersPerRound() {
		final Map<CombatantSide, RoundStatistics> nonShootersPerRound = new HashMap<CombatantSide, RoundStatistics>();
		for (final CombatantSide side : CombatantSide.values()) {
			final RoundStatistics nonShooters = new RoundStatistics("Not shooting", "players");
			nonShootersPerRound.put(side, nonShooters);

			final LabeledData shotRoundCounts = shotsPerRound.get(side);
			if (shotRoundCounts != null) {
				final double[] shotCounts = shotRoundCounts.toArray();
				if (actorsPerRound.size() == shotCounts.length) {
					for (int i = 0; i < shotCounts.length; i++) {
						final int actors = actorsPerRound.get(i).getSideValue(side);
						final double numberNotShooting = actors - shotCounts[i];
						nonShooters.addValue(numberNotShooting);
					}
				} else {
					LOG.warning("round statistics for players and shots differ!");
				}
			} else {
				LOG.warning("no data for players and shots for side " + side);
			}
		}
		return nonShootersPerRound;
	}

	private AreaStatistic getFieldStat(final Area area) {
		if (!fieldStats.containsKey(area)) {
			fieldStats.put(area, new AreaStatistic(area));
		}
		return fieldStats.get(area);
	}

	/**
	 * @return statistics for every field
	 */
	public Map<Area, AreaStatistic> getFieldStatistics() {
		return fieldStats;
	}
}
