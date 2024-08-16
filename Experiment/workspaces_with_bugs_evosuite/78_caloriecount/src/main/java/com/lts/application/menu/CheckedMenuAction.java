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
package com.lts.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import com.lts.application.Application;

/**
 * An action listener that invokes an arbitrary Method against an arbitrary Object
 * and handles any exceptions via Application.showException.
 * 
 * <P>
 * The method is expected to accept no parameters, but may return or throw anything.
 * Any returned value is discarded, however.  static methods are useable by supplying
 * null as the receiver.
 * 
 * <P>
 * Any exceptions that inherit from {@link Exception} are caught and processed via 
 * {@link Application#showException(Throwable)}.
 * 
 * @author cnh
 *
 */
public class CheckedMenuAction implements ActionListener
{
	protected static Object[] NO_ARGS = {};
	
	protected Object myReceiver;
	protected Method myMethod;
	
	public CheckedMenuAction (Object receiver, Method method)
	{
		myReceiver = receiver;
		myMethod = method;
		if (null == myMethod)
		{
			throw new IllegalArgumentException("passed null method");
		}
		
		myMethod.setAccessible(true);
	}
	
	public void actionPerformed (ActionEvent event)
	{
		try
		{
			myMethod.invoke(myReceiver, NO_ARGS);
		}
		catch (Exception e)
		{
			Application.showException(e);
		}
	}
}
