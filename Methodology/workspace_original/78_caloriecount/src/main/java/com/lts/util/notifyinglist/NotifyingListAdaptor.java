package com.lts.util.notifyinglist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NotifyingListAdaptor<E> implements NotifyingList<E>
{
	protected NotifyingListHelper myHelper;
	protected List<E> myList;
	
	public NotifyingListAdaptor()
	{
		initialize(null);
	}
	
	public NotifyingListAdaptor(List<E> list)
	{
		initialize(list);
	}
	
	
	protected void initialize(List<E> list)
	{
		myHelper = new NotifyingListHelper();
		if (null == list)
			myList = new ArrayList<E>();
		else
			myList = list;
	}
	
	
	protected void initialize()
	{
		initialize(null);
	}
	
	public boolean add(E o)
	{
		int index = myList.size();
		add(index, o);
		return true;
	}

	public boolean addAll(Collection<? extends E> c)
	{
		for (E o : c)
		{
			add(o);
		}

		return c.size() > 0;
	}

	public boolean addAll(int index, Collection<? extends E> c)
	{
		for (E o : c)
		{
			add(index, o);
		}
		
		return c.size() > 0;
	}

	public void clear()
	{
		myList.clear();
	}

	public boolean contains(Object o)
	{
		return myList.contains(o);
	}

	public boolean containsAll(Collection c)
	{
		return myList.containsAll(c);
	}

	public boolean equals(Object o)
	{
		return myList.equals(o);
	}

	public E get(int index)
	{
		return myList.get(index);
	}

	public int hashCode()
	{
		return myList.hashCode();
	}

	public int indexOf(Object o)
	{
		return myList.indexOf(o);
	}

	public boolean isEmpty()
	{
		return myList.isEmpty();
	}

	public Iterator iterator()
	{
		return myList.iterator();
	}

	public int lastIndexOf(Object o)
	{
		return myList.lastIndexOf(o);
	}

	public ListIterator listIterator()
	{
		return myList.listIterator();
	}

	public ListIterator listIterator(int index)
	{
		return myList.listIterator(index);
	}

	public E remove(int index)
	{
		E o = basicRemove(index);
		myHelper.fireDelete(index, o);
		return o;
	}

	/**
	 * Remove an element without firing any notifications.
	 * 
	 * @param index The index of the element to remove.
	 * @return The removed element.
	 */
	protected E basicRemove(int index)
	{
		E o = myList.remove(index);
		return o;
	}

	public boolean remove(Object o)
	{
		int index = indexOf(o);
		if (index != -1)
		{
			myList.remove(index);
			myHelper.fireDelete(index, o);
		}
		
		return index != -1;
	}
	
	public void removeAll (int[] indicies)
	{
		if (null == indicies)
			return;
		
		int[] copy = Arrays.copyOf(indicies, indicies.length);
		Arrays.sort(copy);
		for (int index = copy.length - 1; index >= 0; index--)
		{
			remove(index);
		}
	}

	public boolean removeAll(Collection c)
	{
		for (Object o : c)
		{
			remove(o);
		}
		
		return c.size() > 0;
	}

	public boolean retainAll(Collection c)
	{
		boolean modified = false;

		for (int index = myList.size() - 1; index >= 0; index--)
		{
			Object o = myList.get(index);
			if (!c.contains(o))
			{
				modified = true;
				remove(index);
			}
		}
		
		return modified;
	}

	public int size()
	{
		return myList.size();
	}

	public List subList(int fromIndex, int toIndex)
	{
		return myList.subList(fromIndex, toIndex);
	}

	public Object[] toArray()
	{
		return myList.toArray();
	}

	public Object[] toArray(Object[] a)
	{
		return myList.toArray(a);
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");
		boolean first = true;
		for (Object o : myList)
		{
			if (first)
				first = false;
			else
				sb.append (", ");
			
			sb.append(o);
		}
		sb.append("}");
		
		return sb.toString();
	}

	public void add(int index, E element)
	{
		index = basicAdd(index, element);
		myHelper.fireInsert(index, element);
	}

	/**
	 * Add without sending notifications.
	 * 
	 * <P>
	 * If the list has any special requirements or operations to do before signaling
	 * that an insert/add has taken place, this is where it should happen.
	 * </P>
	 * 
	 * @param index Where in the list to insert.
	 * @param element The element to insert
	 * @return The location actually used to insert the element.
	 */
	protected int basicAdd(int index, E element)
	{
		if (-1 == index)
			index = myList.size();
		
		myList.add(index, element);
		return index;
	}

	@Override
	public E set(int index, E element)
	{
		E o = basicSet(index, element);
		myHelper.fireUpdate(index, element);
		return o;
	}

	protected E basicSet(int index, E element)
	{
		E o = myList.set(index, element);
		return o;
	}
	
	

	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}

	public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	synchronized public void replaceWith(List<E> list)
	{
		basicReplaceWith(list);		
		myHelper.fireAllChanged();
	}

	protected void basicReplaceWith(List<E> list)
	{
		myList = new ArrayList<E>(list);
	}
}
