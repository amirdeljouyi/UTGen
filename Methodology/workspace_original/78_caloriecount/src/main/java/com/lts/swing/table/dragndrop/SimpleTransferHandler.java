package com.lts.swing.table.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

/**
 * A class that simplifies Java drag and drop.
 * 
 * <H2>Quickstart</H2>
 * Subclass this class then implement
 * <UL>
 * <LI>getDataFlavors</LI>
 * <LI>getOperations</LI>
 * </UL>
 * 
 * <P>
 * For those operations you want to support, implement...
 * </P>
 * <UL>
 * <LI>cut</LI>
 * <LI>copy</LI>
 * <LI>paste</LI>
 * </UL>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
abstract public class SimpleTransferHandler extends TransferHandler
{
	abstract public int getActions();
	abstract protected void	paste(JComponent component, SimpleTransferData std);
	abstract protected void cut(JTable table, SimpleTransferData std);

	protected Class myComponentClass;
	protected Object myActions;
	
	public int getSourceActions(JComponent c)
	{
		if (null == c)
			return NONE;
		
		Class clazz = c.getClass();
		if (myComponentClass == clazz)
			return getActions();
		else
			return NONE;
	}

	
	public SimpleTransferHandler()
	{
		initialize();
	}
	
	protected void initialize()
	{
		myComponentClass = JTable.class;
		myActions = getActions();
	}
	
	/**
	 * Can this transfer handler import data from the provided source component?
	 * 
	 * <P>
	 * By default, this method ignores the supplied data flavors.
	 * </P>
	 * 
	 * <P>
	 * This method defaults to {@link #canImport(JComponent)}, which basically 
	 * means that if the specified component's class is equals equivalent to 
	 * {@link #myComponentClass}.
	 * </P>
	 * 
	 * @param component The component to import from.
	 * @param flavors The data flavors that the component supports(?)
	 */
	public boolean canImport(JComponent component, DataFlavor[] flavors)
	{
		return canImport(component);
	}
	
	/**
	 * Can this transfer handler import data?
	 * 
	 * <P>
	 * This method defaults to return 
	 * component.getClass().equals(myComponentClass).
	 * </P>
	 * 
	 * @param component The source for the Drag and drop operation.
	 * @return true if we can import from that component, false otherwise.
	 */
	public boolean canImport(JComponent component)
	{
		return component.getClass().equals(myComponentClass);
	}
	
	/**
	 * Import some data from some data source.
	 * 
	 * <P>
	 * When this method has been called it signals one of the following 
	 * events:
	 * </P>
	 * 
	 * <UL>
	 * 		<LI>
	 * 		The user selected some data, highlighted it, and then used control-c or 
	 * 		some such to signal that the data should be copied.
	 * 		</LI>
	 * 		<LI>
	 * 		The suer selected some rows and columns and then used cut copy the 
	 *      existing data.
	 * 		</LI>
	 * </UL>
	 * 
	 * <P>
	 * At some other point in time, the user selects the destination rows and columns
	 * for the data and signals that the data should be imported.
	 * </P>
	 * 
	 * <P>
	 * This implementation's basic approach is that the class of the source component
	 * must match the class that transfer handler is setup to deal with (essentially,
	 * are they of the same class.)  If not, the import is rejected.
	 * </P>
	 * 
	 * <P>
	 * In addition, the class of the transferHandler must match the tyep of transferhandler
	 * that this handler is setup to use.  
	 * </P>
	 * 
	 * <P>
	 * If all of these preconditions are met, then the subclass methods are called 
	 * to actually handle the imports.
	 */
	public boolean importData(JComponent component, Transferable transferable)
	{
		if (!canImport(component))
			return false;
		
		if (!(transferable instanceof SimpleTransferData))
			return false;
		
		SimpleTransferData std = (SimpleTransferData) transferable;
		
		paste(component, std);
		return false;
	}
	
	/**
	 * Copy some data.
	 * 
	 * <P>
	 * This method is invoked when a component that this transfer handler is "in charge 
	 * of" receives an event like a control-C (copy) or control-X (cut).  The data should
	 * not be removed at this point because a) it may not be removed at all (in the case 
	 * of copy) or b) it should not be removed until it has been pasted elsewhere (as 
	 * in cut).
	 * </P>
	 * 
	 * <P>
	 * In our case, we create a SimpleTransferData, which records the source table and 
	 * the rows that were selected in that table, and returns.
	 * </P>
	 * 
	 * @param c The component to copy from.
	 * @return A record of what was selected at the time (see above).
	 */
	protected Transferable createTransferable(JComponent c)
	{
		if (!(c instanceof JTable))
		{
			return null;
		}
		
		JTable table = (JTable) c;
		return copy(table);
	}
	
	protected Transferable copy (JTable table)
	{
		int[] sel = table.getSelectedRows();
		SimpleTransferData data = new SimpleTransferData(table, sel);		
		return data;
	}
	
	/**
	 * If this is a cut operation, then remove the source data.
	 * 
	 * <P>
	 * This method only applies to cut operations.  This method may be called for 
	 * copy and paste operations, but in those cases, the call should be ignored.
	 * </P>
	 * 
	 * <P>
	 * This method is called on the object that the data in a cut/copy/paste operation
	 * originated from.  The default implementation checks the operation and, in
	 * the case of copy and paste, simply returns.  In the case of the cut operation,
	 * the method calls cut.
	 * </P>
	 * 
	 * @param component The component that originated some data.  The instance of 
	 * this class should be the transfer handler for said component.
	 * 
	 * @param data The data copied, via {@link #createTransferable(JComponent)}.
	 * @param action The action (cut, copy or paste).
	 */
	protected void exportDone(JComponent component, Transferable data, int action)
	{
		if (!(component instanceof JTable))
			return;
		
		JTable table = (JTable) component;
		
		if (!(data instanceof SimpleTransferData))
			return;
		
		SimpleTransferData std = (SimpleTransferData) data;
		
		if (action == COPY || action == COPY_OR_MOVE)
			return;
		
		cut(table, std);
	}
	
}
