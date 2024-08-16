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
package com.lts.io;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.lts.LTSException;
import com.lts.io.archive.DefaultNestedArchive;
import com.lts.io.archive.NestedArchive;

/**
 * @author cnh
 */
public class ArchiveScanner 
	extends DirectoryScanner 
{
	protected ImprovedFile myTempdir;
	
	
	public static final int FILE = 0;
	public static final int DIRECTORY = 1;
	public static final int ARCHIVE = 2;
	
	
	public static final String[] DEFAULT_ARCHIVE_EXTENSIONS = {
		".jar",
		".zip",
		".war",
		".ear",
		".JAR",
		".ZIP",
		".WAR",
		".EAR"
	};
	
	
	
	public ArchiveScanner (ImprovedFile tempdir)
	{
		super();
		myTempdir = tempdir;
	}
	
	
	public boolean isArchive (File f)
	{
		String s = f.getName();
		boolean fileIsArchive = false;
		int i = 0;
		
		while (!fileIsArchive && i < DEFAULT_ARCHIVE_EXTENSIONS.length)
		{
			fileIsArchive = s.endsWith(DEFAULT_ARCHIVE_EXTENSIONS[i]);
			i++;
		}
		
		return fileIsArchive;
	}
	
	
	public int toFileType(File f)
	{
		int type = -1;
		
		if (f.isDirectory())
			type = DIRECTORY;
		else if (isArchive(f))
			type = ARCHIVE;
		else if (f.isFile())
			type = FILE;
		
		return type;
	}
	
	
	
	public void processDirectory (File file, String name, boolean fast)
		throws LTSException
	{
		if (isIncluded(name)) 
		{
			if (!isExcluded(name)) {
				dirsIncluded.addElement(name);
				if (fast)
					scandir(file, name+File.separator, fast);
			} 
			else 
			{
				dirsExcluded.addElement(name);
			}
		} 
		else 
		{
			dirsNotIncluded.addElement(name);
			if (fast && couldHoldIncluded(name)) 
			{
				scandir(file, name+File.separator, fast);
			}
		}
		
		if (!fast) 
		{
			scandir(file, name+File.separator, fast);
		}
	}
	
	
	public void processFile (String name)
	{
		if (!isIncluded(name))
			filesNotIncluded.addElement(name);
		else if (isExcluded(name))
			filesExcluded.addElement(name);
		else 
			filesIncluded.addElement(name);
	}
	
	
	
	public void scanArchive (File arcFile, String name)
		throws LTSException
	{
		try 
		{
			NestedArchive narc = new DefaultNestedArchive(arcFile, myTempdir);
			List l = narc.recursiveList();
			Iterator iter = l.iterator();
			while (iter.hasNext())
			{
				String s = (String) iter.next();
				s = name + "!" + s;
				processFile(s);
			}
		}
		finally {} 
	}
	
	
	public void processArchive (File file, String name)
		throws LTSException
	{
		if (!isIncluded(name))
			filesNotIncluded.addElement(name);
		else if (isExcluded(name))
			filesExcluded.addElement(name);
		else 
		{
			filesIncluded.addElement(name);
			scanArchive(file, name);
		}
	}
	
	protected void scandir(File dir, String vpath, boolean fast)
		throws LTSException
	{
		String[] newfiles = dir.list();

		if (newfiles == null) 
		{
			/*
			 * two reasons are mentioned in the API docs for File.list
			 * (1) dir is not a directory. This is impossible as
			 *     we wouldn't get here in this case.
			 * (2) an IO error occurred (why doesn't it throw an exception 
			 *     then???)
			 */
			throw new LTSException(
				"IO error scanning directory " + dir.getAbsolutePath()
			);
		}

		for (int i = 0; i < newfiles.length; i++) 
		{
			String name = vpath+newfiles[i];
			File   file = new File(dir,newfiles[i]);
			int ftype = toFileType(file);
			switch (ftype)
			{
				case DIRECTORY :
					processDirectory(file, name, fast);
					break;
				
				case ARCHIVE :
					processArchive(file, name);
					break;
				
				case FILE :
					processFile(name);
					break;
			}
		}
	}

}
