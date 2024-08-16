package com.lts.trace;

import java.text.SimpleDateFormat;

public class Event
{
	static protected SimpleDateFormat ourFormat =
		new SimpleDateFormat("HH:mm:ss.SSS");
	
	protected long myTime;
	
	public long getTime()
	{
		return myTime;
	}
	
	public String toString()
	{
		return ourFormat.format(myTime);
	}
}
