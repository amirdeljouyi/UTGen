package com.lts.caloriecount.ui.foodwin;



import com.lts.caloriecount.data.food.Food;
import com.lts.exception.NotImplementedException;
import com.lts.swing.table.rowmodel.RowModelAdaptor;

public class FoodRowModel extends RowModelAdaptor
{
	public static final int COL_NAME = 1;
	public static final int COL_CALORIES = 0;
	public static final String[] COLUMN_NAMES = {
		"Calories",
		"Name"
	};
	
	@Override
	public Object getValueAt(int column, Object row)
	{
		Food food = (Food) row;
		Object value = null;
		
		switch(column)
		{
			case COL_NAME :
				value = food.getName();
				break;
				
			case COL_CALORIES :
				value = Integer.toString(food.getCalories());
				break;
				
			default :
				throw new IllegalArgumentException(Integer.toString(column));
		}
		
		return value;
	}

	@Override
	protected String[] subclassGetColumnNames()
	{
		return COLUMN_NAMES;
	}

	public int compareRows(Object o1, Object o2)
	{
		throw new NotImplementedException();
	}

	@Override
	public void setValueAt(int rowIndex, Object data, int column, Object value)
	{
		throw new RuntimeException("not implemented");
	}

}
