package com.lts.application.data.coll;

import com.lts.event.ListenerHelper;

public class ADLListenerHelper extends ListenerHelper
{
	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		ApplicationDataListListener listener = (ApplicationDataListListener) olistener;
		ApplicationDataListEvent event = (ApplicationDataListEvent) data;
		listener.eventOccurred(event);
	}
	
	
	public void fireAllChanged (ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.AllChanged;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, -1, list);
		fire(event);
	}
	
	
	public void fireCreate (int index, ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.Create;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, index, list);
		fire(event);
	}
	
	
	public void fireDelete (int index, ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.Delete;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, index, list);
		fire(event);
	}
	
	
	public void fireUpdate (int index, ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.Update;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, index, list);
		fire(event);
	}
}
