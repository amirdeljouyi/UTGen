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
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * A JPanel that uses GridBagLayout and that "knows" about the current "row,"
 * "column," and how to create constraints for various types of widgets.
 * 
 * <P/>
 * When laying out a screen it is common for me to want to use some sort of 
 * variable for the row and column so that, if I decide to change the position 
 * of a widget or row of widgets, I can move the block of code that defines 
 * it without too much trouble.  For example:
 * 
 * <CODE>
 * <PRE>
 * int gridy = 0;
 * int gridx = 0;
 * JPanel p = new JPanel(GridBagLayout());
 * 
 * JLabel l = new JLabel("foo");
 * p.add(l, new SimpleGridBagConstraint(gridx, gridy, 5));
 * 
 * gridx++;
 * JTextField f = new JTextField();
 * p.add(f, new SimpleGridBagConstraint.horizontalConstraint(gridx, gridy, 5));
 * 
 * gridy++;
 * l = new JLabel("bar");
 * p.add(l, new SimpleGridBagConstraint(gridx, gridy, 5));
 * 
 * gridx++;
 * f = new JTextField();
 * p.add(f, new SimpleGridBagConstraint.horizontalConstraint(gridx, gridy, 5));
 * </PRE>
 * </CODE>
 * 
 * Furthermore, specifying similar values for GridBagConstraints instances is 
 * equally onerous.
 * 
 * <P/>
 * This class simplifies working with GridBagLayout by keeping track of a 
 * current x and y value as well as providing various methods that 
 * automagically create GridBagConstraints objects to take care of things like
 * labels and buttons.  
 * 
 * <P/>
 * For example, rather than the above block, I would use the following:
 * 
 * <CODE>
 * <PRE>
 * LTSPanel p = new LTSPanel();
 * 
 * JLabel l = new JLabel("foo");
 * p.addLabel(l,5);
 * JTextField f = new JTextField();
 * p.addHorizontal(f,5);
 * 
 * p.nextRow();
 * l = new Label("bar");
 * p.addLabel(l,5);
 * f = new JTextField();
 * p.addHorizontal(f,5);
 * ...
 * </PRE>
 * </CODE>
 * @author cnh
 */
@SuppressWarnings("serial")
public class LTSPanel extends JPanel 
{
	protected int myGridx = 0;
	protected int myGridy = 0;
	protected Insets myDefaultInsets;
	
	
	public void setDefaultInsets(int inset)
	{
		myDefaultInsets = new Insets(inset, inset, inset, inset);
	}
	
	public LTSPanel()
	{
		super(new GridBagLayout());
	}
	
	public int getGridx()
	{
		return myGridx;
	}
	
	public void setGridx(int x)
	{
		myGridx = x;
	}
	
	
	public int getGridy()
	{
		return myGridy;
	}
	
	public void setGridy(int y)
	{
		myGridy = y;
	}
	
	public void nextColumn()
	{
		myGridx++;
	}
	
	
	public void nextRow()
	{
		myGridx = 0;
		myGridy++;
	}
	
	
	public void addWithConstraint (JComponent comp, GridBagConstraints gbc)
	{
		gbc.gridx = getGridx();
		gbc.gridy = getGridy();
		
		add(comp, gbc);
		
		nextColumn();
	}
	
	public void addButton (JComponent comp, Insets insets)
	{
		SimpleGridBagConstraint con = 
			SimpleGridBagConstraint.buttonConstraint(getGridx(), getGridy(), insets);
		add(comp, con);
		nextColumn();
	}

	public void addButton (JComponent comp, int insets)
	{
		Insets i = new Insets(insets, insets, insets, insets);
		addButton(comp, i);
	}
	
	public void addButton (JComponent comp)
	{
		if (null == myDefaultInsets)
			addButton(comp, 0);
		else
			addButton(comp, myDefaultInsets);
	}
	
	
	public void addFill (JComponent comp, Insets insets)
	{
		SimpleGridBagConstraint con =
			SimpleGridBagConstraint.fillConstraint(getGridx(), getGridy(), insets);
		
		add(comp, con);
		nextColumn();
	}
	
	
	public void addFill (JComponent comp, int insets)
	{
		Insets i = new Insets(insets, insets, insets, insets);
		
		SimpleGridBagConstraint con =
			SimpleGridBagConstraint.fillConstraint(getGridx(), getGridy(), i);
		
		add(comp, con);
		nextColumn();
	}
	
	public void addFill (JComponent comp)
	{
		if (null == myDefaultInsets)
			addFill(comp, 0);
		else
			addFill(comp, myDefaultInsets);
	}
	
	
	public void addHorizontal (JComponent comp, int insets)
	{
		SimpleGridBagConstraint con =
			SimpleGridBagConstraint.horizontalConstraint(getGridx(), getGridy(), insets);
		
		add(comp, con);
		nextColumn();
	}
	
	public void addHorizontal (JComponent comp, Insets insets)
	{
		SimpleGridBagConstraint con = SimpleGridBagConstraint.horizontalConstraint(
			getGridx(),
			getGridy(),
			insets
		);
		add(comp, con);
		nextColumn();
	}
	
	public void addHorizontal (JComponent comp)
	{
		if (null != myDefaultInsets)
			addHorizontal(comp, myDefaultInsets);
		else
			addHorizontal(comp,0);
	}
	
	
	public void addVertical (JComponent comp, int insets)
	{
		SimpleGridBagConstraint con =
			SimpleGridBagConstraint.verticalConstraint(getGridx(), getGridy(), insets);
		
		add(comp, con);
		nextColumn();
	}
	
	
	public void addVertical (JComponent comp, Insets insets)
	{
		SimpleGridBagConstraint con =
			SimpleGridBagConstraint.verticalConstraint(getGridx(), getGridy(), insets);
		
		add(comp, con);
		nextColumn();
	}
	
	public void addVertical (JComponent comp)
	{
		if (null == myDefaultInsets)
			addVertical(comp, 0);
		else
			addVertical(comp, myDefaultInsets);
	}
	
	public void addLabel (JComponent comp, Insets insets)
	{
		SimpleGridBagConstraint con =
			SimpleGridBagConstraint.labelConstraint(getGridx(), getGridy(), insets);
		
		add(comp, con);
		nextColumn();
	}
	
	public void addLabel (JComponent comp, int insets)
	{
		Insets i = new Insets(insets, insets, insets, insets);
		addLabel(comp, i);
	}
	
	public void addLabel (JComponent comp)
	{
		addLabel(comp, myDefaultInsets);
	}
	
	
	public void addCenteredLabel (JComponent comp, Insets insets)
	{
		GridBagConstraints gbc = new GridBagConstraints (
			getGridx(),
			getGridy(),
			1,
			1,
			0.0,
			0.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.NONE,
			insets,
			0,
			0
		);
		
		add(comp, gbc);
		nextColumn();
	}
	
	
	public void addCenteredLabel (JComponent comp, int insets)
	{
		Insets i = new Insets(insets, insets, insets, insets);
		addCenteredLabel (comp, i);
	}
	
	
	public void addCenteredLabel (JComponent comp)
	{
		if (null == myDefaultInsets)
			addCenteredLabel(comp, 0);
		else
			addCenteredLabel(comp, myDefaultInsets);
	}
	
	
	public GridBagConstraints getConstraints (JComponent comp)
	{
		GridBagLayout gbl = (GridBagLayout) getLayout();
		GridBagConstraints gbc = gbl.getConstraints(comp);
		return gbc;
	}
	
	public void setConstraints (JComponent comp, GridBagConstraints gbc)
	{
		GridBagLayout gbl = (GridBagLayout) getLayout();
		gbl.setConstraints(comp, gbc);
	}
	
	public void setMaxWidth (JComponent comp)
	{
		GridBagConstraints gbc = getConstraints(comp);
		
		if (null != gbc)
		{
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			setConstraints(comp, gbc);
		}
	}
	
	
	public void addTitle (JComponent comp)
	{
		addCenteredLabel(comp);
		setMaxWidth(comp);
	}
	
	
	public void alignLeft (JComponent comp)
	{
		GridBagConstraints gbc = getConstraints(comp);
		
		if (null != gbc)
		{
			gbc.anchor = GridBagConstraints.WEST;
			setConstraints(comp, gbc);
		}
	}
	
	
	public void setFillHorizontal (JComponent comp)
	{
		GridBagConstraints gbc = getConstraints(comp);
		
		if (null != gbc)
		{
			gbc.fill = GridBagConstraints.HORIZONTAL;
			setConstraints(comp, gbc);
		}
	}
	
	
	public void alignRight (JComponent comp)
	{
		GridBagConstraints gbc = getConstraints(comp);
		
		if (null != gbc)
		{
			gbc.anchor = GridBagConstraints.EAST;
			setConstraints(comp, gbc);
		}
	}
	
	
	public void alignCenter (JComponent comp)
	{
		GridBagConstraints gbc = getConstraints(comp);
		
		if (null != gbc)
		{
			gbc.anchor = GridBagConstraints.CENTER;
			setConstraints(comp, gbc);
		}
	}
	
	
	public void alignTop (JComponent comp)
	{
		GridBagConstraints gbc = getConstraints(comp);
		if (null != gbc)
		{
			gbc.anchor = GridBagConstraints.NORTH;
			setConstraints(comp, gbc);
		}
	}
	
	
	public void addSimple(JComponent comp)
	{
		addLabel(comp);
	}
	
	
	public void addSimple(JComponent comp, int insets)
	{
		addLabel(comp,insets);
	}
	
	
	public Window getWindow()
	{
		Container container = getParent();
		
		while (null != container && container != container.getParent() && null != container.getParent())
		{
			container = container.getParent();
		}
		
		Window window = (Window) container;
		return window;
	}
	

	public boolean foundDialog(Container container)
	{
		return
			null == container
			|| container instanceof JDialog
			|| container == container.getParent()
			|| null == container.getParent();
	}
	
	
	public JDialog getDialog()
	{
		Container container = getParent();
		while (!foundDialog(container))
		{
			container = container.getParent();
		}
		
		return (JDialog) container;
	}
	
	public void closeWindow()
	{
		getWindow().setVisible(false);
	}
	
	public void openWindow()
	{
		getWindow().setVisible(true);
	}
}
