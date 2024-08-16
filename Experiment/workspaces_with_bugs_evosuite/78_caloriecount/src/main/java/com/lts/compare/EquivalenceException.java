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
package com.lts.compare;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lts.lang.reflect.ReflectionUtils;

@SuppressWarnings(value="serial")
public class EquivalenceException extends Exception
{
	protected List myRecords;
	protected int myReason;
	
	public static final int REASON_UNKNOWN = 0;
	public static final int REASON_FIELD_ACCESS_EXCEPTION = 1;
	public static final int REASON_NOT_EQUIVALENT = 2;
	public static final int REASON_DIFFERENT_CLASSES = 3;
	public static final int REASON_EXCEPTION = 4;
	public static final int REASON_ARRAY_SIZE = 5;
	
	public static final String STR_REASON_UNKNOWN = "unknown";
	public static final String STR_REASON_FIELD_ACCESS_EXCEPTION = "field access exception";
	public static final String STR_REASON_NOT_EQUIVALENT = "objects are not equal";
	public static final String STR_REASON_DIFFERENT_CLASSES = "fields contained objects of incompatible classes";
	public static final String STR_REASON_EXCEPTION = "an exception was thrown";
	public static final String STR_REASON_ARRAY_SIZE = "different array dimensions";
	
	
	public static final Object[] SPEC_STRING_TO_REASON = {
		STR_REASON_UNKNOWN, 		new Integer(REASON_UNKNOWN),
		STR_REASON_FIELD_ACCESS_EXCEPTION,	new Integer(REASON_FIELD_ACCESS_EXCEPTION),
		STR_REASON_NOT_EQUIVALENT, 	new Integer(REASON_NOT_EQUIVALENT),
		STR_REASON_DIFFERENT_CLASSES, new Integer(REASON_DIFFERENT_CLASSES),
		STR_REASON_EXCEPTION, 		new Integer(REASON_EXCEPTION),
		STR_REASON_ARRAY_SIZE,		new Integer(REASON_ARRAY_SIZE)
	};
	
	
	public int getReason ()
	{
		return myReason;
	}
	
	public void setReason (int reason)
	{
		myReason = reason;
	}
	
	public EquivalenceException ()
	{}
	
	
	public EquivalenceException (String message)
	{
		super(message);
	}
	
	public EquivalenceException (String message, Throwable theCause)
	{
		super(message, theCause);
	}
	
	public EquivalenceException (int reason, CompareRecord rec)
	{
		setReason(reason);
		pushRecord(rec);
	}
	
	public EquivalenceException (int reason, CompareRecord rec, Throwable theCause)
	{
		super(theCause);
		setReason(reason);
		pushRecord(rec);
	}
	
	
	public EquivalenceException (int reason)
	{
		setReason(reason);
	}
	
	public EquivalenceException (String message, int reason)
	{
		super(message);
		setReason(reason);
	}
	
	public EquivalenceException (String message, Throwable theCause, int reason)
	{
		super(message, theCause);
		setReason(reason);
	}
	
	
	public EquivalenceException (Throwable theCause)
	{
		super(theCause);
	}
	
	public EquivalenceException (
		Throwable theCause, 
		CompareRecord rec
	)
	{
		super(theCause);
		pushRecord(rec);
	}
	
	
	public List getRecords ()
	{
		if (null == myRecords)
			myRecords = new ArrayList();
		
		return myRecords;
	}
	
	public void pushRecord (CompareRecord rec)
	{
		getRecords().add(0, rec);
	}
	
	
	public String toFieldList (List records)
	{
		if (records.size() <= 0)
			return "";
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < records.size(); i++)
		{
			CompareRecord rec = (CompareRecord) records.get(i);
			sb.append(rec.toLinkString(i <= 0));
		}
		
		return sb.toString();
	}
	

	public CompareRecord firstRecord()
	{
		if (getRecords().size() > 0)
			return (CompareRecord) getRecords().get(0);
		else
			return null;
	}
	
	
	public CompareRecord lastRecord ()
	{
		int length = getRecords().size();
		if (length <= 0)
			return null;
		else
			return (CompareRecord) getRecords().get(length - 1);
	}
	
	
	public String getMessage ()
	{
		StringBuffer sb = new StringBuffer();
		CompareRecord rec = lastRecord();
		
		String s = toFieldList(getRecords());
		sb.append (s);
		
		if (s.length() > 0)
			sb.append (": ");
		
		switch (myReason)
		{
			case REASON_NOT_EQUIVALENT :
				sb.append (rec.object1);
				sb.append (" != ");
				sb.append (rec.object2);
				break;
				
			case REASON_DIFFERENT_CLASSES :
				sb.append (rec.object1.getClass());
				sb.append (" != ");
				sb.append (rec.object2.getClass());
				break;
				
			case REASON_ARRAY_SIZE :
				sb.append (Array.getLength(rec.object1));
				sb.append (" != ");
				sb.append (Array.getLength(rec.object2));
				break;
				
			case REASON_EXCEPTION :
				sb.append (getCause().getMessage());
				break;
				
			default :
				sb.append(super.getMessage());
				break;
		}
		
		return sb.toString();
	}
	
	
	public String toSimpleString (Object o)
	{
		if (null == o)
			return "null";
		else if (ReflectionUtils.isSimpleType(o.getClass()))
			return o.toString();
		else
			return o.getClass().getName(); 
	}
	
		
	public void initialize (Field theField, Object o1, Object o2)
	{
		FieldCompareRecord rec = new FieldCompareRecord(theField, o1, o2);
		pushRecord(rec);
	}
	
	public void initialize (int index, Object o1, Object o2)
	{
		ArrayCompareRecord rec = new ArrayCompareRecord(index, o1, o2);
		pushRecord(rec);
	}
}
