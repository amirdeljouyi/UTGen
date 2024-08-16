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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.util.CaselessMap;
import com.lts.xml.XMLUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;
import com.lts.xmlser.fixups.ArrayFixup;
import com.lts.xmlser.fixups.ReferenceFixup;

public class ArrayTag extends AbstractTag 
{
	public static final String STR_TAG_NAME = "array";
	public static NullObject NULL_OBJECT = new NullObject();
	
	public String getTagName (Object o)
	{
		return STR_TAG_NAME;
	}
	
	
	public static final Object[] SPEC_STRING_TO_PRIMITIVE = {
		Boolean.TYPE.getName(),		Boolean.TYPE,
		Byte.TYPE.getName(),		Byte.TYPE,
		Character.TYPE.getName(),	Character.TYPE,
		Double.TYPE.getName(),		Double.TYPE,
		Float.TYPE.getName(),		Float.TYPE,
		Integer.TYPE.getName(),		Integer.TYPE,
		Long.TYPE.getName(),		Long.TYPE,
		Short.TYPE.getName(),		Short.TYPE,
	};
	
	public static Map ourStringToPrimitiveMap = 
		new CaselessMap (SPEC_STRING_TO_PRIMITIVE);
	
	
	public Object read (XmlSerializer xser, Element node, boolean forgiving)
		throws LTSException
	{
		String strClass = getRequiredAttr(node, STR_ATTR_CLASS);
		Class c = (Class) ourStringToPrimitiveMap.get(strClass);
		
		try 
		{
			if (null == c)
				c = Class.forName(strClass);
		}
		catch (ClassNotFoundException e)
		{
			throw new LTSException (
				"Could not find class " + strClass,
				e
			);
		}
		
		List l = new ArrayList();
		Iterator i = XMLUtils.getChildElements(node).iterator();
		while (i.hasNext())
		{
			Element child = (Element) i.next();
			Object value = xser.readValue(child);
			if (null != value)
				l.add(value);
			else
			{
				String strRep = child.getAttribute(STR_ATTR_REPLICATE);
				if (null == strRep || "".equals(strRep))
					l.add(NULL_OBJECT);
				else
				{
					int count = Integer.parseInt(strRep);
					for (int j = 0; j < count; j++)
					{
						l.add(NULL_OBJECT);
					}
				}
			}
		}
		
		int length = l.size();
		int index = 0;
		Object array = Array.newInstance(c, length);
		i = l.iterator();
		while (i.hasNext())
		{
			Object o = i.next();
			
			if (o == NULL_OBJECT)
				Array.set(array, index, null);
			else if (!(o instanceof ReferenceFixup))
				Array.set(array, index, o);
			else
			{
				ReferenceFixup f = (ReferenceFixup) o;
				ArrayFixup af = new ArrayFixup(array, f.id, index);
				xser.addIdFixup(f.id, af);
			}
			
			index++;
		}
		
		return array;
	}
	
	
	public void write (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object array,
		boolean printClassName
	)
		throws LTSException
	{
		String strComp = array.getClass().getComponentType().getName();
		int id = xser.addObject(array);
		
		String[] data = {
			STR_ATTR_ID, 	 Integer.toString(id),
			STR_ATTR_ARRAY,  "true",
			STR_ATTR_CLASS,  strComp
		};
		
		printElement(out, name, data, false, true);
		
		out.increaseIndent();
		
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
		{
			Object o = Array.get(array, i);
			xser.printValue(out, o);
		}
		
		out.decreaseIndent();
		
		printClosingElement(out, name);
	}
	
	/*
	public void write (
		IndentingPrintWriter out,
		String name,
		Object array,
		int id,
		int index
	)
	{
		String strComp = array.getClass().getComponentType().getName();
		int length = Array.getLength(array);
		String[] attrs = {
			STR_ATTR_ID, Integer.toString(id),
			STR_ATTR_CLASS, strComp,
			STR_ATTR_LENGTH, Integer.toString(length),
			STR_ATTR_INDEX, Integer.toString(index)
		};
		
		printElement(out, name, attrs, false, true);
		writeElements(out, array);
		printClosingElement(out, name);
	}
	*/
}
