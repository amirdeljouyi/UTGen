package com.lts.application.swing.error;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lts.ExceptionUtil;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleGridBagConstraint;
import com.lts.swing.layout.WindowUtils;
import com.lts.swing.thread.BlockingThread;

@SuppressWarnings("serial")
public class StackTracePanel extends LTSPanel
{
	public StackTracePanel(JFrame win, Throwable throwable, String message)
		throws Exception
	{
		initialize(win, throwable, message);
	}

	public static void showException (Throwable t)
	{
		String msg = "The application encountered an error";		
		showException(t, msg);
	}
	
	
	public static StackTracePanel showException (Throwable throwable, String message)
	{
		try
		{
			JFrame win = new JFrame();
			StackTracePanel pane = new StackTracePanel(win, throwable, message);		
			BlockingThread.staticDisplayAndWait(win);
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
			System.err.println("Error trying to display an exception.  Exception thrown during attempt to display:");
			current.printStackTrace(System.err);
		}
		catch (RuntimeException e)
		{
			// System.err failed!  Nothing we can do at this point
		}
	}


	private void initialize(JFrame win, Throwable throwable, String message)
		throws Exception
	{
		win.setLayout(new GridBagLayout());
		GridBagConstraints cons = SimpleGridBagConstraint.fillConstraint(0, 0, 5);
		win.add(this, cons);
		
		initializeStackTrace(win, throwable);
		WindowUtils.center(win);
	}


	private void initializeStackTrace(JFrame win, Throwable throwable) throws Exception
	{
		String title = "Error Details";
		win.setTitle(title);

		Font font = new Font("Ariel", Font.BOLD, 18);
		JLabel label = new JLabel(title);
		label.setFont(font);
		addCenteredLabel(label, 5);

		nextRow();

		String trace = ExceptionUtil.createStackTrace(throwable);

		JTextArea textArea = new JTextArea(trace);
		textArea.setLineWrap(false);
		JScrollPane jsp = new JScrollPane(textArea);
		addFill(jsp, 5);

		nextRow();

		ActionListener closeAction = new SimpleAction() {
			public void action() throws Exception {
				okButtonPressed();
			}
		};
		JButton okButton = new JButton("OK");
		okButton.addActionListener(closeAction);

		addButton(okButton,5);
		
		WindowUtils.resize(win, 2);
	}


	private void okButtonPressed()
	{
		getRootPane().getParent().setVisible(false);
	}
}
