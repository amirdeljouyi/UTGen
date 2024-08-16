package com.lts.caloriecount.data.conversion;

import com.lts.application.ApplicationException;
import com.lts.xml.simple.SimpleElement;

public class ConvertOneDotOneToOneDotTwo extends CalorieCountConverter
{

	@Override
	public SimpleElement convert(SimpleElement elem) throws ApplicationException
	{
		convertMealList(elem);
		return elem;
	}
	
	public static SimpleElement performConvert(SimpleElement elem) throws ApplicationException
	{
		ConvertOneDotOneToOneDotTwo con = new ConvertOneDotOneToOneDotTwo();
		return con.convert(elem);
	}

	private void convertMealList(SimpleElement elem)
	{
		SimpleElement child = elem.query("mealList");
		if (null != child)
			child.setName("entries");
		
		child = elem.query("meals");
		if (null != child)
			child.setName("entries");
		
		addMealAmounts(elem);
	}

	/**
	 * Add calorie amounts to meals.
	 * 
	 * <P>
	 * 1.1 data did not give an amount for a meal.  Instead, it depended on the Food
	 * entry to supply that value.  The thing is, the calories in a food can be changed via
	 * editing.  It is not clear to me whether or not meals should use the amount that 
	 * a food is now thought to have or whether a meal should use the original amount.
	 * </P>
	 * 
	 * <P>
	 * If one relies on the food object, then the calories consumed for a day can change
	 * depending on whether the food itself is changed.
	 * </P>
	 * 
	 * <P>
	 * If meals amounts are independent of foods, then you can get in the situation where
	 * a meal can be listed as having a different amount of calories than the food it uses,
	 * which is somewhat strange.
	 * </P>
	 * 
	 * @param elem
	 */
	private void addMealAmounts(SimpleElement elem)
	{
	}

}
