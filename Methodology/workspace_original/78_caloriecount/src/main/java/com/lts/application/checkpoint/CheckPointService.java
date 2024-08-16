package com.lts.application.checkpoint;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.util.scheduler.ControllableThreadException;
import com.lts.util.scheduler.WorkPauseThread;

/**
 * A Thread that periodically creates a checkpoint for the system.
 * 
 * @author cnh
 */
public class CheckPointService extends WorkPauseThread
{
	protected static CheckPointService ourInstance;
	
	protected Application myApp;
	
	protected CheckPointService()
	{
		initialize();
	}
	
	public String getName()
	{
		return "Checkpoint";
	}
	
	
	public static CheckPointService getInstance()
	{
		if (null == ourInstance)
			createInstance();
		
		return ourInstance;
	}
	
	
	synchronized protected static void createInstance()
	{
		if (null != ourInstance)
			return;
		
		ourInstance = new CheckPointService();
	}
	
	
	protected void initialize()
	{
		myPauseTime = 2 * 60 * 1000;
	}
	
	@Override
	protected void process() throws ControllableThreadException
	{
		try
		{
			Application.getInstance().checkPoint();
		}
		catch (ApplicationException e)
		{
			throw new ControllableThreadException(true, e);
		}
	}

	public static void startService(Application app)
	{
		getInstance().setApplication(app);
		getInstance().start();
	}
	
	private void setApplication(Application app)
	{
		myApp = app;
	}

	public static void stopService()
	{
		getInstance().stopThread();
	}

	@Override
	protected long getMaxPauseTime()
	{
		return 300000;
	}
}
