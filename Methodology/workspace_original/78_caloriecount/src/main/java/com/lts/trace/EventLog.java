package com.lts.trace;

import java.util.List;

public class EventLog
{
	static public int ME = 2;
	static public int MY_CALLER = 3;
	static public int MY_CALLERS_CALLER = 4;
	
	private static EventLog ourInstance;
	protected List<MethodCall> myCalls;
	
	static public EventLog getInstance()
	{
		return ourInstance;
	}
	
	static synchronized public void resetInstance()
	{
		ourInstance = new EventLog();
	}
	
	public void add()
	{
		add(1 + ME);
	}
	
	public void add(int framesBeforeThis)
	{
		MethodCall call = new MethodCall(1 + framesBeforeThis);
		myCalls.add(call);
	}

	public void enterMethod()
	{
		add(2);
	}
	
	public void leaveMethod()
	{
		add(2);
	}

	public String getTranscript()
	{
		
		// TODO Auto-generated method stub
		return null;
	}
}
