package com.lts.channel.table;

import java.util.Comparator;

import com.lts.swing.table.rowmodel.RowModel;

public class RowModelComparator implements Comparator
{
	private RowModel myRowModel;
	
	public RowModelComparator (RowModel rowModel)
	{
		myRowModel = rowModel;
	}
	
	
	public int compare(Object o1, Object o2)
	{
		return myRowModel.getComparator().compare(o1, o2);
	}
}
