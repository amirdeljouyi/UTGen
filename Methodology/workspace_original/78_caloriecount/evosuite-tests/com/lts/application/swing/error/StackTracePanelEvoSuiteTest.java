/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.application.swing.error;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.application.swing.error.StackTracePanel;
import javax.swing.JFrame;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class StackTracePanelEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      StackTracePanel.showException((Throwable) null);
  }

  @Test
  public void test1()  throws Throwable  {
      StackTracePanel stackTracePanel0 = null;
      try {
        stackTracePanel0 = new StackTracePanel((JFrame) null, (Throwable) null, "");
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }
}