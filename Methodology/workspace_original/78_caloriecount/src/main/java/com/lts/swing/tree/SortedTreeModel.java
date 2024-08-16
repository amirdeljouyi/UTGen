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
package com.lts.swing.tree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */

/**
 * @author cnh
 */
@SuppressWarnings("serial")
public class SortedTreeModel 
	extends DefaultTreeModel 
{
	public SortedTreeModel (DefaultMutableTreeNode root)
	{
		super(root);
	}
	
	
	public void addChild (
		DefaultMutableTreeNode parent,
		DefaultMutableTreeNode child
	)
	{
		String childName = "";
		
		if (null != child.getUserObject())
			childName = child.getUserObject().toString();
			
		int count = parent.getChildCount();
		int index;
		
		for (index = 0; index < count; index++)
		{
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) parent.getChildAt(index);
			String s = "";
			if (null != node.getUserObject())
				s = node.getUserObject().toString();
			
			if (childName.compareTo(s) < 0)
				break;
		}
		
		insertNodeInto(child, parent, index);
	}
	
	public void addChild (DefaultMutableTreeNode child)
	{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();
		addChild(root, child);
	}
}
