/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.io.IndentingPrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class IndentingPrintWriterEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      indentingPrintWriter0.println((float) 'A');
      assertEquals(true, indentingPrintWriter0.needToPrintPrefix());
      assertEquals(false, indentingPrintWriter0.getSuppressPrefix());
      assertEquals(4, indentingPrintWriter0.getIndentSize());
  }

  @Test
  public void test1()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      indentingPrintWriter0.println(876L);
      assertEquals(true, indentingPrintWriter0.needToPrintPrefix());
      assertEquals(4, indentingPrintWriter0.getIndentSize());
      assertEquals(false, indentingPrintWriter0.suppressPrefix());
  }

  @Test
  public void test2()  throws Throwable  {
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) byteArrayOutputStream0);
      indentingPrintWriter0.println((int) 'D');
      assertEquals("68\n", byteArrayOutputStream0.toString());
      assertEquals(3, byteArrayOutputStream0.size());
  }

  @Test
  public void test3()  throws Throwable  {
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) byteArrayOutputStream0);
      char[] charArray0 = new char[1];
      indentingPrintWriter0.println(charArray0);
      assertEquals(2, byteArrayOutputStream0.size());
      assertEquals("\u0000\n", byteArrayOutputStream0.toString());
  }

  @Test
  public void test4()  throws Throwable  {
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) byteArrayOutputStream0);
      indentingPrintWriter0.println((double) 0);
      assertEquals(4, byteArrayOutputStream0.size());
      assertEquals(true, indentingPrintWriter0.needToPrintPrefix());
  }

  @Test
  public void test5()  throws Throwable  {
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) byteArrayOutputStream0);
      boolean boolean0 = indentingPrintWriter0.getSuppressPrefix();
      assertEquals(false, boolean0);
      assertEquals(true, indentingPrintWriter0.needToPrintPrefix());
      assertEquals(4, indentingPrintWriter0.getIndentSize());
  }

  @Test
  public void test6()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      assertEquals(false, indentingPrintWriter0.getSuppressPrefix());
      
      indentingPrintWriter0.setSuppressPrefix(true);
      indentingPrintWriter0.println(true);
      assertEquals(true, indentingPrintWriter0.getSuppressPrefix());
      assertEquals(true, indentingPrintWriter0.suppressPrefix());
  }

  @Test
  public void test7()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      indentingPrintWriter0.decreaseIndent();
      assertEquals(true, indentingPrintWriter0.needToPrintPrefix());
      assertEquals(false, indentingPrintWriter0.getSuppressPrefix());
      assertEquals(4, indentingPrintWriter0.getIndentSize());
  }

  @Test
  public void test8()  throws Throwable  {
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) byteArrayOutputStream0);
      indentingPrintWriter0.print((Object) null);
      assertEquals("null", byteArrayOutputStream0.toString());
      assertEquals(false, indentingPrintWriter0.needToPrintPrefix());
  }

  @Test
  public void test9()  throws Throwable  {
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) byteArrayOutputStream0);
      indentingPrintWriter0.println((Object) null);
      assertEquals("null\n", byteArrayOutputStream0.toString());
      assertEquals(5, byteArrayOutputStream0.size());
  }

  @Test
  public void test10()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      indentingPrintWriter0.println('\n');
      assertEquals(true, indentingPrintWriter0.needToPrintPrefix());
      assertEquals(false, indentingPrintWriter0.getSuppressPrefix());
      assertEquals(4, indentingPrintWriter0.getIndentSize());
  }

  @Test
  public void test11()  throws Throwable  {
      CharArrayWriter charArrayWriter0 = new CharArrayWriter();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((Writer) charArrayWriter0);
      indentingPrintWriter0.setPrefix((String) null);
      indentingPrintWriter0.println("n");
      assertEquals("n\n", charArrayWriter0.toString());
      assertEquals(2, charArrayWriter0.size());
  }

  @Test
  public void test12()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      indentingPrintWriter0.increaseIndent();
      indentingPrintWriter0.increaseIndent();
      indentingPrintWriter0.decreaseIndent();
      assertEquals("    ", indentingPrintWriter0.getPrefix());
  }

  @Test
  public void test13()  throws Throwable  {
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      IndentingPrintWriter indentingPrintWriter0 = new IndentingPrintWriter((OutputStream) pipedOutputStream0);
      indentingPrintWriter0.print((Object) "\u671D\u9C9C\u6587");
      assertEquals(true, indentingPrintWriter0.checkError());
      assertEquals(4, indentingPrintWriter0.getIndentSize());
  }
}