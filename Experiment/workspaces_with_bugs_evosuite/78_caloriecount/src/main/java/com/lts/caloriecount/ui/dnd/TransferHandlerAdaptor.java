package com.lts.caloriecount.ui.dnd;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.lts.caloriecount.ui.dnd.DragAndDropHandler.DragAndDropOperations;


/**
 * Translate the TransferHandler class methods into DragAndDropHandler methods.
 * 
 * <H3>Quick Start</H3>
 * (Example that uses a JTable)
 * 
 * <P>
 * <CODE>
 * <PRE>
 * JComponent comp;
 * JTable table;
 * JTableDragAndDrop handler;
 * ...
 * &lt;initialize comp&gt;
 * &lt;initialize table&gt;
 * &lt;initialize handler&gt;
 * ... 
 * TransferHandlerAdaptor adaptor = new TransferHandlerAdaptor(handler);
 * comp.setTransferHandler(adaptor);
 * comp.setDragEnabled(true);
 * </PRE>
 * </CODE> 
 * </P>
 * 
 * <H3>Description</H3>
 * See the package documentation for details on how to use this class.
 * 
 * <P>
 * Briefly, a class that implements {@link DragAndDropHandler}, such as 
 * {@link JTableDragAndDrop}, must be created to use this class.  Pass that 
 * object along to a new instance of this class.
 * </P> 
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class TransferHandlerAdaptor extends TransferHandler
{
	protected DragAndDropOperations myOperation;
	protected DragAndDropHandler myHandler;
	
	public TransferHandlerAdaptor(DragAndDropHandler handler)
	{
		initialize(handler);
	}

	protected void initialize(DragAndDropHandler handler)
	{
		myHandler = handler;
	}

	protected DataFlavor[] getDataFlavors(TransferSupport support)
	{
		return LocalVMTransferable.JVM_OBJECT_FLAVORS;
	}
	
	@Override
	public boolean canImport(TransferSupport support)
	{
		DataFlavor[] flavors = support.getDataFlavors();
		DataFlavor[] supported = getDataFlavors(support);
		
		for (DataFlavor flavor : flavors)
		{
			for (DataFlavor supportedFlavor : supported)
			{
				if (supportedFlavor.isMimeTypeEqual(flavor))
					return true;
			}
		}

		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent comp)
	{
		if (DragAndDropOperations.Move == myOperation)
			myHandler.moveMarkData(comp);
			
		Object data = myHandler.copy(comp);
		LocalVMTransferable trans = new LocalVMTransferable(data);
		return trans;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action)
	{
		switch (myOperation)
		{
			case Copy :
				break; // nothing extra needs to be done
				
			case Cut :
				myHandler.delete(source, data);
				break;
				
			case Move :
				myHandler.moveDeleteData(source);
				break;
			
			case Paste :
				break;
		}
		
		myOperation = null;
	}

	@Override
	public int getSourceActions(JComponent c)
	{
		return COPY_OR_MOVE;
	}

	
	@Override
	public boolean importData(TransferSupport support)
	{
		try
		{
			if (null == myOperation)
				myOperation = DragAndDropOperations.Paste;

			boolean result = false;
			
			if (myHandler.supportsOperation(myOperation))
			{
				Object data = getTransferData(support.getTransferable());
				if (myOperation == DragAndDropOperations.Move)
					myHandler.movePasteData(support, data);
				else
					myHandler.paste(support, data);
				
				result = true;
			}
			
			if (myOperation == DragAndDropOperations.Paste)
				myOperation = null;
			
			return result;
		}
		catch (UnsupportedFlavorException e)
		{
			return false;
		}
		catch (IOException e)
		{
			String msg = "Error pasting data";
			throw new RuntimeException(msg, e);
		}
	}

	protected Object getTransferData(Transferable transferable)
			throws UnsupportedFlavorException, IOException
	{
		return transferable.getTransferData(LocalVMTransferable.JVM_OBJECT_FLAVOR);
	}

	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action)
	{
		if (myHandler.supportsOperation(DragAndDropOperations.Move))
		{
			myOperation = DragAndDropOperations.Move;
			super.exportAsDrag(comp, e, action);
		}
	}

	@Override
	public void exportToClipboard(JComponent comp, Clipboard clip, int action)
			throws IllegalStateException
	{
		if (MOVE == action)
		{
			myOperation = DragAndDropOperations.Cut;
			if (myHandler.supportsOperation(myOperation))
				myHandler.moveCopyData(comp);
		}
		else
		{
			myOperation = DragAndDropOperations.Copy;
			if (myHandler.supportsOperation(myOperation))
				super.exportToClipboard(comp, clip, action);
		}
	}

	
}
