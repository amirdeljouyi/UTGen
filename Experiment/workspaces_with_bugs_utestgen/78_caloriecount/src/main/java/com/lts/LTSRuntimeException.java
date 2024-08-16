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

import java.io.PrintStream;
import java.io.PrintWriter;




/**
 * A runtime exception that supports wrapping other exceptions.
 * 
 * <P/>
 * This class is primarily for pre JDK 1.4 applications.  The 1.4 JDK 
 * Exception class supports wrapped exceptions.
 * 
 * <P/>
 * The class allows access to the wrapped exception, and all methods that
 * produce messages take the presence of such an exception into account.  
 * In most circumstances, the wrapped exception will be printed first, 
 * followed by the wrapping exception.
 * 
 * @author cnh
 */
public class LTSRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
    protected Object[] data;
    

	public LTSRuntimeException ()
    {
        super();
    }
    
    
    public LTSRuntimeException (String s)
    {
        super(s);
    }
    
    public LTSRuntimeException (Throwable t)
    {
        super(t);
    }
    
    public LTSRuntimeException (Object[] data)
    {
    	setData(data);
    }
    
    public LTSRuntimeException (String message, Object o)
    {
    	super(message);
    	Object[] data = { o };
    	setData(data);
    }
    public LTSRuntimeException (String message, Object[] data)
    {
    	super(message);
    	setData(data);
    }
    
    
    public LTSRuntimeException (Throwable t, Object[] data)
    {
    	super(t);
    	setData(data);
    }
    
    
    public LTSRuntimeException (String message, Throwable cause, Object data)
    {
    	super(message, cause);
    	Object[] array = { data };
    	setData(array);
    }
    
    public LTSRuntimeException (String message, Throwable cause, Object o1, Object o2)
    {
    	super(message, cause);
    	Object[] data = { o1, o2 };
    	setData(data);
    }
    
    
    public LTSRuntimeException(String message, Throwable cause, Object o1, Object o2, Object o3)
    {
    	super(message, cause);
    	Object[] data = { o1, o2, o3 };
    	setData(data);
    }
    
    public LTSRuntimeException (String message, Throwable cause, Object[] data)
    {
    	super(message, cause);
    	setData(data);
    }
    
    public LTSRuntimeException (String s, Throwable t)
    {
        super(s,t);
    }
    
    public LTSRuntimeException (String msg, String data0)
    {
    	super(msg);
    	Object[] data = { data0 };
    	setData(data);
    }


    
    
    public Object[] getData()
    {
    	return this.data;
    }
    
    public void setData (Object[] data)
    {
    	this.data = data;
    }
    
    //
    // redefine the stack trace family of methods to take into account data
    // passed along with the exception.
    //
    public void printStackTrace()
    {
    	printStackTrace(System.err);
    }
    
    public void printStackTrace(PrintWriter out)
    {
		super.printStackTrace(out);
		
		Object[] data = getData();
		
		if (null != data && data.length > 0)
		{
			for (int i = 0; i < data.length; i++)
			{
				out.println("[" + i + "] = " + data[i]);
			}
		}
    }
    
    public void printStackTrace(PrintStream stream)
    {
    	PrintWriter out = new PrintWriter(stream);
    	printStackTrace(out);
    	out.flush();
    }
}
