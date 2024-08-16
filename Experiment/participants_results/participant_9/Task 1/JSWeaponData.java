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
    /*
     *
     *
     *
     *                                    DEFINITELY GOOD CODE
     *
     *
     */

    private static final transient Logger LOG = Logger.getLogger(JSWeaponData.class.getName());
    private static final String CSV_FILE_WEAPONS = "/weaponData.csv";
    private static final String CSV_DELIMITER = ",";

    /**
     * maps the id of a weapon to it's stats
     */
    protected final static Map<Integer, WeaponGameData> data = loadData(CSV_FILE_WEAPONS);

    /**
     * Loads weapon data from a CSV file.
     * <p>
     * This method reads weapon statistics from a CSV file and maps each weapon's ID to its corresponding stats.
     *
     * @param filename The name of the CSV file containing weapon data.
     * @return A Map where each key is a weapon's ID and the value is the weapon's game data.
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
     * Retrieves the damage bonus of a "Golden Gun".
     *
     * @return The damage bonus value for "Golden Guns".
     */
    public int getGoldenGoldDmgBonus() {
        return WeaponData.GOLDEN_GUN_DAMAGE_BONUS;
    }

    /**
     * Retrieves the attack bonus of a "Golden Gun".
     *
     * @return The attack bonus value for "Golden Guns".
     */
    public int getGoldenGoldAttBonus() {
        return WeaponData.GOLDEN_GUN_ATTACK_BONUS;
    }

    /**
     * Checks if the specified weapon has a bayonet attached.
     * <p>
     * This method determines the presence of a bayonet by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has a bayonet, false otherwise.
     */
    public boolean hasBayonet(final Weapon weapon) {
        return hasBonus(weapon, 25, 75);
    }

    /**
     * Checks if the specified weapon has graphite lubricant applied.
     * <p>
     * This method determines the presence of graphite lubricant by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has graphite lubricant, false otherwise.
     */
    public boolean hasGraphitLubricant(final Weapon weapon) {
        return hasBonus(weapon, 20, 40);
    }

    /**
     * Checks if the specified weapon has Fettes weapon oil applied.
     * <p>
     * This method determines the presence of Fettes weapon oil by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has Fettes weapon oil, false otherwise.
     */
    public boolean hasFettesWeaponOil(final Weapon weapon) {
        return hasBonus(weapon, 50, 50);
    }

    /**
     * Checks if the specified weapon has Schmierendes weapon oil applied.
     * <p>
     * This method determines the presence of Schmierendes weapon oil by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has Schmierendes weapon oil, false otherwise.
     */
    public boolean hasSchmierendesWeaponOil(final Weapon weapon) {
        return hasBonus(weapon, 60, 60);
    }

    /**
     * Checks if the specified weapon has shiny weapon oil applied.
     * <p>
     * This method determines the presence of shiny weapon oil by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has shiny weapon oil, false otherwise.
     */
    public boolean hasShinyWeaponOil(final Weapon weapon) {
        return hasBonus(weapon, 75, 75);
    }

    /**
     * Checks if the specified weapon has a hip flask attached.
     * <p>
     * This method determines the presence of a hip flask by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has a hip flask, false otherwise.
     */
    public boolean hasHipFlask(final Weapon weapon) {
        return hasBonus(weapon, 40, 40);
    }

    /**
     * Checks if the specified weapon has either a loading chamber or enhanced patrons applied.
     * <p>
     * This method determines the presence of these modifications by comparing the weapon's stats against predefined values.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon has a loading chamber or enhanced patrons, false otherwise.
     */
    public boolean hasLoadingchamberOrEnhancedPatrons(final Weapon weapon) {
        return hasBonus(weapon, 30, 30);
    }

    /*
     *
     *
     *
     *                                    POSSIBLY FAULTY CODE
     *
     *
     */

    /**
     * Retrieves the defense bonus of a "Golden Gun".
     *
     * @return The defense bonus value for "Golden Guns".
     */
    public int getGoldenGoldDeffBonus() {
        return WeaponData.GOLDEN_GUN_DEFENSE_BONUS;
    }

    /**
     * Determines if the specified weapon is a "Golden Gun".
     * <p>
     * A "Golden Gun" is identified based on certain criteria in its stats.
     *
     * @param weapon The weapon to be checked.
     * @return true if the weapon meets the criteria of a "Golden Gun", false otherwise.
     */
    public boolean isGoldenGun(final Weapon weapon) {
        final WeaponGameData weaponData = data.get(Integer.valueOf(weapon.getId()));
        // System.out.println("data for " + weapon.getId() + " is " +
        // weaponData);
        return weaponData != null && (weaponData.bonusOffense > 0 || weaponData.bonusDefense > 0);
    }


    /**
     * Determines if a weapon has a specific bonus applied.
     * <p>
     * This method checks if the weapon's damage values have been modified by a specific bonus amount. logically a
     * bonus is awarded in the case that the different in max and min damages are equal to those presented.
     *
     * @param weapon      The weapon to be checked.
     * @param minDmgBonus The minimum damage bonus to check for.
     * @param maxDmgBonus The maximum damage bonus to check for.
     * @return true if the weapon has the specified bonus, false otherwise.
     */
    boolean hasBonus(final Weapon weapon, final int minDmgBonus, final int maxDmgBonus) {
        final WeaponGameData weaponData = data.get(Integer.valueOf(weapon.getId()));
        return weaponData != null && weapon.maxDamage() - weaponData.maxDamage == maxDmgBonus
                && weapon.minDamage() - weaponData.minDamage == minDmgBonus;
    }


    /**
     * Represents the game data for a weapon.
     * <p>
     * This class encapsulates all relevant statistics and information about a weapon
     * used in the game, including its damage range, bonuses to offense and defense, and its name.
     * It provides a structured way to access these properties for each weapon.
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
         * Constructs a new {@code WeaponGameData} instance with specified weapon attributes.
         *
         * @param id           The unique identifier for the weapon.
         * @param minDamage    The minimum damage that the weapon can inflict.
         * @param maxDamage    The maximum damage that the weapon can inflict.
         * @param bonusOffense The bonus to offense provided by the weapon.
         * @param bonusDefense The bonus to defense provided by the weapon.
         * @param name         The name of the weapon.
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
         * Generates a hash code for this {@code WeaponGameData}.
         * <p>
         * The hash code is calculated using all the fields of the class.
         *
         * @return The hash code value for this object.
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
         * Compares this {@code WeaponGameData} with another object for equality.
         * <p>
         * This method checks if the given object is an instance of {@code WeaponGameData}
         * and compares all fields for equality.
         *
         * @param obj The object to be compared for equality with this {@code WeaponGameData}.
         * @return true if the specified object is equal to this {@code WeaponGameData}, false otherwise.
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
            if (minDamage != other.minDamage) {
                return false;
            }
            if (maxDamage != other.maxDamage) {
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
         * Returns a string representation of this {@code WeaponGameData}.
         * <p>
         * The string includes all the fields of the class, providing a detailed description
         * of the weapon's game data.
         *
         * @return A string representation of this {@code WeaponGameData}.
         */
        @Override
        public String toString() {
            return "WeaponGameData: minDmg=" + minDamage + ", maxDmg=" + maxDamage + ", offBonus=" + bonusOffense
                    + ", deffBonus=" + bonusDefense + ", name=" + name;
        }

    }

    /**
     * Main method for testing and debugging.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(final String[] args) {
        System.out.println(data);
    }
}
