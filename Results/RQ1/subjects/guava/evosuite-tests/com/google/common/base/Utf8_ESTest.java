/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Tue Mar 19 15:58:11 GMT 2024
 */

package com.google.common.base;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Utf8;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Utf8_ESTest extends Utf8_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEncodedLength() throws Throwable  {
      // This test verifies that the encoded length of a char buffer with a position equal to its limit is 0.
      
      CharBuffer arg0 = CharBuffer.allocate(1496);
      arg0.position(1496);
      
      int encodedLength = Utf8.encodedLength(arg0);
      
      assertEquals(0, encodedLength);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTaking3ArgumentsReturningTrue() throws Throwable  {
      // Given a byte array containing only ASCII characters
      byte[] arg0 = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f' };
      
      // When we check if the byte array is well-formed UTF-8 encoded
      boolean wellFormed = Utf8.isWellFormed(arg0, (int) (byte)1, 1);
      
      // Then it should be considered well-formed
      assertTrue(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningTrue0() throws Throwable  {
      // Given an array of bytes containing a valid UTF-8 sequence
      byte[] arg0 = new byte[7];
      arg0[0] = (byte) -13;
      arg0[2] = (byte) -112;
      arg0[3] = (byte) -112;
      arg0[1] = (byte) -112;
      
      // When the isWellFormed method is called with the array of bytes
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the method should return true, indicating that the sequence is well-formed
      assertTrue(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse0() throws Throwable  {
      // Given a byte array with two UTF-8 encoded characters that are invalid
      byte[] arg0 = new byte[9];
      arg0[0] = (byte) (-13);
      arg0[1] = (byte) (-112);
      arg0[2] = (byte) (-112);
      
      // When the method is called on this array with an invalid UTF-8 character
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the method returns false, indicating that the array contains an invalid character
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse1() throws Throwable  {
      // Given a byte array with negative values
      byte[] arg0 = new byte[7];
      arg0[0] = (byte) (-6);
      arg0[1] = (byte) (-65);
      
      // When the method is called with this byte array as an argument
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the result should be false, indicating that the byte array is not well-formed
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse2() throws Throwable  {
      // Given a byte array with two bytes, arg0
      byte[] arg0 = new byte[7];
      arg0[0] = (byte) (-13);
      arg0[1] = (byte) (-112);
      
      // When the method Utf8.isWellFormed is called on the byte array
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the result should be false, as the byte array contains an invalid UTF-8 sequence
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse3() throws Throwable  {
      // Given a byte array of length 7, where the first element is -13 (which is an invalid UTF-8 sequence)
      byte[] arg0 = new byte[7];
      arg0[0] = (byte) (-13);
      
      // When the Utf8.isWellFormed method is called with this byte array as argument
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the result should be false, indicating that the byte array is not a valid UTF-8 sequence
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningTrue1() throws Throwable  {
      // No Comments were added
      byte[] arg0 = new byte[4];
      arg0[0] = (byte) (-23);
      arg0[1] = (byte) (-112);
      arg0[2] = (byte) (-112);
      boolean wellFormed = Utf8.isWellFormed(arg0);
      assertTrue(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse4() throws Throwable  {
      // Test that a byte array with invalid UTF-8 characters returns false for isWellFormed()
      byte[] arg0 = new byte[3];
      arg0[0] = (byte) (-19); // Set the first element of the byte array to an invalid UTF-8 character (-19)
      arg0[1] = (byte) (-108); // Set the second element of the byte array to an invalid UTF-8 character (-108)
      boolean wellFormed = Utf8.isWellFormed(arg0); // Call isWellFormed() on the byte array, which should return false
      assertFalse(wellFormed); // Assert that the returned value is false, as expected
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse5() throws Throwable  {
      // Given: A byte array with two elements, where the first element is -32 and the second element is -80
      byte[] arg0 = new byte[9];
      arg0[0] = (byte) (-32);
      arg0[1] = (byte) (-80);
      
      // When: The isWellFormed method is called with the above array as input
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then: The method returns false, indicating that the array is not a valid UTF-8 sequence
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse6() throws Throwable  {
      // Given an array of bytes, we want to check if it is well-formed according to UTF-8 encoding rules
      byte[] inputBytes = new byte[9];
      inputBytes[0] = (byte) (-32);
      inputBytes[1] = (byte) (-107);
      
      // When the method Utf8.isWellFormed is called with this array of bytes
      boolean wellFormed = Utf8.isWellFormed(inputBytes);
      
      // Then we expect it to return false, since the array contains a malformed UTF-8 sequence
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse7() throws Throwable  {
      // Given a byte array containing two bytes with the value -19 and -84, respectively
      byte[] arg0 = new byte[8];
      arg0[0] = (byte) (-19);
      arg0[1] = (byte) (-84);
      
      // When we check if the byte array is well-formed with the Utf8.isWellFormed method
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the result should be false, since the byte array contains a negative value that is not a valid UTF-8 character
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse8() throws Throwable  {
      // Given a byte array of length 3 with the third element set to -23, which is an invalid UTF-8 sequence.
      byte[] arg0 = new byte[3];
      arg0[2] = (byte) (-23);
      
      // When the method Utf8.isWellFormed(arg0) is called.
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the method should return false, indicating that the byte array is not a valid UTF-8 sequence.
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse9() throws Throwable  {
      // Given a byte array with invalid UTF-8 encoding
      byte[] arg0 = new byte[3];
      arg0[1] = (byte) 255;
      
      // When the isWellFormed method is called with this byte array
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the result should be false, indicating that the byte array is not a well-formed UTF-8 sequence
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse10() throws Throwable  {
      // No Comments were added
      byte[] arg0 = new byte[4];
      arg0[0] = (byte) 206;
      boolean wellFormed = Utf8.isWellFormed(arg0);
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTaking3ArgumentsReturningFalse() throws Throwable  {
      // Create an array of bytes that represents a UTF-8 encoded string.
      byte[] arg0 = new byte[4];
      arg0[0] = (byte) (-117); // Set the first byte to a negative value, which is not valid in UTF-8 encoding.
      boolean wellFormed = Utf8.isWellFormed(arg0, 0, 1); // Check if the string is well-formed using the Utf8 class's isWellFormed method.
      assertFalse(wellFormed); // Assert that the string is not well-formed.
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse11() throws Throwable  {
      // Given: A byte array containing an invalid UTF-8 sequence
      byte[] arg0 = new byte[8];
      arg0[0] = (byte) (-19);
      
      // When: The isWellFormed method is called with the byte array
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then: The method should return false, indicating that the sequence is not well-formed
      assertFalse(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningTrue2() throws Throwable  {
      // Create an array of bytes that represents a valid UTF-8 sequence
      byte[] arg0 = new byte[8];
      arg0[1] = (byte) (-84);
      arg0[0] = (byte) 194;
      
      // Invoke the isWellFormed method on the array
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Assert that the method returns true, indicating that the sequence is well-formed
      assertTrue(wellFormed);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIsWellFormedTakingByteArrayReturningFalse12() throws Throwable  {
      // Given a byte array with a single element that contains an invalid UTF-8 sequence
      byte[] arg0 = new byte[3];
      arg0[1] = (byte) (-112);
      
      // When the isWellFormed method is called on this byte array
      boolean wellFormed = Utf8.isWellFormed(arg0);
      
      // Then the method should return false, indicating that the byte array does not contain a valid UTF-8 sequence
      assertFalse(wellFormed);
  }
}