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

import java.awt.Container;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.lts.swing.LTSPanel;

/**
 * An object that watches for changes in the window that a Container lives in and 
 * that sets the window title when that window is changed.
 * 
 * <P>
 * On creation, an instance of this class is notified when ContainerListener events
 * occur.  When the monitored Container's Container is changed, the instance will 
 * trace up the hierarchy until it finds the root Container.  If the Container is 
 * capable of having a title, then the instance will set the title of the root to
 * the title it is set to in this class.
 * 
 * <P>
 * The container to be monitored cannot be changed once the object is created.  If
 * a null value is used in the constructor, this class will do nothing.  
 * 
 * <P>
 * The instance watches for changes in the container of the monitored container.  
 * Directly or indirectly, this is the window where the monitored container lives.
 * When the monitored container is put into a window or some other containing 
 * object, this instance will be notified, and it will, in turn, set the title of 
 * the containing window.
 * 
 * <P>
 * The title can be changed by calling {@link #changeTitle(String)}.
 * 
 * <P>
 * Only certain "windows" can have their titles set.  In particular, applets 
 * {@link JApplet} and subclasses of {@link JWindow} do not have titles.  So long 
 * as one of those classes is used to contain the monitored Container, this class
 * will not do anything.
 * 
 * @author cnh
 *
 */
public class TitleDecorator
{
	protected String myTitle;
	protected Container myMonitoredContainer;
	
	/**
	 * Create an instance and start monitoring the specified Container.
	 * 
	 * <P>
	 * Note that the container parameter is the container that the caller wants 
	 * us to monitor, the "monitored container" in the class documentation.  
     *
	 * @param container The Container the caller wants to set the title for.
	 * @param title The title to set the "root pane" to.
	 * 
	 * @exception NullPointerException This exception is thrown if the container 
	 * parameter parameter is null.
	 */
	public TitleDecorator (Container container, String title)
	{
		initialize(container, title);
	}
	
	/**
	 * Start monitoring the specified Container and set its title.
	 * 
	 * @param container The container to monitor.
	 * @param title The value for the title.  Note that this value may be null,
	 * in which case the title for the root pane is removed.
	 * @exception NullPointerException This exception is thrown if the container
	 * parameter is null.
	 */
	protected void initialize (Container container, String title)
	{
		myMonitoredContainer = container;
		
		ContainerListener listener = new ContainerListenerAdaptor() {
			public void componentAdded (ContainerEvent event) {
				containerChanged(event.getContainer());
			}
		};
		
		myTitle = title;
		myMonitoredContainer.addContainerListener(listener);
		
		setWindowTitle(myMonitoredContainer);
	}
	
	/**
	 * Change the title for the monitored container.
	 * 
	 * <P>
	 * This method will also change the title for the root pane, assuming the 
	 * monitored component has one.
	 * 
	 * @param newTitle The new title for the root pane.  Setting this to null removes
	 * the root pane's title.
	 */
	public void changeTitle (String newTitle)
	{
		myTitle = newTitle;
		setWindowTitle(myMonitoredContainer);
	}
	
	/**
	 * The container of the monitored container has changed --- reset the title 
	 * of the root pane.
	 *  
	 * @param container The new parent for the monitored container.  If this is 
	 * null, then the method simply returns.
	 */
	protected void containerChanged (Container container)
	{
		if (null != container)
		{
			setWindowTitle(container);
		}
	}
	
	protected boolean isRootPane (Container cont)
	{
		return 
			cont instanceof JFrame
			|| cont instanceof JDialog
			|| cont instanceof JInternalFrame;
	}
	
	/**
	 * Set the title of the monitored container's root pane.
	 * 
	 * <P>
	 * Note that it is quite possible for the monitored container to not have a 
	 * root pane.  For example, when a {@link JPanel} or {@link LTSPanel} is first
	 * created, it has non root pane.
	 * 
	 * <P>
	 * Note also that only certain types of root pane have titles.  These include 
	 * {@link JFrame}, {@link JDialog}, and {@link JInternalFrame}.  Specifically
	 * excluded are {@link JWindow} and {@link JApplet}.
	 * 
	 * @param container The new container for the monitored container.  If this is 
	 * null, the method simply returns.
	 */
	protected void setWindowTitle (Container container)
	{
		while (null != container && !isRootPane(container))
			container = container.getParent();
		
		if (container instanceof JFrame)
		{
			JFrame frame = (JFrame) container;
			frame.setTitle(myTitle);
		}
		else if (container instanceof JDialog)
		{
			JDialog dialog = (JDialog) container;
			dialog.setTitle(myTitle);
		}
		else if (container instanceof JInternalFrame)
		{
			JInternalFrame frame = (JInternalFrame) container;
			frame.setTitle(myTitle);
		}
	}
}
