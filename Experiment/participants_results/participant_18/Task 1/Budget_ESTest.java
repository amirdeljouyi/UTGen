package com.lts.caloriecount.data.budget;

import org.evosuite.runtime.EvoRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Timeout;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(
        mockJVMNonDeterminism = true,
        useVFS = true,
        useVNET = true,
        resetStaticState = true,
        separateClassLoader = true)
public class Budget_ESTest extends Budget_ESTest_scaffolding {

      /**   To mock current time use: **/
//    Clock fixedClock = Clock.fixed(Instant.parse("2024-02-22T10:15:30.00Z"), ZoneId.of("UTC"));
//    Budget budget = new Budget(fixedClock);


    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetDailyAmount()  throws Throwable  {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(3116.56717795207);
        TimeOfDay timeOfDay0 = budget0.getEndOfDay();
        timeOfDay0.now();
        int int0 = budget0.getDailyAmount();
        assertEquals(3116.56717795207, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(46852, int0);
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
        timeOfDay0.initialize((-1L));
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
        Clock fixedClock = Clock.fixed(Instant.parse("2024-02-22T10:15:30.00Z"), ZoneId.of("UTC"));
        Budget budget0 = new Budget(fixedClock);
        budget0.setCaloriesPerHour(10.0);
        int int0 = budget0.getBudgetUpToNow();
        assertEquals(10.0, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(0, int0);
    }

    @Test
    @Timeout(value = 4000 , unit = TimeUnit.MILLISECONDS)
    public void testGetBudgetUpToNowReturningPositive()  throws Throwable  {
        Clock fixedClock = Clock.fixed(Instant.parse("2024-02-22T10:15:30.00Z"), ZoneId.of("UTC"));
        Budget budget = new Budget(fixedClock);
        budget.setCaloriesPerHour(1951.56182265);
        TimeOfDay timeOfDay0 = new TimeOfDay();
        budget.setStartOfDay(timeOfDay0);
        int int0 = budget.getBudgetUpToNow();
        assertEquals(1951.56182265, budget.getCaloriesPerHour(), 0.01);
        assertEquals(20003, int0);
    }

    @Test
    @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
    public void testGetBudgetFromToReturningPositive() throws Throwable {
        Budget budget0 = new Budget();
        budget0.setCaloriesPerHour(680.34827537);
        TimeOfDay timeOfDay0 = new TimeOfDay();
        TimeOfDay timeOfDay1 = new TimeOfDay(2797, (-2145752000));
        int int0 = budget0.getBudgetFromTo(timeOfDay1, timeOfDay0);
        assertEquals(680.34827537, budget0.getCaloriesPerHour(), 0.01);
        assertEquals(Integer.MAX_VALUE, int0);
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
