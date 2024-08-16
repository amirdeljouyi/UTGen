package com.lts.trace;

import java.util.Arrays;


public class MethodCall extends Event
{
	static public int ME = 2;
	static public int MY_CALLER = 3;
	static public int MY_CALLERS_CALLER = 4;
	
	protected StackTraceElement[] myStack;
	
	public MethodCall()
	{
		initialize(1);
	}
	
	public MethodCall(int callsBefore)
	{
		initialize(callsBefore);
	}
	
	protected void initialize(int callsBeforeThis)
	{
		Exception e = new Exception();
		e.fillInStackTrace();
		StackTraceElement[] stack = e.getStackTrace();
		myStack = Arrays.copyOfRange(stack, callsBeforeThis, stack.length);
	}
	
	protected StackTraceElement getStart()
	{
		return myStack[0];
	}
	
	protected StackTraceElement getCaller()
	{
		return getStart();
	}
	
	public String getShortClass()
	{
		String s = getCaller().getClassName();
		int index = s.lastIndexOf('.');
		if (-1 != index)
		{
			s = s.substring(index + 1);
		}

		return s;
	}
	
	public String getMethodName()
	{
		return getCaller().getMethodName();
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getShortClass());
		sb.append('.');
		sb.append(getMethodName());
		sb.append('@');
		sb.append(super.toString());
		
		return sb.toString();
	}
	
	
	public int divergesFrom(MethodCall call)
	{
		int myIndex = myStack.length;
		int yourIndex = call.myStack.length;
		
		while (myIndex > 0 && yourIndex > 0)
		{
			if (!myStack[myIndex].equals(call.myStack[yourIndex]))
				break;
			
			myIndex--;
			yourIndex--;
		}
		
		return myIndex;
	}
	
	public boolean isAncestorOf (MethodCall call)
	{
		return -1 == divergesFrom(call);
	}
	
	
}
