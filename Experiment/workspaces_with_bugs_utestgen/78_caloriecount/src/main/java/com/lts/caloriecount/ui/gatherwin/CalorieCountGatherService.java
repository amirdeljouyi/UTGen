package com.lts.caloriecount.ui.gatherwin;

import com.lts.util.DefaultSharedQueue;
import com.lts.util.SharedQueue;
import com.lts.util.scheduler.WorkPauseThread;

/**
 * 
 * @author cnh
 */
public class CalorieCountGatherService extends WorkPauseThread
{
	protected long myNextGatherTime;
	protected long myCycleTime;
	protected SharedQueue myQueue;
	protected long myMaxPauseTime;
	static private CalorieCountGatherService ourInstance;
	protected boolean myGenerateEvents;
	private GatherUIThread myUIThread;
	
	static public CalorieCountGatherService getInstance()
	{
		return ourInstance;
	}
	
	static public void createInstance(SharedQueue queue)
	{
		ourInstance = new CalorieCountGatherService(queue);
	}
	
	
	public CalorieCountGatherService(SharedQueue sharedQueue)
	{
		initialize(sharedQueue);
	}
	
	
	protected void initialize(SharedQueue sharedQueue)
	{
		myQueue = sharedQueue;
		myMaxPauseTime = 5000;
		myCycleTime = 10 * 60 * 1000;
		myPauseTime = 5000;
		long now = System.currentTimeMillis();
		myNextGatherTime = calculateNextGatherTime(now);
		myGenerateEvents = false;
		super.initialize("Generator");
	}
	
	public long getNextGatherTime()
	{
		return  myNextGatherTime;
	}
	
	synchronized public Long nextPollOrNull()
	{
		Long time = null;
		
		if (myGenerateEvents)
			time = myNextGatherTime;
		
		return time;
	}
	
	public void setNextGatherTime(long value)
	{
		myNextGatherTime = value;
	}
	
	
	public long getCycleTime()
	{
		return myCycleTime;
	}
	
	public void setCycleTime(long value)
	{
		myCycleTime = value;
	}
	
	
	@Override
	protected long getMaxPauseTime()
	{
		return myMaxPauseTime;
	}

	@Override
	protected void process()
	{
		long now = System.currentTimeMillis();
		if (now > myNextGatherTime && myGenerateEvents)
		{
			Long time = new Long(myNextGatherTime);
			myQueue.put(time);
			myNextGatherTime = calculateNextGatherTime();
		}
	}

	protected long calculateNextGatherTime()
	{
		return calculateNextGatherTime(1 + myNextGatherTime);
	}
	
	
	protected long calculateNextGatherTime(long startingFrom)
	{
		return roundUp(startingFrom, myCycleTime);
	}
	
	
	public static long roundDown (long value, long modulus)
	{
		long temp = value / modulus;
		temp = temp * modulus;
		
		return temp;
	}
	
	
	public static long roundUp (long value, long modulus)
	{
		long rem = value % modulus;
		
		if (0 != rem)
		{
			long temp = 1 + (value / modulus);
			value = temp * modulus;
		}
	
		return value;
	}

	public void setGenerateEvents(Long cycleTime)
	{
		if (null != cycleTime && !myGenerateEvents)
		{
			myCycleTime = cycleTime;
			myNextGatherTime = calculateNextGatherTime(System.currentTimeMillis());
		}
		
		myGenerateEvents = (null != cycleTime);
	}

	synchronized public void gatherNow()
	{
		Long time = System.currentTimeMillis();
		myQueue.put(time);
	}
	
	
	public void startService()
	{
		myQueue = new DefaultSharedQueue();
		CalorieCountGatherService.createInstance(myQueue);
		CalorieCountGatherService.getInstance().start();
		myUIThread = new GatherUIThread(myQueue);
		myUIThread.start();
	}
}
