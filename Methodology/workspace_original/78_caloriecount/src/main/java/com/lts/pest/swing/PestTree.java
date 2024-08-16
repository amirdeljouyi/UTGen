//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of Timelord.
//
//  Timelord is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published
//  by the Free Software Foundation; either version 2.1 of the License, or
//  (at your option) any later version.
//
//  Timelord is distributed in the hope that it will be useful, but WITHOUT
//  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
//  FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public
//  License along with Timelord; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
//  USA

package com.lts.pest.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.lts.application.ApplicationException;
import com.lts.swing.tree.EditTree;

/**
 * An EditTree that supports the notion that each tree node has a unique ID.
 * 
 * <P>
 * In practical terms, this means that the class has a map from ID to node and that
 * the class updates this map when nodes are added or removed.
 * 
 * @author cnh
 *
 */
abstract public class PestTree extends EditTree
{
	protected Map<Object, DefaultMutableTreeNode> myIdToNodeMap;
	
	public PestTree (DefaultTreeModel model) throws ApplicationException
	{
		super();
		initialize(model);
	}
	
	public void initialize (DefaultTreeModel model) throws ApplicationException
	{
		try
		{
			super.initialize(model);
			myIdToNodeMap = buildIdToNodeMap();
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error initializing tree";
			throw new ApplicationException(msg, e);
		}
	}
	
	
	protected void removeFromMap (DefaultMutableTreeNode node)
	{
		if (null == node)
			return;
		
		Object userObject = node.getUserObject();
		if (null != userObject)
			myIdToNodeMap.remove(userObject);
		
		int count = node.getChildCount();
		for (int i = 0; i < count; i++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			removeFromMap(child);
		}
	}
	
	protected void removeNodes (TreePath[] paths)
	{
		for (int i = 0; i < paths.length; i++)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
			removeFromMap(node);
		}
		super.removeNodes(paths);
	}
	
	
	protected void addNodes(Map<Object, DefaultMutableTreeNode> map,
			DefaultMutableTreeNode node)
	{
		if (null == node)
			return;
		
		Object userNode = node.getUserObject();
		if (null != userNode)
			map.put(userNode, node);
		
		int count = node.getChildCount();
		for (int i = 0; i < count; i++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			addNodes(map, child);
		}
	}
	
	protected Map buildIdToNodeMap ()
	{
		Map map = new HashMap<Object, DefaultMutableTreeNode>();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
		addNodes(map, root);
		return map;
	}
	

	@Override
	protected void createNode(DefaultMutableTreeNode parent)
	{
		// TODO Auto-generated method stub

	}
}
