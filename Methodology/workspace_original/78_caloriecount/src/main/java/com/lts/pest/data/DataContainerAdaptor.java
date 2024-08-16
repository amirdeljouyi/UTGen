//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lts.pest.data.event.DataChangeEvent;
import com.lts.pest.data.event.DataChangeListener;
import com.lts.pest.data.event.DataChangeListenerHelper;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

/**
 * A class that simplifies implementing the DataContainer interface.
 * 
 * <P>
 * Subclasses need to add/remove elements from the list of components via the 
 * {@link #addComponent(DataItem)} and {@link #removeComponent(DataItem)} methods
 * in order for this class to work correctly.
 * 
 * <P>
 * The class takes care of maintaining clean/dirty status, and defines the 
 * {@link #isDirty()} method to return true if any element in {@link #components}
 * returns true to {@link DataItem#isDirty()}.  Calls to {@link #setDirty(boolean)}
 * are forwarded on to all elements of components.
 * 
 * <P>
 * The class handles notification for those classes that have registered an 
 * interest via {@link #addDataChangeListener(DataChangeListener).
 * 
 * <P>
 * Changes to component DataItems are <I>not</I> considered to be changes to this object,
 * it only cares about add/removes to the list of components.
 * 
 * <P>
 * Developers need to take action in the following situations:
 * <UL>
 * <LI>You want to use a class other than List<DataItem> for components.
 * <LI>You want to use a different listener helper class.
 * </UL>
 * 
 * <H3>You Want to Use a Class Other than List<DataItem> for components</H3>
 * Override {@link #initialize()} to set {@link #components} to the desired class.
 * It still must be a subclass of {@link DataItem} however.  If you do not want 
 * this to be the case, you cannot use this class.
 * 
 * <H3>You want to use a different listener helper class</H3>
 * The helper class must be a subclass of {@link DataChangeListenerHelper}.  Override
 * {@link #initialize()} to set {@link #helper} to the desired class.
 * 
 * @author cnh
 */
public class DataContainerAdaptor implements DeepCopier
{
	transient protected List<DataItem> components;
	transient protected DataChangeListenerHelper helper;
	
	
	public DataChangeListenerHelper getHelper()
	{
		if (null == this.helper)
			this.helper = new DataChangeListenerHelper();
		
		return this.helper;
	}
	
	
	public DataContainerAdaptor()
	{
		initialize();
	}
	
	protected void initialize()
	{
		this.components = new ArrayList();
		this.helper = new DataChangeListenerHelper();
	}
	
	public DataItem[] getComponents()
	{
		DataItem[] temp = (DataItem[]) this.components.toArray();
		return temp;
	}
	
	public void addComponent (DataItem item)
	{
		if (!this.components.contains(item))
			this.components.add(item);
	}
	
	public boolean removeComponent (DataItem item)
	{
		boolean wasPresent = this.components.remove(item);
		fireDataChanged();
		return wasPresent;
	}

	public void addDataChangeListener(DataChangeListener listener)
	{
		getHelper().addListener(listener);
		// fireDataChanged();
	}

	public boolean isDirty()
	{
		for (DataItem item : this.components)
		{
			if (item.isDirty())
				return true;
		}
		
		return false;
	}

	public boolean removeDataChangeListener(DataChangeListener listener)
	{
		return getHelper().removeListener(listener);
	}

	public void setDirty(boolean theDirty)
	{
		if (null == this.components)
			return;
		
		for (DataItem item : this.components)
		{
			item.setDirty(theDirty);
		}
	}
	
	
	public void fireDataChanged()
	{
		DataChangeEvent event = new DataChangeEvent(this);
		this.helper.fire(event);
	}
	
	
	public void deepCopyData (Object ocopy, Map map, boolean copyTransients) throws DeepCopyException
	{
		DataContainerAdaptor copy = (DataContainerAdaptor) ocopy;
		
		copy.components = DeepCopyUtil.copyList(this.components, map, copyTransients);
		
		
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


}
