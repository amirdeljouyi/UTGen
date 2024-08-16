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
package com.lts.swing.menu;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jdesktop.swing.Application;

import com.lts.LTSException;
import com.lts.application.menu.ApplicationMenuBuilder;
import com.lts.util.CaselessMap;

/**
 * Build a JMenu or a JPopupMenu.
 * 
 * <H2>Usage</H2>
 * <CODE>
 * <PRE>
 * String[][] spec = new String {
 *     { "/File/New", "createNew" },
 *     { "/File/Save", "save" },
 *     { MenuBuilder.SEPARATOR },
 *     { "/File/Exit", "quit" },
 *     { "/Edit/Copy", "copy" },
 *     { "/Edit/Paste", "pase" },
 *     ...
 * };
 * 
 * MenuBuilder builder = new MenuBuilder();
 * JMenuBar menuBar = builder.buildMenuBar(this, spec);
 * </PRE>
 * </CODE>
 * 
 * <H2>Description</H2>
 * This class builds a menu that contains "callbacks" to the indicated methods 
 * of the specified receiver.  The input is an array of two-element string arrays:
 * the first element is the "path" to the menu item; for example: "/File/New".
 * Such a path means that there should be a menu called "File" and then, within 
 * that, an element called "New".  This path should implicitly create the File menu
 * as well as the "New" element.
 * 
 * <P>
 * The second element is the name of a public, no-argument method.  The method 
 * is supposed to be invoked when the menu item is selected.  If there is no 
 * method name associated with the element, then no method is invoked when it is 
 * selected.
 * 
 * <P>
 * In addition to regular menu elements, the client can also constant strings that 
 * are defined in this class.  These constants, and their effects are as follows:
 * 
 * <P>
 * <UL>
 * <LI>FILE_SEPARATOR - puts a separator in the menu
 * </UL>
 *
 * <H2>Note to Subclasses</H2>
 * Most of the methods in this class were marked private so that it could be determined 
 * if they were actually in use.  Any method can be remarked as public/protected and 
 * used by subclasses.
 * 
 * <H2>Algorithm</H2>
 * <P>
 * The process goes thusly:
 * <P>
 * <UL>
 * <LI>Create and "remember" a root node ("/")
 * <LI>For each row in the specification...
 *     <UL>
 *         <LI>process the row, using the root node as the parent
 *     </UL>
 * </UL>
 * 
 * <P>
 * Here is how a line in the specification is processed.  Given a parent node, "parent,"
 * and a "path" such as "/File/Save" or "/Edit/Preferences/Video" the following procedure
 * is used.  
 * <UL>
 *     <LI>If the node exists in the map from paths to nodes...
 *     <UL>
 *         <LI>Nothing to do, take no action
 *     </UL>
 *     <LI>If this is the root menu item ("/")
 *     <UL>
 *         <LI>Nothing to do, take no action
 *     </UL>
 *     <LI>else if this is a "constant" node (e.g., separator)...
 *     <UL>
 *         <LI>Add the corresponding node constant to the parent
 *     </UL>
 *     <LI>else...
 *     <UL>
 *     	   <LI>Split the path using "/"
 *         <LI>For each element in the path...
 *         <UL>
 *             <LI>Form the current path by concatenating the path components together,
 *             starting with "/" + first component
 *             <LI>If the node exists in the path to nodes map, skip it
 *             <LI>Create the node and add it to its parent node
 *         </UL>
 *     </UL>
 * </UL>
 * 
 * @author cnh
 *
 */
abstract public class MenuBuilder
{
	/**
	 * Get an ActionListener to process the selection of a menu item.
	 * 
	 * <P>
	 * This method should return an instance of {@link ActionListener} when called. It is
	 * allowed to return the same instance of ActionListener, etc., but it should either
	 * return a non-null value or throw an exception. Returning a null value will result
	 * in a NullPointerException, at least with the default implementation of
	 * {@link #processLeaf(String, String)} method.
	 * 
	 * <P>
	 * The "default implementation" for this method is contained in
	 * {@link ApplicationMenuBuilder}, which wraps the invocation in a try...catch and
	 * displays any exceptions using {@link Application#showException(Throwable)}.
	 * 
	 * @param receiver
	 *        The object whose method will be invoked. Pass null if the method to invoke
	 *        is a static (class) method rather than an instance method.
	 * @param methodName
	 *        The name of the method. This should be a method that takes no arguments and
	 *        returns nothing (i.e., void). The method may throw any subclass of Exception
	 *        --- checked or unchecked.
	 * @return The instance of ActionListener to use for the name and receiver. This value
	 *         should be non-null or a NullPointerException will result.
	 *         
	 * @see Application
	 * @see Application#showException(Throwable)
	 * @see ActionListener
	 * @see #processLeaf(String, String)
	 * @see ApplicationMenuBuilder
	 */
	abstract protected ActionListener getActionListener(Object receiver, String methodName) throws LTSException;

	/**
	 * When used as the name of a method in a specification, this indicates that 
	 */
	protected static final NewMenuNode NODE_SEPARATOR = new NewMenuNode("<Separator>");
	
	public static final String METHOD_SEPARATOR = "<separator>";
	
	protected static final Object[] SPEC_NAME_TO_NODE = {
		"<separator>", NODE_SEPARATOR, 
	};
	
	
	public static final String SEPARATOR = "<separator>";
	
	protected static Map ourNameToNode = new CaselessMap(SPEC_NAME_TO_NODE);
	
	/**
	 * Used when invoking receiver methods, which are supposed to be no-argument.
	 */
	protected static Object[] NO_PARAMS = {};
	
	
	protected NewMenuNode myRoot;
	protected Object myReceiver;
	
	
	/**
	 * Process a single "line" of a node specification.
	 * 
	 * <H2>Description</H2>
	 * A specification line consists of a path and a method name.  The path causes
	 * the necessary NewMenuNode elements to be created and added to the pathToNode
	 * property in a tree structure.
	 * 
	 * <P>
	 * The method name has one of three effects:
	 * <UL>
	 * <LI>If the method name is null, then the corresponding menuNode will have a 
	 * null value for the methodName as well.
	 * 
	 * <LI>If the method name is equal to one of the METHOD_ constants such as 
	 * METHOD_SEPARATOR, then the node is replaced with the corresponding NOD_
	 * constant value such as NODE_SEPARATOR.
	 * 
	 * <LI>Otherwise the corresponding Method object is looked up in the nameToMethod
	 * property and an instance of LocalActionListener is created and the callback
	 * property of the node is set to that listener.
	 * </UL>
	 * 
	 * @param path The path to the node.
	 * @param methodName The name of the method for the node.
	 */
	private void processNode (String path, String  methodName) throws LTSException
	{
		buildNodes(path);
		processLeaf(path, methodName);
	}

	protected void processLeaf(String path, String methodName) throws LTSException
	{
		NewMenuNode node = (NewMenuNode) myPathToNode.get(path);
		
		if (null == methodName)
			;
		if (methodName.equals(METHOD_SEPARATOR))
		{
			node.setName(METHOD_SEPARATOR);
		}
		else
		{
			ActionListener listener = getActionListener(myReceiver, methodName);
			node.setCallback(listener);
		}
	}
	
	/**
	 * Create the nodes for a node path.
	 * 
	 * <H2>Description</H2>
	 * A node path is a string that contains elements separated by "/" characters;
	 * for example "/Edit/Preferences/Java/Syntax" or "/File/Save As...".  This 
	 * method creates NewMenuNode objects for each element in the path, if they 
	 * do not already exist, and links them together in a parent/child relationship.
	 * These nodes are put in the pathToNode property, using the path of the node
	 * as the key.
	 * 
	 * <P>
	 * For example, suppose the pathToNode map is empty, and the string "/File/Save As.."
	 * is the argument.  The following nodes would be created:
	 * 
	 * <P>
	 * <TABLE border="1">
	 * <TR><TD><B>Path</B></TD><TD><B>Name</B></TD><TD><B>Parent</B></TD></TR>
	 * <TR><TD>/File</TD><TD>File</TD><TD>/</TD></TR>
	 * <TR><TD>/File/Save As...</TD><TD>Save As...</TD><TD>/File</TD></TR>
	 * </TABLE>
	 * 
	 * <P>
	 * Another example, suppose the pathToNode map is empty and the path is 
	 * "/Edit/Preferences/Java/Syntax"  The following myEntries would be created:
	 * 
	 * <TABLE border="1">
	 * <TR><TD><B>Path</B></TD><TD><B>Name</B></TD><TD><B>Parent</B></TD></TR>
	 * <TR><TD>/Edit</TD><TD>Edit</TD></TR>
	 * <TR><TD>/Edit/Preferences/Java</TD><TD>Java</TD><TD>/Edit/Preferences</TD></TR>
	 * <TR><TD>/Edit/Preferences/Java/Syntax</TD><TD>Syntax</TD><TD>/Edit/Preferences/Java</TD></TR>
	 * </TABLE>
	 * 
	 * <P>
	 * The method modifies the state of the instance by adding NewMenuNode objects
	 * to the pathToNode property.
	 * 
	 * @param path The node(s) to add.
	 */
	private void buildNodes(String path)
	{
		String[] comps = path.split("/");
		String cpath = "/";
		int i = 0;
		
		//
		// A string like "/foo/bar" will split to THREE elements: "", "foo" and "bar".
		// If we have a situation like this, skip the first element.
		//
		if (comps.length > 0 && comps[i].equals(""))
			i++;
		
		for ( ; i < comps.length; i++)
		{
			//
			// The parent must already exist
			//
			NewMenuNode parent = (NewMenuNode) myPathToNode.get(cpath);

			//
			// build the current path
			//
			char c = cpath.charAt(cpath.length() - 1);
			if (c != '/')
				cpath = cpath + "/";
			
			cpath = cpath + comps[i];
			
			//
			// if the current node does not exist, create it
			//
			NewMenuNode node = (NewMenuNode) myPathToNode.get(cpath);
			
			if (null == node)
			{
				node = new NewMenuNode(comps[i]);
				parent.addChild(node);
				myPathToNode.put(cpath, node);
			}
		}
	}
	
	
	
	protected Map myPathToNode = new HashMap();
	
	/**
	 * Given a specification that consists of an array of two-element string arrays, 
	 * build a tree of MenuNode elements.
	 * 
	 * <H2>Description</H2>
	 * Create the nodes described by the input specification by updating the pathToNode
	 * property.  The root of the tree has a key (path) of "/".  All other nodes should
	 * be children of that node.
	 * 
	 * @param spec
	 */
	private void buildNodeTree (String[][] spec) throws LTSException
	{
		for (int i = 0; i < spec.length; i++)
		{
			String[] current = spec[i];
			String name = current[0];
			String methodName = null;
			
			if (current.length > 1)
				methodName = current[1];
			
			processNode(name, methodName);
		}	
	}

	
	/**
	 * Create a menu, given the current state of the object and a parent menu node.
	 * 
	 * <H2>Description</H2>
	 * The NewMenuNode passed to the method is expected to have at least one child 
	 * NewMenuNode of its own.  For each child element, the method...
	 * 
	 * <UL>
	 * <LI>if the child is NODE_SEPARATOR, the method creates a separator in the 
	 * resulting menu via calling JMenu.addSeparator().
	 * 
	 * <LI>If the child has one or more children of its own, the method recurses on
	 * the child element.  The child menu item is also named using the name of the 
	 * menu node.
	 * 
	 * <LI>Otherwise, a JMenuItem is created for the child, using the name of node.
	 * If the child has a callback, the callback is added as an action listener to 
	 * the new menu item.
	 * </UL>
	 * 
	 * @param parent The menu node to create the menu for.  The node should have 
	 * at least one child.  The menu is not given a name.
	 * 
	 * @return The resulting menu
	 */
	protected void populateMenu (MenuWrapper menu, NewMenuNode parent) throws LTSException
	{
		for (Iterator i = parent.getChildren().iterator(); i.hasNext();)
		{
			NewMenuNode child = (NewMenuNode) i.next();
			if (child.getName().equals(METHOD_SEPARATOR))
				menu.addSeparator();
			else if (child.getChildCount() > 0)
			{
				JMenu childMenu = new JMenu(child.getName());
				MenuWrapper childWrapper = new MenuWrapper(childMenu);
				populateMenu(childWrapper, child);
				childMenu.setName(child.getName());
				menu.addMenuItem(childMenu);
			}
			else 
			{
				JMenuItem item = new JMenuItem(child.getName());
				
				if (null != child.getCallback())
					item.addActionListener(child.getCallback());
				
				menu.addMenuItem(item);
			}
		}
	}
	
	/**
	 * Preprocess a menu specification so that separator myEntries have a unique path.
	 * 
	 * <H3>Description</H3>
	 * Note that the various flavors of the build method invoke this method internally,
	 * clients do not have to call this method.
	 * 
	 * <P>
	 * The class expects all paths to be unique.  Thus a specification like this:
	 * 
	 * <P>
	 * <CODE>
	 * <PRE>
	 * protected static final String[][] SPEC = {
	 *     ...
	 *     { "/File/New", "newFile" },
	 *     { MenuBuilder.SEPARATOR },
	 *     { "/File/Save", "saveFile" },
	 *     { MenuBuilder.SEPARATOR },
	 *     { "/File/Exit", "exitProgram" },
	 *     ...
	 * };
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * Will not work because the two file separators will have the same path.  
	 * None the less, this is a very convenient and easy to understand way of specify
	 * a menu, so it is worth a little work to make such a notation viable.
	 * 
	 * <P>
	 * The solution is to use this method, which replaces myEntries like this:
	 * 
	 * <P>
	 * <CODE>
	 * <PRE>
	 *     { "/File/New", "newFile" },
	 *     { MenuBuilder.SEPARATOR },
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * With myEntries like this:
	 * 
	 * <P>
	 * <CODE>
	 * <PRE>
	 *     { "/File/New", "newFile" },
	 *     { "/File/New" + MenuBuilder.SEPARATOR, MenuBuilder.METHOD_SEPARATOR },
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * This method therefore creates a new version of the specification where all the 
	 * paths in the spec are unique.
	 * 
	 * @param spec The original specification.
	 * @return See description
	 * @throws LTSException If the spec contains only a single entry which is {@link #SEPARATOR}
	 * 
	 */
	public String[][] preProcessSpec (String[][] spec) throws LTSException
	{
		String[][] newSpec = new String[spec.length][];
		
		for (int i = 0; i < spec.length; i++)
		{
			String[] oldRow = spec[i];
			
			String oldPath = spec[i][0];
			String newMethod, newPath;
			if (oldRow.length < 2)
				newMethod = null;
			else
				newMethod = oldRow[1];
			
			//
			// If this NOT a separator, just copy it
			//
			if (!oldPath.equals(SEPARATOR))
			{
				newPath = oldPath;
			}
			//
			// otherwise, create a new row for it that has a unique path.
			//
			else
			{
				if (i < 1)
				{
					String msg = 
						"Invalid menu specification: first row cannot be a separator";
					throw new LTSException(msg);
				}
				
				
				newPath = spec[i-1][0] + SEPARATOR;
				newMethod = METHOD_SEPARATOR;
			}

			int size = (null == newMethod) ? 1 : 2;
			newSpec[i] = new String[size];
			newSpec[i][0] = newPath;
			
			if (null != newMethod)
				newSpec[i][1] = newMethod;
		}
		
		return newSpec;
	}
	
	
	/**
	 * The primary transform in the process of converting a specification to a
	 * menu.
	 * 
	 * <H2>Description</H2>
	 * At the risk of using functional decomposition terminology in an OO
	 * language, this method is the "central transform" in the process of
	 * translating a menu specification into an actual menu. It performs some
	 * setup and initialization myTasks, such as building the nameToMethod map and
	 * creating the root menu node, than calls buildNodeTree and populateMenu to
	 * get the actual work of the translation done.
	 * 
	 * <P>
	 * This method would be the initial entry point into the class were it not
	 * for the differences between JMenu, JPopupMenu and JMenuBar.
	 * 
	 * @param menu
	 *            The menu-like object to populate.
	 * @param receiver
	 *            The object to perform method calls against when various menu
	 *            items are selected.
	 * 
	 * @param spec
	 *            A string specification of what the contents of the menu should
	 *            be and what to do when a menu item is selectd.
	 * 
	 * @throws LTSException
	 *             This is thrown in two situations: if the specification tries
	 *             to add a separator to a menu bar or if the specification
	 *             tries to invoke a method that does not exist on the receiver.
	 */
	private void populateMenu (MenuWrapper menu, Object receiver, String[][] spec) throws LTSException
	{
		myReceiver = receiver;
		myPathToNode = new HashMap();
		myRoot = new NewMenuNode("/");
		myPathToNode.put("/", myRoot);
		
		String[][] newSpec = preProcessSpec(spec);		
		buildNodeTree(newSpec);
		populateMenu(menu, myRoot);
	}
	
	
	/**
	 * Build a menu bar based on a string specification.
	 * 
	 * <H2>Usage</H2>
	 * <CODE>
	 * <PRE>
	 * String[][] spec = new String[][] {
	 *     { "/File/Save", "save" },
	 *     { "/File/New", "newFile" },
	 *     { "/File/Quit", "quit" },
	 * };
	 * 
	 * MenuBuilder builder = new MenuBuilder();
	 * &lt;Some class&gt; receiver = &lt;some instance&gt;;
	 * JMenuBar menu = builder.buildMenuBar(receiver, spec);
	 * </PRE>
	 * </CODE>
	 * 
	 * <H2>Description</H2>
	 * This method creates a JMenuBar based on a string specification.  See the 
	 * class description for the format of this specification.
	 * 
	 * @param receiver
	 *            The object to perform method calls against when various menu
	 *            items are selected.
	 * 
	 * @param spec
	 *            A string specification of what the contents of the menu should
	 *            be and what to do when a menu item is selectd.
	 * 
	 * @throws LTSException
	 *             This is thrown in two situations: if the specification tries
	 *             to add a separator to a menu bar or if the specification
	 *             tries to invoke a method that does not exist on the receiver.
	 */
	public JMenuBar buildMenuBar(Object receiver, String[][] spec) throws LTSException
	{
		JMenuBar menuBar = new JMenuBar();
		MenuWrapper wrapper = new MenuWrapper(menuBar);
		populateMenu(wrapper, receiver, spec);
		return menuBar;
	}
	
	
	public JMenuBar buildMenuBar (Object receiver, String[][][] spec) throws LTSException
	{
		throw new LTSException("not implemented");
	}

	/**
	 * Build a popup menu based on a string specification.
	 * 
	 * <H2>Usage</H2>
	 * <CODE>
	 * <PRE>
	 * String[][] spec = new String[][] {
	 *     { "/File/Save", "save" },
	 *     { "/File/New", "newFile" },
	 *     { "/File/Quit", "quit" },
	 * };
	 * 
	 * MenuBuilder builder = new MenuBuilder();
	 * &lt;Some class&gt; receiver = &lt;some instance&gt;;
	 * JPopup popup = builder.buildPopupMenu(receiver, spec);
	 * </PRE>
	 * </CODE>
	 * 
	 * <H2>Description</H2>
	 * This method creates a JMenuBar based on a string specification.  See the 
	 * class description for the format of this specification.
	 * 
	 * @param receiver
	 *            The object to perform method calls against when various menu
	 *            items are selected.
	 * 
	 * @param spec
	 *            A string specification of what the contents of the menu should
	 *            be and what to do when a menu item is selectd.
	 * 
	 * @throws LTSException
	 *             This exception is thrown if the specification asks for a method that 
	 *             does not actually exist in the receiver.
	 */
	public JPopupMenu buildPopupMenu(Object receiver, String[][] spec) throws LTSException
	{
		JPopupMenu popup = new JPopupMenu();
		MenuWrapper wrapper = new MenuWrapper(popup);
		populateMenu(wrapper, receiver, spec);
		return popup;
	}
}