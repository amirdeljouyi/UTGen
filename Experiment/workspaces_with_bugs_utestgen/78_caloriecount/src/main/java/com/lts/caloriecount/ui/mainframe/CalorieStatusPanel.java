package com.lts.caloriecount.ui.mainframe;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.budget.Budget;
import com.lts.swing.LTSPanel;
import com.lts.util.DateUtil;

public class CalorieStatusPanel extends LTSPanel
{
	private static final long serialVersionUID = 1L;
	
	private JTextField myBudgetedField;
	private JTextField myTotalCalories;
	private JLabel mySurplusDeficitLabel;
	private JTextField mySurplusDeficitField;

	public CalorieStatusPanel ()
	{
		initialize();
	}
	
	
	private void initialize()
	{
		
		JLabel label;
		// Font font;
		
		//
		// budgeted
		//
		nextRow();
		label = new JLabel("Budgeted");
		addLabel(label,5);
		
		myBudgetedField = new JTextField();
		addLabel(myBudgetedField, 5);
		
		//
		// Total calories 
		//
		nextRow();
		label = new JLabel("Total so Far");
		addLabel(label,5);
		
		myTotalCalories = new JTextField();
		addLabel(myTotalCalories, 5);
		
		//
		// surplus/deficit
		//
		// font = new Font("ariel", Font.BOLD, 18);
		nextRow();
		mySurplusDeficitLabel = new JLabel("Surplus");
		// mySurplusDeficitLabel.setFont(font);
		addLabel(mySurplusDeficitLabel, 5);
		
		mySurplusDeficitField = new JTextField();
		// mySurplusDeficitField.setFont(font);
		addLabel(mySurplusDeficitField,5);
	}
	
	
	public void refresh()
	{
		Budget budget = CalorieCount.getData().getBudget();
		long now = System.currentTimeMillis();
		int totalBudgeted = budget.getBudgetUpToNow();
		myBudgetedField.setText(Integer.toString(totalBudgeted));
		
		int totalConsumed = updateTotal(now);
		
		int difference = totalBudgeted - totalConsumed;
		if (difference > 0)
		{
			mySurplusDeficitLabel.setText("Banked");
		}
		else
		{
			mySurplusDeficitLabel.setText("Deficit");
			mySurplusDeficitLabel.setForeground(Color.RED);
			difference = -1 * difference;
		}
	
		mySurplusDeficitField.setText(Integer.toString(difference));
		doLayout();
	}


	private int updateTotal(long now)
	{
		long start = DateUtil.startOfTodayTime();
		long end = DateUtil.startOfTomorrow();
		int total = CalorieCount.getData().getEntryList().totalCalories(start, end);
		myTotalCalories.setText(Integer.toString(total));
		return total;
	}
}
