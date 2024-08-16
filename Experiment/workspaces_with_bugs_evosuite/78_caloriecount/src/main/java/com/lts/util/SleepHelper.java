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
 * This class contains a bunch of static methods that are useful when working with the 
 * Thread.sleep methods.
 * 
 * @author cnh
 */
public class SleepHelper
{
	/**
	 * Sleep, possibly rethrowing exceptions.
	 * <P>
	 * This method relieves the developer from having to write a try...catch block when
	 * calling {@link Thread#sleep(long)}. While such a block is conceptually simple, it
	 * is annoying to write, makes the source code less readable, and will not usually
	 * occur.
	 * <P>
	 * This method will do the try..catch for you and either ignore or rethrow the
	 * exception as an instance of {@link RuntimeException}. If the developer actually
	 * wanted to do something in reaction to the exception, they can write the try...catch
	 * themselves.
	 * 
	 * @param duration
	 *        How many milliseconds to sleep.
	 * @param rethrow
	 *        true if InterruptedException should be rethrown, false otherwise.
	 */
	public static void sleep (long duration, boolean rethrow)
	{
		try
		{
			Thread.sleep(duration);
		}
		catch (InterruptedException e)
		{
			if (rethrow)
				throw new RuntimeException(e);
		}
	}
	
	/**
	 * Sleep, rethrowing InterruptedException as RuntimeException.
	 * 
	 * <P>
	 * This is equivalent to calling {@link #sleep(long, boolean)} with true for the 
	 * rethrow parameter.
	 * 
	 * @param duration How many milliseconds to sleep.
	 */
	public static void sleep (long duration)
	{
		sleep(duration, true);
	}
}
