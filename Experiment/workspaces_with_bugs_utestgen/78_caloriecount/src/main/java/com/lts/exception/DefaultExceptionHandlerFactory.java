package com.lts.exception;

public class DefaultExceptionHandlerFactory implements ExceptionHandlerFactory
{
	private ExceptionHandler myExceptionHandler;
	
	public ExceptionHandler getExceptionHandler()
	{
		if (null == myExceptionHandler)
			initializeExceptionHandler();
		
		return myExceptionHandler;
	}
	
	
	public void setExceptionHandler(ExceptionHandler handler)
	{
		myExceptionHandler = handler;
	}

	public synchronized void initializeExceptionHandler()
	{
		if (null == myExceptionHandler)
		{
			myExceptionHandler = new DefaultExceptionHandler();
		}
	}
	 
}
