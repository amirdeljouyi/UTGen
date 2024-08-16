package com.lts.channel.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A ListChannel is a list that notifies of clients of changes to the list.
 * 
 * <P>
 * The events that the class sends notifications regarding are defined by the 
 * "EVENT_" constants in the class {@link ListChannelEvent}; briefly they are 
 * add, remove and "all changed."
 * 
 * <P>
 * The list and the elements in int are serializeable, but the listeners are 
 * not.  If an instance of this class is deserialized, it is like creating a 
 * new instance from the standpoint of the listeners.
 * 
 * @author cnh
 */
public class ListChannel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected List myList;
	transient protected ListChannelHelper myHelper;
	
	
	public ListChannel()
	{
		initialize();
	}
	
	public List getList()
	{
		return myList;
	}
	
	protected ListChannelHelper getHelper()
	{
		if (null == myHelper)
			myHelper = new ListChannelHelper();
		
		return myHelper;
	}
	
	protected void initialize()
	{
		myList = new ArrayList();
		myHelper = new ListChannelHelper();
	}
	
	public void addListener(ListChannelListener listener)
	{
		getHelper().addListener(listener);
	}
	
	public boolean removeListener (ListChannelListener listener)
	{
		return getHelper().removeListener(listener);
	}
	
	protected void setList (List list)
	{
		if (null == list)
			throw new IllegalArgumentException();
		
		myList = list;
		ListChannelEvent event = new ListChannelEvent(ListChannelEvent.EVENT_ALL_CHANGED, this);
		getHelper().fire(event);
	}
	
	public void add (Object element)
	{
		if (myList.contains(element))
			return;
	
		myList.add(element);
		int index = myList.indexOf(element);
		
		ListChannelEvent event = new ListChannelEvent(ListChannelEvent.EVENT_ADD, this, index);
		getHelper().fire(event);
	}
	
	public void remove (Object element)
	{
		if (!myList.contains(element))
			return;
		
		int index = myList.indexOf(element);
		myList.remove(index);
		
		ListChannelEvent event = 
			new ListChannelEvent(ListChannelEvent.EVENT_REMOVE, this, index);
		getHelper().fire(event);
	}	
}
