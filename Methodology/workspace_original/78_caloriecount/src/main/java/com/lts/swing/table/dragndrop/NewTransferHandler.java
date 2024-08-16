package com.lts.swing.table.dragndrop;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * <H3>Quick Start</H3>
 * <UL>
 * 		<LI>(Optional) Subclass {@link LTSTransferable}</LI>
 * 		<LI>Create an instance of {@link SimpleTransferHandler}</LI>
 * 		<LI>
 * 			Create an instance of this class, passing the new STH to the 
 * 			constructor.
 * 		</LI>
 * </UL>
 * 
 * <H3>Description</H3>
 * This class serves as a {@link TransferHandler} that delegates some of its 
 * responsibilities to an instance of {@link SimpleTransferHandler}.  The 
 * SimpleTransferHandler presents a much easier API to code to than the 
 * TransferHandler class.
 * 
 * <H3>Methods that are Not Overridden</H3>
 * Not all all the methods defined by {@link TransferHandler} are overridden by
 * this class.  The methods are:
 * <UL>
 * <LI>exportToClipboard</LI>
 * <LI>exportAsDrag</LI>
 * </UL>
 * 
 * <P>
 * These methods control the whole cut/copy/drag mechanism.  That is, 
 * exportToClipboard, for example, takes the following actions:
 * 
 * <UL>
 * <LI>Ensure that the source component can be used as a source to copy from.</LI>
 * <LI>Copy the data by creating a Transferable</LI>
 * <LI>Get the DataFlavor objects that the Transferable supports</LI>
 * <LI>Get the actual data by calling Transferable.getTransferData</LI>
 * <LI>Signal an end to the copy by calling TransferHandler.exportDone</LI>
 * </UL>
 * </P>
 * 
 * <P>
 * Extending {@link TransferHandler#exportToClipboard(JComponent, Clipboard, int)} 
 * means that, in addition to whatever else you were going to do, you now have to 
 * take care of all of the above actions.
 * </P>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class NewTransferHandler extends TransferHandler
{
	static protected TransferableFactory ourFactory;

	protected SimplifiedTransferHandler myHandler;

	synchronized static protected void initializeClass()
	{
		if (null != ourFactory)
			return;

		ourFactory = new LTSTransferableFactory();
	}

	public NewTransferHandler(SimplifiedTransferHandler transferHandler)
	{
		initialize(transferHandler);
	}

	protected void initialize(SimplifiedTransferHandler sth)
	{
		myHandler = sth;
	}

	public boolean canImport(JComponent c, DataFlavor[] flavors)
	{
		return ourFactory.canImport(c, flavors);
	}

	public int getSourceActions(JComponent c)
	{
		return COPY_OR_MOVE;
	}

	@Override
	public boolean importData(TransferSupport support)
	{
		Component comp = support.getComponent();
		LTSTransferable trans = (LTSTransferable) support.getTransferable();
		Object data = trans.getData();
		return myHandler.paste(comp, data);
	}

	protected Transferable createTransferable(JComponent component)
	{
		return ourFactory.createTransferable(component);
	}

	
	@Override
	protected void exportDone(JComponent comp, Transferable data, int action)
	{
		if (MOVE == action)
			myHandler.remove(comp, data);
	}

}
