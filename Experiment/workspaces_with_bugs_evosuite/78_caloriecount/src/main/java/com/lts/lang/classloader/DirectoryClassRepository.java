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
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.lang.classloader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import com.lts.LTSException;
import com.lts.io.DirectoryScanner;
import com.lts.io.IOUtilities;

/**
 * @author cnh
 */
public class DirectoryClassRepository 
	extends AbstractClassFileRepository
	implements Serializable
{
	private static final long serialVersionUID = 1L;


	public DirectoryClassRepository ()
	{}

	public DirectoryClassRepository (File f)
	{
		initialize(f);
	}
	
	
	public File classNameToFile (String className)
	{
		String fname = classNameToFileName(className);
		File f = new File(getFile(), fname);
		return f;
	}
	
	/* (non-Javadoc)
	 * @see com.lts.classweb.Repository#getStreamFor(java.lang.String)
	 */
	public InputStream getStreamForClass(String className)
		throws LTSException
	{
		String fname = null;
		
		try 
		{
			InputStream istream = null;
			File f = classNameToFile(className);
			if (f.exists())
			{
				istream = new FileInputStream(f);
			}
			return istream;
		} 
		catch (FileNotFoundException e) 
		{
			throw new LTSException (
				"Error trying to open class file for class "
				+ className
				+ ", in repository "
				+ getFile()
				+ ", under name "
				+ fname,
				e
			);
		}
	}
	
	
	public long getEntrySize (String className)
	{
		String fname = classNameToFileName(className);
		File f = new File(getFile(), fname);
		if (f.exists())
			return f.length();
		else
			return -1;
	}
	
	
	public byte[] getClassData (String className)
		throws LTSException
	{
		FileInputStream fis = null;
		
		try
		{
			File f = classNameToFile(className);
			if (!f.exists())
				return null;
			
			byte[] data = new byte[(int) f.length()];
			fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			dis.readFully(data);
			dis.close();
			return data;
		}
		catch (IOException e)
		{
			throw new LTSException (
				"Error trying to load class data for class "
				+ className
				+ ", from directory "
				+ getFile(),
				e
			);
		}
		finally
		{
			IOUtilities.closeNoExceptions(fis);
		}
	}

	public String getFileNameFor (String className)
	{
		File f = classNameToFile(className);
		return f.toString();
	}

	
	public File toFile (String name)
	{
		if (name.startsWith("/"))
		{
			name = name.substring(1);
		}
		
		File f = new File(getFile(), name);		
		return f;
	}
	
	
	public URL findResource (String name)
		throws LTSException
	{
		File f = null;
		try
		{
			f = toFile(name);
			if (!f.exists())
				return null;
			else
				return f.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			throw new LTSException (
				"Error trying to convert the file " + f
				+ " into a URL.",
				e	
			);
		}
	}
	
	
	public Vector findResources (String name)
		throws LTSException
	{
		Vector v = new Vector();
		
		URL u = findResource(name);
		if (null != u)
			v.addElement(u);
			
		return v;
	}

	
	public String[] getEntries()
		throws LTSException
	{
		DirectoryScanner dscan = new DirectoryScanner();
		dscan.setBasedir(getFile());
		dscan.scan();
		return dscan.getIncludedFiles();
	}
}
