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
import java.util.HashSet;
import java.util.Set;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JWindow;

/**
 * Given a container, return an instance of LTSRootPane.
 * 
 * <P>
 * The primary "entry point" to this class is {@link #buildRootPane(Container)},
 * which returns the implementation of {@link LTSRootPane} for the container.  Not 
 * every {@link Container} is a root pane, however, so the method may return null 
 * if it is called.  Clients can determine ahead of time if the Container is supported
 * by calling {@link #objectSupported(Containter)}.
 * 
 * @author cnh
 */
public class DefaultRootPaneFactory implements LTSRootPaneFactory
{
	protected static final Class[] SPEC_SUPPORTED = {
		JApplet.class,
		JDialog.class,
		JFrame.class,
		JInternalFrame.class,
		JWindow.class
	};
	
	protected static Set supported;
	
	protected static Set buildSupported (Class[] spec)
	{
		Set s = new HashSet();
		
		for (int i = 0; i < spec.length; i++)
		{
			s.add(spec[i]);
		}
		
		return s;
	}
	
	
	protected static boolean classSupported (Class clazz)
	{
		if (null == supported)
		{
			supported = buildSupported(SPEC_SUPPORTED);
		}
		
		return supported.contains(clazz);
	}
	
	
	protected static boolean instanceSupported (Object o)
	{
		Class clazz = o.getClass();
		return classSupported(clazz);
	}
	
	
	public LTSRootPane buildRootPane (Container container)
	{
		LTSRootPane root = null;
		
		if (container instanceof JWindow)
		{
			JWindow win = (JWindow) container;
			root = new RootPaneJWindow(win);
		}
		else if (container instanceof JDialog)
		{
			JDialog dialog = (JDialog) container;
			root = new RootPaneJDialog(dialog);
		}
		else if (container instanceof JApplet)
		{
			JApplet applet = (JApplet) container;
			root = new RootPaneJApplet(applet);
		}
		else if (container instanceof JInternalFrame)
		{
			JInternalFrame jif = (JInternalFrame) container;
			root = new RootPaneJInternalFrame(jif);
		}
		else if (container instanceof JFrame)
		{
			JFrame frame = (JFrame) container;
			root = new RootPaneJFrame(frame);
		}
		
		return root;
	}
	
	public boolean objectSupported (Container container)
	{
		return instanceSupported(container);
	}
}
