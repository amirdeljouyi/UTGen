package com.lts.caloriecount.ui.foodwin;

import com.lts.caloriecount.data.food.FoodList;

public class FoodSelectDragAndDrop extends ApplicationDataDragAndDrop
{
	public FoodSelectDragAndDrop(FoodList list)
	{
		initialize(list);
	}


	protected Object[][] getSupportSpec()
	{
		Object[][] spec = {
				{ DragAndDropOperations.Copy, true, },
				{ DragAndDropOperations.Cut, true, },
				{ DragAndDropOperations.Move, true, },
				{ DragAndDropOperations.Paste, true, }
		};
		
		return spec;
	}
}
