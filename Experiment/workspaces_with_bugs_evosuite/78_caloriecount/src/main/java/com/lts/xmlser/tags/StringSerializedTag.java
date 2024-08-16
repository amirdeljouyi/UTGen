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
package com.lts.xmlser.tags;

import java.lang.reflect.Constructor;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;

/**
 * A tag for an arbitrary class that can be serialized via the toString method 
 * and reconstituted via a string constructor.
 * 
 */
public class StringSerializedTag extends AbstractTag
{
	public static final String STR_TAG_NAME = "stringSerialized";
	
	public void write(
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value,
		boolean printClassName
	)
		throws LTSException
	{
		int id = xser.addObject(value);
		String[] attrs = null;
		
		
		if (!printClassName && name.equals(value.getClass().getName()))
		{
			attrs = new String[] { STR_ATTR_ID, Integer.toString(id) };
		}
		else 
		{
			attrs = new String[] {
			   STR_ATTR_ID, Integer.toString(id),
			   STR_ATTR_CLASS, value.getClass().getName(),
			};
		}
		
		printElement(out, name, attrs, false, false);
		
		String s = escapeCharacters(value.toString());
		out.print(s);
		
		printClosingElement(out, name);
		
	}

	
	public static Object createInstance (Class c, String value)
		throws LTSException
	{
		try
		{
			Class[] formalParams = { String.class };
			Constructor cons = c.getConstructor(formalParams);
			
			Object[] actualParams = { value };
			Object o = cons.newInstance(actualParams);
			return o;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to create instance of " + c
				+ ", using a string constructor with value " + value,
				e
			);
		}
	}
	
	
	public static Object createInstance (String className, String value)
		throws LTSException
	{
		try
		{
			Class c = Class.forName(className);
			return createInstance(c, value);
		}
		catch (ClassNotFoundException e)
		{
			throw new LTSException (e);
		}
	}
	
	
	public Object read(XmlSerializer xser, Element node, boolean forgiving) 
		throws LTSException
	{
		String className = node.getAttribute(STR_ATTR_CLASS);
		if (null == className || "".equals(className))
			className = node.getNodeName();
		
		String strValue = stringChildValue(node);
		
		return createInstance(className, strValue);
	}
	
	

	public String getTagName(Object o)
	{
		return o.getClass().getName();
	}

}
