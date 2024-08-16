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


public class ArraySorter
{
    public static void sort (Object[] theData, CompareMethod c)
    {
        if (null == theData || theData.length <= 1)
            return;
        
        quickSort (theData, c, 0, theData.length - 1);
    }
    
    public static void quickSort (
        Object[] a, 
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
        Object pivot = a[(start+stop)/2];
        a[(start+stop)/2] = a[stop];
        a[stop] = pivot;
        
        
        while (low < high)
        {
            //
            // find an item that occurs before the pivot in the array 
            // that should occur after the pivot in the array.
            //
            int theResult = c.compare(a[low], pivot);
            while (
                low < high 
                && theResult <= 0
            )
            {
                low++;
                theResult = c.compare(a[low], pivot);
            }
            
            //
            // find an item that occurs after the pivot in the array that
            // should be before the pivot in the array.
            //
            theResult = c.compare(a[high], pivot);
            while (
                low < high
                && theResult >= 0
            )
            {
                high--;
                theResult = c.compare(a[high], pivot);
            }
            
            //
            // if we found an item that occurs before the pivot which should occur after 
            // the pivot, and we found an item that is after the pivot that should
            // occurr before the pivot, then swap them.
            //
            if (low < high)
            {
                Object temp = a[low];
                a[low] = a[high];
                a[high] = temp;
            }
        }
        
        //
        // move the pivot to its proper location in the array
        //
        a[stop] = a[high];
        a[high] = pivot;
 
        //
        // now sort the two sub-arrays
        //
        quickSort (a, c, start, low - 1);
        quickSort (a, c, high + 1, stop);
        
    }
}
