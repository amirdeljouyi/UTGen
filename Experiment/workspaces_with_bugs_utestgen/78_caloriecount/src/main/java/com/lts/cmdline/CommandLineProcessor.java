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
package com.lts.cmdline;

import java.util.Properties;

/**
 * An object that can process program command lines.
 * 
 * @author cnh
 */
abstract public class CommandLineProcessor
{
	/**
	 * Map the command line represented by the string array into a Properties
	 * object.
	 * 
	 * <P>
	 * This method defines the basic step in command line processing.  It is 
	 * left intentially vague to allow for extension and maximal flexibility.
	 * 
	 * @param argv The strings that represent the command line.  All of the 
	 * myEntries in this array must be non-null.
	 * @return The command line transformed into Properties.
	 * @throws Exception If a problem occurs during the process.
	 */
	abstract public Properties processCommandLine (String[] argv) 
		throws Exception;
}
