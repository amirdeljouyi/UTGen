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
package com.lts.swing.editlist;

import javax.swing.JOptionPane;

import com.lts.swing.SimpleListModel;
import com.lts.swing.list.LTSList;
import com.lts.swing.list.ListListenerAdaptor;
import com.lts.util.StringUtils;

/**
 * A simple implementation of the ListListener interface.
 * 
 * <P>
 * This class assumes that it is managing a LTSList of string values.
 * 
 * <P>
 * This class takes the following actions when the specified events occur:
 * 
 * <UL>
 * <LI>create - show an input dialog and add the resulting value before the selected
 * location (if a value is selected), or at the end of the list (if no value is 
 * selected).
 * 
 * <LI>edit - If nothing is selected, then return.  Display the selected value in 
 * an edit dialog.  Take the resulting value and replace whatever was in the list with
 * the new value.  If the user hit cancel then do nothing.
 * 
 * <LI>delete - If nothing is selected, return.  Otherwise, remove the selected value 
 * from the list.
 * 
 * </UL>
 * 
 * @author cnh
 *
 */
public class EditListListener extends ListListenerAdaptor
{
	public EditListListener (LTSList list)
	{
		super(list);
	}
	
	
	public String editCreate (String message, String value)
	{
		if (null == value)
			value = JOptionPane.showInputDialog(null, message);
		else
			value = JOptionPane.showInputDialog(null, message, value);
		
		value = StringUtils.trim(value);
		return value;
	}
	
	
	public void create ()
	{
		String value = editCreate("Enter new value", null);
		if (null == value)
			return;
		
		int index = getList().getSelectedIndex();
		SimpleListModel model = (SimpleListModel) getList().getModel();
		if (-1 == index)
			model.addElement(value);
		else
			model.add(index + 1, value);
			
	}
	
	
	public void edit ()
	{
		int index = getList().getSelectedIndex();
		if (-1 == index)
			return;
		
		String value = (String) getList().getSelectedValue();
		value = editCreate ("Edit value", value);
		if (null == value)
			return;
		
		SimpleListModel model = (SimpleListModel) getList().getModel();
		model.set(index, value);
	}
	
	
	public void delete()
	{
		int index = getList().getSelectedIndex();
		if (-1 == index)
			return;
		
		SimpleListModel model = (SimpleListModel) getList().getModel();
		model.remove(index);
	}
}
