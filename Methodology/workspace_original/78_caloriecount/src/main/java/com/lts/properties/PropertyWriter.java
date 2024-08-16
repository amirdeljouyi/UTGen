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
package com.lts.properties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * A class that knows how to write properties in a form that can be read by the 
 * standard Java properties class.
 * 
 * <H2>Note</H2>
 * This code was shamelessly lifed from {@link Properties#store(OutputStream, String)
 * method.  I would have just called the methods in that class except that they were
 * private.
 * 
 * @author cnh
 */
public class PropertyWriter
{
    /**
     * Writes out the properties passed to us in a particular order.
     * 
     * <P>
     * This method is basically the same as {@link Properties#store(OutputStream, String)} 
     * except that clients can designate the order that properties are written.
     * 
     * @param   out      an output stream.
     * @param   comments   a description of the property list.
     * @param keySequence The order in which the keys should be printed.
     * @param props The properties to print.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this <code>Properties</code> object
     *             contains any keys or values that are not <code>Strings</code>.
     * @exception  NullPointerException  if <code>out</code> is null.
     * @since 1.2
     */
    public void store(OutputStream out, String comments, List<String> keySequence,
			Properties props) throws IOException
	{
        BufferedWriter awriter;
		awriter = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));
		if (comments != null)
			writeln(awriter, "#" + comments);

		writeln(awriter, "#" + new Date().toString());
		for (String key : keySequence)
		{
			String val = (String) props.getProperty(key);
			key = saveConvert(key, true);

			/*
			 * No need to escape embedded and trailing spaces for value, hence pass false
			 * to flag.
			 */
			val = saveConvert(val, false);
			writeln(awriter, key + "=" + val);
		}
		awriter.flush();
    }

    private static void writeln(BufferedWriter bw, String s) throws IOException
	{
		bw.write(s);
		bw.newLine();
	}

    /**
	 * Converts unicodes to encoded &#92;uxxxx and escapes special characters with a
	 * preceding slash.
	 * 
	 * <H2>Note</H2>
	 * This method is copied from a private method in the {@link Properties} class
	 * of the same name and signature.
	 */
    private String saveConvert(String theString, boolean escapeSpace)
	{
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0)
		{
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++)
		{
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127))
			{
				if (aChar == '\\')
				{
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar)
			{
				case ' ':
					if (x == 0 || escapeSpace)
						outBuffer.append('\\');
					outBuffer.append(' ');
					break;
				case '\t':
					outBuffer.append('\\');
					outBuffer.append('t');
					break;
				case '\n':
					outBuffer.append('\\');
					outBuffer.append('n');
					break;
				case '\r':
					outBuffer.append('\\');
					outBuffer.append('r');
					break;
				case '\f':
					outBuffer.append('\\');
					outBuffer.append('f');
					break;
				case '=': // Fall through
				case ':': // Fall through
				case '#': // Fall through
				case '!':
					outBuffer.append('\\');
					outBuffer.append(aChar);
					break;
				default:
					if ((aChar < 0x0020) || (aChar > 0x007e))
					{
						outBuffer.append('\\');
						outBuffer.append('u');
						outBuffer.append(toHex((aChar >> 12) & 0xF));
						outBuffer.append(toHex((aChar >> 8) & 0xF));
						outBuffer.append(toHex((aChar >> 4) & 0xF));
						outBuffer.append(toHex(aChar & 0xF));
					}
					else
					{
						outBuffer.append(aChar);
					}
			}
		}
		return outBuffer.toString();
	}

    /**
	 * Convert a nibble to a hex character
	 * 
	 * <H2>Note</H2>
	 * This method is copied from a method of the same name in the {@link Properties}
	 * class.
	 * 
	 * @param nibble
	 *        the nibble to convert.
	 */
	private static char toHex(int nibble)
	{
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F'
	};

}
