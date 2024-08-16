package com.lts.caloriecount.ui.frequent;

import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.exception.NotImplementedException;
import com.lts.swing.table.rowmodel.RowModelAdaptor;

public class FrequentRowModel extends RowModelAdaptor
{
	static final public int COLUMN_NAME = 2;
	static final public int COLUMN_SELECTIONS = 0;
	static final public int COLUMN_CALORIES = 1;
	
	@Override
	protected String[] subclassGetColumnNames()
	{
		return new String[] { 
				"Count",
				"Calories",
				"Food"
		};
	}

	
	@Override
	public Object getValueAt(int column, Object row)
	{
		FrequentFood frequent = (FrequentFood) row;
		Object value = null;
		
		switch (column)
		{
			case COLUMN_CALORIES :
				value = frequent.getFood().getCalories();
				break;
				
			case COLUMN_NAME :
				value = frequent.getFood().getName();
				break;
				
			case COLUMN_SELECTIONS :
				value = frequent.getCount();
				break;
				
			default :
				throw new IllegalArgumentException(Integer.toString(column));
		}
		
		return value;
	}
	
	

	@Override
	public int compareRows(Object o1, Object o2)
	{
		throw new InvalidOperationException();
	}

	@Override
	public void setValueAt(int rowIndex, Object data, int column, Object value)
	{
		throw new NotImplementedException();
	}
	
	
	
}
