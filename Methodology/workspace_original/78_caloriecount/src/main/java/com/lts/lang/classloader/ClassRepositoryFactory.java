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

/**
 * A factory that knows how to create a ClassFileRepository object from a 
 * File object.
 * 
 * @author cnh
 */
public class ClassRepositoryFactory
{
	public static final int REPOS_DIRECTORY = 0;
	public static final int REPOS_ARCHIVE = 1;
	
	public static final String ARC_JAR = "jar";
	public static final String ARC_ZIP = "zip";
	

	public static final String[] REPOSITORY_FILE_TYPES = {
		ARC_JAR,
		ARC_ZIP
	};
	
	public static boolean isArchiveFile (File f)
	{
		String s = f.toString();
		int index = s.lastIndexOf('.');
		if (-1 == index)
			return false;
		
		s = s.substring(1 + index);
		int i = 0;
		while (
			i < REPOSITORY_FILE_TYPES.length 
			&& !s.equalsIgnoreCase(REPOSITORY_FILE_TYPES[i])
		)
		{
			i++;
		}
		
		return (i < REPOSITORY_FILE_TYPES.length);
	}
	
	
	public ClassRepository createRepository (File f)
	{
		ClassRepository repos = null;
		
		if (f.isDirectory())
			repos = new DirectoryClassRepository(f);
		else if (isArchiveFile(f))
			repos = new ArchiveClassRepository(f);
		
		return repos;
	}
	
	
	public List createRepositoryList (List l)
	{
		List outlist = new ArrayList();
		
		Iterator i = l.iterator();
		while (i.hasNext())
		{
			ClassRepository cfr = toClassFileRepository(i.next());
			outlist.add(cfr);
		}
		
		return outlist;
	}
	
	
	public ClassRepository toClassFileRepository (Object o)
	{
		ClassRepository cfr = null;
		
		if (o instanceof File)
			cfr = createRepository((File) o);
		else if (o instanceof ClassRepository)
			return cfr = (ClassRepository) o;
		else if (o instanceof String)
		{
			String fname = (String) o;
			File f = new File(fname);
			cfr = createRepository(f);
		}
		
		return cfr;
	}
}
