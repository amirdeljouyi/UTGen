package com.lts.util;

import java.util.List;

public class ListUtils
{
	public static void addAll (List list, Object[] objs)
	{
		if (null != objs && objs.length > 0)
		{
			for (int i = 0; i < objs.length; i++)
			{
				list.add(objs[i]);
			}
		}
	}

	public static void addAllStrings(List list, Object[] objs)
	{
		if (null != objs && objs.length > 0)
		{
			for (int i = 0; i < objs.length; i++)
				list.add(objs[i].toString());
		}
	}
}
