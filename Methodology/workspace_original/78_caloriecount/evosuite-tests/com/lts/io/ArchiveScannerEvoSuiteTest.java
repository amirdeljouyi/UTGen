/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.LTSException;
import com.lts.io.ArchiveScanner;
import com.lts.io.ImprovedFile;
import java.io.File;
import java.io.IOException;
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
public class ArchiveScannerEvoSuiteTest {

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
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          ImprovedFile improvedFile0 = new ImprovedFile("");
          ArchiveScanner archiveScanner0 = new ArchiveScanner(improvedFile0);
          File file0 = improvedFile0.getCanonicalFile();
          archiveScanner0.setBasedir(file0);
          // Undeclared exception!
          try {
            archiveScanner0.scan();
            fail("Expecting exception: SecurityException");
          } catch(SecurityException e) {
            /*
             * Security manager blocks (java.io.FilePermission  write)
             * java.lang.Thread.getStackTrace(Thread.java:1479)
             * org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             * java.lang.SecurityManager.checkWrite(SecurityManager.java:962)
             * java.io.File.mkdir(File.java:1155)
             * java.io.File.mkdirs(File.java:1184)
             * com.lts.io.ImprovedFile.createTempDirectory(ImprovedFile.java:257)
             * com.lts.io.ImprovedFile.createTempDir(ImprovedFile.java:429)
             * com.lts.io.archive.AbstractNestedArchive.initialize(AbstractNestedArchive.java:348)
             * com.lts.io.archive.DefaultNestedArchive.<init>(DefaultNestedArchive.java:75)
             * com.lts.io.ArchiveScanner.scanArchive(ArchiveScanner.java:146)
             * com.lts.io.ArchiveScanner.processArchive(ArchiveScanner.java:170)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:205)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:106)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:106)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:106)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:106)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.DirectoryScanner.scan(DirectoryScanner.java:710)
             * sun.reflect.GeneratedMethodAccessor120.invoke(Unknown Source)
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
  public void test1()  throws Throwable  {
      ImprovedFile improvedFile0 = new ImprovedFile("");
      ArchiveScanner archiveScanner0 = new ArchiveScanner(improvedFile0);
      String[] stringArray0 = new String[3];
      stringArray0[0] = "";
      stringArray0[1] = "";
      stringArray0[2] = "";
      archiveScanner0.setIncludes(stringArray0);
      File file0 = improvedFile0.getCanonicalFile();
      archiveScanner0.setBasedir(file0);
      archiveScanner0.scan();
      String[] stringArray1 = archiveScanner0.getNotIncludedDirectories();
      assertNotSame(stringArray1, stringArray0);
  }

  @Test
  public void test2()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          ImprovedFile improvedFile0 = new ImprovedFile("");
          ArchiveScanner archiveScanner0 = new ArchiveScanner(improvedFile0);
          File file0 = improvedFile0.getCanonicalFile();
          String[] stringArray0 = file0.list();
          archiveScanner0.setBasedir(file0);
          archiveScanner0.setExcludes(stringArray0);
          archiveScanner0.scan();
          // Undeclared exception!
          try {
            archiveScanner0.getNotIncludedFiles();
            fail("Expecting exception: SecurityException");
          } catch(SecurityException e) {
            /*
             * Security manager blocks (java.io.FilePermission  write)
             * java.lang.Thread.getStackTrace(Thread.java:1479)
             * org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             * java.lang.SecurityManager.checkWrite(SecurityManager.java:962)
             * java.io.File.mkdir(File.java:1155)
             * java.io.File.mkdirs(File.java:1184)
             * com.lts.io.ImprovedFile.createTempDirectory(ImprovedFile.java:257)
             * com.lts.io.ImprovedFile.createTempDir(ImprovedFile.java:429)
             * com.lts.io.archive.AbstractNestedArchive.initialize(AbstractNestedArchive.java:348)
             * com.lts.io.archive.DefaultNestedArchive.<init>(DefaultNestedArchive.java:75)
             * com.lts.io.ArchiveScanner.scanArchive(ArchiveScanner.java:146)
             * com.lts.io.ArchiveScanner.processArchive(ArchiveScanner.java:170)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:205)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:124)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:124)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.ArchiveScanner.processDirectory(ArchiveScanner.java:124)
             * com.lts.io.ArchiveScanner.scandir(ArchiveScanner.java:201)
             * com.lts.io.DirectoryScanner.slowScan(DirectoryScanner.java:732)
             * com.lts.io.DirectoryScanner.getNotIncludedFiles(DirectoryScanner.java:897)
             * sun.reflect.GeneratedMethodAccessor124.invoke(Unknown Source)
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
  public void test3()  throws Throwable  {
      ImprovedFile improvedFile0 = new ImprovedFile("");
      ArchiveScanner archiveScanner0 = new ArchiveScanner(improvedFile0);
      String[] stringArray0 = new String[3];
      stringArray0[0] = "";
      stringArray0[1] = "";
      stringArray0[2] = "";
      archiveScanner0.setIncludes(stringArray0);
      File file0 = improvedFile0.getCanonicalFile();
      archiveScanner0.setBasedir(file0);
      archiveScanner0.scan();
      archiveScanner0.processFile("");
  }

  @Test
  public void test4()  throws Throwable  {
      ImprovedFile improvedFile0 = new ImprovedFile("");
      ArchiveScanner archiveScanner0 = new ArchiveScanner(improvedFile0);
      try {
        archiveScanner0.scandir((File) improvedFile0, "", true);
        fail("Expecting exception: LTSException");
      } catch(LTSException e) {
        /*
         * IO error scanning directory /mnt/fastdata/ac1gf/SF110/dist/78_caloriecount
         */
      }
  }
}