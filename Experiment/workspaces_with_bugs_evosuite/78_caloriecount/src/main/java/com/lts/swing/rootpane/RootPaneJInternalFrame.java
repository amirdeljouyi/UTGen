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

import java.awt.Container;
import java.awt.event.WindowListener;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import com.lts.exception.NotImplementedException;

/**
 * An LTSRootPane that wraps an instance of JInternalFrame.
 * 
 * <P>
 * This class supports the following {@link LTSRootPane} properties:
 * <UL>
 * <LI>closeAction
 * <LI>location
 * <LI>menuBar
 * <LI>size
 * <LI>title
 * </UL>
 * 
 * @author cnh
 */
public class RootPaneJInternalFrame extends LTSRootPaneAdaptor
{
	public RootPaneJInternalFrame (JInternalFrame dialog)
	{
		myComponent = dialog;
	}
	
	
	protected JInternalFrame getInternalFrame()
	{
		return (JInternalFrame) myComponent;
	}
	
	
	public Integer getCloseAction()
	{
		return new Integer(getInternalFrame().getDefaultCloseOperation());
	}
	
	public void setCloseAction(Integer operation)
	{
		getInternalFrame().setDefaultCloseOperation(operation);
	}
	
	public boolean supportsCloseAction()
	{
		return true;
	}

	
	public boolean supportsLocation()
	{
		return true;
	}
	

	public boolean supportsSize()
	{
		return true;
	}

	
	public String getTitle()
	{
		return getInternalFrame().getTitle();
	}
	
	public void setTitle (String title)
	{
		getInternalFrame().setTitle(title);
	}
	
	public boolean supportsTitle()
	{
		return true;
	}
	
	
	public JMenuBar getMenuBar()
	{
		return getInternalFrame().getJMenuBar();
	}
	
	public void setMenuBar(JMenuBar menuBar)
	{
		getInternalFrame().setJMenuBar(menuBar);
	}
	
	public boolean supportsMenuBar()
	{
		return true;
	}
	
	public Container getContentPane()
	{
		return getInternalFrame().getContentPane();
	}
	
	
	public boolean supportsWindowListener()
	{
		return false;
	}
	
	public void addWindowListener(WindowListener listener)
	{
		throw new NotImplementedException();
	}
	
	public void removeWindowListener(WindowListener listener)
	{
		throw new NotImplementedException();
	}
	

	
	public boolean supportsModal()
	{
		return false;
	}
	
	public boolean getModal()
	{
		throw new NotImplementedException();
	}
	
	public void setModal(boolean modal)
	{
		throw new NotImplementedException();
	}

	
	public boolean supportsAlwaysOnTop()
	{
		return false;
	}

	public void setAlwaysOnTop(boolean alwaysOnTop)
	{
		throw new NotImplementedException();
	}
	
	public boolean getAlwaysOnTop()
	{
		throw new NotImplementedException();
	}
}
