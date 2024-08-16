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
 * Created on Apr 16, 2004
 */
package com.lts.swing;

/**
 * @author cnh
 */
@SuppressWarnings("serial")
public class SortedListModel extends SimpleListModel
{
	public static final int ORDER_ASCENDING = 0;
	public static final int ORDER_DESCENDING = 1;
	
	protected int myOrder;
	
	public int getOrder()
	{
		return myOrder;
	}
	
	public void setOrder(int order)
	{
		myOrder = order;
	}
	
	public void append (String element)
	{
		int length = getSize();
		int index = 0;
		int order = getOrder();
		
		while (index < length)
		{
			String temp = (String) get(index);
			int result = temp.compareTo(element);
			if (result > 0 && order == ORDER_DESCENDING)
				break;
			else if (result < 0 && order == ORDER_ASCENDING)
				break;
			else if (ORDER_DESCENDING == order)
				break;
		}
		
		add(index, element);
	}
}
