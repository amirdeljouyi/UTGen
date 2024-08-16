//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A shared, inverse priority queue.
 * 
 * 
 * @author cnh
 *
 */
public class SchedulerQueue
{
	protected int removeSequence = 0;
	protected List list;
	protected Map contentSet = new HashMap();
	
	public SchedulerQueue ()
	{
		this.list = new ArrayList();
	}
		
	/**
	 * Add a new object to the queue and notify listeners.
	 * <P>
	 * Adds are screened for nulls and elements that are already in the queue.  In
	 * either of those cases, the call is ignored.
	 * <P>
	 * Elements are, however, allowed to have the same priority as existing elements.
	 * In that situation, the element is simply added to the collection via 
	 * {@link List#add(int, Object)} where the insertion point is selected by 
	 * {@link Collections#binarySearch(List, Object)}.
	 * 
	 * @param o The new element.
	 */
	public synchronized void add (SchedulerEvent o)
	{
		if (null == o)
			return;
		
		if (this.list.contains(o))
			return;
		
		int insertPoint = Collections.binarySearch(this.list, o);
		if (0 > insertPoint)
			insertPoint = -1 * (insertPoint + 1); 
		
		this.list.add(insertPoint, o);
		notify();
	}
	

	public synchronized SchedulerEvent take() throws InterruptedException
	{
		return take(-1, Long.MAX_VALUE);
	}
	
	/**
	 * Wait at least a certain amount of time for an event whose scheduled time must be
	 * less than or equal to some value.
	 * <P>
	 * What is really desired is to wait at most a certain period of time, but Java cannot
	 * guarantee that, hence the wording of this method.
	 * 
	 * @param minTime
	 *        The minimum amount of time, in milliseconds that this method must wait
	 *        before declaring failure and returning null.  A value less than zero means 
	 *        wait forever.
	 * @param maxScheduledTime
	 *        The maximum value that an event returned can have for its scheduledTime
	 *        property.
	 * @return The event meeting the above criteria, or null if one could not be found
	 *         after waiting the minimum period of time.
	 * @exception InterruptedException
	 *            Thrown if the thread is interrupted while waiting for the desired event
	 *            to occur.
	 */
	public synchronized SchedulerEvent take(long minTime, long maxScheduledTime)
			throws InterruptedException
	{
		long stop = Long.MAX_VALUE;
		if (minTime > 0)
			stop = System.currentTimeMillis() + minTime;
		
		SchedulerEvent event = null;
		
		long now = System.currentTimeMillis();

		do {
			long waitForNextEvent = Long.MAX_VALUE;
			
			if (this.list.size() > 0)
				event = (SchedulerEvent) this.list.get(0);
			
			if (null != event)
			{
				waitForNextEvent = event.scheduledTime - now;
				if (waitForNextEvent < 0)
					waitForNextEvent = 0;
				
				if (event.scheduledTime > maxScheduledTime)
					event = null;
				else
					this.list.remove(event);
			}
			
			if (null == event)
			{
				long minWaitRemaining = stop - now;
				long waitTime;
				
				if (waitForNextEvent < minWaitRemaining)
					waitTime = waitForNextEvent;
				else
					waitTime = minWaitRemaining;
				
				wait(waitTime);
			}
			
			now = System.currentTimeMillis();
		}
		while (now < stop && null == event);
		
		return event;
	}

	protected SchedulerEvent getEarliest()
	{
		SchedulerEvent event = null;
	
		if (this.list.size() > 0)
		{
			event = (SchedulerEvent) this.list.get(0);
		}
		
		return event;
	}
	
	
	
	public synchronized Object peek () 
	{
		Object o = null;
		
		if (this.list.size() > 0)
			o = this.list.get(0);
		
		return o;
	}
	
	
	public synchronized Object[] snapshot()
	{
		Object[] temp = this.list.toArray();
		Object[] result = temp.clone();
		return result;
	}
	
	public synchronized void clear()
	{
		this.list = new ArrayList();
	}
	
	
	public synchronized SchedulerEvent takeIfAvailable ()
	{
		if (this.list.size() <= 0)
			return null;
		
		SchedulerEvent result = (SchedulerEvent) this.list.remove(0);
		this.contentSet.remove(result);
		return result;
	}
	
	
	public static class StampedElement implements Comparable
	{
		public int sequence;
		public Object data;
		
		public StampedElement (int theSequence, Object theData)
		{
			this.sequence = theSequence;
			this.data = theData;
		}
		
		public String toString()
		{
			return "stamped(" + this.sequence + "," + this.data + ")";
		}
		
		public int compareTo (Object o)
		{
			StampedElement other = (StampedElement) o;
			if (this.sequence < other.sequence)
				return -1;
			else if (this.sequence > other.sequence)
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * Return the next element off the queue while providing a sequence in which the
	 * elements are returned.
	 * <P>
	 * The sequence is the sequence in which the elements are taken off of this object,
	 * with 0 for the first element taken, 1 for the next and so on.  The value of 
	 * {@link QueueElement#time} will contains this value.  {@link QueueElement#object}
	 * contains the data for the element.
	 * 
	 * @return A QueueElement as described above.
	 */
	public synchronized StampedElement takeStamped () throws InterruptedException
	{
		Object data = take();
		StampedElement el = new StampedElement(this.removeSequence, data);
		this.removeSequence++;
		return el;
	}
	
	/**
	 * Return the next element off the queue as an instance of QueueElement, where 
	 * the time property is set to the sequence in which the element was removed from
	 * the queue.
	 * <P>
	 * The sequence of an element is the order in which it was removed from the queue
	 * from among all elements returned via this method or {@link #takeStamped()}.
	 * Otherwise, this method is the same as {@link #takeIfAvailable()}.
	 * 
	 * @return The next element off the queue or null if nothing was available.
	 */
	public synchronized StampedElement takeStampedIfAvailable ()
	{
		StampedElement el = null;
		Object data = takeIfAvailable();
		if (null != data)
		{
			el = new StampedElement(this.removeSequence, data);
			this.removeSequence++;
		}
		
		return el;
	}
	
	public synchronized boolean contains (Object o)
	{
		return this.contentSet.containsKey(o);
	}
}
