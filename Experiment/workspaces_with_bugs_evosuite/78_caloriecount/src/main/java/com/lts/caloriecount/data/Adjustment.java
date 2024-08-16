package com.lts.caloriecount.data;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.xml.simple.SimpleElement;

/**
 * An adjustment the calorie balance.
 * 
 * <P>
 * An instance of this class has a value that corresponds to the number of calories
 * that should be added to the balance.  A negative value indicates that the balance 
 * should be decreased, while a positive value means an increase.
 * </P>
 * 
 * @author cnh
 *
 */
public class Adjustment extends Entry
{
	private static final String TAG_ADJUSTMENT = "adjustment";

	public static final String TAG_CALORIES = "calories";

	private static final long serialVersionUID = 1L;

	private String myDescription;
	private int myCalories;

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("Adjustment: ");
		addValues(sb);
		
		return sb.toString();
	}

	@Override
	public void addValues(StringBuffer sb)
	{
		super.addValues(sb);
		sb.append(myCalories);
		sb.append(", ");
		sb.append(getDescription());
	}
	
	
	public void serializeTo (SimpleElement elem)
	{
		super.serializeTo(elem);		
		elem.createChild(TAG_CALORIES, getCalories());
		elem.createChild(TAG_DESCRIPTION, getDescription());
	}

	public void deserializeFrom(SimpleElement elem)
	{
		super.deserializeFrom(elem);
		SimpleElement child = elem.nameToChild(TAG_DESCRIPTION);
		setDescription(child.getValue());
		
		setCalories(elem.getIntValueOfChild(TAG_CALORIES));
	}
	
	public SimpleElement toSimpleElement(String name)
	{
		SimpleElement elem = new SimpleElement(name);
		
		serializeTo(elem);
		
		return elem;
	}

	@Override
	protected String getSerializationName()
	{
		return TAG_ADJUSTMENT;
	}
	
	
	public String getDescription()
	{
		return myDescription;
	}
	
	public void setDescription(String description)
	{
		myDescription = description;
	}
	
	
	public void copyFrom(ApplicationDataElement elem) throws ApplicationException
	{
		super.copyFrom(elem);
		
		Adjustment other = (Adjustment) elem;
		myDescription = other.myDescription;
		myCalories = other.myCalories;
	}

	public int getCalories()
	{
		return myCalories;
	}

	public void setCalories(int calories)
	{
		myCalories = calories;
	}
	
	
	
}
