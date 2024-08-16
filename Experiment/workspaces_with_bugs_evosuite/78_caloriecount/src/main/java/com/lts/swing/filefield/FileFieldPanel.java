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
package com.lts.swing.filefield;

import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.contentpanel.ContentPanel;
import com.lts.util.StringUtils;

/**
 * Essentially a dialog that contains a FileFieldPanel.
 * 
 * @author cnh
 */
public class FileFieldPanel extends ContentPanel
{
	/**
	 * To shut up eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	private String myHeading;
	
	public String getHeading()
	{
		return myHeading;
	}
	
	public void setHeading(String heading)
	{
		myHeading = heading;
	}
	
	
	private JLabel myHeadingLabel;
	
	public JLabel getHeadingLabel()
	{
		return myHeadingLabel;
	}
	
	
	private FileField myFileField;
	
	public FileField getFileField()
	{
		return myFileField;
	}
	
	
	private JLabel myFieldLabel;
	
	public JLabel getFieldLabel()
	{
		return myFieldLabel;
	}
	
	
	public JPanel createTopPanel()
	{
		LTSPanel panel = new LTSPanel();

		myHeadingLabel = new JLabel("Enter file name");
		panel.addCenteredLabel(myHeadingLabel);
		
		return panel;
	}
	
	
	public JPanel createCenterPanel ()
	{
		LTSPanel panel = new LTSPanel();

	
		myFieldLabel = new JLabel("File Name");
		panel.addLabel(myFieldLabel);
		
		myFileField = new FileField();
		panel.addHorizontal(myFileField, 5);
		
		return panel;
	}
	
	
	public int getBottomPanelMode()
	{
		return ContentPanel.BOTTOM_PANEL_OK_CANCEL;
	}
	

	public Dimension getWindowSize()
	{
		Dimension dim = toPercentOfScreen(0.25);
		
		return dim;
	}
	
	@SuppressWarnings("deprecation")
	public static String showInputDialog(String fileName) throws LTSException
	{
		FileFieldPanel panel = new FileFieldPanel();
		JDialog win = new JDialog();
		panel.initialize(win);
		
		fileName = StringUtils.trim(fileName);
		if (null != fileName)
			panel.getFileField().getTextField().setText(fileName);
			
		Dimension dim = win.getPreferredSize();
		Dimension preferred = win.getPreferredSize();
		dim.height = preferred.height;
		win.setSize(dim);
		
		win.setVisible(true);
		
		if (ContentPanel.RESULT_OK == panel.getResult())
			return panel.getFileField().getTextField().getText();
		else
			return null;
	}
	
	
	public void initializeWindow(Window window) throws LTSException
	{
		super.initializeWindow(window);
		centerWindow();
	}
	
	
	public void okButtonPressed()
	{
	}
}
