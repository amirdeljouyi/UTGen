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

import java.util.Map;

import com.lts.pest.data.event.DataChangeEvent;
import com.lts.pest.data.event.DataChangeListener;
import com.lts.pest.data.event.DataChangeListenerHelper;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

public class DataItemAdapter implements DataItem, DeepCopier
{
	private static final long serialVersionUID = 1L;
	
	protected boolean dirty;
	transient protected DataChangeListenerHelper helper;
	
	protected DataChangeListenerHelper getHelper()
	{
		if (null == this.helper)
			this.helper = new DataChangeListenerHelper();
		
		return this.helper;
	}
	
	
	public DataItemAdapter()
	{
		initialize();
	}
	
	protected void initialize ()
	{
		this.dirty = false;
	}
	
	
	public void setDirty (boolean newStatus)
	{
		this.dirty = newStatus;
	}
	
	public boolean isDirty ()
	{
		return this.dirty;
	}
	
	public void addDataChangeListener (DataChangeListener listener)
	{
		getHelper().addListener(listener);
	}
	
	public boolean removeDataChangeListener (DataChangeListener listener)
	{
		return getHelper().removeListener(listener);
	}

	public void fireDataChanged ()
	{
		DataChangeEvent event = new DataChangeEvent(this);
		getHelper().fire(event);
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


	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
	}
}
