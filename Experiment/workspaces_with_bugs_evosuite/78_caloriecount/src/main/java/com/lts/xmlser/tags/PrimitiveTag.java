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
 * Created on May 31, 2004
 *
 */
package com.lts.xmlser.tags;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;

public abstract class PrimitiveTag extends AbstractTag
{
	public abstract String getTagClassName();
	public abstract Object toValue(String value);
	
	public void write(
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value,
		boolean printClassName
	)
		throws LTSException
	{
		if (printClassName)
			printSimpleValue(xser, out, name, value.toString(), getTagClassName());
		else
			printSimpleValue(xser, out, name, value.toString());
	}
	
	
	public Object read(XmlSerializer xser, Element node, boolean forgiving) 
		throws LTSException
	{
		String strValue = getValueOrText(node);
		return toValue(strValue);
	}
	

}
