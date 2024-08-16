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
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.treetable.test;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.StandardFrame;
import com.lts.swing.treetable.TreeTable;
import com.lts.swing.treetable.TreeTableModel;

/**
 * @author cnh
 */
public class TreeTableWindow 
	extends StandardFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TreeTableWindow()
		throws LTSException
	{
		super();
		initialize();
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel p = new LTSPanel();
		
		TreeTableModel model = new TestTableTreeModel();
		TreeTable tt = new TreeTable(model);

		JScrollPane jsp = new JScrollPane(tt);
		p.addFill(jsp);
		
		return p;
	}
	
	
}
