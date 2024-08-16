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
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author cnh
 */
public class StringUtils 
{
	public static String[] split (String s, String delimiters)
	{
		StringTokenizer st = new StringTokenizer(s, delimiters);
		int count = st.countTokens();
		String[] results = new String[count];
		
		for (int i = 0; i < count; i++)
		{
			results[i] = st.nextToken();
		}
		
		return results;
	}
	
	
	public static List splitToList (String s, String delimiters)
	{
		String[] sa = split(s, delimiters);
		List l = new ArrayList();
		for (int i = 0; i < sa.length; i++)
		{
			l.add(sa[i]);
		}
		
		return l;
	}
	
	public static void trimStrings (String[] strs)
	{
		for (int i = 0; i < strs.length; i++)
		{
			if (null != strs[i])
				strs[i] = strs[i].trim();
		}
	}
	
	/**
	 * Return a trimmed version of the string or null if the input string is 
	 * null or empty.
	 * 
	 * @param s The string to process.
	 * @return null if the input string is null, empty or if the trimmed version
	 * is empty.  Otherwise, return the trimmed version of the input string.
	 */
	public static String trim (String s)
	{
		if (null == s)
			return null;
		
		s = s.trim();
		if ("".equals(s))
			return null;
		else
			return s;
	}

	public static String[] splitAndTrim (String s, String delimiters)
	{
		String[] sa = split(s, delimiters);
		trimStrings(sa);
		return sa;
	}
	
	
	public static int parseString (String s, int index, String delim)
	{
		int c = (int) s.charAt(index);
		while (-1 != delim.indexOf(c))
		{
			index++;
			c = (int) s.charAt(index);
		}
		
		return index;
	}
	
	
	public static final int MODE_START = 0;
	public static final int MODE_TOKEN = 1;
	public static final int MODE_DELIMITER = 2;
	
	
	public static boolean contains (char c, String delim)
	{
		return -1 != delim.indexOf((int) c);
	}
	
	
	public static String[] splitInclude (
		String s, 
		String delimiters
	)
	{
		List l = new ArrayList();
		StringBuffer sb = new StringBuffer();
		int mode = MODE_START;
		int i;
		
		char[] ca = s.toCharArray();
		for (i = 0; i < ca.length; i++)
		{
			switch (mode)
			{
				case MODE_START :
					if (contains(ca[i], delimiters))
					{
						mode = MODE_DELIMITER;
						sb.append(ca[i]);
					}
					else
					{
						mode = MODE_TOKEN;
						sb.append(ca[i]);
					}
					break;
				
				case MODE_TOKEN :
					if (contains(ca[i], delimiters))
					{
						mode = MODE_DELIMITER;
						l.add(sb.toString());
						sb = new StringBuffer();
						sb.append(ca[i]);
					}
					else 
					{
						sb.append(ca[i]);
					}
					break;
				
				case MODE_DELIMITER :
					if (!contains(ca[i], delimiters))
					{
						mode = MODE_TOKEN;
						l.add(sb.toString());
						sb = new StringBuffer();
						sb.append(ca[i]); 
					}
					else
					{
						sb.append(ca[i]);
					}
					break;
			}
		}
		
		l.add(sb.toString());
		
		String[] sa = new String[l.size()];
		for (i = 0; i < sa.length; i++)
		{
			String temp = (String) l.get(i);
			sa[i] = temp;
		}
		
		return sa;
	}
	
	public static boolean containsCharacters (String s, String delims)
	{
		char[] ca = delims.toCharArray();
		for (int i = 0; i < ca.length; i++)
		{
			if (-1 != s.indexOf(ca[i]))
				return true;
		}
		
		return false;
	}
	
	
	public static char lastchar (String s)
	{
		int len = s.length() - 1;
		if (len < 0)
			return (char) -1;
		else
			return s.charAt(len);
	}
	
	
	public static String shortenString (String s)
	{
		int len = s.length() - 1;
		if (len < 0)
			s = "";
		else
			s = s.substring(0,len);
		
		return s;
	}
	
	public static String join (String[] sa, int start, int end)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = start; i <= end; i++)
		{
			sb.append(sa[i]);
		}
		String s = sb.toString();
		return s;
	}
	
	
	public static String join (String[] sa, int end)
	{
		return join (sa, 0, end);
	}
	
	public static String join (String[] sa)
	{
		return join (sa, 0, sa.length - 1);
	}
	
	
	public static final int compareStrings (String s1, String s2)
	{
		if (s1 == s2)
			return 0;
		else if (null == s1)
			return -1;
		else if (null == s2)
			return 1;
		else
			return s1.compareTo(s2);
	}
	
	
	public static final boolean stringsAreEqual(String s1, String s2)
	{
		int result = compareStrings(s1, s2);
		return 0 == result;
	}
	
	public static boolean nullEmpty (String s)
	{
		if (null == s)
			return true;
		
		s = s.trim();
		return "".equals(s);
	}
	
	
	public static String toCamelCase (String s, boolean capitalizeFirstChar)
	{
		char[] inchars = s.toCharArray();
		StringBuffer sb = new StringBuffer(inchars.length);
		boolean capNext = capitalizeFirstChar;
		
		for (int i = 0; i < inchars.length; i++)
		{
			if (inchars[i] == '_' || inchars[i] == '-')
			{
				capNext = true;
			}
			else if (capNext)
			{
				sb.append(Character.toUpperCase(inchars[i]));
				capNext = false;
			}
			else 
			{
				sb.append(Character.toLowerCase(inchars[i]));
			}
		}
		
		String temp = sb.toString();
		return temp;
	}
	
	
	public static String toCamelCase (String s)
	{
		return toCamelCase(s, false);
	}
	

	protected static final String STRING_HEX = "0123456789ABCDEF";
	protected static final char[] ARRAY_HEX = STRING_HEX.toCharArray();
	
	/**
	 * Return a string of the form xx where "xx" are the two hex digits required
	 * to represent a byte. Note that a zero value in the first four bits will 
	 * be printed as "0x" rather than "x".
	 * 
	 * @param b
	 * @return
	 */
	
	public static String byteToHexString (byte b)
	{
		int temp = b;
		temp = b & 0x0F;
		char c0 = ARRAY_HEX[temp];
		
		temp = b & 0xF0;
		temp = temp >> 4;
		char c1 = ARRAY_HEX[temp];
		
		StringBuffer sb = new StringBuffer(2);
		sb.append(c1);
		sb.append(c0);
		
		return sb.toString();
	}

	
	/**
	 * Create a hexidecimal string from the input value, prepending the string with zeros
	 * if the caller asks for this.
	 * <P>
	 * For example, the value 15 converted to a hex string with no zero padding would be
	 * "F". The same value with zero padding: "000000000000000F".
	 * 
	 * @param zeroPad
	 *        true if the client was the value to be prepended with zeros, false
	 *        otherwise.
	 * @param value
	 *        the value to convert.
	 * @return A hex string that corresponds to the value.
	 */
	public static String toHexString (boolean zeroPad, long value, int bytes)
	{
		char[] chars = new char[2 * bytes];
		
		char padChar = ' ';
		if (zeroPad)
			padChar = '0';
		
		for (int i = 0; i < chars.length; i++)
		{
			chars[i] = padChar;
		}
		
		
		for (int i = 0; i < chars.length; i++)
		{
			int index = (int) (value & 0xFL);
			value = value >> 4;
			chars[chars.length - 1 - i] = ARRAY_HEX[index];
		}
		
		String s = new String(chars);
		if (!zeroPad)
			s = s.trim();
		
		return s;
	}
	
	
	public static String toHexString (boolean zeroPad, long value)
	{
		return toHexString(zeroPad, value, 16);
	}
	
	
	public static boolean equivalent (String s1, String s2) 
	{
		if (s1 == s2)
			return true;
		if (null == s1 || null == s2)
			return false;
		else
			return s1.equals(s2);
	}
	
	public static String toHexString(boolean zeroPad, byte value)
	{
		StringBuffer sb = new StringBuffer();
		int ival = value & 0xF0;
		if (ival == 0 && zeroPad)
		{
			sb.append(0);
		}
		else
		{
			ival = ival >> 4;
			sb.append(Integer.toHexString(ival));
		}
		
		ival = value & 0xF;
		sb.append(Integer.toHexString(ival));
		
		return sb.toString();
	}
	
	public static String toHexString(byte[] bytes)
	{
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes)
		{
			sb.append(toHexString(true, b));
		}
		
		return sb.toString();
	}
	
	
	public static boolean different (String s1, String s2)
	{
		return !equivalent(s1, s2);
	}


	public static Integer toInteger(String string)
	{
		Integer ival = null;
		
		try
		{
			ival = new Integer(string);
		}
		catch(NumberFormatException e)
		{}
		
		return ival;
	}


	public static Long toLong(String string)
	{
		Long lval = null;
		
		try
		{
			lval = new Long(string);
		}
		catch(NumberFormatException e)
		{}
		
		return lval;
	}
	
	
	public static class StringComparator implements Comparator<String>
	{
		public int compare(String o1, String o2)
		{
			return o1.compareTo(o2);
		}	
	}
	
	public static Comparator caselessComparator = new Comparator<String>() {
		public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
	};
	
	
	public static class InverseStringComparator implements Comparator<String>
	{
		public int compare(String s1, String s2)
		{
			return -1 * s1.compareTo(s2);
		}
	}
	
	
	public static String strValue(byte b)
	{
		return Integer.toHexString(b);
	}
	
	public static String strValue(int i)
	{
		return Integer.toString(i);
	}
	
	public static String strValue(boolean b)
	{
		return Boolean.toString(b);
	}
	
	public static String strValue(char c)
	{
		return Character.toString(c);
	}
	
	public static String strValue(long l)
	{
		return Long.toString(l);
	}
	
}
