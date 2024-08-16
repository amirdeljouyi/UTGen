package com.lts.caloriecount.data.conversion;

import com.lts.application.ApplicationException;
import com.lts.xml.simple.SimpleElement;

public class ConvertOneDotZeroToOneDotOne extends CalorieCountConverter
{

	@Override
	public SimpleElement convert(SimpleElement root) throws ApplicationException
	{
		SimpleElement elem = root.query("budget/period");
		if (null != elem)
		{
			double val = elem.getDoubleValue();
			long longVal = (long) val;
			elem.setValue(Long.toString(longVal));
		}
		
		return root;
	}
	
	public static SimpleElement performConvert(SimpleElement root) throws ApplicationException
	{
		ConvertOneDotZeroToOneDotOne con = new ConvertOneDotZeroToOneDotOne();
		return con.convert(root);
	}

}
