package com.lts.swing.table.rowmodel;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.event.TableModelEvent;

/**
 * An event from a table.
 * 
 * <P>
 * Unlike {@link TableModelEvent}, an instance of this class is meant for objects
 * that are interested in events happening to the displayed table, rather than the 
 * data associated with that table.
 * </P>
 * 
 * @author cnh
 *
 */
public class TableEvent
{
	/**
	 * Events that can occur to the table.
	 * 
	 * <P>
	 * DoubleClick indicates that the user has performed a double click on a 
	 * table row.
	 * </P>
	 * 
	 * <P>
	 * The other events pertain to keys that have been hit on the keyboard.  Enter
	 * for the enter/return key; insert for the insert key and delete for the delete
	 * key.
	 * </P>
	 * 
	 * @author cnh
	 */
	public enum Events
	{
		SingleClick,
		DoubleClick,
		Enter,
		Insert,
		Delete
	}
	
	public Events event;

	/**
	 * This field represents the row(s) that are selected when the event 
	 * takes place.  Its value are the indicies of the rows. 
	 * 
	 * <P>
	 * For DoubleClick, this should be the row that was selected.
	 * </P>
	 * 
	 * <P>
	 * For Enter, this is the row that was selected.
	 * </P>
	 * 
	 * <P>
	 * For Insert, this is the row that was created.
	 * </P>
	 * 
	 * <P>
	 * For delete, this is the rows that were deleted.
	 * </P>
	 */
	public int[] rows;
	
	
	/**
	 * The event object associated with the event.
	 * 
	 * <P>
	 * For mouse-related events, this is an instance of {@link MouseEvent}.  For
	 * keyboard-related events, this is an instance of {@link KeyEvent}.
	 * </P>
	 */
	public Object eventObject;
	
	public TableEvent(Events theEvent)
	{
		event = theEvent;
	}
	
	public TableEvent(Events theEvent, int row)
	{
		event = theEvent;
		rows = new int[1];
		rows[0] = row;
	}
	
	public TableEvent(Events theEvent, int[] theRows)
	{
		rows = theRows;
	}
}
