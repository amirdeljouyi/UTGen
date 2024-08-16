package com.lts.application.international;

import java.util.ResourceBundle;

public class DefaultMessageFormatter extends MessageFormatter
{
	@Override
	protected void addResourceBundles()
	{
		super.addResourceBundles();
		
		addBundle(new CriticalMessages());
		ResourceBundle bundle = tryLoad("messages");
		if (null != bundle)
			addBundle(bundle);
	}
}
