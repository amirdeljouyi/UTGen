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
 * A class to allow clients to explicitly throw exceptions inside ActionListeners,
 * where exceptions are handled by using Application.showException.
 * 
 * <P>
 * This class allows clients to signal that they may throw exceptions inside an 
 * {@link ActionListener} by simplying using the class.  Furthermore, clients define
 * a default policy by signaling that such exceptions will be handled by calling
 * {@link Application#showException(Throwable)}.
 * 
 * @author cnh
 */
abstract public class CheckedApplicationActionListener implements ActionListener
{
	abstract public void checkedAction(ActionEvent evnet);
	
	public void actionPerformed(ActionEvent event)
	{
		try
		{
			checkedAction(event);
		}
		catch (Exception e)
		{
			Application.showException(e);
		}
	}
	
	
	public CheckedApplicationActionListener ()
	{
	}
}
