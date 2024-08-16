package com.lts.util.scheduler;

import com.lts.util.scheduler.NewScheduler.ScheduledEvent;

/**
 * A QueueReader that receives ScheduledEvents and calls scheduledEvent in 
 * response.
 * 
 * @author cnh
 *
 */
abstract public class SchedulerQueueReader extends QueueReader
{
	abstract protected void scheduledEvent (ScheduledEvent event) throws Exception;
	
	@Override
	protected void process(Object o) throws Exception
	{
		ScheduledEvent event = (ScheduledEvent) o;
		scheduledEvent(event);
	}
}
