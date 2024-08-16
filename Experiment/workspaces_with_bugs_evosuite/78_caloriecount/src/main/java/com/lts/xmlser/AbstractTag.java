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
package com.lts.xmlser;

import java.lang.reflect.Field;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.xml.XMLUtils;

public abstract class AbstractTag 
	implements TagConstants
{
	public abstract Object read (XmlSerializer xser, Element node, boolean forgiving)
		throws LTSException;
	
	
	public abstract String getTagName (Object o);
	
	
	public abstract void write (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value,
		boolean printClassName
	)
		throws LTSException;
	
	
	public void write (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value
	)
		throws LTSException
	{
		write(xser, out, name, value, false);
	}
	
	
	public void printAttribute (
		IndentingPrintWriter out, 
		String name, 
		String value
	)
	{
		out.print (name);
		out.print ("=\"");
		out.print (value);
		out.print ('"');
	}
	
	
	public void printClosingElement (
		IndentingPrintWriter out,
		String name,
		boolean printNewline
	)
	{
		out.print ("</");
		out.print (name);
		out.print ('>');
		
		if (printNewline)
			out.println();
	}
	
	public void printClosingElement (IndentingPrintWriter out, String name)
	{
		printClosingElement(out, name, true);
	}
	
	
	public void printElement (
		IndentingPrintWriter out,
		String name,
		String[] attrs,
		boolean singleton,
		boolean newline
	)
	{
		out.print ('<');
		out.print (name);
		
		if (null != attrs)
		{	
			for (int i = 0; i < attrs.length; i = i + 2)
			{
				out.print (' ');
				printAttribute (out, attrs[i], attrs[1+i]);
			}
		}
		
		if (singleton)
			out.print ('/');
		
		out.print ('>');
		
		if (newline)
			out.println();
	}
	
	
	public void printSingleTextElement (
		IndentingPrintWriter out,
		String name,
		String[] attrs,
		String value
	)
	{
		printElement(out, name, attrs, false, false);
		out.print (value);
		printClosingElement(out, name);
	}
	
	
	public String getRequiredAttr (Element el, String name)
		throws LTSException
	{
		String val = el.getAttribute(name);
		if (null == val || "".equals(val))
		{
			throw new LTSException (
				"Required attribute, " + name 
				+ ", was missing in tag " + el.getTagName()
			);
		}
		
		return val;
	}
	
	
	public Object getFieldValue (Object o, Field f)
		throws LTSException
	{
		try 
		{
			f.setAccessible(true);
			return f.get(o);
		} 
		catch (IllegalArgumentException e) 
		{
			throw new LTSException (
				"Error trying to get field value, " + f.getName()
				+ ".  Object class: " + o.getClass().getName()
				+ ", expected class: " + f.getDeclaringClass().getName(),
				e
			);
		} 
		catch (IllegalAccessException e) 
		{
			throw new LTSException (
				"Error trying to get field value " + f
			);
		}
	}
	
	public Class stringToClass (String strClass)
		throws LTSException
	{
		try
		{
			Class c = Class.forName(strClass);
			return c;
		}
		catch (ClassNotFoundException e)
		{
			throw new LTSException (e);
		}
	}
	
	
	public Object createInstance (String strClass)
		throws LTSException
	{
		try
		{
			Class c = Class.forName(strClass);
			return c.newInstance();
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


	public String escapeCharacters(String s)
	{
		String result = s;
		
		if (-1 != s.indexOf('<') || -1 != s.indexOf('&'))
		{
			StringBuffer sb = new StringBuffer();
			char[] ca = s.toCharArray();
			
			for (int i = 0; i < ca.length; i++)
			{
				if ('<' == ca[i])
					sb.append ("&lt;");
				else if ('&' == ca[i])
					sb.append ("&amp;");
				else
					sb.append (ca[i]);
			}
			
			result = sb.toString();
		}
		
		return result;
	}


	protected String stringChildValue(Element el)
	{
		NodeList nlist = el.getChildNodes();
		String value = "";
		
		for (int i = 0; i < nlist.getLength(); i++)
		{
			Node n = nlist.item(i);
			if (n.getNodeType() == Node.TEXT_NODE)
			{
				value = n.getNodeValue();
				break;
			}
		}
		
		return value;
	}


	public String getValueOrText(Element node, boolean exceptionIfEmpty) 
		throws LTSException
	{
		String value = node.getAttribute(STR_ATTR_VALUE);
		if (null == value || "".equals(value))
			value = XMLUtils.getChildText(node);
		
		if (exceptionIfEmpty && (null == value || "".equals(value)))
		{
			throw new LTSException ("Empty or null value for " + node.getNodeName());
		}
		
		return value;
	}


	public String getValueOrText(Element node) throws LTSException
	{
		return getValueOrText(node, true);
	}


	public void printValueElement(
		XmlSerializer xser, 
		IndentingPrintWriter out, 
		String name, 
		String value
	) 
		throws LTSException
	{
		String[] attrs = { STR_ATTR_VALUE, value };
		printElement (out, name, attrs, true, true);
	}


	public void printSimpleValue(
		XmlSerializer xser, 
		IndentingPrintWriter out, 
		String name, 
		String value
	) 
		throws LTSException
	{
		if (xser.stringSerializePrimitives())
			printSingleTextElement(out, name, null, value);
		else
			printValueElement(xser, out, name, value);
	}
	
	
	public void printSimpleValue (
		XmlSerializer xser, 
		IndentingPrintWriter out, 
		String name, 
		String value,
		String className
	)
	{
		if (null == className)
			printSingleTextElement(out, name, null, value);
		else
		{	
			String[] attrs = { STR_ATTR_VALUE, value, STR_ATTR_CLASS, className };
			printElement(out, name, attrs, true, true);
		}
	}
}
