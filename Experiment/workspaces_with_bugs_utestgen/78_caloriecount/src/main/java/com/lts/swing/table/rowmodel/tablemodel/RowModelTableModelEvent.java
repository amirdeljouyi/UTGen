package com.lts.swing.table.rowmodel.tablemodel;


/**
 * An event has occurred in a RowModelTableModel such as a new row being added, 
 * removed, or updated.
 * 
 * @author cnh
 *
 */
public class RowModelTableModelEvent
{
	public enum EventType 
	{
		/**
		 * A single row has been added to the table, myRowIndex has the index of
		 * the new row.
		 */
		rowAdded,
		
		/**
		 * A single row has been changed.  The receiver should assume that all columns
		 * of the row have been modified until it examines them itself.  myRowIndex 
		 * specifies the index of the changed row.
		 */
		rowChanged,
		
		/**
		 * A single row has been removed from the table.  myRowIndex is the index 
		 * of where the row used to be.
		 */
		rowRemoved,
		
		/**
		 * The entire table has changed.  All rows may have been removed, replaced, etc.
		 * The client should make no assumptions of what the table contains based on 
		 * previous values.  myRowIndex contains a meaningless or undefined value.
		 */
		allChanged
	}
	
	public RowModelTableModelEvent(EventType etype, int row)
	{
		myEventType = etype;
		myRowIndex = row;
	}
	
	public EventType myEventType;
	public int myRowIndex;
}
