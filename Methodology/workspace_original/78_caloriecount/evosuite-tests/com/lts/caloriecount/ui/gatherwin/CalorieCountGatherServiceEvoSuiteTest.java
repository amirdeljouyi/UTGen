/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.caloriecount.ui.gatherwin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.caloriecount.ui.gatherwin.CalorieCountGatherService;
import com.lts.util.DefaultSharedQueue;
import com.lts.util.SharedQueue;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class CalorieCountGatherServiceEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      CalorieCountGatherService calorieCountGatherService0 = CalorieCountGatherService.getInstance();
      calorieCountGatherService0.calculateNextGatherTime();
  }

  @Test
  public void test1()  throws Throwable  {
      DefaultSharedQueue defaultSharedQueue0 = new DefaultSharedQueue();
      CalorieCountGatherService calorieCountGatherService0 = new CalorieCountGatherService((SharedQueue) defaultSharedQueue0);
      assertNotNull(calorieCountGatherService0);
      
      calorieCountGatherService0.startService();
      assertEquals(5000L, calorieCountGatherService0.getPauseTime());
      assertEquals(600000L, calorieCountGatherService0.calculateNextGatherTime());
      assertEquals(0L, calorieCountGatherService0.getNextGatherTime());
      assertEquals(5000L, calorieCountGatherService0.getMaxPauseTime());
  }

  @Test
  public void test2()  throws Throwable  {
      CalorieCountGatherService calorieCountGatherService0 = CalorieCountGatherService.getInstance();
      assertNotNull(calorieCountGatherService0);
      
      calorieCountGatherService0.setNextGatherTime((-1574L));
      assertEquals((-1574L), calorieCountGatherService0.getNextGatherTime());
  }

  @Test
  public void test3()  throws Throwable  {
      long long0 = CalorieCountGatherService.roundDown(475L, (-1049L));
      assertEquals(0L, long0);
  }

  @Test
  public void test4()  throws Throwable  {
      CalorieCountGatherService calorieCountGatherService0 = CalorieCountGatherService.getInstance();
      assertNotNull(calorieCountGatherService0);
      
      long long0 = calorieCountGatherService0.getNextGatherTime();
      assertEquals(1574L, long0);
  }

  @Test
  public void test5()  throws Throwable  {
      CalorieCountGatherService calorieCountGatherService0 = CalorieCountGatherService.getInstance();
      calorieCountGatherService0.gatherNow();
      assertEquals(5000L, calorieCountGatherService0.getMaxPauseTime());
  }

  @Test
  public void test6()  throws Throwable  {
      CalorieCountGatherService calorieCountGatherService0 = CalorieCountGatherService.getInstance();
      calorieCountGatherService0.nextPollOrNull();
  }

  @Test
  public void test7()  throws Throwable  {
      DefaultSharedQueue defaultSharedQueue0 = new DefaultSharedQueue();
      CalorieCountGatherService calorieCountGatherService0 = new CalorieCountGatherService((SharedQueue) defaultSharedQueue0);
      assertNotNull(calorieCountGatherService0);
      
      calorieCountGatherService0.process();
      assertEquals(5000L, calorieCountGatherService0.getPauseTime());
      assertEquals(0L, calorieCountGatherService0.getNextGatherTime());
      assertEquals(600000L, calorieCountGatherService0.calculateNextGatherTime());
      assertEquals(5000L, calorieCountGatherService0.getMaxPauseTime());
  }
}