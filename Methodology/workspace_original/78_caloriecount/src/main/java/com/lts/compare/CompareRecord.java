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

/**
 * This record represents that two objects have already been compared against
 * each other or that the system is in the process of comparing them.
 * 
 * <P/>
 * This is useful in ensuring that the comparrision system does not go into an
 * infinite loop. Consider this rather contrivied example:
 * 
 * <PRE>
 * 
 * public class Foo { public Object whatever; }
 * 
 * ...
 * 
 * Foo f1 = new Foo(); Foo f2 = new Foo(); f1.whatever = f2; f2.whatever = f1;
 * 
 * </PRE>
 * 
 * 
 * If we were to do a field-by-field equivalance comparrison (ffec), we would
 * start with field whatever of f1, decide that it is a non-primitive object,
 * and recursively try to perform an ffec on it.  Upon examining the fields 
 * of f1, we discover that it is also an complex object and do an ffec on it
 * as well.  The process continues infinitely.
 * 
 * <P/>
 * Simply remembering which objects we have already traversed may not be good
 * enough because a given instance may be compared against multiple other 
 * instances of other classes during the course of the compare.  Just because
 * it was equivalent to another field of one other object does not mean that 
 * it is equivalent to all other fields it might be compared against.
 * 
 */
public class CompareRecord
{
	public Object object1;
	public Object object2;
	
	public CompareRecord (Object o1, Object o2)
	{
		object1 = o1;
		object2 = o2;
	}
	
	
	public int hashCode ()
	{
		int code = 0;
		
		if (null != object1)
			code = code ^ object1.hashCode();
		
		if (null != object2)
			code = code ^ object2.hashCode();
		
		return code;
	}
	
	
	/**
	 * Return true if this record is equivalent to another record.
	 * 
	 * <P/>
	 * This implementation is a little odd in that it does not care about the 
	 * order of the objects.  That is, the record (a,b) is considered 
	 * equivalent to the record (b,a).  This is due to the transitive nature of
	 * equivalence: if a equiv b, then b equiv a.
	 */
	public boolean equals (Object o)
	{
		if (null == o)
			return false;
		else if (!(o instanceof CompareRecord))
			return false;
		else
		{
			CompareRecord other = (CompareRecord) o;
			return 
				(object1 == other.object1 && object2 == other.object2)
				|| (object2 == other.object1 && object1 == other.object2);
		}
	}
	
	
	public String getClassString()
	{
		if (null == object1)
			return "null";
		else
			return object1.getClass().getName();
	}
	
	
	public String toLinkString (boolean first)
	{
		if (null == object1)
			return "";
		else
			return object1.getClass().getName();
	}
}
