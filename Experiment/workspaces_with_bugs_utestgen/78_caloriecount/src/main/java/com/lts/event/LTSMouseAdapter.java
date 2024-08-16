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
package com.lts.event;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * A simplified MouseAdapter that allows clients to detect single, double or 
 * popup events.
 * 
 * <P>
 * Subclasses can detect and act on "refined" mouse events such as a single 
 * click, double click or popup mouse event.  This is instead of having to 
 * determine what sort of event has taken place.
 * </P>
 * 
 * <P>
 * The methods that sublasses may want to override are:
 * </P>
 * <UL>
 * <LI>{@link #doubleClick(Object)}</LI>
 * <LI>{@link #showPopup(Object, int, int)}</LI>
 * <LI>{@link #singleClick(Object)}</LI>
 * </UL>
 * 
 * @author cnh
 */
public class LTSMouseAdapter extends MouseAdapter
{
	private JPopupMenu myPopupMenu;
	private MouseEvent myEvent;
	
	public MouseEvent getEvent()
	{
		return myEvent;
	}
	
	
	public JPopupMenu getPopupMenu()
	{
		return myPopupMenu;
	}
	
	public void setPopupMenu(JPopupMenu menu)
	{
		myPopupMenu = menu;
	}
	
	public void mousePressed(MouseEvent e)
	{
		myEvent = e;
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		myEvent = e;
		maybeShowPopup(e);
	}

	/**
	 * The mouse event that corresponds to showing a popup menu (right-click on
	 * windows) has occurred.
	 * 
	 * <P>
	 * The default implementation is to call {@link #getPopupMenu()} and if the result
	 * is non-null, to display the resulting popup.
	 * </P>
	 * 
	 * <P>
	 * Subclasses can override this method to display the popup, or they can 
	 * simply call {@link #setPopupMenu(JPopupMenu)} to set the popup when the 
	 * listener is first created.
	 * </P>
	 * 
	 * @param source The source from the mouse event.
	 * @param x The x position from the mouse event.
	 * @param y The y position from the mouse event.
	 */
	public void showPopup(Object source, int x, int y)
	{
		if (null != myPopupMenu)
		{
			Component src = (Component) source;
			myPopupMenu.show(src, x, y);
		}
	}

	public void singleClick(Object source)
	{}

	public void doubleClick(Object source)
	{}

	public void maybeShowPopup(MouseEvent e)
	{
		if (e.isPopupTrigger())
		{
			myEvent = e;
			Object source = e.getSource();
			showPopup(source, e.getX(), e.getY());
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		myEvent = e;
		Object source = e.getSource();

		if (e.isPopupTrigger())
			maybeShowPopup(e);
		else if (e.getClickCount() < 2)
			singleClick(source);
		else
			doubleClick(source);
	}
}