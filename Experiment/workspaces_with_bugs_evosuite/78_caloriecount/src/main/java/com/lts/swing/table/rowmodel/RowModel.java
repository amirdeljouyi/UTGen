package com.lts.swing.table.rowmodel;

import java.util.Comparator;

/**
 * A row of data in a JTable that uses an underlying object instead of a matrix
 * of object values or the like.
 * 
 * <P>
 * Many of the methods in this class translate directly into methods that are 
 * defined for the TableModel interface.
 * </P>
 * 
 * @author cnh
 */
public interface RowModel
{
	/**
	 * Get the names of the columns in the table.
	 * 
	 * @return Column names.
	 */
	public String[] getColumnNames();
	
	/**
	 * Get the class for a particular table column.
	 * 
	 * @param column 
	 * @return
	 */
	public Class getColumnClass(int column);
	public Object getValueAt (int column, Object row);
	public void setValueAt (int rowIndex, Object data, int column, Object value);
	public Comparator getComparator();
	public boolean isColumnEditable(int col);
	public int getColumnCount();
	public String getColumnName(int column);
	public int compareRows(Object o1, Object o2);
}
