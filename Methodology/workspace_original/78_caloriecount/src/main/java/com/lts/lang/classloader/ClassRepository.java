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
 * Copyright (c) 2003 Long Term Software, LLC.
 */
package com.lts.lang.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import com.lts.LTSException;

/**
 * An object from which class data, specifically classfile data, can be 
 * obtained.
 * 
 * <P/>
 * Instances of this interface can be used by a classloader or similar client
 * to obtain classfile data.  The primary methods used are getClassData and 
 * getStreamForClass.
 * 
 * <P/>
 * The interface should really define methods to allow clients to get resources
 * as well but, due to time constraints, this was not provided.
 * 
 * @author cnh
 */
public interface ClassRepository
{
	/**
	 * Try to locate a particular class within the repository and return
	 * an input stream to the class file data for that class; or null if the 
	 * class could not be found.
	 * 
	 * @param className The class name should be in "dotted" notation.  For 
	 * example: java.lang.Integer
	 * 
	 * @return An input stream to the classfile data or null if the repository
	 * does not contain the class.
	 * 
	 * @throws LTSException If there is a problem loading the class.  Note that
	 * the method should not throw this exception if the repository does not 
	 * contain the class.  In that case, it should simply return null.
	 */
	public InputStream getStreamForClass(String className)
		throws LTSException;
	
	/**
	 * Return the classfile data for a given class or null if the repository 
	 * does not contain the class.
	 * 
	 * @param className The class name should be in "dotted" notation.  For 
	 * example: java.lang.Integer
	 * 
	 * @return The classfile data for the class, as an array of bytes.
	 * 
	 * @throws LTSException If there is a problem loading the class.  Note that
	 * the method should not throw this exception if the repository does not 
	 * contain the class.  In that case, it should simply return null.
	 */
	public byte[] getClassData (String className)
		throws LTSException;
		
	/**
	 * Returns an Enumeration of URLs representing all the resources with the 
	 * given name.
	 * 
	 * <P/>
	 * For no reason that makes sense to me, this method can throw an 
	 * IOException, whereas findResource cannot.
	 *  
	 * @param name The name of the resource to find.
	 * 
	 * @return An enumeration of URLs that indicate where to find the resource.
	 * If the resource could not be found, the enumeration will be empty.
	 * 
	 * @throws IOException If an IOException occurred during the search.
	 */
	public Vector findResources (String name)
		throws LTSException;
	
	/**
	 * Finds the resource with the given name.
	 * 
	 * <P/>
	 * This method tries to find a "resource" in the repository, given the 
	 * name of the resource.  If the resource cannot be found, it returns
	 * null.
	 * 
	 * @param name The name of the resource to search for.
	 * 
	 * @return A URL to the resource, or null if the resource could not be 
	 * found in this repository.
	 */
	public URL findResource (String name)
		throws LTSException;
	
	
	public String[] getEntries () throws LTSException;
	public String[] getClassEntries() throws LTSException;
	public List getClassNames() throws LTSException;

	public File getRepositoryFile () throws LTSException;
}
