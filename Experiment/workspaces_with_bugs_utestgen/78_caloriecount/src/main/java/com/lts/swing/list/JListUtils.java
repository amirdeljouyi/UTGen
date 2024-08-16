package com.lts.swing.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;

/**
 * Some utility methods for dealing with JLists.
 * 
 * @author cnh
 */
public class JListUtils
{
	/**
	 * Remove the items specified by the inidicies from the supplied JList.
	 * 
	 * <P>
	 * The problem is that you must delete the items with the largest index first,
	 * otherwise the indicies change, etc.  This method ensures that this is the case.
	 * </P>
	 * 
	 * @param list The list to remove from.
	 * @param inidicies The inidicies of the items to remove.
	 */
	public static void removeIndicies(JList list, int[] inidicies)
	{
		Arrays.sort(inidicies);
		
		for (int i = inidicies.length - 1; i >= 0; i--)
		{
			list.remove(inidicies[i]);
		}
	}
	
	/**
	 * Remove the selected items from the list.
	 * 
	 * @param list The list to act upon.
	 */
	public static void removeSelected (JList list)
	{
		int[] selected = list.getSelectedIndices();
		removeIndicies(list, selected);
	}

	/**
	 * Add all the array elements to the list.
	 * 
	 * @param list The list to modify.
	 * @param values The values to add.
	 */
	public static void addAll(List list, Object[] values)
	{
		if (null == values)
			return;
		
		for (int i = 0; i < values.length; i++)
		{
			list.add(values[i]);
		}
	}
	
	
	public static List getSelectedList(JList jlist)
	{
		List list = new ArrayList();
		Object[] values = jlist.getSelectedValues();
		
		if (null != values)
		{
			for (int i = 0; i < values.length; i++)
			{
				list.add(values[i]);
			}
		}
		
		return list;
	}
	
	
	
}
