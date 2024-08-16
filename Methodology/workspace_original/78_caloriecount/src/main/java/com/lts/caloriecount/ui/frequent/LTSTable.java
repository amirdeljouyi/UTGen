package com.lts.caloriecount.ui.frequent;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import com.lts.caloriecount.ui.dnd.JTableDragAndDrop;
import com.lts.caloriecount.ui.dnd.TransferHandlerAdaptor;
import com.lts.swing.SimpleMouseListener;

/**
 * A JTable that have a number of convenience features.
 * 
 * <P>
 * This class takes care of a number of common tasks associated with JTables
 * including:
 * </P>
 * 
 * <P>
 * <TABLE border="1">
 * <THEAD>
 * <TR>
 * 		<TD><B>Category</B></TD>
 * 		<TD><B>Item</B></TD>
 * 		<TD><B>Method</B></TD>
 * </TR>
 * </THEAD>
 * <TR>
 * 		<TD>Mouse</TD>
 * 		<TD>Single Click</TD>
 *		<TD>mouseSingle</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Double Click</TD>
 *		<TD>mouseDouble</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Right Click</TD>
 *		<TD>mouseRight</TD>
 * </TR>
 * <TR>
 * 		<TD>Key Listening</TD>
 * 		<TD>Enter/Return</TD>
 *		<TD>keyEnter</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Insert</TD>
 *		<TD>keyInsert</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Delete</TD>
 *		<TD>keyDelete</TD>
 * </TR>
 * <TR>
 * 		<TD>Drag and Drop</TD>
 * 		<TD>Cut</TD>
 *		<TD>dndCut</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Copy</TD>
 *		<TD>dndCopy</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Paste</TD>
 *		<TD>dndPaste</TD>
 * </TR>
 * <TR>
 * 		<TD></TD>
 * 		<TD>Move</TD>
 *		<TD>dndMove</TD>
 * </TR>
 * </TABLE>
 * </P>
 * 
 * <H3>Drag and Drop Support</H3>
 * The primary way that Drag and Drop is supported is by supplying table-specific
 * information to the various methods.  Rather than implementing
 * <CODE>
 * <PRE>
 *     TransferHandler.exportData({copy}, Transferable)
 * </PRE>
 * </CODE>
 * 
 * <P>
 * The developer can instead implement {@link #dndCopy()}, which supplies the rows
 * that need to be copied and returns the class-specific data.
 * </P>
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class LTSTable extends JTable
{
	private class InnerMouseListener extends SimpleMouseListener
	{
		public void showPopup(Component comp, int x, int y)
		{
			mouseShowPopup(comp, x, y);
		}

		protected void singleClick()
		{
			mouseSingle();
		}
		
		protected void doubleClick()
		{
			mouseDouble();
		}
	}
	
	protected JPopupMenu myPopupMenu;
	protected boolean myInitialized = false;
	protected ActionEvent myActionEvent;
	protected boolean myDragAndDropEnabled = false;
	protected boolean myKeyListenerEnabled = false;
	protected boolean myMouseListenerEnabled = false;
	private JTableDragAndDrop myDragAndDropHandler;
	
	public ActionEvent getActionEvent()
	{
		return myActionEvent;
	}
	
	public void setActionEvent(ActionEvent actionEvent)
	{
		myActionEvent = actionEvent;
	}

	public JPopupMenu getPopupMenu()
	{
		return myPopupMenu;
	}
	
	public void setPopupMenu(JPopupMenu menu)
	{
		myPopupMenu = menu;
	}
	
	protected void mouseSingle()
	{}
	
	protected void mouseDouble()
	{
		performDataSelect();
	}
	
	protected void mouseShowPopup(Component comp, int x, int y)
	{
		if (null == myPopupMenu)
			return;
		
		if (comp != this)
			return;
		
		myPopupMenu.show(this, x, y);
	}
	
	
	public LTSTable()
	{
		initialize();
	}
	
	public LTSTable(TableModel model)
	{
		super(model);
		initialize();
	}
	
	
	protected void initialize()
	{
		if (myInitialized)
			return;
		
		myInitialized = true;
		
		initializeMouseListener();
		initializeKeyListener();
		initializeDragAndDrop();
	}

	protected void mapKey(int key, String symbol, Action action)
	{
		KeyStroke keyStroke = KeyStroke.getKeyStroke(key, 0);
		getInputMap().put(keyStroke, symbol);
		getActionMap().put(symbol, action);
	}
	
	protected void initializeKeyListener()
	{
		if (!myKeyListenerEnabled)
			return;
		
		Action action;
		
		action = new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				setActionEvent(event);
				keyEnter();
			}
		};
		mapKey(KeyEvent.VK_ENTER, "return", action);
		
		mapKey(KeyEvent.VK_INSERT, "insert", action);
		action = new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				setActionEvent(event);
				keyInsert();
			}
		};
		
		action = new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				setActionEvent(event);
				keyDelete();
			}
		};
		mapKey(KeyEvent.VK_DELETE, "delete", action);
	}

	protected void keyDelete()
	{}

	protected void keyInsert()
	{}

	protected void keyEnter()
	{
		performDataSelect();
	}

	protected void performDataSelect()
	{
		int[] rows = getSelectedRows();
		if (rows.length == 1)
		{
			selectRow(rows[0]);
		}
		else if (rows.length > 1)
		{
			selectRows(rows);
		}
	}

	/**
	 * Perform some operation on the specified rows.
	 * 
	 * <P>
	 * This method usually signifies that the user selected two or more rows and 
	 * then hit the enter key.  This class does not define any particular reaction 
	 * to that event, but it does provide this method as a "hook" for developers 
	 * to implement such functionality with relative ease.
	 * </P>
	 * 
	 * @param rows The rows the user had selected.
	 */
	protected void selectRows(int[] rows)
	{
	}

	/**
	 * Perform some action in response to a double-click or to the enter key being
	 * hit.
	 * 
	 * <P>
	 * This method provides a hook to developers who want their JTable to take some 
	 * action in response to a double-click of the mouse or to the user hitting 
	 * the enter key.  The default implementation simply returns --- essentially
	 * ignoring the event.
	 * </P>
	 * 
	 * <P>
	 * If this method is called, then the developer can assume that either a double-click
	 * or event or an enter key event occurred.  The developer can also assume that 
	 * exactly one row was selected. 
	 * </P>
	 * 
	 * @param rowIndex The index of the selected row.
	 */
	protected void selectRow(int rowIndex)
	{
	}

	protected void initializeMouseListener()
	{
		if (myMouseListenerEnabled)
			addMouseListener(new InnerMouseListener());
	}
	
	
	protected void initializeDragAndDrop()
	{
		if (!myDragAndDropEnabled)
			return;
		
		setDragEnabled(true);
	}
	
	protected JTableDragAndDrop getDragAndDropHandler()
	{
		return myDragAndDropHandler;
	}
	
	
	protected void setDragAndDropHandler(JTableDragAndDrop handler)
	{
		myDragAndDropHandler = handler;
		TransferHandlerAdaptor adapt = new TransferHandlerAdaptor(handler);
		setTransferHandler(adapt);
		setDragEnabled(true);
	}
}
