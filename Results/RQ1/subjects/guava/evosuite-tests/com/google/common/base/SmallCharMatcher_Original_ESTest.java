/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Tue Mar 19 15:38:32 GMT 2024
 */

package com.google.common.base;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import com.google.common.base.SmallCharMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class SmallCharMatcher_Original_ESTest extends SmallCharMatcher_Original_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSmearReturningZero() throws Throwable  {
      int smear = SmallCharMatcher.smear(0);
      assertEquals(0, smear);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSmearReturningPositive() throws Throwable  {
      int smear = SmallCharMatcher.smear((-661054404));
      assertEquals(566064808, smear);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSmearReturningNegative() throws Throwable  {
      int smear = SmallCharMatcher.smear((-970));
      assertEquals((-661054404), smear);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testChooseTableSizeReturningZero() throws Throwable  {
      int chooseTableSize = SmallCharMatcher.chooseTableSize((-2511));
      assertEquals(0, chooseTableSize);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testChooseTableSizeReturningPositive() throws Throwable  {
      int chooseTableSize = SmallCharMatcher.chooseTableSize(1);
      assertEquals(2, chooseTableSize);
  }
}