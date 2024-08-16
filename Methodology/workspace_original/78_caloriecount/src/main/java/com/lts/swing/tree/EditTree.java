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
package com.lts.swing.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.lts.LTSException;
import com.lts.swing.SimpleMouseAdapter;
import com.lts.swing.menu.MenuBuilder;

/**
 * A JTree that responds to basic editing commands.
 * 
 * <H2>Quick Start</H2>
 * Override the following methods to use the data of your choice:
 * <UL>
 * <LI>{@link #editCreateData(Object)}
 * <LI><I>outside of this class</I> convert the tree model to application data.
 * </UL>
 * 
 * <H2>Description</H2>
 * This class creates a JTree that interprets the following keys:
 * <UL>
 * <LI>insert - create a new node, call createAction
 * <LI>delete - delete the selected node(s), call deleteAction
 * <LI>enter/return - edit the selected node, call editAction
 * <LI>slow double click - edit the selected node, method?
 * </UL>
 * 
 * <P>
 * "slow double click" is the effect that one gets in the Windows file explorer
 * thingie, when you left-click a file, then left click it again slowly enough
 * that the system does not interpret it as a double-click.  That is, it allows
 * you to change the name of the file.
 * 
 * <P>
 * The basic implementation creates a tree of strings that the user can edit.
 * Such a simple implementation takes a non-trivial amount of work and you have
 * to do it over and over again, so here is a quick way to get up and running.
 * 
 * @author cnh
 */
abstract public class EditTree extends JTree
{
	abstract protected MenuBuilder getMenuBuilder();
	
	/**
	 * Interact with the user to create a new node for the tree.
	 * <P>
	 * This method should go through whatever mubo-jumbo required to get the information
	 * required for a new node. The call passes the future immediate parent node, which
	 * this method may need.
	 * <P>
	 * The method should not insert the node into the UI structure. That is the
	 * responsibility of the calling method.
	 * <P>
	 * The method should handle any errors that take place and return null if they are
	 * unrecoverable.
	 * 
	 * @param parent
	 *        The future UI parent for the node.
	 * @return null if it is determined that the node cannot be created, a new
	 *         DefaultMutableTreeNode otherwise.
	 */
	abstract protected void createNode (DefaultMutableTreeNode parent);
	
	/**
	 * Interact with the user to modify an existing node.
	 * <P>
	 * This method interacts with the user to change the application-specific information
	 * containted in the node passed to it. While the {@link DefaultMutableTreeNode} is
	 * passed to the method, it should ignore the UI aspects and focus on only those
	 * properties that the application is concerned with.
	 * <P>
	 * The method should return true if the edits were successful --- if the user accepted
	 * the changes, no errors occurred, etc. The method should return false otherwise.
	 * <P>
	 * The method should handle all errors itself. That is, display any messages, etc. If
	 * the errors are unrecoverable or whatever, the method should return false to signal
	 * that nothing changed. The method should also ensure that, at least from a UI
	 * standpoint, the node did not change if it returns false.
	 * 
	 * @param node
	 *        The node to edit.
	 * @return true if the user accepted the changes, no unrecoverable errors occurred,
	 *         etc. false otherwise.
	 */
	abstract protected void editNode (DefaultMutableTreeNode node);
	
	/**
	 * Ask the application to remove the node.
	 * <P>
	 * This method interacts with the application in such a way as to remove a node
	 * passed to this method.
	 * <P>
	 * Instances of this class do not directly manipulate the tree model.  Instead,
	 * they send requests onto some other agency and then listen for changes.  Thus
	 * the sub-class or the application may decide not to remove the node after all.
	 * 
	 * @param node The node to remove.
	 */
	abstract protected void removeNode (DefaultMutableTreeNode node);
	
	/**
	 * This method moves a node from one parent to another.
	 * <P>
	 * This method initiates the appropriate request to move the designated node 
	 * from its current parent to a new partent node.
	 * <P>
	 * The method does not directly interact with the tree model.  Instead it requests
	 * that a particular action take place and then the tree listens for changes.
	 * 
	 * @param node The node to move.
	 * @param destination The new parent for the node.
	 */
	abstract protected void moveNode (DefaultMutableTreeNode node, DefaultMutableTreeNode destination);
	
	protected DefaultMutableTreeNode[] myCutNodes;
	
	public EditTree (DefaultTreeModel model) throws LTSException
	{
		super();
		initialize(model);
	}
	
	
	public EditTree ()
	{
		super();
	}
	
	public static boolean messagesLoaded = false;
		
	public static String MSG_MULTIPLE_PARENTS;
	public static String MSG_MULTI_DELETE;
	public static String TITLE_MULTI_DELETE;
	public static String ERROR_DELETE_ROOT;
	
	public static final String PREFIX = EditTree.class.getName();
	public static final String LABEL = PREFIX + ".label.";
	public static final String ERROR = PREFIX + ".error.";
	public static final String TITLE = PREFIX + ".title.";
	
	
	protected void initialize() throws LTSException
	{
		setEditable(true);
		setupBindings();
		setRootVisible(false);
	}
	
	protected void initialize(DefaultTreeModel model) throws LTSException
	{
		setModel(model);
		initialize();
	}
	
	public DefaultTreeModel getEditModel()
	{
		return (DefaultTreeModel) getModel();
	}
	
	
	protected void listBindings (InputMap map)
	{
		KeyStroke[] all = map.allKeys();
		for (int i = 0; i < all.length; i++)
		{
			System.out.println(all[i]);
		}
	}
	
	
	protected static final int WANCESTOR = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
	protected static final int WFOCUS = JComponent.WHEN_FOCUSED;
	protected static final int WWINDOW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	
	protected void bindKey (int key, String actionName, Action action)
		throws LTSException
	{	
		InputMap inputMap;
		
		KeyStroke ks = KeyStroke.getKeyStroke(key, 0);
		inputMap = this.getInputMap(WFOCUS);	
		inputMap.put(ks, actionName);
		
		inputMap = this.getInputMap(WANCESTOR);
		inputMap.put(ks, actionName);
		
		inputMap = this.getInputMap(WWINDOW);
		inputMap.put(ks, actionName);
		
		this.getActionMap().put(actionName, action);
	}
	
	
	public DefaultMutableTreeNode getSelectedNode ()
	{
		TreePath path = getSelectionPath();
		if (null == path)
			return null;
		
		return (DefaultMutableTreeNode) path.getLastPathComponent();
	}
	
	
	/**
	 * Filter double-click edits so that they only fire on leaf nodes.
	 * 
	 * <P>
	 * The method simply returns if the mouse event corresponds to a node with 
	 * children.  In the case of a leaf node, the method calls editAction.
	 * 
	 * <P>
	 * In the context of a JTree, a double-click on a node that has children
	 * of any sort, aka an "iterior node," signals that the user wants to expand
	 * or collapse the node.  This panel also wants to use it to bring up the
	 * edit dialog for a node.  The solution to this conflict is to check the 
	 * node being clicked to see if it is a leaf or an interior node, and only
	 * perform the edit on leaf nodes.
	 * 
	 * @param event The mouse event that triggered the method call.
	 */
	protected void doubleClickTree()
	{
		DefaultMutableTreeNode node = getSelectedNode();
		if (null == node)
			return;
		
		doubleClickNode(node);
	}
	
	/**
	 * A node was double clicked.
	 * 
	 * @param node The node that was double clicked.  This value should not be
	 * null.
	 */
	protected void doubleClickNode (DefaultMutableTreeNode node)
	{
		if (node.getChildCount() < 1)
			editAction();
	}
	
	/**
	 * A double click event occurred in the tree.
	 * 
	 * @param event
	 */
	protected void performDoubleClick (MouseEvent event)
	{
		if (treeWasDoubleClicked(event))
			doubleClickTree();
	}
	
	
	protected boolean treeWasDoubleClicked(MouseEvent event)
	{
		if (null == event.getSource())
			return false;
		
		Component comp = getComponentAt(event.getX(), event.getY());
		if (null == comp)
			return false;
		
		if (this != comp)
			return false;
		
		return true;
	}
	
	
	public static final int ACTION_CREATE = 0;
	public static final int ACTION_DELETE = 1;
	public static final int ACTION_EDIT = 2;
	
	protected static String[][] SPEC_POPUP = {
		{ "/Create",		"createAction" },
		{ "/Edit",			"editAction" },
		{ "/Delete",		"deleteAction" },
	};
	
	@SuppressWarnings("serial")
	protected void setupBindings() throws LTSException
	{
		try
		{
			Action act = new AbstractAction() {
				public void actionPerformed (ActionEvent event) {
					createAction();
				}
			};
			bindKey (KeyEvent.VK_INSERT, "createNode", act);
			
			act = new AbstractAction() {
				public void actionPerformed (ActionEvent event) {
					deleteAction();
				}
			};
			bindKey (KeyEvent.VK_DELETE, "deleteNode", act);
			
			act = new AbstractAction() {
				public void actionPerformed (ActionEvent event) {
					editAction();
				}
			};
			bindKey (KeyEvent.VK_ENTER, "editNode", act);

			MenuBuilder builder = getMenuBuilder();
			JPopupMenu menu = builder.buildPopupMenu(this, SPEC_POPUP);

			SimpleMouseAdapter sma = new SimpleMouseAdapter(menu) {
				public void doubleClick (MouseEvent event) {
					performDoubleClick(event);
				}
			};
			this.addMouseListener(sma);
		}
		finally
		{}
	}
	
	
	/**
	 * Respond to a request to delete the selected node(s).
	 * 
	 * <P>
	 * This method performs the following actions:
	 * <UL>
	 * <LI>If nothing is selected, simply return.
	 * <LI>Call {@link #checkDeleteRoot(TreePath[])} and abort if the method
	 * returns false.
	 * <LI>Call {@link #confirmDelete(TreePath[])} to confirm the delete and 
	 * abort if the method returns false.
	 * <LI>Call {@link #removeNodes(TreePath[])} to remove the selected node(s).
	 * </UL>
	 * 
	 * <P>
	 * This method basically supplies a way for subclasses to quickly override 
	 * the default delete behavior.
	 */
	public void deleteAction ()
	{
		TreePath[] paths = this.getSelectionPaths();
		if (null == paths || paths.length < 1)
			return;
		
		if (!checkDeleteRoot(paths))
			return;

		if (!confirmDelete(paths))
			return;
		
		removeNodes(paths);
	}
	
	/**
	 * Respond to a user request to edit the selected node.
	 * 
	 * <P>
	 * This method is a hook to allow subclasses to override the default edit
	 * behavior by overriding this method.  The default action is to call
	 * {@link #nodeAction(int)} with {@link #ACTION_EDIT} as the argument.
	 */
	public void editAction()
	{
		nodeAction(ACTION_EDIT);
	}
	
	protected void editNodeAction (TreePath[] paths)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
			paths[0].getLastPathComponent();
		
		editNode(node);
	}
	
	
	/**
	 * Determine which node will be the parent if the user decides to create one.
	 * 
	 * <P>
	 * This method provides a hook for subclasses that want to use a different policy
	 * for identifying the parent node or if the subclass wants to flag certain 
	 * situations as errors.
	 * 
	 * <P>
	 * The default behavior is to choose a parent in the following manner:
	 * <UL>
	 * <LI>If a single node is selected, the parent is that node.
	 * <LI>If multiple nodes are selected, then the parent is the last node.
	 * <LI>If no nodes are selected, then the parent is the root node.
	 * </UL>
	 * 
	 * @return The parent node, or null if the create should be aborted.
	 */
	protected DefaultMutableTreeNode getParentForCreate ()
	{
		//
		// if multiple nodes are selected, then perform the add on the last such
		// node.  If no nodes are selected, perform the add on the root.
		//
		DefaultMutableTreeNode parent;
		TreePath[] paths = this.getSelectionPaths();
		if (null == paths || paths.length < 1)
		{
			parent = (DefaultMutableTreeNode) getModel().getRoot();
		}
		else
		{
			int index = paths.length - 1;
			parent = (DefaultMutableTreeNode)paths[index].getLastPathComponent();
		}

		return parent;
	}
	
	/**
	 * Insert the node into the tree view.
	 * 
	 * <P>
	 * The default behavior is to simply create a new DefaultMutableTreeNode 
	 * and add it to the view.
	 * 
	 * <P>
	 * This method provides a hook for subclasses to redefine how nodes are actually
	 * added to the view.  A common change, for example, tell some other object to 
	 * perform the add on the model, and then wait for a message when that happens.
	 * The view then updates after the model has been updated.
	 * 
	 * @param parent The parent for the new node.
	 * @param data The data for the new node.
	 */
	protected void insertNode (DefaultMutableTreeNode parent, DefaultMutableTreeNode child)
	{
		getEditModel().insertNodeInto(child, parent, parent.getChildCount());
		TreeNode[] nodePath = child.getPath();
		TreePath treePath = new TreePath(nodePath);
		scrollPathToVisible(treePath);
	}
	
	
	/**
	 * The user wants to create a new node.
	 * 
	 * <P>
	 * This method performs the following actions:
	 * <UL>
	 * <LI>Call getParentForCreate to determine the new node's parent
	 * <LI>If the parent is non-null, then call editCreateData with null to get
	 * the data for the new node.
	 * <LI>If the data is non-null, then call createNode to update the view.
	 * </UL>
	 *
	 */
	public void createAction ()
	{
		DefaultMutableTreeNode parent = getParentForCreate();
		if (null == parent)
			return;
		
		createNode(parent);
	}
	
	
	protected void nodeAction (int action)
	{
		//
		// All the actions we define require at least one node to be selected
		// before we will do anything.  If nothing is selected, then ignore 
		// the request.
		//
		TreePath[] paths = this.getSelectionPaths();
		if (null == paths || paths.length < 1)
			return;
		
		boolean empty = true;
		int index = 0;
		while (empty && index < paths.length)
		{
			empty = paths[index].getLastPathComponent() == null;
			index++;
		}

		if (empty)
			return;
		
		//
		// at least one node was selected --- do something in response
		//
		switch (action) 
		{
			case ACTION_CREATE :
				createNodeAction(paths);
				break;
				
			case ACTION_EDIT :
				editNodeAction(paths);
				break;
				
			case ACTION_DELETE :
				deleteNodes(paths);
				break;
				
			default :
				break;
		}
	}
	
	protected boolean createNodeCheckFail (TreePath[] paths)
	{
		if (paths.length > 1)
		{
			String msg = MSG_MULTIPLE_PARENTS;
			JOptionPane.showMessageDialog(this, msg);
			return true;
		}
		
		return false;
	}
	
	protected void createNodeAction (TreePath[] paths)
	{	
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) getEditModel().getRoot();
		if (paths.length > 0)
		{
			parent = (DefaultMutableTreeNode) paths[0].getLastPathComponent();
		}
		
		createNode(parent);
	}
	
	protected boolean checkDeleteRoot (TreePath[] paths)
	{
		Object root = getModel().getRoot();
		for (int i = 0; i < paths.length; i++)
		{
			if (paths[i].getLastPathComponent() == root)
			{
				JOptionPane.showMessageDialog(this, ERROR_DELETE_ROOT);
				return false;
			}
		}
		
		return true;
	}
	
	
	protected boolean confirmDelete(TreePath[] paths)
	{
		boolean askConfirm = true;
		
		if (paths.length > 1)
		{
			askConfirm = true;
		}
		else
		{
			TreeNode node = (TreeNode) paths[0].getLastPathComponent();
			askConfirm = node.getChildCount() > 0;
		}
		

		boolean performDelete;
		
		if (!askConfirm)
			performDelete = true;
		else
		{
			int optionType = JOptionPane.YES_NO_OPTION;
			String msg = MSG_MULTI_DELETE;			
			String title = TITLE_MULTI_DELETE;
			
			int result = JOptionPane.showConfirmDialog(this, msg, title, optionType);
			performDelete = JOptionPane.YES_OPTION == result;
		}
		
		return performDelete;
	}
	
	
	protected void deleteNodes (TreePath[] paths)
	{
		if (!checkDeleteRoot(paths))
			return;

		if (!confirmDelete(paths))
			return;
		
		removeNodes(paths);
	}

	/**
	 * Update the view by removing the selected nodes.
	 * 
	 * <P>
	 * This method provides a hook for subclasses that want to use a different
	 * approach for removing nodes.  The default behavior is to simply remove
	 * the selected nodes from the tree view.
	 * 
	 * <P>
	 * Clients that want to propogate the operation on to the model and then wait
	 * for an update to the model should override this method to perform some other
	 * action.
	 * 
	 * @param paths the nodes that have been selected for removal.
	 */
	protected void removeNodes(TreePath[] paths)
	{
		Map roots = buildRemoveSet(paths);
		for (Iterator i = roots.keySet().iterator(); i.hasNext();)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) i.next();
			getEditModel().removeNodeFromParent(node);
		}
	}

	/**
	 * Build a minimal set of nodes to remove from the tree.
	 * 
	 * <P>
	 * Essentially, remove redundant nodes --- if the parent of a node has been
	 * selected for deletion, then you don't need to explicitly remove the 
	 * children of that node.  This method builds a HashMap that filters out 
	 * such cases.
	 *
	 * @param paths The nodes selected for removal.
	 * @return The minimal set of nodes to remove.
	 */
	protected Map buildRemoveSet (TreePath[] paths)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		HashMap<Object, Object> roots = new HashMap<Object, Object>();
		
		for (int i = 0; i < paths.length; i++)
		{
			DefaultMutableTreeNode node = 
				(DefaultMutableTreeNode) paths[i].getLastPathComponent();

			//
			// only do something if we have not seen this node before
			//
			if (null == map.get(node))
			{
				//
				// Note that we have seen all of the node's descendents and that
				// none of the descendents needs to explicitly be deleted --- 
				// removing the ancestor will automatically remove the offspring
				//
				Enumeration e = node.breadthFirstEnumeration();
				while (e.hasMoreElements())
				{
					DefaultMutableTreeNode child = 
						(DefaultMutableTreeNode) e.nextElement();
					
					map.put(child, child);
					roots.remove(child);
				}
				
				//
				// remember that we have seen the ancestor and to remove it
				//
				map.put(node, node);
				roots.put(node, node);
			}
		}
		
		return roots;
	}

	
	public void cutAction()
	{
		myCutNodes = null;
		
		TreePath[] paths = getSelectionPaths();
		if (null == paths || paths.length < 1)
			return;
		
		DefaultMutableTreeNode[] sel = new DefaultMutableTreeNode[paths.length];
		for (int i = 0; i < paths.length; i++)
		{
			sel[i] = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
		}
		
		myCutNodes = sel;
	}
	
	
	public void pasteAction()
	{
		DefaultMutableTreeNode destination = 
			(DefaultMutableTreeNode) getSelectedNode();
		
		if (null == destination || null == myCutNodes || myCutNodes.length < 1)
			return;
		
		for (int i = 0; i < myCutNodes.length; i++)
		{
			moveNode(myCutNodes[i], destination);
		}	
	}
}
