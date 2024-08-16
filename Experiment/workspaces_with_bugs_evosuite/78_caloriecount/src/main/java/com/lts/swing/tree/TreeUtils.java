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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.lts.util.StringUtils;

/**
 * @author cnh
 */
public class TreeUtils 
{
	public static final String SEPARATOR = "/";
	public static final char SEPARATOR_CHAR = '/';
	
	public static void addNodes (
		DefaultMutableTreeNode root,
		String spec, 
		Map pathToNode
	)
	{
		String[] names = StringUtils.split(spec, SEPARATOR);
		String path = "";
		
		DefaultMutableTreeNode parent = root;

		for (int i = 0; i < names.length; i++)
		{
			path = path + SEPARATOR + names[i];
			
			DefaultMutableTreeNode node =
				(DefaultMutableTreeNode) pathToNode.get(path);
			
			if (null == node)
			{
				node = new DefaultMutableTreeNode(names[i]);
				parent.add(node);
				pathToNode.put(path, node);
			}
			
			parent = node;
		}
	}
	
	public static DefaultMutableTreeNode buildTree (
		String[] spec, 
		String rootName
	)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		Map pathToNode = new HashMap();
		
		pathToNode.put(SEPARATOR, root);
		for (int i = 0; i < spec.length; i++)
		{
			addNodes(root, spec[i], pathToNode);		
		}
		return root;
	}
	
	
	public static DefaultMutableTreeNode buildTree (List l)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		Map pathToNode = new HashMap();
		pathToNode.put(SEPARATOR, root);
		
		Iterator iter = l.iterator();
		while (iter.hasNext())
		{
			String s = (String) iter.next();
			addNodes(root, s, pathToNode);
		}
		
		
		return root;
	}
	
	
	public static TreeNode findChildNamed (String name, TreeNode parent)
	{
		int count = parent.getChildCount();
		TreeNode child = null;
		int index = 0;
		while (null == child && index < count)
		{
			TreeNode temp = parent.getChildAt(index);
			if (temp.toString().equals(name))
				child = temp;
			
			index++;
		}
		
		return child;
	}
	
	
	public static Object getSelectedNode (JTree tree)
	{
		TreePath path = tree.getSelectionPath();
		if (null == path)
			return null;
		
		return path.getLastPathComponent();
	}
}
