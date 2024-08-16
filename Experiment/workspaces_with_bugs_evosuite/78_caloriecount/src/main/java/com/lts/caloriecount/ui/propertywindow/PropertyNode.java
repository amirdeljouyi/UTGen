package com.lts.caloriecount.ui.propertywindow;

import java.util.HashMap;
import java.util.Map;

import com.lts.util.TreeNode;

public class PropertyNode extends TreeNode
{
	private String myName;
	private Map<String, PropertyNode> myChildren =
		new HashMap<String, PropertyNode>();
	
	public PropertyNode(String name)
	{
		initialize(name);
	}
	
	protected void initialize(String name)
	{
		setName(name);
	}
	
	public String getName()
	{
		return myName;
	}
	public void setName(String name)
	{
		myName = name;
	}
	
	public PropertyNode nameToChild(String name)
	{
		return myChildren.get(name);
	}
	
	
	public void addChild(String name, PropertyNode child)
	{
		myChildren.put(name, child);
	}
	
}
