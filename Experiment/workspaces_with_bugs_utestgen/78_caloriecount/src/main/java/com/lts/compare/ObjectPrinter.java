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
package com.lts.compare;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.lts.lang.reflect.ReflectionUtils;

public class ObjectPrinter 
{
	public static void printObject (Object o, PrintWriter out)
		throws Exception
	{
		Field[] fields = ReflectionUtils.getAllFields(o.getClass());
		
		out.print (o.getClass().getName());
		out.print ('{');
		
		for (int i = 0; i < fields.length; i++)
		{
			if (i > 0)
				out.print(", ");
			
			int mods = fields[i].getModifiers();
			if (Modifier.isStatic(mods) || Modifier.isTransient(mods))
				continue;
			
			fields[i].setAccessible(true);
			out.print (fields[i].getName());
			out.print (" = ");
			
			Object value = fields[i].get(o);
			if (null == value)
				out.print ("null");
			else
				out.print (value);			
		}
		
		out.print ('}');
	}
	
	
	public static void printObject (Object o)
		throws Exception
	{
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		
		printObject(o, out);
		
		out.close();
		System.out.println(sw.toString());
	}
}
