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

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.im.InputContext;
import java.awt.im.InputMethodRequests;
import java.awt.image.BufferStrategy;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.EventListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.accessibility.AccessibleContext;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.lts.LTSException;
import com.lts.event.ReturnKeyListener;

/**
 * A class that simplifies using dialogs and frames.
 * 
 * <P>
 * Java/Swing forces each window to be either a subclass of JDialog or JFrame.
 * Switching back and forth is not that hard, in that all one must do is change
 * the superclass of the window.
 * 
 * <P>
 * The problem comes in when you want to create a class with lots of utility
 * methods to help with creating windows --- you end up having to create two 
 * superclasses: one for frames and one for dialogs.  In my experience, these
 * two superclass are virtually identical, varying only in the superclass.
 * This means that you have to keep two code branches in line: one for the 
 * dialog and one for the frame.  This is error prone and annoying.
 * 
 * <P>
 * This class attempts to solve the problem by centralizing all the helper 
 * methods into one class.  The two superclasses delegate all their calls to 
 * the helper class instead of implementing them on their own. 
 */
public class WindowHelper
{
	public class LocalListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			listSelection(e.getFirstIndex(), e.getLastIndex());
		}
	}

	protected SwingWindow myWindow;
	protected RootPaneContainer myRootContainer;
	protected JButton myOkButton;
	protected JButton myCancelButton;
	protected JLabel myHeading;
	protected ActionListener myActionListener;
	protected MouseAdapter myMouseAdapter;
	protected JPopupMenu myPopupMenu;
	protected boolean myExitOnClose = false;
	protected boolean myPromptBeforeExit = true;
	protected ReturnKeyListener myReturnKeyListener;
	protected ListSelectionListener myListSelectionListener;
	protected KeyListener myKeyListener;
	protected boolean myChangesAccepted;

	public static final int OPTION_OK_CANCEL = 0;
	public static final int OPTION_CLOSE_ONLY = 1;
	public static final int OPTION_NOTHING = 2;
	public static final int OPTION_APPLY_CANCEL = 3;

	public static final int MODE_MAINFRAME = 0;
	public static final int MODE_NORMAL_WINDOW = 1;

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

	public boolean changesAccepted()
	{
		return myChangesAccepted;
	}

	public void setChangesAccepted(boolean b)
	{
		myChangesAccepted = b;
	}

	public void acceptChanges()
	{
		setChangesAccepted(true);
		myWindow.setVisible(false);
	}

	public void rejectChanges()
	{
		setChangesAccepted(false);
		myWindow.setVisible(false);
	}

	public void performAction(Object src)
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

	public String getHeadingString()
	{
		return null;
	}

	public JPanel createTopPanel()
	{
		if (null == getHeadingString() || "".equals(getHeadingString()))
			return null;

		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints gbc;
		myHeading = new JLabel(getHeadingString());
		gbc = SimpleGBC.title(0, 0, 5);
		panel.add(myHeading, gbc);

		return panel;
	}

	public JPanel createCenterPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		return panel;
	}

	public void createOkClosePanel(JPanel p)
	{
		myOkButton = new JButton("OK");
		myOkButton.addActionListener(getActionListener());
		p.add(myOkButton, SimpleGBC.button(0, 0, 5));

		myCancelButton = new JButton("Cancel");
		myCancelButton.addActionListener(getActionListener());
		p.add(myCancelButton, SimpleGBC.button(1, 0, 5));
	}

	public void createCloseOnly(JPanel p)
	{
		myCancelButton = new JButton("Close");
		myCancelButton.addActionListener(getActionListener());
		p.add(myCancelButton, SimpleGBC.button(0, 0, 5));
	}

	public void createApplyCancel(JPanel p)
	{
		myOkButton = new JButton("OK");
		myOkButton.addActionListener(getActionListener());
		p.add(myOkButton, SimpleGBC.button(0, 0, 5));

		myCancelButton = new JButton("Cancel");
		myCancelButton.addActionListener(getActionListener());
		p.add(myCancelButton, SimpleGBC.button(1, 0, 5));
	}

	public void buildBottomPanel(LTSPanel panel, String okButtonString,
			String cancelButtonString)
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

	public Container getContentPane()
	{
		return myWindow.getContentPane();
	}

	public void initialize(int mode, String windowTitle) throws LTSException
	{
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc;

		int gridy = 0;

		JPanel p = createTopPanel();
		if (null != p)
		{
			gbc = topPanelConstraints(gridy);
			getContentPane().add(p, gbc);
			gridy++;
		}

		p = createCenterPanel();
		if (null != p)
		{
			gbc = centerPanelConstraints(gridy);
			getContentPane().add(p, gbc);
			gridy++;
		}

		p = createBottomPanel();
		if (null != p)
		{
			gbc = bottomPanelConstraints(gridy);
			getContentPane().add(p, gbc);
			gridy++;
		}

		setPopupMenu(createPopupMenu());

		JMenuBar menuBar = createMenuBar();
		if (null != menuBar)
			myWindow.setJMenuBar(menuBar);

		if (null == windowTitle)
			windowTitle = getWindowTitle();

		if (null != windowTitle)
			myWindow.setTitle(windowTitle);

		switch (mode)
		{
			case MODE_MAINFRAME :
				setExitOnClose(true);
				setPromptBeforeExit(true);
				break;

			case MODE_NORMAL_WINDOW :
				setExitOnClose(false);
				setPromptBeforeExit(true);
				break;
		}

		myWindow
				.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		myWindow.addWindowListener(new LTSWindow());
		myWindow.setSize(getDefaultSize());
		centerWindow();
	}

	public int getMode()
	{
		return MODE_NORMAL_WINDOW;
	}

	public void initialize() throws LTSException
	{
		initialize(getMode(), null);
	}

	public static Dimension toDimension(double widthPercent, int minWidth,
			double heightPercent, int minHeight)
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

	public WindowHelper(SwingWindow win) throws LTSException
	{
		setWindow(win);
	}

	/**
	 * Center the frame on the screen.
	 */
	public void centerWindow()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		int x = (d.width - myWindow.getWidth()) / 2;
		int y = (d.height - myWindow.getHeight()) / 2;

		myWindow.setLocation(x, y);
	}

	/**
	 * Resize the frame to some percentage of the screen size in terms of both
	 * width and height.
	 */
	public void resizeTo(int percentOfScreen)
	{
		resizeTo((double) percentOfScreen / 100);
	}

	public void resizeTo(double fractionOfScreen)
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		int newWidth = (int) (d.getWidth() * fractionOfScreen);
		int newHeight = (int) (d.getHeight() * fractionOfScreen);
		myWindow.setSize(newWidth, newHeight);
	}

	public void setHeading(String s)
	{
		if (null != s)
		{
			if (null == myHeading)
				myHeading = new JLabel(s);
			else
				myHeading.setText(s);

			String title = myWindow.getTitle();
			if (null == title || "".equals(title))
				myWindow.setTitle(s);
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
				int result = JOptionPane.showConfirmDialog(null,
						"Quit Application?");
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

	public JPopupMenu createPopupMenu() throws LTSException
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

	public void showPopup(Component source, int x, int y)
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
				showPopup(c, e.getX(), e.getY());
			}
		}
	}

	public void singleClick(Object src)
	{
	}

	public void singleMouseClick(MouseEvent me)
	{
		singleClick(me.getSource());
	}

	public void doubleClick(Object src)
	{
	}

	public void doubleMouseClick(MouseEvent me)
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
				showPopup(c, e.getX(), e.getY());
			}
		}

		public void mouseClicked(MouseEvent e)
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

	public void addOKButton(JPanel p)
	{
		GridBagConstraints gbc;

		myOkButton = new JButton("OK");
		myOkButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(0, 0, 5);
		p.add(myOkButton, gbc);
	}

	public void addCancelButton(JPanel p)
	{
		GridBagConstraints gbc;

		myOkButton = new JButton("Cancel");
		myOkButton.addActionListener(getActionListener());
		gbc = SimpleGridBagConstraint.buttonConstraint(1, 0, 5);
		p.add(myOkButton, gbc);
	}

	public static final int OK = 0;
	public static final int OK_CANCEL = 1;

	public JPanel createBottomPanel(int config)
	{
		JPanel p = new JPanel(new GridBagLayout());

		addOKButton(p);

		if (OK_CANCEL == config)
			addCancelButton(p);

		return p;
	}

	public void quit()
	{
		System.exit(0);
	}

	public class StandardRKL extends ReturnKeyListener
	{
		public void performAction(Object source)
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
	{
	}

	public void insertKeyPressed(Object src)
	{
	}

	public class StandardKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
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
	{
	}

	public static String trimString(String s)
	{
		if (null == s)
			return null;

		s = s.trim();
		if ("".equals(s))
			return null;
		else
			return s;
	}

	public void notImplemented()
	{
		JOptionPane.showMessageDialog(null,
				"This feature has not been implemented");
	}

	public void listSelection(int first, int last)
	{
	}

	public ListSelectionListener getListSelectionListener()
	{
		if (null == myListSelectionListener)
			myListSelectionListener = new LocalListSelectionListener();

		return myListSelectionListener;
	}

	public SwingWindow getWindow()
	{
		return myWindow;
	}

	public void setWindow(SwingWindow win)
	{
		myWindow = win;
	}

	/**
	 * @return
	 */
	public Container getFocusCycleRootAncestor()
	{
		return myWindow.getFocusCycleRootAncestor();
	}

	/**
	 * @return
	 */
	public boolean isFocusCycleRoot()
	{
		return myWindow.isFocusCycleRoot();
	}

	/**
	 * @param arg0
	 */
	public void removeHierarchyListener(HierarchyListener arg0)
	{
		myWindow.removeHierarchyListener(arg0);
	}

	/**
	 * @return
	 */
	public boolean isFocusTraversable()
	{
		return myWindow.isFocusTraversable();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 */
	public void repaint(long arg0, int arg1, int arg2, int arg3, int arg4)
	{
		myWindow.repaint(arg0, arg1, arg2, arg3, arg4);
	}

	/**
	 * @return
	 */
	public WindowFocusListener[] getWindowFocusListeners()
	{
		return myWindow.getWindowFocusListeners();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Component getComponentAt(Point arg0)
	{
		return myWindow.getComponentAt(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public boolean isAncestorOf(Component arg0)
	{
		return myWindow.isAncestorOf(arg0);
	}

	/**
	 *  
	 */
	public void validate()
	{
		myWindow.validate();
	}

	/**
	 * @param arg0
	 */
	public void dispatchEvent(AWTEvent arg0)
	{
		myWindow.dispatchEvent(arg0);
	}

	/**
	 * @return
	 */
	public ComponentListener[] getComponentListeners()
	{
		return myWindow.getComponentListeners();
	}

	/**
	 * @return
	 */
	public Point location()
	{
		return myWindow.location();
	}

	/**
	 * @return
	 */
	public Dimension getPreferredSize()
	{
		return myWindow.getPreferredSize();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public boolean isFocusCycleRoot(Container arg0)
	{
		return myWindow.isFocusCycleRoot(arg0);
	}

	/**
	 *  
	 */
	public void removeAll()
	{
		myWindow.removeAll();
	}

	/**
	 * @param arg0
	 */
	public void removeInputMethodListener(InputMethodListener arg0)
	{
		myWindow.removeInputMethodListener(arg0);
	}

	/**
	 * @return
	 */
	public Component getFocusOwner()
	{
		return myWindow.getFocusOwner();
	}

	/**
	 * @return
	 */
	public Color getForeground()
	{
		return myWindow.getForeground();
	}

	/**
	 * @return
	 */
	public boolean isLightweight()
	{
		return myWindow.isLightweight();
	}

	/**
	 * @return
	 */
	public float getAlignmentX()
	{
		return myWindow.getAlignmentX();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public PropertyChangeListener[] getPropertyChangeListeners(String arg0)
	{
		return myWindow.getPropertyChangeListeners(arg0);
	}

	/**
	 *  
	 */
	public void transferFocusUpCycle()
	{
		myWindow.transferFocusUpCycle();
	}

	/**
	 * @param arg0
	 */
	public void removeKeyListener(KeyListener arg0)
	{
		myWindow.removeKeyListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setJMenuBar(JMenuBar arg0)
	{
		myWindow.setJMenuBar(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Component getComponentAt(int arg0, int arg1)
	{
		return myWindow.getComponentAt(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void applyResourceBundle(String arg0)
	{
		myWindow.applyResourceBundle(arg0);
	}

	/**
	 * @return
	 */
	public Insets insets()
	{
		return myWindow.insets();
	}

	/**
	 * @param arg0
	 */
	public void update(Graphics arg0)
	{
		myWindow.update(arg0);
	}

	/**
	 * @return
	 */
	public float getAlignmentY()
	{
		return myWindow.getAlignmentY();
	}

	/**
	 * @param arg0
	 */
	public void printAll(Graphics arg0)
	{
		myWindow.printAll(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setFocusTraversalPolicy(FocusTraversalPolicy arg0)
	{
		myWindow.setFocusTraversalPolicy(arg0);
	}

	/**
	 * @return
	 */
	public InputMethodRequests getInputMethodRequests()
	{
		return myWindow.getInputMethodRequests();
	}

	/**
	 * @param arg0
	 */
	public void setUndecorated(boolean arg0)
	{
		myWindow.setUndecorated(arg0);
	}

	/**
	 * @return
	 */
	public MouseMotionListener[] getMouseMotionListeners()
	{
		return myWindow.getMouseMotionListeners();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public boolean mouseDown(Event arg0, int arg1, int arg2)
	{
		return myWindow.mouseDown(arg0, arg1, arg2);
	}

	/**
	 * @return
	 */
	public boolean isFocusTraversalPolicySet()
	{
		return myWindow.isFocusTraversalPolicySet();
	}

	/**
	 * @return
	 */
	public boolean isResizable()
	{
		return myWindow.isResizable();
	}

	/**
	 * @return
	 */
	public Cursor getCursor()
	{
		return myWindow.getCursor();
	}

	/**
	 * @return
	 */
	public boolean isFocused()
	{
		return myWindow.isFocused();
	}

	/**
	 * @param arg0
	 */
	public void setResizable(boolean arg0)
	{
		myWindow.setResizable(arg0);
	}

	/**
	 * @return
	 */
	public Window[] getOwnedWindows()
	{
		return myWindow.getOwnedWindows();
	}

	/**
	 * @return
	 */
	public InputContext getInputContext()
	{
		return myWindow.getInputContext();
	}

	/**
	 * @return
	 */
	public boolean isBackgroundSet()
	{
		return myWindow.isBackgroundSet();
	}

	/**
	 * @return
	 */
	public Component[] getComponents()
	{
		return myWindow.getComponents();
	}

	/**
	 *  
	 */
	public void transferFocusBackward()
	{
		myWindow.transferFocusBackward();
	}

	/**
	 * @return
	 */
	public WindowListener[] getWindowListeners()
	{
		return myWindow.getWindowListeners();
	}

	/**
	 * @return
	 */
	public Dimension minimumSize()
	{
		return myWindow.minimumSize();
	}

	/**
	 * @param arg0
	 */
	public void removeFocusListener(FocusListener arg0)
	{
		myWindow.removeFocusListener(arg0);
	}

	/**
	 *  
	 */
	public void hide()
	{
		myWindow.hide();
	}

	/**
	 * @return
	 */
	public int getComponentCount()
	{
		return myWindow.getComponentCount();
	}

	/**
	 * @param arg0
	 */
	public void addWindowListener(WindowListener arg0)
	{
		myWindow.addWindowListener(arg0);
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return myWindow.getName();
	}

	/**
	 * @param arg0
	 */
	public void removeHierarchyBoundsListener(HierarchyBoundsListener arg0)
	{
		myWindow.removeHierarchyBoundsListener(arg0);
	}

	/**
	 * @return
	 */
	public boolean getIgnoreRepaint()
	{
		return myWindow.getIgnoreRepaint();
	}

	/**
	 * @return
	 */
	public boolean isFocusable()
	{
		return myWindow.isFocusable();
	}

	/**
	 * @param arg0
	 */
	public void print(Graphics arg0)
	{
		myWindow.print(arg0);
	}

	/**
	 * @return
	 */
	public Locale getLocale()
	{
		return myWindow.getLocale();
	}

	/**
	 * @param arg0
	 */
	public void setCursor(Cursor arg0)
	{
		myWindow.setCursor(arg0);
	}

	/**
	 * @return
	 */
	public int getWidth()
	{
		return myWindow.getWidth();
	}

	/**
	 *  
	 */
	public void addNotify()
	{
		myWindow.addNotify();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean lostFocus(Event arg0, Object arg1)
	{
		return myWindow.lostFocus(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Component add(Component arg0, int arg1)
	{
		return myWindow.add(arg0, arg1);
	}

	/**
	 * @return
	 */
	public AccessibleContext getAccessibleContext()
	{
		return myWindow.getAccessibleContext();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void move(int arg0, int arg1)
	{
		myWindow.move(arg0, arg1);
	}

	/**
	 * @return
	 */
	public Rectangle getBounds()
	{
		return myWindow.getBounds();
	}

	/**
	 * @return
	 */
	public GraphicsConfiguration getGraphicsConfiguration()
	{
		return myWindow.getGraphicsConfiguration();
	}

	/**
	 * @return
	 */
	public JRootPane getRootPane()
	{
		return myWindow.getRootPane();
	}

	/**
	 * @return
	 */
	public boolean hasFocus()
	{
		return myWindow.hasFocus();
	}

	/**
	 * @return
	 */
	public int getY()
	{
		return myWindow.getY();
	}

	/**
	 * @param arg0
	 */
	public void removeWindowListener(WindowListener arg0)
	{
		myWindow.removeWindowListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setFocusable(boolean arg0)
	{
		myWindow.setFocusable(arg0);
	}

	/**
	 * @param arg0
	 */
	public void enableInputMethods(boolean arg0)
	{
		myWindow.enableInputMethods(arg0);
	}

	/**
	 *  
	 */
	public void transferFocusDownCycle()
	{
		myWindow.transferFocusDownCycle();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public boolean mouseExit(Event arg0, int arg1, int arg2)
	{
		return myWindow.mouseExit(arg0, arg1, arg2);
	}

	/**
	 * @return
	 */
	public Container getParent()
	{
		return myWindow.getParent();
	}

	/**
	 * @return
	 */
	public String getTitle()
	{
		return myWindow.getTitle();
	}

	/**
	 * @return
	 */
	public Dimension size()
	{
		return myWindow.size();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public FontMetrics getFontMetrics(Font arg0)
	{
		return myWindow.getFontMetrics(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean gotFocus(Event arg0, Object arg1)
	{
		return myWindow.gotFocus(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void setDefaultCloseOperation(int arg0)
	{
		myWindow.setDefaultCloseOperation(arg0);
	}

	/**
	 * @param arg0
	 */
	public void removeMouseMotionListener(MouseMotionListener arg0)
	{
		myWindow.removeMouseMotionListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void paintAll(Graphics arg0)
	{
		myWindow.paintAll(arg0);
	}

	/**
	 * @return
	 */
	public boolean isDoubleBuffered()
	{
		return myWindow.isDoubleBuffered();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void removePropertyChangeListener(String arg0,
			PropertyChangeListener arg1)
	{
		myWindow.removePropertyChangeListener(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void addPropertyChangeListener(PropertyChangeListener arg0)
	{
		myWindow.addPropertyChangeListener(arg0);
	}

	/**
	 * @return
	 */
	public boolean isEnabled()
	{
		return myWindow.isEnabled();
	}

	/**
	 * @return
	 */
	public boolean isFocusOwner()
	{
		return myWindow.isFocusOwner();
	}

	/**
	 * @param arg0
	 */
	public void add(PopupMenu arg0)
	{
		myWindow.add(arg0);
	}

	/**
	 * @param arg0
	 */
	public void show(boolean arg0)
	{
		myWindow.show(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setFocusCycleRoot(boolean arg0)
	{
		myWindow.setFocusCycleRoot(arg0);
	}

	/**
	 * @return
	 */
	public int getDefaultCloseOperation()
	{
		return myWindow.getDefaultCloseOperation();
	}

	/**
	 * @return
	 */
	public ComponentOrientation getComponentOrientation()
	{
		return myWindow.getComponentOrientation();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public void add(Component arg0, Object arg1, int arg2)
	{
		myWindow.add(arg0, arg1, arg2);
	}

	/**
	 * @return
	 */
	public boolean isDisplayable()
	{
		return myWindow.isDisplayable();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public boolean areFocusTraversalKeysSet(int arg0)
	{
		return myWindow.areFocusTraversalKeysSet(arg0);
	}

	/**
	 * @return
	 */
	public MouseListener[] getMouseListeners()
	{
		return myWindow.getMouseListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return myWindow.toString();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public VolatileImage createVolatileImage(int arg0, int arg1)
	{
		return myWindow.createVolatileImage(arg0, arg1);
	}

	/**
	 * @return
	 */
	public Dimension getMaximumSize()
	{
		return myWindow.getMaximumSize();
	}

	/**
	 * @param arg0
	 */
	public void list(PrintWriter arg0)
	{
		myWindow.list(arg0);
	}

	/**
	 * @param arg0
	 */
	public void remove(int arg0)
	{
		myWindow.remove(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setGlassPane(Component arg0)
	{
		myWindow.setGlassPane(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void setBounds(int arg0, int arg1, int arg2, int arg3)
	{
		myWindow.setBounds(arg0, arg1, arg2, arg3);
	}

	/**
	 * @return
	 */
	public Dimension getMinimumSize()
	{
		return myWindow.getMinimumSize();
	}

	/**
	 * @param arg0
	 */
	public void setDropTarget(DropTarget arg0)
	{
		myWindow.setDropTarget(arg0);
	}

	/**
	 * @param arg0
	 */
	public void removeComponentListener(ComponentListener arg0)
	{
		myWindow.removeComponentListener(arg0);
	}

	/**
	 * @return
	 */
	public boolean isFocusableWindow()
	{
		return myWindow.isFocusableWindow();
	}

	/**
	 * @return
	 */
	public boolean isOpaque()
	{
		return myWindow.isOpaque();
	}

	/**
	 * @param arg0
	 */
	public void setLocation(Point arg0)
	{
		myWindow.setLocation(arg0);
	}

	/**
	 * @param arg0
	 */
	public void addComponentListener(ComponentListener arg0)
	{
		myWindow.addComponentListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void createBufferStrategy(int arg0)
	{
		myWindow.createBufferStrategy(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setComponentOrientation(ComponentOrientation arg0)
	{
		myWindow.setComponentOrientation(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void list(PrintStream arg0, int arg1)
	{
		myWindow.list(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void list(PrintStream arg0)
	{
		myWindow.list(arg0);
	}

	/**
	 * @return
	 */
	public Insets getInsets()
	{
		return myWindow.getInsets();
	}

	/**
	 * @return
	 */
	public boolean isShowing()
	{
		return myWindow.isShowing();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void setLocation(int arg0, int arg1)
	{
		myWindow.setLocation(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public int checkImage(Image arg0, ImageObserver arg1)
	{
		return myWindow.checkImage(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 */
	public boolean prepareImage(Image arg0, int arg1, int arg2,
			ImageObserver arg3)
	{
		return myWindow.prepareImage(arg0, arg1, arg2, arg3);
	}

	/**
	 * @return
	 */
	public HierarchyBoundsListener[] getHierarchyBoundsListeners()
	{
		return myWindow.getHierarchyBoundsListeners();
	}

	/**
	 * @param arg0
	 */
	public void setBackground(Color arg0)
	{
		myWindow.setBackground(arg0);
	}

	/**
	 * @return
	 */
	public boolean isCursorSet()
	{
		return myWindow.isCursorSet();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public boolean mouseEnter(Event arg0, int arg1, int arg2)
	{
		return myWindow.mouseEnter(arg0, arg1, arg2);
	}

	/**
	 * @return
	 */
	public FocusListener[] getFocusListeners()
	{
		return myWindow.getFocusListeners();
	}

	/**
	 * @param arg0
	 */
	public void applyComponentOrientation(ComponentOrientation arg0)
	{
		myWindow.applyComponentOrientation(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public boolean mouseDrag(Event arg0, int arg1, int arg2)
	{
		return myWindow.mouseDrag(arg0, arg1, arg2);
	}

	/**
	 * @return
	 */
	public Point getLocationOnScreen()
	{
		return myWindow.getLocationOnScreen();
	}

	/**
	 * @param arg0
	 */
	public void setFont(Font arg0)
	{
		myWindow.setFont(arg0);
	}

	/**
	 * @return
	 */
	public Graphics getGraphics()
	{
		return myWindow.getGraphics();
	}

	/**
	 *  
	 */
	public void dispose()
	{
		myWindow.dispose();
	}

	/**
	 * @param arg0
	 */
	public void enable(boolean arg0)
	{
		myWindow.enable(arg0);
	}

	/**
	 * @return
	 */
	public int getX()
	{
		return myWindow.getX();
	}

	/**
	 * @param arg0
	 */
	public void setContentPane(Container arg0)
	{
		myWindow.setContentPane(arg0);
	}

	/**
	 * @param arg0
	 */
	public void removeMouseWheelListener(MouseWheelListener arg0)
	{
		myWindow.removeMouseWheelListener(arg0);
	}

	/**
	 *  
	 */
	public void pack()
	{
		myWindow.pack();
	}

	/**
	 * @param arg0
	 */
	public void setBounds(Rectangle arg0)
	{
		myWindow.setBounds(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setLayout(LayoutManager arg0)
	{
		myWindow.setLayout(arg0);
	}

	/**
	 * @return
	 */
	public boolean isForegroundSet()
	{
		return myWindow.isForegroundSet();
	}

	/**
	 *  
	 */
	public void show()
	{
		myWindow.show();
	}

	/**
	 *  
	 */
	public void doLayout()
	{
		myWindow.doLayout();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean keyDown(Event arg0, int arg1)
	{
		return myWindow.keyDown(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return myWindow.hashCode();
	}

	/**
	 * @param arg0
	 */
	public void repaint(long arg0)
	{
		myWindow.repaint(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void setSize(int arg0, int arg1)
	{
		myWindow.setSize(arg0, arg1);
	}

	/**
	 * @return
	 */
	public boolean isActive()
	{
		return myWindow.isActive();
	}

	/**
	 * @param arg0
	 */
	public void addMouseListener(MouseListener arg0)
	{
		myWindow.addMouseListener(arg0);
	}

	/**
	 * @return
	 */
	public Window getOwner()
	{
		return myWindow.getOwner();
	}

	/**
	 * @param arg0
	 */
	public void setTitle(String arg0)
	{
		myWindow.setTitle(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void addPropertyChangeListener(String arg0,
			PropertyChangeListener arg1)
	{
		myWindow.addPropertyChangeListener(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void addMouseMotionListener(MouseMotionListener arg0)
	{
		myWindow.addMouseMotionListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void paint(Graphics arg0)
	{
		myWindow.paint(arg0);
	}

	/**
	 * @param arg0
	 */
	public void addWindowFocusListener(WindowFocusListener arg0)
	{
		myWindow.addWindowFocusListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void addMouseWheelListener(MouseWheelListener arg0)
	{
		myWindow.addMouseWheelListener(arg0);
	}

	/**
	 * @return
	 */
	public String getWarningString()
	{
		return myWindow.getWarningString();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean inside(int arg0, int arg1)
	{
		return myWindow.inside(arg0, arg1);
	}

	/**
	 *  
	 */
	public void requestFocus()
	{
		myWindow.requestFocus();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void add(Component arg0, Object arg1)
	{
		myWindow.add(arg0, arg1);
	}

	/**
	 *  
	 */
	public void nextFocus()
	{
		myWindow.nextFocus();
	}

	/**
	 * @return
	 */
	public Dimension preferredSize()
	{
		return myWindow.preferredSize();
	}

	/**
	 * @return
	 */
	public Point getLocation()
	{
		return myWindow.getLocation();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean keyUp(Event arg0, int arg1)
	{
		return myWindow.keyUp(arg0, arg1);
	}

	/**
	 * @return
	 */
	public boolean requestFocusInWindow()
	{
		return myWindow.requestFocusInWindow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0)
	{
		return myWindow.equals(arg0);
	}

	/**
	 * @param arg0
	 */
	public void printComponents(Graphics arg0)
	{
		myWindow.printComponents(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public boolean postEvent(Event arg0)
	{
		return myWindow.postEvent(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void resize(int arg0, int arg1)
	{
		myWindow.resize(arg0, arg1);
	}

	/**
	 * @return
	 */
	public JMenuBar getJMenuBar()
	{
		return myWindow.getJMenuBar();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Image createImage(int arg0, int arg1)
	{
		return myWindow.createImage(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Component getComponent(int arg0)
	{
		return myWindow.getComponent(arg0);
	}

	/**
	 * @return
	 */
	public FocusTraversalPolicy getFocusTraversalPolicy()
	{
		return myWindow.getFocusTraversalPolicy();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Component add(String arg0, Component arg1)
	{
		return myWindow.add(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void addInputMethodListener(InputMethodListener arg0)
	{
		myWindow.addInputMethodListener(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean prepareImage(Image arg0, ImageObserver arg1)
	{
		return myWindow.prepareImage(arg0, arg1);
	}

	/**
	 * @return
	 */
	public HierarchyListener[] getHierarchyListeners()
	{
		return myWindow.getHierarchyListeners();
	}

	/**
	 *  
	 */
	public void toBack()
	{
		myWindow.toBack();
	}

	/**
	 * @return
	 */
	public InputMethodListener[] getInputMethodListeners()
	{
		return myWindow.getInputMethodListeners();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 * @return
	 */
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5)
	{
		return myWindow.imageUpdate(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public boolean mouseMove(Event arg0, int arg1, int arg2)
	{
		return myWindow.mouseMove(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void list(PrintWriter arg0, int arg1)
	{
		myWindow.list(arg0, arg1);
	}

	/**
	 * @return
	 */
	public boolean isFontSet()
	{
		return myWindow.isFontSet();
	}

	/**
	 * @param arg0
	 */
	public void addHierarchyListener(HierarchyListener arg0)
	{
		myWindow.addHierarchyListener(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void repaint(int arg0, int arg1, int arg2, int arg3)
	{
		myWindow.repaint(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Rectangle getBounds(Rectangle arg0)
	{
		return myWindow.getBounds(arg0);
	}

	/**
	 * @return
	 */
	public boolean isValid()
	{
		return myWindow.isValid();
	}

	/**
	 *  
	 */
	public void repaint()
	{
		myWindow.repaint();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public boolean handleEvent(Event arg0)
	{
		return myWindow.handleEvent(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Component findComponentAt(int arg0, int arg1)
	{
		return myWindow.findComponentAt(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void removeWindowFocusListener(WindowFocusListener arg0)
	{
		myWindow.removeWindowFocusListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setName(String arg0)
	{
		myWindow.setName(arg0);
	}

	/**
	 * @param arg0
	 */
	public void removeMouseListener(MouseListener arg0)
	{
		myWindow.removeMouseListener(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Component findComponentAt(Point arg0)
	{
		return myWindow.findComponentAt(arg0);
	}

	/**
	 *  
	 */
	public void disable()
	{
		myWindow.disable();
	}

	/**
	 * @return
	 */
	public Component getGlassPane()
	{
		return myWindow.getGlassPane();
	}

	/**
	 * @return
	 */
	public Component getMostRecentFocusOwner()
	{
		return myWindow.getMostRecentFocusOwner();
	}

	/**
	 * @return
	 */
	public DropTarget getDropTarget()
	{
		return myWindow.getDropTarget();
	}

	/**
	 * @return
	 */
	public LayoutManager getLayout()
	{
		return myWindow.getLayout();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Set getFocusTraversalKeys(int arg0)
	{
		return myWindow.getFocusTraversalKeys(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public boolean mouseUp(Event arg0, int arg1, int arg2)
	{
		return myWindow.mouseUp(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 */
	public void applyResourceBundle(ResourceBundle arg0)
	{
		myWindow.applyResourceBundle(arg0);
	}

	/**
	 * @return
	 */
	public ComponentPeer getPeer()
	{
		return myWindow.getPeer();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean contains(int arg0, int arg1)
	{
		return myWindow.contains(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void deliverEvent(Event arg0)
	{
		myWindow.deliverEvent(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setLocale(Locale arg0)
	{
		myWindow.setLocale(arg0);
	}

	/**
	 * @return
	 */
	public Object getTreeLock()
	{
		return myWindow.getTreeLock();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Dimension getSize(Dimension arg0)
	{
		return myWindow.getSize(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean action(Event arg0, Object arg1)
	{
		return myWindow.action(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public void setLocationRelativeTo(Component arg0)
	{
		myWindow.setLocationRelativeTo(arg0);
	}

	/**
	 *  
	 */
	public void transferFocus()
	{
		myWindow.transferFocus();
	}

	/**
	 *  
	 */
	public void layout()
	{
		myWindow.layout();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public boolean contains(Point arg0)
	{
		return myWindow.contains(arg0);
	}

	/**
	 * @param arg0
	 */
	public void removePropertyChangeListener(PropertyChangeListener arg0)
	{
		myWindow.removePropertyChangeListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setFocusableWindowState(boolean arg0)
	{
		myWindow.setFocusableWindowState(arg0);
	}

	/**
	 * @return
	 */
	public Color getBackground()
	{
		return myWindow.getBackground();
	}

	/**
	 * @return
	 */
	public WindowStateListener[] getWindowStateListeners()
	{
		return myWindow.getWindowStateListeners();
	}

	/**
	 * @return
	 */
	public PropertyChangeListener[] getPropertyChangeListeners()
	{
		return myWindow.getPropertyChangeListeners();
	}

	/**
	 * @return
	 */
	public Dimension getSize()
	{
		return myWindow.getSize();
	}

	/**
	 * @return
	 */
	public int getHeight()
	{
		return myWindow.getHeight();
	}

	/**
	 * @param arg0
	 */
	public void addHierarchyBoundsListener(HierarchyBoundsListener arg0)
	{
		myWindow.addHierarchyBoundsListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setFocusTraversalKeysEnabled(boolean arg0)
	{
		myWindow.setFocusTraversalKeysEnabled(arg0);
	}

	/**
	 * @param arg0
	 */
	public void resize(Dimension arg0)
	{
		myWindow.resize(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setEnabled(boolean arg0)
	{
		myWindow.setEnabled(arg0);
	}

	/**
	 * @param arg0
	 */
	public void remove(MenuComponent arg0)
	{
		myWindow.remove(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Component locate(int arg0, int arg1)
	{
		return myWindow.locate(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Component add(Component arg0)
	{
		return myWindow.add(arg0);
	}

	/**
	 * @return
	 */
	public Rectangle bounds()
	{
		return myWindow.bounds();
	}

	/**
	 * @param arg0
	 */
	public void addContainerListener(ContainerListener arg0)
	{
		myWindow.addContainerListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void removeContainerListener(ContainerListener arg0)
	{
		myWindow.removeContainerListener(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public EventListener[] getListeners(Class arg0)
	{
		return myWindow.getListeners(arg0);
	}

	/**
	 * @return
	 */
	public ContainerListener[] getContainerListeners()
	{
		return myWindow.getContainerListeners();
	}

	/**
	 *  
	 */
	public void enable()
	{
		myWindow.enable();
	}

	/**
	 * @return
	 */
	public MouseWheelListener[] getMouseWheelListeners()
	{
		return myWindow.getMouseWheelListeners();
	}

	/**
	 * @return
	 */
	public JLayeredPane getLayeredPane()
	{
		return myWindow.getLayeredPane();
	}

	/**
	 * @return
	 */
	public boolean isUndecorated()
	{
		return myWindow.isUndecorated();
	}

	/**
	 *  
	 */
	public void toFront()
	{
		myWindow.toFront();
	}

	/**
	 * @param arg0
	 */
	public void setForeground(Color arg0)
	{
		myWindow.setForeground(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setVisible(boolean arg0)
	{
		myWindow.setVisible(arg0);
	}

	/**
	 *  
	 */
	public void removeNotify()
	{
		myWindow.removeNotify();
	}

	/**
	 * @param arg0
	 */
	public void addKeyListener(KeyListener arg0)
	{
		myWindow.addKeyListener(arg0);
	}

	/**
	 * @return
	 */
	public ColorModel getColorModel()
	{
		return myWindow.getColorModel();
	}

	/**
	 * @return
	 */
	public Toolkit getToolkit()
	{
		return myWindow.getToolkit();
	}

	/**
	 * @param arg0
	 */
	public void remove(Component arg0)
	{
		myWindow.remove(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setIgnoreRepaint(boolean arg0)
	{
		myWindow.setIgnoreRepaint(arg0);
	}

	/**
	 * @return
	 */
	public Font getFont()
	{
		return myWindow.getFont();
	}

	/**
	 *  
	 */
	public void invalidate()
	{
		myWindow.invalidate();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Image createImage(ImageProducer arg0)
	{
		return myWindow.createImage(arg0);
	}

	/**
	 * @return
	 */
	public boolean getFocusTraversalKeysEnabled()
	{
		return myWindow.getFocusTraversalKeysEnabled();
	}

	/**
	 *  
	 */
	public void list()
	{
		myWindow.list();
	}

	/**
	 * @param arg0
	 */
	public void paintComponents(Graphics arg0)
	{
		myWindow.paintComponents(arg0);
	}

	/**
	 * @param arg0
	 */
	public void addFocusListener(FocusListener arg0)
	{
		myWindow.addFocusListener(arg0);
	}

	/**
	 * @param arg0
	 */
	public void addWindowStateListener(WindowStateListener arg0)
	{
		myWindow.addWindowStateListener(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @throws AWTException
	 */
	public void createBufferStrategy(int arg0, BufferCapabilities arg1)
			throws AWTException
	{
		myWindow.createBufferStrategy(arg0, arg1);
	}

	/**
	 * @return
	 */
	public boolean isVisible()
	{
		return myWindow.isVisible();
	}

	/**
	 * @return
	 */
	public boolean getFocusableWindowState()
	{
		return myWindow.getFocusableWindowState();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void setFocusTraversalKeys(int arg0, Set arg1)
	{
		myWindow.setFocusTraversalKeys(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return @throws
	 *         AWTException
	 */
	public VolatileImage createVolatileImage(int arg0, int arg1,
			ImageCapabilities arg2) throws AWTException
	{
		return myWindow.createVolatileImage(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void reshape(int arg0, int arg1, int arg2, int arg3)
	{
		myWindow.reshape(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public Point getLocation(Point arg0)
	{
		return myWindow.getLocation(arg0);
	}

	/**
	 * @return
	 */
	public BufferStrategy getBufferStrategy()
	{
		return myWindow.getBufferStrategy();
	}

	/**
	 * @return
	 */
	public KeyListener[] getKeyListeners()
	{
		return myWindow.getKeyListeners();
	}

	/**
	 * @return
	 */
	public int countComponents()
	{
		return myWindow.countComponents();
	}

	/**
	 * @param arg0
	 */
	public void setLayeredPane(JLayeredPane arg0)
	{
		myWindow.setLayeredPane(arg0);
	}

	/**
	 * @param arg0
	 */
	public void setSize(Dimension arg0)
	{
		myWindow.setSize(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 */
	public int checkImage(Image arg0, int arg1, int arg2, ImageObserver arg3)
	{
		return myWindow.checkImage(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 */
	public void removeWindowStateListener(WindowStateListener arg0)
	{
		myWindow.removeWindowStateListener(arg0);
	}

	public GridBagConstraints centerPanelConstraints(int gridy)
	{
		return SimpleGridBagConstraint.fillConstraint(0, gridy, 5);
	}

	public GridBagConstraints topPanelConstraints(int gridy)
	{
		return SimpleGridBagConstraint.horizontalConstraint(0, gridy, 5);
	}

	public GridBagConstraints bottomPanelConstraints(int gridy)
	{
		return SimpleGridBagConstraint.horizontalConstraint(0, gridy, 5);
	}

}