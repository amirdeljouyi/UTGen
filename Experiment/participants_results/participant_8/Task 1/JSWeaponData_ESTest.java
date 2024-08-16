package de.outstare.fortbattleplayer.model.impl;

import org.evosuite.runtime.EvoRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import de.outstare.fortbattleplayer.model.impl.JSWeaponData;
import de.outstare.fortbattleplayer.model.impl.SimpleWeapon;
import org.evosuite.runtime.EvoRunnerJUnit5;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.jupiter.api.extension.RegisterExtension;
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
    public void testIsGoldenGunReturningTrue() throws Throwable {
        SimpleWeapon simpleWeapon0 = new SimpleWeapon(136, "/*Sfb2v[V]22Kl-", 136, 136);
        JSWeaponData jSWeaponData0 = new JSWeaponData();
        boolean boolean0 = jSWeaponData0.isGoldenGun(simpleWeapon0);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testIsGoldenGunReturningFalse() throws Throwable {
        JSWeaponData jSWeaponData0 = new JSWeaponData();
        SimpleWeapon simpleWeapon0 = new SimpleWeapon(123, "3jB;R`L#}*d:,!", 100, 100);
        boolean boolean0 = jSWeaponData0.isGoldenGun(simpleWeapon0);
        assertFalse(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testGetGoldenGoldDeffBonus() throws Throwable {
        JSWeaponData jSWeaponData0 = new JSWeaponData();
        int int0 = jSWeaponData0.getGoldenGoldDeffBonus();
        assertEquals(3, int0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals0() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-38), (-38), (-38), (-38), (-38), "ELnEN>E");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-38), (-38), (-38), (-38), (-38), "ELnEN>E");
        boolean boolean0 = jSWeaponData_WeaponGameData1.equals(jSWeaponData_WeaponGameData0);
        assertTrue(boolean0);
        assertEquals("WeaponGameData: minDmg=-38, maxDmg=-38, offBonus=-38, deffBonus=-38, name=ELnEN>E", jSWeaponData_WeaponGameData1.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals1() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-4431), (-4431), (-4431), (-4431), (-4431), (String) null);
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-4431), (-4431), (-4431), (-4431), (-4431), "");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertEquals("WeaponGameData: minDmg=-4431, maxDmg=-4431, offBonus=-4431, deffBonus=-4431, name=", jSWeaponData_WeaponGameData1.toString());
        assertFalse(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals2() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-1635), (-1635), (-1635), (-1635), (-1635), "7");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-1635), (-1635), (-1635), (-1635), (-1635), "(`r&:XB");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertFalse(boolean0);
        assertEquals("WeaponGameData: minDmg=-1635, maxDmg=-1635, offBonus=-1635, deffBonus=-1635, name=(`r&:XB", jSWeaponData_WeaponGameData1.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals3() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-5), 7, (-5), (-5), (-5), "N&zMn$@6gffi<");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-5), (-5), (-5), (-5), (-5), "N&zMn$@6gffi<");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertFalse(boolean0);
        assertFalse(jSWeaponData_WeaponGameData1.equals((Object) jSWeaponData_WeaponGameData0));
        assertEquals("WeaponGameData: minDmg=7, maxDmg=-5, offBonus=-5, deffBonus=-5, name=N&zMn$@6gffi<", jSWeaponData_WeaponGameData0.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals4() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-5), 7, (-5), (-5), (-5), "N&zMn$@6gffi<");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-5), (-5), 7, (-5), (-5), "N&zMn$@6gffi<");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertEquals("WeaponGameData: minDmg=-5, maxDmg=7, offBonus=-5, deffBonus=-5, name=N&zMn$@6gffi<", jSWeaponData_WeaponGameData1.toString());
        assertFalse(boolean0);
        assertEquals("WeaponGameData: minDmg=7, maxDmg=-5, offBonus=-5, deffBonus=-5, name=N&zMn$@6gffi<", jSWeaponData_WeaponGameData0.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals5() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-68), (-68), (-68), (-68), (-68), "100,/0,110,0,0,Shuin");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-35), (-68), (-68), (-68), (-68), "100,/0,110,0,0,Shuin");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertFalse(jSWeaponData_WeaponGameData1.equals((Object) jSWeaponData_WeaponGameData0));
        assertFalse(boolean0);
        assertEquals("WeaponGameData: minDmg=-68, maxDmg=-68, offBonus=-68, deffBonus=-68, name=100,/0,110,0,0,Shuin", jSWeaponData_WeaponGameData1.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals6() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-60), (-60), (-60), (-22), (-60), "11,1,256?0,0,Rxstige B\u00FCchs");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-60), (-60), (-60), (-60), (-60), "11,1,256?0,0,Rxstige B\u00FCchs");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertFalse(jSWeaponData_WeaponGameData1.equals((Object) jSWeaponData_WeaponGameData0));
        assertEquals("WeaponGameData: minDmg=-60, maxDmg=-60, offBonus=-22, deffBonus=-60, name=11,1,256?0,0,Rxstige B\u00FCchs", jSWeaponData_WeaponGameData0.toString());
        assertFalse(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals7() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-15), (-15), (-15), (-15), 15, "11,1,256?0,0,Rxstige B\u00FCchs");
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-15), (-15), (-15), 15, (-15), "11,1,256?0,0,Rxstige B\u00FCchs");
        boolean boolean0 = jSWeaponData_WeaponGameData1.equals(jSWeaponData_WeaponGameData0);
        assertEquals("WeaponGameData: minDmg=-15, maxDmg=-15, offBonus=-15, deffBonus=15, name=11,1,256?0,0,Rxstige B\u00FCchs", jSWeaponData_WeaponGameData0.toString());
        assertEquals("WeaponGameData: minDmg=-15, maxDmg=-15, offBonus=15, deffBonus=-15, name=11,1,256?0,0,Rxstige B\u00FCchs", jSWeaponData_WeaponGameData1.toString());
        assertFalse(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals8() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData(2, 2, 2, 2, 2, "S@27|AN6-");
        Object object0 = new Object();
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(object0);
        assertFalse(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals9() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData(2, 2, 2, 2, 2, "S@27|AN6-");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData0);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals10() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData(2075, 2075, 2075, 2075, 2075, "de.outstare.fortbattleplayer.model.SectorBonus$BonusType");
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals((Object) null);
        assertFalse(boolean0);
        assertEquals("WeaponGameData: minDmg=2075, maxDmg=2075, offBonus=2075, deffBonus=2075, name=de.outstare.fortbattleplayer.model.SectorBonus$BonusType", jSWeaponData_WeaponGameData0.toString());
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testHasBonusReturningFalse() throws Throwable {
        JSWeaponData jSWeaponData0 = new JSWeaponData();
        SimpleWeapon simpleWeapon0 = new SimpleWeapon(104, "119,1,242,0,0,Rostige Schrotflinte", 104, 104);
        boolean boolean0 = jSWeaponData0.hasBonus(simpleWeapon0, 155, (-25));
        assertFalse(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testHasBonusReturningTrue() throws Throwable {
        JSWeaponData jSWeaponData0 = new JSWeaponData();
        SimpleWeapon simpleWeapon0 = new SimpleWeapon(104, ",", 104, 104);
        boolean boolean0 = jSWeaponData0.hasBonus(simpleWeapon0, 80, 130);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testCreatesWeaponGameDataTaking6ArgumentsAndCallsEquals11() throws Throwable {
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData0 = new JSWeaponData.WeaponGameData((-4431), (-4431), (-4431), (-4431), (-4431), (String) null);
        JSWeaponData.WeaponGameData jSWeaponData_WeaponGameData1 = new JSWeaponData.WeaponGameData((-4431), (-4431), (-4431), (-4431), (-4431), (String) null);
        boolean boolean0 = jSWeaponData_WeaponGameData0.equals(jSWeaponData_WeaponGameData1);
        assertTrue(boolean0);
        assertEquals("WeaponGameData: minDmg=-4431, maxDmg=-4431, offBonus=-4431, deffBonus=-4431, name=null", jSWeaponData_WeaponGameData1.toString());
    }

}
