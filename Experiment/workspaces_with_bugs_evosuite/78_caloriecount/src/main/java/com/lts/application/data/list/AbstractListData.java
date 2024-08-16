package com.lts.application.data.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;
import com.lts.util.notifyinglist.NotifyingListHelper;
import com.lts.util.notifyinglist.NotifyingListListener;

abstract public class AbstractListData<E> implements ListData<E>
{
	transient private boolean myDirty;
	transient protected NotifyingListHelper myHelper;
	protected List<E> myData;
	
	public AbstractListData()
	{
		try
		{
			postDeserialize();
		}
		catch (ApplicationException e)
		{
			throw new RuntimeException(e);
		}
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
	
	@Override
	public E get(int index)
	{
		return myData.get(index);
	}

	public List getAll()
	{
		return myData;
	}

	public int getCount()
	{
		return myData.size();
	}

	synchronized public void insert(E data, int location)
	{
		myDirty = true;
		myData.add(location, data);
		myHelper.fireInsert(location, data);
	}

	@Override
	synchronized public E remove(int location)
	{
		myDirty = true;
		E o = get(location);
		myData.remove(location);
		myHelper.fireDelete(location, o);
		return o;
	}

	synchronized public void update(Object data, int location)
	{
		myDirty = true;
		
		ApplicationDataElement oldData = 
			(ApplicationDataElement) myData.get(location);
		
		ApplicationDataElement newData = (ApplicationDataElement) data;
		
		try
		{
			oldData.copyFrom(newData);
		}
		catch (ApplicationException e)
		{
			throw new RuntimeException(e);
		}
		
		myHelper.fireUpdate(location, data);
	}

	@Override
	synchronized public void copyFrom(ApplicationDataElement element) throws ApplicationException
	{
		myDirty = true;
		
		ListData newList = (ListData) element;
		
		myData = new ArrayList(newList);
		myHelper.fireAllChanged();
	}

	@Override
	public boolean isDirty()
	{
		return myDirty;
	}

	@Override
	public void postDeserialize() throws ApplicationException
	{
		myDirty = false;
		myHelper = new NotifyingListHelper();
		
		if (null == myData)
			myData = new ArrayList();
	}

	@Override
	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	@Override
	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
			throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	@Override
	public Object deepCopy() throws DeepCopyException
	{
		return deepCopy(false);
	}

	@Override
	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	@Override
	public void deepCopyData(Object copy, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		AbstractListData data = (AbstractListData) copy;
		
		data.myData = DeepCopyUtil.copyList(myData, map, copyTransients);
		
		
		if (copyTransients)
		{
			String msg = "Copying transients is not supported.";
			throw new DeepCopyException(msg);
		}
	}


	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}


	public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}


	@Override
	public boolean add(E e)
	{
		return myData.add(e);
	}


	@Override
	public void add(int index, E element)
	{
		myData.add(element);
	}


	public boolean addAll(Collection<? extends E> c)
	{
		return myData.addAll(c);
	}


	public boolean addAll(int index, Collection<? extends E> c)
	{
		return myData.addAll(index, c);
	}


	public void clear()
	{
		myData.clear();
	}


	public boolean contains(Object o)
	{
		return myData.contains(o);
	}


	public boolean containsAll(Collection<?> c)
	{
		return myData.containsAll(c);
	}


	public boolean equals(Object o)
	{
		return myData.equals(o);
	}


	public int hashCode()
	{
		return myData.hashCode();
	}


	public int indexOf(Object o)
	{
		return myData.indexOf(o);
	}


	public boolean isEmpty()
	{
		return myData.isEmpty();
	}


	public Iterator<E> iterator()
	{
		return myData.iterator();
	}


	public int lastIndexOf(Object o)
	{
		return myData.lastIndexOf(o);
	}


	public ListIterator<E> listIterator()
	{
		return myData.listIterator();
	}


	public ListIterator<E> listIterator(int index)
	{
		return myData.listIterator(index);
	}


	public boolean remove(Object o)
	{
		return myData.remove(o);
	}


	public boolean removeAll(Collection<?> c)
	{
		return myData.removeAll(c);
	}


	public boolean retainAll(Collection<?> c)
	{
		return myData.retainAll(c);
	}


	public E set(int index, E element)
	{
		return myData.set(index, element);
	}


	public int size()
	{
		return myData.size();
	}


	public List<E> subList(int fromIndex, int toIndex)
	{
		return myData.subList(fromIndex, toIndex);
	}


	public Object[] toArray()
	{
		return myData.toArray();
	}


	public <T> T[] toArray(T[] a)
	{
		return myData.toArray(a);
	}
}
