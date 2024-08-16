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


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.lts.LTSException;
import com.lts.event.ReturnKeyListener;
import com.lts.event.WindowClosedHelper;
import com.lts.event.WindowClosedListener;



/**
 * A JFrame that takes care of a bunch of common myTasks.
 * 
 * <P>
 * This class is intended to be used as a superclass for JFrames that follow
 * a common pattern.  That is, they have three panels: top, center and bottom.
 * The top and bottom panels fill the horizontal window, while the center panel
 * fills both horizontally and vertically.  The top panel has a label that 
 * proclaims the title of the window, and the bottom panel contains an OK and 
 * a Cancel button.  When either the OK or Cancel buttons are clicked, the 
 * window closes.
 * 
 * <P>
 * Subclasses may want to override the following methods:
 * <UL>
 * <LI>acceptChanges - called when the OK button is clicked
 * <LI>rejectChanges - called when the Cancel button is clicked
 * <LI>createTopPanel - called to create the top panel
 * <LI>createCenterPanel - called to create the center panel
 * <LI>createBottomPanel - creates the bottom panel
 * <LI>windowCloseRequest - called when the user tries to close the window 
 * via some method other than the OK or Cancel buttons.
 * <LI>createMenuBar - if you want the window to have a menu bar
 * <LI>showPopup - if you want to display a popup menu
 * </UL>
 * 
 */
@SuppressWarnings("serial")
public class StandardFrame
	extends JFrame
	implements WindowListener
{
	public class LocalListSelectionListener
		implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			listSelection(e.getFirstIndex(), e.getLastIndex());
		}
	}
	
	protected JButton myOkButton;
	protected JButton myCancelButton;
	protected JLabel myHeading;
	protected JPanel myTopPanel;
	protected JPanel myCenterPanel;
	protected JPanel myBottomPanel;
	protected ActionListener myActionListener;
	protected MouseAdapter myMouseAdapter;
	protected JPopupMenu myPopupMenu;
	protected boolean myExitOnClose = false;
	protected boolean myPromptBeforeExit = true;
	protected ReturnKeyListener myReturnKeyListener;
	protected String myHeadingString;
	protected ListSelectionListener myListSelectionListener;
	protected KeyListener myKeyListener;
	protected WindowClosedHelper myWindowClosedHelper
		= new WindowClosedHelper();
	
	public static final int OPTION_OK_CANCEL = 0;
	public static final int OPTION_CLOSE_ONLY = 1;
	public static final int OPTION_NOTHING = 2;
	public static final int OPTION_APPLY_CANCEL = 3;
	
	public static final int MODE_MAINFRAME = 0;
	public static final int MODE_NORMAL_WINDOW = 1;
	
	
	public void addWindowClosedListener (WindowClosedListener listener)
	{
		myWindowClosedHelper.addListener(listener);
	}
	
	public void fireWindowClosed ()
	{
		myWindowClosedHelper.fire(0, this);
	}
	
	
	public String getHeadingString()
	{
		return myHeadingString;
	}
	
	public void setHeadingString (String s)
	{
		myHeadingString = s;
	}
	
	
	public boolean exitOnClose()
	{
		return myExitOnClose;
	}
	
	public void setExitOnClose(boolean b)
	{
		myExitOnClose = b;
	}
	
	
	public boolean promptBeforeExit()
	{
		return myPromptBeforeExit;
	}
	
	public void setPromptBeforeExit(boolean b)
	{
		myPromptBeforeExit = b;
	}
	
	
	public int getBottomPanelForm()
	{
		return OPTION_OK_CANCEL;
	}
	
	
	public void acceptChanges()
	{
		setVisible(false);
		
		if (exitOnClose())
		{
			System.exit(0);
		}
	}
	
	public void rejectChanges()
	{
		setVisible(false);
		
		if (exitOnClose())
		{
			System.exit(0);
		}
	}
	
	
	public void performAction (Object src)
	{
		if (src == myOkButton)
			acceptChanges();
		else if (src == myCancelButton)
			rejectChanges();
	}
	
	
	public ActionListener createActionListener()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				performAction(event.getSource());
			}
		};
	}
	
	
	public String getWindowTitle()
	{
		return null;
	}
	
	public ActionListener getActionListener()
	{
		if (null == myActionListener)
			myActionListener = createActionListener();
		
		return myActionListener;
	}

	public JPanel createTopPanel()
	{
		if (null == getHeadingString() || "".equals(getHeadingString()))
			return null;
			
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc;
		myHeading = new JLabel(getHeadingString());
		gbc = SimpleGBC.title(0,0,5);
		panel.add(myHeading, gbc);
		
		return panel;
	}
	
	
	public JPanel createCenterPanel()
		throws LTSException
	{
		JPanel panel = new JPanel(new GridBagLayout());
		return panel;
	}
	

	public void createOkClosePanel(JPanel p)
	{
		myOkButton = new JButton("OK");
		myOkButton.addActionListener(getActionListener());
		p.add(myOkButton, SimpleGBC.button(0,0,5));
		
		myCancelButton = new JButton("Cancel");
		myCancelButton.addActionListener(getActionListener());
		p.add(myCancelButton, SimpleGBC.button(1,0,5));
	}
	
	
	public void createCloseOnly(JPanel p)
	{
		myCancelButton = new JButton("Close");
		myCancelButton.addActionListener(getActionListener());
		p.add(myCancelButton, SimpleGBC.button(0,0,5));
	}
	
	
	public void createApplyCancel(JPanel p)
	{
		myOkButton = new JButton("OK");
		myOkButton.addActionListener(getActionListener());
		p.add(myOkButton, SimpleGBC.button(0,0,5));
		
		myCancelButton = new JButton("Cancel");
		myCancelButton.addActionListener(getActionListener());
		p.add(myCancelButton, SimpleGBC.button(1,0,5));
	}
	
	
	public void buildBottomPanel (
		LTSPanel panel,
		String okButtonString, 
		String cancelButtonString
	)
	{
		if (null != okButtonString)
		{
			myOkButton = new JButton(okButtonString);
			myOkButton.addActionListener(getActionListener());
			panel.addButton(myOkButton, 5);
		}
		
		if (null != cancelButtonString)
		{
			myCancelButton = new JButton(cancelButtonString);
			myCancelButton.addActionListener(getActionListener());
			panel.addButton(myCancelButton, 5);
		}
	}
	
	
	public JPanel createBottomPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		switch (getBottomPanelForm())
		{
			case OPTION_OK_CANCEL :
				buildBottomPanel(panel, "OK", "Cancel");
				break;
				
			case OPTION_CLOSE_ONLY :
				buildBottomPanel(panel, "Close", null);
				break;
			
			case OPTION_APPLY_CANCEL :
				buildBottomPanel(panel, "Apply", "Cancel");
				break;
				
			case OPTION_NOTHING :
				panel = null;
				break;
		}
		
		return panel;
	}
	
	
	public JMenuBar createMenuBar() throws LTSException
	{
		return null;
	}
	
	public void initialize (int mode, String headerString, String windowTitle)
		throws LTSException
	{
		setHeadingString(headerString);
			
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc;
		
		int gridy = 0;
		
		myTopPanel = createTopPanel();
		if (null != myTopPanel)
		{
			gbc = SimpleGridBagConstraint.horizontalConstraint(0,gridy,5);
			getContentPane().add(myTopPanel, gbc);
			gridy++;
		}
		
		myCenterPanel = createCenterPanel();		
		if (null != myCenterPanel)
		{
			gbc = SimpleGridBagConstraint.fillConstraint(0,gridy,5);
			getContentPane().add(myCenterPanel, gbc);
			gridy++;
		}
		
		myBottomPanel = createBottomPanel();
		if (null != myBottomPanel)
		{
			gbc = SimpleGridBagConstraint.horizontalConstraint(0,gridy,5);
			getContentPane().add(myBottomPanel, gbc);
			gridy++;
		}

		setPopupMenu(createPopupMenu());
		
		JMenuBar menuBar = createMenuBar();
		if (null != menuBar)
			setJMenuBar(menuBar);
		
		if (null == windowTitle)
			windowTitle = getWindowTitle();
		
		if (null != windowTitle)
			setTitle(windowTitle);


		switch(mode)
		{
			case MODE_MAINFRAME :
				setExitOnClose(true);
				setPromptBeforeExit(true);
				break;
			
			case MODE_NORMAL_WINDOW :
				setExitOnClose(false);
				setPromptBeforeExit(false);
				break;			
		}
		
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new LTSWindow());
		setSize(getDefaultSize());
		centerWindow();
	}
	

	public int getMode()
	{
		return MODE_NORMAL_WINDOW;
	}
	
	
	public void initialize() throws LTSException
	{
		initialize (getMode(), null, null);
	}
	
	
	
	public static Dimension toDimension (
		double widthPercent,
		int minWidth,
		double heightPercent,
		int minHeight
	)
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		int newWidth = (int) (d.getWidth() * widthPercent);
		if (newWidth < minWidth)
			newWidth = minWidth;
			
		int newHeight = (int) (d.getHeight() * heightPercent);
		if (newHeight < minHeight)
			newHeight = minHeight;
		
		d = new Dimension(newWidth, newHeight);
		return d;		
	}
		

	public Dimension getDefaultSize()
	{
		return toDimension(0.5, 500, 0.5, 300);
	}
	
	
	public StandardFrame()
	{}
	
	/**
	 * Center the frame on the screen.
	 */
	public void centerWindow()
	{
	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		int x = (d.width - getWidth())/2;
		int y = (d.height - getHeight())/2;
		
		setLocation(x,y);
	}
	

	/**
	 * Resize the frame to some percentage of the screen size in terms of both 
	 * width and height.
	 */	
	public void resizeTo(int percentOfScreen)
	{
		resizeTo((double) percentOfScreen/100);
	}
	
	
	public void resizeTo(double fractionOfScreen)
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		int newWidth = (int) (d.getWidth() * fractionOfScreen);
		int newHeight = (int) (d.getHeight() * fractionOfScreen);
		setSize(newWidth, newHeight);
	}
	
	public void setHeading (String s)
	{
		if (null != s)
		{
			if (null == myHeading)
				myHeading = new JLabel(s);
			else
				myHeading.setText(s);

			String title = getTitle();
			if (null == title || "".equals(title))
				setTitle(s);
		}
	}
	
	
	public void windowCloseRequest()
	{
		if (!exitOnClose())
			rejectChanges();
		else
		{
			boolean quit = false;
			
			if (!promptBeforeExit())
				quit = true;
			else
			{
				int result = JOptionPane.showConfirmDialog(this, "Quit Application?");
				quit = (JOptionPane.OK_OPTION == result);
			}
			
			if (quit)
				quit();
		}
	}
	
	class LTSWindow extends WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			windowCloseRequest();
		}
	}
	
	
	public JPopupMenu createPopupMenu()
		throws LTSException
	{
		return null;
	}
	
	public JPopupMenu getPopupMenu()
	{
		return myPopupMenu;
	}
	
	public void setPopupMenu(JPopupMenu menu)
	{
		myPopupMenu = menu;
	}
	
	
	public void showPopup (Component source, int x, int y)
	{
		JPopupMenu menu = getPopupMenu();
		if (null != menu)
		{
			menu.show(source, x, y);
		}
	}
	
	public class LTSMousePopupListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {
	        maybeShowPopup(e);
	    }
	
	    public void mouseReleased(MouseEvent e) 
	    {
	        maybeShowPopup(e);
	    }
	
	    private void maybeShowPopup(MouseEvent e) 
	    {
	        if (e.isPopupTrigger()) 
	        {
	        	Component c = (Component) e.getSource();
	            showPopup (c, e.getX(), e.getY());
	        }
	    }
	}
	
	
	public void singleClick (Object src)
	{}
	
	public void singleMouseClick (MouseEvent me)
	{
		singleClick(me.getSource());
	}
	
	public void doubleClick (Object src)
	{}
	
	public void doubleMouseClick (MouseEvent me)
	{
		doubleClick(me.getSource());
	}
	
	
	public class LTSMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			maybeShowPopup(e);
		}
	
		public void mouseReleased(MouseEvent e) 
		{
			maybeShowPopup(e);
		}
	
		public void maybeShowPopup(MouseEvent e) 
		{
			if (e.isPopupTrigger()) 
			{
				Component c = (Component) e.getSource();
				showPopup (c, e.getX(), e.getY());
			}
		}
		
		public void mouseClicked (MouseEvent e)
		{
			if (e.getClickCount() < 2)
				singleMouseClick(e);
			else 
				doubleMouseClick(e);
		}
	}
	
	public MouseAdapter getMouseAdapter()
	{
		if (null == myMouseAdapter)
			myMouseAdapter = new LTSMouseListener();
		
		return myMouseAdapter;
	}
	
	public void addOKButton (JPanel p)
	{
		GridBagConstraints gbc;
		
		myOkButton = new JButton("OK");
		myOkButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(0,0,5);
		p.add(myOkButton, gbc);
	}
	
	public void addCancelButton (JPanel p)
	{
		GridBagConstraints gbc;
		
		myCancelButton = new JButton("Cancel");
		myCancelButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(1,0,5);
		p.add(myCancelButton, gbc);
	}
	
	public static final int OK = 0;
	public static final int OK_CANCEL = 1;
	
	public JPanel createBottomPanel (int config)
	{
		JPanel p = new JPanel(new GridBagLayout());
		
		addOKButton(p);
		
		if (OK_CANCEL == config)
			addCancelButton(p);
			
		return p;
	}
	
	
	public void quit ()
	{
		String msg = "Quit?";
		
		int result = JOptionPane.showConfirmDialog(this, msg);
		if (JOptionPane.YES_OPTION == result || JOptionPane.OK_OPTION == result)
			System.exit(0);
	}


	public class StandardRKL
		extends ReturnKeyListener
	{
		public void performAction (Object source)
		{
			returnKeyPressed(source);
		}
	}
	
	
	public ReturnKeyListener getReturnKeyListener()
	{
		if (null == myReturnKeyListener)
			myReturnKeyListener = new StandardRKL();
		
		return myReturnKeyListener;
	}


	public void deleteKeyPressed(Object src)
	{}
	
	public void insertKeyPressed(Object src)
	{}
	
	
	public class StandardKeyListener
		extends KeyAdapter
	{
		public void keyPressed (KeyEvent e)
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_ENTER :
					returnKeyPressed(e.getSource());
					break;
					
				case KeyEvent.VK_DELETE :
					deleteKeyPressed(e.getSource());
					break;
					
				case KeyEvent.VK_INSERT :
					insertKeyPressed(e.getSource());
					break;
			}
		}
	}
	
	
	public KeyListener getKeyListener()
	{
		if (null == myKeyListener)
			myKeyListener = new StandardKeyListener();
		
		return myKeyListener;
	}
	
	
	public void returnKeyPressed(Object source)
	{}
	
	
	public static String trimString (String s)
	{
		if (null == s)
			return null;
		
		s = s.trim();
		if ("".equals(s))
			return null;
		else
			return s;
	}
	
	
	public void notImplemented ()
	{
		JOptionPane.showMessageDialog(
			this,
			"This feature has not been implemented" 
		);
	}
	
	
	public void listSelection (int first, int last)
	{}
	
	
	public ListSelectionListener getListSelectionListener()
	{
		if (null == myListSelectionListener)
			myListSelectionListener = new LocalListSelectionListener();
		
		return myListSelectionListener;
	}
	
	public void setVisible(boolean visible)
	{
		boolean closing = !visible && this.isVisible();
		super.setVisible(visible);
		
		if (closing && !isVisible())
			fireWindowClosed();
	}
	
	public String trimField (JTextField field)
	{
		if (null == field)
			return null;
		
		String s = field.getText();
		s = s.trim();
		if ("".equals(s))
			s = null;
		
		return s;
	}
	
	
	public void windowOpened(WindowEvent e)
	{}
	
	public void windowClosing(WindowEvent e)
	{}
	
	public void windowClosed(WindowEvent e)
	{}
	
	public void windowIconified(WindowEvent e)
	{}
	
	public void windowDeiconified(WindowEvent e)
	{}
	
	public void windowActivated(WindowEvent e)
	{}
	
	public void windowDeactivated(WindowEvent e)
	{}
}

