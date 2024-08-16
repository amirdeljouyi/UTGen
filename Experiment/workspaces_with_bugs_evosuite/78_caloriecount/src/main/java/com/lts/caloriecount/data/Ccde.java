package com.lts.caloriecount.data;

import com.lts.application.data.ApplicationDataElement;

/**
 * An ApplicationDataElement that supports rectification.
 * 
 * <P>
 * Rectification is the notion that some objects reference other objects; even though
 * they are stored separately.  If a dependent object has all its references 
 * filled in, it is said to be rectified.
 * </P>
 * 
 * <P>
 * </P>
 * 
 * FIXME This interface should be removed
 * 
 * @author cnh
 */
public interface Ccde extends ApplicationDataElement
{
	public boolean isRectified();
	public void setRectified(boolean rectified);
	public boolean rectify(CalorieCountData data);
}
