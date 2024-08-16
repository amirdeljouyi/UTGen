package com.lts.xml.simple;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lts.caloriecount.data.SimpleSerialization;
import com.lts.util.ReallyCaselessMap;

public class SimpleElement
{
	protected SimpleElement myParent;
	protected String myName;
	protected String myValue;
	protected ReallyCaselessMap<String> myAttributes = new ReallyCaselessMap<String>();
	protected List<SimpleElement> myChildren = new ArrayList<SimpleElement>();
	protected ReallyCaselessMap<SimpleElement> myNameToChild = null;
	
	public SimpleElement ()
	{}
	
	
	public SimpleElement(String name)
	{
		initialize(name);
	}
	
	protected void initialize(String name)
	{
		myName = name;
	}
	
	
	public String getName()
	{
		return myName;
	}

	public void setName(String name)
	{
		String old = myName;
		myName = name;
		
		if (null != myParent)
			myParent.childNameChanged(old, this);
	}

	private void childNameChanged(String old, SimpleElement child)
	{
		myNameToChild.remove(old);
		myNameToChild.put(child.getName(), child);
	}


	public String getValue()
	{
		return myValue;
	}

	public void setValue(String value)
	{
		myValue = value;
	}

	public ReallyCaselessMap<String> getAttributes()
	{
		return myAttributes;
	}

	public void setAttributes(Map<String, String> attributes)
	{
		ReallyCaselessMap<String> map = new ReallyCaselessMap<String>();
		map.putAll(attributes);
		myAttributes = map;
	}
	
	public String getAttributeValue(String name)
	{
		return myAttributes.get(name);
	}
	
	
	public void setAttributeValue(String name, String value)
	{
		myAttributes.put(name, value);
	}
	
	
	public static String stringOrNull (String s)
	{
		if (null == s)
			return "<null>";
		else
			return s;
	}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(stringOrNull(myName));
		appendAttributes(sb);
		
		if (null != myValue)
		{
			sb.append(" ");
			sb.append(myValue);
		}
		
		return sb.toString();
	}

	private void appendAttributes(StringBuffer sb)
	{
		String[] anames = myAttributes.keySet().toArray(new String[0]);
		if (anames.length > 0)
		{
			sb.append("[");
			
			boolean first = true;
			for (String name : anames)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					sb.append(", ");
				}
				
				sb.append(stringOrNull(name));
				sb.append("=");
				String value = myAttributes.get(name);
				sb.append(stringOrNull(value));
			}
			
			sb.append("]");
		}
	}


	public void setChildren(List<SimpleElement> childElements)
	{
		if (null == childElements)
			childElements = new ArrayList<SimpleElement>();
		
		myChildren = childElements;
	}
	
	public List<SimpleElement> getChildren()
	{
		return myChildren;
	}


	public int getIntValue()
	{
		String s = getValue();
		if (null == s)
			return -1;
		else
			return Integer.parseInt(s);
	}
	
	
	public int getIntValueOfChild(String name)
	{
		SimpleElement child = nameToChild(name);
		if (null == child)
			return -1;
		else
			return child.getIntValue();
	}
	
	public long getLongValueOfChild(String name)
	{
		SimpleElement child = nameToChild(name);
		if (null == child)
			return -1;
		else
			return child.getLongValue();
	}
	
	public long getTimeValueOfChild(String name)
	{
		SimpleElement child = nameToChild(name);
		if (null == child)
			return -1;
		else
			return child.getTimeValue();
	}
	
	public SimpleElement nameToChild(String name)
	{
		SimpleElement child = null;
		
		if (null == myNameToChild)
		{
			myNameToChild = new ReallyCaselessMap<SimpleElement>();
			for (SimpleElement elem : myChildren)
			{
				myNameToChild.put(elem.getName(), elem);
			}
		}
		
		child = myNameToChild.get(name);
		return child;
	}


	public void setValue(int value)
	{
		setValue(Integer.toString(value));
	}
	
	public void setValue(long value)
	{
		setValue(Long.toString(value));
	}
	
	public long getLongValue(boolean forgiving)
	{
		String s = getValue();
		if (null == s)
			return -1;
		
		try
		{
			return Long.parseLong(s);
		}
		catch (NumberFormatException e)
		{
			if (forgiving)
				return -1;
			else
				throw e;
		}
	}
	
	public long getLongValue()
	{
		return getLongValue(false);
	}
	
	
	public static SimpleDateFormat ourFormat =
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss.SSS");
	
	public static SimpleDateFormat ourOldFormat = 
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	public long getTimeValue()
	{
		String s = getValue();
		
		if (null == s)
			return -1;
		
		try
		{
			return ourFormat.parse(s).getTime();
		}
		catch (ParseException e)
		{
			try
			{
				return ourOldFormat.parse(s).getTime();
			}
			catch (ParseException ex)
			{
				return -1;
			}
		}
	}
	
	public static String toDateTimeValue(long time)
	{
		return ourFormat.format(time);
	}
	
	public void setTimeValue(long time)
	{
		String s = ourFormat.format(time);
		setValue(s);
	}


	public String getValueOfChild(String name)
	{
		SimpleElement child = nameToChild(name);
		if (null == child)
			return null;
		else
			return child.getValue();
	}


	public SimpleElement(String name, int ival)
	{
		setName(name);
		String val = Integer.toString(ival);
		setValue(val);
	}


	public SimpleElement(String name, String value)
	{
		setName(name);
		setValue(value);
	}


	public SimpleElement(String name, double value)
	{
		setName(name);
		setValue(value);
	}
	
	public SimpleElement(String name, long value)
	{
		setName(name);
		setValue(value);
	}


	public void setValue(double value)
	{
		String s = Double.toString(value);
		setValue(s);
	}


	public void addChild(SimpleElement child)
	{
		myChildren.add(child);
		myNameToChild = null;
		child.setParent(this);
	}


	public double getDoubleValueOfChild(String name)
	{
		SimpleElement child = nameToChild(name);
		if (null == child)
		{
			return 0;
		}
		else
		{
			return child.getDoubleValue();
		}
	}


	public double getDoubleValue()
	{
		String s = getValue();
		
		if (null == s)
			return 0;
		else
		{
			return Double.parseDouble(s);
		}
	}


	public List<SimpleElement> getChildrenNamed(String name)
	{
		List<SimpleElement> list = new ArrayList<SimpleElement>();
		
		for (SimpleElement child : getChildren())
		{
			if (child.getName().equalsIgnoreCase(name))
				list.add(child);
		}
		
		
		return list;
	}
	
	
	public void setAttribute(String name, String value)
	{
		setAttributeValue(name, value);
	}


	public SimpleElement createChild(String name, SimpleSerialization simpleSer)
	{
		SimpleElement child = new SimpleElement(name);
		simpleSer.serializeTo(child);
		addChild(child);
		return child;
	}


	public SimpleElement createChild(String name, int value)
	{
		SimpleElement child = new SimpleElement(name, value);
		addChild(child);
		return child;
	}
	
	public SimpleElement createChild(String name, long interval)
	{
		SimpleElement child = new SimpleElement(name, interval);
		addChild(child);
		return child;
	}


	public SimpleElement createChild(String name, double value)
	{
		SimpleElement child = new SimpleElement(name, value);
		addChild(child);
		return child;
	}


	public double getDoubleValueOfChild(String name, boolean forgiving)
	{
		SimpleElement child = nameToChild(name);
		if (null != child)
			return child.getDoubleValue();
		else if (forgiving)
			return 0;
		else
		{
			throw new RuntimeException("missing child " + name);
		}
	}


	public int getIntValueOfChild(String name, boolean forgiving)
	{
		SimpleElement child = nameToChild(name);
		if (null != child)
			return child.getIntValue();
		else if (forgiving)
			return 0;
		else 
		{
			throw new RuntimeException("missing child" + name);
		}
	}


	public String getValueOfChild(String name, boolean forgiving)
	{
		SimpleElement child = nameToChild(name);
		if (null != child)
		{
			return child.getValue();
		}
		else if (forgiving)
		{
			return null;
		}
		else 
		{
			throw new RuntimeException("missing child " + name);
		}
	}


	public SimpleElement createChild(String name, String value)
	{
		SimpleElement child = new SimpleElement(name, value);
		addChild(child);
		return child;
	}


	public long getTimeValueOfChild(String name, boolean forgiving)
	{
		SimpleElement child = nameToChild(name);
		if (null != child)
		{
			return child.getTimeValue();
		}
		else if (forgiving)
		{
			return 0;
		}
		else
		{
			throw new RuntimeException("missing child " + name);
		}
	}


	public Integer getObjectIntValueOfChild(String name)
	{
		SimpleElement child = nameToChild(name);
		if (null == child)
		{
			return null;
		}
		else
		{
			return child.getIntValue();
		}
	}


	public void setTimeAttribute(String name, long time)
	{
		String s = toDateTimeValue(time);
		setAttribute(name, s);
	}


	public SimpleElement query(String query)
	{
		SimpleElement result = this;
		
		query = query.toLowerCase();
		
		String[] fields = query.split("/");
		int index = 0;
		while (null != result && index < fields.length)
		{
			String field = fields[index];
			result = result.nameToChild(field);
			index++;
		}
		
		return result;
	}


	public SimpleElement getParent()
	{
		return myParent;
	}


	public void setParent(SimpleElement parent)
	{
		myParent = parent;
	}


	public void removeChild(SimpleElement node)
	{
		if (node == null)
			return;
		
		getChildren().remove(node);
	}
}
