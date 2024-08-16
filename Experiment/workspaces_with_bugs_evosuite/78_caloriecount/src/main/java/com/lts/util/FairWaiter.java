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
import java.util.List;


/**
 * Implement a first-in, first-out waiting scheme.
 *
 * <P/>
 * This class was created because the Java wait/notify is not guaranteed to be
 * "fair," let alone FIFO.  That is, in the general case, it is possible for 
 * a thread to wait forever, even when enough notifies have occurred for the 
 * waiter to be awakened if things where FIFO.
 *
 * <P/>
 * Care must be taken when using this class because of the nature of the wait
 * and notify methods.  Specifically, the various wait and notify methods are 
 * all public final, so it is possible for a client to bypass the whole
 * fairness algorithm by using wait instead of fairWait.  Similarly, the 
 * scheme will not work if the client uses notify instead of notifyAll.
 * This being the case, it is a good idea to use another class to handle access
 * to this class, thereby protecting the methods that clients should not use.
 *
 * <P/>
 * For this scheme to work, a client must use notifyAll instead of notify.  
 * This is inefficient, since all but one of the waiting threads will end up 
 * going back to sleep, but necessary because of Java's wait/notify scheme.
 */
public class FairWaiter
{
    protected List myWaiters;
    protected Thread myNextInLine;
    
    
    /** 
     * Get the current list of waiters in a thread unsafe manner.
     *
     * <P/>
     * Note that this method is not synchronized and that the List interface
     * is explicitly thread unsafe.  Clients who want a "snapshot" of the 
     * current list of waiting threads should use the "copyWaiters" method.
     */
    protected List getWaiters()
    {
        if (null == myWaiters)
            myWaiters = new ArrayList();
        
        return myWaiters;
    }
    
    
    public Thread getNextInLine ()
    {
        return myNextInLine;
    }
    
    public void setNextInLine (Thread t)
    {
        myNextInLine = t;
    }
    
    
    /**
     * Take a "snapshot" of the current list of waiting threads.
     *
     * <P/>
     * This method copies the current list of waiting threads in the order 
     * that they will be allowed to awaken via the "fairWait" method.  
     * Once the method relinquishes control of the object's monitor, there is
     * not guarantee that the list will not change.
     */
    public synchronized List copyWaiters ()
    {
        List l = new ArrayList(getWaiters());
        return l;
    }
    
    public synchronized boolean noWaiters ()
    {
        return getWaiters().size() <= 0;
    }
    
    /**
     * Wait using a FIFO scheme.
     *
     * <P/>
     * This method causes the thread to "get in line" and wait for until 
     * it is their "turn."  The thread blocks until a) another thread calls 
     * notify or notifyAll and b) the thread is the "next in line" in the 
     * list of waiters.
     */
    public synchronized void fairWait ()
        throws InterruptedException
    {
        try
        {
            getWaiters().add(Thread.currentThread());
            
            do {
                wait();
            } while (getNextInLine() != Thread.currentThread());
            
            setNextInLine(null);
        }
        //
        // ensure that, when we leave this routine, the current thread is 
        // neither in the wait queue nor the next thread in line
        //
        finally 
        {
            getWaiters().remove(Thread.currentThread());
            if (getNextInLine() == Thread.currentThread())
                setNextInLine(null);
        }
    }
    
    
    public synchronized void fairNotify ()
    {
        if (null == getNextInLine() && getWaiters().size() > 0)
        {
            Thread t = (Thread) getWaiters().remove(0);
            setNextInLine(t);
        }
        
        notifyAll();
    }
            
        
}
