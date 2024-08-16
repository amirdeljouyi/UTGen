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
 * Created on Apr 20, 2004
 */
package com.lts.swing.table;

import java.util.Comparator;


public class RowComparator implements Comparator
{
	public static final int ORDER_ASCENDING = 0;
	public static final int ORDER_DESCENDING = 1;
	
	protected int sortColumn;
	protected int sortOrder;
	
	public int getSortColumn()
	{
		return sortColumn;
	}

	public void setSortColumn(int sortColumn)
	{
		this.sortColumn = sortColumn;
	}

	public int getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(int sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public int compare (Object o1, Object o2)
	{
		Object[] row1 = (Object[]) o1;
		Object[] row2 = (Object[]) o2;
		
		if (null == row2)
			return -1;
		else if (null == row1)
			return 1;
		else
		{
			Object col1 = row1[sortColumn];
			Object col2 = row2[sortColumn];

			if (null == col2)
				return -1;
			else if (null == col1)
				return 1;
			else if (!(col1 instanceof Comparable) || !(col2 instanceof Comparable))
			{
				throw new RuntimeException("row data does not implement Comparable");
			}
			else
			{
				Comparable c1 = (Comparable) col1;
				Comparable c2 = (Comparable) col2;
				return c1.compareTo(c2);
			}
		}
	}
}