package com.lts.swing.table.dragndrop.test;

import java.awt.event.InputEvent;

public class ExportAsDragCalled extends RecordingEvent
{
	protected InputEvent myEvent;
	private int myAction;
	
	public ExportAsDragCalled(InputEvent event, int action)
	{
		initialize();
		myEvent = event;
		myAction = action;
	}
	
	public void appendSubclassToString(StringBuffer sb)
	{
		sb.append(", ");
		sb.append(actionToString(myAction));
	}

}
