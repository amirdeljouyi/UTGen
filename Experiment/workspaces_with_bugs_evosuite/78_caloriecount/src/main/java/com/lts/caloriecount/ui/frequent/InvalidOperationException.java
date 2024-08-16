package com.lts.caloriecount.ui.frequent;

public class InvalidOperationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InvalidOperationException()
	{}
	
	public InvalidOperationException(Throwable cause)
	{
		super(cause);
	}
	
	public InvalidOperationException(String message)
	{
		super(message);
	}
	
	public InvalidOperationException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
