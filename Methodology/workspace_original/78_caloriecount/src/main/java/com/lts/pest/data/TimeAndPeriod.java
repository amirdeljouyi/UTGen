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
package com.lts.pest.data;

import java.text.SimpleDateFormat;

import com.lts.pest.gatherer.TimeConstants;

public class TimeAndPeriod
{
	public Long time;
	public Integer period;
	
	public TimeAndPeriod (Long theTime, Integer thePeriod)
	{
		this.time = theTime;
		this.period = thePeriod;
	}
	
	public TimeAndPeriod copy ()
	{
		TimeAndPeriod theCopy = new TimeAndPeriod(time, period);
		return theCopy;
	}
	
	
	private static final SimpleDateFormat ourFormat = 
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append (ourFormat.format(time));
		sb.append(", ");
		sb.append(TimeConstants.toDurationString(period));
		
		return sb.toString();
	}
}

