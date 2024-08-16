package de.outstare.fortbattleplayer.model;

/**
 * A WeaponModification modifies the attributes of a weapon. Normally the damage
 * of a weapon is boosted, but it may give other bonuses.
 * 
 * @author daniel
 */
public interface WeaponModification {
	/**
	 * @return the display name of this item
	 */
	String getName();

	/**
	 * @return the additional damage this item adds to the maximum damage of a
	 *         weapon (can also be negative)
	 */
	int additionalMaxDamage();

	/**
	 * @return the additional damage this item adds to the minimum damage of a
	 *         weapon (can also be negative)
	 */
	int additionalMinDamage();
}
