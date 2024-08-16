package com.lts.util.deepcopy;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.lts.lang.reflect.ReflectionUtils;

/**
 * Mehtods useful in making deep copies of objects.
 * 
 * @author cnh
 */
public class DeepCopyUtil
{
	public static Object startDeepCopy (DeepCopier original) throws DeepCopyException
	{
		Map map = new IdentityHashMap();
		Object copy = original.continueDeepCopy(map, false);
		return copy;
	}
	
	
	public static Object createInstance(Object o) throws DeepCopyException
	{
		if (null == o)
			return null;
		
		Class clazz = o.getClass();
		
		try
		{
			return clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new DeepCopyException(e);
		}
	}
	
	
	public static String notDeep (Object o)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append ("The object, ");
		sb.append (o);
		sb.append (", of class ");
		sb.append (o.getClass());
		sb.append (", does not implement ");
		sb.append (DeepCopier.class.getName());
		
		return sb.toString();
	}
	
	
	public static List copyList (List list, Map map, boolean copyTransients)
		throws DeepCopyException
	{
		List copy = (List) map.get(list);
		if (null == copy)
			copy = (List) createInstance(list);
		
		for (Object o : list)
		{
			if (!(o instanceof DeepCopier))
				throw new DeepCopyException(notDeep(o));
			
			DeepCopier element = (DeepCopier) o;
			DeepCopier elementCopy = element.continueDeepCopy(map, copyTransients);
			copy.add(elementCopy);
		}
		
		map.put(list, copy);
		return copy;
	}
	
	
	public static Object continueDeepCopy (Object o, Map map, boolean copyTransients)
		throws DeepCopyException
	{
		if (null == o)
			return null;
		
		Object copy = map.get(o);
		if (null != copy)
			return copy;
		
		if (!(o instanceof DeepCopier))
			throw new DeepCopyException(notDeep(o));
		
		DeepCopier original = (DeepCopier) o;
		copy = createInstance(original);
		map.put(original, copy);
		
		original.deepCopyData(copy, map, copyTransients);
		return copy;
	}


	public static Object deepCopy(DeepCopier original, boolean copyTransients) throws DeepCopyException
	{
		Map map = new IdentityHashMap();
		DeepCopier copy = (DeepCopier) createInstance(original);
		map.put(original, copy);
		
		original.deepCopyData(copy, map, copyTransients);
		
		return copy;
	}
	
	
	static private String NAME_DEEP_COPY_DATA = "deepCopyData";
	static private Class[] ARGS_DEEP_COPY_DATA = { Object.class };
	
	public static Object deepCopyObject (IdentityHashMap<Class, Method> classToMethod, Object original, boolean copyTransients) 
		throws DeepCopyException
	{
		Map map = new IdentityHashMap();
		Object copy = createInstance(original);
		
		Method method = classToMethod.get(copy.getClass());
		if (null == method)
		{
			Class clazz = copy.getClass();
			method = ReflectionUtils.getMethod(clazz, NAME_DEEP_COPY_DATA, ARGS_DEEP_COPY_DATA);
			method.setAccessible(true);
			classToMethod.put(clazz, method);
		}
		
		Object[] args = new Object[] { original };
		
		try
		{
			method.invoke(original, args);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error trying to deepCopy object";
			throw new DeepCopyException(msg, e);
		}
		
		map.put(original, copy);
		
		return copy;
	}
	
	
	public static DeepCopyException transientNotImplemented(Object o)
	{
		Class clazz = o.getClass();
		
		StringBuffer sb = new StringBuffer();
		sb.append(clazz.getName());
		sb.append(" does not implement copying transient data");
		
		return new DeepCopyException(sb.toString());
	}
	
	
	public static DeepCopyException deepCopyNotSupported (Object o)
	{
		Class clazz = o.getClass();
		
		StringBuffer sb = new StringBuffer();
		sb.append (clazz.getName());
		sb.append (" does not support deep copy");
		
		return new DeepCopyException(sb.toString());
	}
	
	
	public static Object deepCopyField (Object o, Map map, boolean copyTransients) 
		throws DeepCopyException
	{
		Object theCopy = map.get(o);
		if (null == theCopy)
		{
			theCopy = DeepCopyUtil.continueDeepCopy(theCopy, map, copyTransients);
		}
		
		return theCopy;
	}
}
