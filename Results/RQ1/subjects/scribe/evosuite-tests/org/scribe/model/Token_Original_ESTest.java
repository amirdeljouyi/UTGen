/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Wed Mar 20 11:36:52 GMT 2024
 */

package org.scribe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.scribe.model.Token;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Token_Original_ESTest extends Token_Original_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetSecretReturningNull() throws Throwable  {
      Token token = new Token((String) null, (String) null, (String) null);
      String secret = token.getSecret();
      assertNull(secret);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetSecretReturningNonEmptyString() throws Throwable  {
      Token token = new Token("h1I:VF' ", ";yS=*v", "8{5$$\"1NL6Qx'o*T'");
      String secret = token.getSecret();
      assertEquals(";yS=*v", secret);
      assertEquals("h1I:VF' ", token.getToken());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetRawResponseReturningNonEmptyString() throws Throwable  {
      Token token = new Token("h1I:VF' ", ";yS=*v", "8{5$$\"1NL6Qx'o*T'");
      String rawResponse = token.getRawResponse();
      assertEquals("h1I:VF' ", token.getToken());
      assertEquals(";yS=*v", token.getSecret());
      assertEquals("8{5$$\"1NL6Qx'o*T'", rawResponse);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetRawResponseReturningEmptyString() throws Throwable  {
      Token token = new Token("", "", "");
      String rawResponse = token.getRawResponse();
      assertEquals("", rawResponse);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testToString() throws Throwable  {
      Token token = new Token((String) null, (String) null, (String) null);
      String string = token.toString();
      assertEquals("Token[null , null]", string);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetSecretReturningEmptyString() throws Throwable  {
      Token token = new Token("", "", "");
      String secret = token.getSecret();
      assertEquals("", secret);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetRawResponseThrowsIllegalStateException() throws Throwable  {
      Token token = new Token((String) null, (String) null);
      // Undeclared exception!
      try { 
        token.getRawResponse();
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // This token object was not constructed by scribe and does not have a rawResponse
         //
         verifyException("org.scribe.model.Token", e);
      }
  }
}