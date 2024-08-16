/**
 * 
 */
package com.lts.event;

/**
 * A class that executes a Runnable and checks for exceptions.  
 * 
 * <P>
 * If an exception is encountered, {@link #processException(Exception)} is called
 * to handle the exception.
 * </P>
 * 
 * @author cnh
 *
 */
public class ActionRunner implements Runnable
{
	public SimpleThreadedAction myAction;
	
	public ActionRunner(SimpleThreadedAction action)
	{
		myAction = action;
	}
	
	public void run()
	{
		try
		{
			myAction.action();
		}
		catch (Exception e)
		{
			processException(e);
		}
	}
	
	public void processException(Exception e)
	{
		e.printStackTrace();
	}
}