package com.lts.caloriecount.data.frequent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.IdApplicationDataList;
import com.lts.caloriecount.app.CalorieCountException;
import com.lts.caloriecount.app.CalorieCountException.Reasons;
import com.lts.caloriecount.data.food.Food;

/**
 * The list of frequently selected foods.
 * 
 * <H2>NOTE</H2>
 * <B>The class does not currently support locking!!!</B>
 * The discussion below represents the plan for implementing locks.
 * 
 * <H3>Description</H3>
 * A frequently selected food has a count property that describes the number of times
 * that the entry has been selected.  Each entry can also be "locked" at a specific 
 * position in the list.  Locked entries appear at the same location no matter what
 * their count is.
 * 
 * <P>
 * After lock position and count, the next property used in sorting the list is food
 * name.
 * </P>
 * 
 * <P>
 * One way to think of how sorting is determined is this: remove all the locked entries
 * and sort them by lock position.  Next sort the unlocked entries by # of times selected
 * and then by name.  Now merge the sorted lists of locked and unlocked entries by taking 
 * the first element of the locked list and putting at the locked entries lock position.
 * </P>
 * 
 * <P>
 * A locked entry is one who's position in the list does not change.  It always
 * stays at the same position, unless this would create an empty entry.
 * </P>
 * 
 * <P>
 * If a locked entry is the last one in the list, and an entry somewhere before it is removed,
 * the locked entry shifts up one index.  If that would cause it overwrite the position
 * of a locked entry, the entry above it also shifts up one.  The process continues 
 * until all the entries are updated. 
 * </P>
 * 
 * <P>
 * Example: in the following, a string in square brackets represents a locked entry.
 * The entry being deleted is surrounded with asterisks
 * </P>
 * 
 * <P>
 * <CODE>
 * <PRE>
 * 0 : oranges        0 : oranges
 * 1***carrots*       1 : [fish]
 * 2 : [fish]         2 : [pears]
 * 3 : [pears]
 * </PRE>
 * </CODE>
 * 
 * <P>
 * <CODE>
 * <PRE>
 * 0 : oranges        0 : oranges
 * 1***carrots*       1 : pears
 * 2 : [fish]         2 : [fish]
 * 3 : pears
 * </PRE>
 * </CODE>
 * 
 * @author cnh
 *
 */
public class FrequentFoodList extends IdApplicationDataList<FrequentFood> 
{
	private static final long serialVersionUID = 1L;

	public static final String TAG_FREQUENT_FOODS = "frequentFoods";
	
	transient protected Map<Integer, FrequentFood> myFoodIdToEntry =
		new HashMap<Integer, FrequentFood>();
	
	protected void rebuildFoodIdToEntry()
	{
		myFoodIdToEntry = new HashMap<Integer, FrequentFood>();
		for (FrequentFood entry : this)
		{
			myFoodIdToEntry.put(entry.getFoodId(), entry);
		}
	}
	
	public FrequentFood getEntryForFood(Food food)
	{
		return getEntryForFoodId(food.getId());
	}
	
	
	public FrequentFoodList()
	{
		initialize();
	}
	
	
	public FrequentFood getEntryForFoodId(int id)
	{
		return myFoodIdToEntry.get(id);
	}

	public FrequentFood createFrequentFood(Food food)
	{
		FrequentFood freq = new FrequentFood(-1, food);
		add(freq);
		return freq;
	}
	
	synchronized public void removeEntry(int id) throws CalorieCountException
	{
		FrequentFood entry = idToElement(id);
		if (null == entry)
		{
			throw new CalorieCountException(Reasons.FrequentFoodNotFound);
		}
		
		
		super.remove(entry);
	}
	
	
	@Override
	public void postDeserialize() throws ApplicationException
	{
		removeDuplicates();
		rebuildFoodIdToEntry();
		super.postDeserialize();		
	}

	private void removeDuplicates()
	{
		List<FrequentFood> outlist = new ArrayList<FrequentFood>();
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (FrequentFood food : this)
		{
			if (null == map.get(food.getFoodId()))
			{
				outlist.add(food);
				map.put(food.getFoodId(), food.getFoodId());
			}
		}
		
		basicReplaceWith(outlist);
	}
	
	
	protected int basicAdd(int index, FrequentFood freq)
	{
		if (null != getEntryForFoodId(freq.getFoodId()))
		{
			String msg = "Duplicate food ID: " + freq.getFoodId();
			
			Food food = freq.getFood();
			if (null != food)
			{
				msg = "Duplicate food ID: " + food;
			}
			
			throw new RuntimeException(msg);
		}
		
		myFoodIdToEntry.put(freq.getFoodId(), freq);
		
		return super.basicAdd(index, freq);
	}

	synchronized public void resetAllCounts()
	{
		for (FrequentFood entry : myList)
		{
			entry.setCount(0);
		}
	}
	
	
	synchronized public void increment(Food food)
	{
		FrequentFood entry = getEntryForFood(food);
		entry.increment();
	}


	protected void basicReplaceWith(List<FrequentFood> list)
	{
		super.basicReplaceWith(list);
		
		rebuildFoodIdToEntry();
	}
	
	
	public void setFrequentFoods(List<FrequentFood> list)
	{
		myList = new ArrayList(list);
	}
	
	
//	public FrequentFood set(int index, FrequentFood freq)
//	{
//		FrequentFood old = null;
//		if (index < size())
//			old = get(index);
//		
//		set(index, freq);
//		return old;
//	}
//
//
	@Override
	protected String getSerializationElementName()
	{
		return TAG_FREQUENT_FOODS;
	}


	@Override
	protected FrequentFood createListElement(String name)
	{
		return new FrequentFood();
	}
	
	
	public FrequentFood findOrCreate(Food food)
	{
		FrequentFood freq = getEntryForFood(food);
		if (null == freq)
		{
			freq = new FrequentFood(-1, food);
			add(freq);
		}
		
		return freq;
	}	
}
