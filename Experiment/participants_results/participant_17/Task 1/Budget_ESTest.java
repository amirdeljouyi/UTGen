package com.lts.caloriecount.data.budget;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
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

      /**    To mock current time use: **/
//    Clock fixedClock = Clock.fixed(Instant.parse("2024-02-22T10:15:30.00Z"), ZoneId.of("UTC"));
//    Budget budget = new Budget(fixedClock);

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetDailyAmountFromBudget() throws Throwable {
    // Given a Budget with an end of day time and calories per hour set
    Budget budget = new Budget();
    TimeOfDay arg0 = new TimeOfDay(10, 15);
    budget.setEndOfDay(arg0);
    budget.setCaloriesPerHour(1.0);

    // When the daily amount is requested
    int dailyAmount = budget.getDailyAmount();

    // Then the calculated daily amount should match the expected value
    assertEquals(1.0, budget.getCaloriesPerHour(), 0.01);
    assertEquals(10, dailyAmount);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testCreateBudgetAndSetAttributes() throws Throwable {
    // Create a new Budget object
    Budget budget = new Budget();

    // Set the end of day for the budget to -1L
    TimeOfDay endOfDay = budget.getEndOfDay();
    endOfDay.setTime((-1L));

    // Set the calories per hour for the budget and get the daily amount for the budget
    budget.setCaloriesPerHour((-2625.5868));
    int dailyAmount = budget.getDailyAmount();

    // Assert that the calories per hour is -2625.5868 with a tolerance of 0.01
    assertEquals((-2625.5868), budget.getCaloriesPerHour(), 0.01);

    // Assert that the daily amount is -62970
    assertEquals((-62970), dailyAmount);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetDailyAmountInitialValue() throws Throwable {
    // Given a new Budget object is created
    Budget budget = new Budget();

    // When the getDailyAmount method is called
    int dailyAmount = budget.getDailyAmount();

    // Then the initial value of 0 is returned
    assertEquals(0, dailyAmount);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetCaloriesPerHour() throws Throwable {
    Budget budget = new Budget();
    budget.setCaloriesPerHour(1000.0); // Set the calories per hour to 1000.0
    double expectedCaloriesPerHour = 1000.0; // Expected calories per hour is 1000.0
    assertEquals(expectedCaloriesPerHour, budget.getCaloriesPerHour(), 0.01);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetNegativeCaloriesPerHour() throws Throwable {
    // Create a new budget instance with negative calories per hour
    Budget budget = new Budget();
    budget.setCaloriesPerHour(-1.0);

    // Get the calories per hour from the budget
    double caloriesPerHour = budget.getCaloriesPerHour();

    // Assert that the calories per hour is negative and within 0.01 of -1.0
    assertEquals(-1.0, caloriesPerHour, 0.01);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetCaloriesPerHourWithZeroBudget() throws Throwable {
    // Given: A budget object
    Budget budget = new Budget();

    // When: The calories per hour is requested
    double caloriesPerHour = budget.getCaloriesPerHour();

    // Then: The calculated calories per hour should be 0
    assertEquals(0.0, caloriesPerHour, 0.01);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testBudgetCaloriesPerHour() throws Throwable {
    // Given a Budget object with a caloriesPerHour value of 1107.016
        Clock fixedClock = Clock.fixed(Instant.parse("2024-02-22T10:15:30.00Z"), ZoneId.of("UTC"));
    Budget budget = new Budget(fixedClock);
    budget.setCaloriesPerHour(1107.016);

    // Then the budgetUpToNow value should be 11992
    int budgetUpToNow = budget.getBudgetUpToNow();
    assertEquals(1107.016, budget.getCaloriesPerHour(), 0.01);
    assertEquals(11346, budgetUpToNow);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetBudgetUpToNowWithSetCaloriesPerHour() throws Throwable {
    // Given a Budget object with a calories per hour set to 2000.0
        Clock fixedClock = Clock.fixed(Instant.parse("2024-02-22T10:15:30.00Z"), ZoneId.of("UTC"));
    Budget budget = new Budget(fixedClock);
    budget.setCaloriesPerHour(2000.0);

    // When we get the budget up to now
    int budgetUpToNow = budget.getBudgetUpToNow();

    // Then the calories per hour is 2000.0 and the budget up to now is 20500
    assertEquals(2000.0, budget.getCaloriesPerHour(), 0.01);
    assertEquals(20500, budgetUpToNow);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testReplaceWithItself() throws Throwable {
    // Given a budget with no calories per hour
    Budget budget = new Budget();

    // When we replace it with itself
    budget.replaceWith(budget);

    // Then the calories per hour should be 0.0
    assertEquals(0.0, budget.getCaloriesPerHour(), 0.01);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testReplaceWithItselfCorrect() throws Throwable {
    // Given a budget with no calories per hour
    Budget budget = new Budget();
    Budget budget2 = new Budget();
    budget2.setStartOfDay(new TimeOfDay(10,10));

    // When we replace it with itself
    budget.replaceWith(budget2);

    // Then the calories per hour should be 0.0
    assertEquals(0.0, budget.getCaloriesPerHour(), 0.01);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetBudgetUpToNowInitialValue() throws Throwable {
    // Given: A new Budget object is created and the budget up to now is initialized
    Budget budget = new Budget();
    int budgetUpToNow = budget.getBudgetUpToNow();

    // When: The budget up to now is retrieved
    assertEquals(0, budgetUpToNow);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetBudgetFromToMaximumValue() throws Throwable {
    // Set up the Budget object with a specific caloriesPerHour value
    Budget budget = new Budget();
    budget.setCaloriesPerHour(3868.53178849761);

    // Get the start of day for the Budget object
    TimeOfDay startOfDay = budget.getStartOfDay();

    // Create a new TimeOfDay object with specific values for testing purposes
    TimeOfDay arg0 = new TimeOfDay((-323), (-2142156887));

    // Call the getBudgetFromTo method with the specified arguments and assert the result
    int budgetFromTo = budget.getBudgetFromTo(arg0, startOfDay);
    assertEquals(Integer.MAX_VALUE, budgetFromTo);
  }

  @Test
  @Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
  public void testGetStartOfDayAndBudgetFromTo() throws Throwable {
    // Given a Budget object with a set calorie limit per hour
    Budget budget = new Budget();
    budget.setCaloriesPerHour(3868.53178849761);
    budget.postDeserialize();

    // When we get the start of day for this budget
    TimeOfDay startOfDay = budget.getStartOfDay();

    // Then the start of day should be equal to the current time
    assertEquals(startOfDay.toString(), new TimeOfDay().toString());

    // And when we get the budget from and to for this period
    int budgetFromTo = budget.getBudgetFromTo(startOfDay, startOfDay);

    // Then the budget from and to should be equal to the calorie limit per hour
    assertEquals(0, budgetFromTo);
  }
}
