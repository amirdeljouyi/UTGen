package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;

public class BridgeAdaptor 
	extends TableModelListenerAdaptor 
	implements TableModel
{
	protected TableModelHelper myTableModelHelper;
	protected TableModel myTableModel;

	public BridgeAdaptor(TableModel model)
	{
		initialize(model);
	}
	
	protected void initialize(TableModel tableModel)
	{
		myTableModel = tableModel;
		myTableModel.addTableModelListener(this);
		myTableModelHelper = new TableModelHelper(this);
	}

	public void addTableModelListener(TableModelListener l)
	{
		myTableModelHelper.addListener(l);
	}

	public Class<?> getColumnClass(int columnIndex)
	{
		return myTableModel.getColumnClass(columnIndex);
	}

	public int getColumnCount()
	{
		return myTableModel.getColumnCount();
	}

	public String getColumnName(int columnIndex)
	{
		return myTableModel.getColumnName(columnIndex);
	}

	public int getRowCount()
	{
		return myTableModel.getRowCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return myTableModel.getValueAt(rowIndex, columnIndex);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return myTableModel.isCellEditable(rowIndex, columnIndex);
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myTableModel.removeTableModelListener(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		myTableModel.setValueAt(value, rowIndex, columnIndex);
	}
}
