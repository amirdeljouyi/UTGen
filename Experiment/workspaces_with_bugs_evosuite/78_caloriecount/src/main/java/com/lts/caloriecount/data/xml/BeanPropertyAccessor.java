package com.lts.caloriecount.data.xml;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lts.beans.BeanUtils;
import com.lts.xml.XMLUtils;

/**
 * Simplified access to Java bean properties.
 * 
 * <P>
 * This class allows clients to access properties of a bean using a simplified
 * mechanism, instead of the relatively cumbersome method that the default
 * language allows.
 * </P>
 * 
 * @author cnh
 *
 */
public class BeanPropertyAccessor
{
	protected PropertyDescriptor myDescriptor;
	
	public BeanPropertyAccessor (String name, BeanInfo info)
	{
		initialize(name, info);
	}

	public BeanPropertyAccessor (PropertyDescriptor descriptor)
	{
		initialize(descriptor);
	}

	protected void initialize(PropertyDescriptor descriptor)
	{
		myDescriptor = descriptor;
	}
	
	
	protected void initialize(String name, BeanInfo info)
	{
		for (PropertyDescriptor desc : info.getPropertyDescriptors())
		{
			if (desc.getName().equals(name))
			{
				myDescriptor = desc;
				break;
			}
		}
		
		if (null == myDescriptor)
		{
			String msg = 
				"Descriptor not found: " + name 
				+ " in bean";
			
			throw new IllegalArgumentException(msg);
		}
	}
	
	
	public Element toElement(Document doc, Object bean)
	{
		String value = BeanUtils.toStringValue(bean, myDescriptor);
		if (null == value)
			return null;
		
		Element element = XMLUtils.createElement(doc, myDescriptor.getName(), value);
		return element;
	}
	
	
	public void populateProperty (Object bean, Element node)
	{
		if (null == node)
			return;
		
		String value = XMLUtils.getValue(node);
		if (null == value)
			return;
		
		BeanUtils.setPrimitiveProperty(bean, value, myDescriptor);
	}
}
