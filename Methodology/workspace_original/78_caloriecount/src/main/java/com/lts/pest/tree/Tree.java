//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest.tree;

import java.io.Serializable;
import java.util.Map;

import com.lts.util.TreeNode;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

public class Tree implements Serializable, DeepCopier
{
	private static final long serialVersionUID = 1L;

	protected TreeNode root;
	transient protected TreeListenerHelper myTreeListenerHelper;
	
	public TreeNode getRoot()
	{
		return this.root;
	}
	
	public Tree(TreeNode theRoot)
	{
		initialize(theRoot);
	}
	
	protected Tree ()
	{
		
	}
	
	
	public void initialize (TreeNode theRoot)
	{
		this.root = theRoot;
	}
	
	
	public TreeListenerHelper getTreeListener()
	{
		if (null == myTreeListenerHelper)
			myTreeListenerHelper = new TreeListenerHelper();
		
		return myTreeListenerHelper;
	}
	
	
	public void addNodeTo (TreeNode parent, TreeNode child)
	{
		parent.addChild(child);
		getTreeListener().fireNodeAdded(parent, child);
	}
	
	
	public void removeNodeFrom (TreeNode parent, TreeNode child)
	{
		parent.removeChild(child);
		getTreeListener().fireNodeRemoved(parent, child);
	}
	
	
	/**
	 * Notify listeners that the node has changed.
	 * 
	 * @param node
	 */
	public void changeNode (TreeNode oldVersion, TreeNode newVersion)
	{
		oldVersion.updateFrom(newVersion);
		getTreeListener().fireNodeChanged(oldVersion);
	}
	
	
	public void addTreeListener (TreeListener listener)
	{
		getTreeListener().addListener(listener);
	}
	
	public void removeTreeListener (TreeListener listener)
	{
		getTreeListener().removeListener(listener);
	}
	
	
	public void deepCopyData (Object ocopy, Map map, boolean copyTransients) throws DeepCopyException
	{
		Tree copy = (Tree) ocopy;
		copy.root = (TreeNode) DeepCopyUtil.continueDeepCopy(this.root, map, copyTransients);
		
		if (copyTransients)
			copy.myTreeListenerHelper = (TreeListenerHelper) DeepCopyUtil.continueDeepCopy(this.myTreeListenerHelper, map, copyTransients);
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}
}
