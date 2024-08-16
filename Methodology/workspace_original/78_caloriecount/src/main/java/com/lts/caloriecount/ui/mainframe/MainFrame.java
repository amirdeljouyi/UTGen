package com.lts.caloriecount.ui.mainframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.budget.Budget;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.caloriecount.ui.budget.BudgetWin;
import com.lts.caloriecount.ui.entry.EntryListView;
import com.lts.caloriecount.ui.gatherwin.CalorieCountGatherService;
import com.lts.event.SimpleActionListener;
import com.lts.event.SimpleThreadedAction;
import com.lts.pest.gatherer.TimeConstants;
import com.lts.swing.LTSPanel;
import com.lts.swing.combobox.ComboBoxUtils;
import com.lts.swing.combobox.SimpleComboBoxModel;
import com.lts.util.DateUtil;
import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingListListenerAdaptor;

@SuppressWarnings("serial")
public class MainFrame extends CalorieCountPanel
{
	private static class RefreshThread extends Thread
	{
		private MainFrame myMainFrame;
		
		public RefreshThread(MainFrame mainFrame)
		{
			super("Refresh Mainframe");
			myMainFrame = mainFrame;
		}
		
		public void run()
		{
			try
			{
				while (true)
				{
					myMainFrame.refresh();
					sleep(5 * 1000);				
				}
			}
			catch (InterruptedException e)
			{}
		}
	}

	@Override
	protected String getViewName()
	{
		return "Calorie Counter";
	}
	
	private static MainFrame ourInstance;
	private JTextField myStatusField;
	private JComboBox myCurrentCycleTime;
	private JComboBox myNewCycleTime;
	private JButton myStartButton;
	private JTextField myBudgetedField;
	private JTextField myTotalCalories;
	private JTextField myBalanceField;
	private JTextField myBalanceStatus;
	
	public static MainFrame getInstance() throws LTSException
	{
		if (null == ourInstance)
			createInstance();
		
		return ourInstance;
	}
	
	
	private synchronized static void createInstance() throws LTSException
	{
		if (null == ourInstance)
		{
			JFrame frame = new JFrame();
			ourInstance = new MainFrame(frame);
			RefreshThread thread = new RefreshThread(ourInstance);
			thread.start();
		}
	}


	protected Object[][][] getActionSpec()
	{
		return SPEC_MENU_BAR;
	}


	public MainFrame (JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	protected void initialize(JFrame frame) throws LTSException
	{
		super.initialize(frame);
		
		NotifyingListListenerAdaptor adapt = new NotifyingListListenerAdaptor() {
			public void anyEvent(ListEvent event) {
				refresh();
			}
		};
		
		CalorieCount.getData().getEntryList().addListener(adapt);
	}
	
	
	public static void showMainFrame()
	{
		try
		{
			JFrame frame = new JFrame();
			MainFrame mainFrame = new MainFrame(frame);
			mainFrame.refresh();
			frame.setVisible(true);

			RefreshThread thread = new RefreshThread(mainFrame);
			thread.start();
		}
		catch (LTSException e)
		{
			Application.showException(e);
	}
	}
	
	private JPanel createButtonPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button;
		ActionListener sml;
		
		button = new JButton("Budget");
		sml = new SimpleThreadedAction() {
			public void action() {
				showBudget();
			}
		};
		button.addActionListener(sml);
		panel.addButton(button,5);
		
		panel.nextRow();
		button = new JButton("Day View");
		sml = new SimpleThreadedAction() {
			public void action() {
				showDayView();
			}
		};
		button.addActionListener(sml);
		panel.addButton(button,5);
		
		panel.nextRow();
		
		button = new JButton("Entries");
		sml = new SimpleThreadedAction() {
			public void action() {
				showEntries();
			}
		};
		button.addActionListener(sml);
		panel.addButton(button, 5);
		
		panel.nextRow();
		
		button = new JButton("Gather Now");
		sml = new SimpleActionListener() {
			public void action() {
				gatherNow();
			}
		};
		button.addActionListener(sml);
		panel.addButton(button);
		
		panel.nextRow();
		
		button = new JButton("Foods");
		sml = new SimpleThreadedAction() {
			public void action() {
				showFoodList();
			}
		};
		button.addActionListener(sml);
		panel.addButton(button,5);
		
		panel.nextRow();
		
		button = new JButton("Quit");
		sml = new SimpleActionListener () {
			public void action ()
			{
				quit();
			}
		};
		button.addActionListener(sml);
		panel.addButton(button,5);
		
		return panel;
	}



	private void showEntries()
	{
		EntryListView.showView();
	}


	protected void showBudget()
	{
		Budget budget = CalorieCount.getData().getBudget();
		BudgetWin.edit(budget);
	}


	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addLabel(createNonButtonPanel(),5);
		panel.nextRow();
		panel.addButton(createButtonPanel(),5);
		
		//
		// set the size of the status field so that, when it's value changes
		// due the user turning recording on or off, the components on the window
		// do not move around.
		//
		Dimension dim = new Dimension(90,22);
		myStatusField.setPreferredSize(dim);
		
		return panel;
	}
	
	
	private LTSPanel createNonButtonPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		createControlButtons(panel);
		panel.nextRow();
		createCycleFields(panel);
		panel.nextRow();
		createStatusFields(panel);
		
		return panel;
	}
	
	
	private void createControlButtons(LTSPanel panel)
	{		
		JButton button;
		ActionListener act;
		
		//
		// Record/Update
		//
		act = new SimpleThreadedAction() {
			public void action() {
				startRecording();
			}
		};
		myStartButton = new JButton("Record");
		myStartButton.addActionListener(act);
		panel.addButton(myStartButton,5);
		
		//
		// Stop
		//
		act = new SimpleThreadedAction() {
			public void action() {
				stopRecording();
			}
		};
		button = new JButton("Stop");
		button.addActionListener(act);
		panel.addButton(button,5);
	}

	
	private void createStatusFields(LTSPanel panel)
	{
		JLabel label;
		Dimension dim = new Dimension(32,22);

		
		//
		// budgeted
		//
		label = new JLabel("Budgeted");
		panel.addLabel(label,5);
		myBudgetedField = new JTextField();
		myBudgetedField.setEditable(false);
		myBudgetedField.setPreferredSize(dim);
		panel.addLabel(myBudgetedField, 5);
		
		//
		// Total calories 
		//
		panel.nextRow();
		label = new JLabel("Calories so Far");
		panel.addLabel(label,5);
		myTotalCalories = new JTextField();
		myTotalCalories.setEditable(false);
		myTotalCalories.setPreferredSize(dim);
		panel.addLabel(myTotalCalories, 5);
		
		//
		// balance
		//
		panel.nextRow();
		label = new JLabel("Balance");
		panel.addLabel(label,5);		
		myBalanceField = new JTextField();
		myBalanceField.setEditable(false);
		dim = new Dimension(32,22);
		myBalanceField.setPreferredSize(dim);
		panel.addLabel(myBalanceField,5);

		//
		// Surplus or deficit?
		//
		panel.nextRow();
		label = new JLabel("Surplus/Deficit");
		panel.addLabel(label,5);
		myBalanceStatus = new JTextField();
		myBalanceStatus.setEditable(false);
		dim = new Dimension(100, 22);
		myBalanceStatus.setPreferredSize(dim);
		panel.addLabel(myBalanceStatus,5);
	}
		
		
	private void refreshBudget()
	{
		Budget budget = CalorieCount.getData().getBudget();
		long now = System.currentTimeMillis();
		int totalBudgeted = budget.getBudgetUpToNow();
		myBudgetedField.setText(Integer.toString(totalBudgeted));
		
		long start = DateUtil.startOfTodayTime();
		int totalConsumed = CalorieCount.getData().getMeals().totalCalories(start, now);
		myTotalCalories.setText(Integer.toString(totalConsumed));
		
		int difference = totalBudgeted - totalConsumed;
		if (difference > 0)
		{
			myBalanceStatus.setText("Surplus");
			myBalanceStatus.setForeground(Color.BLACK);
		}
		else
		{
			myBalanceStatus.setText("Deficit");
			myBalanceStatus.setForeground(Color.RED);
			difference = -1 * difference;
		}
	
		myBalanceField.setText(Integer.toString(difference));
	}

		
	private void createCycleFields(LTSPanel panel)
	{
		JLabel label;
				
		//
		// Cycle time that the user can edit
		//
		panel.nextRow();
		label = new JLabel("New Cycle Time: ");
		panel.addLabel(label,5);		
		ComboBoxModel cboxModel = new SimpleComboBoxModel(TimeConstants.SPEC_TIMES);
		myNewCycleTime = new JComboBox(cboxModel);
		panel.addLabel(myNewCycleTime,5);
		
		//
		// current cycle time --- the user cannot edit this
		//
		panel.nextRow();
		label = new JLabel("Current Cycle Time: ");
		panel.addLabel(label,5);		
		cboxModel = new SimpleComboBoxModel(TimeConstants.SPEC_TIMES);
		myCurrentCycleTime = new JComboBox(cboxModel);
		myCurrentCycleTime.setEditable(false);
		panel.addLabel(myCurrentCycleTime,5);
		
		//
		// are we recording?  If we are also show the next gather time
		//
		panel.nextRow();
		label = new JLabel("Next Question at");
		panel.addLabel(label,5);		
		myStatusField = new JTextField();
		myStatusField.setEditable(false);
		panel.addLabel(myStatusField,5);
		Font font = new Font("Ariel",Font.BOLD, 12);
		myStatusField.setFont(font);
	}
	
	
	private void stopRecording()
	{
		CalorieCount.getApp().stopGathering();
		refresh();
	}


	private void startRecording()
	{
		String s = (String) myNewCycleTime.getSelectedItem();
		Long cycleTime = TimeConstants.toDurationValue(s);
		CalorieCount app = CalorieCount.getApp();
		app.stopGathering();
		app.startGathering(cycleTime);
		
		refresh();
	}


	private void gatherNow()
	{
		CalorieCountGatherService.getInstance().gatherNow();
	}
	
	private static final SimpleDateFormat ourFormat =
		new SimpleDateFormat("HH:mm");
	
	private void refresh()
	{
		refreshBudget();
		refreshStatus();
		refreshCycleTime();
		refreshButtons();
		repaint();
	}


	private void refreshButtons()
	{
		boolean gathering = (null != CalorieCount.getApp().getNextPollTime());
		if (gathering)
		{
			myStartButton.setText("Update");
		}
		else
		{
			myStartButton.setText("Record");
		}
	}


	private void refreshStatus()
	{
		String status = "Not Recording";
		Long time = CalorieCount.getApp().getNextPollTime();
		if (null != time)
		{
			StringBuffer sb = new StringBuffer();
			sb.append (ourFormat.format(time));
			status = sb.toString();
		}
		
		myStatusField.setText(status);
	}


	private void refreshCycleTime()
	{
		CalorieCountData data = CalorieCount.getData();
		
		String s = "";
		long interval = data.getBudget().getInterval();
		if (interval > 0)
		{
			s = TimeConstants.toDurationString(interval);
		}

		ComboBoxUtils.setSelectedCaseless(s, myCurrentCycleTime);
		myCurrentCycleTime.setEditable(false);
		myCurrentCycleTime.setEnabled(false);
	}
	
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(301, 536);
	}

	
	public void showSize()
	{
		JOptionPane.showMessageDialog(this, 
				"" + myBalanceField.getSize());
		super.showSize();
	}
}