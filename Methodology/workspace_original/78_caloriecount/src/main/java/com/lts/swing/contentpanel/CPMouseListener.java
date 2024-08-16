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
 * Copyright (c) 2004 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.contentpanel;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * A MouseAdapter that handles common mouse functions.
 * 
 * <P>
 * This class simplifies dealing with mouse events by providing much of the 
 * "busy work" code associated with using MouseAdapter.  
 */
public class CPMouseListener extends MouseAdapter
{
	protected SimpleMouseListener myPeer;
	
	public CPMouseListener (SimpleMouseListener peer)
	{
		myPeer = peer;
	}
	
	
	public void mousePressed(MouseEvent e) 
	{
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) 
	{
		maybeShowPopup(e);
	}

	
	/**
	 * Show the popup menu for the provided component, if there is one.
	 * 
	 * <P>
	 * This method will ask the peer for the JPopupMenu associated with the 
	 * provided component.  If one does not exist (the peer returns null), then
	 * the method tries again with the containing component.  The process 
	 * continues until no containing component exists, or a popup menu is 
	 * found.
	 * 
	 * <P>
	 * If no menu exists, then the method simply returns.  If a menu is found,
	 * then the method displays the popup.
	 * 
	 * @param source The component that the mouse was over when the popup event
	 * was triggered.
	 * 
	 * @param x location of mouse when the popup event was triggered.
	 * @param y location of mouse when the popup event was triggered.
	 */
	public void showPopup (Component source, int x, int y)
	{
		if (null == source)
			return;
		
		JPopupMenu menu = myPeer.getPopup(source);
		while (null != source && null == menu)
		{
			source = source.getParent();
			menu = myPeer.getPopup(source);
		}
		
		if (null != menu)
		{
			menu.show(source, x, y);
		}
	}
	
	public void singleMouseClick (MouseEvent event)
	{
		myPeer.singleClick(event.getSource());
	}
	
	public void doubleMouseClick (MouseEvent event)
	{
		myPeer.doubleClick(event.getSource());
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

