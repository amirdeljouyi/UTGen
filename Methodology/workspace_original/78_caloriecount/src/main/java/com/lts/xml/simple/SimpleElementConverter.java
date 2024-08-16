package com.lts.xml.simple;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.lts.util.StringUtils;
import com.lts.xml.simple.ConversionException.ConversionDirection;

/**
 * A class that knows how to convert a (sub)tree of SimpleElement objects to a 
 * corresponding tree of org.w3c.dom.Element objects.
 * 
 * @author cnh
 *
 */
public class SimpleElementConverter
{
	/**
	 * Given a tree of SimpleElment objects rooted with elem, and a Document in which
	 * to build, convert the input tree to a corresponding Element tree.
	 * 
	 * @param elem The tree of SimpleElement objects to convert.
	 * @param doc The Document that is used to create the new elements and attributes.
	 * @return The corresponding Element tree.
	 */
	public Element toElement(SimpleElement elem, Document doc)
	{
		Element root = doc.createElement(elem.getName());
		
		populateAttributes(root, elem);
		populateChildren(root, elem);
		
		return root;
	}

	private void populateChildren(Element root, SimpleElement elem)
	{
		if (elem.getChildren().size() > 0)
		{
			for (SimpleElement child : elem.getChildren())
			{
				Element childElement = toElement(child, root.getOwnerDocument());
				root.appendChild(childElement);
			}
		}
		else
		{
			String value = StringUtils.trim(elem.getValue());
			if (null != value)
			{
				Text textValue = root.getOwnerDocument().createTextNode(value);
				root.appendChild(textValue);
			}
		}
	}

	private void populateAttributes(Element root, SimpleElement elem)
	{
		NamedNodeMap attrs = root.getAttributes();
		
		for (String name : elem.getAttributes().keySet())
		{
			String value = elem.getAttributeValue(name);
			Attr oneAttr = root.getOwnerDocument().createAttribute(name);
			oneAttr.setValue(value);
			attrs.setNamedItem(oneAttr);
		}
	}
	
	
	/**
	 * Convert a tree of org.w3c.dom.Element objects into the equivalent tree made 
	 * up of SimpleElement objects.
	 * 
	 * <P>
	 * A "canonical" tree of SimpleElement objects may not have a single node that has
	 * both a value a non-empty set of children.  If an Element has non-empty text children
	 * and element children, then this method will throw a ConversionException.    
	 * </P>
	 * 
	 * @param root The tree to convert.
	 * @return The equivalent tree of SimpleElement objects.
	 */
	public SimpleElement toSimpleElement(Element root) throws ConversionException
	{
		SimpleElement rootElement = new SimpleElement();
		
		String name = StringUtils.trim(root.getNodeName());
		if (null != name)
			rootElement.setName(name);
		
		popSimpleAttributes(root, rootElement);
		popSimpleChildren(root, rootElement);
		
		return rootElement;
	}

	/**
	 * Add children or text to the element.
	 * 
	 * <P>
	 * Note that this class will only allow an element to be either a branch or a leaf.
	 * If an OWD element has both text and child elements, then a {@link ConversionException}
	 * is thrown. 
	 * </P>
	 * 
	 * @param root The OWD element to convert.
	 * @param rootElement The SimpleElement to populate.
	 * @throws ConversionException If the node has both text and children or if 
	 * one of the root's children has a problem.
	 */
	private void popSimpleChildren(Element root, SimpleElement rootElement) throws ConversionException
	{
		StringBuffer text = new StringBuffer();
		
		NodeList nlist = root.getChildNodes();
		for (int index = 0; index < nlist.getLength(); index++)
		{
			Node child = nlist.item(index);
			switch (child.getNodeType())
			{
				case Node.TEXT_NODE :
				{
					Text tnode = (Text) child;
					String val = StringUtils.trim(tnode.getNodeValue());
					if (null != val)
						text.append(val);
					break;
				}
				
				case Node.ELEMENT_NODE :
				{
					Element enode = (Element) child;
					rootElement.addChild(toSimpleElement(enode));
					break;
				}
			}
		}
		
		String evalue = StringUtils.trim(text.toString());
		if (null != evalue && rootElement.getChildren().size() > 0)
		{
			throw new ConversionException(
					ConversionDirection.FromOwdToSimple,
					root,
					"Element has both text and children"
			);
		}
		
		else if (null != evalue)
		{
			String s = StringUtils.trim(text.toString());
			if (null != s)
				rootElement.setValue(s);
		}
	}

	private void popSimpleAttributes(Element root, SimpleElement rootElement)
	{
		NamedNodeMap attrs = root.getAttributes();
		for (int index = 0; index < attrs.getLength(); index++)
		{
			Attr attr = (Attr) attrs.item(index);
			String aname = attr.getName();
			String avalue = attr.getNodeValue();
			rootElement.setAttribute(aname, avalue);
		}
	}
}
