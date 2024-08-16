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
 * Copyright (c) Long Term Software, LLC. Created on Jun 15, 2003
 */
package com.lts.swing.table;

import javax.swing.table.DefaultTableModel;

/**
 * @author cnh
 */
public class StringTableModel 
	extends DefaultTableModel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public StringTableModel()
	{
		
	}
	
	
	public StringTableModel (String[] columnNames)
	{
		super(columnNames, 0);
	}
	
	
	public StringTableModel (String[] columnNames, int rows)
	{
		super(columnNames, rows);
	}
	
	
	public StringTableModel (String[][] data, String[] columnNames)
	{
		super(data, columnNames);
	}

	
	public void setValueAt (Object o, int row, int col)
	{
		super.setValueAt(o,row,col);
		
		int rowCount = getRowCount();
		if ((1 + rowCount) >= row)
		{
			String[] rowData = new String[getColumnCount()];
			addRow(rowData);
		}
	}
}
