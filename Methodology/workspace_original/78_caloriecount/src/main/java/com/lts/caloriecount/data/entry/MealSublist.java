package com.lts.caloriecount.data.entry;



import com.lts.caloriecount.data.meal.Meal;
import com.lts.util.notifyinglist.NotifyingListAdaptor;

@SuppressWarnings("serial")
public class MealSublist extends NotifyingListAdaptor<Meal>
{
	private EntryList myEntryList;
	
	
	public MealSublist(EntryList list)
	{
		initialize(list);
	}
	
	protected void initialize(EntryList entryList)
	{
		myEntryList = entryList;
		rebuildList();
	}
	
	private void rebuildList()
	{
		for (Entry entry : myEntryList)
		{
			if (entry instanceof Meal)
				add((Meal) entry);
		}
	}

	public EntryList getEntryList()
	{
		return myEntryList;
	}
}
