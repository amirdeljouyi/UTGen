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
package com.lts.application.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

import com.lts.LTSException;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationMessages;
import com.lts.application.data.ApplicationData;
import com.lts.io.IOUtilities;
import com.lts.xmlser.XmlObjectInputStream;
import com.lts.xmlser.XmlObjectOutputStream;

/**
 * An ArchiveRepository that stores its application data as a single XML file.
 * 
 * <H2>Description</H2>
 * This archive creates a sinlge entry in the archive called "data.xml" that 
 * contains the ApplicationData object.  The object is serialized and restored
 * via the XMLObjectInputStream and XMLObjectOutputStream classes.
 * 
 * @see com.lts.xml.XMLObjectInputStream
 * @see com.lts.xml.XMLObjectOutputStream
 * @author cnh
 */
public class SimpleZipRepository extends ArchiveRepository
{
	public static final String ENTRY_APPLICATION_DATA = "/data.xml";
	
	public SimpleZipRepository (File zipfile, File tempdir, boolean createBackups) throws ApplicationException
	{
		super(zipfile, tempdir, createBackups);
	}
	
	/**
	 * Get the application data from the repository.
	 * 
	 * <H2>Description</H2>
	 * The method tries to load the application data from the entry named by 
	 * the ENTRY_APPLICATION_DATA constant.
	 * 
	 * @return The data for the application, which should be a subclass of 
	 * ApplicationData.  null is returned if the entry does not exist.
	 * 
	 * @throws ApplicationException  This is thrown for a variety of reasons.
	 * The error codes, from ApplicationMessages, and the conditions under
	 * which the exception/code is use include:
	 * <UL>
	 * <LI>ERROR_REPOSITORY_ILLEGAL_STATE - if the repository has been 
	 * closed or deleted.
	 * <LI>ERROR_REPOSITORY_READ - if an exception is caught while trying to read
	 * the entry.
	 * <LI>ERROR_REPOSITORY_WRONG_TYPE - if the entry did not end up being an instance
	 * of ApplicationData.
	 * </UL>
	 */
	public ApplicationData getApplicationData() throws ApplicationException
	{
		if (null == getZipArchive())
		{
			String key = ApplicationMessages.ERROR_REPOSITORY_ILLEGAL_STATE;
			throw new ApplicationException(key);
		}
		
		InputStream istream = null;
		
		try
		{
			istream = getZipArchive().getInputStream(ENTRY_APPLICATION_DATA);
			if (null == istream)
				return null;
			
			XmlObjectInputStream in = new XmlObjectInputStream(istream);
			in.setForgiving(true);
			Object o = in.readObject();
			if (null == o)
				return null;
			
			if (!(o instanceof ApplicationData))
			{
				String expected = ApplicationData.class.getName();
				String actual = o.getClass().getName();
				String fname = getZipArchive().toString();
				Object[] data = new Object[] { fname, ENTRY_APPLICATION_DATA, expected, actual };
				String key = ApplicationMessages.ERROR_REPOSITORY_WRONG_TYPE;
				throw new ApplicationException (key, data); 
			}
			
			return (ApplicationData) o;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String key = ApplicationMessages.ERROR_REPOSITORY_READ;
			String fname = getZipArchive().toString();
			throw new ApplicationException(e, key, fname, ENTRY_APPLICATION_DATA);
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}

	/**
	 * Store the application data as a single entry in the ZIP file whose name is 
	 * defined by ENTRY_APPLICATION_DATA.  The entry contains an XML file that is 
	 * a serialized representation of the data.
	 * 
	 * @throws ApplicationException if a problem is encountered.  The message is 
	 * set to ApplicationMessages.ERROR_REPOSITORY_WRITE and the data contains the
	 * application and repository file name.
	 * 
	 * <P>
	 * This method will also throw this is exception if the archive has already 
	 * been closed or the like.  In that situation, the key will be set to 
	 * ApplicationMessages.ERROR_REPOSITORY_ILLEGAL_STATE.
	 * 
	 * @see #ENTRY_APPLICATION_DATA
	 * @see XMLObjectOutputStream
	 * @see ApplicationMessages#ERROR_REPOSITORY_WRITE
	 * @see ApplicationMessages#ERROR_REPOSITORY_ILLEGAL_STATE
	 */
	public void storeApplicationData(ApplicationData data) throws ApplicationException
	{
		OutputStream ostream = null;
		try
		{
			if (null == getZipArchive())
			{
				String key = ApplicationMessages.ERROR_REPOSITORY_ILLEGAL_STATE;
				throw new ApplicationException(key);
			}
			
			ostream = getZipArchive().getOutputStream(ENTRY_APPLICATION_DATA);
			XmlObjectOutputStream out = new XmlObjectOutputStream(ostream);
			out.writeObject(data);
			out.close();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String key = ApplicationMessages.ERROR_REPOSITORY_WRITE;
			String fileName = getZipArchive().getArchiveFile().toString();
			throw new ApplicationException(e, key, fileName);
		}
		finally
		{
			IOUtilities.close(ostream);
		}
	}

	@Override
	public List<String> listEntries(String name) throws ApplicationException
	{
		try
		{
			List<String> list = new ArrayList<String>();
			
			for (Object entry : getZipArchive().basicList())
			{
				ZipEntry zentry = (ZipEntry) entry;
				if (zentry.getName().startsWith(name))
				{
					list.add(zentry.getName());
				}
			}
			
			return list;
		}
		catch (IOException e)
		{
			throw new ApplicationException("Error trying to list contents");
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (LTSException e)
		{
			throw new ApplicationException("Error trying to list contents", e);
		}
	}

}
