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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import com.lts.LTSException;
import com.lts.swing.contentpanel.ContentPanel;

public class TextContentPanel extends ContentPanel
{
	private static final long serialVersionUID = 1L;
	
	private JTextComponent myTextComponent;
	private String myMessage;
	
	public String getMessage()
	{
		return myMessage;
	}
	
	public void setMessage (String message)
	{
		myMessage = message;
	}
	
	
	public TextContentPanel(String heading, int controlMode, String message)
			throws LTSException
	{
		super(null, heading, controlMode);
		setMessage(message);
	}
	
	
	protected TextContentPanel()
	{}
	
	
	@SuppressWarnings("deprecation")
	public void initialize(JFrame frame) throws LTSException
	{
		myTextComponent = new JTextArea();
		myTextComponent.setText(getMessage());
		myTextComponent.setCaretPosition(0);
		super.initialize(frame);
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		myTextComponent.setEditable(false);
		JScrollPane jsp = new JScrollPane(myTextComponent);
		panel.add(jsp, SimpleGridBagConstraint.fillConstraint(0,0));
		return panel;
	}
	
	
	public static int showPromptDialog(int mode, String heading, String message)
			throws LTSException
	{
		JDialog win = new JDialog();
		TextContentPanel panel;
		panel = new TextContentPanel(heading, mode, message);
		panel.initialize(win);
		panel.initializeWindow(win);
		win.setModal(true);
		JDialogUtil.setAlwaysOnTop(win, true);
		win.setVisible(true);
		return panel.getResult();
	}
	
	
	
	public static int showOkCancel(String heading, String message)
	{
		try
		{
			int mode = ContentPanel.BOTTOM_PANEL_OK_CANCEL;
			return showPromptDialog(mode, heading, message);
		}
		catch (LTSException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public static int showYesNo(String heading, String message)
	{
		try
		{
			int mode = ContentPanel.BOTTOM_PANEL_YES_NO;
			return showPromptDialog(mode, heading, message);
		}
		catch (LTSException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public static void showText (String heading, String message)
	{
		try
		{
			int mode = ContentPanel.BOTTOM_PANEL_OK;
			showPromptDialog(mode, heading, message);
		}
		catch (LTSException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
