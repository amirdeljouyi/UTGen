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
/*
 * Copyright (c) 2003 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.application.repository;

import com.lts.application.ApplicationRepository;

/**
 * A class that simplifies creating an ApplicationRepository.
 * 
 * <P>
 * Subclasses need only implement the load and store methods while this class
 * handles all the other details associated with an application repository.
 * 
 * <P>
 * The load method should cause the repository to represent the same data that
 * is currently on the disk.  In the case of a temporary repository or one 
 * that has never been saved, this is the same as creating a new repository 
 * instance.
 * 
 * <P>
 * When load is called, the subclass should reconstitute the application 
 * specific data from the underlying repository file.
 * 
 * <P>
 * The store method should cause the underlying file that contains the 
 * repository data to be the same as the data currently in the object --- 
 * basically save the data.
 * 
 * <P>
 * Whe store is called, the subclass should serialize the the application 
 * data to the underlying repository file.
 */
public abstract class AbstractRepository implements ApplicationRepository 
{
	public AbstractRepository ()
	{}
	
	

	private boolean tempRepository;
	
	public boolean isTempRepository()
	{
		return this.tempRepository;
	}
	
	public boolean getTempRepository()
	{
		return isTempRepository();
	}
	
	public void setTempRepository(boolean isTempRepository)
	{
		this.tempRepository = isTempRepository;
	}	
}
