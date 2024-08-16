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

import java.util.PriorityQueue;


/**
 * A service that allows clients to schedule notifications.
 * 
 * <H2>Description</H2>
 * Using this class, clients can schedule to be notified after after some point in time.
 * Clients call {@link #getInstance()} and then {@link #notifyAt(ScheduledEventListener, long)}
 * to arrange for notification.  At or after the specified point in time, 
 * {@link ScheduledEventListener#scheduledEvent(SchedulerEvent)} is called.
 * 
 * <H3>Threading Issues</H3>
 * Clients must not block the thread in which {@link ScheduledEventListener#scheduledEvent(SchedulerEvent)}
 * is called.  If that is done, all event delivery will be stopped for all clients.
 * 
 * <P>
 * This class is intended to run in its own thread.  When {@link #getInstance()} is
 * called, a new thread will be created for the scheduler if one does not already exist.
 * 
 * @author cnh
 */
public class Scheduler implements Runnable
{
	protected static Scheduler ourInstance;
	
	private static Scheduler createInstance()
	{
		EventDeliverer deliverer = new EventDeliverer();
		Thread thread = new Thread(deliverer);
		thread.setName("event delivery");
		thread.start();
		
		Scheduler scheduler = new Scheduler(deliverer);
		thread = new Thread(scheduler);
		thread.setName("scheduler");
		thread.start();
		
		return scheduler;
	}
	
	
	public static Scheduler getInstance()
	{
		if (null == ourInstance)
		{
			synchronized(Scheduler.class)
			{
				if (null == ourInstance)
					ourInstance = createInstance();
			}
		}
		
		return ourInstance;
	}
	
	protected boolean keepGoing;
	
	public void stop ()
	{
		this.keepGoing = false;
	}
	
	
	public synchronized void clearAllEvents ()
	{
		this.deliverer.clearAllEvents();
	}
	
	protected Scheduler(EventDeliverer eventDeliverer)
	{
		this.keepGoing = true;
		this.deliverer = eventDeliverer;
		this.queue = new PriorityQueue();
	}

	protected EventDeliverer deliverer;
	protected Thread deliveryThread;
	
	/**
	 * Stop the delivery of events.
	 * <P>
	 * This method will tell the event delivery agent to stop; assuming that it is 
	 * running.  The method uses the deliverer property to determine the object that 
	 * is performing the deliveries --- if that property is null, the method just 
	 * returns without doing anything.  
	 * 
	 * <P>
	 * Assuming the deliverer exists, the {@link EventDeliverer#stopDelivery()} is 
	 * called to signal that it should stop.  This happens regardless of whether the 
	 * delivery thread exists or what its status is.
	 * 
	 * <P>
	 * Next, the method checks for the existence of a delivery thread.  If one does 
	 * not exist, the method returns without doing anything further.  If a thread 
	 * does exist, the {@link Thread#interrupt()} method is called to break out of 
	 * any waiting the deliverer may be engaged in.  Note that the interrupt method 
	 * is called regardless of the status of the thread object.
	 */
	protected synchronized void stopDelivery()
	{
		if (null == this.deliverer)
			return;
		
		this.deliverer.stopDelivery();
		
		if (null == this.deliveryThread)
			return;
		
		this.deliveryThread.interrupt();
	}
	
	protected PriorityQueue<InternalScheduledEvent> queue;
	
	public void performCycle ()
	{
		InternalScheduledEvent request = null;
		
		try
		{
			synchronized (this.queue)
			{
				long now;
				
				do {
					now = System.currentTimeMillis();
					request = this.queue.peek();
					
					//
					// If there are no events, then wait for a new one
					//
					if (null == request)
					{
						this.queue.wait();
					}
					
					//
					// otherwise, if the event occurs in the future, wait for either a 
					// new event to be added, or the current one to come due
					//
					else if (request.scheduledTime > now)
					{
						long waitTime = request.scheduledTime - now;
						this.queue.wait(waitTime);
					}
					
					//
					// otherwise, the current event is both non-null and due, this will 
					// force the loop to terminate
					//
					else
						;
				} while (null == request || request.scheduledTime > now);
				
				//
				// to get here, we must have an event that is deliverable
				//
				this.queue.remove(request);
				this.deliverer.deliver(request);
			}
		}
		//
		// Ignore an InterruptedException because it is assumed that someone has changed
		// the flag status.  Before this method gets called, the flag is checked, hence
		// if we are supposed to stop, we will.
		//
		catch (InterruptedException e)
		{
			
		}
	}
	

	public void run ()
	{
		try
		{
			while (this.keepGoing)
			{
				performCycle();
			}
		}
		finally
		{
			stopDelivery();
		}
	}
	
	
	public void notifyAt (ScheduledEventListener listener, long time, Object data)
	{
		if (null == listener)
			return;
		
		InternalScheduledEvent event = new InternalScheduledEvent(listener, time, data);
		
		synchronized (this.queue)
		{
			this.queue.add(event);
			this.queue.notifyAll();
		}
	}
	
	public void notifyAt (ScheduledEventListener listener, long time)
	{
		notifyAt(listener, time, null);
	}
	
	
	public void cancel (ScheduledEventListener listener)
	{
		synchronized (this.queue)
		{
			for (InternalScheduledEvent event : this.queue)
			{
				if (listener == event.data)
				{
					this.queue.remove(event);
					break;
				}
			}
		}
	}
}
