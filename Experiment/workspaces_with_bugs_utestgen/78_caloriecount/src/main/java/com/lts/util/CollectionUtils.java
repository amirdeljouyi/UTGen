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
package com.lts.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import com.lts.LTSRuntimeException;

/**
 * Some useful utility methods for collections.
 */
public class CollectionUtils
{
	/**
	 * Add an array of values to a collection.
	 * 
	 * <P/>
	 * This method simply calls Collection.add for each element of the array.
	 * 
	 * @param col The collection to add the array elements to.
	 * @param data The array of data to add to the collection.
	 */
	public static void addAll (Collection col, Object[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			col.add(data[i]);
		}
	}
	
	public static HashSet toHashSet (Object[] spec)
	{
		HashSet s = new HashSet();
		addAll(s, spec);
		return s;
	}
	
	
	public static IdentityHashSet toIdentityHashSet (Object[] spec)
	{
		IdentityHashSet s = new IdentityHashSet();
		addAll(s, spec);
		return s;
	}
	
	/**
	 * Add an array of key/value pairs to a map.
	 * 
	 * <P/>
	 * This method takes an array that is arranged as key/value pairs and 
	 * adds each pair to the provided map object via the Map.put method.  Even
	 * numbered array elements are used as keys, while odd numbered elements
	 * are values.
	 * 
	 * <P/>
	 * For example:
	 * 
	 * <PRE>
	 * Map m = new HashMap();
	 * Object[] data = { "one", "1", "two", "2" };
	 * CollectionUtils.addToMap (m, data);
	 * </PRE>
	 * 
	 * Would map the string "one" to the string "1" and the string "two" to 
	 * the string "2".
	 *
	 * @param m The map to which the data should be added. 
	 * @param spec The array of data to add to the map.
	 */
	public static void addToMap (Map m, Object[] spec)
	{
		for (int i = 0; i < spec.length; i = i + 2)
		{
			Object key = spec[i];
			Object value = spec[1+i];
			m.put(key, value);
		}
	}
	
	public static HashMap toHashMap (Object[] spec)
	{
		HashMap m = new HashMap();
		addToMap(m, spec);
		return m;
	}
	
	
	public static ArrayList toArrayList (Object[] data)
	{
		ArrayList l = new ArrayList();
		addAll(l, data);
		return l;
	}
	
	
	public static Properties buildProperties (String[] spec, String novalue)
	{
		Properties p = new Properties();
		
		for (int i = 0; i < spec.length; i = i + 2)
		{
			String name = spec[i];
			String value = spec[1+i];
			
			if (null != novalue && !value.equals(novalue))
				p.setProperty(name, value);
		}
		
		return p;
	}
	
	
	public static Properties buildProperties (String[] spec)
	{
		Properties p = new Properties();
		
		for (int i = 0; i < spec.length; i = i + 2)
		{
			String key = spec[i];
			String value = spec[1+i];

			p.setProperty(key, value);
		}
		
		return p;
	}
	
	
	public static void addProperties (Properties p, String[] spec)
	{
		if (null == spec)
			return;
		
		for (int i = 0; i < spec.length; i = i + 2)
		{
			String name = spec[i];
			String value = spec[i+1];
			
			p.setProperty(name, value);
		}
	}
	
	
	public static Properties buildProperties (String[][] multiSpec)
	{
		Properties p = new Properties();
		
		if (null != multiSpec)
		{
			for (int i = 0; i < multiSpec.length; i++)
			{
				String[] spec = multiSpec[i];
				addProperties(p, spec);
			}
		}
		
		return p;
	}
	
	
	public static void addAll (List names, String[] spec)
	{
		if (null == names || null == spec)
			return;
		
		for (int i = 0; i < spec.length; i++)
		{
			names.add(spec[i]);
		}
	}
	
	
	public static List buildPropertyNames (String[][] propertySpec)
	{
		List l = new ArrayList();
		
		if (null != propertySpec)
		{
			for (int i = 0; i < propertySpec.length; i++)
			{
				addAll(l, propertySpec[i]);
			}
		}
		
		return l;
	}
	
	/**
	 * Print out the contents of a stack, spearating each element with the provided 
	 * string.
	 * 
	 * @param stack
	 * @param separator
	 * @return
	 */
	public static String stackToString(Stack stack, String separator,
			boolean endWithSeparator)
	{
		StringBuffer sb = new StringBuffer();
		
		if (null == separator)
			separator = ", ";
		
		int size = stack.size();
		for (int i = 0; i < size; i++)
		{
			if (i > 0)
				sb.append(separator);
			
			sb.append(stack.get(i));
		}
		
		if (size > 0 && endWithSeparator)
			sb.append(separator);
		
		return sb.toString();
	}
	
	
	/**
	 * Create a map from an array of 2-element arrays.
	 * 
	 * <H2>Description</H2>
	 * The "component" arrays are not required to have exactly two elements, but
	 * they must have at least two elements each: a key and a value.
	 * 
	 * @param spec The array of key-value arrays.
	 * @return The resulting map.
	 */
	public static Map buildMap (Object[][] spec)
	{
		Map map = new HashMap();
		
		for (int i = 0; i < spec.length; i++)
		{
			Object key = spec[i][0];
			Object value = spec[i][1];
			
			map.put(key, value);
		}
		
		return map;
	}
	
	
	/**
	 * Create a new list that contains the elements in the provided Enumeration.
	 * 
	 * The elements occur in the list in the same order that they occur in the 
	 * Enumeration.  An empty enumeration results in an empty list (but non-null list).
	 * A null enumeration will cause a NullPointerException.
	 * 
	 * @param e The Enumeration to convert.
	 * @return The resulting list.
	 */
	public static List toList (Enumeration e)
	{
		List list = new ArrayList();
		
		while (e.hasMoreElements())
		{
			list.add(e.nextElement());
		}
		
		return list;
	}
	
	/**
	 * Convert a properties object, such as the one returned by System.getProperties, into
	 * an array of strings of the form "name=value".
	 * 
	 * Any special characters in the values are not escaped.  A null value for the 
	 * Properties argument will result in a NullPointerException.
	 * 
	 * @param p The properties to convert
	 * @return The resulting array as described above.
	 */
	public static String[] toStringArray (Properties p)
	{
		Set keySet = p.keySet();
		String[] out = new String[keySet.size()];
		Iterator i = keySet.iterator();
		int index = 0;
		
		while (i.hasNext())
		{
			String name = (String) i.next();
			String value = p.getProperty(name);
			out[index] = name + "=" + value;
			index++;
		}
		
		return out;
	}
	
	
	public static void dump (Collection col)
	{
		PrintWriter out = null;
		
		try
		{
			out = new PrintWriter(System.out);
			dump (out, col);
		}
		finally
		{
			if (null != out)
				out.flush();
		}
	}
	
	
	public static void dump (PrintWriter out, Collection col)
	{
		if (null == col)
			return;
		
		for (Iterator i = col.iterator(); i.hasNext(); )
		{
			Object o = i.next();
			out.println(o.toString());
		}
	}
	
	
	public static int compareInts(int i1, int i2)
	{
		if (i1 < i2)
			return -1;
		else if (i1 > i2)
			return 1;
		else
			return 0;
	}
	
	
	public static Comparator<Integer> INT_DESCENDING = new Comparator<Integer>() {
		public int compare(Integer i1, Integer i2)
		{
			return compareInts(i2, i1);
		}
	};
	
	public static Comparator<Integer> INT_ASCENDING = new Comparator<Integer>() {
		public int compare(Integer i1, Integer i2)
		{
			return compareInts(i1, i2);
		}
	};
	
	public static Comparator<String> STRING_ASCENDING = new Comparator<String>() {
		public int compare (String s1, String s2)
		{
			return s1.compareTo(s2);
		}
	};
	
	public static Comparator<String> STRING_DESCENDING = new Comparator<String>() {
		public int compare (String s1, String s2)
		{
			return s2.compareTo(s1);
		}
	};
	
	public static void sortDescending(List list)
	{
		if (list.size() < 1)
			return;
		
		Comparator comp = null;
		Object o = list.get(0);
		
		if (o instanceof Integer)
			comp = INT_DESCENDING;
		else if (o instanceof String)
			comp = STRING_DESCENDING;
		else 
		{
			String msg = "Unsupported type: " + o.getClass();
			throw new LTSRuntimeException(msg);
		}
		
		Collections.sort(list, comp);
	}
	
}
