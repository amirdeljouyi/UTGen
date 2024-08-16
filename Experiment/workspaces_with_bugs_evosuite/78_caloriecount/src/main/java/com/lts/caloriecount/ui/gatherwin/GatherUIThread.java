package com.lts.caloriecount.ui.gatherwin;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.caloriecount.ui.gatherwin.old.OldGatherWindow;
import com.lts.caloriecount.ui.mainframe.MainFrame;
import com.lts.ui.menuflash.MenuBarFlash;
import com.lts.util.SharedQueue;

/**
 * The runnable that is responsible for displaying the myData gathering window.
 * 
 * <P>
 * It endlessly reads from a shared queue.  Every time it gets an object off the
 * queue, it uses the UI to gather myData.
 * </P>
 * 
 * @author cnh
 */
public class GatherUIThread implements Runnable
{
	protected SharedQueue myQueue;
	protected OldGatherWindow myWindow;
	
	protected boolean myGrabFocus = false;
	
	protected WindowFocusListener myWindowFocusListener = new WindowFocusListener() {
		public void windowGainedFocus(WindowEvent e) 
		{
			appWinGainedFocus(e);
		}

		public void windowLostFocus(WindowEvent e)
		{}
	};
	
	protected void appWinGainedFocus(WindowEvent e)
	{
		if (!myGrabFocus)
			return;
		
		myGrabFocus = false;
		
		Window win = GatherWindow.getInstance().getWindow();
		if (win.isVisible())
		{
			win.requestFocus();
		}
		else
		{
			GatherWindow.getInstance().asyncGather();
		}
	}


	public GatherUIThread (SharedQueue queue)
	{
		myQueue = queue;
	}
	
	
	public void start()
	{
		Thread thread = new Thread(this, toString());
		thread.start();
	}
	
	
	public String toString()
	{
		return "Gather UI Thread";
	}
	
	@SuppressWarnings("unused")
	private static SimpleDateFormat ourFormat =
		new SimpleDateFormat("HH:mm:ss");
	
	public void run()
	{
		try
		{
			Long time = (Long) myQueue.next();
			while (null != time)
			{
				performGather();
				time = (Long) myQueue.next();
			}
		}
		catch (InterruptedException e)
		{
			JOptionPane.showMessageDialog(null, "interrupted");
		}
	}
	
	
	synchronized public void performGather()
	{
		try
		{
			basicGather();
		}
		catch (LTSException e)
		{
			ApplicationContentPanel.showException(e);
		}
	}


	private void basicGather() throws LTSException
	{
		GatherWindow gwin = GatherWindow.getInstance();
		
		Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		
		//
		// a null value means none of our windows have the focus
		//
		if (null == comp)
		{
			flashMenuBar(gwin);
		}
		else 
		{
			grabFocus(gwin);
		}
	}


	private void flashMenuBar(GatherWindow gwin) throws LTSException
	{
		myGrabFocus = true;
		listenToAllWindows();
		
		MenuBarFlash mbf = new MenuBarFlash();			
		if (gwin.getWindow().isVisible())
		{			
			mbf.flash(gwin.getWindow(), 4, 200);
		}
		else
		{
			mbf.flash(MainFrame.getInstance().getWindow(), 4, 200);
		}
	}


	private void grabFocus(GatherWindow gwin)
	{
		if (gwin.getWindow().isVisible())
		{
			gwin.grabFocus();
		}
		else
		{
			gwin.asyncGather();
		}
	}
	
	
	private void listenToAllWindows()
	{
		for (Window window : JFrame.getWindows())
		{
			window.addWindowFocusListener(myWindowFocusListener);
		}
	}


	public boolean applicationHasFocus()
	{
		Window[] allWindows = Window.getWindows();
		for (Window win : allWindows)
		{
			if (win.hasFocus())
				return true;
		}
		
		return false;
	}
	
	
	public void gatherNow()
	{
		myQueue.put(System.currentTimeMillis());
	}
}
