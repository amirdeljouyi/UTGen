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
 * Copyright (c) 2004 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.lts.swing.LTSPanel;
import com.lts.swing.contentpanel.ContentPanel;

/**
 * A panel for a "wizard" style frame or dialog.
 * 
 * <P>
 * A wizard style panel is one that walks the user through a sequence of screens
 * in order to gather the information required to perform some operation.  
 * The Eclipse project wizard is one such example.
 * 
 * <P>
 * A wizard panel consists of three sub-panels: a heading/title, a data panel and 
 * a bottom panel.  The top panel, also called the heading/title panel, contains
 * a large font string that describes the current step such as "enter name."
 * 
 * <P>
 * The center panel contains widgets used to obtain information from the user.  
 * For example, the center panel might contain a text input field that allows the
 * user to enter the name for the new class.  It might contain another field 
 * to allow the user to enter the package that the new class should live in.
 * 
 * <P>
 * The bottom panel contains buttons to allow the user to navigation the wizard
 * process.  The standard buttons are previous, next, finish and cancel.
 */
public class WizardPanel extends ContentPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected WizardSession mySession;
	protected JButton myPreviousButton;
	protected JButton myNextButton;
	protected JButton myFinishButton;
	protected JButton myCancelButton;
	
	public WizardSession getSession()
	{
		return mySession;
	}
	
	public void setSession (WizardSession session)
	{
		mySession = session;
	}
	
	
	public void previousButtonPressed ()
	{
		getSession().previousButtonPressed(this);	
	}
	
	public void nextButtonPressed ()
	{
		getSession().nextButtonPressed(this);
	}
	
	public void finishButtonPressed ()
	{
		getSession().finishButtonPressed(this);
	}
	
	public void cancelButtonPressed ()
	{
		getSession().cancelButtonPressed(this);
	}
	
	
	public JPanel createBottomPanel ()
	{
		LTSPanel panel = new LTSPanel();
		
		myPreviousButton = new JButton("Previous");
		myPreviousButton.setEnabled(false);
		myPreviousButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				previousButtonPressed();
			}
		});
		panel.addButton(myPreviousButton, getDefaultInsets());
		
		myNextButton = new JButton("Next");
		myNextButton.setEnabled(false);
		myNextButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				nextButtonPressed();
			}
		});
		panel.addButton(myNextButton, getDefaultInsets());
		
		
		myFinishButton = new JButton("Finish");
		myFinishButton.setEnabled(false);
		myFinishButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				finishButtonPressed();
			}
		});
		panel.addButton(myFinishButton, getDefaultInsets());
		
		myCancelButton = new JButton("Cancel");
		myCancelButton.setEnabled(true);
		myCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				cancelButtonPressed();
			}
		});
		panel.addButton(myCancelButton, getDefaultInsets());
		
		return panel;
	}
	
	
	public void reset (WizardSession session)
	{
		setSession(session);
	}
}
