package com.lts.caloriecount.data.frequent;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.lts.application.data.ApplicationDataElementAdaptor;
import com.lts.caloriecount.data.food.Food;
import com.lts.xml.simple.SimpleElement;

public class FrequentFood extends ApplicationDataElementAdaptor
{
	public static Comparator FrequencyComparator = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			FrequentFood f1 = (FrequentFood) o1;
			FrequentFood f2 = (FrequentFood) o2;
			
			if (f1.myCount > f2.myCount)
				return -1;
			else if (f1.myCount < f2.myCount)
				return 1;
			else
				return 0;
		}
	};
	
	
	public static Comparator LockPositionComparator = new Comparator() {
		public int compare (Object o1, Object o2)
		{
			FrequentFood f1 = (FrequentFood) o1;
			FrequentFood f2 = (FrequentFood) o2;
			
			if (f1.myLockPosition < f2.myLockPosition)
				return -1;
			else if (f1.myLockPosition > f2.myLockPosition)
				return 1;
			else
				return 0;
		}
	};
	
	private static final long serialVersionUID = 1L;

	public static final String TAG_FREQUENT_FOOD = "frequent";
	public static final String TAG_COUNT = "count";
	public static final String TAG_FOOD_ID = "foodId";
	public static final String TAG_LAST_SELECTED = "lastSelected";
	public static final String TAG_LOCK_POSITION = "position";
	
	transient private Food myFood;
	private int myFoodId;
	private boolean myLocked;
	private int myLockPosition;
	private int myCount;
	private long myLastSelected;

	
	static protected SimpleDateFormat ourFormat =
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	public int getFoodId()
	{
		return myFoodId;
	}
	
	public int getLockPosition()
	{
		return myLockPosition;
	}
	
	public void setLockPosition(int lockPosition)
	{
		myLockPosition = lockPosition;
	}
	
	public FrequentFood()
	{}
	
	public FrequentFood (int entryId, Food food)
	{
		initialize(entryId, food);
	}

	public boolean locked()
	{
		return myLocked;
	}
	
	public boolean isLocked()
	{
		return locked();
	}
	
	public void setLocked (boolean locked)
	{
		myLocked = locked;
	}
	
	protected void initialize(int entryId, Food food)
	{
		myFood = food;
		myId = entryId;
		myFoodId = food.getId();
	}
	
	public Food getFood()
	{
		return myFood;
	}
	
	public void setFood(Food food)
	{
		myFood = food;
	}
	
	public int getCount()
	{
		return myCount;
	}
	
	public void setCount(int count)
	{
		myCount = count;
	}
	
	public long getLastSelected()
	{
		return myLastSelected;
	}
	
	public void setLastSelected(long time)
	{
		myLastSelected = time;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		if (null == myFood)
		{
			sb.append("ID = " + myFoodId);
		}
		else
		{
			sb.append(myFood.getName());
		}
		sb.append(", ");
		sb.append(myCount);
		sb.append(", ");
		sb.append(ourFormat.format(myLastSelected));
		
		if (myLocked)
		{
			sb.append(", LOCKED at ");
			sb.append(myLockPosition);
		}
		return sb.toString();
	}
	
	
	public void increment(long when)
	{
		myCount++;
		myLastSelected = when;
	}
	
	public void increment()
	{
		increment(System.currentTimeMillis());
	}

	public void setFoodId(int id)
	{
		myFoodId = id;
	}

	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement elem = new SimpleElement(TAG_FREQUENT_FOOD);
		serializeTo(elem);
		return elem;
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		super.deserializeFrom(elem);
		
		setFoodId(elem.getIntValueOfChild(TAG_FOOD_ID));
		setCount(elem.getIntValueOfChild(TAG_COUNT, true));
		setLastSelected(elem.getTimeValueOfChild(TAG_LAST_SELECTED, true));
		Integer ival = elem.getObjectIntValueOfChild(TAG_LOCK_POSITION);
		setLocked(null != ival);
		if (null != ival)
		{
			setLockPosition(ival);
		}
	}

	@Override
	public void serializeTo(SimpleElement elem)
	{
		super.serializeTo(elem);
		
		Food food = getFood();
		if (null != food)
		{
			elem.createChild("food", food.getName());
		}
		
		elem.createChild(TAG_FOOD_ID, getFoodId());
		elem.createChild(TAG_COUNT, getCount());
		String s = SimpleElement.toDateTimeValue(getLastSelected());
		elem.createChild(TAG_LAST_SELECTED, s);
		if (locked())
		{
			elem.createChild(TAG_LOCK_POSITION, getLockPosition());
		}
	}

	@Override
	protected String getSerializationName()
	{
		return TAG_FREQUENT_FOOD;
	}
}
