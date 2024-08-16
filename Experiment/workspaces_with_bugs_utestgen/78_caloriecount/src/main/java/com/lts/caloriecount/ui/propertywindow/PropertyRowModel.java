package com.lts.caloriecount.ui.propertywindow;

import com.lts.swing.table.rowmodel.RowModelAdaptor;

public class PropertyRowModel extends RowModelAdaptor
{
	public static final int COL_NAME = 0;
	public static final int COL_VALUE = 1;
	
	private static String[] COLUMN_NAMES = {
			"Name",
			"Value"
	};
	
	@Override
	public Object getValueAt(int column, Object row)
	{
		KeyValue keyValue = (KeyValue) row;
		
		String result = null;
		
		switch (column)
		{
			case COL_NAME : 
				result = keyValue.key;
				break;
				
			case COL_VALUE :
				result = keyValue.value;
				break;
		}
		
		return result;
	}

	@Override
	protected String[] subclassGetColumnNames()
	{
		return COLUMN_NAMES;
	}

	@Override
	public int compareRows(Object o1, Object o2)
	{
		KeyValue keyValue1 = (KeyValue) o1;
		KeyValue keyValue2 = (KeyValue) o2;
		
		return keyValue1.key.compareTo(keyValue2.key);
	}

	@Override
	public void setValueAt(int rowIndex, Object data, int column, Object value)
	{
		KeyValue keyValue = (KeyValue) data;
		
		switch (column)
		{
			case COL_NAME :
				keyValue.value = (String) value;
				break;
				
			case COL_VALUE :
				keyValue.value = (String) value;
				break;
		}
	}

	@Override
	public boolean isColumnEditable(int col)
	{
		return true;
	}

	
}
