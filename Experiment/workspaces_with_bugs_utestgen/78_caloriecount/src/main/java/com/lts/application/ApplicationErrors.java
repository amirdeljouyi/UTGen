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

/**
 * Defines a basic set of application errors that can occur.
 */
public interface ApplicationErrors
{
	/**
	 * Some sort of error occurred for which there is no other information.
	 * 
	 * <P>
	 * The contents of the data array are undefined. 
	 */
	public static final int CODE_UNKNOWN_ERROR = 0;
	public static final String STR_UNKNOWN_ERROR = 
		"com.lts.application.unknownError";
		
	/**
	 * While processing command line arguments, a property descriptor returned
	 * an index that was smaller than the pervious index as the "next argument"
	 * to be processed.  The data part of the exception should contain an 
	 * array of two values with the previous index value and the next index 
	 * value.
	 */
	public static final int CODE_BACKWARD_CMD_LINE = 1;
	public static final String STR_BACKWARD_CMD_LINE = 
		"com.lts.application.backwardsCommandLine";
		
	/**
	 * While processing command line arguments, the application was passed an 
	 * option that requires an argument, but no argument was present.  The 
	 * data should contain the switch or long name.
	 */
	public static final int CODE_MISSING_CMD_ARGUMENT = 2;
	public static final String STR_MISSING_CMD_ARGUMENT = 
		"com.lts.application.missingCommandLineArgument";
	
	/**
	 * One of the command line parameters was a switch (e.g. "-a") but the 
	 * switch character was unrecognized.
	 */
	public static final int CODE_UNRECOGNIZED_SWITCH = 3;
	public static final String STR_UNRECOGNIZED_SWITCH = 
		"com.lts.application.unrecognizedSwitch";
	
	/**
	 * One of the command line switchs that requires an argument was not at 
	 * the end of a switch string.  For example, the jar command can take 
	 * the argument "-f" to specify the jar file that it should use.  In this
	 * framework, if the -f option were specified like this: -fo foo.jar, it 
	 * would be an error.
	 */
	public static final int CODE_OPTION_SWITCH_NOT_AT_END = 4;
	public static final String STR_OPTION_SWITCH_NOT_AT_END = 
		"com.lts.application.optionSwitchNotAtEnd";
	
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
	public static final int CODE_MISSING_PROPERTIES_FILE = 5;
	public static final String STR_MISSING_PROPERTIES_FILE = 
		"com.lts.application.missingPropertiesFile";
	
	/**
	 * This means that we encountered an IOException while trying to read 
	 * the application properties file.
	 * 
	 * <P>
	 * The data should contain the file name and the IOException.
	 */
	public static final int CODE_ERROR_READING_PROPERTIES = 6;
	public static final String STR_ERROR_READING_PROPERTIES = 
		"com.lts.application.errorReadingProperties";
	
	/**
	 * Someone tried to get a format string using a key that was not in the
	 * resource bundle.
	 * 
	 * <P>
	 * Generally this error is not contained in an exception; but should 
	 * it be so encountered, the data should contain an integer with the 
	 * unrecognized code.
	 */
	public static final int CODE_UNRECOGNIZED_KEY = 7;
	public static final String STR_UNRECOGNIZED_KEY = 
		"com.lts.application.unrecognizedKey";

	/**
	 * Someone tried to get an error message using an error code that we did
	 * not recognize.
	 * 
	 * <P>
	 * Generally this error is not contained in an exception, but if it is 
	 * encountered in that manner, the data should contain a string that is the
	 * offending key string.
	 */
	public static final int CODE_UNRECOGNIZED_CODE = 8;
	public static final String STR_UNRECOGNIZED_CODE = 
		"com.lts.application.unrecognizedCode";
	
	/**
	 * The application could not create a temporary directory that it needed
	 * for whatever reason.
	 * 
	 * <UL>
	 * <LI>data[0] contains the file object that could not be created.
	 * </UL>
	 */
	public static final int CODE_FAILED_CREATING_TEMPDIR = 9;
	public static final String STR_FAILED_CREATING_TEMPDIR = 
		"com.lts.application.errorCreatingTempdir";
	
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
	public static final int CODE_ERROR_RENAMING_REPOSITORY = 10;
	public static final String STR_ERROR_RENAMING_REPOSITORY = 
		"com.lts.application.errorRenamingRepository";

	/**
	 * The application failed to rename a file or directory for some unknown 
	 * reason.
	 * 
	 * <UL>
	 * <LI>data[0] contains the original file name
	 * <LI>data[1] contains the new file name
	 * </UL>
	 */
	public static final int CODE_ERROR_RENAMING_FILE = 11;
	public static final String STR_ERROR_RENAMING_FILE = 
		"com.lts.application.errorRenamingFile";

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
	public static final int CODE_ERROR_COPYING_REPOSITORY = 12;
	public static final String STR_ERROR_COPYING_REPOSITORY = 
		"com.lts.application.errorCopyingRepository";

	/**
	 * The application caught an exception while trying to create a temporary 
	 * repository.
	 * 
	 * <UL>
	 * <LI>data[0] contains the exception
	 * </UL>
	 */
	public static final int CODE_ERROR_CREATING_TEMP_REPOSITORY = 13;
	public static final String STR_ERROR_CREATING_TEMP_REPOSITORY = 
		"com.lts.application.errorCreatingTempRepository";

	/**
	 * The application encountered a problem while trying to extract out a
	 * repository to a temporary location.
	 * 
	 * <UL>
	 * <LI>data[0] contains the repository file name
	 * <LI>data[1] contains the exception 
	 * </UL>
	 */
	public static final int CODE_ERROR_EXTRACTING_REPOSITORY = 14;
	public static final String STR_ERROR_EXTRACTING_REPOSITORY = 
		"com.lts.application.errorExtractingRepository";

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
	public static final int CODE_ERROR_INPUT_STREAM = 15;
	public static final String STR_ERROR_INPUT_STREAM =
		"com.lts.application.errorGettingEntry";

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
	public static final int CODE_ERROR_OUTPUT_STREAM = 16;
	public static final String STR_ERROR_OUTPUT_STREAM =
		"com.lts.application.errorPuttingEntry";

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
	public static final int CODE_DELETE_FAILED = 18;
	public static final String STR_DELETE_FAILED =
		"com.lts.application.deleteFailed";

	/**
	 * An exception was thrown while trying to remove temporary application 
	 * files.
	 */
	public static final int CODE_ERROR_REMOVING_TEMP_FILES = 19;
	public static final String STR_ERROR_REMOVING_TEMP_FILES =
		"com.lts.application.errorRemovingTempFiles";

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
	public static final int CODE_ERROR_MAKING_BACKUP = 20;
	public static final String STR_ERROR_MAKING_BACKUP =
		"com.lts.application.errorMakingBackup";

	/**
	 * An exception was thrown while trying to save the data in an application 
	 * repository.
	 * 
	 * <UL>
	 * <LI>data[0] (string) The file name that we tried to save to.
	 * <LI>data[1] (Throwable) The exception that was thrown.
	 * </UL>
	 */
	public static final int CODE_ERROR_WRITING_REPOSITORY = 21;
	public static final String STR_ERROR_WRITING_REPOSITORY =
		"com.lts.application.errorWritingRepository";

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
	public static final int CODE_ERROR_PERFORMING_ROLLBACK = 22;
	public static final String STR_ERROR_PERFORMING_ROLLBACK =
		"com.lts.application.errorPerformingRollback";

	/**
	 * An exception was thrown while trying to read from a repository.
	 * 
	 * <UL>
	 * <LI>data[0] (String) The file name of the repository.
	 * <LI>data[1] (String) The entry name.
	 * <LI>data[2] (Throwable) The thrown exception.
	 * </UL>
	 */
	public static final int CODE_ERROR_READING_REPOSITORY = 23;
	public static final String STR_ERROR_READING_REPOSITORY =
		"com.lts.application.errorReadingRepository";
	
	/**
	 * An exception was thrown while trying to create a temporary directory
	 * for the application.
	 * 
	 * <UL>
	 * <LI>data[0] (String) The name of the directory that the application 
	 * was trying to create.
	 * 
	 * <LI>data[1] (Throwable) The exception that was thrown.
	 * </UL>
	 */
	public static final int CODE_ERROR_CREATING_TEMP_DIRECTORY = 24;	
	public static final String STR_ERROR_CREATING_TEMP_DIRECTORY =
		"com.lts.application.errorCreatingTempDirectory";
	
	public static final int CODE_ERROR_CREATING_TEMP_FILE = 25;
	public static final String STR_ERROR_CREATING_TEMP_FILE =
		"com.lts.application.errorCreatingTempFile";
	
	/**
	 * The application could not create a directory that it needs.
	 * 
	 * <UL>
	 * <LI/>data[0] (String) The name of the directory that the application
	 * was trying to create.
	 * </UL>
	 */
	public static final int CODE_FAILED_CREATE_DIRECTORY = 26;
	public static final String STR_FAILED_CREATE_DIRECTORY =
		"com.lts.application.failedCreateDirectory";
	
	/**
	 * The application caught an exception trying to open a file for writing
	 * --- basically a FileNotFoundException.
	 * 
	 * <UL>
	 * <LI/>data[0] (String) The name of the file the app was trying to open.
	 * <LI/>data[1] (Throwable) The exception thrown.
	 * </UL>
	 */
	public static final int CODE_ERROR_OPENING_FILE = 27;
	public static final String STR_ERROR_OPENING_FILE = 
		"com.lts.application.errorOpeningFile";
	
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
	public static final int CODE_ERROR_INTERNAL = 28;
	public static final String STR_ERROR_INTERNAL =
		"com.lts.application.errorInternal";
	
	/**
	 * The application detected a problem that it cannot recover from and that
	 * is probably not the user's doing.  
	 * 
	 * <P/>
	 * For example, if a parameter to a function that is never supposed to be 
	 * null is passed a null value, the issue cannot be easily traced back and 
	 * it was probably not due to something the user did.  In that situation 
	 * there is not really any specific message that can be presented to the 
	 * user that will make any sense, nor can the offending data be identified
	 * in advance.
	 * 
	 * <P/>
	 * If a more specific cause can be identified by that which would normally
	 * throw this exception, a different code should be used instead.
	 * 
	 * <UL>
	 * <LI/>data[0] (Object) May be null --- contains any additional data that
	 * the source of the exception wishes to provide.
	 * </UL> 
	 */
	public static final int CODE_FAILURE_INTERNAL = 28;
	public static final String STR_FAILURE_INTERNAL =
		"com.lts.application.errorInternal";
	
	/**
	 * The application needed to load a resource via 
	 * {@link Class#getResourceAsStream(java.lang.String)}, but that method
	 * returned null.
	 * 
	 * <P/>
	 * This problem is generally the same as {@link #CODE_FAILURE_INTERNAL},
	 * in that there is little that the user or the application can do about 
	 * it.
	 * 
	 * <UL>
	 * <LI/>data[0] (String) the name of the resource that is missing
	 * <LI/>data[1] (Class) the class that was used for the 
	 * {@link Class#getResourceAsStream(java.lang.String)} call.
	 * </UL> 
	 */
	public static final int CODE_RESOURCE_NOT_FOUND = 29;
	public static final String STR_RESOURCE_NOT_FOUND =
		"com.lts.application.resourceNotFound";
	
	public static final Object[] SPEC_CODE_TO_KEY = {
			new Integer(CODE_UNKNOWN_ERROR),			STR_UNKNOWN_ERROR,
			new Integer(CODE_BACKWARD_CMD_LINE), 		STR_BACKWARD_CMD_LINE,
			new Integer(CODE_MISSING_CMD_ARGUMENT), 	STR_MISSING_CMD_ARGUMENT,
			new Integer(CODE_UNRECOGNIZED_SWITCH), 		STR_UNRECOGNIZED_SWITCH,
			new Integer(CODE_OPTION_SWITCH_NOT_AT_END), STR_OPTION_SWITCH_NOT_AT_END,
			new Integer(CODE_MISSING_PROPERTIES_FILE), 	STR_MISSING_PROPERTIES_FILE,
			new Integer(CODE_ERROR_READING_PROPERTIES), STR_ERROR_READING_PROPERTIES,
			new Integer(CODE_UNRECOGNIZED_KEY),			STR_UNRECOGNIZED_KEY,
			new Integer(CODE_UNRECOGNIZED_CODE),		STR_UNRECOGNIZED_CODE,
			new Integer(CODE_ERROR_RENAMING_REPOSITORY),STR_ERROR_RENAMING_REPOSITORY,
			new Integer(CODE_ERROR_RENAMING_FILE),		STR_ERROR_RENAMING_FILE,
			new Integer(CODE_ERROR_COPYING_REPOSITORY),	STR_ERROR_COPYING_REPOSITORY,
			new Integer(CODE_FAILED_CREATING_TEMPDIR),	STR_FAILED_CREATING_TEMPDIR,
			new Integer(CODE_ERROR_CREATING_TEMP_REPOSITORY),	STR_ERROR_CREATING_TEMP_REPOSITORY,
			new Integer(CODE_ERROR_EXTRACTING_REPOSITORY),		STR_ERROR_EXTRACTING_REPOSITORY,
			new Integer(CODE_ERROR_INPUT_STREAM),		STR_ERROR_INPUT_STREAM,
			new Integer(CODE_ERROR_OUTPUT_STREAM),		STR_ERROR_OUTPUT_STREAM,
			new Integer(CODE_DELETE_FAILED),			STR_DELETE_FAILED,
			new Integer(CODE_ERROR_REMOVING_TEMP_FILES),STR_ERROR_REMOVING_TEMP_FILES,
			new Integer(CODE_ERROR_MAKING_BACKUP),		STR_ERROR_MAKING_BACKUP,
			new Integer(CODE_ERROR_WRITING_REPOSITORY),	STR_ERROR_WRITING_REPOSITORY,
			new Integer(CODE_ERROR_PERFORMING_ROLLBACK),STR_ERROR_PERFORMING_ROLLBACK,
			new Integer(CODE_ERROR_READING_REPOSITORY),	STR_ERROR_READING_REPOSITORY,
			new Integer(CODE_ERROR_CREATING_TEMP_DIRECTORY),	STR_ERROR_CREATING_TEMP_DIRECTORY,
			new Integer(CODE_ERROR_CREATING_TEMP_FILE),	STR_ERROR_CREATING_TEMP_FILE,
			new Integer(CODE_FAILED_CREATE_DIRECTORY),	STR_FAILED_CREATE_DIRECTORY,
			new Integer(CODE_ERROR_OPENING_FILE),		STR_ERROR_OPENING_FILE,
			new Integer(CODE_ERROR_INTERNAL),			STR_ERROR_INTERNAL,
			new Integer(CODE_FAILURE_INTERNAL),			STR_FAILURE_INTERNAL,
			new Integer(CODE_RESOURCE_NOT_FOUND),		STR_RESOURCE_NOT_FOUND,
	};
}
