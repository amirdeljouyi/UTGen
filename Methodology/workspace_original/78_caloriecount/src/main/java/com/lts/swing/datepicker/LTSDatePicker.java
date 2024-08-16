package com.lts.swing.datepicker;

import java.awt.Dimension;
import java.util.Date;

import org.jdesktop.swing.JXDatePicker;

@SuppressWarnings("serial")
public class LTSDatePicker extends JXDatePicker
{
	protected Dimension myPreferredSize;
	
	public Dimension getPreferredSize()
	{
		if (null == myPreferredSize)
			return super.getPreferredSize();
		else
			return myPreferredSize;
	}
	
	public void setPreferredSize(Dimension dim)
	{
		myPreferredSize = dim;
	}
	
	
	public void initializePreferredSize()
	{
		Date oldDate = getDate();
		Date temp = new Date();
		setDate(temp);
		Dimension dim = getPreferredSize();
		setDate(oldDate);
		setPreferredSize(dim);
	}
}
