/**
 * 
 */
package com.lts.caloriecount.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

/**
 * A data flavor that represents an object in the local VM.
 * 
 * <H2>NOTE</H2>
 * This class may throw an exception the first time it is referenced by the program.
 * This happens if the method {@link DataFlavor#DataFlavor(String)} throws an exception,
 * which is hopefully never, or at least the sign of a configuration problem.
 * 
 * @author cnh
 *
 */
public class LocalVMTransferable implements Transferable
{
	static public DataFlavor JVM_OBJECT_FLAVOR;
	static public DataFlavor[] JVM_OBJECT_FLAVORS;

	protected Object myData;
	

	/**
	 * <P>
	 * Static blocks are problematic because, if they encounter an exception, 
	 * it appears the first time the program references the class. The alternative,
	 * however is basically the same --- somehow detect the first time a static
	 * is referenced and then perform the operation that may throw an exception.
	 * In the event that an exception is encountered, it is rethrown...at the location
	 * where the class is first referenced by the program.
	 * </P>
	 * 
	 * <P>
	 * Both approaches have problems, but the lazy initialization approach complicates
	 * the source code.  Hence the use of the static block approach.
	 * </P>
	 */
	static {
		initializeStaticData();
	}
	
	
	public LocalVMTransferable(Object data)
	{
		myData = data;
	}
	
	static protected void initializeDataFlavor()
	{
		try
		{
			MimeType mime = new MimeType();
			mime.setPrimaryType("application");
			mime.setSubType("x-java-jvm-local-objectref");
			mime.setParameter("class", "java.lang.Object");
			
			JVM_OBJECT_FLAVOR = new DataFlavor(mime.toString());
		}
		catch (MimeTypeParseException e)
		{
			throw new RuntimeException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	static protected void initializeStaticData()
	{
		initializeDataFlavor();
		JVM_OBJECT_FLAVORS = new DataFlavor[] {JVM_OBJECT_FLAVOR};
	}
	
	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException
	{
		if (flavor.isMimeTypeEqual(JVM_OBJECT_FLAVOR))
			return myData;
		else
		{
			throw new UnsupportedFlavorException(flavor);
		}
	}

	
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return JVM_OBJECT_FLAVORS;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return flavor.isMimeTypeEqual(JVM_OBJECT_FLAVOR);
	}
	
}