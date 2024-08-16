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
package com.lts.swing.tree.mvc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * A "controller" that forwards requests to an underlying application model.
 * 
 * <P>
 * This class implements a controller from the MVC pattern that keeps an application
 * model in sync with a UI view.  It forwards requests to add or remove nodes onto 
 * the application model and listens for events which it then forwards to the UI.
 * 
 * @author cnh
 *
 */
public class DelegatedTreeModel implements TreeModel, ApplicationTreeListener
{
	protected ApplicationTree tree;
	transient protected ApplicationTreeListenerHelper helper;
	transient protected Map<ApplicationNode, DefaultMutableTreeNode> appToTreeNodeMap;
	transient protected Map<DefaultMutableTreeNode, ApplicationNode> treeNodeToAppMap;
	transient protected DefaultTreeModel treeModel;
	transient protected TreeModelListenerHelper treeHelper;
	
	public DelegatedTreeModel (ApplicationTree appTree)
	{
		initialize(appTree);
	}
	
	protected void initialize(ApplicationTree appTree)
	{
		this.tree = appTree;
		this.treeHelper = new TreeModelListenerHelper(this);
		this.appToTreeNodeMap = new HashMap<ApplicationNode, DefaultMutableTreeNode>();
		this.treeNodeToAppMap = new HashMap<DefaultMutableTreeNode, ApplicationNode>();
	}
	
	protected void buildTreeModel ()
	{
		this.appToTreeNodeMap = new HashMap<ApplicationNode, DefaultMutableTreeNode>();
		this.treeNodeToAppMap = new HashMap<DefaultMutableTreeNode, ApplicationNode>();

		ApplicationNode appRoot = this.tree.getRoot();
		
		if (null != appRoot)
		{
			addAppNode(appRoot);
			
			DefaultMutableTreeNode uiRoot;
			uiRoot = appNodeToTreeNode(appRoot);
			this.treeModel = new DefaultTreeModel(uiRoot);
			this.treeModel.addTreeModelListener(this.treeHelper);
		}

	}
	
	protected void addAppNode (ApplicationNode node)
	{
		if (null == node)
			return;
		
		DefaultMutableTreeNode uiNode = new DefaultMutableTreeNode(node);
		this.appToTreeNodeMap.put(node, uiNode);
		this.treeNodeToAppMap.put(uiNode, node);
		
		for (Iterator i = node.getChildren().iterator(); i.hasNext(); )
		{
			ApplicationNode child = (ApplicationNode) i.next();
			addAppNode(child);
			
			DefaultMutableTreeNode uiChild = appNodeToTreeNode(child);
			uiNode.add(uiChild);
		}
	}
	
	
	public void addTreeModelListener(TreeModelListener l)
	{
		this.treeHelper.addTreeModelListener(l);
	}

	public void removeTreeModelListener(TreeModelListener l)
	{
		this.treeHelper.removeTreeModelListener(l);
	}
	
	
	public Map<ApplicationNode, DefaultMutableTreeNode> getAppToTreeNodeMap()
	{
		if (null == this.appToTreeNodeMap)
			this.appToTreeNodeMap = new HashMap<ApplicationNode, DefaultMutableTreeNode>();
		
		return this.appToTreeNodeMap;
	}
	
	
	public DefaultMutableTreeNode appNodeToTreeNode (ApplicationNode node)
	{
		return getAppToTreeNodeMap().get(node);
	}
	
	public void removeFromMap (ApplicationNode node)
	{
		getAppToTreeNodeMap().remove(node);
	}
	
	public void addToMap (ApplicationNode appNode, DefaultMutableTreeNode uiNode)
	{
		getAppToTreeNodeMap().put(appNode, uiNode);
	}
	
	

	public Object getChild(Object parent, int index)
	{
		return this.treeModel.getChild(parent, index);
	}

	public int getChildCount(Object parent)
	{
		return this.treeModel.getChildCount(parent);
	}

	public int getIndexOfChild(Object parent, Object child)
	{
		return this.treeModel.getIndexOfChild(parent, child);
	}

	public Object getRoot()
	{
		return this.treeModel.getRoot();
	}

	public boolean isLeaf(Object node)
	{
		return this.treeModel.isLeaf(node);
	}

	public void valueForPathChanged(TreePath path, Object newValue)
	{
		this.treeModel.valueForPathChanged(path, newValue);
	}

	public void nodeAdded(ApplicationNode node)
	{
		this.addAppNode(node);
		DefaultMutableTreeNode uiNode = appNodeToTreeNode(node);
		ApplicationNode parent = node.getParent();
		DefaultMutableTreeNode uiParent = appNodeToTreeNode(parent);
		
		int index = uiParent.getChildCount();
		this.treeModel.insertNodeInto(uiNode, uiParent, index);
	}

	public void nodeChanged(ApplicationNode node)
	{
		DefaultMutableTreeNode uiNode = appNodeToTreeNode(node);
		this.treeModel.nodeChanged(uiNode);
	}

	public void nodeRemoved(ApplicationNode node, ApplicationNode parent)
	{
		DefaultMutableTreeNode uiNode = appNodeToTreeNode(node);
		this.treeModel.removeNodeFromParent(uiNode);
	}
	
	
	public void createChildNode (ApplicationNode parent, ApplicationNode child)
	{
		this.tree.addNodeTo(parent, child);
	}
	
	public void removeChildNode (ApplicationNode parent, ApplicationNode child)
	{
		this.tree.removeNodeFrom(parent, child);
	}
}
