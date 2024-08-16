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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.xml.XMLUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;
import com.lts.xmlser.fixups.ArrayFixup;
import com.lts.xmlser.fixups.CollectionFixup;
import com.lts.xmlser.fixups.ReferenceFixup;

public class CollectionTag extends AbstractTag 
{
	public static final String STR_TAG_NAME = "collection";
	
	public Object read (XmlSerializer xser, Element node, boolean forgiving)
		throws LTSException
	{
		String strClass = null;
		
		try 
		{
			strClass = node.getAttribute(STR_ATTR_COLLECTION);
			Class c = Class.forName(strClass);
			Collection result = (Collection) c.newInstance();
			
			List l = new ArrayList();
			
			List children = XMLUtils.getChildElements(node);
			Iterator i = children.iterator();
			int index = 0;
			List fixups = new ArrayList();
			while (i.hasNext())
			{
				Element child = (Element) i.next();
				Object value = xser.readValue(child);
				
				if (!(value instanceof ReferenceFixup))
					l.add(value);
				else
				{
					ReferenceFixup f = (ReferenceFixup) value;
					ArrayFixup af = new ArrayFixup();
					af.id = f.id;
					af.myIndex = index;
					fixups.add(af);
					l.add(af);
				}
				
				index++;
			}
			
			if (fixups.size() <= 0)
				result.addAll(l);
			else
			{
				Object[] data = l.toArray();
				i = fixups.iterator();
				while (i.hasNext())
				{
					ArrayFixup af = (ArrayFixup) i.next();
					af.setDestination(data);
					xser.addIdFixup(af.id, af);
				}
				
				CollectionFixup cf = new CollectionFixup(result, data);
				xser.addDeferredFixup(cf);
			}
			
			String strID = getRequiredAttr(node, STR_ATTR_ID); 
			Integer id = new Integer(strID);
			xser.addObject(id, result);
			
			return result;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to create collection with class: " + strClass,
				e
			);
		}
	}
	
	
	public void write (
		XmlSerializer xser,
		IndentingPrintWriter out, 
		String name, 
		Object o,
		boolean printClassName
	)
		throws LTSException
	{
		int id = xser.addObject(o);
		String[] data = {
			STR_ATTR_ID, Integer.toString(id),
			STR_ATTR_COLLECTION, o.getClass().getName(),
		};

		printElement(out, name, data, false, true); 

		out.increaseIndent();
		Collection col = (Collection) o;
		Iterator i = col.iterator();
		while (i.hasNext())
		{
			Object element = i.next();
			xser.printValue(out, element);
		}
		
		out.decreaseIndent();
		printClosingElement(out, name);
	}
	
	
	public String getTagName(Object o)
	{
		return STR_TAG_NAME;
	}
}
