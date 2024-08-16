package com.lts.caloriecount.ui.entry;

import java.util.Comparator;

import com.lts.caloriecount.data.entry.Entry;
import com.lts.swing.table.rowmodel.RowModel;

public class EntryRowModel implements RowModel
{
	public static final int COL_TIME = 0;
	public static final int COL_AMOUNT = 1;
	public static final int COL_DESCRIPTION = 2;
	
	public static final String[] NAMES = new String[] {
		"Time",
		"Amount",
		"Description"
	};
	
	public static final Class[] COLUMN_CLASSES = new Class[] {
		String.class,
		Integer.class,
		String.class
	};
	
	private boolean myShowTimeOnly;
	
	@Override
	public int compareRows(Object o1, Object o2)
	{
		Entry e1 = (Entry) o1;
		Entry e2 = (Entry) o2;
		return e1.compareTo(e2);
	}

	@Override
	public Class getColumnClass(int column)
	{
		return COLUMN_CLASSES[column];		
	}

	@Override
	public int getColumnCount()
	{
		return NAMES.length;
	}

	@Override
	public String getColumnName(int column)
	{
		return NAMES[column];
	}

	@Override
	public String[] getColumnNames()
	{
		return NAMES;
	}

	@Override
	public Comparator getComparator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValueAt(int column, Object row)
	{
		Object value = null;
		
		Entry entry = (Entry) row;
		switch (column)
		{
			case COL_AMOUNT :
				value = new Integer(entry.getCalories());
				break;
				
			case COL_DESCRIPTION :
				value = entry.getDescription();
				break;
				
			case COL_TIME :
				if (getShowTimeOnly())
					value = Entry.ourFormatTime.format(entry.getTime());
				else
					value = Entry.ourFormat.format(entry.getTime());
				break;
		}
		
		return value;
	}

	@Override
	public boolean isColumnEditable(int col)
	{
		return false;
	}

	@Override
	public void setValueAt(int rowIndex, Object data, int column, Object value)
	{}

	public boolean getShowTimeOnly()
	{
		return myShowTimeOnly;
	}

	public void setShowTimeOnly(boolean showTimeOnly)
	{
		myShowTimeOnly = showTimeOnly;
	}
}
