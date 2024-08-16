package com.lts.caloriecount.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.data.ApplicationData;
import com.lts.io.ImprovedFile;
import com.lts.io.ImprovedFile.FileException;
import com.lts.util.StringUtils;

/**
 * A repository implemented as a single file.
 * 
 * <P>
 * Note that this class violates the repository contract in that it is really not a 
 * virtual file system (VFS).  If time allows, VFS should either be removed from the 
 * {@link ApplicationRepository} interface, or implemented in this type of repository.
 * </P>
 * 
 * <P>
 * This style repository really only supports {@link #storeApplicationData(ApplicationData)} 
 * and {@link #getApplicationData()}.  Subclasses must implement those two methods 
 * in order to be instantiatable.
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class SingleFileRepository implements ApplicationRepository
{
	abstract public void storeApplicationData(ApplicationData data) throws ApplicationException;
	abstract public ApplicationData getApplicationData() throws ApplicationException;
	
	private File myFile;
	private File myTempFile;
	
	public SingleFileRepository(File file, String prefix, String suffix)
			throws ApplicationException
	{
		initialize(file, prefix, suffix);
	}
	
	protected void initialize(File file, String prefix, String suffix)
			throws ApplicationException
	{
		try
		{
			myFile = file;
			myTempFile = File.createTempFile(prefix, suffix); 
			myTempFile.deleteOnExit();
			if (myFile.exists())
			{
				ImprovedFile.copyFromToFile(file, myTempFile);
			}
		}
		catch (IOException e)
		{
			String msg =
				"Error trying to initialize repository.  "
				+ "file: " + file
				+ ", prefix: " + prefix
				+ ", suffix: " + suffix;
			
			throw new ApplicationException(msg, e); 
		}
	}
	
	@Override
	public void close() throws ApplicationException
	{
		if (myTempFile.exists() && !myTempFile.delete())
		{
			String msg = 
				"Could not remove temp file: " + myTempFile;
			
			throw new ApplicationException (msg);
		}
	}

	@Override
	public void commit() throws ApplicationException
	{
		commitAs(myFile);
	}

	@Override
	public void commitAs(File outfile) throws ApplicationException
	{
		if (outfile.exists())
		{
			try
			{
				ImprovedFile.backup(outfile, true);
			}
			catch (FileException e)
			{
				String msg =
					"Error trying to backup file, " 
					+ outfile
					+ ", reason " 
					+ e.getReason();
				
				throw new ApplicationException(msg,e);
			}
		}
		
		try
		{
			ImprovedFile.copyFromToFile(myTempFile, outfile);
		}
		catch (IOException e)
		{
			throw new ApplicationException (
					"Error trying to copy "
					+ myTempFile
					+ " to "
					+ outfile,
					e);
		}
	}

	@Override
	public void delete() throws ApplicationException
	{
		if (myFile.exists() && !myFile.delete())
		{
			throw new ApplicationException (
					"Failed to delete " 
					+ myFile);
		}
	}

	@Override
	public InputStream getInputStream(String entry) throws ApplicationException
	{
		if (StringUtils.trim(entry) != null)
		{
			return null;
		}
		else
		{
			try
			{
				return new FileInputStream(myTempFile);
			}
			catch (FileNotFoundException e)
			{
				String msg =
					"Error trying to open input stream to file " 
					+ myTempFile;
				throw new ApplicationException(msg, e);
			}
		}
	}

	@Override
	public OutputStream getOutputStream(String entry, boolean append)
			throws ApplicationException
	{
		if (StringUtils.trim(entry) != null)
		{
			return null;
		}
		else
		{
			try
			{
				return new FileOutputStream(myTempFile, append);
			}
			catch (FileNotFoundException e)
			{
				String msg =
					"Error trying to open output stream to "
					+ myTempFile;
				throw new ApplicationException(msg, e);
			}
		}
	}

	@Override
	public File getRepositoryFile() throws ApplicationException
	{
		return myFile;
	}

	@Override
	public boolean removeEntry(String entry) throws ApplicationException
	{
		throw new ApplicationException("Operation not supported on a single file repostiory");
	}

	@Override
	public boolean repositoryUsesDirectories()
	{
		return false;
	}

	@Override
	public void rollback() throws ApplicationException
	{
		if (myTempFile.exists())
		{
			myTempFile.delete();
		}
		
		if (myFile.exists())
		{
			try
			{
				ImprovedFile.copyFromToFile(myFile, myTempFile);
			}
			catch (IOException e)
			{
				String msg =
					"Error trying to roll back repository by copying contents "
					+ "of the original file, " 
					+ myFile
					+ " to the temp file, "
					+ myTempFile;
				
				throw new ApplicationException(msg, e);
			}
		}
	}


	@Override
	public List<String> listEntries(String name) throws ApplicationException
	{
		return new ArrayList<String>();
	}
	protected File getTempFile()
	{
		return myTempFile;
	}

}
