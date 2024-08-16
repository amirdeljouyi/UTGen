package com.lts.caloriecount.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lts.application.Application;

abstract public class ApplicationAction implements ActionListener
{
	abstract public void action() throws Exception;
	
	@Override
	public void actionPerformed(ActionEvent event)
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
