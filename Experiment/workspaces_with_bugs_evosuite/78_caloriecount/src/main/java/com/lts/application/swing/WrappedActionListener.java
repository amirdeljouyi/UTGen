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
 * An AbstractAction that handles exceptions.
 * <H2>Abstract Class</H2>
 * Subclasses must define the following methods to be instantiateable:
 * <UL>
 * <LI>{@link #basicAction(ActionEvent)}
 * </UL>
 * <H2>Description</H2>
 * This class handles the situation where a implementer of {@link ActionListener} throws
 * an exception in the {ActionListener{@link #actionPerformed(ActionEvent)}} method by
 * calling {@link Application#showException(Throwable)} when such an event occurs.
 * 
 * <P>
 * Subclasses must implement {@link #basicAction(ActionEvent)} rather than 
 * actionPerformed, and basicAction can throw any subclass of {@link Exception}
 * that it wants to.
 * 
 * <P>
 * The basic problem that this class solves is to allow applications to define their
 * exception handling policy for ActionEvents in a single place, rather than having to
 * to place references to this class in all their UI code.
 * 
 * <P>
 * This class defines the actionPerformed method to call basicAction; and this call is
 * wrapped in a try...catch block. The catch watches for java.lang.Exception and calls
 * {@link Application#showException(Throwable)} if it encounters one.
 * 
 * @author cnh
 */
abstract public class WrappedActionListener implements ActionListener
{
	abstract public void processException (Exception exception, ActionEvent event, ActionListener listener);
	
	protected ActionListener actionListener;
	
	public WrappedActionListener (ActionListener listener)
	{
		this.actionListener = listener;
	}
	
	
	public void actionPerformed(ActionEvent event)
	{
		try
		{
			this.actionListener.actionPerformed(event);
		}
		catch (Exception exception)
		{
			ActionListener listener = this;
			if (null != this.actionListener)
				listener = this.actionListener;
			
			processException(exception, event, listener);
		}
	}	
}
