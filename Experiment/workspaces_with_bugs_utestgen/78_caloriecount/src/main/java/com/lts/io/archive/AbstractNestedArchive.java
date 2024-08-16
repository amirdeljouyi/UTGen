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
package com.lts.io.archive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lts.LTSException;
import com.lts.io.ImprovedFile;
import com.lts.util.StringUtils;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

/**
 * An abstract NestedArchive that defines a basic approach to dealing with
 * NestedArchive issues.
 * 
 * <P/>
 * This is an abstract class.  In order to create an instantiatable class, 
 * implementers must define the following methods:
 * 
 * <UL>
 * <LI/>createArchive
 * </UL>
 * 
 * <P/>
 * The basic approach that this class uses is to define a map from entry 
 * specifications to Archive objects.  When presented with an entry spec, 
 * it breaks the spec into components (separated by "!" characters), and 
 * hands them off to the appropriate archive.  If it encounters an archive
 * that it needs to open, it relies on a factory method called createArchive
 * whose purpose in life is to figure out which concrete archive class to 
 * use in order to open an archive.
 * 
 * @author cnh
 */
public abstract class AbstractNestedArchive 
	implements NestedArchive 
{
	public abstract Archive openArchive (
		ImprovedFile archiveFile, 
		ImprovedFile tempdir
	)
		throws LTSException;

	protected Map mySpecToArchiveMap;
	protected ImprovedFile myTempDir;
	protected ArchiveTreeNode myRootArchive;
	protected Map myArchiveToNodeMap;
	
	
	public Map getArchiveToNodeMap()
	{
		if (null == myArchiveToNodeMap)
			myArchiveToNodeMap = new HashMap();
		
		return myArchiveToNodeMap;
	}
	
	
	public ArchiveTreeNode archiveToNode (Archive arc)
	{
		ArchiveTreeNode node;
		node = (ArchiveTreeNode) getArchiveToNodeMap().get(arc);
		return node;
	}
	
	
	public void mapArchiveToNode (Archive arc, ArchiveTreeNode node)
	{
		getArchiveToNodeMap().put(arc,node);
	}
	
	
	public ArchiveTreeNode getRootArchive()
	{
		return myRootArchive;
	}
	
	public void setRootNode (ArchiveTreeNode root)
	{
		myRootArchive = root;
	}
	
	public void setRootArchive (Archive arc)
	{
		myRootArchive = new ArchiveTreeNode(arc, "");
	}
	
	
	
	public ImprovedFile getTempDir()
		throws IOException
	{
		if (null == myTempDir)
			myTempDir = ImprovedFile.createTempDirectory("nestedArc", "");
			
		return myTempDir;
	}
	
	public void setTempDir (ImprovedFile ifile)
	{
		myTempDir = ifile;
	}


	public static final String[] ARCHIVE_EXTENSIONS = {
		".war",
		".jar",
		".ear",
		".zip",
		".ZIP",
		".JAR",
		".WAR",
		".EAR"
	};
	
	public boolean isArchiveEntry (String s)
	{
		boolean isArchive = false;
		int i = 0;
		
		while (!isArchive && i < ARCHIVE_EXTENSIONS.length)
		{
			isArchive = s.endsWith(ARCHIVE_EXTENSIONS[i]);
			i++;
		}
		
		return isArchive;
	}
	
	
	public boolean isDirectoryEntry (String s)
	{
		return s.endsWith("/") || s.endsWith("\\");
	}
	
	
	public int getEntryType (String s)
		throws LTSException
	{
		int theType = -1;
		
		if (null == s)
			return ENTRY_DIRECTORY;
		
		if (isArchiveEntry(s))
			theType = ENTRY_ARCHIVE;
		else if (isDirectoryEntry(s))
			theType = ENTRY_DIRECTORY;
		else
			theType = ENTRY_FILE;
		
		return theType;
	}
	
	
	public boolean entryExists (String s)
		throws LTSException
	{
		String arcSpec = getArchiveSpec(s);
		String entry = getEntrySpec(s);
	
		Archive arc = findArchive(arcSpec);
		return arc.entryExists(entry);
	}
	
	
	public ArchiveTreeNode createEntry (
		String entry,
		ArchiveTreeNode parent,
		Map specToArchiveMap 
	)
	{
		ArchiveTreeNode child = null;
		
		String[] sa = StringUtils.split(entry, "/");
		for (int i = 0; i < sa.length; i++)
		{
			child = (ArchiveTreeNode) specToArchiveMap.get(sa[i]);
			if (null == child)
			{
				child = new ArchiveTreeNode (parent.getArchive(), sa[i]);
				parent.addChild(child);
				parent = child;
			}
		}
		
		return child;
	}
	
	
	
	public Map getSpecToArchiveMap()
	{
		if (null == mySpecToArchiveMap)
			mySpecToArchiveMap = new HashMap();
		
		return mySpecToArchiveMap;
	}

	
	public Archive specToArchive(String spec)
	{
		Archive arc = (Archive) getSpecToArchiveMap().get(spec);
		return arc;
	}

	
	public void mapSpecToArchive (String spec, Archive arc)
	{
		if (null == mySpecToArchiveMap)
			mySpecToArchiveMap = new HashMap();
		
		mySpecToArchiveMap.put(spec, arc);
	}
	
	
	public ArchiveTreeNode buildArchiveNode (
		AbstractTempDirectoryArchive arc,
		String spec,
		ImprovedFile tempdir,
		Map specToNodeMap
	)
		throws LTSException
	{
		ImprovedFile dir = null;
		
		try 
		{
			dir = tempdir.createTempDir("narc", "");
			File f = arc.getTempExtractedFile(spec);
			
			DefaultNestedArchive narc =
				new DefaultNestedArchive (f, dir);
			
			ArchiveTreeNode newNode = new ArchiveTreeNode(
				narc,
				spec,
				ArchiveTreeNode.NODE_ARCHIVE
			);
				
			return newNode;
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to create temp directory " + dir,
				e
			);
		} 
	}
	
	
	public ArchiveTreeNode buildArchiveTree(
		AbstractTempDirectoryArchive arc,
		String path,
		ImprovedFile tempdir,
		Map specToArchiveMap
	)
		throws LTSException
	{
		try 
		{
			ArchiveTreeNode node = new ArchiveTreeNode(arc, path);
			ArchiveTreeNode child = null;
			
			List l = arc.list();
			Iterator i = l.iterator();
			while (i.hasNext())
			{
				String s = (String) i.next();
				if (isArchiveEntry(s))
				{
					child = buildArchiveNode(
						arc,
						s, 
						tempdir, 
						specToArchiveMap
					);
				}
				else
					child = createEntry(s, node, specToArchiveMap);
				
				node.addChild(child);				
			}
			
			return node;
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to list archive contents "
				+ arc.getFile(),
				e
			);
		}
	}
	
	
	public void initialize (
		AbstractTempDirectoryArchive arc, 
		ImprovedFile dir
	)
		throws LTSException
	{
		addToTree(null, "", arc);
		setTempDir(dir);
		mapSpecToArchive("", arc);
	}
	
	
	public void initialize (File arcFile, ImprovedFile tempdir)
		throws LTSException
	{
		try 
		{
			ImprovedFile dir = tempdir.createTempDir("narc", "");
			ZipArchive zarc = new ZipArchive(arcFile, dir, true);
			initialize(zarc, tempdir);
		} 
		catch (IOException e) 
		{
			throw new LTSException ("Error creating temp directory for " + arcFile);
		}
	}
	
	
	/**
	 * Commit all the changes in the nested archive.
	 * 
	 * <P/>
	 * This class takes a "depth first" approach to doing commits in that it
	 * will commit the "lowest level" archives first, then the next level up,
	 * etc.  If any of the commits fail at any level, the entire operation 
	 * fails.
	 * 
	 * @see com.lts.io.archive.Archive#commit()
	 */
	public void commit() 
		throws LTSException, IOException 
	{
		Archive arc = getRootArchive().getArchive();
		commitTo(arc.getFile());
	}
	
	
	public void commitTo (File f)
		throws LTSException
	{
		try 
		{
			Iterator i = getRootArchive().depthFirstList().iterator();
			
			while (i.hasNext())
			{
				ArchiveTreeNode n = (ArchiveTreeNode) i.next();
				if (getRootArchive() == n)
					continue;
					
				Archive arc = n.getArchive();
				arc.commit();
			}
			
			Archive arc = getRootArchive().getArchive();
			arc.commitTo(f);	
		} 
		catch (IOException e) 
		{
			throw new LTSException (e);
		}
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#rollback()
	 */
	public void rollback() 
		throws LTSException 
	{
		String dirName = null;
		
		try
		{
			dirName = getTempDir().toString();
			getTempDir().deleteDirectory(true);
			getTempDir().mkdirs();
		}
		catch (IOException e)
		{
			throw new LTSException (
				"Encountered error while trying to remove temp files at "
				+ dirName,
				e
			);
		}
	}


	public List buildPrefixedList (ArchiveTreeNode n)
		throws LTSException
	{
		String prefix = null;
		
		try 
		{
			prefix = n.getAbsoluteEntry();
			if (!"".equals(prefix))
				prefix = prefix + '!';
			
			List outlist = new ArrayList();
			List inlist = n.getArchive().list();
			Iterator i = inlist.iterator();
			while (i.hasNext())
			{
				String s = (String) i.next();
				s = prefix + s;
				outlist.add(s);
			}
			
			return outlist;
		} 
		catch (IOException e) 
		{
			throw new LTSException (
				"Error trying to create nested archive list for "
				+ "archive at entry "
				+ prefix,
				e
			);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#list()
	 */
	public List list() 
		throws LTSException, IOException 
	{
		List l = new ArrayList();
		
		Iterator i = getRootArchive().depthFirstList().iterator();
		while (i.hasNext())
		{
			Object o = i.next();
			ArchiveTreeNode n = (ArchiveTreeNode) o;
			l.addAll(buildPrefixedList(n));
		}
		
		return l;
	}

	public String getArchiveSpec (String nestedEntry)
	{
		String s = "";
		
		int index = nestedEntry.lastIndexOf('!');
		if (-1 != index)
		{
			s = nestedEntry.substring(0, index);
		}
		
		return s;
	}
	
	public String getEntrySpec (String nestedEntry)
	{
		String s = nestedEntry;
		
		int index = nestedEntry.lastIndexOf('!');
		if (-1 != index)
		{
			s = nestedEntry.substring(1 + index);
		}
		
		return s;
	}
	
	
	public void addToTree (
		Archive parentArchive, 
		String spec, 
		Archive childArchive
	)
	{
		ArchiveTreeNode child = new ArchiveTreeNode (childArchive, spec);
		
		if (null == parentArchive)
			setRootNode(child);
		else
		{
			ArchiveTreeNode parent = archiveToNode(parentArchive);
			parent.addChild(child);
		}
		
		mapArchiveToNode(childArchive, child);
	}
	
	/**
	 * Extract out all the archives "wrap" a given archive.
	 * 
	 * <P/>
	 * Since archives can contain archives, and to get the myEntries from an 
	 * archive you have to extract it out, this method ensures that all the 
	 * archive that contains another archive has, itself, been extracted 
	 * before you try to extract an archive that it, itself, contains.
	 * 
	 * @param arc The archive that you want to ensure is extracted whose 
	 * parents you want to ensure are extracted.
	 * 
	 * @throws LTSException If problems are encountered while trying to 
	 * extract anything in the chain.  If possible, the message will indicate
	 * the offending archive that could not be extracted.
	 */
	public void extractArchives (Archive arc)
		throws LTSException
	{
		AbstractTempDirectoryArchive atda = null;
			 
		try 
		{
			ArchiveTreeNode node = archiveToNode(arc);
			while (null != node)
			{
				atda = (AbstractTempDirectoryArchive) node.getArchive();
				atda.extractArchive();
				
				node = (ArchiveTreeNode) node.getParent();					
			}
		} 
		catch (IOException e) 
		{
			if (null == atda)
				throw new LTSException(e);
			else
			{
				throw new LTSException (
					"Error trying to extract archive, " 
					+ atda.getArchiveFile(),
					e
				);
			}
		}
	}
	
	
	public ImprovedFile createTempDir ()
		throws LTSException
	{
		ImprovedFile mainTempDir = null;
		
		try
		{
			mainTempDir = getTempDir();
			ImprovedFile dir = getTempDir().createTempDir("narc", "");
			return dir;
		}
		catch (IOException e)
		{
			if (null == mainTempDir)
			{
				throw new LTSException (
					"Error getting temp directory for nested archive, "
					+ getFile(),
					e
				);
			}
			else
			{
				throw new LTSException (
					"Error trying to create temp directory in "
					+ mainTempDir,
					e
				);
			}
		}
	}
	
	
	
	public Archive findArchive (String spec)
		throws LTSException
	{
		Archive arc = specToArchive(spec);
		
		if (null == arc)
		{
			//
			// ensure that all containing archives are extracted
			//
			String parentSpec = getArchiveSpec(spec);
			AbstractTempDirectoryArchive parent = 
				(AbstractTempDirectoryArchive) findArchive(parentSpec);
			extractArchives(parent);
			
			//
			// now get the file within the containing archive that 
			// contains the archive we are trying to open
			//
			String childSpec = getEntrySpec(spec);
			ImprovedFile ifile = parent.getTempFileForEntry(childSpec);
			ImprovedFile tempdir = createTempDir();
			arc = openArchive(ifile, tempdir);
			
			//
			// update the various datastructures to include the new archive
			//
			addToTree(parent, spec, arc);
			mapSpecToArchive(spec, arc);
		}
		
		return arc;
	}
	
	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#add(java.lang.String, java.io.File)
	 */
	public void add(String name, File infile)
		throws LTSException, FileNotFoundException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		arc.add(entry, infile);
	}
	
	

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#add(java.lang.String, java.io.InputStream)
	 */
	public void add(String name, InputStream istream)
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		arc.add(entry, istream);
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#remove(java.lang.String)
	 */
	public boolean remove(String name) 
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		return arc.remove(entry);
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#get(java.lang.String)
	 */
	public InputStream get(String name) 
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		return arc.get(entry);
	}


	public OutputStream createEntry (String name)
		throws LTSException 
	{
		String arcspec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcspec);
		return arc.createEntry(entry);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#getFile()
	 */
	public File getFile() 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#extractToFile(java.lang.String, java.io.File)
	 */
	public void extractToFile(String name, File outputFile)
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		arc.extractToFile(entry, outputFile);
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#loadObject(java.lang.String)
	 */
	public Object loadObject(String name) 
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		return arc.loadObject(entry);
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#saveObject(java.lang.String, java.lang.Object)
	 */
	public void saveObject(String name, Object o)
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		arc.saveObject(entry, o);
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#saveProperties(java.lang.String, java.util.Properties)
	 */
	public void saveProperties(String name, Properties p)
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		arc.saveProperties(entry, p);
	}

	/* (non-Javadoc)
	 * @see com.lts.io.archive.Archive#loadProperties(java.lang.String)
	 */
	public Properties loadProperties(String name)
		throws LTSException, IOException 
	{
		String arcSpec = getArchiveSpec(name);
		String entry = getEntrySpec(name);
		
		Archive arc = findArchive(arcSpec);
		return arc.loadProperties(entry);
	}
	
	
	public boolean passesFilter (String s, String filter)
	{
		if (!s.startsWith(filter))
			return false;
		
		s = s.substring(filter.length());
		return !StringUtils.contains('/', s);
	}
	
	
	public String filterEntry (String s, String filter)
	{
		if (!s.startsWith(filter))
			return null;
		
		s = s.substring(filter.length());
		int index = s.indexOf('/');
		if (-1 != index && s.length() > (1 + index))
			s = s.substring(0, 1 + index);
		
		if ("".equals(s) || "/".equals(s))
			return null;
		else
			return s;			
	}
	
	
	public String convertOneString (String s)
	{
		char[] ca = s.toCharArray();
		StringBuffer sb = new StringBuffer(ca.length);
		
		for (int i = 0; i < ca.length; i++)
		{
			if (ca[i] == '\\')
				sb.append ('/');
			else
				sb.append (ca[i]);
		}
		
		return sb.toString();
	}
	
	public List convertPathSeparators (List inlist)
	{
		List outlist = new ArrayList();
		
		Iterator i = inlist.iterator();
		while (i.hasNext())
		{
			String s = (String) i.next();
			s = convertOneString(s);
			outlist.add(s);
		}
		
		return outlist;
	}
	
	public List filterEntries (List inlist, String filter)
	{
		HashSet outset = new HashSet();
		List outlist = new ArrayList();
		
		inlist = convertPathSeparators(inlist);
		
		Iterator i = inlist.iterator();
		while (i.hasNext())
		{
			String s = (String) i.next();
			s = filterEntry(s, filter);
			if (null != s)
				outset.add(s);
		}
		
		outlist.addAll(outset);
		return outlist;
	}
	
	
	public List listEntriesAt (String entry)
		throws LTSException, IOException
	{
		if (!"".equals(entry) && '!' == StringUtils.lastchar(entry))
		{
			entry = StringUtils.shortenString(entry);
		}
			
		String arcSpec = getArchiveSpec(entry);
		String entrySpec = getEntrySpec(entry);
		Archive arc = null;
		
		if (isArchiveEntry(entry))
		{
			arc = findArchive(entry);
		}
		else
		{
			arc = findArchive(arcSpec);
		}
		
		List l = arc.list();
		
		if (isArchiveEntry(entrySpec))
			entrySpec = "";
			
		l = filterEntries(l, entrySpec);
		return l;
	}
	

	public void buildRecursiveList (Archive arc, String prefix, List l)
		throws LTSException
	{
		try 
		{
			List tlist = arc.list();
			Iterator iter = tlist.iterator();
			while (iter.hasNext())
			{
				String s = (String) iter.next();
				s = prefix + s;
				l.add(s);
				
				if (isArchiveEntry(s))
				{
					Archive childArchive = findArchive(s);
					String temp = prefix + s + "!";
					buildRecursiveList (childArchive, temp, l);
				}
			}	
		} 
		catch (IOException e) 
		{
			throw new LTSException ("Error trying to list archive, " + arc, e);
		}
	}
	
	
	public List recursiveList ()
		throws LTSException
	{
		List l = new ArrayList();
		buildRecursiveList(getRootArchive().getArchive(), "", l);
		return l;	
	}
	
	
	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
	{
		throw DeepCopyUtil.deepCopyNotSupported(this);
	}

	public Object deepCopy() throws DeepCopyException
	{
		throw DeepCopyUtil.deepCopyNotSupported(this);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		throw DeepCopyUtil.deepCopyNotSupported(this);
	}

	public void deepCopyData(Object ocopy, Map map, boolean copyTransients)
		throws DeepCopyException
	{
		throw DeepCopyUtil.deepCopyNotSupported(this);
	}


}
