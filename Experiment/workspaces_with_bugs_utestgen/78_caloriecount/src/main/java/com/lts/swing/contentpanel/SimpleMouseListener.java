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

import javax.swing.JPopupMenu;

/**
 * A simplified mouse listener interface to be used with CPMouseListener.
 * 
 * <P>
 * Three common uses for mouse listeners are to listen for single click events,
 * listen for double click events and to display popup menus.  Using this 
 * interface, clients can quickly and easily implement these functions.
 * 
 * <P>
 * Here is an example implementation for a class that just wants to watch for
 * double click events:
 * <PRE>
 * public SimpleMouseListener mySimpleMouseListener() {
 *     public void singleClick (Object source) {}
 *     public void doubleClick (Object source) { doDoubleClick(source); }
 *     public JPopupMenu getPopup (Component component) { return null; }
 * };
 * </PRE>
 * 
 * <P>
 * Here is an example for a class that wants to provide popups:
 * <PRE>
 * protected Map myComponentToPopup;
 * public SimpleMouseListener mySimpleMouseListener() {
 *     public void singleClick (Object source) {}
 *     public void doubleClick (Object source) { doDoubleClick(source); }
 *     public JPopupMenu getPopup (Component component) {
 *         return (JPopupMenu) myComponentToPopup.get(component);
 *     }
 * };
 * </PRE>
 * 
 * 
 */
public interface SimpleMouseListener
{
	public void singleClick (Object source);
	public void doubleClick (Object source);
	public JPopupMenu getPopup (Component component);
}
