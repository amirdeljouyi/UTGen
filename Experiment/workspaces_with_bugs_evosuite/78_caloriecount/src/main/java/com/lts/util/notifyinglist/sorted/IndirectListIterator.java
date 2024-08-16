package com.lts.util.notifyinglist.sorted;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A read-only ListIterator.
 * 
 * <P>
 * This class takes a list to perform operations on as an argument and throws 
 * an UnsupportedOperationException for add, remove and set.
 * </P>
 * 
 * @author cnh
 *
 * @param <E>
 */
public class IndirectListIterator<E> implements ListIterator<E>
{
	public List<E> myActualList;
	public List<Integer> myVirtualList;
	
	public int myCurrent = -1;
	
	public IndirectListIterator (int index, List<Integer> virtual, List<E> actual)
	{
		initialize(index, virtual, actual);
	}
	
	
	protected void initialize(int index, List<Integer> virtual, List<E> actual)
	{
		myCurrent = index;
		myActualList = actual;
		myVirtualList = virtual;
	}


	protected E getActual(int virtual)
	{
		int actual = myVirtualList.get(virtual);
		return myActualList.get(actual);
	}
	
	public IndirectListIterator(List<E> actual, List<Integer> virtual)
	{
		myActualList = actual;
		myVirtualList = virtual;
	}
	
	
	@Override
	public void add(E e)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNext()
	{
		
		return (myCurrent + 1) < myVirtualList.size();
	}

	@Override
	public boolean hasPrevious()
	{
		return myCurrent < myVirtualList.size();
	}

	@Override
	public E next()
	{
		myCurrent++;
		if (myCurrent >= myVirtualList.size())
		{
			throw new NoSuchElementException();
		}
		
		return getActual(myCurrent);
	}

	@Override
	public int nextIndex()
	{
		return myCurrent + 1;
	}

	@Override
	public E previous()
	{
		if (myCurrent <= 0)
			throw new NoSuchElementException();
		
		E e = getActual(myCurrent);
		myCurrent--;
		return e;
	}

	@Override
	public int previousIndex()
	{
		return myCurrent;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(E e)
	{
		throw new UnsupportedOperationException();
	}

}
