//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.application.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lts.LTSException;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationMessages;
import com.lts.io.IOUtilities;
import com.lts.io.ImprovedFile;
import com.lts.io.archive.ZipArchive;

/**
 * A repository that uses some variation of a zip file to store its data.
 * 
 * <H2>Abstract Class</H2>
 * To create a subclass that can be instantiated, the following methods must be 
 * defined:
 * <UL>
 * <LI>loadApplicationData
 * <LI>storeApplicationData
 * </UL>
 * 
 * <H2>Description</H2>
 * The repository works by extracting the archive out to a temporary directory
 * and doing all its work in that directory.  Thus creates, reads, updates and 
 * deletes are made against an underlying file system.
 * 
 * <P>
 * When the time comes to commit, the repository simply zips up the temp directory
 * and presents the zip file.  For a rollback, the temp directory is deleted, along 
 * with its contents, and the zip file is extracted out to the temp directory again.
 * 
 * @author cnh
 */
public abstract class ArchiveRepository extends AbstractRepository
{
	private ZipArchive myZipArchive;
	private String myFilename;
	
	public ZipArchive getZipArchive()
	{
		return myZipArchive;
	}
	
	public ArchiveRepository(File archiveFile, File tempdir, boolean createBackups) throws ApplicationException
	{
		initialize(archiveFile, tempdir, createBackups);
	}
	
	public void initialize (File archiveFile, File tempdir, boolean createBackups) throws ApplicationException
	{
		ImprovedFile ifile = IOUtilities.toImprovedFile(tempdir);
		
		try
		{
			myFilename = archiveFile.toString();
			tempdir = ifile.createTempDir();
		}
		catch (IOException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_CREATING_TEMP_DIR,
					tempdir
			);	
		}
		
		ZipArchive zarc = new ZipArchive(archiveFile, tempdir, createBackups);
		myZipArchive = zarc;
	}

	public ArchiveRepository()
	{}
	
	
	public void delete() throws ApplicationException
	{
		if (null != getZipArchive())
		{
			getZipArchive().getArchiveFile().delete();
		}
	}

	public void commit() throws ApplicationException
	{
		try
		{
			getZipArchive().commit();
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_WRITE,
					getZipArchive().getArchiveFile().toString()
			);
		}
	}
	
	
	private String getZipFileName ()
	{
		File zfile = null;
		String filename;
		if (null == getZipArchive())
			filename = "no archive file";
		else
			zfile = getZipArchive().getArchiveFile();
		
		if (null == zfile)
			filename = "no zip file";
		else
			filename = zfile.toString();
		
		return filename;
	}

	public void rollback() throws ApplicationException
	{
		String filename = null;
		
		try
		{
			File theFile = null;
			ZipArchive zarc = getZipArchive();
			if (null != zarc)
				theFile = zarc.getArchiveFile();
			
			if (null != theFile)
				filename = theFile.toString();
			
			getZipArchive().rollback();
		}
		catch (LTSException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_ROLLBACK,
					filename
			);
		}
	}

	
	public void commitAs(File outfile) throws ApplicationException
	{
		try
		{
			getZipArchive().commitTo(outfile);
		}
		catch (LTSException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_WRITE,
					getZipFileName()
			);
		}
	}

	public File getRepositoryFile() throws ApplicationException
	{
		return getZipArchive().getFile();
	}

	public boolean removeEntry(String entry) throws ApplicationException
	{
		boolean present;
		
		try
		{ present = myZipArchive.entryExists(entry); }
		catch (LTSException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_ACCESS,
					myFilename
			);
		}
		
		if (present)
		{
			try { myZipArchive.remove(entry); }
			catch (Exception e)
			{
				if (e instanceof RuntimeException)
					throw (RuntimeException) e;
				
				throw new ApplicationException (
						e,
						ApplicationMessages.ERROR_REPOSITORY_DELETE,
						myFilename
				);
			}
		}
		
		return present;
	}

	public OutputStream getOutputStream(String entry, boolean append) throws ApplicationException
	{
		if (append)
		{
			throw new ApplicationException (
					ApplicationMessages.ERROR_REPOSITORY_GET_OUTPUT_STREAM,
					myZipArchive.getArchiveFile().toString(),
					entry
			);
		}
		
		try
		{
			return myZipArchive.getOutputStream(entry);
		}
		catch (LTSException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_GET_OUTPUT_STREAM,
					myZipArchive.getArchiveFile().toString(),
					entry
			);
		}
		catch (IOException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_READ,
					myZipArchive.getArchiveFile().toString(),
					entry
			);
					
		}
	}

	public InputStream getInputStream(String entry) throws ApplicationException
	{
		try
		{
			return myZipArchive.getInputStream(entry);
		}
		catch (LTSException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_GET_INPUT_STREAM,
					myZipArchive.getArchiveFile().toString(),
					entry
			);
		}
		catch (IOException e)
		{
			throw new ApplicationException (
					e,
					ApplicationMessages.ERROR_REPOSITORY_GET_INPUT_STREAM,
					myZipArchive.getArchiveFile().toString(),
					entry
			);
		}
	}
	
	
	public void close () throws ApplicationException
	{
		if (null == myZipArchive)
			return;
		
		rollback();
		try
		{
			myZipArchive.removeTempFiles();
		}
		catch (IOException e)
		{
			String msg = 
				"Error removing temp files for the archive "
				+ myZipArchive.getFile();
			throw new ApplicationException(msg, e);
		}
	}
	
	
	public boolean repositoryUsesDirectories()
	{
		return false;
	}
}