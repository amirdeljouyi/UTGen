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
package com.lts.event;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * A KeyListener that only listens for a subset of the key events that are 
 * frequently of interest to applications.
 * 
 * <H2>Description</H2>
 * The subset of keys that this class watches for are:
 * <UL>
 * <LI/>enter
 * <LI/>insert
 * <LI/>delete
 * <LI/>tab
 * </UL>
 * 
 * <P>
 * When these events occur, this class notifies the SimpleKeyListeners that 
 * registered an interest in such changes.
 * 
 * @author cnh
 */
public class SimpleKeyListenerHelper extends ListenerHelper implements KeyListener
{
	private Component component;
	
	public SimpleKeyListenerHelper (Component component)
	{
		component.addKeyListener(this);
		this.component = component;
	}
	
	public void detach ()
	{
		if (null != this.component)
		{
			this.component.removeKeyListener(this);
			this.component = null;
		}
	}
	
	public void notifyListener(Object o, int type, Object data)
	{
		SimpleKeyListener listener = (SimpleKeyListener) o;
		
		switch (type)
		{
			case KeyEvent.VK_ENTER :
				listener.enter(component);
				break;
				
			case KeyEvent.VK_INSERT :
				listener.insert(component);
				break;
				
			case KeyEvent.VK_DELETE :
				listener.delete(component);
				break;
				
			case KeyEvent.VK_TAB :
				listener.tab(component);
				break;
		}
	}

	public void keyPressed(KeyEvent event)
	{
		switch (event.getKeyCode())
		{
			case KeyEvent.VK_ENTER :
			case KeyEvent.VK_INSERT :
			case KeyEvent.VK_DELETE :
			case KeyEvent.VK_TAB :
				this.fire(event.getKeyCode());
				break;
		}
	}

	public void keyReleased(KeyEvent arg0)
	{
		
	}

	public void keyTyped(KeyEvent event)
	{
	}
}
