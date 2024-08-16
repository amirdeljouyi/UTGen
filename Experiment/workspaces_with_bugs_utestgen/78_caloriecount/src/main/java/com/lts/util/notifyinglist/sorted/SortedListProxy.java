package com.lts.util.notifyinglist.sorted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.ListProxy;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListListener;
import com.lts.util.notifyinglist.ListEvent.EventType;

/**
 * A list that obtains its data from a NotifyingList but presents that data to clients in
 * a different order.
 * <P>
 * This class contains a {@link NotifyingList} that it presents in an order determined by
 * a Comparator that is passed to it when the instance is created.
 * </P>
 * 
 * @author cnh
 * @param <E>
 */
public class SortedListProxy<E> 
	extends ListProxy<E> 
	implements NotifyingListListener, NotifyingList<E>
{
	protected Comparator myComparator;
	protected List<Integer> myVirtualList;
	
	public SortedListProxy (Comparator comparator, NotifyingList<E> actualList)
	{
		initialize(comparator, actualList);
	}
	
	protected void initialize(Comparator comparator, NotifyingList<E> actualList)
	{
		if (comparator instanceof IndirectComparator)
		{
			myComparator = comparator;
		}
		else
		{
			myComparator = new IndirectComparator(comparator, actualList);
		}
		
		myActualList = actualList;
		myVirtualList = buildVirtualList(myComparator, actualList);
		actualList.addListener(this);
	}

	protected List<Integer> buildVirtualList(Comparator comp, List<E> actual)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		int count = actual.size();
		for (int index = 0; index < count; index++)
		{
			list.add(index);
		}
		
		Collections.sort(list, comp);
		
		return list;
	}

	protected int findInsertPoint(int actual)
	{
		//
		// result of binary search if the element does not exist:
		//
		//     binarySearch(list, key, comp) = -1 * ipoint - 1
		//
		// ergo
		//		result = -1 * ipoint - 1
		//      result + 1 = -1 * ipoint
		//	    -1 * (result + 1) = ipoint
		// 		ipoint = -1 * (result + 1)
		//
		int index = Collections.binarySearch(myVirtualList, actual, myComparator);
		if (index < 0)
		{
			index = -1 * (index + 1);
		}
		
		return index;
	}
	

	protected E getActual(int virtual)
	{
		int index = myVirtualList.get(virtual);
		return myActualList.get(index);
	}
	
	@Override
	protected boolean kernelCreate(int virtual, E element)
	{
		int actual = myActualList.size();
		myActualList.add(element);
		myVirtualList.add(virtual, actual);
		return true;
	}

	@Override
	protected boolean kernelDelete(int virtual)
	{
		int actual = myVirtualList.get(virtual);
		myVirtualList.remove(virtual);
		return null != myActualList.remove(actual);
	}

	@Override
	protected int kernelIndexOf(Object e)
	{
		return myActualList.indexOf(e);
	}

	@Override
	protected ListIterator<E> kernelIterator(int start)
	{
		return new IndirectListIterator<E>(start, myVirtualList, myActualList);
	}

	@Override
	protected E kernelRead(int index)
	{
		return getActual(index);
	}

	@Override
	protected int kernelSize()
	{
		return myVirtualList.size();
	}

	@Override
	protected E kernelUpdate(int index, E element)
	{
		int actual = myVirtualList.get(index);
		return myActualList.set(actual, element);
	}

	@Override
	public void listEvent(ListEvent event)
	{
		switch (event.eventType)
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
		int oldIndex = myVirtualList.indexOf(event.index);
		int newIndex = findInsertPoint(event.index);
		if (newIndex == oldIndex)
		{
			myHelper.fireUpdate(newIndex, event.element);
		}
		else
		{
			ListEvent newEvent;
			newEvent = new ListEvent(EventType.Delete,event.index, event.element);
			listEventDelete(newEvent);
			newEvent = new ListEvent(EventType.Insert, event.index, event.element);
			listEventInsert(newEvent);
		}
	}

	protected void listEventInsert(ListEvent event)
	{
		E e = (E) event.element;
		int newIndex = findInsertPoint(event.index);
		myVirtualList.add(newIndex, event.index);
		myHelper.fireInsert(newIndex, e);
	}

	protected void listEventDelete(ListEvent event)
	{
		int virtual = myVirtualList.get(event.index);
		myVirtualList = buildVirtualList(myComparator, myActualList);
		myHelper.fireDelete(virtual, event.element);
	}

	protected void listEventAllChanged(ListEvent event)
	{
		myVirtualList = buildVirtualList(myComparator, myActualList);
		myHelper.fireAllChanged();
	}
}
