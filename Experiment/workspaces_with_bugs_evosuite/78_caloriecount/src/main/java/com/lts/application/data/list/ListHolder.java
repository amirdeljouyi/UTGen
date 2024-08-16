package com.lts.application.data.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListDataListener;

import com.lts.application.ApplicationException;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;
import com.lts.util.notifyinglist.NotifyingListHelper;
import com.lts.util.notifyinglist.NotifyingListListener;

abstract public class ListHolder implements ListData
{
	private static final long serialVersionUID = 1L;

	transient private NotifyingListHelper myHelper;
	transient private boolean myDirty;
	private List myList;
	

	protected void initialize(List list)
	{
		myHelper = new NotifyingListHelper();
		myList = list;
	}
	
	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}

	public Object get(int index)
	{
		return myList.get(index);
	}

	public List getAll()
	{
		return myList;
	}

	public int getCount()
	{
		return myList.size();
	}

	synchronized public void insert(Object data, int location)
	{
		myList.add(location, data);
		myHelper.fireInsert(location, data);
	}
	
	
	synchronized public void append (Object data)
	{
		int index = myList.size();
		insert(data, index);
	}

	synchronized public Object remove(int location)
	{
		Object o = myList.remove(location);
		myHelper.fireDelete(location, o);
		return o;
	}

	public boolean removeListener(ListDataListener listener)
	{
		return myHelper.removeListener(listener);
	}

	synchronized public void update(Object data, int location)
	{
		Object o = myList.get(location);
		myList.set(location, data);
		myHelper.fireUpdate(location, o);
	}

	public boolean isDirty()
	{
		return myDirty;
	}

	public void postDeserialize() throws ApplicationException
	{
		myDirty = false;
		if (null == myList)
			myList = new ArrayList();
		
		myHelper = new NotifyingListHelper();
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
			throws DeepCopyException
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

	public void deepCopyData(Object copy, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		ListHolder holder = (ListHolder) copy;
		holder.myList = DeepCopyUtil.copyList(myList, map, copyTransients);
		
		if (copyTransients)
		{
			holder.myDirty = myDirty;
			holder.myHelper = myHelper;
		}
	}

	synchronized public boolean contains(Object data)
	{
		return myList.contains(data);
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
}
