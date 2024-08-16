package com.lts.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


abstract public class SimpleThreadedAction implements ActionListener
{
	abstract public void action() throws Exception;
	
	static protected Executor ourExecutor;
	
	protected Runnable myRunnable;
	
	synchronized static protected void initializeExecutor()
	{
		if (null != ourExecutor)
			return;
		
		ourExecutor = Executors.newCachedThreadPool();
	}
	
	static protected Executor getExecutor()
	{
		if (null == ourExecutor)
			initializeExecutor();
		
		return ourExecutor;
	}
	
	
	protected SimpleThreadedAction ()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		myRunnable = new ActionRunner(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		getExecutor().execute(myRunnable);
	}
}
