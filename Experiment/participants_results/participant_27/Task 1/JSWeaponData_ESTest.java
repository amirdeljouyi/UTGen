package de.outstare.fortbattleplayer.model.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(
        mockJVMNonDeterminism = true,
        useVFS = true,
        useVNET = true,
        resetStaticState = true,
        separateClassLoader = true)
public class JSWeaponData_ESTest extends JSWeaponData_ESTest_scaffolding {

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testIsNotGoldenGun() throws Throwable {
        // Arrange
        JSWeaponData jSWeaponData = new JSWeaponData();
        SimpleWeapon arg0 = new SimpleWeapon(100, "Golden Gun", 100, 100);

        // Act
        boolean goldenGun = jSWeaponData.isGoldenGun(arg0);

        // Assert
        assertFalse(goldenGun);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testIsGoldenGunSimpleWeaponReturnsTrue() throws Throwable {
        // Given: A JSWeaponData instance and a SimpleWeapon with an ID of 136 and the name "The Golden Gun"
        JSWeaponData jSWeaponData = new JSWeaponData();
        SimpleWeapon arg0 = new SimpleWeapon(136, "The Golden Gun", 3, 17);

        // When: The isGoldenGun method is called with the SimpleWeapon as an argument
        boolean goldenGun = jSWeaponData.isGoldenGun(arg0);

        // Then: The method should return true
        assertTrue(goldenGun);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testGetGoldenGoldDeffBonus() throws Throwable {
        JSWeaponData jSWeaponData = new JSWeaponData();
        // Get the golden gold defense bonus from the weapon data object
        int goldenGoldDeffBonus = jSWeaponData.getGoldenGoldDeffBonus();
        // Assert that the golden gold defense bonus is equal to 3
        assertEquals(3, goldenGoldDeffBonus);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualityOfTwoJSWeaponDataObjects() throws Throwable {
        // Arrange
        JSWeaponData.WeaponGameData weaponData1 =
                new JSWeaponData.WeaponGameData(136, 136, 136, 136, 136,
                        "de.outstare.fortbattleplayer.model.impl.JSWeaponData$WeaponGameData");
        JSWeaponData.WeaponGameData weaponData2 =
                new JSWeaponData.WeaponGameData(136, 136, 136, 136, 136,
                        "de.outstare.fortbattleplayer.model.impl.JSWeaponData$WeaponGameData");

        // Act
        boolean result = weaponData1.equals(weaponData2);

        // Assert
        assertTrue(result);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualsAndToString() throws Throwable {
        // Given a JSWeaponData.WeaponGameData object with specific values for minDmg, maxDmg, offBonus, deffBonus, and name
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData =
                new JSWeaponData.WeaponGameData(3465, 3465, 3465, 3465, 3465, (String) null);
        JSWeaponData.WeaponGameData arg0 =
                new JSWeaponData.WeaponGameData(3465, 3465, 3465, 3465, 3465, (String) null);

        // When comparing the given object to another object with the same values
        boolean equals = jSWeaponData_WeaponGameData.equals(arg0);

        // Then assert that the objects are equal and have the expected string representation
        assertTrue(equals);
        assertEquals(
                "WeaponGameData: minDmg=3465, maxDmg=3465, offBonus=3465, deffBonus=3465, name=null", arg0.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualityOfWeaponGameDataObjects() throws Throwable {
        // No Comments were added
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData =
                new JSWeaponData.WeaponGameData(126, 126, 126, 126, 126, (String) null);
        JSWeaponData.WeaponGameData arg0 =
                new JSWeaponData.WeaponGameData(126, 126, 126, 126, 126, "");
        boolean equals = jSWeaponData_WeaponGameData.equals(arg0);
        assertFalse(arg0.equals((Object) jSWeaponData_WeaponGameData));
        assertFalse(equals);
        assertEquals(
                "WeaponGameData: minDmg=126, maxDmg=126, offBonus=126, deffBonus=126, name=", arg0.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualsDifferentMinDmgValues() throws Throwable {
        // Given: We have two instances of JSWeaponData.WeaponGameData with different values for minDmg
        JSWeaponData.WeaponGameData defaultWeapon =
                new JSWeaponData.WeaponGameData(35, 35, 35, 35, 35, "Ninja Sword");
        JSWeaponData.WeaponGameData customWeapon =
                new JSWeaponData.WeaponGameData(35, 17, 35, 35, 35, "Ninja Sword");

        // When: We call equals() method on the two instances with the default WeaponGameData instance
        boolean equals = defaultWeapon.equals(customWeapon);

        // Then: The result of equals is false, since the two instances have different values for minDmg
        assertFalse(equals, "The two instances should not be equal");

        // And: The toString method on the second instance returns a string with the correct values for minDmg, maxDmg, offBonus, deffBonus, and name
        assertEquals(
                "WeaponGameData: minDmg=35, maxDmg=35, offBonus=35, deffBonus=35, name=Ninja Sword", defaultWeapon.toString());
        assertEquals(
                "WeaponGameData: minDmg=35, maxDmg=17, offBonus=35, deffBonus=35, name=Ninja Sword", customWeapon.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualsWithDefaultAndCustomValues() throws Throwable {
        // Given: two instances of WeaponGameData, one with default values and one with custom values
        JSWeaponData.WeaponGameData defaultWeapon =
                new JSWeaponData.WeaponGameData(17, 17, 17, 17, 17, "3,42,158,0,0,Tod Sense");
        JSWeaponData.WeaponGameData customWeapon =
                new JSWeaponData.WeaponGameData(45, 17, 17, 17, 17, "3,42,158,0,0,Tod Sense");

        // When: we call the equals method on both instances
        boolean defaultEquals = defaultWeapon.equals(customWeapon);
        boolean customEquals = customWeapon.equals(defaultWeapon);

        // Then: the instances should not be equal, as they have different minDmg values
        assertFalse(defaultEquals, "The instances with default and custom values should not be equal");
        assertFalse(customEquals, "The instances with default and custom values should not be equal");
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testWeaponGameDataEquals() throws Throwable {
        // Create two JSWeaponData.WeaponGameData instances with different min and max damage values
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData =
                new JSWeaponData.WeaponGameData(106, 106, 118, 106, 106, "Laser Pistol");
        JSWeaponData.WeaponGameData arg0 =
                new JSWeaponData.WeaponGameData(107, 106, 106, 106, 106, "Laser Pistol");

        // Check that the instances are not equal
        assertFalse(jSWeaponData_WeaponGameData.equals(arg0));

        // Assert that the instances have different toString representations
        assertNotEquals(
                "WeaponGameData: minDmg=106, maxDmg=106, offBonus=106, deffBonus=106, name=Laser Pistol", jSWeaponData_WeaponGameData.toString());
        assertNotEquals(
                "WeaponGameData: minDmg=107, maxDmg=106, offBonus=106, deffBonus=106, name=Laser Pistol", arg0.toString());

        // Assert that the instances have different hash codes
        assertNotEquals(jSWeaponData_WeaponGameData.hashCode(), arg0.hashCode());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testWeaponDataEqualityIsNotReflexive() throws Throwable {
        // Given
        JSWeaponData.WeaponGameData weaponData1 =
                new JSWeaponData.WeaponGameData(2530, 106, 106, (-2533), 3, "F?:WMFV%E8.3gop>");
        JSWeaponData.WeaponGameData weaponData2 =
                new JSWeaponData.WeaponGameData(3728, 3728, 1, (-1), 3, "*G[&nWT*WDeSqY'");

        // When
        boolean areEqual = weaponData1.equals(weaponData2);

        // Then
        assertFalse(areEqual);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualsDifferentValues() throws Throwable {
        // Given two instances of the same weapon data with different values for some fields
        JSWeaponData.WeaponGameData weaponData1 =
                new JSWeaponData.WeaponGameData(31, 50, 127, (-1245), (-2100799218), (String) null);
        JSWeaponData.WeaponGameData weaponData2 =
                new JSWeaponData.WeaponGameData(127, 127, (-3533), 1499, (-3533), "Laser-Geschütz");

        // When the equals method is called on these instances with one of them as an argument
        boolean equals = weaponData1.equals(weaponData2);

        // Then assert that these instances are not equal
        assertFalse(equals);
        // And assert that the toString method returns a string representation of the weapon data with the correct values for all fields
        assertEquals("WeaponGameData: minDmg=50, maxDmg=127, offBonus=-1245, deffBonus=-2100799218, name=null", weaponData1.toString());
        assertEquals("WeaponGameData: minDmg=127, maxDmg=-3533, offBonus=1499, deffBonus=-3533, name=Laser-Geschütz", weaponData2.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualsDifferentObjectNotEqual() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData =
                new JSWeaponData.WeaponGameData(126, 126, 126, 126, 126, null);
        Object arg0 = new Object();
        // Test the equals() method with a different object that is not equal to jSWeaponData_WeaponGameData
        boolean equals = jSWeaponData_WeaponGameData.equals(arg0);
        assertFalse(equals, "The objects should not be equal");
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testNotEqualsWithDifferentNames() throws Throwable {
        JSWeaponData.WeaponGameData arg0 =
                new JSWeaponData.WeaponGameData(-1, -1, -1, -1, -1, "H.lfz<9SF|_!2");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData =
                new JSWeaponData.WeaponGameData(-1, -1, -1, -1, -1, "13},4',15{,0,0,Tod S*nse");
        boolean equals = jSWeaponData_WeaponGameData.equals(arg0);
        // Test that the weapon data objects are not equal
        assertFalse(equals);

        // Test that the weapon data objects have different names
        String arg0Name = arg0.name;
        String jSWeaponData_WeaponGameDataName = jSWeaponData_WeaponGameData.name;
        assertNotEquals(arg0Name, jSWeaponData_WeaponGameDataName);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testWeaponEqualsSelf() throws Throwable {
        // Given a weapon with the following properties:
        // - ID is 818
        // - Min damage is -3533
        // - Max damage is 152
        // - Offense bonus is 152
        // - Defense bonus is 2
        // - Name is "Nachbedingung nicht erfüllt"
        JSWeaponData.WeaponGameData testWeapon = new JSWeaponData.WeaponGameData(
                        818, (-3533), 152, 152, 2, "Nachbedingung nicht erfüllt");

        // When the weapon's properties are compared to itself using the "equals" method
        boolean equals = testWeapon.equals(testWeapon);

        // Then the result should be true, as the weapon is equal to itself
        assertTrue(equals);
        assertEquals("WeaponGameData: minDmg=-3533, maxDmg=152, offBonus=152, deffBonus=2, name=Nachbedingung nicht erfüllt",
                testWeapon.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testEqualsWithNullObject() throws Throwable {
        // Given: a JSWeaponData.WeaponGameData object with the specified values
        JSWeaponData.WeaponGameData weaponData =
                new JSWeaponData.WeaponGameData(0, 1, (-3531), 0, (-3531), "Rostige Flinte");

        // When: the object is compared to null
        boolean equals = weaponData.equals((Object) null);

        // Then: the result should be false
        assertFalse(equals);

        // And: the object's toString method should return the expected string
        assertEquals("WeaponGameData: minDmg=1, maxDmg=-3531, offBonus=0, deffBonus=-3531, name=Rostige Flinte",
                weaponData.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testHasBonusWithNegativeRatings() throws Throwable {
        // Given a weapon with attack rating 118 and defense rating 118
        SimpleWeapon weapon = new SimpleWeapon(118, "e", 118, 118);

        // And a JSWeaponData object that has no bonuses for weapons with attack rating -150 or defense rating -150
        JSWeaponData weaponData = new JSWeaponData();

        // When checking if the weapon has a bonus based on its attack and defense ratings
        boolean hasBonus = weaponData.hasBonus(weapon, (-150), (-150));

        // Then the method should return false since the weapon does not have a bonus for attack rating -150 or defense rating -150
        assertFalse(hasBonus);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testHasBonusWithNegativeLevels() throws Throwable {
        // Given a JSWeaponData instance with a weapon data object
        SimpleWeapon arg0 = new SimpleWeapon(118, "_Lv'EaV,zz\"f7ri", 118, 118);

        // When the method hasBonus is called with a specific weapon and level
        JSWeaponData jSWeaponData = new JSWeaponData();
        System.out.println(jSWeaponData);
        boolean bonus = jSWeaponData.hasBonus(arg0, (-50), (-150));

        // Then the method should return true if the weapon has a bonus at that level
        assertTrue(bonus);
    }
}
