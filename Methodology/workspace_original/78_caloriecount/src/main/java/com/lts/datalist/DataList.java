package com.lts.datalist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.bean.xml.annotations.IgnoreProperty;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;
import com.lts.util.notifyinglist.NotifyingListAdaptor;
import com.lts.util.notifyinglist.NotifyingListHelper;

/***
 * A combination of a NotifyingList and an ApplicationDataElement.
 * 
 * <H2>Quick Start</H2>
 * <UL>
 * <LI>Subclass DataList</LI>
 * <LI>
 * 		If the subclass defines any additional data, define/override
 * 		<UL>
 * 		<LI>deepCopyData - to copy subclass specific data</LI>
 * 		<LI>copyFrom - to copy subclass specific data</LI>
 * 		<LI>postDeserialize - to perform any special post deserialization operations</LI>
 *		</UL>
 * </LI>
 * </UL> 
 * 
 * <P>
 * This class brings event notification to a collection of {@link ApplicationDataElement}
 * objects.  Specifically, create, update, delete and all changed.  Furthermore,
 * it provides a basic implementation for this stuff, so clients can concentrate 
 * on the logic instead of the glue.
 * </P>
 * 
 * <H2><A name="subclassData">Subclass Data</A></H2>
 * The following discussions really only apply to subclasses of this class that
 * define additional data fields/members.  If your subclass does not, then skip this
 * section.
 * 
 * <P>
 * As an example, in calorie count there is a list of foods.  Each food is suppose
 * to have a unique ID, and to make things a bit faster, the list keeps track of the 
 * largest ID currently used by the list.  When a new food is added, therefore, 
 * the max ID can be incremented and used, instead of searching through the list to
 * figure out which IDs are available.
 * </P>
 * 
 * <P>
 * Supposing for the moment that this is considered transient data --- it is not 
 * part of the state of the list --- then we need some way of handling that data 
 * when copying the list.
 * </P>
 * 
 * <H2><A name="overrideDeepCopyData">deepCopyData</A></H2>
 * Briefly, a deep copy of an object is like the result of a clone operation except that
 * instances so created are completely independent of the object they are copied from.
 * Continuing the food list example, this method needs to copy the current maxId to the
 * new instance.
 *
 * <H2><A name="overrideCopyFrom">copyFrom</A></H2>
 * This method is used when an instance is updated from another instance.  Continuing 
 * the food list example, the max ID value needs to be updated during this operation.
 * 
 * <H2><A name="overridePostDeserialize">postDeserialize</A></H2>
 * This method is called after an instance is deserialized.  Continuing the food 
 * list an example, we would need to regenerate the max ID property, since that data
 * is not part of the object's persistent state.
 * 
 * @author cnh
 *
 */
abstract public class DataList<E> 
	extends NotifyingListAdaptor<E> 
	implements ApplicationDataElement
{
	private static final long serialVersionUID = 1L;
	
	transient protected boolean myDirty;
	transient protected boolean myRectified;
	
	public DataList()
	{
		
	}
	public boolean rectified()
	{
		return myRectified;
	}
	
	@IgnoreProperty
	public boolean isRectified()
	{
		return rectified();
	}
	
	public void setRectified(boolean rectified)
	{
		myRectified = rectified;
	}
	
	protected void initialize()
	{
		super.initialize();
		try
		{
			postDeserialize();
		}
		catch (ApplicationException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public void copyFrom(ApplicationDataElement element) throws ApplicationException
	{
		DataList list = (DataList) element;
		myList = new ArrayList<E>(list.myList);
		myDirty = true;
		myHelper.fireAllChanged();
	}

	@IgnoreProperty
	public boolean isDirty()
	{
		return myDirty;
	}

	public void postDeserialize() throws ApplicationException
	{
		myHelper = new NotifyingListHelper();
		myDirty = false;
		
		removeNulls();
	}

	protected void removeNulls()
	{
		int index = 0;
		List<Integer> deletes = new ArrayList<Integer>();
		for (Object o : this)
		{
			if (null == o)
			{
				deletes.add(index);
			}
			index++;
		}
		
		Collections.sort(deletes);
		for (index = deletes.size() - 1; index >= 0; index--)
		{
			remove(index);
		}
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
			throws DeepCopyException
	{
		DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
		return this;
	}

	public Object deepCopy() throws DeepCopyException
	{
		return deepCopy(false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	public void deepCopyData(Object o, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		DataList copy = (DataList) o;
		copy.myList = (List<ApplicationDataElement>)
			DeepCopyUtil.continueDeepCopy(myList, map, copyTransients);
		
		if (copyTransients)
		{
			copy.myDirty = myDirty;
			copy.myHelper = (NotifyingListHelper) 
				DeepCopyUtil.continueDeepCopy(myHelper, map, copyTransients);
		}
	}
	
	public void add(int index, E element)
	{
		setDirty(true);
		super.add(index, element);
	}
	
	
	public E remove(int index)
	{
		setDirty(true);
		return super.remove(index);
	}
}
