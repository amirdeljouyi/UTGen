package com.lts.caloriecount.ui.entry;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lts.LTSException;
import com.lts.caloriecount.data.Adjustment;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.caloriecount.ui.mealview.MealWindow;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.table.JTableUtils;

@SuppressWarnings("serial")
public class EntryListView extends CalorieCountPanel
{	
	public static final String WINDOW_PROPERTY_NAME = "EntryList";
	
	protected EntryListViewBuilder myBuilder;
	private JTable myTable;
	
	public EntryListView(JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	@Override
	protected String getViewName()
	{
		return "EntryView";
	}

	protected void initialize (JFrame frame) throws LTSException
	{
		setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
		myBuilder = new EntryListViewBuilder();
		myBuilder.build();
		
		setBottomPanelMode(BOTTOM_PANEL_CLOSE);
		
		super.initialize(frame);
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createTablePanel());
		panel.nextRow();
		panel.addHorizontal(createControlPanel());
		
		return panel;
	}

	
	private JPanel createTablePanel()
	{
		LTSPanel panel = new LTSPanel();

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


	private Entry getSelectedEntry()
	{
		int index = myTable.getSelectedRow();
		if (-1 == index)
			return null;
		else
			return (Entry) myBuilder.myModel.getRow(index);
	}
	
	
	private void editEntry()
	{
		Entry entry = getSelectedEntry();
		if (entry instanceof Meal)
		{
			Meal meal = (Meal) entry;
			MealWindow.edit(meal);
		}
		else if (entry instanceof Adjustment)
		{
			Adjustment adj = (Adjustment) entry;
			AdjustmentWindow.editAdjustment(adj);
		}
	}


	private JPanel createControlPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button;
		ActionListener action;
		
		//
		// Create meal
		//
		action = new SimpleThreadedAction() {
			public void action() {
				createMeal();
			}
		};
		button = new JButton("Create Meal");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		//
		// Create adjustment
		//
		action = new SimpleThreadedAction() {
			public void action() {
				createAdjustment();
			}
		};
		button = new JButton("Create Adjustment");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		//
		// Edit entry
		//
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
				deleteEntry();
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


	private void deleteEntry()
	{
		int[] selections = myTable.getSelectedRows();
		
		if (null == selections || selections.length < 1)
		{
			return;
		}
		
		String msg = "Delete entry(ies)?";
		
		int result = JOptionPane.showConfirmDialog(this, msg);
		if (result == JOptionPane.OK_OPTION)
		{
			myBuilder.myModel.remove(selections);
		}
	}


	private void createMeal()
	{
		MealWindow.createMeal();
	}


	protected void createEntry()
	{
		
	}


	public static void showView()
	{
		try
		{
			JFrame frame = new JFrame();
			new EntryListView(frame);
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			showException(e);
		}
	}


}
