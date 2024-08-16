package com.lts.exception;

/**
 * 
 * @author cnh
 *
 */
abstract public class ExceptionHandler
{
	abstract public void instanceProcessException(Exception e);
	
	private static ExceptionHandlerFactory ourFactory;
	
	public static ExceptionHandler getHandler()
	{
		if (null == ourFactory)
			initializeFactory();
		
		return ourFactory.getExceptionHandler();
	}
	
	protected static synchronized void initializeFactory()
	{
		if (null == ourFactory)
			ourFactory = new DefaultExceptionHandlerFactory();
	}
	
	protected static synchronized void setFactory(ExceptionHandlerFactory factory)
	{
		ourFactory = factory;
	}
	
	public static void processException(Exception e)
	{
		getHandler().instanceProcessException(e);
	}
	
	protected static ExceptionHandlerFactory getFactory()
	{
		if (null == ourFactory)
			initializeFactory();
		
		return ourFactory;
	}
}
