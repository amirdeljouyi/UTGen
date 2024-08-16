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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.lts.LTSException;

/**
 * Functionality for implementing "wizard" style windows.
 *
 * <H3>Abstract Class</H3>
 * This is an abstract class.  For the class to be instantiatable, subclasses
 * must implement the following methods:
 * <UL>
 * <LI/>getWizardMessage
 * <LI/>getWizardHeading
 * <LI/>getDataPanel
 * </UL>
 * 
 * <P/>
 * The primary thing this class brings to the table is the notion of having a 
 * message area, some other input content, and then four buttons.  The 
 * buttons are previous, next, finish and cancel.  Subclasses of this 
 * class should probably implement those methods that correspond to the 
 * above buttons.
 * 
 * <H3>Note</H3>
 * Subclasses must call the initialize method to ensure that the various 
 * panels and whatnot are properly initialized.
 */
public abstract class WizardSwingWindow 
	extends WindowHelper
{
	/**
	 * This is the more lengthy text that the window displays to explain to 
	 * the user what is required for the next step in the process.
	 * 
	 * @return Display text.
	 */
	public abstract String getWizardMessage ();
	
	/**
	 * Return a string that will be displayed in large letters accross the 
	 * top of the window.
	 * 
	 * <P/>
	 * This is typically some short descriptive sentance like "select class"
	 * or the like.
	 * 
	 * @return The heading text.
	 */
	public abstract String getWizardHeading ();
	
	public WizardSwingWindow (JDialogSwingWindow win)
		throws LTSException
	{
		super(win);
	}


	protected JButton myPreviousButton;
	protected JButton myNextButton;
	protected JButton myFinishButton;
	protected JButton myCancelButton;
	
	protected int myAction;

	public static final int ACTION_NONE = 0;
	public static final int ACTION_NEXT = 1;
	public static final int ACTION_PREVIOUS = 2;
	public static final int ACTION_CANCEL = 3;
	public static final int ACTION_FINISH = 4;
	
	public int getAction ()
	{
		return myAction;
	}
	
	public void setAction (int action)
	{
		myAction = action;
	}

	/**
	 * @return Returns the myCancelButton.
	 */
	public JButton getCancelButton()
	{
		return myCancelButton;
	}


	/**
	 * @param myCancelButton The myCancelButton to set.
	 */
	public void setCancelButton(JButton myCancelButton)
	{
		this.myCancelButton = myCancelButton;
	}


	/**
	 * @return Returns the myFinishButton.
	 */
	public JButton getFinishButton()
	{
		return myFinishButton;
	}


	/**
	 * @param myFinishButton The myFinishButton to set.
	 */
	public void setFinishButton(JButton myFinishButton)
	{
		this.myFinishButton = myFinishButton;
	}


	/**
	 * @return Returns the myNextButton.
	 */
	public JButton getNextButton()
	{
		return myNextButton;
	}


	/**
	 * @param myNextButton The myNextButton to set.
	 */
	public void setNextButton(JButton myNextButton)
	{
		this.myNextButton = myNextButton;
	}


	/**
	 * @return Returns the myPreviousButton.
	 */
	public JButton getPreviousButton()
	{
		return myPreviousButton;
	}


	/**
	 * @param myPreviousButton The myPreviousButton to set.
	 */
	public void setPreviousButton(JButton myPreviousButton)
	{
		this.myPreviousButton = myPreviousButton;
	}


	public void previous()
	{
		setAction(ACTION_PREVIOUS);
	}


	public void next()
	{
		setAction(ACTION_NEXT);
	}


	public void cancel()
	{
		setAction(ACTION_CANCEL);
		setVisible(false);
	}


	public void finish()
	{
		setAction(ACTION_FINISH);
		setVisible(false);
	}


	public void performAction(Object src)
	{
		if (src == getPreviousButton())
			previous();
		else if (src == getNextButton())
			next();
		else if (src == getFinishButton())
			finish();
		else if (src == getCancelButton())
			cancel();
	}


	public GridBagConstraints getTopPanelConstraints()
	{
		// return SimpleGridBagConstraint.horizontalConstraint(0,0,5);
		
		Insets ins = new Insets (10, 10, 10, 10);
		GridBagConstraints cons = new SimpleGridBagConstraint (
				0,
				0,
				1,
				1,
				2.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				ins,
				0,
				0
		);		
		
		return cons;
	}


	public JPanel createTopPanel()
	{
		LTSPanel p = new LTSPanel();
		JLabel l;
		
		l = new JLabel(getWizardHeading());
		Font f = new Font("ariel", Font.BOLD, 20);
		l.setFont(f);
		p.addLabel(l, 5);
		p.nextRow();
		
		JTextArea jta = createMessageText();
		p.addHorizontal(jta, 5);
		
		return p;
	}


	public GridBagConstraints topPanelConstraints(int gridy)
	{
		return SimpleGridBagConstraint.horizontalConstraint(0, gridy, 5);
	}


	public GridBagConstraints centerPanelConstraints(int gridy)
	{
		
		Insets ins = new Insets(5, 5, 5, 5);
		
		GridBagConstraints cons = new GridBagConstraints(
			0,		// x
			1,		// y
			1, 		// width
			1,		// height
			0.0,	// horizontal weight 
			1.0,	// vertical weight
			GridBagConstraints.WEST, 	// anchor
			GridBagConstraints.VERTICAL, 	// do not expand to fill the component's location
			ins,	// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
		
		return cons;
		
		
		// return SimpleGridBagConstraint.fillConstraint(0, gridy, 5);
	}


	public JTextArea createMessageText()
	{
		JTextArea jta = new JTextArea(getWizardMessage());
		
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setDisabledTextColor(Color.BLACK);
		jta.setBackground(getBackground());
		jta.setEnabled(false);
		
		return jta;
	}


	public JPanel createBottomPanel()
	{
		LTSPanel p = new LTSPanel();
		
		JButton b;
		
		b = new JButton("Previous");
		b.setEnabled(false);
		setPreviousButton(b);
		b.addActionListener(getActionListener());
		p.addLabel(b, 5);
		
		b = new JButton("Next");
		b.setEnabled(true);
		setNextButton(b);
		b.addActionListener(getActionListener());
		p.addLabel(b, 5);
		
		b = new JButton("Finish");
		setFinishButton(b);
		b.addActionListener(getActionListener());
		b.setEnabled(false);
		p.addLabel(b, 5);
		
		b = new JButton("Cancel");
		setCancelButton(b);
		b.addActionListener(getActionListener());
		b.setEnabled(true);
		p.addLabel(b, 5);
		
		return p;
	}
	
	
	public GridBagConstraints bottomPanelConstrains (int gridy)
	{
		return SimpleGridBagConstraint.horizontalConstraint(0, gridy, 5);
	}
	
	
	public void initFirstScreen(boolean enableFinish)
	{
		getPreviousButton().setEnabled(false);
		getNextButton().setEnabled(true);
		getCancelButton().setEnabled(true);
		getFinishButton().setEnabled(enableFinish);
	}
	
	public void initNextScreen(boolean enableFinish)
	{
		getPreviousButton().setEnabled(true);
		getNextButton().setEnabled(true);
		getCancelButton().setEnabled(true);
		getFinishButton().setEnabled(enableFinish);
	}
	
	public void initFinishScreen ()
	{
		getPreviousButton().setEnabled(true);
		getNextButton().setEnabled(false);
		getCancelButton().setEnabled(true);
		getFinishButton().setEnabled(true);
	}


	
/*
	public GridBagConstraints createMessageConstraints()
	{
		Insets ins = new Insets(5, 5, 5, 5);
		
		GridBagConstraints cons = new GridBagConstraints(
			0,		// x
			0,		// y
			1, 		// width
			1,		// height
			1.0,	// horizontal weight 
			1.0,	// vertical weight
			GridBagConstraints.NORTHWEST, 	// anchor
			GridBagConstraints.HORIZONTAL, 		// expand to fill the component's location
			ins,	// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
		
		return cons;
	}

/*
	public GridBagConstraints createDataPanelConstraints()
	{
		Insets ins = new Insets(5, 5, 5, 5);
		
		GridBagConstraints cons = new GridBagConstraints(
			0,		// x
			1,		// y
			1, 		// width
			1,		// height
			1.0,	// horizontal weight 
			1.0,	// vertical weight
			GridBagConstraints.SOUTHWEST, 		// anchor
			GridBagConstraints.NONE, 	// expand to fill the component's location
			ins,	// insets
			0,		// no horizontal padding
			0		// no vertical padding
		);
		
		return cons;
	}


	
	public JPanel createCenterPanel()
	{
		JPanel p = new JPanel(new GridBagLayout());
		
//		JPanel msgPanel = createMessageSubPanel();
		//p.add(msgPanel, createMessageConstraints());
		
		// JPanel buttonPanel = getDataPanel();
		// p.add(buttonPanel, createDataPanelConstraints());
		
		
		return p;
	}
	*/
	
	
}
