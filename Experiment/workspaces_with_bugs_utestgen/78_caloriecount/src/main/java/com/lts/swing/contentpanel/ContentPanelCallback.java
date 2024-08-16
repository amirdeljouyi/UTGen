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

/**
 * Defines methods that get invoked when certain events occur with a 
 * ContentPanel.
 * 
 * <P>
 * All the methods defined have one thing in common: they all return a boolean
 * value.  This return value is used to determine if the window should close 
 * or not.  A value of true indicates that the window should close, a value 
 * of false indicates that the window should stay open.
 */
public interface ContentPanelCallback
{
	public void okButtonPressed (ContentPanel panel);
	public void cancelButtonPressed (ContentPanel panel);
	public void yesButtonPressed (ContentPanel panel);
	public void noButtonPressed (ContentPanel panel);
	public void closeButtonPressed (ContentPanel panel);
}
