/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.swing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.swing.SortedListModel;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class SortedListModelEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      SortedListModel sortedListModel0 = new SortedListModel();
      sortedListModel0.setOrder(0);
      assertEquals(0, sortedListModel0.getOrder());
  }

  @Test
  public void test1()  throws Throwable  {
      SortedListModel sortedListModel0 = new SortedListModel();
      sortedListModel0.append("");
      sortedListModel0.append(".error.");
      assertEquals(false, sortedListModel0.isEmpty());
      assertEquals(2, sortedListModel0.getSize());
  }
}