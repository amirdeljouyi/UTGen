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


public class IntegerCompareMethod
    implements CompareMethod
{
    public IntegerCompareMethod ()
    {
        setMode(SORT_ASCENDING);
    }
    
    public IntegerCompareMethod (int theMode)
    {
        setMode(theMode);
    }
    
    public static final int SORT_ASCENDING = 0;
    public static final int SORT_DESCENDING = 1;
    
    private int myMode;
    
    public void setMode (int theMode)
    {
        myMode = theMode;
    }
    
    public boolean ascending ()
    {
        return SORT_ASCENDING == myMode;
    }
    
    
    public int compare (Object o1, Object o2)
    {
        Integer i1 = (Integer) o1;
        Integer i2 = (Integer) o2;
        
        
        if (null == o1 || null == o2)
        {
            throw new IllegalArgumentException ("null argument");
        }
        
        if (i1.intValue() == i2.intValue())
            return ELEMENT1_EQUALS_ELEMENT2;
            
        else if (ascending())
        {
            if (i1.intValue() > i2.intValue())
                return ELEMENT1_AFTER_ELEMENT2;
            else
                return ELEMENT1_BEFORE_ELEMENT2;
        }
        
        else
        {
            if (i1.intValue() > i2.intValue())
                return ELEMENT1_BEFORE_ELEMENT2;
            else
                return ELEMENT1_AFTER_ELEMENT2;
        }
    }
}
