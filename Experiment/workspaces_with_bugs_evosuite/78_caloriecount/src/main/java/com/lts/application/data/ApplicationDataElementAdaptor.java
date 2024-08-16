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
import com.lts.xml.simple.SimpleElement;


/**
 * Provide generic data to help in implementing ApplicationDataElement.
 * 
 * <P>
 * The class takes care of set/getDirty and those deep copy oriented methods that 
 * are generic to all classes.  Subclasses need to define 
 * {@link DeepCopier#deepCopyData(Object, Map, boolean)}.
 * </P>
 * 
 * <P>
 * The class defines "do nothing" or very marginal implementations for 
 * {@link #postDeserialize()} (just sets dirty to false), and 
 * {@link #deepCopyData(Object, Map, boolean)} (merely copys the dirty state 
 * if the caller wants to copy transients).  Subclasses should override and 
 * define additional logic to take care of the internal state of the element.
 * </P>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
abstract public class ApplicationDataElementAdaptor implements IdApplicationDataElement
{
	private static final String TAG_ID = "id";

	abstract protected String getSerializationName();

	transient private boolean myDirty;
	
	protected int myId;
	
	public int getId()
	{
		return myId;
	}
	
	public void setId (int id)
	{
		myId = id;
	}
	
	protected void initialize()
	{
		myDirty = false;
	}
	
	
	public boolean isDirty()
	{
		return myDirty;
	}

	public boolean getDirty() throws ApplicationException
	{
		return isDirty();
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
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

	public void postDeserialize() throws ApplicationException
	{
		myDirty = false;
	}

	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
		if (copyTransients)
		{
			ApplicationDataElementAdaptor other = 
				(ApplicationDataElementAdaptor) copy;
			other.myDirty = myDirty;
		}
	}

	public void copyFrom(ApplicationDataElement element) throws ApplicationException
	{
		ApplicationDataElementAdaptor other = (ApplicationDataElementAdaptor) element;
		myId = other.myId;
		postDeserialize();
	}
	
	
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void addValues(StringBuffer sb)
	{
		sb.append(getId());
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		addValues(sb);
		return sb.toString();
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		setId(elem.getIntValueOfChild(TAG_ID));
	}

	@Override
	public void serializeTo(SimpleElement elem)
	{
		elem.createChild(TAG_ID, getId());
	}

	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement root = new SimpleElement(getSerializationName());
		serializeTo(root);
		return root;
	}

}
