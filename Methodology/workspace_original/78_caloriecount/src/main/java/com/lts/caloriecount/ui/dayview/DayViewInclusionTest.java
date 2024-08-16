/**
 * 
 */
package com.lts.caloriecount.ui.dayview;

import com.lts.caloriecount.data.entry.Entry;
import com.lts.util.notifyinglist.sublist.SublistInclusionTest;

public class DayViewInclusionTest implements SublistInclusionTest<Entry>
{
	public long startTime;
	public long stopTime;
	
	public DayViewInclusionTest(long theStartTime, long theStopTime)
	{
		startTime = theStartTime;
		stopTime = theStopTime;
	}
	
	@Override
	public boolean include(Entry meal)
	{
		long time = meal.getTime();
		
		return
			time >= startTime
			&& time <= stopTime;
	}
	
}