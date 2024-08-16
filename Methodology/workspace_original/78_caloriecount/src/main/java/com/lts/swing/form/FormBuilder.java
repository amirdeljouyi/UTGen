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
/*
 * Created on Jul 9, 2004
 */
package com.lts.swing.form;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleGridBagConstraint;
import com.lts.util.StringIntMap;

/**
 * A class that builds simple label/field type forms.
 * 
 * <P>
 * This class simplifies creating forms for Swing applications by allowing 
 * developers to supply an array of strings that the class uses to populate
 * a JPanel with labels and fields.
 * 
 * <P>
 * Here is an example specification:
 * 
 * <PRE>
 * Object[] FORM_SPEC = {
 *     "First Name:", "i:5", myFirstNameField, "f:h",
 *     "Last Name:, "i:5", myLastNameField, "f:h",
 *     "Age:", "i:5", myAgeField, "f:h",  
 * }; 
 * </PRE>
 * 
 * A form specification has the following form:
 * 
 * <PRE>
 * &lt;label text&gt;, &lt;label options&gt;, &lt;field component&gt;, &lt;field options&gt;,
 * </PRE>
 * 
 * <P>
 * &lt;label text&gt; is the text that should be placed in the form.
 * 
 * <P>
 * &lt;label options&gt; are the options for the label.  Options have the 
 * following form:
 * 
 * <PRE>
 * &lt;option&gt;:&lt;value&gt;[, &lt;more options&gt;]
 * </PRE>
 * 
 * <P>
 * The options that the class current supports for labels are:
 * 
 * <UL>
 * <LI>insets/i - the insets for this label, in pixels.
 * </UL>
 * 
 * <P>
 * &lt;field&gt; is the field that should be placed on the same line as the
 * label.  Objects must a subclass of JComponent or a ClassCastException 
 * will occur during the parsing of the specification.
 * 
 * <P>
 * &lt;field options&gt; the options for the field.  The options for fields
 * have the same form as the options for labels, but the name/value pairs
 * are different.  The allowable option/values are:
 * 
 * <UL>
 * <LI>fill/f - whether the field should fill available space.  Valid values
 * are:
 * 
 *     <UL>
 *     <LI>horizontal/h - Fill horizontally.
 *     <LI>vertical/v - Fill vertically.
 *     <LI>both/b - Fill horizontall and vertically
 *     </UL>
 * 
 * <LI>inset/i - the insets for this field, in pixels.
 * </UL>
 */
public class FormBuilder 
{
	protected Insets myDefaultInsets;
	
	public static final String INSET_LONG = "inset";
	public static final String INSET_SHORT = "i";
	public static final int OPTION_INSET = 0;
	
	public static final String FILL_LONG = "fill";
	public static final String FILL_SHORT = "f";
	public static final int OPTION_FILL = 1;
	
	public static final String LABEL_LONG = "label";
	public static final String LABEL_SHORT = "l";
	public static final int OPTION_LABEL = 2;
	
	public static final String FIELD_LONG = "field";
	public static final String FIELD_SHORT = "e";
	public static final int OPTION_FIELD = 3;
	
	public static final String COLSPAN_LONG = "colspan";
	public static final String COLSPAN_SHORT = "c";
	public static final int OPTION_COLSPAN = 4;
	
	public static final Object[] SPEC_OPTIONS = {
		INSET_LONG, 		new Integer(OPTION_INSET),
		INSET_SHORT,		new Integer(OPTION_INSET),
		FILL_LONG,			new Integer(OPTION_FILL),
		FILL_SHORT,			new Integer(OPTION_FILL),
		LABEL_LONG,			new Integer(OPTION_LABEL),
		LABEL_SHORT,		new Integer(OPTION_LABEL),
		FIELD_LONG,			new Integer(OPTION_FIELD),
		FIELD_SHORT,		new Integer(OPTION_FIELD),
		COLSPAN_LONG,		new Integer(OPTION_COLSPAN),
		COLSPAN_SHORT,		new Integer(OPTION_COLSPAN),
	};
		
	public static StringIntMap ourStringOptionMap = new StringIntMap(SPEC_OPTIONS);
	
	public static int stringToOption (String s)
	{
		return ourStringOptionMap.stringToInt(s);
	}
	
	public static String optionToString (int option)
	{
		return ourStringOptionMap.intToString(option);
	}
	
	
	public static final String FILL_HORIZONTAL_LONG = "horizontal";
	public static final String FILL_HORIZONTAL_SHORT = "h";
	public static final int OPTION_FILL_HORIZONTAL = 0;
	
	public static final String FILL_VERTICAL_LONG = "vertical";
	public static final String FILL_VERTICAL_SHORT = "v";
	public static final int OPTION_FILL_VERTICAL = 1;
	
	public static final String FILL_BOTH_LONG = "both";
	public static final String FILL_BOTH_SHORT = "b";
	public static final int OPTION_FILL_BOTH = 2;
	
	public static final Object[] SPEC_FILL_OPTIONS = {
		FILL_HORIZONTAL_LONG,	new Integer(OPTION_FILL_HORIZONTAL),
		FILL_HORIZONTAL_SHORT,	new Integer(OPTION_FILL_HORIZONTAL),
		FILL_VERTICAL_LONG,		new Integer(OPTION_FILL_VERTICAL),
		FILL_VERTICAL_SHORT,	new Integer(OPTION_FILL_VERTICAL),
		FILL_BOTH_LONG,			new Integer(OPTION_FILL_BOTH),
		FILL_BOTH_SHORT,		new Integer(OPTION_FILL_BOTH),
	};
	
	public static StringIntMap ourFillOptionMap = 
		new StringIntMap (SPEC_FILL_OPTIONS);
	
	public static int stringToFillOption (String str)
	{
		return ourFillOptionMap.stringToInt(str);
	}
	
	public static String fillOptionToString (int option)
	{
		return ourFillOptionMap.intToString(option);
	}
	
	public static final String OPTION_DELIMITERS = ", ";
	public static final String ARGUMENT_DELIMITERS = ":";
	
	protected static FormBuilder ourInstance = new FormBuilder();
	
	
	public void updateConstraint (
		SimpleGridBagConstraint constraint,
		String strOption, 
		String strArg
	)
		throws LTSException
	{
		try 
		{
			int option = stringToOption(strOption);
			
			switch (option)
			{
				case OPTION_FILL :
				{
					int fillOption = stringToFillOption(strArg);
					switch (fillOption)
					{
						case OPTION_FILL_HORIZONTAL :
							constraint.fill = GridBagConstraints.HORIZONTAL;
							break;
						
						case OPTION_FILL_VERTICAL :
							constraint.fill = GridBagConstraints.VERTICAL;
							break;
							
						case OPTION_FILL_BOTH :
							constraint.fill = GridBagConstraints.BOTH;
							break;
							
						default :
							throw new LTSException ("Unrecognized fill argument: " + strArg);
					}
					break;
				}
			
				case OPTION_INSET :
				{
					int value = Integer.parseInt(strArg);
					Insets insets = new Insets(value, value, value, value);
					constraint.insets = insets;
					break;
				}
				
				case OPTION_LABEL :
					constraint.anchor = GridBagConstraints.WEST;
					constraint.weightx = 0;
					constraint.weighty = 0;
					constraint.fill = GridBagConstraints.NONE;
					break;
					
				case OPTION_FIELD :
					constraint.anchor = GridBagConstraints.CENTER;
					constraint.weightx = 1.0;
					constraint.weighty = 0.0;
					constraint.fill = GridBagConstraints.HORIZONTAL;
					break;
					
				case OPTION_COLSPAN :
					constraint.gridwidth = Integer.parseInt(strArg);
					break;
				
				default :
					throw new LTSException("Unrecognized option: " + strOption);
			}
		} 
		catch (Exception e)
		{
			throw new LTSException (
				"Error trying to parse option: " + strOption
				+ ", and argument: " + strArg,
				e
			);
		}
	}
	
	
	public void processComponent (LTSPanel panel, JComponent label, String options)
		throws LTSException
	{
		StringTokenizer st = new StringTokenizer(options, OPTION_DELIMITERS);
		SimpleGridBagConstraint constraint = new SimpleGridBagConstraint();
		constraint.gridheight = 1;
		constraint.gridwidth = 1;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		constraint.insets = getDefaultInsets();
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			String option = token;
			String arg = null;
			int index = token.indexOf(ARGUMENT_DELIMITERS);
			if (-1 != index)
			{
				option = token.substring(0,index);
				arg = token.substring(1 + index);
				arg = arg.trim();
			}
			
			option = option.trim();
			
			updateConstraint(constraint, option, arg);
		}

		panel.addWithConstraint(label, constraint);
	}
	
	
	public JPanel build (Object[] spec, LTSPanel panel)
		throws LTSException
	{
		for (int i = 0; i < spec.length; i = i + 4)
		{
			String text = (String) spec[i];
			JLabel label = new JLabel(text);
			String options = (String) spec[1+i];
			processComponent (panel, label, options);
			
			JComponent field = (JComponent) spec[2+i];
			options = (String) spec[3+i];
			processComponent (panel, field, options);
			
			panel.nextRow();
		}
		
		return panel;
	}
	
	public JPanel build (Object[] spec)	throws LTSException
	{
		LTSPanel panel = new LTSPanel();		
		return build(spec, panel);
	}
		
	public Insets getDefaultInsets ()
	{
		if (null == myDefaultInsets)
			myDefaultInsets = new Insets(0,0,0,0);
		
		return myDefaultInsets;
	}
	
	public void setDefaultInsets (int value)
	{
		myDefaultInsets = new Insets(value, value, value, value);
	}
	
	public void setDefaultInsets (Insets insets)
	{
		myDefaultInsets = insets;
	}
}
