/*
 * This file was automatically generated by EvoSuite
 */

package de.outstare.fortbattleplayer.gui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import de.outstare.fortbattleplayer.gui.LogPanel;
import de.outstare.fortbattleplayer.player.Battleplayer;
import de.outstare.fortbattleplayer.player.CombatantEventDispatcher;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class LogPanelEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      LogPanel logPanel0 = null;
      try {
        logPanel0 = new LogPanel((Battleplayer) null, (CombatantEventDispatcher) null);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }
}