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
package com.lts.pest.error;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.ExceptionPanel;
import com.lts.swing.LTSPanel;

@SuppressWarnings(value="serial")
public class ContinueQuitPanel extends ExceptionPanel
{
	private static final long serialVersionUID = 1L;

	public static final String PREFIX = ContinueQuitPanel.class.getName();
	public static final String BUTTON = PREFIX + ".button.";
	public static final String PROMPT = PREFIX + ".prompt.";
	
	public static final String BUTTON_QUIT = BUTTON + "quit";
	public static final String BUTTON_CONTINUE = BUTTON + "continue";
	public static final String BUTTON_DETAILS = BUTTON + "details";
	public static final String TEXT_ASK_CONTINUE = PROMPT + ".askContinue";
	
	public ContinueQuitPanel(String message, Throwable ex, Window win)
			throws LTSException
	{
		super(message, ex, win);
	}
	
	public static String STR_QUIT;
	public static String STR_CONTINUE;
	public static String STR_PROMPT;
	public static String STR_DETAILS;
	public static boolean messagesLoaded = false;
	
	static public void loadMessages()
	{
		if (!messagesLoaded)
			basicLoadMessages();
	}
	
	synchronized static public void basicLoadMessages()
	{
		Application app = Application.getInstance();
		
		String def = "Quit";
		STR_QUIT = app.getDefaultMessage(def,BUTTON_QUIT);
		
		def = "Continue";
		STR_CONTINUE = app.getDefaultMessage(def, BUTTON_CONTINUE);
		
		def = "Severe application error.  Continue?";
		STR_PROMPT = app.getDefaultMessage(def, TEXT_ASK_CONTINUE);
		
		def = "Show Details...";
		STR_DETAILS = app.getDefaultMessage(def, BUTTON_DETAILS);
	}
	
	protected boolean quitApplication;
	
	
	public void resume ()
	{
		if (null != this.suspend)
			this.suspend.resume();
	}
	
	public void quitButton ()
	{
		this.quitApplication = true;
		this.setVisible(false);
		resume();
	}
	
	public void continueButton ()
	{
		this.quitApplication = false;
		this.setVisible(false);
		resume();
	}
	
	public void detailsButton()
	{
		super.details();
	}
	
	public JPanel createBottomPanel ()
	{
		LTSPanel panel = new LTSPanel();

		ActionListener listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				quitButton();
			}
		};
		
		JButton button = new JButton(STR_QUIT);
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				continueButton();
			}
		};
		
		button = new JButton(STR_CONTINUE);
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				detailsButton();
			}
		};
		button = new JButton(STR_DETAILS);
		button.addActionListener(listener);
		panel.addButton(button, 5);
		
		return panel;
	}
	
}
