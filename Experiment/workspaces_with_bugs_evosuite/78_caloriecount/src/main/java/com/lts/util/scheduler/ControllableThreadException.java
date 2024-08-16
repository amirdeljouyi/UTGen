package com.lts.util.scheduler;

public class ControllableThreadException extends Exception
{
	private static final long serialVersionUID = 1L;

	private boolean myTerminateThread;
	
	public ControllableThreadException(boolean terminateThread)
	{
		super();
		myTerminateThread = terminateThread;
	}
	
	public ControllableThreadException(boolean terminateThread, String message)
	{
		super(message);
		myTerminateThread = terminateThread;
	}
	
	public ControllableThreadException(boolean terminateThread, Throwable cause)
	{
		super(cause);
		myTerminateThread = terminateThread;
	}
	
	public ControllableThreadException(boolean terminateThread, String message, Throwable cause)
	{
		super(message,cause);
		myTerminateThread = terminateThread;
	}

	public boolean terminateThread()
	{
		return myTerminateThread;
	}
}
