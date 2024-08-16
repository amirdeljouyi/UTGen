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
package com.lts.compare;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lts.lang.reflect.ReflectionUtils;

public class ObjectComparator
{
	protected boolean mySkipStaticFields = false;
	protected boolean mySkipTransientFields = false;
	// protected boolean myExceptionOnClass = true;
	protected boolean myExceptionOnDifference = false;
	protected boolean myIgnoreEqualsMethods = false;
	protected boolean myLogDifferences = true;
	protected boolean myUseToString = true;
	protected List myLog;
	
	public static Class[] EQUALS_FORMAL_PARAMS = { Object.class };
	
	
	public boolean useToString()
	{
		return myUseToString;
	}
	
	public void setUseToString(boolean b)
	{
		myUseToString = b;
	}
	
	
	public boolean logDifferences()
	{
		return myLogDifferences;
	}
	
	public void setLogDifferences (boolean b)
	{
		myLogDifferences = b;
	}
	
	
	public List getLog()
	{
		if (null == myLog)
			myLog = new ArrayList();
		
		return myLog;
	}
	
	public void logDifference (String msg)
	{
		getLog().add(msg);
	}
	
	
	public boolean ignoreEqualsMethods ()
	{
		return myIgnoreEqualsMethods;
	}
	
	public void setIgnoreEqualsMethods (boolean b)
	{
		myIgnoreEqualsMethods = b;
	}
	
	
	public boolean skipStaticFields()
	{
		return mySkipStaticFields;
	}
	
	public void setSkipStaticFields (boolean b)
	{
		mySkipStaticFields = b;
	}
	
	public boolean skipTransientFields()
	{
		return mySkipTransientFields;
	}
	
	public void setSkipTransientFields (boolean b)
	{
		mySkipTransientFields = b;
	}
	
	/*
	public boolean exceptionOnClass ()
	{
		return myExceptionOnClass;
	}
	
	public void setExceptionOnClass (boolean b)
	{
		myExceptionOnClass = b;
	}
	*/
	
	public boolean exceptionOnDifference ()
	{
		return myExceptionOnDifference;
	}
	
	public void setExceptionOnDifference (boolean b)
	{
		myExceptionOnDifference = b;
	}
	
	public boolean alreadyComparing (Set s, Object o1, Object o2)
	{
		CompareRecord rec = new CompareRecord(o1, o2);
		return s.contains(rec);
	}
	
	
	public boolean arraysAreEquivalent (String path, Set set, Object a1, Object a2)
		throws EquivalenceException
	{
		boolean equivalent = true;
		
		int length = Array.getLength(a1);
		if (length != Array.getLength(a2))
		{
			equivalent = false;
			
			StringBuffer sb = new StringBuffer();
			
			if (null != path)
			{	
				sb.append (path);
				sb.append (" : ");
			}
			
			sb.append ("array sizes differ (");
			sb.append (length);
			sb.append (" != ");
			sb.append (Array.getLength(a2));
			sb.append (")");
			
			if (exceptionOnDifference())
				throw new EquivalenceException(sb.toString());
			else if (logDifferences())
				logDifference(sb.toString());
		}
		else
		{
			int i = 0;
			while (equivalent && i < length)
			{
				Object o1 = Array.get(a1, i);
				Object o2 = Array.get(a2, i);
				ArrayCompareRecord rec = new ArrayCompareRecord (i, o1, o2);
				String s = path;
				
				if (null == s)
					s = "";
				
				s = s + "[" + i + "]";
				equivalent = compareObjects(s, set, o1, o2, rec);
				i++;
			}
		}

		return equivalent;
	}
	
	
	public boolean hasEqualsMethod (Class c)
	{
		return ReflectionUtils.definesCustom(c, "equals", EQUALS_FORMAL_PARAMS);
	}
	
	/*
	public boolean compareObjects (
		Set set,
		Object o1, 
		Object o2,
		CompareRecord rec
	)
		throws EquivalenceException
	{
		if (set.contains(rec))
			return true;
		else
			set.add(rec);
		
		boolean equivalent = false;
		
		try
		{
			//
			// references to the same object or both point to null
			// --- equivalent
			//
			if (o1 == o2)
				equivalent = true;
			
			//
			// One is null, and the other is not --- different
			//
			else if (null == o1 || null == o2)
				equivalent = false;
			
			//
			// Both non-null, but they point to objects of different classes
			// --- different
			//
			else if (o1.getClass() != o2.getClass())
			{
				if (!exceptionOnClass())
					equivalent = false;
				else
				{
					throw new EquivalenceException(EquivalenceException.REASON_DIFFERENT_CLASSES);
				}
			}
			
			//
			// Primitive objects --- compare their values
			//
			else if (o1.getClass().isPrimitive() || ReflectionUtils.isSimpleType(o1.getClass()))
				equivalent = o1.equals(o2);
			
			//
			// Arrays --- perform an array comparison
			//
			else if (o1.getClass().isArray())
				equivalent = arraysAreEquivalent(set, o1, o2);
			
			//
			// The class has its own equals method --- use that
			//
			else if (!ignoreEqualsMethods() && hasEqualsMethod(o1.getClass()))
				equivalent = o1.equals(o2);
			
			//
			// default --- do a field-by-field comparision
			//
			else
				equivalent = allFieldsAreEquivalent(set, o1, o2);
		}
		catch (EquivalenceException e)
		{
			if (!e.getRecords().contains(rec))
				e.pushRecord(rec);
			
			throw e;
		}
		
		
		if (!equivalent && exceptionOnDifference())
		{	
			throw new EquivalenceException(
				EquivalenceException.REASON_NOT_EQUIVALENT,
				rec
			);
		}
		
		return equivalent;
	}
	*/
	
	
	public boolean listsAreEquivalent (
		String path,
		Set set,
		List l1,
		List l2
	)
		throws EquivalenceException
	{
		boolean equivalent = true;

		if (l1.size() != l2.size())
		{
			equivalent = false;
			StringBuffer sb = new StringBuffer();
			
			if (null != path)
			{	
				sb.append (path);
				sb.append (" : ");
			}
			
			sb.append("different list sizes (");
			sb.append (l1.size());
			sb.append (" != ");
			sb.append (l2.size());
			
			if (exceptionOnDifference())
				throw new EquivalenceException(sb.toString());
			else if (logDifferences())
				logDifference(sb.toString());
		}
		else 
		{
			String newPath = path;
			if (null == newPath)
				newPath = "";
			
			newPath = newPath + "(";
			
			for (int i = 0; i < l1.size(); i++)
			{
				Object o1 = l1.get(i);
				Object o2 = l2.get(i);
				
				String s = newPath + i + ")";
				CompareRecord rec = new CompareRecord(o1, o2);
				equivalent = equivalent && compareObjects(s, set, o1, o2, rec);
			}
		}
		
		return equivalent;
	}
	
	
	public boolean compareObjects (
		String path,
		Set set,
		Object o1, 
		Object o2,
		CompareRecord rec
	)
		throws EquivalenceException
	{
		if (set.contains(rec))
			return true;
		else
			set.add(rec);
		
		boolean equivalent = false;
		String message = null;
		
		
		//
		// references to the same object or both point to null
		// --- equivalent
		//
		if (o1 == o2)
			equivalent = true;
		
		//
		// One is null, and the other is not --- different
		//
		else if (null == o1)
		{	
			equivalent = false;
			message = "obj1 is null";
		}
		
		else if (null == o2)
		{
			equivalent = false;
			message = "obj2 is null";
		}
		
		//
		// Both non-null, but they point to objects of different classes
		// --- different
		//
		else if (o1.getClass() != o2.getClass())
		{
			equivalent = false;
			message = o1.getClass().toString() + " != " + o2.getClass().toString();
		}
		
		//
		// Primitive objects --- compare their values
		//
		else if (o1.getClass().isPrimitive() || ReflectionUtils.isSimpleType(o1.getClass()))
		{	
			equivalent = o1.equals(o2);
			if (!equivalent)
				message = o1.toString() + " != " + o2.toString();
		}
		
		//
		// Arrays --- perform an array comparison
		//
		else if (o1.getClass().isArray())
			equivalent = arraysAreEquivalent(path, set, o1, o2);

		//
		// List --- don't use the default because it depends on the component
		// objects to define an equals method.  If the components do not, then 
		// the default equals method uses identity comparrison.
		//
		else if (List.class.isAssignableFrom(o1.getClass()))
			equivalent = listsAreEquivalent(path, set, (List) o1, (List) o2);
		
		//
		// The class has its own equals method --- use that
		//
		else if (!ignoreEqualsMethods() && hasEqualsMethod(o1.getClass()))
		{
			equivalent = o1.equals(o2);
			if (!equivalent)
			{
				if (useToString())
					message = o1.toString() + " != " + o2.toString();
				else
					message = "values are not equal";
			}
		}
		
		//
		// default --- do a field-by-field comparision
		//
		else
			equivalent = allFieldsAreEquivalent(path, set, o1, o2);
		
		
		if (null != message)
		{	
			String s = path;
			if (null == s)
				s = "";
			
			StringBuffer sb = new StringBuffer(s);
			
			if (null != path)
				sb.append (" : ");
			
			sb.append (message);
			
			message = sb.toString();
			
			if (exceptionOnDifference())
				throw new EquivalenceException(sb.toString());
			else if (logDifferences())
				logDifference(message);
		}
		
		return equivalent;
	}
	
	
	/**
	 * Compare two objects on a field by field level.
	 * 
	 * <H3>Note</H3>
	 * The objects to compare against each other must be non-null and must
	 * be of the same class.  Passing null objects or objects that are not 
	 * of the same class will result in exceptions being thrown.
	 * 
	 * <H3>Description</H3>
	 * This method examines two objects of the same class and compares the 
	 * value of each field in the first object against the corresponding field
	 * value in the second object.  
	 * 
	 * @param set
	 * @param o1
	 * @param o2
	 * @return
	 */
	public boolean allFieldsAreEquivalent (
		String path,
		Set set,
		Object o1,
		Object o2
	)
		throws EquivalenceException
	{
		String newPath = path;
		
		if (null == newPath)
			newPath = o1.getClass().getName();
		
		newPath = newPath + ".";
		
		Field[] fields = ReflectionUtils.getAllFields(o1.getClass());
		int i = 0;
		boolean equivalent = true;
		while (i < fields.length && (equivalent || logDifferences()))
		{	
			Field f = fields[i];
			f.setAccessible(true);
			
			if (skipStaticFields() && Modifier.isStatic(f.getModifiers()))
				continue;
			
			if (skipTransientFields() && Modifier.isTransient(f.getModifiers()))
				continue;

			FieldCompareRecord rec = new FieldCompareRecord (f, o1, o2);
			
			try
			{
				String s = newPath + f.getName();
				
				Object value1 = f.get(o1);
				Object value2 = f.get(o2);
				rec = new FieldCompareRecord(f, value1, value2);
				equivalent = compareObjects(s, set, value1, value2, rec) && equivalent;
			}
			catch (RuntimeException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new EquivalenceException (
					EquivalenceException.REASON_EXCEPTION,
					rec,
					e
				);				
			}
			
			i++;
		}
		
		return equivalent;
	}
	
	
	/*
	public boolean allFieldsAreEquivalent (
			Set set,
			Object o1,
			Object o2
	)
	throws EquivalenceException
	{
		Field[] fields = ReflectionUtils.getAllFields(o1.getClass());
		int i = 0;
		boolean equivalent = true;
		while (equivalent && i < fields.length)
		{	
			Field f = fields[i];
			f.setAccessible(true);
			
			if (skipStaticFields() && Modifier.isStatic(f.getModifiers()))
				continue;
			
			if (skipTransientFields() && Modifier.isTransient(f.getModifiers()))
				continue;

			FieldCompareRecord rec = new FieldCompareRecord (f, o1, o2);
			
			try
			{
				Object value1 = f.get(o1);
				Object value2 = f.get(o2);
				rec = new FieldCompareRecord(f, value1, value2);
				equivalent = compareObjects(set, value1, value2, rec);
			}
			catch (RuntimeException e)
			{
				throw e;
			}
			catch (EquivalenceException e)
			{
				if (!e.getRecords().contains(rec))
					e.pushRecord(rec);
				
				throw e;
			}
			catch (Exception e)
			{
				throw new EquivalenceException (
						EquivalenceException.REASON_EXCEPTION,
						rec,
						e
				);				
			}
			
			i++;
		}
		
		return equivalent;
	}
	*/
	
	
	public boolean equivalent (Object o1, Object o2)
		throws EquivalenceException
	{
		HashSet set = new HashSet();
		CompareRecord rec = new CompareRecord(o1, o2);
		return compareObjects(null, set, o1, o2, rec);
	}
}
