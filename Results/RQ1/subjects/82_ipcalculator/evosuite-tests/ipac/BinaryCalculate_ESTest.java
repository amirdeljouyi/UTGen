/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Thu Mar 21 08:41:57 GMT 2024
 */

package ipac;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import ipac.BinaryCalculate;
import java.awt.HeadlessException;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class BinaryCalculate_ESTest extends BinaryCalculate_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSubBinaryReturningEmptyString() throws Throwable  {
      // No Comments were added
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      String subBinary = binaryCalculate.subBinary("O,", "O,");
      assertEquals("", subBinary);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSubBinaryReturningNonEmptyString() throws Throwable  {
      // Create a new instance of the BinaryCalculate class
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      
      // Perform the subtraction operation on two binary strings
      String subBinary = binaryCalculate.subBinary("1010", "1010");
      
      // Assert that the result is equal to "1111"
      assertEquals("1111", subBinary);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary0() throws Throwable  {
      // Given: a BinaryCalculate instance and two binary strings
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      String binaryString1 = "n/";
      String binaryString2 = "<16;qM.{;)?PuV";
      
      // When: the addBinary method is called with these two strings as arguments
      String result = binaryCalculate.addBinary(binaryString1, binaryString2);
      
      // Then: the result should be "1"
      assertEquals("1", result);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary1() throws Throwable  {
      // Initialize a new instance of the BinaryCalculate class
      BinaryCalculate calculator = new BinaryCalculate();
      
      // Add binary strings "10" and "11" using the addBinary method
      String result = calculator.addBinary("10", "11");
      
      // Assert that the result is "100"
      assertEquals("100", result);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary2() throws Throwable  {
      // Create a new instance of the BinaryCalculate class
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      
      // Perform the addition operation on two binary strings
      String addBinary = binaryCalculate.addBinary("01101100", "01101100");
      
      // Assert that the result is equal to the expected value
      assertEquals("11011000", addBinary);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary3() throws Throwable  {
      // Given
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      String firstBinaryString = ">#NMo0n$}E~j1}'~";
      String secondBinaryString = "&wET1o<X]5[nbwb";
      
      // When
      String addedBinaryString = binaryCalculate.addBinary(firstBinaryString, secondBinaryString);
      
      // Then
      assertEquals("1", addedBinaryString);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinaryReturningEmptyString() throws Throwable  {
      // Arrange
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      
      // Act
      String addBinary = binaryCalculate.addBinary("JdYh/H+00p", "Exit");
      
      // Assert
      assertEquals("", addBinary);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary4() throws Throwable  {
      // Test that the addBinary method correctly adds two binary strings
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      String addBinary = binaryCalculate.addBinary("11111", "00001");
      assertEquals("100000", addBinary);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary5() throws Throwable  {
      // This test case tests the addBinary method of the BinaryCalculate class.
      
      // Set up the test data
      String input1 = "0101";
      String input2 = "0110";
      
      // Create an instance of the BinaryCalculate class
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      
      // Call the addBinary method with the test data
      String result = binaryCalculate.addBinary(input1, input2);
      
      // Verify the results
      assertEquals("1011", result);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary6() throws Throwable  {
      // Create a new instance of the BinaryCalculate class
      BinaryCalculate binaryCalculator = new BinaryCalculate();
      
      // Add two binary numbers
      String sum = binaryCalculator.addBinary("0100101", "1101010");
      
      // Assert that the result is equal to the expected value
      assertEquals("10001111", sum);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddBinary7() throws Throwable  {
      // Create a new instance of the BinaryCalculate class
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      
      // Perform the addition operation on the strings "1" and "00001"
      String addBinary = binaryCalculate.addBinary("1", "00001");
      
      // Assert that the result is equal to "00010"
      assertEquals("00010", addBinary);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testPrefixInPrefixCalculateThrowsHeadlessException() throws Throwable  {
      // Given
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      
      // When
      try {
      binaryCalculate.prefixInPrefixCalculate("110", "110", 0, "3]`_i451~f>1fq2Z");
      } catch(HeadlessException e) {
      
      // Then
      verifyException("java.awt.GraphicsEnvironment", e);}
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testPrefixInPrefixCalculateThrowsTooManyResourcesException() throws Throwable  {
      // rollbacked to evosuite
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      binaryCalculate.MAXPREFIX = 32;
      // Undeclared exception!
      binaryCalculate.prefixInPrefixCalculate("Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 0\nSubnet: \nBinary Subnet: \nBroadcast Address: \nPrefix: /-2\nNetmask: \nBinary Netmask: ", "Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 0\nSubnet: \nBinary Subnet: \nBroadcast Address: \nPrefix: /-2\nNetmask: \nBinary Netmask: ", 32, ".");
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testPrefixInPrefixCalculateThrowsStringIndexOutOfBoundsException() throws Throwable  {
      // No Comments were added
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      // Undeclared exception!
      try { 
        binaryCalculate.prefixInPrefixCalculate("Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 0\nSubnet: \nBinary Subnet: Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 64\nSubnet: \nBinary Subnet: \nBroadcast Address: \nPrefix: /-7\nN\nBroadcast Address: \nPrefix: /125\nNetmask: \nBinary Netmask: 0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "wS9A8cI<t/", 127, "3(~");
        fail("Expecting exception: StringIndexOutOfBoundsException");
      
      } catch(StringIndexOutOfBoundsException e) {
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIPCalculate0() throws Throwable  {
      // Create a new instance of the BinaryCalculate class
      BinaryCalculate calculator = new BinaryCalculate();
      
      // Perform the IP calculation with inputs "0" and "0"
      String result = calculator.IPCalculate("0", "0");
      
      // Assert that the result is as expected
      assertEquals("Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : -1\nSubnet: \nBinary Subnet: \nBroadcast Address: \nPrefix: /0\nNetmask: \nBinary Netmask: ", result);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testPrefixInPrefixCalculateThrowsNoSuchElementException() throws Throwable  {
      // No Comments were added
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      // Undeclared exception!
      try { 
        binaryCalculate.prefixInPrefixCalculate("Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 19,342,813,113,834,066,795,298,816\nSubnet: \nBinary Subnet: Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 0\nSubnet: \nBinary Subnet: \nBroadcast Address: \nPrefix: /-2\nNe\nBroadcast Address: \nPrefix: /0\nNetmask: \nBinary Netmask: 0000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000", "Subnet:", 127, "3(~");
        fail("Expecting exception: NoSuchElementException");
      
      } catch(NoSuchElementException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.StringTokenizer", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIPCalculate1() throws Throwable  {
      // No Comments were added
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      binaryCalculate.MAXPREFIX = 6;
      binaryCalculate.lengthOfToken = 6;
      String IPCalculate = binaryCalculate.IPCalculate("H5K&\"SFaGm60x-& q)p", "H5K&\"SFaGm60x-& q)p");
      assertEquals("Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 128\nSubnet: \nBinary Subnet: H5K&\"S\nBroadcast Address: \nPrefix: /0\nNetmask: \nBinary Netmask: 000000", IPCalculate);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testIPCalculateThrowsStringIndexOutOfBoundsException() throws Throwable  {
      // rollbacked to evosuite
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      binaryCalculate.MAXPREFIX = 128;
      // Undeclared exception!
      try { 
        binaryCalculate.IPCalculate("Y;641U#eE8v", "Total Range:  -- \nUsable Range:  -- \n\nTotal usable IP Addresses : 0\nSubnet: \nBinary Subnet: \nBroadcast Address: \nPrefix: /-2\nNetmask: \nBinary Netmask: ");
        fail("Expecting exception: StringIndexOutOfBoundsException");
      
      } catch(StringIndexOutOfBoundsException e) {
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testPrefixInPrefixCalculateThrowsNumberFormatException() throws Throwable  {
      BinaryCalculate binaryCalculate = new BinaryCalculate();
      try {
      binaryCalculate.prefixInPrefixCalculate("Total Range: 192.168.0.0 - 192.168.255.255 \nUsable Range: 192.168.1.0 - 192.168.1.254 \n\nTotal usable IP Addresses : 65,534 \nSubnet: 192.168.0.0/24 \nBinary Subnet: Total Range: 0000000000000000 - 0000000000000000 \nUsable Range: 0000000000000000 - 0000000000000000 \n\nTotal usable IP Addresses : 254 \nSubnet: 192.168.1.0/24 \nBinary Subnet: Total Range: 0000000000000000 - 0000000000000000 \nUsable Range: 0000000000000000 - 0000000000000000 \n\nTotal usable IP Addresses : 254 \nSubnet: 192.168.255.0/24 \nBinary Subnet: Total Range: 1111111111111111 - 1111111111111111 \nUsable Range: 1111111111111110 - 1111111111111110 \n\nTotal usable IP Addresses : 2 \nSubnet: 192.168.255.255/32 \nBinary Subnet: Total Range: 1111111111111111 - 1111111111111111 \nUsable Range: 1111111111111111 - 1111111111111111 \n\nTotal usable IP Addresses : 0", "wS9A8cI<t/", 127, "3(~");
      } catch (NumberFormatException e) {
      // NumberFormatException is expected
      throw e;}
  }
}