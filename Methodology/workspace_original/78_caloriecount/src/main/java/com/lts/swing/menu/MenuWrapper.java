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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.lts.LTSException;

/**
 * Provide a uniform interface for various menu-like classes.
 * 
 * <H2>Description</H2>
 * This class allows menu builders to treat JMenu, JPopupMenu and JMenuBar in 
 * the same manner.  The primary thing that such builders need to do is add separators
 * and menu items to menus.  Instead of having three separate versions of essentially
 * the same method for each menu-like class, builders can program to this interface
 * and then have three methods that wrap an instance of this class.
 * 
 * <P>
 * The primary issue with menu-like classes is that JMenuBar does not support menu
 * separators whereas JMenu and JPopupMenu do.  If an attempt is made to add a 
 * separator to a JMenuBar, the addSeparator method will throw an exception.
 * 
 * @author cnh
 *
 */
public class MenuWrapper
{
	protected JMenu myMenu;
	protected JMenuBar myMenuBar;
	protected JPopupMenu myPopup;
	
	public MenuWrapper (JMenu menu)
	{
		myMenu = menu;
	}
	
	public MenuWrapper (JMenuBar menuBar)
	{
		myMenuBar = menuBar;
	}
	
	public MenuWrapper (JPopupMenu popup)
	{
		myPopup = popup;
	}
	
	
	public void addMenuItem (JMenuItem item)
	{
		if (null != myMenu)
			myMenu.add(item);
		else if (null != myPopup)
			myPopup.add(item);
		else
			myMenuBar.add(item);
	}
	
	
	public void addSeparator () throws LTSException
	{
		if (null != myMenu)
			myMenu.addSeparator();
		else if (null != myPopup)
			myPopup.addSeparator();
		else
		{
			String msg = "Cannot add a separator to a menu bar";
			throw new LTSException(msg);
		}
	}
}
