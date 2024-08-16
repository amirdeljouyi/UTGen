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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lts.util.system.SystemUtils.StandardProperties;

/**
 * Utility methods useful in dealing with files.
 */
public class FileUtils
{
    public static void copyFromToFile(File infile, File outfile)
			throws FileNotFoundException, IOException
	{
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try
		{
			fis = new FileInputStream(infile);
			fos = new FileOutputStream(outfile);
			int buffSize = 8192;
			byte[] buf = new byte[buffSize];
			int byteCount = fis.read(buf);
			while (byteCount > 0)
			{
				fos.write(buf, 0, byteCount);
				byteCount = fis.read(buf);
			}
		}
		finally
		{
			if (null != fos)
				fos.close();

			if (null != fis)
				fis.close();
		}
	}



	public static List<String> findDirectoriesContaining (String name, String[] path, boolean stopAtFirstMatch)
	{
		List<String> list = new ArrayList<String>();
		boolean found = false;
		for (String dir : path)
		{
			File component = new File(dir);
			if (component.isDirectory())
			{
				found = appendDirectoriesToSearch(list, name, component, stopAtFirstMatch);
				if (found && stopAtFirstMatch)
					break;
			}
		}
		
		return list;
	}
	
	public static List<String> findDirectoriesContaining (String name, String path)
	{
		String sep = StandardProperties.PathSeparator.getValue();
		String[] dirs = path.split(sep);
		return findDirectoriesContaining (name, dirs, true);
	}
	
	public static String findFirstDirectoryContaining (String name, String path)
	{
		String sep = StandardProperties.PathSeparator.getValue();
		String[] dirs = path.split(sep);

		List<String> list = findDirectoriesContaining(name, dirs, true);
		if (list.size() < 1)
			return null;
		else
			return list.get(0);
	}

	/**
	 * Search the directory passed for the specified file.
	 * <H3>NOTE</H3>
	 * This method assumes that the directory parameter returns true to
	 * {@link File#isDirectory()}. If the file parameter is not a directory, this method
	 * will probably throw an exception.
	 * <H3>Description</H3>
	 * This method will append the directory passed to the list parameter if the name
	 * specified in the name parameter exists in the directory. In addition, it will
	 * search any sub-directories for matches as well if the searchAll parameter is set to
	 * true.
	 * 
	 * @param list
	 *        The list of directory matches.
	 * @param name
	 *        The name of the file or directory to look for.
	 * @param dir
	 *        The directory to search. See notes above.
	 * @param stopAtFirstMatch
	 *        If set to true, then the search will stop when it finds the first matching
	 *        file or directory. If false, the search will find all matches in the
	 *        specified directory or sub-directories.
	 */
	protected static boolean appendDirectoriesToSearch(List<String> list, String name, File dir, boolean stopAtFirstMatch)
	{
		boolean found = false;
		
		File[] contents = dir.listFiles();
		for (File entry : contents)
		{
			if (entry.getName().equals(name))
			{
				list.add(dir.toString());
				found = true;
				if (stopAtFirstMatch)
					return true;
			}
			
			if (entry.isDirectory())
			{
				found = appendDirectoriesToSearch(list, name, entry, stopAtFirstMatch);
				if (found && stopAtFirstMatch)
					return true;
			}
		}
		
		return found;
	}

	public static String findFirstDirectoryContaining(String name, String[] path)
	{
		List<String> list = findDirectoriesContaining(name, path, true);
		if (list.size() < 1)
			return null;
		else
			return list.get(0);
	}
}
