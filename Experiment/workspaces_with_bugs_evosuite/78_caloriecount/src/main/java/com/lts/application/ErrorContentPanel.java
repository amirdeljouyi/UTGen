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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationTextPane;
import com.lts.event.Callback;
import com.lts.swing.JDialogUtil;
import com.lts.swing.LTSPanel;
import com.lts.swing.TextContentPanel;
import com.lts.swing.contentpanel.ContentPanel;

/**
 * Used to display errors in the application framework.
 * 
 * @author cnh
 */
@SuppressWarnings(value="serial")
public class ErrorContentPanel extends ContentPanel
{
	protected String myMessage;
	protected Throwable myException;
	protected JDialog myDialog;
	
	
	public ErrorContentPanel (String msg, Throwable t)
	{
		super();
		myMessage = msg;
		myException = t;
	}
	
	
	public JPanel createCenterPanel ()
	{
		LTSPanel panel = new LTSPanel();
		
		JTextArea area = new JTextArea();
		area.setText(myMessage);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		area.setEditable(false);
		Color color = panel.getBackground();
		area.setBackground(color);
		
		panel.addFill(area, 5);
		
		return panel;
	}
	
	
	public JDialog showExceptionDialog ()
	{
		try
		{
			JDialog win = new JDialog();
			TextContentPanel panel;
			
			int mode = ContentPanel.BOTTOM_PANEL_OK;
			String msg = ApplicationTextPane.toMessage(myException);
			panel = new TextContentPanel("Exception", mode, msg);
			panel.initialize(win);
			win.setModal(true);
			JDialogUtil.setAlwaysOnTop(win, true);
			
			return win;
		}
		catch (LTSException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	protected static class CloseListener extends WindowAdapter
	{
		public Callback callback;
		
		public CloseListener (Window window, Callback callback)
		{
			this.callback = callback;
			window.addWindowListener(this);
		}
		
		public void windowClosed (WindowEvent event)
		{
			this.callback.callback(null);
		}
	}
	
	
	protected static class ActionThread implements Runnable
	{
		public Window window;
		public Callback callback;
		
		public ActionThread (Window window, Callback callback)
		{
			this.window = window;
			this.callback = callback;
		}
		
		public void run ()
		{
			try
			{
				wait();
				this.callback.callback(null);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		public static void startWaiting (Window window, Callback callback)
		{
			Thread thread = new Thread(new ActionThread(window, callback));
			thread.start();
		}
	}
	
	
	public void showDetails ()
	{
		try
		{
			String heading = "Exception";
			String msg = ApplicationTextPane.toMessage(myException);
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
	
	
	
	public JPanel createBottomPanel ()
	{
		LTSPanel panel = new LTSPanel();

		JButton button = new JButton("OK");
		ActionListener listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				closeWindow();
			}
		};
		button.addActionListener(listener);
		
		panel.addButton(button,5);
		
		button = new JButton("Show Details");
		listener = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				showDetails();
			}
		};
		button.addActionListener(listener);
		
		if (null != myException)
			panel.addButton(button,5);
		
		return panel;
	}
	
	
	public static void showException (Throwable t)
	{
		showException("", t);
	}
	
	
	
	public static void showException (String msg, Throwable t)
	{
		try
		{
			ContentPanel panel = new ErrorContentPanel(msg, t);
			JFrame win = new JFrame();
			panel.initialize(win);

			win.setAlwaysOnTop(true);
			win.setVisible(true);
			
			panel.waitForClose(win);
			
		}
		catch (LTSException e)
		{
			JOptionPane.showMessageDialog(null, "Could not display error");
		}
	}
	
	
	public Dimension getWindowSize()
	{
		Dimension dim = toPercentOfScreen(0.25);
		return dim;
	}

}
