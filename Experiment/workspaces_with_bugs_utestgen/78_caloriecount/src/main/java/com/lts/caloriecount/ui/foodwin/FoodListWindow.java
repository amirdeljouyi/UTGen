package com.lts.caloriecount.ui.foodwin;

import javax.swing.JFrame;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.swing.table.ControlPanel.PanelModes;


@SuppressWarnings("serial")
public class FoodListWindow extends AbstractFoodListWindow
{
	public static final String WINDOW_PROPERTY_NAME = "FoodListWindow";
	
	public FoodListWindow(JFrame frame)
	{
		try
		{
			setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
			initialize(frame);
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}
	
	
	public static void displayFoodList()
	{
		JFrame frame = new JFrame();
		@SuppressWarnings("unused")
		FoodListWindow win = new FoodListWindow(frame);
		frame.setVisible(true);
	}
	
	
	protected FoodControlPanel createFoodControlPanel()
	{
		return new FoodControlPanel(PanelModes.Browse);
	}
}
