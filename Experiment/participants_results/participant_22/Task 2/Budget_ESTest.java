package com.lts.caloriecount.data.budget;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.evosuite.runtime.EvoRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.evosuite.runtime.EvoAssertions.*;

import com.lts.caloriecount.data.budget.Budget;
import com.lts.caloriecount.data.budget.TimeOfDay;
import com.lts.caloriecount.data.meal.MealList;
import com.lts.xml.simple.SimpleElement;
import org.evosuite.runtime.EvoRunnerJUnit5;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.System;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(
        mockJVMNonDeterminism = true,
        useVFS = true,
        useVNET = true,
        resetStaticState = true,
        separateClassLoader = true)
public class Budget_ESTest extends Budget_ESTest_scaffolding {

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetDailyAmount()  throws Throwable  {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(3116.5);
        TimeOfDay timeOfDay0 = budget0.getEndOfDay();
//        timeOfDay0.now();
//        Clock fixedClock = Clock.fixed(Instant.parse("2024-02-26T10:00:00.00Z"), ZoneId.of("UTC"));
//        Budget budget = new Budget(fixedClock);
        budget0.setStartOfDay(new TimeOfDay(10,00));
        budget0.setEndOfDay(new TimeOfDay(12, 00));
        int int0 = budget0.getDailyAmount();
        assertEquals(3116.5, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(6233, int0);
    }


    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testGetDailyAmountReturningPositive() throws Throwable {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour((-1269.50287));
        TimeOfDay timeOfDay0 = new TimeOfDay((-4302L));
        budget0.setStartOfDay(timeOfDay0);
        int int0 = budget0.getDailyAmount();
        assertEquals(30446, int0);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetDailyAmountReturningNegative()  throws Throwable  {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour((-2579.3375));
        TimeOfDay timeOfDay0 = budget0.getEndOfDay();
        timeOfDay0.initialize((-5L));
        int int0 = budget0.getDailyAmount();
        assertEquals((-2579.3375), budget0.getCaloriesPerHour(), 0.01);
        assertEquals((-61861), int0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testGetDailyAmountReturningZero() throws Throwable {
        Budget budget0 = new Budget();
        int int0 = budget0.getDailyAmount();
        assertEquals(0, int0);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetCaloriesPerHour()  throws Throwable  {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(1253.36254962750);
        double double0 = budget0.getCaloriesPerHour();
        assertEquals(1253.36254962750, double0, 0.01);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetCaloriesPerHourReturningZero()  throws Throwable  {
        Budget budget0 = new Budget();
        double double0 = budget0.getCaloriesPerHour();
        assertEquals(0.0, double0, 0.01);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testReplaceWith() throws Throwable {
        Budget budget0 = new Budget();
        budget0.replaceWith(budget0);
        assertEquals(0.0, budget0.getCaloriesPerHour(), 0.01);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testSetCaloriesPerHour() throws Throwable {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(10.0);
        System.setCurrentTimeMillis(0L);
        int int0 = budget0.getBudgetUpToNow();
        assertEquals(10.0, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(0, int0);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetBudgetUpToNowReturningPositive()  throws Throwable  {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(1951.0);
        int int0 = budget0.getBudgetUpToNow();
        assertEquals(1951.0, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(9755, int0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testGetBudgetFromToReturningPositive() throws Throwable {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(680.0);
        TimeOfDay timeOfDay0 = new TimeOfDay();
        TimeOfDay timeOfDay1 = new TimeOfDay(5, 0);
        int int0 = budget0.getBudgetFromTo(timeOfDay1, timeOfDay0);
        assertEquals(680.0, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(3400, int0);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetBudgetFromToReturningZero()  throws Throwable  {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(3116.56717795207);
        TimeOfDay timeOfDay0 = budget0.getEndOfDay();
        int int0 = budget0.getBudgetFromTo(timeOfDay0, timeOfDay0);
        assertEquals(3116.56717795207, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(0, int0);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetBudgetUpToNowReturningZero()  throws Throwable  {
        Budget budget0 = new Budget();
        int int0 = budget0.getBudgetUpToNow();
        assertEquals(0, int0);
    }
}
