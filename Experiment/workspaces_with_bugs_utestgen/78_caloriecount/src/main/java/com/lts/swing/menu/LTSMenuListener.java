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
package com.lts.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JMenuItem;

import com.lts.LTSException;
import com.lts.swing.display.ErrorContentPanel;

/**
 * @author cnh
 */
public class LTSMenuListener implements ActionListener 
{	
	/**
	 * The object that we call methods on in response to menu items getting
	 * selected.
	 */
	protected Object myReceiver;	
	protected Class myReceiverClass;
	
	/**
	 * A map from JMenuItems to MenuMapping objects that identifies the method that
	 * should be invoked in response to a menu item being selected.
	 * 
	 * <P/>
	 * If no mapping exists for a particular item, the event is ignored.
	 */
	protected Map mySourceToMappingMap;
	
	public LTSMenuListener (Object receiver, Map sourceToMappingMap)
	{
		initialize(receiver, sourceToMappingMap);
	}
	
	public void initialize (Object receiver, Map sourceToMappingMap)
	{
		myReceiver = receiver;
		mySourceToMappingMap = sourceToMappingMap;
		myReceiverClass = receiver.getClass();
		
		Iterator i = sourceToMappingMap.keySet().iterator();
		while (i.hasNext())
		{
			JMenuItem item = (JMenuItem) i.next();
			item.addActionListener(this);
		}
	}
	
	public static Object[] voidArguments = new Object[0];
	
	public MenuMapping sourceToMapping (Object src)
	{
		MenuMapping m = (MenuMapping) mySourceToMappingMap.get(src);
		return m;
	}


	public void actionPerformed (ActionEvent event)
	{
		try
		{
			MenuMapping m = sourceToMapping(event.getSource());
			if (null == m)
				return;
			else
				m.invoke(myReceiver);
		}
		catch (LTSException e)
		{
			ErrorContentPanel.showException(e);
		}
	}

}
