// Copyright 2006, Clark N. Hobbie
//
// This file is part of the util library.
//
// The util library is free software; you can redistribute it and/or modify it
// under the terms of the Lesser GNU General Public License as published by
// the Free Software Foundation; either version 2.1 of the License, or (at
// your option) any later version.
//
// The util library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
// or FITNESS FOR A PARTICULAR PURPOSE. See the Lesser GNU General Public
// License for more details.
//
// You should have received a copy of the Lesser GNU General Public License
// along with the util library; if not, write to the Free Software Foundation,
// Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
/*
 * Copyright (c) 2003 by Long Term Software, LLC. All rights reserved.
 */
package com.lts.swing.menu;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import com.lts.util.TreeNode;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

/**
 * @author cnh To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MenuNode extends TreeNode
{
	public static final int METHOD = 0;

	public static final int ITEM = 1;

	public static final int MENU = 2;

	public static final int ROOT = 3;

	public static final int SEPARATOR = 4;

	protected String myName;

	protected int myNodeType;

	public int getNodeType()
	{
		return myNodeType;
	}

	public void setNodeType(int theType)
	{
		myNodeType = theType;
	}

	public String getName()
	{
		return myName;
	}

	public void setName(String s)
	{
		myName = s;
	}

	public MenuNode(int type)
	{
		setNodeType(type);
	}

	public MenuNode(String name, int type)
	{
		setName(name);
		setNodeType(type);
	}

	public void basicGetPath(StringBuffer sb)
	{
		if (null != getParent())
		{
			MenuNode mnode = (MenuNode) getParent();
			mnode.basicGetPath(sb);
		}

		sb.append("/");
		sb.append(getName());
	}

	public String getPath()
	{
		StringBuffer sb = new StringBuffer(128);
		basicGetPath(sb);
		return sb.toString();
	}

	public String getMethodName()
	{
		MenuNode node = (MenuNode) getChildren().get(0);
		return node.getName();
	}

	public MenuNode getChildNamed(String name)
	{
		for (Iterator i = getChildren().iterator(); i.hasNext();)
		{
			MenuNode node = (MenuNode) i.next();
			if (null != node.getName() && name.equals(node.getName()))
				return node;
		}

		return null;
	}

	protected Method myMethod;

	public Method getMethod()
	{
		return myMethod;
	}

	public void setMethod(Method method)
	{
		myMethod = method;
	}

	public void deepCopyData(Object ocopy, Map map, boolean copyTransients)
			throws DeepCopyException
	{
		throw DeepCopyUtil.deepCopyNotSupported(this);
	}

}
