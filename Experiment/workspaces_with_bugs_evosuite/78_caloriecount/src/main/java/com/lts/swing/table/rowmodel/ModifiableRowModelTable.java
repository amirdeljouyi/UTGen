package com.lts.swing.table.rowmodel;

import com.lts.swing.table.rowmodel.tablemodel.RowModelTableModel;

public interface ModifiableRowModelTable extends RowModelTableModel
{
	public void insert(int index, Object data);
	public void update(int index, Object data);	
	public void remove(int index);
}
