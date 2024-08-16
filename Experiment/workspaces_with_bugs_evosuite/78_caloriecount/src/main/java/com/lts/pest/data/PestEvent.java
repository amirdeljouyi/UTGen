package com.lts.pest.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * This class represents a an event that occurred at a particular point in time.
 * 
 * @author cnh
 */
public class PestEvent implements Serializable, Comparable
{
	private static final long serialVersionUID = 1L;

	protected long myTime;
	
	
	public PestEvent()
	{}
	
	public PestEvent (long time)
	{
		initialize(time);
	}
	
	
	protected void initialize (long time)
	{
		myTime = time;
	}
	
	
	public long getTime()
	{
		return myTime;
	}
	
	public void setTime(long l)
	{
		myTime = l;
	}
	
	
	public int compareTo(Object o)
	{
		PestEvent event = (PestEvent) o;
		if (myTime > event.myTime)
			return 1;
		else if (myTime < event.myTime)
			return -1;
		else
			return 0;
	}
	
	
	protected static final SimpleDateFormat FORMAT =
		new SimpleDateFormat("HH:mm:ss");
	
	public String toString()
	{
		return FORMAT.format(myTime);
	}

}
