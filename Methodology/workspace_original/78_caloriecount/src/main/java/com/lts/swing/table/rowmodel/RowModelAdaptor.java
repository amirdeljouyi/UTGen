package com.lts.swing.table.rowmodel;

import java.util.Comparator;

/**
 * A class that simplifies the creation of objects that implement the RowModel interface.
 * 
 * <P>
 * Subclasses must define the following methods:
 * </P>
 * 
 * <UL>
 * <LI>{@link #getValueAt(int, Object)}</LI>
 * <LI>{@link #subclassInitialize()}</LI>
 * </UL>
 * 
 * @author cnh
 */
abstract public class RowModelAdaptor implements RowModel
{
	/**
	 * Return a value for a column given the row data.
	 * 
	 * <P>
	 * This value will be handed to a JTable for display.
	 * </P>
	 */
	abstract public Object getValueAt(int column, Object row);
	
	
	abstract protected String[] subclassGetColumnNames();

	
	protected Class[] myColumnClasses;
	protected String[] myColumnNames;
	protected boolean[] myColumnEditable;
	
	/**
	 * Initialize object properties to their default values.
	 * 
	 * <P>
	 * Central to default initialization is a call to {@link #subclassGetColumnNames()} .
	 * The value returned by that method is used to determine the number
	 * of columns in the table.  The following actions are taken:
	 * 
	 * <UL>
	 * <LI></LI>
	 * <LI>Set myColumnNames to the value returned by subclassGetColumnNames.</LI>
	 * <LI>myColumnClasses is set to String.class for all columns.</LI>
	 * <LI>myColumnEditable is set to false for all columns.</LI>
	 * </UL>
	 * 
	 * <P>
	 * Subclasses that override this method should either call this version of the 
	 * method, or initialize the above mentioned fields.  Failure to do so will 
	 * result in unpredictable behavior.
	 * </P>
	 */
	protected void initialize()
	{
		myColumnNames = subclassGetColumnNames();
		myColumnClasses = new Class[myColumnNames.length];
		for (int i = 0; i < myColumnClasses.length; i++)
		{
			myColumnClasses[i] = String.class;
		}
		
		myColumnEditable = new boolean[myColumnNames.length];
		for (int i = 0; i < myColumnEditable.length; i++)
		{
			myColumnEditable[i] = false;
		}
	}
	
	protected RowModelAdaptor()
	{
		initialize();
	}
	
	public Class getColumnClass(int column)
	{
		return myColumnClasses[column];
	}

	public int getColumnCount()
	{
		return myColumnNames.length;
	}

	public String getColumnName(int column)
	{
		return myColumnNames[column];
	}

	public String[] getColumnNames()
	{
		return myColumnNames;
	}

	public Comparator getComparator()
	{
		return null;
	}

	public boolean isColumnEditable(int col)
	{
		return myColumnEditable[col];
	}

	/**
	 * Set a column for a row to a value --- note that the default version of this
	 * method throws an UnsupportedOperationException.
	 */
	public void setValueAt(Object row, int column, Object value)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * Set a column for a row to a value --- note that the default version of this
	 * method throws an UnsupportedOperationException.
	 */
	public void update(Object destination, Object source)
	{
		throw new UnsupportedOperationException();
	}
}
