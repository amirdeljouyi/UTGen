package com.lts.application.data.coll;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationData;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

@SuppressWarnings("serial")
public class ADCAdaptor 
	implements ApplicationDataCollection
{
	protected boolean myDirty;
	protected Collection<ApplicationData> myElements;
	protected ApplicationDataListHelper myHelper;
	
	protected void initialize(Collection<ApplicationData> elements)
	{
		myElements = elements;
		myHelper = new ApplicationDataListHelper();
		myDirty = false;
	}
	
	public boolean isDirty() throws ApplicationException
	{
		return myDirty;
	}

	public void postDeserialize() throws ApplicationException
	{
		for (ApplicationData element : this)
		{
			element.setDirty(false);
		}
	}

	public void setDirty(boolean dirty)
	{
		myDirty = false;
		for (ApplicationData element : this)
		{
			element.setDirty(false);
		}
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return deepCopy(false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	public void deepCopyData(Object o, Map map, boolean copyTransients) throws DeepCopyException
	{
		ApplicationDataCollection copy = (ApplicationDataCollection) o;
		
		for (ApplicationData element : this)
		{
			ApplicationData elementCopy;
			elementCopy = (ApplicationData) element.deepCopy();
			copy.add(elementCopy);
		}
	}

	public boolean add(ApplicationData o)
	{
		boolean changed = myElements.add(o);
		if (changed)
			myHelper.fireElementAdded(o);
		
		return changed;
	}

	public boolean addAll(Collection<? extends ApplicationData> c)
	{
		boolean changed = false;
		
		for (ApplicationData data : c)
		{
			if (!changed)
				changed = add(data);
		}
		
		return changed;
	}

	public void clear()
	{
		myElements.clear();
		myHelper.fireAllChanged();
	}

	public boolean contains(Object o)
	{
		return myElements.contains(o);
	}

	public boolean containsAll(Collection<?> c)
	{
		for (ApplicationData element : this)
		{
			if (!contains(element))
				return false;
		}
		
		return true;
	}

	public boolean isEmpty()
	{
		return myElements.isEmpty();
	}

	public Iterator<ApplicationData> iterator()
	{
		return myElements.iterator();
	}

	public boolean remove(Object o)
	{
		boolean changed = myElements.remove(o);
		if (changed)
			myHelper.fireDelete((ApplicationData) o);
		
		return changed;
	}

	public boolean removeAll(Collection<?> c)
	{
		boolean changed = false;
		
		for (Object o : c)
		{
			boolean change = remove(o);
			if (change && !changed)
				changed = true;
		}
		
		return changed;
	}

	public boolean retainAll(Collection<?> c)
	{
		boolean changed = false;
		
		for (ApplicationData data : this)
		{
			if (!(c.contains(data)))
			{
				remove(data);
				changed = true;
			}
		}
		
		return changed;
	}

	public int size()
	{
		return myElements.size();
	}

	public Object[] toArray()
	{
		return myElements.toArray();
	}

	public <T> T[] toArray(T[] a)
	{
		return myElements.toArray(a);
	}
	
	
	public void update (ApplicationData data)
	{
		myHelper.fireUpdate(data);
	}

	public void addADCListener(ADCListener listner)
	{
		myHelper.addListener(listner);
	}

	public boolean removeADCListener(ADCListener listener)
	{
		return myHelper.removeListener(listener);
	}
}
