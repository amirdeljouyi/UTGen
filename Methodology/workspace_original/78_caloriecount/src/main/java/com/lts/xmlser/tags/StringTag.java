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

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.xml.XMLUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;


public class StringTag extends AbstractTag 
{
	public static final String STR_TAG_NAME = "string";
	
	public String getTagName (Object o)
	{
		return STR_TAG_NAME;
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
		String[] attrs = null;
		if (printClassName)
			attrs = new String[] { STR_ATTR_CLASS, String.class.getName() };
		
		printElement(out, name, attrs, false, false);
		out.setSuppressPrefix(true);
		out.print (escapeCharacters((String) value));
		out.setSuppressPrefix(false);
		printClosingElement(out, name);
	}
	
	
	public Object read (XmlSerializer xser, Element el, boolean forgiving)
		throws LTSException
	{
		return XMLUtils.getChildText(el);
	}
	
	
}
