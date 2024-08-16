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
/*
 * Copyright (c) 2003 Long Term Software, LLC.
 */
package com.lts.swing.list;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPopupMenu;

/**
 * A JList that has built in support for a number of common list behaviors.
 * 
 * <P/>
 * For example, usually when someone hits the delete button, they want to 
 * delete the list entry.  Similarly when they hit insert, they want to create
 * a new entry.
 * 
 * <P/>
 * The keys that the list catches and the associated hook methods are:
 * <UL>
 * <LI/>return - expandEntry
 * <LI/>delete - removeEntry
 * <LI/>insert - createEntry
 * <LI/>double click - doubleClickEntry (defaults to expandEntry)
 * <LI/>right mouse lick - showPopup
 * </UL>
 * 
 * @author cnh
 */
public class LTSList extends JList
{
	private static final long serialVersionUID = 1L;


	/**
	 * A simple MouseAdapter that listens for single click, double click 
	 * and popup menu events.
	 * 
	 * @author cnh
	 */
	public class LTSMouseListener extends MouseAdapter
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
				Component c = (Component) e.getSource();
				showPopup (c, e.getX(), e.getY());
			}
		}
		
		public void mouseClicked (MouseEvent e)
		{
			if (e.getClickCount() < 2)
				singleMouseClick(e);
			else 
				doubleMouseClick(e);
		}
	}
	
	
	/**
	 * A simple KeyAdapter that listens for the enter key, delete key and 
	 * insert key.
	 * 
	 * @author cnh
	 */
	protected class LTSKeyAdapter extends KeyAdapter
	{
		private Component myComponent;
		
		public void keyPressed (KeyEvent e)
		{
			if (e.getSource() != myComponent)
				return;
				
			Object entry = getSelectedValue();
			int index = getSelectedIndex();
				
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_ENTER :
					editEntry(index, entry);
					break;
					
				case KeyEvent.VK_DELETE :
					deleteEntry(index);
					break;
					
				case KeyEvent.VK_INSERT :
					createEntry(index);
					break;
			}
		}
		
		
		public LTSKeyAdapter (Component comp)
		{
			myComponent = comp;
		}
	}
	
	
	protected MouseAdapter myMouseAdapter;
	protected LTSKeyAdapter myKeyAdapter;
	protected JPopupMenu myPopupMenu;
	
	
	public JPopupMenu getPopupMenu()
	{
		return myPopupMenu;
	}
		
	public MouseAdapter getMouseListener()
	{
		if (null == myMouseAdapter)
			myMouseAdapter = new LTSMouseListener();
		
		return myMouseAdapter;
	}
	
	public KeyAdapter getKeyListener()
	{
		if (null == myKeyAdapter)
			myKeyAdapter = new LTSKeyAdapter(this);
		
		return myKeyAdapter;
	}
	
	public void singleMouseClick (MouseEvent e)
	{}
	
	
	public void doubleMouseClick (MouseEvent e)
	{
		if (e.getSource() != this)
			return;		
		
		Object o = getSelectedValue();
		if (null == o)
			return;
		
		doubleClickEntry (o);
	}
	
	
	public void showPopup (Component source, int x, int y)
	{
		JPopupMenu menu = getPopupMenu();
		if (null != menu)
		{
			menu.show(source, x, y);
		}
	}
	

	/**
	 * Called when the delete key is pressed; the default action is to 
	 * delete the entry.
	 * 
	 * <P/>
	 * Note that this method assumes that the underlying list model is an 
	 * instance or a subclass of DefaultListModel.
	 * 
	 * <P/>
	 * If the index passed is -1, signifying that nothing was selected, then 
	 * the method will ignore the event.
	 * 
	 * <P/>
	 * If some other activity or some additional housekeeping needs to be 
	 * performed when the delete key is pressed, this method should be 
	 * overridden.
	 * 
	 * @param index The index of the entry that was selected when the 
	 * delete key was pressed.  This may be -1 to signify that there was no
	 * selection when the delete key was pressed.
	 */
	public void deleteEntry (int index)
	{
		getListenerHelper().fire(ListListenerHelper.EVENT_DELETE);
	}
	
	/**
	 * This is called when the insert key is pressed; default behavior is to 
	 * do nothing.
	 * 
	 * @param index The index of the list entry that was selected when the  
	 * insert key was pressed.  This may be -1 if nothing was selected.
	 */
	public void createEntry (int index)
	{
		getListenerHelper().fire(ListListenerHelper.EVENT_CREATE);
	}
	
	/**
	 * This is called when a) the enter key is pressed or b) when a list entry
	 * is double clicked.
	 * 
	 * <P/>
	 * This method provides a hook for subclasses.  The default behavior is to 
	 * do nothing.
	 * 
	 * @param entry The selected entry when the event occurred.  If nothing was
	 * selected then this parameter will be null.
	 */
	public void editEntry (int index, Object entry)
	{
		getListenerHelper().fire(ListListenerHelper.EVENT_EDIT);
	}
	
	/**
	 * This method is called when the user double clicks an entry in the list;
	 * the default behavior is to call expandEntry.
	 * 
	 * @param entry The entry the user double-clicked or null if nothing was 
	 * selected.
	 */
	public void doubleClickEntry (Object entry)
	{
		int index = getSelectedIndex();
		editEntry(index, entry);
	}
	
	
	private ListListenerHelper myListenerHelper;
	
	protected ListListenerHelper getListenerHelper()
	{
		if (null == myListenerHelper)
			myListenerHelper = new ListListenerHelper();
		
		return myListenerHelper;
	}
	
	public void addListListener (ListListener listener)
	{
		getListenerHelper().addListener(listener);
	}
	
	public void removeListListener (ListListener listener)
	{
		getListenerHelper().removeListener(listener);
	}
	
	public void initialize()
	{
		addMouseListener(getMouseListener());
		addKeyListener(getKeyListener());
	}
	
	
	public LTSList()
	{
		super();
		initialize();
	}
}
