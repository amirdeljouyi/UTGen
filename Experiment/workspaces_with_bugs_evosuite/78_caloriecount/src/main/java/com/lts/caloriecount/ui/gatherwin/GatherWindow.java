package com.lts.caloriecount.ui.gatherwin;

import java.awt.Dimension;
import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.caloriecount.data.frequent.FrequentFoodList;
import com.lts.caloriecount.swing.ApplicationAction;
import com.lts.caloriecount.ui.dnd.DragAndDropHandler;
import com.lts.caloriecount.ui.foodwin.FoodSelectWindow;
import com.lts.caloriecount.ui.frequent.FrequentRowModel;
import com.lts.caloriecount.ui.frequent.FrequentTableDragAndDrop;
import com.lts.caloriecount.ui.frequent.JTableSelectPanel;
import com.lts.event.Callback;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;
import com.lts.swing.table.rowmodel.RowModel;
import com.lts.ui.datetime.DateTimeField;
import com.lts.util.notifyinglist.TableBridge;
import com.lts.util.notifyinglist.sorted.SortedListProxy;

/**
 * Select a frequently selected food from the current table of foods.
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class GatherWindow extends JTableSelectPanel
{
	public static final String WINDOW_PROPERTY_NAME = "GatherWindow";
	
	protected static GatherWindow ourInstance;

	static public GatherWindow getInstance()
	{
		if (null == ourInstance)
		{
			initializeInstance();
		}
		
		return ourInstance;
	}

	synchronized static protected void initializeInstance()
	{
		
		JFrame frame = new JFrame();
		ourInstance = new GatherWindow(frame);
	}
	
	public GatherWindow (Window window)
	{
		initialize(window);
	}
	
	
	protected void initialize(Window frame)
	{
		try
		{
			myHeadingString = "What have you eaten recently?";
			setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
			
			buildButtonMap();
			
			myAllowDrag = true;
			super.initialize(frame);
			configureTable();
		}
		catch (LTSException e)
		{
			throw new RuntimeException(e);
		}
	}
		
	
	protected Dimension getDefaultWindowSize()
	{
		return new Dimension(500,275);
	}

	protected Object[][] buildButtonMap()
	{
		ApplicationAction selectAction = new ApplicationAction() {
			public void action() {
				selectFood();
			}
		};
		
		ApplicationAction otherAction = new ApplicationAction() {
			public void action () {
				selectOtherEntry();
			}
		};
		
		ApplicationAction skipAction = new ApplicationAction() {
			public void action() {
				cancel();
			}
		};
		
		ApplicationAction stopAction = new ApplicationAction() {
			public void action () {
				stopGathering();
			}
		};
		
		myButtonActionMap = new Object[][] {
			{ "Select", selectAction },
			{ "Other",  otherAction },
			{ "Skip",   skipAction },
			{ "Stop",   stopAction }
		};
		
		return myButtonActionMap;
	}
	
	protected TableBridge<FrequentFood> myModel;

	protected void stopGathering()
	{
		CalorieCount.getApp().stopGathering();
		closeWindow();
	}
	
	
	@Override
	protected JTable createTable()
	{
		myTable = new JTable();
		configureTable();
		return myTable;
	}

	private void configureTable()
	{			
		myModel = buildTableModel();
		myTable.setModel(myModel);
	}
	
	
	private TableBridge<FrequentFood> buildTableModel()
	{
		FrequentFoodList list = CalorieCount.getData().getFrequentFoods();
		Comparator comp = FrequentFood.FrequencyComparator;
		SortedListProxy<FrequentFood> sorted = new SortedListProxy(comp, list);
		RowModel rowModel = new FrequentRowModel();
		return new TableBridge<FrequentFood>(sorted, rowModel);
	}

	@Override
	protected void selectOtherEntry()
	{
		Callback callback = new Callback() {
			public void callback (Object data) {
				FoodSelectWindow fsw = (FoodSelectWindow) data;
				processOtherFoodSelected(fsw.getSelectedFood());
			}
		};
		FoodSelectWindow.launchSelectFood(callback);
	}

	private void processOtherFoodSelected(Food food)
	{
		if (null != food)
		{
			FrequentFoodList list = CalorieCount.getData().getFrequentFoods();
			FrequentFood freq = list.getEntryForFood(food);
			if (null == freq)
			{
				freq = list.createFrequentFood(food);
			}
			
			selectFood(food);			
			closeWindow();
			tellSuccessListeners();
		}
	}
	
	
	static public GatherWindow createInstance(Window owner)
	{
		JFrame frame = new JFrame();
		GatherWindow win = new GatherWindow(frame);
		return win;
	}
	
	
	public FrequentFood selectFood()
	{
		getWindow().setVisible(true);
		return (FrequentFood) getSelection();
	}
	
	
	public void asyncGather ()
	{
		refreshFields();
		getWindow().setVisible(true);
	}
	
	
	private void refreshFields()
	{
		myBalanceField.setText(Integer.toString(CalorieCount.getBalance()));
		long now = System.currentTimeMillis();
		myDateTimeField.setTime(now);
	}

	@Override
	protected String getViewName()
	{
		return "Select Food";
	}

	@Override
	protected DragAndDropHandler createDragAndDropHandler()
	{
		return new FrequentTableDragAndDrop();
	}

	private void selectFood (Food food)
	{
		CalorieCount app = CalorieCount.getApp();
		long time = myDateTimeField.getTime();
		app.createMeal(food, time);
	}

	static public SimpleDateFormat ourTimeStringFormat =
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	public String getTimeString()
	{
		String s = ourTimeStringFormat.format(myDateTimeField.getTime());
		return s;
	}

	@Override
	protected void selectEntry(int selection)
	{
		FrequentFood freq = (FrequentFood) myModel.getRow(selection);
		selectFood(freq.getFood());
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addHorizontal(createDateTimePanel());
		panel.nextRow();
		panel.addFill(createTablePanel());
		
		return panel;
	}

	public SimpleDateFormat ourTimeFormat = new SimpleDateFormat("HH:mm:ss");
	private DateTimeField myDateTimeField;
	private JTextField myBalanceField;

	
	private JPanel createDateTimePanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Balance");
		SwingUtils.setBold(label);
		panel.addLabel(label,5);
		
		myBalanceField = new JTextField();
		myBalanceField.setEditable(false);
		SwingUtils.setPreferredWidth(myBalanceField, 50);
		panel.addLabel(myBalanceField,5);
		
		myDateTimeField = new DateTimeField();
		panel.addLabel(myDateTimeField,5);
		
		return panel;
	}
	
	
	public void grabFocus()
	{
		refreshFields();
		super.grabFocus();
	}
}
