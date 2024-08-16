package com.lts.caloriecount.data.meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.IdApplicationDataList;
import com.lts.bean.xml.annotations.CollectionProperty;
import com.lts.bean.xml.annotations.IgnoreProperties;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.util.DateUtil;
import com.lts.xml.simple.SimpleElement;

/**
 * A list of meal objects.
 * 
 * <P>
 * In addition to whatever other notifications that this class sends out, it also
 * tracks changes to the list of food objects and relays events based on changes 
 * made there.
 * </P>
 * 
 * @author cnh
 */
@IgnoreProperties({"empty", "dataElements"})
public class MealList extends IdApplicationDataList<Meal>
{
	/**
	 * Rebuild transient data members.
	 * 
	 * <P>
	 * This method should be called prior to using an instance of the class after
	 * deserializing.  The only reason it is not in the {@link #postDeserialize()}
	 * method is that it avoids introducing a direct dependency between this class
	 * and {@link CalorieCountData}. 
	 * </P>
	 * 
	 * @param list The list of foods to use when rebuilding.
	 */
	public void rebuildTransients(FoodList list)
	{
		myIdToListPosition = new HashMap<Integer, Integer>();
		
		for (Meal meal : myList)
		{		
			int foodId = meal.getFoodId();
			Food food = list.getFood(foodId);
			meal.setFood(food);
		}
	}
	
	public MealList(IdApplicationDataList<Meal> list)
	{
		initialize(list);
	}
	
	protected void initialize(IdApplicationDataList<Meal> list)
	{
		super.initialize(list);
		MealListFoodListListener listener = new MealListFoodListListener();
		list.addListener(listener);
	}
	
	
	public void removeMeal(Meal meal)
	{
		TimeComparator comp = new TimeComparator();
		int index = Collections.binarySearch(myList, meal, comp);
		if (index != -1)
			remove(index);
	}
	
	
	private class TimeComparator implements Comparator
	{
		public int compare(Object o1, Object o2)
		{
			Long l1, l2;
			
			if (null == o1 && null == o2)
				return 0;
			else if (null == o2)
				return 1;
			else if (null == o1)
				return -1;
			
			if (o1 instanceof Long)
				l1 = (Long) o1;
			else
			{
				Meal meal = (Meal) o1;
				l1 = meal.getTime();
			}
			
			if (o2 instanceof Long)
				l2 = (Long) o2;
			else
			{
				Meal meal = (Meal) o2;
				l2 = meal.getTime();
			}
			
			return l1.compareTo(l2);
		}
	}
	
	private static final long serialVersionUID = 1L;

	public static final String TAG_MEAL_LIST = "mealList";

	private Comparator myComparator;
	private String myEntryName;
	
	
	public MealList ()
	{
		initialize();
	}
	
	
	
	protected void initialize()
	{
		myComparator = new Meal.MealComparator();
		super.initialize();
	}

	public String getEntryName()
	{
		return myEntryName;
	}


	public List<? extends ApplicationDataElement> getDataElements()
	{
		return myList;
	}


	synchronized public Meal createMeal(long time, Food food)
	{
		Meal meal = new Meal(-1, food, time);
		add(meal);
		return meal;
	}
	
	
	public Meal createMeal(Food food)
	{
		long time = System.currentTimeMillis();
		return createMeal(time, food);
	}


	public int total(long start, long end)
	{
		List list = getDataElements();
		int size = list.size();
		int index;
		
		index = findAtOrAfter(start, list);
		int total = 0;
		
		if (index >= 0)
		{
			long ctime = -1;
			while (index < size && ctime < end)
			{
				Meal meal = (Meal) list.get(index);
				index++;
				
				ctime = meal.getTime();
				total = total + meal.getFood().getCalories();
			}
		}
		
		return total;
	}


	private int findAtOrAfter(long start, List list)
	{
		int size = list.size();
		int index;
		Meal key = new Meal();
		key.setTime(start);
		index = Collections.binarySearch(list, key, myComparator);
		if (index < 0)
		{
			index = (-1 * index) - 1; 
		}
		
		long ctime = -1;
		while (index < size && ctime < start)
		{
			Meal meal = (Meal) list.get(index);
			ctime = meal.getTime();
			index++;
		}
		
		if (ctime < start)
			return -1;
		else 
			return index - 1;
	}

	
	public void setMeals (List list)
	{
		myList = list;
		myHelper.fireAllChanged();
	}

	public int total(long end)
	{
		long start = DateUtil.getStartOfToday();
		return total (start,end);
	}


	public List<Meal> getEntriesFor(long date)
	{
		long start = DateUtil.startOfDay(date);
		long end = DateUtil.startOfNextDay(date);
		return getEntriesFor(start, end);
	}
	
	
	public Meal getFirstEntryAtOrAfter (long time)
	{
		TimeComparator comp = new TimeComparator();
		int index = Collections.binarySearch(myList, time, comp);
		if (index < 0)
		{
			index = -1 * index - 1;
			index++;
		}
		
		Meal meal = null;
		if (index < myList.size())
		{
			meal = (Meal) myList.get(index);
		}
		
		return meal;
	}
	
	
	public int getFirstIndexAtOrAfter (long time)
	{
		TimeComparator comp = new TimeComparator();
		int index = Collections.binarySearch(myList, time, comp);
		if (index < 0)
		{
			index = -1 * index - 1;
			index++;
		}
		
		if (index >= myList.size())
			return -1;
		else
			return index;
	}
	
	
	public int findIndex (long time)
	{
		TimeComparator comp = new TimeComparator();
		int index = Collections.binarySearch(myList, time, comp);
		return index;
	}


	public List<Meal> getEntriesFor(long start, long end)
	{
		List<Meal> list = new ArrayList<Meal>();
		
		int index = findAtOrAfter(start, myList);
		if (-1 != index)
		{
			int size = myList.size();
			while (index < size)
			{
				Meal meal = (Meal) myList.get(index);
				index++;
				
				if (meal.getTime() < end)
					list.add(meal);
			}
		}
		
		return list;
	}
	
	
	public List<Integer> getIndiciesFor(long start, long stop)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		int index = findAtOrAfter(start, myList);
		if (-1 != index)
		{
			int size = myList.size();
			while (index < size)
			{
				Meal meal = (Meal) myList.get(index);
				if (meal.getTime() < stop)
					list.add(index);
				index++;
			}
		}
		
		return list;
	}
	
	
	private static class FinderComparator implements Comparator
	{
		public long getTime(Object o)
		{
			long value;
			
			if (o instanceof Long)
			{
				Long l = (Long) o;
				value = l;
			}
			else if (o instanceof Meal)
			{
				Meal meal = (Meal) o;
				value = meal.getTime();
			}
			else
			{
				throw new IllegalArgumentException(o.getClass().getName());
			}
			
			return value;
		}
		
		public int compare(Object o1, Object o2)
		{
			long time1 = getTime(o1);
			long time2 = getTime(o2);
			
			int result;
			
			if (time1 > time2)
				result = 1;
			else if (time1 < time2)
				result = -1;
			else
				result = 0;
			
			return result;
		}
		
		public static FinderComparator COMPARATOR = new FinderComparator();
	};
	
	public int findIndexAtOrAfter(long time)
	{
		int index = Collections.binarySearch(myList, time, FinderComparator.COMPARATOR);
		if (index < 0)
		{
			index = (-1 * index) - 1;

			if (index >= myList.size())
				index = myList.size() - 1;
		}
		
		return index;
	}
	
	
	public int findIndexAtOrBefore(long time)
	{
		int index = Collections.binarySearch(myList, time, new FinderComparator());
		if (index < 0)
		{
			index = (-1 * index) - 1;
		}
		
		return index;
	}


	public Meal getMeal(int index)
	{
		Meal meal = (Meal) super.get(index);
		return meal;
	}
	
	
	public void deleteMeal(Meal meal)
	{
		int index = findIndex(meal.getTime());
		remove(index);
	}

	public void update(int index)
	{
		Meal meal = myList.get(index);
		myHelper.fireUpdate(index, meal);
	}
	
	/**
	 * This method <I>informs</I> the list that the element has changed.
	 * <P>
	 * Calling this method tells the list that the element changed and allows the list to
	 * inform its listeners of the change as well.
	 * </P>
	 * <P>
	 * If the ID of the meal passed to the method is not in the list, the method will
	 * throw an exception.
	 * </P>
	 * <P>
	 * If the meal's ID is in the list but the object is not the same as the one in the
	 * list (i.e., not == equivalent), the method will update the original object.
	 * </P>
	 * 
	 * @param meal
	 *        The updated meal.
	 */
	public void update (Meal meal)
	{
		int index = myIdToListPosition.get(meal.getId());
		Meal orig = get(index);
		if (meal != orig)
		{
			orig.updateFrom(meal);
		}
		
		myDirty = true;
		myHelper.fireUpdate(index, orig);
	}
	
	
	public void notifyOfUpdateTo (Meal meal)
	{
		myDirty = true;
		int index = myIdToListPosition.get(meal.getId());
		myHelper.fireUpdate(index, meal);
	}

	@CollectionProperty(
			elementClass = "com.lts.caloriecount.data.meal.Meal",
			elementName = "meal"
	)
	public List<Meal> getMeals()
	{
		return myList;
	}

	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement root = new SimpleElement(TAG_MEAL_LIST);
		serializeTo(root);
		return root;
	}	


	@Override
	protected String getSerializationElementName()
	{
		return TAG_MEAL_LIST;
	}

	@Override
	protected Meal createListElement(String name)
	{
		return new Meal();
	}
}
