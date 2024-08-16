package de.outstare.fortbattleplayer.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * 
 * @author daniel
 */
public class CombatantStatisticTest {
	private static final int ROUNDS = 33;
	private CombatantStatistic testObject = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testObject = new CombatantStatistic(ROUNDS);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfPlayers(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfPlayersOne() {
		final CombatantStatType stat = CombatantStatType.starthp;
		assertEquals(0, testObject.numberOfPlayers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfPlayers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, 100);

		assertEquals(1, testObject.numberOfPlayers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfPlayers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, 123);

		assertEquals(1, testObject.numberOfPlayers(CombatantSide.ATTACKER));
		assertEquals(1, testObject.numberOfPlayers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfPlayers(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfPlayersMultiple() {
		final CombatantStatType stat = CombatantStatType.starthp;
		for (int i = 0; i < 13; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
		}
		for (int i = 0; i < 150; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, i);
		}

		assertEquals(13, testObject.numberOfPlayers(CombatantSide.ATTACKER));
		assertEquals(150, testObject.numberOfPlayers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#survivedPlayers(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testSurvivedPlayers() {
		final CombatantStatType stat = CombatantStatType.finishedhp;
		assertEquals(0, testObject.survivedPlayers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.survivedPlayers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1, testObject.survivedPlayers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.survivedPlayers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1, testObject.survivedPlayers(CombatantSide.ATTACKER));
		assertEquals(1, testObject.survivedPlayers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#initialHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testInitialHealthPerPlayerOne() {
		final CombatantStatType stat = CombatantStatType.starthp;
		assertEquals(0.0, testObject.initialHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.initialHealthPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.initialHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.initialHealthPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.initialHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.initialHealthPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#initialHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testInitialHealthPerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.starthp;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.initialHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.initialHealthPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageWeaponDamage(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageWeaponDamageOne() {
		final CombatantStatType max = CombatantStatType.weaponmaxdmg;
		final CombatantStatType min = CombatantStatType.weaponmindmg;
		assertEquals(0.0, testObject.averageWeaponDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageWeaponDamage(CombatantSide.DEFENDER), 0.0);

		testObject.addData(max, CombatantSide.ATTACKER, 1234);
		testObject.addData(min, CombatantSide.ATTACKER, 2345);

		assertEquals((1234.0 + 2345.0) / 2.0, testObject.averageWeaponDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageWeaponDamage(CombatantSide.DEFENDER), 0.0);

		testObject.addData(max, CombatantSide.DEFENDER, 111);
		testObject.addData(min, CombatantSide.DEFENDER, 246);

		assertEquals((1234.0 + 2345.0) / 2.0, testObject.averageWeaponDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals((111.0 + 246.0) / 2.0, testObject.averageWeaponDamage(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageWeaponDamage(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageWeaponDamageMultiple() {
		final CombatantStatType max = CombatantStatType.weaponmaxdmg;
		final CombatantStatType min = CombatantStatType.weaponmindmg;
		assertEquals(0.0, testObject.averageWeaponDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageWeaponDamage(CombatantSide.DEFENDER), 0.0);

		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 1; i <= countAtt; i++) {
			testObject.addData(min, CombatantSide.ATTACKER, 2 * i);
			testObject.addData(max, CombatantSide.ATTACKER, 3 * i);
			// (2+3)i / 2 = 2.5i
			sumAtt += (2.5 * i);
		}
		for (int i = 1; i <= countDeff; i++) {
			testObject.addData(min, CombatantSide.DEFENDER, 2 * i);
			testObject.addData(max, CombatantSide.DEFENDER, 3 * i);
			// (2+3)i / 2 = 2.5i
			sumDeff += (2.5 * i);
		}

		assertEquals(sumAtt / countAtt, testObject.averageWeaponDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.averageWeaponDamage(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#causedDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testCausedDamagePerPlayerOne() {
		final CombatantStatType stat = CombatantStatType.totalcauseddamage;
		assertEquals(0.0, testObject.causedDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.causedDamagePerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.causedDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.causedDamagePerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(1234.0, testObject.causedDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(1.0, testObject.causedDamagePerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#causedDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testCausedDamagePerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.totalcauseddamage;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.causedDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.causedDamagePerPlayer(CombatantSide.DEFENDER), 0.0);

		// add some zeros
		for (int i = 0; i < 3; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 0);
		}
		for (int i = 0; i < 31; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 0);
		}
		assertEquals(sumAtt / (countAtt + 3), testObject.causedDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / (countDeff + 31), testObject.causedDamagePerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#shotsFiredWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testShotsFiredWholeSide() {
		final CombatantStatType max = CombatantStatType.hitcount;
		final CombatantStatType min = CombatantStatType.misscount;
		assertEquals(0, testObject.shotsFiredWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.shotsFiredWholeSide(CombatantSide.DEFENDER));

		testObject.addData(max, CombatantSide.ATTACKER, 12);
		testObject.addData(min, CombatantSide.ATTACKER, 23);

		assertEquals(12 + 23, testObject.shotsFiredWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.shotsFiredWholeSide(CombatantSide.DEFENDER));

		testObject.addData(max, CombatantSide.DEFENDER, 111);
		testObject.addData(min, CombatantSide.DEFENDER, 246);

		assertEquals(12 + 23, testObject.shotsFiredWholeSide(CombatantSide.ATTACKER));
		assertEquals(111 + 246, testObject.shotsFiredWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#hitsWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testHitsWholeSide() {
		final CombatantStatType stat = CombatantStatType.hitcount;
		assertEquals(0, testObject.hitsWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.hitsWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234, testObject.hitsWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.hitsWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(1234, testObject.hitsWholeSide(CombatantSide.ATTACKER));
		assertEquals(1, testObject.hitsWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageLifetime(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageLifetimeOne() {
		final CombatantStatType stat = CombatantStatType.diedwhen;
		assertEquals(0.0, testObject.averageLifetime(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageLifetime(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 23);

		assertEquals(23.0, testObject.averageLifetime(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageLifetime(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(23.0, testObject.averageLifetime(CombatantSide.ATTACKER), 0.0);
		assertEquals(1.0, testObject.averageLifetime(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageLifetime(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageLifetimeMultiple() {
		final CombatantStatType stat = CombatantStatType.diedwhen;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 11 + i);
			sumAtt += 11 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.averageLifetime(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.averageLifetime(CombatantSide.DEFENDER), 0.0);

		// add some zeros (lived till the end)
		for (int i = 0; i < 3; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 0);
		}
		for (int i = 0; i < 31; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 0);
		}
		assertEquals((sumAtt + 3 * ROUNDS) / (countAtt + 3), testObject.averageLifetime(CombatantSide.ATTACKER), 0.0);
		assertEquals((sumDeff + 31 * ROUNDS) / (countDeff + 31), testObject.averageLifetime(CombatantSide.DEFENDER),
		        0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfAdventurers(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfAdventurers() {
		final CombatantStatType stat = CombatantStatType.charclass;
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.ADVENTURER.ordinal() - 1);

		assertEquals(1, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.ADVENTURER.ordinal() - 1);

		assertEquals(1, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(1, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfDuelants(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfDuelants() {
		final CombatantStatType stat = CombatantStatType.charclass;
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.ADVENTURER.ordinal() - 1);

		assertEquals(1, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.ADVENTURER.ordinal() - 1);

		assertEquals(1, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(1, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfGreenhorns(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfGreenhorns() {
		final CombatantStatType stat = CombatantStatType.charclass;
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.GREENHORN.ordinal() - 1);

		assertEquals(1, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.GREENHORN.ordinal() - 1);

		assertEquals(1, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(1, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfSoldiers(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfSoldiers() {
		final CombatantStatType stat = CombatantStatType.charclass;
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.SOLDIER.ordinal() - 1);

		assertEquals(1, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.SOLDIER.ordinal() - 1);

		assertEquals(1, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(1, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfWorkers(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfWorkers() {
		final CombatantStatType stat = CombatantStatType.charclass;
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.WORKER.ordinal() - 1);

		assertEquals(1, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.WORKER.ordinal() - 1);

		assertEquals(1, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(1, testObject.numberOfWorkers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfAdventurers(CombatantSide)}
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfDuelants(CombatantSide)}
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfGreenhorns(CombatantSide)}
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfSoldiers(CombatantSide)}
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#numberOfWorkers(CombatantSide)}
	 * .
	 */
	@Test
	public final void testNumberOfClasses() {
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfDuelants(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfDuelants(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		final CombatantStatType stat = CombatantStatType.charclass;
		for (int i = 0; i < 7; i++) {
			if (i % 2 == 0) {
				testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.ADVENTURER.ordinal() - 1);
			}
			testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.ADVENTURER.ordinal() - 1);
		}

		assertEquals(4, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(7, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfDuelants(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfDuelants(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		for (int i = 0; i < 13; i++) {
			if (i % 3 == 0) {
				testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.DUELANT.ordinal() - 1);
			}
			testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.DUELANT.ordinal() - 1);
		}

		assertEquals(4, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(7, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
		assertEquals(5, testObject.numberOfDuelants(CombatantSide.ATTACKER));
		assertEquals(13, testObject.numberOfDuelants(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		for (int i = 0; i < 11; i++) {
			if (i % 4 == 0) {
				testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.GREENHORN.ordinal() - 1);
			}
			testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.GREENHORN.ordinal() - 1);
		}

		assertEquals(4, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(7, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
		assertEquals(5, testObject.numberOfDuelants(CombatantSide.ATTACKER));
		assertEquals(13, testObject.numberOfDuelants(CombatantSide.DEFENDER));
		assertEquals(3, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(11, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		for (int i = 0; i < 27; i++) {
			if (i % 5 == 0) {
				testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.SOLDIER.ordinal() - 1);
			}
			testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.SOLDIER.ordinal() - 1);
		}

		assertEquals(4, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(7, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
		assertEquals(5, testObject.numberOfDuelants(CombatantSide.ATTACKER));
		assertEquals(13, testObject.numberOfDuelants(CombatantSide.DEFENDER));
		assertEquals(3, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(11, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
		assertEquals(6, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(27, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(0, testObject.numberOfWorkers(CombatantSide.DEFENDER));

		for (int i = 0; i < 43; i++) {
			if (i % 6 == 0) {
				testObject.addData(stat, CombatantSide.ATTACKER, CharacterClass.WORKER.ordinal() - 1);
			}
			testObject.addData(stat, CombatantSide.DEFENDER, CharacterClass.WORKER.ordinal() - 1);
		}

		assertEquals(4, testObject.numberOfAdventurers(CombatantSide.ATTACKER));
		assertEquals(7, testObject.numberOfAdventurers(CombatantSide.DEFENDER));
		assertEquals(5, testObject.numberOfDuelants(CombatantSide.ATTACKER));
		assertEquals(13, testObject.numberOfDuelants(CombatantSide.DEFENDER));
		assertEquals(3, testObject.numberOfGreenhorns(CombatantSide.ATTACKER));
		assertEquals(11, testObject.numberOfGreenhorns(CombatantSide.DEFENDER));
		assertEquals(6, testObject.numberOfSoldiers(CombatantSide.ATTACKER));
		assertEquals(27, testObject.numberOfSoldiers(CombatantSide.DEFENDER));
		assertEquals(8, testObject.numberOfWorkers(CombatantSide.ATTACKER));
		assertEquals(43, testObject.numberOfWorkers(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageLevel(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageLevelOne() {
		final CombatantStatType stat = CombatantStatType.charlevel;
		assertEquals(0.0, testObject.averageLevel(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageLevel(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 99);

		assertEquals(99.0, testObject.averageLevel(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageLevel(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(99.0, testObject.averageLevel(CombatantSide.ATTACKER), 0.0);
		assertEquals(1.0, testObject.averageLevel(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageLevel(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageLevelMultiple() {
		final CombatantStatType stat = CombatantStatType.charlevel;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 11 + i);
			sumAtt += 11 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.averageLevel(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.averageLevel(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#initialHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testInitialHealthWholeSideOne() {
		final CombatantStatType stat = CombatantStatType.starthp;
		assertEquals(0.0, testObject.initialHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.initialHealthWholeSide(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.initialHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.initialHealthWholeSide(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.initialHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.initialHealthWholeSide(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#initialHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testInitialHealthWholeSideMultiple() {
		final CombatantStatType stat = CombatantStatType.starthp;
		int sumAtt = 0;
		int sumDeff = 0;
		for (int i = 0; i < 13; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < 131; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt, testObject.initialHealthWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.initialHealthWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#endHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testEndHealthPerPlayer() {
		final CombatantStatType stat = CombatantStatType.finishedhp;
		assertEquals(0.0, testObject.endHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.endHealthPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.endHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.endHealthPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.endHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.endHealthPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#endHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testEndHealthPerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.finishedhp;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.endHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.endHealthPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#endHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testEndHealthWholeSide() {
		final CombatantStatType stat = CombatantStatType.finishedhp;
		assertEquals(0.0, testObject.endHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.endHealthWholeSide(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.endHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.endHealthWholeSide(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.endHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.endHealthWholeSide(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#endHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testEndHealthWholeSideMultiple() {
		final CombatantStatType stat = CombatantStatType.finishedhp;
		int sumAtt = 0;
		int sumDeff = 0;
		for (int i = 0; i < 13; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < 131; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt, testObject.endHealthWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.endHealthWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#maxPossibleHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testMaxPossibleHealthPerPlayer() {
		final CombatantStatType stat = CombatantStatType.maxhp;
		assertEquals(0.0, testObject.maxPossibleHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.maxPossibleHealthPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.maxPossibleHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.maxPossibleHealthPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.maxPossibleHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.maxPossibleHealthPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#initialHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testMaxPossibleHealthPerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.maxhp;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.maxPossibleHealthPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.maxPossibleHealthPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#maxPossibleHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testMaxPossibleHealthWholeSide() {
		final CombatantStatType stat = CombatantStatType.maxhp;
		assertEquals(0.0, testObject.maxPossibleHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.maxPossibleHealthWholeSide(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.maxPossibleHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.maxPossibleHealthWholeSide(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.maxPossibleHealthWholeSide(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.maxPossibleHealthWholeSide(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#endHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testMaxPossibleHealthWholeSideMultiple() {
		final CombatantStatType stat = CombatantStatType.maxhp;
		int sumAtt = 0;
		int sumDeff = 0;
		for (int i = 0; i < 13; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < 131; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt, testObject.maxPossibleHealthWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.maxPossibleHealthWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#causedDamageWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testCausedDamageWholeSide() {
		final CombatantStatType stat = CombatantStatType.totalcauseddamage;
		assertEquals(0, testObject.causedDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.causedDamageWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234, testObject.causedDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.causedDamageWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(1234, testObject.causedDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(1, testObject.causedDamageWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#causedDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testCausedDamageWholeSideMultiple() {
		final CombatantStatType stat = CombatantStatType.totalcauseddamage;
		int sumAtt = 0;
		int sumDeff = 0;
		for (int i = 0; i < 13; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < 131; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt, testObject.causedDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.causedDamageWholeSide(CombatantSide.DEFENDER));

		// add some zeros
		for (int i = 0; i < 3; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 0);
		}
		for (int i = 0; i < 31; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 0);
		}
		assertEquals(sumAtt, testObject.causedDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.causedDamageWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#shotsFiredPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testShotsFiredPerPlayer() {
		final CombatantStatType max = CombatantStatType.hitcount;
		final CombatantStatType min = CombatantStatType.misscount;
		assertEquals(0.0, testObject.shotsFiredPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.shotsFiredPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(max, CombatantSide.ATTACKER, 23);
		testObject.addData(min, CombatantSide.ATTACKER, 28);

		assertEquals(23.0 + 28.0, testObject.shotsFiredPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.shotsFiredPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(max, CombatantSide.DEFENDER, 111);
		testObject.addData(min, CombatantSide.DEFENDER, 246);

		assertEquals(23.0 + 28.0, testObject.shotsFiredPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(111.0 + 246.0, testObject.shotsFiredPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageWeaponDamage(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testShotsFiredPerPlayerMultiple() {
		final CombatantStatType max = CombatantStatType.hitcount;
		final CombatantStatType min = CombatantStatType.misscount;
		assertEquals(0.0, testObject.shotsFiredPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.shotsFiredPerPlayer(CombatantSide.DEFENDER), 0.0);

		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 1; i <= countAtt; i++) {
			testObject.addData(min, CombatantSide.ATTACKER, 2 * i);
			testObject.addData(max, CombatantSide.ATTACKER, i);
			sumAtt += 3 * i;
		}
		for (int i = 1; i <= countDeff; i++) {
			testObject.addData(min, CombatantSide.DEFENDER, i);
			testObject.addData(max, CombatantSide.DEFENDER, 3 * i);
			sumDeff += 4 * i;
		}

		assertEquals(sumAtt / countAtt, testObject.shotsFiredPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.shotsFiredPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#hitsPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testHitsPerPlayer() {
		final CombatantStatType stat = CombatantStatType.hitcount;
		assertEquals(0.0, testObject.hitsPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.hitsPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.hitsPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.hitsPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.hitsPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.hitsPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#hitsPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testHitsPerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.hitcount;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.hitsPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.hitsPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#takenDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testTakenDamagePerPlayer() {
		final CombatantStatType stat = CombatantStatType.takendamage;
		assertEquals(0.0, testObject.takenDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.takenDamagePerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.takenDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.takenDamagePerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.takenDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.takenDamagePerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#takenDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testTakenDamagePerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.takendamage;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.takenDamagePerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.takenDamagePerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#takenDamageWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testTakenDamageWholeSide() {
		final CombatantStatType stat = CombatantStatType.takendamage;
		assertEquals(0, testObject.takenDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.takenDamageWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234, testObject.takenDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.takenDamageWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(1234, testObject.takenDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(1, testObject.takenDamageWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#takenDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testTakenDamageWholeSideMultiple() {
		final CombatantStatType stat = CombatantStatType.takendamage;
		int sumAtt = 0;
		int sumDeff = 0;
		for (int i = 0; i < 13; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < 131; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt, testObject.takenDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.takenDamageWholeSide(CombatantSide.DEFENDER));

		// add some zeros
		for (int i = 0; i < 3; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 0);
		}
		for (int i = 0; i < 31; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 0);
		}
		assertEquals(sumAtt, testObject.takenDamageWholeSide(CombatantSide.ATTACKER));
		assertEquals(sumDeff, testObject.takenDamageWholeSide(CombatantSide.DEFENDER));
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageHitDamage(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testAverageHitDamage() {
		final CombatantStatType max = CombatantStatType.totalcauseddamage;
		final CombatantStatType min = CombatantStatType.hitcount;
		assertEquals(0.0, testObject.averageHitDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageHitDamage(CombatantSide.DEFENDER), 0.0);

		testObject.addData(max, CombatantSide.ATTACKER, 1234);
		testObject.addData(min, CombatantSide.ATTACKER, 2345);

		assertEquals(1234.0 / 2345.0, testObject.averageHitDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageHitDamage(CombatantSide.DEFENDER), 0.0);

		testObject.addData(max, CombatantSide.DEFENDER, 11111);
		testObject.addData(min, CombatantSide.DEFENDER, 246);

		assertEquals(1234.0 / 2345.0, testObject.averageHitDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(11111.0 / 246.0, testObject.averageHitDamage(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#averageHitDamage(CombatantSide)}
	 * .
	 */
	@Test
	public final void testaverageHitDamageMultiple() {
		final CombatantStatType dmg = CombatantStatType.totalcauseddamage;
		final CombatantStatType hits = CombatantStatType.hitcount;
		assertEquals(0.0, testObject.averageHitDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.averageHitDamage(CombatantSide.DEFENDER), 0.0);

		double hitsAtt = 0;
		double hitsDeff = 0;
		double dmgAtt = 0;
		double dmgDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 1; i <= countAtt; i++) {
			testObject.addData(hits, CombatantSide.ATTACKER, 2 * i);
			testObject.addData(dmg, CombatantSide.ATTACKER, 3 * i);
			hitsAtt += 2 * i;
			dmgAtt += 3 * i;
		}
		for (int i = 1; i <= countDeff; i++) {
			testObject.addData(hits, CombatantSide.DEFENDER, i);
			testObject.addData(dmg, CombatantSide.DEFENDER, 131);
			hitsDeff += i;
			dmgDeff += 131.0;
		}

		assertEquals(dmgAtt / hitsAtt, testObject.averageHitDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(dmgDeff / hitsDeff, testObject.averageHitDamage(CombatantSide.DEFENDER), 0.0);

		// add some zeros
		for (int i = 0; i < 13; i++) {
			testObject.addData(dmg, CombatantSide.ATTACKER, 0);
		}
		for (int i = 0; i < 31; i++) {
			testObject.addData(hits, CombatantSide.DEFENDER, 0);
		}
		// expect same results (not changed by zeros)
		assertEquals(dmgAtt / hitsAtt, testObject.averageHitDamage(CombatantSide.ATTACKER), 0.0);
		assertEquals(dmgDeff / hitsDeff, testObject.averageHitDamage(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#dodgesPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testDodgesPerPlayer() {
		final CombatantStatType stat = CombatantStatType.dodgecount;
		assertEquals(0.0, testObject.dodgesPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.dodgesPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234.0, testObject.dodgesPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(0.0, testObject.dodgesPerPlayer(CombatantSide.DEFENDER), 0.0);

		testObject.addData(stat, CombatantSide.DEFENDER, 2345);

		assertEquals(1234.0, testObject.dodgesPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(2345.0, testObject.dodgesPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#dodgesPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testDodgesPerPlayerMultiple() {
		final CombatantStatType stat = CombatantStatType.dodgecount;
		double sumAtt = 0;
		double sumDeff = 0;
		final int countAtt = 13;
		final int countDeff = 131;
		for (int i = 0; i < countAtt; i++) {
			testObject.addData(stat, CombatantSide.ATTACKER, 111 + i);
			sumAtt += 111 + i;
		}
		for (int i = 0; i < countDeff; i++) {
			testObject.addData(stat, CombatantSide.DEFENDER, 13 + i);
			sumDeff += 13 + i;
		}

		assertEquals(sumAtt / countAtt, testObject.dodgesPerPlayer(CombatantSide.ATTACKER), 0.0);
		assertEquals(sumDeff / countDeff, testObject.dodgesPerPlayer(CombatantSide.DEFENDER), 0.0);
	}

	/**
	 * Test method for
	 * {@link de.outstare.fortbattleplayer.statistics.CombatantStatistic#dodgesWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)}
	 * .
	 */
	@Test
	public final void testDodgesWholeSide() {
		final CombatantStatType stat = CombatantStatType.dodgecount;
		assertEquals(0, testObject.dodgesWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.dodgesWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.ATTACKER, 1234);

		assertEquals(1234, testObject.dodgesWholeSide(CombatantSide.ATTACKER));
		assertEquals(0, testObject.dodgesWholeSide(CombatantSide.DEFENDER));

		testObject.addData(stat, CombatantSide.DEFENDER, 1);

		assertEquals(1234, testObject.dodgesWholeSide(CombatantSide.ATTACKER));
		assertEquals(1, testObject.dodgesWholeSide(CombatantSide.DEFENDER));
	}

}
