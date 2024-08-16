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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * A DefaultTreeModel that can display column data for the various nodes in 
 * the tree.
 * 
 * <P/>
 * This is an abstract class.  To be instantiable, the following methods must
 * be defined in a subclass:
 * <UL>
 * <LI/>getColumnCount
 * <LI/>getColumnName
 * <LI/>getValueAt
 * <LI/>isCellEditable
 * <LI/>setValueAt
 * </UL>
 * 
 * <P/>
 * This approach assumes that, for a particular tree node, the object can 
 * display column information about that node.  For example, if you have a 
 * tree that shows departments in a company, with leaf nodes being the 
 * employees, then the model might know that the first column is always 
 * the last name of the employee, the next column is the first name, etc.
 * 
 * <P/>
 * The class assumes that the class for all columns is string.  If other types
 * are desired, then the getColumnClass method should be overridden.
 * 
 * @author cnh
 */
public abstract class SimpleTreeTableModel
	extends DefaultTreeModel
	implements TreeTableModel
{
	public abstract String[] getColumnNames();
	
	public SimpleTreeTableModel ()
	{
		super(new DefaultMutableTreeNode());
	}
	
	public SimpleTreeTableModel (DefaultMutableTreeNode root)
	{
		super(root);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lts.swing.treetable.TreeTableModel#getColumnClass(int)
	 */
	public Class getColumnClass(int column)
	{
		if (0 == column)
			return TreeTableModel.class;
		else
			return String.class;
	}
	
	
	public int getColumnCount()
	{
		return getColumnNames().length;
	}
	
	public String getColumnName(int index)
	{
		return getColumnNames()[index];
	}
}
