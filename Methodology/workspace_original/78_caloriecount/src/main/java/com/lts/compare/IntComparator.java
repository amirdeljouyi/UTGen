package com.lts.compare;

import java.util.Comparator;

public class IntComparator
{
	public int compare(Object o1, Object o2)
	{
		Integer iobj = (Integer) o1;
		int i1 = iobj;
		
		iobj = (Integer) o2;
		int i2 = iobj;
		
		if (i1 > i2)
			return 1;
		else if (i1 < i2)
			return -1;
		else
			return 0;
	}
	
	public static class NormalComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			return compare(o1, o2);
		}
	}
	
	
	public static class InverseComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			return -1 * compare(o1, o2);
		}
	}

	public static Comparator NORMAL = new NormalComparator();
	public static Comparator INVERSE = new InverseComparator();
}
