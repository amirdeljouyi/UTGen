package com.lts.caloriecount.ui.frequent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.swing.LTSPanel;

@SuppressWarnings("serial")
public class FrequentFoodPanel extends ApplicationContentPanel
{
	public static final String WINDOW_PROPERTY_NAME = "FrequentFoodPanel";
	
	protected FrequentFoodPanel (JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	public void initialize(JFrame frame) throws LTSException 
	{
		setWindowBasePropertyName(WINDOW_PROPERTY_NAME);
		super.initialize(frame);
	}
	
	@Override
	protected String getViewName()
	{
		return "Frequently Selected Foods";
	}

	
	static public FrequentFoodPanel showFrame() throws LTSException
	{
		JFrame frame = new JFrame();
		FrequentFoodPanel panel = new FrequentFoodPanel(frame);
		frame.setVisible(true);
		return panel;
	}
	
	@Override
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		panel.addFill(new FrequentControlPanel());
		return panel;
	}
	
	
	
}
