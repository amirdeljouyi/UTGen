//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
// The com.lts.application library is free software; you can redistribute it
// and/or modify it under the terms of the Lesser GNU General Public License as
// published by the Free Software Foundation; either version 2.1 of the
// License, or (at your option) any later version.
//
// The com.lts.application library is distributed in the hope that it will be
// useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Lesser GNU
// General Public License for more details.
//
// You should have received a copy of the Lesser GNU General Public License
// along with the com.lts.application library; if not, write to the Free
// Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
// 02110-1301 USA
//
package com.lts.application.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.menu.ActionMenuBuilder;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.SwingUtils;
import com.lts.swing.TextWindow;
import com.lts.swing.menu.MenuBuilder;
import com.lts.swing.panel.RootPaneContentPanel;
import com.lts.swing.rootpane.LTSRootPane;
import com.lts.util.StringUtils;

/**
 * A ContentPanel that defines a number of commonly used methods that pertain to 
 * using Applications.
 * 
 * <H3>Description</H3>
 * This class provides a number of methods that are useful when dealing with 
 * what amounts to Application windows.  Because of the way Swing/AWT works, one
 * cannot have a single class that is both a JFrame and a JDialog, hence the use 
 * of a panel that is associated with the common ancestor of JFrame and JDialog, 
 * that is, Window.
 * 
 * <P>
 * At any rate, the functionality provided by this class is divided into the following
 * categories:
 * 
 * <P>
 * <UL>
 * <LI><A href="#menu">Menu-Oriented Methods</A>
 * <LI><A href="#title">Window Title-Oriented Methods</A>
 * <LI><A href="#misc">Miscellaneous Methods</A>
 * </UL>
 * 
 * <H3><A name="menu">Menu-Oriented Methods</A></H3>
 * This class is intended to be used with {@link Application}.  It provides functionality
 * that I have found myself implementing over and over again, especially with respect to
 * providing menu bar methods like "create new" and "save as...".
 * 
 * <P>
 * The commonly used, menu-oriented methods provided include:
 * <UL>
 * <LI>createNew --- File>New
 * <LI>open 	 --- File>Open
 * <LI>save 	 --- File>Save
 * <LI>saveAs 	 --- File>Save As...
 * <LI>quit 	 --- File>Exit
 * </UL>
 * 
 * <H3>Window Title-Oriented Methods</H3>
 * The class also defines common notion for how to provide a title for a window that
 * contains the panel.  The pattern is:
 * <PRE>
 * &lt;view&gt; - &lt;file&gt; - &lt;application&gt;
 * </PRE>
 * 
 * <P>
 * For example:
 * 
 * <P>
 * <PRE>
 * Java - ApplicationContentPanel.java - Eclispse Platform
 * </PRE>
 * 
 * <P>
 * The title of the window is set by calling {@link #resetContainerTitle()}.  That 
 * method gathers together the various parts and sets the title of the window associated
 * with the panel.  It also handles situations where the various parts of the title
 * are undefined.  See the method description for details.
 * 
 * <P>
 * The "file" portion is handled by {@link #getFileName()}.
 * 
 * <P>
 * The "view" portion is handled by {@link #getViewName()}.  That method is abstract
 * and must be implemented by a subclass.
 * 
 * <H3><A name="misc">Miscellaneous Methods</A></H3>
 * The miscellaneous methods include:
 * 
 * <P>
 * <UL>
 * <LI>showException - display an error.
 * <LI>getMessage - get a message from a resource bundle.
 * </UL>
 * 
 * @author cnh
 *
 */
public abstract class ApplicationContentPanel extends RootPaneContentPanel
{
	protected abstract String getViewName();
	
	///////////////////////////////////////////////////////////////////////
	//                         File                                      //
	///////////////////////////////////////////////////////////////////////
	
	protected SimpleThreadedAction fileNew = new SimpleThreadedAction() {
		public void action() { createNew(); }
	};
	protected SimpleThreadedAction fileOpen = new SimpleThreadedAction() {
		public void action() { open(); }
	};
	protected SimpleThreadedAction fileSave = new SimpleThreadedAction() {
		public void action() { save(); }
	};
	protected SimpleThreadedAction fileSaveAs = new SimpleThreadedAction() {
		public void action() { saveAs(); }
	};
	protected SimpleThreadedAction fileExit = new SimpleThreadedAction() {
		public void action() { quit(); }
	};
	protected SimpleThreadedAction fileExport = new SimpleThreadedAction() {
		public void action() { exportFiles(); }
	};
	protected SimpleThreadedAction fileImport = new SimpleThreadedAction() {
		public void action() { importFiles(); }
	};
	
	protected Object[][] SPEC_FILE_ACTION_MENU = {
			{ "File" },
				{ "New",			fileNew },
				{ "Open...",		fileOpen },
				{},
				{ "Save", 			fileSave },
				{ "Save As...", 	fileSaveAs },
				{ },
				{ "Export...",		fileExport },
				{ "Import...",		fileImport },
				{ "Exit", 			fileExit },
		};

	
	protected ActionListener myActionListener;
	
	protected ApplicationContentPanel ()
	{}
	
	protected void importFiles() 
	{}
	
	protected void exportFiles() 
	{}
	
	public static final String TEXT_GET_WINDOW_SIZE =
		  "@Override\n"
		+ "public Dimension getWindowSize()\n" 
		+ "{\n"
		+ "\treturn new Dimension(";
	public static final String PROPERTY_WINDOW_SIZE = "Size";
	
	/**
	 * This is used to ensure we do not subscribe to events like resize multiple 
	 * times.
	 */
	protected WindowAdapter myWindowAdaptor;
	
	protected void showSize()
	{
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		
		Component comp = getWindow();
		
		Point loc = comp.getLocation();
		Dimension dim = comp.getSize();
		
		out.print("getWindow().setLocation(");
		out.print(loc.x);
		out.print(", ");
		out.print(loc.y);
		out.println(");");
		
		out.print("getWindow().setSize(");
		out.print(dim.width);
		out.print(", ");
		out.print(dim.height);
		out.println(");");
		
		out.println();
		out.print(TEXT_GET_WINDOW_SIZE);
		out.println(dim.width + ", " + dim.height + ");");
		out.println("}");
		
		out.close();
		
		TextWindow.showText(sw.toString());
	}

	protected SimpleThreadedAction winSize = new SimpleThreadedAction() {
		public void action() { showSize(); }
	};
	
	protected String myWindowBasePropertyName;
	
	public ApplicationContentPanel (Window window) throws LTSException
	{
		super(window);
	}
	
	public ApplicationContentPanel (Container container) throws LTSException
	{
		super(container);
	}
	
	public void initializeContainer (Container container) throws LTSException
	{
		super.initializeContainer(container);
		
		ApplicationWindowRepositoryListener listener = 
			new ApplicationWindowRepositoryListener(this);
		
		Application app = Application.getInstance();
		app.addRepositoryListener(listener);
		resetContainerTitle();
	}
	
	
	public void open()
	{
		Application.getInstance().browseOpenRepository();
	}

	public void quit()
	{
		Application.getInstance().quit();
	}

	public void save()
	{
		Application.getInstance().saveData();
	}

	public void saveAs()
	{
		Application.getInstance().browseSaveData();
	}

	public void resetContainerTitle()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(getViewName());
		sb.append(" - ");
		
		String fileName = StringUtils.trim(getFileName());
		if (null != fileName)
		{
			sb.append(fileName);
			sb.append(" - ");
		}
		
		sb.append(Application.getInstance().getApplicationName());
		
		String windowTitle = sb.toString();
		this.setWindowTitle(windowTitle);
	}

	protected String getFileName()
	{
		ApplicationRepository repository = Application.getInstance().getRepository();
		File file;
		
		try
		{
			file = repository.getRepositoryFile();
		}
		catch (ApplicationException e)
		{
			file = null;
		}
		
		String fileName = null;
		if (null != file)
		{
			fileName = file.getName();
		}
		return fileName;
	}

	static public void showException(Throwable t)
	{
		Application.showException(t);
	}

	public String getMessage(String key)
	{
		return Application.getInstance().getMessage(key);
	}

	public void createNew()
	{
		Application.getInstance().createNew();
	}

	public void windowClosing(WindowEvent event)
	{
		LTSRootPane root = getLTSRootPane();

		if (!exitOnClose())
			root.getComponent().setVisible(false);
		else
		{
			Application app = Application.getInstance();
			if (null == app)
				System.exit(0);
			
			app.quit();
			
			//
			// if app.quit returns, then the user does not want to quit.  If that
			// is the case, then doing nothing should allow the window to stay open.
			//
		}
	}

	public void makeOkDefault ()
	{
		InputMap imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		KeyStroke ks = KeyStroke.getKeyStroke("typed ENTER");
		imap.put(ks, "okButton");
		

	}

	protected Object[][][] getActionSpec()
	{
		return null;
	}
	

	protected JMenuBar buildMenuBarActionSpec()
	{
		Object[][][] spec = getActionSpec();
		if (null == spec)
			return null;
		
		ActionMenuBuilder builder = new ActionMenuBuilder();
		JMenuBar menuBar = builder.buildMenuBar(spec);
		return menuBar;
	}
	
	public void initializeMenuBar() throws LTSException
	{
		JMenuBar menuBar = buildMenuBar();
		if (null == menuBar)
			return;
		
		LTSRootPane lrp = getLTSRootPane();
		lrp.setMenuBar(menuBar);
	}
	
	protected String[][][] get3DMenuSpec()
	{
		return null;
	}
	
	protected JMenuBar buildMenuBarStringSpec() throws LTSException
	{
		MenuBuilder builder = getMenuBuilder();
		String[][][] spec = get3DMenuSpec();
		JMenuBar menuBar = null;
		
		if (null != spec)
			menuBar = builder.buildMenuBar(this, spec);
		
		return menuBar;
	}
	
	/**
	 * A hook to allow subclasses to use a different class to build menus.
	 * 
	 * @return The menu builder that the instance should use.
	 */
	public MenuBuilder getMenuBuilder()
	{
		return null;
	}
	
	
	@SuppressWarnings("serial")
	protected void setupShowSize(JComponent container)
	{		
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				showSize();
			}
		};
		
		setupDefaultKey(container, CTRL_W, action);
	}
	
	@SuppressWarnings("serial")
	protected void setupEnterKey(JComponent container)
	{
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				returnKeyTyped();
			}
		};
		
		setupDefaultKey(container, RETURN_KEY, action);
	}
	
	protected void setupEnterKey()
	{
		setupEnterKey(this);
	}
	
	
	protected JComponent getContainerComponent(Container container)
	{
		JComponent comp = this;
		
		if (container instanceof JComponent)
		{
			comp = (JComponent) container;
		}
		
		return comp;
	}
	
	
	protected void setupEnterKey(Container container)
	{
		JComponent comp = getContainerComponent(container);
		setupEnterKey(comp);
	}
	
	
	protected void setupDefaultKey(JComponent jcomp, KeyStroke keyStroke, Action action)
	{
		while (
				null != jcomp.getParent()
				&& jcomp.getParent() instanceof JComponent
				&& jcomp != jcomp.getParent()
		)
		{
			jcomp = (JComponent) jcomp.getParent();
		}
		
		SwingUtils.mapKeyAsDefault(keyStroke, action, jcomp);
	}
	
	
	protected void setupShowSize(Container container)
	{
		JComponent comp = getContainerComponent(container);
		setupShowSize(comp);
	}
		
	

	protected void returnKeyTyped()
	{
		okButtonPressed();
	}


	
	public static KeyStroke CTRL_W = 
		KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK);
	
	public static KeyStroke RETURN_KEY = 
		KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);


	public JMenuBar buildMenuBar() throws LTSException
	{
		JMenuBar menuBar = buildMenuBarActionSpec();
		
		if (null == menuBar)
			menuBar = buildMenuBarStringSpec();
		
		return menuBar;
	}
	
	
	public static class LocalWindowComponentListener extends WindowComponentListener
	{
		public ApplicationContentPanel myAppPanel;
		
		public LocalWindowComponentListener(ApplicationContentPanel apc)
		{
			myAppPanel = apc;
		}
		
		public void windowClosingOrHidden(ComponentEvent event) 
		{
			myAppPanel.windowClosed(event);
		}
		
		public void windowOpenedOrDisplayed(ComponentEvent event) 
		{
			myAppPanel.windowOpened(event);
		}
		
		public void windowComponentEvent(ComponentEvent event) 
		{
			myAppPanel.windowComponentEvent(event);
		}
	}
	
	protected void listenForWindowComponentEvents()
	{
		WindowComponentListener listener;
		listener = new LocalWindowComponentListener(this);
		Window window = getLTSRootPane().getWindow();
		window.addWindowListener(listener);
		window.addComponentListener(listener);
	}

	protected String buildPropertyNameForWindow(String s)
	{
		return getWindowBasePropertyName() + "." + s;
	}

	protected String getWindowBasePropertyName()
	{
		return myWindowBasePropertyName;
	}

	public void setWindowBasePropertyName(String windowBasePropertyName)
	{
		myWindowBasePropertyName = windowBasePropertyName;
	}

	/**
	 * This signifies that the window has been opened.
	 * 
	 * <P>
	 * The {@link #listenForWindowComponentEvents()} must be called for this method to 
	 * work.
	 * </P>
	 * 
	 * <P>
	 * This method is called in response to one of many calls for the 
	 * WindowEventListener or ComponentEventListener interfaces.  Those events
	 * are:
	 * </P>
	 * 
	 * <UL>
	 * <LI>{@link WindowEvent#WINDOW_DEICONIFIED}</LI>
	 * <LI>{@link WindowEvent#WINDOW_OPENED}</LI>
	 * <LI>{@link ComponentEvent#COMPONENT_SHOWN}</LI>
	 * </UL>
	 * 
	 * @param event The triggering event.
	 */
	protected void windowOpened(ComponentEvent event)
	{}

	/**
	 * This signifies that the window has been closed.
	 * 
	 * <P>
	 * The {@link #listenForWindowComponentEvents()} must be called for this method to 
	 * work.
	 * </P>
	 * 
	 * <P>
	 * This method is called in response to one of many calls for the 
	 * WindowEventListener or ComponentEventListener interfaces.  Those events
	 * are:
	 * </P>
	 * 
	 * <UL>
	 * <LI>{@link WindowEvent#WINDOW_CLOSED}</LI>
	 * <LI>{@link WindowEvent#WINDOW_ICONIFIED}</LI>
	 * <LI>{@link ComponentEvent#COMPONENT_HIDDEN}</LI>
	 * </UL>
	 * 
	 * <P>
	 * The action take by this method is to call {@link #saveWindowSize()}.
	 * </P>
	 * 
	 * @param event The triggering event.
	 */
	protected void windowClosed(ComponentEvent event)
	{
		saveWindowSize();
	}

	/**
	 * A WindowEvent or ComponentEvent has occurred.
	 * 
	 * <P>
	 * The {@link #listenForWindowComponentEvents()} must be called for this method to 
	 * work.
	 * </P>
	 * 
	 * <P>
	 * This method is a hook to allow subclasses that want to take action in response 
	 * to Component or Window events.  {@link #windowClosed(ComponentEvent)} 
	 * and {@link #windowOpened(ComponentEvent)} are also called after this method
	 * is called, so care must be taken to respond to events appropriately (i.e.,
	 * don't process a close event twice).
	 * </P>
	 * 
	 * @param event The triggering event.
	 */
	protected void windowComponentEvent(ComponentEvent event)
	{}

	protected void saveWindowSize()
	{
		if (definesWindowProperties())
		{
			Dimension dim = getLTSRootPane().getWindow().getSize();
			String name = buildPropertyNameForWindow(PROPERTY_WINDOW_SIZE);
			String value = dim.height + "," + dim.width;			
			Application.setAppProperty(name, value);
		}
	}

	public boolean definesWindowProperties()
	{
		return null != myWindowBasePropertyName;
	}
}
