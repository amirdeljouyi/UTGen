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
package com.lts.application.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.display.ErrorContentPanel;

/**
 * Functionality for implementing "wizard" style windows.
 *
 * <H3>Abstract Class</H3>
 * This is an abstract class.  For the class to be instantiatable, subclasses
 * must implement the following methods:
 * <UL>
 * <LI>getWizardHeading - The heading that show be displayed in large font.
 * <LI>getWizardInstructions - Detailed instructions that should be displayed 
 * in smaller font below the heading.
 * </UL>
 * 
 * <H3>Recommended Overrides</H3>
 * While not required to create a concrete class, it is strongly recommended
 * that developers override the following methods:
 * 
 * <UL>
 * <LI>createCenterPanel - Create a panel that the user can input data into the
 * frame.
 * <LI>initialize - This method should call the superclass version of the method, 
 * and then one of initFirstScreen, initNextScreen or initFinishScreen.
 * </UL>
 * 
 * <H3>Description</H3>
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
public abstract class WizardFrame extends ApplicationContentPanel
{
	/**
	 * This is the more lengthy text that the window displays to explain to 
	 * the user what is required for the next step in the process.
	 * 
	 * @return Display text.
	 */
	public abstract String getWizardInstructions ();
	
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
	
	public abstract WizardFrame createNextFrame (Object sessionData)
		throws LTSException;
	
	public abstract WizardFrame createPreviousFrame (Object sessionData)
		throws LTSException;
	
	public abstract void reset (Object sessionData)
		throws LTSException;

	protected JButton myPreviousButton	= new JButton("Previous");
	protected JButton myNextButton 		= new JButton("Next");
	protected JButton myFinishButton	= new JButton("Finish");
	protected JButton myCancelButton	= new JButton("Cancel");
	protected Object mySessionData;
	
	protected WizardFrame myNextFrame;
	protected WizardFrame myPreviousFrame;
	
	protected int myAction;

	public static final int ACTION_NONE = 0;
	public static final int ACTION_NEXT = 1;
	public static final int ACTION_PREVIOUS = 2;
	public static final int ACTION_CANCEL = 3;
	public static final int ACTION_FINISH = 4;
	
	
	public WizardFrame (Object sessionData)
		throws LTSException
	{
		initialize(sessionData);
	}
	
	public WizardFrame ()
		throws LTSException
	{
		initialize(null);
	}
	
	
	public void initialize (Object sessionData)
		throws LTSException
	{
		mySessionData = sessionData;
		reset(sessionData);
	}
	
	
	public int getAction ()
	{
		return myAction;
	}
	
	public void setAction (int action)
	{
		myAction = action;
	}

	public Object getSessionData ()
	{
		return mySessionData;
	}
	
	public void setSessionData (Object data)
	{
		mySessionData = data;
	}
	
	
	public WizardFrame getNextFrame ()
		throws LTSException
	{
		if (null == myNextFrame)
		{
			myNextFrame = createNextFrame(getSessionData());
			myNextFrame.setPreviousFrame(this);
		}
		
		return myNextFrame;
	}
	
	public void setNextFrame (WizardFrame frame)
	{
		myNextFrame = frame;
	}
	
	
	public WizardFrame getPreviousFrame ()
		throws LTSException
	{
		if (null == myPreviousFrame)
		{
			myPreviousFrame = createPreviousFrame(getSessionData());
			myPreviousFrame.setNextFrame(this);
		}
		
		return myPreviousFrame;
	}
	
	public void setPreviousFrame (WizardFrame frame)
	{
		myPreviousFrame = frame;
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
		try
		{
			setAction(ACTION_PREVIOUS);
			getPreviousFrame().reset(getSessionData());
			setVisible(false);
			getPreviousFrame().setVisible(true);
		}
		catch (LTSException e)
		{
			String msg = "Error trying to initialize previous frame.";
			ErrorContentPanel.showException (msg, e);
		}
	}


	public void next()
	{
		try
		{
			setAction(ACTION_NEXT);
			getNextFrame().reset(getSessionData());
			setVisible(false);
			getNextFrame().setVisible(true);
		}
		catch (LTSException e)
		{
			ErrorContentPanel.showException("Error trying to initialize next frame.", e);
		}
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


	public JTextArea createMessageText()
	{
		JTextArea jta = new JTextArea(getWizardInstructions());
		
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
	
	
	public void initFirstScreen(boolean enableFinish)
	{
		getPreviousButton().setEnabled(true);
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

}
