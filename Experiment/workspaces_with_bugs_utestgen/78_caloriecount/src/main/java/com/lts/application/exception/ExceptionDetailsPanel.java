//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application.exception;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.TextContentPanel;

public class ExceptionDetailsPanel extends TextContentPanel
{
	private static final long serialVersionUID = 1L;

	public ExceptionDetailsPanel (JDialog dialog, String title, String heading, String message)
		throws LTSException
	{
		initialize(dialog, title, heading, message);
	}
	
	
	public void initialize (JDialog dialog, String title, String heading, String message)
		throws LTSException
	{
		setWindowTitle(title);
		setHeadingString(heading);
		setMessage(message);

		initialize(dialog);
	}
	
	
	protected Dimension myOldSize;
	protected Point myOldPosition;
	
	public void maximizePanel ()
	{
		if (null != myOldSize && null != myOldPosition)
		{
			getWindow().setSize(myOldSize);
			getWindow().setLocation(myOldPosition);
			getWindow().validate();
			myOldSize = null;
			myOldPosition = null;
		}
		else
		{
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			myOldSize = getWindow().getSize();
			myOldPosition = getWindow().getLocation();
			
//			Point newPosition = new Point(0,0);
//			getWindow().setSize(dim);
//			getWindow().setLocation(newPosition);
			
			getWindow().setBounds(0, 0, dim.width, dim.height);
			Dimension winSize = getWindow().getSize();
			int x = (dim.width - winSize.width)/2;
			int y = (dim.height - winSize.height)/2;
			Point point = new Point(x,y);
			getWindow().setLocation(point);
			getWindow().validate();
		}	
	}
	
	
	public JPanel createBottomPanel ()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button;
		ActionListener listener;
		
		listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				okButtonPressed();
			}
		};
		button = new JButton("OK");
		button.addActionListener(listener);
		panel.addLabel(button,5);
		
		listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				maximizePanel();
			}
		};
		button = new JButton("Maximize");
		button.addActionListener(listener);
		panel.addLabel(button,5);
		
		return panel;
	}
	
}
