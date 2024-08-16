//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest.gatherer;


public class TimeConstants
{
	private static final String STR_2_HOURS = "2 hours";
	private static final String STR_1_5_HOURS = "1.5 hours";
	private static final String STR_1_HOUR = "1 hour";
	private static final String STR_30_MIN = "30 Min";
	private static final String STR_20_MIN = "20 Min";
	private static final String STR_15_MIN = "15 Min";
	private static final String STR_12_MIN = "12 Min";
	private static final String STR_10_MIN = "10 Min";
	private static final String STR_6_MIN = "6 Min";
	private static final String STR_5_MIN = "5 Min";
	private static final String STR_4_MIN = "4 Min";
	private static final String STR_3_MIN = "3 Min";
	private static final String STR_2_MIN = "2 Min";
	private static final String STR_1_MIN = "1 Min";
	private static final String STR_15_SEC = "15 sec";
	
	public static final int MSEC_PER_MINUTE = 60 * 1000;
	public static final int MSEC_PER_HOUR = 60 * MSEC_PER_MINUTE;
	public static final long MSEC_PER_DAY = 24 * MSEC_PER_HOUR;
	public static final int DEFAULT_PERIOD = 15 * MSEC_PER_MINUTE;
	public static final long MSEC_PER_YEAR = 365 * MSEC_PER_DAY;
	
	public static final Object[][] SPEC_TIMES = {
		{ STR_15_SEC,		15 * 1000				},
		{ STR_1_MIN, 		1 * MSEC_PER_MINUTE 	},
		{ STR_2_MIN, 		2 * MSEC_PER_MINUTE 	},
		{ STR_3_MIN, 		3 * MSEC_PER_MINUTE 	},
		{ STR_4_MIN,		4 * MSEC_PER_MINUTE		},
		{ STR_5_MIN,		5 * MSEC_PER_MINUTE 	},
		{ STR_6_MIN,		6 * MSEC_PER_MINUTE 	},
		{ STR_10_MIN,       10 * MSEC_PER_MINUTE    },
		{ STR_12_MIN,		12 * MSEC_PER_MINUTE 	},
		{ STR_15_MIN,		15 * MSEC_PER_MINUTE	},
		{ STR_20_MIN,		20 * MSEC_PER_MINUTE	},
		{ STR_30_MIN,		30 * MSEC_PER_MINUTE	},
		{ STR_1_HOUR,		60 * MSEC_PER_MINUTE	},
		{ STR_1_5_HOURS,	90 * MSEC_PER_MINUTE	},
		{ STR_2_HOURS,		120 * MSEC_PER_MINUTE	},
	};
	
	
	public static final String[] SPEC_TIME_OF_DAY = {
		"00:00",
		"01:00",
		"02:00",
		"03:00",
		"04:00",
		"05:00",
		"06:00",
		"07:00",
		"08:00",
		"09:00",
		"10:00",
		"11:00",
		"12:00",
		"13:00",
		"14:00",
		"15:00",
		"16:00",
		"17:00",
		"18:00",
		"19:00",
		"20:00",
		"22:00",
		"23:00",
	};
	
	
	
	
	
	public static Long toDurationValue(String s)
	{
		if (null == s)
			return null;
		
		for (int i = 0; i < SPEC_TIMES.length; i++)
		{
			String specStr = (String) SPEC_TIMES[i][0];
			if (specStr.equals(s))
			{
				Integer ival = (Integer) SPEC_TIMES[i][1];
				long lval = ival.longValue();
				return lval;
			}
		}
		
		return null;
	}
	
	
	public static String toDurationString(long duration)
	{
		StringBuffer sb = new StringBuffer();
		
		if (duration < 1000)
		{
			sb.append(duration);
			sb.append(" msec");
		}
		else if (duration < MSEC_PER_MINUTE)
		{
			long chronons = duration/1000;
			sb.append(chronons);
			sb.append(" sec");
		}
		else if (duration < MSEC_PER_HOUR)
		{
			long ticks = duration/MSEC_PER_MINUTE;
			sb.append(ticks);
			sb.append(" min");
		}
		else if (duration < MSEC_PER_DAY)
		{
			long ticks = duration/MSEC_PER_HOUR;
			sb.append(ticks);
			
			if (ticks > 1)
				sb.append(" hours");
			else
				sb.append(" hour");
		}
		else if (duration < MSEC_PER_DAY)
		{
			long ticks = duration/MSEC_PER_DAY;
			if (ticks > 1)
				sb.append("days");
			else
				sb.append("day");
		}
		else if (duration < MSEC_PER_YEAR)
		{
			long ticks = duration/MSEC_PER_DAY;
			sb.append(ticks);
			
			if (ticks > 1)
				sb.append(" days");
			else
				sb.append(" day");
		}
		else 
		{
			sb.append(duration);
			sb.append("msec");
		}
		
		return sb.toString();
	}	
}
