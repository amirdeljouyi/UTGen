package com.lts.swing.table.dragndrop.test;

import java.util.ArrayList;
import java.util.List;

public class EventLog
{
	public static enum LogTypes {
		Default,
		Callgraph
	};
	
	protected List<RecordingEvent> myLog;
	static private EventLog ourInstance;
	static private CallGraphLogFactory ourFactory;
	
	protected LogTranscriber myTranscriber;
	
	private int mySequence;
	
	static public EventLog getInstance()
	{
		if (null == ourInstance)
		{
			initializeLog();
		}
		
		return ourInstance;
	}
	
	synchronized static private void initializeLog()
	{
		ourFactory = new CallGraphLogFactory();
		ourInstance = ourFactory.createEventLog();
	}

	public EventLog()
	{
		myLog = new ArrayList<RecordingEvent>();
		mySequence = 0;
	}
	
	static public void add(RecordingEvent event)
	{
		getInstance().addEvent(event);
	}
	
	synchronized public void addEvent(RecordingEvent event)
	{
		myLog.add(event);
		event.setSequence(mySequence);
		mySequence++;
	}
	
	static public RecordingEvent[] events()
	{
		return (RecordingEvent[]) getInstance().getEvents();
	}
	
	synchronized public Object[] getEvents()
	{
		return myLog.toArray();
	}
	
	
	static public String transcript()
	{
		return getInstance().getTranscript();
	}
	
	
	synchronized public String getTranscript()
	{
		return myTranscriber.transcribe(this);
	}
	
	static public void resetInstance()
	{
		getInstance().reset();
	}
	
	
	synchronized public void reset()
	{
		myLog = new ArrayList<RecordingEvent>();
	}

	public void enterMethod()
	{
		RecordingEvent event = new StackTraceEvent();
		addEvent(event);
	}
	
	
	public void leaveMethod()
	{
		RecordingEvent event = new StackTraceEvent();
		addEvent(event);
	}

	public void setTranscriber(LogTranscriber trans)
	{
		myTranscriber = trans;
	}
}
