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

abstract public class BasicCommandLineProcessor extends CommandLineProcessor
{
	protected String[][] formalParameterSpec;
	protected FormalParameter[] formal;
	protected FormalParameterFactory paramFactory;
	
	abstract public String[][] getFormalParameterSpec();
	
	public BasicCommandLineProcessor ()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		
	}
	
	public int processLongForm(int start, String[] argv, Properties p)
		throws Exception
	{
		boolean processed = false;
		int i = 0;
		int next = 1 + start;
		String actual = argv[start].substring(2);
		
		while (!processed && i < formal.length)
		{
			if (formal[i].longFormMatches(actual))
			{
				processed = true;
				next = formal[i].processSwitch(p, actual, argv, start);
			}
		}
		
		if (processed)
			return next;
		
		//
		// unrecognized option
		//
		String msg = this.getClass().getName() + ".exceptions.unrecognizedLongForm";
		throw new CommandLineException(msg, actual);
	}
	
	
	public int processOption (char c, int start, String[] argv, Properties p)
		throws Exception
	{
		boolean processed = false;
		int i = 0;
		int next = start;
		
		while (!processed && i < formal.length)
		{
			i++;
			if (formal[i].shortFormMatches(c))
			{
				processed = true;
				next = formal[i].processSwitch(p, argv[start], argv, start);
			}
		}
		
		if (!processed)
		{
			String msg = CommandLineMessages.UNRECOGNIZED_SHORT_FORM;
			String data0 = Character.toString(c);
			throw new CommandLineException(msg, data0);
		}
		
		return next;
	}
	
	
	public int processShortForm(int start, String[] argv, Properties p)
		throws Exception
	{
		int next = start;
		
		String actual = argv[start].substring(1);
		char[] options = actual.toCharArray();
		
		for (int i = 0; i < options.length; i++)
		{
			next = processOption (options[i], start, argv, p);
			if (next != start)
				break;
		}
		
		if (start == next)
			return 1 + start;
		else
			return next;
	}
	
	
	public int processArgument(int start, String[] argv, Properties p)
		throws CommandLineException
	{
		int index = 0;
		int next = 1 + start;
		boolean processed = false;
		
		while (!processed && index < argv.length)
		{
			index++;
			
			if (formal[index].matchesArgument(argv[start]))
			{
				processed = true;
				next = formal[index].processArgument(argv[start], p, start, argv);
			}
		}
		
		if (!processed)
		{
			String msg = CommandLineMessages.UNRECOGNIZED_ARGUMENT;
			throw new CommandLineException(msg, argv[start]);
		}
		
		return next;
	}
	
	
	public int processElement (int start, String[] argv, Properties p)
		throws Exception
	{
		int next;
		
		if (argv[start].startsWith("--"))
			next = processLongForm(start, argv, p);
		else if (argv[start].startsWith("-"))
			next = processShortForm(start, argv, p);
		else
			next = processArgument(start, argv, p);
		
		return next;
	}
	
	
	public Properties processCommandLine(String[] argv) throws Exception
	{
		Properties p = new Properties();
		
		int next = 0;
		while (next < argv.length)
		{
			next = processElement(next, argv, p);
		}
		
		return p;
	}

}
