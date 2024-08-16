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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil
{
	public static SimpleDateFormat ourSDF = 
		new SimpleDateFormat("yyyy-MM-dd HH:mm.ss.SSS");
	
	/**
	 * Convert a value that represents a date and time to a value that represents only the
	 * offset from midnight for the input value.
	 * <P>
	 * For example, the value for 9:15 on 1/13/1997 would yied the value 9:15 (in
	 * milliseconds).
	 * <P>
	 * Going the other direction is much easier: just take the value for the date and add
	 * the offset.
	 * 
	 * @param dateTime
	 *        A date and time, expressed in milliseconds since the epoc.
	 * @return The time, in msec since the start of the day represented by the input
	 *         value.
	 */
	public static long timeOfDay (long dateTime)
	{
		return (dateTime  % (24 * 60 * 60 * 1000));
	}
	
	
	public static void clearHourMinuteSecondMsec (Calendar cal)
	{
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}
	
	
	public static Calendar calendar(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal;
	}
	
	
	public static Calendar calendarToday()
	{
		return calendar(System.currentTimeMillis());
	}
	
	/**
	 * Reset the hour, minute, second and millisecond of the date to 0.
	 * 
	 * @param date The date to modify.
	 */
	public static void clearHourMinuteSecond(Date date)
	{
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Return a Date that represents 00:00:00.000 yesterday.
	 * 
	 * @return 00:00:00.000 yesterday.
	 */
	public static Date yesterday()
	{
		return yesterday(System.currentTimeMillis());
	}
	
	public static Date yesterday (long time)
	{
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		return cal.getTime();
	}
	
	public static long yesterdayMsec (long time)
	{
		return yesterday(time).getTime();
	}
	
	
	public static long clearHourMinuteSecondMsec (long time)
	{
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		time = cal.getTimeInMillis();
		return time;
	}
	
	
	public static long startOfDay (long time)
	{
		return clearHourMinuteSecondMsec(time);
	}
	
	public static long startOfDay (Date date)
	{
		return clearHourMinuteSecondMsec(date.getTime());
	}
	
	
	public static long nextDay (long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		return cal.getTimeInMillis();
	}
	
	
	public static long nextDay (Date date)
	{
		return nextDay(date.getTime());
	}
	
	
	public static long startOfNextDay (long time)
	{
		long startOfDay = startOfDay(time);
		return nextDay(startOfDay);
	}
	
	
	public static long startOfNextDay (Date date)
	{
		return startOfNextDay(date.getTime());
	}
	
	
	
	/**
	 * Return a date that represents 00:00:00.000 today.
	 * 
	 * @return 00:00:00.000 today.
	 */
	public static Date today()
	{
		Date now = new Date(System.currentTimeMillis());
		clearHourMinuteSecond(now);
		return now;
	}
	
	
	public static long todayMsec()
	{
		long now = System.currentTimeMillis();
		return clearHourMinuteSecondMsec(now);
	}
	
	
	public static long todayMsec(long time)
	{
		Calendar cal = calendar(time);
		clearHourMinuteSecondMsec(cal);
		return cal.getTimeInMillis();		
	}
	
	
	public static long tomorrowMsec(long time)
	{
		Calendar cal = calendar(time);
		clearHourMinuteSecondMsec(cal);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		return cal.getTimeInMillis();
	}
	
	public static long tomorrowMsec()
	{
		return tomorrowMsec(System.currentTimeMillis());
	}
	
	public static String formatForToday (SimpleDateFormat sdf)
	{
		Date today = today();
		String str = sdf.format(today);
		return str;
	}
}
