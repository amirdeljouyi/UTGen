package com.lts.caloriecount.ui.dayview;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.lts.caloriecount.data.meal.Meal;
import com.lts.swing.table.rowmodel.RowModel;

public class MealRowModel implements RowModel
{
	public static final int COL_TIME = 0;
	public static final int COL_CALORIES = 1;
	public static final int COL_NAME = 2;
	
	public static final String[] COLUMN_NAMES = {
		"Time",
		"Calories",
		"Food"
	};
	
	public int getColumnCount()
	{
		return COLUMN_NAMES.length;
	}

	public String getColumnName(int column)
	{
		return COLUMN_NAMES[column];
	}

	public Comparator getComparator()
	{
		return Meal.COMPARATOR;
	}

	public static final SimpleDateFormat ourFormat = 
		new SimpleDateFormat("HH:mm");
	
	public Object getValueAt(int column, Object row)
	{
		Meal meal = (Meal) row;
		Object value = null;
		
		switch (column)
		{
			case COL_CALORIES :
				value = meal.getCalories();
				break;
				
			case COL_NAME :
				value = meal.getFood().getName();
				break;
				
			case COL_TIME :
				value = ourFormat.format(meal.getTime());
				break;
				
			default :
				throw new IllegalArgumentException(Integer.toString(column));
		}
		
		return value;
	}
	

	public boolean isColumnEditable(int col)
	{
		return false;
	}

	public void setValueAt(Object row, int column, Object value)
	{
		throw new UnsupportedOperationException();
	}

	public void update(Object oldData, Object newData)
	{
		throw new UnsupportedOperationException();
	}

	public Class getColumnClass(int column)
	{
		return String.class;
	}

	public String[] getColumnNames()
	{
		return COLUMN_NAMES;
	}

	public int compareRows(Object o1, Object o2)
	{
		return getComparator().compare(o1, o2);
	}

	@Override
	public void setValueAt(int rowIndex, Object data, int column, Object value)
	{
		throw new RuntimeException("not implemented");
	}

}
