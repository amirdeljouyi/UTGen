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


/**
 * An exception that supports wrapping other exceptions.
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
 * <P/>
 * Unfortunately, clients that want to throw RuntimeExceptions and still want
 * to wrap exceptions must use LTSRuntimeException.
 * 
 * @author cnh
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LTSException extends Exception
{
	private static final long serialVersionUID = 1L;
	
    public LTSException ()
    {
        super();
    }
    
    
    public LTSException (String s)
    {
        super(s);
    }
    
    public LTSException (Throwable t)
    {
        super(t);
    }
    
    public LTSException (Object[] data)
    {
    	setData(data);
    }
    
    public LTSException (String message, Object[] data)
    {
    	super(message);
    	setData(data);
    }
    
    
    public LTSException (Throwable t, Object[] data)
    {
    	super(t);
    	setData(data);
    }
    
    
    public LTSException (String message, Throwable cause, Object data)
    {
    	super(message, cause);
    	Object[] array = { data };
    	setData(array);
    }
    
    public LTSException (String message, Throwable cause, Object o1, Object o2)
    {
    	super(message, cause);
    	Object[] data = { o1, o2 };
    	setData(data);
    }
    
    
    public LTSException(String message, Throwable cause, Object o1, Object o2, Object o3)
    {
    	super(message, cause);
    	Object[] data = { o1, o2, o3 };
    	setData(data);
    }
    
    public LTSException (String message, Throwable cause, Object[] data)
    {
    	super(message, cause);
    	setData(data);
    }
    
    public LTSException (String s, Throwable t)
    {
        super(s,t);
    }
    
    public LTSException (String msg, String data0)
    {
    	super(msg);
    	Object[] data = { data0 };
    	setData(data);
    }
    
    
    public Throwable getException ()
    {
        return getCause();
    }
    
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
    
    
 
    
    
    protected Object[] data;
    
    public Object[] getData()
    {
    	return this.data;
    }
    
    public Object getData(int index)
    {
    	if (null == this.data || this.data.length <= index)
    		return null;
    	else
    		return this.data[index];
    }
    
    protected void setData(Object[] data)
    {
    	this.data = data;
    }
    
    protected String myMessage;
    
    public String getMessage()
    {
    	if (null == myMessage)
    		myMessage = buildMessage();
    	
    	return myMessage;
    }


	protected String buildMessage()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(super.getMessage());
		
		if (null != data)
		{
			for (int i = 0; i < data.length; i++)
			{
				sb.append(", [");
				sb.append(i);
				sb.append("] ");
				sb.append(data[i]);
			}
		}
		
		String s = sb.toString();
		return s;
	}
    
    
}
