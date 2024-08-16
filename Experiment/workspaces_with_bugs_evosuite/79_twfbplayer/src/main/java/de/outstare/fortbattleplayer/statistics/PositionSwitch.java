package de.outstare.fortbattleplayer.statistics;

import de.outstare.fortbattleplayer.model.Combatant;

/**
 * Two {@link Combatant}s switched positions.
 * 
 * They will be sorted by round and playername
 * 
 * @author daniel
 */
public class PositionSwitch implements Comparable<PositionSwitch> {
	/**
	 * number of the round in which this swap occured
	 */
	public final int round;
	/**
	 * one of the switched players
	 */
	public final Combatant player1;
	/**
	 * the HP of player1
	 */
	public final Integer health1;
	/**
	 * the other combatant who switched places
	 */
	public final Combatant player2;
	/**
	 * the HP of player2
	 */
	public final Integer health2;

	/**
	 * @param round
	 * @param player1
	 * @param player2
	 */
	PositionSwitch(final int round, final Combatant player1, final Combatant player2) {
		this.round = round;
		this.player1 = player1;
		this.player2 = player2;
		health1 = Integer.valueOf(player1._health());
		health2 = Integer.valueOf(player2._health());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player1 == null) ? 0 : player1.hashCode());
		result = prime * result + round;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PositionSwitch)) {
			return false;
		}
		final PositionSwitch other = (PositionSwitch) obj;
		if (player1 == null) {
			if (other.player1 != null) {
				return false;
			}
		} else if (!player1.equals(other.player1)) {
			return false;
		}
		if (round != other.round) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final PositionSwitch o) {
		int order = -1;
		if (equals(o)) {
			order = 0;
		} else if (round > o.round) {
			order = 1;
		} else if (round == o.round) {
			order = player1.getName().compareTo(o.player1.getName());
		}
		return order;
	}
}
