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
package com.lts.scheduler;

public class InternalScheduledEvent extends SchedulerEvent
{
	public ScheduledEventListener listener;
	
	public InternalScheduledEvent (ScheduledEventListener theListener, long theTime)
	{
		super(theTime, null);
		this.listener = theListener;
	}
	
	public InternalScheduledEvent (ScheduledEventListener theListener, long theTime, Object theData)
	{
		super(theTime, theData);
		listener = theListener;
	}

	public ScheduledEventListener getListener()
	{
		return this.listener;
	}
}
