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
package com.lts.application.swing;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.lts.application.ApplicationException;
import com.lts.swing.TextContentPanel;

@SuppressWarnings("serial")
public class ApplicationTextPane extends TextContentPanel
{
	public static String toMessage (Throwable t)
	{
		StringWriter sw = new StringWriter(8192);
		PrintWriter out = new PrintWriter(sw);
		
		t.printStackTrace(out);
		
		if (t instanceof ApplicationException)
		{
			ApplicationException e = (ApplicationException) t;
			Object[] data = e.getData();

			if (data.length > 0)
				out.println();
			
			for (int i = 0; i < data.length; i++)
			{
				out.println ("data[" + i + "] = " + data[i]);
			}
		}
		
		out.close();
		return sw.toString();
	}
}
