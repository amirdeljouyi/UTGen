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
/**
 * 
 */
package com.lts.scheduler;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * A thread that delivers events taken from a 
 * @author cnh
 *
 */
public class EventDeliverer implements Runnable
{
	protected boolean keepGoing;
	protected LinkedBlockingQueue<InternalScheduledEvent> queue;
	
	
	public void stopDelivery ()
	{
		this.keepGoing = false;
	}
	
	
	public EventDeliverer ()
	{
		this.keepGoing = true;
		this.queue = new LinkedBlockingQueue<InternalScheduledEvent>();
	}
	
	
	public synchronized void clearAllEvents()
	{
		this.queue.clear();
	}
	
	public void run ()
	{
		while (this.keepGoing)
		{
			try
			{
				InternalScheduledEvent event = this.queue.take();
				event.getListener().scheduledEvent(event);
			}
			catch (InterruptedException e)
			{
				this.keepGoing = false;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	public void deliver (InternalScheduledEvent event)
	{
		this.queue.add(event);
	}
	
	
	public static EventDeliverer startDelivery ()
	{
		EventDeliverer deliverer = new EventDeliverer();
		Thread t = new Thread(deliverer);
		t.start();
		
		return deliverer;
	}
}