package com.lts.swing.table.dragndrop.test;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogTranscriber
{
	public String transcribe(EventLog log)
	{
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		
		Object[] events = log.getEvents();
		for (Object o : events)
		{
			out.println(o);
		}
		
		out.close();
		return writer.toString();
	}
}
