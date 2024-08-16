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
package com.lts.swing.rootpane;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.WindowListener;

import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

/**
 * Present a single interface for the differnt root panes.
 * 
 * <P>
 * This interface allows clients to use a single interface with which to perform a 
 * variety of "window-like" operations.  At the of writing, the following classes 
 * are considered "root panes" by Java:
 * <UL>
 * <LI>JApplet
 * <LI>JDialog
 * <LI>JFrame
 * <LI>JInternalFrame
 * <LI>JWindow
 * </UL>
 * 
 * <P>
 * The operations or properties supported include the following:
 * 
 * <UL>
 * <LI>closeAction
 * <LI>location
 * <LI>menuBar
 * <LI>size
 * <LI>title
 * <LI>windowListener
 * </UL>
 * 
 * <P>
 * Because Java provides no generic way to perform these operations, clients who want
 * to write the code to handle root panes are forced to deal with them on a class-by-class
 * basis.  JApplet, for example, supports size and location, but not closeAction, 
 * menuBar and title.  JDialog, on the other hand, supports everything except menuBar.
 * 
 * <P>
 * This interface solves these problems by providing accessors for all properties. 
 * Unsupported properties always return null for their getter method, and ignore passed
 * values for the setter.  Clients can determine if a property is supported by calling
 * supports&lt;property name&gt; For example, {@link #supportsMenuBar()} indicates if
 * the root pane supports menuBar.
 * 
 * <P>
 * closeAction refers to what should happen if the window is closed via the 
 * operating system close window operation.  For example, in windows, if one clicks
 * on the "X" button in the upper right hand corner or, from the task bar, if the 
 * user right-clicks and selects close from the pop-up.
 * 
 * <P>
 * closeAction is rather anomylous in that it returns an Integer but it set with an 
 * int.  This is because the closeAction cannot be set to null, though, if it is not
 * supported, the {@link #getCloseAction()} method will return null.
 * 
 * <P>
 * If closeAction is supported by an instance of this interface, then the integer value
 * of closeAction will correspond to one of the values defined by 
 * {@link WindowConstants}: 
 * <UL>
 * <LI>{@link WindowConstants#DISPOSE_ON_CLOSE} --- free the window resources.
 * <LI>{@link WindowConstants#DO_NOTHING_ON_CLOSE} --- ignore the close request and keep
 * the window open.
 * <LI>{@link WindowConstants#EXIT_ON_CLOSE} --- terminate the application.
 * <LI>{@link WindowConstants#HIDE_ON_CLOSE} --- make the window invisible
 * </UL>
 * 
 * <P>
 * Each of the above constants also correspond to a constant defined by this interface
 * to have the same value as their counterpart in the WindowConstants class:
 * <UL>
 * <LI>CLOSE_DISPOSE --- DISPOSE_ON_CLOSE
 * <LI>CLOSE_DO_NOTHING --- DO_NOTHING_ON_CLOSE
 * <LI>CLOSE_EXIT --- EXIT_ON_CLOSE
 * <LI>CLOSE_HIDE --- HIDE_ON_CLOSE
 * </UL>
 * 
 * <P>
 * Either constant can be used.  The CLOSE_ constants were defined because it makes 
 * it a bit clearer what the options for {@link #setCloseAction(Integer)} are.
 * 
 * <P>
 * All instances of this interface will ACCEPT a related call without throwing an
 * exception.  Only some instances of this interface actually IMPLEMENT the methods.
 * Clients can determin whether an instance supports a given operation by calling
 * the various "supports" methods, {@link #supportsCloseAction()}, for example.
 *  
 * 
 * @author cnh
 *
 */
public interface LTSRootPane
{
	public static final int CLOSE_DO_NOTHING = WindowConstants.DO_NOTHING_ON_CLOSE;
	public static final int CLOSE_DISPOSE = WindowConstants.DISPOSE_ON_CLOSE;
	public static final int CLOSE_EXIT_JAVA = WindowConstants.EXIT_ON_CLOSE;
	public static final int CLOSE_HIDE = WindowConstants.HIDE_ON_CLOSE;
	
	public Component getComponent();
	
	public void setCloseAction(Integer closeAction);
	public Integer getCloseAction();
	public boolean supportsCloseAction();
	
	public void setMenuBar(JMenuBar mb);
	public JMenuBar getMenuBar();
	public boolean supportsMenuBar();
	
	public void setTitle(String title);
	public String getTitle();
	public boolean supportsTitle();
	
	
	public Container getContentPane();
	
	public void addWindowListener (WindowListener listener);
	public void removeWindowListener (WindowListener listener);
	public boolean supportsWindowListener();
	
	public void setAlwaysOnTop (boolean alwaysOnTop);
	public boolean getAlwaysOnTop ();
	public boolean supportsAlwaysOnTop();
	
	public boolean getModal();
	public void setModal (boolean isModal);
	public boolean supportsModal ();
	
	public void setVisible(boolean isVisible);
	public boolean getVisible();
	
	public Window getWindow();
}