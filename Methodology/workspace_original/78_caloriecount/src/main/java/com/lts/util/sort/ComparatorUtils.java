package com.lts.util.sort;


public class ComparatorUtils
{
	public static int compare (long l1, long l2)
	{
		if (l1 > l2)
			return 1;
		else if (l1 < l2)
			return -1;
		else
			return 0;
	}
	
	public static int inverse (long l1, long l2)
	{
		return -1 * compare(l1, l2);
	}
	
	
	public static int compare (int i1, int i2)
	{
		if (i1 > i2)
			return 1;
		else if (i1 < i2)
			return -1;
		else
			return 0;
	}
	
	
	public static int inverse (int i1, int i2)
	{
		return -1 * compare(i1, i2);
	}
}
