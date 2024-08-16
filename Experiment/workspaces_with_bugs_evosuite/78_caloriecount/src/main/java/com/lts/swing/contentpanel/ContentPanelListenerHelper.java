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
package com.lts.swing.contentpanel;

import com.lts.event.ListenerHelper;

public class ContentPanelListenerHelper extends ListenerHelper
{
	public static final int EVENT_OK_BUTTON_PRESSED = 0;
	public static final int EVENT_CANCEL_BUTTON_PRESSED = 1;
	public static final int EVENT_CLOSE_BUTTON_PRESSED = 2;
	public static final int EVENT_NO_BUTTON_PRESSED = 3;
	public static final int EVENT_YES_BUTTON_PRESSED = 4;
	
	public void notifyListener(Object listener, int type, Object data)
	{
		ContentPanelCallback callback = (ContentPanelCallback) listener;
		ContentPanel panel = (ContentPanel) data;
		
		switch (type)
		{
			case EVENT_OK_BUTTON_PRESSED :
				callback.okButtonPressed(panel);
				break;
				
			case EVENT_CANCEL_BUTTON_PRESSED :
				callback.cancelButtonPressed(panel);
				break;
				
			case EVENT_CLOSE_BUTTON_PRESSED :
				callback.closeButtonPressed(panel);
				break;
				
			case EVENT_NO_BUTTON_PRESSED :
				callback.noButtonPressed(panel);
				break;
				
			case EVENT_YES_BUTTON_PRESSED :
				callback.yesButtonPressed(panel);
				break;
		}
	}

}
