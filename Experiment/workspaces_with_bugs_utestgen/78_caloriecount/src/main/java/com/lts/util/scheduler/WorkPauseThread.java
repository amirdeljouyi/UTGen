package com.lts.util.scheduler;

/**
 * A ControllableThread that pauses in between calls to process.
 * <H3>Quick Start</H3>
 * <UL>
 * <LI>Define {@link #process()} to do some meaningful work.
 * <LI>Override {@link #initialize()} to set {@link #myPauseTime}.
 * </UL>
 * 
 * <H3>Description</H3>
 * This class is a framework for "background" style services and the like.  The 
 * class creates a thread 
 * <P>
 * @author cnh
 *
 */
abstract public class WorkPauseThread extends ControllableThread
{
	abstract protected void process() throws ControllableThreadException;

	protected Throwable myLastException;
	protected long myPauseTime;
	protected boolean myProcessThenPause;
	
	public boolean getProcessThenPause()
	{
		return myProcessThenPause;
	}
	
	
	public boolean processThenPause()
	{
		return getProcessThenPause();
	}
		
	public Throwable getLastException()
	{
		return myLastException;
	}
	
	public void resetLastException()
	{
		myLastException = null;
	}
	
	protected long getMaxPauseTime()
	{
		return 5000;
	}
	
	protected WorkPauseThread()
	{
	}
	
	
	/**
	 * Initialize the name and pauseTime to the passed values.
	 * <P>
	 * 
	 * @param name The name for the thread as returned by {@link Thread#getName()}.
	 * @param pauseTime The number of milliseconds to pause (via {@link Object#wait(long)},
	 * in between calls to {@link #process()}.
	 */
	protected void initialize(String name, long pauseTime)
	{
		if (null == name)
			throw new IllegalArgumentException("null thread name");
		
		myName = name;
		myPauseTime = pauseTime;
	}
	
	
	public long getPauseTime()
	{
		return myPauseTime;
	}
	
	protected void setPauseTime(long pauseTime)
	{
		myPauseTime = pauseTime;
	}
	
	/**
	 * Should the thread pause?
	 * <H3>Note</H3>
	 * If this method always returns false, the thread will never pause unless the 
	 * process method somehow causes it to do so.  Generally speaking, this method 
	 * should either always return true (default implementation), or conditionally
	 * return true.
	 * 
	 * <H3>Description</H3>
	 * This method is called once per "iteration" of the {@link ControllableThread#implementOneCycle()}
	 * method.  See the loop method for details, this method is basically called 
	 * between each call to the {@link #process} method.
	 * 
	 * @return true if the thread should pause in between a call to {@link #process},
	 * false otherwise.
	 */
	protected boolean shouldPause()
	{
		return true;
	}
	
	
	/**
	 * Call process, shouldPause and then wait, if appropriate.
	 * 
	 * <H3>Synchronized</H3>
	 * This is a synchronized method --- calling it will cause the caller to pause until
	 * it obtains the monitor for the instance.
	 * 
	 * <H3>Description</H3>
	 * This method loops until the status for the loop is set to something other 
	 * than {@link ControllableThread#STATUS_RUNNING}.  In the loop it performs the
	 * following actions:
	 * <UL>
	 * <LI>Call {@link #process()}.
	 * <LI>Call {@link #shouldPause()}.
	 * <LI>If shouldPause returned true...
	 * <UL>
	 * <LI>Get the pause time by calling {@link #getPauseTime()}.
	 * <LI>Call {@link Object#wait(long)} with the pause time.
	 * </UL>
	 * </UL>
	 */
	synchronized protected void implementOneCycle() throws Exception
	{
		processAndPause();
	}

	protected void processAndPause() throws InterruptedException
	{
		try
		{
			resetLastException();
			if (processThenPause())
			{
				process();
				performPause();
			}
			else
			{
				performPause();
				process();
			}
		}
		catch (InterruptedException e)
		{
			threadInterrupt(e);
		}
		catch (ControllableThreadException e)
		{
			if (e.terminateThread())
				myState = ThreadStates.Stopping;
		}
	}

	/**
	 * Pause the thread for a period of time.
	 * 
	 * <P>
	 * The method will only pause the thread if {@link #shouldPause()} returns true.
	 * </P>
	 * 
	 * <P>
	 * The method determines the minimum time to pause by calling 
	 * {@link #getPauseTime()}.  If the value returned is less than or equal to 0,
	 * the method will use {@link Object#wait()} --- it will pause for an indefinite
	 * period of time.  If a subclass does not want to pause, override shouldPause 
	 * to return false, do not return 0 for getPauseTime.
	 * </P>
	 * 
	 * <P>
	 * If a value greater than 0 is returned by getPauseTime, then the method will
	 * compare that value with what {@link #getMaxPauseTime()} returns.  The smaller
	 * of the two values will be used.
	 * </P>
	 * 
	 * <P>
	 * If the thread needs to be unpaused, the preferred method to call is 
	 * {@link #wakeThread()}, but {@link Object#notify()} or {@link Object#notifyAll()}
	 * can also be used. 
	 * </P>
	 * 
	 * <P>
	 * Calling {@link Thread#interrupt()} on the thread will cause this method to 
	 * throw an {@link InterruptedException}.
	 * </P>
	 * 
	 * @throws InterruptedException If the thread is interrupted while pausing.
	 */
	protected void performPause() throws InterruptedException
	{
		if (shouldPause())
		{
			long thePauseTime = getPauseTime();
			if (0 >= thePauseTime)
				wait();
			else
			{
				long max = getMaxPauseTime();
				if (thePauseTime > max)
					thePauseTime = max;
				
				wait(thePauseTime);
			}
		}
	}

	/**
	 * Asks the thread to stop.
	 */
	synchronized public void stopThread ()
	{
		if (null == myThread)
			return;
		
		Thread.State state = myThread.getState();
		if (Thread.State.TERMINATED != state)
		{
			stop();
		}
	}
	
	
	synchronized public void wakeThread()
	{
		notifyAll();
	}
}
