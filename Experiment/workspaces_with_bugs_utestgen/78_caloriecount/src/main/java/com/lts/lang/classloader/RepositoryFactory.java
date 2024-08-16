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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lts.LTSException;

/**
 * @author cnh
 */
public class RepositoryFactory 
{
	public static final String[] ARCHIVE_EXTENSIONS = {
		".jar",
		".zip"
	};
	
	public static boolean isArchiveFile (File f)
	{
		String fname = f.toString();
		int index = fname.lastIndexOf('.');
		if (-1 == index)
			return false;
		
		String suffix = fname.substring(index);
		for (int i = 0; i < ARCHIVE_EXTENSIONS.length; i++)
		{
			if (ARCHIVE_EXTENSIONS[i].equalsIgnoreCase(suffix))
				return true;
		}
		
		return false;
	}
	
	public AbstractClassFileRepository createRepository (File f) throws LTSException
	{
		AbstractClassFileRepository r = null;
		
		if (f.isDirectory())
			r = new DirectoryClassRepository(f);
		else if (isArchiveFile(f))
			r = new ArchiveClassRepository(f);
		else
		{
			throw new LTSException ("Unrecognized repository type, " + f);
		}
		
		return r;			
	}
	
	
	public List fileNamesToRepositories (List filenames) throws LTSException
	{
		List out = new ArrayList();
		
		for (Iterator i = filenames.iterator(); i.hasNext(); )
		{
			String fname = (String) i.next();
			File file = new File(fname);
			AbstractClassFileRepository repos = createRepository(file);
			out.add(repos);
		}
		
		return out;
	}
}
