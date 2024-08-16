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
public class LoggerFactory 
{
	protected static Logger ourLogger;
	protected static NullLogger ourNullLogger = new NullLogger();
	protected static LoggerFactory ourInstance = new LoggerFactory();
	
	protected LoggerFactory()
	{
		initialize();
	}
	
	public static LoggerFactory getInstance()
	{
		return ourInstance;
	}
	
	public void initialize ()
	{
		createOurLogger();
	}

	public Logger getLogger (int minSeverity, boolean returnNull)
	{
		if (minSeverity >= ourLogger.getLoggingSeverity())
			return ourLogger;
		if (returnNull)
			return null;
		else
			return ourNullLogger;
	}
	
	public Logger getLogger (int minSeverity)
	{
		return getLogger(minSeverity, true);
	}
	
	
	protected static synchronized void createOurLogger()
	{
		if (null == ourLogger)
			ourLogger = new SystemErrLogger();
	}
	
	
	public Logger getLogger()
	{
		return ourLogger;
	}
	
	
	public void setLogger(Logger logger)
	{
		ourLogger = logger;
	}
	
	
}
