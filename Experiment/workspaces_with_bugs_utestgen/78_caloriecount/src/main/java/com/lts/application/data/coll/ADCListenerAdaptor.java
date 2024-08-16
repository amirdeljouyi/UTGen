package com.lts.application.data.coll;

import com.lts.application.data.ApplicationData;

/**
 * A class to simplify the implementation of creating an 
 * ApplicationDataCollectionListener.
 * 
 * <P>
 * This class defines methods for each of the different events defined by 
 * {@link ADCEvent.EventType}.  The default methods simply return without taking 
 * any action.
 * </P>
 * 
 * @author cnh
 */
public class ADCListenerAdaptor implements ADCListener
{
	public void eventOccurred(ADCEvent event)
	{
		switch (event.event)
		{
			case add :
				elementAdded(event.element);
				break;
				
			case delete :
				elementDeleted(event.element);
				break;
				
			case update :
				elementUpdated(event.element);
				break;
				
			case all :
				elementAllChanged();
				break;
				
				
			default :
				throw new IllegalArgumentException(event.event.name());
		}
	}

	protected void elementAllChanged()
	{
		// TODO Auto-generated method stub
		
	}

	protected void elementUpdated(ApplicationData element)
	{
	}

	protected void elementDeleted(ApplicationData element)
	{
	}

	protected void elementAdded(ApplicationData element)
	{
	}
}
