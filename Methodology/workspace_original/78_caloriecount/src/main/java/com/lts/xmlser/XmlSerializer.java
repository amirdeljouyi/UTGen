//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.xmlser;

import java.io.InvalidObjectException;
import java.io.NotSerializableException;
import java.io.ObjectInputValidation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.io.IndentingPrintWriter;
import com.lts.lang.reflect.ReflectionUtils;
import com.lts.util.CollectionUtils;
import com.lts.xmlser.tags.ArrayTag;
import com.lts.xmlser.tags.BooleanTag;
import com.lts.xmlser.tags.ByteTag;
import com.lts.xmlser.tags.CharTag;
import com.lts.xmlser.tags.CollectionTag;
import com.lts.xmlser.tags.DateTag;
import com.lts.xmlser.tags.DoubleTag;
import com.lts.xmlser.tags.FloatTag;
import com.lts.xmlser.tags.IntegerTag;
import com.lts.xmlser.tags.LongTag;
import com.lts.xmlser.tags.MapTag;
import com.lts.xmlser.tags.NullTag;
import com.lts.xmlser.tags.ObjectTag;
import com.lts.xmlser.tags.ReferenceTag;
import com.lts.xmlser.tags.ShortTag;
import com.lts.xmlser.tags.SimpleTag;
import com.lts.xmlser.tags.StringSerializedTag;
import com.lts.xmlser.tags.StringTag;

/**
 * A class that serializes objects to an XML file.
 * 
 * <P/>
 * This class essentially does the same thing that object serialization does,
 * except that, instead of creating a binary file, it creates an XML file 
 * (text file).  
 * 
 * <P/>
 * The goal is to create a file that makes it reasonably easy to manipulate 
 * a serialized object's data via a text editor or the like.  The file has the
 * form:
 * 
 * <PRE>
 * &lt;xml-serialized>
 *     &lt;root id="0" class="&lt;fully qualified class name>">
 *         &lt;propertyOneName type="&lt;a simple Java type>" value="&lt;string value>"/>
 *         &lt;propertyTwoName id="&lt;integer value>" class="&lt;fully qualified class name>">
 *             properties of the object...
 *         &lt;/propertyTwoName>
 *         &lt;propertyThreeName reference="&lt;previously defined id>" class="whatever"/>
 *     &lt;/root>
 * &lt;/xml-serialized>
 * </PRE>
 * 
 * <P/>
 * Each object definition has the form:
 * 
 * <PRE>
 * &lt;property-name id="unique number" class="fully qualified class name">
 *     &lt;propertyOneName ...>
 *     &lt;propertyTwoName ...>
 *     ...
 * &lt;/property-name>
 * </PRE>
 * 
 * <BR/>
 * Where property-name is the name of the data member used by the containing
 * object for the instance.  If this is the root obect for the serialization,
 * then this will be "root."
 * 
 * <P/>
 * Unique number is a number that is unique across all the objects in the file.
 * If == returns true for two objects, then they should have the same ID.
 * 
 * <P/>
 * Fully qualified class name is a fully qualified name for the class.
 * 
 * <H2>Object Properties</H2>
 * An object property has one of the following forms:
 * <UL>
 * <LI/>simple property
 * <LI/>string property
 * <LI/>object reference
 * <LI/>object definition
 * <LI/>array definition
 * <LI/>collection definition
 * </UL>
 * 
 * Object definitions have the same format as described above and will not 
 * be expounded upon.
 * 
 * <H3>Simple Properties</H3>
 * Simple properties are properties whose type is one of the following:
 * <UL>
 * <LI/>short or java.lang.Short
 * <LI/>byte or java.lang.Byte
 * <LI/>int or java.lang.Integer
 * <LI/>long or java.lang.Long
 * <LI/>char or java.lang.Character
 * <LI/>float or java.lang.Float
 * <LI/>double or java.lang.Double
 * <LI/>java.lang.String
 * </UL>
 * 
 * <P/>
 * For these property types, the XML has the form:
 * 
 * <PRE>
 * &lt;property-name simple="simple type" value="string value"/> 
 * </PRE>
 * 
 * property-name is the name of the property within the containing class.  string 
 * value is the string representation of the property.  In the case where the 
 * property is null, the value is "null".  simple type is one of the following 
 * strings which implies the described type:
 * 
 * <UL>
 * <LI/>byte
 * <LI/>Byte (java.lang.Byte)
 * <LI/>short
 * <LI/>Short (java.lang.Short)
 * <LI/>int
 * <LI/>Integer (java.lang.Integer)
 * <LI/>long
 * <LI/>Long (java.lang.Long)
 * <LI/>float
 * <LI/>Float (java.lang.Float)
 * <LI/>double
 * <LI/>Double (java.lang.Double)
 * </UL>
 * 
 * <H3>String Properties</H3>
 * String properties have the form:
 * 
 * <PRE>
 * &lt;property-name simple="string">the string value&lt;/property-name>
 * </PRE>
 * 
 * The string is trimmed before being read in, so leading and trailing spaces 
 * are ignored.
 * 
 * <H3>Object References</H3>
 * An object reference is a reference to another object.  A null reference
 * has the following form:
 * 
 * <PRE>
 * &lt;property-name null="true"/>
 * </PRE> 
 * 
 * This implies that the property is null.
 * 
 * <P/>
 * A non-null reference has the following form:
 * 
 * <PRE>
 * &lt;property-name reference="id number"/>
 * </PRE>
 * 
 * The id number should match the value of the id attribute of an object definition
 * somewhere else in the file.  The definition can occur before or after the 
 * reference.
 * 
 * <H3>Array Properties</H3>
 * An array property has the following form:
 * 
 * <PRE>
 * &lt;propertyName array="component class" dimensions="number of dimensions">
 *     elements...
 * &lt;/propertyName> 
 * </PRE>
 * 
 * The component class is either a fully qualified class name or one of the
 * "simple type" strings as defined by the simple property type.  Elements 
 * have the same form as properties, except that the name of an element tag
 * is "element" instead of a property name.
 * 
 * <H3>Collection Properties</H3>
 * Collection properties have the following form:
 * 
 * <PRE>
 * &lt;propertyName collection="fully qualified class name">
 *     elements...
 * &lt;/propertyName>
 * </PRE>
 * 
 * The specified class must implement the java.util.Collection interface.
 * The element child tags of a collection function in the same manner as the 
 * child elements of an array property.
 * 
 * <H3>How the Damn Thing Works</H3>
 * Some notes on the approach that the class takes in serializing objects.
 * 
 * <H4>Circular References</H4>
 * When serializing a class, the 
 * 
 * <H4>General Notes</H4>
 * When dealing with a generic class, the serializer gets each of the non-static,
 * non-transient fields of the class, sorts the fields by name, and then 
 * serializes each field value in turn.  The serializer uses a "depth first"
 * approach in that it will try to serialize  
 */
public class XmlSerializer
	implements TagConstants
{
	protected int myNextID = 0;
	
	/**
	 * Should primitive fields be serialized using the &lt;foo>value&lt;/foo>
	 * syntax?
	 * 
	 * <P/>
	 * If this property is true, which is the default setting, then primitive
	 * values will be serialized using the "embedded text node" approach.  For
	 * example: &lt;foo>5&lt;/foo>.  If this property is false, then primitives
	 * will be serialized by using a value attribute.  For example: 
	 * &lt;foo value="5">.
	 * 
	 * <P/>
	 * Note that character and string values are always serialized using a 
	 * text child node.
	 */
	protected boolean myStringSerializePrimitives = true;
	
	/**
	 * Should null values be serialized?
	 * 
	 * <P/>
	 * If this property is true, null fields will be serialized using a tag
	 * like this: &lt;foo null="true"/>.  Otherwise, such fields will not 
	 * appear in the resulting XML.
	 * 
	 * <P/>
	 * By default, null values are not serialized.
	 * 
	 * <P/>
	 * An object with a large number of null values can be confusing to read.
	 * By only serializing fields that have non-null values, the resulting 
	 * XML can be more understandable.
	 */
	protected boolean mySerializeNulls = false;
	
	
	protected boolean myForgiving = false;
	
	public boolean forgiving()
	{
		return myForgiving;
	}
	
	public void setForgiving(boolean forgiving)
	{
		myForgiving = forgiving;
	}
	
	protected Map myIdToObjectMap = new HashMap();
	protected Map myObjectToIdMap = new IdentityHashMap();
	protected Map myClassToResolve = new IdentityHashMap();
	
	protected Map myIdToFixupMap = new HashMap();
	protected List myDeferredFixups = new ArrayList();
	
	public static ArrayTag ARRAY_TAG = new ArrayTag();
	public static BooleanTag BOOLEAN_TAG = new BooleanTag();
	public static ByteTag BYTE_TAG = new ByteTag();
	public static CharTag CHAR_TAG = new CharTag();
	public static CollectionTag COLLECTION_TAG = new CollectionTag();
	public static DateTag DATE_TAG = new DateTag();
	public static DoubleTag DOUBLE_TAG = new DoubleTag();
	public static FloatTag FLOAT_TAG = new FloatTag();
	public static IntegerTag INT_TAG = new IntegerTag();
	public static LongTag LONG_TAG = new LongTag();
	public static NullTag NULL_TAG = new NullTag();
	public static ObjectTag OBJECT_TAG = new ObjectTag();
	public static ShortTag SHORT_TAG = new ShortTag();
	public static StringTag STRING_TAG = new StringTag();
	public static ReferenceTag REFERENCE_TAG = new ReferenceTag();
	public static StringSerializedTag STRING_SERIALIZED_TAG = new StringSerializedTag();
	public static SimpleTag SIMPLE_TAG = new SimpleTag();
	public static MapTag MAP_TAG = new MapTag();
	
	
	public static final Object[] SPEC_STRING_TO_TAG = {
		ArrayTag.STR_TAG_NAME,		ARRAY_TAG,
		BooleanTag.STR_TAG_NAME,	BOOLEAN_TAG,
		Boolean.class.getName(),	BOOLEAN_TAG,
		ByteTag.STR_TAG_NAME,		BYTE_TAG,
		Byte.class.getName(),		BYTE_TAG,
		CharTag.STR_TAG_NAME,		CHAR_TAG,
		Character.class.getName(),	CHAR_TAG,
		CollectionTag.STR_TAG_NAME,	COLLECTION_TAG,
		DateTag.STR_TYPE,			DATE_TAG,
		DoubleTag.STR_TAG_NAME,		DOUBLE_TAG,
		Double.class.getName(),		DOUBLE_TAG,
		FloatTag.STR_TAG_NAME,		FLOAT_TAG,
		Float.class.getName(),		FLOAT_TAG,
		IntegerTag.STR_TAG_NAME,	INT_TAG,
		Integer.class.getName(),	INT_TAG,
		LongTag.STR_TAG_NAME,		LONG_TAG,
		Long.class.getName(),		LONG_TAG,
		NullTag.STR_TAG_NAME,		NULL_TAG,
		ReferenceTag.STR_TAG_NAME,	REFERENCE_TAG,
		ShortTag.STR_TAG_NAME,		SHORT_TAG,
		Short.class.getName(),		SHORT_TAG,
		StringTag.STR_TAG_NAME,		STRING_TAG,
		String.class.getName(),		STRING_TAG,
		BigDecimal.class.getName(),	STRING_SERIALIZED_TAG,
		MapTag.STR_TAG_NAME,		MAP_TAG
	};

	
	public static final Object[] SPEC_SPECIAL_CLASS_TO_TAG = {
		java.util.Date.class,		DATE_TAG,
		java.sql.Date.class,		DATE_TAG,
		java.sql.Timestamp.class,	DATE_TAG,
		java.sql.Time.class,		DATE_TAG,
		BigDecimal.class,			STRING_SERIALIZED_TAG
	};
	
	
	public static final Object[] SPEC_CLASS_TO_TAG = {
		Boolean.TYPE,			BOOLEAN_TAG,
		Boolean.class,			BOOLEAN_TAG,
		Byte.TYPE,				BYTE_TAG,
		Byte.class,				BYTE_TAG,
		Character.TYPE,			CHAR_TAG,
		Character.class,		CHAR_TAG,
		Double.TYPE,			DOUBLE_TAG,
		Double.class,			DOUBLE_TAG,
		Float.TYPE,				FLOAT_TAG,
		Float.class,			FLOAT_TAG,
		Integer.TYPE,			INT_TAG,
		Integer.class,			INT_TAG,
		Long.TYPE,				LONG_TAG,
		Long.class,				LONG_TAG,
		Short.TYPE,				SHORT_TAG,
		Short.class,			SHORT_TAG,
		String.class,			STRING_TAG,
		java.util.Date.class,	DATE_TAG,
		java.sql.Date.class,	DATE_TAG,
		java.sql.Timestamp.class, DATE_TAG,
		java.sql.Time.class,	DATE_TAG,
		BigDecimal.class,		STRING_SERIALIZED_TAG,
		URL.class,				STRING_SERIALIZED_TAG,
		URI.class,				STRING_SERIALIZED_TAG,
		HashMap.class,			MAP_TAG,
		Hashtable.class,		MAP_TAG
	};
	
	/**
	 * Instances of these classes should be serialized via the toString method
	 * and reconstituted via a string constructor.
	 */
	public static Object[] SPEC_STRING_SERIALIZED_CLASSES = {
		BigDecimal.class
	};
	
	public static Map ourClassToTagMap = CollectionUtils.toHashMap(SPEC_CLASS_TO_TAG);
	
	protected static Set ourStringSerializedClasses = 
		CollectionUtils.toIdentityHashSet(SPEC_STRING_SERIALIZED_CLASSES);
	
	protected static Map ourStringToTagMap = 
		CollectionUtils.toHashMap(SPEC_STRING_TO_TAG);
	
	protected static Map ourSpecialClassToTagMap =
		CollectionUtils.toHashMap(SPEC_SPECIAL_CLASS_TO_TAG);

	public static AbstractTag specialClassToTag (Class c)
	{
		return (AbstractTag) ourSpecialClassToTagMap.get(c);
	}
	
	
	public List getDeferredFixups()
	{
		if (null == myDeferredFixups)
			myDeferredFixups = new ArrayList();
		
		return myDeferredFixups;
	}
	
	
	public void reset ()
	{
		myNextID = 0;
		myIdToObjectMap = new HashMap();
		myObjectToIdMap = new IdentityHashMap();
		myIdToFixupMap = new HashMap();
		myDeferredFixups = new ArrayList();
	}
	
	
	public AbstractTag stringToTag (String strType)
	{
		AbstractTag t = (AbstractTag) ourStringToTagMap.get(strType);
		return t;
	}
	
	
	public AbstractTag classToTag (Class c)
	{
		AbstractTag t = (AbstractTag) ourClassToTagMap.get(c);
		return t;
	}
	
	
	public boolean isStringSerialized (Class c)
	{
		return ourStringSerializedClasses.contains(c);
	}
	
	public int addObject (Object o)
	{
		int id = myNextID;
		myNextID++;
		
		myIdToObjectMap.put(new Integer(id), o);
		myObjectToIdMap.put(o, new Integer(id));
		
		return id;
	}
	
	public XmlSerializer ()
	{}
	
	
	public boolean stringSerializePrimitives ()
	{
		return myStringSerializePrimitives;
	}
	
	public void setStringSerializePrimitives (boolean b)
	{
		myStringSerializePrimitives = b;
	}
	
	public boolean getStringSerializePrimitives ()
	{
		return myStringSerializePrimitives;
	}
	
	public boolean serializeNulls ()
	{
		return mySerializeNulls;
	}
	
	public void setSerializeNulls (boolean b)
	{
		mySerializeNulls = b;
	}
	
	public boolean getSerializeNulls()
	{
		return mySerializeNulls;
	}
	
	
	public void printAttribute (
		IndentingPrintWriter out, 
		String name, 
		String value
	)
	{
		out.print (name);
		out.print ("=\"");
		out.print (value);
		out.print ('"');
	}
	
	
	public void printClosingElement (
		IndentingPrintWriter out,
		String name,
		boolean printNewline
	)
	{
		out.print ("</");
		out.print (name);
		out.print ('>');
		
		if (printNewline)
			out.println();
	}
	
	public void printClosingElement (IndentingPrintWriter out, String name)
	{
		printClosingElement(out, name, true);
	}
	
	
	public AbstractTag tagForObject (Object o)
	{
		Class c = null;
		
		if (null != o)
			c = o.getClass();
		
		return tagForObject(o, c);
	}
	
	
	/**
	 * Get a tag object for a given value and an expected class.
	 * 
	 * <P/>
	 * The algorithm for selecting a tag is as follows:
	 * <OL>
	 * <LI/>If the value is null, then the null tag is always used.
	 * <LI/>If the value is already serialized or in the process of being 
	 * serialized, then use a reference to the object instead of trying 
	 * to serialize the object again.
	 * 
	 * <LI/>If a tag has been established for the expected type, then use
	 * that tag.
	 * 
	 * <LI/>If the expected type is an array, then use an array tag.
	 * <LI/>If the expected type is a collection, then use a collection tag.
	 * <LI/>If the value is one of the "simple" types, then us a simple tag.
	 * <LI/>If none of the other cases apply, then use an object tag.
	 * </OL>
	 * 
	 * These rules are applied in the order they are listed; so a value 
	 * of null will always use a null tag, even if the expected type is one 
	 * that class has a specific tag type in mind to use when serializing 
	 * the type.
	 * 
	 * @param value The value for which a tag is desired.
	 * @param expectedType The type that is expected to hold the value.
	 * @return An AbstractTag that can be used to serialize the value.
	 */
	public AbstractTag tagForObject (Object value, Class expectedType)
	{
		AbstractTag t = null;
		
		if (null == value)
			t = NULL_TAG;
		else if (null != objectToId(value))
			t = REFERENCE_TAG;
		else if (null != classToTag(expectedType))
			t = classToTag(expectedType);
		else if (expectedType.isArray())
			t = ARRAY_TAG;
		else if (value instanceof Collection)
			t = COLLECTION_TAG;
		else if (value instanceof Map)
			t = MAP_TAG;
		else if (ReflectionUtils.isSimpleType(value.getClass()))
			t = SIMPLE_TAG;
		else
			t = OBJECT_TAG;
		
		return t;
	}
	
	
	public void printField (
		IndentingPrintWriter out,
		Object value,
		Field theField
	)
		throws LTSException
	{
		AbstractTag t = tagForObject(value, theField.getType());
		t.write(this, out, theField.getName(), value);
	}
	
	
	public void printWhatever (
		IndentingPrintWriter out, 
		Object value,
		String tagname
	)
		throws LTSException
	{
		AbstractTag t = tagForObject(value);
		
		t.write(this, out, tagname, value);
	}
	
	public void printValue (IndentingPrintWriter out, Object value)
		throws LTSException
	{
		AbstractTag t = tagForObject(value);
		t.write(this, out, t.getTagName(value), value);
	}
	
	
	public void printValue (
		IndentingPrintWriter out,
		Object value,
		String tagname,
		boolean printClassName
	)
		throws LTSException
	{
		printComment(out, value);
		AbstractTag t = tagForObject(value);
		if (null == tagname)
			tagname = t.getTagName(value);
		
		t.write(this, out, tagname, value, printClassName);
	}
	
	
	public void printValue (
		IndentingPrintWriter out,
		Object value,
		String tagname
	)
		throws LTSException
	{
		AbstractTag t = tagForObject(value);
		if (null == tagname)
			tagname = t.getTagName(value);
		
		t.write(this, out, tagname, value);
	}
	
	
	public void printObject (
		IndentingPrintWriter out,
		Object value,
		String tagname
	)
		throws LTSException
	{
		printComment(out, value);
		AbstractTag t = OBJECT_TAG;
		if (null == tagname)
			tagname = t.getTagName(value);
		
		t.write(this, out, tagname, value);
	}
	
	
	public void printComment(IndentingPrintWriter out, Object value)
	{
		if (null != value)
		{
			String s = value.toString();
			out.print("<!-- ");
			out.print(s);
			out.println (" -->");
		}
	}

	public void printPrimitive (
		IndentingPrintWriter out,
		Object value,
		String tagname
	)
		throws LTSException
	{
		reset();
		if (null == tagname)
			tagname = STRING_SERIALIZED_TAG.getTagName(value);
		
		STRING_SERIALIZED_TAG.write(this, out, tagname, value);
	}
	
	
	public void writeObject (IndentingPrintWriter out, Object o)
		throws NotSerializableException, LTSException
	{
		reset();
		printValue(out, o);
	}
	
	
	
	
	public boolean hasAttribute (Element node, String name)
	{
		String s = node.getAttribute(name);
		return null != s && !"".equals(s);
	}
	

	public AbstractTag classNameToTag (String cname)
		throws LTSException
	{
		try
		{
			Class c = Class.forName(cname);
			AbstractTag t = classToTag(c);
			if (null == t)
				t = OBJECT_TAG;
			
			return t;
		}
		catch (ClassNotFoundException e)
		{
			throw new LTSException ("Could not load class: " + cname);
		}
	}
	
	
	public AbstractTag elementToTag (Element node, Class type)
		throws LTSException
	{
		AbstractTag t = null;
		
		if (hasAttribute(node, STR_ATTR_NULL))
			t = NULL_TAG;
		else if (hasAttribute(node, STR_ATTR_REFERENCE))
			t = REFERENCE_TAG;
		else if (hasAttribute(node, STR_ATTR_COLLECTION))
			t = COLLECTION_TAG;
		else if (hasAttribute(node, STR_ATTR_ARRAY))
			t = ARRAY_TAG;
		else if (null != type && null != classToTag(type))
			t = classToTag(type);
		else if (hasAttribute(node, STR_ATTR_CLASS))
			t = classNameToTag(node.getAttribute(STR_ATTR_CLASS));
		
		return t;
	}

	public Object readField (Element node, Class type)
		throws LTSException
	{
		AbstractTag t = elementToTag(node, type);
		
		if (null == t)
		{
			throw new LTSException (
				"Could not find tag to deserialize " + node
			);
		}
		
		Object result = t.read(this, node, forgiving());
		
		if (null != result && !(result instanceof Fixup))
		{
			String strid = node.getAttribute(STR_ATTR_ID);
			if (null != strid && !"".equals(strid))
			{
				Integer id = new Integer(strid);
				addObject(id, result);
			}
		}
		
		return result;
	}
	
	
	public Object readValue (Element node)
		throws LTSException
	{
		String s = node.getNodeName();
		AbstractTag t = stringToTag(s);
		if (null == t)
			t = elementToTag(node, null);
		
		if (null == t)
			t = OBJECT_TAG;
		
		return t.read(this, node, forgiving());
	}
	
	
	public Object toObject(Element root)
		throws LTSException
	{
		reset();
		Object o = readValue(root);
		performFixups();
		PostDeserializer.postDeserialize(o);
		return o;
	}

	
	public static final String NAME_READ_OBJECT_XML_RESOLVE = "readObjectXmlResolve";
	public static final Class[] FORMAL_READ_OBJECT_XML_RESOLVE = {};
	public static final Object[] ACTUAL_READ_OBJECT_XML_RESOLVE = {};
	
	public Object readObject (Element root)
		throws LTSException
	{
		Object o = basicReadObject(root);
		if (null == o)
			return null;
		
		invokeResolve(o);
		performFixups();
		return o;
	}

	
	private static Object NULL_OBJECT = new Object();
	
	protected Method findResolveMethod(Class clazz)
	{
		Object o = myClassToResolve.get(clazz);
		if (null == o)
		{
			String name = NAME_READ_OBJECT_XML_RESOLVE;
			Class[] formal = FORMAL_READ_OBJECT_XML_RESOLVE;
			o = ReflectionUtils.findMethod(clazz, name, formal);
			
			if (null == o)
				myClassToResolve.put(clazz, NULL_OBJECT);
			else
				myClassToResolve.put(clazz, o);
		}
		
		if (NULL_OBJECT == o)
			return null;
		else
			return (Method) o;
	}
	
	
	protected void invokeResolve(Object o) throws LTSException
	{
		Class clazz = o.getClass();
		Method method = findResolveMethod(clazz);
		if (null != method)
		{
			try
			{
				method.invoke(o, ACTUAL_READ_OBJECT_XML_RESOLVE);
			}
			catch (RuntimeException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				String msg = "Error deserializing object";
				throw new LTSException(msg,e);
			}
		}
	}

	protected Object basicReadObject(Element root) throws LTSException
	{
		Object o = readValue(root);
		return o;
	}
	
		
	public void addIdFixup (Integer id, Fixup f)
	{
		List l = (List) myIdToFixupMap.get(id);
		if (null == l)
		{
			l = new ArrayList();
			myIdToFixupMap.put(id, l);
		}
				
		l.add(f);
	}
	
	public List idToFixups (Integer id)
	{
		List l = (List) myIdToFixupMap.get(id);
		return l;
	}
	

	public void addDeferredFixup (Fixup f)
	{
		getDeferredFixups().add(f);
	}
	
	
	public void addObject (Integer id, Object o)
		throws LTSException
	{
		myIdToObjectMap.put(id, o);
		
		List l = idToFixups(id);
		if (null != l)
		{
			Iterator i = l.iterator();
			while (i.hasNext())
			{
				Fixup f = (Fixup) i.next();
				if (f.fixupSuccessful(this))
				{
					i.remove();
				}
			}
		}
	}
	
	
	public Object idToObject (Integer id)
	{
		return myIdToObjectMap.get(id);
	}
	
	public Integer objectToId (Object o)
	{
		return (Integer) myObjectToIdMap.get(o);
	}
	
	public void validateGraph () throws InvalidObjectException
	{
		for (Iterator i = myIdToObjectMap.values().iterator(); i.hasNext(); )
		{
			Object o = i.next();
			if (o instanceof ObjectInputValidation)
			{
				ObjectInputValidation oiv = (ObjectInputValidation) o;
				oiv.validateObject();
			}
		}
	}
	
	public void fixupReferences (Object o, List l)
		throws LTSException
	{
		Iterator i = l.iterator();
		while (i.hasNext())
		{
			Fixup f = (Fixup) i.next();
			if (f.fixupSuccessful(this))
				i.remove();
		}
	}
	
	
	public void performFixups ()
		throws LTSException
	{
		List fixupids = new ArrayList(myIdToFixupMap.keySet());
		
		boolean keepFixing = true;
		while (keepFixing)
		{
			keepFixing = false;
			Iterator fixupIterator = fixupids.iterator();
			while (fixupIterator.hasNext())
			{
				Integer id = (Integer) fixupIterator.next();
				Object o = idToObject(id);
				if (null == o)
					continue;
				
				List l = (List) myIdToFixupMap.get(id);
				fixupReferences(o, l);
				
				if (l.size() <= 0)
					fixupIterator.remove();
				
				keepFixing = true;
			}
		}
		
		Iterator i = getDeferredFixups().iterator();
		while (i.hasNext())
		{
			Fixup f = (Fixup) i.next();
			if (!f.fixupSuccessful(this))
			{
				throw new LTSException (
					"Failed to process a deferred fixup: " + f
				);
			}
		}
	}
}