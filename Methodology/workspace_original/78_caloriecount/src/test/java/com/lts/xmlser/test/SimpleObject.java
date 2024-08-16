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
package com.lts.xmlser.test;


@SuppressWarnings("serial")
public class SimpleObject
	extends TestObject
{
	protected boolean booleanProperty;
	protected Boolean booleanWrapper;
	
	protected byte byteProperty;
	protected Byte byteWrapper;
	
	protected char charProperty;
	protected Character charWrapper;
	
	protected double doubleProperty;
	protected Double doubleWrapper;
	
	protected float floatProperty;
	protected Float floatWrapper;
	
	protected int intProperty;
	protected Integer intWrapper;
	
	protected long longProperty;
	protected Long longWrapper;
	
	protected short shortProperty;
	protected Short shortWrapper;
	
	protected Object nullProperty;
	
	protected Object objectProperty;
	
	protected Object[] arrayProperty;
	
	protected final String foo = "hi there";
	
	
	public SimpleObject()
	{}
	
	public void initialize (
		boolean bProp,
		Boolean bWrap,
		byte byProp,
		Byte byWrap,
		char cProp,
		Character cWrap,
		double dProp,
		Double dWrap,
		float fProp,
		Float fWrap,
		int iProp,
		Integer iWrap,
		long lProp,
		Long lWrap,
		short sProp,
		Short sWrap
	)
	{
		booleanProperty = bProp;
		booleanWrapper = bWrap;
		byteProperty = byProp;
		byteWrapper = byWrap;
		charProperty = cProp;
		charWrapper = cWrap;
		doubleProperty = dProp;
		doubleWrapper = dWrap;
		floatProperty = fProp;
		floatWrapper = fWrap;
		intProperty = iProp;
		intWrapper = iWrap;
		longProperty = lProp;
		longWrapper = lWrap;
		shortProperty = sProp;
		shortWrapper = sWrap;
		
		objectProperty = "hi there";
		
		arrayProperty = new Object[] {
			"hello", new Double(3.14), new Integer(5), new Boolean(true)
		};
	}
	
	public SimpleObject (
		boolean bProp,
		Boolean bWrap,
		byte byProp,
		Byte byWrap,
		char cProp,
		Character cWrap,
		double dProp,
		Double dWrap,
		float fProp,
		Float fWrap,
		int iProp,
		Integer iWrap,
		long lProp,
		Long lWrap,
		short sProp,
		Short sWrap
	)
	{
		initialize (
			bProp, 
			bWrap, 
			byProp, 
			byWrap, 
			cProp, 
			cWrap,
			dProp,
			dWrap,
			fProp,
			fWrap,
			iProp,
			iWrap,
			lProp,
			lWrap,
			sProp,
			sWrap
		);
	}
	
	
	public boolean equals (Object o)
	{
		SimpleObject so = (SimpleObject) o;
		return 
			so.booleanProperty == booleanProperty
			&& so.booleanWrapper.equals(booleanWrapper)
			&& so.byteProperty == byteProperty
			&& so.byteWrapper.equals(byteWrapper)
			&& so.charProperty == charProperty
			&& so.charWrapper.equals(charWrapper)
			&& so.doubleProperty == doubleProperty
			&& so.doubleWrapper.equals(doubleWrapper)
			&& so.floatProperty == floatProperty
			&& so.floatWrapper.equals(floatWrapper)
			&& so.intProperty == intProperty
			&& so.intWrapper.equals(intWrapper)
			&& so.longProperty == longProperty
			&& so.longWrapper.equals(longWrapper)
			&& so.shortProperty == shortProperty
			&& so.shortWrapper.equals(shortWrapper);
	}
}
