package de.outstare.fortbattleplayer.model;

import de.outstare.fortbattleplayer.model.SectorBonus.BonusType;

/**
 * A Weapon which a {@link Combatant} uses to damage other combatants.
 * 
 * @author daniel
 */
public interface Weapon {
	/**
	 * @return the ingame ID of this weapon to uniquely identify it.
	 */
	int getId();

	/**
	 * @return the name of this weapon (not <code>null</code>)
	 */
	String name();

	/**
	 * @return the minimum damage which can be achieved with this weapon (not
	 *         negative)
	 */
	int minDamage();

	/**
	 * @return the maximum possible damage which can be achieved with this
	 *         weapon (greater or equal to {@link #minDamage()})
	 */
	int maxDamage();

	/**
	 * @param type
	 *            of bonus to get from this weapon
	 * @return the amount of {@link BonusType} this weapon gives to a
	 *         {@link Sector}
	 */
	int getSectorBonus(BonusType type);

	/**
	 * @return the average damage of this weapon
	 */
	int averageDamage();

	/**
	 * @return the item which modifies the attributes of this weapon or
	 *         <code>null</code> if no such exists.
	 */
	WeaponModification getModification();
}
