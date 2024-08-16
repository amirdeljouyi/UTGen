package com.lts.application.data;

import java.util.List;
import java.util.Map;

import com.lts.EnumWrapper;
import com.lts.application.ApplicationException;
import com.lts.util.ReallyCaselessMap;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;


/**
 * ApplicationData that can represent the underlying information via a map 
 * from names to values.
 * 
 * <P>
 * This class seeks to simplify data storage and retrieval by providing 
 * clients with a simple name to value style approach to elements.  That is,
 * clients can get a Map that goes from names (Strings) to values (Objects).
 * </P>
 * 
 * <P>
 * This decouples clients from implementations by freeing them from having to 
 * "know" what the attributes of a particular applications data are.
 * </P>
 * 
 * <P>
 * Ideally, all that would be required would be an enum that a subclass defined
 * that would in turn identify the various elements of the applications data,
 * but unfortunately, enums cannot be used in this manner.  Instead, strings 
 * must be used so that the names of the various elements are not known by 
 * classes that interact with this interface.
 * </P>
 * 
 * <P>
 * Elements are identified via a string.  
 * </P>
 * 
 * <P>
 * Subclasses must define a buildEntries method to create the entries when an 
 * instance of this class is first created.
 * </P>
 * 
 * <P>
 * Subclasses must also define a postDeserializeEntries method that validates entries
 * after an instance of this class is deserialized, but before it is declared to be 
 * in a valid state.
 * </P>
 * 
 * <P>
 * Deep copies are accomplished by forwarding deep copy events onto the various 
 * elements, which the exception of {@link #deepCopyData(Object, Map, boolean)}.
 * That method uses {@link DeepCopyUtil#continueDeepCopy(Object, Map, boolean)} 
 * to copy the currently defined element data.
 * </P>
 * 
 * <P>
 * The {@link #initialize()} method should be called at some point during the creation
 * of an instance of this class.  Subclasses are not forced to do so by a constructor,
 * etc., in order to give them flexibility, in dealing with initialization; but subclasses
 * should call initialize before returning from their constructor. 
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class MultiElementAppData<E extends Enum<E>> implements ApplicationData
{
	abstract protected EnumWrapper<E> getEnumWrapper();
	
	protected EnumWrapper<E> myWrapper;
	protected boolean myInitialized = false;
	
	public E[] getDataElements()
	{
		return myWrapper.values();
	}
	
	
	protected void processData(ReallyCaselessMap<ApplicationDataElement> map) throws ApplicationException
	{
		myElements = map;
		processElements();
	}

	private void processElements()
	{
		Enum[] abstractValues = myWrapper.values();
		E[] values = (E[]) abstractValues;
		
		for (E e : values)
		{
			ApplicationDataElement value = myElements.get(e);
			processDataElement(e, value);
		}
	}
	


	public ReallyCaselessMap<ApplicationDataElement> myElements;
	
	/**
	 * Create the initial entries for the application data.
	 * 
	 * <P>
	 * This method should be called once when an instance is first created.  Its 
	 * purpose is to create the entries that make up the application data and to 
	 * call addEntry for each created entry.
	 * </P>
	 * 
	 * <P>
	 * Here is a sample implementation:
	 * </P>
	 * 
	 * <codea>
	 */
	abstract protected void buildEntries();
	
	/**
	 * Ensure entries are in a valid state just after deserializing an instance of
	 * this class.
	 * 
	 * <P>
	 * This is called by the {@link #postDeserialize()} method after ensuring that
	 * {@link #myElements} exists, but before doing anything else.  The subclass should
	 * ensure that any entries it wants to use are defined and in a consistent state
	 * at this point.
	 * </P>
	 */
	abstract protected void postDeserializeEntries();
	
	
	abstract public List<String> getEntryNames();
	
	

	public ApplicationDataElement get(E element)
	{
		return (ApplicationDataElement) myElements.get(element.toString());
	}
	
	public void setEntry (E e, ApplicationDataElement element)
	{
		String s = e.toString();
		myElements.put(s, element);
	}
	
	
	public void setEntry (String name, ApplicationDataElement element)
	{
		myElements.put(name, element);
	}
	
	public void addEntry (String name, ApplicationDataElement element)
	{
		myElements.put(name, element);
	}
	
	public void setDirty (boolean dirty)
	{
		for (ApplicationDataElement element : myElements.values())
		{
			element.setDirty(dirty);
		}
	}
	
	public boolean getDirty ()
	{
		for (ApplicationDataElement element : myElements.values())
		{
			if (element.isDirty())
				return true;
		}
		
		return false;
	}
	
	public boolean isDirty()
	{
		return getDirty();
	}
	
	/**
	 * Ensure that the constituent elements are defined and in a consistent state
	 * after deserialization.
	 * 
	 * <P>
	 * This method does the following: 
	 * </P>
	 * 
	 * <UL>
	 * <LI>create {@link #myElements}, if it doesn't already exist.</LI>
	 * <LI>call {@link #postDeserializeEntries()}, to ensure that all constituent
	 * elements exist.</LI>
	 * <LI>call {@link ApplicationDataElement#postDeserialize()} on each delement.</LI>
	 * <LI>call {@link #setDirty(boolean)}, which in turn calls {@link 
	 * ApplicationDataElement#setDirty(boolean)} on each element to note that the 
	 * element should not be considered dirty at this point.</LI>
	 * </UL>
	 */
	public void postDeserialize() throws ApplicationException
	{
		if (null == myElements)
			myElements = new ReallyCaselessMap<ApplicationDataElement>();
		
		for (ApplicationDataElement element : myElements.values())
		{
			element.postDeserialize();
		}
		
		setDirty(false);
	}
	
	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
			throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	public void deepCopyData(Object o, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		MultiElementAppData other = (MultiElementAppData) o;
		
		for (E e : myWrapper.values())
		{
			ApplicationDataElement myElement = myElements.get(e);
			ApplicationDataElement otherElement = (ApplicationDataElement) 
				DeepCopyUtil.continueDeepCopy(myElement, map, copyTransients);
			other.setEntry(e.toString(), otherElement);
		}
	}

	protected void initialize()
	{
		if (!myInitialized)
		{
			myInitialized = true;
			myWrapper = getEnumWrapper();
			myElements = new ReallyCaselessMap<ApplicationDataElement>();
			buildEntries();
			setDirty(false);
		}
	}
	
	
	public void copyFrom(MultiElementAppData data) throws ApplicationException
	{
		
		for (E e : myWrapper.values())
		{
			ApplicationDataElement oldElement = get(e);
			ApplicationDataElement newElement = data.get(e);
			oldElement.copyFrom(newElement);
		}
	}

	public void updateFrom(MultiElementAppData newData) throws ApplicationException
	{
		E[] vals = myWrapper.values();
		for (E e : vals)
		{
			ApplicationDataElement oldElement = get(e);
			ApplicationDataElement newElement = newData.get(e);
			oldElement.copyFrom(newElement);
		}
	}

	protected void processDataElement(E e, ApplicationDataElement value)
	{
		setEntry(e.toString(), value);
	}
}
