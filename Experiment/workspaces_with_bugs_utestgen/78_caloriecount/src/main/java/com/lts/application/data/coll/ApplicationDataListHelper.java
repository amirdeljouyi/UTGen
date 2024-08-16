package com.lts.application.data.coll;

import com.lts.application.data.ApplicationData;
import com.lts.event.ListenerHelper;

public class ApplicationDataListHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		ADCListener adcl = (ADCListener) listener;
		ADCEvent event = (ADCEvent) data;
		adcl.eventOccurred(event);
	}
	
	
	public void fireElementAdded (ApplicationData element)
	{
		ADCEvent event = new ADCEvent();
		event.element = element;
		event.event = ADCEvent.EventType.add;
		fire(event);
	}


	public void fireAllChanged()
	{
		ADCEvent event = new ADCEvent();
		event.event = ADCEvent.EventType.all;
		fire(event);
	}
	
	public void fireDelete (ApplicationData element)
	{
		ADCEvent event = new ADCEvent();
		event.element = element;
		event.event = ADCEvent.EventType.delete;
		fire(event);
	}
	
	public void fireUpdate (ApplicationData data)
	{
		ADCEvent event = new ADCEvent();
		event.event = ADCEvent.EventType.update;
		event.element = data;
		fire(event);
	}

}
