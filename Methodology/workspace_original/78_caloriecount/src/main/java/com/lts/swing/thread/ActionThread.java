package com.lts.swing.thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A BlockingThread that starts when an action occurs.
 * 
 * <P>
 * This class was created to be used by the UI in situations where the current (UI)
 * thread should not block.
 * </P>
 * 
 * @author cnh
 *
 */
public abstract class ActionThread extends BlockingThread implements ActionListener
{
	abstract public void run();
	
	public void actionPerformed (ActionEvent event)
	{
		myThread = new Thread(this);
		BlockingThread.addBlockingThread(this);
		startThread();
	}	
}
