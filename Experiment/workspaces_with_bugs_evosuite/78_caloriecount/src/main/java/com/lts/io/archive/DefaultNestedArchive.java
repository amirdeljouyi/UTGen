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
 * Copyright (c) Long Term Software, LLC. All rights reserved.
 */
package com.lts.io.archive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.lts.LTSException;
import com.lts.io.ImprovedFile;

/**
 * An AbstractNestedArchive that assumes all "sub-archives" are some sort 
 * of ZIP based file.
 * 
 * @author cnh
 */
public class DefaultNestedArchive 
	extends AbstractNestedArchive 
{

	/* (non-Javadoc)
	 * @see com.lts.io.archive.AbstractNestedArchive#openArchive(com.lts.io.archive.Archive, java.lang.String)
	 */
	public Archive openArchive(Archive parent, String entry) 
		throws LTSException
	{
		try 
		{
			InputStream istream = parent.get(entry);
			ImprovedFile subdir = getTempDir().createTempDir("narc", "");
			ZipArchive zarc = new ZipArchive(istream, subdir);
			return zarc;
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to create temp archive",
				e
			);
		}
	}
	
	
	public Archive openArchive (ImprovedFile archiveFile, ImprovedFile tempdir)
	{
		ZipArchive zarc = new ZipArchive(archiveFile, tempdir, true);
		return zarc;
	}


	public DefaultNestedArchive (File arcFile, ImprovedFile tempdir)
		throws LTSException
	{
		initialize(arcFile, tempdir);
	}
	
	public URL entryToURL (String name)
	{
		throw new RuntimeException ("not implemented");
	}
}
