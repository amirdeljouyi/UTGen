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
/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lts.LTSException;

/**
 * Display a dialog box with a readonly, scrollable text pane.
 * 
 * @author cnh
 */
public class TextDialog 
	extends StandardDialog 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea myTextArea;
	
	public JPanel createCenterPanel()
	{
		JPanel panel = super.createCenterPanel();
		myTextArea = new JTextArea();
		myTextArea.setEditable(false);
		JScrollPane jsp = new JScrollPane(myTextArea);
		panel.add(jsp, SimpleGridBagConstraint.fillConstraint(0,0));
		return panel;
	}
	
	
	public JPanel createBottomPanel()
	{
		return createBottomPanel(OK);
	}
	
	public void setText (String text)
	{
		myTextArea.setText(text);	
	}
	
	public String getText()
	{
		return myTextArea.getText();
	}
	
	public TextDialog (String headingText, String text)
	{
		initialize();
		setup(headingText, text);
	}
	
	public TextDialog (String text)
	{
		initialize();
		setup ("", text);
	}
	
	public TextDialog (String headingText, Throwable t)
	{
		initialize();
		setup(headingText, t);
	}
	
	public TextDialog (Throwable t)
	{
		initialize();
		setup("Exception", t);
	}
	
	public void setup (String headingText, String text)
	{
		setHeading(headingText);
		setText(text);
	}
	
	public void setup (String headingText, Throwable t)
	{
		String text = LTSException.createStackTrace(t);
		setup(headingText, text);
	}
	
	
	public static void showException (String message, Throwable t)
	{
		TextDialog d = new TextDialog(message, t);
		d.setVisible(true);
	}
}
