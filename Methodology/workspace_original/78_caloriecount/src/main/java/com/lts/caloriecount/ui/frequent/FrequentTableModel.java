package com.lts.caloriecount.ui.frequent;

import com.lts.application.data.IdApplicationDataList;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.swing.table.rowmodel.RowModelDataListBridge;

/**
 * A RowModelDataListBridge that uses FrequentFoodList as its underlying data 
 * list.
 * 
 * <P>
 * This is a convenience class to avoid having to 
 * </P>
 * 
 * @author cnh
 *
 */
public class FrequentTableModel extends RowModelDataListBridge
{
	public FrequentTableModel(IdApplicationDataList<FrequentFood> dataList)
	{
		initialize(dataList);
	}
	
	public void initialize(IdApplicationDataList<FrequentFood> dataList)
	{
		FrequentRowModel rowModel = new FrequentRowModel();
		initialize(dataList, rowModel);
	}
}
