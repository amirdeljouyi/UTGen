//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
/*
 * Copyright (c) Long Term Software, LLC. All rights reserved.
 */
package com.lts.application.swing;

import java.io.File;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.swing.StandardFrame;

/**
 * @author cnh
 */
public abstract class ApplicationWindow extends StandardFrame 
{
	protected String myWindowTitle;
	
	public String getWindowTitle()
	{
		return myWindowTitle;
	}
	
	public void setWindowTitle (String title)
	{
		myWindowTitle = title;
	}
	
	protected Application myApplication;
	
	public Application getApplication()
	{
		return myApplication;
	}
	
	public void setApplication (Application a)
	{
		myApplication = a;
	}
	
	
	public void windowCloseRequest()
	{
		switch (getMode())
		{
			case MODE_MAINFRAME :
				Application.getInstance().quit();
				break;
				
			case MODE_NORMAL_WINDOW :
				super.windowCloseRequest();
				break;
		}
	}
	
	
	public void quit()
	{
		Application.getInstance().quit();
	}
	public int getMode()
	{
		return StandardFrame.MODE_MAINFRAME;
	}
	
	
	public void initialize () throws LTSException
	{
		setApplication(Application.getInstance());
		initialize(null, null);
	}
	
	
	public void initialize (String header, String title) throws LTSException
	{
		setApplication(Application.getInstance());
		
		if (null == title)
			title = getWindowTitle();
			
		super.initialize (getMode(), header, title);
	}
	
	public int getBottomPanelForm()
	{
		return StandardFrame.OPTION_NOTHING;
	}
	
	
	public File browseSave ()
	{
		return getApplication().browseSaveFile(this);
	}
	
	public File browseOpenFile ()
	{
		return getApplication().browseOpenFile(this);
	}
	
	
	public void quitRequest ()
	{
		getApplication().quit();
	}
}
