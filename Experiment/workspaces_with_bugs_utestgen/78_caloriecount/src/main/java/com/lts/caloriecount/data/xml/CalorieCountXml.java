package com.lts.caloriecount.data.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.application.data.IdApplicationDataList;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.CalorieCountDataElements;
import com.lts.caloriecount.data.budget.Budget;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.caloriecount.data.frequent.FrequentFoodList;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.caloriecount.data.meal.MealList;

public class CalorieCountXml extends ApplicationXmlData
{
	protected static Object[][] SPEC_BEAN_XML = {
		{ "food", Food.class },
		{ "meal", Meal.class },
		{ "budget", Budget.class },
		{ "frequent", FrequentFood.class }
	};
	
	
	public CalorieCountXml()
	{
		initialize();
	}
	
	
	
	
	protected Element beanToElement (Document doc, Object bean)
	{
		BeanXml beanXml = myClassToBeanXml.get(bean.getClass());
		Element beanElement = beanXml.toElement(doc, bean);
		return beanElement;
	}
	
	
	public Element toElement (Document doc, CalorieCountData data) throws LTSException
	{
		Element parent = doc.createElement("calorie-count-data");
		Element child;
		
		child = processList(doc, "foods", data.getFoods());
		parent.appendChild(child);
		
		child = processList(doc, "meals", data.getMeals());
		parent.appendChild(child);
		
		child = beanToElement(doc, data.getBudget());
		parent.appendChild(child);
		
		child = processList(doc, "frequently-selected", data.getFrequentFoods());
		parent.appendChild(child);
		
		return parent;
	}
	
	
	
	
	
	protected CalorieCountDataElements toCalorieCountDataElement (String name)
	{
		try
		{
			return CalorieCountDataElements.valueOf(name);
		}
		catch (IllegalArgumentException e)
		{
			return null;
		}
	}
	
	protected void processRootChild(CalorieCountData data, Element element) throws LTSException
	{
		String name = element.getNodeName();
		CalorieCountDataElements dataElement = toCalorieCountDataElement(name);
		if (null == dataElement)
			return;
		
		switch (dataElement)
		{
			case Budget :
			{
				Budget budget = new Budget();
				BeanXml beanXml = myNameToBeanXml.get(name);
				beanXml.populateBean(budget, element);
				data.setBudget(budget);
				break;
			}
			
			case Food :
			{
				FoodList foodList = new FoodList();
				populateList(foodList, element);
				data.setFoods(foodList);
				break;
			}
			
			case Frequent :
			{
				IdApplicationDataList<FrequentFood> list = new FrequentFoodList();
				populateList(list, element);
				data.setFrequentFoods(list);
				break;
			}
			
			case Meal :
			{
				IdApplicationDataList<Meal> list = new MealList();
				populateList(list, element);
				data.setMeals(list);
				break;
			}
		}
	}


	protected Object elementToBean (Element beanElement) throws LTSException
	{
		String name = beanElement.getNodeName().toLowerCase();
		BeanXml beanXml = myNameToBeanXml.get(name);
		return beanXml.createAndPopulate(beanElement);
	}




	@Override
	protected Object[][] getBeanXmlSpec()
	{
		return SPEC_BEAN_XML;
	}
	
	
//	protected Object foo()
//	{
//		NodeList nodeList = root.getChildNodes();
//		int count = nodeList.getLength();
//		for (int i = 0; i < count; i++)
//		{
//			Node childNode = nodeList.item(i);
//			if (childNode.getNodeType() != Node.ELEMENT_NODE)
//				continue;
//			
//			Element childElement = (Element) childNode;
//			processElement(data, childElement);
//		}
//		
//		return data;
//	}


}
