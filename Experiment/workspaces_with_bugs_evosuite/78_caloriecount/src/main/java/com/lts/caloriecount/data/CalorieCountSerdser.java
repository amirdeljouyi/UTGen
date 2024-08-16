package com.lts.caloriecount.data;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.caloriecount.data.conversion.ConvertOneDotOneToOneDotTwo;
import com.lts.caloriecount.data.conversion.ConvertOneDotTwoToOneDotThree;
import com.lts.caloriecount.data.conversion.ConvertOneDotZeroToOneDotOne;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.lang.reflect.ReflectionUtils;
import com.lts.xml.XMLUtils;
import com.lts.xml.simple.ConversionException;
import com.lts.xml.simple.SimpleElement;
import com.lts.xml.simple.SimpleElementConverter;
import com.lts.xml.simple.SimpleElementParser;

public class CalorieCountSerdser
{
	private static CalorieCountSerdser ourInstance =
		new CalorieCountSerdser();
	
	private static Map<Class, Method> ourClassToPopulateMethod =
		new HashMap<Class, Method>();
	protected List<String> myWarnings = new ArrayList<String>();
	
	public static CalorieCountSerdser getInstance()
	{
		return ourInstance;
	}
	
	
	public void logWarning(String msg)
	{
		myWarnings.add(msg);
	}
	
	
	public SimpleElement serializeCalorieCount (CalorieCountData data)
	{
		return data.createSerializationElement();
	}
	
	
	public CalorieCountSerdser()
	{
	}
	
	
	public CalorieCountData deserializeCalorieCount(SimpleElement elem) throws ApplicationException
	{
		CalorieCountData data = new CalorieCountData();
		
		try
		{
			int[] version = buildVersion(elem.getAttributeValue("version"));
			elem = convertData(version, elem);
			data.deserializeFrom(elem);
		}
		catch (RuntimeException e)
		{
			Application.showException(e);
		}
		
		return data;
	}


	private SimpleElement convertData(int[] version, SimpleElement elem) 
		throws ApplicationException
	{
		SimpleElement root = elem;
		
		if (version.length < 1)
		{
			String msg = "The version field has no value!";
			throw new ApplicationException(msg);
		}
		
		
		int val = version[0];
		
		switch (val)
		{
			case 0 :
			case 1 :
				if (version.length < 2)
					val = 0;
				else
					val = version[1];
				
				switch (val)
				{
					case 0 :
						root = ConvertOneDotZeroToOneDotOne.performConvert(root);
						root = ConvertOneDotOneToOneDotTwo.performConvert(root);
						root = ConvertOneDotTwoToOneDotThree.performConvert(root);
						break;
						
					case 1 :
						root = ConvertOneDotOneToOneDotTwo.performConvert(root);
						root = ConvertOneDotTwoToOneDotThree.performConvert(root);
						break;
						
					case 2 :
						root = ConvertOneDotTwoToOneDotThree.performConvert(root);
						break;
					
					case 3 :
						break;
						
					default :
					{
						warningNewerVersion(version);
						break;
					}
						
				}
				break;
				
			default :
				warningNewerVersion(version);
				break;
		}
		
		
		return root;
	}


	private void warningNewerVersion(int[] version)
	{
		String msg = "datafile version: " 
			+ versionToString(version)
			+ ", is newer than this version of the system knows "
			+ "about!";
		
		logWarning(msg);
	}


	private String versionToString(int[] version)
	{
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < version.length; i++)
		{
			if (i > 0)
				sb.append(".");
			
			sb.append(version[i]);
		}
		
		return sb.toString();
	}




	private int[] buildVersion(String s)
	{
		if (null == s)
			return null;
		
		String[] fields = s.split("\\.");
		int[] version = new int[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			version[i] = Integer.parseInt(fields[i]);
		}
		
		return version;
	}

	public CalorieCountData deserializeCalorieCount(File file) throws ApplicationException
	{
		try
		{
			Document doc = XMLUtils.readDocument(file);
			SimpleElementConverter sec = new SimpleElementConverter();
			SimpleElement root = sec.toSimpleElement(doc.getDocumentElement());
			
			CalorieCountSerdser ccs = new CalorieCountSerdser();
			return ccs.deserializeCalorieCount(root);
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (LTSException e)
		{
			String msg = "Error reading " + file;
			throw new ApplicationException(msg, e);
		}
		catch (ConversionException e)
		{
			String msg = "Error converting " + file;
			throw new ApplicationException(msg, e);
		}
	}
	
	
	public FoodList deserializeFoods(File file) throws ApplicationException
	{
		try
		{
			Document doc = XMLUtils.readDocument(file);
			
			SimpleElementParser parser = new SimpleElementParser();
			parser.parse(doc);
			SimpleElement root = parser.getRoot().nameToChild("foods");
			FoodList list = new FoodList();
			list.deserializeFrom(root);
			return list;
		}
		catch (LTSException e)
		{
			String msg = "Error trying to load food list from file " + file;
			throw new ApplicationException(msg, e);
		}
	}


	public void serializeCalorieCount(File file, CalorieCountData data) throws ApplicationException
	{
		try
		{
			SimpleElement root = serializeCalorieCount(data);
			SimpleElementConverter conv = new SimpleElementConverter();
			Document doc = XMLUtils.createDocument();
			Element rootElement = conv.toElement(root, doc);
			doc.appendChild(rootElement);
			XMLUtils.writeDocument(file, doc);
		}
		catch (LTSException e)
		{
			String msg =
				"Error trying to save data to " + file;
			throw new ApplicationException(msg,e);
		}
	}
	
	
	protected static Method getPopulateMethod(Class clazz)
	{
		Method method = ourClassToPopulateMethod.get(clazz);
		
		if (null == method)
		{
			method = ReflectionUtils.findMethod(clazz, "populateFromElement");
			ourClassToPopulateMethod.put(clazz, method);
		}
		
		return method;
	}

}
