/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Mon Mar 18 11:44:15 GMT 2024
 */

package org.apache.commons.collections.primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections.primitives.ArrayByteList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class ArrayByteList_Original_ESTest extends ArrayByteList_Original_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSizeReturningZero() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      int size = arrayByteList.size();
      assertEquals(0, size);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSizeReturningPositive() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.add(0, (byte) (-24));
      int size = arrayByteList.size();
      assertEquals(1, size);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetReturningZero() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList(0);
      arrayByteList.add(0, (byte)0);
      byte set = arrayByteList.set((byte)0, (byte)0);
      assertEquals((byte)0, set);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetReturningPositive() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.add(0, (byte)26);
      byte set = arrayByteList.set(0, (byte)26);
      assertEquals((byte)26, set);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetReturningNegative() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList(0);
      arrayByteList.add(0, (byte) (-1));
      assertEquals(1, arrayByteList.size());
      
      arrayByteList.add(0, (byte)1);
      byte set = arrayByteList.set(1, (byte) (-1));
      assertEquals((byte) (-1), set);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testRemoveElementAtReturningNegative() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.add(0, (byte) (-24));
      byte removeElementAt = arrayByteList.removeElementAt(0);
      assertEquals(0, arrayByteList.size());
      assertEquals((byte) (-24), removeElementAt);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetReturningZero() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList(0);
      arrayByteList.add(0, (byte)0);
      byte get = arrayByteList.get(0);
      assertEquals(1, arrayByteList.size());
      assertEquals((byte)0, get);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetReturningPositive() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList(0);
      arrayByteList.add(0, (byte)2);
      byte get = arrayByteList.get(0);
      assertEquals(1, arrayByteList.size());
      assertEquals((byte)2, get);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetReturningNegative() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.add((byte) (-10));
      byte get = arrayByteList.get(0);
      assertEquals(1, arrayByteList.size());
      assertEquals((byte) (-10), get);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEnsureCapacityWithZero() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.ensureCapacity(0);
      assertEquals(0, arrayByteList.size());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddWithPositive() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList(0);
      // Undeclared exception!
      try { 
        arrayByteList.add((int) (byte)4, (byte)4);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // Should be at least 0 and at most 0, found 4
         //
         verifyException("org.apache.commons.collections.primitives.ArrayByteList", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testTrimToSize() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.trimToSize();
      arrayByteList.trimToSize();
      assertEquals(0, arrayByteList.size());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesArrayByteListTakingInt() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList(0);
      arrayByteList.ensureCapacity((byte)1);
      assertEquals(0, arrayByteList.size());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesArrayByteListTakingNoArguments() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.ensureCapacity(4693);
      assertEquals(0, arrayByteList.size());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testRemoveElementAtReturningPositive() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.add((byte) (-76));
      arrayByteList.add(0, (byte)2);
      byte removeElementAt = arrayByteList.removeElementAt(0);
      assertEquals(1, arrayByteList.size());
      assertEquals((byte)2, removeElementAt);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testRemoveElementAtReturningZero() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      arrayByteList.add((byte)0);
      assertEquals(1, arrayByteList.size());
      
      byte removeElementAt = arrayByteList.removeElementAt((byte)0);
      assertEquals((byte)0, removeElementAt);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testFailsToCreateArrayByteListTakingIntThrowsIllegalArgumentException() throws Throwable  {
      ArrayByteList arrayByteList = null;
      try {
        arrayByteList = new ArrayByteList((-5));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // capacity -5
         //
         verifyException("org.apache.commons.collections.primitives.ArrayByteList", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetThrowsIndexOutOfBoundsException() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      // Undeclared exception!
      try { 
        arrayByteList.get((byte) (-24));
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // Should be at least 0 and less than 0, found -24
         //
         verifyException("org.apache.commons.collections.primitives.ArrayByteList", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetThrowsIndexOutOfBoundsException() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      // Undeclared exception!
      try { 
        arrayByteList.set(0, (byte)26);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // Should be at least 0 and less than 0, found 0
         //
         verifyException("org.apache.commons.collections.primitives.ArrayByteList", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testAddWithNegative() throws Throwable  {
      ArrayByteList arrayByteList = new ArrayByteList();
      // Undeclared exception!
      try { 
        arrayByteList.add((-984), (byte)116);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // Should be at least 0 and at most 0, found -984
         //
         verifyException("org.apache.commons.collections.primitives.ArrayByteList", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesArrayByteListTakingByteCollection() throws Throwable  {
      ArrayByteList arg0 = new ArrayByteList();
      ArrayByteList arrayByteList = new ArrayByteList(arg0);
      assertEquals(0, arg0.size());
      assertTrue(arrayByteList.equals((Object)arg0));
  }
}