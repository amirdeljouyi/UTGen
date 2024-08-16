package com.lts.caloriecount.data.budget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.ApplicationDataElementAdaptor;
import com.lts.bean.xml.annotations.IgnoreProperties;
import com.lts.caloriecount.data.SimpleSerialization;
import com.lts.util.DateUtil;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.xml.simple.SimpleElement;

/**
 * Time of day, in 24 hour time hours and minutes.
 * 
 * <P>
 * Time being tricky enough to keep track of using a long, this class was created to
 * deal with the situation.
 * </P>
 * 
 * @author cnh
 *
 */
@IgnoreProperties({"dirty"})
public class TimeOfDay extends ApplicationDataElementAdaptor
	implements SimpleSerialization
{
	private static final String TAG_TIME_OF_DAY = "timeOfDay";

	private static final long serialVersionUID = 1L;

	public static String TAG_HOUR = "hour";
	public static String TAG_MINUTE = "minute";
	
	private int myHour;
	private int myMinute;
	
	public TimeOfDay(int hour, int minute)
	{
		myHour = hour;
		myMinute = minute;
	}

	public TimeOfDay()
	{
		myHour = 0;
		myMinute = 0;
	}
	
	
	public TimeOfDay(long time)
	{
		initialize(time);
	}

	protected void initialize(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		myHour = cal.get(Calendar.HOUR_OF_DAY);
		myMinute = cal.get(Calendar.MINUTE);
	}
	
	public int getHour()
	{
		return myHour;
	}
	
	public void setHour(int hour)
	{
		myHour = hour;
	}
	
	
	public int getMinute()
	{
		return myMinute;
	}
	
	public void setMinute(int minute)
	{
		myMinute = minute;
	}
	
	
	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
		TimeOfDay time = (TimeOfDay) copy;
		
		myHour = time.myHour;
		myMinute = time.myMinute;
		
		super.deepCopyData(copy, map, copyTransients);
	}
	
	
	public void copyFrom (ApplicationDataElement element) throws ApplicationException
	{
		TimeOfDay other = (TimeOfDay) element;
		
		myHour = other.myHour;
		myMinute = other.myMinute;
		
		super.copyFrom(element);
	}

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		if (myHour < 10)
			sb.append("0");
		
		sb.append(myHour);
		
		sb.append(":");
		
		if (myMinute < 10)
			sb.append("0");
		
		sb.append(myMinute);
		
		
		return sb.toString();
	}
	
	
	public void now()
	{
		long now = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now);
		myHour = cal.get(Calendar.HOUR_OF_DAY);
		myMinute = cal.get(Calendar.MINUTE);
	}
	
	
	public int minutesSince(TimeOfDay start)
	{
		int hours = myHour - start.myHour;
		int minutes = myMinute - start.myMinute;
		minutes = minutes + (hours * 60);
		
		return minutes;
	}

	public void populateFromElement(SimpleElement elem)
	{
		deserializeFrom(elem);
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		Long value = DateUtil.parseCommonTime(elem.getValue());
		if (null != value)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(value);
			myHour = cal.get(Calendar.HOUR_OF_DAY);
			myMinute = cal.get(Calendar.MINUTE);
		}
	}
	
	public long toTime ()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, getHour());
		cal.set(Calendar.MINUTE, getMinute());
		return cal.getTimeInMillis();
	}
	
	public void setTime(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		
		setHour(cal.get(Calendar.HOUR_OF_DAY));
		setMinute(cal.get(Calendar.MINUTE));
	}

	SimpleDateFormat ourFormat = new SimpleDateFormat("HH:mm");
	
	@Override
	public void serializeTo(SimpleElement elem)
	{
		String value = ourFormat.format(toTime());
		elem.setValue(value);
	}

	@Override
	protected String getSerializationName()
	{
		return TAG_TIME_OF_DAY;
	}
}
