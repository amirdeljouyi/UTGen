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

public class MoreArrayUtils
{
	/**
	 * Does the array contain a particular object?
	 * 
	 * <P>
	 * This method returns true if at least one element in the array is identity
	 * equivalent (==) to the provided object.  Note that null is considered a 
	 * valid value for the target (does the array contain null?)
	 * 
	 * <P>
	 * The method returns false if the input array is null or none of its elements
	 * are identity equivalent to the target.
	 * 
	 * @param o The element to try and find in the array.
	 * @param array The array to search.
	 * @return true if the array contains the target, false otherwise.
	 */
	static public boolean contains (Object o, Object[] array)
	{
		if (null == array)
			return false;

		for (int i = 0; i < array.length; i++)
		{
			if (o == array[i])
				return true;
		}
		
		return false;
	}
}
