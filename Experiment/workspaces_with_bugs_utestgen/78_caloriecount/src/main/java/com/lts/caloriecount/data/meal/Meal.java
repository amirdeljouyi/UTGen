package com.lts.caloriecount.data.meal;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Map;

import com.lts.caloriecount.data.CalorieCountDataElements;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.food.Food;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.xml.simple.SimpleElement;

/**
 * A point in time where the user eats something.
 * 
 * <P>
 * While called a "meal", such an event my actually involve eating only a relatively
 * small amount of calories --- a snack if you will.  Rather than having more than 
 * one class to describe different events that involve differing numbers of calories,
 * this class represents all of them.
 * </P>
 * 
 * <UL>
 * <LI>myFoodId - the ID of the food that this meal references</LI>
 * <LI>myTime - the date and myTime when the meal occurred.</LI>
 * <LI>myFood - <B>note that this member is transient</B> the myFood that 
 * the user consumed during the meal.</LI>
 * </UL>
 * 
 * @author cnh
 *
 */
public class Meal extends Entry implements Comparable
{
	private static final long serialVersionUID = 1L;

	public static final String TAG_MEAL = "meal";
	
	public static final String TAG_FOOD_ID = "foodId";
	
	public static Comparator COMPARATOR = new MealComparator();
	public static Comparator TIME_COMPARATOR = new MealComparator();
	
	public static class MealComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			Meal m1 = (Meal) o1;
			Meal m2 = (Meal) o2;
			
			if (null == m1)
			{
				if (null == m2)
					return 0;
				else
					return -1;
			}
			else if (null == m2)
			{
				if (null == m1)
					return 0;
				else 
					return 1;
			}
			else
			{
				return m1.compareTo(m2);
			}
		}
	}
	
	transient protected Food myFood;
	
	protected int myFoodId;
	
	
	public Meal()
	{}
	
	public Meal(int id, Food food, long time)
	{
		initialize(id, food, time);
	}
	
	public void initialize(int id, Food food, long time)
	{
		myId = id;
		myFoodId = food.getId();
		myFood = food;
		myTime = time;
	}
	
	public int getFoodId()
	{
		return myFoodId;
	}
	
	public void setFoodId(int id)
	{
		myFoodId = id;
	}
	
	
	public int compareTo(Object o)
	{
		Meal meal = (Meal) o;
		if (myTime < meal.myTime)
			return -1;
		else if (myTime > meal.myTime)
			return 1;
		else 
			return 0;
	}
	
	
	public String getEntryName()
	{
		return CalorieCountDataElements.Meal.toString();
	}

	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
		super.deepCopyData(copy, map, copyTransients);
		
		Meal other = (Meal) copy;
		other.myFoodId = myFoodId;
	}

	public Food getFood()
	{
		return myFood;
	}

	public void setFood(Food food)
	{
		this.myFood = food;
		myFoodId = food.getId();
	}

	public int getCalories()
	{
		return myFood.getCalories();
	}


	public void updateFrom(Meal meal)
	{
		myFoodId = meal.myFoodId;
		myFood = meal.myFood;
		myTime = meal.myTime;
	}
	
	
	protected static SimpleDateFormat ourFormat = 
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(myFood);
		sb.append(",");
		sb.append(ourFormat.format(myTime));
		
		return sb.toString();
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		super.deserializeFrom(elem);
		
		setFoodId(elem.getIntValueOfChild(TAG_FOOD_ID));
	}

	@Override
	public void serializeTo(SimpleElement elem)
	{
		Food food = getFood();
		if (null != food)
		{
			elem.createChild("food", food.getName());
		}
		
		super.serializeTo(elem);
		
		elem.createChild(TAG_FOOD_ID, getFoodId());
	}

	@Override
	protected String getSerializationName()
	{
		return TAG_MEAL;
	}

	@Override
	public String getDescription()
	{
		String description = null;
		
		if (null != myFood)
		{
			description = myFood.getName();
		}
		
		return description;
	}
}
