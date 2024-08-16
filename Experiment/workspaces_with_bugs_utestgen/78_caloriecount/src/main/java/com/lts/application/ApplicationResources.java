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
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.lts.io.IOUtilities;
import com.lts.util.MultiResourceBundle;

public class ApplicationResources extends MultiResourceBundle
{
	public void appendName (String name)
	{
		this.addBundle(name);
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
	
	/**
	 * Try to load a resource bundle using the default method.
	 * 
	 * <P>
	 * This method uses {@link ResourceBundle#getBundle(java.lang.String)}.
	 * If it encounters a {@link MissingResourceException} it returns null 
	 * rather than throwing the exception.
	 * 
	 * @param name The name to attempt to load.
	 * @return The ResourceBundle or null.
	 */
	public ResourceBundle loadBundle (String name)
	{
		ResourceBundle bundle;
		
		try
		{
			bundle = ResourceBundle.getBundle(name);
		}
		catch (MissingResourceException e)
		{
			bundle = null; 
		}
		
		return bundle;
	}
	
	/**
	 * Try to load a resource bundle using the class loader that was used to 
	 * load this class.
	 * 
	 * <P>
	 * This method uses {@link ResourceBundle#getBundle(java.lang.String, java.util.Locale, java.lang.ClassLoader)}
	 * to load the ResourceBundle.  It uses {@link Locale#getDefault()} as the locale,
	 * and {@link Class#getClassLoader()} as the class loader.
	 * 
	 * <P>
	 * The only reason this method exists at all is 
	 * {@link ResourceBundle#getBundle(java.lang.String)} does not seem to actually use 
	 * the caller's class as it is supposed to.
	 * 
	 * <P>
	 * This method catches {@link MissingResourceException} and returns null
	 * in that situation.  That is, if the resource bundle cannot be found, the 
	 * method returns null rather than throwing an exception.
	 * 
	 * @param name The name to attempt to load.
	 * @return The ResourceBundle or null.
	 */
	public ResourceBundle loadBundleClassLoader (String name)
	{
		ResourceBundle bundle;
		
		try
		{
			Locale locale = Locale.getDefault();
			ResourceClassLoader loader = new ResourceClassLoader(getClass().getClassLoader());
			bundle = ResourceBundle.getBundle(name, locale, loader);
		}
		catch (MissingResourceException e)
		{
			bundle = null; 
		}
		
		return bundle;
	}
	
	
	public ResourceBundle loadBundle(String name, Locale locale, ClassLoader loader)
			throws ApplicationException
	{
		try
		{
			return ResourceBundle.getBundle(name, locale, loader);
		}
		catch (MissingResourceException e)
		{
			String msg = ApplicationMessages.CRIT_BUNDLE_MISSING;
			throw new ApplicationException(msg,name);
		}
	}
	
	
	public String getMessage (String key) throws ApplicationException
	{
		String formatString = keyToString(key);
		Object[] data = { };
		return MessageFormat.format(formatString, data);
	}
	
	
	public String getMessage (String key, Object o1) throws ApplicationException
	{
		String formatString = keyToString(key);
		Object[] data = { o1 };
		return MessageFormat.format(formatString, data);
	}
	
	public String getMessage (String key, Object o1, Object o2) throws ApplicationException
	{
		String formatString = keyToString(key);
		Object[] data = { o1, o2 };
		return MessageFormat.format(formatString, data);
	}

	
	public String getMessage (String key, Object o1, Object o2, Object o3) throws ApplicationException
	{
		String formatString = keyToString(key);
		Object[] data = { o1, o2, o3 };
		return MessageFormat.format(formatString, data);
	}
	
	
	public String getMessage (String key, Object[] data) throws ApplicationException
	{
		String formatString = keyToString(key);
		return MessageFormat.format(formatString, data);
	}
	
	
	public String keyToString (String key)
	{
		return getString(key);
	}
	
	public String getMessage (ApplicationException e) throws ApplicationException
	{
		String msg = e.getMessage();
		Object[] data = e.getData();
		return getMessage(msg, data);
	}


}
