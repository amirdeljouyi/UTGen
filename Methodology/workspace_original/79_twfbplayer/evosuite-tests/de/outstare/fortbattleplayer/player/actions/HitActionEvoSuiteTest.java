/*
 * This file was automatically generated by EvoSuite
 */

package de.outstare.fortbattleplayer.player.actions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.player.actions.HitAction;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class HitActionEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      HitAction hitAction0 = new HitAction((Combatant) null, 1);
      // Undeclared exception!
      try {
        hitAction0.execute();
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test1()  throws Throwable  {
      HitAction hitAction0 = new HitAction((Combatant) null, 1);
      String string0 = hitAction0.toString();
      assertNotNull(string0);
      assertEquals("null hits with 1", string0);
  }
}