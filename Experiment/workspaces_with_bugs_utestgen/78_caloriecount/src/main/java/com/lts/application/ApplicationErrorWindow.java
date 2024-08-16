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
package com.lts.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.TextWindow;

@SuppressWarnings(value="serial")
public class ApplicationErrorWindow extends TextWindow
{
	private Throwable myException;
	
	public ApplicationErrorWindow () throws LTSException
	{}
	
	public ApplicationErrorWindow (String message, Throwable e)
		throws LTSException
	{
		initialize(message, e);
	}
	
	
	public void initialize (String message, Throwable e)
		throws LTSException
	{
		myException = e;
		initializeTW(TextWindow.TYPE_SIMPLE, "Application Error", message);
	}
	
	
	public static ApplicationErrorWindow createWindow (Throwable t, String message)
	{
		try
		{
			ApplicationErrorWindow win = new ApplicationErrorWindow(message, t);
			return win;
		}
		catch (LTSException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
		
	
	public String getHeadingString()
	{
		return "Application Error";
	}
	
	public void showDetails ()
	{
		if (null == myException)
			return;
		
		String msg = toDetailedMessage(myException);
		TextWindow.showText(msg);
	}
	
	
	public String objectToString (Object o)
	{
		if (null == o)
			return "null";
	
		String msg;
		
		try
		{
			msg = o.toString();
		}
		catch (Exception e)
		{
			msg = "<exception trying to print object>";
		}
		
		return msg;
	}
	
	
	public String toDetailedMessage (Throwable e)
	{
		StringWriter sw = new StringWriter(4096);
		PrintWriter out = new PrintWriter(sw);

		e.printStackTrace(out);
		out.println();
		
		Object[] data = null;
		
		if (e instanceof ApplicationException)
		{
			ApplicationException appEx = (ApplicationException) e;
			data = appEx.getData();
		}
		
		if (null != data)
		{
			for (int i = 0; i < data.length; i++)
			{
				out.print ("data[" + i + "] ");
				
				Object o = data[i];
				if (!(o instanceof Throwable))
					out.println (objectToString(data[i]));
				else
				{
					out.println();
					Throwable t = (Throwable) o;
					t.printStackTrace(out);
					out.println();
				}
			}
		}
		
		out.close();
		String msg = sw.toString();
		return msg;
	}
	
	
	public void okButtonClicked ()
	{
		if (exitOnClose())
			System.exit(0);
		else
			setVisible(false);
	}


	public JPanel createBottomPanel ()
	{
		if (null == myException)
			return super.createBottomPanel();
		
		LTSPanel panel = new LTSPanel();
	
		JButton okButton = new JButton ("OK");
		ActionListener alistener;
		alistener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				okButtonClicked();
			}
		};
		okButton.addActionListener(alistener);
		
		panel.addButton(okButton, 5);
		
		JButton detailsButton = new JButton("Details");
		alistener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showDetails();
			}
		};
		detailsButton.addActionListener(alistener);
		
		panel.addButton(detailsButton, 5);
		
		return panel;
	}
}
