package com.lts.caloriecount.ui.frequent;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.lts.application.Application;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.caloriecount.ui.dnd.DragAndDropHandler;
import com.lts.caloriecount.ui.dnd.TransferHandlerAdaptor;
import com.lts.caloriecount.ui.dnd.DragAndDropHandler.DragAndDropOperations;
import com.lts.event.CallbackListenerHelper;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleMouseListener;

/**
 * A class to simplify selecting an item from a JTable.
 * 
 * <P>
 * This class simplifies the process of creating a window that displays a JTable from 
 * which the user is supposed to select a row.  The typical scenario is where the user
 * can select one of the rows, cancel out, select from a larger list of data, or 
 * create a new row. 
 * </P>
 * 
 * <P>
 * The class supports the typical scenario by putting three buttons below the Table: 
 * create, other and cancel.  Users of this class should define a subclass that defines
 * each of the {@link #cancel()}, {@link #createEntry()}, and {@link #selectOtherEntry()}
 * methods to handle each of these cases.
 * </P>
 * 
 * <P>
 * The class signals the user's selection by setting the {@link #mySelection} field 
 * to the selected data and then calling {@link #closeWindow()}.
 * </P>
 * 
 * <P>
 * Configuring the window is accomplished by overriding the {@link #initialize()} method,
 * setting fields appropriately, and then calling the superclass version of that method.
 * Some of the settings and their functions are:
 * </P>
 * 
 * <P>
 * <TABLE border="1">
 * <TR><TD><B>Field</B></TD><TD><B>Description</B></TD></TR>
 * <TR>
 *     <TD>myHeadingString</TD>
 *     <TD>
 *         If non-null, this is used to create a JLabel that appears at the top of
 *         the window.  For example "Select Person".
 *     </TD>
 * </TR>
 * <TR>
 *     <TD>myShow&lt;create, other, cancel&gt;Button</TD>
 *     <TD>
 *         (defaults to true).  Set to false if you do not want the corresponding 
 *         button to appear in the control panel.  By default all the buttons appear.
 *     </TD>
 * </TR>
 * </TABLE>
 * 
 *     
 *     
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
abstract public class JTableSelectPanel extends CalorieCountPanel
{
	abstract protected JTable createTable();
	abstract protected void selectOtherEntry();
	abstract protected void selectEntry(int selection);
	abstract protected DragAndDropHandler createDragAndDropHandler();
	protected CallbackListenerHelper myHelper = new CallbackListenerHelper();

	public static final String PROPERTY_COLUMN_WIDTH = "columnWidth";
	
	protected boolean myShowCreateButton = true;
	protected JButton myCreateButton;
	
	protected boolean myShowOtherButton = true;
	protected JButton myOtherButton;
	
	protected boolean myShowCancelButton = true;
	protected JButton myCancelButton;
	
	protected boolean myAllowDrag = false;
	
	protected boolean myShowSelectButton = true;
	protected JButton mySelectButton;
	
	protected Object[][] myButtonActionMap;
	
	protected JTable myTable;
	protected String myHeadingString;
	protected String myWindowTitle;
	protected Object mySelection;

	protected void cancel()
	{
		mySelection = null;
		closeWindow();
	}
	
	public Object getSelection()
	{
		return mySelection;
	}
	
	
	public JPanel createBottomPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		for (Object[] row : buildButtonMap())
		{
			String label = (String) row[0];
			ActionListener action = (ActionListener) row[1];
			JButton button = new JButton(label);
			button.addActionListener(action);
			panel.addButton(button,5);
		}
		
		return panel;
	}
	
	
	protected Object[][] buildButtonMap()
	{
		return buildDefaultButtonMap();
	}
	
	protected Object[][] buildDefaultButtonMap()
	{
		SimpleAction selectAction = new SimpleAction() {
			public void action () {
				performSelect();
			}
		};
		
		SimpleAction otherAction = new SimpleAction() {
			public void action () {
				selectOtherEntry();
			}
		};
		
		SimpleAction cancelAction = new SimpleAction() {
			public void action() {
				cancel();
			}
		};
		
		
		Object[][] spec = new Object[][] {
				{ "Select", selectAction },
				{ "Other",  otherAction },
				{ "Cancel", cancelAction }
		};
		
		return spec;
	}
	

	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createTablePanel());
		
		return panel;
	}
	
	
	protected JPanel createTablePanel()
	{
		myTable = createTable();
		
		DragAndDropHandler handler = createDragAndDropHandler();
		if (null != handler)
		{
			TransferHandlerAdaptor adapt = new TransferHandlerAdaptor(handler);
			myTable.setTransferHandler(adapt);
			
			boolean supportsDrag = (handler.supportsOperation(DragAndDropOperations.Move));
			myTable.setDragEnabled(supportsDrag);
		}
		
		SimpleMouseListener listener = new SimpleMouseListener() {
			protected void doubleClick() {
				performSelect();
			}
		};
		
		myTable.addMouseListener(listener);
		restoreColumnWidths();
		
		JScrollPane jsp = new JScrollPane(myTable);
		LTSPanel panel = new LTSPanel();
		panel.addFill(jsp);
		return panel;
	}
	
	
	protected void performSelect()
	{
		int selection = myTable.getSelectedRow();
		if (-1 == selection)
			return;
		
		selectEntry(selection);
		closeWindow();
	}
	
	public JPanel createTopPanel()
	{
		if (null == myHeadingString)
			return null;
		
		LTSPanel panel = new LTSPanel();
		
		Font font = new Font("ariel", Font.BOLD, 12);
		JLabel label = new JLabel(myHeadingString);
		label.setFont(font);
		
		return panel;
	}
	
	public void setVisible(boolean isVisible)
	{
		mySelection = null;
		super.setVisible(isVisible);
	}
	
	
	public Object gatherData()
	{
		getWindow().setVisible(true);
		return mySelection;
	}
	
	protected void selectEntry(Object o)
	{
		if (null == o)
			return;
		
		mySelection = o;
		getWindow().setVisible(false);
	}
	
	
	protected void saveWindowSize()
	{
		super.saveWindowSize();
		saveColumnWidths();
	}
	
	
	protected void saveColumnWidths()
	{
		super.saveWindowSize();
		
		if (!definesWindowProperties())
			return;
		
		if (null == myTable)
			return;
		
		Application app = Application.getInstance();
		TableColumnModel tcm = myTable.getColumnModel();
		int count = tcm.getColumnCount();
		for (int index = 0; index < count; index++)
		{
			TableColumn col = tcm.getColumn(index);
			String name = buildColumnWidthPropertyName(index);
			String value = Integer.toString(col.getPreferredWidth());
			app.setProperty(name, value);
		}
	}
	
	
	protected void restoreColumnWidths()
	{
		if (null == myWindowBasePropertyName)
			return;
		
		if (null == myTable)
			return;
		
		Application app = Application.getInstance();
		int index = 0;
		String value = getColumnWidthProperty(app, index);
		
		TableColumnModel tcm = myTable.getColumnModel();
		while (null != value && tcm.getColumnCount() > index)
		{
			String[] fields = value.split("\\.");
			if (null != fields && fields.length > 0)
			{
				String strColumnWidth = fields[fields.length - 1];
				int columnWidth = Integer.parseInt(strColumnWidth);
				TableColumn col = tcm.getColumn(index);
				col.setWidth(columnWidth);
			}
			
			index++;
			value = getColumnWidthProperty(app, index);
		}
	}
	
	
	protected String getColumnWidthProperty(Application app, int index)
	{
		String name = buildColumnWidthPropertyName(index);
		String value = app.getProperty(name);
		return value;
	}
	
	private String buildColumnWidthPropertyName(int index)
	{
		String extension = "columnWidth" + "." + index;
		String name = buildPropertyNameForWindow(extension);
		return name;
	}
}
