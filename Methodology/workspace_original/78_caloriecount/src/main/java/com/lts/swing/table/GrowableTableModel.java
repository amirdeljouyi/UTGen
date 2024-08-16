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
 * Copyright (c) Long Term Software, LLC. All rights reserved.
 */
package com.lts.swing.table;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * A DefaultTableModel that always has a blank row.
 * 
 * <H2>Abstract Class</H2>
 * Subclasses must define the following methods to be instantiateable:
 * <UL>
 * <LI>createEmptyRow - create a blank row of data.
 * </UL>
 * 
 * <P>
 * Whenever something in the blank row is set in such a way to make the row
 * non-empty, a new row is created and added to the table.
 * 
 * @author cnh
 */
public abstract class GrowableTableModel 
	extends DefaultTableModel 
{
	public abstract Object[] createEmptyRow();
	
	public GrowableTableModel (String[] columnNames, int rows)
	{
		super(columnNames, rows);
	}
	
	public GrowableTableModel (String[] columnNames)
	{
		super (columnNames, 1);
	}
	
	public GrowableTableModel (String[] columnNames, List rows)
	{
		super(columnNames, 0);
		addRows(rows);
		addRow(createEmptyRow());
	}	
	

	public void setValueAt (Object o, int row, int col)
	{
		super.setValueAt(o,row,col);
		
		int rowCount = getRowCount();
		if (row >= (rowCount - 1))
		{
			addRow(createEmptyRow());
		}
	}
	
	public void addRows(List rows)
	{
		for (Iterator i = rows.iterator(); i.hasNext();)
		{
			Object[] row = (Object[]) i.next();
			addRow(row);
		}
	}
}
