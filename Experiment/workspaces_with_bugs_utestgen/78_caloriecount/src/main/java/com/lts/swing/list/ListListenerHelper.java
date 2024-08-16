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
package com.lts.swing.list;

import java.awt.event.MouseEvent;

import com.lts.LTSRuntimeException;
import com.lts.event.ListenerHelper;

public class ListListenerHelper extends ListenerHelper
{
	public static final int EVENT_CREATE = 0;
	public static final int EVENT_DELETE = 1;
	public static final int EVENT_EDIT = 2;
	public static final int EVENT_SHOW_POPUP = 3;
	
	public void notifyListener(Object genericListener, int type, Object data)
	{
		
		ListListener listener = (ListListener) genericListener;
		int index = -1;
		
		if (null != data)
			index = (Integer) data;
		
		switch (type)
		{
			case EVENT_CREATE :
				listener.create(index);
				break;
				
			case EVENT_DELETE :
				listener.delete(index);
				break;
				
			case EVENT_EDIT :
				listener.edit(index);
				break;
				
			case EVENT_SHOW_POPUP :
			{
				MouseEvent event = (MouseEvent) data;
				listener.showPopup(event.getComponent(), event.getX(), event.getY());
				break;
			}
				
			default :
				String msg = 
					"Unrecognized event code, " + type;
				throw new LTSRuntimeException(msg);
		}
	}

	public void fireCreate(int index)
	{
		fire(EVENT_CREATE, index);
	}
	
	public void fireDelete(int index)
	{
		fire(EVENT_DELETE, index);
	}
	
	public void fireEdit(int index)
	{
		fire(EVENT_EDIT, index);
	}
	
	public void fireUpdate(int index)
	{
		fireEdit(index);
	}
	
	public void fireShowPopup()
	{
		fire(EVENT_SHOW_POPUP);
	}
}
