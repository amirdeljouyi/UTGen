package com.lts.event;

abstract public class SimpleListenerHelper extends ListenerHelper
{
	abstract public void notifyListener(Object listener, Object data);
	
	@Override
	public void notifyListener(Object l, int type, Object data)
	{
		notifyListener(l, data);
	}

}
