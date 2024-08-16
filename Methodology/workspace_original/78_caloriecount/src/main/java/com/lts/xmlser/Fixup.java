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

import com.lts.LTSException;

/**
 * An instance of this class contains the information needed to "fix" a 
 * reference from one serialized object to another.
 * 
 * <P/>
 * When one object wants to reference another object, but the other object
 * has not be reconstituted yet, a Fixup object is created to record this 
 * fact.  When the referenced object is reconstituted, the list of fixups
 * can be traversed and those objects that want to reference the newly 
 * reconsitituted object can be "fixed" so that they indeed reference it.
 *  
 */
public class Fixup
{
	public boolean fixupSuccessful (XmlSerializer xser)
		throws LTSException
	{
		throw new LTSException("not impelemented");
	}
	
	/**
	 * The object that wants to reference the destination.
	 */
	public Object destination;
	
	public Object getDestination()
	{
		return destination; 
	}
	
	public void setDestination (Object o)
	{
		destination = o;
	}
	
	
	public Fixup ()
	{}
	
	public Fixup (Object o)
	{
		destination = o;
	}
}
