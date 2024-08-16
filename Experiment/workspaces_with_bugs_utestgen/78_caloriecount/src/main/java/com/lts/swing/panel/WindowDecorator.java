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
package com.lts.swing.panel;

import java.awt.Window;

/**
 * An object that performs some sort of decoration on a window.
 * 
 * <P>
 * This interface defines the basic java interface that implements the decorator
 * pattern.  Objects contain collections of these decorators and then invoke them
 * at the appropriate time.
 * 
 * @author cnh
 */
public interface WindowDecorator
{
	/**
	 * This is called when the panel is first connected to a window.
	 * 
	 * <P>
	 * The method should only be called on the top-level panel, thus the decorator
	 * should only receive this call once.
	 * 
	 * <P>
	 * Decorators are expected to 
	 * 
	 * @param window The window to attach to.
	 */
	public void attach (Window window);
	
	/**
	 * This is called when the panel is separated from the current window for 
	 * some reason.
	 * 
	 * <P>
	 * For example, if the panel gets a new window, it is required to call detach
	 * with the old window and then call attach with the new window.
	 *  
	 * @param window The window that the panel was previously attached to.
	 */
	public void detach (Window window);
}
