/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Thu Mar 21 05:29:46 GMT 2024
 */

package bible.obj;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import bible.obj.Book;
import bible.obj.Reference;
import bible.obj.Translation;
import bible.obj.TranslationReference;
import bible.obj.Verse;
import java.util.ArrayList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Verse_Original_ESTest extends Verse_Original_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetText() throws Throwable  {
      Verse New = Verse.New(0);
      String text = New.getText();
      assertNull(text);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetIdTaking4Ints() throws Throwable  {
      int GetId = Verse.GetId((-87), 0, 0, 4910);
      assertEquals((-1), GetId);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTaking3ArgumentsWithNonEmptyArray() throws Throwable  {
      Translation[] arg0 = new Translation[4];
      // Undeclared exception!
      try { 
        Verse.Get(arg0, (Reference) null, (Reference) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bible.util.Util", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTaking3ArgumentsWithEmptyArray() throws Throwable  {
      Translation[] arg0 = new Translation[0];
      // Undeclared exception!
      try { 
        Verse.Get(arg0, (Reference) null, (Reference) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bible.obj.Verse", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTaking3Arguments() throws Throwable  {
      Reference arg2 = new Reference(3157, 2590, (-1485));
      ArrayList Get = Verse.Get((Translation[]) null, arg2, arg2);
      assertEquals(0, Get.size());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTaking4ArgumentsThrowsNullPointerException() throws Throwable  {
      Translation[] arg0 = new Translation[20];
      // Undeclared exception!
      try { 
        Verse.Get(arg0, (-1), (-1), (-1));
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bible.obj.Verse", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTaking4Arguments() throws Throwable  {
      Translation[] arg0 = new Translation[0];
      ArrayList Get = Verse.Get(arg0, 3716, 3716, 3716);
      assertEquals(0, Get.size());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetId() throws Throwable  {
      Verse New = Verse.New((-1414));
      int id = New.getId();
      assertEquals(0, id);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTranslationReference() throws Throwable  {
      Verse New = Verse.New((-1));
      TranslationReference translationReference = New.getTranslationReference();
      assertNull(translationReference);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetValue() throws Throwable  {
      Verse New = Verse.New((-1));
      String value = New.getValue();
      assertNull(value);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testToString() throws Throwable  {
      Verse New = Verse.New(4910);
      assertNotNull(New);
      
      String string = New.toString();
      assertEquals("bible.obj.Verse:[0 null null]", string);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetIdTaking11And2IntsThrowsNullPointerException() throws Throwable  {
      // Undeclared exception!
      try { 
        Verse.GetId((Translation) null, (Book) null, 10, 66);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bible.obj.Verse", e);
      }
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetReferenceThrowsNullPointerException() throws Throwable  {
      Verse New = Verse.New((-1));
      // Undeclared exception!
      try { 
        New.getReference();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bible.obj.Verse", e);
      }
  }
}