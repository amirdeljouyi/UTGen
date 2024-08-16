package com.lts.channel.list;

import com.lts.event.ListenerHelper;

public class ListChannelHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object client, int type, Object data)
	{
		ListChannelListener listener = (ListChannelListener) client;
		ListChannelEvent event = (ListChannelEvent) data;
		ListChannel list = event.getList();
		int oldIndex = event.getOldIndex();
		int newIndex = event.getNewIndex();
		
		switch (event.getEventType())
		{
			case ListChannelEvent.EVENT_ADD :
				listener.addElement(event, list, oldIndex);
				break;
				
			case ListChannelEvent.EVENT_ALL_CHANGED :
				listener.allChanged(event, list);
				break;
				
			case ListChannelEvent.EVENT_MOVE :
				listener.moveElement(event, list, oldIndex, newIndex);
				break;
				
			case ListChannelEvent.EVENT_REMOVE :
				listener.removeElement(event, list, oldIndex);
				break;
			
			default :
				throw new IllegalArgumentException();
		}
	}

}
