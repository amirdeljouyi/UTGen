package com.lts.pest.data.event;

public class ElementHolderEvent
{
	final static public int EVENT_ELEMENT_ADDED = 0;
	final static public int EVENT_ELEMENT_REMOVED = 1;
	final static public int EVENT_ELEMENT_CHANGED = 2;
	final static public int EVENT_ALL_CHANGED = 3;
	final static public int EVENT_POST_DESERIALIZATION = 4;

	protected int myEventType;
	protected Object myData;
	protected Object myOriginalData;
	
	static public String eventToString(int event)
	{
		String s;
		
		switch (event)
		{
			case EVENT_ALL_CHANGED :
				s = "all changed";
				break;
				
			case EVENT_ELEMENT_ADDED :
				s = "element added";
				break;
				
			case EVENT_ELEMENT_CHANGED :
				s = "element changed";
				break;
				
			case EVENT_ELEMENT_REMOVED :
				s = "element removed";
				break;
				
			case EVENT_POST_DESERIALIZATION :
				s = "post deserialization";
				break;
			
			default :
				s = "unknown";
				break;
		}
		
		return s;
	}
	
	
	public ElementHolderEvent (int eventType, Object data, Object originalData)
	{
		initialize(eventType, data, originalData);
	}
	
	
	public ElementHolderEvent (int eventType)
	{
		initialize(eventType, null, null);
	}
	
	public ElementHolderEvent ()
	{
		initialize(-1, null, null);
	}
	
	
	protected void initialize (int eventType, Object data, Object originalData)
	{
		myEventType = eventType;
		myData = data;
		myOriginalData = originalData;
	}
	
	
	public Object getData()
	{
		return myData;
	}
	public void setData(Object data)
	{
		this.myData = data;
	}
	public int getEventType()
	{
		return myEventType;
	}
	public void setEventType(int eventType)
	{
		this.myEventType = eventType;
	}
	public Object getOriginalData()
	{
		return myOriginalData;
	}
	public void setOriginalData(Object originalData)
	{
		this.myOriginalData = originalData;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(eventToString(getEventType()));
		sb.append("{");
		
		if (null != getData())
			sb.append(getData());
		
		if (null != getOriginalData())
		{
			sb.append(", ");
			sb.append(getOriginalData());
		}
		
		sb.append("}");
		
		return sb.toString();
	}
}
