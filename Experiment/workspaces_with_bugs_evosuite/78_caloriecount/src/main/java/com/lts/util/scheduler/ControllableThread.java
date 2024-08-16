package com.lts.util.scheduler;



abstract public class ControllableThread implements Runnable
{
	abstract protected void implementOneCycle() throws Exception;
	
	public enum ThreadStates
	{
		Initialized,
		Starting,
		Running,
		Stopped,
		Stopping,
		Suspending,
		Suspended,
		Wait
	}
	
	
	protected Thread myThread;
	protected ThreadStates myState;
	protected String myName;
	
	protected ControllableThread()
	{
		initialize(null);
	}
	
	protected ControllableThread(String description)
	{
		initialize(description);
	}
	
	protected void initialize(String description)
	{
		myState = ThreadStates.Initialized;
		if (null == description)
			myThread = new Thread(this);
		else
			myThread = new Thread(this, description);
	}
	
	
	/**
	 * Execute the thread.
	 * 
	 * <P>
	 * This method is called by {@link Thread#start()}.  It sets the state to 
	 * {@link ThreadStates#Running} and then goes into a loop, calling 
	 * {@link #threadKeepRunning()} and then {@link #threadCycle()}.
	 * </P>
	 * 
	 * <P>
	 * If threadCycle throws an exception, the method calls 
	 * {@link #processException(Exception)}.
	 * </P>
	 * 
	 * <P>
	 * When the loop terminates, the method sets the state to 
	 * {@link ThreadStates#Stopped}.
	 * </P>
	 */
	synchronized public void run()
	{		
		try
		{
			myState = ThreadStates.Running;
			
			while (threadKeepRunning())
			{
				threadCycle();
			}
		}
		catch (Exception e)
		{
			threadException(e);
		}
		finally
		{
			myState = ThreadStates.Stopped;
		}
	}
	
	
	protected boolean threadKeepRunning()
	{
		return 
			myState != ThreadStates.Stopped
			&& myState != ThreadStates.Stopping;
	}
	

	/**
	 * Perform one loop iteration.
	 * 
	 * <P>
	 * This method is executed once for each cycle the thread goes through.  
	 * The action that the method takes depends on the thread state:
	 * <UL>
	 * <LI>{@link ThreadStates#Initialized}, {@link ThreadStates#Stopped} - 
	 * call {@link #throwIllegalStateException()}</LI>
	 * <LI>{@link ThreadStates#Running} - {@link #implementOneCycle()}</LI>
	 * <LI>{@link ThreadStates#Starting}, {@link ThreadStates#Suspended} - go into
	 * the {@link ThreadStates#Running} state.</LI>
	 * <LI>{@link ThreadStates#Stopped} - throwIllegalStateException.</LI>
	 * <LI>{@link ThreadStates#Stopping} - ignore</LI>
	 * <LI>{@link ThreadStates#Suspending} - {@link #threadSuspend()}.</LI>
	 * <LI>default - call throwIllegalStateExcpetion</LI>
	 * </UL>
	 * 
	 * This method executes a loop until the {@link #threadKeepRunning()} method returns 
	 * false.  While that method 
	 * </P>
	 * 
	 * @throws IllegalStateException This is thrown by throwIllegalStateException
	 * to indicate that thread is in, well, an illegal state.
	 * @throws Exception This can be thrown by threadOneCycle.
	 */
	synchronized protected void threadCycle() throws Exception
	{
		switch (myState)
		{
			case Initialized :
			case Stopped :
				throwIllegalStateException();
				break;
				
			case Running :
				implementOneCycle();
				break;
				
			case Starting :
			case Suspended :
				myState = ThreadStates.Running;
				break;
				
			case Stopping :
				break;
				
			case Suspending :
				threadSuspend();
				break;
				
			default :
				throwIllegalStateException();
				break;
		}
	}


	/**
	 * Suspend the thread for an indefinite period of time.
	 * 
	 * <P>
	 * This method should only be called when the caller is confident that the 
	 * current state is appropriate --- the method does not make any checks of its
	 * own.
	 * </P>
	 * 
	 * <P>
	 * The method sets the state to {@link ThreadStates#Suspended} and then calls
	 * {@link Object#wait()}.
	 * </P>
	 * 
	 * <P>
	 * The preferred method for resuming a thread in this state is to call 
	 * {@link #resume()}.  In addition, calling {@link Object#notify()} or 
	 * {@link Object#notifyAll()} will cause a resume.
	 * </P>
	 * 
	 * <P>
	 * If an {@link InterruptedException} occurs while suspended, the method calls 
	 * {@link #threadException(Exception)}.
	 * </P>
	 * 
	 * <P>
	 * If the method returns normally, it will set the state to 
	 * {@link ThreadStates#Running}.  
	 * </P>
	 * 
	 * @exception Exception This can occur if threadInterrupt throws an exception.
	 */
	synchronized protected void threadSuspend() throws Exception
	{
		try
		{
			myState = ThreadStates.Suspended;
			wait();
		}
		catch (InterruptedException e)
		{
			threadInterrupt(e);
		}
		myState = ThreadStates.Running;
	}
	
	
	synchronized public void noMethod()
	{
		switch (myState)
		{
			case Initialized :
				break;
				
			case Running :
				break;
				
			case Starting :
				break;
				
			case Stopped :
				break;
				
			case Stopping :
				break;
				
			case Suspended :
				break;
				
			case Suspending :
				break;
				
			default :
				throwIllegalStateException();
				break;
		}
	}
	
	/**
	 * Ask the thread to suspend.
	 * 
	 * <P>
	 * The actions of this method depend on the state of the thread when it is called:
	 * <UL>
	 * <LI>
	 * {@link ThreadStates#Initialized}, {@link ThreadStates#Running}, 
	 * or {@link ThreadStates#Starting}: set state to {@link ThreadStates#Suspending}.
	 * </LI>
	 * 
	 * <LI>
	 * {@link ThreadStates#Stopped}, unrecognized: throw {@link IllegalStateException}.
	 * </LI>
	 * 
	 * <LI>
	 * {@link ThreadStates#Stopping}, {@link ThreadStates#Suspended}, 
	 * or {@link ThreadStates#Suspending}: ignore.
	 * </LI>
	 * 
	 * </UL>
	 * </P>
	 * 
	 * <P>
	 * Note that this method makes a request of thread, it does not perform the action
	 * immediately.  Barring unforeseen circumstances, the request is acted upon during
	 * the next call to {@link #threadCycle()}.
	 * </P>
	 */
	synchronized public void suspend()
	{
		switch (myState)
		{
			case Initialized:
			case Running:
			case Starting:
				myState = ThreadStates.Suspending;
				break;

			case Stopped:
				throwIllegalStateException();
				break;

			case Stopping:
			case Suspended:
			case Suspending:
			case Wait:
				break;

			default:
				throwIllegalStateException();
				break;
		}
	}
	
	/**
	 * Ask the thread to resume.
	 * 
	 * <P>
	 * The actions of this method depend on the state of the thread when it is called:
	 * <UL>
	 * <LI>
	 * {@link ThreadStates#Running}, {@link ThreadStates#Starting}, 
	 * or {@link ThreadStates#Stopping}: ignore.
	 * </LI>
	 * 
	 * <LI>
	 * {@link ThreadStates#Suspending}: set state to Running.
	 * </LI>
	 * 
	 * <LI>
	 * {@link ThreadStates#Suspended}: call {@link #threadResume()}.
	 * </LI>
	 * 
	 * <LI>
	 * {@link ThreadStates#Initialized}, {@link ThreadStates#Stopped}, 
	 * unrecognized: throw {@link IllegalStateException}.
	 * </LI>
	 * </UL>
	 * </P>
	 * 
	 * <P>
	 * Note that this may not cause the thread to resume immediately.  Owing to 
	 * the nature of Java threads, the actual target may take some time before 
	 * actually running.
	 * </P>
	 */	
	synchronized public void resume()
	{
		switch (myState)
		{
			case Running :
			case Starting :
			case Stopping :
				break;
				
			case Suspended :
				threadResume();
				break;
				
			case Suspending :
			case Wait :
				myState = ThreadStates.Running;
				break;
				
			case Initialized :
			case Stopped :
			default :
				throwIllegalStateException();
				break;
		}
	}


	/**
	 * Cause the thread to return from a call to Object.wait.
	 * 
	 * <P>
	 * This method assumes that caller has ensured that the thread is in an appropriate
	 * state before calling this method --- it does not check the state itself.
	 * </P>
	 * 
	 * <P>
	 * The default implementation simply calls {@link Object#notify()}, causing a call
	 * to {@link #threadSuspend()} to return.
	 * </P>
	 */
	synchronized protected void threadResume()
	{
		notify();
	}


	protected void throwIllegalStateException()
	{
		Exception e = new Exception();
		e.fillInStackTrace();
		StackTraceElement[] stack = e.getStackTrace();
		StackTraceElement previous = stack[1];
		
		String msg =
			previous.getMethodName()
			+ " called while in state "
			+ myState;
		
		throw new IllegalStateException(msg);
	}


	public synchronized void start()
	{
		switch (myState)
		{
			case Initialized :
				threadStartThread();
				break;

			case Suspended :
			case Suspending :
			case Running :
			case Starting :
			case Wait :
				break;
				
			case Stopping :
			case Stopped :
			default :
				throwIllegalStateException();
				break;
		}
	}


	/**
	 * Create and start the thread.
	 * <P>
	 * This is the preferred way of starting up an instance of this class.
	 * </P>
	 * 
	 * <P>
	 * The method creates an instance of {@link Thread}, setting the name of the 
	 * thread to the value returned by {@link #getName()}, initializes the value
	 * returned by {@link ControllableThread#myThread} to that value, and then 
	 * calls {@link Thread#start()} on the thread.
	 * </P>
	 */
	synchronized protected void threadStartThread()
	{
		String s = getName();
		if (null == s)
			myThread = new Thread(this);
		else
			myThread = new Thread(this, s);
		
		myState = ThreadStates.Starting;
		myThread.start();
	}
	
	
	public synchronized void stop ()
	{
		switch (myState)
		{
			case Initialized :
				myState = ThreadStates.Stopped;
				break;
				
			case Running :
			case Starting :
			case Suspending :
			case Wait :
				myState = ThreadStates.Stopping;
				notify();
				break;
			
			case Suspended :
				threadResume();
				myState = ThreadStates.Stopping;
				break;
				
			case Stopped :
			case Stopping :
				break;
				
			default :
				throwIllegalStateException();
				break;
		}
	}
	
	
	protected void threadException(Exception e)
	{
		e.printStackTrace();
	}
	
	
	protected synchronized void threadInterrupt (InterruptedException e) throws InterruptedException
	{
		switch (myState)
		{
			case Initialized :
			case Stopped :
			case Starting :
			case Stopping :
				throwIllegalStateException();
				break;
				
			case Running :
				throw e;

			case Suspended :
			case Suspending :
			case Wait :
				myState = ThreadStates.Running;
				break;
				
			default :
				throwIllegalStateException();
				break;
		}
	}
	
	public Thread getThread()
	{
		return myThread;
	}

	public String getName()
	{
		return myName;
	}
}
