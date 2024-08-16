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
package com.lts.io;


import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;


public class IndentingPrintWriter
    extends PrintWriter
{
    protected String myPrefix;
    protected boolean myPrefixPrinted;
    protected int myIndentSize;
    protected boolean mySuppressPrefix = false;
    
    
    public IndentingPrintWriter (Writer w)
    {
        super(w);
        initialize();
    }
    
    
    public static Writer toWriter(OutputStream ostream)
    {
    	Writer writer = new OutputStreamWriter(ostream);
    	return writer;
    }
    
    
    public IndentingPrintWriter (OutputStream ostream)
    {
    	super(toWriter(ostream));
    	initialize();
    }
    
    
    public void initialize ()
    {
        setPrefix("");
        setNeedToPrintPrefix(true);
        setIndentSize(4);
    }
    
    
    public boolean getSuppressPrefix()
    {
    	return mySuppressPrefix;
    }
    
    public void setSuppressPrefix(boolean b)
    {
    	mySuppressPrefix = b;
    }
    
    public boolean suppressPrefix()
    {
    	return mySuppressPrefix;
    }
    
    
    public int getIndentSize ()
    {
        return myIndentSize;
    }
    
    public void setIndentSize (int i)
    {
        myIndentSize = i;
    }
    
    
    public String getPrefix ()
    {
        if (null ==  myPrefix)
            myPrefix = "";
        
        return myPrefix;
    }
    
    public void setPrefix (String s)
    {
        myPrefix = s;
    }
    
    public void increaseIndent ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append (getPrefix());
        
        for (int i = 0; i < getIndentSize(); i++)
            sb.append (' ');
        
        setPrefix(sb.toString());
    }
    
    public void decreaseIndent ()
    {
        String s = getPrefix();
        int end = s.length() - getIndentSize();
        
        if (end <= 0)
            s = "";
        else
            s = s.substring(0, end);
        
        setPrefix(s);
    }
            
    
    public boolean needToPrintPrefix ()
    {
        return !myPrefixPrinted;
    }
    
    
    public void setNeedToPrintPrefix (boolean b)
    {
        myPrefixPrinted = !b;
    }
    
    public void printPrefix ()
    {
        if (needToPrintPrefix() && !suppressPrefix())
        {
            super.print(getPrefix());
            setNeedToPrintPrefix(false);
        }
    }
    
    public void print(float f)
    {
        printPrefix();
        super.print(f);
    }

    public void print(long l)
    {
        printPrefix();
        super.print(l);
    }

    public void print(int i)
    {
        printPrefix();
        super.print(i);
    }

    public void print(double d)
    {
        printPrefix();
        super.print(d);
    }

    public void print(Object obj)
    {
    	String s = "null";
    	if (null != obj)
    		s = obj.toString();
    		
        print(s);
    }

    public void print(String s)
    {
    	char[] ca = s.toCharArray();
    	print(ca);
    }

    public void print(char[] s)
    {
    	for (int i = 0; i < s.length; i++)
    	{
    		print(s[i]);
    	}
    }

    public void print(boolean b)
    {
        printPrefix();
        super.print(b);
    }

    public void print(char c)
    {
        printPrefix();
        super.print(c);
        if ('\n' == c)
        	setNeedToPrintPrefix(true);
    }

    public void println(float x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println(long x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println(int x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println(double x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println(Object x)
    {
        printPrefix();
        super.println(x);
    }

    public void println(String x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println(char[] x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println(char x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }

    public void println()
    {
        printPrefix();
        super.println();
        setNeedToPrintPrefix(true);
    }

    public void println(boolean x)
    {
        printPrefix();
        super.println(x);
        setNeedToPrintPrefix(true);
    }
}
