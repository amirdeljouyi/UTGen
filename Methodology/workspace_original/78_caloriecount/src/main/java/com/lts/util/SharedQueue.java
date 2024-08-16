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


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A class that implements a shared queue --- an object that allows communications
 * between multiple threads in a first in, first out fashion.
 *
 * <P/>
 * One of the aspects of a shared queue is that, if there is nothing on the queue,
 * then asking for the next item from the queue will cause the requesting thread
 * to block until something is available.
 *
 * <P/>
 * This implementation places simplicity above efficiency.  that its, it does 
 * things like putting a potential waiter on the queue of waiters even if the 
 * wait queue is empty and there is an item available.  If you find that this 
 * class becomes a bottle-neck, then it would probably help to rewrite some of the 
 * routines so that they are a bit more intelligent about how they handle the 
 * synchronization issues.
 *
 * <P/>
 * For people who might not know exactly how Thread.interrupt (like the author
 * of this code :-), it is safe to call Thread.interrupt on a thread that is 
 * running inside of the next method.  This is because Thread.interrupt merely
 * sets some sort of flag, it does not actually cause an asychronous transfer
 * of control as some people might expect a method called "interrupt" to cause.
 */
 
public abstract class SharedQueue
{
    abstract public void performWait() throws InterruptedException;
    abstract public void performNotify();
    
    protected List myQueue;
    
    
    protected synchronized List getQueue ()
    {
        if (null == myQueue)
            myQueue = new ArrayList();
        
        return myQueue;
    }
    
    
    /**
     * Put an object on the queue; wake up any threads waiting.
     */
    public synchronized void put (Object o)
    {
        getQueue().add(o);
        performNotify();
    }
    
    
    /** 
     * Create a "report" that indicates the contents of the queue and who
     * is currently waiting.
     */
    
    public synchronized String getReport ()
    {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        
        Iterator i;
        
        out.println ("===] Shared Queue Report [===");
        out.println ();
        out.println ("Queue contents:");
        
        i = getQueue().iterator();
        while (i.hasNext())
        {
            Object o = i.next();
            out.println (o);
        }
        
        return sw.toString();
    }
    
    /**
     * Get the next item from the queue and remove it.  
     * 
     * <P>
     * This is an internal method --- it would be private, but subclasses need 
     * to have access to it.
     * 
     * @return null if the queue was empty, otherwise, the element at index 0.
     */
    protected Object basicNext()
    {
    	List l = getQueue();
    	if (l.size() < 1)
    		return null;

    	return l.remove(0);
    }
    
    
    /**
     * Get the next item off the queue; wait if there is nothing available.
     * 
     * <P>
     * Wait for at least maxTime milliseconds for something to arrive if 
     * nothing is currently available.  A value of zero means return immediately
     * if nothing is available.  A value less than zero means wait forever.
     */
    public synchronized Object next (long waitAtLeast) throws InterruptedException
    {
    	Object o = basicNext();
    	if (null != o)
    		return o;
    	
    	
		boolean waitForever = 0 >= waitAtLeast;
		
		long now = System.currentTimeMillis();
		long stopTime = now + waitAtLeast;
		
		while (null == o && (waitForever || now < stopTime))
		{
			long waitTime = stopTime - now;
			if (waitForever)
				waitTime = 0;
			
			wait(waitTime);
			o = basicNext();
			now = System.currentTimeMillis();
		}
		
		//
		// if we got here then either 
		// * we found something
		// * the client has waited waitAtLeast millseconds for something
		//   to become available
		//
        return o;
    }
    
    /**
     * Get the next item from the queue; if nothing is available wait forever 
     * for something to become available.
     * 
     * @return Next available item from the queue
     * @throws InterruptedException If interrupted while waiting.
     */
    public synchronized Object next () throws InterruptedException
    {
    	return next(0);
    }
    
    
    /**
     * Take a snapshot of the shared queue contents.
     */
    
    public synchronized List copyContents ()
    {
        List l = new ArrayList(getQueue());
        return l;
    }
    
    
	public synchronized void clear()
	{
		myQueue.clear();
	}
}
