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

public class BasicFormalParameter implements FormalParameter
{
	protected String name;
	protected String longForm;
	protected Character shortForm;
	protected boolean acceptsArgument;
	protected boolean requiresArgument;
	protected boolean keepAcceptingArguments;
	protected boolean ignoreCase;
	protected int argumentCount;
	
	
	public BasicFormalParameter()
	{
		initialize();
	}
	
	/**
	 * Define a new switch formal parameter.  
	 * 
	 * <P>
	 * The parameter will not accept arguments, but may have both a short and a 
	 * long form.
	 * 
	 * @param name The property name to use when setting the property.
	 * @param longForm The string that the long form must match to be considered
	 * a match.
	 * 
	 * @param shortForm The single character value that a short form switch must
	 * match.
	 */
	public BasicFormalParameter (String name, String longForm, String shortForm)
	{
		initialize(name, longForm, shortForm);
	}
	
	/**
	 * Initialize the object to be a "pure switch"
	 * 
	 * @param name the name of the property to define.
	 * @param longForm The string that an actual parameter must mach in order for
	 * this object to apply to the actual.
	 * 
	 * @param shortForm The single character that a short, actual parameter must
	 * match for this object to apply to them.
	 */
	protected void initialize (String name, String longForm, String shortForm)
	{
		this.name = name;
		this.longForm = longForm;
		if (null != shortForm)
		{
			char[] chars = shortForm.toCharArray();
			this.shortForm = new Character(chars[0]);
		}
		this.acceptsArgument = false;
		this.keepAcceptingArguments = false;
	}
	
	/**
	 * Define a "pure argument" style of formal parameter
	 * 
	 * @param name The name of the property to be set.
	 */
	public BasicFormalParameter (String name)
	{
		initialize(name);
	}
	
	/**
	 * Initialize the object to be a "pure argument."
	 * 
	 * <P>
	 * A pure argument formal parameter never matches a long or short switch and 
	 * always matches an argument command line element.
	 * 
	 * @param name The name of the property to define.
	 */
	protected void initialize (String name)
	{
		this.name = name;
		this.acceptsArgument = true;
		this.keepAcceptingArguments = true;
		this.argumentCount = 0;
	}
	
	
	/**
	 * Intialize an object to represent a "switch with argument" type of parameter.
	 * 
	 * <P>
	 * These types of formals require both a short or long switch and an argument.
	 * 
	 * @param name The name of the property.
	 * @param longForm The long form of the switch.
	 * @param shortForm The short form of the switch.
	 * @param requiresArgument This parameter is needed primarily to change the 
	 * signature of the method so that it can be distinguished from the version that
	 * defines a "pure switch" style parameter.  The parameter can be set to 
	 * false, however, in which case the result is a "pure switch" formal. 
	 */
	public BasicFormalParameter (String name, String longForm, String shortForm, boolean requiresArgument)
	{
		initialize(name, longForm, shortForm, requiresArgument);
	}
	
	/**
	 * Intialize an object to represent a "switch with argument" type of parameter.
	 * 
	 * <P>
	 * These types of formals require both a short or long switch and an argument.
	 * 
	 * @param name The name of the property.
	 * @param longForm The long form of the switch.
	 * @param shortForm The short form of the switch.
	 * @param requiresArgument This parameter is needed primarily to change the 
	 * signature of the method so that it can be distinguished from the version that
	 * defines a "pure switch" style parameter.  The parameter can be set to 
	 * false, however, in which case the result is a "pure switch" formal. 
	 */
	protected void initialize (String name, String longForm, String shortForm, boolean requiresArgument)
	{
		this.name = name;
		this.acceptsArgument = true;
		this.requiresArgument = requiresArgument;
		this.longForm = longForm;
		this.shortForm = null;
		
		if (null != shortForm)
			this.shortForm = new Character(shortForm.charAt(0));
	}
	
	
	public void initialize()
	{
		this.argumentCount = 0;
	}
	
	
	public boolean longFormMatches(String actual) throws CommandLineException
	{
		if (null == this.longForm)
			return false;
		else if (this.ignoreCase)
			return actual.equalsIgnoreCase(this.longForm);
		else
			return actual.equals(this.longForm);		
	}

	public boolean shortFormMatches(char c) throws CommandLineException
	{
		if (null == this.shortForm)
			return false;
		
		char ref = this.shortForm.charValue();
		
		if (this.ignoreCase)
		{
			c = Character.toUpperCase(c);
			ref = Character.toUpperCase(ref);
		}
		
		return ref == c;
	}
	
	public boolean argumentMatches (String actual) throws CommandLineException
	{
		return this.acceptsArgument && this.keepAcceptingArguments;
	}

	public int processSwitch(Properties props, String actual, String[] argv, int index)
			throws CommandLineException
	{
		int next = 1 + index;
		
		props.setProperty(this.name, "true");
		
		if (this.requiresArgument)
		{
			if (next >= argv.length)
			{
				String msg = CommandLineMessages.MISSING_REQUIRED_ARGUMENT;
				throw new CommandLineException(msg, this.name);
			}
			
			props.setProperty(this.name, argv[next]);
			next++;
		}
		
		return next;
	}

	public boolean matchesArgument(String actual) throws CommandLineException
	{
		return (this.acceptsArgument && this.keepAcceptingArguments);
	}

	/**
	 * Define an argument style command line component.
	 * 
	 * <P>
	 * This method will always define a property named &lt;property name&gt;.&lt;count&gt
	 * whose value the actual paramater.  If this is the first instance of this element, the 
	 * method will, in addition, define another property whose name is &lt;property name&gt;
	 * and whose value is the actual parameter.
	 * 
	 * @param actual The actual string passed to the system as the element.
	 * @param props The properties object where the property should be set.
	 * @param start The index within argv where actual can be found.
	 * @param argv The command line.
	 * @throws CommandLineException This method does not itself actually throw this exception.
	 * It is left in the method to support extending classes.
	 */
	public int processArgument(String actual, Properties props, int start, String[] argv)
			throws CommandLineException
	{
		String aname = this.name + this.argumentCount;
		props.setProperty(aname, actual);
		if (this.argumentCount < 1)
			props.setProperty(this.name, actual);
		
		this.argumentCount++;
		return 1 + start;
	}

}
