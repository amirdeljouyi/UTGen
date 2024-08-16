package com.lts.swing.thread;

abstract public class SimpleActionThread extends ActionThread
{
	abstract protected void action();
	
	@Override
	public void run()
	{
		action();
	}
}
