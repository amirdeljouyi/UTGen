package com.lts.swing.table.dragndrop;

/**
 * Capture the state of a component when a drag and drop is initiated.
 * 
 * <P>
 * One issue with Java drag and drop is that the way it is set up, the 
 * TransferHandler may be presented in one state when DnD is initiated, and a 
 * different state when the drop happens.  
 * 
 * <P>
 * For example, suppose you try to drag some rows from a JTable to another row 
 * in the same JTable.  When the drag is initiated, the JTable will return one 
 * set of rows for getSelectedRows when the drag is initiated, but it will return 
 * the destination row when the drop occurs. 
 * </P>
 * 
 * <P>
 * This object encapsulates the component state, if any in between the time when
 * the drag is started, and the time the drop occurs.
 * </P>
 * 
 * @author cnh
 *
 */
public class TransferMark
{

}
