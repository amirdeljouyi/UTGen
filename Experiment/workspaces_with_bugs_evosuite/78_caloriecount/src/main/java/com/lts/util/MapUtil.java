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


import java.util.HashMap;
import java.util.Map;


public class MapUtil
{
    public static Map buildStringToIntMap (Object[] spec)
    {
        Map m = new HashMap();
        
        for (int i = 0; i < spec.length; i = i + 2)
        {
            String s = (String) spec[i];
            s = s.toLowerCase();
            m.put (s, spec[i+1]);
        }
        
        return m;
    }
    
    
    /**
     * Create a HashMap such that the even numbered myEntries become the keys,
     * and the odd numbered myEntries become the values.
     * 
     * @param spec An array of objects of the form [ key, value, key, value, ...]
     * 
     * @return See above.
     */
    public static Map buildMap (Object[] spec)
    {
        Map m = new HashMap();
        
        for (int i = 0; i < spec.length; i = i + 2)
        {
            m.put (spec[i], spec[i+1]);
        }
        
        return m;
    }
    
    
    public static Map buildMap (Object[][] spec)
    {
    	Map m = new HashMap();
    	
    	for (Object[] mapping : spec)
    	{
    		m.put(mapping[0], mapping[1]);
    	}
    	
    	return m;
    }
    
    /**
     * Create a HashMap such that the odd numbered myEntries become the keys,
     * and the even numbered myEntries become the values.
     * 
     * <P>
     * Given an array of values that is nominally of the form [key, value, key, value,...],
     * build a map that goes from value to key, rather than from key to value.
     * 
     * @param spec See description.
     * @return See description.
     */
    public static final Map buildReversedMap (Object[] spec)
    {
    	Map m = new HashMap();
    	
    	for (int i = 0; i < spec.length; i = i + 2)
    	{
    		m.put (spec[i+1], spec[i]);
    	}
    	
    	return m;
    }
}
