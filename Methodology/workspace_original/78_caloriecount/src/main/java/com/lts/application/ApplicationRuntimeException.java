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

import com.lts.util.ArrayUtils;

@SuppressWarnings("serial")
public class ApplicationRuntimeException extends RuntimeException
{
	protected Object[] data;
	
	public ApplicationRuntimeException (Throwable t, String msg)
	{
		super(msg, t);
		
		Object[] temp = new Object[0];
		initialize(temp);
	}
	
	public ApplicationRuntimeException (Throwable t, String msg, Object o1)
	{
		super(msg, t);
		
		Object[] temp = { o1 };
		initialize(temp);
	}
	
	public ApplicationRuntimeException (Throwable t, String msg, Object o1, Object o2)
	{
		super(msg, t);
		
		Object[] temp = { o1, o2 };
		initialize(temp);
	}
	
	public ApplicationRuntimeException (Throwable t, String msg, Object o1, Object o2, Object o3)
	{
		super(msg, t);
		
		Object[] temp = { o1, o2, o3 };
		initialize(temp);
	}
	
	public ApplicationRuntimeException (String msg)
	{
		super(msg);
		
		Object[] temp = new Object[0];
		initialize(temp);
	}
	
	public ApplicationRuntimeException (String msg, Object o1)
	{
		super(msg);
		
		Object[] temp = { o1 };
		initialize(temp);
	}
	
	public ApplicationRuntimeException (String msg, Object o1, Object o2)
	{
		super(msg);
		
		Object[] temp = { o1, o2 };
		initialize(temp);
	}
	
	public ApplicationRuntimeException (String msg, Object o1, Object o2, Object o3)
	{
		super(msg);
		
		Object[] temp = { o1, o2, o3 };
		initialize(temp);
	}
	
	
	
	public void initialize (Object[] exdata)
	{
		data = ArrayUtils.copy(exdata);
	}
	
	public void initialize ()
	{
		Object[] temp = new Object[0];
		initialize(temp);
	}
	
	public void initialize (Object o)
	{
		Object[] temp = { o };
		initialize(temp);
	}
	
	public void initialize (Object o1, Object o2)
	{
		Object[] temp = { o1, o2 };
		initialize(temp);
	}
	
	public void initialize (Object o1, Object o2, Object o3)
	{
		Object[] temp = { o1, o2, o3 };
		initialize(temp);
	}
	
	public void initialize (Object o1, Object o2, Object o3, Object o4)
	{
		Object[] temp = { o1, o2, o3, o4 };
		initialize(temp);
	}
	
	public Object[] getData()
	{
		return data;
	}
}
