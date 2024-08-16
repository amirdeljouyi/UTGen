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
package com.lts.util.zip;


import java.io.File;
import java.util.Comparator;


public class SimpleZipEntry
{
    public String myDirectoryName;
    public String myFileName;
    
    public static Comparator getComparator ()
    {
        return new Comparator()
        {
            public int compare (Object o1, Object o2)
            {
                SimpleZipEntry e1 = (SimpleZipEntry) o1;
                SimpleZipEntry e2 = (SimpleZipEntry) o2;
                
                return e1.myFileName.compareTo(e2.myFileName);
            }
        };
    }
    
    
    public SimpleZipEntry (String dirname, String fname)
    {
        myDirectoryName = dirname;
        myFileName = fname;
    }
    
    
    public File getCompletePath()
    {
        File f;
        
        if (null == myDirectoryName)
            f = new File(myFileName);
        else
            f = new File(myDirectoryName, myFileName);
        
        return f;
    }
}
