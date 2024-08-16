//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application.bundles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import com.lts.LTSException;
import com.lts.io.IOUtilities;

/**
 * Various utility methods having to do with ResourceBundle objects.
 * 
 * @author cnh
 */
public class BundleUtils
{
	public static ResourceBundle loadBundle (String baseName) throws LTSException
	{
		Locale locale = Locale.getDefault();
		return loadBundle(baseName, locale);
	}
	
	public static ResourceBundle loadBundle (String baseName, Locale locale) throws LTSException
	{
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
			return bundle;
		}
		catch (MissingResourceException e)
		{
			String msg = "Could not find resource bundle, " + baseName;
			throw new LTSException(msg,e);
		}
	}
	
	public static List toList (Enumeration en)
	{
		List list = new ArrayList();
		
		while (en.hasMoreElements())
		{
			list.add(en.nextElement());
		}
		
		return list;
	}
	
	
	public static void writeBundleMessageFile (File file, ResourceBundle bundle) throws LTSException
	{
		Enumeration en = bundle.getKeys();
		List names = toList(en);
		Collections.sort(names);
		
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(file);
			PrintWriter out = new PrintWriter(writer);
			
			for (Iterator i = names.iterator(); i.hasNext();)
			{
				String key = (String) i.next();
				String value = bundle.getString(key);
				out.println(key + "=" + value);
			}
		}
		catch (IOException e)
		{
			String msg = "Error opening or writing file, " + file;
			throw new LTSException(msg,e);
		}
		finally
		{
			IOUtilities.close(writer);
		}
	}

	
	public static void sortAndWriteFile (File file, Properties props) throws LTSException
	{
		List names = new ArrayList(props.keySet());
		Collections.sort(names);
		
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(file);
			PrintWriter out = new PrintWriter(writer);
			
			for (Iterator i = names.iterator(); i.hasNext();)
			{
				String key = (String) i.next();
				String value = props.getProperty(key);
				out.println(key + "=" + value);
			}
		}
		catch (IOException e)
		{
			String msg = "Error opening or writing file, " + file;
			throw new LTSException(msg,e);
		}
		finally
		{
			IOUtilities.close(writer);
		}
	}
	

	/**
	 * Get the values of all the public static String fields from the class passed 
	 * to us.
	 * 
	 * @param clazz
	 */
	public static Properties getStaticStrings (Class clazz) throws LTSException
	{
		Properties p = new Properties();
		
		Field[] allFields = clazz.getFields();
		for (int i = 0; i < allFields.length; i++)
		{
			Field field = allFields[i];

			if (!Modifier.isStatic(field.getModifiers()))
				continue;

			if (String.class.isAssignableFrom(field.getType()))
			{
				String name = field.getName();
				String value;
				
				try
				{
					value = (String) field.get(null);
					p.setProperty(name, value);
				}
				catch (Exception e)
				{
					String msg = 
						"Error trying to get value for field "
						+ name
						+ ", of class " 
						+ clazz.getName();
					
					throw new LTSException(msg,e);
				}
			}
		}
		
		return p;
	}
	
	
	public static Properties toProperties (Map map)
	{
		Properties p = new Properties();
		
		for (Iterator i = map.keySet().iterator(); i.hasNext();)
		{
			String name = (String) i.next();
			String value = (String) map.get(name);
			p.setProperty(name,value);
		}
		
		return p;
	}
	
	
	public static Properties toProperties (ResourceBundle bundle)
	{
		Properties p = new Properties();
		
		for (Enumeration en = bundle.getKeys(); en.hasMoreElements(); )
		{
			String name = (String) en.nextElement();
			String value = bundle.getString(name);
			p.setProperty(name,value);
		}
		
		return p;
	}
	
	

	/**
	 * Report each string constant that is not present in the map.
	 * 
	 * <H2>Description</H2>
	 * For each public static string field in the class passed to us, see if the value
	 * of that class has a non-null, non-empty value in the provided map.  If not, then
	 * print out a line using the PrintWriter that has the form:
	 * <P>
	 * <CODE>
	 * <PRE>
	 * constant_name, constant_value
	 * </PRE>
	 * </CODE>
	 * 
	 * @param out Where to report discrepencies.
	 * @param clazz The class to obtain fields from.
	 * @param map The map to check whether the fields are defined.
	 */
	public static void printUndefinedMessages (PrintWriter out, Class clazz, Map map) throws LTSException
	{
		Map fields = getStaticStrings(clazz);
		Set undefined = new HashSet(fields.keySet());
		undefined.removeAll(map.keySet());
		
		for (Iterator i = map.keySet().iterator(); i.hasNext();)
		{
			String key = (String) i.next();
			String value = (String) map.get(key);
			
			if (null == value || "".equals(value))
				undefined.add(key);
		}
		
		List list = new ArrayList(undefined);
		Collections.sort(list);
		
		for (Iterator i = list.iterator(); i.hasNext(); )
		{
			String fieldName = (String) i.next();
			String fieldValue = (String) fields.get(fieldName);
			out.println(fieldName + ", " + fieldValue);
		}
	}
	
	
	/**
	 * Report all the properties in p1 that are absent from p2.
	 * 
	 * <P>
	 * The results are sorted and printed out in the form name, value.
	 * 
	 * @param out Where to report.
	 * @param p1 The reference set of properties.
	 * @param p2 The set of properties that may not cover all in p1.
	 * @throws LTSException If a propblem occurs.
	 */
	public static void printMissing (PrintWriter out, Properties p1, Properties p2) throws LTSException
	{
		Set undefined = new HashSet(p1.keySet());
		undefined.removeAll(p2.keySet());
		
		for (Iterator i = p1.keySet().iterator(); i.hasNext();)
		{
			String key = (String) i.next();
			String value = p1.getProperty(key);
			
			if (null == value || "".equals(value))
				undefined.add(key);
		}
		
		List list = new ArrayList(undefined);
		Collections.sort(list);
		
		for (Iterator i = list.iterator(); i.hasNext(); )
		{
			String fieldName = (String) i.next();
			String fieldValue = (String) p1.get(fieldName);
			out.println(fieldName + "=" + fieldValue);
		}
	}
	
	
	public static void writeUndefinedMessages (File file, Class clazz, Map map) throws LTSException 
	{
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(file);
			PrintWriter out = new PrintWriter(writer);
			
			printUndefinedMessages(out, clazz, map);
			
			out.close();
		}
		catch(LTSException e)
		{
			throw e;
		}
		catch (IOException e)
		{
			String msg = 
				"Error trying to write file, " + file;
			
			throw new LTSException(msg, e);
		}
		finally
		{
			IOUtilities.close(writer);
		}
	}
	
	
	public static Properties invertKeysAndValues (Properties p)
	{
		Properties newProps = new Properties();
		
		for (Enumeration en = p.propertyNames(); en.hasMoreElements(); )
		{
			String key = (String) en.nextElement();
			String value = p.getProperty(key);
			newProps.setProperty(value, key);
		}
		
		return newProps;
	}
	
	
	public static void writeUndefined (File file, Class clazz, ResourceBundle bundle) throws LTSException
	{
		FileWriter writer = null;
		
		try
		{
			Properties messages = toProperties(bundle);
			Properties fields = getStaticStrings(clazz);
			Properties fieldValues = invertKeysAndValues(fields);
			
			writer = new FileWriter(file);
			PrintWriter out = new PrintWriter(writer);
			
			out.println("Fields that have no messages: ");
			out.println();
			
			printMissing(out, fieldValues, messages);
			
			out.println();
			out.println("Messages that have no fields: ");
			out.println();
			
			printMissing(out, messages, fieldValues);
			
			out.close();
		}
		catch (IOException e)
		{
			String msg =
				"Error writing file " + file;
			throw new LTSException(msg, e);
		}
		finally
		{
			IOUtilities.close(writer);
		}
		
	}

}
