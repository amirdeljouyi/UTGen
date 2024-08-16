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
package com.lts.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

/**
 * Call a method in response to a menu item being selected.
 * 
 * <P>
 * This class is actually applicable to all objects that accept listeners who
 * implement {@link ActionListener}, though it was developed for menus.
 *
 * <P>
 * Subclasses must implement {@link #processException(Exception)} in order to be 
 * instantiateable.
 * 
 * <P>
 * This class invokes a method on a particular instance when {@link #actionPerformed(ActionEvent)}
 * is called.  The method invocation is wrapped in a try...catch block that catches
 * java.lang.Exception and all its subclasses --- thus all checked and unchecked exceptions.
 * 
 * <P>
 * The underlying method is expected to require no arguments, though it may be 
 * a class or instance method.  To use a static (class) method, pass null as  the 
 * receiver to the classes constructor.
 * 
 * @author cnh
 */
public abstract class MethodMenuAction implements ActionListener
{
	abstract public void processException (Exception exception);
	
	protected Method method;
	protected Object receiver;
	
	public MethodMenuAction (Object theReceiver, Method theMethod)
	{
		initialize(theReceiver, theMethod);
	}
	
	
	public void initialize (Object theReceiver, Method theMethod)
	{
		this.method = theMethod;
		this.method.setAccessible(true);
		this.receiver = theReceiver;
	}
	
	public void actionPerformed (ActionEvent event)
	{
		try
		{
			this.method.invoke(receiver);
		}
		catch (Exception e)
		{
			processException(e);
		}
	}
	
}
