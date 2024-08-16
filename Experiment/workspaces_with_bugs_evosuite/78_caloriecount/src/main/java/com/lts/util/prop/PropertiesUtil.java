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
package com.lts.util.prop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lts.LTSException;

/**
 * Java 1.4 and above have made this class obsolete. 
 * 
 * @author cnh
 *
 */
public class PropertiesUtil
{
	public static class PropertyRef
	{
		public int start;
		public int stop;
		public String refstr;
		public String propKey;
	}
	
	
	/**
	 * Return the property refence in the provided string or null if the string contains
	 * no property reference.
	 * 
	 * @param str The string to check.
	 * @return The property reference or null if one does not exist.
	 */
	public static PropertyRef getRef (String str)
	{
		PropertyRef ref = null;
		
		int start = str.indexOf("${");
		int stop = str.indexOf("}", start);
		
		if (-1 != start && -1 != stop)
		{
			ref = new PropertyRef();
			ref.start = start;
			ref.stop = stop;
			ref.refstr = str.substring(2 + start, stop);
		}
		
		return ref;
	}
	
	
	/**
	 * Replace a property reference in a value with the value of the property.
	 * 
	 * <H2>Description</H2>
	 * Here is an example: suppose a property called "one" is defined to have 
	 * the value "1".  Then if there is a property called "two" with the value 
	 * "1 + ${one} = 2", then after this method is used, the property "two" 
	 * would have the value: "1 + 1 = 2"
	 *  
	 * @param referringKey The property key (name) whose value contains a reference.
	 * @param referringValue The original value of referringKey.
	 * @param ref The PropertyRef created from the referringValue.
	 * @param p The properties object where the replacement should take place.
	 */
	public static void replaceRef(String referringKey, String referringValue, PropertyRef ref,
			Properties p)	
	{
		String value = p.getProperty(ref.refstr);
		String prefix = referringValue.substring(0, ref.start);
		String suffix = referringValue.substring(1 + ref.stop);
		String newValue = prefix + value + suffix;
		p.setProperty(referringKey, newValue);
	}
	
	
	/**
	 * Search through a properties object for a value that references another property.
	 * 
	 * <H2>Description</H2>
	 * This method returns a PropertyRef object for the first reference the method can 
	 * find in the properties object.  The references are not guaranteed to 
	 * occur in any particular order.  If no value could be found that references another
	 * property, then the method returns null.
	 * 
	 * @param p The properties object to search.
	 * @return A reference in the properties object, or null if none could be found.
	 */
	public static PropertyRef findRef (Properties p)
	{
		PropertyRef ref = null;
		Enumeration en = p.propertyNames();
		while (null == ref && en.hasMoreElements())
		{
			String key = (String) en.nextElement();
			String value = p.getProperty(key);
			ref = getRef (value);
			if (null != ref)
				replaceRef(key, value, ref, p);
		}
		
		return ref;
	}
	
	/**
	 * Replace all the property values that reference other properties in the provided
	 * properties object.
	 * 
	 * <H2>Description</H2>
	 * This method performs replacements until there are no more references to replace
	 * or the method reaches an upper limit on the number of replacements to make.
	 * If the method reaches the upper limit, it throws an exception, otherwise it 
	 * returns normally.
	 * 
	 * @param p The properties to perform replacements in.
	 * @param limit The maximum number of replacements to make.
	 * @throws LTSException If the maximum number of replacements are made.
	 */
	public static void resolveReferences (Properties p, int limit) throws LTSException
	{
		int count = 0;
		PropertyRef ref = findRef(p);
		while (null != ref && count < limit)
		{
			ref = findRef(p);
			count++;
		}
		
		if (count >= limit)
		{
			String msg =
				"Reached maximum number of replacements: " + limit;
			throw new LTSException(msg);
		}
	}
	
	/**
	 * Replace all the property values that reference other properties in the provided
	 * properties object.
	 * 
	 * <H2>Description</H2>
	 * This method performs replacements until there are no more references to replace
	 * or the method reaches an upper limit on the number of replacements to make.
	 * If the method reaches the upper limit, it throws an exception, otherwise it 
	 * returns normally.
	 * 
	 * <P>
	 * The upper limit is 10000 for this method.
	 * 
	 * @param p The properties to perform replacements in.
	 * @throws LTSException If the maximum number of replacements are made.
	 */	
	public static void resolveReferences (Properties p) throws LTSException
	{
		resolveReferences(p, 10000);
	}
	
	/**
	 * Create a properties object from a Map object, such as might be returned by 
	 * System.getenv.
	 * 
	 * <H2>Description</H2>
	 * The keys and values of the map must be instances java.lang.String or the 
	 * method will probably throw a {@link ClassCastException}.
	 * 
	 * @param map The map to convert.
	 * @return The map as an instance of java.util.Properties.
	 */
	public static Properties build (Map map)
	{
		Properties p = new Properties();
		
		for (Iterator i = map.keySet().iterator(); i.hasNext();)
		{
			String name = (String) i.next();
			String value = (String) map.get(name);
			p.setProperty(name, value);
		}
		
		return p;
	}
	
	
	public static Properties toProperties (Map map)
	{
		return build(map);
	}
	
	
	public static void print (Properties p)
	{
		Iterator i = p.keySet().iterator();
		while (i.hasNext())
		{
			String name = (String) i.next();
			String value = (String) p.get(name);
			
			System.out.println(name + " = " + value);
		}
	}
	
	
	public static void printSorted (Properties p)
	{
		List list = new ArrayList(p.keySet());
		Collections.sort(list);
		for (Iterator i = list.iterator(); i.hasNext();)
		{
			String name = (String) i.next();
			String value = p.getProperty(name);
			System.out.println(name + " = " + value);
		}
	}
	
	
	public static List toSortedKeys (Properties p)
	{
		List list = new ArrayList(p.keySet());
		Collections.sort(list);
		return list;
	}
	
	public static List getPropertyNames (Properties p)
	{
		Collection c = p.keySet();
		return new ArrayList(c);		
	}
}
