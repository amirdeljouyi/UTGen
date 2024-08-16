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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


/**
 * A JFrame that takes care of a bunch of common myTasks.
 * 
 * <P>
 * This class is intended to be used as a superclass for JFrames that follow
 * a common pattern.  That is, they have three panels: top, center and bottom.
 * The top and bottom panels fill the horizontal window, while the center panel
 * fills both horizontally and vertically.  The top panel has a label that 
 * proclaims the title of the window, and the bottom panel contains an OK and 
 * a Cancel button.  When either the OK or Cancel buttons are clicked, the 
 * window closes.
 */
@SuppressWarnings("serial")
public class StandardDialog
	extends JDialog
{
	protected JButton okButton;
	protected JButton cancelButton;
	protected JLabel heading;
	protected JPanel topPanel;
	protected JPanel centerPanel;
	protected JPanel bottomPanel;
	protected ActionListener myActionListener;
	protected boolean myChangesAccepted;
	
	
	
	public void acceptChanges()
	{
		setChangesAccepted(true);
		setVisible(false);
	}
	
	public void rejectChanges()
	{
		setChangesAccepted(false);
		setVisible(false);
	}
	
	
	public void performAction (Object src)
	{
		if (src == okButton)
			acceptChanges();
		else if (src == cancelButton)
			rejectChanges();		
	}
	
	
	public class StandardActionListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object src = event.getSource();
			performAction(src);
		}	
	}
	
	
	public ActionListener createActionListener()
	{
		return new StandardActionListener();
	}
	
	
	public ActionListener getActionListener()
	{
		if (null == myActionListener)
			myActionListener = createActionListener();
		
		return myActionListener;
	}


	public String getHeading()
	{
		return "";
	}
	
	
	public JPanel createTopPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc;
		heading = new JLabel(getHeading());
		gbc = SimpleGridBagConstraint.labelConstraint(0,0,5);
		panel.add(heading, gbc);
		
		return panel;
	}
	
	
	public JPanel createCenterPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		return panel;
	}
	
	
	public JButton createOKButton()
	{
		okButton = new JButton("OK");
		okButton.addActionListener(getActionListener());
		return okButton;
	}


	public JButton createCancelButton()
	{
		okButton = new JButton("Cancel");
		okButton.addActionListener(getActionListener());
		return okButton;
	}
	
	
	public JPanel createBottomPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc;
		
		okButton = new JButton("OK");
		okButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(0,0,5);
		panel.add(okButton, gbc);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(1,0,5);
		panel.add(cancelButton, gbc);
		
		return panel;
	}
	
	
	public void addOKButton (JPanel p)
	{
		GridBagConstraints gbc;
		
		okButton = new JButton("OK");
		okButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(0,0,5);
		p.add(okButton, gbc);
	}
	
	public void addCancelButton (JPanel p)
	{
		GridBagConstraints gbc;
		
		okButton = new JButton("Cancel");
		okButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(1,0,5);
		p.add(okButton, gbc);
	}
	
	public static final int OK = 0;
	public static final int OK_CANCEL = 1;
	
	public JPanel createBottomPanel (int config)
	{
		JPanel p = new JPanel(new GridBagLayout());
		
		addOKButton(p);
		
		if (OK_CANCEL == config)
			addCancelButton(p);
			
		return p;
	}
	
	
	public JMenuBar createMenuBar()
	{
		return null;
	}
	
	
	
	public GridBagConstraints getTopPanelConstraints()
	{
		return SimpleGridBagConstraint.horizontalConstraint(0,0,5);
	}
	
	public void initialize()
	{
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc;
		
		topPanel = createTopPanel();
		gbc = getTopPanelConstraints();
		getContentPane().add(topPanel, gbc);
		
		centerPanel = createCenterPanel();
		gbc = SimpleGridBagConstraint.fillConstraint(0,1,5);
		getContentPane().add(centerPanel, gbc);
		
		bottomPanel = createBottomPanel();
		gbc = SimpleGridBagConstraint.horizontalConstraint(0,2,5);
		getContentPane().add(bottomPanel, gbc);
		
		JMenuBar menuBar = createMenuBar();
		if (null != menuBar)
			setJMenuBar(menuBar);
		
		setSize(getDefaultSize());
		centerWindow();
		setModal(true);
		JDialogUtil.setAlwaysOnTop(this, true);
	}
	
	
	/**
	 * The default dialog takes up one quarter of the screen with a minimum 
	 * of 500 pixels in width and 300 pixels in height.
	 *   
	 * @return The dimensions for the dialog (see above).
	 */
	public Dimension getDefaultSize()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		int newWidth = (int) (d.getWidth() * 0.25);
		if (newWidth < 500)
			newWidth = 500;
			
		int newHeight = (int) (d.getHeight() * 0.25);
		if (newHeight < 300)
			newHeight = 300;
		
		d = new Dimension(newWidth, newHeight);
		return d;
	}
	
	
	public static Dimension toDimension (
		double widthPercent,
		int minWidth,
		double heightPercent,
		int minHeight
	)
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		int newWidth = (int) (d.getWidth() * widthPercent);
		if (newWidth < minWidth)
			newWidth = minWidth;
			
		int newHeight = (int) (d.getHeight() * heightPercent);
		if (newHeight < minHeight)
			newHeight = minHeight;
		
		d = new Dimension(newWidth, newHeight);
		return d;		
	}
		
	public StandardDialog()
	{}
	
	public StandardDialog (JFrame win)
	{
		super(win);
	}
	
	/**
	 * Center the frame on the screen.
	 */
	public void centerWindow()
	{
	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		int screenWidth = (int) d.getWidth();
		int screenHeight = (int) d.getHeight();
		
		int x = (screenWidth - getWidth())/2;
		int y = (screenHeight - getHeight())/2;
		setLocation(x,y);
	}
	

	/**
	 * Resize the frame to some percentage of the screen size in terms of both 
	 * width and height.
	 */	
	public void resizeTo(int percentOfScreen)
	{
		resizeTo((double) percentOfScreen/100);
	}
	
	
	public void resizeTo(double fractionOfScreen)
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		int newWidth = (int) (d.getWidth() * fractionOfScreen);
		int newHeight = (int) (d.getHeight() * fractionOfScreen);
		setSize(newWidth, newHeight);
	}
	
	public void setHeading (String s)
	{
		heading.setText(s);
	}
	
	
	public boolean getChangesAccepted()
	{
		return myChangesAccepted;
	}
	
	public void setChangesAccepted (boolean b)
	{
		myChangesAccepted = b;
	}
	
	public boolean changesAccepted()
	{
		return getChangesAccepted();
	}
	
	
	public void setVisible(boolean visible)
	{
		if (visible)
			setChangesAccepted(false);
		
		super.setVisible(visible);
	}
}
