//  Copyright 2006, Clark N. Hobbie
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
package com.lts.application;

import java.awt.Window;

import com.lts.LTSException;

/**
 * Defines a basic set of application errors that can occur.
 * 
 * <H2>Description</H2>
 * This class is intended to be used with the Application.getMessage methods 
 * to look up and format various informational and error messages.  The constants
 * defined here should be used as the key for lookup from a resource bundle.  The 
 * result should be used as the format string to create the message.
 * 
 * <P>
 * The key values have the following format:
 * <CODE>
 * <PRE>
 *     public static final String &lt;constant name&gt; = 
 *         &lt;class name&gt;.&lt;type&gt;.&lt;message name&gt;;
 * </PRE>
 * </CODE>
 * 
 * <P>
 * For example:
 * <CODE>
 * <PRE>
 *     public static final String ERROR_LOOKUP_FAILED = 
 *         "com.lts.application.error.lookupFailed";
 * </PRE>
 * </CODE>
 * 
 * <H3>Class Name</H3>
 * A fully-qualified class name is used as the prefix for the value so that clients
 * can more easily avoid key conflicts.
 * 
 * <H3>Message Type</H3>
 * <P>
 * The application framework defines the following message types:
 * <UL>
 * 	<LI>prompt
 * 	<LI>info
 * 	<LI>warning
 * 	<LI>error
 * 	<LI>fatal
 * 	<LI>critical
 * </UL>
 * 
 * <P>
 * A prompt is a message to displaly to the user when asking for input. For
 * example: "How old are you?".
 * 
 * <P>
 * Info messages are used when displaying information to the user. For example:
 * "The current time is {0}".
 * 
 * <P>
 * A warning is some sort of minor error message.  For example: "You really need
 * to lose some weight".
 * 
 * <P>
 * An error message indicates that something happend that prevents the application
 * from fulfilling a request from the user.  For example: "The file, {0}, is
 * corrupted."
 * 
 * <P>
 * A fatal error indicates a situation so severe that the application is going to
 * terminate in response to it.  For example: "Could not create temp file area,
 * application will terminate." 
 * 
 * <P>
 * The critical message type indicates a situation where the application must
 * terminate and message lookup/formatting will be unavailable while doing so.
 * Generally speaking, these sorts of errors do not have constants, since there by
 * definition the system cannot look up the message to use to format them.
 * 
 * <P>
 * Rather than defining a critical constant, throw an instance of
 * CriticalApplicationException.
 * 
 * <P>
 * Not being able to load the resource bundles, which contain the key/format
 * string pairs, is an example of a critical error.
 * 
 * 
 * <H3>Name</H3>
 *  
 */
public interface ApplicationMessages
{
	public static final String PREFIX = ApplicationMessages.class.getName();
	
	public static final String CRIT = PREFIX + ".critical.";
	public static final String FATAL = PREFIX + ".fatal.";
	public static final String ERROR = PREFIX + ".error.";
	public static final String INFO = PREFIX + ".info.";
	public static final String PROMPT = PREFIX + ".prompt.";
	
	
	/**
	 * The application caught a java.lang.MissingResourceException while trying
	 * to load a resource bundle.  This means that the application probably 
	 * cannot display anything but the most basic messages, and that it is 
	 * going to terminate immediately.
	 * <P>
	 * <UL>
	 * <LI>0 - (String) the name of the resource that caused the exception.
	 * </UL>
	 */
	public static final String CRIT_BUNDLE_MISSING =
		CRIT + "bundleMissingException";
	
	/**
	 * A requested resource was not in any of the resource bundles of the 
	 * message bundle "path."
	 * 
	 * <P>
	 * <UL>
	 * <LI>0 - (String) the key that could not be found.
	 * </UL>
	 */
	public static final String ERROR_KEY_NOT_FOUND = ERROR + "keyNotFound";
	
	/**
	 * The application caught an exception that represents a problem that 
	 * the application cannot fix and for which the user is not responsible.  
	 * 
	 * <P/>
	 * For example, if a call to DateFormatter.getInstance threw an exception,
	 * the app will probably not be able to fix the problem and the user 
	 * was probably not responsible.
	 * 
	 * <UL>
	 * <LI/>data[0] (Throwable) The exception that the application caught.
	 * <LI/>data[1] (Object) Any additional data.  May be null
	 * </UL>
	 */
	public static final String CRIT_INTERNAL =
		CRIT + "internal";
	
	/**
	 * The application caught an exception while trying to load a resource 
	 * bundle.
	 * 
	 * <UL>
	 * <LI/>0 (String) - the name of the resource that is missing
	 * </UL> 
	 */
	public static final String CRIT_BUNDLE_NOT_FOUND =
		CRIT + "bundleNotFound";
	
	/**
	 * Some unanticipated, critical error occurred.
	 */
	public static final String CRIT_ERROR_UNKNOWN = 
		"critcal.error.unknown";
	
	/**
	 * Unsaved changes: save or discard?
	 */
	public static final String CRIT_QUESTION_SAVE = 
		"critical.question.saveData";
	
	/**
	 * Title for save/discard dialog.
	 */
	public static final String CRIT_QUESTION_SAVE_TITLE = 
		"critical.question.saveDataTitle";
	
	/**
	 * Do you want to quit the application?
	 */
	public static final String CRIT_QUESTION_QUIT =
		"critial.question.quit";
	
	/**
	 * Title for quit/continue dialog
	 */
	public static final String CRIT_QUESTION_QUIT_TITLE =
		"critical.question.quit.title";

	/**
	 * An attempt to save some data failed, but there is a short string from the 
	 * application that explains why.  Should the application try to save again 
	 * or should the application discard the data?
	 * 
	 * <P>
	 * The string should be a short explaination like "the file was read-only"
	 * or "the remote host did not respond."
	 */
	public static final String CRIT_QUESTION_SAVE_FAILED =
		"critical.question.saveFailed";
	
	/**
	 * An attempt to save some changes failed and we don't know why.  Should we try 
	 * again or discard the changes?
	 */
	public static final String CRIT_QUESTION_SAVE_FAILED_UNKNOWN =
		"critical.question.saveFailedUnknown";
	
	/**
	 * Title for the save failed dialog.
	 */
	public static final String CRIT_QUESTION_SAVE_FAILED_TITLE =
		"critical.question.saveFailed.title";
	
	/**
	 * One or more resource bundles failed to load.
	 * 
	 * <P>
	 * Resource bundles contain application messages and the like.  Assuming this is
	 * an ApplicationException, the data array has the following format:
	 * <UL>
	 * <LI>data[0] (String) name of failed resource bundle #1
	 * <LI>data[1] (MissingResourceException) exeception encountered while trying to 
	 * load the resource bundle.
	 * <LI>data[2] (String) name of failed resource bundle #2
	 * ...
	 * </UL>
	 * 
	 * <P>
	 * Note that only the resource bundles that failed to load are so detailed.  
	 * The ones that succeeded are not listed.
	 */
	// public static final String CRIT_ERROR_MISSING_RESOURCE_BUNDLES =
		// "critical.error.missingResourceBundle";
	
	/**
	 * The application caught a RuntimeException while trying to initialize a 
	 * ResourceBundle --- this means that no text messages are available via 
	 * Application.getMessage.
	 * 
	 * <P>
	 * If this error occurs, then the application will not be able to function in any
	 * meaningful way.  The best it can do is terminate gracefully.
	 * 
	 * <P>
	 * Data contents:
	 * <UL>
	 * <LI>data[0] (Throwable) The exception caught.
	 * </UL>
	 */
	public static final String CRIT_ERROR_RESOURCE_LOAD_FAILED =
		"critical.com.lts.application.errors.resourceBundle.load";
	
	/**
	 * An exception occurred while trying to close the application.
	 *
	 * <UL>
	 * <LI>cause - exception that was caught.
	 * <LI>data - empty.
	 * </UL>
	 */
	public static final String ERROR_ON_CLOSE = 
		"com.lts.application.errors.onClose";
	
	/**
	 * An exception occurred while trying to the look and feel for the windowing 
	 * system.
	 *
	 * <UL>
	 * <LI>cause - exception that was caught.
	 * <LI>data[0] - the name of the class that the application tried to set the look 
	 * and feel to.
	 * </UL>
	 */
	public static final String ERROR_SETTING_LOOK_AND_FEEL =
		"com.lts.application.errors.settingLookAndFeel";
	
	/**
	 * An {@link LTSException} occurred while calling 
	 * {@link com.lts.swing.contentpanel.ContentPanel#initialize()}.
	 *
	 * <UL>
	 * <LI>cause - the LTSException
	 * <LI>data - empty.
	 * </UL>
	 */
	public static final String ERROR_INITIALIZING_PANEL =
		"com.lts.application.errors.initializingPanel";
	
	/**
	 * An {@link LTSException} occurred while calling 
	 * {@link com.lts.swing.contentpanel.ContentPanel#initializeWindow(Window)}.
	 *
	 * <UL>
	 * <LI>cause - the LTSException.
	 * <LI>data - empty.
	 * </UL>
	 */
	public static final String ERROR_INITIALIZING_WINDOW = 
		"com.lts.application.errors.initializingWindow";
	
	/**
	 * The system cannot use a particular window because it could not find all
	 * the messages associated with the window.
	 */
	public static final String ERROR_LOADING_MESSAGES =
		ERROR + "loadingMessages"; 
		
	/**
	 * While processing command line arguments, a property descriptor returned
	 * an index that was smaller than the pervious index as the "next argument"
	 * to be processed.  The data part of the exception should contain an 
	 * array of two values with the previous index value and the next index 
	 * value.
	 */
	public static final String ERROR_BACKWARD_CMD_LINE = 
		"com.lts.application.errors.backwardsCommandLine";
		
	/**
	 * While processing command line arguments, the application was passed an 
	 * option that requires an argument, but no argument was present.  The 
	 * data should contain the switch or long name.
	 */
	public static final String ERROR_MISSING_CMD_ARGUMENT = 
		"com.lts.application.errors.missingCommandLineArgument";
	
	/**
	 * One of the command line parameters was a switch (e.g. "-a") but the 
	 * switch character was unrecognized.
	 */
	public static final String ERROR_UNRECOGNIZED_SWITCH = 
		"com.lts.application.errors.unrecognizedSwitch";
	
	/**
	 * One of the command line switchs that requires an argument was not at 
	 * the end of a switch string.  For example, the jar command can take 
	 * the argument "-f" to specify the jar file that it should use.  In this
	 * framework, if the -f option were specified like this: -fo foo.jar, it 
	 * would be an error.
	 */
	public static final String ERROR_OPTION_SWITCH_NOT_AT_END = 
		"com.lts.application.errors.optionSwitchNotAtEnd";
	
	/**
	 * We could not find the specified property file.  This means that we 
	 * checked to see if the property file existed, it did, and then, when 
	 * we tried to open it, we got a file not found exception.  In essance, 
	 * this is an impossible case.
	 * 
	 * <UL>
	 * <LI>data[0] (String) the name of the properties file
	 * </UL>
	 */
	public static final String ERROR_MISSING_PROPERTIES_FILE = 
		"com.lts.application.errors.missingPropertiesFile";
	
	/**
	 * This means that we encountered an IOException while trying to read 
	 * the application properties file.
	 * 
	 * <P>
	 * The data should contain the file name and the IOException.
	 */
	public static final String ERROR_READING_PROPERTIES = 
		"com.lts.application.errors.ReadingProperties";
	
	/**
	 * The application could not create a temporary directory that it needed
	 * for whatever reason.
	 * 
	 * <UL>
	 * <LI>data[0] contains the file object that could not be created.
	 * </UL>
	 */
	public static final String ERROR_FAILED_CREATING_TEMPDIR = 
		"com.lts.application.errors.CreatingTempdir";
	
	/**
	 * The application failed to rename a file or directory for some unknown 
	 * reason.
	 * 
	 * <UL>
	 * <LI>data[0] contains the original file name
	 * <LI>data[1] contains the new file name
	 * </UL>
	 */
	public static final String ERROR_RENAMING_FILE = 
		"com.lts.application.errors.RenamingFile";

	/**
	 * The application caught an exception while trying to create a temporary 
	 * repository.
	 * 
	 * <UL>
	 * <LI>data[0] contains the exception
	 * </UL>
	 */
	public static final String ERROR_CREATING_TEMP_REPOSITORY = 
		"com.lts.application.errors.CreatingTempRepository";

	/**
	 * An exception was thrown while trying to remove temporary application 
	 * files.
	 */
	public static final String ERROR_REMOVING_TEMP_FILES =
		"com.lts.application.errors.RemovingTempFiles";

	/**
	 * An exception was thrown while trying to backup a file.
	 * 
	 * <UL>
	 * <LI>data[0] (string) The name of the file we were trying to make a 
	 * backup for.
	 * 
	 * <LI>data[1] (string) the name of the file that we were trying to write 
	 * the data to.
	 * 
	 * <LI>data[2] (Throwable) the exception.
	 * </UL>
	 */
	public static final String ERROR_MAKING_BACKUP =
		"com.lts.application.errors.MakingBackup";

	
	/**
	 * The application could not create create a temporary area to store temp files,
	 * directories and the like.  Because this is a critical capability, encountering 
	 * this problem means that the application will stop, hence the "FATAL" part 
	 * of the name.
	 * 
	 * <UL>
	 * <LI>data[0] (String) The name of the directory that the application 
	 * was trying to create.
	 * 
	 * <LI>data[1] (Throwable) The exception that was thrown.
	 * </UL>
	 */
	public static final String FATAL_CREATING_TEMP_AREA =
		"com.lts.application.fatal.createTempArea";
	
	/**
	 * The application could not create a temp directory for some reason.  Unlike 
	 * {@link #FATAL_CREATING_TEMP_AREA}, this is not necessarily a fatal problem.
	 */
	public static final String ERROR_CREATING_TEMP_DIR =
		"com.lts.application.error.createTempDir";
	
	public static final String ERROR_CREATING_TEMP_FILE =
		"com.lts.application.errors.CreatingTempFile";
	
	/**
	 * The application could not create a directory that it needs.
	 * 
	 * <UL>
	 * <LI/>data[0] (String) The name of the directory that the application
	 * was trying to create.
	 * </UL>
	 */
	public static final String ERROR_FAILED_CREATE_DIRECTORY =
		"com.lts.application.errors.failedCreateDirectory";
	
	/**
	 * The application caught an exception trying to open a file for writing
	 * --- basically a FileNotFoundException.
	 * 
	 * <UL>
	 * <LI>data[0] (String) The name of the file the app was trying to open.
	 * <LI>data[1] (Throwable) The exception thrown.
	 * </UL>
	 */
	public static final String ERROR_OPENING_FILE = 
		"com.lts.application.errors.OpeningFile";
	
	
	/**
	 * The application tried to store itself in a repository, but the repository does not
	 * support virtual file systems.
	 * 
	 * <H2>Data</H2>
	 * <UL>
	 * <LI>data[0] (ApplicationRepository) the repository in question
	 * <LI>data[1] (ApplicationData) the data in question
	 * </UL>
	 */
	public static final String ERROR_VFS_NOT_SUPPORTED =
		"com.lts.application.errors.vfsNotSupported";
	
	
	public static final String ERROR_REPOSITORY_ACCESS =
		"com.lts.application.errors.repository.access";
	
	/**
	 * The application caught an exception while trying to rename a repository.
	 * The original repository should still be OK, but it would be a good idea 
	 * to close and reopen all files.
	 * 
	 * <UL>
	 * <LI>data[0] contains the original name of the repository.
	 * <LI>data[1] contains the name that the app tried to change it to.
	 * <LI>data[2] contains the exception.
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_RENAME = 
		"com.lts.application.errors.repository.renaming";

	/**
	 * The application caught an exception while trying to copy a repository 
	 * from one file/directory to another.  The original repository is probably
	 * OK, but the copy may be corrupted.
	 * 
	 * <UL>
	 * <LI>data[0] contains the original file name
	 * <LI>data[1] contains the new file name
	 * <LI>data[2] contains the exception
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_COPY = 
		"com.lts.application.errors.repository.copying";

	/**
	 * The application encountered a problem while trying to extract out a
	 * repository to a temporary location.
	 * 
	 * <UL>
	 * <LI>data[0] contains the repository file name
	 * <LI>data[1] contains the exception 
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_EXTRACTING = 
		"com.lts.application.errors.repository.extracting";

	/**
	 * An exception was thrown while trying to save the data in an application 
	 * repository.
	 * 
	 * <UL>
	 * <LI>data[0] (string) The file name that we tried to save to.
	 * <LI>data[1] (Throwable) The exception that was thrown.
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_WRITE =
		"com.lts.application.errors.repository.writing";

	/**
	 * An application repository caught an exception while trying to return
	 * an input stream to one of its myEntries.
	 * 
	 * <P>
	 * This is one of those "impossible" cases.
	 * 
	 * <UL>
	 * <LI>data[0] the repository file name (string)
	 * <LI>data[1] the entry name (string)
	 * <LI>data[2] the exception
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_GET_INPUT_STREAM =
		"com.lts.application.errors.repository.GettingEntry";

	/**
	 * An application repository caught an exception while trying to get an 
	 * output stream to an entry.
	 * 
	 * <UL>
	 * <LI>data[0] (string) the repository file name
	 * <LI>data[1] (string) the entry name
	 * <LI>data[2] (Exception) the exception
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_GET_OUTPUT_STREAM =
		"com.lts.application.errors.repository.PuttingEntry";

	/**
	 * An attempt to delete an application repository failed for an unknown 
	 * reason.
	 * 
	 * <P>
	 * For example, calling File.delete returned false.  The reason why the 
	 * file could not be removed in that situation is unknown.
	 * 
	 * <UL>
	 * <LI>data[0] (string) the repository file name
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_DELETE =
		"ccom.lts.application.errors.repository.deleteFailed";
	
	
	public static final String ERROR_REPOSITORY_DELETE_ENTRY =
		"com.lts.application.errors.repository.deleteEntry";
	
	/**
	 * An exception was thrown while trying to rollback changes to a repository.
	 * 
	 * <P>
	 * The way in which repositories were designed means that it is highly 
	 * unlikely that the original file(s) were corrupted, but the temp 
	 * directory used may need to be removed manually.
	 * 
	 * <UL>
	 * <LI>data[0] (String) The file name of the original repository
	 * <LI>data[1] (String) the file name of the temp directory 
	 * <LI>data[2] (Throwable) The thrown exception.
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_ROLLBACK =
		"com.lts.application.errors.repository.rollback";

	/**
	 * An exception was thrown while trying to read from a repository.
	 * 
	 * <UL>
	 * <LI>data[0] (String) The file name of the repository.
	 * <LI>data[1] (String) The entry name.
	 * <LI>data[2] (Throwable) The thrown exception.
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_READ =
		"com.lts.application.errors.repository.read";
	
	/**
	 * A request was made to the repository when it was not in a state to process
	 * the request.
	 * 
	 * <P>
	 * For example, calling delete on a repository and then calling saveApplicationData.
	 * 
	 * <P>
	 * <UL>
	 * <LI>data[0] (String) The file name of the repository.
	 * <LI>data[1] (String) A description of the reason why the repository could not 
	 * process the request.  For example, because it had been deleted.
	 * 
	 * <LI>data[2] (Throwable) Stack trace to the offending call.
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_ILLEGAL_STATE = 
		"com.lts.application.errors.repository.IllegalState";

	
	/**
	 * An entry in the repository was of the wrong class type.
	 * 
	 * <P>
	 * <UL>
	 * <LI>data[0] (String) The file name
	 * <LI>data[1] (String) The entry
	 * <LI>data[2] (String) expected class type
	 * <LI>data[3] (String) actual class type
	 * </UL>
	 */
	public static final String ERROR_REPOSITORY_WRONG_TYPE =
		"com.lts.application.errors.repository.wrongType";
	
	/**
	 * The application could not save its properties file; in particular, when it
	 * tried to open the properties file to write to, an exception was thrown.
	 * This means that things 
	 * like settings and preferences may not be persisted between sessions.
	 * 
	 * <P>
	 * <UL>
	 * <LI>data[0] (String) The name of the properties file.
	 * <LI>data[1] (FileNotFoundException) The exception thrown when trying to 
	 * open the properties file.
	 * </UL>
	 */
//	public static final String CRIT_ERROR_SAVING_PROPERTIES_OPEN =
//		"critical.com.lts.application.errors.properties.saving";
	
	/**
	 * The application could not save the app options, preferences, etc.  Specifically
	 * after opening the file, the application could not write to the properties file.
	 * 
	 * <P>
	 * <UL>
	 * <LI>data[0] (String) The name of the properties file.
	 * <LI>data[1] (IOException) The exception that was thrown.
	 * </UL>
	 */
	public static final String CRIT_ERROR_SAVING_PROPERTIES_WRITE = 
		"critical.com.lts.application.errors.properties.saving";
	
	/**
	 * The title for the dialog window containing the error message for the could not 
	 * save application properties.
	 */
	public static final String CRIT_ERROR_SAVING_PROPERTIES_TITLE =
		"crititcal.com.lts.application.errors.properties.title";
	
	/**
	 * The application caught a RuntimeException while trying to save the properties.
	 * 
	 * <P>
	 * <UL>
	 * <LI>data[0] (String) The properties file name.
	 * <LI>data[1] (Exception) The exception caught
	 * </UL>
	 */
	public static final String CRIT_ERROR_SAVING_PROPERTIES =
		"criticlal.com.lts.application.errors.properties.saving";
	
	/**
	 * The application caught an Exception while trying to format a message using the
	 * ResourceBundle approach.
	 * 
	 * <P>
	 * <UL>
	 * <LI>data[0] (String) The key used to look up the formatting template from 
	 * the ResourceBundle.
	 * <LI>data[1] (String) The formatting template obtained from the ResourceBundle.
	 * <LI>... any other data passed to the formatting method.
	 * </UL>
	 */
	public static final String CRIT_ERROR_MESSAGE_FORMATTING =
		"com.lts.application.errors.messages.formatting";
	
	/**
	 * The application caught an exception while trying to load or otherwise prepare
	 * the properties.
	 */
	public static final String ERROR_PROPERTIES =
		"com.lts.application.errors.properties";
	
	/**
	 * The application cannot create a repository, even the default, empty repository.
	 * In this situation, the application cannot continue and will therefore quit.  The
	 * message should explain this to the user.
	 */
	public static final String FATAL_CREATE_REPOSITORY =
		"com.lts.application.fatal.createRepository";
	
	
	/**
	 * The application caught a RuntimeException during startup.  This probably means
	 * that the user is the unfortunate recipient of a bug, so tell them that this is 
	 * the case and that the appolication is going to terminate.
	 */
	public static final String FATAL_STARTUP_RUNTIME_EXCEPTION =
		"com.lts.application.fatal.startup.runtimeException";
	
	/**
	 * Get a message that explains to the user that an error was encountered while 
	 * trying to process the command line, and that asks the user if they want to 
	 * continue running the program.
	 */
	public static final String PROMPT_STARTUP_CMDLINE_ERROR =
		"com.lts.application.prompt.startup.continueDespiteCommandLineError";
	
	
	/**
	 * A message that asks the user if they want to continue running the application 
	 * despite encountering an error while trying to process the application properties.
	 */
	public static final String PROMPT_STARTUP_PROPERTIES_ERROR =
		"com.lts.application.prompt.startup.errorLoadingProperties";
	
	/**
	 * The application cannot load resource bundles and therefore needs to terminate.
	 */
	public static final String FATAL_LOAD_RESOURCES =
		FATAL + "loadingResources";

	/**
	 * The application encountered a severe error.  Continue or quit?
	 */
	public static final String PROMPT_CONTINUE_WITH_ERROR =
		ERROR + "showAndAskToContinue";
}

