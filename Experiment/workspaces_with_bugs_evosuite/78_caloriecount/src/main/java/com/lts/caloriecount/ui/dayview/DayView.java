package com.lts.caloriecount.ui.dayview;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdesktop.swing.JXDatePicker;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.Adjustment;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.caloriecount.ui.entry.AdjustmentWindow;
import com.lts.caloriecount.ui.entry.CreateAdjustmentView;
import com.lts.caloriecount.ui.mealview.MealWindow;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;
import com.lts.swing.table.JTableUtils;
import com.lts.util.DateUtil;

@SuppressWarnings("serial")
public class DayView extends CalorieCountPanel
{
	private JTable myTable;
	private JXDatePicker myDate;
	private DayViewListBuilder myBuilder;
	private JTextField myBalanceField;
	private JTextField myBudgetField;
	private JTextField mySpentField;
	
	public DayView(JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	protected void initialize (JFrame frame) throws LTSException
	{
		myBuilder = new DayViewListBuilder();
		long start = DateUtil.getStartOfToday();
		long end = DateUtil.getStartOfTomorrow();
		myBuilder.build(start, end);
		setWindowBasePropertyName("DayView");
		setBottomPanelMode(BOTTOM_PANEL_CLOSE);
		
		DayViewRefreshThread.launch(this, frame);
		
		super.initialize(frame);
	}
	
	
	public static DayView displayWindow()
	{
		DayView win = null;
		
		try
		{
			JFrame frame = new JFrame();
			win = new DayView(frame);
			long today = DateUtil.getStartOfToday();
			win.setDate(today);
			win.refresh();
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
		
		return win;
	}
	
	protected void setDate(long date)
	{
		myDate.setDateInMillis(date);
	}


	public void refresh()
	{
		long start = myDate.getDateInMillis();
		start = DateUtil.startOfDay(start);
		long stop = DateUtil.startOfNextDay(start);
		
		myBuilder.setRange(start, stop);
		
		myTable.getColumnModel().getColumn(0).setMaxWidth(50);
		myTable.getColumnModel().getColumn(1).setMaxWidth(75);
		
		int budget = CalorieCount.getBudgetUpToNow();
		myBudgetField.setText(Integer.toString(budget));
		
		int spent = CalorieCount.getSpentUpToNow();
		mySpentField.setText(Integer.toString(spent));
		
		int balance = budget - spent;
		myBalanceField.setText(Integer.toString(balance));
	}


	@Override
	protected String getViewName()
	{
		return "Entries";
	}

	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addHorizontal(createInfoPanel());
		panel.nextRow();
		panel.addFill(createTablePanel());
		panel.nextRow();
		panel.addHorizontal(createControlPanel());
		
		return panel;
	}


	private JComponent createInfoPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Bugeted");
		SwingUtils.setBold(label);
		panel.addLabel(label,5);
		
		myBudgetField = new JTextField();
		SwingUtils.setPreferredWidth(myBudgetField, 50);
		panel.addLabel(myBudgetField,5);
		
		label = new JLabel("Spent");
		SwingUtils.setBold(label);
		panel.addLabel(label,5);
		
		mySpentField = new JTextField();
		SwingUtils.setPreferredWidth(mySpentField, 50);
		panel.addLabel(mySpentField,5);
		
		label = new JLabel("Balance");
		SwingUtils.setBold(label);
		panel.addLabel(label,5);
		
		myBalanceField = new JTextField();
		SwingUtils.setPreferredWidth(myBalanceField, 50);
		panel.addLabel(myBalanceField,5);
		
		return panel;
	}


	private JPanel createTablePanel()
	{
		LTSPanel panel = new LTSPanel();

		long start = myDate.getDateInMillis();
		long stop = DateUtil.startOfNextDay(start);
		myBuilder.build(start, stop);
		myTable = new JTable(myBuilder.myModel);
		JScrollPane jsp = new JScrollPane(myTable);
		panel.addFill(jsp,5);
		
		
		SimpleThreadedAction action = new SimpleThreadedAction() {
			public void action () {
				editEntry();
			}
		};
		JTableUtils.setupDoubleClick(myTable, action);
		
		return panel;
	}
	
	
	private void editEntry()
	{
		Entry entry = getSelectedEntry();
		if (null == entry)
			return;
		
		if (entry instanceof Meal)
		{
			Meal meal = (Meal) entry;
			MealWindow.editAndUpdate(meal);
		}
		else
		{
			Adjustment adjustment = (Adjustment) entry;
			AdjustmentWindow.editAdjustment(adjustment);
		}
	}


	private Entry getSelectedEntry()
	{
		Entry entry = null;
		int index = myTable.getSelectedRow();
		if (-1 != index)
		{
			entry = (Entry) myBuilder.mySortedList.get(index);
		}

		return entry;
	}
	
	private JPanel createControlPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button;
		ActionListener action;
		
		action = new SimpleThreadedAction() {
			public void action() {
				createMeal();
			}
		};
		button = new JButton("Create Meal");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				createAdjustment();
			}
		};
		button = new JButton("Create Adjustment");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				editEntry();
			}
		};
		button = new JButton("Edit");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				deleteMeal();
			}
		};
		button = new JButton("Delete");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		return panel;
	}

	private void createAdjustment()
	{
		CreateAdjustmentView.createAjustment();
	}


	protected void deleteMeal()
	{
		int rows[] = myTable.getSelectedRows();
		if (null == rows || rows.length < 1)
			return;
		
		String message = "Delete Meal(s)?";
		int response = JOptionPane.showConfirmDialog(this, message);
		if (JOptionPane.OK_OPTION != response)
			return;

		myBuilder.myModel.remove(rows);
	}


	private void createMeal()
	{
		MealWindow.createMeal();
	}


	public JPanel createTopPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		myDate = new JXDatePicker();
		panel.addLabel(myDate);
		
		ActionListener listener = new SimpleThreadedAction() {
			public void action() {
				refresh();
			}
		};
		
		myDate.addActionListener(listener);
		
		return panel;
	}
	
	@Override
	public Dimension getDefaultWindowSize()
	{
		return new Dimension(513, 430);
	}
}
