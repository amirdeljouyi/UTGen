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

import java.util.HashMap;
import java.util.Map;

/**
 * A CaselessMap that simplifies mapping from strings to integers and back 
 * again.
 *
 * <P/>
 * A chore that I find myself doing over and over again is defining methods
 * to map from a string to an integer and then from an integer to a string.
 * This class aleviates some of the burdon by defining such methods and 
 * by accepting a specification for the mapping.  It also only requires 
 * a string to integer specification and uses that same specification to 
 * infer the reverse mapping.
 *  
 * @author cnh
 */
public class StringIntMap 
{
	protected Map myStringToIntMap;
	protected Map myIntToStringMap;
	
	public StringIntMap()
	{}
	
	public StringIntMap (Object[] strToIntSpec)
	{
		initialize(strToIntSpec);
	}
	

	public void initialize (Object[] strToIntSpec)
	{
		myStringToIntMap = new CaselessMap(strToIntSpec.length/2);
		myIntToStringMap = new HashMap(strToIntSpec.length/2);
		
		int index = 0;
		while (index < strToIntSpec.length)
		{
			String s = null;
			Integer i = null;
			
			if (strToIntSpec[index] instanceof String)
			{
				s = (String) strToIntSpec[index];
				i = (Integer) strToIntSpec[1+index];
			}
			else
			{
				i = (Integer) strToIntSpec[index];
				s = (String) strToIntSpec[1+index];
			}
			
			myStringToIntMap.put(s, i);
			myIntToStringMap.put(i, s);
			
			index = index + 2;
		}
	}
	
	
	public int stringToInt(String s)
	{
		Integer i = (Integer) myStringToIntMap.get(s);
		
		if (null == i)
			return -1;
		else
			return i.intValue();
	}
	
	
	public String intToString (int ivalue)
	{
		Integer i = new Integer(ivalue);
		String s = (String) myIntToStringMap.get(i);
		
		return s;
	}
	
	
	public Map getStringToIntMap()
	{
		return myStringToIntMap;
	}
	
	public Map getIntToStringMap()
	{
		return myIntToStringMap;
	}
}
