package com.lts.caloriecount.data.food;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.IdApplicationDataList;
import com.lts.bean.xml.annotations.CollectionProperty;
import com.lts.bean.xml.annotations.IgnoreProperties;
import com.lts.bean.xml.annotations.IgnoreProperty;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

@IgnoreProperties({"getEntry", "empty", "dataElements"})
public class FoodList extends IdApplicationDataList<Food>
{
	private static final long serialVersionUID = 1L;

	public static final String TAG_FOOD_LIST = "foods";
	
	transient private Comparator myComparator;
	
	public Food createFood(String name, int calories, String description)
	{
		Food food = new Food(-1, name, calories, description);
		add(food);
		return food;
	}
	
	
	public FoodList()
	{
		initialize(null);
	}
	
	
	public FoodList(List foods)
	{
		initialize(foods);
	}
	
	public void setFoods (List foods)
	{
		myList = foods;
		myHelper.fireAllChanged();
	}
	
	
	protected void initialize(List foods)
	{
		myComparator = Food.FoodComparator;
		super.initialize(foods);
	}
	
	@IgnoreProperty
	public Comparator getComparator()
	{
		return myComparator;
	}


	@IgnoreProperty
	public Food getFoodAt(int index)
	{
		return (Food) get(index);
	}

	public void removeFood (Food food)
	{
		super.remove(food);
	}
	
	
	public List getDataElements()
	{
		return myList;
	}
	
	
	public void postDeserialize() throws ApplicationException
	{
		super.postDeserialize();
		
		myComparator = Food.FoodComparator;
		
		myNextId = 0;
		for (Object o : myList)
		{
			Food food = (Food) o;
			if (food.getId() >= myNextId)
				myNextId = 1 + food.getId();
		}
		
		rebuildIdToListPosition();
	}


//	public void copyFrom(ApplicationDataElement element) throws ApplicationException
//	{
//		FoodList foodList = (FoodList) element;
//		myList = new ArrayList<Food>();
//		myList.addAll(foodList.myList);
//		myDirty = true;
//		myHelper.fireAllChanged();
//	}


	public boolean isDirty()
	{
		return myDirty;
	}


	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}


	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
			throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}


	public Object deepCopy() throws DeepCopyException
	{
		return deepCopy(false);
	}


	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}


	public int findEquivalent(Food food)
	{
		int size = size();
		for (int index = 0; index < size; index++)
		{
			 Food f = getFoodAt(index);
			 if (food.equals(f))
				 return index;
		}
		
		return -1;
	}


	public void updateFrom(FoodList newList)
	{
		myList.clear();
		myList.addAll(newList);
		myHelper.fireAllChanged();
	}


	@IgnoreProperty
	public Food getFood(int id)
	{
		return idToElement(id);
	}
	
	@CollectionProperty(
			elementClass="com.lts.caloriecount.data.food.Food", 
			elementName="Food"
	)
	public List<Food> getFoods()
	{
		return myList;
	}

	public Food createFood(Food food)
	{
		return createFood(food.getName(), food.getCalories(), food.getDescription());
	}


	@Override
	protected String getSerializationElementName()
	{
		return TAG_FOOD_LIST;
	}

	@Override
	protected Food createListElement(String name)
	{
		return new Food();
	}
	
	
	@Override
	public void replaceWith(List<Food> list)
	{
		super.replaceWith(list);
		rebuildIdToListPosition();
		myHelper.fireAllChanged();
	}
}
