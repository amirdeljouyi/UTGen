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

import java.util.Collection;

import com.lts.LTSException;
import com.lts.xmlser.Fixup;
import com.lts.xmlser.XmlSerializer;

/**
 * Fixes up a value that is part of a sequence of some kind.
 * 
 * <P/>
 * Examples of sequences are collections and arrays.  In either case, the
 * underlying scheme is a list.  Instances of this class replace the reference
 * to an IndexedFixup in that list with the actual object.
 */
public class CollectionFixup extends Fixup 
{
	/**
	 * The list that is holding the collection values until all have been 
	 * "fixed."
	 */
	protected Object[] myData;

	public CollectionFixup (Collection destination, Object[] theData)
	{
		super(destination);
		myData = theData;
	}
	
	
	public boolean fixupSuccessful (XmlSerializer xser)
		throws LTSException
	{
		Collection col = (Collection) getDestination();
		for (int i = 0; i < myData.length; i++)
		{	
			col.add(myData[i]);
		}
		
		return true;
	}
}
