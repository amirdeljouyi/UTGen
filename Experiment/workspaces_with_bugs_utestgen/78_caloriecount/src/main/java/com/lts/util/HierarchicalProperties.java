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
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class HierarchicalProperties
    extends ImprovedProperties
{
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_SEPARATOR = ".";
    
    
    public HierarchicalProperties ()
    {
        super();
        setSeparator(DEFAULT_SEPARATOR);
    }
    
    public HierarchicalProperties (String[] initValues)
    {
        super(initValues);
        setSeparator(DEFAULT_SEPARATOR);
    }
    
    public HierarchicalProperties (Properties p)
    {
        super(p);
        setSeparator(DEFAULT_SEPARATOR);
        
    }
    
    
    protected String mySeparator;
    
    public String getSeparator()
    {
        return mySeparator;
    }
    
    public void setSeparator (String theValue)
    {
        mySeparator = theValue;
    }
    
    
    public Set getImmediatePropertyNames ()
    {
        Set s = new HashSet();
        
        Enumeration enu = keys();
        while (enu.hasMoreElements())
        {
            String k = (String) enu.nextElement();
            int i = k.indexOf(getSeparator());
            
            if (-1 != i)
                k = k.substring(0, i);
            
            s.add(k);
        }
        
        return s;
    }
    
    
    public Set getImmediateParentNames ()
    {
        Set s = new HashSet();
        
        Enumeration enu = keys();
        while (enu.hasMoreElements())
        {
            String k = (String) enu.nextElement();
            int i = k.indexOf(getSeparator());
            
            if (-1 != i)
            {
                k = k.substring(0, i);
                s.add(k);
            }
        }
        
        return s;
    }
    
    
    public Set getImmediateLeafNames ()
    {
        Set s = new HashSet();
        
        Enumeration enu = keys();
        while (enu.hasMoreElements())
        {
            String k = (String) enu.nextElement();
            int i = k.indexOf(getSeparator());
            
            if (-1 == i)
                s.add(k);
        }
        
        return s;
    }
    
    
    public HierarchicalProperties getSubProperties (String theParentProperty)
    {
        if (null == theParentProperty || theParentProperty.equals(""))
            return new HierarchicalProperties (this);
        
        HierarchicalProperties p = new HierarchicalProperties();
        
        if (!theParentProperty.endsWith(getSeparator()))
            theParentProperty = theParentProperty + getSeparator();
        
        int plen = theParentProperty.length();
        
        Enumeration enu = keys();
        while (enu.hasMoreElements())
        {
            String k = (String) enu.nextElement();
            if (0 == k.indexOf(theParentProperty) && k.length() > plen)
            {
                String v = (String) getProperty(k);
                k = k.substring(plen, k.length());
                p.setProperty(k, v);
            }
        }
        
        return p;
    }
    
    
    public void addProperties (String prefix, Properties p)
    {
        Enumeration enu = p.keys();
        while (enu.hasMoreElements())
        {
            String theKey = (String) enu.nextElement();
            StringBuffer sb = new StringBuffer();
            sb.append (prefix);
            sb.append (getSeparator());
            sb.append (theKey);
            
            setProperty(theKey, p.getProperty(theKey));
        }
    }
    
    
    
}
    
