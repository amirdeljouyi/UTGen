/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.pest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.application.ApplicationException;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.pest.Pest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.evosuite.Properties.SandboxMode;
import org.evosuite.sandbox.Sandbox;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class PestEvoSuiteTest {

  private static ExecutorService executor; 

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
    org.evosuite.Properties.SANDBOX_MODE = SandboxMode.RECOMMENDED; 
    Sandbox.initializeSecurityManagerForSUT(); 
    executor = Executors.newCachedThreadPool(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    executor.shutdownNow(); 
    Sandbox.resetDefaultSecurityManager(); 
  } 

  @Before 
  public void initTestCase(){ 
    Sandbox.goingToExecuteSUTCode(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    Sandbox.doneWithExecutingSUTCode(); 
  } 


  @Test
  public void test0()  throws Throwable  {
      CalorieCount calorieCount0 = (CalorieCount)Pest.getApp();
      calorieCount0.getScheduler();
  }

  @Test
  public void test1()  throws Throwable  {
      CalorieCount calorieCount0 = new CalorieCount();
      boolean boolean0 = calorieCount0.getQuestioning();
      assertEquals(false, boolean0);
  }

  @Test
  public void test2()  throws Throwable  {
      CalorieCount calorieCount0 = (CalorieCount)Pest.getApp();
      assertEquals(false, calorieCount0.isQuestioning());
  }

  @Test
  public void test3()  throws Throwable  {
      boolean boolean0 = Pest.getAlwaysOnTop();
      assertEquals(false, boolean0);
  }

  @Test
  public void test4()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          CalorieCount calorieCount0 = new CalorieCount();
          // Undeclared exception!
          try {
            calorieCount0.checkPoint();
            fail("Expecting exception: SecurityException");
          } catch(SecurityException e) {
            /*
             * Security manager blocks (java.io.FilePermission checkpoints write)
             * java.lang.Thread.getStackTrace(Thread.java:1479)
             * org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             * java.lang.SecurityManager.checkWrite(SecurityManager.java:962)
             * java.io.File.mkdir(File.java:1155)
             * java.io.File.mkdirs(File.java:1184)
             * com.lts.pest.Pest.createCheckPointRepository(Pest.java:118)
             * com.lts.pest.Pest.checkPoint(Pest.java:98)
             * sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
             * sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
             * sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
             * java.lang.reflect.Method.invoke(Method.java:597)
             * org.evosuite.testcase.MethodStatement$1.execute(MethodStatement.java:262)
             * org.evosuite.testcase.AbstractStatement.exceptionHandler(AbstractStatement.java:142)
             * org.evosuite.testcase.MethodStatement.execute(MethodStatement.java:217)
             * org.evosuite.testcase.TestRunnable.call(TestRunnable.java:291)
             * org.evosuite.testcase.TestRunnable.call(TestRunnable.java:44)
             * java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:303)
             * java.util.concurrent.FutureTask.run(FutureTask.java:138)
             * java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
             * java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
             * java.lang.Thread.run(Thread.java:662)
             */
          }
        } catch(Throwable t) {
            // Need to catch declared exceptions
        }
      } 
    }); 
    future.get(6000, TimeUnit.MILLISECONDS); 
  }

  @Test
  public void test5()  throws Throwable  {
      CalorieCount calorieCount0 = (CalorieCount)Pest.getApp();
      calorieCount0.processCheckPoints();
      assertEquals(false, calorieCount0.dataIsDirty());
  }

  @Test
  public void test6()  throws Throwable  {
      CalorieCount calorieCount0 = (CalorieCount)Pest.getApp();
      assertNotNull(calorieCount0);
      
      boolean boolean0 = calorieCount0.checkPointPresent();
      assertEquals(false, boolean0);
  }
}