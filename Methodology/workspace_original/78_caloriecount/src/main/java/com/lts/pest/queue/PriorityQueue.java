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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A dynamic, synchronized collection of elements whose order is based on 
 * priority.
 * 
 * <H2>Description</H2>
 * Instances of this class are ordered collections of elements whose order can 
 * change instant by instant.  The order at anyone one time is called a snapshot
 * and is obtained by simply iterating over the entire collection and calling 
 * "getPriority" on each element and then sorting the collection based on these
 * values.
 * 
 * <P>
 * If two elements have the same priority in a snapshot, the order of the two 
 * elements relative to each other is not defined --- either ordering is valid.
 * 
 * <P>
 * The class does not allow nulls or duplicates.  Attempting to add a null value
 * results in a NullPointerException.  Attempting to add the same element twice
 * results in the element being added once --- subsequent attempts to add the 
 * element have no effect.
 * 
 * <P>
 * Presence or absence from the queue is based on identify.
 * 
 * @author cnh
 */
public class PriorityQueue
{
	protected HashMap elements;
	
	/**
	 * An object with priority and data.
	 * 
	 * <H2>Internal Class</H2>
	 * Instances of this class are used by the containing class and not meant 
	 * for use outside of it.  This class is not part of any of the containing 
	 * classes public methods.
	 * 
	 * <H2>Description</H2>
	 * Instances of this class are used to maintain a stable priority value of
	 * elements in the priority queue while creating a snapshot.  The priority 
	 * of any one element may change from one call to getPriority to the next,
	 * but stable values are required if one is going to sort the list.  This 
	 * class solves the problem by "remembering" the priority of an element at 
	 * the time instance of this class is created.  That priority is then used 
	 * for sorting the contained element, thus creating a stable value.
	 * 
	 * @author cnh
	 *
	 */
	protected static class SortElement implements Comparable
	{
		public Object data;
		public int priority;
		
		public SortElement (PriorityQueueElement element)
		{
			this.priority = element.getPriority();
			this.data = element;
		}
		
		public int compareTo(Object o)
		{
			SortElement other = (SortElement) o;
			if (this.priority == other.priority)
				return 0;
			else if (this.priority > other.priority)
				return 1;
			else 
				return -1;
		}
	}
	
	/**
	 * Return a list of the queue's elements, sorted by priority during the 
	 * period of the call.
	 * 
	 * <H2>Description</H2>
	 * This method creates a sorted list of the queue elements by copying the 
	 * priority values of the elements and then creating a sorted list based on
	 * those priorities.  The precise time when the priority value is obtained,
	 * and the order in which these values are asked for is not guaranteed.
	 * 
	 * @return
	 */
	synchronized public List snapShot()
	{
		//
		// Create a list of SortElements.  The order of this list is arbitrary.
		//
		ArrayList list = (ArrayList) new ArrayList(this.elements.keySet());
		ArrayList sortable = new ArrayList(this.elements.size());
		for (int i = 0; i < list.size(); i++)
		{
			PriorityQueueElement el = (PriorityQueueElement) list.get(i);
			SortElement sel = new SortElement(el);
			sortable.add(sel);
		}
		
		//
		// Sort the list according to priority of the elements at one instant
		// in time.
		//
		Collections.sort(sortable);

		//
		// create a list of PriorityQueueElements that is ordered based on the 
		// above priority
		//
		list = new ArrayList(sortable.size());
		for (int i = 0; i < sortable.size(); i++)
		{
			SortElement sel = (SortElement) sortable.get(i);
			list.add(sel.data);
		}
		
		return list;
	}
	
	/**
	 * Add a PriorityQueueElement to the queue, return true if the element is 
	 * new to the queue, false otherwise.
	 * 
	 * @param el The element to add.
	 * @return true if the element is new to the queue at the time of the call,
	 * false otherwise.
	 * @exception NullPointerException This is thrown if the provided element
	 * is null.
	 */
	synchronized public boolean add (PriorityQueueElement el)
	{
		if (null == el)
			throw new NullPointerException();
		
		boolean newElement;
		
		if (null != this.elements.get(el))
		{
			newElement = false;
		}
		else
		{
			elements.put(el, el);
			newElement = false;
		}
		
		return newElement;
	}
	
	/**
	 * Remove an element from the queue, return true if the element was in the
	 * queue at the time of the call, false otherwise.
	 * 
	 * @param el the element to remove.
	 * @return true if the element was in the queue at the time of the call, 
	 * false otherwise.
	 * @excepotion NullPointerException if the provided element is null.
	 */
	synchronized public boolean remove (PriorityQueueElement el)
	{
		boolean present;
		
		if (null == el)
			throw new NullPointerException();
		
		if (null == this.elements.get(el))
			present = false;
		else
		{
			this.elements.remove(el);
			present = true;	
		}
		
		return present;
	}
}
