package com.lts.caloriecount.data.xml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lts.LTSException;
import com.lts.beans.BeanUtils;
import com.lts.util.CollectionUtils;

/**
 * Convert between XML and Java beans.
 * 
 * @author cnh
 *
 */
public class BeanXml
{
	static final protected String[] SPEC_IGNORED_PROPERTIES = {
		"class",
		"dirty",
		"rectified",
		"entryName"
	};
	
	protected Map<String, String> myIgnoredProperties;
	protected Map<String, BeanPropertyAccessor> myProperties;
	protected BeanInfo myInfo;
	protected String myName;
	protected List<String> myNameList;
	protected Class myBeanClass;
	protected Constructor myConstructor;
	
	public BeanXml(String name, Class beanClass)
	{
		initialize(name, beanClass);
	}
	
	/**
	 * A hook to allow subclasses to easily add additional ignored properties.
	 * 
	 * <P>
	 * By default, this class simply uses the properties in 
	 * {@link #SPEC_IGNORED_PROPERTIES} 
	 * as the list of ignored properties.  Subclasses can ignore additional 
	 * properties by the following example:
	 * </P>
	 * 
	 * <P>
	 * <CODE>
	 * <PRE>
	 * protected List<String> getIgnoredNames()
	 * {
	 *    List<String> list = super.getIgnoredNames();
	 *    CollectionUtils.addAll(list, &lt;string array containing additional names&gt;);
	 * }
	 * </PRE>
	 * </CODE>
	 * </P>
	 * 
	 * @return A list of strings that are the bean properties that are not 
	 * created or read when converting to XML.
	 */
	protected List<String> getIgnoredNames()
	{
		List<String> list = new ArrayList<String>();
		
		CollectionUtils.addAll(list, SPEC_IGNORED_PROPERTIES);
		
		return list;
	}
	
	
	protected void initializeIgnoredProperties()
	{
		myIgnoredProperties = new HashMap<String, String>();
		List<String> list = getIgnoredNames();
		for (String name : list)
		{
			name = name.toLowerCase();
			myIgnoredProperties.put(name, name);
		}
	}
	
	
	protected void initialize(String name, Class beanClass)
	{
		myBeanClass = beanClass;
		myInfo = getBeanInfo(beanClass);
		initializeIgnoredProperties();
		initializeProperties(myInfo);
		initializePropertyNameList(myProperties.keySet());
		initializeConstructor(beanClass);
		myName = name;
	}

	protected void initializeConstructor(Class beanClass)
	{
		myConstructor = BeanUtils.getDefaultConstructor(beanClass);
	}

	protected void initializePropertyNameList(Set<String> keySet)
	{
		myNameList = new ArrayList<String>(keySet);
		Collections.sort(myNameList);
	}


	protected BeanInfo getBeanInfo (Class beanClass)
	{
		try
		{
			return Introspector.getBeanInfo(beanClass);
		}
		catch (IntrospectionException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	protected void initializeProperties(BeanInfo info)
	{
		myProperties = new HashMap<String, BeanPropertyAccessor>();
		for (PropertyDescriptor descriptor : info.getPropertyDescriptors())
		{
			if (myIgnoredProperties.containsKey(descriptor.getName().toLowerCase()))
				continue;
			
			BeanPropertyAccessor accessor = new BeanPropertyAccessor(descriptor);
			String name = descriptor.getName().toLowerCase();
			myProperties.put(name, accessor);
		}
	}
	
	
	public Element toElement (Document doc, Object bean)
	{
		Element node = doc.createElement(myName);

		for (String name : myNameList)
		{
			BeanPropertyAccessor accessor = myProperties.get(name);
			Element child = accessor.toElement(doc, bean);
			if (null != child)
				node.appendChild(child);
		}
		
		return node;
	}
	
	
	public void populateBean (Object bean, Element tag)
	{
		NodeList nodeList = tag.getChildNodes();
		int count = nodeList.getLength();

		for (int i = 0; i < count; i++)
		{
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			
			Element child = (Element) node;
			String name = child.getNodeName();
			if (myIgnoredProperties.containsKey(name))
				continue;
			
			BeanPropertyAccessor accessor = myProperties.get(child.getNodeName());
			if (null == accessor)
				continue;
			
			accessor.populateProperty(bean, child);
		}
	}

	public Class getBeanClass()
	{
		return myBeanClass;
	}
	
	
	public String getBeanName()
	{
		return myInfo.getBeanDescriptor().getName();
	}

	static private Object[] NO_ARGS = {};
	
	public Object createAndPopulate(Element beanElement) throws LTSException 
	{
		Object bean = BeanUtils.createInstance(myConstructor, NO_ARGS);
		populateBean(bean, beanElement);
		return bean;
	}
}
