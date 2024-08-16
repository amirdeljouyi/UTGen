package com.lts.caloriecount.ui.frequent;

import javax.swing.JTable;

import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.IdApplicationDataList;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.caloriecount.ui.dnd.JTableDragAndDrop;
import com.lts.util.notifyinglist.NotifyingListAdaptor;

public class FrequentTableDragAndDrop extends JTableDragAndDrop
{

	@Override
	protected Object copyRow(JTable table, int source)
	{
		NotifyingListAdaptor<? extends ApplicationDataElement> list = CalorieCount.getData().getFrequentFoods();
		return list.get(source);
	}

	@Override
	protected void deleteRow(JTable table, int row)
	{
		NotifyingListAdaptor<? extends ApplicationDataElement> list = CalorieCount.getData().getFrequentFoods();
		list.remove(row);
	}

	/**
	 * Paste some data into the table at a specified location.
	 * 
	 * <P>
	 * This class is a bit more complex than the typical JTable because of the 
	 * presence of locked rows.  In a drag and move operation, some of the rows
	 * can be locked, which means that the user wants to change the lock position.
	 * </P>
	 * 
	 * <P>
	 * This class must take that into account and update locked positions 
	 * appropriately.
	 * </P>
	 */
	@Override
	protected void pasteRow(JTable table, int destination, Object rowData)
	{
		IdApplicationDataList<FrequentFood> list = CalorieCount.getData().getFrequentFoods();
		FrequentFood source = (FrequentFood) rowData;
		FrequentFood copy = (FrequentFood) source.clone();
		list.add(destination, copy);
		
		FrequentFood food = (FrequentFood) copy;
		if (food.isLocked())
			food.setLockPosition(destination);
	}

	@Override
	public boolean supportsOperation(DragAndDropOperations operation)
	{
		boolean supported;
		
		switch (operation)
		{
			case Copy :
				supported = true;
				break;
				
			case Cut :
				supported = false;
				break;
				
			case Move :
				supported = true;
				break;
				
			case Paste :
				supported = false;
				break;
				
			default :
				throw new IllegalArgumentException(operation.toString());
		}
		
		return supported;
	}

}
