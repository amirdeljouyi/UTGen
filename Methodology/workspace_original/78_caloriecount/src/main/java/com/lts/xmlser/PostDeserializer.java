package com.lts.xmlser;

import java.lang.reflect.Method;

import com.lts.LTSException;
import com.lts.lang.reflect.ReflectionUtils;

public class PostDeserializer
{
	static public void postDeserialize(Object o) throws LTSException
	{
		try
		{
			Method method = ReflectionUtils.findMethod(o.getClass(), "postDeserialize");
			if (null != method)
			{
				Object[] args = {};
				method.invoke(o, args);
			}
		}
		catch (Exception e)
		{
			String msg = "Exception caught during invocation of postDeserialize method.";
			throw new LTSException (msg,e);
		}
	}
}
