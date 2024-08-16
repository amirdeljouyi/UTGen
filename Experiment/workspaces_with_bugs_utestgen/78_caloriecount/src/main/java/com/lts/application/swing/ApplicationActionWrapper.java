//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lts.application.Application;

/**
 * Process exceptions thrown inside ActionListner.actionPerformed by using Application.showException
 * 
 * <P>
 * This class handles {@link RuntimeException} thrown from actionPerformed by using
 * {@link Application#showException(Throwable)} to display the problem.
 * 
 * <H2>Note</H2>
 * This implementation of {@link WrappedActionListener} ignores the {@link ActionEvent} and
 * {@link ActionListener} parameters passed via {@link #processException(Exception, ActionEvent, ActionListener)}
 * <P>
 * 
 * @author cnh
 * @see WrappedActionListener
 * @see Application#showException(Throwable)
 * @see WrappedActionListener#processException(Exception, ActionEvent, ActionListener)
 */
public class ApplicationActionWrapper extends WrappedActionListener
{
	public ApplicationActionWrapper (ActionListener theListener)
	{
		super(theListener);
	}
	
	
	/**
	 * Display exceptions by displaying them with showException.
	 * 
	 * <H2>Note</H2>
	 * This implementation of the method ignores the event and listener arguments.
	 * 
	 * @param listener The ActionListener that threw the exception.
	 * @param event The event that triggeredd the exception.
	 * @param exception The exception thrown.
	 */
	@Override
	public void processException(Exception exception, ActionEvent event, ActionListener listener)
	{
		Application.showException(exception);
	}
}
