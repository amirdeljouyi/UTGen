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
 * Copyright (c) 2004 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.contentpanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.lts.LTSException;
import com.lts.application.menu.ApplicationMenuBuilder;
import com.lts.event.Callback;
import com.lts.event.CallbackListenerHelper;
import com.lts.event.SimpleAction;
import com.lts.exception.NotImplementedException;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleGridBagConstraint;

/**
 * The content for a frame or dialog.
 * 
 * <H3>NOTE</H3>
 * For the time being, this class responds to {@link #basicBuildMenuBar()}
 * by creating an instance of {@link ApplicationMenuBuilder}.  This should really
 * be a call to an abstract method or the {@link #buildMenuBar()} should be
 * moved into a new subclass like "ApplicationContentPanel" so that clients 
 * know that this method is part of the whole Applciation package.
 * 
 * <H3>Description</H3>
 * This class provides a number of convenience methods that help with the
 * creation of GUIs.
 * 
 * <P>
 * One problem with the way that Swing/AWT handles GUIs is that it is hard to
 * create a common base class for JFrame and JDialog classes. Basically, it is
 * impossible to do so; the alternatives are:
 * 
 * <UL>
 * <LI>Create your own window hierarchy --- very difficult and non-standard
 * <LI>Create two hierarchies: one for JFrames and one for JDialogs ---
 * difficult to maintain.
 * <LI>Put all the utilities into a helper class --- a viable alternative.
 * <LI>Put all the utilities into a subclass of JPanel --- this approach.
 * <LI>Some other approach
 * </UL>
 * 
 * <P>
 * The approach taken by this class is to treat a window as one big honkin
 * panel: to use in a JFrame or a JDialog simply use the appropriate
 * constructor.
 * 
 * <H3>Mouse Utilities</H3>
 * <UL>
 * <LI>getMouseListener --- returns a mouse listener that will use the other
 * methods listed here.
 * 
 * <LI>singleClick --- called when a single mouse click event happens. The
 * default implementation does nothing.
 * 
 * <LI>doubleClick --- called when a double mouse click event happens. The
 * default implementation does nothing.
 * </UL>
 * 
 * <H3>Popup Utilities</H3>
 * <UL>
 * <LI>getPopup --- return the JPopupMenu for a component. The default
 * implementation returns null.
 * 
 * <LI>registerPopup --- set things up so the provided popup is displayed when
 * the triggering event is over the provided component.
 * 
 * <LI>unregisterPopup --- reset the class so that it does not know about the
 * specified component with respect to popup menus.
 * </UL>
 * 
 * <H3>Keyboard Events</H3>
 * <UL>
 * <LI>getKeyListener
 * <LI>returnKeyPressed
 * <LI>tabKeyPressed
 * </UL>
 * 
 * <H3>Menu Building</H3>
 * <UL>
 * <LI>buildMenuBar
 * <LI>buildPopupMenu
 * </UL>
 * 
 * <H3>Panel Structure</H3>
 * <UL>
 * <LI>createTopPanel
 * <LI>createCenterPanel
 * <LI>createBottomPanel
 * <LI>getBottomPanelForm
 * <LI>getWindowTitle
 * <LI>getHeadingString
 * <LI>getHeadingLabel
 * <LI>getSubheadingString
 * <LI>getSubheadingLabel
 * </UL>
 * 
 * <H3>Window Control Button</H3>
 * <UL>
 * <LI>cancelButtonPressed
 * <LI>closeButtonPressed
 * <LI>noButtonPressed
 * <LI>okButtonPressed
 * <LI>yesButtonPressed
 * </UL>
 */
public abstract class ContentPanel extends LTSPanel
	implements SimpleMouseListener, WindowListener
{
	/**
	 * Do not display a bottom panel.
	 */
	public static final int BOTTOM_PANEL_NONE = 0;

	/**
	 * Display a bottom panel that contains only a button labeled "close" that
	 * will cause the window/frame/whatever to close.
	 */
	public static final int BOTTOM_PANEL_CLOSE = 1;

	/**
	 * Display a bottom panel that contains an OK button and a cancel button.
	 */
	public static final int BOTTOM_PANEL_OK_CANCEL = 2;

	/**
	 * Display a bottom panel that contains a button labeled "Yes" another
	 * labeled "No" and a final one labeled "Cancel".
	 */
	public static final int BOTTOM_PANEL_YES_NO_CANCEL = 3;
	
	public static final int BOTTOM_PANEL_YES_NO = 4;
	
	public static final int BOTTOM_PANEL_OK = 5;
	
	/**
	 * Display only a button that says "cancel".  
	 * 
	 * <P/>
	 * Useful for progress displays for which a developer wants to allow a 
	 * way for the user to abort lengthy operations.
	 */
	public static final int BOTTOM_PANEL_CANCEL = 9;
	
	/**
	 * Display a button that says "select" and another that says "cancel".
	 */
	public static final int BOTTOM_PANEL_SELECT = 10;

	public static final int BUTTON_CLOSE = 0;
	public static final int BUTTON_OK = 1;
	public static final int BUTTON_CANCEL = 2;
	public static final int BUTTON_YES = 3;
	public static final int BUTTON_NO = 4;
	
	public static final int RESULT_OK = 0;
	public static final int RESULT_CANCEL = 1;
	public static final int RESULT_YES = 2;
	public static final int RESULT_NO = 3;

	private int myBottomPanelMode = BOTTOM_PANEL_NONE;
	
	protected String myWindowTitle = "";
	private String myHeadingString = null;
	
	/**
	 * A map from Component objects to the JPopupMenu that should be displayed
	 * for the component when the popup trigger occurs.
	 * 
	 * <P>
	 * This property may be empty but should never be null.
	 * 
	 * <P>
	 * Clients and subclasses should interact with this property via the
	 * registerPopup, unregisterPopup and getPopup methods. While
	 * getComponentToPopupMap and setComponentToPoupMap can be used instead,
	 * this is not encouraged.
	 * 
	 * <P>
	 * If getPopup returns null for a particular component, then no popup was
	 * explicitly associated with the component (via registerPopup).
	 */
	protected Map myComponentToPopupMap = new HashMap();

	/**
	 * Get a mouse listener that will invoke the "standard" mouse methods on
	 * this instance.
	 * 
	 * <P>
	 * The standard mouse methods are:
	 * 
	 * <UL>
	 * <LI>singleClick
	 * <LI>doubleClick
	 * <LI>getPopup
	 * </UL>
	 * 
	 * <P>
	 * See the class description and the method descriptions for details on
	 * these methods.
	 * 
	 * <P>
	 * This method should never return a null value. The underlying property
	 * uses "lazy initialization," so if the listener was null when this method
	 * was called, an instance will be created in response to this call.
	 * Thereafter, calls to this method will return the same value.
	 */
	protected CPMouseListener myMouseListener;

	/**
	 * The insets to use for the top, center and bottom panels.
	 * 
	 * <P>
	 * This defaults to 5 pixels for top, left and right. 0 pixels are used for
	 * bottom. If different values are desired, call setDefaultInstes prior to
	 * calling initialize.
	 */
	protected Insets myDefaultInsets = new Insets(5, 5, 5, 5);

	/**
	 * The window that contains the panel.
	 * 
	 * <P>
	 * A reference to the window is needed so that certain controls that want to
	 * close the window can do so.
	 */
	protected Window myWindow;

	
	protected int myResult;
	
	public int getResult()
	{
		return myResult;
	}
	
	public void setResult(int result)
	{
		myResult = result;
	}
	
	
	protected JPanel myTopPanel;
	
	public JPanel getTopPanel()
	{
		return myTopPanel;
	}
	
	
	protected JPanel myCenterPanel;
	
	public JPanel getCenterPanel ()
	{
		return myCenterPanel;
	}
	
	
	protected JPanel myBottomPanel;
	
	public JPanel getBottomPanel()
	{
		return myBottomPanel;
	}
	
	
	
	protected ContentPanel ()
	{}
	
	
	public ContentPanel (Window window) throws LTSException
	{
		initialize(window);
	}
	
	
	public void initialize (Container container) throws LTSException
	{
		addTopPanel();
		addCenterPanel();
		addBottomPanel();
		initializeWindow(container);
	}
	
	public ContentPanel(String windowTitle, String heading, int bottomPanelMode)
	{
		if (null == windowTitle && null != heading)
			windowTitle = heading;
		
		setWindowTitle(windowTitle);
		setHeadingString(heading);
		setBottomPanelMode(bottomPanelMode);		
	}
	
	
	protected boolean myExitOnClose = false;

	public boolean exitOnClose()
	{
		return myExitOnClose;
	}

	public void setExitOnClose(boolean exitOnClose)
	{
		myExitOnClose = exitOnClose;
	}

	public Insets getDefaultInsets()
	{
		return myDefaultInsets;
	}

	public void setDefaultInsets(Insets defaultInsets)
	{
		myDefaultInsets = defaultInsets;

		if (null == myDefaultInsets)
			myDefaultInsets = new Insets(0, 0, 0, 0);
	}

	/**
	 * Create a mouse listener that will call the singleClick, doubleClick and 
	 * getPopup methods of this class.
	 * 
	 * <H3>Description</H3>
	 * This method returns an instacle of {@link CPMouseListener} that will listen for 
	 * mouse events and then call the appropriate method on this class.  For a mouse 
	 * single click, it calls {@link #singleClick(Object)}, for a double click, it calls
	 * {@link #doubleClick(Object)} and for a right click it calls 
	 * {@link #getPopup(Component)}.
	 * 
	 * <P>
	 * The default implementations by this class for these methods do nothing execept 
	 * return, or return null (in the case of getPopup).  Subclasses can override these
	 * methods to be able to quickly setup a mouse listener.
	 * 
	 * <P>
	 * One catch to all this is that there is only <B>one</B> mouse listener returned
	 * by this method that is shared for all calls.  If you want to use this listener
	 * for multiple objects, and to have the action change depending on which object you
	 * are positioned over, you will need to check the source of the mouse click or 
	 * create a different mouse listener.
	 * 
	 * @return See above.
	 * @see CPMouseListener
	 * @see #singleClick(Object)
	 * @see #doubleClick(Object)
	 * @see #getPopup(Component)
	 */
	public CPMouseListener getMouseListener()
	{
		if (null == myMouseListener)
			myMouseListener = new CPMouseListener(this);

		return myMouseListener;
	}

	public Map getComponentToPopupMap()
	{
		return myComponentToPopupMap;
	}

	public void setComponentToPopupMap(Map componentToPopupMap)
	{
		myComponentToPopupMap = componentToPopupMap;

		if (null == myComponentToPopupMap)
			myComponentToPopupMap = new HashMap();
	}

	/**
	 * Associate a JPopupMenu with a component.
	 * 
	 * <P>
	 * This method establishes a popup menu for a particular GUI component so
	 * that, when the "popup trigger" event occurs, the menu passed to this
	 * method will be displayed.
	 * 
	 * @param component
	 *            The component for which the client wants to associate a popup
	 *            menu.
	 * 
	 * @param menu
	 *            the menu to associate.
	 */
	public void registerPopup(Component component, JPopupMenu menu)
	{
		myComponentToPopupMap.put(component, menu);
		component.removeMouseListener(getMouseListener());
		component.addMouseListener(getMouseListener());
	}

	/**
	 * Remove any JPopupMenu associated with the component.
	 * 
	 * <P>
	 * That is, the getPopup method should return null for the specified
	 * component after calling this method with said component. If the component
	 * was not registered before this call was made, then calling this method
	 * will have no effect.
	 * 
	 * @param component
	 *            The component to unregister.
	 */
	public void unregisterPopup(Component component)
	{
		myComponentToPopupMap.remove(component);
	}

	/**
	 * Return the JPopupMenu associated with the component.
	 * 
	 * <P>
	 * A menu should be associated wtih the component before this method is
	 * called by invoking registerPopup. The default action is to return null.
	 */
	public JPopupMenu getPopup(Component component)
	{
		return (JPopupMenu) myComponentToPopupMap.get(component);
	}

	/**
	 * Called when the mouse is "single clicked" over a component that
	 * previously set up a mouse listener.
	 * 
	 * <P>
	 * The default implementation simply returns (does nothing).
	 * 
	 * <P>
	 * Typically, this is triggered by a single left click of the mouse over a
	 * registered component. Components are registered via a call to Component.
	 * addMouseListener(getMouseListener()).
	 */
	public void singleClick(Object source)
	{
	}

	/**
	 * Called when the mouse is "double clicked" over a component that
	 * previously set up a mouse listener.
	 * 
	 * <P>
	 * The default implementation simply returns (does nothing).  Note that you must
	 * register the listener with the desired component before using this method.
	 * 
	 * <P>
	 * Typically, this is triggered by a double left click of the mouse over a
	 * registered component. Components are registered via a call to Component.
	 * addMouseListener(getMouseListener()).
	 * 
	 * @see #getMouseListener()
	 */
	public void doubleClick(Object source)
	{
	}

	public JLabel getHeadingLabel()
	{
		return myHeadingLabel;
	}
	
	public String getHeadingString()
	{
		return myHeadingString;
	}
	
	public void setHeadingString (String headingString)
	{
		myHeadingString = headingString;
		if (null != myHeadingLabel)
		{
			myHeadingLabel.setText(headingString);
		}
	}

	protected JLabel mySubHeading;
	
	public JLabel getSubHeadingLabel()
	{
		return mySubHeading;
	}

	
	protected String mySubHeadingString;
	
	public String getSubHeadingString()
	{
		return mySubHeadingString;
	}
	
	public void setSubHeadingString (String s)
	{
		mySubHeadingString = s;
	}

	
	protected JLabel myHeadingLabel;
	
	public JPanel createTopPanel()
	{
		LTSPanel panel = null;

		myHeadingLabel = getHeadingLabel();
		if (null == myHeadingLabel && null != getHeadingString())
		{
			Font font = new Font("ariel", Font.BOLD, 16);
			myHeadingLabel = new JLabel(getHeadingString());
			myHeadingLabel.setFont(font);
		}

		mySubHeading = getSubHeadingLabel();
		if (null == mySubHeading && null != getSubHeadingString())
		{
			Font font = new Font("times", 0, 12);
			mySubHeading = new JLabel(getSubHeadingString());
			mySubHeading.setFont(font);
		}

		if (null != myHeadingLabel || null != mySubHeading)
		{
			panel = new LTSPanel();

			if (null != myHeadingLabel)
			{
				panel.addCenteredLabel(myHeadingLabel,5);
				panel.nextRow();
			}

			if (null != mySubHeading)
			{
				panel.addCenteredLabel(mySubHeading,5);
			}
		}

		return panel;
	}

	public int getBottomPanelMode()
	{
		return this.myBottomPanelMode;
	}
	
	public void setBottomPanelMode(int bottomPanelMode)
	{
		this.myBottomPanelMode = bottomPanelMode;
	}

	public void closeWindow()
	{
		getWindow().setVisible(false);
		
		if (exitOnClose())
			System.exit(0);
	}

	
	
	protected boolean myOkToClose;
	
	public boolean okToClose()
	{
		return myOkToClose;
	}
	
	public void setOkToClose(boolean okToClose)
	{
		myOkToClose = okToClose;
	}
	
	
	protected JButton myOkButton;
	
	public LTSPanel createControlPanel (int mode)
	{
		if (BOTTOM_PANEL_NONE == getBottomPanelMode())
			return null;

		LTSPanel panel = new LTSPanel();

		switch (getBottomPanelMode())
		{
			case BOTTOM_PANEL_CLOSE :
			{
				JButton button = new JButton("Close");
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						closeButtonPressed();
					}
				});
				panel.addCenteredLabel(button, getDefaultInsets());
				break;
			}
			
			case BOTTOM_PANEL_OK :
			{
				myOkButton = new JButton("OK");
				ActionListener alistener = new ActionListener() {
					public void actionPerformed(ActionEvent junk) {
						okButtonPressed();
					}
				};
				myOkButton.addActionListener(alistener);
				panel.addButton(myOkButton);
				break;
			}

			case BOTTOM_PANEL_OK_CANCEL :
			{
				myOkButton = new JButton("OK");
				myOkButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						okButtonPressed();
					}
				});
				panel.addButton(myOkButton, getDefaultInsets());

				JButton button = new JButton("Cancel");
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						cancelButtonPressed();
					}
				});
				panel.addButton(button, getDefaultInsets());

				break;
			}

			case BOTTOM_PANEL_YES_NO :
			{
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent junk) {
						yesButtonPressed();
					}
				};
				JButton button = new JButton("Yes");
				button.addActionListener(listener);
				panel.addButton(button);
				
				listener = new ActionListener() {
					public void actionPerformed (ActionEvent junk) {
						noButtonPressed();
					}
				};
				button = new JButton("No");
				button.addActionListener(listener);
				panel.addButton(button);
				
				break;
			}
			
			case BOTTOM_PANEL_YES_NO_CANCEL :
			{
				JButton button = new JButton("Yes");
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						yesButtonPressed();
					}
				});
				panel.addButton(button, getDefaultInsets());

				button = new JButton("No");
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						noButtonPressed();
					}
				});
				panel.addButton(button, getDefaultInsets());

				button = new JButton("Cancel");
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						cancelButtonPressed();
					}
				});
				panel.addButton(button, getDefaultInsets());
				break;
			}
			
			case BOTTOM_PANEL_CANCEL :
			{
				JButton button = new JButton("Cancel");
				ActionListener listener = new ActionListener() {
					public void actionPerformed (ActionEvent junk) {
						cancelButtonPressed();
					}
				};
				button.addActionListener(listener);
				panel.addButton(button, getDefaultInsets());
				break;
			}
			
			case BOTTOM_PANEL_SELECT :
			{
				JButton button = new JButton("Select");
				ActionListener listener = new SimpleAction() {
					public void action() {
						selectButtonPressed();
					}
				};
				
				button.addActionListener(listener);
				panel.addButton(button, getDefaultInsets());
				
				button = new JButton("Cancel");
				listener = new SimpleAction() {
					public void action() {
						cancelButtonPressed();
					}
				};
				button.addActionListener(listener);
				
				panel.addButton(button, getDefaultInsets());
				break;
			}

			default :
				throw new RuntimeException("Unrecognized code: "
						+ getBottomPanelMode());
		}

		return panel;
	}
	
	
	protected void selectButtonPressed()
	{
		myResult = RESULT_OK;
		closeWindow();
	}

	public JPanel createBottomPanel()
	{
		return createControlPanel(getBottomPanelMode());
	}

	public JMenuBar basicBuildMenuBar() throws LTSException
	{
		//
		// method suspended until this mess is dealt with
		//
		throw new NotImplementedException();
	}
	
	
	public JMenuBar buildMenuBar() throws LTSException
	{
		try
		{
			if (null != getMenuSpec())
				return basicBuildMenuBar();
			else
				return null;
		}
		catch (RuntimeException e)
		{
			throw new LTSException(e);
		}
	}
	
	
	public void initializeMenuBar() throws LTSException
	{
		JMenuBar menuBar = buildMenuBar();
		if (null == menuBar)
			return;

		Window win = getWindow();
		if (win instanceof JFrame)
		{
			JFrame frame = (JFrame) getWindow();
			frame.setJMenuBar(menuBar);
			
			if (null == frame.getJMenuBar())
				throw new IllegalStateException("menu bar should not be null at this point");
		}
		else if (win instanceof JDialog)
		{
			JDialog dialog = (JDialog) getWindow();
			dialog.setJMenuBar(menuBar);
		}
		else
		{
			throw new IllegalArgumentException(
					"Invalid configuration: asked to add a JMenuBar to a "
							+ "window of class " + win.getClass().getName());
		}
	}
	
	
	public Dimension getWindowSize()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();

		int width = dimension.width / 2;
		int height = dimension.height / 2;

		return new Dimension(width, height);
	}
	
	
	public Dimension toPercentOfScreen (double percent)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		int width = (int) (dim.width * percent);
		int height = (int) (dim.height * percent);
		
		return new Dimension(width, height);
	}

	public void reposition(Window window)
	{
		Dimension wdim = window.getSize();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scim = tk.getScreenSize();
		
		int y = scim.height/2 - wdim.height/2;
		int x = scim.width/2 - wdim.width/2;
		
		window.setLocation(x,y);
	}

	
	/**
	 * Set the content for the provided window to be this panel.
	 * 
	 * <P/>
	 * This method is primarily for those windows that will show several 
	 * different panels in succession --- wizard style GUIs for example.
	 * The method removes anything else that the window used to display
	 * and replaces it with this panel.
	 *  
	 * @param window The window that this panel will display in.  This 
	 * parameter is expected to be a JFrame or a JDialog.  If it is not,
	 * the method throws a RuntimeException.
	 */
	public void makeContentFor (Window window)
	{
		Container contentPane;
		
		if (window instanceof JFrame)
		{
			JFrame frame = (JFrame) window;
			contentPane = frame.getContentPane();
			frame.setTitle(getWindowTitle());
		}
		else if (window instanceof JDialog)
		{
			JDialog dialog = (JDialog) window;
			contentPane = dialog.getContentPane();
			dialog.setTitle(getWindowTitle());
		}
		else
		{
			throw new RuntimeException(
					"Window must be an instance of JFrame or JDialog.  Window class: "
							+ window.getClass().getName());
		}
		
		LayoutManager layoutManager = new GridBagLayout();
		SimpleGridBagConstraint constraint = SimpleGridBagConstraint
				.fillConstraint(0, 0);

		contentPane.removeAll();
		contentPane.setLayout(layoutManager);
		contentPane.add(this, constraint);

		window.addWindowListener(this);
	}
	
	
	/**
	 * 
	 * @param window
	 */
	public void setWindowTitle (String title)
	{
		this.myWindowTitle = title;
		Window win = getWindow();
		
		
		if (null == win)
			return;
		else if (win instanceof JFrame)
		{
			JFrame frame = (JFrame) win;
			frame.setTitle(title);
		}
		else
		{
			JDialog dialog = (JDialog) win;
			dialog.setTitle(title);
		}
	}
	
	public void unsetContentFor (Window window)
	{
		window.removeWindowListener(this);
	}
	
	
	public void initializeWindow(Window window) throws LTSException
	{
		if (window instanceof JFrame)
		{
			JFrame jframe = (JFrame) window;
			jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
		else if (window instanceof JDialog)
		{
			JDialog jdialog = (JDialog) window;
			jdialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
		
		Dimension dimension = getWindowSize();
		window.setSize(dimension);
		reposition(window);

		makeContentFor(window);
		initializeMenuBar();
	}
	
	
	public void initializeWindow(Container container) throws LTSException
	{
		if (!(container instanceof Window))
			throw new IllegalArgumentException();
		
		Window window = (Window) container;
		initializeWindow(window);
	}

	
	
	protected void addBottomPanel() throws LTSException
	{
		myBottomPanel = createBottomPanel();
		if (null != myBottomPanel)
		{
			addHorizontal(myBottomPanel, getDefaultInsets());
			nextRow();
		}
	}

	protected void addCenterPanel() throws LTSException
	{
		myCenterPanel = createCenterPanel();
		if (null != myCenterPanel)
		{
			addFill(myCenterPanel, getDefaultInsets());
			nextRow();
		}
	}

	protected void addTopPanel() throws LTSException
	{
		myTopPanel = createTopPanel();
		if (null != myTopPanel)
		{
			addHorizontal(myTopPanel, getDefaultInsets());
			nextRow();
		}
	}

	public void windowClosing(WindowEvent event)
	{
		if (exitOnClose())
			System.exit(0);
		else
		{
			Window window = getWindow();
			if (null != window)
				window.setVisible(false);
		}
	}

	public void windowDeactivated(WindowEvent event)
	{}

	public void windowDeiconified(WindowEvent event)
	{}

	public void windowIconified(WindowEvent event)
	{}

	public void windowOpened(WindowEvent event)
	{}
	

	public void notImplemented()
	{
		JOptionPane.showMessageDialog(this,
				"This feature has not been implemented.");
	}
	
	
	public String getWindowTitle ()
	{
		return this.myWindowTitle;
	}
	
	public void closeButtonPressed()
	{
		closeWindow();
	}
	
	
	public void done()
	{
		
	}
	
	
	public void okButtonPressed()
	{
		setResult(RESULT_OK);
		closeWindow();
		tellSuccessListeners();
		done();
	}
	
	/**
	 * These are objects who want to be notified when the window is "a success."
	 * 
	 * <P>
	 * Generally speaking, this means that the user clicked on the OK button in
	 * situations where the user could have clicked cancel.  There are other situations
	 * like the user double-clicks on a list entry, selecting that entry and 
	 * closing the window.
	 * </P>
	 */
	protected CallbackListenerHelper mySuccessListeners = 
		new CallbackListenerHelper();
	
	protected void tellSuccessListeners()
	{
		mySuccessListeners.fire(this);
	}

	public void addSuccessListener(Callback callback)
	{
		mySuccessListeners.addListener(callback);
	}
	
	public void removeSuccessListener(Callback callback)
	{
		mySuccessListeners.removeListener(callback);
	}
	
	
	public void cancelButtonPressed()
	{
		setResult(RESULT_CANCEL);
		closeWindow();
		done();
	}
	
	public void yesButtonPressed()
	{
		setResult(RESULT_YES);
		closeWindow();
		done();
	}
	
	public void noButtonPressed()
	{
		setResult(RESULT_NO);
		closeWindow();
		done();
	}
	
	
	public void compact ()
	{
		Dimension dim;
		
		if (null != getTopPanel())
		{
			dim = getTopPanel().getPreferredSize();
			getTopPanel().invalidate();
		}
		
		if (null != getCenterPanel())
		{
			dim = getCenterPanel().getPreferredSize();
			getCenterPanel().setMinimumSize(dim);
		}
		
		if (null != getBottomPanel())
		{
			dim = getBottomPanel().getPreferredSize();
			getBottomPanel().setMinimumSize(dim);
		}
		
		getWindow().validate();
		dim = getWindow().getPreferredSize();
		getWindow().setSize(dim);
	}
	
	
	public void centerWindow ()
	{
		Window window = getWindow();
		if (null != window)
			reposition(window);
	}
	
	
	public void processActionEvent (ActionEvent event)
	{
		
	}
	
	public ActionListener getActionListener()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				processActionEvent(event);
			}
		};
	}
	
	
	protected String[][] myMenuSpec;
	
	public String[][] getMenuSpec()
	{
		return myMenuSpec;
	}
	
	public void setMenuSpec(String[][] menuSpec)
	{
		myMenuSpec = menuSpec;
	}
	

	/**
	 * Suspend the calling thread until the provided window closes.
	 * 
	 * <P>
	 * This method allows instances of this class that are using 
	 * {@link JFrame} as a display to simulate dialog behavior.  Specifically,
	 * the calling thread creates an instance of the CloseWaiter class, 
	 * subscribes to the provided Window object, and then calls 
	 * {@link Object#wait()}.  When the target window closes, the waiter is 
	 * notified, and this method then returns.
	 * 
	 * <P>
	 * The method takes responsibility for making the target window  visible.
	 * 
	 * <P>
	 * The method synchronizes on the CloseWaiter that it creates to avoid 
	 * race conditions.
	 * 
	 * @param window
	 */
	public void waitForClose (Window window)
	{
		CloseWaiter cwaiter = new CloseWaiter();
		cwaiter.waitFor(window);
	}
	
	public JPanel createCenterPanel() throws LTSException
	{
		return null;	
	}

	/**
	 * A class used to wait for a window to close.
	 * 
	 * <P>
	 * This class helps simulate modal dialog behavior by calling 
	 * {@link Object#notify()} on one object when an instance of 
	 * {@link Window} sends out a {@link WindowListener#windowClosed(java.awt.event.WindowEvent)}
	 * event. 
	 * 
	 * @author cnh
	 */
	public static class CloseWaiter extends WindowAdapter
	{
		public Object waiter;
		
		public CloseWaiter ()
		{
			initialize();
		}
		
		public void initialize()
		{ 
			this.waiter = this;
		}
		
		
		public void windowClosed (WindowEvent event)
		{
			this.notify();
		}
		
		
		synchronized public void waitFor (Window window)
		{
			try
			{
				window.addWindowListener(this);
				window.setVisible(true);
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Cause the "OK" button to be activated when a certain keystroke is entered.
	 * <P>
	 * This method uses {@link KeyStroke#getKeyStroke(String)} to get the keystroke that
	 * corresponds for the input keyString. It uses {@link JComponent#getInputMap(int))},
	 * to get the OK button's input map, with the condition of
	 * {@link JComponent#WHEN_IN_FOCUSED_WINDOW}. It then gets the action map for the
	 * button via {@link JComponent#getActionMap()} and sets the corresponding action to
	 * {@link #okButtonPressed()}.
	 * <P>
	 * This means that, any time the keySring is entered in this window, the
	 * okButtonPressed method will be invoked.
	 * 
	 * @param keyString
	 *        The key sequence, as in KeyStroke.getKeyStroke(String), that will cause
	 *        okButtonPressed to be invoked.
	 * @param actionName
	 *        the name to use for the action in the various maps.
	 */
	@SuppressWarnings("serial")
	public void makeOkDefaultFor (String keyString, String actionName)
	{
		KeyStroke ks = KeyStroke.getKeyStroke(keyString);
		InputMap imap = myOkButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(ks, actionName);
		
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				okButtonPressed();
			}
		};
		myOkButton.getActionMap().put(actionName, action);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}
}