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
package com.lts.application.data;

import java.io.Serializable;

import com.lts.application.ApplicationException;
import com.lts.util.deepcopy.DeepCopier;

/**
 * An object that handles the serialization of the application's data.
 * 
 * <H2>Note</H2>
 * In addition to defining the methods outlined in this interface, classes are
 * also expected to define a public, no-argument constructor.  Java serialization
 * explicitly states that the constructor for a class is <I>not</I> called during
 * deserialization.  Unfortunately, Java does not provide any way to allocate a 
 * new instance without calling a constructor of some sort,  
 * <P>
 * Instances of this class implement the following:
 * </P>
 * 
 * <UL>
 * <LI>Track whether or not the data has been changed (dirty/clean).</LI>
 * <LI>Implement loading & storing the data to a repository.</LI>
 * <LI>Implement any additional logic required for post deserialization.</LI>
 * </UL>
 *
 * <P>
 * Post deserialization is the notion that the state of the application data 
 * may not be completely consistent after being deserialized.  One reason is that 
 * the class constructor is specifically not called during deserialization, so 
 * any initialization code will not be invoked.  Especially if the application 
 * data has added or removed fields since the last serialization, it is possible 
 * for fields to be inconsistent after an instance is deserialized.
 * </P>
 * 
 * 
 * @author cnh
 */
public interface ApplicationData extends Serializable, DeepCopier
{
	/**
	 * Does this object need to be serialized (saved)?
	 * 
	 * @return true if the data is dirty, false if it is clean.
	 * @throws ApplicationException
	 *         If an error is encountered while trying to determine the status of the
	 *         data.
	 */
	public boolean isDirty() throws ApplicationException;
	
	/**
	 * Mark the object as clean or dirty.
	 * 
	 * @param dirty false if the data is now clean, true if the data is now dirty.
	 */
	public void setDirty (boolean dirty);
	
	/**
	 * Ensure that the object is in a consistent state after being deserialized.
	 * 
	 * <P>
	 * Like the Java serialization API
	 * </P>
	 * 
	 * @throws ApplicationException
	 */
	public void postDeserialize() throws ApplicationException;
}
