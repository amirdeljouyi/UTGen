package com.lts.application.international;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationMessages;
import com.lts.application.ApplicationTerminateException;

public class LTSInternationalizationSupport
{
	/**
	 * The default file that contains things like error messages, prompts, 
	 * window titles, etc.
	 */
	public static final String DEFAULT_RESOURCE_BUNDLE_NAME = 
		"resources.messages.standard";
	
	protected ResourceBundle myBundle;
	protected MessageFormatter myFormatter;

	private ResourceBundle myResourceBundle;
	
	/**
	 * Create the MessageFormatter for the application.
	 * 
	 * <P>
	 * This is essentially a factory method to allow subclasses to chose a different
	 * {@link MessageFormatter} class should they so desire. 
	 * </P>
	 * 
	 * @throws ApplicationTerminateException If the resource bundle cannot be found.
	 */
	protected MessageFormatter createMessageFormatter() throws ApplicationTerminateException
	{
		return new MessageFormatter();
	}
	
	
	protected void initializeResourceBundles() throws ApplicationTerminateException 
	{
		try
		{
			myFormatter = createMessageFormatter();
			if (null != myFormatter)
			{
				List<String> resourcePath = buildResourcePath();
				List<String> bundleNames = buildBundleNames();
				myFormatter.initializeResourceBundle(resourcePath, bundleNames);
			}
		}
		catch (ApplicationException e)
		{
			String msg = "Error loading resource bundle(s)";
			throw new ApplicationTerminateException(msg, e);
		}
	}

	protected List<String> buildBundleNames()
	{
		List<String> list = new ArrayList<String>();
		addBundleNames(list);
		return list;
	}

	protected void addBundleNames(List<String> list)
	{
		list.add(DEFAULT_RESOURCE_BUNDLE_NAME);
	}

	public ResourceBundle getResourceBundle()
	{
		return myResourceBundle;
	}
	
	
	public String formatMessage(String key, Object[] data)
	{
		String result = "key not found";
		String formatString = myResourceBundle.getString(key);
		if (null != formatString)
		{
			result = MessageFormat.format(formatString, data);
		}
		
		return result;
	}

	
	
	/**
	 * Create the list of resource names to use as resource bundles.
	 * 
	 * <P>
	 * This method is essentially a hook to allow sub-classes to provide a 
	 * completely different procedure for creating the list of resource bundle
	 * names.
	 * 
	 * <P>
	 * The default procedure is to create a list, call 
	 * {@link #addResourcePathElements(List)} on it, and return the list.
	 * 
	 * @return A list of resource bundle names.
	 */
	protected List buildResourcePath()
	{
		List list = new ArrayList();
		addResourcePathElements(list);
		return list;
	}
	
	/**
	 * This is the default error message that the system displays if it cannot 
	 * load its resource bundle.  The resource bundle is a file that defines 
	 * all the messages to display to the user as well as locale specific formatting
	 * information, etc.
	 */
	protected static final String DEFAULT_RESOURCE_BUNDLE_ERROR_MESSAGE =
		"The system encountered a severe error will cause it to terminate.";
	
	/**
	 * Add locations to search for resource bundles.
	 * 
	 * <P>
	 * This method is a hook to allow subclasses to tell the application to look in 
	 * alternate locations for resource bundles.
	 * </P>
	 * 
	 * <P>
	 * This implementation of the method simply adds 
	 * {@link #DEFAULT_RESOURCE_BUNDLE_NAME} to the list.
	 * 
	 * <P>
	 * Sub-classes that want to use additional properties files should override 
	 * this method and prepend or append their bundle names to the list.  
	 * 
	 * <P>
	 * The preferred location for resource bundles is "/resources/&lt;bundle name&gt;".
	 * 
	 * <P>
	 * Sub-classes should still call this method if they want the messages that 
	 * the application framework defines.  This includes all the messages defined 
	 * in {@link ApplicationMessages}.
	 * 
	 * @param list The list to add names to.
	 */
	protected void addResourcePathElements(List list)
	{
		list.add("resources.messages.standard");
	}
	
	/**
	 * Get the error message that is displayed if the applciation cannot load the 
	 * ResourceBundle that contains all its messages and locale specific information.
	 * 
	 * <H3>Description</H3>
	 * This method is provided so that subclasses that wish to display a message other
	 * than that defined by {@link #DEFAULT_RESOURCE_BUNDLE_ERROR_MESSAGE} can 
	 * do so by overriding this method.
	 * 
	 * @return See description.
	 */
	protected String getResourceBundleErrorMessage()
	{
		return DEFAULT_RESOURCE_BUNDLE_ERROR_MESSAGE;
	}
	
	
}
