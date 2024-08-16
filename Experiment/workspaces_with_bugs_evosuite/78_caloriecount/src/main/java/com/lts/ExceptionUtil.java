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
package com.lts;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil
{
	public static void createStackTrace (PrintWriter out)
	{
	    Exception e = new Exception();
	    e.printStackTrace(out);
	}

	public static String createStackTrace (Throwable t)
	{
		StringWriter sw = new StringWriter(1024);
		PrintWriter out = new PrintWriter(sw);
		
		t.printStackTrace(out);
		
		out.close();
		return sw.toString();  	 
	}

	
	public static String ltsCreateStackTrace (Throwable t)
	{
		StringWriter sw = new StringWriter(1024);
		PrintWriter out = new PrintWriter(sw);
		
		t.printStackTrace(out);
		
		Object[] data = null;
		
		if (t instanceof LTSException)
		{
			LTSException lts = (LTSException) t;
			data = lts.getData();
		}
		else if (t instanceof LTSRuntimeException)
		{
			LTSRuntimeException lts = (LTSRuntimeException) t;
			data = lts.getData();
		}
		
		if (null != data)
		{
			for (int i = 0; i < data.length; i++)
			{
				out.println("[" + i + "] = " + data[i]);
			}
		}
		
		out.close();
		return sw.toString();  	 
	}
	
    public static Object[] toData (Object o)
    {
    	return new Object[] { o };
    }
    
    public static Object[] toData (Object o1, Object o2)
    {
    	return new Object[] { o1, o2 };
    }
    
    public static Object[] toData (Object o1, Object o2, Object o3)
    {
    	return new Object[] { o1, o2, o3 };
    }
    
    public static Object[] toData (Object o1, Object o2, Object o3, Object o4)
    {
    	return new Object[] { o1, o2, o3, o4 };
    }
    
    
    public static String toSummaryMessage (Throwable t)
    {
    	return t.toString();
    }
}
