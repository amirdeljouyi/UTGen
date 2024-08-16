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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.lts.LTSException;

/**
 * A simple ClassLoader that uses ClassRepositories to load classes.
 * 
 * <P/>
 * This loader only defines findClass and loadClass.  It should really also 
 * override the resource family of methods, but due to time constraints this
 * has been left as an enhancement.  The resource family of methods that 
 * a ClassLoader should define are: findResource, findResources, 
 * getResource, getResourceAsStream and getResources.
 * 
 * @author cnh
 */
public class BasicClassLoader 
	extends ClassLoader
{
	/**
	 * A list of ClassRepository objects that represent the repositories
	 * that this ClassLoader look in for classes.
	 * 
	 * <P/>
	 * This property should only be accessed through the accessor methods.
	 * The property should never be null --- if the ClassLoader has no 
	 * repositories then it should return an empty list.
	 * 
	 * <P/>
	 * The list also defines the order in which the repositories are searched.
	 */
	protected List myRepositories;
	
	public BasicClassLoader ()
	{
		super();
	}
	
	public BasicClassLoader (ClassLoader parent)
	{
		super(parent);
	}
	
	
	public List getRepositories()
	{
		if (null == myRepositories)
			myRepositories = new ArrayList();
		
		return myRepositories;
	}
	
	public void setRepositories (List l)
	{
		myRepositories = l;
	}
	
	public void addRepository (ClassRepository repos)
	{
		getRepositories().add(repos);
	}
	
	public boolean removeRepository (ClassRepository repos)
	{
		return getRepositories().remove(repos);
	}
	
	/**
	 * Called when a class is found.
	 * 
	 * <P/>
	 * This method does nothing.  It is intended to allow sub-classes to 
	 * perform some action like appending to a logfile or the like when the 
	 * loader manages to find something.
	 * 
	 * @param name The name of class that the loader found.  This will be 
	 * a fully qualified name like java.lang.Integer.
	 * 
	 * @param repos The repository where the named item was found.
	 */
	public void logFoundClass (String name, ClassRepository repos)
	{}
	
	
	public void logFindClassStart (String name)
	{
		
	}
	
	
	public void logFindClassEnd (String name, Throwable e)
	{
		
	}
	
	/**
	 * Called when a resource is found.
	 * 
	 * <P/>
	 * This method does nothing.  It is intended to allow sub-classes to 
	 * perform some action when a resource is found, like logging the event 
	 * to the screen.
	 * 
	 * @param name The name of the resource that was found.
	 * @param repos The repository where the resource was found.
	 */
	public void logFoundResource (String name, ClassRepository repos)
	{}
	
	
	private Map myClassCache = new HashMap();
	
	public Map getClassCache()
	{
		return myClassCache;
	}
	
	
	public static final int EVENT_START_FIND = 0;
	public static final int EVENT_END_FIND = 1;
	
	public static final int EVENT_LAST = EVENT_END_FIND;
	
	/**
	 * Find a specified class, looking in the various repositories.
	 * 
	 * <P/>
	 * Because this ClassLoader uses the new "delegation" approach defined
	 * in JDK 1.4, it does not try to ask the system or the parent ClassLoader
	 * to find the class.
	 * 
	 * @param name The name of the class to find.  This should be a 
	 * "fully qualified name" like java.lang.Integer.
	 * 
	 * @return The corresponding class.  The method never returns null, if 
	 * the class could not be found, the method throws ClassNotFoundException
	 * instead.
	 * 
	 * @throws ClassNotFoundException if the class could not be found or if
	 * there was some sort of error during the search.  If there was an error,
	 * the exception should contain an LTSException that details the problem.
	 */
	protected Class findClass (String name)
		throws ClassNotFoundException
	{
		Class c = null;
		ClassNotFoundException cnfe = null;
		
		try
		{
			logEvent(name, EVENT_START_FIND);

			Iterator i = getRepositories().iterator();
			while (i.hasNext())
			{
				ClassRepository repos = (ClassRepository) i.next();
				byte[] data = repos.getClassData(name);
				if (null != data)
				{
					c = defineClass(name, data, 0, data.length);
					myClassCache.put(name, c);
				}
			}

			if (null == c)
			{
				cnfe = new ClassNotFoundException(name);
				throw cnfe;
			}

			logFindClassEnd(name, null);
			return c;
		}
		catch (LTSException e)
		{
			cnfe = new ClassNotFoundException(name, e);
			throw cnfe;
		}
		finally
		{
			logEvent (name, c, cnfe, EVENT_END_FIND);
		}
	}
	
	
	public void logEvent (int event, Object[] data)
	{
		
	}
	
	
	public void logEvent (int event)
	{
		Object[] data = new Object[0];
		logEvent(event, data);
	}
	
	public void logEvent (Object o1, int event)
	{
		Object[] data = new Object[] { o1 };
		logEvent(event, data);
	}
	
	public void logEvent (Object o1, Object o2, int event)
	{
		Object[] data = new Object[] { o1, o2 };
		logEvent(event, data);
	}
	
	public void logEvent (Object o1, Object o2, Object o3, int event)
	{
		Object[] data = new Object[] { o1, o2, o3 };
		logEvent(event, data);
	}
	
	/**
	 * Returns an Enumeration of URLs representing all the resources with the 
	 * given name.
	 * 
	 * <P/>
	 * I am assuming that, with the JDK 1.4 delegated ClassLoader approach, 
	 * this method is only called if similar calls have failed for the system 
	 * and parent ClassLoaders.
	 *  
	 * <P/>
	 * For this class, this simply means enumerate all the URLs from all the 
	 * repositories that this instance knows about.  
	 * 
	 * @author cnh
	 */
	protected Enumeration findResources (String name)
		throws IOException
	{
		Vector v = new Vector();
		
		Iterator i = getRepositories().iterator();
		while (i.hasNext())
		{
			ClassRepository repos = (ClassRepository) i.next();
			try
			{
				v.addAll(repos.findResources(name));
			}
			catch (LTSException e)
			{
				IOException ioe = new IOException (
					"Error trying to find resource " 
					+ name
					+ " in repository "
					+ repos
				);
				ioe.initCause(e);
				throw ioe;
			}
		}
		
		return v.elements();
	}
	
	
	protected URL findResource (String name)
	{
		URL u = null;
		
		Iterator i = getRepositories().iterator();
		while (null == u && i.hasNext())
		{
			ClassRepository repos = (ClassRepository) i.next();
			try
			{
				u = repos.findResource(name);
			}
			catch (LTSException e)
			{
				throw new RuntimeException (
					"Error trying to find resource "
					+ name
					+ ", in respository "
					+ repos,
					e
				);
			}
		}
		
		return u;
	}
}
