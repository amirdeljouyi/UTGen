package com.lts.util;

import java.util.Comparator;

/**
 * A Comparator that compares strings modified by ascending/descending and 
 * case sensitivity.
 * 
 * @author cnh
 */
public class StringComparator implements Comparator<String>
{
	private boolean ascending;
	private boolean caseSensitive;
	
	
	public StringComparator (boolean isAscending, boolean isCaseSensitive)
	{
		ascending = isAscending;
		caseSensitive = isCaseSensitive;
	}
	
	
	public int compare(String s1, String s2)
	{
		int result;
		if (caseSensitive)
			result = s1.compareTo(s2);
		else
			result = s1.compareToIgnoreCase(s2);
		
		if (!ascending)
			result = -1 * result;
		
		return result;
	}
	
	
	static public Comparator COMPARATOR_CASELESS = new StringComparator(true, false);
	static public Comparator COMPARATOR = new StringComparator(true, true);
	static public Comparator COMPARATOR_ASCENDING_CASELESS = 
		new StringComparator(false, false);
	static public Comparator COMPARATOR_ASCENDING = 
		new StringComparator(false, true);
}
