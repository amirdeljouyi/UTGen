package com.lts.swing.table.controlpanel;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JTable;

import com.lts.event.LTSMouseAdapter;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.table.ControlPanel;

abstract public class RowModelControlPanel extends ControlPanel
{
	abstract protected void select(int[] selections);
	abstract protected Object create();
	
	protected JTable myTable;
	
	private PanelModes myMode;
	
	public void initialize(PanelModes mode, JTable table)
	{
		JButton button;
		ActionListener action;
		
		myMode = mode;
		
		action = new SimpleThreadedAction() {
			public void action() {
				create();
			}
		};
		button = new JButton("Create");
		button.addActionListener(action);
		addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				checkedEdit();
			}
		};
		button = new JButton("Edit");
		button.addActionListener(action);
		addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				checkedDelete();
			}
		};
		button = new JButton("Delete");
		button.addActionListener(action);
		addButton(button,5);
		
		if (mode == PanelModes.SelectOnly)
		{
			action = new SimpleThreadedAction() {
				public void action() {
					checkedSelect();
				}
			};
			button = new JButton("Select");
			button.addActionListener(action);
			addButton(button,5);
		}
	}
	
	
	public PanelModes getMode()
	{
		return myMode;
	}
	
	
	public void initializeTable(JTable table)
	{
		myTable = table;
		
		MouseListener listener = new LTSMouseAdapter() {
			public void doubleClick(Object source) {
				performDoubleClick();
			}
		};
		
		myTable.addMouseListener(listener);
	}
}
