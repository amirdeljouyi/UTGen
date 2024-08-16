package com.lts.pest.data;

import com.lts.event.ListenerHelper;
import com.lts.pest.data.GatherMetaDataEvent.EventTypes;

public class GatherMetaDataListenerHelper extends ListenerHelper
{
	@Override
	public void notifyListener(Object l, int type, Object data)
	{
		GatherMetaDataEvent event = (GatherMetaDataEvent) data;
		GatherMetaDataListener listener = (GatherMetaDataListener) l;
		listener.gatherMetaDataEvent(event);
	}

	public void firePeriodChanged (long period)
	{
		GatherMetaDataEvent event = new GatherMetaDataEvent();
		event.setEventType(EventTypes.PeriodChanged);
		event.setPeriod(period);
		fire(event);
	}
	
	public void fireStartGathering (long period)
	{
		GatherMetaDataEvent event = new GatherMetaDataEvent();
		event.setEventType(EventTypes.StartGathering);
		event.setPeriod(period);
		fire(event);
	}
	
	public void fireStopGathering ()
	{
		GatherMetaDataEvent event = new GatherMetaDataEvent();
		event.setEventType(EventTypes.StopGathering);
		fire(event);
	}
}