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
 * Copyright (c) 2004 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.menu;

import java.awt.event.ActionListener;
import java.util.Map;

import com.lts.util.TreeNode;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

/**
 *
 */
public class NewMenuNode extends TreeNode implements Comparable 
{
	protected String name;
	protected ActionListener callback;
	
	public NewMenuNode (Object receiver, String name, ActionListener callback)
	{
		initialize(name, callback);
	}
	
	public NewMenuNode (String name, ActionListener callback)
	{
		this.name = name;
		this.callback = callback;
	}
	
	public void initialize (String name, ActionListener callback)
	{
		setName(name);
		setCallback(callback);
	}
	
	
	public ActionListener getCallback()
	{
		return callback;
	}
	public void setCallback(ActionListener callback)
	{
		this.callback = callback;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public NewMenuNode()
	{}
	
	
	public int compareTo (Object o)
	{
		NewMenuNode other = (NewMenuNode) o;
		return name.compareTo(other.name);
	}
	
	
	public NewMenuNode (String name)
	{
		this.name = name;
	}
	
	public String toString ()
	{
		return this.name;
	}
	
	public void deepCopyData(Object ocopy, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		throw DeepCopyUtil.deepCopyNotSupported(this);
	}

}
