package com.lts.event;

public class ActionMouseListener extends LTSMouseAdapter
{
	private SimpleThreadedAction myDoubleClickAction;
	private SimpleThreadedAction mySingleClickAction;
	private SimpleThreadedAction myRightClickAction;
	
	
	public void setDoubleClickAction(SimpleThreadedAction action)
	{
		myDoubleClickAction = action;
	}
	
	public void doubleClick (Object source)
	{
		if (null != myDoubleClickAction)
			myDoubleClickAction.actionPerformed(null);
	}
	
	public void setSingleClickAction(SimpleThreadedAction action)
	{
		mySingleClickAction = action;
	}
	
	public void singleClick(Object source)
	{
		if (null != mySingleClickAction)
			mySingleClickAction.actionPerformed(null);
		
	}
	public void setRightClickAction(SimpleThreadedAction action)
	{
		if (null != myRightClickAction)
			myRightClickAction.actionPerformed(null);
	}
}
