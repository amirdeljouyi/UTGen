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
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lts.LTSException;

/**
 * A ClassRepository that uses a file of some kind as the repository.
 * 
 * <P/>
 * This is an abstract class.  To be instantiateable, subclasses must define
 * the following methods:
 * <UL>
 * <LI/>getStreamForClass
 * <LI/>getClassData
 * <LI/>getFileNameFor
 * </UL>
 * 
 * <P/>
 * This is an abstract base class that provides a (very) few capabilities 
 * to help subclasses.  This is primarily supporting a file property and 
 * the classNameToFileName method.
 * 
 * @author cnh
 */
public abstract class AbstractClassFileRepository
	implements ClassRepository, Serializable
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
	public abstract InputStream getStreamForClass(String className)
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
	public abstract byte[] getClassData (String className)
		throws LTSException;

	/**
	 * The file that is the location where the class files are located.  This
	 * property should never be null once the instance has been initialized. 
	 */
	protected File myFile;


	public File getFile()
	{
		return myFile;
	}
	
	public void setFile (File f)
	{
		myFile = f;
	}
	
	
	public void initialize (File f)
	{
		setFile(f);
	}
	
	/**
	 * Convert a class name into a file name.
	 * 
	 * <P/>
	 * An example of a class name: java.lang.Integer  An example of the 
	 * equivalent file name: java/lang/Integer.class
	 * 
	 * @param className The class name to convert.
	 * @return The equivalent file name.
	 */
	public String classNameToFileName (String className)
	{
		String s = className.replace('.', '/');
		s = s + ".class";
		return s; 
	}
	
	
	public String toString ()
	{
		return getFile().toString();
	}
	
	
	public String[] getClassEntries()
		throws LTSException
	{
		String[] entries = getEntries();
		List l = new ArrayList(entries.length);
		
		for (int i = 0; i < entries.length; i++)
		{
			if (entries[i].endsWith(".class"))
				l.add(entries[i]);
		}
		
		String[] classEntries = new String[l.size()];
		for (int i = 0; i < classEntries.length; i++)
		{
			String s = (String) l.get(i);
			s = s.replaceAll("\\\\", ".");
			s = s.replaceAll("/", ".");
			classEntries[i] = s;
		}
		
		return classEntries;
	}
	
	
	public List getClassNames() throws LTSException
	{
		String[] fnameArray = getClassEntries();
		List l = new ArrayList();
		
		if (null == fnameArray)
			return l;
		
		for (int i = 0; i < fnameArray.length; i++)
		{
			String s = fnameArray[i];
			int length = s.length();
			s = s.substring(0, length - 6);
			l.add(s);
		}
		
		return l;
	}
	
	
	public File getRepositoryFile ()
	{
		return getFile();
	}
}
