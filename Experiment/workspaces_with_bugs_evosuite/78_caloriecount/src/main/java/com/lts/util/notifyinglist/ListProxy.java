package com.lts.util.notifyinglist;

import java.util.ListIterator;

import com.lts.util.notifyinglist.sublist.SublistProxy;

/**
 * A class that presents an interface to another list.
 * 
 * <P>
 * As it is, the class is not very useful, since one could simply use the actual list
 * to get the same effect.  The class is intended to serve as the base for other classes
 * that do something interesting, such as {@link SublistProxy}
 * </P>
 * 
 * @author cnh
 *
 * @param <E>
 */
public class ListProxy<E> extends SimplifiedList<E> 
	implements NotifyingList<E>, NotifyingListListener
{
	protected NotifyingList<E> myActualList;
	protected NotifyingListHelper myHelper = new NotifyingListHelper();

	
	protected ListProxy()
	{}
	
	public ListProxy(NotifyingList<E> list)
	{
		initialize(list);
	}
	
	
	protected void initialize(NotifyingList<E> list)
	{
		myActualList = list;
		myActualList.addListener(this);
	}
	
	
	@Override
	protected void kernelClear()
	{
		myActualList.clear();
	}

	@Override
	protected boolean kernelCreate(int index, E element)
	{
		myActualList.add(index, element);
		return true;
	}

	@Override
	protected boolean kernelDelete(int index)
	{
		E e = myActualList.remove(index);
		return null != e;
	}

	@Override
	protected int kernelIndexOf(Object e)
	{
		return myActualList.indexOf(e);
	}

	@Override
	protected ListIterator<E> kernelIterator(int start)
	{
		return myActualList.listIterator(start);
	}

	@Override
	protected E kernelRead(int index)
	{
		return myActualList.get(index);
	}

	@Override
	protected int kernelSize()
	{
		return myActualList.size();
	}

	@Override
	protected E kernelUpdate(int index, E element)
	{
		return myActualList.set(index, element);
	}

	@Override
	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}

	@Override
	public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}

	@Override
	public void listEvent(ListEvent event)
	{
		switch(event.eventType)
		{
			case AllChanged :
				listEventAllChanged(event);
				break;
				
			case Delete :
				listEventDelete(event);
				break;
				
			case Insert :
				listEventInsert(event);
				break;
				
			case Update :
				listEventUpdate(event);
				break;
		}
	}

	protected void listEventUpdate(ListEvent event)
	{
		
	}

	protected void listEventInsert(ListEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	protected void listEventDelete(ListEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	protected void listEventAllChanged(ListEvent event)
	{
		// TODO Auto-generated method stub
		
	}
}
