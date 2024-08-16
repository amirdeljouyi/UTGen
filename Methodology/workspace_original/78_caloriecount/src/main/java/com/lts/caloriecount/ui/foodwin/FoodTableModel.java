package com.lts.caloriecount.ui.foodwin;

import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.swing.table.ltstable.LTSTableModel;

public class FoodTableModel extends LTSTableModel
{

	public static final String[] COLUMN_NAMES = {
			"Calories", "Name"
		};
	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_CALORIES = 1;
	private FoodList myFoodList;

	public FoodTableModel()
	{
		super();
	}

	protected void initialize()
	{
		super.initialize();
		myFoodList = CalorieCount.getData().getFoods();
		setColumnNames(COLUMN_NAMES);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	public void reload()
	{
		myFoodList = CalorieCount.getData().getFoods();
		myHelper.fireTableChanged();
	}

	public Object getValueAt(int row, int column)
	{
		Object value = null;
		
		Food food = myFoodList.getFoodAt(row);
		
		switch (column)
		{
			case COLUMN_CALORIES : 
				value = food.getCalories();
				break;
				
			case COLUMN_NAME :
				value = food.getName();
				break;
				
			default :
				throw new IllegalArgumentException("column " + column);
		}
		
		return value;
	}

	public int getRowCount()
	{
		return myFoodList.size();
	}

	public Food getFood(int row)
	{
		return myFoodList.getFoodAt(row);
	}

	public void refreshRow(int index)
	{
		myHelper.fireRowUpdated(index);
	}

}