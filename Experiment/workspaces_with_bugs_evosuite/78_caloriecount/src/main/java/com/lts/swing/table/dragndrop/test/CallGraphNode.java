package com.lts.swing.table.dragndrop.test;

import com.lts.util.TreeNode;

public class CallGraphNode extends TreeNode
{
	private StackTraceEvent myEvent;

	public CallGraphNode(StackTraceEvent event)
	{
		initialize(event);
	}

	public CallGraphNode()
	{
		initialize(null);
	}
	
	public boolean isRoot()
	{
		return null == myEvent;
	}
	
	protected void initialize(StackTraceEvent event)
	{
		myEvent = event;
	}
	
	
	public StackTraceEvent getEvent()
	{
		return myEvent;
	}
	
	public boolean isAncestorOf(StackTraceEvent event)
	{
		return myEvent.isAncestorOf(event);
	}
	
	public boolean isParentOf(StackTraceEvent event)
	{
		return myEvent.isParentOf(event);
	}

	public RecordingEvent getTrace()
	{
		return myEvent;
	}
	
	public String toString()
	{
		if (null == myEvent)
			return "root";
		else
			return myEvent.toString();
	}
	
	/**
	 * Add a child event to this node.
	 * 
	 * <H3>Note</H3>
	 * This method assumes that receiver.isAncestorOf(event) would return true
	 * with the parameter.  If this is note the case, then calling this method 
	 * may throw an exception, explode, etc.
	 * 
	 * <H3>Description</H3>
	 * If this node is the immediate parent of the event, then the argument will
	 * become one of the node's immediate children.  If the receiver is not, then 
	 * this method will try to add the event to a child that is either the parent 
	 * or one of the ancestors of the parameter.  
	 * 
	 * <P>
	 * If the receiver already has child nodes, and the receiver is the parent of the 
	 * parameter, then the child will be added at a location such that a) it occurs
	 * after a point in time of one of the other children or b) it occurs at a line
	 * number after the other child.
	 * </P>
	 * 
	 * @param event The new child event.
	 */
//	public void addChildEvent(StackTraceEvent event)
//	{
//		CallGraphNode node = new CallGraphNode(event);
//		int count = getChildCount();
//		List list = getChildren();
//		
//		for (int i = 0; i < count; i++)
//		{
//			CallGraphNode child = (CallGraphNode) list.get(i);
//		}
//	}
}
