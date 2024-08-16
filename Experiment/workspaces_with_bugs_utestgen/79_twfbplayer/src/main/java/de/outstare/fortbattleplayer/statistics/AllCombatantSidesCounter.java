package de.outstare.fortbattleplayer.statistics;

import java.util.HashMap;
import java.util.Map;

import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * A {@link Counter} for all {@link CombatantSide}s
 * 
 * @author daniel
 */
class AllCombatantSidesCounter {
	private final Map<CombatantSide, Counter> perSideCounters = new HashMap<CombatantSide, Counter>();

	/**
	 * initialize a new {@link AllCombatantSidesCounter}
	 */
	AllCombatantSidesCounter() {
		super();
		addAllSides(perSideCounters);
	}

	private void addAllSides(final Map<CombatantSide, Counter> counters) {
		for (final CombatantSide side : CombatantSide.values()) {
			counters.put(side, new Counter());
		}
	}

	/**
	 * increment the counter of the side of the combatant
	 * 
	 * @param combatant
	 */
	void incrementSide(final Combatant combatant) {
		incrementSide(combatant.getSide());
	}

	/**
	 * increment the counter for the given side by one
	 * 
	 * @param side
	 *            if <code>null</code> increment all counters
	 */
	void incrementSide(final CombatantSide side) {
		if (side == null) {
			for (final Counter counter : perSideCounters.values()) {
				counter.increment();
			}
		} else {
			perSideCounters.get(side).increment();
		}
	}

	/**
	 * @param side
	 *            if <code>null</code> return the sum of all counters
	 * @return the current value of the counter for the given side
	 */
	int getSideValue(final CombatantSide side) {
		if (side == null) {
			int sum = 0;
			for (final Counter counter : perSideCounters.values()) {
				sum += counter.getValue();
			}
			return sum;
		}
		return perSideCounters.get(side).getValue();
	}

	/**
	 * @param amount
	 *            by which the counter will be incremented
	 * @param combatant
	 *            whose side will be incremented
	 */
	public void incrementBy(final int amount, final Combatant combatant) {
		incrementBy(amount, combatant.getSide());
	}

	/**
	 * @param amount
	 *            by which the counter will be incremented
	 * @param side
	 *            if <code>null</code> all counters will be incremented
	 */
	public void incrementBy(final int amount, final CombatantSide side) {
		if (side == null) {
			for (final Counter counter : perSideCounters.values()) {
				counter.incrementBy(amount);
			}
		} else {
			perSideCounters.get(side).incrementBy(amount);
		}
	}
}
