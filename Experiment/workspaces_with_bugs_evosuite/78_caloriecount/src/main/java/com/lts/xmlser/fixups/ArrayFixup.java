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
package com.lts.xmlser.fixups;

import java.lang.reflect.Array;

import com.lts.xmlser.XmlSerializer;

/**
 * A fixup for an array.
 * 
 * <P/>
 * An array fixup has an array object into which the value is placed; it also
 * records the index where that value should be put.
 */
public class ArrayFixup extends ReferenceFixup 
{
	/**
	 * The location in the array that should be fixed.
	 */
	public int myIndex;

	public boolean fixupSuccessful (XmlSerializer xser)
	{
		boolean successful = false;
		Object o = xser.idToObject(id);
		if (null != o)
		{
			Array.set(getDestination(), myIndex, o);
			successful = true;
		}
		
		return successful;
	}
	
	
	public ArrayFixup (Object destination, Integer objectID, int index)
	{
		super(destination, objectID);
		myIndex = index;
	}
	
	public ArrayFixup (ReferenceFixup f)
	{
		super(f);
	}
	
	public ArrayFixup ()
	{}
}
