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
 * A class that is responsible for displaying exceptions.
 *
 * <P>
 * This class must be very reliable, because if it fails or throws an exception, it 
 * is likely that nothing else will be able to catch the exception that this class
 * throws.
 * </P>
 * 
 * @author cnh
 */
abstract public class ApplicationExceptionHandler
{
	public static final int MODE_OK_DETAILS 		= 0;
	public static final int MODE_OK					= 1;
	public static final int MODE_YES_NO 			= 2;
	public static final int MODE_YES_NO_DETAILS 	= 3;
	public static final int MODE_OK_CANCEL			= 4;
	public static final int MODE_OK_CANCEL_DETAILS	= 5;
	
	public static final int RESULT_ERROR = 0;
	public static final int RESULT_OK = 1;
	public static final int RESULT_CANCEL = 2;
	public static final int RESULT_YES = 3;
	public static final int RESULT_NO = 4;
	
	
	/**
	 * Display an exception with a specific message, an OK button,
	 * and a DETAILS button.
	 *  
	 * @param message The string to display.
	 * @param throwable The exception to display.
	 */
	abstract public void processException (boolean waitForClose, String message, Throwable throwable);
	
	/**
	 * Display an exception with a specific message, and the specified 
	 * button configuration.
	 * 
	 * @param message The message to display.
	 * @param throwable The exception to display.
	 * @param mode Should be one of the MODE_ constants defined by this class.
	 * @return The uses response, using one of the RESULT_ codes.
	 */
	abstract public int showAndAsk (String message, Throwable throwable, int mode);
}
