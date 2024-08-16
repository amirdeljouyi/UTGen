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

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import com.lts.LTSException;

/**
 * @author cnh
 */
public class TextWindow extends StandardFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextComponent myTextComponent;
	
	public static final int TYPE_SIMPLE = 0;
	public static final int TYPE_HTML = 1;
	public static final int TYPE_EDITOR = 2;
	public static final int TYPE_YES_NO = 3;
	
	
	public TextWindow () throws LTSException
	{
		super();
	}
	
	
	public TextWindow (int textType) throws LTSException
	{
		super();
		initializeTW(TYPE_SIMPLE, "", "");
	}
	
	public TextWindow (int textType, String heading, String message) throws LTSException
	{
		super();
		initializeTW(textType, heading, message);
	}
	
	
	public void initializeTW (int textType, String heading, String text)
		throws LTSException
	{
		switch (textType)
		{
			case TYPE_HTML :
				myTextComponent = new JTextPane();
				break;
			
			case TYPE_EDITOR :
				myTextComponent = new JEditorPane();
				break;
				
			case TYPE_YES_NO :
				myTextComponent = new JTextArea();
				break;
			
			case TYPE_SIMPLE :
			default :
				myTextComponent = new JTextArea();
				break;
			
		}
		
		super.initialize();
		
		setHeading(heading);
		setText(text);
		myTextComponent.setCaretPosition(0);
	}
	
	
	public void initialize (int textType, String heading, Throwable t)
		throws LTSException
	{
		String text = LTSException.createStackTrace(t);
		initializeTW(textType, heading, text); 
	}
	
	
	public JPanel createCenterPanel() throws LTSException
	{
		JPanel panel = super.createCenterPanel();
		myTextComponent.setEditable(false);
		JScrollPane jsp = new JScrollPane(myTextComponent);
		panel.add(jsp, SimpleGridBagConstraint.fillConstraint(0,0));
		return panel;
	}
	
	
	public static final int OK = 0;
	public static final int OK_CANCEL = 1;
	
	public JPanel createBottomPanel()
	{
		return createBottomPanel(OK);
	}
	
	public void setText (String text)
	{
		myTextComponent.setText(text);	
	}
	
	public String getText()
	{
		return myTextComponent.getText();
	}
	
	public TextWindow (String headingText, String text)
		throws LTSException
	{
		initializeTW(TYPE_SIMPLE, headingText, text);
	}
	
	public TextWindow (Throwable t) throws LTSException
	{
		initialize(TYPE_SIMPLE, "Exception", t);
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
	
	
	public static void showException (Throwable t)
	{
		try
		{
			TextWindow win = new TextWindow(t);
			win.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void showException (String message, Throwable t)
	{
		try
		{
			StringWriter sw = new StringWriter(8192);
			PrintWriter out = new PrintWriter(sw);
			
			out.println (message);
			out.println();
			out.println ("Exception:");
			out.println();
			out.println (LTSException.createStackTrace(t));
			
			out.close();
			TextWindow win = new TextWindow("Exception", sw.toString());
			win.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void showText (String message)
	{
		try
		{
			TextWindow win = new TextWindow("Message", message);
			win.setVisible(true);
		}
		catch (LTSException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static final int OPTION_YES = 0;
	public static final int OPTION_NO = 1;
}
