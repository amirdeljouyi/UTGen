//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;


public class TreeNode implements DeepCopier
{
    protected TreeNode myParent;
    protected List myChildren;
    protected Object myData;

    
    public TreeNode ()
    {
		initialize(null, null, null);
    }
    
    public TreeNode (Object theData)
    {
    	initialize(null, null, theData);
    }
    
    public TreeNode (TreeNode parent)
    {
    	initialize (parent, null, null);
    }
    
    public TreeNode (TreeNode parent, Collection children)
    {
    	initialize(parent, children, null);
    }
    
    
    public TreeNode (TreeNode parent, Collection children, Object data)
    {
    	initialize(parent, children, data);
    }
    
  
    public void initialize (TreeNode parent, Collection children, Object data)
    {
    	setParent(parent);
    	setChildren(children);
    	setData(data);
    }
    

	public Object getData()
	{
		return myData;
	}
	
	public void setData (Object o)
	{
		myData = o;
	}
	
	
    public TreeNode getParent()
    {
        return myParent;
    }
    
    public void setParent (TreeNode parent)
    {
    	myParent = parent;
    }
    
    public List getChildren ()
    {
        return myChildren;
    }
    
    
    public void addChild (TreeNode n)
    {
        myChildren.add(n);
    }
    
    public boolean removeChild (TreeNode child)
    {
    	return myChildren.remove(child);
    }
    
    public void clearChildren()
    {
    	for (Iterator i = this.myChildren.iterator(); i.hasNext(); )
    	{
    		TreeNode child = (TreeNode) i.next();
    		child.setParent(null);
    	}
    	
    	this.myChildren = new ArrayList();
    }
    
    public void replaceChild (TreeNode original, TreeNode replacement)
    {
    	int index = myChildren.indexOf(original);
    	if (-1 == index)
    	{
    		String msg =
    			"The node, " + original + ", is not one of the child nodes of " + this;
    		
    		throw new IllegalArgumentException(msg);
    	}
    	
    	myChildren.add(index, replacement);
    }
    
    
    public void setChildren(Collection c)
    {
    	if (null != myChildren)
    	{
	    	Iterator iter = myChildren.iterator();
	    	while (iter.hasNext())
	    	{
	    		TreeNode child = (TreeNode) iter.next();
	    		child.setParent(null);
	    	}
    	}
    	
    	if (null == c)
    		myChildren = new ArrayList();
    	else if (c instanceof ArrayList)
    		myChildren = (ArrayList) c;
    	else
    		myChildren = new ArrayList(c);
    }
    
    public TreeNode getRoot ()
    {
        if (null == getParent())
            return this;
        else
            return getParent().getRoot();
    }

    
    public void addNodesDepthFirst (List l)
    {
    	Iterator i = getChildren().iterator();
    	while (i.hasNext())
    	{
    		TreeNode child = (TreeNode) i.next();
    		child.addNodesDepthFirst (l);
    	}
    }
    
    
    public List depthFirstList()
    {
    	List l = new ArrayList();
    	
    	getRoot().addNodesDepthFirst(l);
    	
    	return l;
    }
    
    
    public int getChildCount()
    {
    	return getChildren().size();
    }
    
	public boolean isDescendentOf (TreeNode other)
	{
		if (null == other)
			return false;
		else if (this == other || this.equals(other))
			return true;
		
		for (Object o : other.depthFirstList())
		{
			if (o == other || o.equals(other))
				 return true;
		}
		
		return false;
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients)
		throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
		
//		copy = (TreeNode) continueDeepCopy(map, copyTransients);
//		map.put(this, copy);
//		
//		if (null != myParent)
//			copy.myParent = (TreeNode) myParent.continueDeepCopy(map, copyTransients);
//		
//		copy.myData = DeepCopyUtil.continueDeepCopy (myData, map, copyTransients);
//		copy.myChildren = DeepCopyUtil.copyList(myChildren, map, copyTransients);
//		
//		return copy;
	}

	public Object deepCopy() throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	public void deepCopyData(Object o, Map map, boolean copyTransients) throws DeepCopyException
	{
		TreeNode copy = (TreeNode) o;
		
		if (null == myParent)
			copy.myParent = null;
		else
			copy.myParent = (TreeNode) myParent.continueDeepCopy(map, copyTransients);
		
		copy.myData = DeepCopyUtil.continueDeepCopy (myData, map, copyTransients);
		copy.myChildren = DeepCopyUtil.copyList(myChildren, map, copyTransients);
	}
	

	/**
	 * Perform a "shallow update" from the provided TreeNode.
	 * 
	 * <H2>NOTE</H2>
	 * Parents and children are a special case because it is possible that 
	 * the old and new children are not "==" equivalent but are ".equals" 
	 * equivalent.  
	 * 
	 * In that situation we should not change the parents/children
	 * of the node.
	 * 
	 * <P>
	 * A TreeNode actually does nothing for a shallow update.  
	 * </P>
	 * @param ocopy
	 */
	public void updateFrom (Object ocopy)
	{
		TreeNode node = (TreeNode) ocopy;
		
		myChildren = node.myChildren;
		myData = node.myData;
		myParent = node.myParent;
	}
}
