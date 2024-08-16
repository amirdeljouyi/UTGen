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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * A statistic for combatants
 * 
 * TODO use separate classes for calculating and storing the statistics, so
 * statistics i.e. can be serialized
 * 
 * @author daniel
 */
public class CombatantStatistic implements StaticStatistics {
	private final int numberOfRounds;
	private final Map<CombatantStatType, DataSet> allData = new HashMap<CombatantStatType, DataSet>();

	/**
	 * create statistics for the given number of rounds.
	 * 
	 * @param numberOfRounds
	 */
	public CombatantStatistic(final int numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}

	/**
	 * @param type
	 *            what kind of data is given as value?
	 * @param side
	 *            at which the combatant of the value fights
	 * @param value
	 */
	public void addData(final CombatantStatType type, final CombatantSide side, final int value) {
		ensureTypExists(type);
		final Integer storedValue;
		// fix class, because in the data it starts at -1 but we start with 0
		if (type == CombatantStatType.charclass) {
			storedValue = Integer.valueOf(value + 1);
		} else {
			storedValue = Integer.valueOf(value);
		}
		allData.get(type).addData(side, storedValue);
	}

	/**
	 * @param type
	 */
	private void ensureTypExists(final CombatantStatType type) {
		if (!allData.containsKey(type)) {
			allData.put(type, new DataSet());
		}
	}

	private double aggregate(final CombatantStatType stat, final CombatantSide side, final DataAggregationType function) {
		// some data may not be present in old logs (ie. since 1.29 charlevel
		// and charclass were added)
		if (!allData.containsKey(stat)) {
			return 0;
		}
		final DataSet statData = allData.get(stat);
		assert statData != null : "data must be present!";
		return statData.aggregate(side, function);
	}

	private double getAverage(final CombatantStatType stat, final CombatantSide side) {
		return aggregate(stat, side, DataAggregationType.AVERAGE);
	}

	private double getSum(final CombatantStatType stat, final CombatantSide side) {
		return aggregate(stat, side, DataAggregationType.SUM);
	}

	private double getCount(final CombatantStatType stat, final CombatantSide side) {
		return aggregate(stat, side, DataAggregationType.AMOUNT);
	}

	private double getPositiveAverage(final CombatantStatType stat, final CombatantSide side) {
		return aggregate(stat, side, DataAggregationType.POSITIVE_AVERAGE);
	}

	private double getPositiveSum(final CombatantStatType stat, final CombatantSide side) {
		return aggregate(stat, side, DataAggregationType.POSITIVE_SUM);
	}

	private double getPositiveCount(final CombatantStatType stat, final CombatantSide side) {
		return aggregate(stat, side, DataAggregationType.POSITIVE_AMOUNT);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#numberOfPlayers(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfPlayers(final CombatantSide side) {
		// just a stat that is available for all combatants
		return (int) Math.round(getCount(CombatantStatType.starthp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#survivedPlayers(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int survivedPlayers(final CombatantSide side) {
		return (int) Math.round(getPositiveCount(CombatantStatType.finishedhp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#initialHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double initialHealthPerPlayer(final CombatantSide side) {
		return (int) Math.round(getAverage(CombatantStatType.starthp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#averageWeaponDamage(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double averageWeaponDamage(final CombatantSide side) {
		final DataSet allMinDamage = allData.get(CombatantStatType.weaponmindmg);
		final DataSet allMaxDamage = allData.get(CombatantStatType.weaponmaxdmg);
		if (allMinDamage == null || allMaxDamage == null) {
			// TODO logging: LOG.warn("no weapon damage data available!");
			// no weapon data available
			return 0;
		}
		// use double precision for calculating average
		final double avgMinDamage = allMinDamage.aggregate(side, DataAggregationType.AVERAGE);
		final double avgMaxDamage = allMaxDamage.aggregate(side, DataAggregationType.AVERAGE);
		return (avgMinDamage + avgMaxDamage) / 2.0;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#causedDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double causedDamagePerPlayer(final CombatantSide side) {
		return getAverage(CombatantStatType.totalcauseddamage, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#shotsFiredWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int shotsFiredWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.hitcount, side) + getSum(CombatantStatType.misscount, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#hitsWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int hitsWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.hitcount, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#averageLifetime(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double averageLifetime(final CombatantSide side) {
		final int totalPlayers = (int) getCount(CombatantStatType.diedwhen, side);
		if (totalPlayers == 0) {
			return 0.0;
		}
		final int diedPlayers = (int) getPositiveCount(CombatantStatType.diedwhen, side);
		final int survivedPlayers = totalPlayers - diedPlayers;
		final double diedInRound = getPositiveAverage(CombatantStatType.diedwhen, side);

		return (diedPlayers * diedInRound + survivedPlayers * numberOfRounds) / totalPlayers;
	}

	private int numberOfClass(final CharacterClass charClass, final CombatantSide side) {
		final DataSet data = allData.get(CombatantStatType.charclass);
		// only available since v1.29
		if (data == null) {
			return 0;
		}
		final Integer value = Integer.valueOf(charClass.ordinal());
		return data.countOfValuesWith(value, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#numberOfAdventurers(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfAdventurers(final CombatantSide side) {
		return numberOfClass(CharacterClass.ADVENTURER, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#numberOfDuelants(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfDuelants(final CombatantSide side) {
		return numberOfClass(CharacterClass.DUELANT, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#numberOfGreenhorns(CombatantSide)
	 */
	public int numberOfGreenhorns(final CombatantSide side) {
		return numberOfClass(CharacterClass.GREENHORN, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#numberOfSoldiers(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfSoldiers(final CombatantSide side) {
		return numberOfClass(CharacterClass.SOLDIER, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#numberOfWorkers(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int numberOfWorkers(final CombatantSide side) {
		return numberOfClass(CharacterClass.WORKER, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#averageLevel(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double averageLevel(final CombatantSide side) {
		return getAverage(CombatantStatType.charlevel, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#initialHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int initialHealthWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.starthp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#endHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int endHealthPerPlayer(final CombatantSide side) {
		return (int) Math.round(getPositiveAverage(CombatantStatType.finishedhp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#endHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int endHealthWholeSide(final CombatantSide side) {
		return (int) Math.round(getPositiveSum(CombatantStatType.finishedhp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#maxPossibleHealthPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int maxPossibleHealthPerPlayer(final CombatantSide side) {
		return (int) Math.round(getAverage(CombatantStatType.maxhp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#maxPossibleHealthWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int maxPossibleHealthWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.maxhp, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#causedDamageWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int causedDamageWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.totalcauseddamage, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#shotsFiredPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double shotsFiredPerPlayer(final CombatantSide side) {
		return getAverage(CombatantStatType.hitcount, side) + getAverage(CombatantStatType.misscount, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#hitsPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double hitsPerPlayer(final CombatantSide side) {
		return getAverage(CombatantStatType.hitcount, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#takenDamagePerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double takenDamagePerPlayer(final CombatantSide side) {
		return getAverage(CombatantStatType.takendamage, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#takenDamageWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int takenDamageWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.takendamage, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#averageHitDamage(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double averageHitDamage(final CombatantSide side) {
		// don't count the misses
		final double teamHits = hitsWholeSide(side);
		if (teamHits == 0) {
			return 0;
		}
		final double teamDamage = causedDamageWholeSide(side);
		final double totalCalculated = teamDamage / teamHits;
		return totalCalculated;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#dodgesPerPlayer(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public double dodgesPerPlayer(final CombatantSide side) {
		return getAverage(CombatantStatType.dodgecount, side);
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#dodgesWholeSide(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public int dodgesWholeSide(final CombatantSide side) {
		return (int) Math.round(getSum(CombatantStatType.dodgecount, side));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#healthDistribution(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public SortedMap<Number, Number> healthDistribution(final CombatantSide side) {
		// initialize map
		final SortedMap<Number, Number> result = new TreeMap<Number, Number>();
		final int maxHP = 14000;
		final int step = 1000;
		for (int limit = step; limit <= maxHP; limit += step) {
			result.put(Integer.valueOf(limit), new AtomicInteger());
		}
		// fill with values
		final CombatantStatType stat = CombatantStatType.starthp;
		if (allData.containsKey(stat)) {
			final DataSet statData = allData.get(stat);
			final List<Integer> data = statData.getSideData(side);
			for (final Integer value : data) {
				Number key = getUpperLimit(value.intValue(), step);
				if (!result.containsKey(key)) {
					// out of range, put it to the last value
					key = result.lastKey();
				}
				((AtomicInteger) result.get(key)).incrementAndGet();
			}
		}
		return result;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.StaticStatistics#healthDistributionRelative(de.outstare.fortbattleplayer.model.CombatantSide)
	 */
	public SortedMap<Number, Double> healthDistributionRelative(final CombatantSide side) {
		final SortedMap<Number, Number> distribution = healthDistribution(side);
		final TreeMap<Number, Double> result = new TreeMap<Number, Double>();
		final double totalPlayers = numberOfPlayers(side);
		for (final Entry<Number, Number> mapping : distribution.entrySet()) {
			final double currentPlayers = mapping.getValue().doubleValue();
			final Double percentage = Double.valueOf(currentPlayers / totalPlayers * 100.0);
			result.put(mapping.getKey(), percentage);
		}
		return result;
	}

	/**
	 * @param value
	 * @param step
	 * @return
	 */
	private Number getUpperLimit(final int value, final int step) {
		int limit = 0;
		while (value > limit) {
			limit += step;
		}
		return Integer.valueOf(limit);
	}
}
