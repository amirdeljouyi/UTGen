package com.lts.channel.list;


/**
 * This class represents a change to a ListChannel object.
 * <H2>Defined Events</H2>
 * <H3>EVENT_ADD</H3>
 * A new element has been added to the list.  The OldIndex property defines the 
 * location where the new element resides.  Old elements have been shifted down 
 * to accomodate the new element.  NewIndex is undefined.
 * 
 * <H3>EVENT_REMOVE</H3>
 * An existing element has been removed from the list.  The OldIndex property 
 * contains the index where the element used to reside.  NewIndex is undefined.
 * 
 * <H3>EVENT_MOVE</H3>
 * An existing element has been moved from one location in the list to a new 
 * location.  OldIndex contains the index in the list where the element used to
 * reside, NewIndex contains the  index where the element now resides.  
 * 
 * <P>
 * Other elements have been shifted up to fill the location where the element 
 * was and existing elements have been shifted down to accomodate the element's
 * new loication.
 * 
 * @author cnh
 */
public class ListChannelEvent
{
	public static final int EVENT_ADD = 0;
	public static final int EVENT_REMOVE = 1;
	public static final int EVENT_MOVE = 2;
	public static final int EVENT_ALL_CHANGED = 3;
	
	protected ListChannel myList;
	protected int myEventType;
	protected int myOldIndex;
	protected int myNewIndex;
	
	
	public ListChannelEvent (int event, ListChannel list)
	{
		initialize(event, list, -1, -1);
	}
	
	public ListChannelEvent (int event, ListChannel list, int oldIndex)
	{
		initialize (event, list, oldIndex, -1);
	}

	public ListChannelEvent (int event, ListChannel list, int oldIndex, int newIndex)
	{
		initialize(event, list, oldIndex, newIndex);
	}
	
	protected void initialize (int event, ListChannel list, int oldIndex, int newIndex)
	{
		myList = list;
		myEventType = event;
		myOldIndex = oldIndex;
		myNewIndex = newIndex;
	}
	
	
	public ListChannel getList()
	{
		return myList;
	}
	
	public int getEventType()
	{
		return myEventType;
	}
	
	public int getOldIndex()
	{
		return myOldIndex;
	}
	
	public int getNewIndex()
	{
		return myNewIndex;
	}
	
	public static String eventTypeToString (int event)
	{
		String s = "unknown";
		
		switch (event)
		{
			case EVENT_ADD :
				s = "Add";
				break;
				
			case EVENT_REMOVE :
				s = "Remove";
				break;
				
			case EVENT_MOVE :
				s = "Move";
				break;
				
			case EVENT_ALL_CHANGED :
				s = "All Changed";
				break;
		}
		
		return s;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(eventTypeToString(myEventType));
		sb.append (", ");
		sb.append (myOldIndex);
		sb.append (", ");
		sb.append (myNewIndex);
		
		return sb.toString();
	}
}
