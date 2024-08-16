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

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * @author cnh
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
@SuppressWarnings("serial")
public class GridBagPanel extends JPanel 
{
	protected int myRow;
	protected int myColumn;
	
	public int getRow()
	{
		return myRow;
	}
	
	public void setRow(int i)
	{
		myRow = i;
	}
	
	
	public int getColumn()
	{
		return myColumn;
	}
	
	public void setColumn (int col)
	{
		myColumn = col;
	}
	
	
	public GridBagPanel ()
	{
		super(new GridBagLayout());
		setRow(0);
		setColumn(0);
	}
	
	
	public void nextRow()
	{
		setRow(1 + getRow());
		setColumn(0);
	}
	
	public void nextColumn()
	{
		setColumn(1 + getColumn());
	}
	
	
	public void add (Component c, GridBagConstraints con, int width)
	{
		super.add(c, con);
		setColumn(width + getColumn());
	}
	
	public void add (Component c, GridBagConstraints con)
	{
		add(c, con, 1);
	}
	
	
	public void addLabel (Component c, int insets)
	{
		add(c, SimpleGBC.label(getColumn(), getRow(), insets));
	}
	
	public void addLabel (Component c)
	{
		addLabel(c, 0);
	}
	
	
	public void addHorizontal (Component c, int insets)
	{
		add(c, SimpleGBC.horizontal(getColumn(), getRow(), insets));
	}
	
	public void addHorizontal (Component c)
	{
		addHorizontal(c, 0);
	}
	
	
	public void addFill (Component c, int insets, int width)
	{
		add(c, SimpleGBC.fill(getColumn(), getRow(), insets, width), width);
	}
	
	public void addFill (Component c, int insets)
	{
		addFill(c, insets, 1);
	}
	
	public void addFill (Component c)
	{
		addFill(c, 0);
	}
	
	
	public void addButton (Component c, int insets)
	{
		add(c, SimpleGBC.button(getColumn(), getRow(), insets));
	}
	
	public void addButton (Component c)
	{
		add(c, 0);
	}
	
	
	public void addTitle (Component c, int insets, int width)
	{
		add(c, SimpleGBC.title(getColumn(), getRow(), insets, width), width);
	}
	
	public void addTitle (Component c, int insets)
	{
		addTitle(c, insets, 1);
	}
	
	public void addTitle (Component c)
	{
		addTitle(c, 0, 1);
	}
}
