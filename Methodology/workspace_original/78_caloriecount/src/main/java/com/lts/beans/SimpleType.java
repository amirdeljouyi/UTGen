package com.lts.beans;

import java.util.Map;

import com.lts.util.MapUtil;

public enum SimpleType 
{
	SimpleBoolean,
	SimpleByte,
	SimpleShort,
	SimpleInteger,
	SimpleLong,
	SimpleFloat,
	SimpleDouble,
	SimpleCharacter,
	SimpleString;

	private static Map<Class, SimpleType> ourClassToSimpleType;
	
	private static Object[][] SPEC_CLASS_TO_SIMPLE_TYPE = 
	{
		{ Boolean.TYPE, SimpleBoolean },
		{ Boolean.class, SimpleBoolean },
		{ Byte.TYPE, SimpleByte },
		{ Byte.class, SimpleByte },
		{ Short.TYPE, SimpleShort },
		{ Short.class, SimpleShort },
		{ Integer.TYPE, SimpleInteger },
		{ Integer.class, SimpleInteger },
		{ Long.TYPE, SimpleLong },
		{ Long.class, SimpleLong },
		{ Float.TYPE, SimpleFloat },
		{ Float.class, SimpleFloat },
		{ Double.TYPE, SimpleDouble },
		{ Double.class, SimpleDouble },
		{ Character.TYPE, SimpleCharacter },
		{ Character.class, SimpleCharacter },
		{ String.class, SimpleString }
	};
	
	
	static {
		ourClassToSimpleType = MapUtil.buildMap(SPEC_CLASS_TO_SIMPLE_TYPE);
	}
	
	
	public static boolean isSimpleType(Class clazz)
	{
		return null != ourClassToSimpleType.get(clazz);
	}
	
	public static SimpleType toSimpleType(Class clazz)
	{
		return ourClassToSimpleType.get(clazz);
	}
}
