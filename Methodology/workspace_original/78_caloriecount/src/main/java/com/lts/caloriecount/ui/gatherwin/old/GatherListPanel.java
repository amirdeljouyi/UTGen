package com.lts.caloriecount.ui.gatherwin.old;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.caloriecount.ui.foodwin.FoodTableModel;
import com.lts.caloriecount.ui.foodwin.FoodWindow;
import com.lts.caloriecount.ui.gatherwin.GatherListContainer;
import com.lts.event.SimpleAction;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleMouseAdapter;
import com.lts.swing.thread.BlockingThread;

/**
 * A panel that contains a list of foods.
 * 
 * <P>
 * The panel also contains buttons that allow the user to create, edit or delete
 * existing foods from the list.
 * </P>
 * 
 * <P>
 * The class is not intended to function on its own --- instead it should be contained
 * within a class that uses an instance of this class to display the list.  This
 * class communicates with the container via the 
 * </P>
 * 
 * <P>
 * The list functions in one of two modes: select or browse.  The mode is controlled
 * with the Mode property (set/getMode). 
 * </P>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class GatherListPanel extends ApplicationContentPanel
{
	public enum Mode
	{
		Gather,
		Select,
		Browse
	}
	
	private GatherListContainer myListContainer;
	private FoodTableModel myTableModel;
	private JTable myTable;
	private long myGatherTime;
	private Mode myMode;
	
	public GatherListPanel(JFrame frame, GatherListContainer container, Mode mode) throws LTSException
	{
		initialize(frame, container, mode);
	}
	
	
	private void initialize(JFrame frame, GatherListContainer container, Mode mode) throws LTSException
	{
		myMode = mode;
		super.initialize(frame);
		myListContainer = container;
	}
	
	public Dimension getWindowSize()
	{
		return new Dimension(368, 446);
	}


	@Override
	protected String getViewName()
	{
		return "Gather";
	}
	
	protected static OldGatherWindow ourWindow;
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createTablePanel(),5);
		panel.nextRow();
		panel.addHorizontal(createButtonPanel());
		return panel;
	}

	private JComponent createButtonPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		ActionListener act;
		JButton button;

		//
		// create a new food
		//
		act = new SimpleThreadedAction() {
			public void action() {
				createFood();
			}
		};
		button = new JButton("Create New Food");
		button.addActionListener(act);
		panel.addButton(button,5);
		
		//
		// refresh the list of foods
		//
		act = new SimpleAction() {
			public void action() {
				refreshList();
			}
		};
		button = new JButton("Refresh");
		button.addActionListener(act);
		panel.addButton(button,5);
		
		if (myMode == Mode.Gather)
		{
			//
			// didn't eat anything --- skip
			//
			act = new SimpleAction() {
				public void action() {
					skip();
				}
			};
			button = new JButton("Skip");
			button.addActionListener(act);
			panel.addButton(button,5);
			
			//
			// Stop monitoring
			//
			act = new SimpleAction() {
				public void action() {
					stopGathering();
				}
			};	
			button = new JButton("Stop");
			button.addActionListener(act);
			panel.addButton(button,5);
		}
		
		return panel;
	}

	protected void skip()
	{
		myListContainer.doneWithList();
	}

	public void refreshList()
	{
		myTableModel.reload();
	}

	private void createFood()
	{
		Food food = new Food();
		food = FoodWindow.editData(food);
		if (null != food)
		{
			FoodList foodList = CalorieCount.getData().getFoods();
			foodList.add(food);
		}
		
		refreshList();
	}

	protected void stopGathering()
	{
		CalorieCount.getApp().stopGathering();
	}

	private JScrollPane createTablePanel()
	{
		myTableModel = new GatherTableModel();
		myTable = new JTable(myTableModel);
		JScrollPane jsp = new JScrollPane(myTable);
		
		SimpleMouseAdapter sma = new SimpleMouseAdapter() {
			public void doubleClick(MouseEvent junk) {
				selectRow();
			}
		};
		
		myTable.addMouseListener(sma);
		
		return jsp;
	}

	protected void selectRow()
	{
		int row = myTable.getSelectedRow();
		if (-1 != row)
		{
			Food food = myTableModel.getFood(row);
			
			switch (myMode)
			{
				case Browse :
					editFood(food);
					break;
					
				case Gather :
					selectGatherFood(food);
					break;
					
				case Select :
					myListContainer.doneWithList();
					break;
					
				default :
					throw new RuntimeException("Unknown enum value: " + myMode);
			}
		}
	}


	private void editFood(Food food)
	{
		FoodWindow.editDataNonBlocking(food);
	}


	private void selectGatherFood(Food food)
	{
		CalorieCount.getData().getMeals().createMeal(food);
		myListContainer.doneWithList();
	}
	
	
	public void gatherDataBlocking(Long time, Mode mode)
	{
		myMode = mode;
		refreshList();
		setGatherTime(time);
		BlockingThread.staticDisplayAndWait(getWindow());
	}


	public void setGatherTime(Long time)
	{
		myGatherTime = time;
	}
	
	public long getGatherTime()
	{
		return myGatherTime;
	}


	public Food getSelection()
	{
		int index = myTable.getSelectedRow();
		if (-1 == index)
			return null;
		else
			return myTableModel.getFood(index);
	}


	public void setMode(Mode mode)
	{
		myMode = mode;
	}
}
