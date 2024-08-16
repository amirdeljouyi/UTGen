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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.lts.swing.tree.EditTree;
import com.lts.util.TreeNode;

/**
 * A TreeListener that mediates between a Tree and an EditTree.
 * 
 * <P>
 * This class supports a pattern where creating and deleting of nodes
 * does not take place at the JTree or the TreeModel.  Instead, a request to create 
 * a node is sent through this class and it is forwarded to some underlying
 * application object.  
 * 
 * <P>
 * This class then listens to the application object for creates or deletes and 
 * responds by creating or removing DefaultMutableTreeNodes in the JTree's model.
 * 
 * <P>
 * This approach keeps the application and the UI objects in synch because changes
 * always occur to the application object before the UI is notified.
 * 
 * <P>
 * The class listeners for events from the EditTree such as {@link TreeListener#nodeAdded(TreeNode, TreeNode)}
 * and calls the appropriate method on the EditTree to have a new UI node added.
 * 
 * @author cnh
 */
public class SimpleTreeListener implements TreeListener
{
	protected Map<TreeNode, DefaultMutableTreeNode> uiToNode;
	protected DefaultTreeModel model;
	protected Tree tree;
	protected EditTree editTree;
	
	
	public SimpleTreeListener (Tree theTree, EditTree theEditTree)
	{
		initialize(theTree, theEditTree);
	}
	
	
	/**
	 * Add the task sub-tree to the tree model.
	 * 
	 * <H2>Note</H2>
	 * This is intended as an initialization method.  It does not coordinate with
	 * the tree model --- so no listeners are notified of any change.  
	 * Additionally, the rest of the object is not aware of any nodes created by 
	 * this method.
	 * 
	 * <H2>Description</H2>
	 * This method creates a DefaultMutableTreeNode for the provided task.  If the
	 * task has children, then nodes are created for them as well and added to 
	 * the children of the returned node.
	 * 
	 * @param parent The root task of the new tree.
	 * @return The tree node that corresponds to the provided task.
	 */
	protected DefaultMutableTreeNode buildTree (TreeNode parent)
	{
		DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);

		for (Iterator i = parent.getChildren().iterator(); i.hasNext();)
		{
			TreeNode child = (TreeNode) i.next();
			DefaultMutableTreeNode childNode = buildTree(child);
			parentNode.add(childNode);
		}
		
		return parentNode;
	}
	
	/**
	 * Add all the tasks in the nodes in the tree passed to this method to the 
	 * provided map.
	 * 
	 * <P>
	 * The map goes from TreeNode objects to DefaultMutableTreeNode objects.
	 */
	protected void addNodesToMap (Map<TreeNode, DefaultMutableTreeNode> map, DefaultMutableTreeNode node)
	{
		TreeNode parent = (TreeNode) node.getUserObject();
		map.put(parent, node);
		
		Enumeration enumer = node.depthFirstEnumeration();
		while (enumer.hasMoreElements())
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) enumer.nextElement();
			TreeNode childNode = (TreeNode) child.getUserObject();
			map.put(childNode, child);
		}
	}
	
	/**
	 * Create the initial tree.
	 * 
	 * <P>
	 * This method builds a tree of DefaultMutableTreeNode objects that has the 
	 * same structure as the tasks in the PestApplicationData provided to the 
	 * method.  Once this is done, meke those nodes the tree in the provided 
	 * model, and then listen for any future changes to the system of tasks.
	 * 
	 * @param data The tasks and source of events.
	 * @param model The tree model where changes are to be reflected.
	 */
	protected void initialize (Tree theTree, EditTree theEditTree)
	{
		this.tree = theTree;
		this.editTree = theEditTree;
		
		TreeNode root = (TreeNode) this.tree.getRoot();
		DefaultMutableTreeNode rootNode = buildTree(root);
		
		this.model = new DefaultTreeModel(rootNode);
		this.uiToNode = new HashMap<TreeNode, DefaultMutableTreeNode>();
		addNodesToMap(this.uiToNode, rootNode);
	}
	
	/**
	 * A new task has been added to the hierarchy of tasks.
	 * 
	 * <P>
	 * Ensure that a corresponding node is added to the tree.
	 * 
	 * @param parent The parent for the new task.
	 * @param child The new task.
	 */
	public void nodeAdded(TreeNode parent, TreeNode child)
	{
		DefaultMutableTreeNode parentNode = this.uiToNode.get(parent);
		DefaultMutableTreeNode childNode = this.uiToNode.get(child);
		
		if (null == childNode)
		{
			childNode = buildTree(child);
			this.uiToNode.put(child, childNode);
			int loc = parentNode.getChildCount();
			this.model.insertNodeInto(childNode, parentNode, loc);
			
			Object[] nodePath = childNode.getPath();
			TreePath path = new TreePath(nodePath);
			this.editTree.scrollPathToVisible(path);
		}
	}

	/**
	 * A task has been removed from the hierarchy of tasks.
	 * 
	 * <P>
	 * Remove the corresponding node from the tree.
	 * 
	 * @param parent The parent task.
	 * @param child The task being removed.
	 */
	public void nodeRemoved(TreeNode parent, TreeNode child)
	{
		DefaultMutableTreeNode childNode = this.uiToNode.get(child);
		
		if (null == childNode)
			return;
		
		this.model.removeNodeFromParent(childNode);
		this.uiToNode.remove(child);
	}

	public void requestAddNode (TreeNode parent, TreeNode newChild)
	{
		this.tree.addNodeTo(parent, newChild);
	}
	
	public void requestRemoveNode (TreeNode parent, TreeNode child)
	{
		this.tree.removeNodeFrom(parent, child);
	}
	
	public DefaultTreeModel getModel()
	{
		return model;
	}


	public void nodeChanged(TreeNode node)
	{
		DefaultMutableTreeNode modelNode = (DefaultMutableTreeNode) uiToNode.get(node);
		model.nodeChanged(modelNode);
	}
	
	
	public void fireAllChanged()
	{
		javax.swing.tree.TreeNode root = 
			(javax.swing.tree.TreeNode) model.getRoot();
		
		model.nodeStructureChanged(root);
	}


	public void allChanged()
	{
		fireAllChanged();
	}
}
