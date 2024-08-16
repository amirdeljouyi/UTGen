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
 * Copyright (c) 2003 Long Term Software, LLC.
 */
package com.lts.swing.filefield;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.lts.swing.LTSPanel;

/**
 * A GUI component that combines a text field and a button for the purpose
 * of specifying a file name.
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class FileField extends LTSPanel
{
	protected JTextField myTextField;
	protected JButton myButton;
	protected JFileChooser myFileChooser;
	protected int myBrowseMode;
	
	public JButton getButton()
	{
		return myButton;
	}
	
	public void setButton (JButton b)
	{
		myButton = b;
	}
	
	
	public JTextField getTextField()
	{
		return myTextField;
	}
	
	public void setTextField (JTextField f)
	{
		myTextField = f;
	}
	
	
	public JFileChooser getFileChooser()
	{
		if (null == myFileChooser)
			myFileChooser = new JFileChooser();
			
		return myFileChooser;
	}
	
	public void setFileChooser (JFileChooser jfc)
	{
		myFileChooser = jfc;
	}
	
	public int getBrowseMode()
	{
		return myBrowseMode;
	}
	
	public void setBrowseMode (int mode)
	{
		myBrowseMode = mode;
	}
	
	
	public FileField ()
	{
		initialize();
	}
	
	public void browseForFile()
	{
		getFileChooser().setFileSelectionMode(getBrowseMode());
		int choice = getFileChooser().showOpenDialog(this);
		if (JFileChooser.APPROVE_OPTION == choice)
		{
			File f = getFileChooser().getSelectedFile();
			getTextField().setText(f.toString());
		}
	}
	
	
	public void initialize()
	{
		JTextField f = new JTextField();
		setTextField(f);
		addHorizontal (f, 5);
		
		JButton b = new JButton("...");
		ActionListener l = new ActionListener()
		{
			public void actionPerformed (ActionEvent e)
			{
				browseForFile();
			}
		};
		
		setButton(b);
		b.addActionListener(l);
		
		addButton (b, 5);
	}
	
	
	public File getFile ()
	{
		String s = getTextField().getText();
		File f = new File(s);
		return f;
	}
	
	
	public void setFile (File f)
	{
		String s = f.toString();
		getTextField().setText(s);
	}
}
