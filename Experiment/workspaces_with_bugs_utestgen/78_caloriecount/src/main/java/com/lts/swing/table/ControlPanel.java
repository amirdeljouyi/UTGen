package com.lts.swing.table;

import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;

/**
 * A panel that contains a JTable and some controls to manipulate it.
 * 
 * <H2>Quick Start</H2>
 * <UL>
 * <LI>Subclass ControlPanel</LI>
 * <LI>
 * 		define/override the following methods
 * 		<UL>
 * 		<LI>createTableModel</LI>
 * 		<LI>create</LI>
 * 		<LI>edit</LI>
 * 		<LI>delete</LI>
 * 		</UL>
 * </LI>
 * </UL>
 *
 * <H2>Example</H2>
 * <A href="ControlPanelExample.html">ControlPanelExample.java</A>
 * 
 * <H2>Description</H2>
 * Note that this class is really only useful when you have a table that you want to
 * be able to add/edit/delete rows from.
 * 
 * <P>
 * The buttons are create, edit and delete.  The panel also responds to double
 * clicking on a row by either editing the row, or "selecting" the row depending on
 * the mode being used.
 * </P>
 * 
 * <P>
 * Subclasses must define the following methods:
 * </P>
 * 
 * <UL>
 * <LI>create</LI>
 * <LI>edit</LI>
 * <LI>delete</LI>
 * </UL>
 * 
 * <P>
 * Subclasses should also set the mode of the table to one of the {@link #PanelModes}
 * values.  If the table is in "Standard" (edit) mode, then double clicking will
 * cause the panel to call {@link #edit(int)} on the row.
 * </P>
 * 
 * <P>
 * If the table is in "select" mode then a select button also appears on the 
 * controls and double-clicking on a row results in the {@link #select(int[])}
 * method being called.
 * </P>
 * 
 * <H3>Example</H3>
 * Here is an example implementation:
 * 
 * <P>
 * <CODE>
 * <PRE>
 * public void initialize()
 * {
 *     myModel = new LTSTableModel();
 *     setMode(PanelModes.Standard);
 *     super.initialize();
 * }
 * 
 * public void delete(int index)
 * {
 *     myModel.delete(index);
 * }
 * 
 * public void create(int index)
 * {
 *     &lt;do some editing stuff&gt;
 * }
 * 
 * public edit (int index)
 * {
 *     &lt;do some editing stuff&gt;
 * }
 * 
 * public select (int index)
 * {
 *     throw UnimplementedException();
 * }
 * </PRE>
 * </CODE>
 * 
 * @author cnh
 *
 */
abstract public class ControlPanel extends LTSPanel
{
	/**
	 * Edit the specified row.
	 * 
	 * <P>
	 * The caller ensures that a row is selected.  The implementing method must 
	 * update the table model appropriately for the edits to take effect.
	 * </P>
	 * 
	 * @param index Which row to edit.
	 */
	abstract protected void edit (int index);
	
	/**
	 * Delete the specified row.
	 * 
	 * <P>
	 * The caller ensures that a row is selected.  The implementing method must 
	 * perform whatever updates are required for the table to be updated.
	 * </P>
	 * 
	 * @param index The row to delete.
	 */
	abstract protected void delete(int index);
	
	/**
	 * Create a new row.
	 * 
	 * <P>
	 * Create a new row at the specified location.  The row may be -1 if no row is
	 * currently selected.  The calling method must update the table model to reflect
	 * the new row.
	 * </P>
	 */
	abstract protected void create (int index);
	
	/**
	 * Perform a select operation, in response to a double click or if the user 
	 * clicks the select button.
	 * 
	 * <P>
	 * This method is only called if the table is in select mode.
	 * </P>
	 * 
	 * <P>
	 * The caller ensures that a row is selected before calling this method.
	 * </P>
	 * 
	 * @param select The row to select.
	 */
	abstract protected void select (int select);
	
	abstract protected TableModel createTableModel();
	
	abstract protected Object[][] buildButtonSpec();
	
	public enum PanelModes
	{
		Browse,
		SelectOnly
	}

	protected TableModel myModel;
	protected JTable myTable;
	protected PanelModes myMode;
	
	public PanelModes getMode()
	{
		return myMode;
	}
	
	
	protected ControlPanel()
	{
	}

	protected void initialize(PanelModes mode)
	{
		addFill(createTablePanel(mode));
		nextRow();
		addHorizontal(createButtonPanel());
	}

	protected JTable createJTable(TableModel tableModel)
	{
		return new JTable(tableModel);
	}
	
	
	protected LTSPanel createTablePanel(PanelModes mode)
	{
		myModel = createTableModel();
		myTable = createJTable(myModel);
		myMode = mode;
		
		JScrollPane jsp = new JScrollPane(myTable);
		LTSPanel panel = new LTSPanel();
		panel.addFill(jsp);
		
		return panel;		
	}

	private LTSPanel createButtonPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button;
		ActionListener listener;
		if (myMode == PanelModes.SelectOnly)
		{
			button = new JButton("Select");
			listener = new SimpleThreadedAction() {
				public void action() {
					checkedSelect();
				}
			};
			panel.addButton(button,5);
		}
		
		button = new JButton("Create");
		listener = new SimpleThreadedAction() {
			public void action() {
				checkedCreate();
			}
		};
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		button = new JButton("Edit");
		listener = new SimpleThreadedAction() {
			public void action() {
				checkedEdit();
			}
		};
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		button = new JButton("Delete");
		listener = new SimpleThreadedAction() {
			public void action() {
				checkedDelete();
			}
		};
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		return panel;
	}

	protected void launchDoubleClick()
	{
		Thread thread = new Thread() {
			public void run() {
				checkedDoubleClick();
			}
		};
		
		thread.start();
	}

	protected void checkedDelete()
	{
		int[] selections = myTable.getSelectedRows();
		if (null == selections || selections.length <= 0)
			return;
		
		String message = "Delete entry(s)?";
		int response = JOptionPane.showConfirmDialog(this, message);
		if (JOptionPane.OK_OPTION != response)
			return;
		
		for (int i = 0; i < selections.length; i++)
		{
			delete(selections[i]);
		}
	}

	protected void checkedEdit()
	{
		int index = myTable.getSelectedRow();
		if (-1 == index)
			return;
		
		edit(index);
	}

	protected void checkedSelect()
	{
		int[] selections = myTable.getSelectedRows();
		if (null == selections || selections.length < 0)
			return;
		
		select(selections[0]);
	}
	
	
	protected void checkedCreate()
	{
		int index = myTable.getSelectedRow();
		create(index);			
	}
	
	
	protected void performDoubleClick()
	{
		Thread thread = new Thread() {
			public void run() {
				int index = myTable.getSelectedRow();
				if (-1 == index)
					return;
				
				edit(index);
			}
		};
		
		thread.start();
	}
	
	
	protected void checkedDoubleClick()
	{
		int index = myTable.getSelectedRow();
		if (-1 == index)
			return;
		
		switch (myMode)
		{
			case SelectOnly :
				select(index);
				break;
				
			case Browse :
				edit(index);
				break;
				
			default :
				throw new IllegalArgumentException();
		}
	}
	
	public void closeWindow()
	{
		Window window = getWindow();
		window.setVisible(false);
	}
	
	
}