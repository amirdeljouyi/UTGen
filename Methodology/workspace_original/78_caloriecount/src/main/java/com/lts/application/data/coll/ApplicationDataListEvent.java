package com.lts.application.data.coll;

public class ApplicationDataListEvent
{
	public enum EventType
	{
		Create,
		Update,
		Delete,
		AllChanged
	}
	
	private EventType myEventType;
	private int myIndex;
	private ApplicationDataList mySource;
	
	public ApplicationDataListEvent (EventType type, int index, ApplicationDataList source)
	{
		myEventType = type;
		myIndex = index;
		mySource = source;
	}
	
	
	public EventType getEventType()
	{
		return myEventType;
	}
	
	public ApplicationDataList getSource()
	{
		return mySource;
	}
	
	public int getIndex()
	{
		return myIndex;
	}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(myEventType);
		
		if (myEventType != EventType.AllChanged)
		{
			sb.append('(');
			sb.append(myIndex);
			sb.append(')');
		}
		
		return sb.toString();
	}
}
