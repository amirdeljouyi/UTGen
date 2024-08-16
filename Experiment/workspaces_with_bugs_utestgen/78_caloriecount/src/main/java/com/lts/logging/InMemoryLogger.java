//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.logging;

import java.io.IOException;

import com.lts.io.IOBufferOverflowException;
import com.lts.io.SizeStringWriter;

/**
 * A Logger that keepts its messages in memory.
 * 
 * @author cnh
 */
public class InMemoryLogger 
	extends AbstractLogger 
{
	/**
	 * The current destination for messages.
	 */
	protected SizeStringWriter myWriter;
	
	/**
	 * The other buffers in the logger.
	 */
	protected SizeStringWriter[] myBuffers;
	
	/**
	 * Index of the buffer we are currently writing to.
	 */
	protected int myCurrentIndex;
	
	/**
	 * The index of the first buffer to show the user.
	 */
	protected int myUserIndex;

	/**
	 * The size of each buffer in characters, NOT bytes.
	 */
	protected int myBufferSize;

	public InMemoryLogger ()
	{
		initialize (2, 1024);
	}
	
	
	public void initialize (int numberOfBuffers, int bufferSize)
	{
		myBufferSize = bufferSize;
		myBuffers = new SizeStringWriter[numberOfBuffers];
		
		for (int i = 0; i < numberOfBuffers; i++)
		{
			myBuffers[i] = null;
		}
		
		
		myBuffers[0] = new SizeStringWriter(bufferSize, bufferSize);	
		myWriter = myBuffers[0];
		
		myCurrentIndex = 0;		
		myUserIndex = 0;
	}
	
	
	public int nextBufferIndex (int currentIndex)
	{
		if (myBuffers.length <= 1)
			return currentIndex;
			
		currentIndex++;
		if (currentIndex >= myBuffers.length)
			currentIndex = 0;
		
		return currentIndex;
	}
	
	
	public void processOverflow (String message)
	{
		myCurrentIndex = nextBufferIndex(myCurrentIndex);
		
		myBuffers[myCurrentIndex] = 
			new SizeStringWriter(myBufferSize, myBufferSize);

		myWriter = myBuffers[myCurrentIndex];

		//
		// if we are about to start wrapping over a buffer that the user has
		// not seen yet, then advance the user start to the next buffer. 
		// 		
		if (myBuffers.length > 1 && myCurrentIndex == myUserIndex)
		{
			myUserIndex = nextBufferIndex(myUserIndex);
		}
		
		//
		// If we fail to write this message for any reason, there is nothing 
		// we can do about it.  Try to print it out to standard error and 
		// forget about it.
		//
		try
		{
			myWriter.write(message);
		}
		catch (IOException e)
		{
			System.err.println ("Error writing message:");
			System.err.println (message);
			System.err.println ();
			System.err.println ("Exception:");
			e.printStackTrace();
		}		
	}
	
	
	/* (non-Javadoc)
	 * @see com.lts.logging.AbstractLogger#basicLog(java.lang.String, int, java.lang.Throwable)
	 */
	public synchronized void basicLog(String message, int severity, Throwable ex) 
	{
		String msg = format(message, severity, ex, true);
		
		try
		{
			myWriter.write(msg);
		}
		catch (IOBufferOverflowException e)
		{
			processOverflow(msg);
		}
		catch (IOException e)
		{
			System.err.println ("Error writing message:");
			System.err.println (msg);
			System.err.println();
			System.err.println ("Exception: ");
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Return the data from the next buffer the user should see, and then
	 * blank it out.
	 * 
	 * <P/>
	 * If the user has seen all the messages, then return null.
	 * 
	 * @return
	 */
	public synchronized String acknowledgeBuffer ()
	{
		//
		// The user has seen everything if the user index and the current
		// index are pointing to the same buffer, and the current buffer 
		// has not been written to (i.e., size 0).
		//
		if (myCurrentIndex == myUserIndex && myWriter.getSize() == 0)
			return null;
		 
		//
		// Show the user the next buffer and blank it out.
		//
		String s = myBuffers[myUserIndex].toString();
		myBuffers[myUserIndex] = new SizeStringWriter(myBufferSize, myBufferSize);
		
		//
		// If we are going to show the user some buffer other than the one
		// that we are currently writing to, advance to the next buffer.
		//
		if (myCurrentIndex != myUserIndex)
			myUserIndex = nextBufferIndex(myUserIndex);
		
		return s;
	}

}
