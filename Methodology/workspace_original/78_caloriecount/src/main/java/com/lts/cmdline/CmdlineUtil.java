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

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lts.LTSException;

/**
 * A utility for processing ye olde Unix style command line.
 * 
 * <P/>
 * This utility takes a command line, in the form of an array of strings, and 
 * parses the information contained into a properties object, that represents
 * the options and their values, and a list of strings, that represent the 
 * file name arguments.
 * 
 * <P/>
 * The class expects a command line that has the form:
 * 
 * <PRE>
 * whatever -abc -o nerts --a-large-switch-name foo.txt bar.adb
 * </PRE>
 * 
 * <P/>
 * Options are introduced via the dash, "-" character.  A string of them can 
 * be introduced by a string like "-abc".  It is possible to create an option
 * that has a long name by introducing it with two dashes like this: 
 * "--a_large_switch-name"  Some options can take arguments.  For example:
 * "-o nerts.txt"  Finally, the last strings are assumed to be file names like 
 * "foo.txt" and "bar.adb"
 * 
 */
public class CmdlineUtil
{
	
	public void processUnrecognizedOption (
		Properties p,
		Map switchMap,
		String option,
		String[] argv,
		int argvindex
	)
		throws LTSException
	{
		throw new LTSException ("unrecognized option: " + option);
	}
	
	
	public void processMissingArgument (
		Properties p,
		Argspec spec,
		String option,
		String[] argv,
		int argvindex
	)
		throws LTSException
	{
		throw new LTSException ("Missing argument for option: " + option);
	}
	
	
	public int processOption (
		Properties p,
		Map switchMap,
		String option,
		String[] argv,
		int argvindex,
		String value
	)
		throws LTSException
	{
		int nextOptionIndex = 1 + argvindex;
		
		Argspec spec = (Argspec) switchMap.get(option);
		if (null == spec)
		{
			processUnrecognizedOption (
				p, 
				switchMap, 
				option, 
				argv, 
				argvindex
			);
		}
		
		else if (spec.myRequiresArgument)
		{
			if (argvindex < argv.length)
			{	
				value = argv[nextOptionIndex];
				nextOptionIndex++;
			}
			else
			{
				processMissingArgument (
					p,
					spec,
					option,
					argv,
					argvindex
				);
			}
		}
		
		p.setProperty(spec.myVirtualName, value);
		
		return nextOptionIndex;
	}
	
	
	public int processOptionString (
		Properties p, 
		Map switchMap, 
		String option,
		String[] argv,
		int argvindex,
		String value
	)
		throws LTSException
	{
		int nextOptionIndex = 1 + argvindex;
		
		char[] ca = option.toCharArray();
		for (int i = 0; i < ca.length; i++)
		{
			String s = Character.toString(ca[i]);
			int next = processOption(p, switchMap, s, argv, argvindex, value);
			if (next != nextOptionIndex)
				break;
		}
		
		return nextOptionIndex;
	}
	
	
	public Properties processArgv (List l, String[] argv, Map switchMap)
		throws LTSException
	{
		Properties p = new Properties();
		
		if (null == argv)
			return p;
		
		
		int optindex = 0;
		while (optindex < argv.length)
		{
			String argument = argv[optindex];
			if (argument.startsWith("--"))
			{	
				optindex = processOption (
					p, 
					switchMap, 
					argument.substring(2), 
					argv, 
					optindex, 
					"false"
				);
			}
			else if (argument.startsWith("-"))
			{
				optindex = processOptionString (
					p, 
					switchMap,
					argument.substring(1), 
					argv, 
					optindex, 
					"false"
				);
			}
			else if (argument.startsWith("++"))
			{	
				optindex = processOption (
					p, 
					switchMap,
					argument.substring(2), 
					argv, 
					optindex, 
					"true"
				);
			}
			else if (argument.startsWith("+"))
			{	
				optindex = processOptionString (
					p,
					switchMap,
					argument.substring(1), 
					argv, 
					optindex, 
					"true"
				);
			}
			else 
			{
				l.add(argv[optindex]);
				optindex++;
			}
		}
		
		return p;
	}
}
