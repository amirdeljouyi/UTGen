package com.lts.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		ActionEvent event = (ActionEvent) data;
		ActionListener alistener = (ActionListener) listener;
		alistener.actionPerformed(event);
	}
	
	public void fireAction(Object source)
	{
		ActionEvent event = new ActionEvent(source,-1, null);
		fire(event);
	}
}
