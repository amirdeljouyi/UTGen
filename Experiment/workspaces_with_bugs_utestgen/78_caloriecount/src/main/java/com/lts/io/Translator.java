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


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;

import com.lts.LTSException;
import com.lts.util.CaselessMap;


public class Translator
{
    protected Reader myReader;
    protected PrintWriter myWriter;
    protected static Map ourStateToString;
    
    public Reader getReader()
    {
        return myReader;
    }
    
    public void setReader(Reader r)
    {
        myReader = r;
    }
    
    
    public PrintWriter getWriter ()
    {
        return myWriter;
    }
    
    public void setWriter (PrintWriter w)
    {
        myWriter = w;
    }
    
    
    public Translator (InputStream istream, OutputStream ostream)
    {
        initialize(istream, ostream);
    }
    
    public Translator (Reader reader, PrintWriter writer)
    {
        initialize(reader, writer);
    }
    
    public Translator ()
    {
    }
    
    protected void initialize (Reader r, PrintWriter w)
    {
        setReader(r);
        setWriter(w);
    }
    
    
    protected void initialize (InputStream istream, OutputStream ostream)
    {
        Reader r = new InputStreamReader(istream);
        PrintWriter out = new PrintWriter(ostream);
        initialize(r,out);
    }
    
    
    public static final int STATE_START = 0;
    public static final int STATE_START2 = 1;
    public static final int STATE_MATCHING = 2;
    
    public static final char CHAR_START = '$';
    public static final char CHAR_START2 = '{';
    public static final char CHAR_END = '}';
    public static final char CHAR_ESCAPE = '\\';
    public static final char CHAR_NEWLINE = '\n';
    public static final char CHAR_CARRIAGE_RETURN = '\r';
    
    
    public static final Object[] SPEC_STATE_TO_STRING = 
    {
        new Integer(STATE_START),       "start",
        new Integer(STATE_START2),      "start2",
        new Integer(STATE_MATCHING),    "matching"
    };
    
    protected static Map getStateToString()
    {
        if (null == ourStateToString)
            ourStateToString = new CaselessMap(SPEC_STATE_TO_STRING);
        
        return ourStateToString;
    }
    
    protected static String stateToString (int state)
    {
        Integer i = new Integer(state);
        String s = (String) getStateToString().get(i);
        return s;
    }
    
    protected int processStart (int state) throws LTSException
    {
        switch (state)
        {
            //
            // $ ...
            //  ^
            //
            case STATE_START :
                state = STATE_START2;
                break;
                        
            //
            // $$ ...
            //   ^
            //
            case STATE_START2 :
                state = STATE_START;
                myWriter.write(CHAR_START);
                break;
                        
            //
            // ${...$ ...
            //       ^
            //
            case STATE_MATCHING :
                throw new LTSException (
                    "encountered invalid sequence: "
                    + "${...$"
                );
                        
            default :
                throw new LTSException ("Invalid state: " + state);
        }
        
        return state;
    }
    
    
    protected int processStart2 (int state)
        throws LTSException
    {
        switch (state)
        {
            //
            // { ...
            //  ^
            //
            case STATE_START :
                myWriter.write(CHAR_START2);
                break;
                        
            //
            // ${ ...
            //   ^
            //
            case STATE_START2 :
                state = STATE_MATCHING;
                break;
                        
            //
            // ${...{ ...
            //       ^
            //
            case STATE_MATCHING :
                throw new LTSException (
                    "Encountered invalid sequence, ${{"
                );
                        
            default :
                throw new LTSException ("Invalid state: " + state);
        }
        
        return state;
    }
    
    
    protected class TransResult 
    {
        int newstate;
        int nextchar;
        String tag;
    }
    
    
    protected TransResult processEnd (int state, StringBuffer buf)
        throws IOException, LTSException
    {
        TransResult result = new TransResult();
        
        switch (state)
        {
            //
            // ...} ...
            //     ^
            //
            case STATE_START :
                myWriter.write(CHAR_END);
                result.nextchar = myReader.read();
                result.newstate = STATE_START;
                break;
                        
            //
            // $} ...
            //   ^
            //
            case STATE_START2 :
                throw new LTSException ("Invalid sequence: $}");
                        
            //
            // ${...}
            //       ^
            //
            case STATE_MATCHING :
                result.newstate = STATE_START;
                result.tag = buf.toString();
                break;
                        
            default :
                throw new LTSException ("Invalid state: " + state);
        } // switch(state)
        
        return result;
    }
    
    
    protected void processDefault (int state, int c, StringBuffer buf)
        throws LTSException
    {
        switch (state)
        {
            case STATE_START :
                myWriter.write((char) c);
                break;
                        
            //
            // $ ...
            //  ^
            //
            case STATE_START2 :
                throw new LTSException (
                    "Invalid sequence --- $ must be escaped or "
                    + "introduce a tag."
                );
                        
            //
            // ${...
            //      ^
            //
            case STATE_MATCHING :
                buf.append((char) c);
                break;
                        
            default :
                throw new LTSException ("Invalid state: " + state);
        } // switch(state)
    }
    
    
    protected void processNewline (int state)
        throws LTSException
    {
        switch (state)
        {
            case STATE_START2 :
            case STATE_MATCHING :
                throw new LTSException (
                    "Tags cannot span multiple lines.  "
                    + "Current state = " + stateToString(state)
                );
            
            case STATE_START :
            default :
                myWriter.println();
                break;
        }
    }
    
    
    protected void processCarriageReturn(int state) throws LTSException
    {
        switch (state)
        {
            case STATE_START2 :
            case STATE_MATCHING :
                throw new LTSException (
                    "Tags cannot span multiple lines.  "
                    + "Current state = " + stateToString(state)
                );
            
            case STATE_START :
            default :
                break;
        }
    }
    
    
    public String next () throws LTSException, IOException
    {
        int state = STATE_START;
        StringBuffer buf = new StringBuffer();
        String tag = null;
        
        int c = myReader.read();
        while (-1 != c && null == tag)
        {
            switch (c)
            {
                case CHAR_START :
                    state = processStart(state);
                    c = myReader.read();
                    break;
                    
                case CHAR_START2 :
                    state = processStart2(state);
                    c = myReader.read();
                    break;
                
                case CHAR_NEWLINE :
                    processNewline(state);
                    c = myReader.read();
                    break;
                
                case CHAR_CARRIAGE_RETURN :
                    processCarriageReturn(state);
                    c = myReader.read();
                    break;
                    
                case CHAR_END :
                {
                    TransResult result = processEnd(state, buf);
                    tag = result.tag;
                    state = result.newstate;
                    c = result.nextchar;
                    break;
                }
                
                default :
                    processDefault(state, c, buf);
                    c = myReader.read();
                    break;
            }
        }
        
        return tag;
    }
}
