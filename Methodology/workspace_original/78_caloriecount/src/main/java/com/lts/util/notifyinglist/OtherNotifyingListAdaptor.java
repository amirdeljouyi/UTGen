package com.lts.util.notifyinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class OtherNotifyingListAdaptor<E>
	extends SimplifiedList<E>
	implements NotifyingList<E>
{
	protected NotifyingListHelper myHelper = new NotifyingListHelper();
	protected List<E> myList = new ArrayList<E>();
	
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
	protected void kernelClear()
	{
		myList.clear();
		myHelper.fireAllChanged();
	}

	@Override
	protected boolean kernelCreate(int index, E element)
	{
		myList.add(index, element);
		myHelper.fireInsert(index, element);
		return true;
	}

	@Override
	protected boolean kernelDelete(int index)
	{
		E element = get(index);
		myList.remove(index);
		myHelper.fireDelete(index, element);
		return true;
	}

	@Override
	protected int kernelIndexOf(Object e)
	{
		return myList.indexOf(e);
	}

	@Override
	protected ListIterator<E> kernelIterator(int start)
	{
		return myList.listIterator(start);
	}

	@Override
	protected E kernelRead(int index)
	{
		return myList.get(index);
	}

	@Override
	protected int kernelSize()
	{
		return myList.size();
	}

	@Override
	protected E kernelUpdate(int index, E element)
	{
		return myList.set(index, element);
	}
}
