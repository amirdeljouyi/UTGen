package com.lts.application.data.coll;

import java.util.Collection;

import com.lts.application.data.ApplicationData;

/**
 * A collection of arbitrary objects that are managed by an application.
 * @author cnh
 *
 */
public interface ApplicationDataCollection extends 
	ApplicationData, Collection<ApplicationData>
{
	public void addADCListener (ADCListener listner);
	public boolean removeADCListener (ADCListener listener);
}
