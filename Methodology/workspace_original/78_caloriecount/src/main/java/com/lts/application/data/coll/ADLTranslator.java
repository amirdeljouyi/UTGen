package com.lts.application.data.coll;

import com.lts.application.data.ApplicationDataElement;

/**
 * A class that translates the protocol defined by {@link ApplicationDataList}
 * to something that another object can understand.
 * 
 * <H2>Description</H2>
 * The class basically forwards method calls onto another object.
 * 
 * <P>
 * Subclasses must define the following methods:
 * </P>
 * 
 * <UL>
 * <LI>basicGet</LI>
 * <LI>basicInsert</LI>
 * <LI>basicDelete</LI>
 * <LI>basicUpdate</LI>
 * </UL>
 * 
 * @author cnh
 */
abstract public class ADLTranslator implements ApplicationDataList
{
	abstract protected Object basicGet(int index);
	abstract protected void basicInsert(int index, Object data);
	abstract protected void basicDelete(int index);
	abstract protected void basicUpdate(int index, Object newData);
	
	protected ADLListenerHelper myHelper;
	protected boolean myDirty;
	
	public ADLTranslator ()
	{
		myHelper = new ADLListenerHelper();
	}
	
	
	public void addADLListener(ApplicationDataListListener listener)
	{
		myHelper.addListener(listener);
	}

	public void append(ApplicationDataElement data)
	{
		int index = getCount();
		insert(index, data);
	}

	public void delete(int index)
	{
		basicDelete(index);
		myDirty = false;
		myHelper.fireDelete(index, this);
	}
	
	
	public boolean getDirty()
	{
		return myDirty;
	}

	public void insert(int index, ApplicationDataElement data)
	{
		basicInsert(index, data);
		myDirty = true;
		myHelper.fireCreate(index, this);
	}

	public boolean isDirty()
	{
		return myDirty;
	}

	public void removeADLListener(ApplicationDataListListener listener)
	{
		myHelper.removeListener(listener);
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	public void update(int index, ApplicationDataElement newValue)
	{
		basicUpdate(index, newValue);
		myDirty = true;
		myHelper.fireUpdate(index, this);
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
