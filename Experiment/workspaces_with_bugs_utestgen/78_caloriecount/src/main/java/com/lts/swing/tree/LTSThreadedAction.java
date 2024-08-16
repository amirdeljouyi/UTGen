package com.lts.swing.tree;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


@SuppressWarnings("serial")
abstract public class LTSThreadedAction extends AbstractAction
{
	abstract public void action();
	
	public void actionPerformed(ActionEvent e)
	{
		Thread thread = new Thread() {
			public void run() {
				action();
			}
		};
		
		thread.start();
	}
}
