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

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * @author cnh
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
@SuppressWarnings("serial")
public class SimpleGBC extends GridBagConstraints 
{
	public SimpleGBC (
		int gridx, 
		int gridy, 
		int gridwidth, 
		int gridheight, 
		double weightx, 
		double weighty, 
		int anchor, 
		int fill, 
		Insets insets, 
		int ipadx, 
		int ipady
	) 
	{
		super(
			gridx, 
			gridy, 
			gridwidth, 
			gridheight, 
			weightx, 
			weighty,
			anchor,
			fill,
			insets,
			ipadx,
			ipady
		);
	}
	
	/**
	 * Create a constraint appropriate to a button or the like.
	 * 
	 * <P>
	 * This constructor creates a constraint with a "size" of 1 (width of one
	 * and height of one), zero weight, zero insets, zero padding and no fill.
	 */
	
	public static SimpleGBC button (int row, int col, int insets)
	{
		Insets i = new Insets(
			insets,   // top
			insets, // left
			insets,   // bottom
			insets  // right
		);
			
		return new SimpleGBC(
			row,
			col,
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			0.0,	// vertical weight
			GridBagConstraints.CENTER, 	// anchor
			GridBagConstraints.NONE, 	// do not expand to fill the component's location
			i,		// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
	}
	
	public static SimpleGBC button (int row, int col)
	{
		return button(row, col, 0);
	}
	


	
	public static SimpleGBC comboBox (
		int row, 
		int col,
		int horizontalInset,
		int verticalInset
	)
	{
		Insets i = new Insets(
			verticalInset,   // top
			horizontalInset, // left
			verticalInset,   // bottom
			horizontalInset  // right
		);
			
		return new SimpleGBC(
			row,
			col,
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			0.0,	// vertical weight
			GridBagConstraints.WEST, 	// anchor
			GridBagConstraints.NONE, 	// do not expand to fill the component's location
			i,		// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
	}
	
	public static SimpleGBC comboBox (int row, int col)
	{
		return comboBox (row, col, 0, 0);
	}


	public static SimpleGBC comboBoxConstraint (
		int row, 
		int col, 
		int insets
	)
	{
		return comboBox (row, col, insets, insets);
	}



	public static SimpleGBC none (int row, int col, int inset)
	{
		Insets i = new Insets(
			inset,   // top
			inset, // left
			inset,   // bottom
			inset  // right
		);
			
		return new SimpleGBC(
			row,
			col,
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			0.0,	// vertical weight
			GridBagConstraints.CENTER, 	// anchor
			GridBagConstraints.NONE, 	// do not expand to fill the component's location
			i,		// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
	}
	
	
	public static SimpleGBC none (int x, int y)
	{
		return none(x,y,0);
	}
	
	
	
	
	public static SimpleGBC label (
		int row, 
		int col,
		int horizontalInset,
		int verticalInset
	)
	{
		Insets i = new Insets(
			verticalInset,   // top
			horizontalInset, // left
			verticalInset,   // bottom
			horizontalInset  // right
		);
			
		return new SimpleGBC(
			row,
			col,
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			0.0,	// vertical weight
			GridBagConstraints.WEST, 	// anchor
			GridBagConstraints.NONE, 	// do not expand to fill the component's location
			i,		// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
	}
	
	
	
	public static SimpleGBC label (int row, int col)
	{
		return label (row, col, 0, 0);
	}
	
	public static SimpleGBC label (int row, int col, int inset)
	{
		return label (row, col, inset, inset);
	}
	
	
	
	public static SimpleGBC insetConstraint (
		int row, 
		int col, 
		int inset
	)
	{
		return label(row, col, inset);
	}
	
	/**
	 * Create a constraint that is suitable for a component that wants to fill 
	 * the area in the grid where it is placed.
	 * 
	 * <P>
	 * This method creates a gridbag constraint that has a horizontal and 
	 * vertical weight of 1 and that will fill the available space in the grid
	 * where it is placed.
	 */
	public static SimpleGBC fill (
		int row,
		int col,
		int inset,
		int width
	)
	{
		Insets i = new Insets(
			inset,   // top
			inset, // left
			inset,   // bottom
			inset  // right
		);
			
		return new SimpleGBC (
			row,
			col,
			width,
			1,
			1.0,
			1.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.BOTH,
			i,
			0,
			0
		);
	}
	
	
	/**
	 * Create a constraint that will fill the location in the grid where it is
	 * placed, using insets supplied by the caller.
	 * 
	 * <P>
	 * This method creates a gridbag constraint that has a horizontal and 
	 * vertical weight of 1 and that will fill the available space in the grid
	 * where it is placed.  The resulting constraint will also 
	 */
	public static SimpleGBC fill (
		int row,
		int col,
		int insets
	)
	{
		return fill (row, col, insets, 1);
	}
	
	
	public static SimpleGBC fill (int row, int col)
	{
		return fill(row, col, 0, 1);
	}
	
	
	public static SimpleGBC horizontal (
		int row,
		int col,
		int horizontalInset,
		int verticalInset
	)
	{
		Insets i = new Insets(
			verticalInset,   // top
			horizontalInset, // left
			verticalInset,   // bottom
			horizontalInset  // right
		);
			
		return new SimpleGBC (
			row,
			col,
			1,
			1,
			1.0,
			0.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.HORIZONTAL,
			i,
			0,
			0
		);		
	}

		
	public static SimpleGBC horizontal (
		int row,
		int col,
		int insets
	)
	{
		return horizontal(row, col, insets, insets);
	}
	

	public static SimpleGBC horizontal (int row, int col)
	{
		return horizontal(row, col, 0, 0);
	}
	
	
	
	public static SimpleGBC title (
		int x,
		int y,
		int inset,
		int width
	)
	{
		return new SimpleGBC (
			x,
			y,
			width,
			1,
			0.0,
			0.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.NONE,
			new Insets (inset, inset, inset, inset),
			0,
			0
		);
	}
	
	public static SimpleGBC title (int x, int y, int inset)
	{
		return title(x, y, inset, 1);
	}
	
	public static SimpleGBC title (int row, int col)
	{
		return SimpleGBC.title(row, col, 0, 1);
	}
	
	
}

