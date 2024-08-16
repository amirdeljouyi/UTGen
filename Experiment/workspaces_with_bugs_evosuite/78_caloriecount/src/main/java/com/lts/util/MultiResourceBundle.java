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
package com.lts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import com.lts.io.IOUtilities;
import com.lts.properties.PropertyWriter;

/**
 * A resource bundle that combines other resource bundles.
 * 
 * <P>
 * The idea is to allow several bundles to be combined into an object that looks 
 * like one instance.  That way applications can use multiple properties files,
 * etc.
 * 
 * @author cnh
 */
public class MultiResourceBundle extends ResourceBundle
{
	protected Map<String, ResourceBundle> myKeyToBundle;
	
	public MultiResourceBundle()
	{
		initialize();
	}
	
	
	public void initialize ()
	{
		myKeyToBundle = new HashMap<String, ResourceBundle>();
	}
	
	
	@Override
	public Enumeration<String> getKeys()
	{
		Vector<String> v = new Vector<String>(myKeyToBundle.keySet());
		return v.elements();
	}

	@Override
	protected Object handleGetObject(String key)
	{
		ResourceBundle bundle = myKeyToBundle.get(key);
		return bundle.getObject(key);
	}
	
	public void addBundleInstance (ResourceBundle bundle)
	{
		Enumeration<String> e = bundle.getKeys();
		while (e.hasMoreElements())
		{
			String key = e.nextElement();
			myKeyToBundle.put(key, bundle);
		}
	}
	
	
	public void addBundleClass (String baseName)
	{
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		addBundleInstance(bundle);
	}
	
	public static String toSlashClassName (String name)
	{
		char[] nameChars = name.toCharArray();
		StringBuffer sb = new StringBuffer(nameChars.length);
		
		for (int i = 0; i < nameChars.length; i++)
		{
			if ('.' == nameChars[i])
				sb.append('/');
			else
				sb.append(nameChars[i]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Tries to load a properties resource bundle from the supplied resource name.
	 * 
	 * <P>
	 * This method tries to load a {@link ResourceBundle} via the 
	 * {@link ResourceBundle#getBundle(String)} method.  If that attempt fails using
	 * the resource name "as is", then it tries again 
	 * 
	 * @param propertyFileName The property file to load as a resource.
	 * @exception MissingResourceException If neither a class nor a properties file
	 * can be found. 
	 * @see PropertyResourceBundle
	 */
	public void addBundle(String classOrResourceName)
	{
		ResourceBundle bundle = null;
		
		try
		{
			bundle = ResourceBundle.getBundle(classOrResourceName);
		}
		catch (MissingResourceException e)
		{
			bundle = null;
		}
		
		if (null == bundle)
		{
			String absName = "/" + classOrResourceName;
			bundle = ResourceBundle.getBundle(absName);
		}
		
		addBundleInstance(bundle);
	}
	
	
	public void addAll (List list)
	{
		for (Object o : list)
		{
			if (o instanceof ResourceBundle)
				addBundleInstance((ResourceBundle) o);
			else if (o instanceof String)
				addBundle((String) o);
			else
				throw new IllegalArgumentException();
		}
	}
	
	
	
	public List getKeysAsList()
	{
		return new ArrayList(myKeyToBundle.keySet());
	}
	
	
	public enum WriteMode {
		PropertyFile,
		ContentDefine
	};
	
	
	public void writeResourceList (PrintWriter out, WriteMode mode)
	{
		if (mode == WriteMode.ContentDefine)
			writeResourceList (out);
		else
			writePropertyFile (out);
	}
	
	public void writePropertyFile (PrintWriter out)
	{
		List<String> list = getKeysAsList();
		Collections.sort(list);
		for (String key : list)
		{
			String value = getString(key);
			out.print(key);
			out.println("=");
			out.print(value);
		}
	}
	public void writeResourceList (PrintWriter out)
	{
		List<String> list = getKeysAsList();
		Collections.sort(list);
		
		out.println("static final Object[][] contents = {");
		for (String key : list)
		{
			String value = getString(key);
			
			out.println("\t{");
			out.print("\t\t\"");
			out.print(key);
			out.println("\", ");
			out.print("\t\t\"");
			out.print(value);
			out.println("\"");
			out.println ("\t},");
		}
		
		out.println("};");
	}
	
	
	public void writeResourceList (String fileName)
	{
		FileWriter fw = null;
		PrintWriter out = null;
		
		try
		{
			fw = new FileWriter(fileName);
			out = new PrintWriter(fw);
			
			writeResourceList(out);
		}
		catch (IOException e)
		{
			System.err.println("Error writing " + fileName);
			e.printStackTrace();
		}
		finally
		{
			IOUtilities.close(out);
			IOUtilities.close(fw);
		}
	}
	
	
	public void writeResourceList (File file)
	{
		FileWriter fw = null;
		PrintWriter out = null;
		
		try
		{
			fw = new FileWriter(file);
			out = new PrintWriter(fw);
			writeResourceList(out);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtilities.close(out);
			IOUtilities.close(fw);
		}
	}

	/**
	 * Print a property so that it does not exceed a line boundary.
	 * 
	 * <P>
	 * This method prints out a key=value style property such that:
	 * <UL>
	 * <LI>
	 * @param out
	 * @param lineChars
	 */
	protected void printProperty (PrintWriter out, char[] lineChars)
	{
		
	}
	
	
	/**
	 * Create a properties object that contains all the properties in the combined
	 * resources of this instance.
	 * 
	 * @return see above.
	 */
	protected Properties buildProperties ()
	{
		Properties p = new Properties();
		Enumeration e = getKeys();
		while (e.hasMoreElements())
		{
			String key = (String) e.nextElement();
			String value = getString(key);
			p.put(key, value);
		}
		
		return p;
	}
	
	
	public void writeProperties (String fileName) throws IOException
	{
		File file = new File(fileName);
		writeProperties(file);
	}


	public void writeProperties(File file) throws FileNotFoundException, IOException
	{
		FileOutputStream fos = null;
		List<String> list = getKeysAsList();
		Collections.sort(list);
		Properties p = buildProperties();
		try
		{
			fos = new FileOutputStream(file);
			PropertyWriter pwriter = new PropertyWriter();
			pwriter.store(fos, null, list, p);
		}
		finally
		{
			IOUtilities.close(fos);
		}
	}
}
