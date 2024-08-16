/**
 * 
 */
package com.lts.caloriecount.data.xml;

import org.w3c.dom.Element;

abstract class XmlBeanSerializer
{
	abstract public Element toElement(Object o);
	abstract public void loadXml(Element element);
}