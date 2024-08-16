package com.lts.swing.table.rowmodel;

import com.lts.application.data.coll.ApplicationDataList;
import com.lts.swing.table.rowmodel.tablemodel.SimpleRowModelTableModel;

public class ApplicationDataListRowModel extends SimpleRowModelTableModel
{
	@SuppressWarnings("serial")
	protected class ADLListener 
	{
		
	}
	

	protected ApplicationDataList myList;
	
	public ApplicationDataListRowModel(ApplicationDataList list, RowModel rowModel)
	{
		initialize(list, rowModel);
	}
	
	public void initialize(ApplicationDataList list, RowModel rowModel)
	{
		myList = list;
		setRowModel(rowModel);
		super.initialize();
	}
	
	
	@Override
	public void deleteRow(int rowIndex)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRowCount()
	{
		return myList.getCount();
	}

	@Override
	public Object getRowData(int rowIndex)
	{
		return myList.get(rowIndex);
	}

	@Override
	public void insertRow(int rowIndex, Object rowData)
	{
		throw new UnsupportedOperationException();
	}

}
