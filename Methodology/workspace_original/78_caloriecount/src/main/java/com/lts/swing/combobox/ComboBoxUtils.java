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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ComboBoxUtils
{
	public static ComboBoxModel createModel (String[][] spec)
	{
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		
		List list = new ArrayList(spec.length);
		Map map = new HashMap();
		for (int i = 0; i < spec.length; i++)
		{
			String[] row = spec[i];
			map.put(row[0], row[1]);
			list.add(row[0]);
		}
		
		return model;
	}
	
	
	public static boolean setSelectedCaseless(String s, JComboBox cbox)
	{
		ComboBoxModel model = cbox.getModel();
		int count = model.getSize();
		for (int i = 0; i < count; i++)
		{
			Object o = model.getElementAt(i);
			if (s.equalsIgnoreCase(o.toString()))
			{
				model.setSelectedItem(o);
				return true;
			}
		}
		
		return false;
	}
}
