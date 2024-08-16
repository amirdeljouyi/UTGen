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
package com.lts.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuListener implements ActionListener
{
	protected Map itemToMethodMap;
	protected Object receiver;
	
	public MenuListener (Object receiver, Map itemToMethodMap, JMenuBar menuBar)
	{
		this.itemToMethodMap = itemToMethodMap;
		this.receiver = receiver;
		initialize(menuBar);
	}
	
	public MenuListener (Object receiver, Map itemToMethodMap, JPopupMenu popup)
	{
		this.itemToMethodMap = itemToMethodMap;
		this.receiver = receiver;
		initialize(popup);
	}
	
	
	public void initialize (JMenuBar menuBar)
	{
		int count = menuBar.getMenuCount();
		for (int i = 0; i < count; i++)
		{
			JMenu menu = menuBar.getMenu(i);
			registerWith(menu);
		}
	}
	
	
	public void initialize (JPopupMenu popup)
	{
		int count = popup.getComponentCount();
		for (int i = 0; i < count; i++)
		{
			JMenuItem item = (JMenuItem) popup.getComponent(i);
			registerWith(item);
		}
	}
	
	public void registerWith(JMenuItem item)
	{
		item.addActionListener(this);
		
		if (item instanceof JMenu)
		{
			JMenu menu = (JMenu) item;
			int count = menu.getItemCount();
			for (int i = 0; i < count; i++)
			{
				JMenuItem subitem = menu.getItem(i);
				registerWith(subitem);
			}
		}
	}
	
	
	protected static final Object[] VOID_ARGS = new Object[0];
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (!(source instanceof JMenuItem))
			return;
		
		JMenuItem item = (JMenuItem) source;
		Method method = (Method) itemToMethodMap.get(item);
		
		try
		{
			method.invoke(receiver, VOID_ARGS);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
