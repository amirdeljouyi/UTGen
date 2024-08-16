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
package com.lts.pest.tree;

import java.util.Map;

import com.lts.pest.data.DataItem;
import com.lts.pest.data.IdObject;
import com.lts.pest.data.event.DataChangeListener;
import com.lts.util.TreeNode;
import com.lts.util.deepcopy.DeepCopyException;

abstract public class IdTreeNode 
	extends TreeNode 
	implements IdObject, DataItem, Cloneable
{
	protected int id;
	
	public void initialize (int newId, IdTreeNode parent)
	{
		this.id = newId;
		super.initialize(parent, null, null);
	}
	
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId (int newId)
	{
		this.id = newId;
	}
	
	transient protected boolean myDirty;
	
	public boolean isDirty()
	{
		return myDirty;
	}
	
	public void setDirty (boolean dirty)
	{
		myDirty = dirty;
	}
	
	public boolean getDirty ()
	{
		return myDirty;
	}
	
	
	public void addDataChangeListener (DataChangeListener listener)
	{
		throw new IllegalStateException();
	}
	
	public boolean removeDataChangeListener (DataChangeListener listener)
	{
		return false;
	}
	
	
	public void deepCopyData(Object ocopy, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		super.deepCopyData(ocopy, map, copyTransients);
		
		IdTreeNode copy = (IdTreeNode) ocopy;
		copy.id = this.id;
		
		if (copyTransients)
			copy.myDirty = this.myDirty;
	}

	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
