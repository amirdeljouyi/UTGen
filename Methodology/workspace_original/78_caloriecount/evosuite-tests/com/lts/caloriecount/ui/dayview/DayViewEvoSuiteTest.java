/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.caloriecount.ui.dayview;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.LTSException;
import com.lts.caloriecount.ui.dayview.DayView;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class DayViewEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      DayView dayView0 = null;
      try {
        dayView0 = new DayView((JFrame) null);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test1()  throws Throwable  {
      // Undeclared exception!
      try {
        DayView.displayWindow();
        fail("Expecting exception: HeadlessException");
      } catch(HeadlessException e) {
      }
  }
}