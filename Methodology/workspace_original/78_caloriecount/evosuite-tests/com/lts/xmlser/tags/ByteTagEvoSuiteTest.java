/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.xmlser.tags;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.xmlser.XmlSerializer;
import com.lts.xmlser.tags.ByteTag;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class ByteTagEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      ByteTag byteTag0 = XmlSerializer.BYTE_TAG;
      String string0 = byteTag0.getTagName((Object) null);
      assertEquals("byte", string0);
  }

  @Test
  public void test1()  throws Throwable  {
      ByteTag byteTag0 = new ByteTag();
      // Undeclared exception!
      try {
        byteTag0.toValue(",fz>");
        fail("Expecting exception: NumberFormatException");
      } catch(NumberFormatException e) {
        /*
         * For input string: \",fz>\"
         */
      }
  }

  @Test
  public void test2()  throws Throwable  {
      ByteTag byteTag0 = XmlSerializer.BYTE_TAG;
      String string0 = byteTag0.getTagClassName();
      assertEquals("java.lang.Byte", string0);
      assertNotNull(string0);
  }
}