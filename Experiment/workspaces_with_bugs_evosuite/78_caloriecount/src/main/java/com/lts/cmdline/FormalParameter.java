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
 * This class defines the basic interface for a class that represents a command
 * line parameter.
 * 
 * <P>
 * 
 * @author cnh
 *
 */
public interface FormalParameter
{
	/**
	 * Determine if this formal parameter matches an actual parameter from the 
	 * command line.
	 * 
	 * <P>
	 * For example, if the formal parameter defines a "quiet" option that uses 
	 * "-q" to indicate quiet mode and "-q" is the parameter, then the method 
	 * should return true.
	 * 
	 * @param actual The actual value passed.
	 * @return true if the formal param matches the actual value.
	 * @throws Exception If there is a problem.
	 */
	public boolean longFormMatches(String actual) throws CommandLineException;
	
	/**
	 * Does the formal parameter match the short option specified by the character
	 * argument?
	 * 
	 * @param c The short option character.
	 * @return true if this formal parameter can process the switch, false 
	 * otherwise.
	 * @throws Exception
	 */
	public boolean shortFormMatches (char c) throws CommandLineException;
	
	/**
	 * Process the actual parameter(s), mapping the value(s) to properties.
	 * 
	 * <P>
	 * For example, if the application has a property called "quietMode" and 
	 * the user has specified this with the actual params, then calling this 
	 * method would cause the properties object to contain "quietMode = true".
	 * 
	 * @param props The properties object to use.
	 * @param actual The actual parameter that the formal parameter is expected
	 * to process.
	 * 
	 * @param argv The entire command line.
	 * @param index The index of the actual parameter in argv.
	 * @return The new value for the index --- position of the next actual 
	 * parameter.
	 * 
	 * @throws Exception
	 */
	public int processSwitch(Properties props, String actual, String[] argv, int index)
			throws CommandLineException;
	
	/**
	 * Can this formal parameter process the argument?
	 * 
	 * @param actual The option passed on the command line.
	 * @return true if the formal can process it, false otherwise.
	 * @throws Exception
	 */
	public boolean matchesArgument (String actual) throws CommandLineException;
	
	/**
	 * Process an argument.
	 * 
	 * @param actual The string passed on the command line.
	 * @param props The properties object where the data should be placed.
	 * @param start The index of the argument on the command line.
	 * @param argv The command line.
	 * @return The index of the next parameter.
	 * @throws Exception
	 */
	public int processArgument (String actual, Properties props, int start, String[] argv)
		throws CommandLineException;
}
