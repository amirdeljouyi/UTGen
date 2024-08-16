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


import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;


/**
 * A randomly ordered collection.
 * 
 * <H2>Description</H2>
 * This class is a collection where the elements are returned in a random order.
 * 
 * @author cnh
 *
 */
public class Bag implements Collection
{
    private static ImprovedRandom myRandom;
    private Vector myElements;
    
    public Bag ()
    {
        super();
    }
    
    public Bag (Bag theOrig)
    {
        Vector newElements = (Vector) theOrig.getElements().clone();
        setElements(newElements);
    }
    
    public Vector getElements()
    {
        if (null == myElements)
            myElements = new Vector();
        
        return myElements;
    }
    
    public void setElements (Vector theElements)
    {
        myElements = theElements;
    }
    
    public void addElement (Object o)
    {
        getElements().addElement(o);
    }

    public Object getElement ()
    {
		int theIndex = getRandom().nextInt(getElements().size());
		return getElements().elementAt(theIndex);
    }
    
    public boolean removElement (Object o)
    {
        return getElements().removeElement(o);
    }
    
    public Object getNextAndRemove ()
    {
        int theIndex = getRandom().nextInt(getElements().size());
        Object o = getElements().elementAt(theIndex);
        getElements().removeElementAt(theIndex);
        return o;
    }
    
    public Enumeration elements ()
    {
        return new BagEnumeration (this);
    }
    
    public static ImprovedRandom getRandom()
    {
        if (null == myRandom)
            myRandom = new ImprovedRandom(System.currentTimeMillis());
        
        return myRandom;
    }
    
    public class BagEnumeration implements Enumeration
    {
        private Bag myBag;
        
        public BagEnumeration (Bag b)
        {
            myBag = new Bag(b);
        }
        
        public boolean hasMoreElements ()
        {
            return myBag.getElements().size() > 0;
        }
        
        public Object nextElement ()
        {
            return myBag.getNextAndRemove();
        }
    }

	public int size()
	{
		return this.getElements().size();
	}

	public boolean isEmpty()
	{
		return getElements().size() > 0;
	}

	public boolean contains(Object o)
	{
		return myElements.contains(o);
	}

	public static class BagIterator implements Iterator
	{
		private Bag myBag;
		
		public BagIterator (Bag bag)
		{
			myBag = new Bag(bag);
		}
		
		
		public boolean hasNext()
		{
			return myBag.getElements().size() > 0;
		}

		public Object next()
		{
			return myBag.getNextAndRemove();
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
	
	
	public Iterator iterator()
	{
		return new BagIterator(this);
	}

	public Object[] toArray()
	{
		return getElements().toArray();
	}

	public Object[] toArray(Object[] theArray)
	{
		return getElements().toArray(theArray);
	}

	public boolean add(Object o)
	{
		return getElements().add(o);
	}

	public boolean remove(Object o)
	{
		return getElements().remove(o);
	}

	public boolean containsAll(Collection collection)
	{
		return getElements().containsAll(collection);
	}

	public boolean addAll(Collection col)
	{
		return getElements().addAll(col);
	}

	public boolean removeAll(Collection col)
	{
		return getElements().removeAll(col);
	}

	public boolean retainAll(Collection col)
	{
		return getElements().retainAll(col);
	}

	public void clear()
	{
		getElements().clear();
	}
	
	
	public Bag (Collection col)
	{
		myElements = new Vector(col);
	}
}

