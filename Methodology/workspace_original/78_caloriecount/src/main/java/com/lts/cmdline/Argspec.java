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


public class Argspec
{
	public String myLiteralName;
	public String myVirtualName;
	public boolean myAcceptsArgument;
	public boolean myRequiresArgument;
	
	
	public void init()
	{
		myAcceptsArgument = false;
		myRequiresArgument = false;
	}
	
	
	public Argspec (
		String literalName, 
		String virtualName, 
		Boolean acceptsArgument,
		Boolean requiresArgument
	)
	{
		init();
		
		myLiteralName = literalName;
		
		myVirtualName = myLiteralName;
		if (null != virtualName)
			myLiteralName = virtualName;

		if (null != acceptsArgument)
			myAcceptsArgument = acceptsArgument.booleanValue();
		
		if (null != requiresArgument)
			myRequiresArgument = requiresArgument.booleanValue();
	}

}
