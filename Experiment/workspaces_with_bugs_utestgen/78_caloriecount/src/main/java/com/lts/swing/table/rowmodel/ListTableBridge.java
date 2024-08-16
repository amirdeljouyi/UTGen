package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;
import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListListener;
/**
 * A bridge that translates events from NotifyingList to TableModel.
 * 
 * @author cnh
 */
public class ListTableBridge implements TableModel, NotifyingListListener
{
	protected NotifyingList myList;
	protected TableModelHelper myHelper;
	protected RowModel myRowModel;
	
	public ListTableBridge(NotifyingList list, RowModel rowModel)
	{
		initialize(list, rowModel);
	}
	
	protected void initialize (NotifyingList list, RowModel rowModel)
	{
		myList = list;
		myRowModel = rowModel;
		myList.addListener(this);
		myHelper = new TableModelHelper(this);
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

	public int getRowCount()
	{
		return myList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object data = getRowData(rowIndex);
		return myRowModel.getValueAt(columnIndex, data);
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
		Object data = getRowData(rowIndex);
		myRowModel.setValueAt(rowIndex, data, columnIndex, value);
	}

	public Object getRowData(int rowIndex)
	{
		return myList.get(rowIndex);
	}
	
	
	public void listEvent(ListEvent event)
	{
		switch(event.eventType)
		{
			case AllChanged :
				tableAllChanged();
				break;
				
			case Delete :
				tableRowDelete(event.index);
				break;
				
			case Insert :
				tableRowInsert(event.index);
				break;
				
			case Update :
				tableRowUpdate(event.index);
				break;
		}
	}

	protected void tableRowUpdate(int index)
	{
		myHelper.fireRowRemoved(index);
	}

	protected void tableRowInsert(int index)
	{
		myHelper.fireRowAdded(index);
	}

	protected void tableRowDelete(int index)
	{
		myHelper.fireRowRemoved(index);
	}

	protected void tableAllChanged()
	{
		myHelper.fireTableChanged();
	}
	
}
