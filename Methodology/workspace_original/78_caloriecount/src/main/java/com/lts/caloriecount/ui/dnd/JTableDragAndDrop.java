package com.lts.caloriecount.ui.dnd;

import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler.TransferSupport;


abstract public class JTableDragAndDrop implements DragAndDropHandler
{
	/**
	 * Copy a row as part of a drag-move operation.
	 * 
	 * <P>
	 * This method is used by the class to move rows as part of a drag and move
	 * operation.  See the section in the class description for details; this 
	 * method is the part of the sequence that copies the source row.
	 * </P>
	 * 
	 * @param source The index of the row to move.
	 * @return The row data.
	 */
	abstract protected Object copyRow (JTable table, int source);
	
	/**
	 * Paste a row as part of a drag-move operation.
	 * 
	 * <P>
	 * This method puts or moves a row of data to its new location as part of a 
	 * drag and move operation.  The data being passed is simply the result of a 
	 * call to {@link #copyRow(int)}.
	 * </P>
	 * 
	 * @param destination The index where the row data should go.
	 * @param rowData The row data itself.
	 */
	abstract protected void pasteRow(JTable table, int destination, Object rowData);
	
	/**
	 * Remove a source row as part of a drag and move operation.
	 * 
	 * <P>
	 * See the class description for details on how drag and move works with 
	 * this class.
	 * </P>
	 * 
	 * @param source The row to remove.
	 */
	abstract protected void deleteRow (JTable table, int row);
	
	protected int[] myCopiedRows;
	private int[] myMarkedRows;
	private int myMoveDestination;
	
	
	/**
	 * Delete the specified rows.
	 * 
	 * <P>
	 * This method is the "cut" part of a cut and paste operation.  
	 * </P>
	 * 
	 * <P>
	 * Note that this method is <I>not</I> used as part of a drag and move 
	 * operation.  For that, 
	 * </P>
	 * 
	 * @param start
	 * @param end
	 */
	protected void delete(JComponent component, int start, int end)
	{
		JTable table = (JTable) component;
		
		int length = 1 + end - start;
		for (int i = 0; i < length; i++)
			deleteRow(table, start);
	}

	/**
	 * Copy some data to be put in the system "clipboard".
	 * 
	 * <P>
	 * This method is not intended to be implemented by the developer.  Instead,
	 * developers should override 
	 * </P>
	 * 
	 * <P>
	 * This method expects the receiver copy and return the selected data.  This 
	 * is the lower-level version of copying, the 
	 * </P>
	 */
	@Override
	public Object copy(JComponent comp)
	{
		JTable table = (JTable) comp;
		int[] rows = table.getSelectedRows();
		Arrays.sort(rows);
		Object[] data = new Object[rows.length];
		
		for (int i = 0; i < rows.length; i++)
		{
			data[i] = copyRow(table, rows[i]);
		}
		
		myCopiedRows = rows;
		
		return data;
	}
	
	
	@Override
	public void delete(JComponent source, Object data)
	{
		JTable table = (JTable) source;
		int[] rows = table.getSelectedRows();
		
		Arrays.sort(rows);
		for (int i = rows.length - 1; i >= 0; i--)
		{
			deleteRow(table, rows[i]);
		}
	}

	@Override
	public void paste(TransferSupport support, Object data)
	{
		Object[] rowData = (Object[]) data;
		JTable table = (JTable) support.getComponent();
		int index = table.getSelectedRow();
		
		for (int i = 0; i < rowData.length; i++)
		{
			int dest = index + i;
			pasteRow(table, dest, rowData[i]);
		}
	}

	@Override
	public Object moveCopyData(JComponent comp)
	{
		return copy(comp);
	}
	

	/**
	 * Paste the data from a drag and move operation.
	 * 
	 * <P>
	 * In drag and move, the rows are deleted from their original position in the table.
	 * The delete, however, occurs after the data has been pasted, so in the situation
	 * where a selection  of rows is moved upwards in the table, the indicies of the 
	 * data to be removed have been changed. 
	 * </P>
	 * 
	 * <P>
	 * This method takes that fact into account by "remembering" the offset that may  
	 * result from pasting the data so that when the data is removed, the indicies can 
	 * take the new position into account.
	 * </P>
	 */
	@Override
	public void movePasteData(TransferSupport support, Object data)
	{
		JTable table = (JTable) support.getComponent();
		myMoveDestination = table.getSelectedRow();
		paste(support, data);
	}

	/**
	 * Delete the rows that were moved in the recent drag and drop operation.
	 * 
	 * <P>
	 * This is slightly different from a regular delete because the indicies 
	 * may have changed as a result of the move.  This method takes that into account
	 * and then simply calls the delete method.
	 * </P>
	 */
	@Override
	public void moveDeleteData(JComponent comp)
	{
		if (null != myMarkedRows && myMarkedRows.length > 0 && -1 != myMoveDestination)
		{
			JTable table = (JTable) comp;
			
			Arrays.sort(myMarkedRows);
			
			int offset = 0;
			if (myMoveDestination < myMarkedRows[0])
				offset = myMarkedRows.length;
			
			for (int i = myMarkedRows.length - 1; i >= 0; i--)
			{
				deleteRow(table, myMarkedRows[i] + offset);
			}			
		}
	}

	@Override
	public void moveMarkData(JComponent comp)
	{
		JTable table = (JTable) comp;
		myMarkedRows = table.getSelectedRows();
	}
	
	
}
