package com.lts.application.data.plugable;

import java.io.InputStream;
import java.io.OutputStream;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;

/**
 * This interface represents the approach taken to store myData in an 
 * ApplicationRepository.
 * 
 * <P>
 * Example methods: XML, binary, etc.
 * </P>
 * 
 * <P>
 * The purpose of the interface is to separate how the myData is stored in terms 
 * of the form of the myData file from the nature that the repository takes.
 * For example, a directory that contains the files as opposed to a zip file. 
 * </P>
 * 
 * @author cnh
 *
 */
public interface RepositoryStorageMethod
{
	public void writeData (ApplicationRepository repository, Object data, String entry) 
		throws ApplicationException;
	
	public Object readData (ApplicationRepository repository, String entry)
		throws ApplicationException;
	
	public Object read(InputStream istream) throws ApplicationException;
	public void write (OutputStream ostream, Object data) throws ApplicationException;
}	
