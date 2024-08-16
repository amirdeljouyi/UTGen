package com.lts.util.notifyinglist.sublist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.ListProxy;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.ListEvent.EventType;

/**
 * A ListProxy that filters some of its elements.
 * <P>
 * This class defines the {@link #include(Object)} method that, in turn decides if an
 * element in the underlying list show appear to this classes clients. If not, then for
 * all intents and purposes, the clients do not know about the filtered element.
 * </P>
 * <P>
 * The class therefore has notions of a virtual list and an actual list. The actual list
 * is the underlying list that an instance of this class is presented with when it is
 * instantiated. It is sometimes referred to as the "underlying list." The virtual list
 * maps from elements in the client interface list to elements in the actual list.
 * </P>
 * <P>
 * If, for example, the underlying list has elements [0-5], but the sublist decides that
 * element 3 should be excluded, then the virtual list would contain these mappings:
 * [0=>0, 1=>1, 2=>2, 3=>4, 4=>5]
 * </P>
 * 
 * @author cnh
 * @param <E>
 */
public class SublistProxy<E> extends ListProxy<E>
{
	protected SublistInclusionTest<E> myTest;
	protected List<Integer> myVirtualToActual = new ArrayList<Integer>();
	protected Map<Integer, Integer> myActualToVirtual = new HashMap<Integer, Integer>();
	
	public SublistProxy(NotifyingList<E> list, SublistInclusionTest<E> test)
	{
		initialize(list, test);
	}

	protected void initialize(NotifyingList<E> list, SublistInclusionTest<E> test)
	{
		myTest = test;
		super.initialize(list);
		rebuildVirtual();
		rebuildActualToVirtual();
	}

	protected void rebuildVirtual()
	{
		int virtual = 0;
		int actual = 0;
		
		myVirtualToActual = new ArrayList<Integer>();
		
		for (actual = 0; actual < myActualList.size(); actual++)
		{
			E e = myActualList.get(actual);
			if (include(e))
			{
				myVirtualToActual.add(virtual, actual);
				virtual++;
			}
		}
	}
	
	protected void rebuildActualToVirtual()
	{
		myActualToVirtual = new HashMap<Integer, Integer>();
		for (int virtual = 0; virtual < myVirtualToActual.size(); virtual++)
		{
			int actual = myVirtualToActual.get(virtual);
			myActualToVirtual.put(actual, virtual);
		}
	}
	
	
	protected boolean include(E e)
	{
		return myTest.include(e);
	}

	protected int virtualInsert(int virtual, int actual, E e)
	{
		if (virtual < 0)
			virtual = 0;
		
		if (virtual >= myVirtualToActual.size())
		{
			virtual = myVirtualToActual.size();
		}
		
		myVirtualToActual.add(actual);
		myActualToVirtual.put(actual, virtual);
		
		return virtual;
	}


	/**
	 * This is a special case because the entire sublist that is added 
	 * can end up having its indicies thrown off.  For this reason, the 
	 * elements are simply added one after another.
	 */
	protected void initializeVirtualList()
	{
		rebuildVirtualList();
	}

	private void rebuildVirtualList()
	{
		int virtual = 0;
		int actual = 0;
		int actualSize = myActualList.size();
		
		for (actual = 0; actual < actualSize; actual++)
		{
			E e = myActualList.get(actual);
			if (include(e))
			{
				virtualInsert(virtual, actual, e);
				virtual++;
			}
		}
	}
		
	protected int actualToVirtual(int actual)
	{
		if (null == myActualToVirtual.get(actual))
			return -1;
		else
			return myActualToVirtual.get(actual);
	}

	@Override
	protected void listEventUpdate(ListEvent event)
	{
		E e = (E) event.element;
		int actual = event.index;
		int virtual = actualToVirtual(event.index);

		boolean wasVisible = -1 != virtual;
		boolean isVisible = include(e);
		
		if (wasVisible)
		{
			if (isVisible)
			{
				myHelper.fireUpdate(virtual, event.element);
			}
			else
			{
				ListEvent newEvent = new ListEvent(EventType.Delete, actual, e);
				listEventDelete(newEvent);
			}
		}
		else if (isVisible)
		{
			ListEvent newEvent = new ListEvent(EventType.Insert, actual, e);
			listEventInsert(newEvent);
		}
	}

	
	@Override
	protected void listEventDelete(ListEvent event)
	{
		int actual = event.index;
		int virtual = -1;
		if (null != myActualToVirtual.get(actual))
			virtual = myActualToVirtual.get(actual);
		
		if (-1 != virtual)
		{
			rebuildVirtual();
			rebuildActualToVirtual();
			myHelper.fireDelete(virtual, event.element);
		}
	}

	@Override
	protected void listEventInsert(ListEvent event)
	{
		int actual = event.index;
		E e = (E) event.element;
		if (include(e))
		{
			int virtual = findClosestTo(actual);
			virtualInsert(virtual, actual, e);
			rebuildActualToVirtual();
			myHelper.fireInsert(virtual, e);
		}
	}

	protected int findClosestTo(int actual)
	{
		if (myActualList.size() > 0)
		{
			for (int index = 0; index < myVirtualToActual.size(); index++)
			{
				int current = myVirtualToActual.get(index);
				if (current > actual)
					return 1 + index;
			}
		}
		
		return myVirtualToActual.size();
	}
	

	@Override
	protected E kernelRead(int index)
	{
		int actual = myVirtualToActual.get(index);
		return myActualList.get(actual);
	}
	

	@Override
	protected boolean kernelDelete(int index)
	{
		int actual = myVirtualToActual.get(index);
		return null != myActualList.remove(actual);
	}

	public void refresh()
	{
		initialize(myActualList, myTest);
		myHelper.fireAllChanged();
	}

	public int size()
	{
		return myVirtualToActual.size();
	}
}
