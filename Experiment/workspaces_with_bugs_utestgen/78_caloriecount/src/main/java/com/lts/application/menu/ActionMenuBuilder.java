package com.lts.application.menu;


import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ActionMenuBuilder
{

	protected void buildMenu(JMenu menu, Object[][] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			if (isSeparator(spec[i]))
				menu.addSeparator();
			else if (isTitle(spec[i]))
			{
				String name = (String) spec[i][0];
				menu.setName(name);
				menu.setText(name);
			}
			else
			{
				JMenuItem item = buildMenuItem(spec[i]);
				menu.add(item);
			}
		}
	}
	
	
	protected boolean isTitle (Object[] spec)
	{
		return spec.length == 1 && spec[0] instanceof String;
	}
	
	
	protected boolean isSeparator(Object[] objects)
	{
		return objects.length < 1;
	}


	protected JMenuItem buildMenuItem(Object[] spec)
	{
		String title = (String) spec[0];
		ActionListener action = (ActionListener) spec[1];
		JMenuItem item = new JMenuItem();
		item.addActionListener(action);
		item.setName(title);
		item.setText(title);
		
		return item;
	}


	public JMenuBar buildMenuBar(Object[][][] spec)
	{
		JMenuBar menuBar = new JMenuBar();
		
		for (int i = 0; i < spec.length; i++)
		{
			Object[][] subSpec = spec[i];
			JMenu menu = new JMenu();
			buildMenu(menu, subSpec);
			menuBar.add(menu);
		}
		
		return menuBar;
	}


	protected void buildMenu (Object menu, Object[][] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			if (isSeparator(spec[i]))
				addSeparator(menu);
			else if (isTitle(spec[i]))
			{
				String name = (String) spec[i][0];
				setMenuTitle(menu, name);
			}
			else
			{
				JMenuItem item = buildMenuItem(spec[i]);
				addMenuItem(menu, item);
			}
		}
	}
	
	
	protected void addMenuItem (Object menuObject, JMenuItem item)
	{
		if (menuObject instanceof JMenu)
		{
			JMenu menu = (JMenu) menuObject;
			menu.add(item);
		}
		else if (menuObject instanceof JPopupMenu)
		{
			JPopupMenu menu = (JPopupMenu) menuObject;
			menu.add(item);
		}
		else
		{
			String msg = "Unrecognized menu class: " + menuObject.getClass().getName();
			throw new RuntimeException(msg);
		}
	}
	
	protected void addSeparator (Object menuObject)
	{
		if (menuObject instanceof JMenu)
		{
			JMenu menu = (JMenu) menuObject;
			menu.addSeparator();
		}
		else if (menuObject instanceof JPopupMenu)
		{
			JPopupMenu menu = (JPopupMenu) menuObject;
			menu.addSeparator();
		}
		else
		{
			String msg = "Unrecognized menu class: " + menuObject.getClass().getName();
			throw new RuntimeException(msg);
		}
	}
	
	protected void setMenuTitle (Object menuObject, String title)
	{
		if (menuObject instanceof JMenu)
		{
			JMenu menu = (JMenu) menuObject;
			menu.setText(title);
			menu.setName(title);
		}
		else if (menuObject instanceof JPopupMenu)
		{
			; // silently ignore
		}
		else
		{
			String msg = "Unrecognized menu class: " + menuObject.getClass().getName();
			throw new RuntimeException(msg);
		}
	}
	
	
	public JPopupMenu buildPopup(Object[][] spec)
	{
		JPopupMenu popup = new JPopupMenu();
		buildMenu(popup, spec);
		return popup;
	}

}
