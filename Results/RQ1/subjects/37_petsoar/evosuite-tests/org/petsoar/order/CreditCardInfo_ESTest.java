/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Thu Mar 21 00:34:08 GMT 2024
 */

package org.petsoar.order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;
import org.petsoar.order.CreditCardInfo;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CreditCardInfo_ESTest extends CreditCardInfo_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetExpirationDate() throws Throwable  {
      // Given a CreditCardInfo instance with an expiration date set
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      Date expirationDate = new Date(123456789);
      creditCardInfo.setExpirationDate(expirationDate);
      
      // When the expiration date is retrieved
      Date returnedExpirationDate = creditCardInfo.getExpirationDate();
      
      // Then the correct expiration date should be returned
      assertSame(returnedExpirationDate, expirationDate);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetCreditCardNumberReturningEmptyString() throws Throwable  {
      // Given a CreditCardInfo object with an empty credit card number
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      creditCardInfo.setCreditCardNumber("");
      
      // When we retrieve the credit card number using getCreditCardNumber() method
      String creditCardNumber = creditCardInfo.getCreditCardNumber();
      
      // Then we expect to receive an empty string
      assertEquals("", creditCardNumber);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetCardTypeThrowsIllegalArgumentException() throws Throwable  {
      // Given
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      
      try {
      // When
      creditCardInfo.setCardType("Gold Card");
      
      // Then
      fail("Expecting exception: IllegalArgumentException");
      } catch(IllegalArgumentException e) {
      // Invalid card type
      verifyException("org.petsoar.order.CreditCardInfo", e);}
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetCardTypeWithNonEmptyString() throws Throwable  {
      // Given: A CreditCardInfo object is created with a valid card type "American Express"
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      creditCardInfo.setCardType("American Express");
      
      // When: The getCardType method is called on the CreditCardInfo object
      String actualCardType = creditCardInfo.getCardType();
      
      // Then: The returned card type should be "American Express"
      assertEquals("American Express", actualCardType);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesCreditCardInfo() throws Throwable  {
      // Given
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      
      // When
      creditCardInfo.setCardType("Master Card");
      
      // Then
      assertEquals("Master Card", creditCardInfo.getCardType());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetCardTypeWithNull() throws Throwable  {
      // Create a new CreditCardInfo object with a null card type
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      creditCardInfo.setCardType(null);
      
      // Assert that the card type is set to "Unknown"
      assertEquals("Unknown", creditCardInfo.getCardType());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetCardTypeReturningNonEmptyString() throws Throwable  {
      // Test that the setter method for card type sets the correct value
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      creditCardInfo.setCardType("Visa");
      String cardType = creditCardInfo.getCardType();
      assertEquals("Visa", cardType);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetCardTypeReturningNull() throws Throwable  {
      // rollbacked to evosuite
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      String cardType = creditCardInfo.getCardType();
      assertNull(cardType);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetExpirationDateReturningNull() throws Throwable  {
      // Given: a CreditCardInfo object with no data
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      
      // When: the expiration date is requested
      Date expirationDate = creditCardInfo.getExpirationDate();
      
      // Then: null should be returned as there is no expiration date
      assertNull(expirationDate);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetCreditCardNumberReturningNull() throws Throwable  {
      // Given: A CreditCardInfo object is created and a credit card number is retrieved from it.
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      String creditCardNumber = creditCardInfo.getCreditCardNumber();
      
      // When: The credit card number is checked for nullity.
      assertNull(creditCardNumber);
      
      // Then: The credit card number should be null, as it has not been set yet.
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetCreditCardNumberReturningNonEmptyString() throws Throwable  {
      // Create a new CreditCardInfo object with a valid credit card number
      CreditCardInfo creditCardInfo = new CreditCardInfo();
      creditCardInfo.setCreditCardNumber("1234-5678-9012-3456");
      
      // Get the credit card number from the object
      String creditCardNumber = creditCardInfo.getCreditCardNumber();
      
      // Assert that the returned credit card number is the same as the one set
      assertEquals("1234-5678-9012-3456", creditCardNumber);
  }
}