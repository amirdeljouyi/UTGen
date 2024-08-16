//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest;

import com.lts.util.DoubleMap;

/**
 * Default values for various priority levels.
 * 
 * @author cnh
 */
public class Priority
{
	public static final int HIGHEST	= 100;
	public static final int HIGH 	= 75;
	public static final int NORMAL 	= 50;
	public static final int LOW 	= 25;
	public static final int LOWEST 	= 1;
	
	final static public String STR_HIGHEST 	= "Highest";
	final static public String STR_HIGH 	= "High";
	final static public String STR_NORMAL 	= "Normal";
	final static public String STR_LOW 		= "Low";
	final static public String STR_LOWEST 	= "Lowest";
	
	final static private Object[][] SPEC_PRIORITY_MAP = {
		{ STR_HIGHEST,	HIGHEST 	},
		{ STR_HIGH, 	HIGH 		},
		{ STR_NORMAL, 	NORMAL 		},
		{ STR_LOW, 		LOW 		},
		{ STR_LOWEST, 	LOWEST 		}
	};
	
	
	final static public String[] PRIORITY_STRINGS = {
		STR_HIGHEST, STR_HIGH, STR_NORMAL, STR_LOW, STR_LOWEST
	};
	
	static private DoubleMap ourMap;
	
	static private void initializeMap()
	{
		if (null == ourMap)
		{
			ourMap = new DoubleMap(SPEC_PRIORITY_MAP);
		}
	}
	
	static private DoubleMap getMap()
	{
		if (null == ourMap)
			initializeMap();
		
		return ourMap;
	}
	
	static public int stringToPriority(String s)
	{
		Integer i = (Integer) getMap().get(s);
		if (null == i)
		{
			String msg = "Unrecognized priority string: " + s;
			throw new IllegalArgumentException(msg);
		}
		
		return i.intValue();
	}
	
	
	static public String priorityToString(int priority)
	{
		String s;
		
		if (priority > HIGHEST)
		{
			String msg = 
				"Priority code is greater than highest value.  "
				+ "Max value: " + HIGHEST
				+ ", supplied value: " + priority;
			
			throw new IllegalArgumentException(msg);
		}
		else if (priority == HIGHEST)
			s = STR_HIGHEST;
		else if (priority > NORMAL)
			s = STR_HIGH;
		else if (priority > LOW)
			s = STR_NORMAL;
		else if (priority > LOWEST)
			s = STR_LOW;
		else if (priority == LOWEST)
			s = STR_LOWEST;
		else
		{
			String msg =
				"Priority code is less than minimum value.  "
				+ "Min value: " + LOWEST
				+ ", supplied vlaue: " + priority;
			throw new IllegalArgumentException(msg);
		}
		
		return s;
	}
	
	/**
	 * Return the relative positions of the two priorities a la Comparable.
	 * 
	 * @param p1 The first priority
	 * @param p2 The second priority
	 * @return -1, 0, or 1 signalling that p1 should come before, at the same point,
	 * or after p2
	 */
	static public int sortComparePriorities (int p1, int p2)
	{
		if (p1 > p2)
			return -1;
		else if (p1 < p2)
			return 1;
		else
			return 0;
	}
}
