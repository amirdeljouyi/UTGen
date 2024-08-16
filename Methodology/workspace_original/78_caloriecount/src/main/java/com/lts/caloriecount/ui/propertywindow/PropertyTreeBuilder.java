package com.lts.caloriecount.ui.propertywindow;

import java.util.Properties;

public class PropertyTreeBuilder
{
	public PropertyNode buildTree (Properties p)
	{
		PropertyNode root = new PropertyNode(null);
		String[] names = (String[]) p.keySet().toArray();
		for (int index = 0; index < names.length; index++)
		{
			String name = names[index];	
			buildNodes(root, name, p.getProperty(name));
		}
		
		return root;
	}
	
	
	public void buildNodes (PropertyNode root, String name, String value)
	{
		PropertyNode parent = root;
		String[] comps = name.split("\\.");
		for (String comp : comps)
		{
			PropertyNode child = parent.nameToChild(comp);
			if (null == child)
			{
				child = new PropertyNode(comp);
				parent.addChild(child);
			}
			
			parent = child;
		}
	}
}
