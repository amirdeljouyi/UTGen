package com.lts.util.notifyinglist;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.lts.exception.NotImplementedException;

/**
 * Iterator for the NotifyingListProxy class.
 * 
 * <P>
 * This class throws {@link NotImplementedException} for the set and remove optional
 * methods.
 * </P>
 * 
 * @author cnh
 *
 * @param <E>
 */
public class ProxyIterator<E> implements ListIterator<E>
{
	protected int myIndex;
	protected List<Integer> myVirtual;
	protected List<E> myActual;
	
	public ProxyIterator(List<Integer> virtual, List<E> actual, int start)
	{
		myIndex = start - 1;
		myVirtual = virtual;
		myActual = actual;
	}
	
	
	@Override
	public void add(Object o)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNext()
	{
		int nextIndex = 1 + myIndex;		
		return nextIndex < myVirtual.size(); 
	}

	@Override
	public boolean hasPrevious()
	{
		int size = myVirtual.size();
		return (size > 0) && (myIndex > 0);
	}

	protected E virtualToActual(int virtual)
	{
		int actual = myVirtual.get(virtual);
		return myActual.get(actual);
	}
	
	@Override
	public E next()
	{
		myIndex++;
		if (myIndex >= myVirtual.size())
		{
			throw new NoSuchElementException();
		}
		
		return virtualToActual(myIndex);
	}

	@Override
	public int nextIndex()
	{
		return 1 + myIndex;
	}

	@Override
	public E previous()
	{
		if (myIndex < 0)
			throw new NoSuchElementException();
		else
		{
			E o = virtualToActual(myIndex);
			myIndex--;
			return o;
		}
	}

	@Override
	public int previousIndex()
	{
		if (myIndex < 0)
			return -1;
		else
			return myIndex;
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
