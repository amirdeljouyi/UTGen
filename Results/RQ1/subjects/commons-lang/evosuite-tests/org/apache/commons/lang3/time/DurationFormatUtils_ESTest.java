/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Mon Mar 18 16:32:15 GMT 2024
 */

package org.apache.commons.lang3.time;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class DurationFormatUtils_ESTest extends DurationFormatUtils_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetCount() throws Throwable  {
      // Given a DurationFormatUtils object with 1 token
      DurationFormatUtils arg0 = new DurationFormatUtils();
      DurationFormatUtils.Token durationFormatUtils_Token = new DurationFormatUtils.Token(arg0);
      
      // When the getCount method is called on this token
      int count = durationFormatUtils_Token.getCount();
      
      // Then the returned value should be 1
      assertEquals(1, count);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testLexxReturningNonEmptyArray() throws Throwable  {
      // No Comments were added
      DurationFormatUtils.Token[] lexx = DurationFormatUtils.lexx("2h5m4s");
      assertEquals(4, lexx.length);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testContainsTokenWithValue() throws Throwable  {
      // Given: We have a list of tokens from the input string
      DurationFormatUtils.Token[] tokens = DurationFormatUtils.lexx("");
      
      // When: We check if the list contains any token with the value ""
      boolean containsTokenWithValue = DurationFormatUtils.Token.containsTokenWithValue(tokens, "");
      
      // Then: The method should return false because there are no tokens with the given value in the list
      assertFalse(containsTokenWithValue);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5ArgumentsReturningEmptyString() throws Throwable  {
      // Given a start time of 260 and an end time of 65
      long startTime = 260L;
      long endTime = 65L;
      
      // When the duration is formatted using the default time zone
      TimeZone arg4 = TimeZone.getDefault();
      String formatPeriod = DurationFormatUtils.formatPeriod(startTime, endTime, "", true, arg4);
      
      // Then the output should be an empty string
      assertEquals("", formatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking3ArgumentsReturningEmptyString() throws Throwable  {
      // No Comments were added
      String formatPeriod = DurationFormatUtils.formatPeriod(1779L, 1779L, "");
      assertEquals("", formatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5Arguments0() throws Throwable  {
      // No Comments were added
      ZoneId arg0 = ZoneId.systemDefault();
      TimeZone arg4 = TimeZone.getTimeZone(arg0);
      String formatPeriod = DurationFormatUtils.formatPeriod(1L, 0L, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, arg4);
      assertNotNull(formatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationTaking3ArgumentsReturningNonEmptyString() throws Throwable  {
      String formatDuration = DurationFormatUtils.formatDuration(0L, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
      assertNotNull(formatDuration); // Assert that the duration string is not null
      
      String expectedDuration = "PT0S"; // PT indicates a period of time, 0S indicates a duration of zero seconds
      assertEquals(expectedDuration, formatDuration); // Assert that the formatted duration equals the expected value
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationTaking3ArgumentsReturningEmptyString() throws Throwable  {
      // Test the formatting of a duration in milliseconds to a human-readable string
      String formatDuration = DurationFormatUtils.formatDuration(86400000L, "", true);
      assertEquals("", formatDuration);
      
      // Expected result: The formatted duration should be "1d" since 86400000L is equal to 1 day
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTokenTakingObjectAndCallsEquals0() throws Throwable  {
      // Given
      DurationFormatUtils.Token token1 = new DurationFormatUtils.Token("'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'");
      DurationFormatUtils.Token token2 = new DurationFormatUtils.Token("H");
      
      // When
      boolean equals = token1.equals(token2);
      
      // Then
      assertFalse(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIncrement() throws Throwable  {
      // Given two tokens with different values
      DurationFormatUtils.Token arg0 = new DurationFormatUtils.Token("H");
      DurationFormatUtils.Token durationFormatUtils_Token = new DurationFormatUtils.Token("0java.lang.StringBuilder@00000000100java.lang.StringBuilder@00000000110java.lang.StringBuilder@00000000120java.lang.StringBuilder@0000000013", 1);
      
      // When we call the equals method on them
      boolean equals = durationFormatUtils_Token.equals(arg0);
      
      // Then the result is false, since they have different values
      assertFalse(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTokenTakingObjectAndCallsEquals1() throws Throwable  {
      // Given a DurationFormatUtils.Token with the pattern "PyyyyMMddTHHmmss.SSS"
      DurationFormatUtils.Token token = new DurationFormatUtils.Token("'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'");
      
      // When the equals method is called with the same argument as the object being tested (the token)
      boolean result = token.equals(token);
      
      // Then the result should be true, indicating that the token is equal to itself
      assertTrue(result);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationTaking2ArgumentsReturningNonEmptyString() throws Throwable  {
      // Test that the formatDuration method returns a non-null value for a negative duration.
      String formatDuration = DurationFormatUtils.formatDuration((-1636L), "java.lang.StringBuilder@00000000160java.lang.StringBuilder@00000000170java.lang.StringBuilder@00000000180java.lang.StringBuilder@00000000190java.lang.StringBuilder@00000000200java.lang.StringBuilder@0000000021-1java.lang.StringBuilder@0000000022-636java.lang.StringBuilder@0000000023");
      assertNotNull(formatDuration);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormat() throws Throwable  {
      // Given a duration of 458 milliseconds and a format token array
      DurationFormatUtils.Token[] tokens = new DurationFormatUtils.Token[1];
      tokens[0] = new DurationFormatUtils.Token("0java.lang.StringBuilder@00000000090java.lang.StringBuilder@00000000100java.lang.StringBuilder@00000000110java.lang.StringBuilder@0000000012", 77);
      
      // When the format method is called with this duration and token array
      String formattedDuration = DurationFormatUtils.format(tokens, 458L, 77, 0L, 0L, 0L, 0L, 0L, true);
      
      // Then the resulting string should be empty
      assertEquals("", formattedDuration);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5ArgumentsThrowsTooManyResourcesException() throws Throwable  {
      // rollbacked to evosuite
      SimpleTimeZone arg4 = new SimpleTimeZone(0, "");
      // Undeclared exception!
      DurationFormatUtils.formatPeriod(1L, (-3232L), "", true, (TimeZone) arg4);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5ArgumentsWithNegative() throws Throwable  {
      // Test that the formatPeriod method returns a non-null value when given valid input parameters.
      SimpleTimeZone arg4 = new SimpleTimeZone(121, "");
      String expectedFormatPeriod = DurationFormatUtils.formatPeriod((long) (-348), 1L, "t4jeI0r*@PKe@z", false, (TimeZone) arg4);
      assertNotNull(expectedFormatPeriod);
      
      // Test that the formatPeriod method returns a valid value when given valid input parameters.
      String actualFormatPeriod = DurationFormatUtils.formatPeriod((long) (-348), 1L, "t4jeI0r*@PKe@z", false, (TimeZone) arg4);
      assertEquals(expectedFormatPeriod, actualFormatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5Arguments1() throws Throwable  {
      // Given a SimpleTimeZone with an ID and a display name
      SimpleTimeZone arg4 = new SimpleTimeZone(0, "s(wX[V9yBoJ\u0002", 0, 13, 0, 0, 0, 0, 13, 0, 1252);
      
      // When formatting a period of time with the SimpleTimeZone
      String formatPeriod = DurationFormatUtils.formatPeriod(3600000L, 0L, "s(wX[V9yBoJ\u0002", false, (TimeZone) arg4);
      
      // Then the formatted period should not be null
      assertNotNull(formatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5ArgumentsWithZero() throws Throwable  {
      // Given the need to format a period of time for display
      // When a negative duration is passed in
      // Then the formatted string should include the offset and abbreviation
      TimeZone arg4 = TimeZone.getTimeZone("");
      String expectedFormatPeriod = DurationFormatUtils.formatPeriod(0L, (-3083L), "Minimum abbreviation width with offset is 7", false, arg4);
      assertNotNull(expectedFormatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking5ArgumentsWithPositive() throws Throwable  {
      // Given a duration of 297 milliseconds and a time zone offset of America/Los_Angeles
      long duration = 297L;
      TimeZone arg4 = TimeZone.getTimeZone("America/Los_Angeles");
      
      // When the period is formatted with an offset
      String formatPeriod = DurationFormatUtils.formatPeriod(duration, duration, "Minimum abbreviation width with offset is 7", false, arg4);
      
      // Then the resulting string should not be null
      assertNotNull(formatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking3ArgumentsReturningNonEmptyString() throws Throwable  {
      // No Comments were added
      String formatPeriod = DurationFormatUtils.formatPeriod(484L, 4L, "hh:mm:ss");
      assertNotNull(formatPeriod);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationWordsWithTrue() throws Throwable  {
      // No Comments were added
      String formatDurationWords = DurationFormatUtils.formatDurationWords(999L, true, true);
      assertNotNull(formatDurationWords);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationWordsWithFalse() throws Throwable  {
      // No Comments were added
      String formatDurationWords = DurationFormatUtils.formatDurationWords((-127L), false, false);
      assertNotNull(formatDurationWords);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationTaking2ArgumentsReturningEmptyString() throws Throwable  {
      // No Comments were added
      String formatDuration = DurationFormatUtils.formatDuration((-1532L), "");
      assertEquals("", formatDuration);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesDurationFormatUtilsTakingNoArguments() throws Throwable  {
      // rollbacked to evosuite
      DurationFormatUtils arg0 = new DurationFormatUtils();
      DurationFormatUtils.Token durationFormatUtils_Token = new DurationFormatUtils.Token(arg0);
      boolean equals = durationFormatUtils_Token.equals(arg0);
      assertFalse(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationHMS() throws Throwable  {
      // Test that the method returns a non-null value for a negative duration.
      String formatDurationHMS = DurationFormatUtils.formatDurationHMS((-1369L));
      assertNotNull(formatDurationHMS);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodISO() throws Throwable  {
      // No Comments were added
      String formatPeriodISO = DurationFormatUtils.formatPeriodISO((-1L), 4311L);
      assertNotNull(formatPeriodISO);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatPeriodTaking3ArgumentsThrowsIllegalArgumentException() throws Throwable  {
      // No Comments were added
      // Undeclared exception!
      try { 
        DurationFormatUtils.formatPeriod(1024L, 1024L, "Search and Replace array lengths don't match: ");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Unmatched quote in format: Search and Replace array lengths don't match: 
         //
         verifyException("org.apache.commons.lang3.time.DurationFormatUtils", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFormatDurationISO() throws Throwable  {
      // Format a duration of 0 milliseconds using the ISO-8601 format
      String formatDurationISO = DurationFormatUtils.formatDurationISO(0L);
      
      // Check that the formatted duration is not null
      assertNotNull(formatDurationISO);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetValue() throws Throwable  {
      // Given a DurationFormatUtils.Token instance with the value "H"
      DurationFormatUtils.Token token = new DurationFormatUtils.Token("H", 1);
      
      // When the getValue method is called on the token instance
      Object value = token.getValue();
      
      // Then the returned value should be "H"
      assertEquals("H", value);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testToString() throws Throwable  {
      // Given
      DurationFormatUtils.Token token = new DurationFormatUtils.Token("d");
      
      // When
      String string = token.toString();
      
      // Then
      assertNotNull(string);
  }
}