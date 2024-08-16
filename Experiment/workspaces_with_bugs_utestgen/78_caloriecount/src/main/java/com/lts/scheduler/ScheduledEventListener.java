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

/**
 * An "end-user" of the Scheduler system.
 * 
 * <P>
 * Clients register to receive periodic events by using {@link Scheduler}; but are 
 * required to present an instance of this interface.  At or after each period, 
 * {@link #scheduledEvent(SchedulerEvent)} is called to signal that the period has 
 * expired.
 * 
 * @author cnh
 *
 */
public interface ScheduledEventListener
{
	public void scheduledEvent (SchedulerEvent event);
}
