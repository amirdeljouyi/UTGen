package com.lts.swing.table.dragndrop.test;

import java.util.Stack;

public class CallGraphLog extends EventLog
{
	protected boolean myDirty = true;
	protected Stack<CallGraphNode> myCallstack;
	protected CallGraphNode myRoot;
	
	public CallGraphLog ()
	{
		initalize();
	}
	
	
	private void initalize()
	{
		myRoot = new CallGraphNode();
		myCallstack = new Stack<CallGraphNode>();
	}


	public boolean isDirty()
	{
		return myDirty;
	}
	
	public void organize()
	{
		myDirty = false;
		
		
	}

	@Override
	public void enterMethod()
	{
		CallEnterEvent event = new CallEnterEvent(2);
		addEvent(event);
		
		CallGraphNode node = new CallGraphNode(event);
		if (myCallstack.empty())
		{
			myRoot.addChild(node);
		}
		else
		{
			myCallstack.peek().addChild(node);
		}
		
		myCallstack.push(node);
	}

	@Override
	public void leaveMethod()
	{
		CallLeaveEvent event = new CallLeaveEvent(2);
		addEvent(event);
		
		if (!myCallstack.empty())
		{
			myCallstack.pop();
		}
		
		CallGraphNode child = new CallGraphNode(event);
		if (myCallstack.empty())
		{
			myRoot.addChild(child);
		}
		else
		{
			myCallstack.peek().addChild(child);
		}
	}


	public CallGraphNode getRoot()
	{
		return myRoot;
	}
}
