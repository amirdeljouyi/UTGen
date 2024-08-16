package com.lts.ui.datetime;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.swing.JXDatePicker;

import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;
import com.lts.util.DateUtil;
import com.lts.util.StringUtils;

@SuppressWarnings("serial")
public class DateTimeField extends LTSPanel
{
	private JXDatePicker myDateField;
	private JTextField myTimeField;
	
	public int timeFieldSize = 100;
	public boolean boldLabels = true;
	public boolean boldText = false;
	private JLabel myDateLabel;
	private JLabel myTimeLabel;
	
	public DateTimeField()
	{
		initialize();
	}
	
	
	public void initialize()
	{
		createFields();
		configureFields();
	}

	protected void configureFields()
	{
		Dimension dim = myTimeField.getPreferredSize();
		dim.width = timeFieldSize;
		myTimeField.setPreferredSize(dim);
		
		if (boldText)
		{
			SwingUtils.setBold(myTimeField);
		}
		
		if (boldLabels)
		{
			SwingUtils.setBold(myDateLabel);
			SwingUtils.setBold(myTimeLabel);
		}
		
		validate();
	}

	private void createFields()
	{
		myDateLabel = new JLabel("Date");
		addLabel(myDateLabel,5);
		myDateField = new JXDatePicker();
		addLabel(myDateField,5);
		
		myTimeLabel = new JLabel("Time");
		addLabel(myTimeLabel,5);
		myTimeField = new JTextField();
		SwingUtils.setPreferredWidth(myTimeField, 60);
		addLabel(myTimeField,5);
	}

	public long getTime()
	{
		long time = myDateField.getDateInMillis();
		time = DateUtil.clearTime(time);
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		
		String s = myTimeField.getText();
		long timeOfDay = DateUtil.parseTimeString(s);
		
		Calendar timeCal = Calendar.getInstance();
		timeCal.setTimeInMillis(timeOfDay);
		
		cal.set(
				cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONTH), 
				cal.get(Calendar.DAY_OF_MONTH), 
				timeCal.get(Calendar.HOUR_OF_DAY), 
				timeCal.get(Calendar.MINUTE), 
				timeCal.get(Calendar.SECOND)
		);
		
		long timeAndDate = cal.getTimeInMillis();		
		return timeAndDate;
	}
	
	public long getDateOnly()
	{
		long time = myDateField.getDateInMillis();
		time = DateUtil.clearTime(time);
		return time;
	}
	
	public long getTimeOnly()
	{
		String s = myTimeField.getText();
		s = StringUtils.trim(s);
		
		long time = -1;
		if (null != s)
		{
			time = DateUtil.parseTimeString(s);
		}
		
		return time;
	}
	
	public static SimpleDateFormat ourTimeFormat = new SimpleDateFormat("HH:mm:ss");
	
	public void setTime(long time)
	{
		myDateField.setDateInMillis(time);
		myTimeField.setText(ourTimeFormat.format(time));
	}
}
