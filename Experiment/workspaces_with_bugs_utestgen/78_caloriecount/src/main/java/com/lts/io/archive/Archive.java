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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import com.lts.LTSException;
import com.lts.util.deepcopy.DeepCopier;

/**
 * A collection of files.
 *
 * <P>
 * This interface defines the methods, etc. that a client expects from a kind 
 * of "virtual file system."  The intention for this interface is to provide a
 * simple interface into various archive files such as TAR files, ZIPs, etc.
 */
public interface Archive extends DeepCopier
{
    /**
     * Write out any changes that have been made to the archive.
     */
    public void commit()
        throws LTSException, IOException;
    
    public void commitTo (File outfile)
    	throws LTSException;
    	
    /**
     * Undo any changes that have been made to the archive since the last 
     * commit.
     */
    public void rollback()
        throws LTSException;
    
    /**
     * Create a list of strings that represent the files contained in the 
     * archive.
     */
    public List list()
        throws LTSException, IOException;
    
    /**
     * Add a new file to the archive, with the specified file name.
     *
     * <P>
     * If an entry already exists with the same name, it is overwritten by the
     * new entry.
     */
    public void add (String name, File infile)
        throws LTSException, FileNotFoundException, IOException;
    
    /**
     * Add a new file to the archive, taking the contents of the file from the
     * provided input stream.
     */
    public void add (String name, InputStream istream)
        throws LTSException, IOException;
    
    public OutputStream createEntry (String name)
    	throws LTSException;
    	
    /**
     * Remove an entry from the archive.
     *
     * <P>
     * The method returns whether or not the entry existed in the archive to 
     * begin with.  A value of true means that the entry was in the archive 
     * before the call was made.
     */
    public boolean remove (String name)
        throws LTSException, IOException;
    
    /**
     * Get an input stream that represents the specified entry.
     */
    public InputStream get (String name)
        throws LTSException, IOException;
    
    
    /**
     * Does an entry exist in the archive?
     * 
     * @return true if the entry exists, false otherwise.
     */
	public boolean entryExists (String name)
		throws LTSException;
		
	/**
	 * Get the File object that the archive corresponds to.  
	 * 
	 * <P/>
	 * If the archive does not have a corresponding file, as might be the
	 * case if the archive was obtained via a URL, then return null.
	 * 
	 * @return The file for the archive.
	 */
	public File getFile();
	
	
	/**
	 * Copy an entry from the archive to the provided file.
	 * 
	 * <P/>
	 * The file contents are overwritten in favor of the entry.
	 * 
	 * @param entry The entry to extract.  See the class description for a 
	 * discussion of the format of archive myEntries.
	 * 
	 * @param outputFile The file where the entry data should go.
	 * 
	 * @throws LTSException
	 * @throws IOException
	 */
	public void extractToFile (String entry, File outputFile)
		throws LTSException, IOException;
	
	/**
	 * Return an URL that can be used to access the entry.
	 * 
	 * @param name The entry name to search for.
	 * @return The URL to reach the resource or null if the entry does not
	 * exist.
	 */
	public URL entryToURL (String entry);
	
	public static final int ENTRY_FILE = 0;
	public static final int ENTRY_DIRECTORY = 1;
	public static final int ENTRY_ARCHIVE = 2;
	
	public abstract int getEntryType (String entry)
		throws LTSException;
	
	
    /**
     * Convenience method
     */
    public Object loadObject (String name)
        throws LTSException, IOException;
    
    public void saveObject (String name, Object o)
        throws LTSException, IOException;
    
    public void saveProperties (String name, Properties p)
        throws LTSException, IOException;
    
    public Properties loadProperties (String name)
        throws LTSException, IOException;
}

    
