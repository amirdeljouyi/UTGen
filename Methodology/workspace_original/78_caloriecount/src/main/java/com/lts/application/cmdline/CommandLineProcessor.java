package com.lts.application.cmdline;

import java.util.Properties;

import com.lts.application.ApplicationException;

/**
 * A class that processes the command line to an application.
 * 
 * @author cnh
 *
 */
public interface CommandLineProcessor
{
	/**
	 * Process the command line and return the interpreted values as properties.
	 * 
	 * <P>
	 * The application framework is designed with the idea that arguments to the 
	 * program can be represented as a properties object.  The properties set and 
	 * the values used should have more meaning than simply parroting the contents
	 * of the argv array.
	 * </P>
	 * 
	 * <P>
	 * For example, the program might take an argument like "-s" to indicate non-
	 * verbose mode.  In that situation, the resulting properties object might 
	 * contain something like key="verbosity", value="terse" or "silent=true".
	 * </P>
	 * 
	 * @param argv The array of string passed to the program on the command line.
	 * @return The settings that the input translates to.
	 */
	public Properties processArguments(String[] argv) throws ApplicationException;
}
