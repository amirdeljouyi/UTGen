/*
 * This file was automatically generated by UTestGen and EvoSuite
 * Wed Mar 20 11:51:11 GMT 2024
 */

package org.tartarus.snowball.ext;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.tartarus.snowball.ext.turkishStemmer;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class turkishStemmer_Original_ESTest extends turkishStemmer_Original_ESTest_scaffolding {

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEqualsReturningFalse() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("=&)A");
      boolean equals = _turkishStemmer.equals((Object) null);
      assertFalse(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testStemReturningFalse() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("soyad");
      boolean stem = _turkishStemmer.stem();
      assertFalse(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testStemReturningTrue() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("1soyad");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer0() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("org.tartarus.snowball.Among");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer1() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("ousm+wi)Ol7ng");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer2() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("oL&trus.ncwbllAmng");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer3() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("u|WoBfR6^zb");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer4() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("ca\u0131na");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer5() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("asa");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer6() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("Doad");
      _turkishStemmer.stem();
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer7() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("0b4htW+#g,");
      _turkishStemmer.setCurrent("s\u0131n\u0131z");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer8() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("cas\u0131n");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer9() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("cas\u0131nla");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer10() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("cas\u0131la");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer11() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("uyTta");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer12() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("leri");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer13() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("org.tartarus.snowball.SnowballProgram");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer14() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("A(uum");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer15() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("olya");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer16() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("a-\u0131na");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer17() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("JZrpXRuX~Z$XNsu");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer18() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("a\u0131ca");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer19() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("inin");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer20() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("s\u00FC \u00FCz");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer21() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("d\u00FCn1\u00FCz");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer22() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("soya");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer23() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("faulty slice operation");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer24() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("i)nin");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer25() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("Bg+@NeLZdi");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer26() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("h\u0131ca");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer27() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("org.tartarus.snowball.SnowballStemmer");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer28() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("oaefd");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer29() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("cas\u0131na");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testCreatesTurkishStemmer30() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("(YuYum");
      boolean stem = _turkishStemmer.stem();
      assertTrue(stem);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testEqualsReturningTrue() throws Throwable  {
      turkishStemmer arg0 = new turkishStemmer("+]ej\"dg.Hhvfu tp");
      boolean equals = arg0.equals(arg0);
      assertTrue(equals);
  }

  @Test
  @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
  public void testHashCode() throws Throwable  {
      turkishStemmer _turkishStemmer = new turkishStemmer("inin");
      _turkishStemmer.hashCode();
  }
}