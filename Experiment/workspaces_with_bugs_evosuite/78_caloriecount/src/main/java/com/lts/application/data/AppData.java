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
package com.lts.application.data;

import java.util.ArrayList;
import java.util.List;

import com.lts.application.ApplicationException;

/**
 * Application data that supports the notion of hierarchical data.
 * 
 * <H3>Quickstart</H3>
 * <UL>
 * <LI>Subclass this class
 * <LI>Add properties for different elements
 * <LI>create accessors that also call add/removeElement
 * </UL>
 * 
 * <P>
 * The individual elements of data can subclass this class, or implement 
 * {@link ApplicationDataElement} directly.
 * 
 * <P>
 * Accessors that also call add/removeElement are needed so that the isDirty method
 * works correctly.
 * 
 * <P>
 * This class supports the following properties:
 * <UL>
 * <LI>dirtyElement --- is this specific node dirty?
 * <LI>dirty --- is this node or any of its children dirty?
 * <LI>element(s) --- children of this node.
 * </UL>
 * 
 * <P>
 * An element status refers to whether or not an individual object is clear or 
 * dirty.
 * 
 * <P>
 * The network status refers to whether this element and its child elements are 
 * dirty.  If the child elements have, in turn, their own elements, then those offspring
 * are also considered.
 * 
 * <P>
 * The elements property are the the "child data" of this element.  Though you can 
 * nest elements to any arbitrary level, 1 level of children is usually sufficient.
 * 
 * @author cnh
 */
abstract public class AppData implements ApplicationDataElement 
{
	transient protected boolean dirtyElement;
	protected List<ApplicationDataElement> elements;
	
	public boolean isDirtyElement()
	{
		return this.dirtyElement;
	}
	
	public void setDirtyElement (boolean newDirty)
	{
		this.dirtyElement = newDirty;
	}
	
	public boolean getDirtyElement()
	{
		return isDirtyElement();
	}
	
	
	public boolean isDirty()
	{
		if (isDirtyElement())
			return true;
		
		for (ApplicationDataElement data : getElements())
		{
			if (data.isDirty())
				return true;
		}
		
		return false;
	}
	
	public boolean getDirty() throws ApplicationException
	{
		return isDirty();
	}
	
	public void setDirty(boolean newDirty)
	{
		setDirtyElement(newDirty);
		for (ApplicationDataElement data : getElements())
		{
			data.setDirty(newDirty);
		}
	}
	
	public void setClean()
	{
		setDirty(false);
	}
	
	public void setDirty()
	{
		setDirty(true);
	}
	
	/**
	 * The sub-elements of this instance's application data.
	 * 
	 * <H3>NOTE</H3>
	 * While one can modify the returned list directly, this is a very, very bad 
	 * idea!
	 * 
	 * @return
	 */
	public List<ApplicationDataElement> getElements()
	{
		if (null == this.elements)
			this.elements = new ArrayList<ApplicationDataElement>();
		
		return this.elements;
	}
	
	public void addElement(ApplicationDataElement data)
	{
		List<ApplicationDataElement> list = getElements();
		if (!list.contains(data))
			list.add(data);
	}
	
	
	public boolean removeElement (ApplicationDataElement data)
	{
		List<ApplicationDataElement> list = getElements();
		return list.remove(data);
	}
	
	public void clearElements ()
	{
		this.elements = null;
	}
	
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}
}
