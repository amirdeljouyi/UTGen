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
package com.lts.application.prop;

import java.util.Properties;

/**
 * A class that contains information about a command line parameter.
 * 
 * <P>
 * In this framework, command line parameters are described using name/value
 * pairs such as "debug=true"
 * 
 * <H2>Properties</H2>
 * <UL>
 * <LI>name - the property name
 * <LI>defaultValue - the default value for the property
 * <LI>value - the actual value of the property
 * <LI>switchValues - the character values when used as single character switch
 * values
 * <LI>longNames - the long names when introduced with the "--" switch
 * <LI>requiresArgument - whether the option requires a string argument
 * </UL>
 * 
 */
public class PropertyDescriptor
{
	private String name;
	private String defaultValue;
	private String value;
	private char[] switchValues;
	private String[] longNames;
	private boolean requiresArgument;
	
	
	/**
	 * @return Returns the defaultValue.
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}
	/**
	 * @param defaultValue The defaultValue to set.
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	/**
	 * @return Returns the longNames.
	 */
	public String[] getLongNames()
	{
		return longNames;
	}
	/**
	 * @param longNames The longNames to set.
	 */
	public void setLongNames(String[] longNames)
	{
		this.longNames = longNames;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	

	/**
	 * @return Returns the switchValues.
	 */
	public char[] getSwitchValues()
	{
		return switchValues;
	}
	/**
	 * @param switchValues The switchValues to set.
	 */
	public void setSwitchValues(char[] switchValues)
	{
		this.switchValues = switchValues;
	}
	
	public boolean matches(char c)
	{
		char[] ca = getSwitchValues();
		if (null == ca)
			return false;
		
		for (int i = 0; i < ca.length; i++)
		{
			if (ca[i] == c)
				return true;
		}
		
		return false;
	}
	
	
	public boolean matches(String longName)
	{
		String sa[] = getLongNames();
		if (null == sa)
			return false;
		
		for (int i = 0; i < sa.length; i++)
		{
			if (sa[i].equalsIgnoreCase(longName))
				return true;
		}
		
		return false;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public void setValue(String s)
	{
		this.value = s;
	}
	
	public String getValueFor (Properties p, String[] argv, int index, char c)
	{
		return getValue();
	}
	
	public String getValueFor (Properties p, String[] argv, int index, String s)
	{
		return getValue();
	}
	
	public String getNameFor (Properties p, String[] argv, int index, char c)
	{
		return getName();
	}
	
	public String getNameFor (Properties p, String[] argv, int index, String s)
	{
		return getName();
	}

	public boolean requiresArgument ()
	{
		return this.requiresArgument;
	}
	
	public boolean getRequiresArgument ()
	{
		return requiresArgument();
	}
	
	public void setRequiresArgument (boolean requiresArgument)
	{
		this.requiresArgument = requiresArgument;
	}

	public int process (Properties p, String[] argv, int index, char c)
	{
		String theName = getNameFor(p, argv, index, c);
		String theValue = getValueFor(p, argv, index, c);
		p.setProperty(theName, theValue);
		
		return 1 + index;
	}
	
	public int process (Properties p, String[] argv, int index, String s)
	{
		String theName = getNameFor(p, argv, index, s);
		String theValue = getValueFor (p, argv, index, s);
		p.setProperty(theName, theValue);
		
		return 1 + index;
	}
}
