package com.lts.caloriecount.ui.gatherwin;

import com.lts.caloriecount.data.food.Food;
import com.lts.event.ListenerHelper;

public class GatherListenerHelper extends ListenerHelper
{
	@Override
	public void notifyListener(Object o, int type, Object data)
	{
		GatherListener listener = (GatherListener) o;
		GatherEvent event = (GatherEvent) data;
		listener.gatherCompleted(event);
	}

	
	public void fireGather (long time, Food food)
	{
		GatherEvent event = new GatherEvent();
		event.time = time;
		event.food = food;
		
		fire(event);
	}
}
