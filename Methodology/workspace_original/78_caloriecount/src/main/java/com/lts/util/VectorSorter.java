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


import java.util.Vector;

public class VectorSorter
{
    public static void sort (Vector v, CompareMethod c)
    {
        if (null == v || v.size() <= 1)
            return;
        
        quickSort (v, c, 0, v.size() - 1);
    }
    
    public static void quickSort (
        Vector v, 
        CompareMethod c, 
        int start,
        int stop
    )
    {
        //
        // an array of one element is already sorted
        //
        if (start >= stop)
            return;

        //
        // choose a pivot and then move it out of the way
        //
        int low = start;
        int high = stop;
        int mid = (start + stop)/2;
        Object pivot = v.elementAt(mid);
        v.setElementAt(v.elementAt(stop), mid);
        v.setElementAt(pivot, stop);
        
        
        while (low < high)
        {
            //
            // find an item that occurs before the pivot in the array 
            // that should occur after the pivot in the array.
            //
            Object o = v.elementAt(low);
            int theResult = c.compare(o, pivot);
            while (
                low < high 
                && CompareMethod.ELEMENT1_AFTER_ELEMENT2 != theResult
            )
            {
                low++;
                o = v.elementAt(low);
                theResult = c.compare(o, pivot);
            }
            
            //
            // find an item that occurs after the pivot in the array that
            // should be before the pivot in the array.
            //
            o = v.elementAt(high);
            theResult = c.compare(o, pivot);
            while (
                low < high
                && CompareMethod.ELEMENT1_BEFORE_ELEMENT2 != theResult
            )
            {
                high--;
                o = v.elementAt(high);
                theResult = c.compare(o, pivot);
            }
            
            //
            // if we found an item that occurs before the pivot which should occur after 
            // the pivot, and we found an item that is after the pivot that should
            // occurr before the pivot, then swap them.
            //
            if (low < high)
            {
                Object temp = v.elementAt(low);
                v.setElementAt(v.elementAt(high), low);
                v.setElementAt(temp, high);
            }
        }
        
        //
        // move the pivot to its proper location in the array
        //
        v.setElementAt(v.elementAt(high), stop);
        v.setElementAt(pivot, high);
 
        //
        // now sort the two sub-arrays
        //
        quickSort (v, c, start, low - 1);
        quickSort (v, c, high + 1, stop);
        
    }
}


    
