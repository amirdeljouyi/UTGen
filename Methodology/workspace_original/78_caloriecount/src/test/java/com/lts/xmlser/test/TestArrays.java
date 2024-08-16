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
package com.lts.xmlser.test;


/**
 * Ensure that arrays work.
 * 
 * <P>
 * Arrays pose many challenges.  This test ensures that the following 
 * aspects are functioning property:
 * 
 * <UL>
 * <LI>Arrays of primitive types
 * <LI>Arrays of objects of different classes
 * <LI>multi-dimensional arrays
 * </UL>
 */
public class TestArrays
	extends TestXmlSerializer
{
	public void testPrimitives()
		throws Exception
	{
		int[] intArray = new int[] {
			5, 6, 7, 8, 9
		};

		writeObject(intArray);
		Object o = readObject();
		compare(intArray, o);
	}
	
	
	public void testObjects ()
		throws Exception
	{
		Object o1 = createArray();
		writeObject(o1);
		Object o2 = readObject();
		compare(o1, o2);
	}
	
	
	public void testMultiPrimitive ()
		throws Exception
	{
		int[][] intArray = new int[][] {
			{ 5, 6, 7, 8 },
			{ 8, 7, 6, 5 },
			{ 6, 5, 8, 7 }
		};
		
		writeObject(intArray);
		Object o = readObject();
		compare(intArray, o);
	}
	
	
	public void test()
	{
		try
		{
			testPrimitives();
			testObjects();
			testMultiPrimitive();
			System.out.println ("Passed array tests");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
