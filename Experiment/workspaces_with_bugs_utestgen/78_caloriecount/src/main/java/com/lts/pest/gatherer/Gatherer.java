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
package com.lts.pest.gatherer;

import java.util.concurrent.LinkedBlockingQueue;

import com.lts.pest.data.GatherMetaData;
import com.lts.scheduler.ScheduledEventListener;
import com.lts.scheduler.SchedulerEvent;

/**
 * The part of the system that is responsible for gathering data from the user.
 * 
 * <H3>Deadlock Warning</H3>
 * Some of the methods in this class either directly or indireclty obtain a lock
 * on the "current instance of GatherMetaData."  Such methods are not marked as 
 * synchronized, and the method descriptions much be read to determine which ones
 * do so.
 * 
 * <H3>Abstract Class</H3>
 * The following methods must be defines in order to create a class that can be 
 * instantiated:
 * <UL>
 * <LI>gatherData
 * <LI>getGatherData
 * </UL>
 * 
 * <H3>Description</H3>
 * Clients use this object to
 * 
 * <P>
 * <UL>
 * <LI>set the gather frequency
 * <LI>start gathering data
 * <LI>stop gathering data
 * <LI>When the next gather will take place
 * </UL>
 * 
 * <P>
 * <H3>Gather Period</H3>
 * This is the number of milliseconds that the application should wait between prompts
 * to the user.  This is also the amount of time that a sample represents: if the period
 * is 12 minutes when sampling takes place, then the duration for that sample will be 
 * 12 minutes as well. 
 * 
 * <P>
 * Note that gather frequency is usually a factor of 60.  The prime factors of 60 are 
 * 2 * 2 * 3 * 5.
 * 
 * <P>
 * 
 * <P>
 * Alignment happens automatically, unless the period requested is not a factor of 60.
 * That is, if the period is not factorable by 2,3 or 5.  For example, if the period
 * is 7. 
 * 
 * <H3>Endless Loops</H3>
 * This class has the potential for getting into an endless loop because the method 
 * {@link #updateGatherData(Long)} calls {@link GatherMetaData#setNextSample(Long)}, 
 * and that method calls {@link GatherMetaData#fireDataChanged()}, which calls 
 * updateGatherData.
 * 
 * <P>
 * The solution for this vulnerability is to ensure the following:
 * <UL>
 * <LI>One and only one instance of this class can exist.
 * <LI>Each instance of this class is forced to ignore any calls to GatherMetaData if 
 * it is already in the process of making one.
 * </UL>
 * 
 * <P>
 * The class ensures that only one instance exists via the following:
 * 
 * <P>
 * <UL>
 * <LI>Only methods in this class can access the constructor.
 * <LI>The only method that uses the constructor is {@link #createSingleton()}.
 * <LI>createSingleton only calls the constructor once/VM in the vast majority of
 * cases.
 * </UL>
 *
 * <P>
 * The class ensures that it can make only one call to GatherMetaData via the following:
 * 
 * <P>
 * <UL>
 * <LI>Only one method directly writes to GatherMetaData, updateGatherData.
 * <LI>updateGatherData is private --- only other methods in this class can use it.
 * <LI>updateGatherData is static synchronized --- only one thread per VM.
 * <LI>updateGatherData ignores the call unless {@link #previousGatherTime} is null.
 * <LI>updateGatherData sets previousGatherTime to null before writing to GatherMetaData.   
 * </UL>
 *
 * <P>
 * In the absolute case, access modifiers can be ignored, multiple instances of the
 * same class can be created in the same VM, etc.  These measures should alert 
 * developers of the potential hazards of doing so.
 * 
 * <P>
 * This being the case, only this class can create an instance of this class --- it
 * has a private constructor.
 * 
 * <P>
 * The only method that uses the constructor is {@link #getInstance()}.
 * 
 * @author cnh
 */
abstract public class Gatherer
{
	/**
	 * Poll the user in order to obtain information.
	 * 
	 * <P>
	 * This is the method that interacts with the user.  If the app is recording 
	 * what food the user eats, then this method should ask the user: hey, what 
	 * food have you eaten?
	 * 
	 * @param time The start time for the polling period.  Note that this can be
	 * hours in the past the using has been AFK, etc.  The value uses the same
	 * format that {@link System#currentTimeMillis()}.
	 * 
	 * @param period How long the polling period is supposed to be for.  This is 
	 * in seconds.
	 */
	abstract public void gatherData(long time);
	
	/**
	 * 
	 * @return
	 */
	abstract public GatherMetaData getGatherData();
	
	static private Gatherer ourInstance;
	

	public Object[][] getTimes()
	{
		return TimeConstants.SPEC_TIMES;
	}
	
	
	
	static public Gatherer getInstance()
	{
		return ourInstance;
	}
	
	static public void initializeClass (Gatherer gatherer)
	{
		if (null != ourInstance)
			throw new IllegalStateException();
		
		ourInstance = gatherer;
	}
	
	protected Gatherer ()
	{}
	
	public static class LocalListener implements ScheduledEventListener
	{
		protected LinkedBlockingQueue queue;
		
		public LocalListener (LinkedBlockingQueue theQueue)
		{
			this.queue = theQueue;
		}
		
		public void scheduledEvent(SchedulerEvent event)
		{
			Long l = new Long(event.scheduledTime);
			this.queue.add(l);
		}
	}
	
	
	public static class LocalSchedulerData 
	{
		public long myTime;
		public long myDuration;
		
		public LocalSchedulerData (long time, long duration)
		{
			myTime = time;
			myDuration = duration;
		}
	}
	
	
	/**
	 * The point in time during each day that the application should start gathering
	 * data.
	 * 
	 * <P>
	 * The value returned is in milliseconds and relative to 00:00.000 of the day
	 */
	public long getStartOfDay()
	{
		return getGatherData().getStartOfDay();
	}
	
	
	/**
	 * The point in time during each day that the application should stop gathering
	 * data.
	 * <P>
	 * The value returned is in milliseconds and relative to 00:00.000 of the day
	 */
	public long getEndOfDay()
	{
		return getGatherData().getEndOfDay();
	}	
}
