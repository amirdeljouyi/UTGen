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
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.tree;

import javax.swing.event.TreeModelEvent;

/**
 * @author cnh
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class DeferredTreeModelEvent 
	extends TreeModelEvent 
{
	protected int myEventType;
	
	public static final int NODES_CHANGED = 0;
	public static final int NODES_INSERTED = 1;
	public static final int NODES_DELETED = 2;
	public static final int STRUCTURE_CHANGED = 4;
	
	public int getEventType ()
	{
		return myEventType;
	}
	
	public void setEventType (int i)
	{
		myEventType = i;
	}
	
	public DeferredTreeModelEvent (
		int eventType,
		Object source,
		Object[] path,
		int[] childIndices,
		Object[] children
	)
	{
		super(source, path, childIndices, children);
		setEventType(eventType);
	}
}
