package com.lts.swing.thread;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * Display a Component in another thread, then perform an activity.
 * 
 * <P>
 * This class creates a new thread that displays a component, waits for it to 
 * close, and then calls the {@link #afterDisplay()} method.  By default, the 
 * afterDisplay method does nothing, so those clients that just want to show 
 * a component in another thread can use this class as is.  
 * </P>
 * 
 * <P>
 * Clients that want to do something after displaying the Component can define
 * a new version of this class, often with an anonymous inner method, that 
 * performs some action.
 * </P>
 * 
 * <P>
 * Suppose you want to display a window to edit some information.  The user can
 * close the window by clicking on an OK or Cancel button.  Based on how the 
 * window closes, you want to either accept or discard the edits.
 * </P>
 * 
 * <P>
 * Here is one way to implement that scenario with this class:
 * </P>
 * 
 * <CODE>
 * <PRE>
 * JFrame myFrame;
 * 
 * void foo()
 * {
 *     myFrame = createAndInitializeFrameMehtod();
 *     BlockThread thread = new BlockThread() {
 *         protected void afterDisplay() {
 *             processResults();
 *         }
 *     }
 *     
 *     thread.display();
 * }
 * 
 * 
 * void processResults()
 * {
 *     &lt;do something useful with the results of the edit&gt;;
 * }
 * </PRE>
 * </CODE>
 * 
 * <P>
 * NOTE THAT display() RETURNS IMMEDIATELY!!  It does not mean that the window 
 * has closed!  Use the afterDisplay method for that.
 * </P>
 *         
 * 
 * @author cnh
 *
 */
public class BlockThread extends ComponentAdapter implements Runnable
{
	private Component myComponent;
	private Thread myThread;
	
	private static int ourCount = 0;
	
	synchronized private static int getCountAndIncrement()
	{
		return ourCount++;
	}
	
	
	public BlockThread(Component component)
	{
		String name = "blocking thread " + getCountAndIncrement();
		myThread = new Thread(this, name);
		myComponent = component;
	}
	
	synchronized public void display (Component component)
	{
		setComponent(component);
		display();
	}
	
	
	private void setComponent(Component component)
	{
		myComponent = component;
	}


	synchronized public void display()
	{
		if (null == myComponent)
		{
			String msg = "No component has been set to display";
			throw new IllegalStateException(msg);
		}
		
		myThread.start();
	}
	
	synchronized public void componentHidden(ComponentEvent event)
	{
		notify();
	}

	synchronized public void run()
	{
		myComponent.addComponentListener(this);
		try
		{
			myComponent.setVisible(true);
			wait();
			afterDisplay();
		}
		catch (InterruptedException e)
		{
		}
		myComponent.removeComponentListener(this);
	}
	
	
	protected void afterDisplay()
	{
		
	}
}
