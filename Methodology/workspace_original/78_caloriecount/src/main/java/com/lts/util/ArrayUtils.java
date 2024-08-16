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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils
{
	public static String[] appendString (String[] data, String s)
	{
		if (null == data)
			return null;
		
		int size = 1 + data.length;		
		String[] newArray = new String[size];
		
		if (null != data)
		{
			for (int i = 0; i < data.length; i++)
			{
				newArray[i] = data[i];
			}
		}
		
		newArray[newArray.length - 1] = s;
		return newArray;
	}
	

	public static Object[] append (Object[] oldArray, Object o)
	{
		if (null == oldArray)
			return null;
		
		int size = 0;
		Object[] newArray;
				
		Class compType = oldArray.getClass().getComponentType();
		size = oldArray.length + 1;
		
		newArray = (Object[]) Array.newInstance(compType, size);
		
		for (int i = 0; i < oldArray.length; i++)
		{
			newArray[i] = oldArray[i];
		}
		
		newArray[size - 1] = o;
		return newArray;
	}
	
	
	public static List toList(Object o)
	{
		List list = new ArrayList();
		Class aclass = o.getClass();
		if (!aclass.isArray())
			throw new IllegalArgumentException(aclass + " is not an array");
		
		int length = Array.getLength(o);
		for (int i = 0; i < length; i++)
		{
			Object element = Array.get(o, i);
			if (null != o)
				list.add(element);
		}
		
		return list;
	}
	
	
	public static void print (PrintWriter out, char[] ca)
	{
		out.print("[ ");
		
		for (int i = 0; i < ca.length; i++)
		{
			if (i > 0)
				out.print(", ");
			
			out.print(ca[i]);
		}
		
		out.print("]");
	}
	
	
	public static void print (StringBuffer sb, char[] ca)
	{
		sb.append ('[');
		
		for (int i = 0; i < ca.length; i++)
		{
			if (i > 0)
				sb.append (", ");
			
			sb.append(ca[i]);
		}
		
		sb.append (']');
	}
	
	
	public static void print (PrintWriter out, Object[] oa)
	{
		for (int i = 0; i < oa.length; i++)
		{
			out.println("[" + i +"] = " + oa[i]);
		}
	}
	
	public static void print (Object[] oa)
	{
		PrintWriter out = new PrintWriter(System.out);
		print(out, oa);
	}
	
	public static void clear (byte[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = 0;
		}
	}
	
	/**
	 * Create a new array that consists of { s0, s1, theArray[0], theArray[1], ... }
	 * 
	 * @param s0 New first element of the array
	 * @param s1 New second element of the array
	 * @param theArray The array to append after the new elements
	 * @return the resulting array
	 */
	public static String[] toStringArray (String s0, String s1, String[] theArray)
	{
		if (null == theArray)
			theArray = new String[0];
		
		String[] outArray = new String[2 + theArray.length];
		outArray[0] = s0;
		outArray[1] = s1;
		
		for (int i = 0; i < theArray.length; i++)
			outArray[i+2] = theArray[i];
		
		return outArray;
	}
	
	/**
	 * Create a new array that consists of one array combined with another.
	 * 
	 * @param a1 The first array to combine.
	 * @param a2 The second array to combine.
	 * @return The result of combining the two arrays { a1[0], a1[1],...,a2[0], a2[1],... }
	 */
	public static String[] appendStrings (String[] a1, String[] a2)
	{
		String[] newArray = new String[a1.length + a2.length];
		
		if (null == a1)
			a1 = new String[0];
		
		if (null == a2)
			a2 = new String[0];
		
		int i;
		for (i = 0; i < a1.length; i++)
		{
			newArray[i] = a1[i];
		}
		
		for (i = 0; i < a2.length; i++)
		{
			newArray[i + a1.length] = a2[i];
		}
		
		return newArray;
	}
	
	
	public static Object[] copy (Object[] src)
	{
		Object[] temp = new Object[src.length];
		
		for (int i = 0; i < src.length; i++)
		{
			temp[i] = src[i];
		}
		
		return temp;
	}
	
	public static Object[] copy (Object[] src, int start)
	{
		int length = src.length - 1;
		Object[] temp = new Object[length];
		
		for (int i = 0; i < length; i++)
		{
			temp[i] = src[length + i];
		}
		
		return temp;
	}
	
	
	public static void copy(Object[] src, Object[] dest, int srcStart, int destStart,
			int length)
	{
		for (int i = 0; i < length; i++)
		{
			dest[i + destStart] = src[i + srcStart];
		}
	}
	
	public static Object[] copy (Class aclass, Object[] src, int start) throws Exception
	{
		if (!aclass.isArray())
			throw new IllegalArgumentException();
		
		Class ctype = aclass.getComponentType();
		int length = src.length - start;
		if (length < 0)
			throw new IllegalArgumentException();
		
		Object[] dest = (Object[]) Array.newInstance(ctype, length);
		copy(src, dest, start, 0, length);
		return dest;
	}

	
	public static void reverse(int[] intArray)
	{
		if (intArray.length < 2)
			return;
		
		int last = intArray.length/2;
		for (int index = intArray.length/2; index < last; index++)
		{
			int swapIndex = last + index;
			int swap = intArray[swapIndex];
			intArray[swapIndex] = intArray[index];
			intArray[index] = swap;
		}
	}
	
	
	public static void sortDescending(int[] intArray)
	{
		if (null == intArray)
			return;
		
		Arrays.sort(intArray);
		reverse(intArray);
	}


	public static <E> boolean arrayContains(E[] array, E obj)
	{
		for (E element : array)
		{
			if (obj.equals(element))
				return true;
		}
		
		return false;
	}
}
