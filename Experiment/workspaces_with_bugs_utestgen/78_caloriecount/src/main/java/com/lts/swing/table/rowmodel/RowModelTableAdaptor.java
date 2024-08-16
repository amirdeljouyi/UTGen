package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelListener;

import com.lts.swing.table.TableModelHelper;

abstract public class RowModelTableAdaptor implements ModifiableRowModelTable
{
	abstract public void insert(int index, Object data);
	abstract public Object getRow(int index);
	abstract public void update(int index, Object data);
	abstract public void remove(int index);
	abstract public int getRowCount();
	
	
	protected RowModel myRowModel;
	protected TableModelHelper myHelper;
	
	protected void initialize(RowModel rowModel)
	{
		myHelper = new TableModelHelper(this);
		myRowModel = rowModel;
	}
	
	public Object getValueAt(int rowIndex, int colIndex)
	{
		Object row = getRow(rowIndex);
		return myRowModel.getValueAt(colIndex, row);
	}
	
	public void setValueAt (Object value, int rowIndex, int colIndex)
	{
		Object row = getRow(rowIndex);
		myRowModel.setValueAt(rowIndex, row, colIndex, value);
	}
	
	public void append(Object rowData)
	{
		int index = getRowCount();
		insert(index, rowData);
	}

	public void remove(int[] indicies)
	{
		for (int i = 0; i < indicies.length; i++)
		{
			remove(indicies[i]);
		}
	}
	
	public Class getColumnClass(int columnIndex)
	{
		return myRowModel.getColumnClass(columnIndex);
	}

	public int getColumnCount()
	{
		return myRowModel.getColumnCount();
	}

	public String getColumnName(int columnIndex)
	{
		return myRowModel.getColumnName(columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return myRowModel.isColumnEditable(columnIndex);
	}

	public void addTableModelListener(TableModelListener listener)
	{
		myHelper.addListener(listener);
	}
	
	public void removeTableModelListener(TableModelListener listener)
	{
		myHelper.removeListener(listener);
	}
}
