package com.lts.caloriecount.data.food;

import java.util.Comparator;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.ApplicationDataElementAdaptor;
import com.lts.caloriecount.data.CalorieCountDataElements;
import com.lts.util.StringUtils;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.xml.simple.SimpleElement;

/**
 * A single serving of food such as an Apple or a Sandwich.
 * 
 * <P>
 * Each food has the following characteristics:
 * </P>
 * 
 * <UL>
 * <LI>name - a short name for the food such as "apple" or "slug sandwich"</LI>
 * <LI>description - a freeform blob of text that gives more info about 
 *     the food like "a relatively large (140g) Yakima apple."</LI>
 * <LI>calories - the number of kcalories that the food contains.</LI>
 * </UL>
 * 
 * @author cnh
 */
public class Food extends ApplicationDataElementAdaptor implements Comparable
{
	private static final long serialVersionUID = 1L;

	public static final String TAG_FOOD = "food";
	
	public static final String TAG_CALORIES = "calories";
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_NAME = "name";
	
	public static Comparator FoodComparator =
	new Comparator()
	{
		public int compare (Object o1, Object o2)
		{
			Food f1 = (Food) o1;
			Food f2 = (Food) o2;
			return f1.myName.compareToIgnoreCase(f2.myName);
		}
	};
	
	private String myName;
	private String myDescription;
	private int myCalories;
	
	public Food() 
	{}
	
	public Food(int id)
	{
		initialize(id, null, -1, null);
	}
	
	
	public Food(int id, String name, int calories, String description)
	{
		initialize(id, name, calories, description);
	}

	protected void initialize (int id, String name, int calories, String description)
	{
		myId = id;
		myName = name;
		myDescription = description;
		myCalories = calories;
	}
	
	
	public int compareTo (Object o)
	{
		return FoodComparator.compare(this, o);
	}
	
	
	public int getCalories()
	{
		return myCalories;
	}
	public void setCalories(int calories)
	{
		this.myCalories = calories;
	}
	public String getDescription()
	{
		return myDescription;
	}
	public void setDescription(String description)
	{
		this.myDescription = description;
	}
	public String getName()
	{
		return myName;
	}
	public void setName(String name)
	{
		this.myName = name;
	}
	
	
	public String getEntryName()
	{
		return CalorieCountDataElements.Food.toString();
	}
	
	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
		Food other = (Food) copy;
		other.myCalories 	= myCalories;
		other.myDescription = myDescription;
		other.myName 		= myName;
	}
	
	
	public int compareTo(Food food)
	{
		return myName.compareTo(food.myName);
	}
	
	public void copyFrom (ApplicationDataElement element) throws ApplicationException
	{
		Food other = (Food) element;
		myCalories = other.myCalories;
		myDescription = other.myDescription;
		myName = other.myName;
		
		postDeserialize();
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(myName);
		sb.append(",");
		sb.append(myCalories);
		
		return sb.toString();
	}

	public boolean equals (Object o)
	{
		Food other = (Food) o;
		
		return 
			myCalories == other.myCalories
			&& StringUtils.equivalent(myDescription, other.myDescription)
			&& StringUtils.equivalent(myName, other.myName);
	}

	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		super.deserializeFrom(elem);
		
		setName(elem.getValueOfChild(TAG_NAME));
		setCalories(elem.getIntValueOfChild(TAG_CALORIES));
		setDescription(elem.getValueOfChild(TAG_DESCRIPTION, true));
	}

	@Override
	public void serializeTo(SimpleElement elem)
	{
		super.serializeTo(elem);
		
		elem.createChild(TAG_NAME, getName());
		elem.createChild(TAG_CALORIES, getCalories());
		elem.createChild(TAG_DESCRIPTION, getDescription());
	}

	@Override
	protected String getSerializationName()
	{
		return TAG_FOOD;
	}
}
