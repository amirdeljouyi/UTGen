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

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringTokenizer;

import com.lts.LTSException;
import com.lts.util.MapUtil;

/**
 * A repository for utility methods that pertain to java beans.
 */
public class BeanUtils
{
	/**
	 * Return the input string with the first character converted to lower
	 * case.
	 * 
	 * @param name The string to convert.  Passing null or the empty string
	 * will result in an exception.
	 * 
	 * @return The input string with the first character converted to lower
	 * case.
	 */
	public static String toLowerName (String name)
	{
		char[] ca = name.toCharArray();
		ca[0] = Character.toLowerCase(ca[0]);
		
		return String.valueOf(ca);
	}
	
	/**
	 * Return the input string with the first character converted to upper
	 * case.
	 * 
	 * @param name The string to convert.  Passing null or the empty string
	 * will result in an exception.
	 * 
	 * @return The input string with the first character converted to upper
	 * case.
	 */
	public static String toCapName (String name)
	{
		char[] ca = name.toCharArray();
		ca[0] = Character.toUpperCase(ca[0]);
		
		return String.valueOf(ca);
	}
	
	/**
	 * Return a method name of the form get&lt;name&gt;.
	 * 
	 * <P/>
	 * The first character of the input string is converted to upper case 
	 * before creating the getter name.  For example, "foo" would become
	 * "getFoo".
	 * 
	 * @param name The property name.
	 * @return The getter name as described above.
	 */
	public static String toGetterName (String name)
	{
		String uname = toCapName(name);
		return "get" + uname;
	}
	
	
	public static String toSetterName (String name)
	{
		String uname = toCapName (name);
		return "set" + uname;
	}
	
	/**
	 * Convert a string that contains words separated by underscore characters
	 * into a "camel case" version of the same.
	 * 
	 * <P/>
	 * "Camel case" uses capitalization to denote the start of a word instead 
	 * of underscores.  Thus the string "hello_my_world" becomes "helloMyWold".
	 * 
	 * @param underscored The string to convert.
	 * @return A camel case version of the input string.
	 */
	public static String uscoreToPropertyName(String underscored)
	{
		char[] ca = underscored.toCharArray();
		StringBuffer sb = new StringBuffer(ca.length);
		
		boolean capNext = false;
		for (int i = 0; i < ca.length; i++)
		{
			char c = ca[i];
			if ('_' == c)
				capNext = true;
			else 
			{
				if (capNext)
				{
					c = Character.toUpperCase(c);
					capNext = false;
				}
				else
				{
					c = Character.toLowerCase(c);
				}
				
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Convert a string that contains words separated by underscores into a 
	 * "Camel case" version of the same with the first character capitalized.
	 * 
	 * <P/>
	 * Generally speaking, a class name starts with a capitalized character.
	 * Otherwise, this method is very similar to {@link #uscoreToPropertyName(String)}.
	 * 
	 * <P/>
	 * For example, "trunk_group_table" becomes "TrunkGroupTable".
	 * 
	 * @param uscore The string to convert.
	 * @return See above.
	 */
	public static String uscoreToClassName (String uscore)
	{
		String pname = uscoreToPropertyName(uscore);
		pname = toCapName(pname);
		return pname;
	}
	
	
	/**
	 * Convert the dots (".") in the input string into file separators.
	 * 
	 * <P/>
	 * For example, "java.swig.table" might be converted to "java/swing/table".
	 * "java.swing.table.DefaultTableModel" might be converted into 
	 * "java/swing/table/DefaultTableModel".  The path separator used is the 
	 * value from File.pathSeparator.
	 * 
	 * @param str The string to convert.
	 * @return See above.
	 */
	public static String dotsToPathSeparatots (String str)
	{
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(str, ".");
		boolean first = true;
		while (st.hasMoreTokens())
		{
			if (first)
				first = false;
			else
				sb.append (File.pathSeparator);
			
			String s = st.nextToken();
			sb.append (s);
		}
		
		String fname = sb.toString();
		return fname;
	}
	
	/**
	 * Convert a fully qualified class name into a name that is used internally
	 * in class files and the like.
	 * 
	 * <P/>
	 * Class files always use the same character to separate package names and
	 * the like.  Unfortunately, this character is not the period (".") 
	 * character, but is, instead, the forward slash character ("/").
	 * 
	 * <P/>
	 * As an example, the string "java.swing.table.DefaultTableModel" would be 
	 * converted to "java/swing/table/DefaultTableModel".
	 * 
	 * @param cname A fully qualified class name.
	 * @return The input string converted as described above.
	 */
	public static String classNameToSlashName (String cname)
	{
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(".", cname);
		boolean first = true;
		while (st.hasMoreTokens())
		{
			if (first)
				first = false;
			else
				sb.append("/");
			
			sb.append(st.nextToken());
		}
		
		return sb.toString();
	}
	
	
	public static File classNameToFile (File parentDir, String cname, String suffix)
	{
		String replacement = "\\" + File.separator;
		String fname = cname.replaceAll("\\.", replacement);
		
		if (null != suffix)
			fname = fname + suffix;
		
		File f;
		
		if (null == parentDir)
			f = new File(fname);
		else
			f = new File(parentDir, fname);
		
		return f;
	}
	
	
	public static File classNameToClassFile (File parentDir, String cname)
	{
		return classNameToFile(parentDir, cname, ".class");
	}

	public static File classNameToJavaFile (File parentDir, String cname)
	{
		return classNameToFile(parentDir, cname, ".java");
	}
	
	
	/**
	 * Given a JavaBean and a map from property names to values, set the 
	 * properties of a bean.
	 * 
	 * @param bean The bean to be populated.
	 * @param data The data to populate the bean with.
	 * @exception LTSException This exception is thrown by this method if 
	 * there is a problem getting the property descriptors for the bean or 
	 * if there is a problem using the setter method for one of the bean's
	 * properties.
	 */
	public static void populateBean (Object bean, Map data)
		throws LTSException
	{	
		String className = null;
		if (null != bean)
			className = bean.getClass().getName();
		
		String propertyName = null;
		Object value = null;
		
		try
		{
			BeanInfo info = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			
			for (int i = 0; i < props.length; i++)
			{
				propertyName = props[i].getName();
				value = data.get(props[i].getName());
				Object[] args = { value };
				PropertyDescriptor pdesc = props[i];
				pdesc.getWriteMethod().invoke(bean, args);
			}
		}
		catch (Exception e)
		{
			String msg;
			
			if (null == propertyName)
			{
				msg = 
					"Error trying to get the properies for bean of class "
					+ className;
			}
			else
			{
				msg =
					"Error trying to write bean property "
					+ propertyName + ", of class " + className
					+ ", to value " + value;
			}

			throw new LTSException (msg, e);
		}
	}

	
	public static void populateBean (Object bean, Object[] data)
		throws LTSException
	{
		Map m = MapUtil.buildMap(data);
		populateBean(bean, m);
	}
	
	
	protected static Object[] NO_ARGUMENTS = { };
	
	public static Object getValue (Object bean, Method getter) throws LTSException
	{
		try
		{
			return getter.invoke(bean, NO_ARGUMENTS);
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
	
	
	public static void setValue (Object bean, Object value, Method setter) throws LTSException
	{
		try
		{
			Object[] data = new Object[] { value };
			setter.invoke(bean, data);
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
	
	
	public static void setValue (Object bean, Object value, String property)
	{
		
	}
	
	/**
	 * Get the package name from a fully-qualified class name.
	 * 
	 * <P>
	 * For example, the package name from the string "java.lang.String" is 
	 * "java.lang".
	 * 
	 * @param fullname A fully qualified class name.
	 * @return The package name or null if the name has no package (i.e., the default
	 * package).
	 */
	public static String getPackageName(String fullname)
	{
		int index = fullname.lastIndexOf('.');
		if (-1 == index)
			return null;
		
		String pkgname = fullname.substring(0,index);
		return pkgname;
	}

	static public Object toPrimitiveValue(Class clazz, String s)
	{
		Object value = null;
		
		if (Boolean.TYPE == clazz)
			value = new Boolean(s);
		else if (Character.TYPE == clazz)
		{
			char c = s.charAt(0);
			value = new Character(c);
		}
		else if (Byte.TYPE == clazz)
			value = new Byte(s);
		else if (Short.TYPE == clazz)
			value = new Short(s);
		else if (Integer.TYPE == clazz)
			value = new Integer(s);
		else if (Long.TYPE == clazz)
			value = new Long(s);
		else if (Float.TYPE == clazz)
			value = new Float(s);
		else if (Double.TYPE == clazz)
			value = new Double(s);
		else if (String.class == clazz)
			value = s;
		else
		{
			throw new IllegalArgumentException(clazz.toString());
		}
		
		return value;
	}
	
	/**
	 * Set the property of a bean, given the PropertyDescription, and a string 
	 * representation of the value of the property.
	 * 
	 * <P>
	 * The property must be one of the primitive types (byte, short, int, long float
	 * or double).  If a property of another type is passed to the method, it will
	 * throw an {@link IllegalArgumentException}.
	 * </P>
	 * 
	 * @param bean The bean to set.
	 * @param strValue The string value of the property.
	 * @param prop The property to set.
	 */
	static public void setPrimitiveProperty (Object bean, String strValue, PropertyDescriptor prop)
	{
		Object value;
		
		if (null == strValue)
			value = null;
		else 
			value = toPrimitiveValue(prop.getPropertyType(), strValue);
		
		try
		{
			prop.getWriteMethod().invoke(bean, value);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String toStringValue(Object bean, PropertyDescriptor prop)
	{
		try
		{
			Object o = prop.getReadMethod().invoke(bean);
			if (null == o)
				return null;
			else
				return o.toString();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public static Object createBean (BeanInfo info) throws LTSException
	{
		try
		{
			BeanDescriptor beanDesc = info.getBeanDescriptor();
			Class clazz = beanDesc.getBeanClass();
			Constructor cons = getDefaultConstructor(clazz);
			return cons.newInstance();
		}
		catch (Exception e)
		{
			throw new LTSException(e);
		}
	}

	public static Constructor getDefaultConstructor(Class clazz)
	{
		Constructor[] constructors = clazz.getConstructors();
		for (Constructor cons : constructors)
		{
			Class[] formalParams = cons.getParameterTypes();
			if (null == formalParams || formalParams.length < 1)
				return cons;
		}
		
		return null;
	}
	
	
	public static Object createInstance(Constructor cons, Object[] params) throws LTSException
	{
		try
		{
			return cons.newInstance(params);
		}
		catch (Exception e)
		{
			throw new LTSException(e);
		}
	}

	public static Class[] SimpleClasses = {
		Boolean.class,
		Byte.class,
		Character.class,
		Double.class,
		Float.class,
		Integer.class,
		Long.class,
		Short.class,
		String.class,
		Boolean.TYPE,
		Byte.TYPE,
		Character.TYPE,
		Double.TYPE,
		Float.TYPE,
		Integer.TYPE,
		Long.TYPE,
		Short.TYPE,
	};
	
	public static boolean isSimpleType(Class clazz)
	{
		return 
			clazz.isPrimitive()
			|| clazz == Byte.class
			|| clazz == Character.class
			|| clazz == Double.class
			|| clazz == Short.class
			|| clazz == Integer.class
			|| clazz == Long.class
			|| clazz == Float.class
			|| clazz == String.class;
	}

	public static Object toSimpleValue(Class clazz, String s)
	{
		Object value = null;
		
		if (clazz == Byte.class || clazz == Byte.TYPE)
			value = new Byte(s);
		else if (clazz == Short.class || clazz == Short.TYPE)
			value = new Short(s);
		else if (clazz == Integer.class || clazz == Integer.TYPE)
			value = new Integer(s);
		else if (clazz == Long.class || clazz == Long.TYPE)
			value = new Long(s);
		else if (clazz == Float.class || clazz == Float.TYPE)
			value = new Float(s);
		else if (clazz == Double.class || clazz == Double.TYPE)
			value = new Double(s);
		else if (clazz == Character.class || clazz == Character.TYPE)
			value = new Character(s.charAt(0));
		else 
			value = s;
		
		return value;
	}

	public static String buildAbsolutePropertyName(Class beanClass, String name)
	{
		return 
			beanClass.getName() + "." + name;
	}

	public static String toAbsoluteName(Class beanClass, PropertyDescriptor property)
	{
		return 
			beanClass.getName() + "." + property.getName();
	}

	public static String getSimpleName(Class clazz)
	{
		String s = clazz.getName();
		return getSimpleName(s);
	}

	public static String getSimpleName(String name)
	{
		int index = name.lastIndexOf('.');
		if (-1 != index)
			name = name.substring(1 + index);
		
		return name;
	}
	
	
	public static String accessorToPropertyName(Method method)
	{
		String name = method.getName();

		//
		// getFoo or setFoo
		//
		if (name.length() > 3 && (name.startsWith("get") || name.startsWith("set")))
		{
			name = name.substring(3);
			char[] nameChars = name.toCharArray();
			nameChars[0] = Character.toLowerCase(nameChars[0]);
			name = new String(nameChars);
		}
		//
		// isJunk, isLame style
		//
		else if (name.length() > 2 && name.startsWith("is"))
		{
			name = name.substring(2);
			char[] nameChars = name.toCharArray();
			nameChars[0] = Character.toLowerCase(nameChars[0]);
			name = new String(nameChars);
		}
		
		return name;
	}

	public static PropertyDescriptor getPropertyDescriptor(BeanInfo bean, String name)
	{
		for (PropertyDescriptor desc : bean.getPropertyDescriptors())
		{
			if (desc.getName().equals(name))
			{
				return desc;
			}
		}
		
		return null;
	}

	public static Object createInstance(Class clazz) throws LTSException
	{
		try
		{
			return clazz.newInstance();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = 
				"Caught exception while trying to instantiate " 
				+ clazz.getName();
			
			throw new LTSException(msg);
		}
	}

	public static String buildAbsolutePropertyName(Class clazz, Method method)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(clazz.getName());
		sb.append(".");
		sb.append(accessorToPropertyName(method));
		
		return sb.toString();
	}

}
