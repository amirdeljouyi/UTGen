package com.lts.application.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An abstract application data element that is a sequenced collection of 
 * ApplicationData objects.
 * 
 * @author cnh
 *
 */
abstract public class ApplicationListData extends AbstractAppData
{
	abstract public Comparator getComparator();

	protected boolean myAllowDuplicates;
	
	protected List<AbstractAppData> myList;
	
	/**
	 * Add an element to the list.
	 * 
	 * <P>
	 * The method will insert the new element into the list at the sorted position,
	 * as determined by {@link #getComparator()}.  
	 * </P>
	 * 
	 * <P>
	 * The behavior of the method in  the case of a duplicate entry depends on the 
	 * value of {@link #myAllowDuplicates}.  If that value is false, the method throws
	 * a {@link RuntimeException}.  If that value is true, then the element is inserted
	 * at the first location that is not equal to the new element.
	 * </P>
	 * 
	 * @param newElement The element to add.
	 * @return The position where it was added.
	 */
	public int addElement (AbstractAppData newElement)
	{
		int index = Collections.binarySearch(myList, newElement, getComparator());
		if (index > -1 && !myAllowDuplicates)
		{
			throw new IllegalArgumentException("Duplicate entry");
		}

		if (index > -1)
		{
			index = after(newElement, index);
		}
		else
		{
			//
			// If the element is not already in the list, then 
			// Collections.binarySearch returns -(insert point) - 1, ergo the 
			// insertion point is -(return value) - 1
			//
			index = index - 1;
		}
		
		myList.add(index, newElement);
		return index;
	}

	/**
	 * Find the location where a duplicate element should be inserted in the list.
	 * 
	 * <P>
	 * This method scans the list starting at a provided location and looks for the 
	 * first location such that it does not equal the provided key.  That is, look
	 * for the first element after a specific location that is not equals equivalent
	 * to the provided element.
	 * </P>
	 * 
	 * <P>
	 * If the scan hits the end of the list before it finds an elementn that meets the 
	 * criteria, then the method returns the index after the last one in the list.
	 * </P>
	 * 
	 * <P>
	 * In order to call this method and avoid runtime exceptions, the following conditions
	 * must hold:
	 * </P>
	 * 
	 * <UL>
	 * <LI>The list must contain at least one element.</LI>
	 * <LI>index points to an element that is equals equivalent to newElement</LI>
	 * </UL>
	 * 
	 * @param newElement The key to use.
	 * @param index Where to start looking.
	 * @return The first location that is not equal to the newElement parameter.
	 */
	protected int after(AbstractAppData newElement, int index)
	{
		AbstractAppData data = myList.get(index);
		int size = myList.size();
		while (data.equals(newElement) && (1 + index) < size)
		{
			index++;
			data = myList.get(index);
		}
		
		if (data.equals(newElement))
			index++;
		
		return index;
	}

	protected void initialize()
	{
		myList = new ArrayList<AbstractAppData>();
	}
}
