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
package com.lts.application;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.lts.event.Callback;
import com.lts.io.ImprovedFile;

/**
 * A callback that deletes some files when called.
 */
public class DeleteFileCallback implements Callback
{
	private Set<ImprovedFile> fileset;
	
	public void addFile (File f)
	{
		if (null == f)
			return;
		
		if (null == this.fileset)
			this.fileset = new HashSet<ImprovedFile>();

		ImprovedFile ifile;
		if (f instanceof ImprovedFile)
			ifile = (ImprovedFile) f;
		else
			ifile = new ImprovedFile(f);

		this.fileset.add(ifile);
	}
	
	public boolean removeFile (File f)
	{
		if (null == f)
			return false;
		else if (null == this.fileset)
			return false;
		else
			return this.fileset.remove(f);
	}
	
	
	public void callback(Object data)
	{
		if (null == this.fileset)
			return;
		
		Application.getInstance().deleteTempFiles(fileset);
	}

}
