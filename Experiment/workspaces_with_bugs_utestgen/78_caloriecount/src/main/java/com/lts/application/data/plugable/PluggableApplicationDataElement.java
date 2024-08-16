package com.lts.application.data.plugable;

import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

abstract public class PluggableApplicationDataElement implements ApplicationDataElement
{
	transient private boolean myDirty;
	
	public boolean isDirty()
	{
		return myDirty;
	}

	public void postDeserialize() throws ApplicationException
	{
		myDirty = false;
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
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

	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
		PluggableApplicationDataElement other;
		other = (PluggableApplicationDataElement) copy;
		
		if (copyTransients)
			other.myDirty = myDirty;
	}
	
	public void copyFrom (ApplicationDataElement element) throws ApplicationException
	{
		postDeserialize();
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
