package com.lts.swing.table.dragndrop.example;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;

@SuppressWarnings("serial")
public class ExampleWindow extends ApplicationContentPanel
{
	public String getViewName()
	{
		return "example";
	}
	
	
	public ExampleWindow (JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	public void initialize(JFrame frame) throws LTSException
	{
		initialize();
		super.initialize(frame);
	}
	
	
	static public ExampleWindow showFrame() throws LTSException
	{
		JFrame frame = new JFrame();
		ExampleWindow win = new ExampleWindow(frame);
		frame.setVisible(true);
		return win;
	}
	
	static protected String[] DATA = {
		"Hi there",
		"Hello world",
		"line three",
		"The JDK documentation for this sucks",
		"Which is why I am writing this example",
		"Flurndebits are tastee",
		"Twinkees",
		"Turtles eat corn",
		"Cheese and whine",
		"Corn eats turtles"
	};
	
	public void initialize()
	{
		ExampleModel model = new ExampleModel();
		for (int i = 0; i < DATA.length; i++)
		{
			model.addString(DATA[i]);
		}
		
		
		JTable table = new JTable(model);
		// ExampleTransferHandler handler = new ExampleTransferHandler();
		table.setTransferHandler(null);
		table.setDragEnabled(true);
		
		JScrollPane jsp = new JScrollPane(table);
		addFill(jsp);
	}
}
