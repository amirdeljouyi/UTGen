/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.swing.table.dragndrop.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.swing.table.dragndrop.test.GetTransferDataFlavorsCalled;
import java.awt.datatransfer.DataFlavor;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class GetTransferDataFlavorsCalledEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      DataFlavor[] dataFlavorArray0 = new DataFlavor[2];
      GetTransferDataFlavorsCalled getTransferDataFlavorsCalled0 = new GetTransferDataFlavorsCalled(dataFlavorArray0);
      StringBuffer stringBuffer0 = new StringBuffer("J3Qxw~o-2dDzjk");
      // Undeclared exception!
      try {
        getTransferDataFlavorsCalled0.appendSubclassToString(stringBuffer0);
        fail("Expecting exception: NullPointerException");
      } catch(NullPointerException e) {
      }
  }
}