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
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.lts.LTSException;
import com.lts.io.IOUtilities;
import com.lts.util.StringUtils;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * A class that contains various utility methods for dealing with XML.
 * 
 * @author cnh
 */
public class XMLUtils 
{
	public static String getAttrValue (Node n, String name)
	{
		NamedNodeMap m = n.getAttributes();
		Node attrNode = m.getNamedItem(name);
		String value = null;
		
		if (null != attrNode)
			value = attrNode.getNodeValue();
		
		value = StringUtils.trim(value);
		return value;
	}
	
	public static Integer getIntegerAttr (Node n, String name)
	{
		String s = getAttrValue(n, name);
		if (null == s)
			return null;
		else
			return new Integer(s);
	}
	
	public static Boolean getBooleanAttr (Node n, String name)
	{
		String s = getAttrValue(n, name);
		if (null == s)
			return null;
		else if ("true".equalsIgnoreCase(s))
			return new Boolean(true);
		else
			return new Boolean(false); 
	}
	
	
	public static Element getChild (String s, Element parent)
	{
		NodeList nlist = parent.getChildNodes();
		int count = nlist.getLength();
		int i = 0;
		Element child = null;
		
		while (null == child && i < count)
		{
			Node n = nlist.item(i);
			
			if (
				n.getNodeType() == Node.ELEMENT_NODE
				&& n.getNodeName().equals(s)
			)
			{
				child = (Element) n;
			}
			i++;
		}

		return child;
	}
	
	
	public static List<Element> getChildElements (Element root)
	{
		List l = new ArrayList<Element>();
		
		NodeList nlist = root.getChildNodes();
		for (int i = 0; i < nlist.getLength(); i++)
		{
			Node n = nlist.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE)
			{
				Element child = (Element) n;
				l.add(child);
			}
		}
		
		return l;
	}
	
	/**
	 * Given a node and a tag name return a list of the immediate nodes whose tag 
	 * names match.
	 *  
	 * @param root The parent element.
	 * @param s The tag name to match.
	 * @return A list of matching children.  The list may be empty, but it should 
	 * not be null.
	 */
	public static List getChildElements (Element root, String s, boolean caseSensitive)
	{
		List oldList = getChildElements(root);
		List newList = new ArrayList();
		
		Iterator i = oldList.iterator();
		while (i.hasNext())
		{
			Element el = (Element) i.next();
			String name = el.getNodeName();
			
			boolean match = false;
			
			if (caseSensitive)
				match = s.equals(name);
			else
				match = s.equalsIgnoreCase(name);
			
			if (match)
				newList.add(el);
		}
		
		return newList;
	}
	
	static public List getChildElements (Element root, String s)
	{
		return getChildElements(root, s, false);
	}

	/**
	 * Get the text of a named child of a provided node.
	 * 
	 * <P>
	 * The method looks through the immediate children of the supplied node and
	 * looks for a child with the specified name.  It then returns the text of that
	 * child.
	 * </P>
	 * 
	 * <P>
	 * The method returns null if the provided node has no children or if the 
	 * specified child does not have any text associated with it.
	 * </P>
	 * 
	 * @param element The parent element.
	 * @param name The name of the child element (tag name).
	 * @return The text of the child or null.
	 */
	static public String getChildText (Element element, String name)
	{
		List list = getChildElements(element, name);
		if (null == list || list.size() < 1)
			return null;
		
		Element child = (Element) list.get(0);
		String text = getChildText(child);
		return text;
	}
	
	static public Element getChild (Element element, String name)
	{
		List list = getChildElements(element, name);
		if (null == list || list.size() < 1)
			return null;
		
		Element child = (Element) list.get(0);
		return child;
	}
	
	/**
	 * Return the integer value of a child node's text.
	 * 
	 * <P>
	 * Find the first immediate child of the provided node whose name matches
	 * the supplied node.  Convert the text of that node into an integer and 
	 * return it.
	 * </P>
	 * 
	 * @param element The parent node to search.
	 * @param name The name of the child to convert.
	 * @return The integer value of the child node's text.
	 * @throws NullPointerException If the provided node is null, if no child 
	 * exists with the specified name, if the child exists but does not have any 
	 * text.
	 */
	static public int getChildInt (Element element, String name)
	{
		String text = getChildText(element, name);
		Integer ival = new Integer(text);
		return ival.intValue();
	}
	
	
	public static String getChildText (Element el)
	{
		NodeList nlist = el.getChildNodes();
		for (int i = 0; i < nlist.getLength(); i++)
		{
			Node n = nlist.item(i);
			if (Node.TEXT_NODE == n.getNodeType())
			{
				return n.getNodeValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Create a child element whose content is a formatted time string.
	 * 
	 * <P>
	 * The method uses {@link #ourPreferredFormat} as manner in which to format
	 * the time value.
	 * </P>
	 * 
	 * @param el The parent for the new node.
	 * @param name The element name for the new node.
	 * @param time The time to format as {@link System#currentTimeMillis()}.
	 * @return The new child element.
	 */
	public static Element createChildTime(Element el, String name, long time)
	{
		Date date = new Date(time);
		String s = ourPreferredFormat.format(date);
		return createChild(el, name, s);
	}
	
	/**
	 * Return a string value that can be the value of an attribute or the value 
	 * of a child node, if the attribute is not defined.
	 * 
	 * @param attrName
	 * @param childTag
	 * @return The value or null.
	 */
	public static String getAttrOrChild (Element element, String attrName, String childTag)
	{
		String value = getAttrValue(element, attrName);
		
		if (null != value && !"".equals(value))
			return value;
		
		List list = getChildElements(element, childTag);
		if (list.size() < 1)
			return null;
		
		Element child = (Element) list.get(0);
		value = getChildText(child);
		value = StringUtils.trim(value);
		return value;
	}
	

	/**
	 * Return a string value that is the value of an element attribute or the text 
	 * content of a child node.
	 * 
	 * <P>
	 * The attribute/child must have the same value for the attribute name or tag name.
	 * 
	 * @param element
	 * @param name
	 * @return
	 */
	public static String getAttrOrChild (Element element, String name)
	{
		return getAttrOrChild(element, name, name);
	}
	
	
	public static Map getAttributes (Element element)
	{
		NamedNodeMap attrs = element.getAttributes();
		int length = attrs.getLength();
		Map map = new HashMap();
		
		for (int i = 0; i < length; i++)
		{
			Node node = attrs.item(i);
			String name = node.getNodeName();
			String value = node.getNodeValue();
			map.put(name, value);
		}
		
		return map;
	}
	
	
	public static Element createChild (Element parent, String name, String value)
	{
		Element child = parent.getOwnerDocument().createElement(name);
		child.setNodeValue(value);
		parent.appendChild(child);
		return child;
	}
	
	
	public static Element createChildAndText (Element parent, String name, String value)
	{
		Element child = parent.getOwnerDocument().createElement(name);
		Text text = parent.getOwnerDocument().createTextNode(value);
		child.appendChild(text);
		parent.appendChild(child);
		return child;
	}
	
	
	public static Element createChild(Element parent, String name, int value)
	{
		String val = Integer.toString(value);
		return createChild(parent, name, val);
	}
	
	static public String[] STANDARD_TIME_PATTERNS = {
		"yyyy-MM-dd@HH:mm:ss",
		"mm/dd/yyyy@HH:mm:ss"
	};
	
	static public String PREFERRED_TIME_PATTERN = 
		"yyyy-MM-dd@HH:mm:ss";
	
	static protected SimpleDateFormat ourPreferredFormat =
		new SimpleDateFormat(PREFERRED_TIME_PATTERN);
	
	static protected SimpleDateFormat[] STANDARD_FORMATS;
	
	static public SimpleDateFormat[] getStandardFormats()
	{
		if (null == STANDARD_FORMATS)
		{
			STANDARD_FORMATS = new SimpleDateFormat[STANDARD_TIME_PATTERNS.length];
			for (int i = 0; i < STANDARD_FORMATS.length; i++)
			{
				STANDARD_FORMATS[i] = new SimpleDateFormat(STANDARD_TIME_PATTERNS[i]);
			}
		}
		
		return STANDARD_FORMATS;
	}
	
	static public Long getChildTime(Element parent, String name, String[] patterns)
	{
		Long time = null;
		String text = XMLUtils.getChildText(parent, name);
		while (null == time)
		{
			for (int i = 0; i < patterns.length; i++)
			{
				time = parseTime(text, patterns[i]);
			}
		}
		
		return time;
	}
	
	static public Long getChildTime(Element parent, String name, SimpleDateFormat[] formats)
	{
		Long time = null;
		String text = XMLUtils.getChildText(parent, name);
		if (null == text || "".equals(text))
			return new Long(0);
		
		for (int i = 0; i < formats.length; i++)
		{
			try
			{
				Date date = formats[i].parse(text);
				time = date.getTime();
				break;
			}
			catch (ParseException e)
			{}
		}
		
		if (null == time)
		{
			try
			{
				time = new Long(text);
			}
			catch (NumberFormatException e)
			{}
		}
		return time;
	}
	
	
	static public Long getChildTime(Element parent, String name)
	{
		return getChildTime(parent, name, getStandardFormats());
	}
	
	
	static public Long parseTime (String text, String pattern)
	{
		Long time = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(text);
			return time = date.getTime();
		}
		catch (ParseException e)
		{}
		
		return time;
	}

	public static Element createChild(Element root, String name)
	{
		Element el = root.getOwnerDocument().createElement(name);
		root.appendChild(el);
		return el;
	}

	public static final String ATTR_VALUE = "value";
	
	public static Element createChild (Element parent, String name, double value)
	{
		Element child = parent.getOwnerDocument().createElement(name);
		child.setAttribute(ATTR_VALUE, Double.toString(value));
		parent.appendChild(child);
		return child;
	}
	
	public static void createAttr (Element parent, String name, long value)
	{
		String s = Long.toString(value);
		parent.setAttribute(name, s);
	}
	
	public static void createAttr (Element element, String name, int value)
	{
		String s = Integer.toString(value);
		element.setAttribute(name, s);
	}
	

	static public Map<String, String> buildNameValueMap (Element parent)
	{
		Map map = new HashMap<String, String>();
		List list = XMLUtils.getChildElements(parent);
		for (Object o : list)
		{
			Element child = (Element) o;
			String value = XMLUtils.getChildText(child);
			map.put(child.getNodeName(), value);
		}
		
		return map;
	}
	
	
	static public Document createDocument() throws LTSException
	{
		try
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fact.newDocumentBuilder();
			Document doc = builder.newDocument();
			return doc;
		}
		catch (ParserConfigurationException e)
		{
			throw new LTSException("error creating document builder", e);
		}
	}
	
	
	static public DocumentBuilder createDocumentBuilder() throws LTSException
	{
		try
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fact.newDocumentBuilder();
			return builder;
		}
		catch (ParserConfigurationException e)
		{
			throw new LTSException("error creating document builder", e);
		}
	}
	
	
	static public void writeDocument (File file, Document doc) 
		throws LTSException
	{
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(file);
			OutputFormat format = new OutputFormat();
			format.setIndenting(true);
			format.setIndent(4);
			XMLSerializer xser = new XMLSerializer(writer, format);
			xser.serialize(doc);
		}
		catch (IOException e)
		{
			String msg = "Error opening file " + file;
			throw new LTSException(msg,e);
		}
		finally
		{
			IOUtilities.close(writer);
		}
	}

	
	static public Document readDocument (File file) throws LTSException
	{
		FileReader reader = null;
		try
		{
			DocumentBuilder builder = createDocumentBuilder();
			Document document = builder.parse(file);
			return document;
		}
		catch (FileNotFoundException e)
		{
			String msg = "Could not find " + file;
			throw new LTSException(msg, e);
		}
		catch (IOException e)
		{
			String msg = "Error reading " + file;
			throw new LTSException(msg, e);
		}
		catch (SAXException e)
		{
			String msg = "Error parsing " + file;
			throw new LTSException(msg, e);
		}
		finally
		{
			IOUtilities.close(reader);
		}
	}

	
	public static Element createElement(Document doc, String name)
	{
		return createElement(doc, name, null);
	}
	
	
	public static Element createElement(Document doc, String name, String value)
	{
		Element element = doc.createElement(name);
		if (null != value)
		{
			Text text = doc.createTextNode(value);
			element.appendChild(text);
		}
		
		return element;
	}

	public static String getValue(Element element)
	{
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node child = nodeList.item(i);
			if (child.getNodeType() != Node.TEXT_NODE)
				continue;
			
			Text text = (Text) child;
			return text.getNodeValue();
		}
		
		return null;
	}

	public static long getChildLong(Element parent, String name)
	{
		String text = getChildText(parent, name);
		Long lval = new Long(text);
		return lval;
	}

	
	public static String getAbsolutePath(Element parent)
	{
		StringBuffer sb = new StringBuffer();
		buildPath(sb, parent);
		
		return sb.toString();
	}

	private static void buildPath(StringBuffer sb, Element element)
	{
		if (null == element)
			return;
		
		Node node = element.getParentNode();
		if (node instanceof Element)
		{
			Element parent = (Element) node;
			buildPath(sb, parent);
		}
		
		sb.append("/");
		sb.append(element.getNodeName());
	}

	public static void addText(Element node, String string)
	{
		Text text = node.getOwnerDocument().createTextNode(string);
		node.appendChild(text);
	}

}
