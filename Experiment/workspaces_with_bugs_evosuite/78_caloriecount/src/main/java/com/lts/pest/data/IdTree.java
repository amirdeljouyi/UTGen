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
package com.lts.pest.data;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lts.pest.tree.IdTreeNode;
import com.lts.pest.tree.Tree;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;


/**
 * A tree that contains IdObject data.
 * 
 * <P>
 * The tree is able to look up an object based on its ID.
 * 
 * @author cnh
 */
abstract public class IdTree extends Tree implements ObjectInputValidation
{
	protected transient Map<Integer, IdTreeNode> idNodeMap;
	protected int nextInt;
	protected long nextLong;

	protected IdTree()
	{}
	
	protected IdTree (IdTreeNode theRoot)
	{
		super(theRoot);
	}

	protected void addNodeToMap(Map<Integer, IdTreeNode> map, IdTreeNode node)
	{
		map.put(node.getId(), node);
		
		for (Iterator i = node.getChildren().iterator(); i.hasNext();)
		{
			IdTreeNode child = (IdTreeNode) i.next();
			addNodeToMap(map, child);
		}
	}

	protected Map<Integer, IdTreeNode> buildIdActivityMap(IdTreeNode theRoot)
	{
		Map<Integer, IdTreeNode> map = new HashMap<Integer, IdTreeNode>();
		addNodeToMap(map, theRoot);
		return map;
	}
	
	protected void initializeIdActivityMap ()
	{
		Map<Integer, IdTreeNode> map = buildIdActivityMap(getRootIdNode());
		this.idNodeMap = map;
	}
	

	public void initialize(IdTreeNode theRoot)
	{
		super.initialize(theRoot);
		
		Map<Integer, IdTreeNode> map = buildIdActivityMap(theRoot);
		this.idNodeMap = map;
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
		getIdNodeMap().put(child.getId(), child);
		super.addNodeTo(parent, child);
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
		getIdNodeMap().remove(child.getId());
		super.removeNodeFrom(parent, child);
	}

	protected Map<Integer, IdTreeNode> getIdNodeMap()
	{
		if (null == this.idNodeMap)
			this.idNodeMap = new HashMap<Integer, IdTreeNode>();
		
		return this.idNodeMap;
	}
	
	
	public IdTreeNode idToNode (int id)
	{
		return getIdNodeMap().get(id);
	}

	public int nextIntegerValue()
	{
		int value = this.nextInt;
		this.nextInt++;
		return value;
	}

	public long nextLongValue()
	{
		long value = this.nextLong;
		this.nextLong++;
		return value;
	}
	
	
	public IdTreeNode getRootIdNode()
	{
		return (IdTreeNode) getRoot();
	}
	
	
	public void validateObject() throws InvalidObjectException
	{
		initializeIdActivityMap();
	}
	
	
	public void deepCopyData (Object ocopy, Map map, boolean copyTransients)
		throws DeepCopyException
	{
		super.deepCopyData(ocopy, map, copyTransients);
		
		IdTree copy = (IdTree) ocopy;
		
		copy.nextInt = this.nextInt;
		copy.nextLong = this.nextLong;
		
		if (copyTransients)
		{
			throw DeepCopyUtil.transientNotImplemented(this);
		}
		
	}
	
}
