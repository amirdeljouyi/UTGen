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


import java.util.Enumeration;
import java.util.Properties;


public class ImprovedProperties extends Properties
{
	private static final long serialVersionUID = 1L;


	public ImprovedProperties ()
    {
        super();
    }
    
    public ImprovedProperties (String[] initialValues)
    {
        super();
        
        if (null == initialValues)
            return;
        
        int i = 0;
        while (i < initialValues.length)
        {
            String k = initialValues[i];
            String v = initialValues[i+1];
            
            setProperty(k, v);
            i = i + 2;
        }
    }
    
    
    public ImprovedProperties (Properties p)
    {
        super();
     
        
        Enumeration enu = p.keys();
        while (enu.hasMoreElements())
        {
            String k = (String) enu.nextElement();
            String v = p.getProperty(k);
            
            setProperty(k, v);
        }
    }
}


            
