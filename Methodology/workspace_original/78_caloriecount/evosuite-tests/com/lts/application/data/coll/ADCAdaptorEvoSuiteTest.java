/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.application.data.coll;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationData;
import com.lts.application.data.coll.ADCAdaptor;
import com.lts.application.data.coll.ADCListener;
import com.lts.application.data.coll.ADCListenerAdaptor;
import com.lts.application.data.coll.ApplicationDataCollection;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class ADCAdaptorEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      // Undeclared exception!
      try {
        aDCAdaptor0.update((ApplicationData) aDCAdaptor0);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test1()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      // Undeclared exception!
      try {
        aDCAdaptor0.toArray();
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test2()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      ApplicationDataCollection[] applicationDataCollectionArray0 = new ApplicationDataCollection[10];
      // Undeclared exception!
      try {
        aDCAdaptor0.toArray(applicationDataCollectionArray0);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test3()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      // Undeclared exception!
      try {
        aDCAdaptor0.size();
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test4()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      ADCListenerAdaptor aDCListenerAdaptor0 = new ADCListenerAdaptor();
      // Undeclared exception!
      try {
        aDCAdaptor0.addADCListener((ADCListener) aDCListenerAdaptor0);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test5()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      // Undeclared exception!
      try {
        aDCAdaptor0.removeADCListener((ADCListener) null);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test6()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      // Undeclared exception!
      try {
        aDCAdaptor0.clear();
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test7()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      boolean boolean0 = aDCAdaptor0.isDirty();
      assertEquals(false, boolean0);
  }

  @Test
  public void test8()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      linkedList0.add((ApplicationData) aDCAdaptor0);
      boolean boolean0 = aDCAdaptor0.containsAll((Collection<?>) aDCAdaptor0);
      assertEquals(false, aDCAdaptor0.isDirty());
      assertEquals(true, boolean0);
  }

  @Test
  public void test9()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      aDCAdaptor0.postDeserialize();
      assertEquals(false, aDCAdaptor0.isDirty());
  }

  @Test
  public void test10()  throws Throwable  {
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      linkedList0.add((ApplicationData) aDCAdaptor0);
      // Undeclared exception!
      try {
        aDCAdaptor0.postDeserialize();
        fail("Expecting exception: StackOverflowError");
      } catch(StackOverflowError e) {
      }
  }

  @Test
  public void test11()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      aDCAdaptor0.setDirty(true);
      assertEquals(false, aDCAdaptor0.isDirty());
  }

  @Test
  public void test12()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      ADCAdaptor aDCAdaptor1 = (ADCAdaptor)aDCAdaptor0.deepCopy();
      assertEquals(false, aDCAdaptor0.isDirty());
      assertNotNull(aDCAdaptor1);
  }

  @Test
  public void test13()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ADCAdaptor> linkedList0 = new LinkedList<ADCAdaptor>();
      linkedList0.add(aDCAdaptor0);
      linkedList0.add(aDCAdaptor0);
      LinkedList<ApplicationData> linkedList1 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList1);
      boolean boolean0 = aDCAdaptor0.addAll((Collection<? extends ApplicationData>) linkedList0);
      assertEquals(1, aDCAdaptor0.size());
      assertEquals(true, boolean0);
  }

  @Test
  public void test14()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      aDCAdaptor0.remove((Object) "");
      assertEquals(false, aDCAdaptor0.isDirty());
  }

  @Test
  public void test15()  throws Throwable  {
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      linkedList0.add((ApplicationData) aDCAdaptor0);
      aDCAdaptor0.add((ApplicationData) aDCAdaptor0);
      boolean boolean0 = aDCAdaptor0.removeAll((Collection<?>) linkedList0);
      assertEquals(false, linkedList0.isEmpty());
      assertEquals(true, boolean0);
  }

  @Test
  public void test16()  throws Throwable  {
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      linkedList0.offerFirst((ApplicationData) aDCAdaptor0);
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      linkedList0.add((ApplicationData) aDCAdaptor0);
      LinkedList<DeepCopier> linkedList1 = new LinkedList<DeepCopier>();
      boolean boolean0 = aDCAdaptor0.retainAll((Collection<?>) linkedList1);
      assertEquals(1, aDCAdaptor0.size());
      assertEquals(true, boolean0);
  }

  @Test
  public void test17()  throws Throwable  {
      LinkedList<ApplicationData> linkedList0 = new LinkedList<ApplicationData>();
      ADCAdaptor aDCAdaptor0 = new ADCAdaptor();
      linkedList0.offerFirst((ApplicationData) aDCAdaptor0);
      aDCAdaptor0.initialize((Collection<ApplicationData>) linkedList0);
      boolean boolean0 = aDCAdaptor0.retainAll((Collection<?>) linkedList0);
      assertEquals(1, linkedList0.size());
      assertEquals(false, boolean0);
  }
}