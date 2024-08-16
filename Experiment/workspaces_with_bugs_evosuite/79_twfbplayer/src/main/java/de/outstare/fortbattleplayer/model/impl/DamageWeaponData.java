package de.outstare.fortbattleplayer.model.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.WeaponData;

/**
 * {@link DamageWeaponData} determines {@link WeaponData} based on the damages
 * of the weapons
 * 
 * @author daniel
 * @deprecated use {@link JSWeaponData} because it is much more accurate
 */
@Deprecated
public class DamageWeaponData implements WeaponData {
	private static final transient Logger LOG = Logger.getLogger(DamageWeaponData.class.getName());
	private static final String CSV_FILE_DAMAGE_BAYONET = "/bayonetdmg.csv";
	private static final String CSV_FILE_DAMAGE_GRAPHIT = "/graphitdmg.csv";
	private static final String CSV_DELIMITER = ",";

	/**
	 * maps the max damage to the min damage
	 */
	private static final Map<Integer, Set<Integer>> bayonetDamages;

	/**
	 * maps the max damage to the min damage
	 */
	private static final Map<Integer, Set<Integer>> graphitDamages;

	// static constructor
	static {
		bayonetDamages = readCSVFile(CSV_FILE_DAMAGE_BAYONET);
		graphitDamages = readCSVFile(CSV_FILE_DAMAGE_GRAPHIT);
	}

	/**
	 * @param filename
	 * @return
	 */
	private static Map<Integer, Set<Integer>> readCSVFile(final String filename) {
		final HashMap<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();

		try {
			final InputStream fileInput = DamageWeaponData.class.getResourceAsStream(filename);
			if (fileInput == null) {
				throw new FileNotFoundException(filename);
			}
			final BufferedReader input = new BufferedReader(new InputStreamReader(fileInput));
			try {
				String line;
				while ((line = input.readLine()) != null) {
					if ("".equals(line.trim())) {
						continue;
					}
					final String[] dmgParts = line.split(CSV_DELIMITER);
					assert dmgParts.length == 2 : "csv has not 2 fields!";
					final Integer minDmg = Integer.valueOf(dmgParts[0]);
					final Integer maxDmg = Integer.valueOf(dmgParts[1]);
					// map maxDmg to min
					if (!result.containsKey(maxDmg)) {
						result.put(maxDmg, new HashSet<Integer>());
					}
					result.get(maxDmg).add(minDmg);
				}
				LOG.fine("successfully read " + result.size() + " weapon damages from file " + filename);
			} finally {
				input.close();
				fileInput.close();
			}
		} catch (final FileNotFoundException e) {
			LOG.severe("the CSV file with the bayonet damages was not found: " + CSV_FILE_DAMAGE_BAYONET);
			e.printStackTrace();
		} catch (final IOException e) {
			LOG.severe("failed to load CSV file with bayonet damages: " + e.toString());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#isGoldenGun(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean isGoldenGun(final Weapon weapon) {
		assert weapon != null : "pre condition violated: weapon != null";
		final Locale locale = Locale.getDefault();
		return weapon.name().toLowerCase(locale).contains(WeaponData.GOLDEN_GUN.toLowerCase(locale));
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#getGoldenGoldDmgBonus()
	 */
	public int getGoldenGoldDmgBonus() {
		return WeaponData.GOLDEN_GUN_DAMAGE_BONUS;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#getGoldenGoldAttBonus()
	 */
	public int getGoldenGoldAttBonus() {
		return WeaponData.GOLDEN_GUN_ATTACK_BONUS;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#getGoldenGoldDeffBonus()
	 */
	public int getGoldenGoldDeffBonus() {
		return WeaponData.GOLDEN_GUN_DEFENSE_BONUS;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasBayonet(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasBayonet(final Weapon weapon) {
		return containsWeapon(bayonetDamages, weapon);
	}

	/**
	 * @param weapondamages
	 * @param weapon
	 * @return
	 */
	private boolean containsWeapon(final Map<Integer, Set<Integer>> weapondamages, final Weapon weapon) {
		final Integer weaponMaxDmg = Integer.valueOf(weapon.maxDamage());
		final Integer weaponMinDmg = Integer.valueOf(weapon.minDamage());
		boolean hasBajonett = false;
		// the bayonet damages have to be distinct from the normal damages
		if (weapondamages.containsKey(weaponMaxDmg)) {
			final Set<Integer> minDiff = weapondamages.get(weaponMaxDmg);
			if (minDiff.contains(weaponMinDmg)) {
				hasBajonett = true;
			}
		}
		return hasBajonett;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasGraphitLubricant(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasGraphitLubricant(final Weapon weapon) {
		return containsWeapon(graphitDamages, weapon);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasFettesWeaponOil(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasFettesWeaponOil(final Weapon weapon) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasSchmierendesWeaponOil(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasSchmierendesWeaponOil(final Weapon weapon) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasShinyWeaponOil(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasShinyWeaponOil(final Weapon weapon) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasLoadingchamberOrEnhancedPatrons(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasLoadingchamberOrEnhancedPatrons(final Weapon weapon) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasHipFlask(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasHipFlask(final Weapon w) {
		// TODO Auto-generated method stub
		return false;
	}
}
