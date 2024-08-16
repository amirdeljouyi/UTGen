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

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import com.lts.event.ListenerHelper;

public class TreeModelListenerHelper 
	extends ListenerHelper 
	implements TreeModelListener
{
	public static final int EVENT_INSERT = 0;
	public static final int EVENT_REMOVE = 1;
	public static final int EVENT_CHANGE = 2;
	public static final int EVENT_STRUCTURE_CHANGED = 3;
	
	protected Object actualSource;
	
	public TreeModelListenerHelper (Object theActualSource)
	{
		initialize(theActualSource);
	}
	
	public void initialize (Object theActualSource)
	{
		this.actualSource = theActualSource;
	}
	
	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		TreeModelListener tml = (TreeModelListener) listener;
		TreeModelEvent event = (TreeModelEvent) data;
		switch (type)
		{
			case EVENT_INSERT :
				tml.treeNodesInserted(event);
				break;
				
			case EVENT_REMOVE :
				tml.treeNodesRemoved(event);
				break;
				
			case EVENT_CHANGE :
				tml.treeNodesChanged(event);
				break;
			
			case EVENT_STRUCTURE_CHANGED :
				tml.treeStructureChanged(event);
				break;
				
			default :
			{
				String msg = "Unrecognized event code: " + type;
				throw new IllegalArgumentException(msg);
			}
		}
	}
	
	
	public void fireEvent (int eventType, TreeModelEvent event)
	{
		TreeModelEvent newEvent;
		newEvent = new TreeModelEvent(this.actualSource, event.getPath(), 
				event.getChildIndices(), event.getChildren());
		this.fire(eventType, newEvent);
	}


	public void treeNodesChanged(TreeModelEvent event)
	{
		fireEvent(EVENT_CHANGE, event);
	}


	public void treeNodesInserted(TreeModelEvent event)
	{
		fireEvent(EVENT_INSERT, event);
	}


	public void treeNodesRemoved(TreeModelEvent event)
	{
		fireEvent(EVENT_REMOVE, event);
	}


	public void treeStructureChanged(TreeModelEvent event)
	{
		fireEvent(EVENT_STRUCTURE_CHANGED, event);
	}

	public void addTreeModelListener(TreeModelListener l)
	{
		addListener(l);
	}

	public void removeTreeModelListener(TreeModelListener l)
	{
		removeListener(l);
	}
}
