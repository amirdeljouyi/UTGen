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


/**
 * Data that is shared by all the frames/dialogs in the wizard.
 * 
 * <H3>Abstract Class</H3>
 * To create an instantiatable class, a number of methods need to be defined.
 * All of the methods share the same basic behavior: return the WizardPanel 
 * associated with the next/previous/whatever window, given the current window.
 * 
 * <P>
 * Returning null from any of these methods indicates that no window should be
 * displayed.  The getCancelPanel, for example, usually returns null; since the 
 * user wants to quite the whole process when they click cancel.
 * 
 * <P>
 * The methods that should be defined are:
 * 
 * <UL>
 * <LI>getPreviousPanel
 * <LI>getNextPanel
 * <LI>getFinishPanel
 * <LI>getCancelPanel
 * </UL>
 * 
 * <H3>Description</H3>
 * This class helps implement "wizard" style functionality.  With a "wizard" 
 * type approach, the user is presented with a sequence of screens that gather 
 * information that the system uses to perform some operation.  Throughout the 
 * process the user can move forward or backwards through the sequence by using
 * the "next" or "previous" buttons.  This implies that something is keeping 
 * the information entered so far to facilitate such movement.
 * 
 * <P>
 * Keeping track of the information entered so far is where this class comes in.
 * Developers should subclass this class to contain the information gathered at 
 * each stage.
 * 
 * <P>
 * Another function of this class is to determine what the next/previous step 
 * in the process is.  Such decisions are encapsulated by the 
 * get&lt;action&gt;Panel methods.  When the user hits the next button, for example
 * the getNextPanel method is called to determine what should be displayed next.
 * 
 * <P>
 * This class is very tightly coupled with the WizardPanel class.  While it is
 * possible to use it with some other class, it would be difficult to adapt it 
 * to such a use.
 */
public abstract class WizardSession
{
	public abstract WizardPanel getPreviousPanel (WizardPanel currentPanel);
	public abstract WizardPanel getNextPanel (WizardPanel currentPanel);
	public abstract WizardPanel getFinishPanel (WizardPanel currentPanel);
	public abstract WizardPanel getCancelPanel (WizardPanel currentPanel);
	
	public void displayPanel (WizardPanel currentPanel, WizardPanel newPanel)
	{
		if (currentPanel == newPanel)
			return;

		newPanel.reset(this);
		currentPanel.getWindow().setVisible(false);
		newPanel.setVisible(true);
	}
	
	
	public void previousButtonPressed (WizardPanel wizardPanel)
	{
		WizardPanel panel = getPreviousPanel(wizardPanel);
		displayPanel(wizardPanel, panel);
	}
	
	public void nextButtonPressed (WizardPanel wizardPanel)
	{
		WizardPanel panel = getNextPanel(wizardPanel);
		displayPanel(wizardPanel, panel);
	}
	
	public void cancelButtonPressed (WizardPanel currentPanel)
	{
		WizardPanel nextPanel = getCancelPanel(currentPanel);
		displayPanel(currentPanel, nextPanel);
	}
	
	public void finishButtonPressed (WizardPanel currentPanel)
	{
		WizardPanel nextPanel = getFinishPanel(currentPanel);
		displayPanel(currentPanel, nextPanel);
	}
	
}
