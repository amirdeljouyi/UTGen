package com.lts.swing.thread;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Turn a JFrame into a dialog-like thread.
 * 
 * <H3>Quick Start</H3>
 * <CODE>
 * <PRE>
 * void someButtonPressed()
 * {
 *     BlockingThread thread = new BlockingThread() {
 *         public void run() {
 *             actuallyDoSomething();
 *         }
 *     };
 *     thread.start();
 * }
 * 
 * void actuallyDoSomething()
 * {
 *     JFrame frame = new JFrame();
 *     FooPane pane = new FooPane(frame);
 *     BlockingThread.displayAndWait(frame);
 *     // we only get here after the the JFrame has closed
 *     if (pane.accepted())
 *     {
 *         ...
 *     }
 * }
 * </PRE>
 * </CODE>
 * 
 * <P>
 * This method creates a thread that can block without causing the UI to hang.  The
 * primary purpose of the class is to call {@link #waitForClose(PestWindow)}, which 
 * will wait for the Window containing the specified PestWindow to close and then
 * resume execution.  
 * </P>
 * 
 * <H3>Notes</H3>
 * This class has a bit of a problem with race conditions because it wants to pause
 * on a window closing.  The problem is that, immediately after calling Window.setVisible(true), 
 * it is possible for the user to view the window and close it before the object 
 * gets to call Object.wait().  
 * 
 * <P>
 * The solution is to use a flag that signals if the window has been closed, and 
 * to set or query that flag inside synchronized methods.  The method that checks 
 * the flag must be synchronized in order to call Object.wait(), so this is not a 
 * problem when displaying the frame.
 * </P>
 * 
 * <P>
 * The method that sets the flag when the monitored frame closes, 
 * {@link #wakeUp()}, must also be synchronized to ensure that this object does
 * not wait on a frame that is already closed.
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class BlockingThread implements Runnable
{
	abstract public void run();
	
	protected Thread myThread;
	protected boolean myFrameClosed;
	
	public BlockingThread()
	{
		initialize("Blocking Thread");
	}
	
	public BlockingThread(String name)
	{
		initialize(name);
	}
	
	public void initialize(String name)
	{
		if (null == name)
			name = "Blocking Thread";
		
		myThread = new Thread(this, name);
		myFrameClosed = false;
		addBlockingThread(this);
	}
	
	
	synchronized public void displayAndWait (Component comp)
	{
		ComponentAdapter capt = new ComponentAdapter() {
			public void componentHidden(ComponentEvent event) {
				wakeUp();
			}
		};

		myFrameClosed = false;
		comp.addComponentListener(capt);
		comp.setVisible(true);
		
		try
		{
			if (!myFrameClosed)
				this.wait();
		}
		catch (InterruptedException e)
		{
			// ignore and simply return
		}
	}
	
	
	static protected class HideCallback extends ComponentAdapter
	{
		public Object mySemaphore;
		public boolean myWindowVisible;
		
		public HideCallback (Object semaphore)
		{
			mySemaphore = semaphore;
		}
		
		public void componentHidden (ComponentEvent event) 
		{
			synchronized (mySemaphore)
			{
				mySemaphore.notify();
			}
		}
	}
	
	static public void displayAndWait (Component comp, Object semaphore)
	{
		if (null == semaphore)
			semaphore = new Object();
		
		//
		// This listener will ensure that, if the window is closed, we will receive
		// a message.
		//
		HideCallback listener = new HideCallback(semaphore);
		comp.addComponentListener(listener);
		
		//
		// Putting the setVisible and the wait inside a synchronized block 
		// ensures that another thread cannot use the notify method until we 
		// execute the wait method.  This guards against lost notifies if the 
		// window closes between the time we call setVisible and wait.
		//
		synchronized (semaphore)
		{
			comp.setVisible(true);
			try
			{
				semaphore.wait();
			}
			catch (InterruptedException e)
			{
				// ignore
			}
		}
	}
	
	
	static public void threadDisplayAndWait (Component comp)
	{
		BlockThread thread = new BlockThread(comp);
		thread.display();
	}
	
	static public void staticDisplayAndWait(Component comp)
	{
		displayAndWait(comp, null);
	}
	
	
	protected void setup()
	{
	}
	
	synchronized protected void wakeUp()
	{
		myFrameClosed = true;
		this.notifyAll();
	}
	
	
	public void startThread()
	{
		myThread.start();
	}
	
	
	static protected Map<Thread,BlockingThread> ourThreadToBlock;
	
	synchronized static public BlockingThread getCurrentBlockingThread()
	{
		if (null == ourThreadToBlock)
			ourThreadToBlock = new HashMap<Thread,BlockingThread>();
		
		Thread thread = Thread.currentThread();
		return ourThreadToBlock.get(thread);
	}
	
	
	synchronized static protected void addBlockingThread(BlockingThread block)
	{
		if (null == ourThreadToBlock)
			ourThreadToBlock = new HashMap<Thread, BlockingThread>();
		
		ourThreadToBlock.put(block.myThread, block);
	}

	public static void go(Runnable runner)
	{
		Thread t = new Thread(runner, "blocking-thread");
		t.start();
	}
}
