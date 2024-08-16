package de.outstare.fortbattleplayer.statistics;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.CombatantState;
import de.outstare.fortbattleplayer.model.Sector;
import de.outstare.fortbattleplayer.model.impl.SimpleArea;
import de.outstare.fortbattleplayer.model.impl.SimpleCombatant;
import de.outstare.fortbattleplayer.model.impl.SimpleSector;
import de.outstare.fortbattleplayer.model.impl.SimpleWeapon;

/**
 * 
 * @author daniel
 */
public class BattleStatisticsTest {
	private static final int ATTACKERS = 17;
	private static final int DEFENDERS = 31;
	private static final CombatantSide A = CombatantSide.ATTACKER;
	private static final CombatantSide D = CombatantSide.DEFENDER;
	private final Sector nullSector = new SimpleSector(0, false, false, 0, 0, false, 0, null);
	private final Sector bonusSector = new SimpleSector(5, true, true, 13, 23, true, 71, CharacterClass.GREENHORN);
	private final Area area1 = new SimpleArea(0, 0, nullSector);
	private final Area area51 = new SimpleArea(5, 1, bonusSector);
	private final CombatantState attacker1State = new CombatantState(area1, 999, area1, true);
	private final CombatantState attacker2State = new CombatantState(area1, 789, area1, false);
	private final Combatant attacker1 = new SimpleCombatant(A, attacker1State, 1111, "test attacker 1",
	        CharacterClass.SOLDIER, new SimpleWeapon(1, "test weapon", 103, 227), "test city");
	private final Combatant attacker2 = new SimpleCombatant(A, attacker2State, 1111, "test attacker 2",
	        CharacterClass.SOLDIER, new SimpleWeapon(1, "test weapon", 103, 227), "test city2");
	private final CombatantState defender1State = new CombatantState(area51, 4567, null, false);
	private final Combatant defender1 = new SimpleCombatant(D, defender1State, 4567, "test defender 1",
	        CharacterClass.GREENHORN, new SimpleWeapon(2, "test weapon2", 0, 13), "test city2");
	private BattleStatistics testObject = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testObject = new BattleStatistics(ATTACKERS, DEFENDERS);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#percentAbleToShoot(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testPercentAbleToShoot() {
		assertEquals(0, testObject.percentAbleToShoot(A));
		assertEquals(0, testObject.percentAbleToShoot(D));

		testObject.aimsAt(attacker1, defender1);
		// every online event is counted as round
		testObject.isOnline(attacker1, false);

		assertEquals(100, testObject.percentAbleToShoot(A));
		assertEquals(0, testObject.percentAbleToShoot(D));

		testObject.aimsAt(defender1, attacker1);
		// every online event is counted as round
		testObject.isOnline(defender1, false);
		testObject.isOnline(defender1, false);
		testObject.isOnline(defender1, false);

		assertEquals(100, testObject.percentAbleToShoot(A));
		assertEquals(33, testObject.percentAbleToShoot(D));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#percentAbleToShoot(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testPercentAbleToShootMultiple() {
		assertEquals(0, testObject.percentAbleToShoot(A));
		assertEquals(0, testObject.percentAbleToShoot(D));

		// shots by single player
		final int attRounds = 71;
		final int shots = randomShots(attRounds, attacker1, defender1);

		final int percent = (int) Math.round(shots * 100.0 / attRounds);
		assertEquals(percent, testObject.percentAbleToShoot(A));
		assertEquals(0, testObject.percentAbleToShoot(D));

		// multiple players
		final int players = 97;
		double sumOfAverages = 0;
		for (int i = 1; i <= players; i++) {
			final CombatantState state = new CombatantState(area51, i, i % 2 == 0 ? area1 : null, i % 3 == 0 ? false
			        : true);
			final Combatant player = new SimpleCombatant(D, state, 117 * i, "temp" + i, CharacterClass.values()[i
			        % CharacterClass.values().length], new SimpleWeapon(i, "tempWeapon" + i, i, 3 * i + 17),
			        "temp town " + i);
			final int playerRounds = i;
			final int playerShots = randomShots(playerRounds, player, attacker1);
			sumOfAverages += playerShots * 100.0 / playerRounds;
		}

		assertEquals(percent, testObject.percentAbleToShoot(A));
		assertEquals((int) (sumOfAverages / players), testObject.percentAbleToShoot(D));
	}

	/**
	 * @param attRounds
	 * @param shooter
	 * @param victim
	 * @param r
	 * @return
	 */
	private int randomShots(final int attRounds, final Combatant shooter, final Combatant victim) {
		int shots = 0;
		for (int i = 0; i < attRounds; i++) {
			// every online event is counted as round
			testObject.isOnline(shooter, false);
			if (i % 3 == 0) {
				testObject.aimsAt(shooter, victim);
				shots++;
			}
		}
		return shots;
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#amountOfAttackBonus(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAmountOfAttackBonus() {
		assertEquals(0, testObject.amountOfAttackBonus(A));
		assertEquals(0, testObject.amountOfAttackBonus(D));

		// every online event is counted as round
		testObject.isOnline(attacker1, false);

		assertEquals(nullSector.getBonus(attacker1.getCharacterClass()).attackBonus, testObject.amountOfAttackBonus(A));
		assertEquals(0, testObject.amountOfAttackBonus(D));

		// every online event is counted as round
		testObject.isOnline(defender1, false);

		assertEquals(nullSector.getBonus(attacker1.getCharacterClass()).attackBonus, testObject.amountOfAttackBonus(A));
		assertEquals(bonusSector.getBonus(defender1.getCharacterClass()).attackBonus, testObject.amountOfAttackBonus(D));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#amountOfDefenseBonus(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAmountOfDefenseBonus() {
		assertEquals(0, testObject.amountOfDefenseBonus(A));
		assertEquals(0, testObject.amountOfDefenseBonus(D));

		// every online event is counted as round
		testObject.isOnline(attacker1, false);

		assertEquals(nullSector.getBonus(attacker1.getCharacterClass()).defendBonus, testObject.amountOfDefenseBonus(A));
		assertEquals(0, testObject.amountOfDefenseBonus(D));

		// every online event is counted as round
		testObject.isOnline(defender1, false);

		assertEquals(nullSector.getBonus(attacker1.getCharacterClass()).defendBonus, testObject.amountOfDefenseBonus(A));
		assertEquals(bonusSector.getBonus(defender1.getCharacterClass()).defendBonus,
		        testObject.amountOfDefenseBonus(D));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#numberOfMovesPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfMovesPerPlayer() {
		assertEquals(0.0, testObject.numberOfMovesPerPlayer(A), 0.0);
		assertEquals(0.0, testObject.numberOfMovesPerPlayer(D), 0.0);

		testObject.hasMoved(attacker1, area1);

		assertEquals(1.0 / ATTACKERS, testObject.numberOfMovesPerPlayer(A), 0.0);
		assertEquals(0.0, testObject.numberOfMovesPerPlayer(D), 0.0);

		testObject.hasMoved(defender1, area1);

		assertEquals(1.0 / ATTACKERS, testObject.numberOfMovesPerPlayer(A), 0.0);
		assertEquals(1.0 / DEFENDERS, testObject.numberOfMovesPerPlayer(D), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#numberOfMovesPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfMovesPerPlayerMultiple() {
		assertEquals(0.0, testObject.numberOfMovesPerPlayer(A), 0.0);
		assertEquals(0.0, testObject.numberOfMovesPerPlayer(D), 0.0);

		// multiple moves by one player
		for (int i = 0; i < 137; i++) {
			testObject.hasMoved(attacker1, (i % 2 == 0) ? area1 : area51);
		}

		assertEquals(137.0 / ATTACKERS, testObject.numberOfMovesPerPlayer(A), 0.0);
		assertEquals(0.0, testObject.numberOfMovesPerPlayer(D), 0.0);

		// moves by different players
		for (int i = 1; i <= 97; i++) {
			final CombatantState state = new CombatantState(area51, i, i % 2 == 0 ? area1 : null, i % 3 == 0 ? false
			        : true);
			final Combatant player = new SimpleCombatant(D, state, 117 * i, "temp" + i, CharacterClass.values()[i
			        % CharacterClass.values().length], new SimpleWeapon(i, "tempWeapon" + i, i, 3 * i + 17),
			        "temp town " + i);
			testObject.hasMoved(player, area1);
		}

		assertEquals(137.0 / ATTACKERS, testObject.numberOfMovesPerPlayer(A), 0.0);
		assertEquals(97.0 / DEFENDERS, testObject.numberOfMovesPerPlayer(D), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#numberOfOffliners(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfOffliners() {
		assertEquals(0, testObject.numberOfOffliners(A));
		assertEquals(0, testObject.numberOfOffliners(D));

		// attacker1 is online by default
		testObject.isOnline(attacker1, false);

		assertEquals(0, testObject.numberOfOffliners(A));
		assertEquals(0, testObject.numberOfOffliners(D));

		// defender1 is offline by default
		testObject.isOnline(defender1, false);

		assertEquals(0, testObject.numberOfOffliners(A));
		assertEquals(1, testObject.numberOfOffliners(D));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#roundsTillOnline(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testRoundsTillOnline() {
		assertEquals(0.0, testObject.roundsTillOnline(A), 0.0);
		assertEquals(0.0, testObject.roundsTillOnline(D), 0.0);

		// attacker1 is online by default
		testObject.isOnline(attacker1, false);

		assertEquals(0.0, testObject.roundsTillOnline(A), 0.0);
		assertEquals(0.0, testObject.roundsTillOnline(D), 0.0);

		// defender1 is offline by default
		testObject.isOnline(defender1, false);
		testObject.isOnline(defender1, false);
		testObject.isOnline(defender1, false);
		defender1._setState(defender1State.setOnline(true));
		testObject.isOnline(defender1, false);

		assertEquals(0.0, testObject.roundsTillOnline(A), 0.0);
		assertEquals(3.0, testObject.roundsTillOnline(D), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#averageRoundsOnline(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageRoundsOnline() {
		assertEquals(0.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(0.0, testObject.averageRoundsOnline(D), 0.0);

		// attacker1 is online by default
		testObject.isOnline(attacker1, false);
		testObject.isOnline(attacker1, false);

		assertEquals(2.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(0.0, testObject.averageRoundsOnline(D), 0.0);

		// defender1 is offline by default
		testObject.isOnline(defender1, false);
		testObject.isOnline(defender1, false);
		testObject.isOnline(defender1, false);
		defender1._setState(defender1State.setOnline(true));
		testObject.isOnline(defender1, false);

		assertEquals(2.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(1.0, testObject.averageRoundsOnline(D), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#averageRoundsOnline(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageRoundsOnlineMultiple() {
		assertEquals(0.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(0.0, testObject.averageRoundsOnline(D), 0.0);

		// multiple rounds by one player
		for (int i = 0; i < 31; i++) {
			testObject.isOnline(attacker1, false);
			testObject.isOnline(attacker2, false);
		}

		// count only onliners
		assertEquals(31.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(0.0, testObject.averageRoundsOnline(D), 0.0);

		attacker2._setState(attacker2State.setOnline(true));
		testObject.isOnline(attacker2, true);
		testObject.isOnline(attacker2, true);

		assertEquals((31 + 2) / 2.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(0.0, testObject.averageRoundsOnline(D), 0.0);

		// one round for multiple players
		final Random r = new Random();
		int sumRounds = 0;
		for (int i = 1; i <= 97; i++) {
			final CombatantState state = new CombatantState(area51, i, null, true);
			final Combatant player = new SimpleCombatant(D, state, 117 * i, "temp" + i, CharacterClass.values()[i
			        % CharacterClass.values().length], new SimpleWeapon(i, "tempWeapon" + i, i, 3 * i + 17),
			        "temp town " + i);
			int rounds = r.nextInt(10) + 1;
			sumRounds += rounds;
			// only online rounds
			for (; rounds > 0; rounds--) {
				testObject.isOnline(player, false);
			}
		}

		assertEquals((31 + 2) / 2.0, testObject.averageRoundsOnline(A), 0.0);
		assertEquals(sumRounds / 97.0, testObject.averageRoundsOnline(D), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#targetNoOne(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testTargetNoOne() {
		assertEquals("", testObject.targetNoOne(A));
		assertEquals("", testObject.targetNoOne(D));

		testObject.aimsAt(attacker1, defender1);

		assertEquals("", testObject.targetNoOne(A));
		assertEquals(defender1.getName() + "(1)", testObject.targetNoOne(D));

		testObject.aimsAt(defender1, attacker1);
		testObject.aimsAt(defender1, attacker1);

		assertEquals(attacker1.getName() + "(2)", testObject.targetNoOne(A));
		assertEquals(defender1.getName() + "(1)", testObject.targetNoOne(D));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#totalSwaps(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testTotalSwaps() {
		assertEquals(0, testObject.totalSwaps(A));
		assertEquals(0, testObject.totalSwaps(D));

		testObject.hasSwappedPosition(attacker1, attacker2);

		assertEquals(1, testObject.totalSwaps(A));
		assertEquals(0, testObject.totalSwaps(D));

		// TODO is this what we want? only the first parameter determines the
		// side count?
		testObject.hasSwappedPosition(defender1, attacker1);
		testObject.hasSwappedPosition(defender1, attacker1);

		assertEquals(1, testObject.totalSwaps(A));
		assertEquals(2, testObject.totalSwaps(D));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.BattleStatistics#criticalHits(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testCriticalHits() {
		assertEquals(0, testObject.criticalHits(A));
		assertEquals(0, testObject.criticalHits(D));

		testObject.criticalShot(attacker1, defender1, 123);

		assertEquals(1, testObject.criticalHits(A));
		assertEquals(0, testObject.criticalHits(D));

		testObject.criticalShot(defender1, attacker2, 234);
		testObject.criticalShot(defender1, attacker2, 345);

		assertEquals(1, testObject.criticalHits(A));
		assertEquals(2, testObject.criticalHits(D));
	}

}
