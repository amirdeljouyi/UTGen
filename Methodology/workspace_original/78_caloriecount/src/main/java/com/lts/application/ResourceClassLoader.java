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
package com.lts.application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.lts.io.IOUtilities;

/**
 * A ClassLoader that uses an alternative approach to loading resources.
 * 
 * <P>
 * This class basically only cares about the {@link java.lang.ClassLoader#getResource(java.lang.String)}
 * method --- all other methods use the superclass version.
 * 
 * <P>
 * This class tries using the superclass version of 
 * {@link java.lang.ClassLoader#getResource(java.lang.String)} to get resources,
 * but, if that fails, it tries using the class loader that loaded this class to 
 * load the resource.  This is useful in situations where certain system classes
 * return null for {@link java.lang.Class#getClassLoader()}.  
 * 
 * <P>
 * In some situations, system classes will report that they have a null class 
 * loader.  This could be because they are loaded in a debugging environment,
 * but it is difficult to ascertain the exact reason for this.
 * 
 * @author cnh
 */
public class ResourceClassLoader extends ClassLoader
{
	public ResourceClassLoader (ClassLoader parent)
	{
		super(parent);
	}
	
	
	public URL getResource(String name)
	{
		URL url = super.getResource(name);
		
		if (null == url)
			url = getClass().getResource(name);
		
		return url;
	}

	protected void readURL (URL url)
	{
		InputStream istream = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		
		try
		{
			istream = url.openStream();
			isr = new InputStreamReader(istream);
			in = new BufferedReader(isr);
			
			String line = in.readLine();
			while (null != line)
			{
				System.out.println(line);
				line = in.readLine();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtilities.close(in);
			IOUtilities.close(isr);
			IOUtilities.close(istream);
		}
	}
}
