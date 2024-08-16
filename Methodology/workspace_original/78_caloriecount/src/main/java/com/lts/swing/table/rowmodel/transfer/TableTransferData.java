package com.lts.swing.table.rowmodel.transfer;

import javax.swing.table.TableModel;

public class TableTransferData
{
	public int[] selectedRows;
	public TableModel model;
	
	public TableTransferData (int[] theSelected, TableModel theModel)
	{
		selectedRows = theSelected;
		model = theModel;
	}
}
