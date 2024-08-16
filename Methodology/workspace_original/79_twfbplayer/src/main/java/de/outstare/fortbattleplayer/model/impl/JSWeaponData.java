package de.outstare.fortbattleplayer.model.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.WeaponData;

/**
 * A JSWeaponData uses the data from the JavaScript object "ItemManager".
 * 
 * @author daniel
 */
public class JSWeaponData implements WeaponData {
	private static final transient Logger LOG = Logger.getLogger(JSWeaponData.class.getName());
	private static final String CSV_FILE_WEAPONS = "/weaponData.csv";
	private static final String CSV_DELIMITER = ",";

	/**
	 * maps the id of a weapon to it's stats
	 */
	protected final static Map<Integer, WeaponGameData> data = loadData(CSV_FILE_WEAPONS);

	/**
	 * @param filename
	 * @return
	 */
	private static Map<Integer, WeaponGameData> loadData(final String filename) {
		final Map<Integer, WeaponGameData> result = new HashMap<Integer, WeaponGameData>();
		try {
			final InputStream fileInput = JSWeaponData.class.getResourceAsStream(filename);
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
					final String[] values = line.split(CSV_DELIMITER);
					assert values.length >= 5 : "csv has not at least 5 fields!";
					final int id = Integer.valueOf(values[0]).intValue();
					final int minDmg = Integer.valueOf(values[1]).intValue();
					final int maxDmg = Integer.valueOf(values[2]).intValue();
					final int attBonus = Integer.valueOf(values[3]).intValue();
					final int deffBonus = Integer.valueOf(values[4]).intValue();
					final String weaponName;
					if (values.length > 5) {
						weaponName = values[5];
					} else {
						weaponName = "";
					}
					final WeaponGameData weapon = new WeaponGameData(id, minDmg, maxDmg, attBonus, deffBonus,
					        weaponName);
					result.put(Integer.valueOf(id), weapon);
				}
				LOG.fine("successfully read " + result.size() + " weapon data from file " + filename);
			} finally {
				input.close();
				fileInput.close();
			}
		} catch (final FileNotFoundException e) {
			LOG.severe("the CSV file with the bayonet damages was not found: " + filename);
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
		final WeaponGameData weaponData = data.get(Integer.valueOf(weapon.getId()));
		// System.out.println("data for " + weapon.getId() + " is " +
		// weaponData);
		return weaponData != null && (weaponData.bonusOffense > 0 || weaponData.bonusDefense > 0);
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
		return hasBonus(weapon, 25, 75);
	}

	/**
	 * @param weapon
	 * @param weaponData
	 * @param minDmgBonus
	 * @param maxDmgBonus
	 * @return
	 */
	boolean hasBonus(final Weapon weapon, final int minDmgBonus, final int maxDmgBonus) {
		final WeaponGameData weaponData = data.get(Integer.valueOf(weapon.getId()));
		return weaponData != null && weapon.maxDamage() - weaponData.maxDamage == maxDmgBonus
		        && weapon.minDamage() - weaponData.minDamage == minDmgBonus;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasGraphitLubricant(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasGraphitLubricant(final Weapon weapon) {
		return hasBonus(weapon, 20, 40);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasFettesWeaponOil(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasFettesWeaponOil(final Weapon weapon) {
		return hasBonus(weapon, 50, 50);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasSchmierendesWeaponOil(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasSchmierendesWeaponOil(final Weapon weapon) {
		return hasBonus(weapon, 60, 60);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasShinyWeaponOil(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasShinyWeaponOil(final Weapon weapon) {
		return hasBonus(weapon, 75, 75);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasHipFlask(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasHipFlask(final Weapon weapon) {
		return hasBonus(weapon, 40, 40);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.WeaponData#hasLoadingchamberOrEnhancedPatrons(de.outstare.fortbattleplayer.model.Weapon)
	 */
	public boolean hasLoadingchamberOrEnhancedPatrons(final Weapon weapon) {
		return hasBonus(weapon, 30, 30);
	}

	/**
	 * Parsed data from file.
	 * 
	 * @author daniel
	 */
	static class WeaponGameData {
		@SuppressWarnings("javadoc")
		final int id;
		@SuppressWarnings("javadoc")
		final int minDamage;
		@SuppressWarnings("javadoc")
		final int maxDamage;
		@SuppressWarnings("javadoc")
		final int bonusOffense;
		@SuppressWarnings("javadoc")
		final int bonusDefense;
		@SuppressWarnings("javadoc")
		final String name;

		/**
		 * @param id
		 * @param minDamage
		 * @param maxDamage
		 * @param bonusOffense
		 * @param bonusDefense
		 * @param name
		 */
		WeaponGameData(final int id, final int minDamage, final int maxDamage, final int bonusOffense,
		        final int bonusDefense, final String name) {
			this.id = id;
			this.minDamage = minDamage;
			this.maxDamage = maxDamage;
			this.bonusOffense = bonusOffense;
			this.bonusDefense = bonusDefense;
			this.name = name;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + bonusDefense;
			result = prime * result + bonusOffense;
			result = prime * result + id;
			result = prime * result + maxDamage;
			result = prime * result + minDamage;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			if (!(obj instanceof WeaponGameData)) {
				return false;
			}
			final WeaponGameData other = (WeaponGameData) obj;
			if (bonusDefense != other.bonusDefense) {
				return false;
			}
			if (bonusOffense != other.bonusOffense) {
				return false;
			}
			if (id != other.id) {
				return false;
			}
			if (maxDamage != other.maxDamage) {
				return false;
			}
			if (minDamage != other.minDamage) {
				return false;
			}
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "WeaponGameData: minDmg=" + minDamage + ", maxDmg=" + maxDamage + ", offBonus=" + bonusOffense
			        + ", deffBonus=" + bonusDefense + ", name=" + name;
		}

	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println(data);
	}
}
