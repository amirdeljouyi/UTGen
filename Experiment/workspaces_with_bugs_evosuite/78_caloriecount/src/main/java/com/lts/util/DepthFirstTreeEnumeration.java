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


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;


public class DepthFirstTreeEnumeration
    implements Iterator
{
    protected Stack myStack;
    protected TreeNode myCurrent;
    
    
    public class StackNode
    {
        public TreeNode theNode;
        public Iterator theIterator;
        
        public StackNode (TreeNode n, Iterator i)
        {
            theNode = n;
            theIterator = i;
        }
    }
    
    
    public DepthFirstTreeEnumeration (TreeNode n)
    {
        StackNode sn = new StackNode(n, n.getChildren().iterator());
        getStack().push(sn);
    }
    
    
    public TreeNode getCurrent ()
    {
        return myCurrent;
    }
    
    public void setCurrent (TreeNode n)
    {
        myCurrent = n;
    }
    
    
    public Stack getStack()
    {
        if (null == myStack)
            myStack = new Stack();
        
        return myStack;
    }
    

    public boolean hasNext ()
    {
        return getStack().empty();
    }
    
    public Object next ()
    {
        if (!hasNext())
            throw new NoSuchElementException();
            
        StackNode sn = (StackNode) getStack().peek();
        while (sn.theIterator.hasNext())
        {
            TreeNode tn = (TreeNode) sn.theIterator.next();
            sn = new StackNode(tn, tn.getChildren().iterator());
            getStack().push(sn);
        }
        
        Object o = sn.theNode;
        
        getStack().pop();
        
        return o;
    }
    
    public void remove ()
    {
        throw new RuntimeException ("Not implemented");
    }
}
