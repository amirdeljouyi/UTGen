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
import java.awt.Window;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.lts.exception.NotImplementedException;
import com.lts.util.MoreArrayUtils;

/**
 * An LTSRootPane that wraps an instance of JInternalFrame.
 * 
 * <P>
 * This class supports the following {@link LTSRootPane} properties:
 * <UL>
 * <LI>actionOnClose
 * <LI>location
 * <LI>menuBar
 * <LI>size
 * <LI>title
 * <LI>windowListener
 * </UL>
 * 
 * @author cnh
 */
public class RootPaneJFrame extends LTSRootPaneAdaptor
{
	
	public RootPaneJFrame (JFrame dialog)
	{
		myComponent = dialog;
	}

	public JFrame getJFrame()
	{
		return (JFrame) myComponent;
	}
	
	public Window getWindow()
	{
		return getJFrame();
	}
	
	public Integer getCloseAction()
	{
		return new Integer(getJFrame().getDefaultCloseOperation());
	}

	public JMenuBar getMenuBar()
	{
		return getJFrame().getJMenuBar();
	}

	public String getTitle()
	{
		return getJFrame().getTitle();
	}

	public void setCloseAction(Integer action)
	{
		getJFrame().setDefaultCloseOperation(action);
	}


	public void setMenuBar(JMenuBar menuBar)
	{
		getJFrame().setJMenuBar(menuBar);
	}

	public void setTitle (String title)
	{
		getJFrame().setTitle(title);
	}

	public boolean supportsCloseAction()
	{
		return true;
	}

	public boolean supportsMenuBar()
	{
		return true;
	}

	public boolean supportsTitle()
	{
		return true;
	}

	public Container getContentPane()
	{
		return getJFrame().getContentPane();
	}
	
	public void addWindowListener(WindowListener listener)
	{
		WindowListener[] current = getJFrame().getWindowListeners();
		if (MoreArrayUtils.contains(listener, current))
			return;
		
		getJFrame().addWindowListener(listener);
	}
	
	public void removeWindowListener(WindowListener listener)
	{
		WindowListener[] current = getJFrame().getWindowListeners();
		if (MoreArrayUtils.contains(listener, current))
			return;
		
		getJFrame().removeWindowListener(listener);
	}
	
	public boolean supportsWindowListener()
	{
		return true;
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
