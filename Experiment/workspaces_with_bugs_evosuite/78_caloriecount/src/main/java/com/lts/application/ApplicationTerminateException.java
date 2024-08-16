//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application;

import java.io.PrintWriter;

/**
 * An exception that signals the applications desire to terminate the program.
 * 
 * <H3>Description</H3>
 * This exception is thrown when the application cannot continue executing for some
 * reason.  It is not a subclass of ApplicationException because there is no message
 * to be displayed to the user --- if this exception is thrown, then the thrower 
 * should have already give the user an error message explaining the situation.
 * 
 * <P>
 * Under most circumstances, anyone catching this should either re-throw it or call
 * System.exit immediately.
 * 
 * @author cnh
 */
@SuppressWarnings(value="serial")
public class ApplicationTerminateException extends Exception
{
	public ApplicationTerminateException()
	{}
	
	public ApplicationTerminateException(String msg)
	{
		super(msg);
	}
	
	public ApplicationTerminateException(Throwable cause)
	{
		super(cause);
	}
	
	public ApplicationTerminateException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
		
	public void printStackTrack (PrintWriter out)
	{
		out.println(getMessage());
		super.printStackTrace(out);
		getCause().printStackTrace(out);
	}
}
