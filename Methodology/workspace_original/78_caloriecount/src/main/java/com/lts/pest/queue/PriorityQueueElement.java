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
package com.lts.pest.queue;

/**
 * An object with an integer priority value that can change at any time.
 * 
 * <H2>Description</H2>
 * The literal meaning of this interface is that the implementor returns a 
 * value for getPriority, and the value can change between calls.
 * 
 * <P>
 * The implied meaning of this interface is that there are some number of 
 * objects, one of which is the "highest priority," another of which is the 
 * "next highest" and so on.  The collection may be sorted by priority, but that
 * order is not stable.  The relative priority of the elements can change at 
 * any time.
 * 
 * @author cnh
 */
public interface PriorityQueueElement
{
	public int getPriority();
}
