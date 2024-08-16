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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationMessages;

/**
 * Process elements of the command line one at a time, based on a sequence 
 * @author cnh
 *
 */
public class DefaultCommandLinePolicy implements CommandLinePolicy
{
	private PropertyDescriptor[] arguments;
	
	
	public int processLongArgument (Properties p, int index, String[] argv, String arg)
		throws ApplicationException
	{
		int next = 1 + index;
		
		int i = 0;
		boolean foundMatch = false;
		
		while (!foundMatch && i < this.arguments.length)
		{
			foundMatch = this.arguments[i].matches(arg);
			i++;
		}
		
		if (!foundMatch)
		{
			Object[] data = new Object[] {
					arg
			};
			
			String key = ApplicationMessages.ERROR_UNRECOGNIZED_SWITCH;
			throw new ApplicationException(key, data);
		}
		
		return next;
	}
	
	
	public int processSwitch (Properties p, int index, String[] argv, String arg)
		throws ApplicationException
	{
		char[] ca = arg.toCharArray();
		int next = 1 + index;
		
		for (int i = 0; i < ca.length; i++)
		{
			char c = ca[i];
			boolean foundMatch = false;
			int j = 0;
			
			while (!foundMatch && j < this.arguments.length)
			{
				if (arguments[j].matches(c))
				{
					arguments[j].process(p, argv, index, c);
					foundMatch = true;
				}
			}

			if (!foundMatch)
			{
				Object[] data = new Object[] {
						new Character(c)
				};
				
				String key = ApplicationMessages.ERROR_UNRECOGNIZED_SWITCH;
				throw new ApplicationException(key, data);
			}
			
			if (arguments[j].requiresArgument())
			{
				if ((1 + i) == ca.length)
					next++;
				else
				{
					Object[] data = new Object[] {
							new Character(c)
					};
					
					String key = ApplicationMessages.ERROR_OPTION_SWITCH_NOT_AT_END;
					throw new ApplicationException(key, data);
				}
			}
		}

		return next;
	}
	
	
	/**
	 * Process a single 
	 * @param p
	 * @param index
	 * @param argv
	 * @param params
	 * @return
	 * @throws ApplicationException
	 */
	public int processArgument (Properties p, int index, String[] argv, List params)
		throws ApplicationException
	{
		int next;
		String arg = argv[index];
		
		if (arg.startsWith("--"))
		{
			arg = arg.substring(2);
			next = processLongArgument(p, index, argv, arg);
		}
		else if (arg.startsWith("-"))
		{
			arg = arg.substring(1);
			next = processSwitch(p, index, argv, arg);
		}
		else
		{
			params.add(arg);
			next = 1 + index;
		}
		
		return next;
	}
	
	
	public Properties processCommandLine(String[] argv) throws ApplicationException
	{
		Properties p = new Properties();
		if (null == argv)
			return p;
		
		List params = new ArrayList();
		int index = 0;
		while (index < argv.length)
		{
			int next = processArgument(p, index, argv, params);
			
			//
			// ensure that the loop terminates eventually by requiring the index to always
			// increase.  Thus eventually index >= argv.length and command line processing 
			// will therefore stop
			//
			if (index >= next)
			{
				next++;
			}
		}
		
		for (int i = 0; i < params.size(); i++)
		{
			String name = ApplicationProperties.PROP_PARAMETER + "." + i;
			String value = (String) params.get(i);
			p.setProperty(name, value);
		}
		
		return p;
	}

}
