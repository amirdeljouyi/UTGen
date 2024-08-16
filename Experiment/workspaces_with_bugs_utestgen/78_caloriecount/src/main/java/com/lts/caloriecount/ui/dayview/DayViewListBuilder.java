package com.lts.caloriecount.ui.dayview;

import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.ui.entry.EntryRowModel;
import com.lts.util.notifyinglist.TableBridge;
import com.lts.util.notifyinglist.sorted.IndirectComparator;
import com.lts.util.notifyinglist.sorted.SortedListProxy;
import com.lts.util.notifyinglist.sublist.SublistProxy;

public class DayViewListBuilder
{
	public SublistProxy<Entry> mySublist;
	public DayViewInclusionTest myInclusionTest;
	public SortedListProxy<Entry> mySortedList;
	public TableBridge myModel;
	
	public void build(long start, long end)
	{
		EntryList list = CalorieCount.getEntries();
		myInclusionTest = new DayViewInclusionTest(start, end);
		mySublist = new SublistProxy<Entry>(list, myInclusionTest);
		IndirectComparator comp = new IndirectComparator(Entry.TIME_COMPARATOR, mySublist);
		mySortedList = new SortedListProxy<Entry>(comp, mySublist);
		EntryRowModel rowModel = new EntryRowModel();
		rowModel.setShowTimeOnly(true);
		myModel = new TableBridge<Entry>(mySortedList, rowModel);
	}
	
	public void setRange(long start, long end)
	{
		myInclusionTest.startTime = start;
		myInclusionTest.stopTime = end;
		mySublist.refresh();
	}
}
