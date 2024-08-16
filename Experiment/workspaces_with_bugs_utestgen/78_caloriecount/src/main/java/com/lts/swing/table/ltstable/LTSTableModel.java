package com.lts.swing.table.ltstable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;

import com.lts.swing.table.ModifiableTableModel;
import com.lts.swing.table.TableModelHelper;

/**
 * An implementation of TableModel based on a list.
 * 
 * <P>
 * This class implements TableModel using an approach where the model stores 
 * the data itself for display.  This is sometimes called "brute force".
 * </P>
 * 
 * <P>
 * Clients need to set the ColumnNames property in order for the table to display
 * the correct column names.
 * </P>
 * 
 * <P>
 * The class uses String.class as the class for all columns.
 * </P>
 * 
 * <P>
 * The class assumes that the data comes in the form of arrays (Object[]).
 * If some other form is desired, the following methods will need to be overridden:
 * </P>
 * 
 * <UL>
 * <LI>{@link #getValueAt(int, int)}</LI>
 * <LI>{@link #insert(int, Object)}</LI>
 * <LI>{@link #setValueAt(Object, int, int)}</LI>
 * </UL>
 * 
 * <P>
 * For a more general solution, see the {@see com.lts.swing.table.rowmodel}
 * package
 * </P>
 * 
 * @author cnh
 */
public class LTSTableModel implements ModifiableTableModel
{
	protected TableModelHelper myHelper;
	protected List myData;
	protected String[] myColumnNames;
	
	
	public LTSTableModel()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		myHelper = new TableModelHelper(this);
		myData = new ArrayList();
	}
	
	
	public String[] getColumnNames()
	{
		return myColumnNames;
	}
	
	public void setColumnNames(String[] columnNames)
	{
		myColumnNames = columnNames;
	}
	
	
	public void addTableModelListener(TableModelListener l)
	{
		myHelper.addListener(l);
	}

	public Class<?> getColumnClass(int columnIndex)
	{
		return String.class;
	}

	public int getColumnCount()
	{
		return myColumnNames.length;
	}
	
	
	public String getColumnName(int columnIndex)
	{
		return myColumnNames[columnIndex];
	}

	public int getRowCount()
	{
		return myData.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object[] row = (Object[]) myData.get(rowIndex);
		return row[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myHelper.removeListener(l);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		Object[] row = (Object[]) myData.get(rowIndex);
		row[columnIndex] = aValue;
	}

	public void insert(int index, Object data)
	{
		Object[] row = (Object[]) data;
		myData.add(index, row);
	}

	public void remove(int index)
	{
		myData.remove(index);
	}

	public void replace(int index, Object data)
	{
		myData.set(index, data);
	}

	public Object getRowData(int index)
	{
		return myData.get(index);
	}
}
