package com.lts.swing.table.rowmodel.transfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.TableModel;

import com.lts.swing.table.rowmodel.tablemodel.SimpleRowModelTableModel;

@SuppressWarnings(value="serial")
abstract public class TableTransferHandler extends TransferHandler
{
	abstract protected void moveRows (TableModel model, int destRow, int[] srcRows)
		throws Exception;
	
	public boolean canImport(JComponent c, DataFlavor[] flavors)
	{
		for (int i = 0; i < flavors.length; i++)
		{
			if (TableTransferable.getFlavor().equals(flavors[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public int getSourceActions(JComponent c)
	{
		return COPY_OR_MOVE;
	}

	public boolean importData(JComponent c, Transferable t)
	{
		boolean result = false;
		try
		{
			Object o = t.getTransferData(TableTransferable.getFlavor());
			
			if (c instanceof JTable)
			{
				JTable table = (JTable) c;
				int dest = table.getSelectedRow();
				TableModel model = table.getModel();
				TableTransferData data = (TableTransferData) o;
				
				moveRows (model, dest, data.selectedRows);

				result = true;
			}
		}
		catch (Exception e)
		{}

		return result;
	}
	
	protected Transferable createTransferable(JComponent c)
	{
		if (!(c instanceof JTable))
		{
			return null;
		}
		
		JTable table = (JTable) c;
		int[] sel = table.getSelectedRows();
		TableModel model = table.getModel();
		TableTransferable data = new TableTransferable(sel, model);
		
		return data;
	}

	
	public void exportAsDrag(JComponent comp, InputEvent e, int action)
	{
		super.exportAsDrag(comp, e, action);
	}
	
	
	protected void exportDone(JComponent c, Transferable data, int action)
	{
		super.exportDone(c, data, action);
		
		if (null == data)
			return;
		
		if (!(c instanceof JTable))
			return;
		
		JTable table = (JTable) c;
		int dest = table.getSelectedRow();
		if (-1 == dest)
			return;
		
		if (MOVE != action)
			return;
		
		TableModel model = table.getModel();
		if (!(model instanceof SimpleRowModelTableModel))
			return;
		
		TableTransferData ttd = null;
		try
		{
			ttd = (TableTransferData) data.getTransferData(TableTransferable.getFlavor());
		}
		catch (Exception e)
		{}
		
		if (null == ttd)
			return;
		
		SimpleRowModelTableModel rtm = (SimpleRowModelTableModel) model;
		
		
		try
		{
			moveRows(rtm, dest, ttd.selectedRows);
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
	
	
//	protected void moveRows (RowTableModel model, int dest, int sources)
//	{
//		List<Integer> list = toList(ttd.selectedRows);
//		Collections.sort(list);
//		
//		int first = list.get(0);
//		if (first > dest)
//		{
//			dest = dest - list.size();
//		}
//		
//		
//		for (Integer index : list)
//		{
//			
//		}
//		rtm.moveRows(ttd.selectedRows, dest);
//			
//		// rtm.cleanup(myTransferData);
//		
//	}
	
	
	protected List<Integer> toList (int[] intArray)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < intArray.length; i++)
		{
			list.add(intArray[i]);
		}
		
		return list;
	}


	
	
//	protected void cleanup(JComponent c, boolean remove)
//	{
//		System.out.println("cleanup: remove = " + remove);
//	}

}
