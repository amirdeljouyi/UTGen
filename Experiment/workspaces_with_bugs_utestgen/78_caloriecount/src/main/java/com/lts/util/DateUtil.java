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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil
{
	public static void setMidnight(Date d)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Date temp = cal.getTime();
		d.setTime(temp.getTime());
	}
	
	
	public static Date startOfToday()
	{
		Date d = new Date();
		setMidnight(d);
		return d;
	}
	
	
	static public long startOfTodayTime()
	{
		return startOfToday().getTime();
	}
	
	
	static public Date startOfYesterday()
	{
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DAY_OF_YEAR, -1);
		d = cal.getTime();
		setMidnight(d);
		return d;
	}
	
	static public long startOfYesterdayTime()
	{
		return startOfYesterday().getTime();
	}
	
	
	public static long midnightMsec()
	{
		Date d = new Date();
		setMidnight(d);
		return d.getTime();
	}
	
	static private Long ourStartOfToday;
	static private Long ourStartOfTomorrow;
	static private Long ourStartOfYesterday;
	
	static public long getStartOfToday()
	{
		if (null == ourStartOfToday)
			initializeConstants();
		
		return ourStartOfToday;
	}
	
	static public long getToday()
	{
		return getStartOfToday();
	}
	
	
	static public long getStartOfTomorrow()
	{
		if (null == ourStartOfTomorrow)
			initializeConstants();
		
		return ourStartOfTomorrow;
	}
	
	static public long getTomorrow()
	{
		return getStartOfTomorrow();
	}
	
	
	static public long getStartOfYesterday()
	{
		if (null == ourStartOfYesterday)
			initializeConstants();
		
		return ourStartOfYesterday;
	}
	
	static public long getYesterday()
	{
		return getStartOfYesterday();
	}
	
	synchronized static private void initializeConstants()
	{
		if (null != ourStartOfToday && null != ourStartOfTomorrow)
			return;
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		ourStartOfYesterday = cal.getTimeInMillis();
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		ourStartOfToday = cal.getTimeInMillis();
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		ourStartOfTomorrow = cal.getTimeInMillis();
	}

	public static final int MSEC_MINUTE = 1000 * 60;
	public static final int MSEC_HOUR = 60 * MSEC_MINUTE;
	public static final int MSEC_DAY = 24 * MSEC_HOUR;
	
	/**
	 * Return the represented time, relative to time zero (0).
	 * 
	 * <P>
	 * The method expects a string of the form HH[:mm] where "HH" is the 24
	 * hour time and ":mm" are the number of minutes into that hour.  For example,
	 * "13:29" is 1:30pm.
	 * </P>
	 * 
	 * <P>
	 * The value returned by the method is not relative to the current day.  In fact
	 * it is relative to the "zero time;" or the value, 0.  To get this time relative
	 * to the current day or whatever, the value for midnight of that day must be 
	 * added to the value.
	 * </P>
	 * 
	 * @param s the time string to convert.
	 * @return null if the input string is invalid, otherwise the time it represents,
	 * relative to midnight.
	 */
	public static Long parseTime (String s)
	{
		try
		{
			if (null == s)
				return null;
			
			String[] fields = s.split(":");
			if (fields.length < 2)
				return null;
			
			int hours = Integer.parseInt(fields[0]);
			int minutes = Integer.parseInt(fields[1]);
			int seconds = 0;
			
			if (fields.length > 2)
				seconds = Integer.parseInt(fields[2]);
			
			long time = hours * MSEC_HOUR;
			time += minutes * MSEC_MINUTE;
			time += seconds * 1000;
			
			return time;
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	
	public static final SimpleDateFormat UTC_TIME_ONLY_FORMAT = 
		new SimpleDateFormat("HH:mm:ss.SSS");
	
	public static SimpleDateFormat[] COMMON_TIME_FORMATS = 
	{
		new SimpleDateFormat("HH:mm:ss"),
		new SimpleDateFormat("HH:mm"),
		new SimpleDateFormat("hh:mm:ss aa"),
		new SimpleDateFormat("hh:mm aa"),
		new SimpleDateFormat("hh:mm:ss"),
		new SimpleDateFormat("hh:mm"),
	};
	
	/**
	 * return the time value for today plus the time specified in the parameter.
	 * <P>
	 * For example, if the input string is "9:43" then the method should return a time
	 * that is today at 9:43. More precisely, the number of milliseconds since the epoch
	 * today at 9:43 AM, local time.
	 * </P>
	 * 
	 * @param s
	 *        The time string. See the class description for a list of known formats for
	 *        this value.
	 * @return The time, relative to today.
	 */
	public static Long parseCommonTime (String s)
	{
		Long result = null;
		
		try
		{
			long timeOfDay = parseTimeString(s);
			if (-1 == timeOfDay)
				return null;
			
			return timeOfDay;
		}
		catch (NumberFormatException e)
		{
			result = null;
		}
		
		return result;
	}


	/**
	 * Time time of day, relative to 0 date.
	 * 
	 * <P>
	 * This method returns a value between 0 and {@link #MSEC_DAY} that represents
	 * the time string passed to it.  See the class description for the different 
	 * formats of time strings that this method understands.
	 * </P>
	 * 
	 * <P>
	 * If the input string failed to parse against any of the known formats, a value
	 * of -1 is returned.
	 * </P>
	 * 
	 * @param s The time string to parse.
	 * @return 0 and MSEC_DAY or -1 if the string could not be parsed.
	 */
	public static long parseTimeString(String s)
	{
		if (null == s)
			return -1;
		
		long time = -1;
		
		for (SimpleDateFormat format : COMMON_TIME_FORMATS)
		{
			try
			{
				time = format.parse(s).getTime();
				break;
			}
			catch (ParseException e)
			{
				//
				// ignore exception and try next format
				//
			}
		}
		
		return time;
	}


	public static Long attemptParse(Calendar calendar, SimpleDateFormat format, String s)
	{
		try
		{
			Date date = format.parse(s);
			Calendar local = Calendar.getInstance();
			local.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, local.get(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, local.get(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, local.get(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, 0);
			
			return calendar.getTimeInMillis();
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	
	public static Long attemptParse(SimpleDateFormat format, String s)
	{
		Calendar cal = Calendar.getInstance();
		return attemptParse(cal, format, s);
	}

	public static final long SEC_PER_MINUTE = 60;
	public static final long SEC_PER_TWO_MINUTES = 2 * SEC_PER_MINUTE;
	public static final long SEC_PER_HOUR = 60 * SEC_PER_MINUTE;
	public static final long SEC_PER_TWO_HOURS = 2 * SEC_PER_HOUR;
	public static final long SEC_PER_DAY = 24 * SEC_PER_HOUR;
	public static final long SEC_PER_TWO_DAYS = 2 * SEC_PER_DAY;
	
	/**
	 * Return the hours, seconds, etc. that the time string represents; relative
	 * to zero.
	 * 
	 * <P>
	 * "Relative to zero" means that the time value returned is not for the current
	 * day or indeed any date.  To get a time of day for a particular day, add the 
	 * time value for the start of that day.
	 * </P>
	 * 
	 * <P>
	 * The input is expected to have the format: 
	 * <PRE>
	 *     HH[:mm] [min|hour|day]
	 * </PRE>
	 * </P>
	 * 
	 * <P>
	 * Where "HH" is the hour in 24 hour time, ":mm" is the number of minutes.  If 
	 * the string has no suffix like "min", "hour", etc. then the suffix is assumed to be 
	 * minutes.
	 * </P>
	 * 
	 * @param s The time string, as described above.
	 * @return The time value, as described above.
	 */
	public static Long parseDelay(String s, long defaultUnitsMsec)
	{
		s = StringUtils.trim(s);
		
		if (null == s)
			return null;

		s = s.toLowerCase();
		String[] fields = specialSplit(s);

		if (fields.length < 1)
			return null;
			
		String delayString = fields[0];
		if (fields.length > 1)
			defaultUnitsMsec = parseUnits(fields[1]);

		TimeResults results = parseDelayTime(delayString);
		long time = 0;
		if (results.ambiguous)
		{
			time = results.minutes * defaultUnitsMsec;
		}
		else
		{
			time = results.hours * SEC_PER_HOUR;
			time = time + (results.minutes * SEC_PER_MINUTE);
			time = time + (results.seconds * 1000);
		}

		return time;
	}
	
	
	
	
	public static final String[] MINUTE_ABBREVIATIONS = 
	{
		"m",
		"min",
		"mins",
		"minute",
		"minutes"
	};
	
	
	public static final String[] SECOND_ABBREVIATIONS = 
	{
		"s",
		"sec",
		"secs",
		"second",
		"seconds"
	};
	
	
	public static final String[] HOUR_ABBREVIATIONS = 
	{
		"h",
		"hr",
		"hrs",
		"hour",
		"hours"
	};

	
	private static boolean matchesArray(String[] values, String s)
	{
		for (int i = 0; i < values.length; i++)
		{
			String temp = values[i];
			if (temp.equalsIgnoreCase(s))
				return true;
		}
		
		return false;
	}
	
	
	private static long parseUnits(String s)
	{
		long value = 1;
		
		if (matchesArray(SECOND_ABBREVIATIONS, s))
			value = 1000;
		else if (matchesArray(MINUTE_ABBREVIATIONS, s))
			value = MSEC_MINUTE;
		else if (matchesArray(HOUR_ABBREVIATIONS, s))
			value = MSEC_HOUR;
		
		return value;
	}





	private static class TimeResults
	{
		public boolean ambiguous;
		
		public int hours;
		public int minutes;
		public int seconds;
	}
	
	/**
	 * Parse a that represents a delay in time, it can have several forms.
	 * 
	 * <P>
	 * The allowed forms are: "mm", "hh:mm" and "hh:mm:ss".  That is, a simple integer
	 * value is assumed to be minutes, 
	 * </P>
	 * 
	 * @param s The string to parse.
	 * @return The value in milliseconds that it represents.
	 */
	static private TimeResults parseDelayTime(String s)
	{
		TimeResults results = new TimeResults();
		results.ambiguous = false;
		
		String[] fields = s.split(":");
		
		//
		// hh:mm:ss
		//
		if (fields.length > 2)
		{
			results.hours = Integer.parseInt(fields[0]);
			results.minutes = Integer.parseInt(fields[1]);
			results.seconds = Integer.parseInt(fields[2]);
		}
		//
		// hh:mm
		//
		else if (fields.length > 1)
		{
			results.hours = Integer.parseInt(fields[0]);
			results.minutes = Integer.parseInt(fields[1]);
		}
		//
		// minutes
		//
		else
		{
			results.ambiguous = true;
			results.minutes = Integer.parseInt(fields[0]);
		}
		
		return results;
	}

	private enum ParseStates
	{
		Start,
		Digits,
		Whitespace,
		Letters
	}
	
	
	private static String[] specialSplit(String s)
	{
		char[] copy = s.toCharArray();
		List<String> fields = new ArrayList<String>();
		ParseStates state = ParseStates.Start;
		StringBuffer current = new StringBuffer();
		
		for (int i = 0; i < copy.length; i++)
		{
			switch(state)
			{
				case Digits :
				{
					if (Character.isDigit(copy[i]))
						current.append(copy[i]);
					else if (Character.isWhitespace(copy[i]))
					{
						fields.add(current.toString());
						state = ParseStates.Whitespace;
					}
					else
					{
						fields.add(current.toString());
						current = new StringBuffer();
						state = ParseStates.Letters;
						current.append(copy[i]);
					}
					break;
				}
				
				case Letters :
				{
					if (Character.isLetter(copy[i]))
						current.append(copy[i]);
					else if (Character.isWhitespace(copy[i]))
					{
						fields.add(current.toString());
						state = ParseStates.Whitespace;
					}
					else
					{
						fields.add(current.toString());
						current = new StringBuffer();
						state = ParseStates.Digits;
						current.append(copy[i]);
					}
					break;
				}
				
				
				case Start :
				{
					current.append(copy[i]);
					if (Character.isLetter(copy[i]))
						state = ParseStates.Letters;
					else if (Character.isWhitespace(copy[i]))
						state = ParseStates.Whitespace;
					else if (Character.isDigit(copy[i]))
						state = ParseStates.Digits;
					else
					{
						state = ParseStates.Whitespace;
					}
					break;
				}
				
				
				case Whitespace :
				{
					if (Character.isLetter(copy[i]))
						state = ParseStates.Letters;
					else if (Character.isDigit(copy[i]))
						state = ParseStates.Digits;
					else 
						state = ParseStates.Whitespace;
					break;
				}
			}
		}
		
		
		switch(state)
		{
			case Digits :
			case Letters :
			{
				fields.add(current.toString());
				break;
			}
			
			case Start :
			case Whitespace :
				break;
				
			default :
			{
				String msg = "Unrecognized state: " + state;
				throw new IllegalStateException(msg);
			}
		}
		
		String[] result = new String[fields.size()];
		
		for (int i = 0; i < result.length; i++)
		{
			result[i] = fields.get(i);
		}
		
		return result;
	}
	
	static public long clearDate (long date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		
		return cal.getTimeInMillis();
	}
	
	
	static public long clearTime (long date)
	{
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTimeInMillis();
	}
	
	
	static public long startOfDay (long date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTimeInMillis();
	}


	public static long alignToPeriod(long time, long period)
	{
		long remainder = time % period;
		time = time - remainder;
		return time;
	}


	/**
	 * Return the next time that falls on the boundary of a given interval.
	 * 
	 * <P>
	 * For example, if suppose it is 10am and the caller supplies a time 
	 * interval of 15 seconds, then this method would return 10:00:15.  The same
	 * value is returned until it is 10:00:15, at which time the method would 
	 * return 10:00:30, etc. 
	 * </P>
	 * 
	 * @param period The time interval.
	 * @return see above.
	 */
	public static long nextTimeAligned(long period)
	{
		long time = System.currentTimeMillis() + period;
		long remainder = time % period;
		time = time - remainder;
		return time;
	}


	public static long startOfTomorrow()
	{
		long time = startOfTodayTime();
		time = time + MSEC_DAY;
		return time;
	}


	public static long startOfNextDay(long date)
	{
		long time = startOfDay(date);
		time = time + MSEC_DAY;
		return time;
	}
	
	
	public static long combineDateAndTime(long date, long time)
	{
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTimeInMillis(date);
		
		Calendar timeCal = Calendar.getInstance();
		timeCal.setTimeInMillis(time);
		
		dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
		
		return dateCal.getTimeInMillis();
	}


	public static String toDelayString(long period)
	{
		StringBuffer sb = new StringBuffer();
		
		if (period > SEC_PER_TWO_DAYS)
		{
			int days = (int) (period % SEC_PER_DAY);
			sb.append(days);
			sb.append(" days");
		}
		else if (period > SEC_PER_TWO_HOURS)
		{
			int hours = (int) (period % SEC_PER_HOUR);
			sb.append(hours);
			sb.append(" hours");
		}
		else if (period > SEC_PER_TWO_MINUTES)
		{
			int minutes = (int) (period % SEC_PER_TWO_MINUTES);
			sb.append(minutes);
			sb.append(" mins");
		}
		else
		{
			int sec = (int) (period % 1000);
			sb.append(sec);
			sb.append(" sec");
		}
		
		return sb.toString();
	}


	public static String zeroPad(int value)
	{
		StringBuilder sb = new StringBuilder();
		if (value < 10)
			sb.append("0");
		
		sb.append(value);
		
		return sb.toString();
	}
	
	
	public static long timeToAbsolute(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startOfTodayTime());
		
		int hours = (int) (time % SEC_PER_HOUR);
		int minutes = (int) (time % SEC_PER_MINUTE);

		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		
		return cal.getTimeInMillis();
	}
	
	
	public static SimpleDateFormat ourTimeFormat =
		new SimpleDateFormat("HH:mm");
	
	public static String toTimeString(long time, boolean relativeToToday)
	{
		if (relativeToToday)
			time = timeToAbsolute(time);
		
		return ourTimeFormat.format(time);
	}
	
	public static String toTimeString(long time)
	{
		return toTimeString(time, true);
	}
	
	
	public static long timeStringToTime(String s, long relativeTime)
	{
		s = StringUtils.trim(s);
		String[] fields = s.split(":");
		
		int seconds = 0;
		if (fields.length > 2)
		{
			seconds = Integer.parseInt(fields[2]);
		}
		
		int minutes = 0;
		if (fields.length > 1)
		{
			minutes = Integer.parseInt(fields[1]);
		}
		
		int hours = 0;
		if (fields.length > 1)
		{
			hours = Integer.parseInt(fields[0]);
		}
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(relativeTime);
		
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		
		return cal.getTimeInMillis();
	}


	public static long setMidnight(long current)
	{
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		return cal.getTimeInMillis();
	}
	
	
 
	public static String secondsToPeriodString (long msec)
	{
		Calendar cal = GregorianCalendar.getInstance();
		
		long temp = msec;
		int millisec = (int) temp % 1000;
		cal.set(Calendar.MILLISECOND, millisec);
		
		temp = temp / 1000;
		int sec = (int) temp % 60;
		cal.set(Calendar.SECOND, sec);
		
		temp = temp / 60;
		int min = (int) temp % 60;
		cal.set(Calendar.MINUTE, min);
		
		temp = temp / 60;
		int hour = (int) temp % 24;
		cal.set(Calendar.HOUR_OF_DAY, hour);
		
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		
		String s = UTC_TIME_ONLY_FORMAT.format(cal.getTimeInMillis());
		
		return s;
	}
	
	
	public static long periodStringToSeconds (String s)
	{
		try
		{
			s = StringUtils.trim(s);
			if (null == s)
				return -1;
			
			long time = UTC_TIME_ONLY_FORMAT.parse(s).getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			
			long val = 0;
			val += cal.get(Calendar.HOUR_OF_DAY);
			val *= 60;
			val += cal.get(Calendar.MINUTE);
			val *= 60;
			val += cal.get(Calendar.SECOND);
			val *= 1000;			
			val += cal.get(Calendar.MILLISECOND);
			
			
			
			return val;
		}
		catch (ParseException e)
		{
			return -1;
		}
	}
}
