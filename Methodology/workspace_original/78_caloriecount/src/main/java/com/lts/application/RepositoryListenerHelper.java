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
package com.lts.application;

import com.lts.event.ListenerHelper;

public class RepositoryListenerHelper extends ListenerHelper
{
	public static final int EVENT_LOADED = 0;
	public static final int EVENT_SAVED = 1;
	public static final int EVENT_DISCARDED = 2;
	
	public void notifyListener(Object listener, int type, Object data)
	{
		RepositoryListener rl = (RepositoryListener) listener;
		ApplicationRepository repository = (ApplicationRepository) data;
		switch (type)
		{
			case EVENT_DISCARDED :
				rl.repositoryDiscarded(repository);
				break;
				
			case EVENT_LOADED :
				rl.repositoryLoaded(repository);
				break;
				
			case EVENT_SAVED :
				rl.repositorySaved(repository);
				break;
		}
	}
	
	
	public void saved (ApplicationRepository repos)
	{
		int event = EVENT_SAVED;
		fire(event, repos);
	}
	
	public void loaded (ApplicationRepository repos)
	{
		int event = EVENT_LOADED;
		fire(event, repos);
	}
}
