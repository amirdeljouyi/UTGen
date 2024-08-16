package com.lts.application.data.plugable;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.data.ApplicationData;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.MultiElementAppData;
import com.lts.io.IOUtilities;

abstract public class PluggableApplicationRepository implements ApplicationRepository
{
	abstract protected MultiElementAppData createApplicationData();

	private ApplicationRepository myRepository;
	private RepositoryStorageMethod myStorageMethod;
	private MultiElementAppData myApplicationData;
	
	
	public RepositoryStorageMethod getStorageMethod()
	{
		return myStorageMethod;
	}
	
	public void setStorageMethod(RepositoryStorageMethod method)
	{
		myStorageMethod = method;
	}
	
	public ApplicationRepository getRepository()
	{
		return myRepository;
	}
	
	public void setRepository(ApplicationRepository repository)
	{
		myRepository = repository;
	}
	
	
	public void setApplicationData(MultiElementAppData data)
	{
		myApplicationData = data;
	}
	
	
	public void close() throws ApplicationException
	{
		myRepository.close();
	}

	public void commit() throws ApplicationException
	{
		myRepository.commit();
	}

	public void commitAs(File outfile) throws ApplicationException
	{
		myRepository.commitAs(outfile);

	}

	public void delete() throws ApplicationException
	{
		myRepository.delete();
	}

	public ApplicationData getApplicationData() throws ApplicationException
	{
		if (null == myApplicationData)
		{
			myApplicationData = createApplicationData();
			loadApplicationData(myApplicationData);
		}
		
		return myApplicationData;
	}

	public InputStream getInputStream(String entry) throws ApplicationException
	{
		return myRepository.getInputStream(entry);
	}

	public OutputStream getOutputStream(String entry, boolean append)
			throws ApplicationException
	{
		return myRepository.getOutputStream(entry, append);
	}

	public File getRepositoryFile() throws ApplicationException
	{
		return myRepository.getRepositoryFile();
	}

	public boolean removeEntry(String entry) throws ApplicationException
	{
		return myRepository.removeEntry(entry);
	}

	public void rollback() throws ApplicationException
	{
		myRepository.rollback();
	}

	public void storeApplicationData(MultiElementAppData data) throws ApplicationException
	{
		for (Enum e : data.getDataElements())
		{
			ApplicationDataElement pade = data.get(e);
			myStorageMethod.writeData(myRepository, pade, e.toString());
		}
	}

	/**
	 * Populate the entries in an instance of MultiElementAppData with data obtained 
	 * from the repository.
	 * 
	 * TODO: This method should really just populate the instance of appdata instead
	 * of using this weird scheme.  In the interest of getting something working, 
	 * I have ignored the issue.
	 * 
	 * @param appdata
	 * @throws ApplicationException
	 */
	public void loadApplicationData (MultiElementAppData appdata) throws ApplicationException
	{
		for (Enum e : appdata.getDataElements())
		{
			ApplicationDataElement element;
			element = (ApplicationDataElement) myStorageMethod.readData(myRepository, e.toString());
			if (null != element)
				appdata.setEntry(e, element);
		}
	}
	
	
	public Object read(String entry) throws ApplicationException
	{
		InputStream istream = null;
	
		try
		{
			istream = myRepository.getInputStream(entry);
			return myStorageMethod.read(istream);
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}
	
	public void write (String entry, Object data) throws ApplicationException
	{
		OutputStream ostream = null;
		
		try
		{
			ostream = myRepository.getOutputStream(entry, false);
			myStorageMethod.write(ostream, data);
		}
		finally
		{
			IOUtilities.close(ostream);
		}
	}
	

	public void storeApplicationData (ApplicationData data) throws ApplicationException
	{
		MultiElementAppData mead = (MultiElementAppData) data;
		storeApplicationData(mead);
	}

	@Override
	public List<String> listEntries(String name) throws ApplicationException
	{
		return myRepository.listEntries(name);
	}
}
