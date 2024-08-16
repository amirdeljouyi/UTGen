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
import java.util.List;

import com.lts.application.ApplicationException;


/**
 * An object that contains ApplicationData objects.
 * 
 * <P>
 * An object of this interface does not have data of its own, instead it contains 
 * instances of {@link com.lts.application.data.ApplicationData} that actually
 * contain the data.  An instance of this object returns true when {@link #isDirty()}
 * is called if any of its constituents returns true for 
 * {@link ApplicationData#isDirty()} and false if none of those methods return true.
 * 
 * <P>
 * Calling {@link #setDirty(boolean)} on this method results in that call being
 * passed along to each constituent object.
 * 
 * <P>
 * The reconstitutionComplete method is intended to be called after an instance of 
 * this class has been deserialized --- when an instance has been restored to memory
 * from its serialized state.  In practical terms, it allows the containing object 
 * to commence monitoring of its constituent objects.
 * 
 * <P>
 * The element property accessors provide basic access to consituent objects rather
 * than bulk methods.
 *  
 * @author cnh
 */
public interface ApplicationDataContainer extends Serializable
{
	public boolean isDirty() throws ApplicationException;
	public void setDirty(boolean dirty);
	public List<ApplicationDataElement> getElements();
	public void setElements (List<ApplicationDataElement> elements);
	public void addElement (ApplicationDataElement element);
}
