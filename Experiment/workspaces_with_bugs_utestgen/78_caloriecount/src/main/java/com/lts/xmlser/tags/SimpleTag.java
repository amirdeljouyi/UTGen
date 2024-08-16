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
import com.lts.util.StringIntMap;
import com.lts.xml.XMLUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.XmlSerializer;

/**
 * This class is generally used to handle serialization for wrapper classes  
 * when the expected type is java.lang.Object.
 * 
 * <P/>
 * Consider the situation where a class has a field of type Object that has
 * been assigned a value of type java.lang.String.  Serializing the value 
 * as if it were a generic class results in weird, incomprehensible output
 * like this:
 * 
 * <PRE>
 * &lt;foo class="java.lang.String"&gt;
 *     &lt;offset&gt;0&lt;/offset&gt;
 *     &lt;characters array="true" class="char"&gt;
 *         &lt;char&gt;h&lt;/char&gt;
 *         &lt;char&gt;i&lt;/char&gt;
 *     &lt;/characters&gt;
 * &lt;/foo&gt;
 * </PRE>
 * 
 * What would probably be better would be something like this:
 * 
 * <PRE>
 * <foo class="java.lang.String">hi</foo>
 * </PRE>
 * 
 * This class handles that situation by always providing a class attribute to 
 * the tag, but then always using to toString method to serialize the value.
 */
public class SimpleTag extends AbstractTag
{
	public static final int TYPE_BOOLEAN = 0;
	public static final int TYPE_BYTE = 1;
	public static final int TYPE_CHARACTER = 2;
	public static final int TYPE_DOUBLE = 3;
	public static final int TYPE_FLOAT = 4;
	public static final int TYPE_INTEGER = 5;
	public static final int TYPE_LONG = 6;
	public static final int TYPE_SHORT = 7;
	public static final int TYPE_STRING = 8;
	
	public static Object[] SPEC_STRING_TO_TYPE = {
		Boolean.class.getName(), 	new Integer(TYPE_BOOLEAN),
		Byte.class.getName(), 		new Integer(TYPE_BYTE),
		Character.class.getName(),	new Integer(TYPE_CHARACTER),
		Double.class.getName(),		new Integer(TYPE_DOUBLE),
		Float.class.getName(),		new Integer(TYPE_FLOAT),
		Integer.class.getName(),	new Integer(TYPE_INTEGER),
		Long.class.getName(),		new Integer(TYPE_LONG),
		Short.class.getName(),		new Integer(TYPE_SHORT),
		String.class.getName(),		new Integer(TYPE_STRING)
	};
	
	public static StringIntMap ourStringToType 
		= new StringIntMap(SPEC_STRING_TO_TYPE);
	
	public static int stringToType (String s)
	{
		return ourStringToType.stringToInt(s);
	}
	
	
	public void writeAsAttribute (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value
	)
	{
		int id = xser.addObject(value);
		String[] attrs = {
			STR_ATTR_CLASS, value.getClass().getName(),
			STR_ATTR_ID, Integer.toString(id),
			STR_ATTR_VALUE, value.toString()
		};
		
		printElement(out, name, attrs, true, true);
	}
	
	
	public void writeAsChild (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value
	)
	{
		int id = xser.addObject(value);
		String[] attrs = {
			STR_ATTR_CLASS, value.getClass().getName(),
			STR_ATTR_ID, Integer.toString(id)
		};
		
		printElement(out, name, attrs, false, false);
		out.print(escapeCharacters(value.toString()));
		printClosingElement(out, name);
	}
	
	
	public void write(
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value,
		boolean printClassName
	)
		throws LTSException
	{
		if (value instanceof String || xser.stringSerializePrimitives())
			writeAsChild(xser, out, name, value);
		else
			writeAsAttribute(xser, out, name, value);
	}

	public Object read(XmlSerializer xser, Element node, boolean forgiving) throws LTSException
	{
		String cname = getRequiredAttr(node, STR_ATTR_CLASS);
		String value = node.getAttribute(STR_ATTR_VALUE);
		if (null == value || "".equals(value))
			value = XMLUtils.getChildText(node);
		
		
		Object o = null;
		int code = stringToType(cname);
		switch (code)
		{
			case TYPE_BOOLEAN :
				o = new Boolean(value);
				break;
				
			case TYPE_BYTE :
				o = new Byte(value);
				break;
			
			case TYPE_CHARACTER :
			{
				char c = value.charAt(0);
				o = new Character(c);
				break;
			}
				
			case TYPE_DOUBLE :
				o = new Double(value);
				break;
			
			case TYPE_FLOAT :
				o = new Float(value);
				break;
			
			case TYPE_LONG :
				o = new Long(value);
				break;
				
			case TYPE_INTEGER :
				o = new Integer(value);
				break;
				
			case TYPE_SHORT :
				o = new Short(value);
				break;
				
			case TYPE_STRING :
				o = value;
				break;
		}
		
		return o;
	}

	
	public String getTagName(Object o)
	{
		return o.getClass().getName();
	}

}
