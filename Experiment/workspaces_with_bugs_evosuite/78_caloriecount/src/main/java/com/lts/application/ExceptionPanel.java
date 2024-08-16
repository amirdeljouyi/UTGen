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

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.lts.LTSException;
import com.lts.Suspender;
import com.lts.application.swing.ApplicationTextPane;
import com.lts.event.Callback;
import com.lts.io.IOUtilities;
import com.lts.swing.LTSPanel;
import com.lts.swing.TextContentPanel;
import com.lts.swing.contentpanel.ContentPanel;

@SuppressWarnings(value="serial")
public class ExceptionPanel extends ContentPanel
{
	protected JTextPane textPane;
	protected Throwable throwable;
	protected Suspender suspend;
	protected String message;
	
	
	public Suspender getSuspend()
	{
		return suspend;
	}


	public void setSuspend(Suspender suspend)
	{
		this.suspend = suspend;
	}


	public JTextPane getTextPane()
	{
		return textPane;
	}


	public void setTextPane(JTextPane textPane)
	{
		this.textPane = textPane;
	}


	public Throwable getThrowable()
	{
		return throwable;
	}


	public void setThrowable(Throwable throwable)
	{
		this.throwable = throwable;
	}


	public ExceptionPanel (String message, Throwable ex, Window window)
		throws LTSException
	{
		initialize(message, ex, window);
	}
	
	
	public void initialize (String message, Throwable ex, Window window)
		throws LTSException
	{
		this.message = message;
		this.throwable = ex;
		
		initialize(window);
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(panel.getBackground());
		textPane.setText(this.message);
		
		StyledDocument doc = textPane.getStyledDocument();
		MutableAttributeSet standard = new SimpleAttributeSet();
		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, 0, standard, true);
		
		panel.addFill(textPane);
		
		return panel;
	}
	
	
	public void ok ()
	{
		this.setVisible(false);
		
		if (null != this.suspend)
			this.suspend.resume();
	}
	
	
	public String toMessage (Throwable t)
	{
		String msg = ApplicationTextPane.toMessage(t);
		
		Throwable basic = t;
		while (basic.getCause() != null)
			basic = basic.getCause();
		
		
		if (basic instanceof ApplicationException)
		{
			StringWriter sw = new StringWriter(msg.length());
			PrintWriter out = new PrintWriter(sw);
			out.print(msg);
			
			ApplicationException ae = (ApplicationException) basic;
			Object[] data = ae.getData();
			
			for (int i = 0; i < data.length; i++)
			{
				out.println("data[" + i + "] = " + data[i]);
			}
			
			IOUtilities.close(out);
			IOUtilities.close(sw);
			msg = sw.toString();
		}
		
		return msg;
	}
	
	public void details ()
	{
		try
		{
			String heading = "Exception";
			String msg = this.toMessage(this.throwable);
			int mode = ContentPanel.BOTTOM_PANEL_OK;
			
			TextContentPanel panel = new TextContentPanel(heading, mode, msg);
			JFrame win = new JFrame();
			panel.initialize(win);
			win.setAlwaysOnTop(true);
			win.setVisible(true);
		}
		catch (LTSException e)
		{
			//
			// Not a whole lot else we can do in this situation
			//
			e.printStackTrace();
		}
	}
	
	
	public JPanel createBottomPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button = new JButton("OK");
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ok();
			}
		};
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		button = new JButton("Details...");
		listener = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				details();
			}
		};
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		return panel;
	}
	
	
	public JDialog getDialog ()
	{
		return (JDialog) getWindow();
	}
	
	public JFrame getJFrame ()
	{
		return (JFrame) getWindow();
	}
	
	
	public static ExceptionPanel showException(Window win, String message, Throwable t,
			Callback callback)
	{
		try
		{
			ExceptionPanel panel = new ExceptionPanel(message, t, win);
			return panel;
		}
		catch (LTSException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	

}
