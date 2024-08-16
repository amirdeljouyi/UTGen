package com.lts.application.swing;

import com.lts.application.Application;
import com.lts.swing.thread.ActionThread;

abstract public class ApplicationActionThread extends ActionThread
{
	abstract protected void action() throws Exception;
	
	@Override
	public void run()
	{
		try
		{
			action();
		}
		catch (Exception e)
		{
			Application.showException(e);
		}
	}
}
