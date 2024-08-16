//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.swing.editlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.list.LTSList;
import com.lts.util.StringUtils;

/**
 * A list with buttons that allow the user to add, delete and update myEntries in the list.
 * 
 * <H2>Description</H2>
 * An instance of this class consists of a list of string elements and 5 buttons labeld
 * "add", "delete", "edit", "up" and "down."  The buttons can appear North, South, East or 
 * West of the list.  The default behavior for the various buttons are:
 * <UL>
 * <LI>Add - Open a simple dialog asking the user for a string and then appending the 
 * string to the end of the list.
 * <LI>Delete - Remove the selected item from the list.
 * <LI>Edit - Open a simple dialog with the current text of the item.  Allow the user to 
 * accept or cancel changes and then update the list entry with the changes.
 * <LI>Up - Move the currently selected entry up.
 * <LI>Down - Move the currently selected entry down.
 * </UL>
 * 
 * <P>
 * With all the buttons that require a selection, the system does nothing if there is no 
 * currently selected entry.  
 * 
 * <P>
 * The list elements can be accessed by calling getElements to obtain the current list 
 * elements.
 * 
 * <H2>Quickstart</H2>
 * The basic decisions and customization options that EditList supports include the 
 * following:
 * <UL>
 * <LI>The position of the editing buttons.
 * <LI>Whether to confirm deletes
 * <LI>The response to hitting the "add" and "edit" buttons.
 * </UL>
 * 
 * <P>
 * The position of the elements can be set by using the buttonPosition property.  The 
 * values defined by the constants POSITION_NORTH, POSITION_SOUTH, etc. determine where
 * the buttons will appear.  The default value is POSITION_SOUTH.
 * 
 * <P>
 * The confirmDelete property controls whether to confirm a delete.  A value of true 
 * means that the system will display a short dialog asking the user to confirm a 
 * delete.  The default value is false.
 * 
 * <P>
 * The response to the "add" button is changed by overriding the 
 * {@link #userAddElement} method.
 * 
 * <P>
 * The response to the "edit" button is changed by overriding the 
 * {@link #userEditElement} method.
 * 
 * @author cnh
 */
public class EditListPanel extends LTSPanel
{
	/**
	 * To turn off warnings from Eclipse.
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * Value for the buttonPosition property that signals that the buttons are supposed
	 * to be displayed below the liset.
	 */
	public static final int POSITION_SOUTH = 0;
	
	/**
	 * Value for the buttonPosition property that signals that the buttons are supposed
	 * to be displayed to the left of the list.
	 */
	public static final int POSITION_WEST = 1;
	
	/**
	 * Value for the buttonPosition property that signals that the buttons are supposed
	 * to be displayed to the north of the list.
	 */
	public static final int POSITION_NORTH = 2;
	
	/**
	 * Value for the buttonPosition property that signals that the buttons are supposed
	 * to be displayed to the right of the list.
	 */
	public static final int POSITION_EAST = 3;
	
	private int myButtonPosition;
	
	public int getButtonPosition()
	{
		return myButtonPosition;
	}
	
	public void setButtonPosition(int buttonPosition)
	{
		myButtonPosition = buttonPosition;
	}
	
	private boolean myConfirmDeletes;
	
	public boolean getConfirmDeletes()
	{
		return myConfirmDeletes;
	}
	
	public void setConfirmDeletes(boolean confirmDeletes)
	{
		myConfirmDeletes = confirmDeletes;
	}
	
	public boolean confirmDeletes()
	{
		return getConfirmDeletes();
	}
	
	
	/**
	 * Prompt the user to enter a new value for the list.
	 * 
	 * <P>
	 * The default implementation for this method displays a dialog box asking the user 
	 * to enter a value.  This string value is then returned by the method unless the 
	 * user cancels, in which case it returns null.
	 * 
	 * <P>
	 * Subclasses can perform whatever edits they like, but they should return a value 
	 * if the new value should be added, and the override should return null if no value
	 * should be added.
	 * 
	 * @return The new value to be added or null if no value should be added.
	 */
	public Object[] userAddElements()
	{
		String value = (String) JOptionPane.showInputDialog(this, "Enter new value");
		value = StringUtils.trim(value);
		return new Object[] { value };
	}
	
	/**
	 * Prompt the user to edit a value, starting with the current value from the list.
	 * 
	 * <P>
	 * The default implementation for this method assumes that list consists of string 
	 * values.  The method displays a dialog, with the current value in the dialog.  If 
	 * the user accepts the value, the method returns it, otherwise it returns null.
	 * 
	 * <P>
	 * Subclasses do not have to assume the list consists of strings and can perform 
	 * whatever operations deemed appropriate to allow the user to edit the current 
	 * value.  The overridden method should return the new value if the user wants to 
	 * replace it, or null to keep the old value.
	 * 
	 * @param current The current value from the list.
	 * 
	 * @return The new value, or null if the system should keep the old value.
	 */
	public Object userEditElement(Object current)
	{
		String s = (String) current;
		if (null == s)
			s = "";
		
		s = JOptionPane.showInputDialog(this, "Edit value", s);
		s = StringUtils.trim(s);
		
		return s;
	}
	
	
	/**
	 * Return the new index for an element that the user wants to move up in the 
	 * list.
	 * 
	 * <P>
	 * The default behavior is to move the element upwards in the list unless it is 
	 * at the top of the list already.
	 * 
	 * @param current The current index of the current element.
	 * @return The new index for the element.  Returns the input value if the element's
	 * position should not change.
	 */
	public int userMoveUp (int current)
	{
		if (current > 0)
			current--;
		
		return current;
	}
	
	
	/**
	 * Return the new index for an element that the user wants to move down in the 
	 * list.
	 * 
	 * <P>
	 * The default behavior is to move the element downwards in the list unless it is 
	 * at the bottom of the list already.
	 * 
	 * @param current The current index of the current element.
	 * 
	 * @return The new index for the element.  Returns the input value if the element's
	 * position should not change.
	 */
	public int userMoveDown (int current)
	{
		if (current < (getListSize() - 1))
			current++;
			
		return current;
	}
	
	
	private JList myList;
	
	public JList getList()
	{
		if (null == myList)
			myList = createList(null);
		
		return myList;
	}
	
	public void setList (JList list)
	{
		myList = list;
	}
	
	public int getListSize()
	{
		return getList().getModel().getSize();
	}
	
	
	private JButton myAddButton;
	
	public JButton getAddButton()
	{
		return myAddButton;
	}
	
	public void setAddButton(JButton button)
	{
		myAddButton = button;
	}
	
	
	protected EditListListener createListener(LTSList list)
	{
		return new EditListListener(list);
	}
	
	
	protected JList createList (List elements)
	{
		if (null == elements)
			elements = new ArrayList();
		
		SimpleListModel model = new SimpleListModel(elements);		
		LTSList list = new LTSList();
		list.setModel(model);
		createListener(list);
		
		return list;
	}
	
	public void addButtonAction(ActionEvent event)
	{
		Object[] values = userAddElements();
		if (null != values && values.length > 0)
		{
			SimpleListModel model;
			model = (SimpleListModel) getList().getModel();
			
			for (int i = 0; i < values.length; i++)
				model.addElement(values[i]);
		}
	}
	
	protected JButton createAddButton()
	{
		JButton button = new JButton("Add");
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addButtonAction(event);
			}
		};
		button.addActionListener(listener);
		return button;
	}
	
	
	public boolean userConfirmedDelete()
	{
		int result = JOptionPane.showConfirmDialog(this, "Delete this element?");
		return result == JOptionPane.YES_OPTION;
	}
	
	
	public void deleteButtonAction(ActionEvent event)
	{
		
		if (confirmDeletes() && !userConfirmedDelete())
			return;
		
		int index = getList().getSelectedIndex();
		SimpleListModel model = (SimpleListModel) getList().getModel();
		model.remove(index);
	}
	
	
	protected JButton createDeleteButton()
	{
		JButton button = new JButton("Delete");
		ActionListener listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				deleteButtonAction(event);
			}
		};
		button.addActionListener(listener);
		return button;
	}
	
	
	public void editButtonAction(ActionEvent event) 
	{
		SimpleListModel model = (SimpleListModel) getList().getModel();
		int index = getList().getSelectedIndex();
		if (-1 == index)
			return;
		
		Object value = getList().getModel().getElementAt(index);
		value = userEditElement(value);
		if (null != value)
			model.setElementAt(value, index);
	}
	
	
	protected JButton createEditButton()
	{
		JButton button = new JButton("Edit");
		ActionListener listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				editButtonAction(event);
			}
		};
		button.addActionListener(listener);
		return button;
	}
	
	
	public void moveButtonEvent(int direction)
	{
		SimpleListModel model = (SimpleListModel) getList().getModel();
		
		int oldIndex = getList().getSelectedIndex();
		if (-1 == oldIndex)
			return;
		
		int newIndex;
		if (direction < 0)
			newIndex = userMoveUp(oldIndex);
		else
			newIndex = userMoveDown(oldIndex);
		
		if (newIndex != oldIndex)
		{
			Object currentValue = model.get(oldIndex);
			Object otherValue = model.get(newIndex);
			model.setElementAt(currentValue, newIndex);
			model.setElementAt(otherValue, oldIndex);
			getList().setSelectedIndex(newIndex);
		}	
	}
	
	public void upButtonEvent(ActionEvent event)
	{
		moveButtonEvent(-1);
	}
	
	
	public void downButtonEvent (ActionEvent event)
	{
		moveButtonEvent(1);
	}
	
	
	protected JButton createUpButton()
	{
		JButton button = new JButton("Up");
		ActionListener listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				upButtonEvent(event);
			}
		};
		button.addActionListener(listener);
		return button;
	}
	
	
	protected JButton createDownButton()
	{
		JButton button = new JButton("Down");
		ActionListener listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				downButtonEvent(event);
			}
		};
		button.addActionListener(listener);
		return button;
	}
	
	
	
	protected List createButtons()
	{
		List list = new ArrayList();
			
		list.add(createAddButton());
		list.add(createDeleteButton());
		list.add(createEditButton());
		list.add(createUpButton());
		list.add(createDownButton());
			
		return list;
	}
	
	
	
	public void initialize (List userList, int buttonPosition, JLabel title)
	{
		setUserData(userList);
		setButtonPosition(buttonPosition);
		
		List elements = getUserData();
		if (null == elements)
			elements = new ArrayList();
		
		LTSPanel listPanel = new LTSPanel();
		
		
		if (null != title)
		{			
			listPanel.addCenteredLabel(title);
			listPanel.nextRow();
		}
		
		JList list = createList(elements);
		setList(list);
		JScrollPane jsp = new JScrollPane(list);
		listPanel.addFill(jsp,5);
		
		
		List blist = createButtons();
		Iterator bit = blist.iterator();
		
		LTSPanel buttonPanel = new LTSPanel();
		while (bit.hasNext())
		{
			JButton button = (JButton) bit.next();
			
			switch (getButtonPosition())
			{
				case POSITION_SOUTH :
				case POSITION_NORTH :
					buttonPanel.addButton(button,5);
					break;
					
				case POSITION_EAST :
				case POSITION_WEST :
					buttonPanel.addHorizontal(button,5);
					buttonPanel.nextRow();					
					break;
			}
		}
		
		switch (getButtonPosition())
		{
			case POSITION_SOUTH :
			case POSITION_NORTH :
				if (getButtonPosition() == POSITION_NORTH)
				{
					addHorizontal(buttonPanel);
					nextRow();
					addFill(listPanel);
				}
				else 
				{
					addFill(listPanel);
					nextRow();
					addHorizontal(buttonPanel);					
				}
				break;
				
			case POSITION_EAST :
			case POSITION_WEST :
				if (getButtonPosition() == POSITION_EAST)
				{
					addFill(listPanel);
					add(buttonPanel);
					setFillHorizontal(buttonPanel);
				}
				else
				{
					add(buttonPanel);
					setFillHorizontal(buttonPanel);
					addFill(listPanel);
				}
				break;
		}
	}
	
	
	public List getUserData()
	{
		ListModel model = getList().getModel();
		int size = model.getSize();
		List userData = new ArrayList(size);
		for (int i = 0; i < size; i++)
		{
			Object o = model.getElementAt(i);
			if (null != o)
				userData.add(o.toString());
		}
		
		return userData;
	}
	
	public void setUserData (List userData)
	{
		if (null == userData)
			userData = new ArrayList();
		
		SimpleListModel model = new SimpleListModel(userData);
		getList().setModel(model);
	}
	
	
	
	public EditListPanel ()
	{
		initialize(null, POSITION_EAST, null);
	}
	
	public EditListPanel (List userList)
	{
		initialize(userList, POSITION_EAST, null);
	}
	
	public EditListPanel (int buttonPosition)
	{
		initialize(null, buttonPosition, null);
	}
	
	public EditListPanel (List userList, int buttonPosition)
	{
		initialize(userList, buttonPosition, null);
	}
	
	public EditListPanel (String titleString)
	{
		JLabel label = new JLabel(titleString);
		initialize(null, POSITION_EAST, label);
	}
}
