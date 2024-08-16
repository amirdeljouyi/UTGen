package com.lts.application.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lts.application.Application;

abstract public class SimpleApplicationAction implements ActionListener
{
	abstract public void action() throws Exception;
	
	public void actionPerformed (ActionEvent event)
	{
		try
		{
			action();
		}
		catch (Exception e)
		{
			Application.showException(e);
		}
	}
}
