package com.lts.swing.table.rowmodel;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.lts.event.LTSMouseAdapter;

/**
 * This class listens for certain events on a JTable and translates them into 
 * events that {@link JTableEventListener} uses.
 * 
 * <H2>Quickstart</H2>
 * <CODE>
 * <PRE>
 * private void doubleClick(MouseEvent event) {
 *     JOptionPane.showMessageDialog(null, "double click");
 * }
 * ...
 * {
 *     JTable table;
 *     ...
 *     JTableEventBridge bridge = new JTableEventBridge(table);
 *     ...
 * }   
 * </PRE>
 * </CODE>
 * 
 * <P>
 * After this code is executed, the message "double click" will appear in a 
 * JOptionPane if the associated JTable is double clicked.
 * </P>
 * 
 * <H2>Description</H2>
 * Using this class, one can create an inner class to perform initial processing
 * on JTable events.  This is typically done to specify (by overriding) those 
 * events of interest.
 * 
 * <P>
 * Note that calling {@link #setTable(JTable)} implicitly calls {@link #release()}
 * on any old table and then {@link #attach()}.
 * </P>
 * 
 * @author cnh
 *
 */
public class JTableEventBridge
{
	protected JTableEventHelper myHelper;
	protected JTable myTable;
	protected JPopupMenu myMenu;
	protected LTSMouseAdapter myMouseAdaptor;
	
	/**
	 * Causes the instance to try and register as a mouse listener for the current
	 * table.  If there is no current table, the method takes no action.
	 */
	public void attach()
	{
		if (null == myTable)
			return;
		
		myMouseAdaptor = new LTSMouseAdapter() {
			public void singleClick(Object source)
			{
				bridgeSingleClick(getEvent());
			}
			
			public void doubleClick(Object source)
			{
				birdgeDoubleClick(getEvent());
			}
		};
		
		myMouseAdaptor.setPopupMenu(myMenu);
		myTable.addMouseListener(myMouseAdaptor);
	}
	
	
	protected void bridgeKeyTyped(KeyEvent event)
	{
	}


	protected void birdgeDoubleClick(MouseEvent mouseEvent)
	{
		int[] selectedRows = myTable.getSelectedRows();
		TableEvent.Events theEvent = TableEvent.Events.DoubleClick;
		TableEvent event = new TableEvent(theEvent, selectedRows);
		myHelper.fireEvent(event);
	}


	protected void bridgeSingleClick(MouseEvent mouseEvent)
	{
		
	}
	
	/**
	 * Unregister this instance as a listener for the JTable.  This also sets
	 * the table and mouseAdaptor properties to null.
	 */
	public void release()
	{
		if (null != myMouseAdaptor && null != myTable)
			myTable.removeMouseListener(myMouseAdaptor);
		
		myMouseAdaptor = null;
		myTable = null;
	}
	
	public void bridgeSingleClick()
	{}

	public void bridgeDoubleClick()
	{}
	
	public JTableEventBridge()
	{
		initialize(null);
	}
	
	
	public void initialize(JTable table)
	{
		myHelper = new JTableEventHelper();
		myTable = table;
		attach();
	}
	
	public JTableEventBridge(JTable table)
	{
		initialize(table);
	}
	
	
	public JTable getTable()
	{
		return myTable;
	}
	
	public void setTable(JTable table)
	{
		release();
		myTable = table;
		attach();
	}
	
	public void addListener(JTableEventListener listener)
	{
		myHelper.addListener(listener);
	}
	
	public void removeListener(JTableEventListener listener)
	{
		myHelper.removeListener(listener);
	}
}
