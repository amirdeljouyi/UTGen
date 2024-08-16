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

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class SimpleComboBox extends JComboBox
{
	private static final long serialVersionUID = 1L;

	private SimpleComboBoxModel mySimpleModel;
	
	public SimpleComboBox(SimpleComboBoxModel model)
	{
		super(model);
	}
	
	
	public void setModel (ComboBoxModel model)
	{
		if (model instanceof SimpleComboBoxModel)
			mySimpleModel = (SimpleComboBoxModel) model;
		
		super.setModel(model);
	}
	
	public Object getSelectedValue()
	{
		if (null == mySimpleModel)
			return null;
		
		return mySimpleModel.getSelectedValue();
	}
	
	
	public void setSelectedValue (Object value)
	{
		mySimpleModel.setSelectedValue(value);
	}
	
	public int getSelectedInt()
	{
		Object o = getSelectedValue();
		if (null == o)
			return -1;
		else if (o instanceof Integer)
		{
			Integer ival = (Integer) o;
			return ival.intValue();
		}
		else if (o instanceof Long)
		{
			Long lval = (Long) o;
			return lval.intValue();
		}
		else if (o instanceof Short)
		{
			Short sval = (Short) o;
			return sval.intValue();
		}
		else if (o instanceof Byte)
		{
			Byte bval = (Byte) o;
			return bval.intValue();
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	
	public long getSelectedLong()
	{
		Object o = getSelectedValue();
		if (null == o)
			return -1;
		else if (o instanceof Integer)
		{
			Integer ival = (Integer) o;
			return ival.longValue();
		}
		else if (o instanceof Long)
		{
			Long lval = (Long) o;
			return lval.longValue();
		}
		else if (o instanceof Short)
		{
			Short sval = (Short) o;
			return sval.longValue();
		}
		else if (o instanceof Byte)
		{
			Byte bval = (Byte) o;
			return bval.longValue();
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	

}
