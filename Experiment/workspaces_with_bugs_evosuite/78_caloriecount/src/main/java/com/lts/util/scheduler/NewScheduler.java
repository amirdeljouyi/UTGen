package com.lts.util.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.util.SharedQueue;

/**
 * A system for clients to receive a call after a specified point in time.
 * 
 * <H2>Quickstart</H2>
 * <CODE>
 * <PRE>
 * // receive a call after about 10 sec
 * ScheduledQueueReader reader = &lt;create a ScheduledQueueReader&gt;;
 * reader.start();
 * long notifyTime = System.currentTimeMillis();
 * NewScheduler sched = NewScheduler.getInstance();
 * sched.notifyAt(notifyTime, reader.getQueue(), null);
 * </PRE>
 * </CODE>
 * 
 * @author cnh
 *
 */
public class NewScheduler extends ControllableThread
{
	public static class ScheduledEvent implements Comparable<ScheduledEvent> 
	{
		public long scheduledTime;
		public Object data;
		public SharedQueue queue;
		
		public ScheduledEvent (long time, SharedQueue queue, Object theData)
		{
			scheduledTime = time;
			data = theData;
			this.queue = queue;
		}

		public int compareTo(ScheduledEvent o)
		{
			if (scheduledTime < o.scheduledTime)
				return -1;
			else if (scheduledTime > o.scheduledTime)
				return 1;
			else
				return 0;
		}
	}
	
	
	protected NewScheduler()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		myThread = new Thread(this);
		myQueueToNext = new HashMap<SharedQueue, ScheduledEvent>();
		myEvents = new ArrayList<ScheduledEvent>();
	}
	
	
	protected static NewScheduler ourInstance;
	
	protected Thread myThread;
	protected Map<SharedQueue, ScheduledEvent> myQueueToNext;
	protected List<ScheduledEvent> myEvents;
	
	public static NewScheduler getInstance()
	{
		if (null == ourInstance)
			startSystem();
		
		return ourInstance;
	}
	
	synchronized public static void startSystem()
	{
		if (null != ourInstance)
			return;
		
		ourInstance = new NewScheduler();
		ourInstance.start();
	}
	
	
	public void processException(Exception e)
	{
		e.printStackTrace();
	}
	
	
	public static final long CYCLE_TIME = 2000;
	
	protected synchronized void processInterrupt (InterruptedException e)
	{
		;
	}

	
	protected synchronized void process() throws Exception
	{
		deliverEvents();
		long sleepTime = getNextSleepTime();
		
		if (sleepTime < CYCLE_TIME)
			sleepTime = CYCLE_TIME;
		
		wait(sleepTime);
	}


	
	protected void deliverEvents()
	{
		ScheduledEvent event = null;
		if (myEvents.size() > 0)
			event = myEvents.get(0);
		
		long now = System.currentTimeMillis();
		while (null != event && event.scheduledTime > now)
		{
			myQueueToNext.remove(event.queue);
			ScheduledEvent temp = myEvents.remove(0);
			if (temp != event)
			{
				throw new RuntimeException("impossible state!");
			}
			
			event.queue.put(event);
			event = null;
			if (myEvents.size() > 0)
				event = myEvents.get(0);
		}
	}
	
	
	protected long getNextSleepTime() throws InterruptedException
	{
		ScheduledEvent event = null;
		if (myEvents.size() > 0)
			event = myEvents.get(0);
		
		long now = System.currentTimeMillis();
		long wakeTime = now + CYCLE_TIME;
		if (null != event && event.scheduledTime < wakeTime)
			wakeTime = event.scheduledTime;
		
		long sleepTime = wakeTime - now;
		
		return sleepTime;
	}
	
	
	public synchronized void notifyAt (SharedQueue queue, long time)
	{
		if (null == queue)
			throw new IllegalArgumentException();
		
		ScheduledEvent event = new ScheduledEvent(time, queue, null);
		ScheduledEvent old = myQueueToNext.get(event.queue);
		if (null != old)
		{
			myEvents.remove(old);
		}
			
		myQueueToNext.put(queue, event);
		myEvents.add(event);
		Collections.sort(myEvents);
	}
	
	
	public synchronized void cancel (SharedQueue queue)
	{
		ScheduledEvent event = myQueueToNext.get(queue);
		if (null != event)
		{
			myQueueToNext.remove(queue);
			myEvents.remove(event);
		}
	}


	protected void implementOneCycle() throws Exception
	{
		myState = ThreadStates.Running;
		ThreadStates currentStatus = myState;
		
		while (ThreadStates.Running == currentStatus)
		{
			try
			{
				process();
			}
			catch (InterruptedException e)
			{
				processInterrupt(e);
			}
			finally
			{
				currentStatus = myState;
			}
		}
	}
}
