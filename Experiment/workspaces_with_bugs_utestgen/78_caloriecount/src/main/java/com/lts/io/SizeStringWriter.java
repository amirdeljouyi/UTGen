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
package com.lts.io;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


/**
 * A StringWriter that can report its size and can be instructed to limit
 * its size to some value.
 * 
 * <P/>
 * This class is useful when trying to ensure that a program does not use 
 * too much space in memory for some purpose.
 * 
 * 
 * @author cnh
 */
public class SizeStringWriter
	extends Writer 
{
	/**
	 * How much space is the object currently using, in characters.
	 */
	protected int mySize;
	
	/**
	 * What is the maximum size that the object should use, in characters.
	 * 
	 * <P/>
	 * If this value is not -1, then if an operation would cause the buffer
	 * to exceed the max size, the operation throws an exception instead.
	 * If the value is -1, then the object will not throw exceptions.
	 */
	protected int myMaxSize = -1;
	
	/**
	 * The object delegates all of its operations to a StringWriter.
	 */
	protected StringWriter myStringWriter;
	
	
	public void initialize (int initialSize, int maxSize)
	{
		if (0 >= initialSize)
			initialSize = 128;
			
		mySize = 0;
		myMaxSize = maxSize;
		myStringWriter = new StringWriter(initialSize);
	}
	
	public void initialize (int initialSize)
	{
		initialize(initialSize, -1);
	}
	
	public void initialize ()
	{
		initialize (128, -1);
	}
	
	
	public SizeStringWriter ()
	{
		initialize();
	}
		
	public SizeStringWriter (int initialSize)
	{
		initialize (initialSize, -1);
	}
	
	public SizeStringWriter (int initialSize, int maxSize)
	{
		initialize (initialSize, maxSize);
	}
		
	
	public int getSize ()
	{
		return mySize;
	}
	
	
	public long getMaxSize ()
	{
		return myMaxSize;
	}
	
	
	/* (non-Javadoc)
	 * @see java.io.Writer#close()
	 */
	public void close()
		throws IOException
	{
		myStringWriter.close();
	}

	/* (non-Javadoc)
	 * @see java.io.Writer#flush()
	 */
	public void flush() 
		throws IOException 
	{
		myStringWriter.flush();
	}

	/* (non-Javadoc)
	 * @see java.io.Writer#write(char[], int, int)
	 */
	public void write(char[] arg0, int arg1, int arg2) 
		throws IOException 
	{
		int newSize = mySize + arg2;
		if (-1 != myMaxSize && newSize > myMaxSize)
		{
			throw new IOBufferOverflowException (
				"Operation would cause an overflow.  "
				+ "Current size: " + mySize
				+ ", write size: " + arg2
				+ ", maximum size: " + myMaxSize
				+ ", potential resulting size: " + newSize
			);
		}
		
		myStringWriter.write(arg0, arg1, arg2);
		
		mySize = newSize;
	}
	
	
	public StringBuffer getBuffer()
	{
		return myStringWriter.getBuffer();
	}
	
	
	public String toString()
	{
		return myStringWriter.toString();
	}
}
