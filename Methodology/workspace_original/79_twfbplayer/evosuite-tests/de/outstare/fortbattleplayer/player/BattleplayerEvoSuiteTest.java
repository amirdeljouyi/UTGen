/*
 * This file was automatically generated by EvoSuite
 */

package de.outstare.fortbattleplayer.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import de.outstare.fortbattleplayer.player.Battleplan;
import de.outstare.fortbattleplayer.player.Battleplayer;
import de.outstare.fortbattleplayer.player.Round;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class BattleplayerEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      LinkedHashSet<Round> linkedHashSet0 = new LinkedHashSet<Round>();
      Battleplan battleplan0 = new Battleplan((Collection<Round>) linkedHashSet0);
      Battleplayer battleplayer0 = null;
      try {
        battleplayer0 = new Battleplayer(battleplan0);
        fail("Expecting exception: IllegalStateException");
      } catch(IllegalStateException e) {
        /*
         * Cannot play a battle without rounds!
         */
      }
  }
}