/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.swing.table;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.swing.table.RowComparator;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class RowComparatorEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      RowComparator rowComparator0 = new RowComparator();
      rowComparator0.setSortOrder(0);
      assertEquals(0, rowComparator0.getSortOrder());
  }

  @Test
  public void test1()  throws Throwable  {
      RowComparator rowComparator0 = new RowComparator();
      int int0 = rowComparator0.getSortOrder();
      assertEquals(0, int0);
  }

  @Test
  public void test2()  throws Throwable  {
      RowComparator rowComparator0 = new RowComparator();
      int int0 = rowComparator0.getSortColumn();
      assertEquals(0, int0);
  }

  @Test
  public void test3()  throws Throwable  {
      RowComparator rowComparator0 = new RowComparator();
      rowComparator0.setSortColumn(0);
      assertEquals(0, rowComparator0.getSortColumn());
  }

  @Test
  public void test4()  throws Throwable  {
      RowComparator rowComparator0 = new RowComparator();
      int int0 = rowComparator0.compare((Object) null, (Object) null);
      assertEquals((-1), int0);
  }
}