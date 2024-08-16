package com.lts.swing.table.rowmodel.tablemodel;

import javax.swing.table.TableModel;

/**
 * A TableModel that uses the RowModel framework.  
 * 
 * <P>
 * Instances of this interface provide basic table-like operations.  The data 
 * is a sequence of data that can be accessed by row and column indexes.  The actual
 * organization can be quite different. Regardless of what it was trying to do, 
 * </P>
 * 
 * @author cnh
 *
 */
public interface RowModelTableModel extends TableModel
{
	/**
	 * Get the underlying object at the specified row instead of the values used 
	 * to display the row.
	 * 
	 * @param index the row to get.
	 * @return The object for that row.
	 */
	public Object getRow(int index);
	
	/**
	 * Get the number of rows in the table.
	 * 
	 * @return The number of rows in the table.
	 */
	public int getRowCount();
}
