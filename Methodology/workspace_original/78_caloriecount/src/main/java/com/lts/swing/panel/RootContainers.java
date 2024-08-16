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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public enum RootContainers implements ContainerListener
{
	JFRAME {
		public void basicSetTitle ()
		{
			JFrame frame = (JFrame) myContainer;
			frame.setTitle(myTitle);
		}
	},
	
	JDIALOG {
		public void basicSetTitle()
		{
			JDialog dialog = (JDialog) myContainer;
			dialog.setTitle(myTitle);
		}
	},
	
	JINTERNAL_FRAME {
		public void basicSetTitle()
		{
			JInternalFrame internal = (JInternalFrame) myContainer;
			internal.setTitle(myTitle);
		}
	},
	
	// cannot set the title of an applet
	JAPPLET,
	
	// JWindows do not have things like titles and menu bars
	JWINDOW,
	;

	protected void basicSetTitle()
	{}
	
	protected String myTitle;
	protected Container myContainer;
	
	public void addThisListener ()
	{
		if (null == myTitle)
			return;
		
		if (null == myContainer)
			return;
		
		myContainer.addContainerListener(this);
	}
	
	
	public void removeThisListener ()
	{
		if (null == myTitle)
			return;
		
		if (null == myContainer)
			return;
		
		myContainer.removeContainerListener(this);
	}
	
	
	public void componentAdded(ContainerEvent e)
	{
		removeThisListener();
		myContainer = e.getContainer();
		addThisListener();
		setTitle();
	}
	
	
	public void setTitle ()
	{
		if (null == myContainer)
			return;
		
		if (null == myTitle)
			return;
		
		setTitle();
	}
	

	public void componentRemoved(ContainerEvent e)
	{
		Container container = e.getContainer();
		if (null != container)
			container.removeContainerListener(this);
	}
}
