package com.lts.caloriecount.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.lts.application.ApplicationProperties;
import com.lts.util.ArrayUtils;
import com.lts.util.MapUtil;

public class CalorieCountProperties extends Properties implements ApplicationProperties
{
	private static final long serialVersionUID = 1L;
	protected Map<String, String> myDefaults;
	

	public CalorieCountProperties(Properties properties)
	{
		super(properties);
	}
	
	
	public File getLastDirectory()
	{
		String s = getProperty(PROP_LAST_DIRECTORY);
		if (null == s)
			return null;
		
		File f = new File(s);
		if (!f.isDirectory())
			return null;
		
		return f;
	}
	
	public void setLastDirectory(File directory)
	{
		String value = null;
		if (null != directory && directory.isDirectory())
		{
			value = directory.toString();
		}
		
		setProperty(PROP_LAST_DIRECTORY, value);
	}
	
	static protected String[] SPEC_CALORIE_COUNT_DEFAULTS = {
		PROP_RELOAD_WINDOWS, "true"
	};
	
	protected Map<String, String> buildDefaults()
	{
		String[] newSpec = ArrayUtils.appendStrings(SPEC_DEFAULT_VALUES, SPEC_CALORIE_COUNT_DEFAULTS);
		Map<String, String> map = new HashMap<String, String>();
		map = MapUtil.buildMap(newSpec);
		return map;
	}

	synchronized protected void initializeDefaults()
	{
		if (null == myDefaults)
		{
			myDefaults = buildDefaults();
		}
	}
	
	public Map<String, String> getDefaults()
	{
		if (null == myDefaults)
			initializeDefaults();
		
		return myDefaults;
	}
	
	
	
	
	public boolean getBooleanProperty(String name)
	{
		try
		{
			String stringValue = getProperty(name);
			if (null == stringValue)
			{
				stringValue = getDefaults().get(name);
			}
			
			if (null == stringValue)
			{
				stringValue = "false";
			}
			
			Boolean bool = new Boolean(stringValue);
			return bool;
		}
		catch (RuntimeException e)
		{
			return false;
		}
	}
	
	public boolean reloadWindows()
	{
		return getBooleanProperty(PROP_RELOAD_WINDOWS);
	}
	
	
//	public static final String[] SPEC_APP_PROPERTY_NAMES = {
//		PROP_LAST_DIRECTORY,
//		PROP_LAST_FILE,
//		PROP_REPOSITORY,
//		PROP_PROPERTY_FILE,
//		PROP_PARAMETER,
//		PROP_LOAD_TIME_MILLIS,
//		PROP_RELOAD_WINDOWS,
// };

}
