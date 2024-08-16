package com.lts.swing.table.rowmodel.transfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JTable;

import com.lts.swing.table.rowmodel.tablemodel.SimpleRowModelTableModel;

/**
 * A Transferable that can contain transfer row data.
 * 
 * <H2>NOTE</H2>
 * This class only works with data in the same VM as the JTable where it resides.
 * It does not support copying to text, etc.  Attempting to use flavors other than 
 * {@link RowTableDataFlavor#FLAVOR} will result in exceptions.
 * 
 * <H2>Description</H2>
 * This class only supports "live", unserialized objects.  The flavor for this 
 * is RowTableDataFlavor.
 * 
 * @author cnh
 */
public class RowTableTransferable implements Transferable
{
	private JTable myTable;
	private SimpleRowModelTableModel myModel;
	private int[] myRows;
	private Object[] myData;
	
	public RowTableTransferable (JTable table)
	{
		initialize(table);
	}
	
	private void initialize (JTable table)
	{
		myTable = table;
		myModel = (SimpleRowModelTableModel) table.getModel();
		myRows = table.getSelectedRows();
		Arrays.sort(myRows);
		myData = new Object[myRows.length];
		
		for (int row = 0; row < myRows.length; row++)
		{
			Object[] rowData = new Object[myModel.getColumnCount()];
			for (int col = 0; col < rowData.length; col++)
			{
				rowData[col] = myModel.getValueAt(row, col);
			}
			myData[row] = rowData;
		}
	}
	
	public JTable getTable()
	{
		return myTable;
	}
	
//	public RowModelTableModelAdaptor getModel()
//	{
//		return myModel;
//	}
	
	public int[] getRows()
	{
		return myRows;
	}
	
	public Object[] getData()
	{
		return myData;
	}
	
	
	static public DataFlavor DATA_FLAVOR = createFlavor();
	
	static private DataFlavor createFlavor()
	{
		try
		{
			String mime = DataFlavor.javaJVMLocalObjectMimeType 
				+ ";class=" + RowTableTransferable.class.getName();
			
			return new DataFlavor(mime);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public SimpleRowModelTableModel getSourceTableModel()
	{
		return myModel;
	}
	
	public int[] getSourceRows()
	{
		return myRows;
	}
	
	
//	public RowTableTransferable (JTable table)
//	{
//		initialize(table);
//	}
//	
//	private void initialize(JTable table)
//	{
//		myRows = table.getSelectedRows();
//		if (null != myRows && myRows.length > 0)
//		{
//			myModel = (SimpleRowModelTableModel) table.getModel();
//		}
//	}
	
	
//	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
//			IOException
//	{
//		if (!flavor.equals(RowTableDataFlavor.FLAVOR))
//		{
//			throw new UnsupportedFlavorException(flavor);
//		}
//		
//		return mySourceRows;
//	}

	
	final static private DataFlavor[] FLAVORS = { RowTableDataFlavor.FLAVOR };
	
	public DataFlavor[] getTransferDataFlavors()
	{
		return FLAVORS;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return RowTableDataFlavor.FLAVOR.equals(flavor);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
	{
		return this;
	}

}
