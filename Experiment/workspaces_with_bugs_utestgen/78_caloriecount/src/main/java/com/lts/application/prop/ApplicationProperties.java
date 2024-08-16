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
package com.lts.application.prop;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.lts.util.CollectionUtils;
import com.lts.util.PropertyUtil;

public class ApplicationProperties extends Properties
{
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_SHORT_PROPERTY_FILE_NAME = "app.properties";
	
	/**
	 * Properties having to do with the application's use of files.
	 */
	public static final String CAT_FILES = "app.files";

	/**
	 * The last directory the user visited.  
	 * 
	 * <P>
	 * The application starts in this directory when performing browse style operations.
	 */
	public static final String PROP_LAST_DIRECTORY = CAT_FILES + ".lastDirectory";
	
	/**
	 * The last file the application used.
	 * 
	 * <P>
	 * The last time the user selected a file to save data to or retrieve data from, 
	 * this is the file.
	 */
	public static final String PROP_LAST_FILE = CAT_FILES + ".lastFile";
	
	/**
	 * Properties pertaining to the application's use of repositories.
	 */
	public static final String CAT_REPOSITORY = "app.repository";
	
	/**
	 * The last file used as the application's repository.
	 */
	public static final String PROP_REPOSITORY = CAT_REPOSITORY + ".file";
	
	/**
	 * The number of milliseconds it took to save or restore the repository.
	 */
	public static final String PROP_LOAD_TIME_MILLIS = CAT_REPOSITORY + ".loadTimeMillis";
	
	/**
	 * Properties about the application's use of properties themselves.
	 */
	public static final String CAT_PROPERTY = "app.property";
	
	/**
	 * The property file where the application's properties were loaded from, 
	 * and where they will be saved to.
	 */
	public static final String PROP_PROPERTY_FILE 	= CAT_PROPERTY + ".file";
	
	public static final String PROP_PARAMETER = "app.parameter";

	//
	// FIXME: need to get rid of this class or The ApplicationProperties interface
	//
	public static final String[] SPEC_DEFAULT_VALUES = {
			PROP_PROPERTY_FILE,		"application.properties",
			PROP_LOAD_TIME_MILLIS,	"10000",
	};
	
	/**
	 * Defines the properties that this application knows about.
	 * 
	 * <P>
	 * Generally speaking, only known properties will be saved to the properties file.
	 * Others are ignored.
	 */
	public static final String[] SPEC_PROPERTY_NAMES = {
			PROP_LAST_DIRECTORY,
			PROP_LAST_FILE,
			PROP_REPOSITORY,
			// PROP_PROPERTY_FILE,
			PROP_PARAMETER,
			PROP_LOAD_TIME_MILLIS,
	};
	
	/**
	 * Hook for subclasses to add their property names.
	 * 
	 * <P>
	 * This method is used to build a set that, in turn, is used to figure out which 
	 * properties to save, etc.  Subclasses should override this method and add their 
	 * names to the set, then call the superclass method to add its names, etc.
	 * 
	 * @param names The set that the names should be added to.
	 */
	protected void addNames (Set names)
	{
		Set set = CollectionUtils.toHashSet(SPEC_PROPERTY_NAMES);
		names.addAll(set);
	}
	
	
	protected Set buildNameSet ()
	{
		Set names = new HashSet();
		addNames(names);
		return names;
	}
	
	
	public ApplicationProperties filterProperties ()
	{
		Set names = buildNameSet();
		Properties p = PropertyUtil.filter(this, names);
		return (ApplicationProperties) p;
	}
	
	/**
	 * Store the properties to the designated stream using Properties.store.
	 * 
	 * <P>
	 * This method performs no filtering or other manipulation of the properties 
	 * object --- it merely stores it.  The reason for this method is that this 
	 * class overrides the {@link #store(OutputStream, String)} to create a copy of
	 * this object that only contains the propreties defined by the class and then 
	 * that object is stored.  If the garden variety store method were used, it would
	 * result in an endless loop, hence this method.
	 *  
	 * param ostream Where to store the properties.
	 * @param comments Comments for the properties.
	 * @throws IOException If an error is encountered while writing to the outout
	 * stream.
	 */
	protected void basicStore (OutputStream ostream, String comments) throws IOException
	{
		super.store(ostream, comments);
	}
	
	/**
	 * Store the properties in this object to the provided stream.
	 * 
	 * <P>
	 * This method filters the properties so that only those whose names are in the 
	 * set defined by {@link #buildNameSet()} are actually saved.  The method uses 
	 * {@link #basicStore(OutputStream, String)} to actually effect the store.
	 * 
	 * @param ostream The stream to store the properties in.
	 * @param comments The comments for the properties file.
	 * @exception IOException If an error occurs while trying to write the outout stream.
	 */
	public void store (OutputStream ostream, String comments) throws IOException
	{
		ApplicationProperties filtered = filterProperties();
		filtered.basicStore(ostream, comments);
	}
	
	/**
	 * Get a property that *may* be a list of properties as opposed to a single value.
	 * 
	 * <P>
	 * For this method to recognize something as a list property, names must have the 
	 * following form:
	 * 
	 * <p>
	 * <CODE><PRE>
	 *     prefix.instance.restOfName
	 * </PRE></CODE>
	 * 
	 * <P>
	 * Where "prefix" is any string, "instance" is a number, and "restOfName" is an 
	 * arbitrary string.  For example:
	 * 
	 * <P>
	 * <CODE><PRE>
	 *     foo.1.liwipi
	 *     foo.2.liwipi
	 *     foo.3.nerts.1.spam
	 *     foo.3.nerts.2.spam
	 * </PRE></CODE>
	 * 
	 * <P>
	 * In this example, asking for the list of names for the prefix "foo" results in
	 * the following:
	 * 
	 * <P>
	 * <CODE><PRE>
	 *     [0,0] = foo.1.liwipi
	 *     [1,0] = foo.2.liwipi
	 *     [2,0] = foo.3.nerts.1.spam
	 *     [2,1] = foo.3.nerts.2.spam
	 * </PRE></CODE>
	 * 
	 * <P>
	 * Here is a more practical example.  Suppose you have a property called "classpath" 
	 * that stores the directories, jar files, etc. that make up a classic java 
	 * classpath.  Here is how such a concept might be represented:
	 * 
	 * <P>
	 * <CODE><PRE>
	 *     classpath.1 = /foo/bar/nerts.jar
	 *     classpath.2 = ../junkdir
	 *     classpath.3 = /system/library/network.jar
	 *     classpath.4 = .
	 * </PRE></CODE>
	 * 
	 * <P>
	 * Calling this method would result in the following:
	 * 
	 * <P>
	 * <CODE><PRE>
	 *     [0,0] = classpath.1
	 *     [1,0] = classpath.2
	 *     [2,0] = classpath.3
	 *     [2,1] = classpath.4
	 * </PRE></CODE>
	 * 
	 * @param prefix The prefix for those properties that should have, after this prefix,
	 * a number that indicates which element of the property one is dealing with.
	 * 
	 * @return A 2-dimensional array of strings that indicate the property names for each
	 * element of the list.  
	 */
//	public List getList (String prefix, boolean sorted)
//	{
//		int plen = prefix.length();
//		Map table = new HashMap();
//		Enumeration enumer = this.propertyNames();
//		List names = CollectionUtils.toList(enumer);
//		Collections.sort(names);
//		for (Iterator i = names.iterator(); i.hasNext(); )
//		{
//			String name = (String) i.next();
//			if (name.startsWith(prefix))
//			{
//				//
//				// foo.bar.1.nerts
//				//       ^
//				//
//				// ensure that the next character is a dot --- "."
//				//
//				int index = plen;
//				if (index >= name.length())
//					continue;
//				
//				if (name.charAt(index) != '.')
//					continue;
//				
//				index++;
//				
//				//
//				// foo.bar.1.nerts
//				//        ^
//				//
//				// ensure that there is a substring between the dots
//				//
//				int last = name.indexOf('.', index);
//				if (-1 == last)
//					continue;
//				
//				String indexSubstring = name.substring(index, last);
//				
//				//
//				// foo.bar.  1  .nerts
//				//          ^
//				//
//				// 
//			}
//		}
//		while (enumer.hasMoreElements())
//		{
//		}
//	}
	
	
	protected static class Whatever
	{
		public int listedIndex;
	}
	
	
	/**
	 * Return the set of list property name prefixes, give a list of property names.
	 * 
	 * <P>
	 * Given the input strings:
	 * 
	 * <P>
	 * <CODE><PRE>
	 * foo.bar.1.nerts
	 * foo.bar.2.nerts
	 * classpath.1
	 * classpath.2
	 * whatever.a.1.b
	 * whatever.a.2.b
	 * </PRE></CODE>
	 * 
	 * <P>
	 * The method would return the following set:
	 * 
	 * <P>
	 * <CODE><PRE>
	 * foo.bar
	 * classpath
	 * whatever.a
	 * </PRE></CODE>
	 */
	public Set findPrefixes (List names)
	{
		Set set = new HashSet();
		//
		// find those strings that have the form:
		//     prefix.integer.suffix
		//     prefix.integer
		//
		String regex = ".*\\\\.[0-9]($|\\\\..*)";
		int length = names.size();
		for (int i = 0; i < length; i++)
		{
			String s = (String) names.get(i);
			String[] subs = s.split(regex);
			if (null != subs && subs.length > 0)
			{
				String prefix = subs[0] + ".";
				set.add(prefix);
			}
		}
		
		return set;
	}
	
	/**
	 * Given a prefix and an input string, return the integer value of that portion 
	 * of the input string that matches the prefix.
	 * 
	 * <H3>Note</H3>
	 * The prefix is assumed to end with a period (".").  If it does not, then 
	 * this method will probably not work.
	 * 
	 * <H3>Description</H3>
	 * This method takes a prefix string of the form:
	 * 
	 * <P>
	 * <CODE><PRE>
	 *     prefix.
	 * </PRE></CODE>
	 * 
	 * <P>
	 * And tries to match it against the input string, where the input string must have 
	 * the form:
	 * <CODE><PRE>
	 * <BR>
	 *     prefix.integer
	 *     prefix.integer.whatever
	 * </PRE></CODE>
	 * 
	 * <P>
	 * If the input string has this form, the method parses the integer and returns it.
	 * If the string does not have this form, then the method returns -1.
	 * 
	 * <P>
	 * All such integers must be values greater than or equal to 0.
	 * 
	 * @param prefix The prefix to look for.
	 * @param name The string in which to look for the prefix.
	 * @return The value of the integer portion of the string or -1 if the string 
	 * does not match the required form.
	 */
	protected int toIntegerValue (String prefix, String name)
	{
		int index = -1;
		
		//
		// The input string is less than or equal to the prefix and therefore cannot
		// possibly have the correct form.
		//
		if (name.length() <= prefix.length())
			return -1;
		
		//
		// The input string does not start with the prefix and therefore does not have 
		// the correct form.
		//
		if (!name.startsWith(prefix))
			return -1;
		
		//
		// Create a substring of the form: 
		//     integer[.whatever] 
		// 
		// from the input string
		//
		String sub = name.substring(0, name.length() - 1);
		
		//
		// Find the "." in [.whatever]
		//
		int sindex = sub.indexOf('.');
		
		//
		// Substring has the form integer, parse the integer part.
		//
		String istring;
		if (-1 == sindex)
			istring = sub;
		
		//
		// Substring has the form integer.whatever, get the substring that just consists
		// of the integer string
		//
		else
			istring = sub.substring(0, sindex - 1);
		
		//
		// We now have a string of the form:
		//     integer
		//
		// Or the string is a non-integer
		//
		try
		{
			//
			// integer string, return the value of the integer
			//
			index = Integer.parseInt(istring);
		}
		//
		// non-integer string, return -1
		//
		catch (NumberFormatException e)
		{
			index = -1;
		}
		
		return index;
	}
	
	
//	protected List whatever (String prefix, List candidates)
//	{
//		List list = new ArrayList();
//		
//		for (Iterator i = candidates.iterator(); i.hasNext(); )
//		{
//			String fullName = (String) i.next();
//			if (!fullName.startsWith(prefix))
//				continue;
//			
//			//
//			// Ensure the name has one of the following forms:
//			//     <prefix.>integer
//			//     <prefix.>integer.whatever
//			//
//			// Note that <prefix.> implies that the supplied prefix ends with a dot (.)
//			//
//			String s = fullName.substring(prefix.length() - 1);
//			String[] comps = s.split("\\\\.");
//			if (null == comps || comps.length < 1)
//				continue;
//
//			String naturalNumber = "[0-9]+";
//			String istring = comps[0];
//			if (!istring.matches(naturalNumber))
//				continue;
//
//			int listIndex = Integer.parseInt(istring);
//
//			String relativeName;
//			int index = s.indexOf('.');
//			if (-1 == index)
//				relativeName = null;
//			else
//				relativeName = s.substring(1 + index);
//			
//			relativeName = StringUtils.trim(relativeName);
//			String value = getProperty(fullName);
//			
//			PropertyElement el = new PropertyElement(fullName, relativeName, value, listIndex);
//			list.add(el);
//		}
//		
//		return list;
//	}
	
	/**
	 * Build a list of PropertyElement objects that match the supplied prefix and that
	 * match a certain string form.
	 * <P>
	 * To match, a string must have the form:
	 * <P>
	 * <CODE><PRE>
	 *     integer 
	 *     integer.whatever
	 * </PRE></CODE>
	 * 
	 * <P>
	 * Where the input string, s, is the input string that starts with the prefix, and
	 * from which the prefix has been removed.
	 * 
	 * @param prefix
	 *        The prefix to match against.
	 * @param names
	 *        A list of names to match against.
	 * @return A list of PropertyElement objects that have been "filled out" based on the
	 *         matching name and value for that name. The list will have been sorted by
	 *         the index value of each element.
	 */
	protected List<PropertyElement> buildElementList (String prefix, List names)
	{
		List list = new ArrayList<PropertyElement>();
		
		for (Iterator i = names.iterator(); i.hasNext(); )
		{
			String name = (String) i.next();
			int index = toIntegerValue(prefix, name);
			if (-1 == index)
				continue;

			String suffix = name.substring(prefix.length(), name.length());
			int dotIndex = name.indexOf('.');
			if (-1 != dotIndex)
				suffix = suffix.substring(1 + dotIndex);
			
			String value = getProperty(name);
			PropertyElement el = new PropertyElement(prefix, index, suffix, value);
			
			list.add(el);
		}
		
		Collections.sort(list, PropertyElement.INDEX_COMPARATOR);
		return list;
	}
	
	
	/**
	 * Return a map from prefix names to lists of values that are all the names in this
	 * object that match the prefix.
	 * 
	 * <P>
	 * Each list is sorted by the "list index" value for the property.  See the class
	 * description for details.
	 * 
	 * @return A map from prefix name to list of names that match the prefix.
	 */
	protected Map<String, List> buildNameList ()
	{
		Map<String, List> map = new HashMap<String, List>();
		
		List allNames = CollectionUtils.toList(propertyNames());
		Set prefixies = findPrefixes(allNames);
		
		for (Iterator i = prefixies.iterator(); i.hasNext(); )
		{
			String prefix = (String) i.next();
			List<PropertyElement> list = buildElementList(prefix, allNames);
			List listElements = new ArrayList();
			
			for (PropertyElement el : list)
			{
				listElements.add(el.getFullName());
			}
			
			map.put(prefix, listElements);
		}
		
		return map;
	}
	
	
	protected List<String> buildRelativeNameList (String prefix)
	{
		List allNames = CollectionUtils.toList(propertyNames());
		List<PropertyElement> elements = buildElementList(prefix, allNames);
		
		List<String> relNames = new ArrayList<String>();
		for (PropertyElement el : elements)
		{
			relNames.add(el.suffix);
		}
		
		return relNames;
	}
	
	
	protected List<PropertyElement> buildPropertyElementList (String prefix)
	{
		List allNames = CollectionUtils.toList(propertyNames());
		return buildElementList(prefix, allNames);
	}
	
	/**
	 * Return a map from property names to a list of property names where the lists 
	 * contain the names of list properties.
	 * 
	 * <P>
	 * See <A href="#listProperties">the discussion of list properties</A> in the class
	 * documenation for a more through description of what a list property is.
	 * 
	 * @return See description.
	 */
	public Map<String, List> toMapList ()
	{
		Map<String, List> map = new HashMap<String, List>();
		
		Enumeration enumer = propertyNames();
		List names = CollectionUtils.toList(enumer);
		Set prefixies = findPrefixes(names);
		for (Iterator i = prefixies.iterator(); i.hasNext(); )
		{
			String prefix = (String) i.next();
			List list = buildElementList (prefix, names);
			map.put(prefix, list);
		}
			
		return map;
	}
	
	protected int toFirstPropertyIndex (String s)
	{
		//
		// Split around the dots in a string.  Thus
		//     one.two.three.four
		//
		// Becomes
		//     [0] = one
		//     [1] = two
		//     [2] = three
		//     [3] = four
		//
		String regex = "\\\\.";
		String[] substrs = s.split(regex);
		
		if (null == substrs || substrs.length < 2)
			return -1;
		
		//
		// We should now have a string of the form: .int[.]
		//
		int index = -1;
		
		//
		// Because the regex should be an integer, but ya never know
		//
		try
		{
			index = Integer.parseInt(substrs[1]);
		}
		catch (NumberFormatException e)
		{
		}
		
		return index;
	}
	
	
	protected List<PropertyElement> renumber (List<PropertyElement> inlist)
	{
		List<PropertyElement> outlist = new ArrayList<PropertyElement>(inlist.size());
		
		int previous = -1;
		int current = 0;
		
		for (PropertyElement el : outlist)
		{
			if (-1 != el.index && previous != el.index)
			{
				current++;
				previous = el.index;
			}
			
			el.index = current;
		}
		
		return outlist;
	}
	
	
	public static class NameValue
	{
		public String name;
		public String value;
		
		public NameValue(String theName, String theValue)
		{
			this.name = theName;
			this.value = theValue;
		}
	}
	
	
	public List<NameValue> buildPropertyList (String prefix, List<PropertyElement> elements)
	{
		Collections.sort(elements, PropertyElement.INDEX_NAME_COMPARATOR);
		elements = renumber(elements);
		
		List<NameValue> list = new ArrayList<NameValue>();
		
		for (PropertyElement el : elements)
		{
			String name = 
				el.prefix + "." 
				+ Integer.toString(el.index)
				+ "." + el.suffix;
			
			String value = el.value;
			NameValue nameValue = new NameValue(name, value);
			list.add(nameValue);
		}
		
		return list;
	}

	
	public Properties buildPropertiesFromElements (String prefix, List<PropertyElement> elements)
	{
		Collections.sort(elements, PropertyElement.INDEX_NAME_COMPARATOR);
		elements = renumber(elements);
		
		Properties p = new Properties();
		for (PropertyElement el : elements)
		{
			p.setProperty(el.getFullName(), el.value);
		}
		
		return p;
	}
	
	public Properties buildProperties (String prefix, List<NameValue> nameValues)
	{
		Properties p = new Properties();
		
		for (NameValue nv : nameValues)
		{
			p.setProperty(nv.name, nv.value);
		}

		return p;
	}
	
	
	public Properties buildPropertiesFromList (String prefix, List values)
	{
		Properties p = new Properties();
		
		int count = 0;
		for (Iterator i = values.iterator(); i.hasNext(); )
		{
			String name = prefix + count;
			String value = (String) i.next();
			p.setProperty(name, value);
		}
		
		return p;
	}
}
