package com.lts.util.notifyinglist.sorted;

import java.util.Comparator;
import java.util.List;

public class IndirectComparator implements Comparator
{
	public List myList;
	public Comparator myBasicComparator;
	
	public IndirectComparator()
	{}
	
	
	public IndirectComparator(Comparator comp, List list)
	{
		myBasicComparator = comp;
		myList = list;
	}
	
	public Object getValue(int index)
	{
		return myList.get(index);
	}
	
	
	@Override
	public int compare(Object o1, Object o2)
	{
		Integer i1 = (Integer) o1;
		Integer i2 = (Integer) o2;
		Object v1 = getValue(i1);
		Object v2 = getValue(i2);
		return myBasicComparator.compare(v1, v2);
	}
}
