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
 * A priority queue that have a stable order at well-defined times.
 * 
 * <H2>Description</H2>
 * An instance of this class requires that a) the priority of elements must remain
 * stable at defined points in time and b) that the result of calling "compareTo" 
 * between two elements with the same priority remain stable at these same times.
 * 
 * <P>
 * The "well defined time(s)" from the perspective of this class are from the time
 * that freezePriorities is called to the time that unfreezePriorities is called.
 * 
 * @author cnh
 *
 */
public class StrictPriorityQueue extends PriorityQueue
{

}
