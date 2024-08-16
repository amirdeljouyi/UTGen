package com.lts.caloriecount.data.food;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class FoodBeanInfo extends SimpleBeanInfo
{
	public FoodBeanInfo()
	{
		initialize();
	}
	
	protected PropertyDescriptor[] myProperties;
	
	protected void initialize()
	{
		try
		{
			buildCaloriesProperty();
		}
		catch (IntrospectionException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected void buildCaloriesProperty() throws IntrospectionException
	{
		myProperties = new PropertyDescriptor[] {
				new PropertyDescriptor("calories", Integer.TYPE),
				new PropertyDescriptor("name", String.class),
				new PropertyDescriptor("description", String.class)
		};
	}
	
	@Override
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return myProperties;
	}
}
