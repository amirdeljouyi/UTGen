package com.lts.swing.table.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

/**
 * A transfer handler that allows data to be moved around in a JTable.
 * 
 * <P>
 * This class translates the various method calls against a transfer handler
 * into operations like cut, copy, paste, delete and move.  
 * </P>
 * 
 * @author cnh
 *
 */
@SuppressWarnings(value="serial")
abstract public class LTSTransferHandler extends TransferHandler
{
	protected enum TransferStates
	{
		//
		// The handler has cut some data via a drag and move and is waiting 
		// for the user to use the drop gesture
		//
		Marking,
		
		//
		// The handler has copied some data 
		Copying,
		Cutting,
		Normal,
	}
	
	
	static protected TransferableFactory ourFactory;
	protected SimplifiedTransferHandler myHandler;
	
	synchronized static protected void initializeClass()
	{
		if (null != ourFactory)
			return;
		
		ourFactory = new LTSTransferableFactory();
	}
	
	
	public LTSTransferHandler(SimplifiedTransferHandler transferHandler)
	{
		initialize(transferHandler);
	}
	
	protected void initialize(SimplifiedTransferHandler sth)
	{
		myHandler = sth;
	}
	
	
	public boolean canImport(JComponent c, DataFlavor[] flavors)
	{
		return ourFactory.canImport(c, flavors);
	}
	
	
	public int getSourceActions(JComponent c)
	{
		return COPY_OR_MOVE;
	}
	

	public boolean basicImportData(JComponent c, Transferable t) throws UnsupportedFlavorException, IOException
	{
//		SimpleTransferData std = (SimpleTransferData) t.getTransferData(SimpleTransferData.getFlavor());
//		int[] srcRows = std.getRows();
//		JTable table = (JTable) c;
//		int destRow = table.getSelectedRow(); 
//		TableModel model = table.getModel();
//		
//		std.setDestinationRow(table.getSelectedRow());
//		
//		Arrays.sort(std.getRows());
//		Object[] data = new Object[std.getRows().length];
//		for (int i = 0; i < data.length; i++)
//		{
//			// data[i] = moveRows(model, std.getRows()[i]);
//		}
//
//		//
//		// if the source rows occur before the destination row, then the 
//		// destination row needs to be adjusted, since after the move, there
//		// will be fewer rows than before
//		//
//		int count = 0;
//		for (int i = 0; i < srcRows.length; i++)
//		{
//			if (srcRows[i] < destRow)
//				count++;
//			else
//				break;
//		}
//		
//		int index = destRow - count;
//		std.setDestinationRow(index);
//		for (int i = 0; i < srcRows.length; i++)
//		{
//			insertRow(model, data[i], index + i);
//		}
		
		return true;
	}
	
	protected Transferable createTransferable(JComponent c)
	{
		if (!(c instanceof JTable))
		{
			return null;
		}
		
		JTable table = (JTable) c;
		int[] sel = table.getSelectedRows();
		SimpleTransferData data = new SimpleTransferData(table, sel);
		
		return data;
	}
	
	@Override
	protected void exportDone(JComponent comp, Transferable data, int action)
	{
		if (null == data)
		{
			return;
		}
		
		// SimpleTransferData std = (SimpleTransferData) data;
		JTable table = (JTable) comp;
		// int[] fromRows = table.getSelectedRows();
		table.clearSelection();
	}
	
	@Override
	public void exportAsDrag(JComponent comp, InputEvent event, int action)
	{
		
	}
	
}
