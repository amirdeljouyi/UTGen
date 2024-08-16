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
package com.lts.swing.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.lts.util.StringUtils;

/**
 * A TableModel that provides methods to sort the rows via a column.
 * 
 * <H2>Quickstart</H2>
 * <CODE>
 * <PRE>
 * TableModel model = &lt;create your model as usual&gt;
 * SortedTableView view = new SortedTableView(model);
 * &lt;use the view as you would the model&gt;
 * </PRE>
 * </CODE>
 * 
 * <H2>Description</H2>
 * This class is almost always used with {@link SortingJTable}, because its 
 * methods are of little use with a JTable that does not use them.
 * 
 * <P>
 * The model uses {@link StringUtils#compareStrings(String, String)} to compare
 * row/column values.  If you want to use some other comparison, you would 
 * override the 
 * @author cnh
 *
 */
@SuppressWarnings(value="serial")
public class SortedTableView extends AbstractTableModel
{
	protected TableModel tableModel;
	protected List view;
	protected boolean sortAscending;
	protected int sortingColumn;
	
	
	protected static class SortingNode
	{
		public String value;
		public int modelIndex;
		
		public SortingNode (String value, int index)
		{
			this.value = value;
			this.modelIndex = index;
		}
		
		public static final int compareNodes (Object o1, Object o2)
		{
			SortingNode node1 = (SortingNode) o1;
			SortingNode node2 = (SortingNode) o2;

			return StringUtils.compareStrings(node1.value, node2.value);
		}
		
		
		public static final Comparator ascendingComparator = new Comparator()
		{
			public int compare (Object o1, Object o2)
			{
				return compareNodes(o1, o2);
			}
		};
		
		public static final Comparator descendingComparator = new Comparator()
		{
			public int compare (Object o1, Object o2)
			{
				return -1 * compareNodes(o1, o2);
			}
		};
	}
	
	
	public SortedTableView (TableModel model)
	{
		initialize(model);
	}
	
	
	private List buildView (TableModel model, int sortingColumn, boolean sortAscending)
	{
		List l = new ArrayList(model.getRowCount());
		
		for (int row = 0; row < model.getRowCount(); row++)
		{
			Object o = model.getValueAt(row, sortingColumn);
			if (null == o)
				continue;
			
			if (!(o instanceof String))
				o = o.toString();
			
			String s = (String) o;
			SortingNode node = new SortingNode(s, row);
			l.add(node);
		}
		
		Comparator comp = getComparator(sortingColumn);
		
		if (!sortAscending)
			comp = new InverseComparator(comp);
		
		Collections.sort(l, comp);
		return l;
	}
	
	
	protected static class InverseComparator implements Comparator
	{
		protected Comparator myComparator;
		
		public InverseComparator (Comparator comp)
		{
			myComparator = comp;
		}
		
		
		public int compare(Object o1, Object o2)
		{
			return -1 * myComparator.compare(o1, o2);
		}
	}
	
	
	public Comparator getComparator(int column)
	{
		return SortingNode.ascendingComparator;
	}
	
	
	private void initialize (TableModel model)
	{
		tableModel = model;
		sortAscending = true;
		sortingColumn = 0;
		view = buildView (model, 0, true);
	}
	
	public int getColumnCount()
	{
		return tableModel.getColumnCount();
	}

	public int getRowCount()
	{
		return tableModel.getRowCount();
	}

	public Object getValueAt(int row, int column)
	{
		SortingNode node = (SortingNode) this.view.get(row);
		return this.tableModel.getValueAt(node.modelIndex, column);
	}
	
	
	public void setValueAt (Object value, int row, int column)
	{
		this.tableModel.setValueAt(value, row, column);
	}
	
	
	private void resort ()
	{
		this.view = buildView (
			this.tableModel,
			this.sortingColumn,
			this.sortAscending
		);
		
		this.fireTableStructureChanged();
	}
	
	public void setSortingColumn (int column)
	{
		this.sortingColumn = column;
		resort();
	}
	
	public int getSortingColumn ()
	{
		return this.sortingColumn;
	}
	
	
	public boolean getSortAscending()
	{
		return this.sortAscending;
	}
	
	public void setSortAscending (boolean sortAscending)
	{
		this.sortAscending = sortAscending;
		resort();
	}
	
	
	public String getColumnName (int column)
	{
		return this.tableModel.getColumnName(column);
	}
	
	
	public boolean isCellEditable (int row, int column)
	{
		SortingNode node = (SortingNode) this.view.get(row);
		return this.tableModel.isCellEditable(node.modelIndex, column);
	}
}
