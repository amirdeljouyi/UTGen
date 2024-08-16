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
package com.lts.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A map that "goes" in both directions: from key to value and value to key.
 */
public class DoubleMap
{
	protected Map keyToValueMap = new HashMap();
	protected Map valueToKeyMap = new HashMap();
	
	public Object keyToValue (Object key)
	{
		return keyToValueMap.get(key);
	}
	
	public Object valueToKey (Object value)
	{
		return valueToKeyMap.get(value);
	}
	
	public DoubleMap()
	{}
	
	public DoubleMap (Object[] spec)
	{
		for (int i = 0; i < spec.length; i += 2)
		{
			Object key = spec[i];
			Object value = spec[1+i];
			put (key, value);
		}
	}
	
	public DoubleMap (Object[][] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			Object[] row = spec[i];
			put(row[0], row[1]);
		}
	}
	
	
	public void put (Object key, Object value)
	{
		if (null == key)
			throw new IllegalArgumentException();
		
		if (null == value)
			remove(key);
		else
		{
			keyToValueMap.put(key, value);
			valueToKeyMap.put(value, key);
		}
	}
	
	public void addChecked (Object key, Object value)
	{
		if (null == key || null == value)
			throw new IllegalArgumentException();

		if (null != keyToValueMap.get(key))
			throw new IllegalArgumentException();
		
		if (null != valueToKeyMap.get(key))
			throw new IllegalArgumentException();
		
		keyToValueMap.put(key, value);
		valueToKeyMap.put(value, key);
	}
	
	
	public Object get (Object key)
	{
		return getKeyToValueMap().get(key);
	}
	
	public void remove (Object key)
	{
		removeKey(key);
	}
	
	public void removeKey (Object key)
	{
		Object value = keyToValueMap.get(key);
		keyToValueMap.remove(key);
		
		if (null != value)
			valueToKeyMap.remove(value);
	}
	
	public void removeValue (Object value)
	{
		Object key = valueToKeyMap.get(value);
		valueToKeyMap.remove(value);
		
		if (null != key)
			keyToValueMap.remove(key);
	}
	
	
	public Map getKeyToValueMap()
	{
		return keyToValueMap;
	}
	
	public Map getValueToKeyMap ()
	{
		return valueToKeyMap;
	}
	
	public void clear ()
	{
		keyToValueMap = new HashMap();
		valueToKeyMap = new HashMap();
	}
}
