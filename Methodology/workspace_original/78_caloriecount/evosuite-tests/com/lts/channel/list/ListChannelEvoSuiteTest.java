/*
 * This file was automatically generated by EvoSuite
 */

package com.lts.channel.list;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.junit.EvoSuiteRunner;
import static org.junit.Assert.*;
import com.lts.channel.list.ListChannel;
import com.lts.channel.list.ListChannelListener;
import com.lts.channel.list.ListChannelListenerAdaptor;
import java.util.List;
import org.junit.BeforeClass;

@RunWith(EvoSuiteRunner.class)
public class ListChannelEvoSuiteTest {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.Properties.REPLACE_CALLS = true; 
  } 


  @Test
  public void test0()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      ListChannelListenerAdaptor listChannelListenerAdaptor0 = new ListChannelListenerAdaptor();
      boolean boolean0 = listChannel0.removeListener((ListChannelListener) listChannelListenerAdaptor0);
      assertEquals(false, boolean0);
  }

  @Test
  public void test1()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      ListChannelListenerAdaptor listChannelListenerAdaptor0 = new ListChannelListenerAdaptor();
      listChannel0.addListener((ListChannelListener) listChannelListenerAdaptor0);
  }

  @Test
  public void test2()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      List<Object> list0 = listChannel0.getList();
      listChannel0.setList(list0);
      assertEquals(0, list0.size());
  }

  @Test
  public void test3()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      // Undeclared exception!
      try {
        listChannel0.setList((List) null);
        fail("Expecting exception: IllegalArgumentException");
      } catch(IllegalArgumentException e) {
      }
  }

  @Test
  public void test4()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      listChannel0.add((Object) "[]");
      listChannel0.add((Object) "[]");
  }

  @Test
  public void test5()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      listChannel0.add((Object) "[]");
      listChannel0.remove((Object) "[]");
  }

  @Test
  public void test6()  throws Throwable  {
      ListChannel listChannel0 = new ListChannel();
      ListChannelListenerAdaptor listChannelListenerAdaptor0 = new ListChannelListenerAdaptor();
      listChannel0.remove((Object) listChannelListenerAdaptor0);
  }
}