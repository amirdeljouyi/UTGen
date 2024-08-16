package com.lts.lang.reflect;

public class InvocationException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvocationException(Throwable e)
	{
		super(e);
	}
	
	public InvocationException(String msg)
	{
		super(msg);
	}
	
	public InvocationException(String msg, Throwable e)
	{
		super(msg, e);
	}
}
