/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.LTSException;
import com.lts.xml.XMLPropertyUtil;
import java.lang.reflect.Method;
import javax.imageio.metadata.IIOMetadataNode;
import org.junit.BeforeClass;
import org.w3c.dom.Element;

@RunWith(EvoSuiteRunner.class)
public class XMLPropertyUtilEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Boolean boolean0 = Boolean.valueOf(true);
      try {
        xMLPropertyUtil0.setProperty(true, (Object) boolean0, "2", "2");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * The class, java.lang.Boolean, does not have a property named 2
         */
      }
  }

  @Test
  public void test1()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = XMLPropertyUtil.getInstance();
      Class<?> class0 = String.class;
      Method method0 = xMLPropertyUtil0.getWriteMethod((Class) class0, "");
      assertNull(method0);
  }

  @Test
  public void test2()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = XMLPropertyUtil.getInstance();
      xMLPropertyUtil0.writeProperty(false, (Object) "", (Method) null, (Object) "");
  }

  @Test
  public void test3()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      try {
        xMLPropertyUtil0.writeProperty(true, (Object) xMLPropertyUtil0, (Method) null, (Object) xMLPropertyUtil0);
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * java.lang.NullPointerException
         */
      }
  }

  @Test
  public void test4()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = XMLPropertyUtil.getInstance();
      Class<?> class0 = Long.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "3[Q#V&Ew@j!9b");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Error converting the value, 3[Q#V&Ew@j!9b to an instance of java.lang.Long
         */
      }
  }

  @Test
  public void test5()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Short.class;
      Object object0 = xMLPropertyUtil0.convertValue((Class) class0, (String) null);
      assertNull(object0);
  }

  @Test
  public void test6()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = XMLPropertyUtil.getInstance();
      Class<?> class0 = String.class;
      String string0 = (String)xMLPropertyUtil0.convertValue((Class) class0, "");
      assertEquals("", string0);
  }

  @Test
  public void test7()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Integer.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "fffffffffffff927");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Error converting the value, fffffffffffff927 to an instance of java.lang.Integer
         */
      }
  }

  @Test
  public void test8()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Short.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "*u");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Error converting the value, *u to an instance of java.lang.Short
         */
      }
  }

  @Test
  public void test9()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Boolean.class;
      Boolean boolean0 = (Boolean)xMLPropertyUtil0.convertValue((Class) class0, "fjhmX:");
      assertEquals(false, (boolean)boolean0);
  }

  @Test
  public void test10()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Double.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "Error parsing ");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Error converting the value, Error parsing  to an instance of java.lang.Double
         */
      }
  }

  @Test
  public void test11()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Float.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "R");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Error converting the value, R to an instance of java.lang.Float
         */
      }
  }

  @Test
  public void test12()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Byte.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Error converting the value,  to an instance of java.lang.Byte
         */
      }
  }

  @Test
  public void test13()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = Character.class;
      Character character0 = (Character)xMLPropertyUtil0.convertValue((Class) class0, "1662");
      IIOMetadataNode iIOMetadataNode0 = new IIOMetadataNode();
      xMLPropertyUtil0.setProperties((Object) character0, (Element) iIOMetadataNode0);
      assertNull(iIOMetadataNode0.getNodeValue());
  }

  @Test
  public void test14()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Class<?> class0 = XMLPropertyUtil.class;
      try {
        xMLPropertyUtil0.convertValue((Class) class0, "1662");
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * Unsupported type: com.lts.xml.XMLPropertyUtil
         */
      }
  }

  @Test
  public void test15()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      xMLPropertyUtil0.setProperty(true, (Object) null, "&;mO`~+", "&;mO`~+");
  }

  @Test
  public void test16()  throws Throwable  {
      XMLPropertyUtil xMLPropertyUtil0 = new XMLPropertyUtil();
      Integer integer0 = new Integer((-1753));
      xMLPropertyUtil0.setProperty(false, (Object) integer0, "Y", "Y");
      assertEquals(-1753, integer0.intValue());
  }
}