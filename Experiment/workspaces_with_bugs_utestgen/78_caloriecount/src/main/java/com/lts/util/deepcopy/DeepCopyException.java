package com.lts.util.deepcopy;

public class DeepCopyException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DeepCopyException (String msg)
	{
		super(msg);
	}

	public DeepCopyException (Throwable cause)
	{
		super(cause);
	}
	
	public DeepCopyException (String msg, Throwable cause)
	{
		super(msg, cause);
	}
	
}
