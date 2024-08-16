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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A MouseAdapter that can display a popup menu and respond to double-click 
 * actions.
 *
 * <H2>Abstract Class</H2>
 * To be instantiateable, subclasses must implement the following methods:
 * <UL>
 * <LI>{@link #showPopup(Component, int, int)}
 * <LI>{@link #doubleClick(MouseEvent)}
 * </UL>
 * 
 * <H2>Description</H2>
 * This class simplifies dealing with the mouse in circumstances that I typically
 * encounter.  The two most common reasons I use a mouse listener are to 1) show
 * a popup menu and 2) respond to a double-click event.  The class filters mouse
 * events down to these two events for classes to implement.
 * 
 * @author cnh
 */
public class SimpleMouseListener extends MouseAdapter
{
	public void showPopup (Component comp, int x, int y)
	{}
	
	public void doubleClick (MouseEvent e)
	{
		int i = e.getClickCount();
		i++;
	}
	
	protected MouseEvent myEvent;
	
	public void processMouseEvent(MouseEvent e, boolean press) 
	{
		myEvent = e;
		if (e.isPopupTrigger()) 
		{
			Component c = (Component) e.getSource();
			if (null == c)
				return;
			
			showPopup (c, e.getX(), e.getY());
		}
		else if (e.getClickCount() < 2 && press)
		{
			singleClick();
		}
		else if (e.getClickCount() > 1 && press)
		{
			doubleClick();
		}
	}

	protected void singleClick()
	{}

	protected void doubleClick()
	{}
	
	protected void showPopup()
	{}
	
	protected void singleClick(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) 
	{
		processMouseEvent(e, true);
	}


	public void mouseReleased(MouseEvent e) 
	{
		processMouseEvent(e, false);
	}
}
