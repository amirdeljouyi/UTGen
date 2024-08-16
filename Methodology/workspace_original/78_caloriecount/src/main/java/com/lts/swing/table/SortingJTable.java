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
 * Created on Apr 24, 2004
 */
package com.lts.swing.table;

import javax.swing.JTable;

/**
 * A JTable that allows the user to sort the rows by clicking on a column.
 * 
 * <H2>Quickstart</H2>
 * <code>
 * <PRE>
 * TableModel model = &lt;the table model you were going to use&gt;
 * SortedTableView view = new SortedTableView(model);
 * SortingJTable table = new SortingJTable(view);
 * ...
 * </PRE>
 * </CODE>
 * 
 * 
 * 
 * @author cnh
 */
@SuppressWarnings(value="serial")
public class SortingJTable extends JTable
{
//	public class MouseHandler extends MouseAdapter 
//	{
//		public void mouseClicked(MouseEvent e) 
//		{
//			JTableHeader h = (JTableHeader) e.getSource();
//			TableColumnModel columnModel = h.getColumnModel();
//			int viewColumn = columnModel.getColumnIndexAtX(e.getX());
//			int currentColumn = getView().getSortingColumn();
//			if (viewColumn != currentColumn)
//				getView().setSortingColumn(viewColumn);
//			else
//			{
//				boolean ascending = getView().getSortAscending();
//				getView().setSortAscending(ascending);
//			}
//		}
//	}
//	
//	protected JTableHeader tableHeader;
//	protected StringTableModel view;
//	
//	public SortingJTable (SortedTableView view)
//	{
//		super(view);
//		initialize(view);
//	}
//	
//	public SortingJTable ()
//	{
//		super();
//	}
//	
//	public void setModel (SortedTableView view)
//	{
//		super.setModel(view);
//		initialize(view);
//	}
//	
//	public void initialize (SortedTableView view)
//	{
//		this.tableHeader = getTableHeader();
//		this.tableHeader.addMouseListener(new MouseHandler());
//	}
}
