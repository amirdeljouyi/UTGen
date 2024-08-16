package com.lts.channel.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.event.ListenerHelper;

public class TableModelListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object o, int type, Object data)
	{
		TableModelListener listener = (TableModelListener) o;
		TableModelEvent event = (TableModelEvent) data;
		listener.tableChanged(event);
	}

	public void fireRowInserted(TableModel source, int row)
	{
		int col = TableModelEvent.ALL_COLUMNS;
		int eventType = TableModelEvent.INSERT;
		
		TableModelEvent event = new TableModelEvent(source, row, row, col, eventType);
		
		fire(event);
	}

	public void fireRowChanged(TableModel source, int row)
	{
		TableModelEvent event = new TableModelEvent(source,row);
		fire(event);
	}
	
	
	public void fireRowDeleted(TableModel source, int row)
	{
		int col = TableModelEvent.ALL_COLUMNS;
		int type = TableModelEvent.DELETE;
		TableModelEvent event = new TableModelEvent(source, row, row, col, type);
		
		fire(event);
	}
	
	public void fireAllChanged(TableModel source)
	{
		TableModelEvent event = new TableModelEvent(source);
		fire(event);
	}

	public void fireValueChanged(TableModel source, int rowIndex, int columnIndex)
	{
		TableModelEvent event;
		int type = TableModelEvent.UPDATE;
		event = new TableModelEvent(source, rowIndex, rowIndex, columnIndex, type);
		
		fire(event);
	}
}
