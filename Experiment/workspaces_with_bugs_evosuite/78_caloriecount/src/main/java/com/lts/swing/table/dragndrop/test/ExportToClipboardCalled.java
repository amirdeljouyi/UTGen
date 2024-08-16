package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.Clipboard;

public class ExportToClipboardCalled extends RecordingEvent
{
	protected Clipboard myClipboard;
	protected int myAction;
	
	public ExportToClipboardCalled (Clipboard clipboard, int action)
	{
		myClipboard = clipboard;
		myAction = action;
	}
	
	
	public void appendSubclassToString(StringBuffer sb)
	{
		sb.append(", ");
		sb.append(actionToString(myAction));
	}
}
