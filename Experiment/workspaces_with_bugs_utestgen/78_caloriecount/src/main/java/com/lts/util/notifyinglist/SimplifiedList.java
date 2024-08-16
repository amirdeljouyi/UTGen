package com.lts.util.notifyinglist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.lts.exception.NotImplementedException;

/**
 * Define a "kernel" of methods and then implement the List interface using those 
 * methods.
 * 
 * <H2>Abstract Methods</H2>
 * The following are the methods that subclasses must define in order to create a 
 * "concrete" implementation of the class:
 * 
 * <UL>
 *     <LI>kernelCreate</LI>
 *     <LI>kernelRead</LI>
 *     <LI>kernelUpdate</LI>
 *     <LI>kernelDelete</LI>
 *     <LI>kernelClear</LI>
 *     <LI>kernelIndexOf </LI>
 *     <LI>kernelSize</LI>
 *     <LI>kernelIterator</LI>
 *     <LI>sublist</LI>
 * </UL>
 *
 * <P>
 * To create a complete implementation of the List interface, one should implement
 * the following:
 * 
 * <UL>
 * <LI>{@link #specializedCaseToArray(Object[])}</LI>
 * <LI>{@link #sublist(int, int)}</LI>
 * </UL>
 *
 * <P>
 * These two methods were deemed overly complex and the default implementations simply
 * throw {@link NotImplementedException}.
 * </P>
 * 
 * @author cnh
 *
 * @param <E>
 */
abstract public class SimplifiedList<E> implements List<E>
{
	abstract protected boolean kernelCreate(int index, E element);
	abstract protected E kernelRead(int index);
	abstract protected E kernelUpdate(int index, E element);
	abstract protected boolean kernelDelete(int index);	
	abstract protected void kernelClear();	
	abstract protected int kernelIndexOf (Object e);	
	abstract protected int kernelSize();	
	abstract protected ListIterator<E> kernelIterator(int start);
	
	
	@Override
	public boolean add(E e)
	{
		int index = size();
		add(index, e);
		return true;
	}

	@Override
	public void add(int index, E element)
	{
		kernelCreate(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for (E e : c)
		{
			add(e);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		for (E e : c)
		{
			add(index, e);
			index++;
		}
		
		return true;
	}

	@Override
	public void clear()
	{
		kernelClear();
	}

	@Override
	public boolean contains(Object o)
	{
		return -1 != kernelIndexOf(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for (Object o : c)
		{
			if (!contains(o))
				return false;
		}
		
		return true;
	}

	@Override
	public E get(int index)
	{
		return kernelRead(index);
	}

	@Override
	public int indexOf(Object o)
	{
		return kernelIndexOf(o);
	}

	@Override
	public boolean isEmpty()
	{
		return kernelSize() > 0;
	}

	@Override
	public Iterator<E> iterator()
	{
		return kernelIterator(0);
	}

	@Override
	public int lastIndexOf(Object o)
	{
		int index = size() - 1;
		boolean found = false;
		
		while (!found && index >= 0)
		{
			Object current = get(index);
			if (current.equals(o))
				return index;
			
			index--;
		}
		
		return -1;
	}

	@Override
	public ListIterator<E> listIterator()
	{
		return kernelIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index)
	{
		return kernelIterator(index - 1);
	}

	@Override
	public boolean remove(Object o)
	{
		int index = indexOf(o);
		if (-1 == index)
			return false;
		else
		{
			remove(index);
			return true;
		}
	}

	@Override
	public E remove(int index)
	{
		E e = get(index);
		kernelDelete(index);
		return e;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean modified = false;
		
		for (Object o : c)
		{
			if (remove(o))
				modified = true;
		}
		
		return modified;
	}
	
	public boolean removeAll(E[] a)
	{
		boolean modified = false;
		
		for (E e : a)
		{
			if (null != e)
			{
				if (remove(e))
					modified = true;
			}
		}
		
		return modified;
	}
	

	@Override
	public boolean retainAll(Collection<?> c)
	{
		boolean modified = false;
		
		for (E e : this)
		{
			if (!c.contains(e))
			{
				remove(e);
				modified = true;
			}
		}
		
		return modified;
	}

	@Override
	public E set(int index, E element)
	{
		E old = get(index);
		kernelUpdate(index, element);
		return old;
	}

	@Override
	public int size()
	{
		return kernelSize();
	}

	@Override
	public Object[] toArray()
	{
		Object[] a = new Object[size()];
		int index = 0;
		for (E e : this)
		{
			a[index] = e;
			index++;
		}
		
		return toArray(a);
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		if (a.length < size())
		{
			return (T[]) specializedCaseToArray(a);
		}
		
		int count = size();
		int index = 0;
		for (E e : this)
		{
			a[index] = (T) e;
			index++;
		}
		
		if (a.length > count)
		{
			a[count] = null;
		}
		
		return a;
	}
	
	
	protected <T> T[] specializedCaseToArray(T[] a)
	{
		throw new NotImplementedException();
	}
	
	
	public List<E> subList(int from, int to)
	{
		throw new NotImplementedException();
	}
}
