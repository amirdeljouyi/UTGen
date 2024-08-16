package com.lts.swing.table.dragndrop.test;

public class CallLeaveEvent extends StackTraceEvent
{
	public CallLeaveEvent(int framesBeforeThis)
	{
		initialize(1 + framesBeforeThis);
	}
	
	public String toString()
	{
		return "{ " + myTrace[0];
	}
}
