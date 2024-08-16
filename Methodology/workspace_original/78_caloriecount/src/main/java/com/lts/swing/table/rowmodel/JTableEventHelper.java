package com.lts.swing.table.rowmodel;

import com.lts.event.ListenerHelper;

public class JTableEventHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object l, int type, Object d)
	{
		JTableEventListener listener = (JTableEventListener) l;
 		TableEvent event = (TableEvent) d;
		listener.jtableEvent(event);
	}

	public void fireEvent(TableEvent event)
	{
		fire(event);
	}

}
