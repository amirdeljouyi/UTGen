package com.lts.caloriecount.ui.propertywindow;

import java.util.Comparator;

public class KeyValue
{
	public String key;
	public String value;
	
	public KeyValue (String theKey, String theValue)
	{
		key = theKey;
		value = theValue;
	}
	
	public static Comparator<KeyValue> CASELESS = new Comparator<KeyValue>() {
		@Override
		public int compare(KeyValue o1, KeyValue o2)
		{
			return o1.key.compareToIgnoreCase(o2.key);
		}
		
	};
}
