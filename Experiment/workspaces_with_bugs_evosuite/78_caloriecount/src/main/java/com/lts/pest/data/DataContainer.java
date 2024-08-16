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
package com.lts.pest.data;

/**
 * A DataItem that is an aggregate of other DataItems.
 * 
 * <P>
 * The object itself is not dirty or clean, instead the collection of all the constituent
 * objects determine the object's status.  If any component object is dirty, then 
 * the object is considered dirty.  If the object is marked as clean or dirty, then the 
 * the status is forwarded to all components via {@link DataItem#setDirty(boolean)}.
 * 
 * @author cnh
 */
public interface DataContainer extends DataItem
{
	public DataItem[] getComponents();
}
