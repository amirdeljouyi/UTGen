package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;
/**
 * A TableModel that uses a RowModel as its data.
 * 
 * <H2>Quick Start</H2>
 * <CODE>
 * <PRE>
 * public class SomeRowTableModel extends TableModel
 * {
 *     public SomeRowTableModel (RowModel rowModel)
 *     {
 *         myRowModel = rowModel;
 *     }
 * }
 * </PRE>
 * </CODE>
 * 
 * <P>
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class RowModelTableModel implements TableModel
{
	abstract public Object getRowData(int rowIndex);
	
	protected RowModel myRowModel;
	protected TableModelHelper myHelper;
	
	public RowModelTableModel()
	{
		initialize();
	}
	
	public void initialize()
	{
		myHelper = new TableModelHelper(this);
	}
	
	
	public RowModel getRowModel()
	{
		return myRowModel;
	}
	
	public void setRowModel (RowModel rowModel)
	{
		myRowModel = rowModel;
	}
	
	public TableModelHelper getHelper()
	{
		return myHelper;
	}
	
	public void setHelper (TableModelHelper helper)
	{
		myHelper = helper;
		if (null == myHelper)
		{
			myHelper = new TableModelHelper(this);
		}
	}
	
	public void addTableModelListener(TableModelListener l)
	{
		myHelper.addListener(l);
	}

	public Class<?> getColumnClass(int columnIndex)
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

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object row = getRowData(rowIndex);
		return myRowModel.getValueAt(columnIndex, row);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return myRowModel.isColumnEditable(columnIndex);
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myHelper.removeListener(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		Object rowData = getRowData(rowIndex);
		myRowModel.setValueAt(rowIndex, rowData, columnIndex, value);
	}
	
}
