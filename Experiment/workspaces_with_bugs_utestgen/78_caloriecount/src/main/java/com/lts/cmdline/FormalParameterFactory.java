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

/**
 * A class that can create an instance of FormalParameter, given a String 
 * "specification."
 * 
 * @author cnh
 */
public class FormalParameterFactory
{
	public static final String TYPE_SWITCH = "switch";
	public static final String TYPE_ARGUMENT = "argument";
	public static final String TYPE_SWITCH_ARG = "switch-arg";
	
	
	
	protected FormalParameter processSwitch (String name, String formalType, String[] spec)
		throws CommandLineException
	{
		BasicFormalParameter fparm = new BasicFormalParameter(name, spec[2], spec[3]);
		return fparm;
	}
	
	
	
	
	
	public FormalParameter toFormalParameter (String[] spec)
		throws CommandLineException
	{
		FormalParameter form = null;
		
		String err = CommandLineMessages.INVALID_FORMAL_SPEC;
		
		if (spec.length < 4)
		{
			String data0 =
				"Specifications should have at least 4 fields";
			throw new CommandLineException(err, data0);
		}
		
		String name = spec[0];
		if (null == name)
		{
			String data0 = "Null property name";
			throw new CommandLineException(err, data0);
		}
		
		String formalType = spec[1];
		if (null == formalType)
		{
			String data0 = "null specification type";
			throw new CommandLineException(err, data0);
		}
		
		if (TYPE_SWITCH.equals(formalType))
			form = processSwitch(name, formalType, spec);
		else if (TYPE_SWITCH_ARG.equals(formalType))
			form = processSwitchArg(name, formalType, spec);
		else if (TYPE_ARGUMENT.equals(formalType))
			form = processArgument(name, formalType, spec);
		else
		{
			String data0 = "unrecognized specification type: " + formalType;
			throw new CommandLineException(err, data0);
		}
		
		return form;
	}


	protected FormalParameter processArgument(String name, String formalType, String[] spec)
	{
		return new BasicFormalParameter(name);
	}


	protected FormalParameter processSwitchArg(String name, String formalType, String[] spec)
	{
		return new BasicFormalParameter(name, spec[2], spec[3], true);
	}
}
