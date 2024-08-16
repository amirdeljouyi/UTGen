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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import com.lts.LTSException;
import com.lts.io.IOUtilities;
import com.lts.io.archive.ZipArchive;

/**
 * A ClassFileRepository that uses an archive like a JAR or ZIP file as its
 * source.
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class ArchiveClassRepository extends AbstractClassFileRepository 
{
	protected ZipArchive myArchive;
	
	
	public ArchiveClassRepository()
	{}
	
	public ArchiveClassRepository (File f)
	{
		initialize(f);
	}
	
	public void initialize (File archiveFile)
	{
		super.initialize(archiveFile);
		ZipArchive zarc = new ZipArchive(archiveFile);
		setArchive(zarc);
	}
	
	
	public ZipArchive getArchive()
	{
		return myArchive;
	}
	
	public void setArchive (ZipArchive zarc)
	{
		myArchive = zarc;
	}
	
	
	/* (non-Javadoc)
	 * @see com.lts.classweb.ClassFileRepository#getStreamFor(java.lang.String)
	 */
	public InputStream getStreamForClass(String className) 
		throws LTSException 
	{
		try 
		{
			String ename = classNameToFileName(className);
			return getArchive().get(ename);
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to get input stream for class "
				+ className
				+ ", from archive "
				+ getArchive().getFile(),
				e			);
		}
	}
	
	
	public byte[] getClassData (String className)
		throws LTSException
	{
		byte[] data = null;
		InputStream istream = null;
		
		try
		{
			String ename = classNameToFileName(className);
			istream = getStreamForClass(className);
			if (null != istream)
			{
				long dataSize = getArchive().getEntrySize(ename);
				data = new byte[(int) dataSize];
				DataInputStream dis = new DataInputStream(istream);
				dis.readFully(data);
			}
		}
		catch (IOException e)
		{
			throw new LTSException (
				"Error trying to load class file data for class: "
				+ className
				+ ", from archive "
				+ getArchive().getFile(),
				e
			);
		}
		finally
		{
			IOUtilities.closeNoExceptions(istream);
		}
		
		return data;
	}
	
	
	public String getFileNameFor (String className)
	{
		return getArchive().getFile().toString();
	}
	
	/**
	 * Finds the resource with the given name.
	 * 
	 * <P/>
	 * The method attempts to find the first instance of a particular resource
	 * within the repository.  It returns null if the resource cannot be
	 * found in the repository.
	 */
	public URL findResource (String name)
		throws LTSException
	{
		URL u = null;
		
		if (getArchive().entryExists(name))
		{
			u = getArchive().entryToURL(name);
		}
		
		return u;
	}
	
	/**
	 * Returns an Enumeration of URLs representing all the resources with the 
	 * given name.
	 * 
	 * <P/>
	 * Because archives require that each entry have a unique name, this method
	 * will always return a vector with at most one element.
	 */
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
		try
		{
			List l = myArchive.list();
			String[] entries = new String[l.size()];
			for (int i = 0; i < entries.length; i++)
			{
				entries[i] = (String) l.get(i);
			}
			
			return entries;
		}
		catch (IOException e)
		{
			throw new LTSException(e);
		}
	}	
}
