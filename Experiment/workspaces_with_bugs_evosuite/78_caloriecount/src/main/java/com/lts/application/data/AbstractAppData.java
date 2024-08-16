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

import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;


/**
 * A generic interface for application data.
 * 
 * <P>
 * This class simplifies processing application data by providing a view of the 
 * data as a list of {@link ApplicationDataElement} objects.  The usual clean/dirty
 * status is kept by the class as well as passing along event messages like 
 * {@link #postDeserialize()}.
 * </P>
 * 
 * <P>
 * Concrete subclasses must implement the {@link #getDataElements()} method and 
 * the {@link #processDataElement(ApplicationDataElement)} method.  getDataElements
 * assembles a list of the applications data, broken into ApplicationDataElements.
 * processDataElement is used to receive data (such as during deserialization) 
 * and put it in the right place for the application. 
 * </P>
 * 
 * @author cnh
 */
abstract public class AbstractAppData implements ApplicationData
{
	transient protected boolean myDirty;
	
	
	public boolean getDirty()
	{
		return myDirty;
	}
	
	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}
	
	public boolean isDirty()
	{
		return getDirty();
	}
	
	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
			throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	public void deepCopyData(Object o, Map map, boolean copyTransients) throws DeepCopyException
	{
		if (copyTransients)
		{
			AbstractAppData other = (AbstractAppData) o;
			other.myDirty = myDirty;
		}
	}

	public void postDeserialize() throws ApplicationException
	{
		myDirty = false;
	}
}
