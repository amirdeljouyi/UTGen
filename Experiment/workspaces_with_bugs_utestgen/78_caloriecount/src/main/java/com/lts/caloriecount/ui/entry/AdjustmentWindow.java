package com.lts.caloriecount.ui.entry;

import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.Adjustment;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.event.Callback;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;
import com.lts.ui.datetime.DateTimeField;

@SuppressWarnings("serial")
public class AdjustmentWindow extends CalorieCountPanel
{
	public static final String WINDOW_PROPERTY_NAME = "AdjustmentWindow";
	
	private JTextField myAmountField;
	private JTextField myDescriptionField;
	
	private Adjustment myOriginal;
	private Adjustment myCurrent;

	private DateTimeField myDateTimeField;
	
	public AdjustmentWindow(Window window) throws LTSException
	{
		initialize(window);
	}
	
	
	@Override
	protected String getViewName()
	{
		return "Adjustment Editor";
	}
	
	public void updateFields()
	{
		myAmountField.setText(Integer.toString(myOriginal.getCalories()));
		myDateTimeField.setTime(myOriginal.getTime());
		myDescriptionField.setText(myOriginal.getDescription());
	}
	
	public void updateCurrentObject()
	{
		myCurrent.setCalories(Integer.parseInt(myAmountField.getText()));
		myCurrent.setDescription(myDescriptionField.getText());
		myCurrent.setTime(myDateTimeField.getTime());
	}

	public Adjustment getOriginal()
	{
		return myOriginal;
	}

	public void setOriginal(Adjustment original)
	{
		myOriginal = original;
	}

	public Adjustment getCurrent()
	{
		return myCurrent;
	}

	public void setCurrent(Adjustment current)
	{
		myCurrent = current;
	}

	public static void edit(Adjustment adj, int index)
	{
		try
		{
			JFrame frame = new JFrame();
			AdjustmentWindow win = new AdjustmentWindow(frame);
			win.setOriginal(adj);
			Adjustment current = (Adjustment) adj.clone();
			win.setCurrent(current);
			win.updateFields();
			
			Callback callback = new Callback() {
				public void callback(Object data) {
					AdjustmentWindow win = (AdjustmentWindow) data;
					createAdjustment(win);
				}
			};
			
			win.addSuccessListener(callback);
			
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}
	
	private static void createAdjustment(AdjustmentWindow win)
	{
		EntryList list = CalorieCount.getData().getEntryList();
		Adjustment adjustment = win.getCurrent();
		list.add(adjustment);
	}
	
	
	private static void updateAdjustment(AdjustmentWindow win)
	{
		try
		{
			EntryList list = CalorieCount.getData().getEntryList();
			if (!list.update(win.getOriginal(), win.getCurrent()))
			{
				String msg =
					"The adjustment you were editing has been deleted.\n"
					+ "Do you want to add the new version to the list?";
				
				int result = JOptionPane.showConfirmDialog(win.getWindow(), msg);
				if (result == JOptionPane.OK_OPTION)
				{
					list.add(win.getCurrent());
				}
			}
		}
		catch (ApplicationException e)
		{
			Application.showException(e);
		}
	}


	public static void edit(Adjustment original, Callback callback)
	{
		try
		{
			JFrame frame = new JFrame();
			AdjustmentWindow win = new AdjustmentWindow(frame);
			win.setOriginal(original);
			Adjustment copy = (Adjustment) original;
			win.setCurrent(copy);
			win.updateFields();
			
			win.addSuccessListener(callback);
			
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}

	public static void editAdjustment(Adjustment original)
	{
		Callback callback = new Callback() {
			public void callback (Object data) {
				AdjustmentWindow win = (AdjustmentWindow) data;
				updateAdjustment(win);
			}
		};
		
		edit(original, callback);
	}

	public static void createAjustment()
	{
		Adjustment original = new Adjustment();
		
		Callback callback = new Callback() {
			public void callback (Object data) {
				AdjustmentWindow win = (AdjustmentWindow) data;
				performCreate(win);
			}
		};
		
		edit(original, callback);
	}


	protected static void performCreate(AdjustmentWindow win)
	{
		EntryList list = CalorieCount.getData().getEntryList();
		list.add(win.getCurrent());
	}
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();

		myDateTimeField = new DateTimeField();
		panel.addLabel(myDateTimeField);
		panel.nextRow();
		panel.addLabel(buildOtherSubpanel());
		
		return panel;
	}


	private JPanel buildOtherSubpanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label;
		Dimension dim;
		
		label = new JLabel("Calories");
		panel.addLabel(label,5);
		myAmountField = new JTextField();
		SwingUtils.setPreferredWidth(myAmountField, 60);
		panel.addLabel(myAmountField,5);
		
		panel.nextRow();
		
		label = new JLabel("Description");
		panel.addLabel(label,5);
		dim = new Dimension(100,20);
		myDescriptionField = new JTextField();
		myDescriptionField.setPreferredSize(dim);
		panel.addLabel(myDescriptionField,5);
		
		return panel;
	}
	
	
	protected void initialize(Window window) throws LTSException
	{
		setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
		setBottomPanelMode(BOTTOM_PANEL_OK_CANCEL);
		setupEnterKey();
		super.initialize(window);
	}
	
	/**
	 * Pass listeners the newly created adjustment instead of the window.
	 */
	public void tellSuccessListeners(Object o)
	{
		mySuccessListeners.fire(myCurrent);
	}
	
	
	public void okButtonPressed()
	{
		updateCurrentObject();
		super.okButtonPressed();
	}
}
