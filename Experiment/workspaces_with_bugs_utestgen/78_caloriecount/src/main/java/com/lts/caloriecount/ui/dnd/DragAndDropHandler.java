package com.lts.caloriecount.ui.dnd;

import javax.swing.JComponent;
import javax.swing.TransferHandler.TransferSupport;

public interface DragAndDropHandler
{
	public enum DragAndDropOperations
	{
		Copy,
		Cut,
		Move,
		Paste
	}

	/**
	 * Does the handler support a particular operation like move?
	 * 
	 * <P>
	 * The basic method calls like {@link #copy(JComponent)} do not indicate the overall
	 * nature of what is going on.  Copy is used for both exporting data to the clipboard
	 * and for moving data.  In order to allow control over what the handler can be 
	 * used for, this method allows it to explicitly indicate what it allows and what it
	 * does not.
	 * </P>
	 * 
	 * @param operation The drag and drop operation in question.
	 * @return Whether it is supported or not.
	 */
	boolean supportsOperation(DragAndDropOperations operation);
	
	Object copy(JComponent c);
	void paste(TransferSupport support, Object data);
	void delete(JComponent source, Object data);
	
	void moveMarkData (JComponent comp);
	Object moveCopyData (JComponent comp);
	void moveDeleteData (JComponent comp);
	void movePasteData(TransferSupport support, Object data);
}
