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
package com.lts.swing.tree.mvc;

import com.lts.event.ListenerHelper;

public class ApplicationTreeListenerHelper extends ListenerHelper
{

	public static final int EVENT_ADDED = 0;
	public static final int EVENT_REMOVED = 1;
	public static final int EVENT_CHANGED = 2;
	
	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		ApplicationNode node = (ApplicationNode) data;
		ApplicationTreeListener atl = (ApplicationTreeListener) listener;
		
		switch (type)
		{
			case EVENT_ADDED :
				atl.nodeAdded(node);
				break;
				
			case EVENT_REMOVED :
			{
				Object[] array = (Object[]) data;
				ApplicationNode child = (ApplicationNode) array[0];
				ApplicationNode parent = (ApplicationNode) array[1];
				
				atl.nodeRemoved(child, parent);
				break;
			}
				
			case EVENT_CHANGED :
				atl.nodeChanged(node);
				break;
				
			default :
				throw new IllegalArgumentException();
		}
	}

}
