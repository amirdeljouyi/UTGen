package com.lts.swing.table.rowmodel.transfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.table.TableModel;

public class TableTransferable implements Transferable
{
	protected DataFlavor myFlavor;
	protected static DataFlavor ourFlavor;
	protected TableTransferData myData;
	
	public static final String SPEC_MIME_TYPE =
		DataFlavor.javaJVMLocalObjectMimeType
		+ ";class=javax.swing.table.TableModel";
	
	
	public TableTransferable (int[] selections, TableModel model)
	{
		initialize(selections, model);
	}
	
	
	public void initialize (int[] selections, TableModel model)
	{
		myData = new TableTransferData(selections, model);
	}
	
	public static DataFlavor getFlavor()
	{
		if (null == ourFlavor)
		{
			try
			{
				ourFlavor = new DataFlavor(SPEC_MIME_TYPE);
			}
			catch (RuntimeException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		
		return ourFlavor;
	}
	
	
	public Object getTransferData(DataFlavor flavor) 
		throws UnsupportedFlavorException, IOException
	{
		if (getFlavor() != flavor)
			throw new UnsupportedFlavorException(flavor);
		
		return myData;
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		DataFlavor[] flavors = { getFlavor() };
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return flavor == getFlavor();
	}

	public TableTransferData getData()
	{
		return myData;
	}
}
