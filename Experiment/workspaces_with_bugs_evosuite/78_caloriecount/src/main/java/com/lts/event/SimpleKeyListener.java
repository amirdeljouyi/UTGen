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
/*
 * Copyright (c) 2004 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.event;


/**
 * A simplified interface for keyboard listeners.
 * 
 * <P>
 * This interface simplifies what clients have to implement by defining methods
 * for commonly listened for keys instead of passing a KeyEvent and making the
 * client figure out which key was pressed.
 */
public interface SimpleKeyListener
{
	public void enter (Object source);
	public void tab (Object source);
	public void insert (Object source);
	public void delete (Object source);
	public void other (Object source, int code);
}
