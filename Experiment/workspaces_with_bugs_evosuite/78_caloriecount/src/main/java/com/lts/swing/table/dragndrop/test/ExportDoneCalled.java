package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.Transferable;


public class ExportDoneCalled extends RecordingEvent
{
	protected Transferable myTransferable;
	protected int myAction;
	
	public ExportDoneCalled (Transferable data, int action)
	{
		initialize();
		myTransferable = data;
		myAction = action;
	}
	
	public void appendSubclassToString(StringBuffer sb)
	{
		sb.append(", ");
		sb.append(actionToString(myAction));
	}
}
