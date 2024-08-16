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
package com.lts.swing.treetable.test;

import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.lts.swing.tree.TreeUtils;
import com.lts.swing.treetable.DefaultTreeTableModel;
import com.lts.util.CaselessMap;

/**
 * @author cnh
 */
public class TestTableTreeModel 
	extends DefaultTreeTableModel 
{
	public static final String[] TREE_SPEC = {
		"/File/Open",
		"/File/New",
		"/File/Save",
		"/File/Save As...",
		"/Edit/Cut",
		"/Edit/Copy",
		"/Edit/Paste"
	};
	
	public static final String[] COLUMN_NAMES = {
		"Name",
		"Method",
		"Description"
	};
	
	public static final String[][] TABLE_DATA = {
		{ "/",			"root",			"The root menu" },
		
		{ "File",		"file",			"The file menu" },
		{ "Open",		"openFile",		"opens the damn file" },
		{ "New",		"newFile",		"creates a new file" },
		{ "Save",		"saveFile",		"saves the damn file" },
		{ "Save As...",	"saveFileAs",	"save the file with another name" },
		
		{ "Edit",		"edit",			"actions having to do with editing" },
		{ "Cut",		"cutNode",		"cut something" },
		{ "Copy",		"copyNode",		"copy something" },
		{ "Paste",		"paseNode",		"paste something" }
	};
	
	
	public static final Object[] SPEC_MAP = {
		"/",					new Integer(0),
		"/File",				new Integer(1),
		"/File/Open",			new Integer(2),
		"/File/New",			new Integer(3),
		"/File/Save",			new Integer(4),
		"/File/Save As...",		new Integer(5),
		"/Edit",				new Integer(6),
		"/Edit/Cut",			new Integer(7),
		"/Edit/Copy",			new Integer(8),
		"/Edit/Paste",			new Integer(9)
	};
	
	public static Map ourPathToRowMap = new CaselessMap(SPEC_MAP);
	
	
	
	public TestTableTreeModel()
	{
		TreeNode root = TreeUtils.buildTree(TREE_SPEC, "root");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		setTreeModel(treeModel);
		
		DefaultTableModel tableModel = new DefaultTableModel(TABLE_DATA, COLUMN_NAMES);
		setTableModel(tableModel);
		
		setNodeToRowMap(ourPathToRowMap);
	}
	
	
	public int nodeToRow (Object o)
	{
		StringBuffer sb = new StringBuffer();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;
		
		TreeNode[] path = node.getPath();
		for (int i = 0; i < path.length; i++)
		{
			String s = path[i].toString();
			if ("".equals(s))
				continue;
				
			sb.append("/");
			sb.append (path[i].toString());
		}
		
		String s = sb.toString();
		if ("".equals(s))
			s = "/";
		
		int row = super.nodeToRow(s);
		
		if (0 > row)
			row = 0;
		
		return row;
	}
	

	public String getHeadingString()
	{
		return "TreeTable Test";
	}

}
