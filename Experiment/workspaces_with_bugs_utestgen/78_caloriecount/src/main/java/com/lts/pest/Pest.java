//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.checkpoint.CheckPointManager;
import com.lts.io.ImprovedFile;
import com.lts.scheduler.ScheduledEventListener;
import com.lts.scheduler.Scheduler;

/**
 * 
 * @author cnh
 */
abstract public class Pest extends Application
{
	protected static boolean debugDialogs = true;
	
	transient protected Scheduler myScheduler;
	transient protected ScheduledEventListener schedulerListener;
	transient protected boolean isQuestioning;
	transient protected ApplicationRepository myCheckpointRepository;

	private ImprovedFile myCheckPointDirectory;
	
	public void setCheckpointRepository(ApplicationRepository repos)
	{
		myCheckpointRepository = repos;
	}
	
	
	public static boolean getAlwaysOnTop ()
	{
		return !debugDialogs;
	}
	
	
	public boolean isQuestioning()
	{
		return this.isQuestioning;
	}
	
	public boolean getQuestioning()
	{
		return isQuestioning();
	}
	
	protected void addResourcePathElements(List list)
	{
		list.add("resources.messages.pest");
	}
	
	
	@Override
	public String getApplicationName()
	{
		return "Pest";
	}

	
	public Scheduler getScheduler()
	{
		return Scheduler.getInstance();
	}

	static public Application getApp()
	{
		return (Application) getInstance();
	}
	
	
	public synchronized void checkPoint() throws ApplicationException
	{
		if (null == myCheckpointRepository)
		{
			myCheckpointRepository = createCheckPointRepository();
		}
		
		myCheckpointRepository.storeApplicationData(getApplicationData());
		myCheckpointRepository.commit();
		myCheckpointRepository.close();
	}
	
	
	protected ApplicationRepository getCheckpointRepository() throws ApplicationException
	{
		if (null == myCheckpointRepository)
			myCheckpointRepository = createRepository();
		
		return myCheckpointRepository;
	}
	protected ApplicationRepository createCheckPointRepository() throws ApplicationException
	{
		try
		{
			if (!getCheckPointDirectory().exists() && !getCheckPointDirectory().mkdirs())
			{
				String msg = "Could not create check point directory: "
						+ getCheckPointDirectory();
	
				throw new ApplicationException(msg);
			}
			
			File reposFile = File.createTempFile("checkpoint", ".zip", getCheckPointDirectory());
			ApplicationRepository repos = createRepository(reposFile, getCheckPointDirectory(), false);
			deleteOnShutdown(reposFile);
			return repos;
		}
		catch (IOException e)
		{
			String msg = "Error creating repository for checkpoint";
			throw new ApplicationException(msg);
		}
	}
	public ImprovedFile getCheckPointDirectory()
	{
		if (null == myCheckPointDirectory)
		{
			myCheckPointDirectory = new ImprovedFile(getMasterTempDir(), "checkpoints");
		}
		
		return myCheckPointDirectory;
	}
	
	
	protected void processCheckPoints()
	{
		try
		{
			File dir = getCheckPointDirectory();
			String[] files = null;
			if (dir.isDirectory())
				files = dir.list();
			
			if (null != files && files.length > 0)
			{
				CheckPointManager manager = CheckPointManager.getInstance();
				manager.setCheckPointDirectory(getCheckPointDirectory());
				manager.cleanupCheckPointDirectory();
				manager.reloadData();
				
				manager.displayAndWait();
				if (manager.quitApplication())
					quit();
				
				if (null != manager.getRepository())
					setRepository(manager.getRepository());
			}
		}
		catch (ApplicationException e)
		{
			String msg = "Error processing checkpoints";
			Application.showException(msg, e);
		}
	}
	protected boolean checkPointPresent()
	{
		ImprovedFile dir = getCheckPointDirectory();
		if (!dir.exists())
			return false;
		
		String[] contents = dir.list();
		return (null != contents && contents.length > 0);
	}
}
