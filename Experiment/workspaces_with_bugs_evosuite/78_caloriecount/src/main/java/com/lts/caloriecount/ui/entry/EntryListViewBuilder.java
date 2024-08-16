package com.lts.caloriecount.ui.entry;

import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.util.notifyinglist.TableBridge;
import com.lts.util.notifyinglist.sorted.IndirectComparator;
import com.lts.util.notifyinglist.sorted.SortedListProxy;

public class EntryListViewBuilder
{
	public SortedListProxy<Entry> mySortedList;
	public TableBridge myModel;

	public void build()
	{
		EntryList list = CalorieCount.getData().getEntryList();
		IndirectComparator comp = new IndirectComparator(Entry.TIME_COMPARATOR, list);
		mySortedList = new SortedListProxy<Entry>(comp, list);
		myModel = new TableBridge<Entry>(mySortedList, new EntryRowModel());
	}
}
