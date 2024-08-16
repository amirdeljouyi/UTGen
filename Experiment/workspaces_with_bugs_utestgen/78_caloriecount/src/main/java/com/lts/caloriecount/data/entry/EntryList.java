package com.lts.caloriecount.data.entry;

import com.lts.application.ApplicationException;
import com.lts.application.data.IdApplicationDataList;
import com.lts.caloriecount.data.Adjustment;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.util.DateUtil;
import com.lts.xml.simple.SimpleElement;

public class EntryList extends IdApplicationDataList<Entry>
{
	public static final String TAG_ENTRY_LIST = "entries";
	
	private static final long serialVersionUID = 1L;


	public enum EntryTypes
	{
		Meal,
		Adjustment;
		
		public static boolean isValidName(String s)
		{
			for (EntryTypes entry : values())
			{
				if (entry.name().equals(s))
				{
					return true;
				}
			}
			
			return false;
		}
		
		public static EntryTypes valueOfIgnoreCase (String s)
		{
			for (EntryTypes entry : values())
			{
				if (entry.name().equalsIgnoreCase(s))
				{
					return entry;
				}
			}
			
			return null;
		}
	}
	
	@Override
	protected String getSerializationElementName()
	{
		return TAG_ENTRY_LIST;
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		for (SimpleElement child : elem.getChildren())
		{
			Entry entry = deserializeChildFrom(child);
			if (null != entry)
				add(entry);
		}
	}
	
	protected Entry deserializeChildFrom(SimpleElement elem)
	{
		Entry entry = null;
		EntryTypes entryType = EntryTypes.valueOfIgnoreCase(elem.getName());
		if (null != entryType)
		{
			switch (entryType)
			{
				case Adjustment :
					entry = new Adjustment();
					entry.deserializeFrom(elem);
					break;
					
				case Meal :
					entry = new Meal();
					entry.deserializeFrom(elem);
					break;
					
				default :
					throw new RuntimeException("Impossible case");
			}
		}
		
		return entry;
	}

	@Override
	public void serializeTo(SimpleElement elem)
	{
		for (Entry entry : this)
		{
			SimpleElement child = entry.createSerializationElement();
			elem.addChild(child);
		}
	}

	protected Entry createChildElement(SimpleElement elem)
	{
		Entry entry = null;
		EntryTypes entryType = EntryTypes.valueOfIgnoreCase(elem.getName());
		if (null != entryType)
		{
			switch (entryType)
			{
				case Adjustment :
					entry = new Adjustment();
					entry.deserializeFrom(elem);
					break;
					
				case Meal :
					entry = new Meal();
					entry.deserializeFrom(elem);
					break;
					
				default :
					throw new RuntimeException("Impossible case");
			}
		}
		
		return entry;
	}


	@Override
	protected Entry createListElement(String name)
	{
		Entry entry = null;
		EntryTypes entryType = EntryTypes.valueOfIgnoreCase(name);
		if (null != entryType)
		{
			switch (entryType)
			{
				case Adjustment :
					entry = new Adjustment();
					break;
					
				case Meal :
					entry = new Meal();
					break;
					
				default :
					throw new RuntimeException("Impossible case");
			}
		}
		
		return entry;
	}

	/**
	 * Update an object in the list with the contents of another object.
	 * 
	 * <P>
	 * If the original object is no longer in the list, the method does nothing 
	 * except return false.  Otherwise it should call {@link Adjustment#updateFrom(Adjustment)}
	 * and then send out the appropriate notifications.
	 * </P>
	 * 
	 * @param original The object that should still be in the list.
	 * @param current The new version of the object.
	 * @return true if the original was in the list, false otherwise.
	 * @throws ApplicationException 
	 */
	public boolean update(Adjustment original, Adjustment current) throws ApplicationException
	{
		int index = indexOf(original);
		if (-1 == index)
			return false;
		else
		{
			original.copyFrom(current);
			myHelper.fireUpdate(index, original);
			return true;
		}
	}

	public void createMeal(Food food)
	{
		createMeal(System.currentTimeMillis(), food);
	}

	public int totalCalories(long start, long end)
	{
		int total = 0;
		
		for (Entry entry : this)
		{
			if (entry.getTime() >= start && entry.getTime() <= end)
				total += entry.getCalories();
		}
		
		return total;
	}

	public void createMeal(long time, Food food)
	{
		Meal meal = new Meal(0, food, time);
		add(meal);
	}

	public boolean update(Meal original, Meal update)
	{
		int index = indexOf(original);
		if (-1 == index)
		{
			return false;
		}
		else
		{
			remove(index);
			add(update);
			return true;
		}
	}
	
	public int getTotalUpToNow()
	{
		long start = DateUtil.startOfTodayTime();
		long end = DateUtil.startOfTomorrow() - 1;
		return totalCalories(start, end);
	}
}
