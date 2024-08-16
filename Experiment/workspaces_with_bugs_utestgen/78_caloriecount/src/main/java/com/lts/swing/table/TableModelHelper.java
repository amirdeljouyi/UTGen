package com.lts.swing.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.event.ListenerHelper;

public class TableModelHelper extends ListenerHelper
{
	protected TableModel mySource;
	
	public TableModelHelper(TableModel source)
	{
		mySource = source;
	}
	
	public void setSource(TableModel source)
	{
		mySource = source;
	}
	
	
	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		TableModelListener listener = (TableModelListener) olistener;
		TableModelEvent event = (TableModelEvent) data;
		listener.tableChanged(event);
	}

	public void fireRowAdded(int index)
	{
		TableModelEvent event = new TableModelEvent(mySource, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		fire(event);
	}
	
	public void fireRowRemoved(int index)
	{
		TableModelEvent event = new TableModelEvent(mySource, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		fire(event);
	}
	
	
	public void fireRowUpdated(int index)
	{
		TableModelEvent event = new TableModelEvent(mySource, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
		fire(event);
	}


	public void fireTableChanged()
	{
		TableModelEvent event = new TableModelEvent(mySource);
		fire(event);
	}

	public void fireCellChanged(int rowIndex, int column)
	{
		TableModelEvent event = new TableModelEvent(mySource, rowIndex, rowIndex, column);
		fire(event);
	}

}
