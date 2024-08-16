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
package com.lts.application.international;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationMessages;
import com.lts.application.RuntimeApplicationException;
import com.lts.io.IOUtilities;
import com.lts.util.CollectionUtils;

/**
 * Get messages for the application.
 * 
 * <H2>Usage</H2>
 * <CODE>
 * <PRE>
 * MessageFormatter formatter = new MessageFormatter();
 * String key = ApplicationMessages.&lt;some constant name&gt;;
 * Object[] data = new Object[] { &lt;some data&gt;...};
 * String msg = formatter.getMessage(key, data);
 * ...
 * ApplicationException e = &lt;some exception we caught&gt;;
 * String msg = formatter.getMessage(e);
 * ...
 * Throwable t = &lt;some exception we caught&gt;;
 * String msg = formatter.getMessage(t);
 * </PRE>
 * </CODE>
 * 
 * <H2>Description</H2>
 * This class is responsible for formatting messages in the com.lts.application
 * framework.  It works using ResourceBundle classes and Formatter classes to create
 * user-meaningful messages.
 * 
 * <P>
 * Generally this class does its work behind the scenes and does not require additional
 * programming from the developer.  At most, one must tell the Application object 
 * what "bundle path" to use in order to find messages.
 * 
 * <H2>The Resource Bundle Path</H2>
 * This class uses instances of the ResourceBundle to obtain and format messages.  
 * It can use more than one resource bundle to resolve a message, and use some bundles
 * before other ones.  If you want to ensure that one bundle is searched before the
 * others, put it first the path.
 * 
 * <P>
 * The path can be set by providing it to the constructor, or by calling setBundlePath
 * and then calling reloadBundles.  The named resource bundles can be any regular 
 * resource bundle --- see the java documentation on resource bundles and internationalization
 * for details regarding how they are resolved.
 * 
 * <P>
 * Regardless of the settings, the bundle path should always contain at least one entry
 * called "ApplicationBundle".  That corresponds to the ApplicationBundle class, which
 * should define a base set of messages. 
 * 
 * <P>
 * When getting a message, this class will try asking the first resource bundle for the 
 * key.  If that fails, it tries the next bundle in the path, etc.  If the message cannot
 * be found in any of the bundles, the getMessage method returns null.
 * 
 * @author cnh
 * @see com.lts.application.Application
 * @see java.util.ResourceBundle
 * @see java.util.Formatter
 */
public class MessageFormatter
{
	public static final String DEFAULT_BUNDLE = "resources.messages.standard";
	public static final String[] DEFAULT_BUNDLE_PATH = { DEFAULT_BUNDLE };
	
	protected Throwable myLastException;
	private List<ResourceBundle> myBundles = new ArrayList<ResourceBundle>();
	private Locale myLocale;
	private ClassLoader myClassLoader;
	private List<String> myResourcePath;
	private List<String> myNames;
	
	
	public void addBundle (ResourceBundle bundle)
	{
		myBundles.add(bundle);
	}
	
	public Throwable getLastException()
	{
		return myLastException;
	}
	
	
	public static List buildList (Object[] spec)
	{
		List list = new ArrayList();
		
		if (null != spec)
		{
			for (int i = 0; i < spec.length; i++)
				list.add(spec[i]);
		}
		
		return list;
	}
	
	
	public static class LoadBundleError
	{
		public LoadBundleError (String name, Throwable error)
		{
			this.bundleName = name;
			this.error = error;
		}
		
		public String bundleName;
		public Throwable error;
	}
	
	
	protected List loadCriticalBundles ()
	{
		List list = new ArrayList();
		
		
		
		return list;
	}
	
	public void temp()
	{
		InputStream istream = null;
		
		try
		{
			istream = getClass().getResourceAsStream(DEFAULT_BUNDLE);
			System.out.println("hi there");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}
	
	

	protected ApplicationException createLoadException(List errors)
	{
		String key = ApplicationMessages.CRIT_ERROR_RESOURCE_LOAD_FAILED;		
		List errorData = new ArrayList();
		
		for (Iterator i = errors.iterator(); i.hasNext();)
		{
			LoadBundleError error = (LoadBundleError) i.next();
			errorData.add(error.bundleName);
			errorData.add(error.error);
		}
		
		Object[] data = errorData.toArray();
		ApplicationException appEx = new ApplicationException(key, data);
		return appEx;
	}
	

	public List listResources (String baseName, ClassLoader loader)
	{
		List list = new ArrayList();
		
		try
		{
			list = CollectionUtils.toList(loader.getResources(baseName));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public void initializeResourceBundle() throws ApplicationException
	{
		List<String> path = new ArrayList<String>();
		path.add(".");
	}
	
	public void initializeResourceBundle(List<String> path) throws ApplicationException
	{
		List<String> names = new ArrayList<String>();
		names.add("messages");
		initializeResourceBundle(path, names);
	}
	
	
	/**
	 * Format a message using a key, some data and a resource bundle.
	 * 
	 * <H2>Description</H2>
	 * This method tries to find a given key in a particular resource bundle. If
	 * a matching template string is found, then the method attempts to format a
	 * message, using the template, the default locale, and a MessageFormat
	 * object. If that is successful, the message is returned to the caller.
	 * 
	 * <P>
	 * If problems occur along the way, such as not being able to find the
	 * template string, or catching an exception when trying to format the 
	 * message, the method will return null.
	 * 
	 * <P>
	 * The method can record problems by adding any encountered exceptions to a
	 * list provided by the caller. This will happen if the corresponding
	 * parameter signals that errors should be recorded. If not, then exceptions
	 * are ignored.
	 * 
	 * @param recordErrors
	 *            true, if the caller wants the method to record exceptions in
	 *            the corresponding parameter. false if exceptions should be
	 *            ignored.
	 * 
	 * @param errors
	 *            A list that any exceptions encountered, such as
	 *            MissingResourceException, should be added. This only happens
	 *            if the corresponding parameter signals to record exceptions.
	 * 
	 * @param key
	 *            The string key to use when looking up the template in the
	 *            resource bundle.
	 * 
	 * @param data
	 *            The data to use when formatting the message. This may be null
	 *            or empty. The formatter may or may not use the provided data,
	 *            depending on the template.
	 * 
	 * @param bundle
	 *            The resource bundle to search.
	 * 
	 * @return The resulting formatted message. This will be null if an error
	 *         occurs, such as not being able to find the key, or an exception
	 *         during formatting.
	 */
	protected String formatMessage(String key, Object[] data, ResourceBundle bundle)
	{
		Object o;
		String message;
		String template = "<undefined>";
		myLastException = null;
		
		if (null == data)
			data = new Object[0];
		
		try
		{
			o = bundle.getString(key);
			template = (String) o;
			MessageFormat formatter = new MessageFormat(template);
			message = formatter.format(data);
		}
		catch (MissingResourceException e)
		{
			message = null;
		}
				
		return message;
	}
	
	
	public String formatMessage (String key, Object[] data) throws RuntimeApplicationException
	{
		String msg = null;
	
		for (ResourceBundle bundle : myBundles)
		{
			msg = formatMessage(key, data, bundle);
			if (null != msg)
				break;
		}
		
		if (null == msg)
		{
			msg = ApplicationMessages.ERROR_KEY_NOT_FOUND;
			throw new RuntimeApplicationException(msg, key);
		}
		
		return msg;
	}
	
	
	/**
	 * Get a user-meaningful message, given a key and some data.
	 * 
	 * <H2>Description</H2>
	 * This method tries to find a matching key and then format the message with the 
	 * resulting format string and data.  The process starts with the first resource 
	 * bundle in the bundles property and continues with each subsequent resource 
	 * bundle until a message is successfully formatted.
	 * 
	 * <P>
	 * If the method cannot find and/or format the specified key and data, the method
	 * returns null, but never directly throws an exception.
	 * 
	 * @param ignoreErrors true if errors should be recorded in the formatErrors
	 * property.  false if exceptions should be ignored.
	 * 
	 * @param key The key to use when searching the resource bundles.
	 * @param data The data to use when formatting any message found in a resource 
	 * bundle.  If null, the data is ignored.
	 * 
	 * @return A formatted string, if the search and format is successful, false 
	 * if no match could be found and formatted.
	 */
	public String getMessage (String key, Object[] data) throws RuntimeApplicationException
	{
		return formatMessage(key, data);
	}
	
	
	public String getMessage (String key) throws ApplicationException
	{
		Object[] data = new Object[0];
		return formatMessage(key, data);
	}
	
	public String getMessage (String key, Object o1, Object o2)
		throws ApplicationException
	{
		Object[] data = new Object[] { o1, o2 };
		return formatMessage(key, data);
	}
	
	public String getMessage (String key, Object o1, Object o2, Object o3)
		throws ApplicationException
	{
		Object[] data = new Object[] { o1, o2, o3 };
		return formatMessage(key, data);
	}
	
	public String getMessage (Throwable t)
		throws ApplicationException
	{
		String key = ApplicationMessages.CRIT_ERROR_UNKNOWN;
		Object[] data = new Object[] { t };
		
		if (t instanceof ApplicationException)
		{
			ApplicationException e = (ApplicationException) t;
			key = e.getMessage();
			data = e.getData();
		}
		
		return formatMessage(key, data);
	}


	public void initializeResourceBundle(List resourcePath, List names) throws ApplicationException
	{
		Locale locale = Locale.getDefault();
		ClassLoader loader = getClass().getClassLoader();
		initializeResourceBundle(resourcePath, names, locale, loader);
	}
	
	
	/**
	 * Load the resource bundle.
	 * 
	 * <P>
	 * This method attempts to load the resource bundle specified by looking for the
	 * first match of any instance of the string specified by the names parameter 
	 * in any of the locations specified by the resourcePath parameter.  That is, 
	 * for each name and path pair, try to load a {@link ResourceBundle} via the 
	 * supplied class loader.  The method follows all the rules that the ResourceBundle
	 * class uses with respect to determining if a resource bundle exists for a 
	 * particular name.
	 * </P>
	 * 
	 * <P>
	 * It is very important that all of the methods parameters are non-null.  Passing
	 * null for a parameter will probably result in a {@link NullPointerException}.
	 * </P>
	 * 
	 * @param names A list of strings that are the names that the resource bundle may
	 * use.
	 * @param resourcePath The that the method should look for the supplied resource 
	 * name.
	 * @param locale The locale to use when loading the resource bundle.
	 * @param loader The class loader that should be used to find the resource bundle.
	 * 
	 * @throws ApplicationException If a problem occurs while trying to load the 
	 * resource bundle.
	 */
	public void initializeResourceBundle(List<String> resourcePath, List<String> names, Locale locale,
			ClassLoader loader) throws ApplicationException
	{
		myBundles = new ArrayList();
		myLocale = locale;
		myClassLoader = loader;
		myResourcePath = resourcePath;
		myNames = names;
		
		addResourceBundles();
		
		if (myBundles.size() < 1)
		{
			String msg = 
				"Could not find any resource bundle give names: "
				+ listToString(names)
				+ "; and resource path: "
				+ listToString(resourcePath);
			
			throw new ApplicationException(msg);
		}
	}


	protected void addResourceBundles()
	{
		for (String pathElement : myResourcePath)
		{
			for (String name : myNames)
			{
				ResourceBundle bundle = tryLoad(pathElement, name);
				if (null != bundle)
					myBundles.add(bundle);
			}
		}
	}
	

	public String listToString(List<String> list)
	{
		if (null == list)
			return "null";
		
		StringBuffer sb = new StringBuffer();
		
		boolean first = true;
		for (String s : list)
		{
			if (first)
				first = false;
			else
			{
				sb.append(", ");
			}
			
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	
	protected ResourceBundle tryLoad(String pathElement, String name)
	{
		String fullname = name;
		
		if (null != pathElement && (!"".equals(pathElement)))
		{
			StringBuffer sb = new StringBuffer();
			sb.append(pathElement);
			sb.append("/");
			sb.append(name);
			
			fullname = sb.toString();
		}
		
		try
		{
			return ResourceBundle.getBundle(fullname, myLocale, myClassLoader);
		}
		catch (MissingResourceException e)
		{
			return null;
		}
	}
	
	
	protected ResourceBundle tryLoad(String name)
	{
		ResourceBundle bundle = null;
		
		for (String pathElement : myResourcePath)
		{
			bundle = tryLoad(pathElement, name);
			if (null != bundle)
				break;
		}
		
		return bundle;
	}
}
