/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Tue Mar 19 17:02:47 GMT 2024
 */

package org.hibernate.search.util.logging.impl;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class LoggerFactory_ESTest_scaffolding {

  @org.junit.jupiter.api.extension.RegisterExtension
  public org.evosuite.runtime.vnet.NonFunctionalRequirementExtension nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementExtension();

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeAll
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "org.hibernate.search.util.logging.impl.LoggerFactory"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.OFF; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @BeforeEach
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @AfterEach
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    /*No java.lang.System property to set*/
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(LoggerFactory_ESTest_scaffolding.class.getClassLoader() ,
      "org.apache.lucene.search.Query",
      "org.jboss.logging.AbstractLoggerProvider",
      "org.hibernate.search.util.logging.impl.Log_$logger",
      "org.hibernate.search.exception.AssertionFailure",
      "org.jboss.logging.LoggerProviders$1",
      "org.apache.lucene.index.CompositeReader",
      "org.jboss.logging.LoggerProvider",
      "org.jboss.logging.Logger",
      "org.apache.lucene.search.SortField$Type",
      "org.apache.lucene.index.CorruptIndexException",
      "org.hibernate.search.exception.SearchException",
      "org.jboss.logging.BasicLogger",
      "org.jboss.logging.Slf4jLoggerProvider",
      "org.jboss.logging.LoggerProviders",
      "org.jboss.logging.Slf4jLocationAwareLogger",
      "org.hibernate.search.backend.spi.DeletionQuery",
      "org.jboss.logging.Slf4jLocationAwareLogger$1",
      "org.hibernate.annotations.common.reflection.XAnnotatedElement",
      "org.apache.lucene.index.DirectoryReader",
      "org.hibernate.search.util.logging.impl.Log",
      "org.jboss.logging.DelegatingBasicLogger",
      "org.jboss.logging.Slf4jLogger",
      "org.hibernate.annotations.common.reflection.XMember",
      "org.hibernate.annotations.common.reflection.XClass",
      "org.jboss.logging.Logger$1",
      "org.jboss.logging.Logger$Level",
      "org.hibernate.search.exception.EmptyQueryException",
      "org.hibernate.search.util.logging.impl.LogCategory",
      "org.hibernate.search.util.logging.impl.LoggerFactory",
      "org.hibernate.search.backend.spi.WorkType",
      "org.apache.lucene.index.BaseCompositeReader",
      "org.apache.lucene.index.IndexReader"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(LoggerFactory_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.hibernate.search.util.logging.impl.LoggerFactory",
      "org.jboss.logging.Logger",
      "org.jboss.logging.Logger$1",
      "org.jboss.logging.DelegatingBasicLogger",
      "org.hibernate.search.util.logging.impl.Log_$logger",
      "org.jboss.logging.LoggerProviders$1",
      "org.jboss.logging.AbstractLoggerProvider",
      "org.jboss.logging.Slf4jLoggerProvider",
      "org.jboss.logging.Slf4jLocationAwareLogger",
      "org.jboss.logging.Logger$Level",
      "org.jboss.logging.Slf4jLocationAwareLogger$1",
      "org.jboss.logging.LoggerProviders",
      "org.apache.lucene.util.AttributeImpl",
      "org.apache.lucene.search.FuzzyTermsEnum$LevenshteinAutomataAttributeImpl"
    );
  }
}