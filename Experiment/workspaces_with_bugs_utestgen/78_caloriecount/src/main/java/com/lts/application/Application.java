//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.application;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.lts.LTSException;
import com.lts.application.cmdline.CommandLineProcessor;
import com.lts.application.cmdline.SimpleCommandLineProcessor;
import com.lts.application.data.ApplicationData;
import com.lts.application.international.DefaultMessageFormatter;
import com.lts.application.international.MessageFormatter;
import com.lts.application.prop.ApplicationProperties;
import com.lts.application.prop.CommandLinePolicy;
import com.lts.application.repository.SimpleZipRepository;
import com.lts.application.swing.ApplicationActionWrapper;
import com.lts.application.swing.WrappedActionListener;
import com.lts.event.Callback;
import com.lts.event.CallbackListenerHelper;
import com.lts.io.IOUtilities;
import com.lts.io.ImprovedFile;
import com.lts.util.CollectionUtils;
import com.lts.util.StringIntMap;
import com.lts.util.StringUtils;
import com.lts.util.prop.PropertiesUtil;

/**
 * A class that defines a pattern for the "main" class of a Java application.
 * 
 * <H3>Abstract Class</H3>
 * For a subclass to be instantiateable, it needs to define the following 
 * methods:
 * <UL>
 * <LI/>startApplication
 * </UL>
 * 
 * <H3>Description</H3>
 * This class contains some utility classes that I have found myself writing 
 * over and over again that pertain to applications.  The rest of this discussion
 * gives an overview of how to use this class and what those utilities are.
 * 
 * <UL>
 * <LI><A href="#how_to_use">How to use this class</A>
 * <LI><A href="#class_properties">Class Properties</A>
 * <LI><A href="#system_properties">System properties</A>
 * <LI><A href="#repositories">Application repository</A>
 * <LI><A href="#file_operations">File operations</A>
 * <LI><A href="#start_and_stop">Startup and shutdown</A>
 * </UL>
 * 
 * <H3><A name="class_properties">Class Properties</A></H3>
 * This section details the properties of the class itself.  The class
 * properties are:
 * 
 * <UL>
 * <LI>
 * </UL>
 * 
 * <H3><A name="how_to_use">How to use this class</A></H3>
 * The basic pattern for applications that use this framework is to have a 
 * main method like the following:
 * 
 * <PRE>
 * public static void main (String[] argv)
 * {
 *     ApplicationSubclass sub = new ApplicationSubclass();
 *     sub.startApplication(argv);
 * }
 * 
 * 
 * public void startApplication()
 * {
 *    &lt;perform application stuff&gt;
 * }
 * </PRE>
 * 
 * <H3><A name="#properties">System Properties</A></H3>
 * This class uses two primary mechanisms for handling data: the application
 * properties file and the application repository.  
 * 
 * <P>
 * The application properties is a text file that uses the format that
 * {@link Properties#store(java.io.OutputStream, java.lang.String)} uses.  
 * Specifically, each line is a property of the form 
 * &lt;name&gt;=&lt;value&gt;.
 * 
 * <P>
 * By default, this class uses the user.home system property as the directory
 * to look for the system properties file.  Within that directory, the class
 * looks for a file whose name is defined by {@link #getPropertyFileName()}
 * and then loads that file.
 * 
 * <P>
 * The properties related methods this class defines includes:
 * 
 * <UL>
 * <LI>{@link #getPropertyFileName()} - defines the file name that the application
 * will use as the name for the properties file.
 * 
 * <LI>{@link #getProperty(String)} - get a property from the property file.
 * <LI>{@link #getLastDirectory()} - get the directory the user last loaded or 
 * stored a file to.
 * 
 * <LI>{@link #getLastFile()} - get the File object that represents the file that
 * the user last loaded or stored.
 * 
 * <LI>{@link #loadApplicationProperties()} - called to load the application 
 * properties file.  This is called by {@link #startApplication(String[])}.
 * 
 * <LI>{@link #setProperty(String, String)} - set an application property.
 * <LI>{@link #storeApplicationProperties()} - saves the applciation properties.
 * This is called by {@link #saveData()}.
 * </UL>
 * 
 * <H3><A name="#repositories">Repositories</A></H3>
 * Application repositories are central locations to load and store data.  They
 * present what amounts to a virtual file system; while frequently the 
 * repository is implemented as a single ZIP file.
 * 
 * <P>
 * The methods pertaining to repositories are:
 * 
 * <UL>
 * <LI>{@link #createRepository()} - create an "empty" application repository.
 * <LI>{@link #getRepository()} - get the current application repository.
 * <LI>{@link #loadObject(String, String)} - load an object from the repository.
 * <LI>{@link #storeObject(String, Object, String)} - store an object in 
 * the repository.
 * 
 * <LI>{@link #loadXml(String, String)} - load an object from an XML "file" 
 * within the application repository.
 * 
 * <LI>{@link #storeAsXML(String, Object, String)} - store an object to an 
 * XML "file" within the application repository. 
 * </UL>
 * 
 * <P>
 * To use repositories, override the createRepository method to return a 
 * subclass of ApplicationRepository.  The default method returns null, 
 * which signals that the application has no repository.  
 * 
 * <H3><A name="file_operations">File Operations</A></H3>
 * This class provides a simplified interface for selecting files.  Furthermore,
 * if these methods are used, the application "remembers" things like the last
 * file loaded/stored and the last directory used.
 * 
 * <UL>
 * <LI>{@link #browseOpenDirectory()} - prompt the user select a directory.
 * <LI>{@link #browseOpenFile()} - prompt the user select a file.
 * <LI>{@link #browseOpenFileOrDirectory()} - prompt the user to select a file 
 * or a directory.
 * 
 * <LI>{@link #browseSaveDirectory()} - prmopt the user to select a directory 
 * to save data to.
 * 
 * <LI>{@link #browseSaveFile()} - prompt the user to select a file to save
 * data to.
 * 
 * <LI>{@link #browseSaveFileOrDirectory()} - prompt the user to select a file
 * or directory to save data to.
 * </UL>
 * 
 * <H3><A name="start_and_stop">Startup and Shutdown</A></H3>
 * This class supplies some functionality when the application starts up and
 * when it shuts down.  Specifically:
 * 
 * <UL>
 * <LI>{@link #startApplication(String[])} - parse the command line, load 
 * the application properties file and load the application repository.
 * 
 * <LI>{@link #quit()} - prompt the user to confirm the quit, save system data
 * (properties, repositories, etc.) and remove temporary files.
 * </UL>
 * 
 * <H3>Additional Notes</H3>
 * To be functional, a subclass must initialize the static data member, 
 * ourInstance, to an instance of the subclass in a static initialization 
 * block.  The static data member is used in the main method, hence the 
 * need for the instance.
 * 
 * <P/>
 * This class handles a number of common "house keeping" behaviors that are
 * common to applications.  This includes:
 * 
 * <UL>
 * <LI/>Having a configuration file that is used to inialize the application
 * <LI/>Command line argument processing
 * <LI/>Creating temporary files and directories
 * </UL>
 * 
 * <H3>Standard Directories</H3>
 * The framework assumes that, when clients want to find files, they want 
 * to look in some "standard locations."  The list of directories defined 
 * by getStandardDirectories defines the locations and the order in which
 * they are searched.
 * 
 * <P/>
 * Subclasses may wish to modify createStandardDirectories and or call 
 * setStandardDirectories if they wish to modify the search locations.
 * Note that setStandardDirectories is not thread safe, so clients should
 * probably synchronize on the instance of the application when making 
 * modifications to ensure that someone else does not modify the list 
 * in between the time when the calls are made.
 * 
 * 
 * @author cnh
 */
public abstract class Application 
{
	/**
	 * Start the application.
	 * 
	 * <H3>Description</H3>
	 * This is called after the startup sequence has completed.
	 * 
	 * @throws ApplicationException If a problem is encountered.
	 */
	public abstract void startApplication() throws ApplicationException;
	
	/**
	 * Signals to the application that is should create a new set of data, as would
	 * be the case when the application is opened without any file selected.
	 * 
	 * @return New, empty application data; or null if the application has no data.
	 * @throws ApplicationException if there is a problem creating the data.
	 */
	public abstract ApplicationData createApplicationData() throws ApplicationException;
	
	public abstract String getApplicationName();
	
	protected static Application ourApplication;
	
	/**
	 * Indicates that the application's data consists of a simple properties
	 * file, suitable for loading via Properties.load
	 * 
	 * <P/>
	 * Return this value from the getAppDataType method to have the startup 
	 * and shutdown methods treat the applications data as a property file.
	 * 
	 * <P/>
	 * This is the default application data type.  The application properties
	 * file is the file that this property indicates.  
	 * 
	 * <P/>
	 * Applications that use this approach cannot load and stored data via 
	 * the getInputStream and getOutputStream methods.
	 */
	public static final int APP_DATA_PROPERTIES_FILE = 0;
	
	/**
	 * Indicates that the application's data is stored in a system of 
	 * directories.
	 * 
	 * <P/>
	 * Return this value from the getAppDataType method to have the startup
	 * and shutdown methods use the application directory approach.  The 
	 * primary affect is that the class will try to get the application 
	 * properties file from the application directory.
	 */
	public static final int APP_DATA_DIRECTORY = 1;
	
	/**
	 * Indicates that the application's data is stored in a zip file.
	 * 
	 * <P/>
	 * Return this value from the getAppDataType method to have the startup 
	 * and shutdown methods use the archive rather than a properties file.
	 * 
	 * <P/>
	 * If the application signals that it wants to use an archive, then 
	 * the class will try to locate the application properties file in 
	 * the archive.
	 */
	public static final int APP_DATA_ARCHIVE = 2;
	
	
	public static final String STR_ARG_APPLICATION_DIRECTORY = "applicationDirectory";
	public static final String STR_SHORT_ARG_APPLICATION_DIRECTORY = "d";
	
	public static final String STR_ARG_INIT_FILE = "initializationFile";
	public static final String STR_SHORT_ARG_INIT_FILE = "i";
	
	public static final String STR_ARG_REPOSITORY = "repository";
	public static final String STR_SHORT_ARG_REPOSITORY = "r";
	
	public static final int ARG_APPLICATION_DIRECTORY = 0;
	public static final int ARG_INIT_FILE = 1;
	public static final int ARG_REPOSITORY = 2;
	
	
	public static final Object[] SPEC_STRING_TO_ARGUMENT = {
		STR_ARG_APPLICATION_DIRECTORY,	new Integer(ARG_APPLICATION_DIRECTORY),
		STR_ARG_INIT_FILE,				new Integer(ARG_INIT_FILE),
		STR_ARG_REPOSITORY,				new Integer(ARG_REPOSITORY)
	};
	
	public static final Object[] SPEC_SHORT_STRING_TO_ARGUMENT = {
		STR_SHORT_ARG_APPLICATION_DIRECTORY, new Integer(ARG_APPLICATION_DIRECTORY),
		STR_SHORT_ARG_INIT_FILE,			 new Integer(ARG_INIT_FILE),
		STR_SHORT_ARG_REPOSITORY,			 new Integer(ARG_REPOSITORY)
	};
	
	protected static StringIntMap ourStrToArgMap = new StringIntMap (SPEC_STRING_TO_ARGUMENT);
	protected static StringIntMap ourShortStrToArgMap = new StringIntMap(SPEC_SHORT_STRING_TO_ARGUMENT);
	
	protected JFileChooser myFileChooser;
	protected CallbackListenerHelper shutdownCallbacks;
	protected DeleteFileCallback deleteFileCallback;
	protected List myStandardDirectories;
	protected ImprovedFile myMasterTempDir;
	protected ApplicationData myApplicationData;
	protected File myPropertyFile;
	protected boolean myRemoveTempFiles = false;
	
	
	public boolean keepTempFiles()
	{
		return !myRemoveTempFiles;
	}
	
	public boolean deleteTempFiles()
	{
		return myRemoveTempFiles;
	}
	
	public void setRemoveTempFiles(boolean removeTempFiles)
	{
		myRemoveTempFiles = removeTempFiles;
	}
	
	
	synchronized public ApplicationData getApplicationData()
	{
		return myApplicationData;
	}
	
	
	synchronized public void checkPoint() throws ApplicationException
	{
		throw new ApplicationException("checkpoints not supported");
	}
	
	public void setApplicationData (ApplicationData data)
	{
		myApplicationData = data;
	}
	
	
	public File getPropertyFile()
	{
		String fileName = getProperty(ApplicationProperties.PROP_PROPERTY_FILE);
		if (null == fileName)
			return null;
		else
			return new File(fileName);
	}
	
	public void setPropertyFile(File propertyFile)
	{
		String fileName = null;
		if (null != propertyFile)
		{
			fileName = propertyFile.toString();
		}
		
		setProperty(ApplicationProperties.PROP_PROPERTY_FILE, fileName);
	}

	protected Properties myCommandLineProperties;
	
	public Properties getCommandLineProperties()
	{
		if (null == myCommandLineProperties)
			myCommandLineProperties = new Properties();
		
		return myCommandLineProperties;
	}
	
	public void setCommandLineProperties(Properties p)
	{
		myCommandLineProperties = p;
	}
	

	protected static StringIntMap ourCodeToKeyMap;
	
	public static String codeToKey (int code)
	{
		if (null == ourCodeToKeyMap)
			return null;
		else
			return ourCodeToKeyMap.intToString(code);
	}
	
	public static void initializeCodeToKeyMap(Object[] spec)
	{
		ourCodeToKeyMap = new StringIntMap(spec);
	}
	
	
	protected String[] myCommandLineArguments;
	
	public String[] getCommandLineArguments()
	{
		return myCommandLineArguments;
	}
	
	public void setCommandLineArguments (String[] argv)
	{
		myCommandLineArguments = argv;
	}
	
	protected Properties myProperties;

	public Properties getProperties()
	{
		if (null == myProperties)
			myProperties = new Properties();
		
		return myProperties;
	}
	
	public void setProperties(Properties properties)
	{
		myProperties = properties;
	}
	
	
	
	
	protected List mySystemRepositories;
	
	public List getSystemRepositories()
	{
		if (null == mySystemRepositories)
			mySystemRepositories = new ArrayList();
		
		return mySystemRepositories;
	}
	public void setSystemRepositories(List mySystemRepositories)
	{
		this.mySystemRepositories = mySystemRepositories;
	}
	
	
	
	public void addShutdownCallback(Callback callback)
	{
		if (null == this.shutdownCallbacks)
			this.shutdownCallbacks = new CallbackListenerHelper();
		
		this.shutdownCallbacks.addListener(callback);
	}
	
	public void removeShutdownCallback (Callback callback)
	{
		if (null == this.shutdownCallbacks)
			return;
		
		this.shutdownCallbacks.removeListener(callback);
	}
	
	public void notifyShutdownListeners()
	{
		if (null == this.shutdownCallbacks)
			return;
		
		this.shutdownCallbacks.fire();
	}
	
	
	public DeleteFileCallback getDeleteFileCallback()
	{
		return this.deleteFileCallback;
	}
	
	public synchronized void initDeleteOnShutdown()
	{
		if (null != this.deleteFileCallback)
			return;
		
		this.deleteFileCallback = new DeleteFileCallback();
		addShutdownCallback(this.deleteFileCallback);
	}
	
	public void deleteOnShutdown(File f)
	{
		if (null == this.deleteFileCallback)
			initDeleteOnShutdown();

		this.deleteFileCallback.addFile(f);
	}
	
	
	/**
	 * Remove temp files if we are shutting down normally.
	 * 
	 * <P>
	 * If the removeTempFiles flag is set to true, then this method will remove
	 * the temp files.  Generally, this flag is set by the {@link #quit()} 
	 * method.
	 * 
	 * @param tempFiles The files to remove.
	 */
	public void deleteTempFiles (Set<ImprovedFile> tempFiles)
	{
		if (keepTempFiles())
			return;
		
		for (ImprovedFile file : tempFiles)
		{
			if (!file.exists())
				continue;
			
			if (!file.isDirectory())
				file.delete();
			else
			{
				try
				{
					file.deleteDirectory();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public boolean doNotDeleteOnShutdown(File f)
	{
		if (null == this.deleteFileCallback)
			initDeleteOnShutdown();
		
		return this.deleteFileCallback.removeFile(f);
	}
	
	
	protected MessageFormatter myFormatter;
	
	public MessageFormatter getFormatter()
	{
		return myFormatter;
	}
	
	
	protected final static String DEFAULT_RESOURCE_NOT_FOUND_ERROR =
		"The application has encountered an internal error.";
	
	
	/**
	 * Get a "message" string, using a default value if the message could not be found.
	 * <H2>NOTE</H2>
	 * This method catches {@link Exception} resulting from
	 * {@link #getMessage(String, Object[])}, and ignores them. This includes things like
	 * {@link NullPointerException}, {@link ArithmeticException}, etc.
	 * <H2>Description</H2>
	 * Look up a message string via {@link #getMessage(String, Object[])}, but return a
	 * default value should that method throw an exception.  The default value is also 
	 * used if getMessage returns a null value.
	 * 
	 * @param defaultValue
	 *        The value to return if getMessage throws an exception or returns a null
	 *        value.
	 * @param key
	 *        The key to lookup.
	 * @param data
	 *        Any data to use in formatting the message.
	 * @return The result of the lookup, or the defaultValue.
	 */
	public String getDefaultMessage (String defaultValue, String key, Object[] data)
	{
		String message;
		
		try
		{
			message = getMessage(key, data);
		}
		catch (Exception e)
		{
			message = defaultValue;
		}
		
		if (null == message)
			message = defaultValue;
		
		return message;
	}
	
	
	public String getDefaultMessage (String defaultValue, String key)
	{
		Object[] data = {};
		
		return getDefaultMessage(defaultValue, key, data);
	}
	
	/**
	 * See {@link #getDefaultMessage(String, String, Object[])}.
	 * 
	 * @param defaultValue value to return if an exception is thrown or the result
	 * would otherwise be null.
	 * @param key Key to look up.
	 * @param o1 Data to use in formatting the message.
	 * @return The formatted message.
	 */
	public String getDefaultMessage (String defaultValue, String key, Object o1)
	{
		Object data = new Object[] { o1 };
		return getDefaultMessage(defaultValue, key, data);
	}
	
	/**
	 * See {@link #getDefaultMessage(String, String, Object[])}.
	 * 
	 * @param defaultValue value to return if an exception is thrown or the result
	 * would otherwise be null.
	 * @param key Key to look up.
	 * @param o1 Data to use in formatting the message.
	 * @param o2 Data to use in formatting the message.
	 * @return The formatted message.
	 */
	public String getDefaultMessage (String defaultValue, String key, Object o1, Object o2)
	{
		Object data = new Object[] { o1, o2 };
		return getDefaultMessage(defaultValue, key, data);
	}
	
	/**
	 * See {@link #getDefaultMessage(String, String, Object[])}.
	 * 
	 * @param defaultValue value to return if an exception is thrown or the result
	 * would otherwise be null.
	 * @param key Key to look up.
	 * @param o1 Data to use in formatting the message.
	 * @param o2 Data to use in formatting the message.
	 * @param o3 Data to use in formatting the message.
	 * @return The formatted message.
	 */
	public String getDefaultMessage (String defaultValue, String key, Object o1, Object o2, Object o3)
	{
		Object data = new Object[] { o1, o2, o3 };
		return getDefaultMessage(defaultValue, key, data);
	}
	
	
	public String getMessage (String key, Object[] data) throws RuntimeApplicationException
	{
		return myFormatter.getMessage(key, data);
	}
	
	
	public String getLookupFailureMessage (String key, Object[] data)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			
			sb.append ("Lookup failed for key = " + key);
			if (null != data && data.length > 0)
			{
				sb.append (" and ");
				for (int i = 0; i < data.length; i++)
				{
					if (i > 0)
						sb.append (", ");
					
					sb.append(data[i]);
				}
			}
			
			return sb.toString();
		}
		catch (RuntimeException e)
		{
			return "Lookup failed for key = " + key;
		}		
	}
	
	
	public String getMessageOneChance (String key, Object[] data)
	{
		try
		{
			return myFormatter.getMessage(key, data);
		}
		catch (RuntimeApplicationException e)
		{
			return getLookupFailureMessage(key, data);
		}
	}
	
	public String getMessage (String key, Object o) throws RuntimeApplicationException
	{
		Object[] data = { o };
		return getMessage(key, data);
	}
	
	public String getMessage (String key) throws RuntimeApplicationException
	{
		return getMessage(key, null);
	}
	
	
	public void initialize()
	{
		ourApplication = this;
		ourExceptionHandler = createExceptionHandler();
		initializeTempFileNames();
	}
	
	/**
	 * Initialize the tempFilePrefix and tempFileSuffix properties so as to preserve 
	 * any values subclasses may have set.
	 * 
	 * <H3>Description</H3>
	 * Basically, if the properties already have non-null values, they are left as is.
	 * Otherwise, the default prefix, "app", and suffix, "tmp", are used.
	 */
	protected void initializeTempFileNames()
	{
		if (null == this.tempFilePrefix)
			this.tempFilePrefix = getApplicationName();
		
		if (null == this.tempFilePrefix)
			this.tempFilePrefix = "app";
		
		if (null == this.tempFileSuffix)
			this.tempFileSuffix = "tmp";
	}
	
	
	public Application ()
	{
		initialize();
	}
	
	public boolean unsavedChanges ()
		throws LTSException
	{
		ApplicationData data = getApplicationData();
		return (null != data && data.isDirty());
	}
	
	
	protected ApplicationRepository myRepository;
	
	public ApplicationRepository getRepository()
	{
		return myRepository;
	}
	
	public void setRepository (ApplicationRepository repository) throws ApplicationException
	{
		myRepository = repository;
		String fname = null;
		if (null != myRepository && null != myRepository.getRepositoryFile())
			fname = myRepository.getRepositoryFile().toString();
		
		String key = ApplicationProperties.PROP_REPOSITORY;
		setProperty(key,fname);
	}
	
	
	public File createTempDir() throws ApplicationException
	{
		return createTempDir(getTempFilePrefix());
	}
	
	
	public File createTempDir (String prefix) throws ApplicationException
	{
		return createTempDir(prefix, getMasterTempDir());
	}
	
	
	public File createTempDir (String prefix, File parentDirectory) throws ApplicationException
	{
		try
		{
			ImprovedFile f = ImprovedFile.createTempDirectory(prefix, null, parentDirectory);
			return f;
		}
		catch (IOException e)
		{
			String msg = ApplicationMessages.ERROR_CREATING_TEMP_DIR;
			throw new ApplicationException(e, msg);
		}
	}
	
	
	/**
	 * Create a directory where all other temp files, etc. can be created.
	 * <H3>Description</H3>
	 * This is part of the application framework startup procedure, see
	 * {@link #initializeApplication(String[])} for other details. Because this operation
	 * is critical to the application's functioning, the method may not throw an
	 * ApplicationException, but must instead throw an ApplicationTerminateException to
	 * signal that the program must stop.
	 * 
	 * @throws ApplicationTerminateException
	 *         This is thrown if the program cannot create a temporary area for files and
	 *         whatnot. If this is thrown, then the program will terminate, though in a
	 *         controlled manner.
	 */
	protected void initializeMasterTempDir () throws ApplicationTerminateException
	{
		try
		{
			if (null == myMasterTempDir)
			{
				String tempDir = System.getProperty("java.io.tmpdir");
				if (null != tempDir)
				{
					File f = new File(tempDir, getApplicationName());
					f.mkdirs();
					if (f.isDirectory())
					{
						myMasterTempDir = IOUtilities.toImprovedFile(f);
					}
				}
				
				if (null == myMasterTempDir)
				{
					File f = IOUtilities.createTempDirectory(getTempFilePrefix());
					myMasterTempDir = IOUtilities.toImprovedFile(f);
					deleteOnShutdown(myMasterTempDir);
				}
			}				
		}
		catch (IOException e)
		{
			String msg = ApplicationMessages.FATAL_CREATING_TEMP_AREA;
			ApplicationException ae = new ApplicationException(e,msg);
			showException(ae);
			
			throw new ApplicationTerminateException();
		}
	}
	
	
	public ImprovedFile getMasterTempDir()
	{
		return myMasterTempDir;
	}
	
	
	public void setMasterTempDir (ImprovedFile dir)
	{
		myMasterTempDir = dir;
	}
	
	public File createTempFile (String prefix, String suffix)
		throws IOException
	{
		File f = File.createTempFile(prefix, suffix, getMasterTempDir());
		return f;
	}
	
	
	public File createTempFile () throws ApplicationException
	{
		try
		{
			File f = File.createTempFile(getTempFilePrefix(), getTempFileSuffix(),
					getMasterTempDir());
			f.deleteOnExit();
			return f;
		}
		catch (IOException e)
		{
			String msg = ApplicationMessages.ERROR_CREATING_TEMP_FILE;
			throw new ApplicationException(e, msg);
		}
	}
	
		
	public void removeTempFiles () throws LTSException
	{
		try
		{
			if (null != myMasterTempDir)
				myMasterTempDir.deleteDirectory(true);
		}
		catch (IOException e)
		{
			throw new LTSException (
				"Error while trying to remove temp directory, "
				+ myMasterTempDir,
				e
			);
		}
	}
	
	
	public static int strToArg (String s)
	{
		return ourStrToArgMap.stringToInt(s);
	}
	
	public static int shortStrToArg (String s)
	{
		return ourShortStrToArgMap.stringToInt(s);
	}
	
	public static String intToArg (int arg)
	{
		return ourStrToArgMap.intToString(arg);
	}
	

	public static Application getInstance()
	{
		return ourApplication;
	}
	
	protected static void setInstance(Application app)
	{
		ourApplication = app;
	}
	
	
	/**
	 * Shutdown the system without saving repositories or asking the user.
	 * 
	 * <H3>Description</H3>
	 * This method causes the system to shutdown in a cotrolled manner as soon as 
	 * it can.  This means storing application properties if possible and removing
	 * any temporary directories, etc.
	 * 
	 * <P>
	 * The method calls {@link System#exit(int)}, so it will not return.  Subclasses
	 * wishing to perform additional cleanup should override this method, do whatever
	 * cleanup is required, and then call this version of the method.
	 * 
	 * <P>
	 * One important aspect of this method is that it does not save repository data.
	 * For that sort of operation, user intervention is really required, so use the 
	 * {@link #quit()} method instead.
	 * 
	 * <P>
	 * A final note is that methods that call this method do not expect it to throw 
	 * exceptions, so care must be taken that any method called from within this 
	 * method checks for {@link RuntimeException}.
	 */
	public void performImmediateShutdown ()
	{
		try
		{
			shutDown(false);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public void processStartupApplicationException (LTSException e)
	{
		processStartupException(e);
	}
	
	/**
	 * This method is called when application startup failed as signaled by an 
	 * ApplicationTerminateException being thrown.  
	 * 
	 * <H3>Description</H3>
	 * If this method is called, it means that the application has decided to terminate.
	 * In that situation, the objective is to leave things in as consistent a state 
	 * as possible.  It can be safely assumed that no data has been modified or the 
	 * like, since any such operation would take place after initialization were 
	 * complete.
	 * 
	 * @param e The terminate exception that caused this method to be called. 
	 */
	public void processStartupTerminateException(ApplicationTerminateException e)
	{
		String msg = DEFAULT_STARTUP_EXCEPTION_MESSAGE;
		showAndDie(e, msg);
	}
	
	
	protected static final String OLD_DEFAULT_STARTUP_EXCEPTION_MESSAGE =
		"The system has encountered an severe error that has prevented it "
		+ "starting up.  This is probably the result of a bug or other "
		+ "problem that is beyond your control.  The details button may "
		+ "provide information that will help in resolving the problem "
		+ "so you may wish to copy the contents of that window and include "
		+ "it in any bug reports, etc.";
	
	protected static final String DEFAULT_STARTUP_EXCEPTION_MESSAGE =
		"The system has encountered a severe, internal error";
	
	public void processStartupException (Throwable t)
	{
		String key = ApplicationMessages.FATAL_STARTUP_RUNTIME_EXCEPTION;
		String defMsg = DEFAULT_STARTUP_EXCEPTION_MESSAGE;
		String msg = getDefaultMessage(defMsg, key);
		showAndDie(t, msg);
	}
	
	/**
	 * This method is called if a RuntimeException is caught during system startup.
	 * 
	 * <H3>Description</H3>
	 * The purpose of this method is to allow subclasses to change how the system 
	 * reacts to such an event occurring.  The default behavior is to issue a generic
	 * error message and terminate.
	 * 
	 * @param e The caught exception.
	 */
	public void processStartupRuntimeException(RuntimeException e)
	{
		processStartupException(e);
	}
	
	
	public void startApplication(String[] argv)
	{
		try
		{
			initializeApplication(argv);
			startApplication();
		} 
		catch (ApplicationException e) 
		{
			processStartupApplicationException(e);
		}
		catch (ApplicationTerminateException e)
		{
			processStartupTerminateException(e);
		}
		catch (RuntimeException e)
		{
			processStartupRuntimeException(e);
		}
	}
	
	
	public String getExitConfirmMessage ()
	{
		return "Are you sure you want to exit the Application?";
	}
	
	public String getSaveBeforeExitMessage ()
	{
		return "Do you want to save your changes before existing?";
	}
	
	
	public static final int QUIT_CONTINUE = 0;
	public static final int QUIT_CANCEL = 1;
	public static final int QUIT_RETRY_SAVE = 2;
	
	/**
	 * Browse for a location to save the application data and return a repository 
	 * for that location.
	 * 
	 * <P>
	 * This method is called when the application wants to save and commit data, 
	 * but does not currently have a repository to save it in.  
	 * 
	 * <P>
	 * By default, this method assumes that the repository lives in a file, so override
	 * if your application uses something else.  If the user changes their mind and 
	 * does not want to save after all, return null.
	 * 
	 * @return An ApplicationRepository where data should be saved or null if the 
	 * user does not want to save after all.
	 */
	public ApplicationRepository browseSaveRepository() throws ApplicationException
	{		
		File file;
		
		File temp = getRepository().getRepositoryFile();
		if (temp.isDirectory())
			file = browseSaveDirectory();
		else
			file = browseSaveFile();
		
		if (null == file)
			return null;
		
		ApplicationRepository repos = openRepository(file);
		return repos;
	}
	
	
	/**
	 * Force a save to a new location and make that new location the repository for 
	 * the application.
	 * 
	 * @return true if the data was saved, false otherwise.
	 */
	public boolean browseSaveData ()
	{
		try
		{
			File file;
			
			ApplicationRepository repos = createRepository();
			File temp = repos.getRepositoryFile();
			if (temp.isDirectory())
				file = browseSaveDirectory();
			else
				file = browseSaveFile();
			
			if (null == file)
				return false;
			
			saveDataAs(file);
			
			return true;
		}
		catch (ApplicationException e)
		{
			showException(e);
			return false;
		}
	}
	
	/**
	 * Save the application data, browsing for a place to save if need be.
	 * 
	 * <H2>Desscription</H2>
	 * This method is called by clients who want the application to save any
	 * application data. The method calls browseSaveRepository if no repository
	 * is currently defined for the application.
	 * 
	 * <P>
	 * If your application does not need to save any data, then override this
	 * method and always return true.
	 * 
	 * <P>
	 * This operation does not throw (checked) exceptions; nor does it directly
	 * throw unchecked exceptions.  If a problem occurs, the intention is for this
	 * method to display an error message and return false.
	 * 
	 * <P>
	 * If you want the application to save the data to a new location, use the 
	 * browseSaveData method instead.
	 * 
	 * @return true if the application data was saved or such an operation is
	 *         not applicable. Return false if the user does not want to save
	 *         the data or an error occurred.
	 * 
	 * @see #browseSaveRepository()
	 */
	public boolean saveData ()
	{
		try
		{
			ApplicationData data = getApplicationData();
			if (null == data)
				return true;
			
			ApplicationRepository repos = getRepository();
			if (null == repos)
			{
				repos = browseSaveRepository();
			}
			
			if (null == repos)
				return false;
			
			File f = repos.getRepositoryFile();
			saveDataAs(f, repos, data);
			return true;
		}
		catch (ApplicationException e)
		{
			showException(e);
			return false;
		}
	}
	
	
	/**
	 * Ask the user if they want to save their changes.
	 * 
	 * <H2>Description</H2>
	 * This method checks to see if the application has any dirty application data.
	 * If it does, then it asks the user if they want to save the data.  If so, the 
	 * method calls saveData.
	 * 
	 * <P>
	 * If the application has no unsaved changes or the user does not want to save
	 * them, then the method simply returns true.
	 * 
	 * @return true if there were no changes to save, or the user did not want to 
	 * save them or the data was saved.  false if a problem was encountered.
	 */
	public boolean promptAndSaveData ()
	{
		boolean result;
		
		try
		{
			ApplicationData data = getApplicationData();
			if (null == data || !data.isDirty())
				result = true;
			else
			{
				String msg = "You have unsaved changes.  Save them now?";
				String title = "Unsaved Changes";
				
				int optionType = JOptionPane.YES_NO_CANCEL_OPTION;
				int decision = JOptionPane.showConfirmDialog(null, msg, title, 
						optionType);
				
				switch (decision)
				{
					case JOptionPane.YES_OPTION :
						result = saveData();
						break;
						
					case JOptionPane.NO_OPTION :
						result = true;
						break;
						
					case JOptionPane.CANCEL_OPTION :
					default :
						result = false;
						break;
				}
			}
		}
		catch (ApplicationException e)
		{
			showException(e);
			result = false;
		}
		catch (RuntimeException e)
		{
			String msg = ApplicationMessages.ERROR_REPOSITORY_WRITE;
			ApplicationException ae = new ApplicationException(e,msg);
			showException(ae);
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Ask the user if they want to save changes before they quit; also give them 
	 * the option to abort the quit.
	 * 
	 * <P>
	 * Display a yes/no/cancel dialog asking the user if they want to save changes 
	 * before quitting the application.  If yes, then save the changes and quit.  If no
	 * then quit without saving.  If cancel then return.
	 * </P>
	 *
	 */
	public void promptAndSaveQuitCancel()
	{
		String msg = "You have unsaved changes.  Save them before quitting?";
		String title = "Save Changes";
		
		int decision = JOptionPane.showConfirmDialog(null, msg, title, 
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		if (JOptionPane.CANCEL_OPTION != decision)
		{
			boolean quitContinue = true;
			
			if (JOptionPane.YES_OPTION == decision)
				quitContinue = saveData();
			
			if (quitContinue)
			{
				shutDown(true);
			}
		}
	}
	
	public boolean isTemporaryRepository (ApplicationRepository repository)
		throws ApplicationException
	{
		File rfile = repository.getRepositoryFile();
		if (null == rfile)
			return true;
		
		File tempdir = getMasterTempDir();
		if (null == tempdir)
			return false;
		
		File parent = rfile.getParentFile();
		if (null == rfile)
			return false;
		
		String rname = parent.getAbsolutePath();
		String tname = tempdir.getAbsolutePath();
		
		return tname.startsWith(rname);
	}
	
	
	public void saveDataAs (File f) throws ApplicationException
	{
		ApplicationData data = getApplicationData();
		if (null == data)
			return;
		
		ApplicationRepository repos = getRepository();
		if (null == repos)
		{
			repos = createRepository();
			setRepository(repos);
		}
		
		saveDataAs(f, repos, data);
	}
	
	
	/**
	 * Save the application data to a particular repository file.
	 * 
	 * <P>
	 * This method takes care of some house-keeping operations such as:
	 * <UL>
	 * <LI>Calling {@link ApplicationRepository#storeApplicationData(ApplicationData)}.
	 * <LI>Clearing the application data dirty flag.
	 * <LI>Setting the application data to the object passed.
	 * <LI>Setting the application repository to the object passed.
	 * <LI>notifying repository listeners of the save.
	 * </UL>
	 * 
	 * @param f The file to save to.  This may be the value that the repository passed
	 * to the method returns.
	 * @param repos The repository to save to.
	 * @param data The data to save.
	 * @throws ApplicationException If an error occurs during the process.
	 */
	public void saveDataAs (File f, ApplicationRepository repos, ApplicationData data)
		throws ApplicationException
	{
		repos.storeApplicationData(data);
		repos.commitAs(f);
		data.setDirty(false);
		setApplicationData(data);
		setRepository(repos);
		
		getRepositoryHelper().saved(repos);
	}
	
	
	/**
	 * Make a request to shut down the application, saving data and giving the 
	 * user a chance to abort the shutdown.
	 * 
	 * <P>
	 * This method takes the following actions:
	 * <UL>
	 * <LI>Call {@link #promptAndSaveData()}.
	 * <LI>Check {@link #approveQuit()} and abort the shutdown if that method 
	 * returns false.
	 * <LI>Call {@link #shutDown()}.
	 */
	public void quit ()
	{
		try
		{
			ApplicationData data = getApplicationData();
			if (null != data && data.isDirty())
				promptAndSaveQuitCancel();
			else
				promptAndQuit();
		}
		catch (ApplicationException e)
		{
			promptErrorAndQuit();
		}
	}

	/**
	 * Ask the user if they want to quit despite encountering an error while trying to
	 * save the data.
	 * <P>
	 * This method is called when a) the application is trying to shut down and b) the
	 * application encounters an exception. Since the user can get into the situation
	 * where they cannot, this method allows the user to quit even though the save failed.
	 * </P>
	 * <P>
	 * Note that, in this situation, the application checkpoint files are not removed,
	 * thus providing some mechanism for recovering changes.
	 * </P>
	 * <P>
	 * If the user opts to quit, this method will not return. It calls {@link #shutDown()}
	 * instead. If the user aborts the quit, then the method returns normally.
	 * </P>
	 */
	protected void promptErrorAndQuit()
	{
		String msg = "Error encountered while trying to save data.  Quit anyways?";
		String title = "Error";
		int decision = JOptionPane.showConfirmDialog(null, msg, title,
				JOptionPane.YES_NO_OPTION);
		
		if (JOptionPane.YES_OPTION == decision)
			shutDown(true);
	}

	public void promptAndQuit()
	{
		String msg = "Quit application?";
		String title = "Quit";
		int decision = JOptionPane.showConfirmDialog(null, msg, title,
				JOptionPane.YES_NO_OPTION);
		
		if (JOptionPane.YES_OPTION == decision)
		{
			shutDown(true);
		}
	}

	/**
	 * Quit the application, saving properties and notifying listeners, but without
	 * checking for unsaved data.
	 */
	public void shutDown(boolean removeTempFiles)
	{
		myRemoveTempFiles = removeTempFiles;
		
		storeApplicationProperties();
		notifyShutdownListeners();
		System.exit(0);
	}
	
	
	/**
	 * This is a code that some methods return to indicate that a quit operation 
	 * should be aborted.
	 * 
	 * <P>
	 * Generally speaking this code is returned when the system has unsaved data,
	 * the user has been asked if they want to discard the data, and the user has
	 * indicated the system should abort the quit.
	 */
	public static final int DECISION_QUIT_CANCEL = 0;
	
	/**
	 * This is a code that some methods return to indicate that a quit operation
	 * should continue.
	 * 
	 * <P>
	 * Generally speaking this code is returned when the system has unsaved data,
	 * the user has been asked if they want to discard the data, and the user has
	 * indicated that the data should be discarded.
	 */
	public static final int DECISION_QUIT_CONTINUE = 1;
	
	/**
	 * This is a code that some methods return to indicate that the system should
	 * try saving data again before continuing with a quit operation.
	 * 
	 * <P>
	 * Generally speaking this code is returned when...
	 * <UL>
	 * <LI>the system has unsaved data
	 * <LI>The user opted to save the data before quitting.
	 * <LI>The save failed.
	 * <LI>The user was queried re: whether to retry the save.
	 * <LI>The user wanted to retry.
	 * </UL>
	 */
	public static final int DECISION_QUIT_RETRY = 2;
	

	/**
	 * The system is in the process of terminating when unsaved data was found: 
	 * ask the user whether to discard the data, cancel the quit or save the 
	 * data.
	 * 
	 * <H2>NOTE</H2>
	 * This method should not throw any exceptions.  Doing so will likely result
	 * in unsaved data being lost.
	 * 
	 * <H2>Description</H2>
	 * This method informs the user that they have unsaved changes and then asks
	 * them if they want to save the data before quitting.  The reason for 
	 * creating a method just to do this is that the method must not throw any 
	 * exceptions.  Doing so is likely to cause the application to "drop" the 
	 * unsaved data.
	 * 
	 * <P>
	 * This method should also return one of the following codes.  Failing to 
	 * do so will probably cause the application to abort the quit; which can
	 * be annoying to the user.
	 * 
	 * <P>
	 * The save codes are defined by the constants whose names are prefixed 
	 * with DECISION_QUIT_  They are:
	 * <UL>
	 * <LI>DECISION_QUIT_CANCEL - abort the quit.
	 * <LI>DECISION_QUIT_CONTINUE - discard the data and quit.
	 * </UL>
	 * 
	 * @return DECISION_QUIT_CANCEL or DECISION_QUIT_CONTINUE.
	 */
	public int askQuitSaveData()
	{
		int decision = DECISION_QUIT_CANCEL;
		
		
		
		return decision;
	}
	

	
	
	public boolean approveQuit ()
	{
		String key = ApplicationMessages.CRIT_QUESTION_QUIT;
		String def = "Quit the application?";
		String msg = getDefaultMessage(def, key);
		
		key = ApplicationMessages.CRIT_QUESTION_QUIT_TITLE;
		def = "Quit Application";
		String title = getDefaultMessage(def, key);
		
		int decision = JOptionPane.showConfirmDialog(null, msg, title,
				JOptionPane.YES_NO_OPTION);
		return JOptionPane.YES_OPTION == decision;
	}

	
	public void processRepositoryArgument (String repositoryFileName)
	{}
	
	
	public File findFile (File directory, String[] fileNames)
	{
		File f = null;
		int i = 0;
		while (null == f && i < fileNames.length)
		{
			File temp = new File(directory, fileNames[i]);
			if (temp.exists())
				f = temp;
				
			i++;
		}
		
		return f;
	}
	
	/**
	 * Create a list of files that are the directories where the system should
	 * look for files.
	 * 
	 * <P/>
	 * Note that the values returned are determined programmatically.  They
	 * include the following locations:
	 * 
	 * <UL>
	 * <LI/>The user's current working directory
	 * <LI/>The user's home directory
	 * </UL>
	 * 
	 * <P/>
	 * This list is determined once, when the method is first called, and then
	 * cached thereafter.  To make a change to the list at run-time, the
	 * application should call getStandardDirectories, make the desired
	 * modifications, and then call setStandardDirectories with the new 
	 * version.
	 * 
	 * @return
	 */
	public List createStandardDirectories ()
	{
		if (null == myStandardDirectories)
		{
			List l = new ArrayList();
			
			String s;
			File f;
			
			s = System.getProperty("user.dir");
			f = new File(s);
			l.add(f);
			
			s = System.getProperty("user.home");
			f = new File(s);
			l.add(f);
			
			myStandardDirectories = l;
		}
		
		return myStandardDirectories;
	}
	
	
	public synchronized List getStandardDirectories ()
	{
		if (null == myStandardDirectories)
			myStandardDirectories = createStandardDirectories();
		
		return myStandardDirectories;
	}
	
	
	public synchronized void setStandardDirectories (List l)
	{
		myStandardDirectories = l;
	}
	
	public synchronized List copyStandardDirectories ()
	{
		List l = new ArrayList(getStandardDirectories());
		return l;
	}
	
	/**
	 * Look in the standard directories for a file.
	 * 
	 * <P/>
	 * The method looks in a sequence of directories for a file with a given
	 * name.  It returns a file object "pointing" to the first match that 
	 * it finds.  If no matches are found, it returns null.
	 * 
	 * <P/> 
	 * The standard locations are files returned by getStandardDirectories.
	 * 
	 * <P/>
	 * Note that this method is thread safe to the extent that it takes a
	 * "snapshot" of the list of standard locations and then searches them.
	 * Someone else 
	 * 
	 * @param fname The file name to look for in the standard locations.
	 *  
	 * @return The first matching file or null if no match was found.
	 */
	public File findFile (String fname)
	{
		File f = null;
		
		List l = copyStandardDirectories();
		Iterator i = l.iterator();
		while (null == f && i.hasNext())
		{
			File dir = (File) i.next();
			File temp = new File(dir, fname);
			if (temp.exists())
				f = temp;
		}
		
		return f;
	}
	
	
	protected String[] myDefaultPropertiesSpec = ApplicationProperties.SPEC_DEFAULT_VALUES;
	
	public String[] getDefaultPropertiesSpec()
	{
		return myDefaultPropertiesSpec;
	}
	
	public void setDefaultPropertiesSpec (String[] spec)
	{
		myDefaultPropertiesSpec = spec;
	}
	
	
	public Properties getDefaultProperties()
	{
		Properties p = new Properties();
		String[] spec = getDefaultPropertiesSpec();
		if (null!= spec)
			p = CollectionUtils.buildProperties(spec);
		
		return p;
	}
	
	
	/**
	 * This what most people would consider to be the name of the application 
	 * properties file.  
	 * 
	 * <P>
	 * For example, if the complete name were /foo/bar/nerts.prop, then the 
	 * short name would be nerts.prop
	 */
	protected String myShortPropertyFileName = ApplicationProperties.DEFAULT_SHORT_PROPERTY_FILE_NAME;

	public String getShortPropertyFileName()
	{
		return myShortPropertyFileName;
	}
	
	public void setShortPropertyFileName (String shortName)
	{
		myShortPropertyFileName = shortName;
	}
	
	
	public File getDefaultPropertyFile()
	{
		String shortName = getShortPropertyFileName();
		String directoryName = System.getProperty("user.home");
		return new File(directoryName, shortName);
	}
	
	
	/**
	 * return the properties file for this application or null if it does not
	 * have one.
	 * 
	 * @return The associated file or null if there is no file.
	 */
	public File findPropertiesFile ()
	{
		//
		// see if the user told us to use a specific properties file from the
		// command line
		//
		File propertyFile = null;
		String pname = ApplicationProperties.PROP_PROPERTY_FILE;
		String pfileName = getCommandLineProperties().getProperty(pname);
		if (null != pfileName)
		{
			File f = new File(pfileName);
			if (f.exists())
				propertyFile = f;
		}
		
		//
		// next try to the current directory.
		//
		if (null == propertyFile)
		{
			String shortName = getShortPropertyFileName();
			String directoryName = System.getProperty("user.dir");
			File f = new File(directoryName, shortName);
			if (f.exists())
				propertyFile = f;
		}
		
		//
		// If that fails, try the user's home directory
		//
		if (null == propertyFile)
		{
			String shortName = getShortPropertyFileName();
			String directoryName = System.getProperty("user.home");
			File f = new File(directoryName, shortName);
			if (f.exists())
				propertyFile = f;
		}
		
		return propertyFile;
	}
	
	/**
	 * Create an instance of Properties that contains the default values for the 
	 * application's properties.
	 * 
	 * <P>
	 * This implementation of the method 
	 * 
	 * @return An instance of Properties.
	 * @throws ApplicationException If an error occurs while trying to create the 
	 * properties.
	 */
	protected Properties createApplicationProperties() throws ApplicationException
	{
		return new Properties();
	}
	
	
	public Properties loadPropertiesFile () throws ApplicationException
	{
		Properties p = createApplicationProperties();
		File propertyFile = findPropertiesFile();

		try
		{
			if (null != propertyFile)
				basicLoadProperties(p, propertyFile);
		} 
		catch (ApplicationException e)
		{
			; // ignore the exception
		}
		
		setPropertyFile(propertyFile);
		return p;
	}
	
	
	/**
	 * Augment a properties file by replacing any properties in the provided
	 * object with values that may be defined in the provided file.
	 * 
	 * <H2>NOTE</H2>
	 * The method does not throw an exception if the provided file name does not
	 * exist. This can happen if this is the first time the application is
	 * executed, since the app has not had a chance to write out a property
	 * file.
	 * 
	 * <H2>Description</H2>
	 * This tries to load properties from the file passed to it. Any properties
	 * that exist in the file replace the values that may exist in the
	 * properties object passed to the method. If a value exists in the provided
	 * properties object but not in the file, however, the existing value is
	 * preseved.
	 * 
	 * @param p
	 *            The properties to augment.
	 * @param propertyFile
	 *            The file to load properties from. If null, the properties
	 *            object is not modified.
	 * 
	 * @throws ApplicationException
	 *             This is thrown if an IOException is encountered while trying
	 *             to read the properties file. In that situation, the message
	 *             will be set to
	 *             {@link ApplicationMessages#ERROR_READING_PROPERTIES}.
	 */
	protected void basicLoadProperties(Properties p, File propertyFile) throws ApplicationException
	{
		if (null == propertyFile)
			return;
			
		FileInputStream fis = null; 
		try
		{
			fis = new FileInputStream(propertyFile);
			p.load(fis);
		}
		catch (FileNotFoundException e)
		{
			// 
			// This is not considered an error
			//
			;
		}
		catch (IOException e)
		{
			String msg = ApplicationMessages.ERROR_READING_PROPERTIES;
			throw new ApplicationException(e, msg, propertyFile.toString());
		}
		finally
		{
			IOUtilities.close(fis);
		}
	}

	
	/**
	 * Show an exception and then ask the user if they want to continue the application.
	 * 
	 * <H3>Description</H3>
	 * This method is used when the application has encountered a problem that would cause
	 * the program to operation with only partial functionality or without some
	 * information it was expecting, such as a file, etc. The method displays the error
	 * and then asks the user if they want to keep running in this situation. The method
	 * returns true if the user wants to continue and false otherwise.
	 * 
	 * @param e
	 *        The error that is causing the problem.
	 * @param message
	 *        The message to display in the prompt asking the user if they want to
	 *        continue.
	 * @return true if the user wants to continue, false otherwise.
	 */
	public boolean showAndAskToContinue (ApplicationException e, String message)
	{
		showException(e);
		
		int decision = JOptionPane.showConfirmDialog(null, message);
		return JOptionPane.YES_OPTION == decision;
	}
	
	
	/**
	 * Load the application properties files into the System properties.
	 * 
	 * <H2>Description</H2>
	 * This method tries to load properties from a file whose name is defined
	 * by getPropertyFileName().  It looks for said file 
	 * in the following locations:
	 * 
	 * <UL>
	 * <LI>Application.PROP_PROPERTY_FILE_FSPEC
	 * <LI>user.dir
	 * <LI>user.home
	 * </UL>
	 * 
	 * <P>
	 * If a propery file is found it is used to augment the values in the 
	 * system properties.  That is the contents of the properties file is 
	 * used in Properties.putAll against the properties returned by 
	 * System.getProperties, and the result of that is used as the argument
	 * to System.setProperties.
	 * 
	 * @throws LTSException This exception is thrown by this method if an 
	 * error is encountered while trying to read the properties file.
	 */
	public void loadApplicationProperties() throws ApplicationTerminateException
	{
		try
		{
			basicBuildAppProperties();
		}
		catch (ApplicationException e)
		{
			showException(e);
			throw new ApplicationTerminateException();
		}
	}

	private void basicBuildAppProperties() throws ApplicationException
	{
		//
		// first construct the system default properties
		//
		Properties props = getDefaultProperties();
		
		//
		// next, overwrite the defaults with the contents of the properties
		// file (assuming there is one)
		//
		Properties temp = loadPropertiesFile();
		props.putAll(temp);
		
		//
		// next, overwrite the current properties with the values defined on 
		// the command line.
		//
		temp = getCommandLineProperties();
		props.putAll(temp);
		
		//
		// finally, resolve references in properties to other properties
		//
		try
		{
			PropertiesUtil.resolveReferences(props);
		}
		catch (LTSException e)
		{
			String msg = ApplicationMessages.ERROR_PROPERTIES;
			ApplicationException ae = new ApplicationException(e,msg);
			showException(ae);
		}
		
		setProperties(props);
	}
		
	
	protected static final String DEFAULT_ERROR_SAVING_PROPERTIES =
		"The system encountered an error trying to save the system settings.";
	
	protected static final String DEFAULT_ERROR_TITLE =
		"Error";
	
	
	protected void storeApplicationProperties()
	{
		try
		{
			File file = findPropertiesFile();
			if (null == file)
				file = getDefaultPropertiesFile();
			
			if (null == file)
				return;
			
			Properties p = getProperties();
			List<String> list = sortProperties(p);
			writePropertiesFile(file, p, list);
		}
		catch (Exception e)
		{
			String key = ApplicationMessages.CRIT_ERROR_SAVING_PROPERTIES;
			String def = DEFAULT_ERROR_SAVING_PROPERTIES;
			String msg = getDefaultMessage(def, key);
			
			key = ApplicationMessages.CRIT_ERROR_SAVING_PROPERTIES_TITLE;
			def = DEFAULT_ERROR_TITLE;
			String title = getDefaultMessage(def, key);
			
			JOptionPane.showMessageDialog(null, msg, title, JOptionPane.OK_OPTION);
		}
	}

	protected File getDefaultPropertiesFile()
	{
		String dirName = System.getProperty("user.home");
		File file = new File(dirName, getShortPropertyFileName());
		return file;
	}

	protected void writePropertiesFile(File file, Properties p, List<String> list)
			throws FileNotFoundException
	{
		PrintWriter out = null;
		
		try
		{
			out = new PrintWriter(file);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
			out.println("# " + sdf.format(System.currentTimeMillis()));
			for (String name : list)
			{
				String value = p.getProperty(name);
				value = escapePathNames(value);
				out.println(name + "=" + value);
			}
		}
		finally
		{
			IOUtilities.close(out);
		}
	}

	protected List<String> sortProperties(Properties p)
	{
		List<String> list = new ArrayList<String>();
		
		for (Object o : p.keySet())
		{
			String s = (String) o;
			list.add(s);
		}			
		Collections.sort(list, StringUtils.caselessComparator);
		
		return list;
	}

	private String escapePathNames(String value)
	{
		if (value.contains("\\"))
		{
			char[] ca = value.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (char c : ca)
			{
				if (c == '\\')
				{
					sb.append('\\');
				}
				
				sb.append(c);
			}
			value = sb.toString();
		}
		return value;
	}
	
	
	public File getFileProperty(String name)
	{
		String fname = getProperties().getProperty(name);
		File f = null;
		if (null != fname)
		{
			f = new File(fname);
			if (!f.exists())
				f = null;
		}
		
		return f;
	}
	
	public File getDirectoryProperty (String name)
	{
		File d = getFileProperty(name);
		
		if (null != d && d.isDirectory())
			return d;
		else
			return null;
	}
	
	public void setFileProperty (String name, File f)
	{
		if (null == f)
			removeProperty(name);
		else
		{
			String s = f.toString();
			setProperty(name, s);
		}
	}
	
	public String getProperty (String name)
	{
		return getProperties().getProperty(name);
	}
	
	public void removeProperty (String name)
	{
		getProperties().remove(name);
	}
	
	public void setProperty (String name, String value)
	{
		if (null == value)
			getProperties().remove(name);
		else
			getProperties().setProperty(name, value);
	}
	
	public static void setAppProperty(String name, String value)
	{
		getInstance().setProperty(name, value);
	}
	

	public Properties createCommandLine (String[] argv)
	{
		Properties p = new Properties();
		
		return p;
	}
	
	
	/**
	 * Load the application repository that was in use the last time the application 
	 * was running.
	 * 
	 * <H3>Description</H3>
	 * This method is called as part of the startup procedure for the class.  Subclasses
	 * that want to change the way that repository loading should override this method.
	 * 
	 * <P>
	 * The method should attempt to find the repository that was being used in the last
	 * "session" the application was running and then to open it.  If there is some 
	 * problem with loading the application repository, the method should create an 
	 * empty repository instead.
	 * 
	 * <P>
	 * Generally, the previous repository is defined by the property, 
	 * {@link ApplicationProperties#PROP_REPOSITORY}, which should contain the absolute
	 * file name of the file that identifies the repository.
	 * 
	 * <P>
	 * Generally speaking, this method should not throw an exception.  Doing so will 
	 * cause the program to terminate without displaying any messages, etc.  
	 */
	public void loadRepository () throws ApplicationTerminateException
	{
		String fileName = getProperty(ApplicationProperties.PROP_REPOSITORY);
		if (null != fileName)
		{
			try
			{
				File repositoryFile = new File(fileName);
				if (repositoryFile.exists() && repositoryFile.length() > 0)
				{
					loadRepository(repositoryFile);
				}
				else
				{
					fileName = null;
				}
			}
			catch (ApplicationException e)
			{
				ApplicationException exception = 
					new ApplicationException(e, "error processing file " + fileName);
				showException(exception);
				fileName = null;
			}
		}
		
		// 
		// Have no previous repository or it does not exist anymore --- use a default
		// repository.
		//
		if (null == fileName)
		{
			defaultApplicationRepository();
		}
	}

	/**
	 * Create an empty repository and empty application data.
	 * 
	 * <P>
	 * This method is called when there is no repository for the application, 
	 * for example when the application is first started or if the user selects 
	 * "File>New" to create a new set of data.
	 * 
	 * <P>
	 * This is a critical method whose failure will cause the application to 
	 * terminate.  Therefore, if the method encounters an exception it displays
	 * a message and terminates the application rather than re-throwing it.
	 * 
	 * <P>
	 * The method takes care of notifying clients that a new repository has been 
	 * "loaded."
	 */
	protected void defaultApplicationRepository()
	{
		try
		{
			ApplicationRepository repos = createRepository();
			setRepository(repos);
			ApplicationData data = createApplicationData();
			setApplicationData(data);
			if (null != data)
				data.setDirty(false);
			
			int event = RepositoryListenerHelper.EVENT_LOADED;
			getRepositoryHelper().fire(event, repos);
		}
		catch (ApplicationException e)
		{
			showAndDie(e);
		}
	}
	
	
	public boolean dataIsDirty () throws ApplicationException
	{
		ApplicationData data = getApplicationData();
		return (null != data && data.isDirty());
	}
	
	public boolean loadRepository(File repositoryFile) throws ApplicationException
	{
		if (null == repositoryFile || !repositoryFile.exists())
			return false;
		
		long start = System.currentTimeMillis();
		ApplicationRepository repos = openRepository(repositoryFile);
		ApplicationData data = repos.getApplicationData();
		if (null == data)
			return false;
		
		// data.postDeserialize();
		setRepository(repos);
		setApplicationData(data);
		data.setDirty(false);
		
		long finish = System.currentTimeMillis();
		long duration = finish - start;
		String value = Long.toString(duration);
		setProperty (ApplicationProperties.PROP_LOAD_TIME_MILLIS, value);
		
		int event = RepositoryListenerHelper.EVENT_LOADED;
		getRepositoryHelper().fire(event, repos);
		
		return true;
	}
	


	/**
	 * This method performs some common start up processing for all applications.
	 * 
	 * <P>
	 * At present, "common processing" includes the following:
	 * </P>
	 * 
	 * <UL>
	 * <LI>setLookAndFeel</LI>
	 * <LI>{@link #setLookAndFeel()}</LI>
	 * <LI>{@link #processCommandLine(String[])}</LI>
	 * <LI>{@link #loadApplicationProperties()}</LI>
	 * <LI>{@link #initializeMasterTempDir()}</LI>
	 * <LI>{@link #createMessageFormatter()}</LI>
	 * <LI>{@link #cleanupRepositories()}</LI>
	 * <LI>{@link #loadRepository()}</LI>
	 * </UL>
	 * 
	 * Note that this method skips resource bundle initialization.  See the package
	 * and class details for how to re-enable it.
	 * 
	 * @param argv
	 * @throws ApplicationTerminateException
	 */
	public void initializeApplication (String[] argv)
		throws ApplicationTerminateException
	{
		try
		{
			setLookAndFeel();
			processCommandLine(argv);
			loadApplicationProperties();
			initializeMasterTempDir();
			initializeResourceBundles();
			cleanupRepositories();
			loadRepository();
		}
		catch (ApplicationTerminateException e)
		{
			processStartupTerminateException(e);
		}
		catch (RuntimeException e)
		{
			processStartupRuntimeException(e);
		}
	}
	
	
	protected void initializeResourceBundles()
	{
		myFormatter = new DefaultMessageFormatter();
	}
	
	protected static ApplicationExceptionHandler ourExceptionHandler;
	
	public static void setExceptionHandler (ApplicationExceptionHandler theHandler)
	{
		if (null == theHandler)
			throw new NullPointerException();
		
		ourExceptionHandler = theHandler;
	}
	
	public static ApplicationExceptionHandler getExceptionHandler()
	{
		return ourExceptionHandler;
	}
	
	public ApplicationExceptionHandler createExceptionHandler()
	{
		return new SimpleExceptionHandler();
	}
	
	public static void showException (String message, Throwable throwable)
	{
		ourExceptionHandler.processException(false, message, throwable);
	}
	
	public static void showException (Throwable throwable)
	{
		ourExceptionHandler.processException(false, "Exception", throwable);
	}
	
	public static int showAndAsk(String message, Throwable throwable, int mode)
	{
		return ourExceptionHandler.showAndAsk(message, throwable, mode);
	}
	
	
	public void showAndDie (Throwable t, String message)
	{
		ourExceptionHandler.processException(true, message, t);
		System.exit(1);
	}
	
	public void showAndDie (Throwable t)
	{
		String msg = 
			"The application encountered a critical error and must now terminate.";
		
		showAndDie (t, msg);
	}
	
	public void showError (Throwable t, String code)
	{
		ApplicationException e = new ApplicationException(t, code);
		showException(e);
	}
	
	/**
	 * Process the command line options to the program.
	 * 
	 * <H3>Description</H3>
	 * This method uses the {@link CommandLinePolicy} class to process the command
	 * line arguments to the program.  Assuming that said processing is successful,
	 * the method takes the properties obtained from the processing and puts them 
	 * in the application properties.
	 * 
	 * <P>
	 * If command line processing fails, the method asks the user if they want to 
	 * continue or not.  If the user wants to continue anyway, the method returns
	 * as if no problems were encountered.  If the user declines, the method throws
	 * an {@link ApplicationTerminateException} to signal that the application should
	 * quit.
	 * 
	 * @param argv
	 * @throws LTSException
	 */
	protected void processCommandLine(String[] argv) throws ApplicationTerminateException
	{
		try
		{
			setCommandLineArguments(argv);
			CommandLineProcessor clp = getCommandLineProcessor();
			Properties p = clp.processArguments(argv);
			setCommandLineProperties(p);
		}
		catch (ApplicationException e)
		{
			processCommandLineException(e);
		}
	}

	/**
	 * Get the application's command line processor.
	 * 
	 * <P>
	 * The default implementation of this method returns an instance of 
	 * {@link SimpleCommandLineProcessor}.
	 * @return
	 */
	protected CommandLineProcessor getCommandLineProcessor()
	{
		return new SimpleCommandLineProcessor();
	}

	protected void processCommandLineException(ApplicationException e) throws ApplicationTerminateException
	{
		showException(e);
		
		String key = ApplicationMessages.PROMPT_STARTUP_CMDLINE_ERROR;
		String message;
		
		try
		{
			message = getMessage(key);
		}
		catch (RuntimeApplicationException e2)
		{
			showException(e);
			throw new ApplicationTerminateException();
		}
		
		int respose = JOptionPane.showConfirmDialog(null,message);
		if (JOptionPane.YES_OPTION != respose)
			throw new ApplicationTerminateException();
	}

	public File getLastDirectory()
	{
		String name = ApplicationProperties.PROP_LAST_DIRECTORY;
		return getDirectoryProperty(name);
	}

	public void setLastDirectory (File d)
	{
		String name = ApplicationProperties.PROP_LAST_DIRECTORY;
		setFileProperty(name, d);
	}
	
	
	public File getLastFile()
	{
		String name = ApplicationProperties.PROP_LAST_FILE;
		return getFileProperty(name);
	}
	
	public void setLastFile (File f)
	{
		String name = ApplicationProperties.PROP_LAST_FILE;
		setFileProperty(name, f);
	}
	

	public JFileChooser getFileChooser()
	{
		if (null == myFileChooser)
		{
			myFileChooser = new JFileChooser();
			
			File f = getLastDirectory();
			if (null != f)
				myFileChooser.setCurrentDirectory(f);
		}
		
		return myFileChooser;
	}
	
	
	public File browseFiles (
		Component comp,
		boolean openFile,
		int mode,
		File startingDirectory,
		File startingFile 
	)
	{
		File f = null;
		int result;
		
		do {
			JFileChooser jfc = getFileChooser();
			jfc.setFileSelectionMode(mode);
			
			if (null != startingDirectory)
				jfc.setCurrentDirectory(startingDirectory);
			
			if (null != startingFile)
				jfc.setSelectedFile(startingFile);
				
			result = JFileChooser.CANCEL_OPTION;
			
			if (openFile)
				result = jfc.showOpenDialog(comp);
			else 
				result = jfc.showSaveDialog(comp);
			
			if (JFileChooser.CANCEL_OPTION == result)
				break;
			
			if (JFileChooser.APPROVE_OPTION == result)
			{
				f = jfc.getSelectedFile();
				if (f.exists() && !openFile)
				{
					String msg =
						"The file already exists, overwrite it?";
					
					int ans = JOptionPane.showConfirmDialog(null, msg);
					if (JOptionPane.YES_OPTION != ans)
					{
						result = JFileChooser.CANCEL_OPTION;
						continue;
					}
				}
				setLastDirectory(f.getParentFile());
				setLastFile(f);
			}
		} while (result != JFileChooser.APPROVE_OPTION);
		
		return f;
	}
	
	
	public File browseOpenFile (Component comp)
	{
		return browseFiles (comp, true, JFileChooser.FILES_ONLY, getLastDirectory(), getLastFile());
	}
	
	
	public File browseOpenFile ()
	{
		return browseOpenFile (null);
	}
	
	
	public File browseOpenFileOrDirectory (Component comp)
	{
		return browseFiles (comp, true, JFileChooser.FILES_AND_DIRECTORIES, getLastDirectory(), getLastFile());
	}
	
	public File browseOpenFileOrDirectory ()
	{
		return browseOpenFileOrDirectory(null);
	}
	
	
	public File browseOpenDirectory (Component comp)
	{
		return browseFiles (comp, true, JFileChooser.DIRECTORIES_ONLY, null, null);
	}
	
	public File browseOpenDirectory ()
	{
		return browseFiles (null, true, JFileChooser.DIRECTORIES_ONLY, null, null);
	}
	
	public File browseOpenDirectory (File dir)
	{
		return browseFiles(null, true, JFileChooser.DIRECTORIES_ONLY, dir, null);
	}
	
	
	/**
	 * The equivalent to most application's "/File/Open" method.
	 * 
	 * <P>
	 * This method performs the following actions:
	 * <UL>
	 * <LI>Check for unsaved data via {@link #saveData()}.
	 * <LI>Open a file via {@link #browseOpenFile()}.
	 * <LI>Load the data via {@link #loadRepository(File)}.
	 * </UL>
	 * 
	 * <P>
	 * At each step, the method takes the action you would expect for errors, 
	 * cancels, etc.  Specifically:
	 * <UL>
	 * <LI>If saveData errors, the open aborts.
	 * <LI>If the user cancels during saveData, the open aborts.
	 * <LI>If browseOpenFile errors, the open aborts.
	 * <LI>If the user cancels during browseOpenFile, the open aborts.
	 * <LI>If loadRepository errors, the method displays an error and calls
	 * {@link #defaultApplicationRepository()}.
	 * </UL>
	 */
	public void browseOpenRepository ()
	{
		try
		{
			if (!promptAndSaveData())
				return;
			
			File file;
			
			if (getRepository().repositoryUsesDirectories())
				file = browseOpenDirectory();
			else
				file = browseOpenFile();

			if (null == file)
				return;
			
			loadRepository(file);		
		}
		catch (ApplicationException e)
		{
			showException(e);
			defaultApplicationRepository();
		}
	}
	
	
	public File browseSaveFile (Component comp)
	{
		return browseFiles (comp, false, JFileChooser.FILES_ONLY, null, null);
	}
	
	public File browseSaveFile ()
	{
		return browseSaveFile (null);
	}
	
	
	public File browseSaveFileOrDirectory (Component comp)
	{
		return browseFiles (comp, false, JFileChooser.FILES_AND_DIRECTORIES, null, null);
	}
	
	public File browseSaveFileOrDirectory ()
	{
		return browseSaveFileOrDirectory (null);
	}
	
	
	public File browseSaveDirectory (Component comp)
	{
		return browseFiles (comp, false, JFileChooser.DIRECTORIES_ONLY, null, null);
	}
	
	public File browseSaveDirectory ()
	{
		return browseSaveDirectory (null);
	}
	
		
	public void resetApplication ()
	{
		setMasterTempDir(null);
		setLastDirectory(null);
	}
	
	
	protected String tempFilePrefix;
	
	public String getTempFilePrefix()
	{
		return this.tempFilePrefix;
	}
	
	public void setTempFilePrefix (String tempFilePrefix)
	{
		this.tempFilePrefix = tempFilePrefix;
	}
	
	
	protected String tempFileSuffix = "";
	
	public String getTempFileSuffix()
	{
		return this.tempFileSuffix;
	}
	
	public void setTempFileSuffix(String tempFileSuffix)
	{
		this.tempFileSuffix = tempFileSuffix;
	}
	
	
	protected RepositoryListenerHelper myRepositoryHelper;

	public RepositoryListenerHelper getRepositoryHelper()
	{
		if (null == myRepositoryHelper)
			myRepositoryHelper = new RepositoryListenerHelper();
		
		return myRepositoryHelper;
	}
	
	public void addRepositoryListener (RepositoryListener listener)
	{
		getRepositoryHelper().addListener(listener);
	}
	
	public void removeRepositoryListener (RepositoryListener listener)
	{
		getRepositoryHelper().removeListener(listener);
	}
	
	private static final String PLAF_WINDOWS = 
		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public static final String[] LAF_DEFAULT_LOCATIONS = {
			"/resources/swing/swing.properties",
			"/resources/swing.properties",
			"/swing.properties"
		};
	
	public static void setWindowsLookAndFeel() throws ApplicationException
	{	
		try
		{
			UIManager.setLookAndFeel(PLAF_WINDOWS);
		}
		catch (RuntimeException e)
		{
			throw (RuntimeException) e;
		}
		catch (Exception e)
		{
			throw new ApplicationException (
				e,
				ApplicationMessages.ERROR_SETTING_LOOK_AND_FEEL,
				PLAF_WINDOWS
			);
		}
	}
	
	
	public void loadApplciationData (File f) throws ApplicationException
	{
		ApplicationRepository repos = openRepository(f);
		ApplicationData data = repos.getApplicationData();
		
		setRepository(repos);
		setApplicationData(data);
	}
	
	
	public ApplicationRepository createRepository () throws ApplicationException
	{
		File tempdir = getMasterTempDir();
		File zipfile = createTempFile();
		return createRepository(zipfile, tempdir);
	}
	
	
	public ApplicationRepository createRepository(File zipfile, File tempdir, boolean createBackups) throws ApplicationException
	{
		SimpleZipRepository szr = new SimpleZipRepository(zipfile, tempdir, createBackups);
		
		byte[] temp = new byte[256];
		Random r = new Random();
		r.nextBytes(temp);
		OutputStream ostream = null;
		try
		{
			ostream = szr.getOutputStream("/test", false);
			ostream.write(temp);
		}
		catch (Exception e)
		{
			showException(e);
		}
		finally
		{
			IOUtilities.close(ostream);
		}
		
		szr.commit();
		return szr;
	}
	
	
	public ApplicationRepository createRepository(File zipfile, File tempdir) throws ApplicationException
	{
		return createRepository(zipfile, tempdir, true);
	}

	
	public File getRepositoryDir() throws ApplicationException
	{
		File baseDir = new File(getMasterTempDir(), "repos");
		if (!baseDir.isDirectory() && !baseDir.mkdirs())
		{
			String message = 
				"Could not create repository temp dir, "
				+ baseDir;
			
			throw new ApplicationException(message);
		}
		
		return baseDir;
	}
	
	
	public File createRepositoryTempDir() throws ApplicationException
	{
		return createTempDir(getTempFilePrefix(), getRepositoryDir());
	}
	
	
	public ApplicationRepository openRepository(File f) throws ApplicationException
	{
		File tempdir = createRepositoryTempDir();
		deleteOnShutdown(tempdir);
		return createRepository(f, tempdir);
	}

	
	/**
	 * Set the application repository based on interactions with the user.
	 * 
	 * <H2>Description</H2>
	 * This method performs a number of steps by calling methods:
	 * <UL>
	 * <LI>promptAndSaveData to save any existing data.
	 * <LI>browseOpenFile to get the repository file.
	 * <LI>openRepository with the obtained file to get the repository.
	 * <LI>getApplicationData to get the new data from the repository.
	 * <LI>setApplicationData to set the new data.
	 * </UL>
	 * 
	 * <P>
	 * Generally speaking, if a step fails, as might happen if the user cancels
	 * the save of the existing data, the method stops at that point and returns null.
	 * 
	 * @return The new application data.
	 * @throws ApplicationException If an error is encountered.
	 */
	public ApplicationData resetRepository () throws ApplicationException
	{
		boolean saved = promptAndSaveData();
		if (!saved)
			return null;
		
		File file = browseOpenFile();
		if (null == file)
			return null;
		
		ApplicationRepository repository = openRepository(file);
		ApplicationData data = repository.getApplicationData();
		setApplicationData(data);
		
		return data;
	}
	
	/**
	 * Create a new, empty repository; checking for unsaved data before doing so.
	 *
	 * <P>
	 * This method performs the following actions:
	 * 
	 * <UL>
	 * <LI>Call {@link #promptAndSaveData()}.
	 * <LI>If that method returned true, call {@link #defaultApplicationRepository()}
	 * </UL>
	 */
	public void createNew ()
	{
		boolean saved = promptAndSaveData();
		if (!saved)
			return;
		
		defaultApplicationRepository();
	}
	
	
	/**
	 * Wrap an ActionListener in a try...catch block.
	 * 
	 * <P>
	 * This method is basically a hook to allow subclasses to define an exception handling
	 * policy for the situations where {@link RuntimeException} is thrown from an 
	 * {@link ActionListener}.  The default method uses {@link ApplicationActionWrapper}
	 * to catch and display unchecked exceptions.
	 * 
	 * <P>
	 * The idea behind this method is to call {@link ActionListener#actionPerformed(java.awt.event.ActionEvent)}
	 * inside a try...catch block so that RuntimeExceptions such as NullPointerException are 
	 * caught and displayed, rather than being spewed to the standard error.
	 * 
	 * <P>
	 * Subclasses that want to override this method should check the listener to ensure that
	 * they do not create multiple layers of wrappers --- that is, if the listener is 
	 * an instance of WrappedActionListener, then do not wrap it again.
	 *  
	 * @param listener The ActionListener to wrap.
	 * @return A wrapped ActionListener.
	 * @see ApplicationActionWrapper
	 * @see WrappedActionListener
	 */
	public ActionListener wrapListener(ActionListener listener)
	{
		if (listener instanceof ActionListener)
			return listener;
		
		ActionListener newListener = new ApplicationActionWrapper(listener);
		return newListener;
	}

	protected void cleanupRepositories() throws ApplicationTerminateException
	{
		try
		{
			File[] tempfiles = getRepositoryDir().listFiles();
			if (null == tempfiles || tempfiles.length < 1)
			{
				return;
			}

			for (int i = 0; i < tempfiles.length; i++)
			{
				if (!tempfiles[i].exists())
					continue;

				if (tempfiles[i].isFile())
				{
					if (!tempfiles[i].delete())
					{
						String msg =
							"Could not remove the file, " + tempfiles[i];
						throw new ApplicationException(msg);
					}
				}
				else if (tempfiles[i].isDirectory())
				{
					ImprovedFile ifile = new ImprovedFile(tempfiles[i]);
					ifile.deleteDirectory(true);
				}
				else
				{
					String msg = "The file, " + tempfiles[i]
							+ ", is neither a directory nor " + "a regular file.";
					throw new ApplicationException(msg);
				}
			}
		}
		catch (Exception e)
		{
			String msg = "Error during repository cleanup.\nContinue startup?";
			int mode = ApplicationExceptionHandler.MODE_YES_NO_DETAILS;
			int result = showAndAsk(msg, e, mode);

			if (ApplicationExceptionHandler.RESULT_YES != result)
			{
				throw new ApplicationTerminateException();
			}

			return;
		}
	}

	/**
	 * Return the resource locations to search for the look and feel.
	 * 
	 * <P>
	 * This method returns an array of strings that are the locations that will
	 * be used as arguments to {@link Class#getResourceAsStream(String)} in order
	 * to try and find a matching properties file.
	 * 
	 * <P>
	 * See {@link #setLookAndFeel()} for details.
	 * 
	 * @see #setLookAndFeel()
	 */
	public String[] lafGetResourceNames()
	{
		return LAF_DEFAULT_LOCATIONS;
	}

	/**
	 * Try to find the "swing.defaultlaf" property in a particular resource.
	 * 
	 * <P>
	 * This method performs the following actions, returning null if any step
	 * fails:
	 * <UL>
	 * <LI>Find a resource via {@link Class#getResourceAsStream(String)}.
	 * <LI>Create a properties object from the resource.
	 * <LI>Find and return the value of the "swing.defaultlaf" property
	 * </UL>
	 * 
	 * @param name Name of the resource to search.
	 * @return Value of the property, or null if the property was not found.
	 */
	protected String lafCheckResourceName(String name)
	{
		InputStream istream = null;
		Properties props = new Properties();
		
		try
		{
			istream = getClass().getResourceAsStream(name);
			if (null == istream)
				return null;
			
			props.load(istream);
			return props.getProperty("swing.defaultlaf");
		}
		catch (Exception e)
		{
			Application.showException(e);
			return null;
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}

	/**
	 * Try to set the UIManager look and feel.
	 * 
	 * <P>
	 * The method calls {@link UIManager#setLookAndFeel(String)}.  If this fails,
	 * the method displays the resulting exception and returns false.  Otherwise
	 * it returns true.
	 * 
	 * @param className Name of the class to use in the call to setLookAndFeel
	 * @return true if the call succeeds, false otherwise.
	 */
	protected boolean lafSetLookAndFeel(String className)
	{
		try
		{
			UIManager.setLookAndFeel(className);
			return true;
		}
		catch (Exception e)
		{
			Application.showException(e);
			return false;
		}
	}

	/**
	 * Set the look and feel for the application by checking for a properties file.
	 * 
	 * <P>
	 * This method tries to set the UI look and feel by looking up a properties file
	 * located in one of several "well defined" locations.
	 * 
	 * <P>
	 * The method uses {@link Class#getResourceAsStream(String)} to try and find the
	 * resource (properties file) "swing.properties" in the following locations:
	 * <UL>
	 * <LI>/resources/swing/swing.properties
	 * <LI>/resources/swing.properties
	 * <LI>/swing.properties
	 * </UL>
	 * 
	 * <P>
	 * For each of the resource names specified, the method does the following:
	 * <UL>
	 * <LI>try to get the name of the class to use in a call to 
	 * {@link UIManager#setLookAndFeel(String)} by calling 
	 * {@link #lafCheckResourceName(String)}.
	 * 
	 * <LI>If lafCheckResourceName returns a value, then that string returned is 
	 * deemed to be the name of a class that should be used as the new nook and 
	 * to be used in a call to {@link UIManager#setLookAndFeel(String)}.
	 * 
	 * If the call to the UIManager succeeds, then the method stops.  If the call
	 * fails, then the process tries again with the next location.
	 */
	protected void setLookAndFeel()
	{
		String[] locations = lafGetResourceNames();
		if (null == locations)
			return;
		
		for (int i = 0; i < locations.length; i++)
		{
			String className = lafCheckResourceName(locations[i]);
			if (null != className)
				if (lafSetLookAndFeel(className))
					break;
		}
	}
	
	
	static public String message (String key, Object... data)
	{
		return getInstance().myFormatter.formatMessage(key, data);
	}

	public static String getAppProperty(String name)
	{
		return getInstance().getProperty(name);
	}
	
	
}
