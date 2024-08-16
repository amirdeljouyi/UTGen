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

import java.util.Map;

import com.lts.util.TreeNode;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

/**
 * Represents an archive that lives in a nested archive file.
 * 
 * <P/>
 * See the NestedArchive interface for details on nested archives.  Instances
 * of this class are different from TreeNode instances in that they "know" 
 * the entry name that is used to identify them within their parent archive
 * and that they "know" what archive they are associated with.  The "root
 * archive" has no entry associated with it.
 * 
 * @author cnh
 */
public class ArchiveTreeNode extends TreeNode 
{
	public static final int NODE_FILE = 0;
	public static final int NODE_ARCHIVE = 1;
	public static final int NODE_ROOT = 2;
	
	protected String myEntry;
	protected Archive myArchive;
	protected int myNodeType;
	
	
	public int getNodeType()
	{
		return myNodeType;
	}
	
	public void setNodeType (int i)
	{
		myNodeType = i;
	}
	
	
	public String getEntry()
	{
		return myEntry;
	}
	
	public void setEntry (String s)
	{
		myEntry = s;
	}
	
	
	public ArchiveTreeNode (Archive arc, String entry)
	{
		setArchive(arc);
		setEntry(entry);
	}
	
	public ArchiveTreeNode (Archive arc, String entry, int nodeType)
	{
		setArchive(arc);
		setEntry(entry);
		setNodeType(nodeType);
	}
	
	public void buildAbsoluteEntry (StringBuffer sb)
	{
		if (null != getParent())
		{
			ArchiveTreeNode parent = (ArchiveTreeNode) getParent();
			parent.buildAbsoluteEntry(sb);
			sb.append ('!');
		}
		
		sb.append (getEntry());
	}
	
	
	public String getAbsoluteEntry ()
	{
		StringBuffer sb = new StringBuffer();
	
		buildAbsoluteEntry(sb);
			
		String s = sb.toString();
		return s;
	}
	
	public Archive getArchive ()
	{
		return myArchive;
	}
	
	public void setArchive (Archive arc)
	{
		myArchive = arc;
	}
	
	public void deepCopyData(Object ocopy, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		super.deepCopyData(ocopy, map, copyTransients);
		
		ArchiveTreeNode copy = (ArchiveTreeNode) ocopy;
		copy.myArchive = (Archive) DeepCopyUtil.continueDeepCopy(this.myArchive, map, copyTransients);
		copy.myEntry = this.myEntry;
		copy.myNodeType = this.myNodeType;
		
	}

	
	
}
