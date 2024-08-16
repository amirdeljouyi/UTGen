package com.lts.caloriecount.data.frequent;

public class RectifyException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public RectifyException ()
	{}
	
	public RectifyException (String message)
	{
		super(message);
	}
	
	public RectifyException (Throwable cause)
	{
		super(cause);
	}
	
	public RectifyException (String message, Throwable cause)
	{
		super(message,cause);
	}
}
