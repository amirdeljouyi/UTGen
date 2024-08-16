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
package com.lts.swing.menu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lts.LTSException;

/**
 * @author cnh
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MenuMapping
{
	public static final Object[] voidArgs = new Object[0];
	
	public String theSpecification;
	public Method theMethod;
	
	public MenuMapping (String spec, Method m)
	{
		theSpecification = spec;
		theMethod = m;
	}
	
	
	public String toString ()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(theSpecification);
		sb.append (" maps to ");
		sb.append (theMethod);
			
		return sb.toString();	 
	}
	
	public void invoke (Object receiver)
		throws LTSException
	{
		try
		{
			theMethod.invoke(receiver, voidArgs);
		}
		catch (InvocationTargetException e)
		{
			throw new LTSException (
				"Menu action threw exception",
				e.getTargetException()
			);
		}
		catch (IllegalAccessException e)
		{
			throw new LTSException (
				"Invalid access while trying to invoke menu action",
				e
			);
		}
	}
}
	
