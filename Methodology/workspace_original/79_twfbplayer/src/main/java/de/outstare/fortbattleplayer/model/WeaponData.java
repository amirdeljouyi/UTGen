package de.outstare.fortbattleplayer.model;

/**
 * WeaponData holds data about weapons. It can be used to determine additional
 * information to a weapon from the fortbattle log.
 * 
 * @author daniel
 */
public interface WeaponData {
	/**
	 * the name will be translated, so this should be contained in all
	 * languages! It is therefore only a heuristic :(
	 */
	public static final String GOLDEN_GUN = "Gold";
	/**
	 * additional damage the golden gun gives to a sector. This is not in the
	 * fortbattle data and therefore a constant :-/
	 */
	public static final int GOLDEN_GUN_DAMAGE_BONUS = 15;
	/**
	 * additional attack bonus the golden gun gives to a sector. This is not in
	 * the fortbattle data and therefore a constant :-/
	 */
	public static final int GOLDEN_GUN_ATTACK_BONUS = 3;
	/**
	 * additional defense bonus the golden gun gives to a sector. This is not in
	 * the fortbattle data and therefore a constant :-/
	 */
	public static final int GOLDEN_GUN_DEFENSE_BONUS = 3;

	/**
	 * @param weapon
	 * @return <code>true</code> if the given weapon has the special bonuses of
	 *         the golden gun
	 */
	boolean isGoldenGun(Weapon weapon);

	/**
	 * @return the amount of additional damage a golden gun gives to it's sector
	 */
	int getGoldenGoldDmgBonus();

	/**
	 * @return the amount of shooting bonus a golden gun gives to it's sector
	 */
	int getGoldenGoldAttBonus();

	/**
	 * @return the amount of dodging bonus a golden gun gives to it's sector
	 */
	int getGoldenGoldDeffBonus();

	/**
	 * @param weapon
	 * @return <code>true</code> if the the given weapon has a bajonett (does
	 *         more damage than normal)
	 */
	boolean hasBayonet(Weapon weapon);

	/**
	 * @param weapon
	 * @return <code>true</code> if the the given weapon has graphit lubricant
	 *         (does more damage than normal)
	 */
	boolean hasGraphitLubricant(Weapon weapon);

	boolean hasFettesWeaponOil(Weapon weapon);

	boolean hasSchmierendesWeaponOil(Weapon weapon);

	boolean hasShinyWeaponOil(Weapon weapon);

	boolean hasLoadingchamberOrEnhancedPatrons(Weapon weapon);

	boolean hasHipFlask(Weapon w);
}
