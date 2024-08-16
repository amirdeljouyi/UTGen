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
package com.lts.pest;


/**
 * <H2>Message Formatting and Internationalization</H2>
 * This class is part of a system of classes that cooperate to simplify 
 * message handling.  Specifically, subclasses and clients of this class should 
 * use the strategies described here to get the following kinds of text 
 * messages:
 * 
 * <P>
 * <UL>
 * <LI>Text prompts --- "Enter your age" or "SSN"
 * <LI>Informational messages --- "data saved" or "connection established"
 * <LI>Heading strings for windows --- "Tasks", "Current Projects"
 * <LI>Error messages --- "Could not find the file {1}"
 * <LI>Text labels --- (includes button labels) "SSN", "OK", "Cancel"
 * </UL>
 * 
 * To use the system described, follow these steps:
 * <UL>
 * <LI>Create an interface that defines the message keys.
 * <LI>Use Application.formatMessage
 * <LI>Create a resource file
 * <LI>define addBundleName
 * </UL>
 * 
 * <H3>Create an Interface that Defines Message Keys</H3>
 * The class should look something like this:
 * <CODE>
 * <PRE>
 * package com.foo.app;
 * public interface AppMessages extends ApplicationMessages {
 *     public static final String ERROR = AppMessages.class.getName + ".error.";
 *     public static final String ERROR_FILE_NOT_FOUND = ERROR + "fileNotFound";
 *     public static final String ERROR_PERMISSION = ERROR + "permission";
 *     ...
 * };
 * </PRE>
 * </CODE>
 * 
 * <P>
 * It is important to use the class name as a prefix for the message key to 
 * avoid "name collisions" with keys defined elsewhere.
 * 
 * <H3>Use Application.formatMessage</H3>
 * This method takes care of looking up the message string from the key, using
 * the right formatter, etc.  Use the keys that you defined in the previous 
 * step.
 * 
 * <P>
 * <CODE>
 * <PRE>
 * ...
 * Application app = Application.getInstance();
 * File f = new File("some name");
 * if (!f.exists()) {
 *     String msg = app.getMessage(AppMessages.ERROR_FILE_NOT_FOUND, f.getName());
 *     JOptionPane.showMessage(msg);
 * }
 * </PRE>
 * </CODE>
 * 
 * <H3>Create a Resource File</H3>
 * This file maps message keys to format strings.  The format strings are then 
 * used along with any data passed to the getMessage method to create the 
 * actual message.
 * 
 * <P>
 * The file should live in a sub-directory called "resources" of the same parent 
 * directory that holds the .java source files.
 * 
 * <P>
 * <PRE>
 * <CODE>
 * com.foo.app.errors.fileNotFound=Could not find the file, {1}
 * com.foo.app.errors.permission=The file, {1} is read-only
 * </CODE>
 * </PRE>
 * 
 * <H3>define addBundleName</H3>
 * This method simply tells the framework where to find the resource bundle for 
 * this application.  By default, the only application bundle is 
 * "application".  If you are using this framework, then override 
 * this method to return the base names of the other bundles you have added to 
 * the system.  For example:
 * 
 * <P>
 * <CODE>
 * <PRE>
 * public String getBundleName() {
 *     return "FooApp";
 * }
 * </PRE>
 * </CODE>
 *  
 * <P>
 * If you have several bundles you want to add to the bundle path, then 
 * override getBundleNames instead: 
 *  
 * <P>
 * <CODE>
 * <PRE>
 * public String[] getBundleNames () {
 *     return { "bundle1", "bundle2", "bundle3" };
 * }
 * </PRE>
 * </CODE>
 * @author cnh
 */
public interface PestMessages
{
	public static final String PREFIX = PestMessages.class.getName();
	
	public static final String PROMPT = PREFIX + ".prompt";
	public static final String ERROR = PREFIX + ".errors.";
	
	
	/**
	 * An internal error that means an argument to 
	 * {@link javax.swing.KeyStroke#getKeyStroke(java.lang.String)} returned
	 * null.
	 * <UL>
	 * <LI>0 (String) --- the key name that was not found
	 * </UL>
	 */
	public static final String ERROR_KEY_NOT_FOUND =
		ERROR + "keyNotFound";
	
	public static final String ERROR_KEY_NOT_FOUND_MSG =
		"Could not find the message for the key {0}";
	

	/**
	 * A string to display to the user when asking if they want to continue 
	 * despite a severe application error.
	 */
	public static final String PROMPT_ASK_CONTINUE =
		PROMPT + "askContinue";
	
	public static final String PROMPT_ASK_CONTINUE_MSG = 
		"The system has encountered a severe error.\n"
		+ "Do you want to retry or quite?";
	
	/**
	 * Exception thrown while initializing a window.  Not a whole lot you can
	 * say...
	 */
	public static final String ERROR_INITIALIZING_WINDOW =
		ERROR + "errorCreatingWindow";
	
	public static final String ERROR_INITIALIZING_WINDOW_MSG =
		"The system has encountered an internal error that prevents\n"
		+ "it from performing the requested operation.";
	
	public static String[][] values = {
		{ ERROR_KEY_NOT_FOUND, 		ERROR_KEY_NOT_FOUND_MSG },
		{ PROMPT_ASK_CONTINUE, 		PROMPT_ASK_CONTINUE_MSG },
		{ ERROR_INITIALIZING_WINDOW, ERROR_INITIALIZING_WINDOW_MSG },
	};

}
