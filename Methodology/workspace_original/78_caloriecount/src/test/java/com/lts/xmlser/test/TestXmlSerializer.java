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

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lts.compare.EquivalenceException;
import com.lts.compare.ObjectComparator;
import com.lts.xmlser.XmlObjectInputStream;
import com.lts.xmlser.XmlObjectOutputStream;

public class TestXmlSerializer
{
	public static Object createTestObject ()
	{
		// return new Foo();
		return null;
	}
	
	
	public void compare (Object o1, Object o2)
		throws EquivalenceException
	{
		ObjectComparator oc = new ObjectComparator();
		oc.setExceptionOnDifference(true);
		oc.setIgnoreEqualsMethods(true);
		oc.equivalent(o1, o2);
	}
	
	
	public Object readObject (String fileName)
		throws Exception
	{
		XmlObjectInputStream in = null;
		try
		{
			FileReader freader = new FileReader(fileName);
			in = new XmlObjectInputStream(freader);
			return in.readObject();
		}
		finally
		{
			if (null != in)
				in.close();
		}
	}
	
	
	public void writeObject (String fileName, Object o)
		throws Exception
	{
		XmlObjectOutputStream out = null;
		
		try
		{
			FileWriter fwriter = new FileWriter(fileName);
			out = new XmlObjectOutputStream(fwriter);
			out.writeObject(o);
		}
		finally
		{
			if (null != out)
				out.close();
		}
	}
	
	public Object createSimpleSubclass()
	{
		SimpleSubclass o = new SimpleSubclass();
		o.initialize(
			true,
			new Boolean(false),
			(byte) 5,
			new Byte((byte) 14),
			'f',
			new Character('D'),
			(double) 1.45E23,
			new Double(5.73E10),
			(float) 123.456,
			new Float(456.789),
			12345678,
			new Integer(1234567),
			1234567890123L,
			new Long(1234567890123L),
			(short) 10,
			new Short((short)-7)
		);
		
		o.whatever = "hi <& there";
		o.subclassInt = 8;
		
		return o;
	}
	
	public Object createCollection ()
	{
		Object[] data = {
			"hi there",
			new Integer(13),
			new Character('c'),
			createSimpleObject()
		};
		
		ArrayList l = new ArrayList();
		for (int i = 0; i < data.length; i++)
		{
			l.add(data[i]);
		}
		
		return l;
	}
	
	
	public Object createSimpleObject ()
	{
		SimpleObject o = new SimpleObject (
			true,
			new Boolean(false),
			(byte) 5,
			new Byte((byte) 14),
			'f',
			new Character('D'),
			(double) 1.45E23,
			new Double(5.73E10),
			(float) 123.456,
			new Float(456.789),
			12345678,
			new Integer(1234567),
			1234567890123L,
			new Long(1234567890123L),
			(short) 10,
			new Short((short)-7)
		);
		
		o.nullProperty = null;
		
		return o;
	}
	
	
	public Object createArray ()
	{
		Object[] data = {
			"hi there",
			new Integer(13),
			new Double(1.234),
			null,
			"hello world!",
			createSimpleObject()
		};
		
		return data;
	}
	
	
	public Object createComplexObject ()
	{
		ComplexObject o1 = new ComplexObject();
		o1.myName = "o1";
		ComplexObject o2 = new ComplexObject();
		o2.myName = "o2";
		
		o1.myOther = o2;
		o2.myOther = o1;
		
		return o1;
	}
	
	
	public void writeObject (Object o)
		throws Exception
	{
		writeObject("temp.xml", o);
	}
	
	
	public Object readObject ()
		throws Exception
	{
		return readObject("temp.xml");
	}
	
	
	public void performTest (String msg, Object o)
	{
		try
		{
			writeObject(o);
			Object o2 = readObject();
			ObjectComparator oc = new ObjectComparator();
			// oc.setExceptionOnClass(true);
			oc.setExceptionOnDifference(false);
			oc.setIgnoreEqualsMethods(true);
			oc.setLogDifferences(true);
			if (oc.equivalent(o, o2))
				System.out.println (msg + " test passed");
			else
			{
				System.out.println ("FAILED --- " + msg + " test");
				Iterator i = oc.getLog().iterator();
				while (i.hasNext())
				{
					System.out.println ("    " + i.next());
				}
			}
		}
		catch (EquivalenceException e)
		{
			if (
				e.getReason() != EquivalenceException.REASON_EXCEPTION
				&& e.getReason() != EquivalenceException.REASON_UNKNOWN
			)
			{
				System.out.println ("FAILED --- " + msg + " test");
				System.out.println ("    " + e.getMessage());
				// e.printStackTrace();
			}
			else
			{
				System.err.println ("EXCEPTION during " + msg + " test.");
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			System.err.println ("EXCEPTION during " + msg + " test.");
			e.printStackTrace();
		}
	}
	
	
	public boolean equivalent (Object o1, Object o2)
	{
		if (null == o1 && null == o2)
			return true;
		else if (null == o1 || null == o2)
			return false;
		else
			return o1.equals(o2);
	}
	
	
	public boolean different (Object o1, Object o2)
	{
		return !equivalent(o1, o2);
	}
	
	
	
	public void testArray ()
	{
		try
		{
			/*
			Object o = createArray();
			writeObject(o);
			Object o2 = readObject();
			
			ObjectComparator oc = new ObjectComparator();
			// oc.setExceptionOnClass(true);
			oc.setExceptionOnDifference(true);
			oc.setIgnoreEqualsMethods(true);
			oc.equivalent(o, o2);
			
			System.out.println ("array test passed");
			*/
			TestArrays test = new TestArrays();
			test.test();
		}
		catch (Exception e)
		{
			System.err.println ("EXCEPTION during array test.");
			e.printStackTrace();
		}
	}
	
	public void testCollection ()
	{
		Object o = createCollection();
		performTest("collection", o);
	}
	
	public void testComplexCollection ()
	{
		Object o = createComplexListObject();
		performTest("complex list", o);
	}
	
	public void testComplexObject ()
	{
		Object o = createComplexObject();
		performTest ("complex object", o);
	}
	
	public void testSimpleObject ()
	{
		Object o = createSimpleObject();
		performTest ("simple object", o);
	}
	
	public void testSimpleSubclass()
	{
		Object o = createSimpleSubclass();
		performTest ("simple subclass", o);
	}
	
	
	public void testMultipleObjects()
	{
		XmlObjectOutputStream out = null;
		XmlObjectInputStream in = null;
		
		try
		{
			FileWriter fwriter = new FileWriter("temp.xml");
			out = new XmlObjectOutputStream(fwriter);
			Object o1 = createSimpleObject();
			out.writeObject(o1);
			Object o2 = createComplexObject();
			out.writeObject(o2);
			Object o3 = createSimpleObject();
			out.writeObject(o3);
			out.close();
			out = null;
			
			FileReader freader = new FileReader("temp.xml");
			in = new XmlObjectInputStream(freader);
			Object o = in.readObject();
			
			if (!o.equals(o1))
			{
				System.err.println ("FAILED --- multi-object test.");
			}
			
			o = in.readObject();
			if (!o.equals(o2))
			{
				System.err.println ("FAILED --- multi-object test.");
			}
			
			o = in.readObject();
			if (!o.equals(o3))
			{
				System.err.println ("FAILED --- multi-object test.");
			}
			
			in.close();
			in = null;
			
			System.out.println ("passed multi-object test.");
		}
		catch (Exception e)
		{
			System.err.println ("EXCEPTION during multi-object test");
			e.printStackTrace();
		}
		finally
		{
			if (null != out)
				out.close();
			
			if (null != in)
				in.close();
		}
	}
	
	
	public void testMultiplePrimitives ()
	{
		XmlObjectOutputStream out = null;
		XmlObjectInputStream in = null;
		try
		{
			out = new XmlObjectOutputStream("temp.xml");
			
			boolean bool = true;
			out.write(bool, "booleanPrimitive");
			
			byte b = 17;
			out.write(b, "bytePrimitive");
			
			char c = 'z';
			out.write(c, "charPrimitive");
			
			double d = 123.456;
			out.write(d, "doublePrimitive");
			
			float f = (float) 456.789;
			out.write(f);
			
			int i = 12345;
			out.write(i);
						
			long l = 78901234;
			out.write(l, "longPrimitive");
			
			short s = 890;
			out.write(s, "shortPrimitve");
			
			out.close();
			out = null;
			
			in = new XmlObjectInputStream("temp.xml");
			boolean bool2 = in.readBoolean();
			if (bool != bool2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			byte b2 = in.readByte();
			if (b != b2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			char c2 = in.readChar();
			if (c != c2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			double d2 = in.readDouble();
			if (d != d2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			float f2 = in.readFloat();
			if (f != f2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			int i2 = in.readInt();
			if (i != i2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			long l2 = in.readLong();
			if (l != l2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			short s2 = in.readShort();
			if (s != s2)
			{
				System.err.println ("FAILED multi-primitive test");
				return;
			}
			
			System.out.println ("multi-primitive test passed.");
		}
		catch (Exception e)
		{
			System.err.println ("EXCEPTION during multi-primitive test");
			e.printStackTrace();
		}
		finally
		{
			if (null != out)
				out.close();
			
			if (null != in)
				in.close();
		}
	}
	
	
	public Object createMap()
	{
		Map m = new HashMap();
		m.put("spam", "liwipi");
		m.put("nerts", new Integer(5));
		return m;
	}
	
	public void testMap()
	{
		try
		{
			Object o = createMap();
			writeObject(o);
		}
		catch (Exception e)
		{
			System.err.println ("EXCEPTION during map test.");
			e.printStackTrace();
		}
		
	}
	
	
	public void test ()
	{
		testArray();
		testCollection();
		testComplexCollection();
		testComplexObject();
		testSimpleObject();
		testSimpleSubclass();
		testMultipleObjects();
		testMultiplePrimitives();
		testMap();
	}
	
	
	public Object createComplexListObject()
	{
		ComplexListObject o = new ComplexListObject();
		
		o.myList = (ArrayList) createCollection();
		o.myName = "fred";
		o.myComplexObject = (ComplexObject) createComplexObject();
		o.myComplexObject.myName = "whatever";
		
		o.myList.add(o.myComplexObject);
		
		return o;
	}
	
	
	public static void main (String[] argv)
	{
		TestXmlSerializer test = new TestXmlSerializer();
		test.test();
		// test.testComplexCollection();
	}
}
