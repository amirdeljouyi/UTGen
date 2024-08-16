package com.lts.util;

import java.util.Comparator;

/**
 * A comparator that calls toString on its objects and then uses StringComparator
 * to compare them.
 * 
 * @author cnh
 *
 */
public class ToStringComparator implements Comparator
{
	private StringComparator myComparator;
	
	public ToStringComparator (boolean ascending, boolean caseSensitive)
	{
		myComparator = new StringComparator(ascending, caseSensitive);
	}
	
	public int compare(Object o1, Object o2)
	{
		String s1 = o1.toString();
		String s2 = o2.toString();
		
		return myComparator.compare(s1, s2);
	}

	static public Comparator CASE_SENSITIVE_ASCENDING = new ToStringComparator(true, true);
	static public Comparator CASELESS_ASCENDING = new ToStringComparator(true, false);
	static public Comparator CASE_SENSITIVE_DESCENDING = new ToStringComparator(false, true);
	static public Comparator CASELESS_DESCENDING = new ToStringComparator(false, false);
}
