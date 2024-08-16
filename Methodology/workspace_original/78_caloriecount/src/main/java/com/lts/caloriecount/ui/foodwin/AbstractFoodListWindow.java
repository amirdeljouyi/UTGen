package com.lts.caloriecount.ui.foodwin;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.swing.LTSPanel;
import com.lts.swing.table.LTSTable;

public abstract class AbstractFoodListWindow extends CalorieCountPanel
{
	abstract protected FoodControlPanel createFoodControlPanel();
	
	protected LTSTable myTable;
	protected FoodTableModel myModel;
	protected FoodControlPanel myControlPanel;

	public AbstractFoodListWindow()
	{
		super();
	}

	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		myControlPanel = createFoodControlPanel();
		panel.addFill(myControlPanel);
		
		return panel;
	}



	@Override
	protected String getViewName()
	{
		return "Foods";
	}

	
	public void refresh()
	{
		myModel.reload();
	}
	
	
	public void initialize(JFrame frame) throws LTSException
	{
		setBottomPanelMode(BOTTOM_PANEL_CLOSE);
		super.initialize(frame);
		
	}

}