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
package com.lts.beans;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utilities that are useful when working with java.beans.Introspector.
 * 
 * @author cnh
 * @see java.beans.Introspector
 */
public class IntrospectorUtils
{
	/**
	 * Given a BeanInfo object, build a map from property name to PropertyDescriptor map.
	 * 
	 * @param info The info to build the map from.
	 * @return A map from property names (String) to property descriptors 
	 * (PropertyDescriptor).
	 */
	public static Map buildNameDescriptorMap (BeanInfo info)
	{
		Map map = new HashMap();
		PropertyDescriptor[] descs = info.getPropertyDescriptors();
		for (int i = 0; i < descs.length; i++)
		{
			map.put(descs[i].getName(), descs[i]);
		}
		
		return map;
	}
	
	/**
	 * Create a map from property names to setter methods.
	 * 
	 * <H2>Description</H2>
	 * Note that properties that have getter methods may not have setter methods 
	 * (i.e., they are read-only).
	 * 
	 * @param info The info to build the map from.
	 * @return A map from property names (String) to setter methods (Method).
	 */
	public static Map buildNameSetterMap (BeanInfo info)
	{
		Map nameToDesc = buildNameDescriptorMap(info);
		Map nameToSetter = new HashMap();
		
		for (Iterator i = nameToDesc.keySet().iterator(); i.hasNext();)
		{
			String name = (String) i.next();
			PropertyDescriptor desc = (PropertyDescriptor) nameToDesc.get(name);
			if (null != desc.getWriteMethod())
				nameToSetter.put(name, desc.getWriteMethod());
		}
		
		return nameToSetter;
	}
}
