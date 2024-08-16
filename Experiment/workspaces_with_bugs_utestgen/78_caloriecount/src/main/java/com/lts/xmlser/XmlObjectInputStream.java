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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Reader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.lts.LTSException;
import com.lts.io.IOUtilities;
import com.lts.xml.XMLUtils;

/**
 * Read objects from XML input files.
 * 
 * <H3>Serialization Methods</H3>
 * This refers to the methods defined in the Java Serialization specification rather
 * than approaches to serializing objects.
 * 
 * <P>
 * For those objects that implement {@link ObjectInputValidation} the class will
 * call {@link ObjectInputValidation#validateObject()} just before returning an 
 * object from {@link #readObject()}.  This is called on each object in the "graph"
 * of objects that implements ObjectInputValidation, not just the one that is 
 * going to be returned.
 * 
 * <P>
 * Most of the other Serialization API methods, however, are not called.  This is 
 * mostly because such methods assume a binary input stream, whereas this class 
 * assumes a text input stream.  Furthermore, the class parses the entire XML document
 * prior to returning anything, whereas most of these methods assume that the 
 * system is in the midst of the input stream.
 * 
 * <P>
 * The methods that I know of that are in the API but that are not called include: 
 * 
 * <P>
 * <UL>
 * <LI>readObject
 * <LI>readObjectNoData
 * <LI>readExternal
 * <LI>readResolve
 * </UL>
 * 
 * @author cnh
 *
 */
public class XmlObjectInputStream
{
	protected Document myDocument;
	protected List myRootElements;
	protected Integer myIndex;
	protected boolean myForgiving;
	
	
	public boolean forgiving()
	{
		return myForgiving;
	}
	
	public void setForgiving(boolean forgiving)
	{
		myForgiving = forgiving;
	}
	
	
	protected XmlObjectInputStream ()
	{}
	
	
	public Document getDocument()
	{
		return myDocument;
	}
	
	public void setDocument (Document d)
	{
		reset();
		myDocument = d;
	}
	
	
	public List getRootElements()
	{
		if (null == myRootElements)
		{
			Element root = getDocument().getDocumentElement();
			myRootElements = XMLUtils.getChildElements(root);
		}
		
		return myRootElements;
	}
	
	
	public int getIndex ()
	{
		if (null == myIndex)
			myIndex = new Integer(0);
		
		return myIndex.intValue();
	}
	
	public void setIndex (int index)
	{
		myIndex = new Integer(index);
	}
	
	
	public void reset()
	{
		myRootElements = null;
		myDocument = null;
		myIndex = null;
	}
	
	public void initialize (InputSource isource)
		throws LTSException, IOException
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(isource);
			setDocument(document);
		}
		catch (FactoryConfigurationError e)
		{
			throw new LTSException (
				"Error trying to create XML parser",
				e
			);
		}
		catch (ParserConfigurationException e)
		{
			throw new LTSException (
				"Error trying to create XML parser",
				e
			);
		}
		catch (SAXException e)
		{
			throw new LTSException (
				"Error trying to parse input document.",
				e
			);
		}
		catch (IOException e)
		{
			throw e;
		}
		
	}
	
	
	public void initialize (String filename)
		throws LTSException, IOException
	{
		FileReader freader = null;
		try
		{
			freader = new FileReader(filename);
			InputSource isource = new InputSource(freader);
			initialize(isource);
		}
		finally
		{
			IOUtilities.closeNoExceptions(freader);
		}
	}
	
	
	public void initialize (File infile)
		throws LTSException, IOException
	{
		FileReader freader = null;
		try
		{
			freader = new FileReader(infile);
			InputSource isource = new InputSource(freader);
			initialize(isource);
		}
		finally
		{
			IOUtilities.closeNoExceptions(freader);
		}
	}
	
	
	public XmlObjectInputStream (InputStream istream)
		throws LTSException, IOException
	{
		try
		{
			InputSource isource = new InputSource(istream);
			initialize(isource);
		}
		finally
		{
			IOUtilities.closeNoExceptions(istream);
		}
	}
	
	
	public XmlObjectInputStream (Reader r)
		throws LTSException, IOException
	{
		try
		{
			InputSource isource = new InputSource(r);
			initialize(isource);
		}
		finally
		{
			IOUtilities.closeNoExceptions(r);
		}
	}
	
	
	public XmlObjectInputStream (String filename)
		throws LTSException, IOException
	{
		initialize(filename);
	}
	
	
	public XmlObjectInputStream (File infile)
		throws LTSException, IOException
	{
		initialize(infile);
	}
	
	
	public void validateGraph(XmlSerializer xser) throws LTSException
	{
		try
		{
			xser.validateGraph();
		}
		catch (InvalidObjectException e)
		{
			throw new LTSException(e);
		}
	}
	
	
	public Object readObject() throws LTSException
	{
		Object o = null;
		
		if (getIndex() < getRootElements().size())
		{	
			XmlSerializer xser = new XmlSerializer();
			xser.setForgiving(forgiving());
			int index = getIndex();
			Element el = (Element) getRootElements().get(index);
			o = xser.toObject(el);
			
			if (null != o)
				validateGraph(xser);
			
			setIndex(1 + index);
		}
		
		
		return o;
	}
	
	
	public void close()
	{}


	public boolean readBoolean() throws LTSException
	{
		Boolean b = (Boolean) readObject();
		return b.booleanValue();
	}


	public byte readByte() throws LTSException
	{
		Byte b = (Byte) readObject();
		return b.byteValue();
	}


	public char readChar() throws LTSException
	{
		Character c = (Character) readObject();
		return c.charValue();
	}


	public double readDouble() throws LTSException
	{
		Double d = (Double) readObject();
		return d.doubleValue();
	}


	public float readFloat() throws LTSException
	{
		Float f = (Float) readObject();
		return f.floatValue();
	}


	public int readInt() throws LTSException
	{
		Integer i = (Integer) readObject();
		return i.intValue();
	}


	public long readLong() throws LTSException
	{
		Long l = (Long) readObject();
		return l.longValue();
	}


	public short readShort() throws LTSException
	{
		Short s = (Short) readObject();
		return s.shortValue();
	}
}
