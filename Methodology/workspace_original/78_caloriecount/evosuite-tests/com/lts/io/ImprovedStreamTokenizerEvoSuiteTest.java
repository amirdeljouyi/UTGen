/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.io.ImprovedStreamTokenizer;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class ImprovedStreamTokenizerEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      StringReader stringReader0 = new StringReader("}l}4z}/CKpi> =WZ;");
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) stringReader0, "}l}4z}/CKpi> =WZ;", "}l}4z}/CKpi> =WZ;", false);
      try {
        improvedStreamTokenizer0.nextBoolean();
        fail("Expecting exception: IOException");
      } catch(IOException e) {
        /*
         * non-boolean
         */
      }
  }

  @Test
  public void test1()  throws Throwable  {
      StringReader stringReader0 = new StringReader("");
      LineNumberReader lineNumberReader0 = new LineNumberReader((Reader) stringReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) lineNumberReader0);
      // Undeclared exception!
      try {
        improvedStreamTokenizer0.nextByte();
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }

  @Test
  public void test2()  throws Throwable  {
      StringReader stringReader0 = new StringReader("}l}4z}/CKpi> =WZ;");
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) stringReader0);
      improvedStreamTokenizer0.nextByte();
  }

  @Test
  public void test3()  throws Throwable  {
      char[] charArray0 = new char[18];
      charArray0[0] = 'U';
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0);
      LineNumberReader lineNumberReader0 = new LineNumberReader((Reader) charArrayReader0);
      PushbackReader pushbackReader0 = new PushbackReader((Reader) lineNumberReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) pushbackReader0);
      String string0 = improvedStreamTokenizer0.nextWord();
      assertNull(string0);
  }

  @Test
  public void test4()  throws Throwable  {
      char[] charArray0 = new char[3];
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0);
      LineNumberReader lineNumberReader0 = new LineNumberReader((Reader) charArrayReader0);
      PushbackReader pushbackReader0 = new PushbackReader((Reader) lineNumberReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) pushbackReader0);
      String string0 = "";
      try {
        string0 = improvedStreamTokenizer0.nextWord();
        fail("Expecting exception: IOException");
      } catch(IOException e) {
        /*
         * non-string
         */
      }
  }

  @Test
  public void test5()  throws Throwable  {
      char[] charArray0 = new char[7];
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0);
      LineNumberReader lineNumberReader0 = new LineNumberReader((Reader) charArrayReader0);
      PushbackReader pushbackReader0 = new PushbackReader((Reader) lineNumberReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) pushbackReader0);
      charArrayReader0.read(charArray0);
      String string0 = improvedStreamTokenizer0.nextWord();
      assertNull(string0);
  }

  @Test
  public void test6()  throws Throwable  {
      StringReader stringReader0 = new StringReader(".label.");
      PushbackReader pushbackReader0 = new PushbackReader((Reader) stringReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) pushbackReader0);
      // Undeclared exception!
      try {
        improvedStreamTokenizer0.nextInt();
        fail("Expecting exception: NumberFormatException");
      } catch(NumberFormatException e) {
        /*
         * For input string: \".label.\"
         */
      }
  }

  @Test
  public void test7()  throws Throwable  {
      StringReader stringReader0 = new StringReader(".label.");
      PushbackReader pushbackReader0 = new PushbackReader((Reader) stringReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) pushbackReader0);
      improvedStreamTokenizer0.whiteSpaceCharacters(".label.");
      try {
        improvedStreamTokenizer0.nextInt();
        fail("Expecting exception: IOException");
      } catch(IOException e) {
        /*
         * unexpected end of input
         */
      }
  }

  @Test
  public void test8()  throws Throwable  {
      char[] charArray0 = new char[2];
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0, (int) '\u0000', (int) '9');
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) charArrayReader0);
      try {
        improvedStreamTokenizer0.nextInteger();
        fail("Expecting exception: IOException");
      } catch(IOException e) {
        /*
         * non-number
         */
      }
  }

  @Test
  public void test9()  throws Throwable  {
      char[] charArray0 = new char[7];
      charArray0[0] = 'y';
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0);
      LineNumberReader lineNumberReader0 = new LineNumberReader((Reader) charArrayReader0);
      PushbackReader pushbackReader0 = new PushbackReader((Reader) lineNumberReader0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) pushbackReader0);
      improvedStreamTokenizer0.nextBool();
  }

  @Test
  public void test10()  throws Throwable  {
      char[] charArray0 = new char[3];
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0);
      charArrayReader0.read(charArray0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) charArrayReader0, "root should have nuLl as its parent.", "root should have nuLl as its parent.", true);
      try {
        improvedStreamTokenizer0.nextBool();
        fail("Expecting exception: IOException");
      } catch(IOException e) {
        /*
         * unexpected end of input
         */
      }
  }

  @Test
  public void test11()  throws Throwable  {
      char[] charArray0 = new char[7];
      CharArrayReader charArrayReader0 = new CharArrayReader(charArray0);
      ImprovedStreamTokenizer improvedStreamTokenizer0 = new ImprovedStreamTokenizer((Reader) charArrayReader0);
      try {
        improvedStreamTokenizer0.nextByteObject();
        fail("Expecting exception: IOException");
      } catch(IOException e) {
        /*
         * non-byte
         */
      }
  }

  @Test
  public void test12()  throws Throwable  {
      byte byte0 = ImprovedStreamTokenizer.charToHex('&');
      assertEquals((byte) (-17), byte0);
  }

  @Test
  public void test13()  throws Throwable  {
      byte byte0 = ImprovedStreamTokenizer.charToHex('3');
      assertEquals((byte)3, byte0);
  }

  @Test
  public void test14()  throws Throwable  {
      byte byte0 = ImprovedStreamTokenizer.charToHex('X');
      assertEquals((byte)33, byte0);
  }
}