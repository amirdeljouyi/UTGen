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
 * Created on Apr 20, 2004
 */
package com.lts.swing.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.lts.LTSException;

/**
 * @author cnh
 */
public class SimpleTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;


	protected static class SimpleColumnSpec 
	{
		public String name;
		public Class columnClass;
		public boolean editable;
		
		public SimpleColumnSpec()
		{}
		
		public SimpleColumnSpec (String theName, Class theColumnClass, boolean isEditable)
		{
			this.name = theName;
			this.columnClass = theColumnClass;
			this.editable = isEditable;
		}
	}
	
	
	protected SimpleColumnSpec[] toColumnSpec (Object[][] spec)
	{
		SimpleColumnSpec[] scs = new SimpleColumnSpec[spec.length];
		
		for (int i = 0; i < spec.length; i++)
		{
			SimpleColumnSpec cur = new SimpleColumnSpec();
			cur.name = (String) spec[i][0];
			cur.columnClass = (Class) spec[i][1];
			Boolean temp = (Boolean) spec[i][2];
			cur.editable = temp.booleanValue();
			
			scs[i] = cur;
		}
		
		return scs;
	}
	
	protected SimpleColumnSpec[] myColumns;
	
	public boolean isCellEditable (int row, int column)
	{
		return myColumns[column].editable;
	}
	
	public Class getColumnClass (int col)
	{
		return myColumns[col].columnClass;
	}
	
	public String getColumnName (int col)
	{
		return myColumns[col].name;
	}
	
	public int getColumnCount()
	{
		return myColumns.length;
	}

	
	protected Object[][] myData;
	
	public Object getValueAt (int row, int column)
	{
		return myData[row][column];
	}
	
	public void setValueAt (Object value, int row, int column)
	{
		myData[row][column] = value;
	}
	
	public int getRowCount()
	{
		return myData.length;
	}
	
	public void setData (Object[][] data)
	{
		myData = data;
		fireTableDataChanged();
	}


	public SimpleTableModel ()
	{}
	
	public SimpleTableModel (Object[][] spec, Object[][] data)
	{
		initialize(spec, data);
	}
	
	
	public SimpleTableModel (String[] columns, List data)
	{
		initialize(columns);
		setTableData(data);
	}
	
	
	public SimpleTableModel (String[] columns, Object[][] data)
	{
		initialize(columns);
		myData = data;
	}
	
	
	public SimpleTableModel (String[] columns, String[][] data)
	{
		initialize(columns, data);
	}

	public void setTableData (List rows)
	{
		int size = rows.size();
		Object[][] data = new Object[size][];
		
		for (int i = 0; i < size; i++)
		{
			Object[] row = (Object[]) rows.get(i);
			data[i] = row;
		}
	}
	
	
	public Object[] toRowData (Object o)
	{
		Object[] row;
		
		//
		// This is a "scalar" value like "hello world" or Integer(7): just
		// a single-element array containing the value.
		//
		if (!o.getClass().isArray())
		{
			row = new Object[] { o };
		}
		
		else
		{
			//
			// If it's an array of objects, we can just copy it over
			//
			if (!o.getClass().getComponentType().isPrimitive())
				row = (Object[]) o;
			//
			// Otherwise, convert the array of primitives to objects
			//
			else
			{
				//
				// TODO: implement this!
				//
				throw new RuntimeException("not implemented");
			}
		}
		
		return row;
	}
	
	
	public int getPopulatedCount()
	{
		int count = 0;
		
		if (null != myData)
		{
			while (count < myData.length && null != this.getValueAt(count, 0))
				count++;
			
			if (count > myData.length)
				count = myData.length;
		}
		
		return count;
	}
	
	
	public void addAll (List rows)
	{
		int size = rows.size();
		
		//
		// if we don't currently have a table, create one big enough to hold the
		// new list of data
		//
		if (null == myData)
			myData = new Object[size][];
		
		//
		// If the current table is not big enough to hold the old and new data,
		// then create a new table and copy over the old data.
		//
		Object[][] newData = myData;
		int count = this.getPopulatedCount();
		if (myData.length < (size + count))
		{
			newData = new Object[size + count][];
			for (int i = 0; i < count; i++)
			{
				newData[i] = myData[i]; 
			}
		}
		
		//
		// append the new data to the table
		//
		for (int i = 0; i < size; i++)
		{
			myData[i] = toRowData(rows.get(i));
		}
	}
	
	
	public void initialize (Object[][] spec, Object[][] data)
	{
		SimpleColumnSpec[] cols = toColumnSpec(spec);		
		initialize(cols, data);
	}

	public void initialize (SimpleColumnSpec[] columns, Object[][] data)
	{
		myColumns = columns;
		myData = data;
		
		if (null == myData)
			return;
		
		for (int i = 0; i < data.length; i++)
		{
			if (null == myData[i])
			{
				Object[] row = new Object[myColumns.length];
				myData[i] = row;
			}
		}
	}
	
	public void initialize (
			List names, 
			List data,
			Class[] columnClasses, 
			boolean[] editableColumns
	)
		throws LTSException
	{
		SimpleColumnSpec[] spec = new SimpleColumnSpec[names.size()];
		
		for (int i = 0; i < names.size(); i++)
		{
			SimpleColumnSpec scs = new SimpleColumnSpec();
			
			scs.name = (String) names.get(i);
			
			if (null == columnClasses)
				scs.columnClass = Object.class;
			else
				scs.columnClass = columnClasses[i];
			
			if (null == editableColumns)
				scs.editable = true;
			else
				scs.editable = editableColumns[i];
			
			spec[i] = scs;
		}
		
		myColumns = spec;
		
		setTableData(data);
	}
	
	
	public Object[][] buildData (int rows, int columns)
	{
		Object[][] data = new Object[rows][columns];	
		return data;
	}
	
	
	public void initialize (String[] names)
	{
		SimpleColumnSpec[] spec = buildColumnNames(names);
		myColumns = spec;
		myData = this.buildData(256, names.length);
	}

	public SimpleColumnSpec[] buildColumnNames(String[] names)
	{
		SimpleColumnSpec[] spec = new SimpleColumnSpec[names.length];
		
		for (int i = 0; i < names.length; i++)
		{
			SimpleColumnSpec scs = new SimpleColumnSpec();
			scs.name = names[i];
			scs.columnClass = Object.class;
			scs.editable = true;
			
			spec[i] = scs;
		}
		return spec;
	}
	
	
	public void initialize (String[] names, String[][] data)
	{
		SimpleColumnSpec[] spec = buildColumnNames(names);
		myColumns = spec;
		myData = data;
	}
	
	
	public SimpleTableModel (String[] names)
	{
		initialize(names);
	}
	
	public void setColumns (String[] names)
	{
		SimpleColumnSpec[] spec = buildColumnNames(names);
		myColumns = spec;
		fireTableStructureChanged();
	}
	
	public void setColumns (List list)
	{
		String[] names = new String[list.size()];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = list.get(i).toString();
		}
		
		setColumns(names);
	}
	
}
