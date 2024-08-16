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
package com.lts.application.bundles;

import java.util.ResourceBundle;

/**
 * Contains information about what went wrong when an attempt was made to 
 * format a message using the MessageFormatter class.
 * 
 * <H2>Description</H2>
 * This class contains data detailing a problem encountered while trying to 
 * format a message.  Some or all of the information may be absent, depending 
 * on what the nature of the error was.
 * 
 * <H2>Properties</H2>
 * <UL>
 * <LI>errorCode - the nature of the problem.
 * <LI>resourceBundle - the resource bundle used when trying to find/format 
 * the message.
 * 
 * <LI>key - the key used to look up the template.
 * <LI>template - the template string used to format the message.  May be 
 * null.
 * 
 * <LI>exception - the exception caught.
 * </UL>
 * 
 * @author cnh
 *
 */
public class FormatError
{
	public static final int ERROR_KEY_NOT_FOUND = 0;
	public static final int ERROR_FORMAT_EXCEPTION = 1;
	
	protected int myErrorCode;
	
	public int getErrorCode()
	{
		return myErrorCode;
	}
	
	public void setErrorCode (int code)
	{
		myErrorCode = code;
	}
	
	
	protected ResourceBundle myResourceBundle;
	
	public ResourceBundle getResourceBundle()
	{
		return myResourceBundle;
	}
	
	public void setResourceBundle (ResourceBundle bundle)
	{
		myResourceBundle = bundle;
	}
	
	
	protected String myKey;
	
	public String getKey()
	{
		return myKey;
	}
	
	public void setKey (String key)
	{
		myKey = key;
	}
	
	
	protected String myTemplate;
	
	public String getTemplate()
	{
		return myTemplate;
	}
	
	public void setTemplate(String template)
	{
		myTemplate = template;
	}
	
	
	protected Throwable myException;
	
	public Throwable getException()
	{
		return myException;
	}
	
	public void setException (Throwable t)
	{
		myException = t;
	}

	
	public FormatError(Throwable exception, int code, String key, ResourceBundle bundle,
			String template)
	{
		myException = exception;
		myErrorCode = code;
		myKey = key;
		myResourceBundle = bundle;
		myTemplate = template;
	}
}
