package com.lts.caloriecount.data.xml;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.xml.XMLUtils;

abstract public class ApplicationXmlData
{
	abstract protected Object elementToBean (Element element) throws LTSException;
	abstract protected Element beanToElement(Document doc, Object o) throws LTSException;
	abstract protected Object[][] getBeanXmlSpec();
	
	protected Map<Class, BeanXml> myClassToBeanXml;
	protected Map<String, BeanXml> myNameToBeanXml;

	public ApplicationXmlData()
	{
		super();
	}

	protected void buildBeanXml ()
	{
		myClassToBeanXml = new HashMap<Class, BeanXml>();
		myNameToBeanXml = new HashMap<String, BeanXml>();
		
		Object[][] spec = getBeanXmlSpec();
		for (Object[] row : spec)
		{
			String name = (String) row[0];
			Class clazz = (Class) row[1];
			BeanXml beanXml = new BeanXml(name, clazz);
			myClassToBeanXml.put(beanXml.getBeanClass(), beanXml);
			myNameToBeanXml.put(beanXml.getBeanName().toLowerCase(), beanXml);
		}
	}

	
	protected void initialize()
	{
		buildBeanXml();
	}
	
	
	public Element processList(Document doc, String name, List list) throws LTSException
	{
		Element parent = doc.createElement(name);
		for (Object o : list)
		{
			Element child = beanToElement(doc, o);
			parent.appendChild(child);
		}
		
		return parent;
	}

	public void saveData(File file) throws LTSException
	{
		Document doc = XMLUtils.createDocument();
		Element root = beanToElement(doc, CalorieCount.getData());
		doc.appendChild(root);
		XMLUtils.writeDocument(file, doc);
	}

	public Object loadData(File file) throws LTSException
	{
		Document doc = XMLUtils.readDocument(file);
		Element root = doc.getDocumentElement();
		return elementToBean(root);
	}
		
	protected void populateList(List list, Element listElement) throws LTSException
	{
		List<Element> elements = XMLUtils.getChildElements(listElement);
		for (Element childElement : elements)
		{
			Object o = elementToBean(childElement);
			list.add(o);
		}
	}

}