package com.lts.caloriecount.ui.foodwin;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.util.Comparator;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.caloriecount.ui.dnd.DragAndDropHandler;
import com.lts.caloriecount.ui.frequent.JTableSelectPanel;
import com.lts.event.Callback;
import com.lts.event.SimpleAction;
import com.lts.swing.table.JTableUtils;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.TableBridge;
import com.lts.util.notifyinglist.sorted.SortedListProxy;

@SuppressWarnings("serial")
public class FoodSelectWindow extends JTableSelectPanel
{
	public static final String WINDOW_PROPERTY_NAME = "FoodSelectWindow";
	
	protected JTable myTable;
	protected TableBridge<Food> myTableModel;
	protected NotifyingList<Food> myList;
	
	protected FoodSelectWindow (Window window) throws LTSException
	{
		initialize(window);
	}
	
	
	protected void initialize(Window frame) throws LTSException
	{
		setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
		myShowOtherButton = false;
		myShowCreateButton = false;
		super.initialize(frame);
	}
	
	@Override
	public Dimension getDefaultWindowSize()
	{
		return new Dimension(500, 200);
	}
	
	
	protected Object[][] buildButtonMap()
	{
		SimpleAction selectAction = new SimpleAction() {
			public void action () {
				performSelect();
			}
		};
		
		SimpleAction createAction = new SimpleAction() {
			public void action() {
				createEntry();
			}
		};
		
		SimpleAction cancelAction = new SimpleAction() {
			public void action() {
				cancel();
			}
		};
		
		
		Object[][] spec = new Object[][] {
				{ "Select", selectAction },
				{ "Create", createAction },
				{ "Cancel", cancelAction }
		};
		
		return spec;
	}
	


	
	@Override
	protected DragAndDropHandler createDragAndDropHandler()
	{
		FoodList list = CalorieCount.getData().getFoods();
		return new FoodSelectDragAndDrop(list);
	}

	private void createEntry()
	{
		JDialog dialog = new JDialog(getWindow(), ModalityType.APPLICATION_MODAL);
		
		Food food = FoodWindow.createFood(dialog);
		if (null != food)
		{
			food = CalorieCount.getData().getFoods().createFood(food);
			selectEntry(food);
			getLTSRootPane().getWindow().setVisible(false);
		}
	}

	@Override
	protected JTable createTable()
	{
		Comparator comp = Food.FoodComparator;
		myList = new SortedListProxy<Food>(comp, CalorieCount.getData().getFoods());
		FoodRowModel rowModel = new FoodRowModel();
		TableBridge<Food> bridge = new TableBridge<Food>(myList, rowModel);
		myTable = new JTable(bridge);
		
		JTableUtils.setColumnWidth(myTable, 0, 75);
		
		return myTable;
	}

	@Override
	protected void selectEntry(int selection)
	{
		Food food = myList.get(selection);
		selectEntry(food);
	}
	
	
	private void selectEntry (Food food)
	{
		mySelection = food;
		tellSuccessListeners();
	}

	@Override
	protected void selectOtherEntry()
	{
		throw new RuntimeException("invalid operation");
	}

	@Override
	protected String getViewName()
	{
		return "Select Food";
	}

	static protected FoodSelectWindow ourInstance;
	
	synchronized static protected void initializeInstance()
	{
		try
		{
			if (null == ourInstance)
			{
				JFrame frame = new JFrame();
				FoodSelectWindow win = new FoodSelectWindow(frame);
				ourInstance = win;
			}
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}
	
	static public FoodSelectWindow getInstance()
	{
		if (null == ourInstance || CalorieCount.getProps().reloadWindows())
			initializeInstance();
		
		return ourInstance;
	}
	
	
	static public void displayWindow()
	{
		getInstance().openWindow();
	}


	public static void launchSelectFood(Callback callback)
	{
		try
		{
			JFrame frame = new JFrame();
			FoodSelectWindow win = new FoodSelectWindow(frame);
			win.instanceSelectFood(callback);
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}
	
	
	@Override
	protected void tellSuccessListeners()
	{
		Food food = getSelectedFood();
		if (null != food)
			mySuccessListeners.fire(this);
	}


	private void instanceSelectFood(Callback callback)
	{
		addSuccessListener(callback);
		getWindow().setVisible(true);
	}


	static public Food staticSelectFood(Window owner)
	{
		Food selection = null;
		
		try
		{
			JDialog dialog = new JDialog(owner, ModalityType.DOCUMENT_MODAL);
			FoodSelectWindow win = new FoodSelectWindow(dialog);
			dialog.setVisible(true);
			
			selection =  win.getSelectedFood();
		}
		catch (LTSException e)
		{
			Application.showException(e);
			selection = null;
		}
		
		
		return selection;
	}
	
	
	public Food getSelectedFood()
	{
		return (Food) mySelection;
	}
}
