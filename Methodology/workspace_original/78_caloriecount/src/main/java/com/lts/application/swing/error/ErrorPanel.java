//  Copyright 2006, Clark N. Hobbie
//
//	  This file is part of the com.lts.application library.
//
//	  The com.lts.application library is free software; you can redistribute it
//	  and/or modify it under the terms of the Lesser GNU General Public License
//	  as published by the Free Software Foundation; either version 2.1 of the
//	  License, or (at your option) any later version.
//
//	  The com.lts.application library is distributed in the hope that it will be
//	  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//	  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//	  General Public License for more details.
//
//	  You should have received a copy of the Lesser GNU General Public License
//	  along with the com.lts.application library; if not, write to the Free
//	  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//	  02110-1301 USA
//
package com.lts.application.swing.error;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lts.application.ApplicationExceptionHandler;
import com.lts.event.SimpleAction;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleGridBagConstraint;
import com.lts.swing.layout.WindowUtils;
import com.lts.swing.thread.BlockThread;
import com.lts.swing.thread.BlockingThread;

/**
 * A relatively simple text window, used mostly to display exceptions.
 * <P>
 * This window uses one of three primary configurations:
 * </P>
 * <UL>
 * <LI>Show a default message, an OK button and a details button.
 * <LI>Show a question (text message), a yes button, a no button, and a details button.
 * <LI>Show a detailed exception: complete stack trace(s) and an OK button.
 * </UL>
 * 
 * <P>
 * In all cases, invoking this window will cause the calling thread to block until the 
 * window has been dismissed.
 * </P>
 * 
 * <P>
 * In all cases, ok button cases the window to close. The details button causes a new
 * window open, this one using an instance of this class in detailed exception mode.
 * </P>
 * <P>
 * The first form is used by calling {@link #showException(Throwable)}.  It simply 
 * displays a stock error message along with OK and Details.
 * </P>
 * 
 * <P>
 * The first form can also be invoked by calling {@link #showException(Throwable, String, int)}
 * and setting the mode parameter to {@link #MODE_OK_DETAILS}.
 * </P>
 * 
 * <P>
 * The second form is used by calling {@link #showException(Throwable, String, int)} and 
 * setting the mode parameter to {@link #MODE_YES_NO_DETAILS}.  In that form, the panel 
 * object returned get be interrogated by calling {@link #getResult()} to determine if 
 * the user clicked yes or no.  The window does not return until the user clicks one or 
 * the other.  Clicking details causes the detail window to display.
 * </P>
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class ErrorPanel extends LTSPanel
{	
	private int myResult = -1;
	private Throwable myException;

	public int getResult()
	{
		return myResult;
	}
	
	public ErrorPanel(JFrame win, Throwable throwable, String message, int mode)
			throws Exception
	{
		initialize(win, throwable, message, mode);
	}

	public static void showException(Throwable t)
	{
		String msg = "The application encountered an error";
		showException(false, t, msg, ApplicationExceptionHandler.MODE_OK_DETAILS);
	}

	public static ErrorPanel showException(boolean waitForClose, Throwable throwable,
			String message, int mode)
	{
		try
		{
			JFrame win = new JFrame();
			ErrorPanel pane = new ErrorPanel(win, throwable, message, mode);
			BlockThread thread = new BlockThread(win);
			
			if (waitForClose)
				BlockingThread.staticDisplayAndWait(win);
			else
				thread.display();
			
			return pane;
		}
		catch (Exception e)
		{
			emergencyLog(throwable, e);
			return null;
		}
	}

	/**
	 * Only used to log exceptions to standard out when showException fails.
	 * 
	 * @param throwable The original exception cause.
	 * @param e The exception thrown by showException
	 */
	private static void emergencyLog(Throwable original, Exception current)
	{
		try
		{
			System.err
					.println("Error trying to display an exception.  Exception thrown during attempt to display:");
			current.printStackTrace(System.err);
		}
		catch (RuntimeException e)
		{
			// System.err failed!  Nothing we can do at this point
		}
	}

	private void initialize(JFrame win, Throwable throwable, String message, int mode)
			throws Exception
	{
		myException = throwable;
		win.setLayout(new GridBagLayout());
		GridBagConstraints cons = SimpleGridBagConstraint.fillConstraint(0, 0, 5);
		win.add(this, cons);

		initializeDialog(win, throwable, message, mode);

		WindowUtils.center(win);
	}

	private void initializeDialog(JFrame win, Throwable throwable, String message,
			int mode)
	{
		String title = "Application Error";
		win.setTitle(title);

		JTextArea area = new JTextArea(message);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setBackground(getBackground());
		area.setEditable(false);

		JScrollPane jsp = new JScrollPane(area);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jsp.setBorder(null);

		addFill(jsp, 5);
		nextRow();
		addHorizontal(createButtonPanel(mode), 5);

		Dimension screen = WindowUtils.getScreenSize();
		Dimension maxSize = new Dimension(screen);
		maxSize.width = maxSize.width / 4;
		maxSize.height = maxSize.height / 5;
		win.setSize(maxSize);
		
		// area.setText(createMessage(throwable));
	}
	
	
	protected String createMessage(Throwable throwable)
	{
		String message;
		
		try
		{
			StringWriter writer = new StringWriter();
			PrintWriter out = new PrintWriter(writer);
			
			throwable.printStackTrace(out);
			
			out.close();
			message = writer.toString();
		}
		catch (Exception e)
		{
			message = "Failed to create stack trace";
		}
		
		return message;
	}

	private JPanel createButtonPanel(int mode)
	{
		LTSPanel panel = new LTSPanel();

		ActionListener closeAction = new SimpleAction() {
			public void action() throws Exception
			{
				okButtonPressed();
			}
		};
		JButton okButton = new JButton("OK");
		okButton.addActionListener(closeAction);

		ActionListener detailsAction = new SimpleThreadedAction() {
			public void action() throws Exception
			{
				detailsButtonPressed();
			}
		};
		JButton detailsButton = new JButton("Details");
		detailsButton.addActionListener(detailsAction);

		ActionListener yesAction = new SimpleAction() {
			public void action()
			{
				yesButtonPressed();
			}
		};
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(yesAction);

		ActionListener noAction = new SimpleAction() {
			public void action()
			{
				noButtonPressed();
			}
		};
		JButton noButton = new JButton("No");
		noButton.addActionListener(noAction);

		switch (mode)
		{
			case ApplicationExceptionHandler.MODE_OK:
				panel.addButton(okButton, 5);
				break;

			case ApplicationExceptionHandler.MODE_YES_NO_DETAILS:
				panel.addButton(yesButton, 5);
				panel.addButton(noButton, 5);
				panel.addButton(detailsButton, 5);
				break;

			case ApplicationExceptionHandler.MODE_OK_DETAILS:
			default:
				panel.addButton(okButton, 5);
				panel.addButton(detailsButton, 5);
				break;
		}

		return panel;
	}

	private void detailsButtonPressed()
	{
		StackTracePanel.showException(myException);
	}

	private void okButtonPressed()
	{
		myResult = ApplicationExceptionHandler.RESULT_OK;
		getRootPane().getParent().setVisible(false);
	}

	private void noButtonPressed()
	{
		myResult = ApplicationExceptionHandler.RESULT_NO;
		getRootPane().getParent().setVisible(false);
	}

	private void yesButtonPressed()
	{
		myResult = ApplicationExceptionHandler.RESULT_YES;
		getRootPane().getParent().setVisible(false);
	}

	public static int showAndAsk(String message, Throwable throwable, int mode)
	{
		try
		{
			JFrame win = new JFrame();
			ErrorPanel panel = new ErrorPanel(win, throwable, message, mode);
			BlockingThread.staticDisplayAndWait(win);
			
			return panel.getResult();
		}
		catch (Exception e)
		{
			emergencyLog(throwable, e);
			return ApplicationExceptionHandler.RESULT_ERROR;
		}
	}
}
