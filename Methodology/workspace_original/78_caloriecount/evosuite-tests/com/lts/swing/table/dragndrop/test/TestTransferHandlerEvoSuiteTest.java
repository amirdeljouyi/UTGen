/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.swing.table.dragndrop.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.swing.table.dragndrop.test.DummyTransferable;
import com.lts.swing.table.dragndrop.test.TestTransferHandler;
import java.awt.Component;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.TransferHandler;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class TestTransferHandlerEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      TestTransferHandler testTransferHandler0 = new TestTransferHandler();
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      DummyTransferable dummyTransferable0 = (DummyTransferable)testTransferHandler0.createTransferable((JComponent) jInternalFrame0);
      TransferHandler.TransferSupport transferHandler_TransferSupport0 = new TransferHandler.TransferSupport((Component) jInternalFrame0, (Transferable) dummyTransferable0);
      boolean boolean0 = testTransferHandler0.canImport(transferHandler_TransferSupport0);
      assertEquals(true, boolean0);
  }

  @Test
  public void test1()  throws Throwable  {
      TestTransferHandler testTransferHandler0 = new TestTransferHandler();
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      JLayeredPane jLayeredPane0 = jInternalFrame0.getLayeredPane();
      int int0 = testTransferHandler0.getSourceActions((JComponent) jLayeredPane0);
      assertEquals(3, int0);
  }
}