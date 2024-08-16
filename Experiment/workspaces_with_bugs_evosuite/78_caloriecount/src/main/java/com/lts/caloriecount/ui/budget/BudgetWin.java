package com.lts.caloriecount.ui.budget;

import java.awt.Container;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.caloriecount.data.budget.Budget;
import com.lts.caloriecount.data.budget.TimeOfDay;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.pest.gatherer.TimeConstants;
import com.lts.swing.LTSPanel;
import com.lts.swing.thread.BlockingThread;
import com.lts.util.StringUtils;

@SuppressWarnings("serial")
public class BudgetWin extends CalorieCountPanel
{
	public BudgetWin (JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	private Budget myBudget;
	private JTextField myCalorieField;
	private JComboBox myStartCombo;
	private JComboBox myEndCombo;
	
	public Budget getBudget()
	{
		return myBudget;
	}
	
	public void setBudget(Budget budget)
	{
		myBudget = budget;
	}
	
	public void initialize(Container comp) throws LTSException
	{
		setBottomPanelMode(BOTTOM_PANEL_OK_CANCEL);
		super.initialize(comp);
		getWindow().setSize(280, 175);
		setupEnterKey();
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel reference;
		JLabel label;
		
		//
		// calories per hour
		//
		label = new JLabel("Calories per hour");
		panel.addLabel(label,5);		
		reference = new JLabel("wwwwww");
		myCalorieField = new JTextField();
		Dimension dim = myCalorieField.getPreferredSize();
		dim.width = reference.getPreferredSize().width;
		myCalorieField.setSize(dim);
		// myCalorieField.setText("wwwwww");
		panel.addHorizontal(myCalorieField,5);
		
		//
		// start of day
		//
		panel.nextRow();
		label = new JLabel("Day starts at");
		panel.addLabel(label,5);
		myStartCombo = new JComboBox(TimeConstants.SPEC_TIME_OF_DAY);
		myStartCombo.setEditable(true);
		panel.addLabel(myStartCombo,5);
		
		//
		// end of day
		//
		panel.nextRow();
		label = new JLabel("Day ends at ");
		panel.addLabel(label,5);
		myEndCombo = new JComboBox(TimeConstants.SPEC_TIME_OF_DAY);
		myEndCombo.setEditable(true);
		panel.addLabel(myEndCombo,5);
		
		return panel;
	}
	
	@Override
	protected String getViewName()
	{
		return "Budget";
	}
	
	
	public void okButtonPressed()
	{
		if (updateObject(true))
		{
			super.okButtonPressed();
		}
	}

	private boolean updateObject(boolean validate)
	{
		TimeOfDay start = parseValidate(myStartCombo, validate, "start time");
		if (null == start)
			return false;
		
		TimeOfDay end = parseValidate(myEndCombo, validate, "end time");
		if (null == end)
			return false;
		
		Integer ival = parseValidateInt(myCalorieField, validate, "calories per hour");
		if (null == ival)
			return false;
		
		myBudget.setStartOfDay(start);
		myBudget.setEndOfDay(end);
		myBudget.setCaloriesPerHour(ival);
		
		return true;
	}

	private Integer parseValidateInt(JTextField field, boolean validate, String name)
	{
		Integer ival = null;
		
		String s = StringUtils.trim(field.getText());
		try
		{
			ival = new Integer(s);
		}
		catch (NumberFormatException e)
		{}
		
		if (null == ival)
		{
			if (validate)
			{
				String message = "Invalid " + name + ":" + s;
				JOptionPane.showMessageDialog(this, message);
			}
		}
		
		return ival;
	}

	private TimeOfDay parseValidate(JComboBox comboBox, boolean validate, String fieldName)
	{
		String s = (String) comboBox.getSelectedItem();
		s = StringUtils.trim(s);
		if (null == s)
		{
			if (validate)
			{
				String message = "Please enter a value for " + fieldName;
				JOptionPane.showMessageDialog(this, message);
			}
			
			return null;
		}
		
		String[] fields = s.split(":");
		
		if (fields.length < 1)
		{
			if (validate)
			{
				String message = "Invalid time string: " + s;
				JOptionPane.showMessageDialog(this, message);
			}
			
			return null;
		}
		
		int hour = Integer.parseInt(fields[0]);
		int minute = 0;
		
		if (fields.length > 1)
		{
			minute = Integer.parseInt(fields[1]);
		}
		
		return new TimeOfDay(hour, minute);
	}
	
	
	
	public static Budget edit (Budget budget)
	{
		Budget result = null;
		
		try
		{
			JFrame frame = new JFrame();
			BudgetWin win = new BudgetWin(frame);
			win.setBudget(budget);
			win.updateFields();
			// frame.doLayout();
			BlockingThread.staticDisplayAndWait(frame);
			
			if (win.getResult() == BudgetWin.RESULT_OK)
			{
				result = win.getBudget();
				budget.copyFrom(result);
			}
			
		}
		catch (LTSException e)
		{
			Application.showException(e);
			budget = null;
		}
		
		return result;
	}

	private void updateFields()
	{
		String s;
		
		DecimalFormat fmt = new DecimalFormat("#,###");
		s = fmt.format(myBudget.getCaloriesPerHour());
		myCalorieField.setText(s);
		
		myStartCombo.setSelectedItem(myBudget.getStartOfDay().toString());
		myEndCombo.setSelectedItem(myBudget.getEndOfDay().toString());
	}
	
	
	public static BudgetWin displayWindow()
	{
		try
		{
			JFrame frame = new JFrame();
			BudgetWin win = new BudgetWin(frame);
			frame.setVisible(true);
			return win;
		}
		catch (LTSException e)
		{
			showException(e);
			return null;
		}
	}
	
}
