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
package com.lts.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListModel;

import com.lts.swing.keyboard.InputKey;

public class SwingUtils
{
	public enum CommonKeys
	{
		Enter,
		ControlQ,
		ControlX,
		ControlP,
		ControlC,
		ControlV,
		ControlW;
		
		
	}
	public static List toModelData (JList list)
	{
		List dataList = new ArrayList();
		
		ListModel model = list.getModel();
		for (int i = 0; i < model.getSize(); i++)
		{
			Object o = model.getElementAt(i);
			if (null != o)
				dataList.add(o);
		}
		
		return dataList;
	}
	
	
	public static List toStringList (JList list)
	{
		List strings = new ArrayList();
		
		ListModel model = list.getModel();
		int size = model.getSize();
		
		for (int i = 0; i < size; i++)
		{
			Object o = model.getElementAt(i);
			if (null == o)
				strings.add(o.toString());
		}
		
		return strings;
	}
	
	public static void mapKey(int condition, InputKey key, Action action, JComponent comp)
	{
		InputMap imap = comp.getInputMap(condition);
		imap.put(key.getKeyStroke(), key);
		ActionMap amap = comp.getActionMap();
		amap.put(key, action);
	}
	
	
	public static void mapKey(InputKey key, Action action, JComponent comp)
	{
		mapKey(JComponent.WHEN_FOCUSED, key, action, comp);
	}
	
	
	public static void mapKeyAsDefault(KeyStroke key, Action action, JComponent comp)
	{
		int condition = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
		InputMap imap = comp.getInputMap(condition);
		imap.put(key, key);
		ActionMap amap = comp.getActionMap();
		amap.put(key, action);
	}
	
	
	public static void setPreferredWidth(JComponent comp, int width)
	{
		Dimension dim = comp.getPreferredSize();
		dim.width = width;
		comp.setPreferredSize(dim);
	}

	public static void setBold(JComponent field)
	{
		Font font = field.getFont();
		font = new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize());
		field.setFont(font);
	}

}
