/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Thu Mar 21 04:23:19 GMT 2024
 */

package mygrid.web;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import javax.xml.namespace.QName;
import mygrid.web.ArrayOfJob;
import mygrid.web._AvailableJobsResponse;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class _AvailableJobsResponse_ESTest extends _AvailableJobsResponse_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetAvailableJobsResultReturningNonNull() throws Throwable  {
      // Arrange
      _AvailableJobsResponse availableJobsResponse = new _AvailableJobsResponse();
      ArrayOfJob arg0 = new ArrayOfJob();
      availableJobsResponse.setAvailableJobsResult(arg0);
      
      // Act
      ArrayOfJob availableJobsResult = availableJobsResponse.getAvailableJobsResult();
      
      // Assert
      assertSame(availableJobsResult, arg0);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetAvailableJobsResultReturningNull() throws Throwable  {
      // Set up the test data
      _AvailableJobsResponse __AvailableJobsResponse = new _AvailableJobsResponse();
      ArrayOfJob availableJobsResult = __AvailableJobsResponse.getAvailableJobsResult();
      
      // Assert that the result is null
      assertNull(availableJobsResult);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testSetAvailableJobsResult() throws Throwable  {
      // Given a _AvailableJobsResponse instance with an empty result list
      _AvailableJobsResponse availableJobsResponse = new _AvailableJobsResponse();
      ArrayOfJob arg0 = new ArrayOfJob();
      availableJobsResponse.setAvailableJobsResult(arg0);
      
      // When the hashCode method is called on the instance
      int hashCode = availableJobsResponse.hashCode();
      
      // Then the result should be 0, since the list of jobs is empty
      assertEquals(0, hashCode);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEqualsReturningTrue() throws Throwable  {
      // rollbacked to evosuite
      _AvailableJobsResponse __AvailableJobsResponse = new _AvailableJobsResponse();
      _AvailableJobsResponse arg0 = new _AvailableJobsResponse();
      boolean equals = __AvailableJobsResponse.equals(arg0);
      assertTrue(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEqualsReturningFalse() throws Throwable  {
      // Given the availability of jobs to be matched with applicants
      _AvailableJobsResponse availableJobsResponse = new _AvailableJobsResponse();
      
      // When we call the equals method on the AvailableJobsResponse object
      boolean equals = availableJobsResponse.equals("This is a string");
      
      // Then we expect that the result of the equals method will be false, since the object does not contain a string value
      assertFalse(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEqualsWithNonNull() throws Throwable  {
      // Arrange
      _AvailableJobsResponse availableJobsResponse = new _AvailableJobsResponse();
      
      // Act
      boolean equals = availableJobsResponse.equals(availableJobsResponse);
      
      // Assert
      assertTrue(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testHashCode() throws Throwable  {
      // Create an instance of _AvailableJobsResponse
      _AvailableJobsResponse availableJobsResponse = new _AvailableJobsResponse();
      
      // Call the hashCode method on the created instance
      int hashCode = availableJobsResponse.hashCode();
      
      // Assert that the returned hash code is not equal to 0 (default value)
      assertNotEquals(0, hashCode);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetDeserializer() throws Throwable  {
      // Test that the deserializer for AvailableJobsResponse is not ready to be used
      QName arrayOfContextElement = QName.valueOf("ArrayOfContextElement");
      Class<Object> contextElementClass = Object.class;
      Deserializer deserializer = _AvailableJobsResponse.getDeserializer("fpe>", contextElementClass, arrayOfContextElement);
      assertFalse(deserializer.componentsReady());
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetTypeDesc() throws Throwable  {
      // Given a TypeDesc instance with no attributes
      TypeDesc typeDesc = _AvailableJobsResponse.getTypeDesc();
      
      // When the hasAttributes method is called
      boolean actualResult = typeDesc.hasAttributes();
      
      // Then it should return false
      assertFalse(actualResult);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testGetSerializer() throws Throwable  {
      // Test that the serializer for AvailableJobsResponse is correct
      Class<Object> arg1 = Object.class;
      Serializer serializer = _AvailableJobsResponse.getSerializer("org.apache.axis.encoding.SerializationContext", arg1, (QName) null);
      assertEquals("Axis SAX Mechanism", serializer.getMechanismType());
  }
}