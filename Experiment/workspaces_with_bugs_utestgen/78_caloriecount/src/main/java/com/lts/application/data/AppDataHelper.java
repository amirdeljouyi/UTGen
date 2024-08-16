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
package com.lts.application.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationMessages;
import com.lts.application.ApplicationRepository;
import com.lts.io.IOUtilities;
import com.lts.xmlser.XmlObjectInputStream;
import com.lts.xmlser.XmlObjectOutputStream;

/**
 * A collection of utility methods that are useful when working with instances
 * of ApplicationData.
 */
public class AppDataHelper
{
	public static List loadTextList (ApplicationRepository vfs, String name) 
		throws ApplicationException
	{
		List list = new ArrayList();
		InputStream istream = null;
		
		try
		{
			istream = vfs.getInputStream(name);
			if (null == istream)
				return list;
			
			InputStreamReader isr = new InputStreamReader(istream);
			BufferedReader in = new BufferedReader(isr);
			
			String line = in.readLine();
			while (null != line)
			{
				list.add(line);
				line = in.readLine();
			}
			
			return list;
		}
		catch (IOException e)
		{
			throw new ApplicationException(
					e, 
					ApplicationMessages.ERROR_REPOSITORY_READ,
					vfs.getRepositoryFile().toString(), 
					name);
			
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}
	
	
	public static void storeTextList (ApplicationRepository vfs, String name, List l)
		throws ApplicationException
	{
		OutputStream ostream = null;
		PrintWriter out = null;
		
		try
		{
			ostream = vfs.getOutputStream(name, false);
			out = new PrintWriter(ostream);
			
			for (Iterator i = l.iterator(); i.hasNext(); )
			{
				Object o = i.next();
				String s = o.toString();
				out.println(s);
			}
		}
		finally
		{
			IOUtilities.close(out);
			IOUtilities.close(ostream);
		}
	}

	public static void storeProperties(ApplicationRepository vfs, String name, Properties p)
			throws ApplicationException
	{
		OutputStream ostream = null;

		try
		{
			if (null == p)
				return;

			ostream = vfs.getOutputStream(name, false);
			p.store(ostream, null);
		}
		catch (IOException e)
		{
			String key = ApplicationMessages.CRIT_ERROR_UNKNOWN;
			throw new ApplicationException(key, e);
		}
		finally
		{
			IOUtilities.close(ostream);
		}
	}

	public static Properties loadProperties(ApplicationRepository vfs, String name)
			throws ApplicationException
	{
		InputStream istream = null;

		try
		{
			istream = vfs.getInputStream(name);
			if (null == istream)
				return null;

			Properties p = new Properties();
			p.load(istream);
			return p;
		}
		catch (IOException e)
		{
			String key = ApplicationMessages.CRIT_ERROR_UNKNOWN;
			throw new ApplicationException(e, key);
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}

	public static Object loadXml (ApplicationRepository vfs, String name)  
		throws ApplicationException 
	{
		InputStream istream = null;
		
		try
		{
			istream = vfs.getInputStream(name);
			XmlObjectInputStream in = new XmlObjectInputStream(istream);
			Object o = in.readObject();
			in.close();
			return o;
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			
			throw new ApplicationException(
					e,
					ApplicationMessages.ERROR_REPOSITORY_READ,
					vfs.getRepositoryFile().toString(),
					name);
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}
	
	public static void storeXml (ApplicationRepository vfs, String name, Object o) 
		throws ApplicationException
	{
		OutputStream ostream = null;
		XmlObjectOutputStream out = null; 
		
		try
		{
			ostream = vfs.getOutputStream(name, false);
			out = new XmlObjectOutputStream(ostream);
			out.writeObject(o);
			out.close();
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			
			throw new ApplicationException(
					e,
					ApplicationMessages.ERROR_REPOSITORY_WRITE,
					vfs.getRepositoryFile().toString());
					
		}
		finally
		{
			IOUtilities.close(ostream);
		}
	}



}