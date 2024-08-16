//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application;

public interface ApplicationProperties
{
	public static final String DEFAULT_SHORT_PROPERTY_FILE_NAME = "app.properties";
	
	public static final String PROP_LAST_DIRECTORY 	= "app.lastDirectory";
	public static final String PROP_LAST_FILE 		= "app.lastFile";
	public static final String PROP_REPOSITORY 		= "app.repositoryFile";
	// public static final String PROP_PROPERTY_FILE 	= "app.propertyFile";
	public static final String PROP_PARAMETER 		= "app.parameter";
	public static final String PROP_LOAD_TIME_MILLIS= "app.repositoryLoadTimeMillis";
	public static final String PROP_RELOAD_WINDOWS  = "app.ui.reloadWindows";

	public static final String[] SPEC_DEFAULT_VALUES = {
			// PROP_PROPERTY_FILE,		"application.properties",
			PROP_LOAD_TIME_MILLIS,	"10000",
			PROP_RELOAD_WINDOWS,	"false",
	};
	
	public static final String[] SPEC_APP_PROPERTY_NAMES = {
			PROP_LAST_DIRECTORY,
			PROP_LAST_FILE,
			PROP_REPOSITORY,
			// PROP_PROPERTY_FILE,
			PROP_PARAMETER,
			PROP_LOAD_TIME_MILLIS,
			PROP_RELOAD_WINDOWS,
	};
}
