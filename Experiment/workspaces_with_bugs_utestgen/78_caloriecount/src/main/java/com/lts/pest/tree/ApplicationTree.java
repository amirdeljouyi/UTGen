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

import java.util.Iterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.RepositoryListener;
import com.lts.application.data.ApplicationData;
import com.lts.application.menu.ApplicationMenuBuilder;
import com.lts.swing.menu.MenuBuilder;
import com.lts.swing.tree.EditTree;
import com.lts.util.TreeNode;

/**
 * An EditTree that reloads itself when an application changes the repository that it is
 * using.
 * 
 * <H2>Abstract Class</H2>
 * Concrete subclasses must define the following methods:
 * <UL>
 * <LI>A constructor that takes an instance of {@link Application}.
 * <LI>The method {@link #getTree(ApplicationData)}.
 * <LI>The method {@link #editCreateData(Object)}.
 * </UL>
 * 
 * <P>
 * This class defines {@link EditTree#getMenuBuilder()} to use 
 * {@link ApplicationMenuBuilder} --- subclasses do not have that responsibility.
 * 
 * <H2>Description</H2>
 * The tree is designed to operate in concert with an instance of {@link Application} and
 * adds itself as a {@link RepositoryListener}. When any event occurs concerning the
 * repository, the class calls {@link #reload(ApplicationRepository)}.
 * 
 * @author cnh
 * @see EditTree
 * @see ApplicationMenuBuilder
 */
abstract public class ApplicationTree extends EditTree
{
	/**
	 * Get the subclass of Tree that we are going to "watch" from the 
	 * application.
	 * 
	 * @param app The application to obtain the tree from.
	 * @return The tree data.
	 */
	abstract public Tree getTree (ApplicationData appData);

	protected Tree tree;
	
	public ApplicationTree() throws ApplicationException
	{}
	
	public ApplicationTree(Application app) throws ApplicationException
	{
		initialize(app);
	}
	
	/**
	 * Initialize the instance.
	 * 
	 * <P>
	 * In addition to the operations that the superclass might perform, this 
	 * version of the initialize method also does the following:
	 * <UL>
	 * <LI>Registers a listener for repository changes.
	 * <LI>Register a listener for tree changes (and/remove nodes).
	 * </UL>
	 * 
	 * @param app The application whose data should be used.
	 * @throws ApplicationException If a problem occurs during initialization.
	 */
	public void initialize (Application app) throws ApplicationException
	{
		try
		{
			super.initialize();
			
			SimplifiedRepositoryListener listener = new SimplifiedRepositoryListener() {
				public void repositoryChanged(ApplicationRepository repository) {
					reload(repository);
				}
			};
			
			app.addRepositoryListener(listener);
			reload(app.getApplicationData());
		}
		catch (LTSException e)
		{
			String msg = "Error trying to setup listeners";
			throw new ApplicationException(msg, e);
		}
	}
	
	
	public void reload (ApplicationRepository repos)
	{
		ApplicationData data = Application.getInstance().getApplicationData();
		reload(data);
	}
	
	
	public void reload(ApplicationData appData)
	{
		this.tree = getTree(appData);
		SimpleTreeListener listener = new SimpleTreeListener(this.tree, this);
		this.tree.addTreeListener(listener);
		
		DefaultTreeModel model = listener.getModel();
		setModel(model);
	}

	protected void insertNode (DefaultMutableTreeNode parent, Object data)
	{
		TreeNode parentNode = (TreeNode) parent.getUserObject();
		TreeNode childNode = (TreeNode) data;
		
		this.tree.addNodeTo(parentNode, childNode);
	}

	/**
	 * Override the default remove to instead ask the model part of the MVC to
	 * remove the node.
	 * 
	 * <P>
	 * This version of the method does not remove the selected nodes directly.  
	 * Instead it calls {@link PestApplicationData#removeChildFrom(Activity, Activity)} 
	 * to perform this; the class depends on a signal being sent to listeners
	 * when the node is actually removed for the update to occur.
	 */
	protected void removeNodes(TreePath[] paths)
	{
		Map roots = buildRemoveSet(paths);
		for (Iterator i = roots.keySet().iterator(); i.hasNext();)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) i.next();
			TreeNode child = (TreeNode) node.getUserObject();
			node = (DefaultMutableTreeNode) node.getParent();
			TreeNode parent = (TreeNode) node.getUserObject();
			
			this.tree.removeNodeFrom(parent, child);
		}
	}
	
	
	protected MenuBuilder getMenuBuilder()
	{
		MenuBuilder builder = new ApplicationMenuBuilder();
		return builder;
	}
}
