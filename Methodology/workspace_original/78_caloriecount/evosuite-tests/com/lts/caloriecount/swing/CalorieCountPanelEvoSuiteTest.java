/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.caloriecount.swing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.caloriecount.ui.mealview.MealWindow;
import javax.swing.JFrame;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class CalorieCountPanelEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      MealWindow mealWindow0 = null;
      try {
        mealWindow0 = new MealWindow((JFrame) null);
        fail("Expecting exception: IllegalArgumentException");
      } catch(IllegalArgumentException e) {
      }
  }
}