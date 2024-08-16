package com.lts.application.data.coll;

import com.lts.application.data.ApplicationData;

public class ADCEvent
{
	public enum EventType 
	{
		add,
		delete,
		update,
		all
	}
	
	public EventType event;
	public ApplicationData element;
}
