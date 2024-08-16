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
package com.lts.swing;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

import com.lts.util.CollectionUtils;


@SuppressWarnings("serial")
public class SimpleListModel extends DefaultListModel
{
	public SimpleListModel ()
	{}
	
	public SimpleListModel (List l)
	{
		initialize(l);
	}
	
	public SimpleListModel (Object[] elements)
	{
		initialize(elements);
	}
	
	
	public void add (Object o)
	{
		add(size(), o);
	}
	
	public void addAll (List l)
	{
		if (null == l)
			return;
		
		Iterator i = l.iterator();
		while (i.hasNext())
		{
			addElement(i.next());
		}
	}
	
	public void addAll (Object[] elements)
	{
		if (null == elements)
			return;
		
		for (int i = 0; i < elements.length; i++)
		{
			addElement(elements[i]);
		}
	}
	
	
	public void initialize (List l)
	{
		addAll(l);
	}
	
	
	public void initialize (Object[] elements)
	{
		addAll(elements);
	}
	
	
	public List copyToFileList ()
	{
		List l = new ArrayList();
		
		int count = size();
		for (int i = 0; i < count; i++)
		{
			String fname = (String) get(i);
			File f = new File(fname);
			l.add(f);
		}
		
		return l;
	}
	
	
	public void setElements (List l)
	{
		clear();
		addAll(l);
	}
	
	
	public List copyToList ()
	{
		List l = new ArrayList();
		
		int count = size();
		for (int i = 0; i < count; i++)
		{
			Object o = get(i);
			l.add(o);
		}
		
		return l;
	}
	
	
	public void remove (int[] indicies)
	{
		//
		// basic approach is to start at the last index and work our way back
		// to the start of the list.  That way, the indices should always be 
		// valid
		//
		List l = new ArrayList();
		
		int i;
		for (i = 0; i < indicies.length; i++)
		{
			l.add(new Integer(indicies[i]));
		}
		
		Collections.sort(l);
		
		for (i = l.size() - 1; i >= 0; i--)
		{
			Integer ival = (Integer) l.get(i);
			remove(ival.intValue());
		}
	}
	
	/**
	 * Move an element towards the start of the list.
	 * 
	 * <P/>
	 * This method ensures that the index is valid and that, if the element 
	 * is already at the start of the list, that it does not get moved.
	 * 
	 * <P/>
	 * The method takes care of notifying any listeners for the instance.
	 * 
	 * @param index The index of the element to move.  The value should be 
	 * from 0 to length - 1.  Using a value outside this range may cause an 
	 * exception.
	 */
	public void moveElementUp (int index)
	{
		if (index <= 0)
			return;
		
		Object o = remove(index);
		insertElementAt(o, index - 1);
	}
	
	/**
	 * Move an element towards the end of the list.
	 * 
	 * <P/>
	 * This method does nothing if the element is already at the end of the 
	 * list.  The method notifies any listeners for the instance.
	 * 
	 * @param index The index of the element to move.  If the value is out of 
	 * bounds, an exception may be thrown.
	 */
	public void moveElementDown (int index)
	{
		if (index >= (size() - 1))
			return;
		
		Object o = remove(index);
		insertElementAt(o, index + 1);
	}
	
	/**
	 * Add a new element to the list at the point where it should occur in 
	 * a sorted list.
	 * 
	 * <P/>
	 * This method only applies in the situation where the underlying data in 
	 * the list consists of Strings, and that list is sorted.
	 * 
	 * @param s The new string to add.
	 */
	public void addSorted (String s)
	{
		int index;
		
		for (index = 0; index < getSize(); index++)
		{
			String element = (String) getElementAt(index);
			if (s.compareTo(element) < 0)
				break;
		}
		
		insertElementAt(s, index);
	}
	
	public List asList()
	{
		return CollectionUtils.toList(elements());
	}
}
    
