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
package com.lts.swing;

import javax.swing.JDialog;

public class JDialogUtil
{
	protected static int ourMajorVersion;
	protected static int ourMinorVersion;
	
	protected static void initMajorMinor()
	{
		if (0 != ourMajorVersion && 0 != ourMinorVersion)
			return;

		String version = System.getProperty("java.vm.version");
		if (null == version)
		{
			ourMajorVersion = -1;
			ourMinorVersion = -1;
			return;
		}
		
		String[] fields = version.split("\\.");
		if (fields.length < 2)
		{
			ourMajorVersion = -1;
			ourMinorVersion = -1;
			return;
		}
		
		ourMajorVersion = Integer.parseInt(fields[0]);
		ourMinorVersion = Integer.parseInt(fields[1]);
	}
	
	
	public static void setAlwaysOnTop(JDialog dialog, boolean alwaysOnTop)
	{
		if (ourMajorVersion > 0 && ourMinorVersion > 4)
			dialog.setAlwaysOnTop(alwaysOnTop);
	}
	
}
