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

import java.util.HashMap;
import java.util.Map;

import com.lts.pest.data.IdTree;
import com.lts.util.TreeNode;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

public class DirtyCleanTree extends IdTree
{
	private static final long serialVersionUID = 1L;
	
	protected transient boolean dirty;
	
	public void initialize (IdTreeNode rootTask)
	{
		super.initialize(rootTask);
		setDirty(false);		
	}
	
	public boolean isDirty()
	{
		return this.dirty;
	}

	
	/**
	 * Add the child activity to the children of the parent activity.
	 * 
	 * <P>
	 * This method is the "preferred" method for adding a new Activity to the system
	 * as it sends out the appropriate messages after making the change.
	 * 
	 * @param parent The existing activity.
	 * @param child The new activity.
	 */
	public void addNodeTo(IdTreeNode parent, IdTreeNode child)
	{
		super.addNodeTo(parent, child);
		setDirty(true);
	}

	
	public void setDirty(boolean isDirty)
	{
		dirty = isDirty;
	}
	
	public void postDeserialize()
	{
		consistencyCheck(getRoot());
		buildIdActivityMap(getRootIdNode());
	}

	/**
	 * Ensure that the tree is consistent.
	 * 
	 * <P>
	 * A consistent tree has the following properties:
	 * <UL>
	 * <LI>Ensure that the root node has null as its parent.</LI>
	 * <LI>Ensure that the root node has an ID of 0.</LI>
	 * <LI>All node IDs are unique.</LI>
	 * <LI>Given a node, "parent", ensure that for each "child", child.parent = parent.</LI>
	 * </UL>
	 * </P>
	 * 
	 * @param root The root of the tree.
	 */
	private void consistencyCheck(TreeNode root)
	{
		consistencyCheckRoot(root);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		consistencyCheckChild(root, map);
	}

	private void consistencyCheckRoot(TreeNode root)
	{
		if (null == root)
		{
			throw new IllegalArgumentException("null root");
		}
		
		if (null != root.getParent())
		{
			throw new IllegalArgumentException("root should have null as its parent.");
		}
	}

	private void consistencyCheckChild(TreeNode parent, Map<Integer, Integer> map)
	{
		for (Object o : parent.getChildren())
		{
			TreeNode child = (TreeNode) o;
			
			if (parent == child)
			{
				String msg =
					"Child and parent are the same object: " + child;
				throw new IllegalArgumentException(msg);
			}
			
			if (parent != child.getParent())
			{
				String msg = 
					"child, " + child + ", is inconsistent with parent, " + parent;
				throw new IllegalArgumentException(msg);
			}
			
			consistencyCheckChild(child, map);
		}
	}

	/**
	 * Remove an existing activity from another activity, notify listeners as 
	 * appropriate.
	 * 
	 * <P>
	 * This is the preferred method for removing activities from the system.  Once
	 * the activity is removed, {@link ActivityListener#activityRemoved(Activity, Activity)}
	 * is called on all the listeners for this instance.
	 * 
	 * @param parent The "containing" activity.
	 * @param child The activity to be removed.
	 */
	public void removeNodeFrom(IdTreeNode parent, IdTreeNode child)
	{
		super.removeNodeFrom(parent, child);
		setDirty(true);
	}
	
	
	public void deepCopyData (Object ocopy, Map map, boolean copyTransients)
		throws DeepCopyException
	{
		super.deepCopyData(ocopy, map, copyTransients);
		if (copyTransients)
		{
			throw DeepCopyUtil.transientNotImplemented(this);
		}
	}
	
	
	public void changeNode (IdTreeNode oldVersion, IdTreeNode newVersion)
	{
		super.changeNode(oldVersion, newVersion);
		setDirty(true);
	}
}
