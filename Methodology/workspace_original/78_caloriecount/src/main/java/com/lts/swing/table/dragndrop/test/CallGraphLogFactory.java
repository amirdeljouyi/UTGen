package com.lts.swing.table.dragndrop.test;

import com.lts.swing.table.dragndrop.test.EventLog.LogTypes;

public class CallGraphLogFactory
{
	public EventLog createEventLog(LogTypes type)
	{
		EventLog log = null;
		
		if (null == type)
		{
			log = new EventLog();
		}
		else
		{
			switch (type)
			{
				case Callgraph :
					log = new CallGraphLog();
					break;
					
				case Default :
					log = new EventLog();
					break;
			}
		}
		
		return log;
	}

	public EventLog createEventLog()
	{
		EventLog log = createEventLog(LogTypes.Callgraph);
		LogTranscriber trans = new CallgraphTranscriber();
		log.setTranscriber(trans);
		
		return log;
	}
}
