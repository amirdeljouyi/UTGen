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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

import jdk.internal.reflect.FieldAccessor;
import jdk.internal.reflect.ReflectionFactory;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.lang.reflect.ReflectionUtils;
import com.lts.util.CollectionUtils;
import com.lts.xml.XMLUtils;
import com.lts.xmlser.AbstractTag;
import com.lts.xmlser.FieldValue;
import com.lts.xmlser.SerializationUtils;
import com.lts.xmlser.XmlSerializer;
import com.lts.xmlser.fixups.FieldFixup;
import com.lts.xmlser.fixups.ReferenceFixup;

/**
 * A tag for handling serialization for a generic class.
 * 
 * <P/>
 * This class is not intended for use to serialize primitive or primitive
 * wrapper classes such as Integer.
 */
public class ObjectTag extends AbstractTag 
{
	public static final String STR_TYPE = "object";
	
	public String getTagName (Object o)
	{
		return o.getClass().getName();
	}
	
	
	public void setFinalValue (Object dest, Object value, Field theField)
	{
		theField.setAccessible(true);
		ReflectionFactory fact = ReflectionFactory.getReflectionFactory();
		FieldAccessor access = fact.newFieldAccessor(theField,true);
		try
		{
			access.set(dest, value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public FieldValue toFieldValue (
		String propertyName,
		Class targetClass,
		Element node,
		XmlSerializer xser
	)
		throws LTSException
	{
		FieldValue fv = new FieldValue();
		
		try
		{
			fv.theField = ReflectionUtils.getField(
				targetClass, 
				propertyName
			);
		}
		catch (NoSuchFieldException e)
		{
			if (xser.forgiving())
				return null;
			
			throw new LTSException (
				"Could not find field, " + propertyName
				+ ", in class " + targetClass.getName(),
				e
			);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
		
		fv.theValue = xser.readField(node, fv.theField.getType());
		return fv;
	}
	
	
	public void processProperty (
		String name, 
		Object dest, 
		Element child,
		XmlSerializer xser
	)
		throws LTSException
	{
		Field theField = null;
		
		try
		{
			theField = ReflectionUtils.getField(dest.getClass(), name);
		}
		catch (NoSuchFieldException e)
		{
			if (xser.forgiving())
				return;
			
			throw new LTSException (
				"Could not find field, " + name
				+ ", in class " + dest.getClass().getName(),
				e
			);
		}
		
		
		theField.setAccessible(true);
		Object value = xser.readField(child, theField.getType());
		try
		{			
			//
			// unfortunately, you have to resort to this in order to 
			// deserialize final, member variables
			//
			if (value instanceof ReferenceFixup)
			{
				ReferenceFixup f = (ReferenceFixup) value;
				FieldFixup fixup = new FieldFixup();
				fixup.destination = dest;
				fixup.id = f.id;
				fixup.field = theField;
				xser.addIdFixup(f.id, fixup);
			}
			
			else if (Modifier.isFinal(theField.getModifiers()))
			{
				setFinalValue(dest, value, theField);
			}
			else
			{
				theField.set(dest, value);
			}
			
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to set field " + theField.getName()
				+ ", in class " + dest.getClass().getName()
				+ ", to value " + value,
				e
			);
		}
	}
	
	
	public Object read (XmlSerializer xser, Element node, boolean forgiving)
		throws LTSException
	{
		String strClass = node.getAttribute(STR_ATTR_CLASS);
		if (null == strClass || "".equals(strClass))
		{
			strClass = node.getTagName();
		}
		
		Class c = null;
		try
		{
			c = Class.forName(strClass);
		}
		catch (ClassNotFoundException e)
		{
			throw new LTSException (
				"Could not find class, " + strClass,
				e
			);
		}

		
		List temp = new ArrayList();
		List l = XMLUtils.getChildElements(node);
		Iterator i = l.iterator();
		while (i.hasNext())
		{
			Element child = (Element) i.next();
			String propertyName = child.getNodeName();
			FieldValue fv = toFieldValue(propertyName, c, child, xser);
			if (null != fv)
				temp.add(fv);
		}
		
		List fixups = new ArrayList();
		FieldValue[] values = new FieldValue[temp.size()];
		int j;
		for (j = 0; j < values.length; j++)
		{
			FieldValue fv = (FieldValue) temp.get(j);
			if (fv.theValue instanceof ReferenceFixup)
			{
				ReferenceFixup rf = (ReferenceFixup) fv.theValue;
				
				FieldFixup fix = new FieldFixup();
				fix.field = fv.theField;
				fix.id = rf.id;
				fixups.add(fix);
				
				fv.theValue = null;
			}
			values[j] = (FieldValue) temp.get(j);
		}
		
		SerializationUtils utils = new SerializationUtils();
		Object result = utils.create(c, values, xser.forgiving());
		
		
		i = fixups.iterator();
		while (i.hasNext())
		{
			FieldFixup fix = (FieldFixup) i.next();
			fix.destination = result;
			xser.addIdFixup(fix.id, fix);
		}
		
		
		if (null != result)
		{
			String strID = getRequiredAttr(node, STR_ATTR_ID);
			Integer id = new Integer(strID);
			xser.addObject(id, result);
		}
		
		return result;
	}
	
	
	protected static class FieldComparator 
		implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			Field f1 = (Field) o1;
			Field f2 = (Field) o2;
			return f1.getName().compareTo(f2.getName());
		}
	}
	
	
	public void printHeader (
		XmlSerializer xser,
		IndentingPrintWriter out,
		String name,
		Object value,
		boolean printNewline
	)
	{
		int id = xser.addObject(value);
		
		String[] attrs = null;
		
		if (name.equals(value.getClass().getName()))
		{			
			attrs = new String[] { STR_ATTR_ID,	Integer.toString(id) };
		}
		else
		{
			attrs = new String[] {
			   STR_ATTR_ID,	Integer.toString(id),
			   STR_ATTR_CLASS, value.getClass().getName()
			};
		}
		
		printElement(out, name, attrs, false, printNewline);
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
		printHeader(xser, out, name, value, true);
		
		Field[] fields = ReflectionUtils.getAllFields(value.getClass());
		List l = CollectionUtils.toArrayList(fields);
		Collections.sort(l, new FieldComparator());
		
		out.increaseIndent();
		Iterator i = l.iterator();
		while (i.hasNext())
		{
			Field f = (Field) i.next();
			int mods = f.getModifiers();
			if (
				Modifier.isStatic(mods) 
				|| Modifier.isTransient(mods)
			)
			{	
				continue;
			}
			
			Object o = getFieldValue(value, f);
			if (null != o || xser.serializeNulls())
				xser.printField(out, o, f);
		}
		out.decreaseIndent();
		
		printClosingElement(out, name);
	}

}
