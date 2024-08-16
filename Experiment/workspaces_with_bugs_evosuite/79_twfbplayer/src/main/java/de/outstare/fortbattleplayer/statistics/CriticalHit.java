package de.outstare.fortbattleplayer.statistics;

import de.outstare.fortbattleplayer.model.Combatant;

/**
 * A CriticalHit is an unormal amount of damage inflicted by a {@link Combatant}
 * .
 * 
 * @author daniel
 */
public class CriticalHit {
	/**
	 * the player who has fired this shot
	 */
	public final String playerName;
	/**
	 * the player who received this hit
	 */
	public final String victimName;
	/**
	 * the damage of this hit
	 */
	public final int damage;
	/**
	 * the round in which this hit was inflicted
	 */
	public final int round;
	/**
	 * the amount of damage above the normal weapon damage
	 */
	public final int critOnlyDamage;

	/**
	 * @param player
	 * @param victim
	 * @param damage
	 * @param round
	 */
	CriticalHit(final Combatant player, final Combatant victim, final int damage, final int round) {
		playerName = player.getName();
		victimName = victim.getName();
		this.damage = damage;
		critOnlyDamage = damage - player.getWeapon().averageDamage();
		this.round = round;
	}
}
