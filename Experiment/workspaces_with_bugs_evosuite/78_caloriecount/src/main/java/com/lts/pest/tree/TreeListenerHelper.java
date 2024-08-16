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
package com.lts.pest.tree;

import com.lts.event.ListenerHelper;
import com.lts.util.TreeNode;

public class TreeListenerHelper extends ListenerHelper
{
	protected static final int EVENT_ADD_NODE = 0;
	protected static final int EVENT_REMOVE_NODE = 1;
	protected static final int EVENT_CHANGE_NODE = 2;
	protected static final int EVENT_ALL_CHANGED = 3;
	
	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		Object[] dataArray = (Object[]) data;
		TreeNode parent = null;
		if (dataArray.length > 0)
			parent = (TreeNode) dataArray[0];
		
		TreeNode child = null;
		if (dataArray.length > 1)
			child = (TreeNode) dataArray[1];
		
		TreeListener tl = (TreeListener) listener;
		
		switch (type)
		{
			case EVENT_ADD_NODE :
				tl.nodeAdded(parent, child);
				break;
				
			case EVENT_REMOVE_NODE :
				tl.nodeRemoved(parent, child);
				break;
				
			case EVENT_CHANGE_NODE :
				tl.nodeChanged(parent);
				break;
				
			case EVENT_ALL_CHANGED :
				tl.allChanged();
				break;
		}
	}
	
	public void fireNodeAdded (TreeNode parent, TreeNode child)
	{
		fire(EVENT_ADD_NODE, parent, child);
	}
	
	public void fireNodeRemoved (TreeNode parent, TreeNode child)
	{
		fire(EVENT_REMOVE_NODE, parent, child);
	}
	
	public void fireNodeChanged (TreeNode node)
	{
		Object[] data = { node };
		fire(EVENT_CHANGE_NODE, data);
	}

	public void fireAllChanged()
	{
		fire(EVENT_ALL_CHANGED, null);
	}

	public void resumeAllChanged()
	{
		fireAllChanged();
	}

}
