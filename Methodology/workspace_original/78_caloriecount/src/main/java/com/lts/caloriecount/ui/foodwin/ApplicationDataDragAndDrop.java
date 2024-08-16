package com.lts.caloriecount.ui.foodwin;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;

import com.lts.application.data.ApplicationDataElement;
import com.lts.caloriecount.ui.dnd.JTableDragAndDrop;
import com.lts.util.notifyinglist.NotifyingList;

abstract public class ApplicationDataDragAndDrop extends JTableDragAndDrop
{
	abstract protected Object[][] getSupportSpec();

	protected NotifyingList<ApplicationDataElement> myList;
	protected Map<DragAndDropOperations, Boolean> mySupportedOperations;

	@Override
	protected Object copyRow(JTable table, int source)
	{
		try
		{
			ApplicationDataElement element = (ApplicationDataElement) myList.get(source);
			return element.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void deleteRow(JTable table, int row)
	{
		myList.remove(row);
	}

	@Override
	protected void pasteRow(JTable table, int destination, Object rowData)
	{
		ApplicationDataElement data = (ApplicationDataElement) rowData;
		myList.add(destination, data);
	}

	protected Map<DragAndDropOperations, Boolean> buildSpportedOperation(Object[][] supportSpec)
	{
		Map<DragAndDropOperations, Boolean> map = new HashMap<DragAndDropOperations, Boolean>();
		for (Object[] row : supportSpec)
		{
			DragAndDropOperations operation = (DragAndDropOperations) row[0];
			Boolean supported = (Boolean) row[1];
			map.put(operation,supported);
		}
		
		return map;
	}

	protected void initialize(NotifyingList list)
	{
		myList = list;
		Object[][] supportSpec = getSupportSpec();
		mySupportedOperations = buildSpportedOperation(supportSpec);
	}

	@Override
	public boolean supportsOperation(DragAndDropOperations operation)
	{
		Boolean supported = mySupportedOperations.get(operation);
		if (null == supported)
			return false;
		else
			return supported;
	}
}
