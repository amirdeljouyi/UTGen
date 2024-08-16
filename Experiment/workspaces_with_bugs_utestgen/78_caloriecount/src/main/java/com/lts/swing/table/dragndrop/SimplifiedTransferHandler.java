package com.lts.swing.table.dragndrop;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.TransferHandler;


/**
 * A simplified approach to Drag and Drop support.
 * <P>
 * This class presents a simple approach to implementing Drag and Drop in Java. In order
 * to do this, it makes the following assumptions:
 * 
 * <UL>
 * <LI>All transfers are within the same Java VM.</LI>
 * <LI>All transfers use raw objects, no text, etc.</LI>
 * </UL>
 * 
 * </P>
 * 
 * <P>
 * If you need to implement Drag and Drop that, for example, can use a number of "data
 * flavors" such as text, files, objects, etc. then you are better off subclassing
 * {@link TransferHandler} rather than this class.
 * </P>
 * 
 * <P>
 * Subclasses will need to "know" what sort of data is being sent to them, which can mean
 * that all transfers are limited to the same JComponent.
 * </P>
 * 
 * <H3>Drag vs. Cut/Copy/Paste</H3>
 * Cut, copy and paste all use the clipboard as either the destination or the source for
 * their data. Drag is different in that it never uses the clipboard --- all data is kept
 * in memory during the transfer.
 * 
 * <H3>Class of Data</H3>
 * This interface does not specify the class of the data being copied or pasted.
 * The implementing class is responsible for ensuring that the proper types of 
 * data are being passed around.  This means that an instance of this transfer 
 * handler must know how to import data from <I>any</I> source that uses this 
 * class.
 * 
 * <P>
 * If your system is more complex than this, then it would be advisable to either
 * package the data in some sort of "envelope" class that all implementers know 
 * about, or use the TransferHandler mechanism directly.
 * </P>
 *  
 * @author cnh
 */
@SuppressWarnings("serial")
public interface SimplifiedTransferHandler
{
	/**
	 * Does this instance support cutting data to the clipboard?
	 * 
	 * @return true if it does, false otherwise.
	 */
	public boolean supportsCut();
	
	/**
	 * Does this class support copying data to the clipboard?
	 * 
	 * @return true if it does, false otherwise.
	 */
	public boolean supportsCopy();
	
	/**
	 * Does this class support pasting data from the clipboard?
	 * 
	 * @return true if it does, false otherwise.
	 */
	public boolean supportsPaste();
	
	/**
	 * Does this class support moving data via drag and drop?
	 * 
	 * @return true if it does, false otherwise.
	 */
	public boolean supportsMove();
	
	/**
	 * Paste some data to a component.
	 * 
	 * <P>
	 * Note that neither a clipboard nor the action that is being performed are 
	 * specified by this call.  The instance of this interface merely needs to 
	 * paste the data to the component.
	 * </P>
	 * 
	 * @param comp The destination JComponent.
	 * @param data The data to paste.
	 * @return true if the paste was successful, false otherwise.
	 */
	public boolean paste(Component comp, Object data);
	
	/**
	 * Copy some data from a component.
	 * 
	 * <P>
	 * This method should copy the "selected" data from the source component and
	 * return it.  The mechanism whereby the data to copy is identified, and the 
	 * class for the data is left to the implementor.
	 * </P>
	 * 
	 * @param comp The source for the data.
	 * @return The copied data.
	 */
	public Object copy(JComponent comp);
	
	/**
	 * Remove some data from a component, as a result of a cut or drag-move operation.
	 * 
	 * <P>
	 * This method should remove the data specified by the data argument from the 
	 * source JComponent.  The mechanism whereby the data to be removed is 
	 * identified, along with whether it is removed at all, is left up to the 
	 * implementation of this interface.
	 * </P>
	 * 
	 * @param comp The component that the data should be removed from.
	 * @param data The data to remove.
	 */
	public void remove(JComponent comp, Object data);
}
