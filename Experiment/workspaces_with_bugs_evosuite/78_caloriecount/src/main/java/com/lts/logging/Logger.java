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

/**
 * @author cnh
 */
public interface Logger 
{
	public static final int SEVERITY_DEBUG = 0;
	public static final int SEVERITY_INFO = 1;
	public static final int SEVERITY_WARNING = 2;
	public static final int SEVERITY_ERROR = 3;
	public static final int SEVERITY_CRITICAL = 4;
	public static final int SEVERITY_FORCE = 5;
	
	public static final int SEVERITY_MINIMUM = SEVERITY_DEBUG;
	public static final int SEVERITY_MAXIMUM = SEVERITY_CRITICAL;
	
	public static final String STR_DEBUG = "debug";
	public static final String STR_INFO = "info";
	public static final String STR_WARNING = "warning";
	public static final String STR_ERROR = "error";
	public static final String STR_CRITICAL = "critical";
	public static final String STR_FORCE = "forced";
	
	
	public void log (String message, int severity, Throwable ex);
	public void logDebug (String message, Throwable ex);
	public void debug (String message);
	public void logInfo (String message, Throwable ex);
	public void info (String message);
	public void logWarning (String message, Throwable ex);
	public void warning (String message);
	public void logError (String message, Throwable ex);
	public void error (String message);
	public void logCritical (String message, Throwable ex);
	public void critical (String message);
	public void forceLog (String message, Throwable ex);
	public void force (String message);
	
	public void setLoggingSeverity (int severity);
	public int getLoggingSeverity ();
}
