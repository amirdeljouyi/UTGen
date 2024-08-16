package com.lts.exception;

public class DefaultExceptionHandler extends ExceptionHandler
{
	public void instanceProcessException(Exception e)
	{
		e.printStackTrace();
	}
}
