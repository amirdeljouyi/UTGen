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
package com.lts.cmdline;

public interface CommandLineMessages
{
	public static final String PREFIX = CommandLineMessages.class.getName();
	public static final String ERROR = PREFIX + ".errors.";
	
	/**
	 * The command line processor encountered a long option (e.g., --help), 
	 * but the option itself was not recognized.
	 * 
	 * <UL>
	 * <LI>0 (String) --- the unrecognized option
	 * </UL>
	 */
	public static final String UNRECOGNIZED_LONG_FORM =
		ERROR + "unrecognizedLongForm";
	
	
	/**
	 * The command line processor encountered a short option (e.g., -l), but 
	 * that option did not match any formal parameters.
	 * 
	 * <UL>
	 * <LI>0 (String) --- the unrecognized option
	 * </UL>
	 */
	public static final String UNRECOGNIZED_SHORT_FORM =
		ERROR + "unrecognizedShortForm";
	
	/**
	 * The command line processor encountered an argument that it could not 
	 * process --- that is, the command does not accept arguments.
	 * 
	 * <UL>
	 * <LI>0 (String) --- the unrecognized argument
	 * </UL>
	 */
	public static final String UNRECOGNIZED_ARGUMENT =
		ERROR + "unrecognizedArgument";
	
	/**
	 * The command line processor encountered a formal parameter specification
	 * that has some sort of problem.  The data parameter should identify the
	 * specific problem, should that which catches the exception actually care.
	 * 
	 * <UL>
	 * <LI>0 (String) --- a more specific error message
	 * </UL>
	 */
	public static final String INVALID_FORMAL_SPEC =
		ERROR + "invalidFormalParameterSpecification";
	
	/**
	 * The command line specified a switch that requires an argument, but none 
	 * was supplied.
	 * 
	 * <UL>
	 * <LI>0 (String) --- the name of the property that is missing an argument.
	 * </UL>
	 */
	public static final String MISSING_REQUIRED_ARGUMENT =
		ERROR + "missingRequiredArgument";
}
