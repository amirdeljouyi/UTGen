package com.lts.caloriecount.data.conversion;

import com.lts.application.ApplicationException;
import com.lts.xml.simple.SimpleElement;

abstract public class CalorieCountConverter
{
	abstract public SimpleElement convert(SimpleElement elem) throws ApplicationException;
}
