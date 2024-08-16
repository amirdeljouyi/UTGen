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
package com.lts.io.archive;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.lts.LTSException;
import com.lts.io.DirectoryScanner;
import com.lts.io.IOUtilities;
import com.lts.io.ImprovedFile;
import com.lts.util.deepcopy.DeepCopyException;


/**
 * An archive that uses a zip file to store files.
 */
public class ZipArchive extends AbstractTempDirectoryArchive
{
	protected ZipFile myZipFile;
	
	public ZipFile getZipFile () throws LTSException
	{
		try 
		{
			if (null == myZipFile)
				myZipFile = new ZipFile(getArchiveFile());
			
			return myZipFile;
		} 
		catch (ZipException e) 
		{
			throw new LTSException (
				"Error trying to initialize zipfile for " 
				+ getArchiveFile(),
				e
			);
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to initialize zipfile for "
				+ getArchiveFile(),
				e
			);
		}
	}
	
	
	public void finalize ()
	{
		try
		{
			if (null != myZipFile)
				myZipFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public ZipArchive ()
	{}
	
	
    public ZipArchive (File zipfile, File tempdir, boolean createBackups)
    {
        initialize(zipfile, tempdir, createBackups);
    }
    
    public ZipArchive (String zipfileName)
    {
        initialize(zipfileName);
    }
    
    
    public ZipArchive (File archiveFile)
    {
    	initialize(archiveFile);
    }
    
    
    public ZipArchive (InputStream istream, ImprovedFile tempdir)
    	throws IOException
    {
    	initialize(istream, tempdir);
    }
    
    
    /**
     * Extract the contents of the archive to the directory passed to the 
     * method.
     */
    public void extractArchive (File dir)
        throws LTSException, IOException
    {
        try
        {
            if (!getArchiveFile().exists() || getArchiveFile().length() <= 0)
                return;
            
            ZipFile zfile = new ZipFile(getArchiveFile());
            Enumeration enu = zfile.entries();
            while (enu.hasMoreElements())
            {
                ZipEntry zentry = (ZipEntry) enu.nextElement();
				File outputFile = new File(dir, zentry.getName());
                if (zentry.isDirectory())
                {
                	if (!outputFile.isDirectory() && !outputFile.mkdirs())
                	{
                		throw new LTSException (                			"Failed to create directory, "
                			+ outputFile
                		);	
                	}
                }
                else
                {
	                createParentDirectories(outputFile);
	                FileOutputStream fos = new FileOutputStream(outputFile);
	                InputStream istream = zfile.getInputStream(zentry);
	                copyFromTo(istream, fos);
	                istream.close();
	                fos.close();
                }
            }
        }
        catch (ZipException e)
        {
            throw new LTSException (
                "Error trying to read zipfile, "
                + getArchiveFile(),
                e
            );
        }
    }
    
    
   	public boolean entryExists (String name)
   		throws LTSException
   	{
   		try
   		{
   			if (!getArchiveFile().exists())
   				return false;
   			
   			if (name.startsWith("/"))
   				name = name.substring(1);
   			
   			ZipFile zfile = new ZipFile(getArchiveFile());
   			ZipEntry zentry = zfile.getEntry(name);
   			return null != zentry;
   		}
   		catch (IOException e)
   		{
   			throw new LTSException (
   				"Error trying to ascertain the existence of entry, " + name,
   				e
   			);
   		}
   	}
   	
   	
   	public ZipEntry getZipEntry (String name)
   		throws LTSException
   	{
		try
		{
			if (name.startsWith("/"))
				name = name.substring(1);
			
			ZipFile zfile = new ZipFile(getArchiveFile());
			ZipEntry zentry = zfile.getEntry(name);
			return zentry;
		}
		catch (ZipException e)
		{
			throw new LTSException (
				"Error trying to get zip entry for "
				+ name
				+ ", from archive, "
				+ getArchiveFile(),
				e
			);
		}
		catch (IOException e)
		{
			throw new LTSException (
				"Error trying to get zip entry for "
				+ name
				+ ", from archive, "
				+ getArchiveFile(),
				e
			);
		}
   	}
   	
   	
    /**
     * Get a file from the archive file rather than the directory "cache."
     */
    public InputStream basicGet (String name)
        throws LTSException, IOException
    {
        try
        {
        	if (name.startsWith("/"))
        		name = name.substring(1);
        		
            ZipFile zfile = new ZipFile(getArchiveFile());
            ZipEntry zentry = zfile.getEntry(name);
            if (null == zentry)
                return null;
            
            return zfile.getInputStream(zentry);
        }
        catch (ZipException e)
        {
            throw new LTSException (
                "Error trying to read entry, "
                + name
                + ", from archive, "
                + getArchiveFile(),
                e
            );
        }
    }
    
    
    public long getEntrySize (String entry)
    	throws LTSException
    {
    	ZipEntry zentry = getZipEntry(entry);
    	if (null == zentry)
    		return -1;
    	else
    		return zentry.getSize();
    }
    
    /**
     * Get a list of the files from the archive file rather than using the
     * directory cache.
     */
    public List basicList()
        throws LTSException, IOException
    {
        try
        {
            ZipFile zfile = new ZipFile(getArchiveFile());
            Enumeration enu = zfile.entries();
            ArrayList l = new ArrayList();
            while (enu.hasMoreElements())
            {
                ZipEntry zentry = (ZipEntry) enu.nextElement();
                l.add(zentry.getName());
            }
            return l;
        }
        catch (ZipException e)
        {
            throw new LTSException (
                "Error trying to list files in archive, "
                + getArchiveFile(),
                e
            );
        }
    }
    
    /**
     * Create an archive file, overwriting any existing file with the same 
     * name, using the files from the specified directory.
     */
    public void writeArchiveFromTo (File inputDir, File outputFile)
        throws LTSException, IOException
    {
        DirectoryScanner scan = new DirectoryScanner();
        scan.setBasedir(inputDir);
        String[] includes = {"**"};
        scan.setIncludes(includes);
        scan.scan();
        String[] files = scan.getIncludedFiles();
        
        FileOutputStream fos = new FileOutputStream(outputFile);
        ZipOutputStream zout = new ZipOutputStream(fos);
        
        for (int i = 0; i < files.length; i++)
        {
            File inputFile = new File(inputDir, files[i]);
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream(inputFile);
            }
            catch (FileNotFoundException e)
            {
                throw new LTSException (
                    "Error trying to open file, "
                    + inputFile
                    + ", in preparation to write to archive, "
                    + getArchiveFile(),
                    e
                );
            }

			String s = convertOneString(files[i]);
            ZipEntry zentry = new ZipEntry(s);
            zout.putNextEntry(zentry);
            copyFromTo (fis, zout);
            fis.close();
        }
        
        zout.close();
    }
    
	
	public void closeNoExceptions (InputStream istream)
	{
		if (null == istream)
			return;
			
		try
		{
			istream.close();
		}
		catch (IOException e)
		{}
	}
	
	public void closeNoExceptions (OutputStream ostream)
	{
		if (null == ostream)
			return;
			
		try
		{
			ostream.close();
		}
		catch (IOException e)
		{}
	}
	
	
	public void extractToFile (String entry, File outfile)
		throws LTSException, IOException
	{
		InputStream istream = null;		
		FileOutputStream fos = null;
		
		try 
		{
			istream = get(entry);
			fos = new FileOutputStream(outfile);
			byte[] buf = new byte[8192];
			int count;
			
			count = istream.read(buf);
			while (count > 0)
			{
				fos.write(buf, 0, count);
				count = istream.read(buf);
			}
		} 
		catch (FileNotFoundException e) 
		{
			throw new LTSException (
				"Could not find output file, " + outfile,
				e
			);
		} 
		finally
		{
			closeNoExceptions(istream);
			closeNoExceptions(fos);
		}
	}
	
	
	public List enumToList (Enumeration enu)
	{
		List l = new ArrayList();
		
		while (enu.hasMoreElements())
		{
			ZipEntry zentry = (ZipEntry) enu.nextElement();
			l.add(zentry.getName());
		}
		return l;
	}
	
	
	public int getEntryType (String s)
		throws LTSException
	{
		String fname = getArchiveFile().toString();
		
		try 
		{
			ZipFile zfile = new ZipFile(getArchiveFile());
			// List l = enumToList(zfile.entries());
			ZipEntry zentry = zfile.getEntry(s);
			if (null == zentry)
				return -1;
			
			if (zentry.isDirectory())
				return ENTRY_DIRECTORY;
			else
				return ENTRY_FILE;
		} 
		catch (ZipException e) 
		{
			throw new LTSException (
				"Error trying to load entry " + fname,
				e
			);
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to load entry " + fname,
				e
			);
		}
	}
	
	public void createArchive()
		throws LTSException
	{
		if (getFile().exists())
			return;
		
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		try 
		{
			fos = new FileOutputStream(getFile());
			zos = new ZipOutputStream(fos);
			ZipEntry zentry = new ZipEntry("junk");
			zos.putNextEntry(zentry);
			String s = "A zip file requires at least one entry.";
			zos.write(s.getBytes());
			zos.close();
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error creating archive file, " 
				+ getFile(),
				e
			);
		}
		finally
		{
			IOUtilities.closeNoExceptions(fos);
			IOUtilities.closeNoExceptions(zos);
		}
		
	}
	
	
	public URL entryToURL (String entry)
	{
		try
		{
			String cpath = getArchiveFile().getCanonicalFile().toURI().toURL().toString();
			String spec =
				"jar:"
				+ cpath
				+ "!/" + entry;
			
			System.err.println(spec);
			URL url = new URL(spec);
			return url;
		}
		catch (IOException e)
		{
			return null;
		}
	}
	
	public void deepCopyData(Object ocopy, Map map, boolean copyTransients)
		throws DeepCopyException
	{
		super.deepCopyData(ocopy, map, copyTransients);
		
		// ZipFile file = new ZipFile(this.myZipFile.getName(), this.myZipFile);
		
		ZipArchive copy = (ZipArchive) ocopy;
		try
		{
			copy.myZipFile = new ZipFile(this.myZipFile.getName());
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new DeepCopyException(e);
		}
	}

}
