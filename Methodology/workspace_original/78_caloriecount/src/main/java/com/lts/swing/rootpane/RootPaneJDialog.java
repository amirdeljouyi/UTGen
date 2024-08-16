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

import javax.swing.JDialog;
import javax.swing.JMenuBar;

import com.lts.exception.NotImplementedException;
import com.lts.util.MoreArrayUtils;

/**
 * An LTSRootPane that wraps an instance of JDialog.
 * 
 * <P>
 * JDialog supports the following properties:
 * <UL>
 * <LI>closeAction
 * <LI>location
 * <LI>size
 * <LI>title
 * <LI>windowListener
 * </UL>
 * 
 * @author cnh
 */
public class RootPaneJDialog extends LTSRootPaneAdaptor
{
	protected JDialog getDialog()
	{
		return (JDialog) myComponent;
	}
	
	public Window getWindow()
	{
		return getDialog();
	}
	
	public RootPaneJDialog (JDialog dialog)
	{
		myComponent = dialog;
	}
	
	
	public Integer getCloseAction()
	{
		return new Integer(getDialog().getDefaultCloseOperation());
	}
	
	public void setCloseAction(int operation)
	{
		getDialog().setDefaultCloseOperation(operation);
	}
	
	public boolean supportsCloseAction()
	{
		return true;
	}

	
	public String getTitle()
	{
		return getDialog().getTitle();
	}
	
	public void setTitle (String title)
	{
		getDialog().setTitle(title);
	}
	
	public boolean supportsTitle()
	{
		return true;
	}
	
	public Container getContentPane()
	{
		return getDialog().getContentPane();
	}
	
	public void addWindowListener(WindowListener listener)
	{
		WindowListener[] current = getDialog().getWindowListeners();
		if (MoreArrayUtils.contains(listener, current))
			return;
		
		getDialog().addWindowListener(listener);
	}
	
	public void removeWindowListener(WindowListener listener)
	{
		WindowListener[] current = getDialog().getWindowListeners();
		if (MoreArrayUtils.contains(listener, current))
			return;
		
		getDialog().removeWindowListener(listener);
	}
	
	public boolean supportsWindowListener()
	{
		return true;
	}
	
	
	public boolean supportsModal()
	{
		return true;
	}
	
	public boolean getModal ()
	{
		return getDialog().isModal();
	}
	
	public void setModal (boolean isModal)
	{
		getDialog().setModal(isModal);
	}
	
	
	public boolean supportsAlwaysOnTop ()
	{
		return true;
	}
	
	public boolean getAlwaysOnTop ()
	{
		return getDialog().isAlwaysOnTop();
	}
	
	public void setAlwaysOnTop (boolean isAlwaysOnTop)
	{
		getDialog().setAlwaysOnTop(isAlwaysOnTop);
	}
	
	
	public void close ()
	{
		throw new NotImplementedException();
	}

	
	public JMenuBar getMenuBar()
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
}
