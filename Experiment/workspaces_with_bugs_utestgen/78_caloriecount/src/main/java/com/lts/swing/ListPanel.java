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
package com.lts.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import com.lts.event.Callback;
import com.lts.event.ReturnKeyListener;
import com.lts.util.MapUtil;

/**
 * A class that simplifies using a list where users can create, update, 
 * delete and move myEntries.
 *
 * <P/>
 * This panel creates a scrollable panel that contains a JList and four 
 * buttons.  The buttons are add, delete, up and down.
 * 
 * <H3>Subclassing</H3>
 * When creating subclasses of this class, here are some of the methods
 * you may want to override:
 * 
 * <UL>
 * <LI>createListModel
 * <LI>createElement
 * <LI>editElement
 * <LI>deleteElement
 * <LI>singleClick
 * <LI>doubleClick
 * <LI>showPopup
 * </UL>
 */
public class ListPanel extends LTSPanel
{
	private static final long serialVersionUID = 1L;

	public class ListPanelButtonListener 
		implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			buttonPressed (event.getSource());
		}
	}
	
	public class ListMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			maybeShowPopup(e);
		}
		
		public void mouseReleased(MouseEvent e) 
		{
			maybeShowPopup(e);
		}
		
		public void maybeShowPopup(MouseEvent e) 
		{
			if (e.isPopupTrigger()) 
			{
				showPopup(e);
			}			
		}
		
		public void mouseClicked (MouseEvent e)
		{
			if (e.getClickCount() < 2)
				singleClick(e.getSource());
			else 
				doubleClick(e.getSource());
		}
	}
	
	
	public class ListReturnKeyListener extends ReturnKeyListener
	{
		public void performAction (Object source)
		{
			if (getList() == source)
			{
				returnKeyPressed();
			}
		}
	}
	
	protected ListMouseListener myMouseListener;
	protected ChangeableListModel myListModel;
	protected ListReturnKeyListener myKeyListener;
	protected String myAddButtonText = "Add";
	protected String myDeleteButtonText = "Delete";
	protected String myUpButtonText = "Up";
	protected String myDownButtonText = "Down";
	protected String myEditButtonText = "Edit";
	
	protected ListPanelButtonListener myListener;
	
	protected JButton myAddButton;
	protected JButton myDeleteButton;
	protected JButton myUpButton;
	protected JButton myDownButton;
	protected JButton myEditButton;
	
	protected boolean myShowButtons = true;
	protected boolean myEditOnDoubleClick = false;
	
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	
	protected int myButtonOrientation = EAST;
	
	public static final int BEHAVIOR_CREATE = 0;
	public static final int BEHAVIOR_EDIT = 1;
	
	protected int myResponseToEnter = BEHAVIOR_EDIT;
	
	public static final int BUTTON_EDIT = 0;
	public static final int BUTTON_CREATE = 1;
	public static final int BUTTON_DELETE = 2;
	public static final int BUTTON_UP = 3;
	public static final int BUTTON_DOWN = 4;
	
	protected Map myButtonCodeToDisplayMap;
	
	public Map getButtonCodeToDisplayMap()
	{
		if (null == myButtonCodeToDisplayMap)
			myButtonCodeToDisplayMap = buildButtonCodeToDisplayMap(getButtonPanelMode());
		
		return myButtonCodeToDisplayMap;
	}
	
	public boolean displayButton (int buttonCode)
	{
		Integer i = new Integer(buttonCode);
		Boolean b = (Boolean) getButtonCodeToDisplayMap().get(i);
		return b.booleanValue();
	}
	
	public void setDisplayButton (int buttonCode, boolean display)
	{
		Integer i = new Integer(buttonCode);
		Boolean b = new Boolean(display);
		getButtonCodeToDisplayMap().put(i,b);
	}
	
	public boolean getDisplayButton(int buttonCode)
	{
		return displayButton(buttonCode);
	}
	
	
	public void showPopup (MouseEvent e)
	{
		JPopupMenu pop = getPopupMenu(e);
		if (null != pop)
		{
			pop.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	
	public ReturnKeyListener getKeyListener()
	{
		if (null == myKeyListener)
			myKeyListener = new ListReturnKeyListener();
		
		return myKeyListener;
	}

	
	public int getResponseToEnter ()
	{
		return myResponseToEnter;
	}
	
	public void setResponseToEnter (int i)
	{
		myResponseToEnter = i;
	}
	
	
	public ListMouseListener getMouseListener()
	{
		if (null == myMouseListener)
			myMouseListener = new ListMouseListener();
		
		return myMouseListener;
	}
	
	
	public ListPanelButtonListener getListener()
	{
		if (null == myListener)
			myListener = new ListPanelButtonListener();
		
		return myListener;
	}
	
	
	public String getEditButtonText()
	{
		return myEditButtonText;
	}
	
	public void setEditButtonText(String s)
	{
		myEditButtonText = s;
	}
	
	
	/**
	 * @return Returns the addButtonText.
	 */
	public String getAddButtonText()
	{
		return myAddButtonText;
	}

	/**
	 * @param addButtonText The addButtonText to set.
	 */
	public void setAddButtonText(String addButtonText)
	{
		this.myAddButtonText = addButtonText;
	}

	/**
	 * @return Returns the deleteButtonText.
	 */
	public String getDeleteButtonText()
	{
		return myDeleteButtonText;
	}

	/**
	 * @param deleteButtonText The deleteButtonText to set.
	 */
	public void setDeleteButtonText(String deleteButtonText)
	{
		this.myDeleteButtonText = deleteButtonText;
	}

	/**
	 * @return Returns the downButtonText.
	 */
	public String getDownButtonText()
	{
		return myDownButtonText;
	}

	/**
	 * @param downButtonText The downButtonText to set.
	 */
	public void setDownButtonText(String downButtonText)
	{
		this.myDownButtonText = downButtonText;
	}

	/**
	 * @return Returns the upButtonText.
	 */
	public String getUpButtonText()
	{
		return myUpButtonText;
	}

	/**
	 * @param upButtonText The upButtonText to set.
	 */
	public void setUpButtonText(String upButtonText)
	{
		this.myUpButtonText = upButtonText;
	}

	public int getButtonOrientation ()
	{
		return myButtonOrientation;
	}
	
	public void setButtonOrientation (int orientation)
	{
		myButtonOrientation = orientation;
	}
	
	
	public boolean getShowButtons ()
	{
		return myShowButtons;
	}
	
	public boolean showButtons ()
	{
		return getShowButtons();
	}
	
	public void setShowButtons (boolean b)
	{
		myShowButtons = b;
	}
	
	
	public JButton getAddButton ()
	{
		return myAddButton;
	}
	
	public void setAddButton (JButton b)
	{
		myAddButton = b;
	}
	
	
	public JButton getDeleteButton ()
	{
		return myDeleteButton;
	}
	
	public void setDeleteButton (JButton b)
	{
		myDeleteButton = b;
	}
	
	
	public JButton getEditButton ()
	{
		return myEditButton;
	}
	
	public void setEditButton (JButton b)
	{
		myEditButton = b;
	}
	
	
	public JButton getUpButton ()
	{
		return myUpButton;
	}
	
	public void setUpButton (JButton b)
	{
		myUpButton = b;
	}
	
	
	public JButton getDownButton ()
	{
		return myDownButton;
	}
	
	public void setDownButton (JButton b)
	{
		myDownButton = b;
	}
	
	
	public ChangeableListModel getListModel()
	{
		return myListModel;
	}
	
	public void setListModel (ChangeableListModel model)
	{
		myListModel = model;
	}
	
	
	protected JList myList;
	
	public JList getList()
	{
		return myList;
	}
	
	public void setList(JList list)
	{
		myList = list;
	}
	
	
	public List getAll ()
	{
		List l = new ArrayList();
		int size = getListModel().getSize();
		
		for (int i = 0; i < size; i++)
		{
			Object o = getListModel().getElementAt(i);
			l.add(o);
		}
		
		return l;
	}
	
	
	public void setAll (List data)
	{
		getListModel().removeAll();
		
		if (null == data)
			return;
		
		int size = data.size();
		
		for (int i = 0; i < size; i++)
		{
			getListModel().addElement(data.get(i));
		}
	}
	
	
	public void addAll (List data)
	{
		if (null == data)
			return;
		
		int size = data.size();
		for (int i = 0; i < size; i++)
		{
			getListModel().addElement(data.get(i));
		}
	}
	
	
	public void addOne (Object o)
	{
		getListModel().addElement(o);
	}
	
	public ListPanel ()
	{
		super();
	}
	
	
	/**
	 * Create the list model for the JList.
	 * 
	 * <P/>
	 * Override this method if you want to use something other than 
	 * the SimpleListModel class.
	 * 
	 * @return The new model.
	 */
	public ChangeableListModel createListModel()
	{
		return new SimpleChangeableListModel();
	}
	
	
	public void initialize ()
	{
		rebuild();
	}
	
	
	public void addButtonToPanel (JButton button, LTSPanel panel)
	{
		switch (getButtonOrientation())
		{
			case NORTH :
			case SOUTH :
				panel.addButton(button, 5);
				break;
				
			case EAST :
			case WEST :
				panel.addHorizontal(button, 5);
				// panel.addButton(button, 5);
				panel.nextRow();
				break;
		}
	}
	
	
	protected Callback myAddButtonCallback;
	
	public Callback getAddButtonCallback()
	{
		return myAddButtonCallback;
	}
	
	public void setAddButtonCallback (Callback callback)
	{
		myAddButtonCallback = callback;
	}
	
	
	public void addButtonPressed ()
	{
		if (null == getAddButtonCallback())
			insertElement();
		else
			getAddButtonCallback().callback(this);
	}
	
	
	protected Callback myDeleteButtonCallback;
	
	public Callback getDeleteButtonCallback()
	{
		return myDeleteButtonCallback;
	}
	
	public void setDeleteButtonCallback (Callback callback)
	{
		myDeleteButtonCallback = callback;
	}
	
	
	public void deleteButtonPressed ()
	{
		if (null == getDeleteButtonCallback())
			deleteElement();
		else
			getDeleteButtonCallback().callback(this);
	}
	
	
	protected Callback myUpButtonCallback;
	
	public Callback getUpButtonCallback()
	{
		return myUpButtonCallback;
	}
	
	public void setUpButtonCallback (Callback callback)
	{
		myUpButtonCallback = callback;
	}
	
	public void upButtonPressed()
	{
		if (null == getUpButtonCallback())
			moveElementUp();
		else
			getUpButtonCallback().callback(this);
	}
	
	
	protected Callback myDownButtonCallback;
	
	public Callback getDownButtonCallback()
	{
		return myDownButtonCallback;
	}
	
	public void setDownButtonCallback (Callback callback)
	{
		myDownButtonCallback = callback;
	}
	
	public void downButtonPressed ()
	{
		if (null == getDownButtonCallback())
			moveElementDown();
		else
			getDownButtonCallback().callback(this);
	}
	
	
	protected Callback myEditButtonCallback;
	
	public Callback getEditButtonCallback()
	{
		return myEditButtonCallback;
	}
	
	public void setEditButtonCallback (Callback callback)
	{
		myEditButtonCallback = callback;
	}
	
	public void editElement ()
	{
		Object o = getList().getSelectedValue();
		if (null == o)
			return;
		else
			editElement(o);
	}
	
	
	protected void editButtonPressed ()
	{
		if (null == getEditButtonCallback())
			editElement();
		else
			getEditCallback().callback(this);
	}
	
	
	/**
	 * Create a sub-panel that contains the various buttons that the 
	 * the list will use.
	 * 
	 * <P/>
	 * By default, this panel consists of add, delete, up and down.
	 * 
	 * @return A panel that contains the buttons.
	 */
	public JPanel createButtonPanel ()
	{
		LTSPanel p = new LTSPanel();
		
		JButton b;
		
		if (displayButton(BUTTON_EDIT))
		{
			ActionListener listener = new ActionListener() {
				public void actionPerformed (ActionEvent junk) {
					editButtonPressed();
				}
			};
			b = new JButton(getEditButtonText());
			b.addActionListener(listener);
			addButtonToPanel(b, p);
			setEditButton(b);
		}
		
		if (displayButton(BUTTON_CREATE))
		{
			ActionListener listener = new ActionListener() {
				public void actionPerformed (ActionEvent junk) {
					addButtonPressed();
				}
			};
			b = new JButton(getAddButtonText());
			b.addActionListener(listener);
			addButtonToPanel(b, p);
			setAddButton(b);
		}
		
		if (displayButton(BUTTON_DELETE))
		{
			ActionListener listener = new ActionListener() {
				public void actionPerformed (ActionEvent junk) {
					deleteButtonPressed();
				}
			};
			b = new JButton(getDeleteAction());
			b.addActionListener(listener);
			b.setText(getDeleteButtonText());
			addButtonToPanel(b, p);
			setDeleteButton(b);
		}
		
		if (displayButton(BUTTON_UP))
		{
			ActionListener listener = new ActionListener() {
				public void actionPerformed (ActionEvent junk) {
					upButtonPressed();
				}
			};
			b = new JButton(getUpAction());
			b.addActionListener(listener);
			b.setText(getUpButtonText());
			addButtonToPanel(b, p);
			setUpButton(b);
		}
		
		if (displayButton(BUTTON_DOWN))
		{
			ActionListener listener = new ActionListener() {
				public void actionPerformed (ActionEvent junk) {
					upButtonPressed();
				}
			};
			b = new JButton(getDownAction());
			b.addActionListener(listener);
			b.setText(getDownButtonText());
			addButtonToPanel(b, p);
			setDownButton(b);
		}
		
		return p;
	}
	
	
	/**
	 * Call this method if you want to force the panel to recreate all of 
	 * the components from scratch.
	 * 
	 * <P/>
	 * This method will discard anything it currently has for buttons, 
	 * components, etc. and then create it anew.  If you only want to rebuild
	 * things that are supposed to be there but that may be null, then use
	 * reinitialize instead.
	 */
	public void rebuild ()
	{
		removeAll();
		
		ChangeableListModel model = createListModel();
		setListModel(model);
		
		JList list = new JList(model);
		list.addMouseListener(getMouseListener());
		list.addKeyListener(getKeyListener());
		list.setDragEnabled(true);
		setList(list);
		
		JScrollPane jsp = new JScrollPane(list);
		
		if (showButtons())
		{	
			JPanel buttonPanel = createButtonPanel();
			
			switch (getButtonOrientation())
			{
				case NORTH :
					addHorizontal(buttonPanel);
					nextRow();
					addFill(jsp,5);
					break;
					
				case SOUTH :
					addFill(jsp,5);
					nextRow();
					addHorizontal(buttonPanel);
					break;
					
				case EAST :
					addFill(jsp,5);
					addVertical(buttonPanel);
					break;
					
				case WEST :
					addVertical(buttonPanel);
					addFill(jsp,5);
					break;
			}
			
		}
	}
	
	
	
	/**
	 * This is called when the user has signaled that they want to add a 
	 * new element to the list.
	 * 
	 * <P/>
	 * The default behavior is to pop up a JOptionPane and ask the user for 
	 * a string, then add that string to the before the element that is 
	 * currently selected.  If no element is selected, then the new element
	 * is added to the end of the list.
	 */
	public Object createElement()
	{
		String s = JOptionPane.showInputDialog(this, "Enter new element data");
		if (null == s)
			return null;
		else 
			return s;
	}
	
	
	public void insertElement()
	{
		Object newElement = createElement();
		if (null == newElement)
			return;
		
		int index = getList().getSelectedIndex();
		if (-1 == index)
		{
			index = 0; 
			if (getListModel().getSize() > 0)
				index = getListModel().getSize();
		}
	
		getListModel().add(index, newElement);
	}
	
	
	public boolean confirmDelete(Object o)
	{
		return true;
	}
	
	
	/**
	 * Delete an element from the list.
	 * 
	 * <P/>
	 * This method determines what the selected list element is removes it.  
	 * By default, if no element is selected, then the method does nothing.
	 */
	public void deleteElement()
	{
		int index = getList().getSelectedIndex();
		if (-1 == index)
			return;
		
		Object o = getListModel().getElementAt(index);
		if (confirmDelete(o))
			getListModel().removeElementAt(index);
	}
	
	/**
	 * All the user to change the text for the selected element.
	 * 
	 * <P/>
	 * This method will check to see if anything is selected.  If not, the 
	 * method does nothing.  Otherwise, the method will prompt the user to 
	 * edit the text for the selected element.  If the user accepts the 
	 * changes, the method will change the underlying list element text.
	 * 
	 * <P/>
	 * If you want the class to do something more involved, like display an 
	 * edit dialog for example, then you will need to override this method.
	 */
	public void performEdit()
	{
		Object o = getList().getSelectedValue();
		if (null == o)
			return;
		
		editElement(o);
	}
	
	
	public void editElement (Object o)
	{
		if (null != getEditCallback())
			getEditCallback().callback(o);
	}
	
	/**
	 * Move the selected list element upwards by one.
	 * 
	 * <P/>
	 * If nothing is selected when this method is called, then the method 
	 * does nothing.  If something is selected, but it is the first element
	 * in the list, then the method does nothing.  Otherwise, the selected
	 * element is moved upwards towards the start of the list, by one 
	 * position.
	 */
	public void moveElementUp()
	{
		int index = getList().getSelectedIndex();
		if (0 < index)
		{
			Object o1 = getListModel().getElementAt(index -1);
			Object o2 = getListModel().getElementAt(index);

			getListModel().setElementAt(o1, index);
			getListModel().setElementAt(o2, index - 1);
			
			getList().setSelectedIndex(index -1);
		}
	}
	
	/**
	 * Move the selected list element towards the end of the list by one
	 * position.
	 * 
	 * <P/>
	 * If nothing is selected when this method is called, then the method 
	 * does nothing.  If something is selected, but it is the last element
	 * in the list, then the method does nothing.  Otherwise, the selected
	 * element is moved towards the end of the list by one position.
	 */
	public void moveElementDown ()
	{
		int index = getList().getSelectedIndex();
		if (1 + index < getListModel().getSize())
		{
			Object o1 = getListModel().getElementAt(index);
			Object o2 = getListModel().getElementAt(index + 1);
			
			getListModel().setElementAt(o1, index + 1);
			getListModel().setElementAt(o2, index);

			getList().setSelectedIndex(index + 1);
		}
	}
	
	
	public void buttonPressed (Object source)
	{
		if (source == getAddButton())
			insertElement();
		else if (source == getDeleteButton())
			deleteElement();
		else if (source == getEditButton())
			performEdit();
		else if (source == getUpButton())
			moveElementUp();
		else if (source == getDownButton())
			moveElementDown();
	}
	
	
	public void singleClick (Object source)
	{}
	
	public void doubleClick (Object source)
	{
		Object o = getList().getSelectedValue();
		if (null == o)
			return;
		
		if (null != getDoubleClickCallback())
			getDoubleClickCallback().callback(o);
		else if (editOnDoubleClick())
			editElement(o);
	}
	
	public void returnKeyPressed ()
	{
		switch (getResponseToEnter())
		{
			case BEHAVIOR_EDIT :
				performEdit();
				break;
			
			case BEHAVIOR_CREATE :
				insertElement();
				break;
		}
	}



	protected Action myCreateAction;
	protected Action myEditAction;
	protected Action myDeleteAction;
	protected Action myUpAction;
	protected Action myDownAction;

	
	@SuppressWarnings("serial")
	public Action getUpAction()
	{
		if (null == myUpAction)
		{
			myUpAction = new AbstractAction() {
				public void actionPerformed (ActionEvent e) {
					moveElementUp();
				}
			};
		}
		
		return myUpAction;
	}
	
	
	@SuppressWarnings("serial")
	public Action getDownAction ()
	{
		if (null == myDownAction)
		{
			myDownAction = new AbstractAction() {
				public void actionPerformed (ActionEvent e) {
					moveElementDown();
				}
			};
		}
		
		return myDownAction;
	}
	
	@SuppressWarnings("serial")
	public Action getCreateAction()
	{
		if (null == myCreateAction)
		{
			myCreateAction = new AbstractAction() {
				public void actionPerformed (ActionEvent e) {
					insertElement();
				}
			};
		}
		
		return myCreateAction;
	}
	
	

	@SuppressWarnings("serial")
	public Action getEditAction()
	{
		if (null == myEditAction)
		{
			myEditAction = new AbstractAction() {
				public void actionPerformed (ActionEvent e) {
					performEdit();
				}
			};
		}
		
		return myEditAction;
	}


	@SuppressWarnings("serial")
	public Action getDeleteAction()
	{
		if (null == myDeleteAction)
		{
			myDeleteAction = new AbstractAction() {
				public void actionPerformed (ActionEvent e) {
					deleteElement();
				}
			};
		}
		
		return myDeleteAction;
	}



	protected JPopupMenu myPopup;


	public JPopupMenu getPopup()
	{
		if (null == myPopup)
		{
			myPopup = new JPopupMenu();
			
			JMenuItem item;
			
			item = new JMenuItem(getCreateAction());
			item.setText("Create");
			myPopup.add(item);
			
			item = new JMenuItem(getEditAction());
			item.setText("Edit");
			myPopup.add(item);
			
			item = new JMenuItem(getDeleteAction());
			item.setText("Delete");
			myPopup.add(item);
		}
		
		return myPopup;
	}


	public JPopupMenu getPopupMenu(MouseEvent mev)
	{
		return getPopup();
	}
	
	
	public List copyListElements()
	{
		int length = getListModel().getSize();
		List l = new ArrayList(length);
		
		for (int i = 0; i < length; i++)
		{
			l.add(getListModel().getElementAt(i));
		}
		return l;
	}
	
	
	public boolean editOnDoubleClick()
	{
		return myEditOnDoubleClick;
	}
	
	public void setEditOnDoubleClick(boolean editOnDoubleClick)
	{
		myEditOnDoubleClick = editOnDoubleClick;
	}
	
	public boolean getEditOnDoubleClick()
	{
		return editOnDoubleClick();
	}
	
	
	protected Callback myEditCallback;
	
	public Callback getEditCallback()
	{
		return myEditCallback;
	}
	
	public void setEditCallback(Callback callback)
	{
		myEditCallback = callback;
	}
	
	
	protected Callback myDoubleClickCallback;
	
	public Callback getDoubleClickCallback()
	{
		return myDoubleClickCallback;
	}
	
	public void setDoubleClickCallback(Callback callback)
	{
		myDoubleClickCallback = callback;
	}
	
	protected static final Object[] SPEC_DEFAULT_BUTTON_PANEL_MODE = {
			new Integer(BUTTON_EDIT),	new Boolean(false),
			new Integer(BUTTON_CREATE),	new Boolean(true),
			new Integer(BUTTON_DELETE),	new Boolean(true),
			new Integer(BUTTON_UP),		new Boolean(true),
			new Integer(BUTTON_DOWN),	new Boolean(true),
	};
	
	protected static final Object[] SPEC_SORTED_BUTTON_PANEL_MODE = {
			new Integer(BUTTON_EDIT),	new Boolean(true),
			new Integer(BUTTON_CREATE),	new Boolean(true),
			new Integer(BUTTON_DELETE),	new Boolean(true),
			new Integer(BUTTON_UP),		new Boolean(false),
			new Integer(BUTTON_DOWN),	new Boolean(false),
	};
	
	protected static final Object[] SPEC_EDIT_BUTTON_PANEL_MODE = {
			new Integer(BUTTON_EDIT),	new Boolean(true),
			new Integer(BUTTON_CREATE),	new Boolean(true),
			new Integer(BUTTON_DELETE),	new Boolean(true),
			new Integer(BUTTON_UP),		new Boolean(true),
			new Integer(BUTTON_DOWN),	new Boolean(true),
	};
	
	
	public static final int BUTTON_PANEL_MODE_DEFAULT = 0;
	public static final int BUTTON_PANEL_MODE_SORTED = 1;
	public static final int BUTTON_PANEL_MODE_EDIT = 2;
	
	protected int myButtonPanelMode;
	
	public void setButtonPanelMode (int mode)
	{
		myButtonPanelMode = mode;
	}
	
	public int getButtonPanelMode ()
	{
		return myButtonPanelMode;
	}
	
	public Map buildButtonCodeToDisplayMap (int mode)
	{
		Object[] spec;
		
		switch (mode)
		{
			case BUTTON_PANEL_MODE_EDIT :
				spec = SPEC_EDIT_BUTTON_PANEL_MODE;
				break;
				
			case BUTTON_PANEL_MODE_SORTED :
				spec = SPEC_SORTED_BUTTON_PANEL_MODE;
				break;
				
			default :
				spec = SPEC_DEFAULT_BUTTON_PANEL_MODE;
				break;
		}
		
		Map m = MapUtil.buildMap(spec);
		return m;
	}
	
	
	public void showList (List list)
	{
		myListModel = new SimpleChangeableListModel(list);
		
	}
}
