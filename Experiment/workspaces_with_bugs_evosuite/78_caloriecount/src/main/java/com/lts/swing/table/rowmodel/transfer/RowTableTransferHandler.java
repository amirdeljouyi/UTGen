package com.lts.swing.table.rowmodel.transfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.TableModel;

import com.lts.swing.table.JTableUtils;
import com.lts.swing.table.rowmodel.tablemodel.SimpleRowModelTableModel;


/**
 * A TransferHandler specifically designed to work with SimpleRowModelTableModel.
 * 
 * <H3>Superclass Methods</H3>
 * The following methods are implemented by TransferHandler instead of this class:
 * <UL>
 * <LI/>exportAsDrag
 * <LI/>exportToClipboard
 * <LI/>getVisualRepresentation
 * </UL>
 * 
 * <P>
 * This class implements the following methods:
 * <UL>
 * <LI/>canImport
 * <LI/>correctComponentType
 * <LI/>getSourceActions
 * <LI/>createTransferable
 * <LI/>importData
 * </UL>
 * </P>
 * 
 * <P>
 * This class only supports moving rows from one SimpleRowModelTableModel to another SimpleRowModelTableModel
 * such that the class of the rows is the same.  
 * </P>
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class RowTableTransferHandler extends TransferHandler
{
	/**
	 * Can the destination component accept data of a particular type.
	 * 
	 * <P>
	 * This method returns true if:
	 * <UL>
	 * <LI>The destination is a JTable
	 * <LI>The model of the JTable is an instance of SimpleRowModelTableModel
	 * <LI>RowModel.getRowClass of this instance equals the corresponding value
	 * from the destination.
	 * </UL>
	 * </P>
	 * 
	 * <P>
	 * In other words, return true if the destination uses the same data for its
	 * rows that we do.
	 * </P>
	 */
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors)
	{
		return correctComponentType(comp);
	}
	
	private boolean correctComponentType (JComponent comp)
	{
		if (!(comp instanceof JTable))
			return true;
		
		JTable table = (JTable) comp;
		TableModel model = table.getModel();
		return model instanceof SimpleRowModelTableModel;
	}
	
	/**
	 * What actions does the specified component support --- this class only supports
	 * TransferHandler.MOVE.
	 * 
	 * <P>
	 * When the user makes a "drag gesture," this method is called on the source data to 
	 * determine what sorts of things can be performed.  In our case, the answer is that 
	 * we only support move. 
	 * </P>
	 * 
	 * @param comp The component that the user has tried to drag from.
	 * @return TransferHandler.MOVE
	 */
	public int getSourceActions(JComponent comp)
	{
		if (correctComponentType(comp))
			return TransferHandler.MOVE;
		else
			return TransferHandler.NONE;
	}
	
	
	/**
	 * Get the transferable for the component --- always returns RowTableTransferable.
	 * 
	 * <H2>Note</H2>
	 * The JComponent passed to this method is assumed to be an instance of JTable.
	 * If it is not, then a ClassCastException will be thrown.  The TableModel of the
	 * JTable must be an instance of SimpleRowModelTableModel to avoid a ClassCastException. 
	 * 
	 * 
	 * <H2>Description</H2>
	 * The method creates a transferable that contains the currently selected row 
	 * data in the designated JTable.
	 * 
	 * @param c The component from which to get the data.
	 * @return A Transferable that contains the data.
	 * @exception ClassCastException See description.
	 */
	protected Transferable createTransferable(JComponent c)
	{
		JTable table = (JTable) c;
		return new RowTableTransferable(table);
	}
	
	/**
	 * Causes a transfer to a component from a clipboard or a DND drop operation.
	 * 
	 * This is the heart of the TransferHandler class.  It tells the class to actually
	 * "drop" the data into the destination table.
	 * 
	 * @param comp The destination JTable.
	 * @param t The data to transfer.
	 */
	public boolean importData(JComponent comp, Transferable t)
	{
		if (!canImport(comp, t.getTransferDataFlavors()))
			return false;
		
        try
		{
        	DataFlavor flavor = RowTableTransferable.DATA_FLAVOR;
        	RowTableTransferable trans = (RowTableTransferable) t.getTransferData(flavor);
        	JTable table = (JTable) comp;
			performImport(table, trans);
			return true;
		}
        catch (RuntimeException e)
        {
        	e.printStackTrace();
        	return false;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return false;
        }
	}
	
	
	protected void exportDone(JComponent source, Transferable data, int action)
	{
		if (MOVE == action)
		{
			JTable table = (JTable) source;
			RowTableTransferable trans = (RowTableTransferable) data;
			cleanup(table, trans);
		}
	}
	
	
	private void cleanup (JTable table, RowTableTransferable trans)
	{
		
	}
	
	protected boolean sameTable (JTable table, RowTableTransferable trans)
	{
		return table.getModel() == trans.getSourceTableModel();
	}
	
	
	/**
	 * Import some data into the destination JTable.
	 * <P>
	 * </P>
	 * 
	 * @param table
	 *        The table that will receive the data.
	 * @param myData
	 *        The data to transfer.
	 */
	protected void performImport(JTable table, RowTableTransferable trans)
	{
		SimpleRowModelTableModel destModel = (SimpleRowModelTableModel) table.getModel();

		int destRow = table.getSelectedRow();
		if (-1 == destRow)
			destRow = 0;

		SimpleRowModelTableModel srcModel = trans.getSourceTableModel();

		//
		// create an array of indices, in ascending order
		//
		int[] rows = trans.getSourceRows();
		Arrays.sort(rows);

		//
		// Copy the source data
		//
		Object[] copy = new Object[rows.length];
		for (int i = 0; i < copy.length; i++)
		{
			copy[i] = srcModel.copyRowData(rows[i]);
		}

		//
		// insert/delete
		//
		
		boolean sameTable = sameTable(table, trans);
		
		handleMove(srcModel, destModel, destRow, rows, copy);
		
		//
		// Select the rows that were moved.  The tricky part is the situation where
		// this is a) the same table and b) one or more rows were moved to an area
		// *below* the source rows.
		//
		int start = destRow;
		int end = start + copy.length - 1;
		
		//
		// If this is "the tricky case" the selected rows have moved *up* a number
		// of rows equal to the number we had to remove.  That is, if rows 1 & 2 
		// are being moved to index 5, then rows 3 & 4 will need to be selected, not
		// 5 & 6.
		//
		if (sameTable && rows[0] < destRow)
		{
			start = start - rows.length;
			end = end - rows.length;
		}
		JTableUtils.setSelectedRows(table, start, end);
	}
	
	
	
	protected void handleMove (
		SimpleRowModelTableModel srcModel, 
		SimpleRowModelTableModel destModel, 
		int insertPoint, 
		int[] rowIndicies, 
		Object[] data
	)
	{
		boolean insertAfter = insertPoint > rowIndicies[0];
		
		//
		// remove the rows we are moving
		//
		for (int index = rowIndicies.length - 1; index >= 0; index--)
		{
			srcModel.deleteRow(rowIndicies[index]);
		}
		
		if (insertAfter)
			insertPoint = insertPoint - rowIndicies.length;
		
		if (insertPoint < 0)
			insertPoint = 0;
		
		for (int i = 0; i < data.length; i++)
		{
			destModel.insertRow(i, data[i]);
		}
	}

	
	protected Object[] copyValues(int row, SimpleRowModelTableModel model)
	{
		Object[] values = new Object[model.getColumnCount()];
		for (int i = 0; i < values.length; i++)
		{
			values[i] = model.getValueAt(row, i);
		}
		
		return values;
	}
	
	/**
	 * Return the selection index after the move is completed.
	 * 
	 * <P>
	 * Most of the time this is just whatever row is selected in the destination table,
	 * but there is one case where it is not. If rows are being moved in the same table,
	 * and the insertion point is after the last source row, then the selected index
	 * shifts upwards.
	 * </P>
	 * <P>
	 * The "selection point" shifts upwards because a number of rows were removed from the
	 * table after the insert (the source rows). This being the case, the slection point
	 * moves up a number of rows equal to the number of rows that were moved downwards.
	 * </P>
	 * 
	 * @param table
	 *        The destination table. This determines the original selection point.
	 * @param trans
	 *        The transferable object. This determines the source rows and the source row
	 *        indicies.
	 * @return The selection point. See description for details.
	 */
	protected int toSelectionPoint (JTable table, RowTableTransferable trans)
	{
		int index;
		boolean sameTable = sameTable(table, trans);
		
		if (!sameTable)
		{
			index = table.getSelectedRow();
		}
		else
		{
			int insertionPoint = table.getSelectedRow();
			if (insertionPoint < trans.getSourceRows()[0])
				index = insertionPoint;
			else
				index = insertionPoint - trans.getSourceRows().length;
		}
		
		return index;
	}
}
