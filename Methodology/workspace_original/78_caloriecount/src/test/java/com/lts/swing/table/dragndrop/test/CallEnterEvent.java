package com.lts.swing.table.dragndrop.test;

public class CallEnterEvent extends StackTraceEvent
{
	public CallEnterEvent(int framesBeforeThis)
	{
		initialize(1 + framesBeforeThis);
	}
	
	public String toString()
	{
		return "} " + myTrace[0];
	}
}
