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
package com.lts.util.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.lts.LTSException;
import com.lts.io.DirectoryScanner;
import com.lts.io.ImprovedFile;
import com.lts.io.ImprovedFile.FileException;


public class SimpleZipFile
{
    protected File myFile;
    
    /**
     * A list of SimpleZipEntry objects that represent the contents of the
     * zipfile.
     */
    protected List myFileList;
    
    /**
     * Does the underlying ZIP file need to be re-created?
     */
    protected boolean myUpdateRequired;
    
    /**
     * Should the object ignore missing files when it creates the archive?
     */
    protected boolean myIgnoreMissingFiles;
    
    
    /**
     * Should updates be performed as soon as a call is made to addFile or 
     * addDirectory?
     *
     * <P>
     * If true, call createZipfile after each call to addFile or addDirectory.
     * If false, do not make those calls.
     */
    protected boolean myUpdateImmediately;
    
    /**
     * Should we create a backup file by default?
     *
     * <P>
     * If the client does not specify one way or another, should we backup 
     * the target zipfile before overwriting it?  A value of true indicates 
     * that a backup file should be created.
     */
    protected boolean myShouldCreateBackup;
    
    public boolean shouldCreateBackup()
    {
        return myShouldCreateBackup;
    }
    
    public void setShouldCreateBackup(boolean b)
    {
        myShouldCreateBackup = b;
    }
    
    
    public boolean updateImmediately()
    {
        return myUpdateImmediately;
    }
    
    public void setUpdateImmediately(boolean b)
    {
        myUpdateImmediately = b;
    }
    
    
    public boolean ignoreMissingFiles()
    {
        return myIgnoreMissingFiles;
    }
    
    public void setIgnoreMissingFiles (boolean b)
    {
        myIgnoreMissingFiles = b;
    }
    
    
    public boolean updateRequired()
    {
        return myUpdateRequired;
    }
    
    public void setUpdateRequired (boolean b)
    {
        myUpdateRequired = b;
    }
    
    
    public List getFileList ()
    {
        if (null == myFileList)
            myFileList = new ArrayList();
        
        return myFileList;
    }
    
    public void setFileList (List l)
    {
        myFileList = l;
    }
    
    
    public File getFile()
    {
        return myFile;
    }
    
    public void setFile (File f)
    {
        myFile = f;
    }
    
    
    public SimpleZipFile (File zipfile)
    {
        initialize(zipfile);
    }
    
    public SimpleZipFile (String zipfileName)
    {
        initialize(zipfileName);
    }
    
    
    public void initialize (File f)
    {
        setFile(f);
    }
    
    public void initialize (String s)
    {
        File f = new File(s);
        initialize(f);
    }
    
    
    public void basicAddFile (String dir, String fname)
    {
        String dirName = null;
        if (null != dir)
            dirName = dir.toString();
            
        SimpleZipEntry zentry = new SimpleZipEntry(dirName, fname);
        getFileList().add(zentry);
        setUpdateRequired(true);
    }
    
    
    public void basicAddDirectory (File dir)
        throws LTSException
    {
        DirectoryScanner scan = new DirectoryScanner();
        scan.setBasedir(dir);
        String[] includes = {"**"};
        scan.setIncludes(includes);
        scan.scan();
        
        String[] files = scan.getIncludedFiles();
        for (int i = 0; i < files.length; i++)
        {
            basicAddFile(dir.toString(), files[i]);
        }
    }
    
    
    public void zipFile (ZipOutputStream zout, String dirName, String fname)
        throws LTSException
    {
        File f = new File(dirName, fname);
        
        try
        {
            ZipEntry zentry = new ZipEntry(fname);
            zout.putNextEntry(zentry);
            
            FileInputStream fis = new FileInputStream(f);
            byte[] buf = new byte[4096];
            int count = 0;
            
            do {
                count = fis.read(buf);
                if (count > 0)
                    zout.write(buf, 0, count);
            } while (count > 0);
            
            fis.close();
        }
        catch (IOException e)
        {
            
            throw new LTSException(
                "Error reading file, "
                + f.toString(),
                e
            );
        }
    }
    
    
    public void createZipfile (boolean createBackup)
        throws LTSException
    {
        try
        {
            Collections.sort(getFileList(), SimpleZipEntry.getComparator());
            
            //
            // Create a temporary file initially
            //
            File tempfile = new File("tempzipfile");
            FileOutputStream fos = new FileOutputStream(tempfile);
            ZipOutputStream zout = new ZipOutputStream(fos);
            
            Iterator i = getFileList().iterator();
            while (i.hasNext())
            {
                SimpleZipEntry zentry = (SimpleZipEntry) i.next();
                zipFile(zout, zentry.myDirectoryName, zentry.myFileName);
            }
            
            zout.close();
            
            //
            // Backup the target file if requested
            //
            if (getFile().exists() && createBackup)
            {
                ImprovedFile ifile = new ImprovedFile(getFile());
                try
                {
                    ifile.backup(true);
                }
                catch (FileException e)
                {
                	String msg =
                		"Error trying to backup "
                		+ getFile()
                		+ ", reason "
                		+ e.getReason();
                	throw new LTSException(msg,e);
                }
            }
            
            getFile().delete();
            tempfile.renameTo(getFile());
            
            setUpdateRequired(false);
        }
        catch (FileNotFoundException e)
        {
            throw new LTSException (
                "Could not open temp zipfile: tempzipfile",
                e
            );
        }
        catch (IOException e)
        {
            throw new LTSException (
                "Error trying to create zipfile, "
                + getFile(),
                e
            );
        }
    }
    
    
    public void createZipfile ()
        throws LTSException
    {
        createZipfile(shouldCreateBackup());
    }
    
    
    public void addFile (File f, boolean update)
        throws LTSException
    {
        if (f.isDirectory())
            basicAddDirectory (f);
        else
            getFileList().add(f.toString());
        
        if (update)
            createZipfile();
    }
    
    public void addFile (File f)
        throws LTSException
    {
        addFile(f, updateImmediately());
    }
    
    
    public void addFile (String s, boolean update)
        throws LTSException
    {
        File f = new File(s);
        addFile(f, update);
    }
    
    public void addFile (String s)
        throws LTSException
    {
        addFile(s, updateImmediately());
    }
    
    
    public void addDirectory (File f, boolean update)
        throws LTSException
    {
        basicAddDirectory(f);
    }
    
    public void addDirectory (File f)
        throws LTSException
    {
        addDirectory(f, updateImmediately());
    }
    
    public void addDirectory (String s, boolean update)
        throws LTSException
    {
        File f = new File(s);
        addDirectory(f, update);
    }
    
    public void addDirectory (String s)
        throws LTSException
    {
        addDirectory(s, updateImmediately());
    }
    
}
