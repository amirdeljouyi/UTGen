//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.swing.layout;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class WindowUtils
{
	public static void resize (Container comp, int factor)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		Dimension size = new Dimension(dim.width/factor, dim.height/factor);
		comp.setSize(size);
	}
	
	public static Dimension getFractionOfScreenSize(int denominator)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		dim = new Dimension(dim.width/denominator, dim.height/denominator);
		return dim;
	}
	
	
	public static void center (Container cont)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		Dimension size = cont.getSize();
		int x = (screen.width - size.width)/2;
		int y = (screen.height - size.height)/2;
		cont.setLocation(x, y);
	}
	
	public static int labelWidth (String msg, Font font)
	{
		JLabel label = new JLabel(msg);
		label.setFont(font);
		return label.getSize().width;
	}
	
	
	public static int getScreenWidth()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		return screen.width;
	}
	
	
	public static int labelWidth (int start, String s, Font font)
	{
		JLabel label = new JLabel(s);
		label.setFont(font);
		return label.getWidth();
	}
	
	
//	public static JPanel buildTextPanel (String msg, Font font, int width, int height) throws Exception
//	{
//		//
//		// The basic algorithm is this: keep adding words to a line until it exceeds
//		// the width.  At that point try adding a newline and then adding the word.
//		// if that does not work then give up, otherwise keep going until you run out
//		// of words
//		//
//		LTSPanel panel = new LTSPanel();
//		StringBuffer lines = new StringBuffer();
//		String cur = "";
//		
//		String[] words = msg.split(" ");
//		
//		for (int i = 0; i < words.length; i++)
//		{
//			//
//			// if the current word, by itself, exceeds the max length, then signal
//			// failure.
//			//
//			if (labelWidth(words[i], font) > width)
//			{
//				String message = "The word, " + words[i] + ", exceeds the max width, " + width;
//				throw new Exception(message);
//			}
//			
//			String temp;
//			if (cur.length() > 0)
//				temp = cur + ' ' + words[i];
//			else
//				temp = words[i];
//			
//			//
//			// if the new word causes the current to exceed the limit, then start
//			// a new line
//			//
//			if (labelWidth(temp, font) > width)
//			{
//				lines.append(cur);
//				lines.append("\n");
//				cur = words[i];
//			}
//			
//			//
//			// otherwise, add the current word and keep going 
//			//
//			else
//			{
//				lines.append(words[i]);
//			}
//		}
//		
//		//
//		// We now have one string such that 
//		
//		return panel;
//	}
	
	
	public static JTextArea buildTextArea(String message, Font font, int width)
	{
		JTextArea textArea = new JTextArea(message);
		textArea.setFont(font);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		Dimension size = new Dimension();
		size.width = width;
		size.height = Integer.MAX_VALUE;
		textArea.setSize(size);
		
		return textArea;
	}
	
	
	public static Dimension getScreenSize()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		return screen;
	}
}
