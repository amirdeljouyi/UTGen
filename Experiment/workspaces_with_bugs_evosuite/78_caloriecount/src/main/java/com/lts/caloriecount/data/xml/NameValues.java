package com.lts.caloriecount.data.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameValues
{
	protected Map<String, Object> myNameToValue;
	
	
	public NameValues(Map<String, Object> nameValues)
	{
		initialize(nameValues);
	}
	
	public NameValues()
	{
		initialize();
	}
	
	
	public NameValues(String name, String value)
	{
		initialize(name, value);
	}
	
	protected void initialize(String name, String value)
	{
		myNameToValue = new HashMap<String, Object>();
	}
	
	protected void initialize()
	{
		myNameToValue = new HashMap<String, Object>();
	}


	public String getValue(String name)
	{
		Object o = myNameToValue.get(name);
		if (null == o)
			return null;
		else if (o instanceof String)
			return (String) o;
		else if (o instanceof String[])
		{
			String[] strArray = (String[]) o;
			if (strArray.length < 1)
				return null;
			else
				return strArray[0];
		}
		else
			return null;
	}
	
	public void initialize(Map<String, Object> nameValues)
	{
		myNameToValue = nameValues;
	}
	
	public void addValue(String name, String value)
	{
		Object o = myNameToValue.get(name);
		
		if (null == o)
			myNameToValue.put(name, value);
		else if (o instanceof String)
		{
			List list = new ArrayList(2);
			list.add((String) o);
		}
		else if (o instanceof List)
		{
			List list = (List) o;
			list.add(value);
		}
	}
	
	public Collection<String> getNames()
	{
		return myNameToValue.keySet();
	}
}
