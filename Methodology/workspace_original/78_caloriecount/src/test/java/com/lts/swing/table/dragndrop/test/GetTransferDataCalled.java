package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.DataFlavor;


public class GetTransferDataCalled extends RecordingEvent
{
	protected DataFlavor myFlavor;
	
	public GetTransferDataCalled(DataFlavor flavor)
	{
		myFlavor = flavor;
	}

	public void appendSubclassToString(StringBuffer sb)
	{
		sb.append(", ");
		sb.append(flavorToString(myFlavor));
	}
}
