/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.beans.SimpleType;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class SimpleTypeEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      Class<?> class0 = String.class;
      SimpleType simpleType0 = SimpleType.toSimpleType(class0);
      assertEquals(8, simpleType0.ordinal());
  }

  @Test
  public void test1()  throws Throwable  {
      Class<?> class0 = SimpleType.class;
      boolean boolean0 = SimpleType.isSimpleType(class0);
      assertEquals(false, boolean0);
  }

  @Test
  public void test2()  throws Throwable  {
      Class<?> class0 = String.class;
      boolean boolean0 = SimpleType.isSimpleType(class0);
      assertEquals(true, boolean0);
  }
}