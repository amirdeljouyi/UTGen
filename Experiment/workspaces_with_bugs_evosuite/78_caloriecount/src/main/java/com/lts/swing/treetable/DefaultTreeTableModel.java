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
package com.lts.swing.treetable;

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * A simple-minded approach to the problem that uses a TreeModel and a 
 * TableModel to handle the calls relating to trees or tables and a 
 * hash table to figure out the row that is associated with a particular
 * node.
 * 
 * @author cnh
 */
public class DefaultTreeTableModel 
	implements TreeTableModel 
{
	protected TreeModel myTreeModel;
	protected TableModel myTableModel;
	protected Map myNodeToRowMap;
	
	
	public DefaultTreeTableModel ()
	{
		initialize();
	}
	
	
	public void initialize ()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		myTreeModel = new DefaultTreeModel(root);
		myTableModel = new DefaultTableModel();
		myNodeToRowMap = new HashMap();
	}
	
	
	public TreeModel getTreeModel()
	{
		return myTreeModel;
	}
	
	public void setTreeModel (TreeModel tmodel)
	{
		myTreeModel = tmodel;
	}
	
	
	public TableModel getTableModel()
	{
		return myTableModel;
	}
	
	public void setTableModel (TableModel tableModel)
	{
		myTableModel = tableModel;
	}
	
	
	public Map getNodeToRowMap()
	{
		return myNodeToRowMap;
	}
	
	public void setNodeToRowMap(Map m)
	{
		myNodeToRowMap = m;
	}
	
	public int nodeToRow (Object node)
	{
		Integer i = (Integer) myNodeToRowMap.get(node);
		if (null == i)
			return -1;
		else
			return i.intValue();
	}
	
	
	public void mapNodeToRow (TreeNode node, int row)
	{
		Integer i = new Integer(row);
		getNodeToRowMap().put(node, i);
	}
	
	public void unmapNodeToRow (TreeNode node)
	{
		getNodeToRowMap().remove(node);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#getColumnCount()
	 */
	public int getColumnCount() 
	{
		return getTableModel().getColumnCount();
	}

	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) 
	{
		return getTableModel().getColumnName(column);
	}

	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#getColumnClass(int)
	 */
	public Class getColumnClass(int column) 
	{
		if (0 == column)
			return TreeTableModel.class;
		else
			return getTableModel().getColumnClass(column);
	}

	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#getValueAt(java.lang.Object, int)
	 */
	public Object getValueAt(Object node, int column) 
	{
		int row = nodeToRow(node);
		return getTableModel().getValueAt(row, column);
	}

	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#isCellEditable(java.lang.Object, int)
	 */
	public boolean isCellEditable(Object node, int column) 
	{
		int row = nodeToRow(node);
		return getTableModel().isCellEditable(row, column);
	}

	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
	 */
	public void setValueAt(Object aValue, Object node, int column) 
	{
		int row = nodeToRow(node);
		getTableModel().setValueAt(aValue, row, column);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() 
	{
		return getTreeModel().getRoot();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index) 
	{
		return getTreeModel().getChild(parent, index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object node) 
	{
		return getTreeModel().getChildCount(node);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object node) 
	{
		return getTreeModel().isLeaf(node);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
	 */
	public void valueForPathChanged(TreePath path, Object newValue) 
	{
		getTreeModel().valueForPathChanged(path, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) 
	{
		return getTreeModel().getIndexOfChild(parent, child);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener listener) 
	{
		getTreeModel().addTreeModelListener(listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener listener) 
	{
		getTreeModel().removeTreeModelListener(listener);
	}

}
