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
 * Created on Jan 4, 2004
 * 
 * To change the template for this generated file go to Window - Preferences - Java -
 * Code Generation - Code and Comments
 */
package com.lts.swing;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.AWTKeyStroke;
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
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
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
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;

/**
 * @author cnh
 * 
 * To change the template for this generated type comment go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
public interface SwingWindow
{
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean action(Event arg0, Object arg1);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Component add(Component arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract Component add(Component arg0, int arg1);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void add(Component arg0, Object arg1);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public abstract void add(Component arg0, Object arg1, int arg2);
	/**
	 * @param arg0
	 */
	public abstract void add(PopupMenu arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract Component add(String arg0, Component arg1);
	/**
	 * @param arg0
	 */
	public abstract void addComponentListener(ComponentListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addContainerListener(ContainerListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addFocusListener(FocusListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addHierarchyBoundsListener(HierarchyBoundsListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addHierarchyListener(HierarchyListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addInputMethodListener(InputMethodListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addKeyListener(KeyListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addMouseListener(MouseListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addMouseMotionListener(MouseMotionListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addMouseWheelListener(MouseWheelListener arg0);
	/**
	 *  
	 */
	public abstract void addNotify();
	/**
	 * @param arg0
	 */
	public abstract void addPropertyChangeListener(PropertyChangeListener arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void addPropertyChangeListener(
		String arg0,
		PropertyChangeListener arg1);
	/**
	 * @param arg0
	 */
	public abstract void addWindowFocusListener(WindowFocusListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addWindowListener(WindowListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void addWindowStateListener(WindowStateListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void applyComponentOrientation(ComponentOrientation arg0);
	/**
	 * @param arg0
	 */
	public abstract void applyResourceBundle(String arg0);
	/**
	 * @param arg0
	 */
	public abstract void applyResourceBundle(ResourceBundle arg0);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract boolean areFocusTraversalKeysSet(int arg0);
	/**
	 * @return
	 */
	public abstract Rectangle bounds();
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 */
	public abstract int checkImage(Image arg0, int arg1, int arg2, ImageObserver arg3);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract int checkImage(Image arg0, ImageObserver arg1);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean contains(int arg0, int arg1);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract boolean contains(Point arg0);
	/**
	 * @return
	 */
	public abstract int countComponents();
	/**
	 * @param arg0
	 */
	public abstract void createBufferStrategy(int arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @throws java.awt.AWTException
	 */
	public abstract void createBufferStrategy(int arg0, BufferCapabilities arg1)
		throws AWTException;
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract Image createImage(int arg0, int arg1);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Image createImage(ImageProducer arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract VolatileImage createVolatileImage(int arg0, int arg1);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return @throws
	 *         java.awt.AWTException
	 */
	public abstract VolatileImage createVolatileImage(
		int arg0,
		int arg1,
		ImageCapabilities arg2)
		throws AWTException;
	/**
	 * @param arg0
	 */
	public abstract void deliverEvent(Event arg0);
	/**
	 *  
	 */
	public abstract void disable();
	/**
	 * @param arg0
	 */
	public abstract void dispatchEvent(AWTEvent arg0);
	/**
	 *  
	 */
	public abstract void dispose();
	/**
	 *  
	 */
	public abstract void doLayout();
	/**
	 *  
	 */
	public abstract void enable();
	/**
	 * @param arg0
	 */
	public abstract void enable(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void enableInputMethods(boolean arg0);
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract Component findComponentAt(int arg0, int arg1);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Component findComponentAt(Point arg0);
	/**
	 * @return
	 */
	public abstract AccessibleContext getAccessibleContext();
	/**
	 * @return
	 */
	public abstract float getAlignmentX();
	/**
	 * @return
	 */
	public abstract float getAlignmentY();
	/**
	 * @return
	 */
	public abstract Color getBackground();
	/**
	 * @return
	 */
	public abstract Rectangle getBounds();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Rectangle getBounds(Rectangle arg0);
	/**
	 * @return
	 */
	public abstract BufferStrategy getBufferStrategy();
	/**
	 * @return
	 */
	public abstract ColorModel getColorModel();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Component getComponent(int arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract Component getComponentAt(int arg0, int arg1);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Component getComponentAt(Point arg0);
	/**
	 * @return
	 */
	public abstract int getComponentCount();
	/**
	 * @return
	 */
	public abstract ComponentListener[] getComponentListeners();
	/**
	 * @return
	 */
	public abstract ComponentOrientation getComponentOrientation();
	/**
	 * @return
	 */
	public abstract Component[] getComponents();
	/**
	 * @return
	 */
	public abstract ContainerListener[] getContainerListeners();
	/**
	 * @return
	 */
	public abstract Container getContentPane();
	/**
	 * @return
	 */
	public abstract Cursor getCursor();
	/**
	 * @return
	 */
	public abstract int getDefaultCloseOperation();
	/**
	 * @return
	 */
	public abstract DropTarget getDropTarget();
	/**
	 * @return
	 */
	public abstract boolean getFocusableWindowState();
	/**
	 * @return
	 */
	public abstract Container getFocusCycleRootAncestor();
	/**
	 * @return
	 */
	public abstract FocusListener[] getFocusListeners();
	/**
	 * @return
	 */
	public abstract Component getFocusOwner();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Set getFocusTraversalKeys(int arg0);
	/**
	 * @return
	 */
	public abstract boolean getFocusTraversalKeysEnabled();
	/**
	 * @return
	 */
	public abstract FocusTraversalPolicy getFocusTraversalPolicy();
	/**
	 * @return
	 */
	public abstract Font getFont();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract FontMetrics getFontMetrics(Font arg0);
	/**
	 * @return
	 */
	public abstract Color getForeground();
	/**
	 * @return
	 */
	public abstract Component getGlassPane();
	/**
	 * @return
	 */
	public abstract Graphics getGraphics();
	/**
	 * @return
	 */
	public abstract GraphicsConfiguration getGraphicsConfiguration();
	/**
	 * @return
	 */
	public abstract int getHeight();
	/**
	 * @return
	 */
	public abstract HierarchyBoundsListener[] getHierarchyBoundsListeners();
	/**
	 * @return
	 */
	public abstract HierarchyListener[] getHierarchyListeners();
	/**
	 * @return
	 */
	public abstract boolean getIgnoreRepaint();
	/**
	 * @return
	 */
	public abstract InputContext getInputContext();
	/**
	 * @return
	 */
	public abstract InputMethodListener[] getInputMethodListeners();
	/**
	 * @return
	 */
	public abstract InputMethodRequests getInputMethodRequests();
	/**
	 * @return
	 */
	public abstract Insets getInsets();
	/**
	 * @return
	 */
	public abstract JMenuBar getJMenuBar();
	/**
	 * @return
	 */
	public abstract KeyListener[] getKeyListeners();
	/**
	 * @return
	 */
	public abstract JLayeredPane getLayeredPane();
	/**
	 * @return
	 */
	public abstract LayoutManager getLayout();
	/**
	 * @param listenerType
	 * @return
	 */
	// public abstract EventListener[] getListeners(Class T);
    public <T extends EventListener> T[] getListeners(Class<T> listenerType);

	/**
	 * @return
	 */
	public abstract Locale getLocale();
	/**
	 * @return
	 */
	public abstract Point getLocation();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Point getLocation(Point arg0);
	/**
	 * @return
	 */
	public abstract Point getLocationOnScreen();
	/**
	 * @return
	 */
	public abstract Dimension getMaximumSize();
	/**
	 * @return
	 */
	public abstract Dimension getMinimumSize();
	/**
	 * @return
	 */
	public abstract Component getMostRecentFocusOwner();
	/**
	 * @return
	 */
	public abstract MouseListener[] getMouseListeners();
	/**
	 * @return
	 */
	public abstract MouseMotionListener[] getMouseMotionListeners();
	/**
	 * @return
	 */
	public abstract MouseWheelListener[] getMouseWheelListeners();
	/**
	 * @return
	 */
	public abstract String getName();
	/**
	 * @return
	 */
	public abstract Window[] getOwnedWindows();
	/**
	 * @return
	 */
	public abstract Window getOwner();
	/**
	 * @return
	 */
	public abstract Container getParent();
	/**
	 * @return
	 */
	public abstract ComponentPeer getPeer();
	/**
	 * @return
	 */
	public abstract Dimension getPreferredSize();
	/**
	 * @return
	 */
	public abstract PropertyChangeListener[] getPropertyChangeListeners();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract PropertyChangeListener[] getPropertyChangeListeners(String arg0);
	/**
	 * @return
	 */
	public abstract JRootPane getRootPane();
	/**
	 * @return
	 */
	public abstract Dimension getSize();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract Dimension getSize(Dimension arg0);
	/**
	 * @return
	 */
	public abstract String getTitle();
	/**
	 * @return
	 */
	public abstract Toolkit getToolkit();
	/**
	 * @return
	 */
	public abstract Object getTreeLock();
	/**
	 * @return
	 */
	public abstract String getWarningString();
	/**
	 * @return
	 */
	public abstract int getWidth();
	/**
	 * @return
	 */
	public abstract WindowFocusListener[] getWindowFocusListeners();
	/**
	 * @return
	 */
	public abstract WindowListener[] getWindowListeners();
	/**
	 * @return
	 */
	public abstract WindowStateListener[] getWindowStateListeners();
	/**
	 * @return
	 */
	public abstract int getX();
	/**
	 * @return
	 */
	public abstract int getY();
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean gotFocus(Event arg0, Object arg1);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract boolean handleEvent(Event arg0);
	/**
	 * @return
	 */
	public abstract boolean hasFocus();
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();
	/**
	 *  
	 */
	public abstract void hide();
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 * @return
	 */
	public abstract boolean imageUpdate(
		Image arg0,
		int arg1,
		int arg2,
		int arg3,
		int arg4,
		int arg5);
	/**
	 * @return
	 */
	public abstract Insets insets();
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean inside(int arg0, int arg1);
	/**
	 *  
	 */
	public abstract void invalidate();
	/**
	 * @return
	 */
	public abstract boolean isActive();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract boolean isAncestorOf(Component arg0);
	/**
	 * @return
	 */
	public abstract boolean isBackgroundSet();
	/**
	 * @return
	 */
	public abstract boolean isCursorSet();
	/**
	 * @return
	 */
	public abstract boolean isDisplayable();
	/**
	 * @return
	 */
	public abstract boolean isDoubleBuffered();
	/**
	 * @return
	 */
	public abstract boolean isEnabled();
	/**
	 * @return
	 */
	public abstract boolean isFocusable();
	/**
	 * @return
	 */
	public abstract boolean isFocusableWindow();
	/**
	 * @return
	 */
	public abstract boolean isFocusCycleRoot();
	/**
	 * @param arg0
	 * @return
	 */
	public abstract boolean isFocusCycleRoot(Container arg0);
	/**
	 * @return
	 */
	public abstract boolean isFocused();
	/**
	 * @return
	 */
	public abstract boolean isFocusOwner();
	/**
	 * @return
	 */
	public abstract boolean isFocusTraversable();
	/**
	 * @return
	 */
	public abstract boolean isFocusTraversalPolicySet();
	/**
	 * @return
	 */
	public abstract boolean isFontSet();
	/**
	 * @return
	 */
	public abstract boolean isForegroundSet();
	/**
	 * @return
	 */
	public abstract boolean isLightweight();
	/**
	 * @return
	 */
	public abstract boolean isOpaque();
	/**
	 * @return
	 */
	public abstract boolean isResizable();
	/**
	 * @return
	 */
	public abstract boolean isShowing();
	/**
	 * @return
	 */
	public abstract boolean isUndecorated();
	/**
	 * @return
	 */
	public abstract boolean isValid();
	/**
	 * @return
	 */
	public abstract boolean isVisible();
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean keyDown(Event arg0, int arg1);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean keyUp(Event arg0, int arg1);
	/**
	 *  
	 */
	public abstract void layout();
	/**
	 *  
	 */
	public abstract void list();
	/**
	 * @param arg0
	 */
	public abstract void list(PrintStream arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void list(PrintStream arg0, int arg1);
	/**
	 * @param arg0
	 */
	public abstract void list(PrintWriter arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void list(PrintWriter arg0, int arg1);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract Component locate(int arg0, int arg1);
	/**
	 * @return
	 */
	public abstract Point location();
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean lostFocus(Event arg0, Object arg1);
	/**
	 * @return
	 */
	public abstract Dimension minimumSize();
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public abstract boolean mouseDown(Event arg0, int arg1, int arg2);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public abstract boolean mouseDrag(Event arg0, int arg1, int arg2);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public abstract boolean mouseEnter(Event arg0, int arg1, int arg2);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public abstract boolean mouseExit(Event arg0, int arg1, int arg2);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public abstract boolean mouseMove(Event arg0, int arg1, int arg2);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public abstract boolean mouseUp(Event arg0, int arg1, int arg2);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void move(int arg0, int arg1);
	/**
	 *  
	 */
	public abstract void nextFocus();
	/**
	 *  
	 */
	public abstract void pack();
	/**
	 * @param arg0
	 */
	public abstract void paint(Graphics arg0);
	/**
	 * @param arg0
	 */
	public abstract void paintAll(Graphics arg0);
	/**
	 * @param arg0
	 */
	public abstract void paintComponents(Graphics arg0);
	/**
	 * @param arg0
	 * @return
	 */
	public abstract boolean postEvent(Event arg0);
	/**
	 * @return
	 */
	public abstract Dimension preferredSize();
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 */
	public abstract boolean prepareImage(
		Image arg0,
		int arg1,
		int arg2,
		ImageObserver arg3);
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public abstract boolean prepareImage(Image arg0, ImageObserver arg1);
	/**
	 * @param arg0
	 */
	public abstract void print(Graphics arg0);
	/**
	 * @param arg0
	 */
	public abstract void printAll(Graphics arg0);
	/**
	 * @param arg0
	 */
	public abstract void printComponents(Graphics arg0);
	/**
	 * @param arg0
	 */
	public abstract void remove(int arg0);
	/**
	 * @param arg0
	 */
	public abstract void remove(Component arg0);
	/**
	 * @param arg0
	 */
	public abstract void remove(MenuComponent arg0);
	/**
	 *  
	 */
	public abstract void removeAll();
	/**
	 * @param arg0
	 */
	public abstract void removeComponentListener(ComponentListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeContainerListener(ContainerListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeFocusListener(FocusListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeHierarchyBoundsListener(HierarchyBoundsListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeHierarchyListener(HierarchyListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeInputMethodListener(InputMethodListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeKeyListener(KeyListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeMouseListener(MouseListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeMouseMotionListener(MouseMotionListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeMouseWheelListener(MouseWheelListener arg0);
	/**
	 *  
	 */
	public abstract void removeNotify();
	/**
	 * @param arg0
	 */
	public abstract void removePropertyChangeListener(PropertyChangeListener arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void removePropertyChangeListener(
		String arg0,
		PropertyChangeListener arg1);
	/**
	 * @param arg0
	 */
	public abstract void removeWindowFocusListener(WindowFocusListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeWindowListener(WindowListener arg0);
	/**
	 * @param arg0
	 */
	public abstract void removeWindowStateListener(WindowStateListener arg0);
	/**
	 *  
	 */
	public abstract void repaint();
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public abstract void repaint(int arg0, int arg1, int arg2, int arg3);
	/**
	 * @param arg0
	 */
	public abstract void repaint(long arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 */
	public abstract void repaint(long arg0, int arg1, int arg2, int arg3, int arg4);
	/**
	 *  
	 */
	public abstract void requestFocus();
	/**
	 * @return
	 */
	public abstract boolean requestFocusInWindow();
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public abstract void reshape(int arg0, int arg1, int arg2, int arg3);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void resize(int arg0, int arg1);
	/**
	 * @param arg0
	 */
	public abstract void resize(Dimension arg0);
	/**
	 * @param arg0
	 */
	public abstract void setBackground(Color arg0);
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public abstract void setBounds(int arg0, int arg1, int arg2, int arg3);
	/**
	 * @param arg0
	 */
	public abstract void setBounds(Rectangle arg0);
	/**
	 * @param arg0
	 */
	public abstract void setComponentOrientation(ComponentOrientation arg0);
	/**
	 * @param arg0
	 */
	public abstract void setContentPane(Container arg0);
	/**
	 * @param arg0
	 */
	public abstract void setCursor(Cursor arg0);
	/**
	 * @param arg0
	 */
	public abstract void setDefaultCloseOperation(int arg0);
	/**
	 * @param arg0
	 */
	public abstract void setDropTarget(DropTarget arg0);
	/**
	 * @param arg0
	 */
	public abstract void setEnabled(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void setFocusable(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void setFocusableWindowState(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void setFocusCycleRoot(boolean arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void setFocusTraversalKeys(int arg0, Set<? extends AWTKeyStroke> arg1);
	/**
	 * @param arg0
	 */
	public abstract void setFocusTraversalKeysEnabled(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void setFocusTraversalPolicy(FocusTraversalPolicy arg0);
	/**
	 * @param arg0
	 */
	public abstract void setFont(Font arg0);
	/**
	 * @param arg0
	 */
	public abstract void setForeground(Color arg0);
	/**
	 * @param arg0
	 */
	public abstract void setGlassPane(Component arg0);
	/**
	 * @param arg0
	 */
	public abstract void setIgnoreRepaint(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void setJMenuBar(JMenuBar arg0);
	/**
	 * @param arg0
	 */
	public abstract void setLayeredPane(JLayeredPane arg0);
	/**
	 * @param arg0
	 */
	public abstract void setLayout(LayoutManager arg0);
	/**
	 * @param arg0
	 */
	public abstract void setLocale(Locale arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void setLocation(int arg0, int arg1);
	/**
	 * @param arg0
	 */
	public abstract void setLocation(Point arg0);
	/**
	 * @param arg0
	 */
	public abstract void setLocationRelativeTo(Component arg0);
	/**
	 * @param arg0
	 */
	public abstract void setName(String arg0);
	/**
	 * @param arg0
	 */
	public abstract void setResizable(boolean arg0);
	/**
	 * @param arg0
	 * @param arg1
	 */
	public abstract void setSize(int arg0, int arg1);
	/**
	 * @param arg0
	 */
	public abstract void setSize(Dimension arg0);
	/**
	 * @param arg0
	 */
	public abstract void setTitle(String arg0);
	/**
	 * @param arg0
	 */
	public abstract void setUndecorated(boolean arg0);
	/**
	 * @param arg0
	 */
	public abstract void setVisible(boolean arg0);
	/**
	 *  
	 */
	public abstract void show();
	/**
	 * @param arg0
	 */
	public abstract void show(boolean arg0);
	/**
	 * @return
	 */
	public abstract Dimension size();
	/**
	 *  
	 */
	public abstract void toBack();
	/**
	 *  
	 */
	public abstract void toFront();
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();
	/**
	 *  
	 */
	public abstract void transferFocus();
	/**
	 *  
	 */
	public abstract void transferFocusBackward();
	/**
	 *  
	 */
	public abstract void transferFocusDownCycle();
	/**
	 *  
	 */
	public abstract void transferFocusUpCycle();
	/**
	 * @param arg0
	 */
	public abstract void update(Graphics arg0);
	/**
	 *  
	 */
	public abstract void validate();
}