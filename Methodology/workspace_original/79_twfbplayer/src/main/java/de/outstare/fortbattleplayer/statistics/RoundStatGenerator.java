package de.outstare.fortbattleplayer.statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.CombatantState;

/**
 * generate {@link LabeledData} for selected aspects. To do this the state
 * of each round is taken and aggregated.
 * 
 * @author daniel
 */
public class RoundStatGenerator {
	private final Map<CombatantSide, LabeledData> count = new HashMap<CombatantSide, LabeledData>();
	private final Map<CombatantSide, LabeledData> health = new HashMap<CombatantSide, LabeledData>();

	/**
	 * create a new {@link RoundStatGenerator}
	 */
	public RoundStatGenerator() {
		for (final CombatantSide side : CombatantSide.values()) {
			health.put(side, new RoundStatistics("total health", "HP"));
			count.put(side, new RoundStatistics("number of combatants", "players"));
		}
	}

	/**
	 * @return the number of living {@link Combatant}s per round and team
	 */
	public Map<CombatantSide, LabeledData> getLivingCounts() {
		return count;
	}

	/**
	 * @return the health per team and round
	 */
	public Map<CombatantSide, LabeledData> getHealthAmount() {
		return health;
	}

	/**
	 * @param combatantStates
	 *            at the beginning of a round
	 */
	public void addRoundState(final Map<Combatant, CombatantState> combatantStates) {
		// TODO this may be optimized to iterate only once through the states
		createRoundStat(combatantStates, new HealthAggregator(), health);
		createRoundStat(combatantStates, new LivingCombatantAggregator(), count);
	}

	/**
	 * @param combatantStates
	 *            the source of the data
	 * @param aggregator
	 *            the processor of the data
	 * @param stats
	 *            the store for the processed data
	 */
	private void createRoundStat(final Map<Combatant, CombatantState> combatantStates,
	        final RoundAggregator aggregator, final Map<CombatantSide, LabeledData> stats) {
		for (final Entry<Combatant, CombatantState> combatantState : combatantStates.entrySet()) {
			aggregator.addValue(combatantState.getKey().getSide(), combatantState.getValue());
		}

		for (final Entry<CombatantSide, LabeledData> sideStats : stats.entrySet()) {
			final CombatantSide side = sideStats.getKey();
			final RoundStatistics stat = (RoundStatistics) sideStats.getValue();
			stat.addValue(aggregator.getSum(side));
		}
	}

	private static interface RoundAggregator {
		void addValue(CombatantSide side, CombatantState state);

		double getSum(CombatantSide side);
	}

	private abstract static class AbstractAggregator implements RoundAggregator {
		private final Map<CombatantSide, Counter> sums = new HashMap<CombatantSide, Counter>();

		AbstractAggregator() {
			for (final CombatantSide side : CombatantSide.values()) {
				sums.put(side, new Counter());
			}
		}

		/**
		 * @param side
		 * @param value
		 */
		protected void addValue(final CombatantSide side, final int value) {
			sums.get(side).incrementBy(value);
		}

		/**
		 * @see de.outstare.fortbattleplayer.statistics.RoundStatGenerator.RoundAggregator#getSum()
		 */
		public double getSum(final CombatantSide side) {
			return sums.get(side).getValue();
		}
	}

	private static class HealthAggregator extends AbstractAggregator {
		// for visibility not private
		HealthAggregator() {
			super();
		}

		/**
		 * @see de.outstare.fortbattleplayer.statistics.RoundStatGenerator.RoundAggregator#addValue(de.outstare.fortbattleplayer.model.CombatantState)
		 */
		public void addValue(final CombatantSide side, final CombatantState state) {
			addValue(side, state.getHealth());
		}
	}

	private static class LivingCombatantAggregator extends AbstractAggregator {
		// for visibility not private
		LivingCombatantAggregator() {
			super();
		}

		/**
		 * @see de.outstare.fortbattleplayer.statistics.RoundStatGenerator.RoundAggregator#addValue(de.outstare.fortbattleplayer.model.CombatantState)
		 */
		public void addValue(final CombatantSide side, final CombatantState state) {
			if (state.getHealth() > 0) {
				addValue(side, 1);
			}
		}
	}
}
