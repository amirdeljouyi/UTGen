package com.lts.swing.table.rowmodel.transfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

abstract public class TransferableAdapter implements Transferable
{
	protected abstract String[] getMimeTypes();
	
	protected Object myData;
	protected DataFlavor[] myFlavors;
	
	
	public TransferableAdapter (Object data)
	{
		initialize(data);
	}
	
	protected void initialize (Object data)
	{
		try
		{
			String[] mime = getMimeTypes();
			for (int i = 0; i < mime.length; i++)
			{
				myFlavors[i] = new DataFlavor(mime[i]);
			}
		}
		catch (ClassNotFoundException e)
		{
		}
	}
	
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
			IOException
	{
		return myData;
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		return myFlavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		for (int i = 0; i < myFlavors.length; i++)
			if (myFlavors[i] == flavor)
				return true;
		
		return false;
	}

}
