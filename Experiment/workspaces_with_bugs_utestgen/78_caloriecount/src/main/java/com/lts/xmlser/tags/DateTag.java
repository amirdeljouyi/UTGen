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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.lang.reflect.ReflectionUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;

public class DateTag extends AbstractTag
{
	public static final String STR_TYPE = "date";
	public static final String STR_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS Z";
	public static SimpleDateFormat ourFormat = new SimpleDateFormat(STR_DATE_FORMAT);
	
	public String getTagName (Object o)
	{
		return o.getClass().getName();
	}
	
	
	public void write (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value,
		boolean printClassName
	)
		throws LTSException
	{
		int id = xser.addObject(value);
		Date d = (Date) value;
		String strValue = ourFormat.format(d);
		
		String[] attrs = {
			STR_ATTR_ID, Integer.toString(id),
			STR_ATTR_VALUE, strValue,
			STR_ATTR_CLASS, value.getClass().getName()
		};
		
		printElement(out, name, attrs, true, true);
	}
	
	
	public Object read (XmlSerializer xser, Element node, boolean forgiving)
		throws LTSException
	{
		String s;
		
		s = getRequiredAttr(node, STR_ATTR_VALUE);
		long time = 0;
		try
		{
			time = ourFormat.parse(s).getTime();
		}
		catch (ParseException e)
		{
			throw new LTSException (
				"Date values should have the format: " + STR_DATE_FORMAT
				+ ".  Got value: " + s
				+ ", for element: " + node.getNodeName(),
				e
			);
		}
		
		//
		// none of the punk java.sql subclasses of java.util.Date have a public
		// no-arg constructor.  All classes of Date, including java.util.Date 
		// do have a constructor that takes a single long value as an argument.
		// This being the case, use that constructor rather than the no-arg one.
		//
		Class[] formalParams = { Long.TYPE };
		Object[] actualParams = { new Long(time) };
		
		Date d = null;
		try
		{
			d = (Date) ReflectionUtils.createInstance(s, formalParams, actualParams);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException(
				"Error trying to create instance of class " 
				+ s
				+ " with argument " + time
				+ ", in tag " + node.getNodeName(),
				e
			);
		}
		
		return d;
	}
	
}
