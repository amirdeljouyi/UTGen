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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.xml.XMLUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;

public class MapTag extends AbstractTag
{
	public static final String STR_TAG_NAME = "map";
	
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
		Map m = (Map) value;
		
		String[] attrs = {
			STR_ATTR_ID, Integer.toString(id),
			STR_ATTR_CLASS, value.getClass().getName()
		};
		
		printElement(out, name, attrs, false, true);
		
		out.increaseIndent();
		
		for (Iterator i = m.keySet().iterator(); i.hasNext();)
		{
			Object key = i.next();
			Object theValue = m.get(key);
			writeMapping (xser, out, key, theValue);
		}
		
		out.decreaseIndent();
		
		this.printClosingElement(out, name);
	}

	
	public void writeMapping(
		XmlSerializer xser, 
		IndentingPrintWriter out,
		Object key,
		Object value
	) 
		throws LTSException
	{
		printElement(out, "mapping", null, false, true);
		
		out.increaseIndent();
		xser.printValue(out, key, "key", true);		
		xser.printValue(out, value, "value", true);
		out.decreaseIndent();
		
		printClosingElement(out, "mapping", true);
	}


	public void processMapping (XmlSerializer xser, Element node, Map m)
		throws LTSException
	{
		Element keyElement = XMLUtils.getChild("key", node);
		Object key = xser.readValue(keyElement);
		Element valueElement = XMLUtils.getChild("value", node);
		Object value = xser.readValue(valueElement);
		
		m.put(key, value);
	}
	
	
	public Object read(XmlSerializer xser, Element node, boolean forgiving) 
		throws LTSException
	{
		try
		{
			String s = getRequiredAttr(node, STR_ATTR_CLASS);
			Class c = Class.forName(s);
			Map m = (Map) c.newInstance();
			
			List children = XMLUtils.getChildElements(node);
			for (Iterator i = children.iterator(); i.hasNext();)
			{
				Element el = (Element) i.next();
				if (el.getNodeName().equalsIgnoreCase("mapping"))
				{
					processMapping(xser, el, m);
				}
			}
			return m;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException(e);
		}
	}

	public String getTagName(Object o)
	{
		return "map";
	}

}
