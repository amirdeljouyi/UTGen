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
package com.lts.swing.combobox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class SimpleComboBoxModel extends AbstractListModel implements ComboBoxModel
{
	private static final long serialVersionUID = 1L;
	
	protected List data;
	protected Object selected;
	protected Map displayToValue;
	protected Map valueToDisplay;
	
	
	
	public Object getSelectedValue()
	{
		Object selection = getSelectedItem();
		Object value = displayToValue.get(selection);
		return value;
	}
	
	
	public SimpleComboBoxModel()
	{
		initialize((Object[][]) null);
	}
	
	
	public SimpleComboBoxModel(Object[][] spec)
	{
		initialize(spec);
	}
	
	
	public SimpleComboBoxModel(List list)
	{
		initialize(list);
	}
	
	
	protected void initialize()
	{
		this.data = new ArrayList();
		this.displayToValue = new HashMap();
		this.valueToDisplay = new HashMap();
		this.selected = null;
	}
	
	public void initialize(Object[][] spec)
	{
		initialize();
		this.data = new ArrayList();
		this.displayToValue = new HashMap();
		this.valueToDisplay = new HashMap();
		this.selected = null;
		
		if (null != spec)
		{
			this.data = new ArrayList(spec.length);
			this.displayToValue = new HashMap();
			
			for (int i = 0; i < spec.length; i++)
			{
				Object[] row = spec[i];
				add(row[0], row[1]);
			}
		}
	}
	
	
	protected void initialize(List list)
	{
		
	}
	
	
	public void insert (int index, Object display, Object value)
	{
		this.data.add(index, display);
		this.displayToValue.put(display, value);
		this.valueToDisplay.put(value, display);
	}
	
	
	public void add (Object display, Object value)
	{
		this.data.add(display);
		this.displayToValue.put(display, value);
		this.valueToDisplay.put(value, display);
	}
	
	public Object getElementAt(int index)
	{
		return this.data.get(index);
	}

	public int getSize()
	{
		return this.data.size();
	}

	public Object getSelectedItem()
	{
		return this.selected;
	}

	public void setSelectedItem(Object anItem)
	{
		this.selected = anItem;
	}
	
	public void setSelectedValue (Object value)
	{
		Object key = valueToDisplay(value);
		
		if (null != key)
			setSelectedItem(key);
	}
	
	
	public Object displayToValue (Object key)
	{
		return this.displayToValue.get(key);
	}
	
	public Object valueToDisplay (Object key)
	{
		return this.valueToDisplay.get(key);
	}

	protected List getValues()
	{
		List list = new ArrayList(valueToDisplay.keySet());
		return list;
	}
}
