package com.lts.caloriecount.ui.foodwin;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.swing.LTSPanel;
import com.lts.swing.thread.BlockingThread;
import com.lts.util.StringUtils;

/**
 * A window that allows the user to edit a food.
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class FoodWindow extends CalorieCountPanel
{
	public static final String WINDOW_PROPERTY_NAME = "FoodWindow";
	
	private JTextField myNameField;
	private JTextField myCalorieField;
	
	private Food myFood;
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(357, 154);
	}

	public void setFood(Food food)
	{
		myFood = food;
		
		String s = Integer.toString(myFood.getCalories());
		myCalorieField.setText(s);
		myNameField.setText(myFood.getName());
	}
	
	public FoodWindow (JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	public FoodWindow (JDialog dialog) throws LTSException
	{
		initialize(dialog);
	}
	
	
	public void initialize(Container frame) throws LTSException
	{
		setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
		setBottomPanelMode(BOTTOM_PANEL_OK_CANCEL);
		super.initialize(frame);
		myFood = new Food();
		setupEnterKey(frame);
	}
	
	
	@Override
	protected String getViewName()
	{
		return "Meal";
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label;
		
		label = new JLabel("Name");
		panel.addLabel(label,5);
		myNameField = new JTextField();
		panel.addHorizontal(myNameField, 5);
		
		panel.nextRow();
		
		label = new JLabel("Calories");
		panel.addLabel(label,5);
		myCalorieField = new JTextField();
		Dimension dim = new Dimension(50,20);
		myCalorieField.setMinimumSize(dim);
		myCalorieField.setPreferredSize(dim);
		panel.addLabel(myCalorieField,5);
		
		return panel;
	}

	
	public void okButtonPressed()
	{
		if (validateFields())
		{
			updateObject();
			super.okButtonPressed();
		}
	}
	
	
	private boolean validateFields()
	{
		String s = StringUtils.trim(myNameField.getText());
		if (null == s)
		{
			JOptionPane.showMessageDialog(this, "Please enter a name.");
			return false;
		}
		
		s = StringUtils.trim(myCalorieField.getText());
		if (null == s)
		{
			String message =
				"Please enter a value for the number of calories";
			
			JOptionPane.showMessageDialog(this, message);
			return false;
		}
		
		return true;
	}
	
	
	private void updateObject()
	{
		myFood.setName(myNameField.getText());
		int ival = StringUtils.toInteger(myCalorieField.getText());
		myFood.setCalories(ival);
	}
	

	static public Food editData(Food food)
	{
		Food result = null;
		
		try
		{
			JFrame frame = new JFrame();
			FoodWindow win = new FoodWindow(frame);
			win.setFood(food);
			win.setResult(RESULT_CANCEL);
			
			BlockingThread.staticDisplayAndWait(frame);
			
			if (win.getResult() == RESULT_OK)
				result = win.getFood();
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
		
		return result;
	}
	
	
	static public Food editData(Food food, JDialog dialog)
	{
		Food result = null;

		try
		{
			FoodWindow win = new FoodWindow(dialog);
			win.setFood(food);
			win.setResult(RESULT_CANCEL);
			dialog.setVisible(true);
			if (RESULT_CANCEL != win.getResult())
				result = win.getFood();
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}

		return result;
	}
	
	
	static public Food createFood()
	{
		Food food = new Food();
		food = editData(food);
		if (null != food)
		{
			CalorieCount.getData().getFoods().add(food);
			return food;
		}
		
		return food;
	}
	
	
	static public Food createFood(JDialog dialog)
	{
		Food food = new Food();
		food = editData(food, dialog);		
		return food;
	}

	public Food getFood()
	{
		return myFood;
	}
	
	
	public void setVisible (boolean val)
	{
		super.setVisible(val);
	}

	private static class LocalBlockingThread extends BlockingThread
	{
		private Food myFood;
		
		public LocalBlockingThread (Food food)
		{
			myFood = food;
		}
		public void run()
		{
			editData(myFood);
		}
	}
	
	public static void editDataNonBlocking(Food food)
	{
		LocalBlockingThread thread = new LocalBlockingThread(food);
		thread.startThread();
	}

}
