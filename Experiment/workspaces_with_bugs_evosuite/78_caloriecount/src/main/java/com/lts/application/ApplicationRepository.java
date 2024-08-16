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
package com.lts.application;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.lts.LTSException;
import com.lts.application.data.ApplicationData;
import com.lts.application.repository.VirtualFileSystem;

/**
 * An object that loads and stores data to/from a common location.
 * 
 * <P>
 * Objects that implement this interface provide a number of services:
 * <UL>
 * <LI>Keep data in a common location (directory, archive, etc.)
 * <LI>Provide a "virtual file system" for the application
 * <LI>Provide "transactional" commit/rollback operations
 * </UL>
 * 
 * <P>
 * All changes made to a repository in the form of store method calls or data
 * written to output streams is not saved unless the commit method is called. In
 * fact, the state of the repository can be restored to just after the last
 * commit was called by invoking the rollback method.
 * 
 * @author cnh
 */
public interface ApplicationRepository extends VirtualFileSystem
{
	/**
	 * Remove any underlying persistent storage for the repository.
	 * 
	 * <H2>Description</H2>
	 * After calling this method, the repository is no longer in a defined state.
	 * Calling any method other than delete or close should result in an 
	 * ApplicationException being thrown, with the message set to 
	 * ApplicationMessages.ERROR_REPOSITORY_INVALID_STATE.
	 * If clients want to save data, a new repository must be created.
	 * 
	 * @throws ApplicationException See description.
	 * @see #delete()
	 * @see ApplicationMessages#ERROR_REPOSITORY_ILLEGAL_STATE
	 */
	public void delete() throws ApplicationException;
	
	/**
	 * Synchronize the persistent storage with the repositories current version 
	 * of the data.
	 * 
	 * @throws ApplicationException
	 */
	public void commit() throws ApplicationException;
	
	/**
	 * Restore the repository to the state it was in after the last commit or 
	 * rollback.
	 * 
	 * @throws ApplicationException
	 */
	public void rollback() throws ApplicationException;
	
	/**
	 * Return the file associated with the repository, or null not applicable.
	 * 
	 * @return The associated file or null.
	 * @throws ApplicationException
	 */
	public File getRepositoryFile() throws ApplicationException;
	
	/**
	 * Release any resources associated with the repository, such as files, temp 
	 * storage, etc.
	 * 
	 * <H2>Description</H2>
	 * This method informs the repository that it is no longer going to be used.
	 * From the stand point of data, it is the same as calling rollback, but after
	 * the call, the repository cannot be used again.  Any subsequent calls should
	 * result in ApplicationException being thrown, with the message set to 
	 * ApplicationMessages.ERROR_REPOSITORY_ILLEGAL_STATE.
	 * 
	 * @throws ApplicationException See description.
	 */
	public void close() throws ApplicationException;

	/**
	 * Save the current state of the repository to the specified location.
	 * 
	 * <P>
	 * This method is similar to the "save as..." functionality that many
	 * applications offer: the original file is not changed, but the new file
	 * contains any changes made since the last save. After this method
	 * completes, the object now refers to the new location, not the old
	 * location.
	 * 
	 * <P>
	 * The original repository still exists, but a new ApplicationRepository
	 * must be created in order to access it. This being the case, the reopened
	 * repository will have forgotten about the changes that are saved using
	 * this method.
	 * 
	 * <P>
	 * This method differs from a copy in that a copy would not include the
	 * current state of the repository --- such an operation would copy the
	 * repository as it existed since the last commit.
	 * 
	 * <P>
	 * This method differs from a rename followed by a commit in that the
	 * original repository is not effected by this operation.
	 * 
	 * @param outfile
	 *            The file where the repository should be saved.
	 * 
	 * @throws LTSException
	 *             If problems are encountered while trying to save the
	 *             repository.
	 */
	public void commitAs(File outfile) throws ApplicationException;
	
	
	/**
	 * Remove an entry from the repository.
	 * 
	 * @param entry
	 *            The entry to remove.
	 * @return True if the entry existed and was removed, false if the entry did
	 *         not exist in the repository to begin with. If the entry exists
	 *         but cannot be removed instead of returning false the method
	 *         throws an ApplicationException.
	 * @throws ApplicationException
	 *             If there is a problem removing the entry.
	 */
	public boolean removeEntry (String entry) throws ApplicationException;

	/**
	 * Create or append to an entry and return an output stream to it.
	 * 
	 * <P>
	 * Note that calling this method with append set to false will replace any
	 * entry that currently exists with the same name. If no data is written to
	 * the output stream, this will result in a zero-length entry.
	 * 
	 * <P>
	 * Data written to the output stream will be saved if the stream is closed
	 * and {@link #commit()} or {@link #commitAs(File)} succeeds against the
	 * instance.
	 * 
	 * @param entry
	 *            Which entry to create or append to.
	 * @param append
	 *            Whether to append to the entry if it already exists. If set to
	 *            true, then the method will append to the entry. If set to
	 *            false, the method will remove any existing entry. This
	 *            parameter has no effect if the entry does not currently exist
	 *            in the repository.
	 * 
	 * @return An OutputStream to the entry.
	 * 
	 * @throws ApplicationException
	 *             If there is a problem accessing the entry.
	 */
	public OutputStream getOutputStream (String entry, boolean append) throws ApplicationException;
	
	
	/**
	 * Get an input stream to an entry in the repository, or return null if the
	 * entry does not currently exist in the repository.
	 * 
	 * @param entry
	 *            The desired entry.
	 * @return An InputStream or null if the entry does not exist.
	 * @throws ApplicationException
	 *             If there is a problem accessing the entry.
	 */
	public InputStream getInputStream (String entry) throws ApplicationException;
	
	/**
	 * Return the application data, loading it if necessary.
	 * 
	 * <H2>Description</H2>
	 * This method should return the application data from the underlying repository 
	 * as it currently exists there.
	 * 
	 * @return The application data.
	 * @throws ApplicationException If a problem occurs while trying to load the data.
	 */
	public ApplicationData getApplicationData () throws ApplicationException;
	
	/**
	 * Set the underlying repository's version of the data.
	 * 
	 * <H2>Description</H2>
	 * After calling this method, subsequent calls to commit should cause the application 
	 * data to be written to the underlying storage in such a way that subsequent creation
	 * of a repository and calls to getApplicationData will return an object that is 
	 * "equals equivalent" to what is passed to this method.
	 * 
	 * <P>
	 * Subsequent calls to getApplicationData should return an object that is "equals equivalent"
	 * to what is passed to this method.
	 * 
	 * <P>
	 * If the data is large enough, calling this method can require writing data out to 
	 * temporary storage, hence the throws clause.
	 * 
	 * @param data The new version of the data.
	 * @throws ApplicationException If an error is encountered while synchronizing the data.
	 */
	public void storeApplicationData (ApplicationData data) throws ApplicationException;
	
	/**
	 * Return true if the application repository uses a directory as its repository file.
	 * 
	 * <P>
	 * For example, a repository that uses ZIP files would answer false.  A repository
	 * that used directories would return true.
	 * </P>
	 * 
	 * @return true if the repository uses directories, false otherwise.
	 */
	public boolean repositoryUsesDirectories();
}