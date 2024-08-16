package com.lts.util.collection;

import com.lts.event.ListenerHelper;

public class CollectionListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	public void fireElementAdded (Object element)
	{
		CollectionEvent event = new CollectionEvent();
		event.myElement = element;
		event.myEvent = CollectionEvent.EventType.add;
		fire(event);
	}
	
	public void fireElementRemoved (Object element)
	{
		CollectionEvent event = new CollectionEvent();
		event.myElement = element;
		event.myEvent = CollectionEvent.EventType.remove;
		fire(event);
	}
	
	public void fireElementChanged (Object element)
	{
		CollectionEvent event = new CollectionEvent();
		event.myElement = element;
		event.myEvent = CollectionEvent.EventType.update;
		fire(event);
	}

	public void fireAllChanged ()
	{
		CollectionEvent event = new CollectionEvent();
		event.myEvent = CollectionEvent.EventType.allChanged;
		fire(event);
	}
}
