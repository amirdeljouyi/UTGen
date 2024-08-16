package com.lts.caloriecount.ui.mealview;

import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swing.JXDatePicker;

import com.lts.LTSException;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.caloriecount.ui.foodwin.FoodSelectWindow;
import com.lts.event.Callback;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.thread.BlockingThread;
import com.lts.util.DateUtil;

/**
 * Allow the user to edit a meal.
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class MealWindow extends CalorieCountPanel
{
	public static final String WINDOW_PROPERTY_NAME = "MealWindow";
	
	private JTextField myTimeField;
	private JTextField myFoodField;
	private Meal myMeal;
	private boolean myFirstLayout;
	private JXDatePicker myDateField;


	public void setMeal (Meal meal)
	{
		myMeal = meal;
	}
	
	public Meal getMeal ()
	{
		return myMeal;
	}
	
	
	public MealWindow(JFrame frame)
	{
		initialize(frame);
	}

	
	public void initialize(JFrame frame)
	{
		try
		{
			setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
			myFirstLayout = true;
			setBottomPanelMode(BOTTOM_PANEL_OK_CANCEL);
			super.initialize(frame);
			setupEnterKey();
		}
		catch (LTSException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(330, 200);
	}

	@Override
	protected String getViewName()
	{
		return "Edit Meal";
	}

	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label;
		JButton button;
		
		LTSPanel subpanel = new LTSPanel();
		label = new JLabel("Date");
		subpanel.addLabel(label,5);
		
		myDateField = new JXDatePicker();
		subpanel.addLabel(myDateField,5);
		
		label = new JLabel("Time");
		subpanel.addLabel(label,5);
		myTimeField = new JTextField();
		Dimension dim = myTimeField.getPreferredSize();
		dim.width = 60;
		myTimeField.setPreferredSize(dim);
		subpanel.addLabel(myTimeField,5);
		
		panel.addHorizontal(subpanel);
		panel.nextRow();
		
		subpanel = new LTSPanel();
		label = new JLabel("Food");
		subpanel.addLabel(label,5);
		myFoodField = new JTextField();
		dim = myFoodField.getPreferredSize();
		dim.width = 200;
		myFoodField.setPreferredSize(dim);
		subpanel.addLabel(myFoodField,5);
		myFoodField.setEditable(false);
		
		SimpleThreadedAction action = new SimpleThreadedAction() {
			public void action() {
				selectFood();
			}
		};
		button = new JButton("...");
		button.addActionListener(action);
		subpanel.addButton(button,5);
		
		panel.addHorizontal(subpanel);
		return panel;
	}


	private void selectFood()
	{
		Callback callback = new Callback() {
			public void callback (Object data) {
				FoodSelectWindow win = (FoodSelectWindow) data;
				performFoodSelected(win);
			}
		};
		
		FoodSelectWindow.launchSelectFood(callback);
	}
	
	
	private void performFoodSelected(FoodSelectWindow win)
	{
		Food food = win.getSelectedFood();
		if (null == myMeal)
		{
			myMeal = new Meal(-1, food, System.currentTimeMillis());
		}
		
		myMeal.setFood(food);
		updateFields();
	}

	public void validate()
	{
		if (myFirstLayout)
		{
			Dimension dim = getSize();
			dim.width += 1;
			setSize(dim);
		}
		
		super.validate();
		myFirstLayout = false;
	}
	
	
	protected void refresh()
	{
		updateFields();
		validate();
	}

	/**
	 * Edit a meal object.
	 * 
	 * <H2>NOTE</H2>
	 * This method blocks the calling thread, acting like a dialog window in this 
	 * respect.  If the calling thread is the main UI thread, the Java UI will 
	 * lock up!
	 * 
	 * <H2>Description</H2>
	 * This method makes a copy of the input object, edits it, and then returns 
	 * either null or an updated version of the copy.
	 * 
	 * @param meal The meal to edit.
	 * @return null, if the user quit or hit cancel, or a copy of the input object,
	 * updated with the new data from the user.
	 */
	public static Meal edit (Meal meal)
	{
		Meal copy = null;
		
		copy = (Meal) meal.clone();
		JFrame frame = new JFrame();
		MealWindow win = new MealWindow(frame);
		win.setMeal(copy);
		win.refresh();
		win.myFirstLayout = true;
		BlockingThread.staticDisplayAndWait(frame);
		if (MealWindow.RESULT_OK == win.getResult())
		{
			return copy;
		}
		else
		{
			return null;
		}
	}
	
	
	public static void editAndUpdate (Meal meal)
	{
		Meal copy = edit(meal);
		if (null != copy)
		{
			EntryList list = CalorieCount.getEntries();
			list.update(meal, copy);
		}
	}
	
	
	private static SimpleDateFormat ourFormat = new SimpleDateFormat("HH:mm");
	
	private void updateFields()
	{
		String s = ourFormat.format(myMeal.getTime());
		myTimeField.setText(s);
		
		StringBuffer sb = new StringBuffer();
		if (null != myMeal.getFood())
		{
			sb.append(myMeal.getFood().getName());
			sb.append(", ");
			sb.append(myMeal.getFood().getCalories());
		}
		
		s = sb.toString();
		myFoodField.setText(s);
	}
	
	
	@Override
	public void okButtonPressed()
	{
		updateMeal();
		super.okButtonPressed();
	}

	private void updateMeal()
	{
		String s = myTimeField.getText();
		if (null != s)
		{
			long current = myMeal.getTime();
			current = DateUtil.clearTime(current);
			long updated = DateUtil.timeStringToTime(s, current);
			myMeal.setTime(updated);
		}
	}

	public static void createMeal()
	{
		JFrame frame = new JFrame();
		MealWindow win = new MealWindow(frame);
		
		Meal meal = new Meal();
		meal.setTime(System.currentTimeMillis());
		win.setMeal(meal);
		
		Callback callback = new Callback() {
			public void callback(Object data) {
				MealWindow win = (MealWindow) data;
				createMeal(win);
			}
		};
		win.addSuccessListener(callback);
		win.updateFields();
		
		frame.setVisible(true);
	}

	private static void createMeal(MealWindow win)
	{
		Meal meal = win.getMeal();
		CalorieCount.getData().addMeal(meal);
	}
}
