package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class DummyTransferable implements Transferable
{
	static protected DataFlavor ourJVMLocalObjectFlavor;
	static protected DataFlavor[] ourFlavors;
	
	static protected void checkConstants()
	{
		if (null == ourJVMLocalObjectFlavor || null == ourFlavors)
			initializeConstants();
	}
	
	static synchronized protected void initializeConstants()
	{
		if (null != ourJVMLocalObjectFlavor || null != ourFlavors)
			return;
		
		try
		{
			ourJVMLocalObjectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
			ourFlavors = new DataFlavor[] { ourJVMLocalObjectFlavor };
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
			IOException
	{
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return ourFlavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return true;
	}

}
