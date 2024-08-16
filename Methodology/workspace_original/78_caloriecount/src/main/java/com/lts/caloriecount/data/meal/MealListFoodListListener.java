/**
 * 
 */
package com.lts.caloriecount.data.meal;

import java.util.List;
import java.util.Map;

import com.lts.application.data.IdApplicationDataList;
import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingListListenerAdaptor;

/**
 * Converts FoodList events to MealList events.
 * 
 * <P>
 * The primary was that this class "adds value" is to translate between indexes used
 * by Foods to indexes used by {@link MealList}.
 * </P>
 * 
 * @author cnh
 *
 */
public class MealListFoodListListener extends NotifyingListListenerAdaptor
{
	/**
	 * A map from a food ID to a list of meal IDs where that food is used.
	 */
	protected Map<Integer, List<Integer>> myFoodToMealList;
	protected IdApplicationDataList<Meal> myMeals;
	
	@Override
	public void allRowsChanged()
	{
		// TODO Auto-generated method stub
		super.allRowsChanged();
	}

//	@Override
//	public void delete(int index)
//	{
//		// TODO Auto-generated method stub
//		super.delete(index);
//	}
//
//	@Override
//	public void insert(int index)
//	{
//		// TODO Auto-generated method stub
//		super.insert(index);
//	}

	@Override
	public void listEvent(ListEvent event)
	{
		// TODO Auto-generated method stub
		super.listEvent(event);
	}

//	@Override
//	public void update(int index)
//	{
//		// TODO Auto-generated method stub
//		super.update(index);
//	}
}