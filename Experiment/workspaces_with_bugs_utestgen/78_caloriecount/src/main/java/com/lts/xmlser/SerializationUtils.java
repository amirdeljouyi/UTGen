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
package com.lts.xmlser;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

import com.lts.LTSException;
import com.lts.lang.reflect.ReflectionUtils;

/**
 * A class that creates instances of classes based on the Serialization 
 * requirements.
 * 
 * <P/>
 * When deserializing an object, the default constructor for the class in
 * question is not supposed to be called.  Instead, the default constructor
 * for the the first non-serializable superclass is called.  In essance, we 
 * are just trying to allocate an instance of the class, not initialize it.
 * The rest of the serialization code handles the initialization.
 * 
 * <P/>
 * Unfortunately, there is no easy was of allocating an object without calling
 * a constructor.  The approach that Java takes is to cheat and call a method
 * that is part of sun.reflect.ReflectionFactory.  That class is not part of 
 * the standard Java class library, or at least the source code is not part 
 * of the standard library.  Therefore, this approach will only work if 
 * sun.reflect.ReflectionFactory can be found.  Since the standard Java 
 * serialization uses this class, this is a fairly safe bet.
 * 
 * <P/>
 * The methods used here were taken from the source code for 
 * java.io.ObjectStreamClass and are thus the property of Sun Microsystems. 
 * I would have simply called the methods on ObjectStreamClass but the 
 * methods are declared private in that class.
 */
public class SerializationUtils
{
	protected static ReflectionFactory reflectionFactory = 
		ReflectionFactory.getReflectionFactory();
	
	protected static Unsafe ourUnsafe;
	

	protected Map myClassToFieldMap;
	
	public Map getClassToFieldMap ()
	{
		if (null == myClassToFieldMap)
			myClassToFieldMap = new HashMap();
		
		return myClassToFieldMap;
	}
	
	
	public void mapClassToFields (Class c, Field[] fields)
	{
		getClassToFieldMap().put(c, fields);
	}
	
	
	public Field[] discoverSerializeableFields (Class c)
	{
		Field[] temp = ReflectionUtils.getAllFields(c);
		
		List l = new ArrayList();
		int incount = 0;
		
		while (incount < temp.length)
		{
			Field f = temp[incount];
			if (
				!Modifier.isTransient(f.getModifiers())
				&& !Modifier.isStatic(f.getModifiers())
			)
			{
				l.add(f);
			}
			
			incount++;
		}
		
		Field[] fields = new Field[l.size()];
		for (int i = 0; i < fields.length; i++)
		{
			fields[i] = (Field) l.get(i);
		}
		
		return fields;
	}
	
	
	public Field[] getFieldsForClass (Class c)
	{
		Field[] fields = (Field[]) getClassToFieldMap().get(c);
		if (null == fields)
		{
			ReflectionUtils.getAllFields(c);
			fields = discoverSerializeableFields(c);
			mapClassToFields(c, fields);
		}
		
		return fields;
	}
	
	
	/**
	 * Returns package name of given class.
	 */
	private String getPackageName(Class cl) 
	{
		String s = cl.getName();
		int i = s.lastIndexOf('[');
		if (i >= 0) 
		{
			s = s.substring(i + 2);
		}
		i = s.lastIndexOf('.');
		return (i >= 0) ? s.substring(0, i) : "";
	}

	/**
	 * Returns true if classes are defined in the same runtime package, false
	 * otherwise.
	 */
	public boolean packageEquals(Class cl1, Class cl2) 
	{
		return 
			cl1.getClassLoader() == cl2.getClassLoader() 
			&& getPackageName(cl1).equals(getPackageName(cl2));
	}

	
	public Constructor getSerializableConstructor(Class cl) 
	{
		Class initCl = cl;
		while (!Serializable.class.isAssignableFrom(initCl)) 
		{
			if ((initCl = initCl.getSuperclass()) == null) 
			{
				return null;
			}
		}
		
		try 
		{
			Constructor cons = initCl.getDeclaredConstructor(new Class[0]);
			if (!packageEquals(cl, initCl))
			{
				return null;
			}
			cons = reflectionFactory.newConstructorForSerialization(cl, cons);
			cons.setAccessible(true);
			return cons;
		} 
		catch (NoSuchMethodException ex) 
		{
			return null;
		}
	}
	
	public Object create(Class type)
		throws LTSException
	{
		try 
		{
			Constructor cons = getSerializableConstructor(type);
			if (null == cons)
			{
				throw new LTSException (
					"Cannot instantiate " + type
				);
			}
			
			Object[] actualParams = new Object[0];
			return cons.newInstance(actualParams);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to get serialization constructor for " + type,
				e
			);
		}
	}

	
	public boolean fieldBelongsTo (Field f, Class c)
	{
		boolean foundField = false;
		
		Field[] theFields = getFieldsForClass(c);
		int i = 0;
		while (!foundField && i < theFields.length)
		{
			foundField = f.equals(theFields[i]);
			i++;
		}
		
		return foundField;
	}
	
	public static Unsafe getUnsafe()
		throws LTSException
	{  
		Unsafe unsafe = null;   
		try 
		{   
			Class uc = Unsafe.class;   
			Field[] fields = uc.getDeclaredFields();   
			for (int i = 0; i < fields.length; i++) 
			{    
				if (fields[i].getName().equals("theUnsafe")) 
				{     
					fields[i].setAccessible(true);     
					unsafe = (Unsafe) fields[i].get(uc);     
					break;    
				}   
			}  
		}  
		catch (Exception e) 
		{
			throw new LTSException ("Error trying to get unsafe operator", e);
		}   
		
		return unsafe;
	}
	
	
	public void setField(Object dest, FieldValue fieldValue)
		throws LTSException
	{
		setField(dest, fieldValue, false);
	}
	
	public void setField (Object dest, FieldValue fv, boolean forgiving)
		throws LTSException
	{
		//
		// Ensure that the field is actually part of the destination
		// object.
		//
		if (!fieldBelongsTo(fv.theField, dest.getClass()))
		{
			if (forgiving)
				return;
			
			throw new LTSException (
				"The field, " + fv.theField + " is not part of "
				+ "the class " + dest.getClass().getName()
			);
		}
		
		//
		// Ensure that the field is serializable
		//
		if (Modifier.isTransient(fv.theField.getModifiers()))
		{
			throw new LTSException (
				"Attempt to set state of transient field, "
				+ fv.theField
			);
		}
		
		if (Modifier.isStatic(fv.theField.getModifiers()))
		{
			throw new LTSException (
				"Attempt to set state of static field, " 
				+ fv.theField
			);
		}
		
		Object data = fv.theValue;
		Unsafe u = getUnsafe();
		long offset = u.objectFieldOffset(fv.theField);
		
		switch (ReflectionUtils.classToPrimitiveCode(fv.theField.getType()))
		{
			case ReflectionUtils.PRIMITIVE_BOOLEAN :
				u.putBoolean(dest, offset, ((Boolean) data).booleanValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_BYTE :
				u.putByte(dest, offset, ((Byte) data).byteValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_CHAR :
				u.putChar(dest, offset, ((Character) data).charValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_DOUBLE :
				u.putDouble(dest, offset, ((Double) data).doubleValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_FLOAT :
				u.putFloat(dest, offset, ((Float) data).floatValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_INT :
				u.putInt(dest, offset, ((Integer) data).intValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_LONG :
				u.putLong(dest, offset, ((Long) data).longValue());
				break;
				
			case ReflectionUtils.PRIMITIVE_SHORT :
				u.putShort(dest, offset, ((Short) data).shortValue());
				break;
				
			default :
				u.putObject(dest, offset, data);
				break;
		}
	}
	
	public void setState (Object dest, List state, boolean forgiving) throws LTSException
	{
		Iterator i = state.iterator();
		while (i.hasNext())
		{
			FieldValue fv = (FieldValue) i.next();
			setField(dest, fv, forgiving);
		}
	}
	
	public void setState (Object dest, List state) throws LTSException
	{
		setState(dest, state, false);
	}
	
	/**
	 * An internal method to set the state of an object.
	 * 
	 * <P/>
	 * This method is a complete security hole in the 
	 * Java VM.  It allows the caller to set the value of any non-static,
	 * non-transient field in an instance of an object.
	 * 
	 * @param dest The object whose state is to be set.
	 * @param state The fields and values that should be used to initialize
	 * the destination's state to.
	 */
	public void setState (Object dest, FieldValue[] state, boolean forgiving)
		throws LTSException
	{
		for (int i = 0; i < state.length; i++)
		{
			setField (dest, state[i], forgiving);
		}
	}
	
	
	public void setState (Object dest, FieldValue[] state)
		throws LTSException
	{
		setState(dest, state, false);
	}
	
	/**
	 * Create an instance of a class and initialize its state.
	 * 
	 * <P/>
	 * This method creates an instance of an object in accordance with the Java
	 * serialization specification and then initializes it using data passed
	 * to the method.  When creating an instance of a serialized object, the 
	 * first public, no-argument constructor in the class hierarchy of the 
	 * class passed to the method is invoked.  This means that subclass 
	 * constructors are not called.
	 * 
	 * <P/>
	 * Next, the fields and data passed to the method are used to initialize
	 * the newly created instance.  Each non-static, non-transient field that
	 * applies to the newly creating instance is set to its corresponding 
	 * value.  This will set values for final instance fields, which you cannot
	 * normally do via the Java reflection API.
	 *  
	 * @param type The object to create.
	 * @param state The fields to initialize and the values to initialize them 
	 * to.
	 * 
	 * @return A new instance of the provided class, initialized using the 
	 * provided state.
	 * 
	 */
	public Object create (Class type, FieldValue[] state, boolean forgiving)
		throws LTSException
	{
		Object o = create(type);
		setState(o, state, forgiving);
		return o;
	}
	
	
	public Object create (Class type, FieldValue[] state) throws LTSException
	{
		return create (type, state, false);
	}
	
	
	public Object create (Class type, List state, boolean forgiving)
		throws LTSException
	{
		Object o = create(type);
		setState(o, state, forgiving);
		return o;
	}
	
	
	public FieldValue[] getState (Object o)
		throws LTSException
	{
		Field[] theFields = getFieldsForClass(o.getClass());
		FieldValue[] state = new FieldValue[theFields.length];
		
		for (int i = 0; i < theFields.length; i++)
		{
			Field f = theFields[i];
			f.setAccessible(true);
			
			FieldValue fv = new FieldValue();
			state[i] = fv;
			fv.theField = f;
			try
			{
				fv.theValue = f.get(o);
			}
			catch (RuntimeException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new LTSException (
					"Error trying to get the value for field " + f
					+ " from " + o,
					e
				);
			}
		}
		
		return state;
	}
}
