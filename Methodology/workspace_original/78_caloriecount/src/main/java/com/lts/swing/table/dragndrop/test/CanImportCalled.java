package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.DataFlavor;

public class CanImportCalled extends RecordingEvent
{
	protected DataFlavor[] myFlavors;
	
	public CanImportCalled (DataFlavor[] flavors)
	{
		myFlavors = flavors;
	}
	
	public void appendSubclassToString(StringBuffer sb)
	{
		sb.append(", ");
		flavorsToString(myFlavors, sb);
	}
}
