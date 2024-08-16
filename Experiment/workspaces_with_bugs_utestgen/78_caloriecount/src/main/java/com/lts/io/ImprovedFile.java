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
package com.lts.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings(value="serial")
public class ImprovedFile
    extends File
{
    public ImprovedFile (File parent, String child)
    {
        super(parent, child);
    }

    public ImprovedFile (String pathname)
    {
        super(pathname);
    }
    
    public ImprovedFile (String parent, String child)
    {
        super(parent, child);
    }
    
    public ImprovedFile (File f)
    {
        super(f.toString());
    }
    
    
    public static void copyFromToFile (File infile, File outfile)
    	throws FileNotFoundException, IOException
    {
    	FileInputStream fis = null;
    	FileOutputStream fos = null;
    	
    	try
    	{
    		fis = new FileInputStream(infile);
    		fos = new FileOutputStream(outfile);
    		int buffSize = 8192;
	    	byte[] buf = new byte[buffSize];
	    	int byteCount = fis.read(buf);
	    	while (byteCount > 0)
	    	{
	    		fos.write(buf, 0, byteCount);
	    		byteCount = fis.read(buf);
	    	}
	    }
    	finally
    	{
    		if (null != fos)
    			fos.close();
    		
    		if (null != fis)
    			fis.close();
    	}
    }
    
    
    public void copyTo (File f)
        throws FileNotFoundException, IOException
    {
    	copyFromToFile(this, f);
    }
    
    
    public static void copy (InputStream istream, OutputStream ostream)
    	throws IOException
    {
		int buffSize = 2^13;
		byte[] buf = new byte[buffSize];
		int count = istream.read(buf);
		while (count > 0)
		{
			ostream.write(buf, 0, count);
			count = istream.read(buf);
		}
		
		istream.close();
		ostream.close();
    }
    
    public void copyFrom (InputStream istream)
    	throws IOException
    {
    	FileOutputStream fos = new FileOutputStream(this);
    	ImprovedFile.copy(istream, fos);
    }
    
    
    public static File backup (File file, boolean overWriteExitingBackup)
        throws FileException
    {
    	if (!file.exists())
    	{
    		throw new FileException(Reason.FILE_DOES_NOT_EXIST, file.toString());
    	}
    	
        String[] parts = file.toString().split("\\.");
        if (parts.length < 1)
        {
        	throw new FileException(Reason.INVALID_FILE_NAME, file.toString());
        }
        
        String backupFname = parts[0] + ".bak";
        
        File f = new File(backupFname);
        
        if (f.exists() && !overWriteExitingBackup)
        {
        	throw new FileException(Reason.FILE_EXISTS, backupFname);
        }
        
        try
		{
			if (f.isDirectory())
				copyFromToDir(file, f);
			else
				copyFromToFile(file, f);
		}
		catch (IOException e)
		{
			throw new FileException(Reason.OTHER_ERROR, e);
		}

        return f;
    }
    
    
    public ImprovedFile tempBackup ()
    	throws FileNotFoundException, IOException
    {
    	File backup;
    	
    	if (this.isFile())
    	{
    		backup = createTempFile("tmp", "backup");
    		this.copyTo(backup);
    	}
    	else if (this.isDirectory())
    	{
    		backup = createTempDirectory("tmp", null);
    		copyFromToDir(this, backup);
    	}
    	else
    	{
    		throw new IOException (
    			"source file is neither directory nor file.  File: "
    			+ this
    		);
    	}
    	
    	if (backup instanceof ImprovedFile)
    		return (ImprovedFile) backup;
    	else
    		return new ImprovedFile(backup);
    }
    
    
    public static File buildName (
    	String prefix, 
		long name, 
		String suffix, 
		File dir
    )
    {
    	StringBuffer sb = new StringBuffer();
    	
    	if (null != prefix)
    		sb.append (prefix);
    	
    	sb.append (name);
    	
    	if (null != suffix)
    	{
    		sb.append ('.');
    		sb.append (suffix);
    	}
    	
    	return new File (dir, sb.toString());
    }
    
    
    public static File createTempFileName (String prefix, String suffix, File dir)
    {
    	long name = System.currentTimeMillis() % 1000000;
    	File f = buildName(prefix, name, suffix, dir);
    	
    	while (f.exists())
    	{
    		name++;
    		f = buildName(prefix, name, suffix, dir);
    	}
    	
    	return f;
    }
    
    
    /**
     * Rename the file that this object represents to a randomly generated name.
     *
     */
    public boolean tempRename ()
    {
    	File parent = this.getParentFile();
    	File tempName = createTempFileName(null, null, parent);
    	return this.renameTo(tempName);
    }
    
    
    public static ImprovedFile createTempDirectory (String prefix, String suffix)
    	throws IOException
    {
    	return createTempDirectory (prefix, suffix, null);
    }
    
    
    public static ImprovedFile createTempDirectory (
    	String prefix, 
    	String suffix, 
    	File parentDirectory
    )
    	throws IOException
    {
    	if (null == prefix)
    		prefix = "";
    	
    	if (null == suffix)
    		suffix = "";
    	
    	File f;
    	
    	if (!parentDirectory.exists() && !parentDirectory.mkdirs())
    	{
    		String msg = "Could not create directory, "
    			+ parentDirectory
    			+ ", before creating temp directory with prefix "
    			+ prefix
    			+ ", suffix "
    			+ suffix;
    		
    		throw new IOException(msg);
    	}
    	if (null == parentDirectory)
    		f = File.createTempFile(prefix, suffix);
    	else
    		f = File.createTempFile(prefix, suffix, parentDirectory);
    	
		if (f.exists())
		{
			if (!f.delete())
			{
				throw new IOException (
					"Error trying to create temp directory, " 
					+ f
					+ ", could not delete temp file to make way for directory."
				);
			}
		}
    	
		if (!f.mkdirs() || !f.isDirectory())
		{
			throw new IOException (
				"Error trying to create temp directory, "
				+ f
				+ ", the call to create the directory failed."
			);
		}
    	
		return new ImprovedFile(f);
    }
    
    public static void createTempDirectory (ImprovedFile ifile)
    	throws IOException
	{
		if (ifile.exists())
		{
			if (!ifile.delete())
			{
				throw new IOException (
					"Error trying to create temp directory, " 
					+ ifile
					+ ", could not delete temp file to make way for directory."
				);
			}
		}
    	
		if (!ifile.mkdir())
		{
			throw new IOException (
				"Error trying to create temp directory, "
				+ ifile
				+ ", the call to create the directory failed."
			);
		}
	}
    
    
    public static ImprovedFile createTempDirectory ()
    	throws IOException
    {
    	return createTempDirectory ("temp", null);
    }
    
    
    public static void rmdir (File dir, boolean removeFiles)
    	throws IOException
    {
    	File[] files = dir.listFiles();
    	
    	//
    	// empty directory --- simply remove it
    	//
    	if (files.length <= 0)
    		;
    	
    	//
    	// non-empty directory, throw an exception if we cannot remove contents
    	//
    	else if (!removeFiles)
    	{
    		throw new IOException (
    			"The directory, " + dir + ", is not empty."
    		);
    	}
    	
    	//
    	// otherwise, recursively remove directories
    	//
    	else
    	{
    		for (int i = 0; i < files.length; i++)
    		{
    			if (files[i].isDirectory())
    				rmdir(files[i], true);
    			else if (!files[i].delete())
    			{
    				throw new IOException (
    					"Failed to remove file, " + files[i]
    				);
    			}
    		}
    	}
    	
    	//
    	// remove the containing directory itself
    	//
    	if (!dir.delete())
    	{
    		throw new IOException (
				"Failed to delete directory, "
				+ dir
			);
    	}
    }
    
    
    public void deleteDirectory (boolean removeFiles) throws IOException
    {
    	rmdir (this, removeFiles);
    }
    
    public void deleteAll () throws FileException, IOException
    {
    	if (!exists())
    		return;
    	
    	if (isFile())
    	{
    		if (!delete())
    		{
    			String msg = "Could not remove file, " + toString();
    			throw new FileException(Reason.DELETE_FAILED, msg);
    		}
    	}
    	else
    	{
    		deleteDirectory();
    	}
    }
    
    
    public void deleteDirectory ()
    	throws IOException
    {
    	deleteDirectory(true);
    }
    
    
    public static ImprovedFile createTempImprovedFile (
    	String prefix, 
    	String suffix, 
    	File dir
    )
    	throws IOException
    {
    	File f = File.createTempFile(prefix, suffix, dir);
    	return new ImprovedFile(f);
    }
    
    
    public ImprovedFile createTempDir (String prefix, String suffix)
    	throws IOException
    {
    	return ImprovedFile.createTempDirectory(prefix, suffix, this);
    }
    
    public ImprovedFile createTempDir ()
    	throws IOException
    {
    	return ImprovedFile.createTempDirectory("tempdir", "", this);
    }
    
    
    public static String getLastPathComponent (File f)
    {
    	String s = f.toString();
    	String parent = f.getParent();
    	
    	if (null == parent || parent.length() <= 0)
    		return s;
    	else
    		return s.substring(parent.length());
    }
    
    
    public static void copyFromToDir (File source, File dest)
    	throws IOException
    {
    	if (source.isDirectory())
    	{
    		if (!dest.mkdirs())
    		{
    			String message = 
    				"Could not create directory " + dest;
    			throw new IOException(message);
    		}
    	}
    	
    	File[] files = source.listFiles();
    	if (null == files || files.length <= 0)
    		return;
    	
    	for (int i = 0; i < files.length; i++)
    	{
    		File newfile = new File(dest, getLastPathComponent(files[i]));
    		if (!files[i].isDirectory())
    		{
    			copyFromToFile (files[i], newfile);
    		}
    		else
    		{
    			if (!newfile.mkdirs())
    			{
    				throw new IOException (
    					"Error copying directory, could not create directory " 
    					+ newfile
    				);
    			}
    			copyFromToDir (files[i], newfile);
    		}
    	}
    }
    
    
    public void copyFrom (File source, boolean truncate)
    	throws IOException
    {
    	if (!source.exists())
    	{
    		throw new IOException ("Asked to copy non-existent file, " + source);
    	}
    	
    	if (this.exists())
    	{
    		if (source.isDirectory() && !this.isDirectory())
    		{	
    			throw new IOException (
    				"Attempt to copy a directory, " + source + " to a file, " + this
    			);
    		}
    		else if (source.isFile() && this.isDirectory())
    		{
    			throw new IOException (
    				"Attemp tto copy a file, " + source + ", to a directory, " + this
    			); 
    		}
    	}
    	
    	if (truncate)
    		this.delete();
    	
    	if (source.isFile())
    		copyFromToFile(source, this);
    	else if (source.isDirectory())
    		copyFromToDir(source, this);
    	else
    	{
    		throw new IOException (
    			"Asked to copy file that is neither directory nor regular file, " + source
    		);
    	}
    }
    
    
    public void copyFrom (File source)
    	throws IOException
    {
    	copyFrom(source, false);
    }
    
    
	public enum Reason
	{
		FILE_EXISTS,
		MKDIR_FAILED,
		RENAME_FAILED,
		FILE_DOES_NOT_EXIST, 
		DELETE_FAILED,
		INVALID_FILE_NAME,
		OTHER_ERROR
	}
	
    public static class FileException extends Exception
    {
    	private Reason myReason;
    	
    	public FileException (Reason reason, String message, Throwable cause)
    	{
    		super(message, cause);
    		initialize(reason);
    	}
    	
    	public FileException (Reason reason)
    	{
    		initialize(reason);
    	}
    	
    	public FileException (Reason reason, String message)
    	{
    		super(message);
    		initialize(reason);
    	}
    	
    	public FileException (Reason reason, Throwable cause)
    	{
    		super(cause);
    		initialize(reason);
    	}
    	
    	
    	public void initialize(Reason reason)
    	{
    		myReason = reason;
    	}
    	
    	public Reason getReason ()
    	{
    		return myReason;
    	}
    }
    
    
    /**
     * Create the directory represented by this file; throw an exception if you
     * fail.
     * 
     * <P>
     * This method is essentially {@link File#mkdirs()} except that it throws 
     * an exception rather than returning true/false.
     * </P>
     * 
     * <P>
     * If the directory currently exists, then this method does nothing.
     * </P>
     * 
     * @throws FileException If the method cannot create the directory.
     */
    public void makeDirectories () throws FileException
    {
    	basicMakeDirectories(this);
    }

    /**
     * Create any parent directories and then create the directory that the 
     * supplied file represents.
     * 
     * @param file The directory to create.
     * @throws FileException If the file cannot be created.
     */
	protected void basicMakeDirectories(File file) throws FileException
	{
		if (null == file)
			return;
		
		File parent = file.getParentFile();
		if (!parent.isDirectory())
			basicMakeDirectories(parent);
		
		if (file.isDirectory())
			return;
		
		if (file.exists())
		{
			throw new FileException(Reason.FILE_EXISTS, file.toString());
		}
		else if (!file.mkdir())
		{
			throw new FileException(Reason.MKDIR_FAILED, file.toString());
		}
	}
	
	
	/**
	 * Change the name of a file, removing anything that currently exists with
	 * the destination name.
	 * 
	 * @param destination The new name.
	 * @throws FileException If the attempt to move fails.  Reasons include:
	 * <UL>
	 * <LI>Reason.FILE_DOES_NOT_EXIST if the source does not exist.</LI>
	 * <LI>Reason.DELETE_FAILED if the destination exists and could not be 
	 * removed</LI>
	 * <LI>Reason.RENAME_FAILED if the underlying call to 
	 * {@link File#renameTo(File)} returns false.</LI>
	 * </UL>
	 */
	public void move (File destination) throws FileException
	{
		if (!exists())
		{
			throw new FileException(Reason.FILE_DOES_NOT_EXIST, toString());
		}
		
		if (destination.exists())
		{
			try
			{
				ImprovedFile dest = new ImprovedFile(destination);
				dest.deleteAll();
			}
			catch (IOException e)
			{
				String msg = 
					"Could not remove the destination file, " + destination;
				throw new FileException(Reason.DELETE_FAILED, msg, e);
			}
		}
		
		if (!renameTo(destination))
		{
			String msg =
				"renameTo returned false when trying to rename " + toString() 
				+ ", to  " + destination;
			throw new FileException(Reason.RENAME_FAILED, msg);
		}
	}

	public boolean backup(boolean overwrite) throws FileException
	{
		backup(this, overwrite);
		return true;
	}
}
