package com.lts.util.collection;

import com.lts.util.notifyinglist.NotifyingCollection;

public class CollectionEvent
{
	public enum EventType
	{
		add,
		remove,
		update,
		allChanged
	}
	
	public NotifyingCollection myCollection;
	public Object myElement;
	public EventType myEvent;
}
