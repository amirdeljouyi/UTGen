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
package com.lts.util;

import java.lang.reflect.Method;

/**
 * Execute a method in a separate thread.
 * 
 * <P>
 * This class allows clients to create a new thread and then execute a particular method
 * in that thread.  This is useful when simulating a separate VM.
 * 
 * @author cnh
 */
public class MethodThread implements Runnable
{

	protected Method method;
	protected Object[] actualParams;
	protected Object target;
	protected String name;
	
	public MethodThread (Object target, Method method, Object[] actualParams)
	{
		this.target = target;
		this.method = method;
		this.actualParams = actualParams;
	}
	
	
	public void run()
	{
		try
		{
			Object[] allParams = { this.actualParams };
			this.method.invoke(target, allParams);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String toString()
	{
		if (null == this.name)
			return super.toString();
		else
			return this.name;
	}
}
