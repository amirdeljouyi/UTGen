package com.lts.application.checkpoint;

import java.io.File;
import java.text.SimpleDateFormat;

public class CheckPoint
{
	private File myFile;
	
	public File getFile()
	{
		return myFile;
	}
	
	
	public CheckPoint(File file)
	{
		initialize(file);
	}
	
	
	protected void initialize(File file)
	{
		if (!file.exists())
		{
			String msg = "The file, " + file + ", does not exist";
			throw new IllegalArgumentException(msg);
		}
		
		myFile = file;
	}
	
	
	final static private SimpleDateFormat ourFormat =
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	
	public String toString()
	{
		return ourFormat.format(myFile.lastModified());
	}
}