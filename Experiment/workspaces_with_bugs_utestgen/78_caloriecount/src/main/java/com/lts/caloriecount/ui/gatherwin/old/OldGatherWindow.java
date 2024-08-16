package com.lts.caloriecount.ui.gatherwin.old;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.caloriecount.ui.foodwin.FoodWindow;
import com.lts.caloriecount.ui.gatherwin.GatherListContainer;
import com.lts.swing.thread.BlockingThread;

@SuppressWarnings("serial")
public class OldGatherWindow 
	extends CalorieCountPanel 
	implements GatherListContainer
{
	static private OldGatherWindow ourInstance;
	
	private GatherListPanel myGatherListPanel;
	private GatherListPanel.Mode myMode;
	
	public static Food selectFood ()
	{
		Food food = null;
		
		try
		{
			JFrame frame = new JFrame();
			OldGatherWindow win = new OldGatherWindow(frame, GatherListPanel.Mode.Select);
			win.gatherDataBlocking(System.currentTimeMillis(), GatherListPanel.Mode.Select);
			food = win.getSelectedFood();
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
		
		return food;
	}
	
	
	static synchronized protected void initializeInstance()
	{
		try
		{
			if (null == ourInstance)
			{
				JFrame frame = new JFrame();
				ourInstance = new OldGatherWindow(frame, GatherListPanel.Mode.Gather);
				ourInstance.getWindow().setLocation(491, 301);
				ourInstance.getWindow().setSize(393, 431);
			}
		}
		catch (LTSException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	static public OldGatherWindow getInstance()
	{
		if (null == ourInstance)
		{
			initializeInstance();
		}
		
		return ourInstance;
	}


	public OldGatherWindow(JFrame frame, GatherListPanel.Mode mode) throws LTSException
	{
		initialize(frame, mode);
	}
	
	
	protected void initialize (JFrame frame, GatherListPanel.Mode mode) throws LTSException
	{
		myMode = mode;
		super.initialize(frame);
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
	
	public JPanel createCenterPanel() throws LTSException 
	{
		JFrame frame = (JFrame) myLTSRootPane.getComponent();
		myGatherListPanel = new GatherListPanel(frame, this, myMode);
		return myGatherListPanel;
	}

	protected void createFood()
	{
		Food food = new Food();
		food = FoodWindow.editData(food);
		if (null != food)
			CalorieCount.getData().getFoods().add(food);
		
		myGatherListPanel.refreshList();
	}

	public void gatherDataBlocking(Long time)
	{
		gatherDataBlocking(time, GatherListPanel.Mode.Gather);
	}


	public void gatherDataBlocking(Long time, GatherListPanel.Mode mode)
	{
		myGatherListPanel.refreshList();
		myGatherListPanel.setGatherTime(time);
		myGatherListPanel.setMode(mode);
		BlockingThread.staticDisplayAndWait(getWindow());
	}


	public void doneWithList()
	{
		Food food = myGatherListPanel.getSelection();
		if (null == food)
			cancelButtonPressed();
		else
			okButtonPressed();
	}
	
	
	public Food getSelectedFood()
	{
		return myGatherListPanel.getSelection();
	}
	
	protected Object[][][] SPEC_MENU_BAR = {
			SPEC_FILE_ACTION_MENU,
			SPEC_WINDOW_MENU
		};

	
	protected Object[][][] getActionSpec()
	{
		return SPEC_MENU_BAR;
	}
	
	public void setVisible(boolean nowVisible)
	{
		super.setVisible(nowVisible);
	}
}
