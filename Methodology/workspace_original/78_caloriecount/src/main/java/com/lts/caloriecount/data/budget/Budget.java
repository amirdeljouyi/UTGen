package com.lts.caloriecount.data.budget;

import java.text.SimpleDateFormat;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.ApplicationDataElementAdaptor;
import com.lts.bean.xml.annotations.IgnoreProperties;
import com.lts.bean.xml.annotations.IgnoreProperty;
import com.lts.util.DateUtil;
import com.lts.xml.simple.SimpleElement;

@IgnoreProperties({"dirty", "rectified"})
public class Budget extends ApplicationDataElementAdaptor
{
	private static final String TAG_START = "start";
	private static final String TAG_PERIOD = "period";
	private static final String TAG_END = "end";
	private static final String TAG_CALORIES_PER_HOUR = "caloriesPerHour";

	private static final long serialVersionUID = 1L;

	public static final String TAG_BUDGET = "budget";

	private double myCaloriesPerHour;
	private TimeOfDay myStartOfDay;
	private TimeOfDay myEndOfDay;
	private long myInterval;
	
	
	public long getInterval()
	{
		return myInterval;
	}
	
	public void setInterval(long interval)
	{
		myInterval = interval;
	}
	
	
	public double getCaloriesPerHour()
	{
		return myCaloriesPerHour;
	}
	
	public void setCaloriesPerHour(double caloriesPerHour)
	{
		myCaloriesPerHour = caloriesPerHour;
	}
	
	
	public Budget()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		try
		{
			postDeserialize();
		}
		catch (ApplicationException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public void postDeserialize () throws ApplicationException
	{
		if (null == myStartOfDay)
			myStartOfDay = new TimeOfDay();
		
		if (null == myEndOfDay)
			myEndOfDay = new TimeOfDay();
		
		super.postDeserialize();
	}
	
	
	public int getDailyAmount()
	{
		double hours = (double) myEndOfDay.getHour() - myStartOfDay.getHour();
		double minutes = (double) myEndOfDay.getMinute() - myStartOfDay.getMinute();
		hours = hours + minutes/60;
		
		double amt = ((double) myCaloriesPerHour) * hours;
		
		return (int) amt;
	}
	
	@IgnoreProperty
	public int getBudgetUpToNow ()
	{
		TimeOfDay now = new TimeOfDay(System.currentTimeMillis());
		return getBudgetFromTo(myStartOfDay, now);
	}

	private int getBudgetFromTo(TimeOfDay start, TimeOfDay stop)
	{
		double minutes = stop.minutesSince(start);
		double hours = minutes/60;
		
		
		int amt = 0;
		if (myCaloriesPerHour > 1.0 && hours > 1.0)
		{
			amt = (int) (myCaloriesPerHour * hours);
		}

		return amt;
	}


	public void setStartOfDay(TimeOfDay start)
	{
		myStartOfDay = start;
	}
	
	public TimeOfDay getStartOfDay()
	{
		return myStartOfDay;
	}
	
	public TimeOfDay getEndOfDay()
	{
		return myEndOfDay;
	}
	
	public void setEndOfDay(TimeOfDay endOfDay)
	{
		myEndOfDay = endOfDay;
	}
	
	
	public void copyFrom (ApplicationDataElement element) throws ApplicationException
	{
		Budget other = (Budget) element;
		
		myCaloriesPerHour = other.myCaloriesPerHour;
		myEndOfDay = other.myEndOfDay;
		myStartOfDay = other.myStartOfDay;
		myInterval = other.myInterval;
		
		super.copyFrom(element);
	}

	public void replaceWith(Budget budget)
	{
		myCaloriesPerHour = budget.myCaloriesPerHour;
		myEndOfDay = budget.myEndOfDay;
		myInterval = budget.myInterval;
		myStartOfDay = budget.myStartOfDay;
	}

	public void populateFromElement(SimpleElement root)
	{
		setCaloriesPerHour(root.getDoubleValueOfChild("caloriesPerHour"));
		setDirty(false);
		
		TimeOfDay tod = new TimeOfDay();
		SimpleElement child = root.nameToChild(TAG_END);
		if (null != child)
			tod.populateFromElement(child);		
		setEndOfDay(tod);
		
		child = root.nameToChild(TAG_PERIOD);
		if (null != child)
		{
			String s = child.getValue();
			long value = DateUtil.periodStringToSeconds(s);
			if (-1 != value)
				setInterval(value);
		}
		
		tod = new TimeOfDay();
		child = root.nameToChild(TAG_START);
		if (null != child)
			tod.populateFromElement(child);
		setStartOfDay(tod);
		
		double cph = root.getDoubleValueOfChild(TAG_CALORIES_PER_HOUR,true);
		setCaloriesPerHour(cph);
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		populateFromElement(elem);
	}

	public static final SimpleDateFormat ourFormat = 
		new SimpleDateFormat("HH:mm:ss.SSS");
	
	
	@Override
	public void serializeTo(SimpleElement elem)
	{
		elem.createChild(TAG_END, getEndOfDay());
		elem.createChild(TAG_PERIOD, DateUtil.secondsToPeriodString(getInterval()));
		elem.createChild(TAG_START, getStartOfDay());
		elem.createChild(TAG_CALORIES_PER_HOUR, getCaloriesPerHour());
	}

	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement elem = new SimpleElement(TAG_BUDGET);
		serializeTo(elem);
		return elem;
	}

	@Override
	protected String getSerializationName()
	{
		return TAG_BUDGET;
	}
}
