package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.DataFlavor;

public class GetTransferDataFlavorsCalled extends RecordingEvent
{
	protected DataFlavor[] myFlavors;
	
	public GetTransferDataFlavorsCalled(DataFlavor[] flavors)
	{
		myFlavors = flavors;
	}
	
	
	public void appendSubclassToString(StringBuffer sb)
	{
		sb.append(", ");
		flavorsToString(myFlavors, sb);
	}

}
