//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.test;

import java.awt.Container;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.panel.RootPaneContentPanel;

public class TestContentPanel extends RootPaneContentPanel
{
	private static final long serialVersionUID = 1L;

	public TestContentPanel (Window win, String newTitle) throws LTSException
	{
		super();
		initialize(win, newTitle);
	}
	
	
	public TestContentPanel (Container cont, String newTitle) throws LTSException
	{
		super();
		initialize(cont, newTitle);
	}
	
	
	public void initialize (Container cont, String newTitle) throws LTSException
	{
		setWindowTitle(newTitle);
		initialize(cont);
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Hi There");
		panel.addLabel(label);
		
		return panel;
	}
	
	public void okButtonPressed ()
	{
		super.okButtonPressed();
		System.exit(0);
	}
}
