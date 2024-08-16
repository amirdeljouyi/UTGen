package com.lts.util.notifyinglist;

import com.lts.event.ListenerHelper;
import com.lts.util.notifyinglist.ListEvent.EventType;

public class NotifyingListHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		NotifyingListListener listener = (NotifyingListListener) olistener;
		ListEvent event = (ListEvent) data;
		listener.listEvent(event);
	}

	public void fireInsert (int index, Object element)
	{
		ListEvent event = new ListEvent(EventType.Insert, index, element);
		fire(event);
	}
	
	public void fireUpdate (int index, Object element)
	{
		ListEvent event = new ListEvent(EventType.Update, index, element);
		fire(event);
	}
	
	public void fireDelete (int index, Object element)
	{
		ListEvent event = new ListEvent(EventType.Delete, index, element);
		fire(event);
	}
	
	public void fireAllChanged()
	{
		ListEvent event = new ListEvent(EventType.AllChanged);
		fire(event);
	}
}
