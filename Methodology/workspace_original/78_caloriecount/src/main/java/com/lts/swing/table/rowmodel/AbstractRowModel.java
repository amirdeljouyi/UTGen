package com.lts.swing.table.rowmodel;

import java.util.Comparator;
import java.util.List;

abstract public class AbstractRowModel implements RowModel
{
	private Comparator myComparator;
	private Class[] myColumnClasses;
	private String[] myNames;
	
	private List myData;
	
	
	
	protected int findIndexOfRow(Object row)
	{
		if (null == myData)
		{
			String msg = "This class of RowModel, " 
				+ getClass().getName()
				+ ", does not support findIndexOfRow";
			
			throw new RuntimeException(msg);
		}
		
		for (int i = 0; i < myData.size(); i++)
		{
			if (myData.get(i).equals(row))
				return i;
		}
		
		return -1;
	}

	protected void initialize(Comparator comp, Class[] columns, String[] names, List data)
	{
		myComparator = comp;
		myColumnClasses = columns;
		myNames = names;
		myData = data;
	}
	
	protected void initialize(Comparator comp, Class[] columns, String[] names)
	{
		initialize(comp, columns, names, null);
	}

	public int compareRows(Object o1, Object o2)
	{
		return myComparator.compare(o1, o2);
	}

	public Class getColumnClass(int column)
	{
		return myColumnClasses[column];
	}

	public String[] getColumnNames()
	{
		return myNames;
	}

//	public static void resizeColumns(JTable table)
//	{
//		TableModel model = table.getModel();
//		if (null == model)
//			return;
//		
//		if (!(model instanceof RowModelTableModelAdaptor))
//			return;
//		
//		RowModelTableModelAdaptor rowTableModel = (RowModelTableModelAdaptor) model;
//		RowModel rowModel = rowTableModel.getRowModel();
//		int count = rowModel.getColumnNames().length;
//		for (int i = 0; i < count; i++)
//		{
//			
//		}
//	}
}
