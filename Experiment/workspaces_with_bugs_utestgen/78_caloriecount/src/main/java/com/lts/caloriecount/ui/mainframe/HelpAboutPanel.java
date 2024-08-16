package com.lts.caloriecount.ui.mainframe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import com.lts.LTSException;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.swing.LTSPanel;

@SuppressWarnings("serial")
public class HelpAboutPanel extends CalorieCountPanel
{
	private JTextPane myTextArea;


	protected HelpAboutPanel()
	{}
	
	public HelpAboutPanel(Window win) throws LTSException
	{
		initialize(win);
	}
	
	
	protected String getViewName()
	{
		return "About";
	}

	public void initialize(Container container) throws LTSException
	{
		setBottomPanelMode(BOTTOM_PANEL_OK);
		
		super.initialize(container);
	}
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();

		String msg = "CalorieCount version 0.2.1, build: 20080611, \"Imperious Ant\"";
		JLabel label = new JLabel(msg);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		String text = getMiscText();
		myTextArea = new JTextPane();
		myTextArea.setText(text);
		myTextArea.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(myTextArea);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.addFill(jsp,5);

		return panel;
	}
	
	
	private String getMiscText()
	{
		String text = 
			"CalorieCount is copyright(c) 2008 by Long Term Software, LLC. and others, "
			+ "all rights reserved.\n"
			+ "\n"
			+ "This software contains code from the Swingx project "
			+ "(http://www.swinglabs.org), "
			+ "the Apache project "
			+ "(http://apache.org), and others.\n"
			+ "\n"
			+ "Find details and whatnot at http://caloriecount.sourceforge.net\n"
			+ "\n"
			+ "CalorieCount is distributed under the GNU Lesser Public License which can "
			+ "be found at http://www.gnu.org/licenses/lgpl.html\n"
			+ "\n"
			+ "Enjoy";
		
		return text;
	}

	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(400, 334);
	}

	
	public static void showDialog()
	{
		try
		{
			JDialog dialog = new JDialog();
			@SuppressWarnings("unused")
			HelpAboutPanel hap = new HelpAboutPanel(dialog);
			dialog.setVisible(true);
		}
		catch (LTSException e)
		{
			showException(e);
		}
	}
	
	
	public static void showAboutWindow()
	{
		try
		{
			JFrame frame = new JFrame();
			HelpAboutPanel hap = new HelpAboutPanel(frame);
			hap.showWindow();
		}
		catch (LTSException e)
		{
			showException(e);
		}
	}
}
