package com.lts.application.event;

import com.lts.application.Application;
import com.lts.event.Callback;

/**
 * A callback that wraps its call in a try...catch block
 * 
 * @author cnh
 */
abstract public class ApplicationCallback implements Callback
{
	abstract protected void basicCallback(Object data) throws Exception;
	
	public void callback(Object data)
	{
		try
		{
			basicCallback(data);
		}
		catch (Exception e)
		{
			Application.showException(e);
		}
	}

}
