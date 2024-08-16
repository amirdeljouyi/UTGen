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
package com.lts.util;

/**
 * A shared queue that makes no promises with respect to "fairness."
 * 
 * <P>
 * See the discussion of {@link Object#wait(long)} for a discussion of fairness
 * with respect to thread waiting.
 * 
 * @author cnh
 */
public class DefaultSharedQueue extends SharedQueue
{

	@Override
	public void performNotify()
	{
		notify();
	}

	@Override
	public void performWait() throws InterruptedException
	{
		wait();
	}

}
