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
package com.lts.io.archive;

import java.io.IOException;
import java.util.List;

import com.lts.LTSException;

/**
 * An archive that can contain other archives.
 * 
 * <P/>
 * A nested archive operates the same way that a regular archive does
 * except that the entry strings can specify multiple levels of archives.
 * Archives are separated with "!" characters.  For example:
 * 
 * <P/>
 * foo/bar/nerts.zip!what/ever/spam.jar!a/b/c.doc
 * 
 * <P/>
 * Specifies the file c.doc, which lives in the archive "spam.jar"  spam.jar, 
 * in turn, lives in the nerts.zip archive under the entry 
 * "what/ever/spam.jar"  nerts.zip, in turn, lives in the "top level" archive
 * that an instance of this class represents.
 *
 * <P/>
 * This interface is similar to the java.io.Serializeable interface in that 
 * it does not define any new methods (i.e. it is a "merit badge" interface).
 *   
 * @author cnh
 */
public interface NestedArchive 
	extends Archive 
{
	public List listEntriesAt(String entry)
		throws LTSException, IOException;
		
	public boolean isArchiveEntry (String entry);
	
	/**
	 * List the contents of the archive, including the contents of all sub-
	 * archives.
	 * 
	 * <P/>
	 * Entries use the "!" character to separate archives.  For example, the 
	 * string 
	 * 
	 * <PRE>
	 * foo/bar/nerts.doc
	 * </PRE>
	 * 
	 * Represents an entry in the "root" archive itself, whereas
	 * 
	 * <PRE>
	 * foo/bar/arc.zip!what/ever/file.doc
	 * </PRE>
	 * 
	 * Represents a file that lives at "what/ever/file.doc" in the arc.zip 
	 * archive that, in turn, lives in the root archive under the entry
	 * "foo/bar/arc.zip"
	 * 
	 * @return A list of myEntries (strings) that are all the myEntries in this 
	 * archive as well as the contents of any of the sub-archives.
	 * 
	 * @throws LTSException
	 */
	
	public List recursiveList ()
		throws LTSException;
	
}
