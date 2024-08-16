package com.lts.caloriecount.data.entry;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.ApplicationDataElementAdaptor;
import com.lts.xml.simple.SimpleElement;

/**
 * The common ancestor of Meals and Adjustments.
 * 
 * @author cnh
 */
abstract public class Entry extends ApplicationDataElementAdaptor implements Comparable
{
	private static final String TAG_TIME = "time";
	private static final long serialVersionUID = 1L;
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_AMOUNT = "calories";
	
	abstract public String getDescription();
	abstract public int getCalories();

	public static Comparator<Entry> TIME_COMPARATOR = new Comparator<Entry>()
	{
		public int compare(Entry o1, Entry o2)
		{
			long time1 = o1.getTime();
			long time2 = o2.getTime();
			
			if (time1 < time2)
				return -1;
			else if (time1 > time2)
				return 1;
			else
				return 0;
		}
	};
	
	public static SimpleDateFormat ourFormat =
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss.SSS");
	public static SimpleDateFormat ourFormatTime =
		new SimpleDateFormat("HH:mm");
	
	protected long myTime;
	
	public long getTime()
	{
		return myTime;
	}


	public void setTime(long time)
	{
		myTime = time;
	}


	@Override
	public int compareTo(Object o)
	{
		Entry other = (Entry) o;
		return TIME_COMPARATOR.compare(this, other);
	}
	
	/**
	 * This method returns a string containing the values of the object without any
	 * reference to that instance's "name".
	 */
	public void addValues(StringBuffer sb)
	{
		super.addValues(sb);
		sb.append(", ");
		sb.append(ourFormat.format(getTime()));
	}


	@Override
	public void serializeTo(SimpleElement elem)
	{
		super.serializeTo(elem);
		
		String value = ourFormat.format(getTime());
		elem.createChild(TAG_TIME, value);
	}

	

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		super.deserializeFrom(elem);
		setTime(elem.getTimeValueOfChild(TAG_TIME));
	}


	@Override
	public void copyFrom(ApplicationDataElement element) throws ApplicationException
	{
		super.copyFrom(element);
		
		Entry other = (Entry) element;
		myTime = other.myTime;
	}
}
