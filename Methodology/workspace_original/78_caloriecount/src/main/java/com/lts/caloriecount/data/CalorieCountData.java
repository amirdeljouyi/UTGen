package com.lts.caloriecount.data;

import java.util.ArrayList;
import java.util.List;

import com.lts.EnumWrapper;
import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.IdApplicationDataList;
import com.lts.application.data.MultiElementAppData;
import com.lts.bean.xml.annotations.CollectionProperty;
import com.lts.bean.xml.annotations.IgnoreProperties;
import com.lts.caloriecount.data.budget.Budget;
import com.lts.caloriecount.data.budget.TimeOfDay;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.caloriecount.data.frequent.FrequentFoodList;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.caloriecount.data.meal.MealList;
import com.lts.util.ListUtils;
import com.lts.xml.simple.SimpleElement;

/**
 * The myData that the CalorieCount application tracks.
 * 
 * <P>
 * This includes the following:
 * </P>
 * 
 * <UL>
 * <LI>foods - commonly chosen foods.</LI>
 * <LI>myEntries - What was eaten and when.</LI>
 * </UL>
 * 
 * @author cnh
 */
@IgnoreProperties({"dirty"})
public class CalorieCountData extends MultiElementAppData<CalorieCountDataElements>
	implements SimpleSerialization
{
	private static final long serialVersionUID = 1L;

	public static final String TAG_TAG = "calorieCount";

	transient protected List<String> myDeserializeWarnings;
	
	public static class CalorieCountDataElementsWrapper extends EnumWrapper<CalorieCountDataElements>
	{
		public CalorieCountDataElements valueOf (String s)
		{
			return CalorieCountDataElements.valueOf(s);
		}
		
		public CalorieCountDataElements[] values()
		{
			return (CalorieCountDataElements[]) CalorieCountDataElements.values();
		}
	}
	
	
	public EnumWrapper<CalorieCountDataElements> getEnumWrapper()
	{
		return new CalorieCountDataElementsWrapper();
	}
	
	
	public CalorieCountData()
	{
		initialize();
	}
	
	@CollectionProperty(
			collectionClass="com.lts.caloriecount.data.food.FoodList",
			elementClass="com.lts.caloriecount.data.food.Food",
			elementName="food"
	)
	public FoodList getFoods()
	{
		return (FoodList) get(CalorieCountDataElements.Food);
	}
	
	public void setFoods(FoodList newList)
	{
		FoodList list = getFoods();
		if (null == list)
			setEntry(CalorieCountDataElements.Food, newList);
		else
			list.updateFrom(newList);
	}
	
	public EntryList getMeals()
	{
		return (EntryList) get(CalorieCountDataElements.Entries);
	}
	
	
	public String getEntryName()
	{
		return "myData";
	}

	@Override
	protected void buildEntries()
	{
		postDeserializeEntries();
	}

	protected void checkElement(CalorieCountDataElements element)
	{
		ApplicationDataElement ade = get(element);
		
		switch(element)
		{
			case Budget :
			{
				if (null == ade || !(ade instanceof Budget))
					ade = new Budget();
				break;
			}
			
			case Food :
			{
				if (null == ade || !(ade instanceof FoodList))
					ade = new FoodList();
				break;
			}
			
			case Frequent :
			{
				if (null == ade || !(ade instanceof FrequentFoodList))
					ade = new FrequentFoodList();
				break;
			}
			
			case Meal :
			{
				if (null == ade || !(ade instanceof MealList))
					ade = new MealList();
				break;
			}
			
			case Entries :
			{
				if (null == ade || !(ade instanceof EntryList))
					ade = new EntryList();
				break;
			}
			
			default :
			{
				String msg = 
					"Unrecognized enumration value " + element;
				throw new IllegalArgumentException(msg);
			}
		}
		
		setEntry(element, ade);
	}
	
	
	
	@Override
	protected void postDeserializeEntries() 
	{
		for (CalorieCountDataElements element : ourElements)
		{
			checkElement(element);
		}
	}


	protected Ccde getDataElement(CalorieCountDataElements element)
	{
		return (Ccde) get(element);
	}
	
	
	static public CalorieCountDataElements[] ourElements = CalorieCountDataElements.values();
	
	public FrequentFoodList getFrequentFoods()
	{
		return (FrequentFoodList) get(CalorieCountDataElements.Frequent);
	}


	public Budget getBudget()
	{
		return (Budget) get(CalorieCountDataElements.Budget);
	}


	@Override
	public List<String> getEntryNames()
	{
		List<String> list = new ArrayList<String>();
		
		ListUtils.addAllStrings(list, CalorieCountDataElements.values());
		
		return list;
	}


	public void setBudget(Budget budget) throws ApplicationException
	{
		Budget current = getBudget();
		if (null == current)
			setEntry(CalorieCountDataElements.Budget.toString(), budget);
		else
			current.copyFrom(budget);
	}


	public void setFrequentFoods(IdApplicationDataList<FrequentFood> list) throws ApplicationException
	{
		IdApplicationDataList<FrequentFood> ffl = getFrequentFoods();
		if (null == ffl)
			setEntry(CalorieCountDataElements.Frequent.toString(), list);
		else
			ffl.copyFrom(list);
	}


	public void setMeals(IdApplicationDataList<Meal> list) throws ApplicationException
	{
	}


	public void postDeserialize() throws ApplicationException
	{
		postDeserializeMeals();
		postDeserializeFoods();
		postDeserializeFrequent();
		postDeserializeBudget();
		
		rectifyFoods();
		rectifyMeals();
		rectifyFrequentFoods();
	}


	private void postDeserializeMeals() throws ApplicationException
	{
		if (null == getMeals())
		{
			MealList list = new MealList();
			setMeals(list);
		}
		
		getMeals().postDeserialize();
	}
	
	
	private void postDeserializeFoods() throws ApplicationException
	{
		if (null == getFoods())
		{
			FoodList list = new FoodList();
			setFoods(list);
		}
		
		getFoods().postDeserialize();
	}
	
	
	private void postDeserializeFrequent() throws ApplicationException
	{
		if (null == getFrequentFoods())
		{
			IdApplicationDataList<FrequentFood> list = new FrequentFoodList();
			setFrequentFoods(list);
		}
		
		getFrequentFoods().postDeserialize();
	}
	
	
	private void postDeserializeBudget() throws ApplicationException
	{
		if (null == getBudget())
		{
			Budget budget = new Budget();
			setBudget(budget);
		}
		
		getBudget().postDeserialize();
	}


	private void rectifyFrequentFoods()
	{
		FrequentFoodList list = getFrequentFoods();
		FoodList foodList = getFoods();
		for (FrequentFood entry : list)
		{
			Food food = foodList.getFood(entry.getFoodId());
			entry.setFood(food);
		}
		
		list.resetAllCounts();
		
		for (Entry entry : getEntryList())
		{
			if (entry instanceof Meal)
			{
				Meal meal = (Meal) entry;
				FrequentFood ff = list.getEntryForFoodId(meal.getFoodId());
				if (null == ff)
				{
					ff = list.createFrequentFood(meal.getFood());
				}
				ff.increment(meal.getTime());
			}
		}
	}

	private void rectifyFoods()
	{
	}


	private void rectifyMeals()
	{
		myDeserializeWarnings = new ArrayList<String>();
		
		FoodList foods = getFoods();
		
		for (Entry entry : getEntryList())
		{
			if (!(entry instanceof Meal))
				continue;
			
			Meal meal = (Meal) entry;
			Food food = foods.getFood(meal.getFoodId());
			if (null != food)
				meal.setFood(food);
			else
			{
				String msg = "Could not find food for ID: " + meal.getFoodId();
				myDeserializeWarnings.add(msg);
				throw new RuntimeException(msg);
			}
		}
	}


	/**
	 * Replace all the data in the object with data from the parameter.
	 * 
	 * <P>
	 * The primary objective for this method is to notify the object's listeners. 
	 * </P>
	 * 
	 * @param data The new data to use.
	 */
	public void replaceWith(CalorieCountData data)
	{
		getBudget().replaceWith(data.getBudget());
		getFoods().replaceWith(data.getFoods());
		getFrequentFoods().replaceWith(data.getFrequentFoods());
		getMeals().replaceWith(data.getMeals());
	}


	public void populateFromElement(SimpleElement elem)
			throws ApplicationException
	{
		SimpleElement child;
		
		child = elem.nameToChild("budget");
		Budget budget = new Budget();
		if (null != child)
			budget = deserializeBudget(child);
		setBudget(budget);
		
		FoodList foodList = new FoodList();
		child = elem.nameToChild("foods");
		if (null != child)
		{
			List<Food> list = deserializeFoods(child);
			foodList.setFoods(list);
		}
		setFoods(foodList);
		
		child = elem.nameToChild("frequent");
		FrequentFoodList ffl = new FrequentFoodList();
		if (null != child)
		{
			List<FrequentFood> list = deserializeFrequentList(child);
			ffl.setFrequentFoods(list);
		}
		setFrequentFoods(ffl);
	
		child = elem.nameToChild("meals");
		MealList mealList = new MealList();
		if (null != child)
		{
			List<Meal> list = deserializeMeals(child);
			mealList.setMeals(list);
		}
		setMeals(mealList);
		
		postDeserialize();
	}


	private List<Food> deserializeFoods(SimpleElement elem)
	{
		List<Food> list = new ArrayList<Food>();
		
		for (SimpleElement child : elem.getChildren())
		{
			Food food = deserializeOneFood(child);
			list.add(food);
		}
		
		return list;
	}


	private Food deserializeOneFood(SimpleElement elem)
	{
		Food food = new Food();
		
		food.setId(elem.getIntValueOfChild("id"));
		food.setCalories(elem.getIntValueOfChild("calories"));
		food.setDirty(false);
		food.setName(elem.getValueOfChild("name"));
		
		return food;
	}


	private List<FrequentFood> deserializeFrequentList(SimpleElement elem)
	{
		List<FrequentFood> list = new ArrayList<FrequentFood>();
		
		for (SimpleElement child : elem.getChildrenNamed("frequentSelection"))
		{
			FrequentFood freq = deserializeOneFrequent(child);
			list.add(freq);
		}
		
		return list;
	}


	private FrequentFood deserializeOneFrequent (SimpleElement elem)
	{
		FrequentFood freq = new FrequentFood();
		
		freq.setCount(elem.getIntValueOfChild("count"));
		freq.setFoodId(elem.getIntValueOfChild("foodId"));
		freq.setLockPosition(elem.getIntValueOfChild("lockPosition"));
		
		return freq;
	}


	private Budget deserializeBudget(SimpleElement root)
	{
		Budget budget = new Budget();
		
		budget.setCaloriesPerHour(root.getDoubleValueOfChild("caloriesPerHour"));
		budget.setDirty(false);
		budget.setEndOfDay(deserializeTimeOfDay(root.nameToChild("end")));
		
		double temp = root.getDoubleValueOfChild("period");
		long period = (long) temp;
		budget.setInterval(period);
		budget.setStartOfDay(deserializeTimeOfDay(root.nameToChild("start")));
		
		return budget;
	}


	private List<Meal> deserializeMeals (SimpleElement root)
	{
		List<Meal> list = new ArrayList<Meal>();
		
		for (SimpleElement child : root.getChildrenNamed("meal"))
		{
			Meal meal = deserializeOneMeal(child);
			list.add(meal);
		}
		
		return list;
	}


	private Meal deserializeOneMeal(SimpleElement child)
	{
		Meal meal = new Meal();
		
		meal.setDirty(false);
		meal.setFoodId(child.getIntValueOfChild("foodId"));
		meal.setTime(child.getTimeValueOfChild("time"));
		
		return meal;
	}


	private TimeOfDay deserializeTimeOfDay(SimpleElement elem)
	{
		TimeOfDay tod = new TimeOfDay();
		
		int val = elem.getIntValueOfChild("hour");
		if (-1 != val)
			tod.setHour(val);
		
		val = elem.getIntValueOfChild("minute");
		if (-1 != val)
			tod.setMinute(val);
		
		return tod;
	}


	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement element = new SimpleElement(TAG_TAG);
		
		serializeTo(element);
		
		return element;
	}


	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		SimpleElement child;

		try
		{
			Budget budget = new Budget();
			child = elem.nameToChild(Budget.TAG_BUDGET);
			if (null != child)
			{
				budget.deserializeFrom(child);
			}
			setBudget(budget);
			
			FoodList foodList = new FoodList();
			child = elem.nameToChild(FoodList.TAG_FOOD_LIST);
			if (null != child)
			{
				foodList.deserializeFrom(child);
			}
			setFoods(foodList);
			
			EntryList entryList = new EntryList();
			child = elem.nameToChild(EntryList.TAG_ENTRY_LIST);
			if (null != child)
			{
				entryList.deserializeFrom(child);
			}
			setEntryList(entryList);
			
			IdApplicationDataList<FrequentFood> ffl = new FrequentFoodList();
			child = elem.nameToChild(FrequentFoodList.TAG_FREQUENT_FOODS);
			if (null != child)
			{
				ffl.deserializeFrom(child);
			}
			setFrequentFoods(ffl);
			
			postDeserialize();
		}
		catch (ApplicationException e)
		{
			throw new RuntimeException("Error deserializing", e);
		}
	}

	@Override
	public void serializeTo(SimpleElement elem)
	{
		elem.setAttribute("version", "1.3");
		elem.setTimeAttribute("date", System.currentTimeMillis());

		elem.createChild(Budget.TAG_BUDGET, getBudget());
		elem.createChild(FoodList.TAG_FOOD_LIST, getFoods());
		elem.createChild(FrequentFoodList.TAG_FREQUENT_FOODS, getFrequentFoods());
		elem.createChild(EntryList.TAG_ENTRY_LIST, getEntryList());
	}

	public static final String ENTRY_LIST_NAME = "entries";
	
	public EntryList getEntryList()
	{
		return (EntryList) get(CalorieCountDataElements.Entries);
	}
	
	public void setEntryList(EntryList list)
	{
		if (null == list)
			list = new EntryList();
		
		setEntry(CalorieCountDataElements.Entries, list);
	}


	public void addMeal(Meal meal)
	{
		getEntryList().add(meal);
		FrequentFood freq = getFrequentFoods().getEntryForFoodId(meal.getFoodId());
		if (null == freq)
		{
			createFrequentFood(meal.getFoodId());
		}
	}


	public void createFrequentFood(int foodId)
	{
		Food food = getFoods().getFood(foodId);
		getFrequentFoods().createFrequentFood(food);
	}
}
