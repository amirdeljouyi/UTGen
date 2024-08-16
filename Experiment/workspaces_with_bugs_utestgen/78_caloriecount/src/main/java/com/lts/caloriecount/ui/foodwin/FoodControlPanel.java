package com.lts.caloriecount.ui.foodwin;

import java.util.Comparator;

import javax.swing.DropMode;
import javax.swing.table.TableModel;

import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.table.ControlPanel;
import com.lts.swing.table.JTableUtils;
import com.lts.swing.table.rowmodel.RowModel;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListAdaptor;
import com.lts.util.notifyinglist.TableBridge;
import com.lts.util.notifyinglist.sorted.SortedListProxy;

@SuppressWarnings("serial")
public class FoodControlPanel extends ControlPanel
{
	private NotifyingList<Food> myList;
	private Food mySelectedFood;
	
	public static FoodControlPanel createPanel(PanelModes mode)
	{
		return new FoodControlPanel(mode);
	}
	
	public FoodControlPanel (PanelModes mode)
	{
		initialize(mode);
	}
	
	@Override
	protected void initialize(PanelModes mode)
	{
		FoodList list = CalorieCount.getData().getFoods();
		NotifyingListAdaptor<Food> nla = new NotifyingListAdaptor<Food>(list);
		Comparator comp = Food.FoodComparator;
		myList = new SortedListProxy<Food>(comp, nla);

		super.initialize(mode);
		myTable.getColumnModel().getColumn(0).setWidth(75);
		
		setupDoubleClick();
	}

	private void setupDoubleClick()
	{
		SimpleThreadedAction action = new SimpleThreadedAction() {

			@Override
			public void action() throws Exception
			{
				editFood();
			}
			
		};
		
		JTableUtils.setupDoubleClick(myTable, action);
	}
	
	protected void editFood()
	{
		int index = myTable.getSelectedRow();
		if (-1 != index)
			edit(index);
	}

	
	@Override
	protected void create(int index)
	{
		Food food = new Food();
		food = FoodWindow.editData(food);
		if (null != food)
		{
			if (-1 == index)
				myList.add(food);
			else
				myList.add(index, food);
		}
	}

	@Override
	protected void edit(int index)
	{
		Food food = (Food) myList.get(index);
		food = FoodWindow.editData(food);
		if (null != food)
			myList.set(index, food);
	}

	
	@Override
	protected LTSPanel createTablePanel(PanelModes mode)
	{
		LTSPanel panel = super.createTablePanel(mode);
		myTable.getColumnModel().getColumn(0).setPreferredWidth(75);
		myTable.getColumnModel().getColumn(0).setMaxWidth(75);
		myTable.setDropMode(DropMode.ON_OR_INSERT_ROWS);
		return panel;
	}
	
	@Override
	protected TableModel createTableModel()
	{
		FoodList list = CalorieCount.getData().getFoods();
		Comparator comp = Food.FoodComparator;
		SortedListProxy sortedList = new SortedListProxy<Food>(comp, list);
		
		RowModel rowModel = new FoodRowModel();
		TableBridge<Food> bridge = new TableBridge<Food>(sortedList, rowModel);
		return bridge;
	}

	@Override
	protected void delete(int index)
	{
		myList.remove(index);
	}

	@Override
	protected void select(int select)
	{
		mySelectedFood = myList.get(select);
	}
	
	
	public Food getSelectedFood()
	{
		return mySelectedFood;
	}
	
	
	public Object[][] buildButtonSpec()
	{
		Object[][] spec = {
				
		};
		
		
		return spec;
	}

}
