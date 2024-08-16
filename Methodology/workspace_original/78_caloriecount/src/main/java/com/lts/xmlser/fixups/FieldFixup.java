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

import java.lang.reflect.Field;

import com.lts.LTSException;
import com.lts.xmlser.XmlSerializer;

public class FieldFixup extends ReferenceFixup 
{
	/**
	 * The field within the source that should point to the destination.
	 */
	public Field field;
	
	/*
	public FieldFixup (ReferenceFixup fixup, Field theField)
	{
		super(fixup);
		field = theField;
	}
	
	
	public FieldFixup (Object destination, Integer refid, Field theField)
	{
		super(destination, refid);
		field = theField;
	}
	*/
	
	public boolean fixupSuccessful (XmlSerializer xser)
		throws LTSException
	{
		try 
		{
			boolean successful = false;
			
			Object o = xser.idToObject(id);
			if (null != o)
			{
				field.setAccessible(true);
				field.set(destination, o);
				successful = true;
			}
			
			return successful;
		} 
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to set field, " + field.getName()
				+ ", in object " + destination
				+ ", with value " + xser,
				e
			);
		} 
	}
}
