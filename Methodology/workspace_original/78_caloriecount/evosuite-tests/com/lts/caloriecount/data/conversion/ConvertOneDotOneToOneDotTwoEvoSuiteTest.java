/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.caloriecount.data.conversion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.application.ApplicationException;
import com.lts.caloriecount.data.conversion.ConvertOneDotOneToOneDotTwo;
import com.lts.xml.simple.SimpleElement;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class ConvertOneDotOneToOneDotTwoEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      SimpleElement simpleElement0 = new SimpleElement("r-a0LmW',a", 522L);
      SimpleElement simpleElement1 = ConvertOneDotOneToOneDotTwo.performConvert(simpleElement0);
      assertEquals(522, simpleElement1.getIntValue());
  }
}