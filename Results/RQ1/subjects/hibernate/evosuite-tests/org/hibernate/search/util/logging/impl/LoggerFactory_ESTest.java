/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Tue Mar 19 17:02:47 GMT 2024
 */

package org.hibernate.search.util.logging.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.hibernate.search.util.logging.impl.Log;
import org.hibernate.search.util.logging.impl.LogCategory;
import org.hibernate.search.util.logging.impl.LoggerFactory;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class LoggerFactory_ESTest extends LoggerFactory_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMakeTakingNoArguments() throws Throwable  {
      // No Comments were added
      Log make = LoggerFactory.make();
      assertNotNull(make);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMakeTakingLogCategory() throws Throwable  {
      // rollbacked to evosuite
      LogCategory arg0 = LogCategory.QUERY;
      Log make = LoggerFactory.make(arg0);
      assertNotNull(make);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMakeTakingClassThrowsIllegalArgumentException() throws Throwable  {
      // This test checks that an exception is thrown when trying to create a logger instance with a non-existent class.
      
      // Create a reference to the Object class, which does not have a corresponding Logger implementation.
      Class<Object> arg0 = Object.class;
      
      // Try to create a logger instance using the Object class as the argument.
      try {
      LoggerFactory.make(arg0);
      
      // If we reach this line, it means that no exception was thrown when trying to create the logger instance, which is unexpected.
      fail("Expecting exception: IllegalArgumentException");
      
      } catch (IllegalArgumentException e) {
      // Verify that the expected exception is thrown and that its message matches the expected one.
      verifyException("org.jboss.logging.Logger$1", e);}
  }
}