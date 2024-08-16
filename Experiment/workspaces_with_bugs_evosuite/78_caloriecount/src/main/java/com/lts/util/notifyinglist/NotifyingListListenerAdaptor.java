package com.lts.util.notifyinglist;

public class NotifyingListListenerAdaptor implements NotifyingListListener
{

	public void listEvent(ListEvent event)
	{
		switch (event.eventType)
		{
			case AllChanged :
				allRowsChanged();
				break;
				
			case Delete :
				rowDeleted(event.index, event.element);
				break;
				
			case Insert :
				rowInserted(event.index, event.element);
				break;
				
			case Update :
				rowUpdated(event.index, event.element);
				break;
		}
		anyEvent(event);
	}

	public void allRowsChanged()
	{}

	public void rowUpdated(int index, Object element)
	{}

	public void rowInserted(int index, Object element)
	{}
	
	public void rowDeleted(int index, Object element)
	{}

	public void anyEvent(ListEvent event)
	{}
}
