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
 * A class that will hopefully simplify the use of GridBagLayout.
 * 
 * <P>
 * This class simplifies using GridBagLayout by defining a number of 
 * constructors that take much of the details out of creating an instance of
 * GridBagConstrains.
 * 
 * <P>
 * The following "pre-created" constraints are available:
 * <UL>
 * <LI>label - left aligned, no weight, no fill
 * <LI>button - center aligned, no weight, no fill
 * <LI>comboBox - same as label
 * <LI>fillConstraint - center aligned, weight of 1, fill both
 * <LI>horizontalConstraint - center aligned, h-weight of 1, fill horizontal
 * </UL>
 * 
 * <P>
 * Each of these constraint types also has at least three variations:
 * <UL>
 * <LI>no insets
 * <LI>same inset for vertial and horizonal
 * <LI>different insets for vertial and horizontal
 * </UL>
 */

@SuppressWarnings("serial")
public class SimpleGridBagConstraint
	extends GridBagConstraints
{
	protected int myGridX;
	protected int myGridY;
	protected int myWidth;
	protected int myHeight;
	protected double myXWeight;
	protected double myYWeight;
	protected int myAnchor;
	protected int myFill;
	protected Insets myInsets;
	protected int myXPad;
	protected int myYPad;
	
	
	
	
	public SimpleGridBagConstraint()
	{
		setGridX(0);
		setGridY(0);
		setWidth(1);
		setHeight(1);
		setXWeight(1.0);
		setYWeight(1.0);
		setAnchor (GridBagConstraints.CENTER);
		setFill(GridBagConstraints.NONE);
		Insets ins = new Insets (0, 0, 0, 0);
		setInsets(ins);
		setXPad(0);
		setYPad(0);
	}
	
	
	public SimpleGridBagConstraint (
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
		/*
		GridBagConstraints(
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
		*/ 		
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
	
	public static SimpleGridBagConstraint buttonConstraint (
		int gridx, 
		int gridy,
		Insets insets
	)
	{
		return new SimpleGridBagConstraint(
			gridx,
			gridy,
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			0.0,	// vertical weight
			GridBagConstraints.CENTER, 	// anchor
			GridBagConstraints.NONE, 	// do not expand to fill the component's location
			insets,	// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
	}
	
	
	public static SimpleGridBagConstraint buttonConstraint (
		int gridx, 
		int gridy,
		int horizontalInset,
		int verticalInset
	)
	{
		Insets insets = new Insets(
			verticalInset,   // top
			horizontalInset, // left
			verticalInset,   // bottom
			horizontalInset  // right
		);
		
		return buttonConstraint(gridx, gridy, insets);
	}
		
	
	
	public static SimpleGridBagConstraint buttonConstraint (int gridx, int gridy)
	{
		return buttonConstraint(gridx, gridy, 0, 0);
	}
	
	public static SimpleGridBagConstraint buttonConstraint (
		int gridx, 
		int gridy, 
		int insets
	)
	{
		return buttonConstraint(gridx, gridy, insets, insets);
	}
	


	
	public static SimpleGridBagConstraint comboBoxConstraint (
		int gridx, 
		int gridy,
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
			
		return new SimpleGridBagConstraint(
			gridx,
			gridy,
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
	
	public static SimpleGridBagConstraint comboBoxConstraint (int gridx, int gridy)
	{
		return comboBoxConstraint (gridx, gridy, 0, 0);
	}


	public static SimpleGridBagConstraint comboBoxConstraint (
		int gridx, 
		int gridy, 
		int insets
	)
	{
		return comboBoxConstraint (gridx, gridy, insets, insets);
	}


	
	public static SimpleGridBagConstraint labelConstraint (
		int gridx, 
		int gridy,
		int horizontalInset,
		int verticalInset
	)
	{
		Insets insets = new Insets(
			verticalInset,   // top
			horizontalInset, // left
			verticalInset,   // bottom
			horizontalInset  // right
		);
		
		return labelConstraint (gridx, gridy, insets);
	}
	
	
	public static SimpleGridBagConstraint labelConstraint (
		int gridx,
		int gridy,
		Insets insets
	)
	{
		if (null == insets)
		{
			insets = new Insets(0,0,0,0);
		}
		
		return new SimpleGridBagConstraint(
			gridx,
			gridy,
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			0.0,	// vertical weight
			GridBagConstraints.WEST, 	// anchor
			GridBagConstraints.NONE, 	// do not expand to fill the component's location
			insets,	// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
	}
	
	
	
	public static SimpleGridBagConstraint labelConstraint (int gridx, int gridy)
	{
		return labelConstraint (gridx, gridy, 0, 0);
	}
	
	public static SimpleGridBagConstraint labelConstraint (int gridx, int gridy, int inset)
	{
		return labelConstraint (gridx, gridy, inset, inset);
	}
	
	
	
	public static SimpleGridBagConstraint insetConstraint (
		int gridx, 
		int gridy, 
		int inset
	)
	{
		return buttonConstraint(gridx, gridy, inset);
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
	public static SimpleGridBagConstraint fillConstraint (
		int gridx,
		int gridy,
		Insets insets
	)
	{
		return new SimpleGridBagConstraint (
			gridx,
			gridy,
			1,
			1,
			1.0,
			1.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.BOTH,
			insets,
			0,
			0
		);
	}
	
	public static SimpleGridBagConstraint fillConstraint (
		int gridx,
		int gridy,
		int horizontalInset,
		int verticalInset
	)
	{
		Insets insets = new Insets(
			verticalInset,   // top
			horizontalInset, // left
			verticalInset,   // bottom
			horizontalInset  // right
		);
		
		return fillConstraint (gridx, gridy, insets);
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
	public static SimpleGridBagConstraint fillConstraint (
		int gridx,
		int gridy,
		int insets
	)
	{
		return fillConstraint (gridx, gridy, insets, insets);
	}
	
	
	public static SimpleGridBagConstraint fillConstraint (int gridx, int gridy)
	{
		return fillConstraint(gridx, gridy, 0, 0);
	}
	
	
	public static SimpleGridBagConstraint verticalConstraint (
		int gridx,
		int gridy,
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
		
		return new SimpleGridBagConstraint (
			gridx,
			gridy,
			1,
			1,
			0.0,
			1.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.VERTICAL,
			i,
			0,
			0
		);
	}
	
	
	public static SimpleGridBagConstraint verticalConstraint (
			int gridx,
			int gridy,
			Insets insets
		)
	{
		return new SimpleGridBagConstraint (
			gridx,
			gridy,
			1,
			1,
			0.0,
			1.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.VERTICAL,
			insets,
			0,
			0
		);
	}
		
		

	public static SimpleGridBagConstraint verticalConstraint (
		int gridx,
		int gridy,
		int insets
	)
	{
		return verticalConstraint(gridx, gridy, insets, insets);
	}
	

	public static SimpleGridBagConstraint verticalConstraint (int gridx, int gridy)
	{
		return verticalConstraint(gridx, gridy, 0, 0);
	}
	
	
	public static SimpleGridBagConstraint horizontalConstraint (
		int gridx,
		int gridy,
		Insets insets
	)
	{
		return new SimpleGridBagConstraint (
			gridx,
			gridy,
			1,
			1,
			1.0,
			0.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.HORIZONTAL,
			insets,
			0,
			0
		);		
	}

			
	public static SimpleGridBagConstraint horizontalConstraint (
		int gridx,
		int gridy,
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
			
		return horizontalConstraint (gridx, gridy, i);
	}

			
	public static SimpleGridBagConstraint horizontalConstraint (
		int gridx,
		int gridy,
		int insets
	)
	{
		return horizontalConstraint(gridx, gridy, insets, insets);
	}
	

	public static SimpleGridBagConstraint horizontalConstraint (int gridx, int gridy)
	{
		return horizontalConstraint(gridx, gridy, 0, 0);
	}
	
	
	
	public static SimpleGridBagConstraint titleConstraint (
		int gridx,
		int gridy,
		int inset
	)
	{
		return new SimpleGridBagConstraint (
			gridx,
			gridy,
			1,
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
	
	public static SimpleGridBagConstraint titleConstraint (int gridx, int gridy)
	{
		return SimpleGridBagConstraint.titleConstraint(gridx, gridy, 0);
	}
	
	
	/**
	 * @return Returns the myAnchor.
	 */
	public int getAnchor()
	{
		return myAnchor;
	}

	/**
	 * @param myAnchor The myAnchor to set.
	 */
	public void setAnchor(int myAnchor)
	{
		this.myAnchor = myAnchor;
	}

	/**
	 * @return Returns the myFill.
	 */
	public int getFill()
	{
		return myFill;
	}

	/**
	 * @param myFill The myFill to set.
	 */
	public void setFill(int myFill)
	{
		this.myFill = myFill;
	}

	/**
	 * @return Returns the myGridX.
	 */
	public int getGridX()
	{
		return myGridX;
	}

	/**
	 * @param myGridX The myGridX to set.
	 */
	public void setGridX(int myGridX)
	{
		this.myGridX = myGridX;
	}

	/**
	 * @return Returns the myGridY.
	 */
	public int getGridY()
	{
		return myGridY;
	}

	/**
	 * @param myGridY The myGridY to set.
	 */
	public void setGridY(int myGridY)
	{
		this.myGridY = myGridY;
	}

	/**
	 * @return Returns the myHeight.
	 */
	public int getHeight()
	{
		return myHeight;
	}

	/**
	 * @param myHeight The myHeight to set.
	 */
	public void setHeight(int myHeight)
	{
		this.myHeight = myHeight;
	}

	/**
	 * @return Returns the myInsets.
	 */
	public Insets getInsets()
	{
		return myInsets;
	}

	/**
	 * @param myInsets The myInsets to set.
	 */
	public void setInsets(Insets myInsets)
	{
		this.myInsets = myInsets;
	}

	/**
	 * @return Returns the myWidth.
	 */
	public int getWidth()
	{
		return myWidth;
	}

	/**
	 * @param myWidth The myWidth to set.
	 */
	public void setWidth(int myWidth)
	{
		this.myWidth = myWidth;
	}

	/**
	 * @return Returns the myXPad.
	 */
	public int getXPad()
	{
		return myXPad;
	}

	/**
	 * @param myXPad The myXPad to set.
	 */
	public void setXPad(int myXPad)
	{
		this.myXPad = myXPad;
	}

	/**
	 * @return Returns the myXWeight.
	 */
	public double getXWeight()
	{
		return myXWeight;
	}

	/**
	 * @param myXWeight The myXWeight to set.
	 */
	public void setXWeight(double myXWeight)
	{
		this.myXWeight = myXWeight;
	}

	/**
	 * @return Returns the myYPad.
	 */
	public int getYPad()
	{
		return myYPad;
	}

	/**
	 * @param myYPad The myYPad to set.
	 */
	public void setYPad(int myYPad)
	{
		this.myYPad = myYPad;
	}

	/**
	 * @return Returns the myYWeight.
	 */
	public double getYWeight()
	{
		return myYWeight;
	}

	/**
	 * @param myYWeight The myYWeight to set.
	 */
	public void setYWeight(double myYWeight)
	{
		this.myYWeight = myYWeight;
	}
	
	
	public GridBagConstraints create ()
	{
		return new GridBagConstraints (
			getGridX(), 
			getGridY(), 
			getWidth(), 
			getHeight(), 
			getXWeight(), 
			getYWeight(),
			getAnchor(),
			getFill(),
			getInsets(),
			getXPad(),
			getYPad()
		);
	}
	
	
	public GridBagConstraints createButton (int x, int y)
	{
		GridBagConstraints cons = create();
		cons.gridx = x;
		cons.gridy = y;
		
		return cons;
	}
	
	
	public GridBagConstraints createButton (
		int x,
		int y,
		int insets
	)
	{
		GridBagConstraints cons = create();
		cons.gridx = x;
		cons.gridy = y;
		Insets ins = new Insets(insets, insets, insets, insets);
		cons.insets = ins;
		
		return cons;
	}

}
