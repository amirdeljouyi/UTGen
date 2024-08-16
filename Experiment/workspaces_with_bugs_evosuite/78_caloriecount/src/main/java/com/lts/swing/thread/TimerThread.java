package com.lts.swing.thread;


/**
 * A thread that loops endlessly in a sleep-process cycle.
 * 
 * <P>
 * The amount of time to sleep is specified by either overriding the 
 * {@link #getSleepTime()} method or by setting the {@link #mySleepTime} 
 * property.
 * </P>
 * 
 * <P>
 * Subclasses define what the thread actually does during each cycle by defining
 * the {@link #process()} method.
 * 
 * @author cnh
 */
public abstract class TimerThread implements Runnable
{
	abstract protected void process() throws Exception;
	
	protected Thread myThread;
	protected Long mySleepTime;
	protected boolean myKeepGoing;
	
	final static public long DEFAULT_SLEEP_TIME = 500;
	
	public boolean keepGoing()
	{
		return myKeepGoing;
	}
	
	synchronized void setKeepGoing(boolean keepGoing)
	{
		myKeepGoing = keepGoing;
	}
	
	
	public long getSleepTime()
	{
		return mySleepTime;
	}
	
	synchronized public void setSleepTime(long time)
	{
		mySleepTime = time;
	}
	
	public TimerThread()
	{
		initialize(null);
	}
	
	public TimerThread(String name)
	{
		initialize(name);
	}
	
	public void initialize(String name)
	{
		if (null == name)
			name = "Timer Thread";
		
		myThread = new Thread(this, name);
		myKeepGoing = true;
		
		if (null == mySleepTime)
			mySleepTime = DEFAULT_SLEEP_TIME;
	}
	
	public void startThread()
	{
		myThread.start();
	}
	
	
	public void run ()
	{
		try
		{
			loop();
		}
		catch (Exception e)
		{
			processException(e);
		}
	}
	
	
	protected void processException(Throwable t)
	{
		t.printStackTrace();
	}
	
	protected void loop() throws Exception
	{
		while (keepGoing())
		{
			process();
			sleep();
		}
	}

	protected void sleep()
	{
		try
		{
			long sleepTime = getSleepTime();
			synchronized (myThread)
			{
				myThread.wait(sleepTime);
			}
		}
		catch (InterruptedException e)
		{
			processInterrupted(e);
		}
	}

	protected void processInterrupted(InterruptedException e)
	{
		; // default behavior is to simply wake up early.
	}
}
