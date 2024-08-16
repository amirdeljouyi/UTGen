package com.lts.swing.table.rowmodel.tablemodel;

import com.lts.event.ListenerHelper;

public class RowModelTableModelListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		RowModelTableModelEvent event = (RowModelTableModelEvent) data;
		RowModelTableModelListener l = (RowModelTableModelListener) listener;
		l.eventOccurred(event);
	}
	
	
	public void rowAdded (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.rowAdded;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}
	
	
	public void rowChanged (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.rowChanged;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}

	public void rowDelete (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.rowRemoved;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}

	public void allChanged (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.allChanged;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}
}
