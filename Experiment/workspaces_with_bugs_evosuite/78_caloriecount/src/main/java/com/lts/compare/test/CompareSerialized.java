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
package com.lts.compare.test;

import java.util.Iterator;

import com.lts.compare.ObjectComparator;
import com.lts.xmlser.XmlObjectInputStream;

public class CompareSerialized
{
	
	public void test (String[] argv)
		throws Exception
	{
		if (argv.length < 2)
		{
			System.err.println ("usage: compare <file 1> <file 2>");
			return;
		}
		

		XmlObjectInputStream in = new XmlObjectInputStream(argv[0]);
		Object o1 = in.readObject();
		in.close();
		
		in = new XmlObjectInputStream(argv[1]);
		Object o2 = in.readObject();
		in.close();
		
		ObjectComparator oc = new ObjectComparator();
		oc.setExceptionOnDifference(false);
		oc.setLogDifferences(true);
		
		boolean equivalent = oc.equivalent(o1, o2);
		if (equivalent)
			System.out.println ("Objects are equivalent");
		else
		{
			Iterator i = oc.getLog().iterator();
			while (i.hasNext())
			{
				System.out.println (i.next());
			}
		}
	}
	
	
	public static void main (String[] argv)
	{
		try
		{
			CompareSerialized o = new CompareSerialized();
			o.test(argv);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
