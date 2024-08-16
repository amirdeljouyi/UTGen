package com.lts.caloriecount.ui.dayview;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ComponentEvent;

import com.lts.application.swing.WindowComponentListener;

public class DayViewRefreshThread extends WindowComponentListener
	implements Runnable
{
	public enum ThreadStates
	{
		/**
		 * The window is not visible, but it may become visible in the future.
		 */
		Paused,
		
		/**
		 * The window is active and the thread should use it's fields to be 
		 * refreshed.
		 */
		Displaying,
		
		/**
		 * The window has been destroyed, discarded, etc.  The thread should 
		 * terminate. 
		 */
		Terminating
	}
	
	public DayView myView;
	public ThreadStates myState;
	public Component myComponent;
	private long mySleepTime;
	
	private DayViewRefreshThread(DayView view, Component component)
	{
		mySleepTime = 15000;
		myView = view;
		myState = ThreadStates.Paused;
		myComponent = component;
		
		if (myComponent instanceof Window)
		{
			Window window = (Window) myComponent;
			window.addWindowListener(this);
		}
		
		myComponent.addComponentListener(this);
	}
	
	
	public void run ()
	{
		try
		{
			while (myState != ThreadStates.Terminating)
			{
				performOneInterator();
			}
		}
		catch (InterruptedException e)
		{
			String msg = "Interrupted while executing";
			throw new RuntimeException(msg, e);
		}
	}


	synchronized private void performOneInterator() throws InterruptedException
	{
		switch (myState)
		{
			case Displaying :
				myView.refresh();
				wait(mySleepTime);
				break;
				
			case Paused :
				wait();
				break;
				
			case Terminating :
				break;
				
			default :
			{
				String msg = "Unrecognized state " + myState;
				throw new RuntimeException(msg);
			}
		}
	}
	
	
	synchronized public void windowClosingOrHidden(ComponentEvent event)
	{
		myState = ThreadStates.Terminating;
		notify();
	}
	
	synchronized public void windowOpenedOrDisplayed(ComponentEvent event)
	{
		myState = ThreadStates.Displaying;
		notify();
	}
	
	synchronized public void stop()
	{
		myState = ThreadStates.Terminating;
		notify();
	}
	
	
	public static DayViewRefreshThread launch(DayView dayView, Component component)
	{
		DayViewRefreshThread dvrt = new DayViewRefreshThread(dayView, component);
		Thread thread = new Thread(dvrt,"DayView Refresh Thread");
		thread.start();
		return dvrt;
	}
}
