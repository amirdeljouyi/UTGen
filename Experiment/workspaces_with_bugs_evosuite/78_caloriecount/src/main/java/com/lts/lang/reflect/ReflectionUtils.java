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
 * Copyright (c) 2003 Long Term Software, LLC.
 */
package com.lts.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lts.util.CaselessMap;
import com.lts.util.StringIntMap;

/**
 * A class that provides some utility methods that are useful when dealing 
 * with the reflection API.
 * 
 * @author cnh
 */
public class ReflectionUtils
{
	public static final Object[] SPEC_SIMPLE_TYPES = {
		Boolean.TYPE, 
		Character.TYPE, 
		Byte.TYPE, 
		Short.TYPE, 
		Integer.TYPE, 
		Long.TYPE, 
		Float.TYPE, 
		Double.TYPE,
		Boolean.class,
		Character.class,
		Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		Float.class,
		Double.class,
		String.class
	};
	
	public static final String[] SIMPLE_SHORT_NAMES = {
		"boolean",
		"char",
		"byte",
		"short",
		"int",
		"long",
		"float",
		"double",
		"Boolean",
		"Character",
		"Byte",
		"Short",
		"Integer",
		"Long",
		"Float",
		"Double",
		"String"
	};
	
	
	public static final int SIMPLE_UNKNOWN = 0;
	public static final int SIMPLE_BOOLEAN = 1;
	public static final int SIMPLE_CHAR = 2;
	public static final int SIMPLE_BYTE = 3;
	public static final int SIMPLE_SHORT = 4;
	public static final int SIMPLE_INT = 5;
	public static final int SIMPLE_LONG = 6;
	public static final int SIMPLE_FLOAT = 7;
	public static final int SIMPLE_DOUBLE = 8;
	public static final int SIMPLE_WRAP_BOOLEAN = 9;
	public static final int SIMPLE_CHARACTER = 10;
	public static final int SIMPLE_WRAP_BYTE = 11;
	public static final int SIMPLE_WRAP_SHORT = 12;
	public static final int SIMPLE_INTEGER = 13;
	public static final int SIMPLE_WRAP_LONG = 14;
	public static final int SIMPLE_WRAP_FLOAT = 15;
	public static final int SIMPLE_WRAP_DOUBLE = 16;
	public static final int SIMPLE_STRING = 17;
	public static final int SIMPLE_DATE = 18;
	
	public static final Object[] SPEC_SIMPLE_TO_LONG_NAME = {
		new Integer(SIMPLE_BOOLEAN),      "boolean",
		new Integer(SIMPLE_CHAR),         "char",
		new Integer(SIMPLE_BYTE),         "byte",
		new Integer(SIMPLE_SHORT),        "short",
		new Integer(SIMPLE_INT),          "int",
		new Integer(SIMPLE_LONG),         "long",
		new Integer(SIMPLE_FLOAT),        "float",
		new Integer(SIMPLE_DOUBLE),       "double",
		new Integer(SIMPLE_WRAP_BOOLEAN), "java.lang.Boolean",
		new Integer(SIMPLE_CHARACTER),    "java.lang.Character",
		new Integer(SIMPLE_WRAP_BYTE),    "java.lang.Byte",
		new Integer(SIMPLE_WRAP_SHORT),   "java.lang.Short",
		new Integer(SIMPLE_INTEGER),      "java.lang.Integer",
		new Integer(SIMPLE_WRAP_LONG),    "java.lang.Long",
		new Integer(SIMPLE_WRAP_FLOAT),   "java.lang.Float",
		new Integer(SIMPLE_WRAP_DOUBLE),  "java.lang.Double",
		new Integer(SIMPLE_STRING),       "java.lang.String"
	};
	
	
	public static final Object[] SPEC_SIMPLE_TO_SHORT_NAME = {
		new Integer(SIMPLE_BOOLEAN),      "boolean",
		new Integer(SIMPLE_CHAR),         "char",
		new Integer(SIMPLE_BYTE),         "byte",
		new Integer(SIMPLE_SHORT),        "short",
		new Integer(SIMPLE_INT),          "int",
		new Integer(SIMPLE_LONG),         "long",
		new Integer(SIMPLE_FLOAT),        "float",
		new Integer(SIMPLE_DOUBLE),       "double",
		new Integer(SIMPLE_WRAP_BOOLEAN), "Boolean",
		new Integer(SIMPLE_CHARACTER),    "Character",
		new Integer(SIMPLE_WRAP_BYTE),    "Byte",
		new Integer(SIMPLE_WRAP_SHORT),   "Short",
		new Integer(SIMPLE_INTEGER),      "Integer",
		new Integer(SIMPLE_WRAP_LONG),    "Long",
		new Integer(SIMPLE_WRAP_FLOAT),   "Float",
		new Integer(SIMPLE_WRAP_DOUBLE),  "Double",
		new Integer(SIMPLE_STRING),       "String"
	};
	
	
	public static final Object[] SPEC_CLASS_TO_SIMPLE_TYPE = {
		Boolean.TYPE,		new Integer(SIMPLE_BOOLEAN),
		Character.TYPE, 	new Integer(SIMPLE_CHAR),
		Byte.TYPE, 			new Integer(SIMPLE_BYTE),
		Short.TYPE, 		new Integer(SIMPLE_SHORT),
		Integer.TYPE, 		new Integer(SIMPLE_INT),
		Long.TYPE, 			new Integer(SIMPLE_LONG),
		Float.TYPE, 		new Integer(SIMPLE_FLOAT),
		Double.TYPE,		new Integer(SIMPLE_DOUBLE),
		Boolean.class,		new Integer(SIMPLE_WRAP_BOOLEAN),
		Character.class,	new Integer(SIMPLE_CHARACTER),
		Byte.class,			new Integer(SIMPLE_WRAP_BYTE),
		Short.class,		new Integer(SIMPLE_WRAP_SHORT),
		Integer.class,		new Integer(SIMPLE_INTEGER),
		Long.class,			new Integer(SIMPLE_WRAP_LONG),
		Float.class,		new Integer(SIMPLE_WRAP_FLOAT),
		Double.class,		new Integer(SIMPLE_WRAP_DOUBLE),
		String.class,		new Integer(SIMPLE_STRING)
	};
	
	
	public static final int PRIMITIVE_BOOLEAN = 0;
	public static final int PRIMITIVE_BYTE = 1;
	public static final int PRIMITIVE_CHAR = 2;
	public static final int PRIMITIVE_DOUBLE = 3;
	public static final int PRIMITIVE_FLOAT = 4;
	public static final int PRIMITIVE_INT = 5;
	public static final int PRIMITIVE_LONG = 6;
	public static final int PRIMITIVE_SHORT = 7;

	public static final Object[] SPEC_CLASS_TO_PRIMITIVE_CODE = {
		Boolean.TYPE,		new Integer(PRIMITIVE_BOOLEAN),
		Byte.TYPE,			new Integer(PRIMITIVE_BYTE),
		Character.TYPE,		new Integer(PRIMITIVE_CHAR),
		Double.TYPE,		new Integer(PRIMITIVE_DOUBLE),
		Float.TYPE,			new Integer(PRIMITIVE_FLOAT),
		Integer.TYPE,		new Integer(PRIMITIVE_INT),
		Long.TYPE,			new Integer(PRIMITIVE_LONG),
		Short.TYPE,			new Integer(PRIMITIVE_SHORT)
	};

	protected static Map ourClassToPrimitiveCodeMap = 
		new CaselessMap(SPEC_CLASS_TO_PRIMITIVE_CODE);
	
	protected static Set ourSimpleTypes;
	protected static StringIntMap ourSimpleLongNameMap 
		= new StringIntMap (SPEC_SIMPLE_TO_LONG_NAME);
	
	protected static StringIntMap ourSimpleShortNameMap
		= new StringIntMap (SPEC_SIMPLE_TO_SHORT_NAME);
	
	protected static Map ourClassSimpleMap = 
		new CaselessMap (SPEC_CLASS_TO_SIMPLE_TYPE);
	
	
	public static int classToPrimitiveCode (Class c)
	{
		int code = -1;
		
		if (null != c)
		{
			Integer i = (Integer) ourClassToPrimitiveCodeMap.get(c);
			if (null != i)
				code = i.intValue();
		}
		
		return code;
	}
	
	
	public static boolean isDefaultValue (Class c, Object value)
	{
		//
		// For references, the default value is null
		//
		if (null == value)
			return true;
		
		boolean isDefault = false;
		
		switch (classToPrimitiveCode(c))
		{
			case PRIMITIVE_BOOLEAN :
			{
				Boolean b = (Boolean) value;
				isDefault = !b.booleanValue();
				break;
			}
				
			case PRIMITIVE_BYTE :
			{
				Byte b = (Byte) value;
				isDefault = (0 == b.byteValue());
				break;
			}
				
			case PRIMITIVE_CHAR :
			{
				Character theChar = (Character) value;
				isDefault = (0 == theChar.charValue());
				break;
			}
				
			case PRIMITIVE_DOUBLE :
			{
				Double d = (Double) value;
				isDefault = (0.0 == d.doubleValue());
				break;
			}
			
			case PRIMITIVE_FLOAT :
			{
				Float f = (Float) value;
				isDefault = (0.0 == f.floatValue());
				break;
			}
				
			case PRIMITIVE_INT :
			{
				Integer i = (Integer) value;
				isDefault = (0 == i.intValue());
				break;
			}
				
			case PRIMITIVE_LONG :
			{
				Long l = (Long) value;
				isDefault = (0 == l.longValue());
				break;
			}
				
			case PRIMITIVE_SHORT :
			{
				Short s = (Short) value;
				isDefault = (0 == s.shortValue());
				break;
			}
			
			//
			// If this is a non-null reference, then it does not have the 
			// default value.
			//
			default :
				isDefault = false;
				break;
		}
		
		return isDefault;
	}
	
	
	public static int classToSimpleType (Class c)
	{
		Integer i = (Integer) ourClassSimpleMap.get(c);
		if (null == i)
			return -1;
		else
			return i.intValue();
	}
	
	
	public static String typeToLongName(int type)
	{
		return ourSimpleLongNameMap.intToString(type);
	}
	
	public static int longNameToType (String s)
	{
		return ourSimpleLongNameMap.stringToInt(s);
	}
	
	public static String typeToShortName (int type)
	{
		return ourSimpleShortNameMap.intToString(type);
	}
	
	public static int shortNameToType (String s)
	{
		return ourSimpleShortNameMap.stringToInt(s);
	}
	
	
	public static Set getSimpleTypes()
	{
		if (null == ourSimpleTypes)
		{
			ourSimpleTypes = new HashSet();
			for (int i = 0; i < SPEC_SIMPLE_TYPES.length; i++)
			{
				ourSimpleTypes.add(SPEC_SIMPLE_TYPES[i]);
			}
		}
		
		return ourSimpleTypes;
	}
	
	
	/**
	 * Is the class object passed to the method "simple?"
	 * 
	 * <P/>
	 * A simple type is a primitive, one of the primitive wrapper classes, 
	 * a String or a Date class.
	 * 
	 * @param c The class to test.
	 * @return true if the class is simple, false otherwise.
	 */
	public static boolean isSimpleType (Class c)
	{
		return getSimpleTypes().contains(c);
	}
	
	
	/**
	 * Add all the fields for this class and its super-classes to the provided
	 * list.
	 * 
	 * <P/>
	 * Note that clients should probably use getAllDeclaredFields (Class) rather
	 * than this method.
	 * 
	 * <P/>
	 * This is an internal method that will add all the fields declared by the 
	 * provided class to the provided list.  It will then proceed to call itself
	 * against the superclass of the provided class and all the interfaces that
	 * this class implements.
	 * 
	 * @param l
	 * @param c
	 */
	public static void addDeclaredFields (List l, Class c)
	{
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			l.add(fields[i]);
		}
		
		Class superClass = c.getSuperclass();
		if (null != superClass)
			addDeclaredFields(l, superClass);
		
		Class[] interfaces = c.getInterfaces();
		if (null != interfaces)
		{
			for (int i = 0; i < interfaces.length; i++)
			{
				addDeclaredFields(l, interfaces[i]);
			}
		}
	}
	
	
	/**
	 * Get all the fields declared by this class and all its super-classes 
	 * or interfaces.
	 * 
	 * <P/>
	 * This is essentially a version of java.lang.Class.getDeclaredFields that 
	 * returns all the superclass fields as well as the fields for the class 
	 * itself.
	 * 
	 * @param c
	 * @return
	 */
	public static Field[] getAllFields (Class c)
	{
		List l = new ArrayList();
		addDeclaredFields(l, c);
		
		Field[] fields = new Field[l.size()];
		fields = (Field[]) l.toArray(fields);
		return fields;
	}
	
	/**
	 * Return the number of dimensions that a provided array contains.
	 * 
	 * @param o The array to be inspected.
	 * @return The number of dimensions or 0, if the object is not an 
	 * array.
	 */
	public static int getDimensions (Object o)
	{
		int dimensions = 0;
		
		for (Class c = o.getClass(); c.isArray(); c = c.getComponentType())
		{
			dimensions++;
		}
		
		return dimensions;
	}
	
	/**
	 * Return the class object that is the basic component type of the 
	 * array.
	 * 
	 * <P/>
	 * The problem is that a call like the following:
	 * 
	 * <PRE>
	 * int[5][4] foo = new int[5][4];
	 * Class c = foo.getClass().getComponentType();
	 * </PRE>
	 * 
	 * <P/>
	 * Will return int[] as the value for c.  What is usually wanted is the 
	 * type "int".  In the above situation, this method will return the class
	 * object for int.
	 * 
	 * @param array The object to be inspected.
	 * @return The class of the array component.  If the argument is not an 
	 * array, then the class of the object will be returned.
	 */
	public static Class getComponentClass (Object array)
	{
		Class c = array.getClass();
		while (c.isArray())
		{
			c = c.getComponentType();
		}
		
		return c;
	}
	
	/**
	 * Returns the field object with the given name from the provided class
	 * or one of its superclasses or interfaces.
	 * 
	 * <P/>
	 * The nice thing about this method is that it will return the field, if 
	 * it exists, regardless of the field's access permissions and whether the
	 * field is in the provided class or one of the superclasses or interfaces.
	 *  
	 * @param c The class to start searching in.
	 * @param name The field to locate
	 * @return The field.
	 */
	public static Field getField (Class c, String name)
		throws NoSuchFieldException
	{
		Field f = null;
		
		while (null != c && null == f)
		{
			try
			{
				f = c.getDeclaredField(name);
			}
			catch (NoSuchFieldException e)
			{
				c = c.getSuperclass();
			}
		}
		
		if (null == f)
		{
			throw new NoSuchFieldException (
				"Could not find field " + name
			);
		}
		
		return f;
	}
	
	
	public static Method[] getAllMethods (Class c)
	{
		List l = new ArrayList();
		
		while (null != c)
		{
			Method[] methods = c.getDeclaredMethods();
			for (int index = 0; index < methods.length; index++)
			{
				l.add(methods[index]);
			}
			
			c = c.getSuperclass();
		}
		
		Method[] methods = new Method[l.size()];
		Iterator i = l.iterator();
		int index = 0;
		while (i.hasNext())
		{
			Method m = (Method) i.next();
			methods[index] = m;
			index++;
		}
		
		return methods;
	}
	
	
	public static Method getMethod (Class c, String name, Class[] formalParams)
	{
		Method m = null;
		
		while (null == m && null != c)
		{
			try
			{
				m = c.getDeclaredMethod(name, formalParams);
			}
			catch (NoSuchMethodException e)
			{}
			
			c = c.getSuperclass();
		}
		
		return m;
	}
	
	
	public static Object createInstance (
		Class c, 
		Class[] formalParams, 
		Object[] actualParams,
		boolean forceAccess
	)
		throws 
			NoSuchMethodException, 
			InvocationTargetException,
			InstantiationException,
			IllegalAccessException
	{
		Constructor cons = c.getConstructor(formalParams);
		
		if (forceAccess)
			cons.setAccessible(true);
		
		return cons.newInstance(actualParams);
	}
	
	
	public static Object createInstance (
		String className,
		Class[] formalParams,
		Object[] actualParams,
		boolean forceAccess
	)
		throws
			NoSuchMethodException, 
			InvocationTargetException,
			InstantiationException,
			IllegalAccessException,
			ClassNotFoundException
	{
		Class c = Class.forName(className);
		return createInstance(c, formalParams, actualParams, forceAccess);
	}
	
	public static Object createInstance (
		Class c,
		Class[] formalParams,
		Object[] actualParams
	)
		throws 
			NoSuchMethodException, 
			InvocationTargetException,
			InstantiationException,
			IllegalAccessException
	{
		return createInstance(c, formalParams, actualParams, false);
	}
	
	
	public static Object createInstance (
		String className,
		Class[] formalParams,
		Object[] actualParams
	)
		throws
			NoSuchMethodException, 
			InvocationTargetException,
			InstantiationException,
			IllegalAccessException,
			ClassNotFoundException
	{
		return createInstance(className, formalParams, actualParams, false);
	}
	
	
	public static boolean definesCustom (
		Class c,
		String methodName, 
		Class[] formalParams
	)
	{
		Method m = null;
		
		while (null == m && null != c && Object.class != c)
		{
			try
			{
				m = c.getDeclaredMethod(methodName, formalParams);
			}
			catch (NoSuchMethodException e)
			{}
			
			c = c.getSuperclass();
		}
		
		return null != m;
	}
	
	
	public static Method findMethod (Class c, String name, Class[] formalParams)
	{
		Method m = null;
		
		while (null == m && null != c && Object.class != c)
		{
			try
			{
				m = c.getDeclaredMethod(name, formalParams);
			}
			catch (NoSuchMethodException e)
			{}
			
			c = c.getSuperclass();
		}
		
		return m;
	}
	
	public static Method findMethod(Class clazz, String name)
	{
		Class[] formalParams = {};
		return findMethod(clazz, name, formalParams);
	}
	
	
	
	
	static public Object performInvoke (
			Object target, 
			String name,
			Class[] formalParams,
			Object[] actualParams
	)
	{
		try
		{
			Class clazz = target.getClass();
			Method method =  findMethod(clazz, name, formalParams);
			return method.invoke(target, actualParams);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	static public boolean isStatic (Method method)
	{
		int mods = method.getModifiers();
		return Modifier.isStatic(mods);
	}


	public static boolean isFinal(Method method)
	{
		int mods = method.getModifiers();
		return Modifier.isFinal(mods);
	}


	public static boolean isPrivate(Method method)
	{
		return Modifier.isPrivate(method.getModifiers());
	}


	public static String toSimpleName(String longName)
	{
		String shortName = longName;
		
		int index = longName.lastIndexOf('.');
		if (-1 != index)
		{
			shortName = shortName.substring(1 + index);
		}
		
		return shortName;
	}

	
	static public int getDimensions(Class clazz)
	{
		int dimensions = 0;
		while (clazz.isArray())
		{
			clazz = clazz.getComponentType();
			dimensions++;
		}
		
		if (dimensions < 1)
			return -1;
		else
			return dimensions;
	}
	
	
	static public Class getBasicArrayType(Class clazz)
	{
		while (clazz.isArray())
		{
			clazz = clazz.getComponentType();
		}
		
		return clazz;
	}


	public static boolean isNative(Method method)
	{
		return Modifier.isNative(method.getModifiers());
	}


	public static Object performInvoke(Method method, Object o) throws InvocationException 
	{
		Object[] args = { o };
		return performInvoke(method, args);
	}
	
	
	public static Object performInvoke(Method method, Object o, Object arg1)
		throws InvocationException
	{
		Object[] args = { o, arg1 };
		return performInvoke(method, args);
	}
	
	public static Object performInvoke(Method method, Object o, Object arg1, Object arg2)
		throws InvocationException
	{
		Object[] args = { o, arg1, arg2 };
		return performInvoke(method, args);
	}

	public static Object performInvoke(Method method, Object o, Object arg1, Object arg2,
			Object arg3) throws InvocationException
	{
		Object[] args = { o, arg1, arg2, arg3 };
		return performInvoke(method, args);
	}

	public static Object performInvoke(Method method, Object[] args) throws InvocationException
	{
		try
		{
			return method.invoke(args);
		}
		catch (Exception e)
		{
			StringBuffer sb = new StringBuffer();
			
			sb.append("Error invoking method ");
			sb.append(method.getName());
			sb.append(" with arguments: ");
			
			boolean first = true;
			for (Object arg : args)
			{
				if (first)
					first = false;
				else
					sb.append(", ");
				
				sb.append(arg);
			}
			
			throw new InvocationException(sb.toString(), e);
		}
	}


	public static Object invoke(Method method, Object[] actualParams)
	{
		Object result = null;
		
		try
		{
			result = method.invoke(actualParams);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	
	public static Object invoke(Method method)
	{
		return invoke(method);
	}
	
	
}
