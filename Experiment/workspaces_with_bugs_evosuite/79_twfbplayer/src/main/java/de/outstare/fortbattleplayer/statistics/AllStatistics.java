package de.outstare.fortbattleplayer.statistics;

/**
 * This is a container for the different statistic objects
 * 
 * @author daniel
 */
public class AllStatistics {
	/**
	 * global statistics about the result of a team
	 */
	public final StaticStatistics combatant;
	/**
	 * statistics about the actions during the battle
	 */
	public final DynamicStatistics battle;
	/**
	 * deliveres per round statistics
	 */
	public final RoundStatGenerator rounds;

	/**
	 * @param combatant
	 * @param battle
	 * @param rounds
	 */
	public AllStatistics(final CombatantStatistic combatant, final BattleStatistics battle,
	        final RoundStatGenerator rounds) {
		this.combatant = combatant;
		this.battle = battle;
		this.rounds = rounds;
	}
}
