package com.lts.swing.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.lts.event.LTSMouseAdapter;

/**
 * A TableModel that can sort itself based on one of the columns in that 
 * table.
 * 
 * <P>
 * This class monitors when the user clicks on the containing TableModel's 
 * header row.  When the user clicks on the heading of a column, that column 
 * is used to sort the table.  If the user clicks on that same column again, 
 * the table is sorted by that column in reverse order.
 * </P>
 * 
 * <P>
 * The class "wraps" an instance of {@link TableModel}.  The inner table model
 * is used to contain the "actual" data, when this table keeps track of the 
 * rows as they are ordered by the user.
 * </P>
 * 
 * <P>
 * By default, this class contains a list of {@link SortingRow} to keep track
 * of the mapping from displayed row to actual row.   It uses {@link SortingRow#COMPARATOR}
 * to order the rows.
 * </P>
 * 
 * <P>
 * Subclasses that want to use some other method of ordering rows need to do the
 * following:
 * </P>
 * 
 * <UL>
 * <LI>Override {@link #getComparator(int)} to return the appropriate comparator
 * for the new ordering and given the value {@link #myAscending}.</LI>
 * </UL>
 * 
 * <P>
 * Subclasses that want to use a different class in mySortRows need to override 
 * {@link #rebuildSortRows()} to return a collection that contains the appropriate
 * data.  Those rows still need to be a subclass of SortingRow.
 * </P>
 * 
 * <UL>
 * <LI></LI>
 * <LI>Override {@link #getComparator(int)} to return the appropriate comparator
 * for the new ordering and given the value {@link #myAscending}.</LI>
 * </UL>
 * @author cnh
 *
 */
public class SortingTableModel implements TableModel
{
	private class ColumnMouseListener extends LTSMouseAdapter
	{
		JTable myTable;
		
		public ColumnMouseListener (JTable table)
		{
			initialize(table);
		}
		
		private void initialize (JTable table)
		{
			JTableHeader header = table.getTableHeader();
			header.addMouseListener(this);
			myTable = table;
		}
		
		public void mouseClicked (MouseEvent event)
		{
			selectHeader(event);
		}
	}
	
	
	static protected class SortingRow
	{
		public String value;
		public int virtualIndex;
		public int actualIndex;
		
		public SortingRow(Object theValue, int theVirtualIndex, int theActualIndex)
		{
			value = theValue.toString();
			virtualIndex = theVirtualIndex;
			actualIndex = theActualIndex;
		}
		
		static public Comparator ASCENDING = new Comparator() {
			public int compare (Object o1, Object o2) {
				SortingRow row1 = (SortingRow) o1;
				SortingRow row2 = (SortingRow) o2;
				return row1.value.compareToIgnoreCase(row2.value);
			}
		};
		
		static public Comparator DESCENDING = new Comparator() {
			public int compare (Object o1, Object o2) {
				SortingRow row1 = (SortingRow) o1;
				SortingRow row2 = (SortingRow) o2;
				return -1 * row1.value.compareToIgnoreCase(row2.value);
			}
		};
	}
	
	private TableModel myTableModel;
	private int myCurrentColumn;
	protected boolean myAscending = true;
	private List mySortRows;
	private Comparator myComparator;
	private Map<Integer, Integer> myVirtualToActual;
	private Map<Integer, Integer> myActualToVirtual;
	private TableModelHelper myHelper;
	
	
	public SortingTableModel (JTable table, TableModel model)
	{
		initialize(table, model);
	}
	
	
	public void initialize (JTable table, TableModel model)
	{
		myVirtualToActual = new HashMap<Integer, Integer>();
		myActualToVirtual = new HashMap<Integer, Integer>();
		myHelper = new TableModelHelper(model);
		
		myTableModel = model;
		myCurrentColumn = 0;
		
		TableModelListener listener = new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				processModelChanged(e);
			}
		};
		
		if (null != myTableModel)
			myTableModel.addTableModelListener(listener);
		
		MouseListener mouseListener = new ColumnMouseListener(table);
		table.addMouseListener(mouseListener);
		resort();
	}
	
	
	protected void processModelChanged(TableModelEvent e)
	{
		switch (e.getType())
		{
			case TableModelEvent.DELETE :
				processRowsDeleted(e.getFirstRow(), e.getLastRow());
				break;
				
			case TableModelEvent.INSERT :
				processRowsDeleted(e.getFirstRow(), e.getLastRow());
				break;
				
			case TableModelEvent.UPDATE :
				processRowsUpdated(e.getFirstRow(), e.getLastRow());
				break;
			
			default :
				String s = Integer.toString(e.getType());
				throw new IllegalArgumentException(s);
		}
	}


	protected void processRowsUpdated(int firstRow, int lastRow)
	{
		if (firstRow > lastRow)
		{
			int temp = firstRow;
			firstRow = lastRow;
			lastRow = temp;
		}
		
		for (int i = firstRow; i <= lastRow; i++)
		{
			processRowDeleted(i);
			processRowInserted(i);
		}
	}

	protected void processRowDeleted(int actualRow)
	{
		int virtualRow = myActualToVirtual.get(actualRow);
		myVirtualToActual.remove(virtualRow);
		myActualToVirtual.remove(actualRow);
		myHelper.fireRowRemoved(virtualRow);
	}

	protected void processRowsDeleted(int firstRow, int lastRow)
	{
		if (firstRow > lastRow)
		{
			int temp = firstRow;
			firstRow = lastRow;
			lastRow = temp;
		}
		
		for (int i = lastRow; i >= firstRow; i--)
		{
			processRowDeleted(i);
		}
	}
	
	
	protected void processRowsInserted(int firstRow, int lastRow)
	{
		if (firstRow > lastRow)
		{
			int temp = firstRow;
			firstRow = lastRow;
			lastRow = temp;
		}
		
		for (int i = firstRow; i <= lastRow; i++)
		{
			processRowInserted(i);
		}
	}


	protected void processRowInserted(int actualIndex)
	{
		Object value = myTableModel.getValueAt(actualIndex, myCurrentColumn);
		SortingRow row = new SortingRow(value, actualIndex, actualIndex);
		
		int index = Collections.binarySearch(mySortRows, myComparator);
		if (index < 0)
		{
			index = (-1 * index) - 1;  
		}
		
		row.virtualIndex = index;
		mySortRows.add(index, row);
		myVirtualToActual.put(row.virtualIndex, row.actualIndex);
		myActualToVirtual.put(row.actualIndex, row.virtualIndex);
		myHelper.fireRowAdded(row.virtualIndex);
	}
	
	
	public void addTableModelListener(TableModelListener l)
	{
		myHelper.addListener(l);
	}

	private void selectHeader(MouseEvent event)
	{
		if (!(event.getSource() instanceof JTableHeader))
			return;
		
		JTableHeader header = (JTableHeader) event.getSource();
		TableColumnModel columnModel = header.getColumnModel();
		int viewColumn = columnModel.getColumnIndexAtX(event.getX());
		if (viewColumn == myCurrentColumn)
		{
			myAscending = !myAscending;
		}
		else
		{
			myAscending = true;
			myCurrentColumn = viewColumn;
		}
		
		resort();
	}

	
	protected void resort()
	{
		myComparator = getComparator(myCurrentColumn);
		rebuildSortRows();
		myActualToVirtual = new HashMap<Integer, Integer>();
		myVirtualToActual = new HashMap<Integer, Integer>();
		Collections.sort(mySortRows, myComparator);
		
		int count = mySortRows.size();
		for (int i = 0; i < count; i++)
		{
			SortingRow row = (SortingRow) mySortRows.get(i);
			row.virtualIndex = i;
			myActualToVirtual.put(row.actualIndex, row.virtualIndex);
			myVirtualToActual.put(row.virtualIndex, row.actualIndex);
		}
		
		myHelper.fireTableChanged();
	}


	/**
	 * A hook to allow subclasses to use a different component object in 
	 * mySortRows.
	 * 
	 * <P>
	 * See the class description for instructions for details on using a 
	 * different class in {@link #mySortRows}.
	 * </P>
	 */
	protected void rebuildSortRows()
	{
		mySortRows = new ArrayList();
		
		int count = myTableModel.getRowCount();
		for (int i = 0; i < count; i++)
		{	
			String value = myTableModel.getValueAt(i, myCurrentColumn).toString();
			SortingRow row = new SortingRow(value, i, i);
			mySortRows.add(row);
		}
	}
	
	
	protected Comparator getComparator(int column)
	{
		if (myAscending)
			return SortingRow.ASCENDING;
		else
			return SortingRow.DESCENDING;
	}
	
	
	public Class<?> getColumnClass(int columnIndex)
	{
		return myTableModel.getColumnClass(columnIndex);
	}

	public int getColumnCount()
	{
		return myTableModel.getColumnCount();
	}

	public String getColumnName(int columnIndex)
	{
		return myTableModel.getColumnName(columnIndex);
	}

	public int getRowCount()
	{
		return myTableModel.getRowCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		int actualIndex = myVirtualToActual.get(rowIndex);
		return myTableModel.getValueAt(actualIndex, columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		int actualIndex = myVirtualToActual.get(rowIndex);
		return myTableModel.isCellEditable(actualIndex, columnIndex);
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myTableModel.removeTableModelListener(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		int actualIndex = myVirtualToActual.get(rowIndex);
		myTableModel.setValueAt(value, actualIndex, columnIndex);
	}
	
	
}
