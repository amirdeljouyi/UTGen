/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.swing.rootpane;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.exception.NotImplementedException;
import com.lts.swing.rootpane.RootPaneJInternalFrame;
import java.awt.event.WindowListener;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class RootPaneJInternalFrameEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("6]Z~g_7|=}xD", true, true);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      boolean boolean0 = rootPaneJInternalFrame0.supportsSize();
      assertEquals(true, boolean0);
  }

  @Test
  public void test1()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("6]Z~g_7|=}xD", true, true);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      // Undeclared exception!
      try {
        rootPaneJInternalFrame0.getAlwaysOnTop();
        fail("Expecting exception: NotImplementedException");
      } catch(NotImplementedException e) {
      }
  }

  @Test
  public void test2()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("@Bi2[x,Z\"1dH@C'", true, false, false, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      boolean boolean0 = rootPaneJInternalFrame0.supportsAlwaysOnTop();
      assertEquals(false, boolean0);
  }

  @Test
  public void test3()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      boolean boolean0 = rootPaneJInternalFrame0.supportsModal();
      assertEquals(false, boolean0);
  }

  @Test
  public void test4()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      String string0 = rootPaneJInternalFrame0.getTitle();
      assertEquals("", string0);
  }

  @Test
  public void test5()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      boolean boolean0 = rootPaneJInternalFrame0.supportsCloseAction();
      assertEquals(true, boolean0);
  }

  @Test
  public void test6()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("", true, false, true, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      rootPaneJInternalFrame0.setMenuBar((JMenuBar) null);
      assertEquals(true, rootPaneJInternalFrame0.supportsLocation());
  }

  @Test
  public void test7()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      boolean boolean0 = rootPaneJInternalFrame0.supportsWindowListener();
      assertEquals(false, boolean0);
  }

  @Test
  public void test8()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      JPanel jPanel0 = (JPanel)rootPaneJInternalFrame0.getContentPane();
      assertEquals(false, jPanel0.requestFocusInWindow());
  }

  @Test
  public void test9()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("", true, false, true, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      JMenuBar jMenuBar0 = rootPaneJInternalFrame0.getMenuBar();
      assertNull(jMenuBar0);
  }

  @Test
  public void test10()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame((String) null, false, false, false, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      // Undeclared exception!
      try {
        rootPaneJInternalFrame0.setModal(false);
        fail("Expecting exception: NotImplementedException");
      } catch(NotImplementedException e) {
      }
  }

  @Test
  public void test11()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("@Bi2[x,Z\"1dH@C'", true, false, false, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      // Undeclared exception!
      try {
        rootPaneJInternalFrame0.getModal();
        fail("Expecting exception: NotImplementedException");
      } catch(NotImplementedException e) {
      }
  }

  @Test
  public void test12()  throws Throwable  {
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame((JInternalFrame) null);
      boolean boolean0 = rootPaneJInternalFrame0.supportsTitle();
      assertEquals(true, boolean0);
  }

  @Test
  public void test13()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("", true, false, true, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      Integer integer0 = JLayeredPane.POPUP_LAYER;
      rootPaneJInternalFrame0.setCloseAction(integer0);
      assertEquals(300, jInternalFrame0.getDefaultCloseOperation());
  }

  @Test
  public void test14()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("", true, false, true, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      // Undeclared exception!
      try {
        rootPaneJInternalFrame0.removeWindowListener((WindowListener) null);
        fail("Expecting exception: NotImplementedException");
      } catch(NotImplementedException e) {
      }
  }

  @Test
  public void test15()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("?rz'8kI!0");
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      rootPaneJInternalFrame0.setTitle("?rz'8kI!0");
      assertEquals(false, rootPaneJInternalFrame0.getVisible());
  }

  @Test
  public void test16()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      // Undeclared exception!
      try {
        rootPaneJInternalFrame0.setAlwaysOnTop(true);
        fail("Expecting exception: NotImplementedException");
      } catch(NotImplementedException e) {
      }
  }

  @Test
  public void test17()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame();
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      // Undeclared exception!
      try {
        rootPaneJInternalFrame0.addWindowListener((WindowListener) null);
        fail("Expecting exception: NotImplementedException");
      } catch(NotImplementedException e) {
      }
  }

  @Test
  public void test18()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("", true, false, true, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      Integer integer0 = rootPaneJInternalFrame0.getCloseAction();
      assertEquals(2, (int)integer0);
  }

  @Test
  public void test19()  throws Throwable  {
      JInternalFrame jInternalFrame0 = new JInternalFrame("", true, false, true, false);
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame(jInternalFrame0);
      boolean boolean0 = rootPaneJInternalFrame0.supportsLocation();
      assertEquals(true, boolean0);
  }

  @Test
  public void test20()  throws Throwable  {
      RootPaneJInternalFrame rootPaneJInternalFrame0 = new RootPaneJInternalFrame((JInternalFrame) null);
      boolean boolean0 = rootPaneJInternalFrame0.supportsMenuBar();
      assertEquals(true, boolean0);
  }
}