/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Mon Mar 18 01:41:41 GMT 2024
 */

package org.jsecurity.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsecurity.util.AntPathMatcher;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class AntPathMatcher_Original_ESTest extends AntPathMatcher_Original_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testExtractPathWithinPatternAndSetPathSeparator0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("6l)qy,");
      String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("/", "qhf.t|S<}IlYKwiyO");
      assertEquals("6l)qy,YKwi6l)qy,O", extractPathWithinPattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testExtractPathWithinPatternAndSetPathSeparatorAndSetPathSeparatorWithEmptyString() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("");
      String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("I?yXWkrJm!JU~oO=", "XOffYfU!k^AMnP+A1j");
      assertEquals("XOffYfU!k^AMnP+A1j", extractPathWithinPattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testExtractPathWithinPatternAndSetPathSeparator1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("F,9##UFlo>QWUVc");
      String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("/*bV@G<*F$vY", "%hnH:](8F0JU+7V6\"h9");
      assertEquals("F,9##UFlo>QWUVc%hnH:](8F,9##UFlo>QWUVc0JF,9##UFlo>QWUVc6\"h", extractPathWithinPattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testExtractPathWithinPatternReturningEmptyString() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("DXm)7*,", "");
      assertEquals("", extractPathWithinPattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testExtractPathWithinPattern() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("/^Xs0/sd@V{*K", "/^Xs0/sd@V{*K");
      assertEquals("sd@V{*K", extractPathWithinPattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testExtractPathWithinPatternWithEmptyString() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("", ">pYbfWt12TjKxB");
      assertEquals(">pYbfWt12TjKxB", extractPathWithinPattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatch0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean match = antPathMatcher.match("*5?h*QN", "*5?h*QN");
      assertTrue(match);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchStartAndMatchStartReturningFalse0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matchStart = antPathMatcher.matchStart("kC*L*", "kC*/B");
      assertFalse(matchStart);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatch1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean match = antPathMatcher.match("Cu*)A?9y7O4JU", "Cu*)A?9y7O4JU");
      assertTrue(match);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchesAndMatchesReturningTrue0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matches = antPathMatcher.matches("WWd5**", "WWd5");
      assertTrue(matches);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchReturningFalse() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("");
      boolean match = antPathMatcher.match("<G8mh\"w9S4r5u*b!Fk", "Y U.|)]Y U.|)toY U.|)formY U.|)aY U.|)key/valueY U.|)pair");
      assertFalse(match);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchesAndMatchesReturningTrue1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matches = antPathMatcher.matches("Y#k[|?%o=gGyH*", "Y#k[|?%o=gGyH*");
      assertTrue(matches);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchesAndMatchesReturningFalse0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matches = antPathMatcher.matches("kC*[", "k");
      assertFalse(matches);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testDoMatchWithFalse() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("");
      boolean doMatch = antPathMatcher.doMatch("C", "@", false);
      assertFalse(doMatch);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatch2() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean match = antPathMatcher.match("/?4 UBKxM$%wtdc", "/?4 UBKxM$%wtdc");
      assertTrue(match);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testDoMatchAndSetPathSeparator() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("");
      boolean doMatch = antPathMatcher.doMatch("/-!4z0", ".S+oV42E\"-F#EKDtJVj5s=VagQ6q[", true);
      assertFalse(doMatch);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testDoMatchReturningTrue() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean doMatch = antPathMatcher.doMatch("%hnH:](8F0JU+7V6\"h9", "%hnH:](8F0JU+7V6\"h9", false);
      assertTrue(doMatch);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchStartAndMatchStartReturningTrue0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matchStart = antPathMatcher.matchStart("**", "**");
      assertTrue(matchStart);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testDoMatchWithEmptyString() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("");
      boolean doMatch = antPathMatcher.doMatch("", "$JX`a", false);
      assertFalse(doMatch);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchesAndMatchesReturningFalse1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matches = antPathMatcher.matches("/*", "/ ");
      assertFalse(matches);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatch3() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean match = antPathMatcher.match("//**", "/");
      assertTrue(match);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatch4() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean match = antPathMatcher.match("/*/", "/");
      assertTrue(match);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchesAndMatchesReturningFalse2() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matches = antPathMatcher.matches("kC**", "kC*/");
      assertFalse(matches);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchStartAndMatchStartReturningTrue1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matchStart = antPathMatcher.matchStart("/", "/");
      assertTrue(matchStart);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchStartAndMatchStartReturningFalse1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matchStart = antPathMatcher.matchStart("*bt%G<*o$vY", "*");
      assertFalse(matchStart);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testDoMatch() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean doMatch = antPathMatcher.doMatch("/?V1U/`-", "/", true);
      assertFalse(doMatch);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchesAndMatchesReturningFalse3() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matches = antPathMatcher.matches("DNZTgEB!:]IUf", "qhf.t|S<}IlYKwiyO");
      assertFalse(matches);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsPatternReturningFalse() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean pattern = antPathMatcher.isPattern("/");
      assertFalse(pattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsPattern0() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean pattern = antPathMatcher.isPattern("!/BU|gN5?[F}3Dp\")");
      assertTrue(pattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsPattern1() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean pattern = antPathMatcher.isPattern("**");
      assertTrue(pattern);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchStartWithEmptyString() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator("");
      boolean matchStart = antPathMatcher.matchStart("nX1W)tCxEwL)A-P-(7.", "");
      assertTrue(matchStart);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetPathSeparatorWithNull() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      antPathMatcher.setPathSeparator((String) null);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatchStartAndMatchStartReturningTrue2() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean matchStart = antPathMatcher.matchStart("*", "*");
      assertTrue(matchStart);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testMatch5() throws Throwable  {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      boolean match = antPathMatcher.match("**", "**");
      assertTrue(match);
  }
}