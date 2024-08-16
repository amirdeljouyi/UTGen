package com.lts.application.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lts.LTSException;
import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationData;
import com.lts.io.IOUtilities;
import com.lts.io.ImprovedFile;
import com.lts.io.ImprovedFile.FileException;
import com.lts.util.ListUtils;
import com.lts.xmlser.XmlObjectInputStream;
import com.lts.xmlser.XmlObjectOutputStream;

/**
 * An instantiateable application repository that uses a directory as the 
 * "backing store" for its contents.
 * 
 * @author cnh
 */
public class DirectoryRepository extends AbstractRepository
{
	private ImprovedFile myDirectory;
	private ImprovedFile myTempDirectory;
	
	public DirectoryRepository (File directory, File tempDirectory)
		throws ApplicationException
	{
		initialize(directory, tempDirectory);
	}
	
	protected void initialize(File directory, File tempDirectory)
			throws ApplicationException
	{
		myDirectory = new ImprovedFile(directory);		
		myTempDirectory = new ImprovedFile(tempDirectory);
	}
	
	protected ImprovedFile getDirectory()
	{
		return myDirectory;
	}
	
	protected ImprovedFile getTempDirectory()
	{
		return myTempDirectory;
	}
	
	public void close() throws ApplicationException
	{
		try
		{
			if (getTempDirectory().exists())
				getTempDirectory().deleteAll();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Caught exception while trying to delete temp files " + getTempDirectory();
			throw new ApplicationException(e, msg);
		}
	}

	public void commit() throws ApplicationException
	{
		try
		{
			if (getDirectory().exists())
			{
				File backup = new File(getDirectory().toString() + ".bak");
				getDirectory().move(backup);
			}
			
			ImprovedFile file = new ImprovedFile(getTempDirectory());
			getTempDirectory().move(getDirectory());
			myTempDirectory = new ImprovedFile(file);
			if (!myTempDirectory.isDirectory())
			{
				if (!myTempDirectory.mkdirs())
				{
					String msg = "Could not create temp directory, " + myTempDirectory;
					throw new ApplicationException(msg);
				}
			}
		}
		catch (FileException e)
		{
			String msg = "rename/move failed";
			throw new ApplicationException(e, msg);
		}
	}

	public void commitAs(File outfile) throws ApplicationException
	{
		myDirectory = new ImprovedFile(outfile);
		commit();
	}

	public void delete() throws ApplicationException
	{
		try
		{
			getDirectory().deleteAll();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Could not delete repository directory, " + getDirectory();
			
			throw new ApplicationException(msg, e);
		}
	}

	public static final String DEFAULT_DATA_FILE = "data.xml";
	
	public ApplicationData getApplicationData() throws ApplicationException
	{
		XmlObjectInputStream xois = null;
		InputStream istream = null;
		
		try
		{
			istream = getInputStream(DEFAULT_DATA_FILE);
			if (null == istream)
				return null;
			
			xois = new XmlObjectInputStream(istream);
			return (ApplicationData) xois.readObject();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Caught exception while trying to get application data from "
				+ DEFAULT_DATA_FILE;
			throw new ApplicationException(e, msg);
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}
	
	
	public void storeAsXml (String entry, Object o) throws ApplicationException
	{
		XmlObjectOutputStream xoos = null;
		
		try
		{
			xoos = getXmlOutputStream(entry, false);
			xoos.write(o);
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error trying to store entry " + entry;
			throw new ApplicationException(msg,e);
		}
		finally
		{
			if (null != xoos)
				xoos.close();
		}
	}
	
	
	public Object loadXml (String entry) throws ApplicationException
	{
		XmlObjectInputStream xois = null;
		try
		{
			xois = getXmlInputStream(entry);
			if (null == xois)
				return null;
			else
				return xois.readObject();
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (LTSException e)
		{
			String msg = "Error trying to load entry " + entry + ", from " + myDirectory;
			throw new ApplicationException(msg, e);
		}
		finally
		{
			if (null != xois)
				xois.close();
		}
	}
	
	public XmlObjectOutputStream getXmlOutputStream(String entry, boolean append)
		throws ApplicationException
	{
		XmlObjectOutputStream xoos = new XmlObjectOutputStream(getOutputStream(entry, append));
		return xoos;
	}
	
	public XmlObjectInputStream getXmlInputStream (String entry)
		throws ApplicationException
	{
		try
		{
			InputStream istream = getInputStream(entry);
			if (null == istream)
				return null;
			
			XmlObjectInputStream xois = new XmlObjectInputStream(getInputStream(entry));
			return xois;
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Error opening XML object input stream for " + entry;
			throw new ApplicationException(e, msg);
		}
	}

	public InputStream getInputStream(String entry) throws ApplicationException
	{
		File inputFile = new File(getDirectory(), entry);
		if (!inputFile.exists())
			return null;
		
		try
		{
			return new FileInputStream(inputFile);
		}
		catch (FileNotFoundException e)
		{
			String msg = "Could not find " + inputFile;
			throw new ApplicationException(msg, e);
		}
	}

	public OutputStream getOutputStream(String entry, boolean append)
			throws ApplicationException
	{
		File outputFile = new File(getTempDirectory(), entry);
		
		try
		{
			FileOutputStream fos = new FileOutputStream(outputFile, append);
			return fos;
		}
		catch (IOException e)
		{
			String msg = "Error opening output file, " + outputFile;
			throw new ApplicationException(msg, e);
		}
	}

	public File getRepositoryFile() throws ApplicationException
	{
		return getDirectory();
	}

	public boolean removeEntry(String entry) throws ApplicationException
	{
		ImprovedFile file = new ImprovedFile(getDirectory(), entry);
		
		try
		{
			if (!file.exists())
				return true;
			
			file.deleteAll();
			return true;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Caught exception while trying to remove " + file;
			throw new ApplicationException(e, msg);
		}
	}

	public void rollback() throws ApplicationException
	{
		try
		{
			getDirectory().deleteAll();
			getTempDirectory().move(getDirectory());
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Caught exception while trying to roll back repository";
			throw new ApplicationException(e, msg);
		}
	}

	public void storeApplicationData(ApplicationData data) throws ApplicationException
	{
		OutputStream ostream = null;
		XmlObjectOutputStream xoos = null;
		
		try
		{
			ostream = getOutputStream(DEFAULT_DATA_FILE,false);
			xoos = new XmlObjectOutputStream(ostream);
			xoos.write(data);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg =
				"Caught exception while trying to store the application data to "
				+ DEFAULT_DATA_FILE;
			
			throw new ApplicationException(e, msg);
		}
		finally
		{
			if (null != xoos)
				xoos.close();
			
			IOUtilities.close(ostream);
		}
	}

	public boolean repositoryUsesDirectories()
	{
		return true;
	}
	
	
	public List<String> listEntries (String name)
	{
		File file = new File(myDirectory, name);
		String[] contents = file.list();
		List<String> list = new ArrayList<String>();
		ListUtils.addAll(list, contents);
		return list;
	}
}
