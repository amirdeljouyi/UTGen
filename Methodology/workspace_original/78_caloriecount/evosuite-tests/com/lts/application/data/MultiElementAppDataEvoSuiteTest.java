/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.application.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.MultiElementAppData;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.CalorieCountDataElements;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import java.util.Hashtable;
import java.util.Map;
import org.evosuite.testcase.CodeUnderTestException;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class MultiElementAppDataEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
      assertNotNull(calorieCountData0);
      
      Hashtable<ApplicationDataElement, DeepCopier> hashtable0 = new Hashtable<ApplicationDataElement, DeepCopier>();
      calorieCountData0.continueDeepCopy((Map) hashtable0, false);
      assertEquals(1, hashtable0.size());
      assertEquals(false, hashtable0.isEmpty());
  }

  @Test
  public void test1()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
  }

  @Test
  public void test2()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
      assertNotNull(calorieCountData0);
      
      CalorieCountData calorieCountData1 = (CalorieCountData)calorieCountData0.deepCopy();
      assertNotNull(calorieCountData1);
      assertEquals(false, calorieCountData0.getDirty());
      assertEquals(false, calorieCountData0.isDirty());
  }

  @Test
  public void test3()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
      assertNotNull(calorieCountData0);
      
      CalorieCountData calorieCountData1 = (CalorieCountData)calorieCountData0.deepCopy(false);
      assertEquals(false, calorieCountData0.getDirty());
      assertNotNull(calorieCountData1);
      assertEquals(false, calorieCountData0.isDirty());
  }

  @Test
  public void test4()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
      assertNotNull(calorieCountData0);
      
      boolean boolean0 = calorieCountData0.isDirty();
      assertEquals(false, boolean0);
  }

  @Test
  public void test5()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
      assertNotNull(calorieCountData0);
      
      CalorieCountDataElements[] calorieCountDataElementsArray0 = calorieCountData0.getDataElements();
      assertEquals(false, calorieCountData0.isDirty());
      assertNotNull(calorieCountDataElementsArray0);
      assertEquals(false, calorieCountData0.getDirty());
  }

  @Test
  public void test6()  throws Throwable  {
      CalorieCountData calorieCountData0 = new CalorieCountData();
      assertNotNull(calorieCountData0);
      assertEquals(false, calorieCountData0.getDirty());
      
      calorieCountData0.updateFrom((MultiElementAppData) calorieCountData0);
      assertEquals(true, calorieCountData0.getDirty());
      assertEquals(true, calorieCountData0.isDirty());
  }
}