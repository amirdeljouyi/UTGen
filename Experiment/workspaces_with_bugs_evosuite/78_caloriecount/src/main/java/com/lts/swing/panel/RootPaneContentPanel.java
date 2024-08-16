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
package com.lts.swing.panel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.JMenuBar;
import javax.swing.JRootPane;

import com.lts.LTSException;
import com.lts.swing.SimpleGridBagConstraint;
import com.lts.swing.contentpanel.ContentPanel;
import com.lts.swing.rootpane.DefaultRootPaneFactory;
import com.lts.swing.rootpane.LTSRootPane;
import com.lts.swing.rootpane.LTSRootPaneFactory;
import com.lts.swing.thread.BlockThread;

/**
 * A ContentPanel that uses LTSRootPane instead of instanceof when working with 
 * windows ("root panes").
 * 
 * <P>
 * See the discussion of {@link LTSRootPane} for more information on root panes and their 
 * capabilities.
 * 
 * <P>
 * The practical difference between this class and {@link ContentPanel} is that 
 * instances of this class can be used in applets whereas ContentPanel cannot.  The class 
 * can also be used inside JWindow and JInternalFrame, but the author uses those classes
 * so rarely that the capability is unused.
 * 
 * <P>
 * Not all capabilities are supported by all root panes.  For example, applets do 
 * not support menu bars.  In these situations, the class "silently ignores" unsupported
 * requests.  That is, trying to use a menu bar inside an applet will not cause an
 * exception, but it does not result in a menu bar being created either.
 * 
 * <P>
 * Root panes vary in the capabilities that they support.  All root panes support
 * location, size and contentPane.  In addition to this, other properties that
 * are somtimes supported include:
 * 
 * <P>
 * <TABLE border="1">
 * <TR>
 *     <TD><B>Property</B></TD>
 *     <TD><B>JApplet</B></TD>
 *     <TD><B>JDialog</B></TD>
 *     <TD><B>JFrame</B></TD>
 *     <TD><B>JInternalFrame</B></TD>
 *     <TD><B>JWindow</B></TD>
 * </TR>
 * 
 * <TR>
 *     <TD><B>closeAction</B></TD>
 *     <TD> </TD>		<!-- JApplet        -->
 *     <TD>X</TD>		<!-- JDialog        -->
 *     <TD>X</TD>		<!-- JFrame         -->
 *     <TD>X</TD>		<!-- JInternalFrame -->
 *     <TD> </TD>		<!-- JWindow        -->
 * </TR>
 * 
 * <TR>
 *     <TD><B>menuBar</B></TD>
 *     <TD> </TD>		<!-- JApplet        -->
 *     <TD> </TD>		<!-- JDialog        -->
 *     <TD>X</TD>		<!-- JFrame         -->
 *     <TD>X</TD>		<!-- JInternalFrame -->
 *     <TD></TD>		<!-- JWindow        -->
 * </TR>
 * 
 * <TR>
 *     <TD><B>title</B></TD>
 *     <TD></TD>		<!-- JApplet        -->
 *     <TD>X</TD>		<!-- JDialog        -->
 *     <TD>X</TD>		<!-- JFrame         -->
 *     <TD>X</TD>		<!-- JInternalFrame -->
 *     <TD></TD>		<!-- JWindow        -->
 * </TR>
 * 
 * <TR>
 *     <TD><B>windowListener</B></TD>
 *     <TD></TD>		<!-- JApplet        -->
 *     <TD>X</TD>		<!-- JDialog        -->
 *     <TD>X</TD>		<!-- JFrame         -->
 *     <TD></TD>		<!-- JInternalFrame -->
 *     <TD>X</TD>		<!-- JWindow        -->
 * </TR>
 * 
 * </TABLE>
 * 
 * <P>
 * If the client wants to ensure that an instance of LTSRootPane supports a particular 
 * capability, methods exist that indicate whether the property is supported.  These
 * methods use the naming convention "supports&lt;property&gt;"  For example, 
 * supportsMenuBar.
 * </P>
 * 
 * <H2>
 * <P>
 * </P>
 * 
 * @author cnh
 */
public class RootPaneContentPanel extends ContentPanel
{
	private static final long serialVersionUID = 1L;

	protected LTSRootPane myLTSRootPane;
	static protected LTSRootPaneFactory ourRootPaneFact;
	
	
	protected RootPaneContentPanel()
	{}
	
	public RootPaneContentPanel (Container container) throws LTSException
	{
		initialize(container);
	}
	
	
	public void initialzie (Window window)
	{
		String msg 
			= "Please use " + getClass() + ".initialize(Container) instead";
		throw new RuntimeException(msg);
	}
	
	
	public void initialize (Container container) throws LTSException
	{
		myLTSRootPane = getRootPaneFactory().buildRootPane(container);
		super.initialize(container);
		initializeContainer(container);
	}
	
	public LTSRootPane getLTSRootPane()
	{
		return myLTSRootPane;
	}
	
	public void setLTSRootPane (LTSRootPane ltsRootPane)
	{
		myLTSRootPane = ltsRootPane;
	}
	
	
	LTSRootPaneFactory getRootPaneFactory ()
	{
		if (null == ourRootPaneFact)
			ourRootPaneFact = new DefaultRootPaneFactory();
		
		return ourRootPaneFact;
	}
	
	/**
	 * Move the root pane to the center of the screen.
	 * 
	 * @param theRootPane The window or "root pane" to reposition.
	 */
	public void reposition(LTSRootPane theRootPane)
	{
		Dimension wdim = theRootPane.getComponent().getSize();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scim = tk.getScreenSize();
		
		int y = scim.height/2 - wdim.height/2;
		int x = scim.width/2 - wdim.width/2;
		Point p = new Point(x,y);
		
		theRootPane.getComponent().setLocation(p);
	}
	
	
	public void setWindowTitle(String title)
	{
		myWindowTitle = title;
		LTSRootPane lrp = getLTSRootPane();
		if (null != lrp)
			lrp.setTitle(title);
	}
	
	/**
	 * Set the content for the provided window to be this panel.
	 * <P>
	 * This method is primarily for those windows that will show several different panels
	 * in succession --- wizard style GUIs for example. The method removes anything else
	 * that the window used to display and replaces it with this panel.
	 * 
	 * @param window
	 *        The window that this panel will display in. This parameter is expected to be
	 *        a JFrame or a JDialog. If it is not, the method throws a RuntimeException.
	 */
	public void makeContentFor (LTSRootPane theRootPane)
	{
		Container contentPane = theRootPane.getContentPane();
		
		
		LayoutManager layoutManager = new GridBagLayout();
		SimpleGridBagConstraint constraint = SimpleGridBagConstraint
				.fillConstraint(0, 0);

		contentPane.removeAll();
		contentPane.setLayout(layoutManager);
		contentPane.add(this, constraint);
		
		theRootPane.setTitle(getWindowTitle());
		theRootPane.addWindowListener(this);
	}
	
	/**
	 * Make this object the root pane's content, resize the window, center it and setup
	 * the menu bar.
	 * <P>
	 * The container parameter should be the "root pane" for the window --- and instance
	 * of JDialog, JFrame, etc. The method resets the root pane content by calling
	 * {@link #makeContentFor(LTSRootPane)}. {@link #getWindowSize()} is used to
	 * determine the size for the root pane. {@link #reposition(LTSRootPane)} centers the
	 * window on the screen and {@link #initializeMenuBar()} is used to set up the menu
	 * bar.
	 * 
	 * @param container
	 *        The root pane in which to place the container.
	 * @throws LTSException
	 *         If a problem is encountered during initialization. This method does not
	 *         throw any exceptions directly, but the methods called might.
	 */
	public void initializeContainer (Container container) throws LTSException
	{
		LTSRootPaneFactory fact = getRootPaneFactory();
		LTSRootPane lrp = fact.buildRootPane(container);
		setLTSRootPane(lrp);
		
		Dimension dimension = getWindowSize();
		lrp.getComponent().setSize(dimension);
		
		reposition(lrp);
		makeContentFor(lrp);
		
		initializeMenuBar();
	}
	
	/**
	 * Setup the root pane to use the instance's menu bar, assuming it supports menu bars.
	 * <P>
	 * The method uses {@link #buildMenuBar()} to create the menu bar. If the instance has
	 * no menu bar or the root pane is currently undefined, the method simply returns.
	 * 
	 * @exception LTSException
	 *            The most likely reason for this exception is that the menu specification
	 *            is malformed or it refers to a method that does not exist.
	 */
	public void initializeMenuBar() throws LTSException
	{
		JMenuBar menuBar = buildMenuBar();
		if (null == menuBar)
			return;

		if (null == myLTSRootPane)
			return;
		
		myLTSRootPane.setMenuBar(menuBar);
	}
	
	/**
	 * Close the window that the panel is contained in.
	 * 
	 * <P>
	 * The method gets the root pane for the panel and sets visible to false.  If the
	 * panel does not have a root pane, then the method simply returns.
	 */
	public void closeWindow()
	{
		JRootPane rootPane = getRootPane();
		if (null == rootPane)
			return;
		
		Container parent = rootPane.getParent();
		if (null != parent)
			parent.setVisible(false);
	}
	
	/**
	 * Simplify dealing with "dialog-like" windows.
	 *
	 * <P>
	 * Use this method in conjunction with methods like resultOK, resultCancel, 
	 * etc. to quickly create dialog-like windows for editing data, etc.
	 * </P>
	 * 
	 * <P>
	 * For example, suppose you have a panel that displays some information about
	 * a person such as name, address, etc.  The panel allows the user to change some
	 * of the information and has OK and Cancel buttons to allow the user to accept
	 * or discard their changes.  Here is a skeleton of this might be implemented:
	 * </P>
	 * 
	 * <CODE>
	 * <PRE>
	 * public void initialize (Container container)
	 * {
	 *     setBottomPanelForm(BOTTOM_PANEL_OK_CANCEL);
	 *     ...
	 *     super.initializeWindow(container);
	 *     ...
	 * }
	 * 
	 * ...
	 * public void editData (Person person)
	 * {
	 *     myFirstNameField.setText(person.getFirstName());
	 *     myLastNameField.setText(person.getLastName());
	 *     ...
	 *     displayAndEvaluate();
	 * }
	 * ...
	 * public void evaluateResultOk()
	 * {
	 *     person.setFirstName(myFirstNameField.getText());
	 *     person.setLastName(myLastNameFieled.getText());
	 *     ...
	 *     &lt;pass this data onto the rest of the system&gt;
	 * }
	 * 
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * This method uses the {@link BlockThread} to create a new thread of execution to
	 * display the panel's container.  The thread blocks, waiting for the window to to
	 * close.  At that time, it calls the evaluateResult method.  evaluateResult, in 
	 * turn calls methods related to the value returned by getResult().
	 * </P>
	 *
	 * <P>
	 * Note that when displayAndEvaluate returns, the user is NOT done editing ---
	 * the user is done when one of the evaluateResult methods is called.
	 */
	public void displayAndEvaluate()
	{
		BlockThread thread;
		thread = new BlockThread(getLTSRootPane().getWindow()) 
		{
			public void afterDisplay()
			{
				evaluateResult();
			}
		};
		
		thread.display();
	}
	
	/**
	 * Evaluate the results after the user has closed the window that this panel
	 * lives in.
	 * 
	 * <P>
	 * The method calls one of the evaluateResult methods based on the code returned
	 * by getResult.  The methods include:
	 * </P>
	 * 
	 * <UL>
	 * <LI>evaluateResultCancel</LI>
	 * <LI>evaluateResultNo</LI>
	 * <LI>evaluateResultOK</LI>
	 * <LI>evaluateResultYes</LI>
	 * </UL>
	 * 
	 * <P>
	 * If the result code does not correspond to a known code, the method throws 
	 * an {@link IllegalStateException}, so subclasses that define new result codes,
	 * should take care to check for them before calling this version of the method.
	 * </P>
	 */
	protected void evaluateResult()
	{
		switch(getResult())
		{
			case RESULT_CANCEL :
				evaluateResultCancel();
				break;
				
			case RESULT_NO :
				evaluateResultNo();
				break;
				
			case RESULT_OK :
				evaluateResultOK();
				break;
				
			case RESULT_YES :
				evaluateResultYes();
				break;
				
			default :
				String msg = "Unrecognized result code: " + getResult();
				throw new IllegalStateException(msg);
		}
	}

	/**
	 * The user closed the window by clicking on the "Yes" button.
	 * 
	 * <P>
	 * This version of the method simply returns without doing anything.
	 * </P>
	 */
	protected void evaluateResultYes()
	{}

	/**
	 * The user closed the window by clicking on the "OK" button.
	 * 
	 * <P>
	 * This version of the method simply returns without doing anything.
	 * </P>
	 */
	protected void evaluateResultOK()
	{}

	/**
	 * The user closed the window by clicking on the "No" button.
	 * 
	 * <P>
	 * This version of the method simply returns without doing anything.
	 * </P>
	 */
	protected void evaluateResultNo()
	{}

	/**
	 * The user closed the window by clicking on the "Cancel" button.
	 * 
	 * <P>
	 * This version of the method simply returns without doing anything.
	 * </P>
	 */
	protected void evaluateResultCancel()
	{}

	public void showAndBlock()
	{
		// BlockingThread.staticDisplayAndWait(getWindow());
		getWindow().setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void showWindow()
	{
		getLTSRootPane().setVisible(true);
	}
	
}
