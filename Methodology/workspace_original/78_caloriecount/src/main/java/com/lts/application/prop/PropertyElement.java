//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application.prop;

import java.util.Comparator;

public class PropertyElement implements Comparable
{
	public String prefix;
	public int index;
	public String suffix;
	public String value;
	
	public PropertyElement (String theprefix, int theindex, String thesuffix, String thevalue)
	{
		this.prefix = theprefix;
		this.index = theindex;
		this.suffix = thesuffix;
		this.value = thevalue;
	}
	
	
	public String getFullName ()
	{
		return this.prefix + "." + this.index + "." + this.suffix;
	}
	
	public static class IndexComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			PropertyElement el1 = (PropertyElement) o1;
			PropertyElement el2 = (PropertyElement) o2;
			
			if (el1.index < el2.index)
				return -1;
			else if (el1.index > el2.index)
				return 1;
			else
				return 0;
		}
	}
	
	
	public static class NameComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			PropertyElement el1 = (PropertyElement) o1;
			PropertyElement el2 = (PropertyElement) o2;
			
			return el1.getFullName().compareTo(el2.getFullName());
		}
	}
	
	
	/**
	 * Compare by index, then by relative name.
	 * 
	 * <P>
	 * Note that relative name is only used if the indicies of the two elements 
	 * are the same.
	 * 
	 * @author cnh
	 *
	 */
	public static class IndexNameComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			int result = INDEX_COMPARATOR.compare(o1, o2);
			
			if (0 != result)
			{
				result = NAME_COMPARATOR.compare(o1, o2);
			}
			
			return result;
		}
	}
	
	protected static NameComparator NAME_COMPARATOR = new NameComparator();
	protected static IndexComparator INDEX_COMPARATOR = new IndexComparator();
	protected static Comparator INDEX_NAME_COMPARATOR = new IndexNameComparator();
	protected static Comparator DEFAULT_COMPARATOR = NAME_COMPARATOR;
	
	public int compareTo (Object o)
	{
		return DEFAULT_COMPARATOR.compare(this, o);
	}
}
