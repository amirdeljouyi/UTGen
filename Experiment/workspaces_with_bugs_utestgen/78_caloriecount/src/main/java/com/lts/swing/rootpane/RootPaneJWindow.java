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

import javax.swing.JMenuBar;
import javax.swing.JWindow;

import com.lts.exception.NotImplementedException;
import com.lts.util.MoreArrayUtils;

/**
 * An LTSRootPane that contains an instance of JWindow.
 * 
 * <P>
 * JWindow supports the following:
 * <UL>
 * <LI>location
 * <LI>size
 * <LI>windowListener
 * </UL>
 * 
 * @author cnh
 */
public class RootPaneJWindow extends LTSRootPaneAdaptor
{
	public RootPaneJWindow (JWindow window)
	{
		myComponent = window;
	}
	

	protected JWindow getJWindow()
	{
		return (JWindow) myComponent;
	}
	
	public Window getWindow()
	{
		return getJWindow();
	}
	
	public Container getContentPane()
	{
		return getJWindow().getContentPane();
	}
	
	public void addWindowListener(WindowListener listener)
	{
		WindowListener[] current = getJWindow().getWindowListeners();
		if (MoreArrayUtils.contains(listener, current))
			return;
		
		getJWindow().addWindowListener(listener);
	}
	
	public void removeWindowListener(WindowListener listener)
	{
		WindowListener[] current = getJWindow().getWindowListeners();
		if (MoreArrayUtils.contains(listener, current))
			return;
		
		getJWindow().removeWindowListener(listener);
	}
	
	public boolean supportsWindowListener()
	{
		return true;
	}
	
	
	public boolean supportsCloseAction()
	{
		return false;
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

	
	public Integer getCloseAction()
	{
		throw new NotImplementedException();
	}

	public JMenuBar getMenuBar()
	{
		throw new NotImplementedException();
	}

	public String getTitle()
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



	public void setCloseAction(Integer closeAction)
	{
		throw new NotImplementedException();
	}

	
	public boolean supportsMenuBar()
	{
		return false;
	}

	public void setMenuBar(JMenuBar mb)
	{
		throw new NotImplementedException();
	}

	public void setTitle(String title)
	{
		throw new NotImplementedException();
	}

	public boolean supportsTitle()
	{
		return false;
	}	
}
