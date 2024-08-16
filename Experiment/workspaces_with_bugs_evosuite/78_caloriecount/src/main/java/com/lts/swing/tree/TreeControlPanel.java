package com.lts.swing.tree;

import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleMouseListener;
import com.lts.swing.table.KeyHelper;

abstract public class TreeControlPanel extends LTSPanel
{
	/**
	 * Edit the specified node.
	 * 
	 * <P>
	 * The caller ensures that a row is selected.  The implementing method must 
	 * update the table model appropriately for the edits to take effect.
	 * </P>
	 * 
	 * @param index Which row to edit.
	 */
	abstract protected void edit (DefaultMutableTreeNode node);
	
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
	abstract protected void delete(TreePath path);
	
	/**
	 * Create a new row.
	 * 
	 * <P>
	 * Create a new row at the specified location.  The row may be -1 if no row is
	 * currently selected.  The calling method must update the table model to reflect
	 * the new row.
	 * </P>
	 */
	abstract protected void create (DefaultMutableTreeNode parent);
	
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
	abstract protected void select (DefaultMutableTreeNode node);
	
	protected JTree myTree;
	protected PanelModes myMode;
	
	public enum PanelModes
	{
		Standard,
		Select
	}

	protected TreeControlPanel()
	{
	}

	
	@SuppressWarnings("serial")
	protected void initialize(PanelModes mode)
	{
		myTree = new JTree();
		myMode = mode;
		
		JButton button;
		Action action;
		
		if (myMode == PanelModes.Select)
		{
			button = new JButton("Select");
			
			action = new LTSThreadedAction() {
				public void action() {
					checkedSelect();
				}
			};
			button.addActionListener(action);
			addButton(button,5);

			KeyHelper.mapKey(KeyHelper.Mapping.Enter, this, action);
		}
		
		button = new JButton("Create");
		action = new LTSThreadedAction() {
			public void action() {
				checkedCreate();
			}
		};
		button.addActionListener(action);
		addButton(button,5);
		
		KeyHelper.mapKey(KeyHelper.Mapping.Insert, this, action);
		
		if (myMode == PanelModes.Standard)
		{
			button = new JButton("Edit");
			action = new LTSThreadedAction() {
				public void action() {
					checkedEdit();
				}
			};
			button.addActionListener(action);
			addButton(button,5);
		
			KeyHelper.mapKey(KeyHelper.Mapping.Enter, this, action);
		}
		
		KeyHelper.mapKey(KeyHelper.Mapping.Insert, this, action);

		button = new JButton("Delete");
		action = new LTSThreadedAction() {
			public void action() {
				checkedDelete();
			}
		};
		button.addActionListener(action);
		addButton(button,5);
		
		KeyHelper.mapKey(KeyHelper.Mapping.Delete, this, action);
		
		SimpleMouseListener sml = new SimpleMouseListener() {
			public void doubleClick(MouseEvent event) {
				launchDoubleClick();
			}
		};
		
		myTree.addMouseListener(sml);
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
		TreePath[] paths = myTree.getSelectionPaths();
		if (null == paths || paths.length < 1)
			return;
		
		String message = "Delete entry(s)?";
		int response = JOptionPane.showConfirmDialog(this, message);
		if (JOptionPane.OK_OPTION != response)
			return;

		delete(paths[0]);
	}

	protected void checkedEdit()
	{
		TreePath path = myTree.getSelectionPath();
		if (null == path)
			return;
		
		edit((DefaultMutableTreeNode) path.getLastPathComponent());
	}

	protected void checkedSelect()
	{
		TreePath path = myTree.getSelectionPath();
		if (null == path)
			return;
		
		DefaultMutableTreeNode node;
		node = (DefaultMutableTreeNode) path.getLastPathComponent();
		select(node);
	}
	
	
	protected void checkedCreate()
	{
		TreePath path = myTree.getSelectionPath();
		if (null == path)
			return;
		
		DefaultMutableTreeNode node ;
		node = (DefaultMutableTreeNode) path.getLastPathComponent();
		create(node);
	}
	
	
	protected void checkedDoubleClick()
	{
		TreePath path = myTree.getSelectionPath();
		if (null == path)
			return;
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		
		switch (myMode)
		{
			case Select :
				select(node);
				break;
				
			case Standard :
				edit(node);
				break;
				
			default :
				throw new IllegalArgumentException();
		}
	}
}
