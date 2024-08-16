package com.lts.caloriecount.ui.entry;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

public class LTSCheckBox extends JCheckBox
{
	private static final long serialVersionUID = 1L;
	private boolean myChecked;
	
	public LTSCheckBox()
	{
		super();
		initialize();
	}
	
	protected void initialize()
	{
		ItemListener listener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				myChecked = e.getStateChange() == ItemEvent.SELECTED;
			}
		};
		
		addItemListener(listener);
	}

	public LTSCheckBox(String text)
	{
		super(text);
		initialize();
	}

	public boolean isChecked()
	{
		return myChecked;
	}
	
	public void setChecked(boolean value)
	{
		myChecked = value;
		setSelected(value);
	}
}
