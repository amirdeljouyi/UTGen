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


import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class PropertyUtil
{
    public static Properties combine (Properties p1, Properties p2)
    {
        Properties result = new Properties();
        Enumeration enu = p1.keys();
        while (enu.hasMoreElements())
        {
            String theKey = (String) enu.nextElement();
            String theValue = p1.getProperty(theKey);
            result.setProperty(theKey, theValue);
        }
        
        enu = p2.keys();
        while (enu.hasMoreElements())
        {
            String theKey = (String) enu.nextElement();
            String theValue = p2.getProperty(theKey);
            
            result.setProperty(theKey, theValue);
        }
        
        return result;
    }
    
    
    public static String[][] toSortedList (Properties p)
    {
    	List list = new ArrayList(p.keySet());
    	Collections.sort(list);
    	String[][] array = new String[list.size()][];
    	for (int i = 0; i < array.length; i++)
    	{
    		String name = (String) list.get(i);
    		String value = p.getProperty(name);
    		array[i] = new String[]{ name, value };
    	}
    	
    	return array;
    }
    
    
    public static String[][] toSortedList (Map map)
    {
    	List list = new ArrayList(map.keySet());
    	Collections.sort(list);
    	String[][] array = new String[list.size()][];
    	for (int i = 0; i < array.length; i++)
    	{
    		String name = (String) list.get(i);
    		String value = (String) map.get(name);
    		array[i] = new String[]{ name, value };
    	}
    	
    	return array;
    }
    
    
    protected static Properties createInstance (Properties p)
    {
    	String className = p.getClass().getName();
    	
    	try
		{
			return p = p.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			String msg = 
				"Exception thrown while trying to create instance of "
				+ className;
			
			throw new RuntimeException(msg, e);
		}
		catch (IllegalAccessException e)
		{
			String msg = 
				"public, no-arg constructor is not available for class "
				+ className;
			
			throw new RuntimeException(msg, e);
		}
    }
    
    
    public static Properties filter (Properties p, Set names)
    {
    	Properties newProperties = createInstance(p);
    	Enumeration enumer = p.propertyNames();
    	while (enumer.hasMoreElements())
    	{
    		String name = (String) enumer.nextElement();
    		if (names.contains(name))
    		{
    			String value = p.getProperty(name);
    			newProperties.put(name, value);
    		}
    	}
    	
    	return newProperties;
    }
    
    
    public static void dump (Map map, PrintWriter out)
    {
    	String[][] values = toSortedList(map);
    	for (int i = 0; i < values.length; i++)
    	{
    		out.println(values[i][0] + " = " + values[i][1]);
    	}
    }
    
    
    public static void dump (Map map, PrintStream pstream)
    {
    	PrintWriter out = new PrintWriter(pstream);
    	dump (map, out);
    	out.flush();
    }
    
    public static void dump ()
    {
    	dump (System.getProperties(), System.out);
    }
    
    
    public static void dump (Map map)
    {
    	dump (map, System.out);
    }
    
    
    static public Boolean getBoolean(String name, Properties properties)
    {
    	try
		{
			String value = properties.getProperty(name);
			if (null == value)
				return null;
			else
				return new Boolean(value);
		}
		catch (RuntimeException e)
		{
			return null;
		}
    }
}
    
