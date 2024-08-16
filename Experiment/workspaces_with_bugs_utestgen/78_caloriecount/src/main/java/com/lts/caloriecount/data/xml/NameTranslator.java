package com.lts.caloriecount.data.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * Translate between XML tag names and objects.
 * <H2>Example</H2>
 * <CODE>
 * <PRE>
 * &lt;foo-bar&gt;
 *     &lt;barf&gt;
 *         &lt;name&gt;John&lt;/name&gt;
 *         &lt;age&gt;25&lt;/age&gt;
 *     &lt;/barf&gt;
 * &lt;/foo-bar&gt;
 * </PRE>
 * </CODE>
 * 
 * <P>
 * This class would allow you to define which class to use for the "barf" element.
 * </P>
 * 
 * <H3>Description</H2>
 * To define the desired mappings, create a sub-class that defines the abstract
 * methods and then "install" this class by calling 
 * {@link BeanImportExport#setClassToName}  
 * 
 * @author cnh
 */
abstract public class NameTranslator
{
	abstract protected Object[][] getClassToNameSpec();
	
	protected Map<Class, String> myClassToNameMap;
	protected Map<String, Class> myNameToClassMap;
	
	
	protected void buildMappings()
	{
		myClassToNameMap = new HashMap<Class, String>();
		myNameToClassMap = new HashMap<String, Class>();
		
		Object[][] spec = getClassToNameSpec();
		for (Object[] row : spec)
		{
			Class clazz = (Class) row[0];
			String name = (String) row[1];
			myClassToNameMap.put(clazz, name);
			myNameToClassMap.put(name, clazz);
		}
	}
	
	
	protected void initialize()
	{
		buildMappings();
	}
	
	
	public String getTagName(Object o)
	{
		if (null == o)
			return null;
		
		return myClassToNameMap.get(o.getClass());
	}
	
	
	public Class getClassForName(String s)
	{
		if (null == s)
			return null;
		
		return myNameToClassMap.get(s);
	}
}
