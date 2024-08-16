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
package com.lts.xml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Element;

import com.lts.LTSException;

/**
 * A utility to access object properties.
 * 
 * @author cnh
 */
public class XMLPropertyUtil
{
	protected static XMLPropertyUtil ourInstance;
	
	public static XMLPropertyUtil getInstance()
	{
		if (null == ourInstance)
			ourInstance = new XMLPropertyUtil();
		
		return ourInstance;
	}
	
	
	protected Map myClassToPropertyMap;
	
	public XMLPropertyUtil ()
	{
		myClassToPropertyMap = new HashMap();
	}
	
	/**
	 * Create a map from property names (String) to setter methods (Method).
	 * 
	 * <H2>Description</H2>
	 * Only java bean style methods are discovered.
	 * 
	 * @param clazz The class that the map is being built for. 
	 * @return The resulting map.
	 * @throws LTSException If a problem is encountered.
	 */
	public Map buildPropertyMap (Class clazz) throws LTSException
	{
		try
		{
			Map map = new HashMap();

			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for (int i = 0; i < props.length; i++)
			{
				map.put(props[i].getName(), props[i]);
			}
			
			return map;
		}
		catch (IntrospectionException e)
		{
			throw new LTSException(e);
		}
	}
	
//	public Map buildPropertyMap (Class clazz) throws LTSException
//	{
//		try
//		{
//			Map map = new HashMap();
//
//			BeanInfo info = Introspector.getBeanInfo(clazz);
//			PropertyDescriptor[] props = info.getPropertyDescriptors();
//			for (int i = 0; i < props.length; i++)
//			{
//				Method method = props[i].getWriteMethod();
//				map.put(props[i].getName(), method);
//			}
//			
//			return map;
//		}
//		catch (IntrospectionException e)
//		{
//			throw new LTSException(e);
//		}
//	}
	
	/**
	 * Get the setter method for a property of a class.
	 * 
	 * <H2>Description</H2>
	 * This method gets the setter method for a property of a java bean style class.
	 * Generally, this method will be called "set&lt;Property&gt;".
	 * 
	 * @param clazz The class the setter method is being obtained for.
	 * @param name The property to get the setter for.
	 * @return The setter method, or null if no setter could be found.
	 * @throws LTSException If a problem occurs.
	 */
	public Method getWriteMethod(Class clazz, String name)
			throws LTSException
	{
		Map map = (Map) myClassToPropertyMap.get(clazz);
		if (null == map)
		{
			map = buildPropertyMap(clazz);
		}
		
		Method method = (Method) map.get(name);
		return method;
	}
	
	
	/**
	 * Get the java.bean.Property object for the given class an property name, or 
	 * return null if the property does not exist.
	 */
	public PropertyDescriptor getProperty (Class clazz, String name) throws LTSException
	{
		Map map = (Map) myClassToPropertyMap.get(clazz);
		if (null == map)
		{
			map = buildPropertyMap(clazz);
		}
		
		PropertyDescriptor prop = (PropertyDescriptor) map.get(name);
		return prop;
	}
	
	/**
	 * Use a setter method to write a property value.
	 * 
	 * @param o The object whose value they are going to set.
	 * @param method The setter method to use.
	 * @param value The value to set.
	 * @throws LTSException If a problem occurs.
	 */
	public void writeProperty(boolean throwException, Object o, Method method,
			Object value) throws LTSException
	{
		try
		{
			Object[] data = { value };
			method.invoke(o, data);
		}
		catch (Exception e)
		{
			if (throwException)
				throw new LTSException(e);
		}
	}
	
	/**
	 * Convert a string value to a value that is assignment compatible with the class
	 * being passed to us.
	 * 
	 * @param throwException
	 *        If true and conversion is not possible, throw an exception that explains the
	 *        problem.
	 * @param clazz
	 *        The "destination" type.
	 * @param value
	 *        The value we are trying to assign.
	 * @return A value that is assignment compatible with the destination class.
	 */
	public Object convertValue (Class clazz, String value) throws LTSException
	{
		try
		{
			if (null == value)
				return null;
			
			Object result;
			
			//
			// Go through the types we know how to convert
			//
			if (clazz == String.class)
				result = value;
			else if (clazz == Integer.class || clazz == Integer.TYPE)
				result = new Integer(value);
			else if (clazz == Short.class || clazz == Short.TYPE)
				result = new Short(value);
			else if (clazz == Long.class || clazz == Long.TYPE)
				result = new Long(value);
			else if (clazz == Double.class || clazz == Double.TYPE)
				result = new Double(value);
			else if (clazz == Float.class || clazz == Float.TYPE)
				result = new Float(value);
			else if (clazz == Byte.class || clazz == Byte.TYPE)
				result = new Byte(value);
			else if (clazz == Boolean.class || clazz == Boolean.TYPE)
				result = new Boolean(value);
			else if (clazz == Character.class || clazz == Character.TYPE)
				result = new Character(value.charAt(0));
			else
			{
				String msg = 
					"Unsupported type: " + clazz.getName();
				throw new LTSException(msg);
			}
			
			return result;
		}
		catch (NumberFormatException e)
		{
			String msg =
				"Error converting the value, " + value + " to an instance of "
				+ clazz.getName();
			throw new LTSException(msg,e);
		}
	}
	
	/**
	 * Set the property of an object to a value.
	 * 
	 * @param o The object whose property is going to be set.
	 * @param name The property to set.
	 * @param value The value to set it to.
	 * @throws LTSException If a problem occurs.
	 */
	public void setProperty (boolean throwException, Object o, String name, String value) throws LTSException
	{
		if (null == o)
			return;
		
		Class clazz = o.getClass();
		PropertyDescriptor prop = getProperty(clazz, name);
		if (null == prop)
		{
			if (!throwException)
				return;
			
			String msg = 
				"The class, " + clazz.getName() + ", does not have a property "
				+ "named " + name;
			throw new LTSException(msg);
		}
		
		
		Method method = prop.getWriteMethod();
		if (null == method)
		{
			if (!throwException)
				return;
			
			String msg =
				"Property " + name + " is not writeable for class " + clazz.getName();
			
			throw new LTSException(msg);
		}
		

		try
		{
			Object pvalue = convertValue(prop.getPropertyType(), value);
			writeProperty(throwException, o, method, pvalue);
		}
		catch (LTSException e)
		{
			if (!throwException)
				return;
			
			throw e;
		}
		
	}
	
	/**
	 * Set the properties of a JavaBean from an XML element.
	 * 
	 * @param o The bean whose values to set.
	 * @param element The element that has the bean's values.
	 * @throws LTSException If an error occurs while setting the properties.
	 */
	public void setProperties (Object o, Element element) throws LTSException
	{
		Map map = XMLUtils.getAttributes(element);
		for (Iterator i = map.keySet().iterator(); i.hasNext();)
		{
			String key = (String) i.next();
			String value = (String) map.get(key);
			setProperty(false, o, key, value);
		}
	}
}
