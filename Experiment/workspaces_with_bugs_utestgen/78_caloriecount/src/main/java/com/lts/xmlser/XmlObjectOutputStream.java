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
package com.lts.xmlser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.OutputStream;
import java.io.Writer;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;

public class XmlObjectOutputStream
{
	protected IndentingPrintWriter myOut;
	
	public IndentingPrintWriter getOut()
	{
		return myOut;
	}
	
	protected XmlObjectOutputStream()
	{}
	
	
	public XmlObjectOutputStream (OutputStream ostream)
	{
		initialize(ostream);
	}
	
	public XmlObjectOutputStream (Writer writer)
	{
		initialize(writer);
	}
	
	public XmlObjectOutputStream (String filename)
		throws IOException
	{
		initialize(filename);
	}
	
	public XmlObjectOutputStream (File outfile)
		throws IOException
	{
		initialize(outfile);
	}
	
	
	public void initialize (String filename)
		throws IOException
	{
		FileWriter fwriter = new FileWriter(filename);
		initialize(fwriter);
	}
	
	public void initialize(OutputStream ostream)
	{
		IndentingPrintWriter ipw = new IndentingPrintWriter(ostream);
		initialize(ipw);
	}
	
	public void initialize (IndentingPrintWriter out)
	{
		myOut = out;
		out.println ("<xml-serialized>");
	}
	
	
	public void initialize (Writer writer)
	{
		IndentingPrintWriter ipw = new IndentingPrintWriter (writer);
		initialize(ipw);
	}
	
	
	public void initialize (File outfile)
		throws IOException
	{
		FileWriter fwrite = new FileWriter(outfile);
		IndentingPrintWriter ipw = new IndentingPrintWriter(fwrite);
		initialize(ipw);
	}
	
	
	public void close ()
	{
		if (null != getOut())
		{	
			getOut().println ("</xml-serialized>");
			getOut().close();
		}
		
		myOut = null;
	}
	
	
	public void writeObject (Object o)
		throws LTSException, IOException
	{
		XmlSerializer xser = new XmlSerializer();
		xser.writeObject(getOut(), o);
	}

	public void write(Object o) throws NotSerializableException, LTSException
	{
		write(o, null);
	}

	public void write(Object o, String tag) throws NotSerializableException, LTSException
	{
		XmlSerializer xser = new XmlSerializer();
		xser.printValue(getOut(), o, tag);
	}

	public void write(boolean b) throws LTSException
	{
		write(b, null);
	}

	public void write(boolean b, String tag) throws LTSException
	{
		writePrimitive(new Boolean(b), tag);
	}

	public void write(char c) throws LTSException
	{
		write (c, null);
	}

	public void write(char c, String tag) throws LTSException
	{
		writePrimitive(new Character(c), tag);
	}

	public void write(double d) throws LTSException
	{
		write (d, null);
	}

	public void write(double d, String tag) throws LTSException
	{
		writePrimitive(new Double(d), tag);
	}

	public void write(float f) throws LTSException
	{
		write(f, null);
	}

	public void write(float f, String tag) throws LTSException
	{
		writePrimitive(new Float(f), tag);
	}

	public void write(byte b) throws LTSException
	{
		write(b, null);
	}

	public void write(byte b, String tag) throws LTSException
	{
		writePrimitive(new Byte(b), tag);
	}

	public void write(short s) throws LTSException
	{
		write(s, null);
	}

	public void write(short s, String tag) throws LTSException
	{
		writePrimitive(new Short(s), tag);
	}

	public void write(int i) throws LTSException
	{
		write(i, null);
	}

	public void write(int i, String tag) throws LTSException
	{
		writePrimitive(new Integer(i), tag);
	}

	public void write(long l) throws LTSException
	{
		write(l, null);
	}

	public void write(long l, String tag) throws LTSException
	{
		writePrimitive(new Long(l), tag);
	}

	public void writePrimitive(Object o, String tag) throws LTSException
	{
		XmlSerializer xser = new XmlSerializer();
		xser.printPrimitive(getOut(), o, tag);
	}
}
