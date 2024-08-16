package com.lts.swing.table.dragndrop;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * A very simple Transfer handler that allows clients to move rows around in 
 * the same table.
 * 
 * <P>
 * On an implementation note, one aspect of a move transfer handler that makes it
 * different from other transfer handlers is that it defers importing data until 
 * the {@link TransferHandler#exportDone(JComponent, Transferable, int)}
 * </P>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
abstract public class MoveTransferHandler // extends LTSTransferHandler
{
//	abstract protected void move(JTable table, int[] rows, int selection);
//
//	@Override
//	protected Object copyRow(TableModel model, int index)
//	{
//		throw new InvalidOperationException();
//	}
//
//	@Override
//	protected void deleteRow(TableModel model, int index)
//	{
//		throw new InvalidOperationException();
//	}
//
//	@Override
//	protected void insertRow(TableModel model, Object data, int index)
//	{
//		throw new InvalidOperationException();
//	}
//
//	protected Transferable myTransferable;
//	
//	@Override
//	public boolean importData(JComponent c, Transferable t)
//	{
//		return false;
//	}
//
//	@Override
//	protected void exportDone(JComponent comp, Transferable data, int action)
//	{
//		if (MOVE != action)
//			return;
//		
//		JTable table = (JTable) comp;
//		int selection = table.getSelectedRow();
//		if (-1 == selection)
//			return;
//		
//		SimpleTransferData std = (SimpleTransferData) data;
//		move(table, std.getRows(), selection); 
//	}

}
