package com.lts.xml;

import java.util.Date;

/**
 * A SimpleDateFormatter that uses the number of milliseconds as its format
 * 
 * <P>
 * That is, this class inputs/outputs {@link Date#getTime()} as its value.
 * </P>
 * 
 * @author cnh
 *
 */
public class TimeFormatter
{
	private static final long serialVersionUID = 1L;

	public TimeFormatter()
	{}
	
	public Date parse(String s)
	{
		long time;
		
		try
		{
			time = new Long(s);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
		
		return new Date(time);
	}
	
	public String format(Date d)
	{
		return format(d.getTime());
	}
	
	public String format(long time)
	{
		return Long.toString(time);
	}
}
