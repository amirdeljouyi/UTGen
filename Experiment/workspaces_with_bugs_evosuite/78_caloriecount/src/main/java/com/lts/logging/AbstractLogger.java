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
/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lts.util.StringIntMap;

/**
 * @author cnh
 */
public abstract class AbstractLogger 
	implements Logger 
{
	abstract public void basicLog (String message, int severity, Throwable ex);
	
	protected int myLoggingSeverity;
	protected static SimpleDateFormat ourSDF 
		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static final Object[] SPEC_STRING_TO_SEVERITY = {
		STR_DEBUG,		new Integer(SEVERITY_DEBUG),
		STR_INFO,		new Integer(SEVERITY_INFO),
		STR_WARNING,	new Integer(SEVERITY_WARNING),
		STR_ERROR,		new Integer(SEVERITY_ERROR),
		STR_CRITICAL,	new Integer(SEVERITY_CRITICAL),
		STR_FORCE,		new Integer(SEVERITY_FORCE),
	};
	
	protected static StringIntMap ourStringSeverityMap = 
		new StringIntMap(SPEC_STRING_TO_SEVERITY);
	
	public static int stringToSeverity (String s)
	{
		return ourStringSeverityMap.stringToInt(s);
	}
	
	public static String severityToString (int severity)
	{
		return ourStringSeverityMap.intToString(severity);
	}
	
	
	
	public void log(String message, int severity, Throwable ex) 
	{
		if (severity < myLoggingSeverity)
			return;
		
		basicLog (message, severity, ex);
	}


	public void logDebug(String message, Throwable ex) 
	{
		log(message, SEVERITY_DEBUG, ex);
	}
	
	public void debug (String message)
	{
		log(message, SEVERITY_DEBUG, null);
	}


	public void logInfo(String message, Throwable ex) 
	{
		log(message, SEVERITY_INFO, ex);
	}
	
	public void info (String message)
	{
		log(message, SEVERITY_INFO, null);
	}

	public void logWarning(String message, Throwable ex) 
	{
		log(message, SEVERITY_WARNING, ex);
	}
	
	public void warning (String message)
	{
		log(message, SEVERITY_WARNING, null);
	}

	public void logError(String message, Throwable ex) 
	{
		log(message, SEVERITY_ERROR, ex);
	}
	
	public void error (String message)
	{
		log(message, SEVERITY_ERROR, null);
	}

	public void logCritical(String message, Throwable ex) 
	{
		log(message, SEVERITY_CRITICAL, ex);
	}
	
	public void critical (String message)
	{
		log(message, SEVERITY_CRITICAL, null);
	}

	public void forceLog(String message, Throwable ex) 
	{
		log(message, SEVERITY_FORCE, ex);
	}
	
	public void force (String message)
	{
		log(message, SEVERITY_FORCE, null);
	}

	public void setLoggingSeverity(int severity) 
	{
		if (severity < SEVERITY_MINIMUM)
			severity = SEVERITY_MINIMUM;
		
		if (severity > SEVERITY_MAXIMUM)
			severity = SEVERITY_MAXIMUM;
			
		myLoggingSeverity = severity;
	}

	/* (non-Javadoc)
	 * @see com.lts.logging.Logger#getLoggingSeverity()
	 */
	public int getLoggingSeverity() 
	{
		return myLoggingSeverity;
	}
	
	
	public String format (
		String message, 
		int severity, 
		Throwable ex,
		boolean printNewline
	)
	{
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);

		Date d = new Date();
				
		out.print ('|');
		out.print (ourSDF.format(d));
		out.print ('|');
		out.print (severityToString(severity));
		out.print ('|');
		out.print (message);
		out.print ('|');
		
		if (null != ex)
		{
			out.println ();
			ex.printStackTrace(out);
		}
		
		if (printNewline)
			out.println();
			
		out.close();
		return sw.toString();
	}
	
	
	public String format (
		String message,
		int severity,
		Throwable ex
	)
	{
		return format (message, severity, ex, false);
	}

}
