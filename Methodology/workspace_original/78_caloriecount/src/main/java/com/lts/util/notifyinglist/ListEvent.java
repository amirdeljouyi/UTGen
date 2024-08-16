package com.lts.util.notifyinglist;

public class ListEvent
{
	public enum EventType
	{
		Insert,
		Update,
		Delete,
		AllChanged
	}
	
	public int index;
	public EventType eventType;
	public Object element;
	
	protected void initialize(EventType eventType, int eventIndex, Object element)
	{
		this.index = eventIndex;
		this.eventType = eventType;
		this.element = element;
	}
	
	
	public ListEvent (EventType etype)
	{
		initialize(etype, -1, null);
	}
	
	
	public ListEvent (EventType eventType, int index, Object element)
	{
		initialize(eventType, index, element);
	}
}
