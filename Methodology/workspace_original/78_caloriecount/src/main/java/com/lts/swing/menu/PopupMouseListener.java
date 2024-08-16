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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * A class that simplifies the use of popup menus by combining a popup with 
 * a mouse listener.
 * 
 * <H2>Examples</H2>
 * <CODE>
 * <PRE>
 * JTable table;
 * JPopupMenu popup;
 * ...
 * PopupMouseListener pml = new PopupMouseListener(popup);
 * table.addMouseListener(pml);
 * </PRE>
 * </CODE>
 * 
 * <P/>
 * The JPopupMenu described by popup will now be triggered while the 
 * appropriate mouse button is pressed or released.
 */
public class PopupMouseListener extends MouseAdapter
{
	protected JPopupMenu myPopup;
	
	public PopupMouseListener (JPopupMenu popup)
	{
		myPopup = popup;
	}
	
	
	public void showPopup (Component source, int x, int y)
	{
		if (null == source)
			return;

		myPopup.show(source, x, y);
	}
	
	public void maybeShowPopup(MouseEvent e) 
	{
		if (e.isPopupTrigger()) 
		{
			Component c = (Component) e.getSource();
			showPopup (c, e.getX(), e.getY());
		}
	}
	

	public void mousePressed(MouseEvent e) 
	{
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) 
	{
		maybeShowPopup(e);
	}
}
