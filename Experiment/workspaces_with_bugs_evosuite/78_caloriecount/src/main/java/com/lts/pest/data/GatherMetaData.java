//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest.data;

import java.util.Map;

import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;


/**
 * Contains information about when and how sampling should take place.
 * 
 * <P>
 * This object contains the following properties:
 * <UL>
 * <LI>nextSample - when the next sampling will take place, in the form used by 
 * {@link System#currentTimeMillis()}.  This value is null if sampling is not 
 * currently taking place.
 * 
 * <LI>duration - how much time the next sample will represent, expressed as the 
 * number of milliseconds.  This value is null if sampling has not taken place 
 * before.
 * 
 * <LI>top - The number of "most popular" entries in the "quick pick" list.
 * 
 * <LI>myStartOfDay - The point in time, relative to 00:00.000, when the application
 * is supposed to start gathering data.
 * 
 * <LI>myEndOfDay - The point in time, relative to 00:00.000, when the application is
 * supposed to stop gathering data.
 *  
 * </UL>
 * 
 * @author cnh
 *
 */
public class GatherMetaData implements DeepCopier
{
	private static final long serialVersionUID = 1L;

	public static final int MSEC_PER_MINUTE = 60 * 1000;
	public static final int MSEC_PER_HOUR = 60 * MSEC_PER_MINUTE;
	
	public static final int DEFAULT_DURATION = 15 * MSEC_PER_MINUTE;
	public static final int DEFAULT_START_OF_DAY = 6 * MSEC_PER_HOUR;
	public static final int DEFAULT_END_OF_DAY = 19 * MSEC_PER_HOUR;

	transient private boolean myGathering; 
	transient private GatherMetaDataListenerHelper myHelper;
	transient private boolean myDirty;
	
	private Long myStartOfDay;
	private Long myEndOfDay;
	private Long myDuration;
	private Long myPeriod;
	
	public Long getPeriod()
	{
		return myPeriod;
	}
	
	public void addGatherMetaDataListener(GatherMetaDataListener listener)
	{
		getHelper().addListener(listener);
	}
	
	public void removeGatherMetaDataListener(GatherMetaDataListener listener)
	{
		getHelper().removeListener(listener);
	}
	
	synchronized public void setPeriod(long period)
	{
		myPeriod = period;
		getHelper().firePeriodChanged(period);
	}
	
	public GatherMetaDataListenerHelper getHelper()
	{
		if (null == myHelper)
			myHelper = new GatherMetaDataListenerHelper();
		
		return myHelper;
	}
	
	
	public boolean isGathering()
	{
		return myGathering;
	}
	
	public boolean getGathering()
	{
		return isGathering();
	}
	
	synchronized public void startGathering (long period)
	{
		myGathering = true;
		myPeriod = period;
		getHelper().fireStartGathering(period);
	}
	
	
	public void stopGathering()
	{
		myGathering = false;
		getHelper().fireStopGathering();
	}
	
	

	/**
	 * Set the nextGather to align with the gathering period.
	 * 
	 * <P>
	 * Set the nextGather property so that it coincides with the period property.
	 * If the current time does not occur on a period boundary, the time is set to the 
	 * next time in the future that does.
	 */
	public static long alignTime (long time, long period)
	{
		if (0 != (time % period))
		{
			long temp = time % period;
			time = time - temp + period;
		}
		
		return time;
	}
	
	
	public long getStartOfDay()
	{
		if (null == myStartOfDay)
			myStartOfDay = new Long(DEFAULT_START_OF_DAY);
		
		return this.myStartOfDay;
	}
	
	public void setStartOfDay (long start)
	{
		myStartOfDay = start;
		setDirty(true);
	}
	
	public long getEndOfDay()
	{
		if (null == myEndOfDay)
			this.myEndOfDay = new Long(DEFAULT_END_OF_DAY);
		
		return this.myEndOfDay;
	}
	
	public void setEndOfDay(long end)
	{
		this.myEndOfDay = end;
		setDirty(true);
	}
	
	
	public boolean isDirty()
	{
		return myDirty;
	}
	
	public boolean getDirty()
	{
		return isDirty();
	}
	
	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}
	
	public void deepCopyData (Object ocopy, Map map, boolean copyTransients) throws DeepCopyException
	{
		GatherMetaData copy = (GatherMetaData) ocopy;
		
		copy.myEndOfDay = this.myEndOfDay;
		copy.myStartOfDay = this.myStartOfDay;
		copy.myDuration = myDuration;
		copy.myPeriod = myPeriod;
		
		if (copyTransients)
		{
			copy.myGathering = myGathering;
			copy.myDirty = myDirty;
		}
	}
	
	
	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return deepCopy(false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}
}
