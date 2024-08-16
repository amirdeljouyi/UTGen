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

import com.lts.LTSException;

/**
 * An exception occurring in the {@link com.lts.application.Application}
 * framework.
 * 
 * <P>
 * The primary difference between an LTSException and instances of this class is that 
 * this class contains an error code and <I>may</I> some context data.
 * @author cnh
 *
 */
public class ApplicationException extends LTSException
{
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	public String getKey()
	{
		return this.key;
	}
	
	public void setKey (String key)
	{
		this.key = key;
	}
	
	
	public void initialize (String key, Object[] data)
	{
		setKey(key);
		setData(data);
	}
	
	public ApplicationException (String msg)
	{
		super(msg);
		Object[] data = new Object[] {};
		initialize(msg, data);
	}
	
	public ApplicationException (String msg, Object arg1)
	{
		super(msg);
		Object[] data = new Object[] { arg1 };
		initialize(msg, data);
	}
	
	
	public ApplicationException (String msg, Object arg1, Object arg2)
	{
		super(msg);
		Object[] data = new Object[] { arg1, arg2 };
		initialize(msg, data);
	}
	
	public ApplicationException (String msg, Object[] argv)
	{
		super(msg);
		initialize(msg, argv);
	}
	
	public ApplicationException (Throwable t, String msg)
	{
		super(msg,t);
		Object[] data = new Object[] {};
		initialize(msg, data);
	}
	
	
	public ApplicationException (Throwable t, String msg, Object arg1)
	{
		super(msg,t);
		Object[] data = new Object[] { arg1 };
		initialize(msg, data);
	}
	
	public ApplicationException (Throwable t, String msg, Object arg1, Object arg2)
	{
		super(msg,t);
		Object[] data = new Object[] { arg1, arg2 };
		initialize(msg, data);
	}
	
	
	public ApplicationException (Throwable t, String msg, Object arg1, Object arg2, Object arg3)
	{
		super(msg,t);
		Object[] data = new Object[] { arg1, arg2, arg3 };
		initialize (msg, data);
	}
	
	public ApplicationException (Throwable t, String msg, Object[] data)
	{
		super(msg,t);
		initialize(msg,data);
	}
	
		
	public void addData (Object[] moreData)
	{
		int len = data.length + moreData.length;
		Object[] a = new Object[len];
		
		for (int i = 0; i < len; i++)
		{
			if (i < data.length)
				a[i] = data[i];
			else
			{
				int fromIndex = i - data.length;
				int toIndex = i;
				a[toIndex] = moreData[fromIndex];
			}
		}
		
		data = a;
	}
	
	public void addData (Object moreData)
	{
		Object[] a = new Object[] { moreData };
		addData(a);
	}
	
	public void addData (Object o1, Object o2)
	{
		Object[] a = new Object[] { o1, o2 };
		addData(a);
	}
	
	public void addData (Object o1, Object o2, Object o3)
	{
		Object[] a = new Object[] { o1, o2, o3 };
		addData(a);
	}
}
