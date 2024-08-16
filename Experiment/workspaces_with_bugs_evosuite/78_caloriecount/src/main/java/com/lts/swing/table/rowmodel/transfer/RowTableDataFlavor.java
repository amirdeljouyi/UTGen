package com.lts.swing.table.rowmodel.transfer;

import java.awt.datatransfer.DataFlavor;

@SuppressWarnings("serial")
public class RowTableDataFlavor extends DataFlavor
{
	final static public RowTableDataFlavor FLAVOR =
		new RowTableDataFlavor();
	
	private RowTableDataFlavor()
	{
		super(Object.class, "application/x-java-object");
	}
}
