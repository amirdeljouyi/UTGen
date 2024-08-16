package com.lts.caloriecount.data.conversion;

import com.lts.application.ApplicationException;
import com.lts.util.DateUtil;
import com.lts.xml.simple.SimpleElement;

public class ConvertOneDotTwoToOneDotThree extends CalorieCountConverter
{

	@Override
	public SimpleElement convert(SimpleElement root) throws ApplicationException
	{
		SimpleElement child = root.query("budget");
		if (null == child)
			return root;
		
		convertBudget(child);
		return root;
	}
	
	public static SimpleElement performConvert(SimpleElement root) throws ApplicationException
	{
		ConvertOneDotTwoToOneDotThree con = new ConvertOneDotTwoToOneDotThree();
		return con.convert(root);
	}

	private void convertBudget(SimpleElement elem)
	{
		SimpleElement child = elem.query("start");
		if (null != child)
			convertTimeOfDay(child);
		
		child = elem.query("end");
		if (null != child)
			convertTimeOfDay(child);
		
		child = elem.query("period");
		if (null != child)
			convertPeriod(child);
		
	}

	private void convertPeriod(SimpleElement child)
	{
		long value = child.getLongValue();
		if (-1 == value)
			return;
		
		String s = DateUtil.secondsToPeriodString(value);
		child.setValue(s);
	}

	private void convertTimeOfDay(SimpleElement child)
	{
		SimpleElement hourChild = child.query("hour");
		SimpleElement minuteChild = child.query("minute");
		
		int hours = 0;
		if (null != hourChild)
			hours = child.getIntValueOfChild("hour");
		
		int minutes = 0;
		if (null != minuteChild)
			minutes = child.getIntValueOfChild("minute");
		
		long time = hours;
		time *= 60;
		time += minutes;
		time *= 60000;
		
		child.getChildren().clear();
		
		String s = DateUtil.secondsToPeriodString(time);
		child.setValue(s);
	}
}
