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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ListenerHelper
{
	public abstract void notifyListener (
		Object listener, 
		int type, 
		Object data
	);
	
	transient protected Set listeners = new HashSet();
	transient protected Boolean myDeferFlag = null;
	
	public Set getListeners()
	{
		return this.listeners;
	}
	
	public void setListeners(Set listeners)
	{
		this.listeners = listeners;
		if (null == this.listeners)
			this.listeners = new HashSet();
	}
	
	public void addListener (Object o)
	{
		this.listeners.add(o);
	}
	
	public boolean removeListener (Object o)
	{
		if (null == o)
			return false;
		else
			return this.listeners.remove(o);
	}
	
	
	public void fire ()
	{
		fire (-1, null);
	}
	
	
	public void fire (int type)
	{
		fire (type, null);
	}
	
	public void fire (Object data)
	{
		fire (-1, data);
	}
	
	public void fire (DeferredEvent event)
	{
		fire(event.type, event.data);
	}
	
	public static class DeferredEvent
	{
		public DeferredEvent (int _type, Object _data)
		{
			type = _type;
			data = _data;
		}
		
		public int type;
		public Object data;
	}
	
	
	protected DeferredEvent myDeferredEvent;
	
	
	public void fire (int type, Object data)
	{
		if (null != myDeferFlag && myDeferFlag.booleanValue())
		{
			myDeferredEvent = new DeferredEvent(type, data);
		}
		
		Iterator i = this.listeners.iterator();
		while (i.hasNext())
		{
			Object listener = i.next();
			notifyListener(listener, type, data);
		}
	}
	
	public void fire (int type, Object data1, Object data2)
	{
		Object[] data = new Object[] { data1, data2 };
		fire (type, data);
	}
	
	public void fire (int type, Object data1, Object data2, Object data3)
	{
		Object[] data = new Object[] { data1, data2, data3 };
		fire(type, data);
	}
	
	
	public void deferNotifications()
	{
		myDeferFlag = new Boolean(true);
	}
	
	public void activateNotifications()
	{
		if (null != myDeferFlag && myDeferFlag.booleanValue())
		{
			myDeferFlag = null;
			
			if (null != myDeferredEvent)
			{
				fire(myDeferredEvent);
				myDeferredEvent = null;
			}
		}
	}
	
	/**
	 * Remove any deferred events, resume event delivery, and fire a new event.
	 * 
	 * <P>
	 * This method is used when a client has suspended event delivery and wants
	 * to resume it.  Usually in that situation, clients want forget about any 
	 * previous events and instead tell their listeners that everything has changed.
	 * This method allows clients to do that in one atomic operation. 
	 * </P>
	 * 
	 * <P>
	 * The {@link #activateNotifications()} method can be used in the situation 
	 * where the client does not want to forget about events that occurred while
	 * notifications were deferred.
	 * </P>
	 * 
	 * @param type The new event to fire.
	 * @param data The data for the new event.
	 * @see #activateNotifications() 
	 */
	synchronized public void clearResumeAndFire(int type, Object data)
	{
		myDeferredEvent = null;
		myDeferFlag = null;
		fire(type, data);
	}

	public IllegalArgumentException unknownCode(int code)
	{
		StringBuffer sb = new StringBuffer();
		sb.append ("The event type code, ");
		sb.append(code);
		sb.append(", was not recognized.");
		
		return new IllegalArgumentException(sb.toString());
	}
}
