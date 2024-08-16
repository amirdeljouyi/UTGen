package com.lts.caloriecount.ui.entry;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.Adjustment;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.event.Callback;
import com.lts.swing.LTSPanel;
import com.lts.util.StringUtils;

@SuppressWarnings("serial")
public class CreateAdjustmentView extends CalorieCountPanel
{
	public static final String WINDOW_PROPERTY_NAME = "CreateAdjustmentView";
	
	private JTextField myValueField;
	private JTextArea myDescriptionField;
	private LTSCheckBox mySetBalance;
	private LTSCheckBox mySetValue;


	public CreateAdjustmentView(Container cont) throws LTSException
	{
		initialize(cont);
	}
	
	@Override
	protected String getViewName()
	{
		return "Create Adjustment";
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		Font font;
		ButtonGroup group = new ButtonGroup();
		
		mySetBalance = new LTSCheckBox("Set Balance");
		font = mySetBalance.getFont();
		font = new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize());
		mySetBalance.setFont(font);
		group.add(mySetBalance);
		panel.addLabel(mySetBalance,5);
		
		panel.nextRow();
		mySetValue = new LTSCheckBox("Set Value");
		mySetValue.setFont(font);
		group.add(mySetValue);
		panel.addLabel(mySetValue,5);
		
		panel.nextRow();
		
		JLabel label = new JLabel("Calories");
		label.setFont(font);
		panel.addLabel(label,5);
		
		myValueField = new JTextField();
		myValueField.setPreferredSize(new Dimension(100,20));
		panel.addLabel(myValueField,5);
		
		panel.nextRow();
		label = new JLabel("Description");
		label.setFont(font);
		panel.addLabel(label,5);
		
		panel.nextRow();
		myDescriptionField = new JTextArea();
		JScrollPane jsp = new JScrollPane(myDescriptionField);
		panel.addFill(jsp);
		panel.setMaxWidth(jsp);
		
		
		return panel;
	}
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(308, 264);
	}


	@Override
	public void initialize(Container container) throws LTSException
	{
		setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
		setBottomPanelMode(BOTTOM_PANEL_OK_CANCEL);
		setupEnterKey();
		super.initialize(container);
	}


	public static void createAjustment()
	{
		try
		{
			JFrame frame = new JFrame();
			CreateAdjustmentView view = new CreateAdjustmentView(frame);
			
			Callback callback = new Callback() {
				public void callback(Object data) {
					CreateAdjustmentView view = (CreateAdjustmentView) data;
					performCreateAdjustment(view);
				}
			};
			
			view.addSuccessListener(callback);
			
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			showException(e);
		}
	}

	private static void performCreateAdjustment(CreateAdjustmentView view)
	{
		CalorieCountData data = CalorieCount.getData();
		EntryList list = data.getEntryList();
		Adjustment adj = new Adjustment();
		
		int value = Integer.parseInt(view.myValueField.getText());
		String description = view.myDescriptionField.getText();
		description = StringUtils.trim(description);
		
		if (view.mySetValue.isChecked())
		{
			if (null == description)
				description = "Adjustment";
		}
		else
		{
			if (null == description)
				description = "Set Balance";
			
			int total = list.getTotalUpToNow();
			int balance = data.getBudget().getBudgetUpToNow() - total; 
			value = balance - value;
		}
		
		adj.setCalories(value);
		adj.setDescription(description);
		adj.setTime(System.currentTimeMillis());
		list.add(adj);
	}
	
	

}
