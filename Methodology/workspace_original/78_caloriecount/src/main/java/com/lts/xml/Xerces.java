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
/*
 * Created on Jan 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.lts.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.lts.LTSException;

/**
 * @author cnh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Xerces 
{
	protected DocumentBuilder myBuilder;
	
	public DocumentBuilder getBuilder()
		throws LTSException
	{
		if (null == myBuilder)
			myBuilder = createDocumentBuilder();
		
		return myBuilder;
	}
	
	
	public DocumentBuilder createDocumentBuilder()
		throws LTSException
	{
		try 
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to get Xerces document builder.",
				e
			);
		}
	}
	
	
	public Document parse (String fname)
		throws LTSException
	{
		try 
		{
			Document document = getBuilder().parse(fname);
			return document;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (LTSException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException ("Error parsing file: " + fname, e);
		}
	}
	
	
	public Document parse (InputStream istream)
		throws LTSException
	{
		try
		{
			return getBuilder().parse(istream);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (LTSException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to parse input stream.",
				e
			);
		}
	}
}
