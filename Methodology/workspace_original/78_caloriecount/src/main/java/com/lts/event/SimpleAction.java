package com.lts.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lts.application.Application;

abstract public class SimpleAction implements ActionListener
{
	abstract public void action() throws Exception;
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			action();
		}
		catch (Exception ex)
		{
			Application.showException(ex);
		}
	}

}
